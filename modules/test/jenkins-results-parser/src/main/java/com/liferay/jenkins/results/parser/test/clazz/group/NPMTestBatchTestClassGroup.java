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

package com.liferay.jenkins.results.parser.test.clazz.group;

import com.liferay.jenkins.results.parser.PortalTestClassJob;

import java.io.File;
import java.io.IOException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author Michael Hashimoto
 */
public class NPMTestBatchTestClassGroup extends BatchTestClassGroup {

	@Override
	public AxisTestClassGroup getAxisTestClassGroup(int axisId) {
		if (axisId != 0) {
			throw new IllegalArgumentException("axisId is not 0");
		}

		AxisTestClassGroup axisTestClassGroup = axisTestClassGroups.get(axisId);

		if (axisTestClassGroup != null) {
			return axisTestClassGroups.get(axisId);
		}

		return new AxisTestClassGroup(this, axisId);
	}

	public Map<File, NPMTestBatchTestClass> getNPMTestBatchTestClasses() {
		return NPMTestBatchTestClass.getNPMTestBatchTestClasses();
	}

	public static class NPMTestBatchTestClass extends BaseTestClass {

		protected static NPMTestBatchTestClass getInstance(
			String batchName, File moduleDir) {

			return new NPMTestBatchTestClass(batchName, moduleDir);
		}

		protected static Map<File, NPMTestBatchTestClass>
			getNPMTestBatchTestClasses() {

			return _npmTestBatchTestClasses;
		}

		protected NPMTestBatchTestClass(String batchName, File file) {
			super(file);

			addTestMethod(batchName);
		}

		private static final Pattern _itPattern = Pattern.compile(
			"\\s+(?<xit>x)?it\\s*\\(\\s*\\'(?<description>[\\s\\S]*?)\\'");
		private static final Map<File, NPMTestBatchTestClass>
			_npmTestBatchTestClasses = new HashMap<>();

	}

	protected NPMTestBatchTestClassGroup(
		String batchName, PortalTestClassJob portalTestClassJob) {

		super(batchName, portalTestClassJob);

		List<File> moduleDirs;

		try {
			if (testRelevantChanges) {
				moduleDirs =
					portalGitWorkingDirectory.
						getModifiedNPMTestModuleDirsList();
			}
			else {
				moduleDirs =
					portalGitWorkingDirectory.getNPMTestModuleDirsList();
			}
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}

		if (moduleDirs.isEmpty()) {
			return;
		}

		AxisTestClassGroup axisTestClassGroup = new AxisTestClassGroup(this, 0);

		for (File moduleDir : moduleDirs) {
			NPMTestBatchTestClass npmTestBatchTestClass =
				NPMTestBatchTestClass.getInstance(batchName, moduleDir);

			testClasses.add(npmTestBatchTestClass);

			axisTestClassGroup.addTestClass(npmTestBatchTestClass);
		}

		axisTestClassGroups.put(0, axisTestClassGroup);
	}

}