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

package com.liferay.poshi.core;

import com.liferay.poshi.core.util.FileUtil;

import java.io.File;

import junit.framework.TestCase;

import org.apache.commons.lang3.ArrayUtils;

import org.dom4j.Element;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Karen Dang
 * @author Michael Hashimoto
 */
public class PoshiContextTest extends TestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		String[] poshiFileNames = ArrayUtils.addAll(
			PoshiContext.POSHI_SUPPORT_FILE_INCLUDES,
			PoshiContext.POSHI_TEST_FILE_INCLUDES);

		String poshiFileDir =
			"/opt/dev/projects/github/liferay-portal/portal-web";

		PoshiContext.readFiles(poshiFileNames, poshiFileDir);
	}

	@After
	@Override
	public void tearDown() throws Exception {
		PoshiContext.clear();
	}

	@Test
	public void testGetFilePath() throws Exception {
		System.out.println("Run this test for validation on portal-web");
	}

	@Test
	public void testGetFunctionCommandElement() throws Exception {
		Element element = PoshiContext.getFunctionCommandElement(
			"Click#clickAt", PoshiContext.getDefaultNamespace());

		Assert.assertEquals(
			"getFunctionCommandElement is failing", "clickAt",
			element.attributeValue("name"));
	}

	@Test
	public void testGetFunctionLocatorCount() throws Exception {
		int locatorCount = PoshiContext.getFunctionLocatorCount(
			"Click", PoshiContext.getDefaultNamespace());

		Assert.assertEquals(
			"getFunctionLocatorCount is failing", 1, locatorCount);
	}

	@Test
	public void testGetFunctionRootElement() {
		Element element = PoshiContext.getFunctionRootElement(
			"Click", PoshiContext.getDefaultNamespace());

		Assert.assertEquals(
			"getFunctionRootElement is failing", "definition",
			element.getName());
	}

	@Test
	public void testGetMacroCommandElement() {
		Element element = PoshiContext.getMacroCommandElement(
			"Macro#test", PoshiContext.getDefaultNamespace());

		Assert.assertEquals(
			"getMacroCommandElement is failing", "test",
			element.attributeValue("name"));
	}

	@Test
	public void testGetPathLocator() throws Exception {
		String locator = PoshiContext.getPathLocator(
			"Action1#TEST_TITLE", PoshiContext.getDefaultNamespace());

		Assert.assertEquals(
			"getPathLocator is failing", "//input[@class='Title']", locator);

		locator = PoshiContext.getPathLocator(
			"Action1#TEST_CONTENT", PoshiContext.getDefaultNamespace());

		Assert.assertEquals(
			"getPathLocator is failing", "//input[@class='Content']", locator);
	}

	@Test
	public void testGetSeleniumParameterCount() {
		int count = PoshiContext.getSeleniumParameterCount("clickAt");

		Assert.assertEquals("getSeleniumParameterCount is failing", 2, count);

		count = PoshiContext.getSeleniumParameterCount("click");

		Assert.assertEquals("getSeleniumParameterCount is failing", 1, count);
	}

	@Test
	public void testGetTestCaseCommandElement() {
		Element element = PoshiContext.getTestCaseCommandElement(
			"Test#test", PoshiContext.getDefaultNamespace());

		Assert.assertEquals(
			"getTestCaseCommandElement is failing", "test",
			element.attributeValue("name"));
	}

	@Test
	public void testGetTestCaseRootElement() {
		Element element = PoshiContext.getTestCaseRootElement(
			"Test", PoshiContext.getDefaultNamespace());

		Assert.assertEquals(
			"getTestCaseRootElement is failing", "definition",
			element.getName());
	}

}