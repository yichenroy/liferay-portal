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

import com.liferay.jenkins.results.parser.test.clazz.group.BaseTestClassGroup;
import com.liferay.jenkins.results.parser.test.clazz.group.FunctionalBatchTestClassGroup;
import com.liferay.jenkins.results.parser.test.clazz.group.JUnitBatchTestClassGroup;
import com.liferay.jenkins.results.parser.test.clazz.group.ModulesSemVerBatchTestClassGroup;
import com.liferay.jenkins.results.parser.test.clazz.group.NPMTestBatchTestClassGroup;
import com.liferay.jenkins.results.parser.test.clazz.group.ServiceBuilderBatchTestClassGroup;
import com.liferay.jenkins.results.parser.test.clazz.group.TestClassGroup;
import com.liferay.jenkins.results.parser.test.clazz.group.TestClassGroupFactory;

import java.io.File;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Yi-Chen Tsai
 */
public class JUnitTestListGenerator {

	public static Map<File, NPMTestBatchTestClassGroup.NPMTestBatchTestClass>
		getAllJSTestList() {

		Job currentGitRepositoryJob = JobFactory.newJob(
			"test-portal-acceptance-upstream(master)");

		NPMTestBatchTestClassGroup npmBatchTestClassGroup =
			(NPMTestBatchTestClassGroup)
				TestClassGroupFactory.newBatchTestClassGroup(
					"portal-frontend-js-jdk8", currentGitRepositoryJob);

		Map<File, NPMTestBatchTestClassGroup.NPMTestBatchTestClass>
			allJSTestList = npmBatchTestClassGroup.getNPMTestBatchTestClasses();

		try {
			npmBatchTestClassGroup.writeTestCSVReportFile();
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return allJSTestList;
	}

	public static Map<File, JUnitBatchTestClassGroup.JunitBatchTestClass>
		getAllJUnitTestList() {

		Job currentGitRepositoryJob = JobFactory.newJob(_JOB_NAME, "relevant");

		JUnitBatchTestClassGroup jUnitBatchTestClassGroup =
			(JUnitBatchTestClassGroup)
				TestClassGroupFactory.newBatchTestClassGroup(
					"modules-integration-project-templates-jdk8",
					currentGitRepositoryJob);

		Map<File, JUnitBatchTestClassGroup.JunitBatchTestClass>
			allJunitTestClasses =
				jUnitBatchTestClassGroup.getJunitTestClasses();

		System.out.println(
			"allJunitTestClasses size: " + allJunitTestClasses.size());

		//		try {
		//			jUnitBatchTestClassGroup.writeTestCSVReportFile();
		//		}
		//		catch (Exception e) {
		//			e.printStackTrace();
		//		}

		return allJunitTestClasses;
	}

	public static String getFunctionalQuery() {
		Job currentGitRepositoryJob = JobFactory.newJob(_JOB_NAME, "relevant");

		FunctionalBatchTestClassGroup functionalBatchTestClassGroup =
			(FunctionalBatchTestClassGroup)
				TestClassGroupFactory.newBatchTestClassGroup(
					"functional-tomcat90-mysql57-jdk8",
					currentGitRepositoryJob);

		String query =
			functionalBatchTestClassGroup.getTestBatchRunPropertyQuery();

		System.out.println("Query: " + query);

		return query;
	}

	public static List getServiceBuilderModulesList() {
		Job currentGitRepositoryJob = JobFactory.newJob(_JOB_NAME, "default");

		ServiceBuilderBatchTestClassGroup serviceBuilderBatchTestClassGroup =
			(ServiceBuilderBatchTestClassGroup)
				TestClassGroupFactory.newBatchTestClassGroup(
					"service-builder-jdk8", currentGitRepositoryJob);

		System.out.println(
			"BUILD TYPE: " + serviceBuilderBatchTestClassGroup.getBuildType());

		List<TestClassGroup.TestClass> testClasses =
			serviceBuilderBatchTestClassGroup.getTestClasses();

		for (TestClassGroup.TestClass testClass : testClasses) {
			System.out.println(
				"testClass: " +
					testClass.getTestClassFile(
					).getAbsolutePath());
		}

		return new ArrayList();
	}

	public static List getModulesSemanticVersioningList() {
		Job currentGitRepositoryJob = JobFactory.newJob(_JOB_NAME, "acceptance-dxp");

		ModulesSemVerBatchTestClassGroup modulesSemVerBatchTestClassGroup =
			(ModulesSemVerBatchTestClassGroup)
				TestClassGroupFactory.newBatchTestClassGroup(
					"modules-semantic-versioning-jdk8", currentGitRepositoryJob);

		List<TestClassGroup.TestClass> testClasses =
			modulesSemVerBatchTestClassGroup.getTestClasses();

		for (TestClassGroup.TestClass testClass : testClasses) {
			System.out.println(
				"testClass: " +
					testClass.getTestClassFile(
					).getAbsolutePath());
		}

		return new ArrayList();
	}

	public static void main(String[] args) throws Exception {
		//		System.out.println("######################################");
		//		System.out.println("GET ALL FUNCTIONAL QUERY");
		//		System.out.println("######################################");
		//
		//		getFunctionalQuery();

		//		System.out.println("######################################");
		//		System.out.println("GET ALL MODULES JUNIT TEST CLASSES");
		//		System.out.println("######################################");
		//
		//		printMap1(getAllJUnitTestList());

		//		System.out.println("######################################");
		//		System.out.println("GET ALL JS TEST CLASSES");
		//		System.out.println("######################################");
		//
		//		printMap(getAllJSTestList());

		//		System.out.println("######################################");
		//		System.out.println("GET ALL SERVICE BUILDER MODULES");
		//		System.out.println("######################################");
		//
		//		getServiceBuilderModulesList();

		System.out.println("######################################");
		System.out.println("GET ALL SEMANTIC VERSIONING MODULES");
		System.out.println("######################################");

		getModulesSemanticVersioningList();
	}

	protected static void printMap(
		Map<File, NPMTestBatchTestClassGroup.NPMTestBatchTestClass> map) {

		for (Map.Entry<File, NPMTestBatchTestClassGroup.NPMTestBatchTestClass>
				entry : map.entrySet()) {

			NPMTestBatchTestClassGroup.NPMTestBatchTestClass
				npmTestBatchTestClass = entry.getValue();

			List<BaseTestClassGroup.BaseTestClass.TestClassMethod>
				jsTestMethods = npmTestBatchTestClass.getJSTestClassMethods();

			for (BaseTestClassGroup.BaseTestClass.TestClassMethod jsTestMethod :
					jsTestMethods) {

				if (jsTestMethod.isIgnored()) {
					System.out.println(jsTestMethod.getName());
				}
			}
		}
	}

	protected static void printMap1(
		Map<File, JUnitBatchTestClassGroup.JunitBatchTestClass> map) {

		for (Map.Entry<File, JUnitBatchTestClassGroup.JunitBatchTestClass>
				entry : map.entrySet()) {

			JUnitBatchTestClassGroup.JunitBatchTestClass junitBatchTestClass =
				entry.getValue();

			List<BaseTestClassGroup.BaseTestClass.TestClassMethod> testMethods =
				junitBatchTestClass.getTestClassMethods();

			for (BaseTestClassGroup.BaseTestClass.TestClassMethod testMethod :
					testMethods) {

				System.out.println(
					junitBatchTestClass.getTestClassFile(
					).getAbsolutePath() + "::" + testMethod.getName());
			}
		}
	}

	private static final String _JOB_NAME =
		"test-portal-acceptance-upstream-dxp(master)";

}