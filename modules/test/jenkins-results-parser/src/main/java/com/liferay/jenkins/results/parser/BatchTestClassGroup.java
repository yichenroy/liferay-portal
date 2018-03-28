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
import java.io.IOException;

import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Michael Hashimoto
 */
public class Batch {

	public BatchAxis getBatchAxis(int batchAxisId) {
		return _batchAxes.get(batchAxisId);
	}

	public int getBatchAxisCount() {
		return _batchAxes.size();
	}

	public String getBatchName() {
		return _batchName;
	}

	public GitWorkingDirectory getGitWorkingDirectory() {
		return _gitWorkingDirectory;
	}

	public Properties getPortalTestProperties() {
		return _portalTestProperties;
	}

	public static class BatchAxis {

		public void addTestClassFile(File testClassFile) {
			_testClassFiles.add(testClassFile);
		}

		public int getId() {
			return _id;
		}

		public List<File> getTestClassFiles() {
			return _testClassFiles;
		}

		private BatchAxis(int id) {
			_id = id;

			_testClassFiles = new ArrayList<>();
		}

		private final int _id;
		private final List<File> _testClassFiles;

	}

	protected Batch(String batchName, GitWorkingDirectory gitWorkingDirectory) {
		this(batchName, gitWorkingDirectory, null);
	}

	protected Batch(
		String batchName, GitWorkingDirectory gitWorkingDirectory,
		String testSuiteName) {

		_batchName = batchName;
		_gitWorkingDirectory = gitWorkingDirectory;
		_testSuiteName = testSuiteName;

		_portalTestProperties = JenkinsResultsParserUtil.getProperties(
			new File(
				_gitWorkingDirectory.getWorkingDirectory(), "test.properties"));

		_setBatchRelevantChanges();

		_setTestClassNamesExcludesRelativeGlobs();
		_setTestClassNamesIncludesRelativeGlobs();

		_setBatchAxes();
	}

	private int _getBatchAxisMaxSize() {
		String batchAxisMaxSize = _getBatchAxisMaxSizePropertyValue();

		if (batchAxisMaxSize != null) {
			return Integer.parseInt(batchAxisMaxSize);
		}

		return _DEFAULT_BATCH_AXIS_MAX_SIZE;
	}

	private String _getBatchAxisMaxSizePropertyValue() {
		List<String> propertyNames = new ArrayList<>();

		if (_testSuiteName != null) {
			propertyNames.add(
				JenkinsResultsParserUtil.combine(
					"test.batch.axis.max.size[", _batchName, "][",
					_testSuiteName, "]"));

			propertyNames.add(
				_getWildcardPropertyName(
					_batchName, _portalTestProperties,
					"test.batch.axis.max.size", _testSuiteName));

			propertyNames.add(
				JenkinsResultsParserUtil.combine(
					"test.batch.axis.max.size[", _testSuiteName, "]"));
		}

		propertyNames.add(
			JenkinsResultsParserUtil.combine(
				"test.batch.axis.max.size[", _batchName, "]"));

		propertyNames.add(
			_getWildcardPropertyName(
				_batchName, _portalTestProperties, "test.batch.axis.max.size"));

		propertyNames.add("test.batch.axis.max.size");

		return _getFirstPropertyValue(_portalTestProperties, propertyNames);
	}

	private String _getFirstPropertyValue(
		Properties properties, List<String> propertyNames) {

		for (String propertyName : propertyNames) {
			if (propertyName == null) {
				continue;
			}

			if (properties.containsKey(propertyName)) {
				String propertyValue = properties.getProperty(propertyName);

				if ((propertyValue != null) && !propertyValue.isEmpty()) {
					return propertyValue;
				}
			}
		}

		return null;
	}

	private List<String> _getRelevantTestClassNamesRelativeGlobs(
		List<String> testClassNamesRelativeGlobs) {

		List<String> relevantTestClassNameRelativeGlobs = new ArrayList<>();

		PortalGitWorkingDirectory portalGitWorkingDirectory =
			(PortalGitWorkingDirectory)_gitWorkingDirectory;

		List<File> moduleGroupDirs = null;

		try {
			moduleGroupDirs =
				portalGitWorkingDirectory.getCurrentBranchModuleGroupDirs();
		}
		catch (IOException ioe) {
			File workingDirectory = _gitWorkingDirectory.getWorkingDirectory();

			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to get relevant module group directories in ",
					workingDirectory.getPath()),
				ioe);
		}

		for (File moduleGroupDir : moduleGroupDirs) {
			for (String testClassNamesRelativeGlob :
					testClassNamesRelativeGlobs) {

				relevantTestClassNameRelativeGlobs.add(
					JenkinsResultsParserUtil.combine(
						moduleGroupDir.getPath(), "/",
						testClassNamesRelativeGlob));

				if (testClassNamesRelativeGlob.startsWith("**/")) {
					relevantTestClassNameRelativeGlobs.add(
						JenkinsResultsParserUtil.combine(
							moduleGroupDir.getPath(), "/",
							testClassNamesRelativeGlob.substring(3)));
				}
			}
		}

		return relevantTestClassNameRelativeGlobs;
	}

	private Set<String> _getTestClassFileNames() {
		File workingDirectory = _gitWorkingDirectory.getWorkingDirectory();

		final Set<String> testClassFileNames = new HashSet<>();

		try {
			Files.walkFileTree(
				workingDirectory.toPath(),
				new SimpleFileVisitor<Path>() {

					@Override
					public FileVisitResult preVisitDirectory(
							Path filePath, BasicFileAttributes attrs)
						throws IOException {

						if (_pathExcluded(filePath)) {
							return FileVisitResult.SKIP_SUBTREE;
						}

						return FileVisitResult.CONTINUE;
					}

					@Override
					public FileVisitResult visitFile(
							Path filePath, BasicFileAttributes attrs)
						throws IOException {

						if (_pathIncluded(filePath) &&
							!_pathExcluded(filePath)) {

							testClassFileNames.add(
								_getPackagePath(filePath.toString()));
						}

						return FileVisitResult.CONTINUE;
					}

					private String _getPackagePath(String filePath) {
						Matcher matcher = _packagePathPattern.matcher(filePath);

						if (matcher.find()) {
							String packagePath = matcher.group("packagePath");

							packagePath = packagePath.replace(
								".java", ".class");

							return packagePath;
						}

						return filePath.replace(".java", ".class");
					}

					private boolean _pathExcluded(Path path) {
						return _pathMatches(
							path, _testClassNamesExcludesPathMatchers);
					}

					private boolean _pathIncluded(Path path) {
						return _pathMatches(
							path, _testClassNamesIncludesPathMatchers);
					}

					private boolean _pathMatches(
						Path path, List<PathMatcher> pathMatchers) {

						for (PathMatcher pathMatcher : pathMatchers) {
							if (pathMatcher.matches(path)) {
								return true;
							}
						}

						return false;
					}

				});
		}
		catch (IOException ioe) {
			throw new RuntimeException(
				"Unable to search for test file names in " +
					workingDirectory.getPath(),
				ioe);
		}

		return testClassFileNames;
	}

	private String _getTestClassNamesExcludesPropertyValue() {
		List<String> propertyNames = new ArrayList<>();

		if (_testSuiteName != null) {
			propertyNames.add(
				JenkinsResultsParserUtil.combine(
					"test.batch.class.names.excludes[", _batchName, "][",
					_testSuiteName, "]"));

			propertyNames.add(
				_getWildcardPropertyName(
					_batchName, _portalTestProperties,
					"test.batch.class.names.excludes", _testSuiteName));

			propertyNames.add(
				JenkinsResultsParserUtil.combine(
					"test.batch.class.names.excludes[", _testSuiteName, "]"));
		}

		propertyNames.add(
			_getWildcardPropertyName(
				_batchName, _portalTestProperties,
				"test.batch.class.names.excludes"));

		propertyNames.add(
			JenkinsResultsParserUtil.combine(
				"test.batch.class.names.excludes[", _batchName, "]"));

		propertyNames.add("test.batch.class.names.excludes");

		propertyNames.add("test.class.names.excludes");

		return _getFirstPropertyValue(_portalTestProperties, propertyNames);
	}

	private String _getTestClassNamesIncludesPropertyValue() {
		List<String> propertyNames = new ArrayList<>();

		if (_testSuiteName != null) {
			propertyNames.add(
				JenkinsResultsParserUtil.combine(
					"test.batch.class.names.includes[", _batchName, "][",
					_testSuiteName, "]"));

			propertyNames.add(
				_getWildcardPropertyName(
					_batchName, _portalTestProperties,
					"test.batch.class.names.includes", _testSuiteName));

			propertyNames.add(
				JenkinsResultsParserUtil.combine(
					"test.batch.class.names.includes[", _testSuiteName, "]"));
		}

		propertyNames.add(
			JenkinsResultsParserUtil.combine(
				"test.batch.class.names.includes[", _batchName, "]"));

		propertyNames.add(
			_getWildcardPropertyName(
				_batchName, _portalTestProperties,
				"test.batch.class.names.includes"));

		propertyNames.add("test.batch.class.names.includes");

		propertyNames.add("test.class.names.includes");

		return _getFirstPropertyValue(_portalTestProperties, propertyNames);
	}

	private List<PathMatcher> _getTestClassNamesPathMatchers(
		List<String> testClassNamesRelativeGlobs) {

		List<PathMatcher> pathMatchers = new ArrayList<>();

		File workingDirectory = _gitWorkingDirectory.getWorkingDirectory();

		String workingDirectoryPath = workingDirectory.getAbsolutePath();

		for (String testClassNamesRelativeGlob : testClassNamesRelativeGlobs) {
			FileSystem fileSystem = FileSystems.getDefault();

			pathMatchers.add(
				fileSystem.getPathMatcher(
					JenkinsResultsParserUtil.combine(
						"glob:", workingDirectoryPath, "/",
						testClassNamesRelativeGlob)));
		}

		return pathMatchers;
	}

	private String _getWildcardPropertyName(
		String batchName, Properties properties, String propertyName) {

		return _getWildcardPropertyName(
			batchName, properties, propertyName, null);
	}

	private String _getWildcardPropertyName(
		String batchName, Properties properties, String propertyName,
		String testSuiteName) {

		for (String wildcardPropertyName : properties.stringPropertyNames()) {
			if (!wildcardPropertyName.startsWith(propertyName)) {
				continue;
			}

			Matcher matcher = _propertyNamePattern.matcher(
				wildcardPropertyName);

			if (matcher.find()) {
				String batchNameMatcher = matcher.group("batchName");

				batchNameMatcher = batchNameMatcher.replace("*", ".+");

				if (!batchName.matches(batchNameMatcher)) {
					continue;
				}

				String testSuiteNameMatcher = matcher.group("testSuiteName");

				if (testSuiteName != testSuiteNameMatcher) {
					continue;
				}

				return wildcardPropertyName;
			}
		}

		return null;
	}

	private void _setBatchAxes() {
		List<String> testClassFileNames = new ArrayList<>(
			_getTestClassFileNames());

		Collections.sort(testClassFileNames);

		int testClassFileNamesCount = testClassFileNames.size();

		if (testClassFileNamesCount == 0) {
			return;
		}

		int batchAxisMaxSize = _getBatchAxisMaxSize();

		int batchAxisCount = (int)Math.ceil(
			(double)testClassFileNamesCount / batchAxisMaxSize);

		int batchAxisSize = (int)Math.ceil(
			(double)testClassFileNamesCount / batchAxisCount);

		int id = 0;

		for (List<String> batchAxisTestClassFileNames :
				Lists.partition(testClassFileNames, batchAxisSize)) {

			BatchAxis batchAxis = new BatchAxis(id);

			_batchAxes.put(id, batchAxis);

			for (String batchAxisTestClassFileName :
					batchAxisTestClassFileNames) {

				batchAxis.addTestClassFile(
					new File(batchAxisTestClassFileName));
			}

			id++;
		}
	}

	private void _setBatchRelevantChanges() {
		List<String> propertyNames = new ArrayList<>();

		if (_testSuiteName != null) {
			propertyNames.add(
				JenkinsResultsParserUtil.combine(
					"test.relevant.changes[", _testSuiteName, "]"));
		}

		propertyNames.add("test.relevant.changes");

		String propertyValue = _getFirstPropertyValue(
			_portalTestProperties, propertyNames);

		if (propertyValue != null) {
			_testRelevantChanges = Boolean.parseBoolean(propertyValue);

			return;
		}

		_testRelevantChanges = _DEFAULT_TEST_RELEVANT_CHANGES;
	}

	private void _setTestClassNamesExcludesRelativeGlobs() {
		String testClassNamesExcludesPropertyValue =
			_getTestClassNamesExcludesPropertyValue();

		if ((testClassNamesExcludesPropertyValue == null) ||
			testClassNamesExcludesPropertyValue.isEmpty()) {

			return;
		}

		List<String> testClassNamesExcludesRelativeGlobs = Arrays.asList(
			testClassNamesExcludesPropertyValue.split(","));

		if (_testRelevantChanges) {
			testClassNamesExcludesRelativeGlobs =
				_getRelevantTestClassNamesRelativeGlobs(
					testClassNamesExcludesRelativeGlobs);
		}

		_testClassNamesExcludesPathMatchers.addAll(
			_getTestClassNamesPathMatchers(
				testClassNamesExcludesRelativeGlobs));
	}

	private void _setTestClassNamesIncludesRelativeGlobs() {
		String testClassNamesIncludesPropertyValue =
			_getTestClassNamesIncludesPropertyValue();

		if ((testClassNamesIncludesPropertyValue == null) ||
			testClassNamesIncludesPropertyValue.isEmpty()) {

			return;
		}

		List<String> testClassNamesIncludesRelativeGlobs = Arrays.asList(
			testClassNamesIncludesPropertyValue.split(","));

		if (_testRelevantChanges) {
			testClassNamesIncludesRelativeGlobs =
				_getRelevantTestClassNamesRelativeGlobs(
					testClassNamesIncludesRelativeGlobs);
		}

		_testClassNamesIncludesPathMatchers.addAll(
			_getTestClassNamesPathMatchers(
				testClassNamesIncludesRelativeGlobs));
	}

	private static final int _DEFAULT_BATCH_AXIS_MAX_SIZE = 5000;

	private static final boolean _DEFAULT_TEST_RELEVANT_CHANGES = false;

	private final Map<Integer, BatchAxis> _batchAxes = new HashMap<>();
	private final String _batchName;
	private final GitWorkingDirectory _gitWorkingDirectory;
	private final Pattern _packagePathPattern = Pattern.compile(
		".*/(?<packagePath>com/.*)");
	private final Properties _portalTestProperties;
	private final Pattern _propertyNamePattern = Pattern.compile(
		"[^\\]]+\\[(?<batchName>[^\\]]+)\\](\\[(?<testSuiteName>[^\\]]+)\\])?");
	private final List<PathMatcher> _testClassNamesExcludesPathMatchers =
		new ArrayList<>();
	private final List<PathMatcher> _testClassNamesIncludesPathMatchers =
		new ArrayList<>();
	private boolean _testRelevantChanges;
	private final String _testSuiteName;

}