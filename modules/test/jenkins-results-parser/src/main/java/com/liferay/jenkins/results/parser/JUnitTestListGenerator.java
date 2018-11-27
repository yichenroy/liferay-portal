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

import com.liferay.jenkins.results.parser.test.clazz.group.FunctionalBatchTestClassGroup;
import com.liferay.jenkins.results.parser.test.clazz.group.JUnitBatchTestClassGroup;
import com.liferay.jenkins.results.parser.test.clazz.group.ModulesCompileBatchTestClassGroup;
import com.liferay.jenkins.results.parser.test.clazz.group.ModulesJUnitBatchTestClassGroup;
import com.liferay.jenkins.results.parser.test.clazz.group.TestClassGroup;
import com.liferay.jenkins.results.parser.test.clazz.group.TestClassGroupFactory;

import java.io.File;

import java.nio.file.Path;
import java.nio.file.PathMatcher;

import java.util.List;

/**
 * @author Yi-Chen Tsai
 */
public class JUnitTestListGenerator {

	public static void comparePaths() {
		File file = new File(
			"/opt/dev/projects/github/liferay-portal/modules/apps/adaptive-media");

		Path path = file.toPath();

		List<PathMatcher> pathMatchers =
			JenkinsResultsParserUtil.toPathMatchers(
				"/opt/dev/projects/github/liferay-portal/modules/",
				"apps/adaptive-media/**/*.*");

		for (PathMatcher pathMatcher : pathMatchers) {
			if (pathMatcher.matches(path)) {
				System.out.println(path + " MATCHES!!");
			}
			else {
				System.out.println(path + " DOES NOT MATCH!!");
			}
		}
	}

	public static void main(String[] args) {
		System.out.println("######################################");
		System.out.println("GET ALL JUNIT TEST CLASSES");
		System.out.println("######################################");

//		comparePaths();

		printModulesCompile();
//
//		printModulesIntegration();
//
//		printModulesUnit();
//
//		printUnit();
//
//		printIntegration();
//
//		printFunctional();
	}

	public static void printFunctional() {
		Job portalGitRepositoryJob = JobFactory.newJob(
			"test-portal-acceptance-pullrequest(master)", "relevant", "master");

		FunctionalBatchTestClassGroup functionalBatchTestClassGroup =
			(FunctionalBatchTestClassGroup)TestClassGroupFactory.
				newBatchTestClassGroup(
					"functional-tomcat90-mysql57-jdk8", portalGitRepositoryJob);

		System.out.println(
			"################### FUNCTIONAL #####################");

		System.out.println("##############################################");
	}

	public static void printIntegration() {
		Job portalGitRepositoryJob = JobFactory.newJob(
			"test-portal-acceptance-pullrequest(master)", "relevant", "master");

		JUnitBatchTestClassGroup jUnitBatchTestClassGroup =
			(JUnitBatchTestClassGroup)TestClassGroupFactory.
				newBatchTestClassGroup(
					"integration-mysql57-jdk8", portalGitRepositoryJob);

		List<TestClassGroup.TestClass> testClasses =
			jUnitBatchTestClassGroup.getTestClasses();

		System.out.println("############### INTEGRATION ####################");

		for (TestClassGroup.TestClass testClass : testClasses) {
			System.out.println(testClass.getTestClassFile().getAbsolutePath());
		}

		System.out.println("##############################################");
	}

	public static void printModulesCompile() {
		Job portalGitRepositoryJob = JobFactory.newJob(
			"test-portal-acceptance-pullrequest(master)", "default", "master");

		ModulesCompileBatchTestClassGroup modulesCompileBatchTestClassGroup =
			(ModulesCompileBatchTestClassGroup)TestClassGroupFactory.
				newBatchTestClassGroup(
					"modules-compile-jdk8", portalGitRepositoryJob);

		List<TestClassGroup.TestClass> testClasses =
			modulesCompileBatchTestClassGroup.getTestClasses();

		System.out.println("############ MODULES COMPILE ####################");

		for (TestClassGroup.TestClass testClass : testClasses) {
			System.out.println(testClass.getTestClassFile().getName());
		}

		System.out.println("##############################################");
	}

	public static void printModulesIntegration() {
		Job portalGitRepositoryJob = JobFactory.newJob(
			"test-portal-acceptance-pullrequest(master)", "relevant", "master");

		ModulesJUnitBatchTestClassGroup modulesJUnitBatchTestClassGroup =
			(ModulesJUnitBatchTestClassGroup)TestClassGroupFactory.
				newBatchTestClassGroup(
					"modules-integration-mysql57-jdk8", portalGitRepositoryJob);

		List<TestClassGroup.TestClass> testClasses =
			modulesJUnitBatchTestClassGroup.getTestClasses();

		System.out.println("############ MODULES INTEGRATION ################");

		for (TestClassGroup.TestClass testClass : testClasses) {
			System.out.println(testClass.getTestClassFile().getAbsolutePath());
		}

		System.out.println("##############################################");
	}

	public static void printModulesUnit() {
		Job portalGitRepositoryJob = JobFactory.newJob(
			"test-portal-acceptance-pullrequest(master)", "relevant", "master");

		ModulesJUnitBatchTestClassGroup modulesJUnitBatchTestClassGroup =
			(ModulesJUnitBatchTestClassGroup)TestClassGroupFactory.
				newBatchTestClassGroup(
					"modules-unit-jdk8", portalGitRepositoryJob);

		List<TestClassGroup.TestClass> testClasses =
			modulesJUnitBatchTestClassGroup.getTestClasses();

		System.out.println("############ MODULES UNIT ################");

		for (TestClassGroup.TestClass testClass : testClasses) {
			System.out.println(testClass.getTestClassFile().getAbsolutePath());
		}

		System.out.println("##############################################");
	}

	public static void printUnit() {
		Job portalGitRepositoryJob = JobFactory.newJob(
			"test-portal-acceptance-pullrequest(master)", "relevant", "master");

		JUnitBatchTestClassGroup jUnitBatchTestClassGroup =
			(JUnitBatchTestClassGroup)TestClassGroupFactory.
				newBatchTestClassGroup("unit-jdk8", portalGitRepositoryJob);

		List<TestClassGroup.TestClass> testClasses =
			jUnitBatchTestClassGroup.getTestClasses();

		System.out.println("################### UNIT #####################");

		for (TestClassGroup.TestClass testClass : testClasses) {
			System.out.println(testClass.getTestClassFile().getAbsolutePath());
		}

		System.out.println("##############################################");
	}

}