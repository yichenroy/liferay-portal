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

import java.io.File;

/**
 * @author Yi-Chen Tsai
 */
public class TestrayResultsParserUtil {

	public static void processTestrayResultFile(File file) {

	}

	public static void processTestrayResultFiles(File dir) {
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

	private static final long _BYTES_MAX_SIZE_TESTRAY_RESULT_FILE = 1024 * 200;

	private static final int _COUNT_MAX_TESTCASE = 100;

	private static final String _EXTENSION_TESTRAY_RESULT_FILE = ".xml";

}