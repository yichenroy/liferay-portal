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

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Element;
import org.dom4j.Node;

/**
 * @author Kenji Heigel
 */
public abstract class DefinitionPoshiElement extends PoshiElement {

	@Override
	public void parseReadableSyntax(String readableSyntax) {
		for (String readableBlock : getReadableBlocks(readableSyntax)) {
			if (readableBlock.startsWith("@") && !readableBlock.endsWith("}")) {
				String name = getNameFromAssignment(readableBlock);
				String value = getQuotedContent(readableBlock);

				addAttribute(name, value);

				continue;
			}

			if (isReadableSyntaxComment(readableBlock)) {
				add(PoshiNodeFactory.newPoshiNode(this, readableBlock));

				continue;
			}

			add(PoshiNodeFactory.newPoshiNode(this, readableBlock));
		}
	}

	@Override
	public String toReadableSyntax() {
		StringBuilder sb = new StringBuilder();

		for (PoshiElementAttribute poshiElementAttribute :
				toPoshiElementAttributes(attributeList())) {

			sb.append("\n@");

			sb.append(poshiElementAttribute.toReadableSyntax());
		}

		StringBuilder content = new StringBuilder();

		Node previousNode = null;

		for (Node node : Dom4JUtil.toNodeList(content())) {
			if (node instanceof PoshiComment) {
				PoshiComment poshiComment = (PoshiComment)node;

				content.append("\n");
				content.append(poshiComment.toReadableSyntax());
			}
			else if (node instanceof PoshiElement) {
				content.append("\n");

				if (previousNode == null) {
					content.deleteCharAt(content.length() - 1);
				}
				else if ((node instanceof PropertyPoshiElement) &&
						 (previousNode instanceof PropertyPoshiElement)) {

					content.deleteCharAt(content.length() - 1);
				}
				else if ((node instanceof VarPoshiElement) &&
						 (previousNode instanceof VarPoshiElement)) {

					content.deleteCharAt(content.length() - 1);
				}

				PoshiElement poshiElement = (PoshiElement)node;

				content.append(poshiElement.toReadableSyntax());
			}

			previousNode = node;
		}

		sb.append(createReadableBlock(content.toString()));

		String string = sb.toString();

		return string.trim();
	}

	protected DefinitionPoshiElement() {
	}

	protected DefinitionPoshiElement(Element element) {
		super(_ELEMENT_NAME, element);
	}

	protected DefinitionPoshiElement(
		List<Attribute> attributes, List<Node> nodes) {

		super(_ELEMENT_NAME, attributes, nodes);
	}

	protected DefinitionPoshiElement(
		PoshiElement parentPoshiElement, String readableSyntax) {

		super(_ELEMENT_NAME, parentPoshiElement, readableSyntax);
	}

	@Override
	protected String getBlockName() {
		return "definition";
	}

	protected String getElementName() {
		return _ELEMENT_NAME;
	}

	protected String getFileType() {
		return null;
	}

	@Override
	protected String getPad() {
		return "";
	}

	protected List<String> getReadableBlocks(String readableSyntax) {
		StringBuilder sb = new StringBuilder();

		List<String> readableBlocks = new ArrayList<>();

		for (String line : readableSyntax.split("\n")) {
			String trimmedLine = line.trim();

			if (trimmedLine.length() == 0) {
				sb.append("\n");

				continue;
			}

			if (trimmedLine.equals(line) && trimmedLine.startsWith("@")) {
				readableBlocks.add(line);

				continue;
			}

			if (trimmedLine.startsWith("definition {")) {
				continue;
			}

			String readableBlock = sb.toString();

			readableBlock = readableBlock.trim();

			if (isValidReadableBlock(readableBlock)) {
				readableBlocks.add(readableBlock);

				sb.setLength(0);
			}

			sb.append(line);
			sb.append("\n");
		}

		return readableBlocks;
	}

	protected String getReadableCommandKeyword() {
		if (getFileType().equals("testcase")) {
			return "test";
		}

		return getFileType();
	}

	@Override
	protected boolean isBalanceValidationRequired(String readableSyntax) {
		readableSyntax = readableSyntax.trim();

		if (readableSyntax.endsWith("}") &&
			(readableSyntax.startsWith("@") ||
			 readableSyntax.startsWith("setUp") ||
			 readableSyntax.startsWith("tearDown") ||
			 readableSyntax.startsWith(getReadableCommandKeyword()))) {

			return true;
		}

		return false;
	}

	protected boolean isElementType(String readableSyntax) {
		readableSyntax = readableSyntax.trim();

		if (!isBalancedReadableSyntax(readableSyntax)) {
			return false;
		}

		if (!readableSyntax.endsWith("}")) {
			return false;
		}

		for (String line : readableSyntax.split("\n")) {
			line = line.trim();

			if (line.startsWith("@")) {
				continue;
			}

			if (!line.equals("definition {")) {
				return false;
			}

			break;
		}

		return true;
	}

	private static final String _ELEMENT_NAME = "definition";

}