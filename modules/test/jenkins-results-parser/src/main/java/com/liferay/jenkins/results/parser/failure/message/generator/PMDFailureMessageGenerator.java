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

import com.liferay.jenkins.results.parser.Build;

import org.dom4j.Element;

/**
 * @author Yi-Chen Tsai
 */
public class PMDFailureMessageGenerator extends BaseFailureMessageGenerator {

	@Override
	public Element getMessageElement(Build build) {
		String consoleText = build.getConsoleText();

		if (!consoleText.contains(_TOKEN_PMD_VIOLATIONS_WERE_FOUND)) {
			return null;
		}

		int start = consoleText.indexOf(_TOKEN_PMD_VIOLATIONS_WERE_FOUND);

		start = consoleText.lastIndexOf("\n", start);

		return getConsoleTextSnippetElementByStart(consoleText, start);
	}

	private static final String _TOKEN_PMD_VIOLATIONS_WERE_FOUND =
		"PMD violation(s) were found";

}