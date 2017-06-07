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

package com.liferay.jenkins.results.parser;

import java.util.Hashtable;

import org.dom4j.Element;

/**
 * @author Peter Yoo
 */
public class SourceFormatFailureMessageGenerator
	extends BaseFailureMessageGenerator {

	@Override
	public String getMessage(
		String buildURL, String consoleOutput, Hashtable<?, ?> properties) {

		if (!consoleOutput.contains(_SOURCE_FORMAT_STRING)) {
			return null;
		}

		int start = consoleOutput.lastIndexOf(
			_UTIL_SYSTEM_EXT_PROPERTIES_STRING);

		start += _UTIL_SYSTEM_EXT_PROPERTIES_STRING.length();

		consoleOutput = consoleOutput.substring(start);

		int end = consoleOutput.indexOf(_SOURCE_FORMAT_STRING);

		return getConsoleOutputSnippet(consoleOutput, true, end);
	}

	@Override
	public Element getMessageElement(Build build) {
		String consoleText = build.getConsoleText();

		if (!consoleText.contains(_SOURCE_FORMAT_STRING)) {
			return null;
		}

		int start = consoleText.lastIndexOf(_UTIL_SYSTEM_EXT_PROPERTIES_STRING);

		start = consoleText.indexOf("\n", start);

		consoleText = consoleText.substring(start);

		int end = consoleText.indexOf(_SOURCE_FORMAT_STRING);

		end = consoleText.lastIndexOf("\n", end);

		return getConsoleOutputSnippetElement(consoleText, true, end);
	}

	private static final String _SOURCE_FORMAT_STRING =
		"at com.liferay.source.formatter";

	private static final String _UTIL_SYSTEM_EXT_PROPERTIES_STRING =
		"util-java/test-classes/unit/system-ext.properties";

}