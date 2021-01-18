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

import com.liferay.jenkins.results.parser.test.clazz.group.FunctionalBatchTestClassGroup.FunctionalTestClass;

import java.io.File;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author Michael Hashimoto
 */
public class FunctionalAxisTestClassGroup extends AxisTestClassGroup {

	public List<FunctionalTestClass> getFunctionalTestClasses() {
		List<FunctionalTestClass> functionalTestClasses = new ArrayList<>();

		for (TestClass testClass : getTestClasses()) {
			if (!(testClass instanceof FunctionalTestClass)) {
				continue;
			}

			functionalTestClasses.add((FunctionalTestClass)testClass);
		}

		return functionalTestClasses;
	}

	@Override
	public Integer getMinimumSlaveRAM() {
		Properties poshiProperties = getPoshiProperties();

		String minimumSlaveRAM = poshiProperties.getProperty(
			"minimum.slave.ram");

		if ((minimumSlaveRAM != null) && minimumSlaveRAM.matches("\\d+")) {
			return Integer.valueOf(minimumSlaveRAM);
		}

		return super.getMinimumSlaveRAM();
	}

	public Properties getPoshiProperties() {
		List<FunctionalTestClass> functionalTestClasses =
			getFunctionalTestClasses();

		FunctionalTestClass functionalTestClass = functionalTestClasses.get(0);

		return functionalTestClass.getPoshiProperties();
	}

	public File getTestBaseDir() {
		return _testBaseDir;
	}

	public List<String> getTestClassMethodNames() {
		List<String> testClassMethodNames = new ArrayList<>();

		for (FunctionalTestClass functionalTestClass :
				getFunctionalTestClasses()) {

			testClassMethodNames.add(
				functionalTestClass.getTestClassMethodName());
		}

		return testClassMethodNames;
	}

	protected FunctionalAxisTestClassGroup(
		FunctionalBatchTestClassGroup functionalBatchTestClassGroup,
		File testBaseDir) {

		super(functionalBatchTestClassGroup);

		_testBaseDir = testBaseDir;
	}

	private final File _testBaseDir;

}