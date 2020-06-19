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

import com.google.common.collect.Lists;

import java.io.File;

import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;

/**
 * @author Yi-Chen Tsai
 */
public class TestrayResultsParserUtil {

	public static void processTestrayResultFile(File file) throws Exception {
		Document document = Dom4JUtil.parse(
			JenkinsResultsParserUtil.read(file));

		Element rootElement = document.getRootElement();

		Element commonRootElement = _getCommonRootElement(rootElement);

		int partitionID = 0;

		List<Element> testcaseElements = rootElement.elements("testcase");

		for (List<Element> testcaseElementsPartition :
				Lists.partition(testcaseElements, _COUNT_MAX_TESTCASE)) {

			Element partitionRootElement = commonRootElement.createCopy();

			Dom4JUtil.addToElement(
				partitionRootElement, testcaseElementsPartition.toArray());

			Document partitionDocument = Dom4JUtil.parse(
				Dom4JUtil.format(partitionRootElement));

			JenkinsResultsParserUtil.write(
				_getPartitionFilePath(file, partitionID),
				partitionDocument.asXML());

			partitionID++;
		}
	}

	public static void processTestrayResultFiles(File dir) throws Exception {
		for (File file : dir.listFiles()) {
			if (file.isDirectory()) {
				continue;
			}

			String fileName = file.getName();

			if (!fileName.endsWith(_EXTENSION_TESTRAY_RESULT_FILE)) {
				continue;
			}

			if (file.length() > _BYTES_MAX_SIZE_TESTRAY_RESULT_FILE) {
				processTestrayResultFile(file);
			}
		}
	}

	private static Element _getCommonRootElement(Element rootElement) {
		Element commonRootElement = rootElement.createCopy();

		commonRootElement.clearContent();

		Element environmentsElement = rootElement.element("environments");
		Element propertiesElement = rootElement.element("properties");

		Dom4JUtil.addToElement(
			commonRootElement, environmentsElement.createCopy(),
			propertiesElement.createCopy());

		return commonRootElement;
	}

	private static String _getPartitionFilePath(File file, int partitionID) {
		String filePath = file.getAbsolutePath();

		String partitionPredicate = JenkinsResultsParserUtil.combine(
			"_partition_", String.valueOf(partitionID),
			_EXTENSION_TESTRAY_RESULT_FILE);

		return filePath.replace(
			_EXTENSION_TESTRAY_RESULT_FILE, partitionPredicate);
	}
	
	private static final long _BYTES_MAX_SIZE_TESTRAY_RESULT_FILE = 1024 * 200;

	private static final int _COUNT_MAX_TESTCASE = 100;

	private static final String _EXTENSION_TESTRAY_RESULT_FILE = ".xml";

}