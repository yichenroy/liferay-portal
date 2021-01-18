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

import com.google.common.collect.Lists;

import com.liferay.jenkins.results.parser.JenkinsMaster;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.Job;
import com.liferay.jenkins.results.parser.PortalGitWorkingDirectory;
import com.liferay.jenkins.results.parser.PortalTestClassJob;
import com.liferay.jenkins.results.parser.TestSuiteJob;

import java.io.File;

import java.nio.file.PathMatcher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 * @author Michael Hashimoto
 */
public abstract class BatchTestClassGroup extends BaseTestClassGroup {

	public int getAxisCount() {
		String axisCount = getFirstPropertyValue("test.batch.axis.count");

		if (axisCount != null) {
			return Integer.parseInt(axisCount);
		}

		int testClassCount = testClasses.size();

		if (testClassCount == 0) {
			return 0;
		}

		int axisMaxSize = getAxisMaxSize();

		if (axisMaxSize <= 0) {
			throw new RuntimeException(
				"'test.batch.axis.max.size' cannot be 0 or less");
		}

		return (int)Math.ceil((double)testClassCount / axisMaxSize);
	}

	public AxisTestClassGroup getAxisTestClassGroup(int axisId) {
		return axisTestClassGroups.get(axisId);
	}

	public List<AxisTestClassGroup> getAxisTestClassGroups() {
		return axisTestClassGroups;
	}

	public String getBatchJobName() {
		String topLevelJobName = portalTestClassJob.getJobName();

		Matcher jobNameMatcher = _jobNamePattern.matcher(topLevelJobName);

		if (jobNameMatcher.find()) {
			return JenkinsResultsParserUtil.combine(
				jobNameMatcher.group("jobBaseName"), "-batch",
				jobNameMatcher.group("jobVariant"));
		}

		return topLevelJobName + "-batch";
	}

	public String getBatchName() {
		return batchName;
	}

	@Override
	public Job getJob() {
		return portalTestClassJob;
	}

	public Properties getJobProperties() {
		return jobProperties;
	}

	public Integer getMaximumSlavesPerHost() {
		String maximumSlavesPerHost = getFirstPropertyValue(
			"test.batch.maximum.slaves.per.host");

		if (maximumSlavesPerHost == null) {
			return JenkinsMaster.getSlavesPerHostDefault();
		}

		return Integer.valueOf(maximumSlavesPerHost);
	}

	public Integer getMinimumSlaveRAM() {
		String minimumSlaveRAM = getFirstPropertyValue(
			"test.batch.minimum.slave.ram");

		if (minimumSlaveRAM == null) {
			return JenkinsMaster.getSlaveRAMMinimumDefault();
		}

		return Integer.valueOf(minimumSlaveRAM);
	}

	public PortalGitWorkingDirectory getPortalGitWorkingDirectory() {
		return portalGitWorkingDirectory;
	}

	public int getSegmentCount() {
		return _segmentTestClassGroups.size();
	}

	public SegmentTestClassGroup getSegmentTestClassGroup(int segmentId) {
		return _segmentTestClassGroups.get(segmentId);
	}

	public List<SegmentTestClassGroup> getSegmentTestClassGroups() {
		return _segmentTestClassGroups;
	}

	public static class BatchTestClass extends BaseTestClass {

		protected static BatchTestClass getInstance(
			String batchName,
			PortalGitWorkingDirectory portalGitWorkingDirectory) {

			File testClassFile = new File(
				portalGitWorkingDirectory.getWorkingDirectory(),
				"build-test-batch.xml");

			return new BatchTestClass(batchName, testClassFile);
		}

		protected BatchTestClass(String batchName, File testClassFile) {
			super(testClassFile);

			addTestClassMethod(batchName);
		}

	}

	protected BatchTestClassGroup(
		String batchName, PortalTestClassJob portalTestClassJob) {

		this.batchName = batchName;
		this.portalTestClassJob = portalTestClassJob;

		portalGitWorkingDirectory =
			portalTestClassJob.getPortalGitWorkingDirectory();

		String portalBranchName =
			portalGitWorkingDirectory.getUpstreamBranchName();

		if (portalBranchName.endsWith("-private")) {
			testPrivatePortalBranch = true;
		}

		if (portalTestClassJob instanceof TestSuiteJob) {
			TestSuiteJob testSuiteJob = (TestSuiteJob)portalTestClassJob;

			testSuiteName = testSuiteJob.getTestSuiteName();
		}
		else {
			testSuiteName = null;
		}

		jobProperties = portalTestClassJob.getJobProperties();

		String stableTestSuiteBatchNamesFirstPropertyValue =
			getFirstPropertyValue(
				"test.batch.names", batchName, NAME_STABLE_TEST_SUITE);

		if (stableTestSuiteBatchNamesFirstPropertyValue != null) {
			Collections.addAll(
				stableTestSuiteBatchNames,
				stableTestSuiteBatchNamesFirstPropertyValue.split("\\s*,\\s*"));
		}

		_setTestReleaseBundle();
		_setTestRelevantChanges();
		_setTestRelevantIntegrationUnitOnly();

		_setIncludeStableTestSuite();
	}

	protected int getAxisMaxSize() {
		String axisMaxSize = getFirstPropertyValue("test.batch.axis.max.size");

		if (axisMaxSize != null) {
			return Integer.parseInt(axisMaxSize);
		}

		return _AXES_SIZE_MAX_DEFAULT;
	}

	protected String getFirstMatchingPropertyName(
		String basePropertyName, Properties properties, String testSuiteName) {

		return getFirstMatchingPropertyName(
			basePropertyName, null, properties, testSuiteName);
	}

	protected String getFirstMatchingPropertyName(
		String basePropertyName, String batchName, Properties properties,
		String testSuiteName) {

		if (batchName == null) {
			batchName = this.batchName;
		}

		if (basePropertyName.contains("[") || basePropertyName.contains("]")) {
			throw new RuntimeException(
				"Invalid base property name " + basePropertyName);
		}

		Pattern pattern = Pattern.compile(
			JenkinsResultsParserUtil.combine(
				basePropertyName, "\\[(?<batchName>[^\\]]+)\\]",
				"(\\[(?<testSuiteName>[^\\]]+)\\])?"));

		for (String propertyName : properties.stringPropertyNames()) {
			if (!propertyName.startsWith(basePropertyName)) {
				continue;
			}

			Matcher matcher = pattern.matcher(propertyName);

			if (matcher.find()) {
				String batchNameRegex = matcher.group("batchName");

				batchNameRegex = batchNameRegex.replace("*", ".+");

				if (!batchName.matches(batchNameRegex)) {
					continue;
				}

				String targetTestSuiteName = matcher.group("testSuiteName");

				if (Objects.equals(testSuiteName, targetTestSuiteName)) {
					return propertyName;
				}
			}
		}

		return null;
	}

	protected String getFirstPropertyValue(String basePropertyName) {
		return getFirstPropertyValue(
			basePropertyName, batchName, testSuiteName);
	}

	protected String getFirstPropertyValue(
		String basePropertyName, String batchName) {

		return getFirstPropertyValue(
			basePropertyName, batchName, testSuiteName);
	}

	protected String getFirstPropertyValue(
		String basePropertyName, String batchName, String testSuiteName) {

		if (basePropertyName.contains("[") || basePropertyName.contains("]")) {
			throw new RuntimeException(
				"Invalid base property name " + basePropertyName);
		}

		List<String> propertyNames = new ArrayList<>();

		if (testSuiteName != null) {
			propertyNames.add(
				JenkinsResultsParserUtil.combine(
					basePropertyName, "[", batchName, "][", testSuiteName,
					"]"));

			propertyNames.add(
				getFirstMatchingPropertyName(
					basePropertyName, batchName, jobProperties, testSuiteName));

			propertyNames.add(
				JenkinsResultsParserUtil.combine(
					basePropertyName, "[", testSuiteName, "]"));
		}

		propertyNames.add(
			JenkinsResultsParserUtil.combine(
				basePropertyName, "[", batchName, "]"));

		propertyNames.add(
			getFirstMatchingPropertyName(
				basePropertyName, batchName, jobProperties, null));

		propertyNames.add(basePropertyName);

		for (String propertyName : propertyNames) {
			if (propertyName == null) {
				continue;
			}

			if (jobProperties.containsKey(propertyName)) {
				String propertyValue = JenkinsResultsParserUtil.getProperty(
					jobProperties, propertyName);

				if ((propertyValue != null) && !propertyValue.isEmpty()) {
					return propertyValue;
				}
			}
		}

		return null;
	}

	protected String getJobName() {
		return portalTestClassJob.getJobName();
	}

	protected List<PathMatcher> getPathMatchers(
		String relativeGlobs, File workingDirectory) {

		if ((relativeGlobs == null) || relativeGlobs.isEmpty()) {
			return Collections.emptyList();
		}

		return JenkinsResultsParserUtil.toPathMatchers(
			JenkinsResultsParserUtil.getCanonicalPath(workingDirectory) +
				File.separator,
			JenkinsResultsParserUtil.getGlobsFromProperty(relativeGlobs));
	}

	protected List<String> getRelevantIntegrationUnitBatchList() {
		List<String> relevantIntegrationUnitBatchList = new ArrayList<>();

		if (testSuiteName.equals("relevant")) {
			String relevantTestBatchNames = getFirstPropertyValue(
				"test.batch.names");

			if (relevantTestBatchNames != null) {
				for (String relevantTestBatchName :
						relevantTestBatchNames.split(",")) {

					if (relevantTestBatchName.startsWith("integration-") ||
						relevantTestBatchName.startsWith(
							"modules-integration") ||
						relevantTestBatchName.startsWith("modules-unit") ||
						relevantTestBatchName.startsWith("unit-")) {

						relevantIntegrationUnitBatchList.add(
							relevantTestBatchName);
					}
				}
			}
		}

		return relevantIntegrationUnitBatchList;
	}

	protected List<PathMatcher>
		getRelevantIntegrationUnitIncludePathMatchers() {

		List<PathMatcher> relevantIntegrationUnitIncludePathMatchers =
			new ArrayList<>();

		List<String> relevantIntegrationUnitBatchList =
			getRelevantIntegrationUnitBatchList();

		if (!relevantIntegrationUnitBatchList.isEmpty()) {
			for (String relevantIntegrationUnitBatch :
					relevantIntegrationUnitBatchList) {

				String integrationUnitIncludesPropertyValue =
					getFirstPropertyValue(
						"test.batch.class.names.includes",
						relevantIntegrationUnitBatch);

				if (integrationUnitIncludesPropertyValue != null) {
					relevantIntegrationUnitIncludePathMatchers.addAll(
						getPathMatchers(
							integrationUnitIncludesPropertyValue,
							portalGitWorkingDirectory.getWorkingDirectory()));
				}
			}
		}

		return relevantIntegrationUnitIncludePathMatchers;
	}

	protected List<File> getRequiredModuleDirs(List<File> moduleDirs) {
		return _getRequiredModuleDirs(moduleDirs, new ArrayList<>(moduleDirs));
	}

	protected int getSegmentMaxChildren() {
		String segmentMaxChildren = getFirstPropertyValue(
			"test.batch.segment.max.children");

		if ((segmentMaxChildren == null) ||
			!segmentMaxChildren.matches("\\d+")) {

			return _SEGMENT_MAX_CHILDREN_DEFAULT;
		}

		return Integer.valueOf(segmentMaxChildren);
	}

	protected String getTestSuiteName() {
		return testSuiteName;
	}

	protected boolean isIntegrationUnitTestFileModifiedOnly() {
		List<PathMatcher> relevantIntegrationUnitIncludePathMatchers =
			getRelevantIntegrationUnitIncludePathMatchers();

		List<File> modifiedFilesList =
			portalGitWorkingDirectory.getModifiedFilesList();

		if (relevantIntegrationUnitIncludePathMatchers.isEmpty() ||
			modifiedFilesList.isEmpty()) {

			return false;
		}

		for (File modifiedFile : modifiedFilesList) {
			if (!JenkinsResultsParserUtil.isFileIncluded(
					null, relevantIntegrationUnitIncludePathMatchers,
					modifiedFile)) {

				return false;
			}
		}

		return true;
	}

	protected boolean isStableTestSuiteBatch() {
		return stableTestSuiteBatchNames.contains(batchName);
	}

	protected void setAxisTestClassGroups() {
		int testClassCount = testClasses.size();

		if (testClassCount == 0) {
			return;
		}

		int axisCount = getAxisCount();

		int axisSize = (int)Math.ceil((double)testClassCount / axisCount);

		for (List<TestClass> axisTestClasses :
				Lists.partition(testClasses, axisSize)) {

			AxisTestClassGroup axisTestClassGroup =
				TestClassGroupFactory.newAxisTestClassGroup(this);

			for (TestClass axisTestClass : axisTestClasses) {
				axisTestClassGroup.addTestClass(axisTestClass);
			}

			axisTestClassGroups.add(axisTestClassGroup);
		}
	}

	protected void setSegmentTestClassGroups() {
		if (!_segmentTestClassGroups.isEmpty()) {
			return;
		}

		if (axisTestClassGroups.isEmpty()) {
			return;
		}

		Map<Integer, List<AxisTestClassGroup>> axisTestClassGroupsMap =
			new HashMap<>();

		for (AxisTestClassGroup axisTestClassGroup : axisTestClassGroups) {
			Integer minimumSlaveRAM = axisTestClassGroup.getMinimumSlaveRAM();

			List<AxisTestClassGroup> axisTestClassGroups =
				axisTestClassGroupsMap.get(minimumSlaveRAM);

			if (axisTestClassGroups == null) {
				axisTestClassGroups = new ArrayList<>();
			}

			axisTestClassGroups.add(axisTestClassGroup);

			axisTestClassGroupsMap.put(minimumSlaveRAM, axisTestClassGroups);
		}

		for (List<AxisTestClassGroup> axisTestClassGroupsMapValue :
				axisTestClassGroupsMap.values()) {

			for (List<AxisTestClassGroup> axisTestClassGroups :
					Lists.partition(
						axisTestClassGroupsMapValue, getSegmentMaxChildren())) {

				SegmentTestClassGroup segmentTestClassGroup =
					TestClassGroupFactory.newSegmentTestClassGroup(this);

				for (AxisTestClassGroup axisTestClassGroup :
						axisTestClassGroups) {

					segmentTestClassGroup.addAxisTestClassGroup(
						axisTestClassGroup);
				}

				_segmentTestClassGroups.add(segmentTestClassGroup);
			}
		}
	}

	protected static final String NAME_STABLE_TEST_SUITE = "stable";

	protected final List<AxisTestClassGroup> axisTestClassGroups =
		new ArrayList<>();
	protected final String batchName;
	protected final List<PathMatcher> excludesPathMatchers = new ArrayList<>();
	protected final List<PathMatcher> includesPathMatchers = new ArrayList<>();
	protected boolean includeStableTestSuite;
	protected final Properties jobProperties;
	protected final PortalGitWorkingDirectory portalGitWorkingDirectory;
	protected final PortalTestClassJob portalTestClassJob;
	protected List<String> stableTestSuiteBatchNames = new ArrayList<>();
	protected boolean testPrivatePortalBranch;
	protected boolean testReleaseBundle;
	protected boolean testRelevantChanges;
	protected boolean testRelevantIntegrationUnitOnly;
	protected final String testSuiteName;

	protected static final class CSVReport {

		public CSVReport(Row headerRow) {
			if (headerRow == null) {
				throw new IllegalArgumentException("headerRow is null");
			}

			_csvReportRows.add(headerRow);
		}

		public void addRow(Row csvReportRow) {
			Row headerRow = _csvReportRows.get(0);

			if (csvReportRow.size() != headerRow.size()) {
				throw new IllegalArgumentException(
					"Row length does not match headers length");
			}

			_csvReportRows.add(csvReportRow);
		}

		@Override
		public String toString() {
			StringBuilder sb = null;

			for (Row csvReportRow : _csvReportRows) {
				if (sb == null) {
					sb = new StringBuilder();
				}
				else {
					sb.append("\n");
				}

				sb.append(csvReportRow.toString());
			}

			return sb.toString();
		}

		protected static final class Row extends ArrayList<String> {

			public Row() {
			}

			public Row(String... strings) {
				for (String string : strings) {
					add(string);
				}
			}

			@Override
			public String toString() {
				return StringUtils.join(iterator(), ",");
			}

		}

		private List<Row> _csvReportRows = new ArrayList<>();

	}

	private List<File> _getRequiredModuleDirs(
		List<File> moduleDirs, List<File> requiredModuleDirs) {

		File modulesBaseDir = new File(
			portalGitWorkingDirectory.getWorkingDirectory(), "modules");

		for (File moduleDir : moduleDirs) {
			Properties moduleDirTestProperties =
				JenkinsResultsParserUtil.getProperties(
					new File(moduleDir, "test.properties"));

			String requiredModuleDirPaths = moduleDirTestProperties.getProperty(
				"modules.includes.required[" + testSuiteName + "]");

			if (requiredModuleDirPaths == null) {
				continue;
			}

			for (String requiredModuleDirPath :
					requiredModuleDirPaths.split(",")) {

				File requiredModuleDir = new File(
					modulesBaseDir, requiredModuleDirPath);

				if (!requiredModuleDir.exists()) {
					continue;
				}

				if (requiredModuleDirs.contains(requiredModuleDir)) {
					continue;
				}

				requiredModuleDirs.add(requiredModuleDir);
			}
		}

		return Lists.newArrayList(requiredModuleDirs);
	}

	private void _setIncludeStableTestSuite() {
		includeStableTestSuite = testRelevantChanges;
	}

	private void _setTestReleaseBundle() {
		String propertyValue = getFirstPropertyValue("test.release.bundle");

		if (propertyValue != null) {
			testReleaseBundle = Boolean.parseBoolean(propertyValue);

			return;
		}

		testReleaseBundle = _ENABLE_TEST_RELEASE_BUNDLE_DEFAULT;
	}

	private void _setTestRelevantChanges() {
		String propertyValue = getFirstPropertyValue("test.relevant.changes");

		if (propertyValue != null) {
			testRelevantChanges = Boolean.parseBoolean(propertyValue);

			return;
		}

		testRelevantChanges = _ENABLE_TEST_RELEVANT_CHANGES_DEFAULT;
	}

	private void _setTestRelevantIntegrationUnitOnly() {
		if (testRelevantChanges && isIntegrationUnitTestFileModifiedOnly()) {
			testRelevantIntegrationUnitOnly = true;

			return;
		}

		testRelevantIntegrationUnitOnly = false;
	}

	private static final int _AXES_SIZE_MAX_DEFAULT = 5000;

	private static final boolean _ENABLE_TEST_RELEASE_BUNDLE_DEFAULT = false;

	private static final boolean _ENABLE_TEST_RELEVANT_CHANGES_DEFAULT = false;

	private static final int _SEGMENT_MAX_CHILDREN_DEFAULT = 25;

	private static final Pattern _jobNamePattern = Pattern.compile(
		"(?<jobBaseName>.*)(?<jobVariant>\\([^\\)]+\\))");

	private final List<SegmentTestClassGroup> _segmentTestClassGroups =
		new ArrayList<>();

}