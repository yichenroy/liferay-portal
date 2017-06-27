/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.poshi.runner.elements;

import static com.liferay.poshi.runner.elements.ReadableSyntaxKeys.AND;
import static com.liferay.poshi.runner.elements.ReadableSyntaxKeys.AT_LOCATOR;
import static com.liferay.poshi.runner.elements.ReadableSyntaxKeys.GIVEN;
import static com.liferay.poshi.runner.elements.ReadableSyntaxKeys.THEN;
import static com.liferay.poshi.runner.elements.ReadableSyntaxKeys.THE_VALUE;
import static com.liferay.poshi.runner.elements.ReadableSyntaxKeys.WHEN;

import com.liferay.poshi.runner.util.Dom4JUtil;
import com.liferay.poshi.runner.util.StringUtil;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Attribute;
import org.dom4j.Element;

/**
 * @author Kenji Heigel
 */
public class ExecuteElement extends PoshiElement {

	public ExecuteElement(Element element) {
		super("execute", element);
	}

	public ExecuteElement(String readableSyntax) {
		super("execute", readableSyntax);
	}

	@Override
	public void parseReadableSyntax(String readableSyntax) {
		int contentStart = readableSyntax.indexOf("(") + 1;

		String classCommandName = readableSyntax.substring(0, contentStart - 1);

		classCommandName = classCommandName.replace(".", "#");

		int contentEnd = readableSyntax.lastIndexOf(")");

		String content = "";

		if (contentEnd > contentStart) {
			content = readableSyntax.substring(contentStart, contentEnd);
		}

		String executeType = "macro";

		if (content.contains("locator1") || content.contains("locator2") ||
			content.contains("value1") || content.contains("value2")) {

			executeType = "function";
		}

		addAttribute(executeType, classCommandName);

		if (content.length() == 0) {
			return;
		}

		String[] assignments = content.split(",");

		for (String assignment : assignments) {
			if (executeType.equals("macro")) {
				assignment = "var " + assignment;

				addElementFromReadableSyntax(assignment);

				continue;
			}

			String name = getNameFromAssignment(assignment);
			String value = getValueFromAssignment(assignment);

			addAttribute(name, value);
		}
	}

	@Override
	public String toReadableSyntax() {
		if (attributeValue("function") != null) {
			StringBuilder sb = new StringBuilder();

			for (Attribute attribute :
					Dom4JUtil.toAttributeList(attributeList())) {

				String name = attribute.getName();

				if (name.equals("function")) {
					continue;
				}

				String value = attribute.getValue();

				sb.append(getAssignment(name, value));

				sb.append(", ");
			}

			if (sb.length() > 2) {
				sb.setLength(sb.length() - 2);
			}

			String function = attributeValue("function");

			return _createReadableExecuteBlock(function, sb.toString());
		}

		String macro = attributeValue("macro");
		String readableSyntax = super.toReadableSyntax();

		return _createReadableExecuteBlock(macro, readableSyntax);
	}

	private static String _createReadableExecuteBlock(
		String commandName, String content) {

		StringBuilder sb = new StringBuilder();

		String pad = "\t";

		sb.append("\n\n");
		sb.append(pad);
		sb.append(commandName.replace("#", "."));
		sb.append("(");

		String trimmedContent = content.trim();

		if (!trimmedContent.equals("")) {
			if (content.contains("\n")) {
				content = content.replaceAll("\n", ",\n" + pad);
				content = content.replaceFirst(",", "");
				content = content + "\n" + pad;
			}

			sb.append(content);
		}

		sb.append(");");

		return sb.toString();
	}

	private void _addFunctionAttribute(
		String readableSyntax, String attributeName) {

		String attributeValue = getAttributeValue("'", "'", readableSyntax);

		if (attributeValue(attributeName + "1") == null) {
			addAttribute(attributeName + "1", attributeValue);

			return;
		}

		addAttribute(attributeName + "2", attributeValue);
	}

	private void _addFunctionAttributes(String readableSyntax) {
		String[] keys = {AT_LOCATOR, THE_VALUE};

		List<String> functionItems = StringUtil.partition(readableSyntax, keys);

		for (String functionItem : functionItems) {
			if (functionItem.contains(AT_LOCATOR)) {
				_addFunctionAttribute(functionItem, "locator");

				continue;
			}

			if (functionItem.contains(THE_VALUE)) {
				_addFunctionAttribute(functionItem, "value");

				continue;
			}

			addAttribute("function", _getClassCommandName(functionItem));
		}
	}

	private String _getClassCommandName(String readableSyntax) {
		int index = readableSyntax.indexOf("\n");

		if (index < 0) {
			index = readableSyntax.length();
		}

		String line = readableSyntax.substring(0, index);

		for (String key : READABLE_EXECUTE_BLOCK_KEYS) {
			if (!line.startsWith(key)) {
				continue;
			}

			Pattern pattern = Pattern.compile(".*?" + key + ".*?.([A-z]*)(.*)");

			Matcher matcher = pattern.matcher(line);

			if (matcher.find()) {
				StringBuilder sb = new StringBuilder();

				sb.append(matcher.group(1));

				String commandName = matcher.group(2);

				commandName = StringUtil.removeSpaces(commandName);

				if (commandName.length() > 0) {
					sb.append("#");
					sb.append(commandName);
				}

				return sb.toString();
			}
		}

		return null;
	}

	private String _getReadableSyntaxCommandPhrase(String classCommandName) {
		StringBuilder sb = new StringBuilder();

		if (classCommandName.contains("#")) {
			String className = classCommandName.split("#")[0];

			sb.append(className);

			sb.append(" ");

			String commandName = classCommandName.split("#")[1];

			String commandPhrase = toPhrase(commandName);

			sb.append(commandPhrase);

			return sb.toString();
		}

		return classCommandName;
	}

}