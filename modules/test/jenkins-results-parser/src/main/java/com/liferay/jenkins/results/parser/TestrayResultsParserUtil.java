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
import java.io.FileFilter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

/**
 * @author Yi-Chen Tsai
 */
public class TestrayResultsParserUtil {

	public static void processTestrayResultFile(File file)
		throws DocumentException, IOException {

		Document document = Dom4JUtil.parse(
			JenkinsResultsParserUtil.read(file));

		Element rootElement = document.getRootElement();

		List<Element> summaryElements = rootElement.elements("summary");

		Dom4JUtil.detach(summaryElements.toArray());

		List<Element> testcaseElements = rootElement.elements("testcase");

		Dom4JUtil.detach(testcaseElements.toArray());

		rootElement.setText(rootElement.getTextTrim());

		List<List<Element>> testcaseElementsPartitions = Lists.partition(
			testcaseElements, _COUNT_MAX_TESTCASE);

		List<File> partitionFiles = new ArrayList<>(
			testcaseElementsPartitions.size());

		try {
			for (List<Element> testcaseElementsPartition :
					testcaseElementsPartitions) {

				Document partitionDocument = (Document)document.clone();

				for (Element testcaseElement : testcaseElementsPartition) {
					Dom4JUtil.truncateElement(
						testcaseElement, _LENGTH_MAX_TESTCASE_FIELD);
				}

				Element partitionRootElement =
					partitionDocument.getRootElement();

				Dom4JUtil.addToElement(
					partitionRootElement, testcaseElementsPartition.toArray());

				Dom4JUtil.addToElement(
					partitionRootElement,
					_getSummaryElement(testcaseElementsPartition));

				File partitionFile = new File(
					_getPartitionFilePath(
						file,
						testcaseElementsPartitions.indexOf(
							testcaseElementsPartition)));

				JenkinsResultsParserUtil.write(
					partitionFile, partitionDocument.asXML());

				partitionFiles.add(partitionFile);
			}

			file.delete();
		}
		catch (Exception exception) {
			for (File partitionFile : partitionFiles) {
				partitionFile.delete();
			}

			if (exception instanceof IOException) {
				throw (IOException)exception;
			}

			throw new RuntimeException(exception);
		}

		System.out.println(
			JenkinsResultsParserUtil.combine(
				"The Testray result file '", file.getName(),
				"' has been split into ",
				String.valueOf(testcaseElementsPartitions.size()),
				" partitions."));
	}

	public static void processTestrayResultFiles(File dir) {
		FileFilter fileFilter = new FileFilter() {

			@Override
			public boolean accept(File file) {
				String name = file.getName();

				if (!file.isDirectory() &&
					name.endsWith(_EXTENSION_TESTRAY_RESULT_FILE) &&
					(file.length() > _BYTES_MAX_SIZE_TESTRAY_RESULT_FILE)) {

					return true;
				}

				return false;
			}

		};

		for (File file : dir.listFiles(fileFilter)) {
			try {
				processTestrayResultFile(file);
			}
			catch (Exception exception) {
				System.out.println(
					JenkinsResultsParserUtil.combine(
						"Unable to process large Testray result file '",
						file.getName(), "'."));

				exception.printStackTrace();
			}
		}
	}

	private static String _getPartitionFilePath(File file, int partitionID) {
		String filePath = file.getAbsolutePath();

		String partitionPredicate = JenkinsResultsParserUtil.combine(
			"_partition_", String.valueOf(partitionID),
			_EXTENSION_TESTRAY_RESULT_FILE);

		return filePath.replace(
			_EXTENSION_TESTRAY_RESULT_FILE, partitionPredicate);
	}

	private static Element _getSummaryElement(List<Element> testcaseElements) {
		Element summaryElement = Dom4JUtil.getNewElement("summary");

		int numPassed = 0;

		for (Element testcaseElement : testcaseElements) {
			String testrayTestcaseStatus = _getTestrayTestcaseStatus(
				testcaseElement);

			if (testrayTestcaseStatus.equals("passed")) {
				numPassed++;
			}
		}

		Element passedPropertyElement = Dom4JUtil.getNewElement("property");

		passedPropertyElement.addAttribute("name", "passed");
		passedPropertyElement.addAttribute("value", String.valueOf(numPassed));

		Element failedPropertyElement = Dom4JUtil.getNewElement("property");

		failedPropertyElement.addAttribute("name", "failed");
		failedPropertyElement.addAttribute(
			"value", String.valueOf(testcaseElements.size() - numPassed));

		Dom4JUtil.addToElement(
			summaryElement, passedPropertyElement, failedPropertyElement);

		return summaryElement;
	}

	private static String _getTestrayTestcaseStatus(Element testcaseElement) {
		String testrayTestcaseStatus = "";

		Element propertiesElement = testcaseElement.element("properties");

		List<Element> propertyElements = propertiesElement.elements("property");

		for (Element propertyElement : propertyElements) {
			if (_NAME_TESTRAY_TESTCASE_STATUS_PROPERTY.equals(
					propertyElement.attributeValue("name", ""))) {

				testrayTestcaseStatus = propertyElement.attributeValue(
					"value", "");
			}
		}

		return testrayTestcaseStatus;
	}

	private static final long _BYTES_MAX_SIZE_TESTRAY_RESULT_FILE = 1024 * 200;

	private static final int _COUNT_MAX_TESTCASE = 100;

	private static final String _EXTENSION_TESTRAY_RESULT_FILE = ".xml";

	private static final int _LENGTH_MAX_TESTCASE_FIELD = 2500;

	private static final String _NAME_TESTRAY_TESTCASE_STATUS_PROPERTY =
		"testray.testcase.status";

}