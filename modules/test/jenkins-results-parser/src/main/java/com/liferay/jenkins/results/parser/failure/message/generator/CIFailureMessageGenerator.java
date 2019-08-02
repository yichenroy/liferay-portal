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

package com.liferay.jenkins.results.parser.failure.message.generator;

import com.liferay.jenkins.results.parser.Dom4JUtil;

import org.dom4j.Element;

/**
 * @author Yi-Chen Tsai
 */
public class CIFailureMessageGenerator extends BaseFailureMessageGenerator {

	@Override
	public Element getMessageElement(String consoleText) {
		int index = consoleText.indexOf(_TOKEN_CI_ERROR);

		if (index == -1) {
			return null;
		}

		String snippet = consoleText.substring(
			index, consoleText.indexOf("\n", index));

		return Dom4JUtil.toCodeSnippetElement(snippet);
	}

	@Override
	public boolean isGenericCIFailure() {
		return true;
	}

	private static final String _TOKEN_CI_ERROR = "A CI failure has occurred.";

}