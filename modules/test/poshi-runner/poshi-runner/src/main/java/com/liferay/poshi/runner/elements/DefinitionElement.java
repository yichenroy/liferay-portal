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

import com.liferay.poshi.runner.util.Dom4JUtil;

import java.io.BufferedReader;
import java.io.StringReader;

import org.dom4j.Attribute;
import org.dom4j.Element;

/**
 * @author Kenji Heigel
 */
public class DefinitionElement extends PoshiElement {

	public DefinitionElement(Element element) {
		super("definition", element);
	}

	public DefinitionElement(String readableSyntax) {
		super("definition", readableSyntax);
	}

	@Override
	public void parseReadableSyntax(String readableSyntax) {
		try (BufferedReader bufferedReader = new BufferedReader(
				new StringReader(readableSyntax))) {

			StringBuilder sb = new StringBuilder();

			String line = null;

			while ((line = bufferedReader.readLine()) != null) {
				line = line.trim();

				if (line.length() == 0) {
					continue;
				}

				if (line.startsWith("@") && !line.contains("@description") &&
					!line.contains("@priority")) {

					String name = getNameFromAssignment(line);
					String value = getValueFromAssignment(line);

					addAttribute(name, value);

					continue;
				}

				if (line.startsWith("definition {")) {
					continue;
				}

				if ((line.startsWith("property") || line.startsWith("var")) &&
					(sb.length() == 0)) {

					addElementFromReadableSyntax(line);

					continue;
				}

				if (line.equals("}")) {
					sb.append(line);

					if (sb.length() > 1) {
						addElementFromReadableSyntax(sb.toString());

						sb.setLength(0);

						continue;
					}
				}

				sb.append(line);
				sb.append("\n");
			}

		}
		catch (Exception e) {
			System.out.println("Unable to generate the 'definition' element");
		}
	}

	@Override
	public String toReadableSyntax() {
		StringBuilder sb = new StringBuilder();

		for (Attribute attribute : Dom4JUtil.toAttributeList(attributeList())) {
			sb.append("\n@");

			String name = attribute.getName();
			String value = attribute.getValue();

			sb.append(getAssignment(name, value));
		}

		StringBuilder content = new StringBuilder();

		for (PoshiElement poshiElement :
				toPoshiElements(elements("property"))) {

			content.append(poshiElement.toReadableSyntax());
		}

		content.append("\n");

		for (PoshiElement poshiElement : toPoshiElements(elements("set-up"))) {
			content.append(poshiElement.toReadableSyntax());
		}

		content.append("\n");

		for (PoshiElement poshiElement :
				toPoshiElements(elements("tear-down"))) {

			content.append(poshiElement.toReadableSyntax());
		}

		for (PoshiElement poshiElement : toPoshiElements(elements("command"))) {
			content.append("\n");
			content.append(poshiElement.toReadableSyntax());
		}

		sb.append(createReadableBlock(content.toString()));

		String sbString = sb.toString();

		return sbString.trim();
	}

	@Override
	protected String getBlockName() {
		return "definition";
	}

	@Override
	protected String getPad() {
		return "";
	}

}