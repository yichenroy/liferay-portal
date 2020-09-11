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

package com.liferay.talend.common.oas;

import com.liferay.talend.BaseTestCase;

import java.util.Set;

import org.hamcrest.Matchers;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Igor Beslic
 */
public class OASExplorerTest extends BaseTestCase {

	@Test
	public void testGetEntitySchemaNames() {
		OASExplorer oasExplorer = new OASExplorer();

		Set<String> entitySchemaNames = oasExplorer.getEntitySchemaNames(
			readObject("openapi.json"));

		Assert.assertThat(entitySchemaNames, Matchers.hasSize(16));

		Assert.assertThat(
			entitySchemaNames,
			Matchers.hasItems(
				"Attachment", "Category", "Option", "OptionCategory",
				"ProductOption", "ProductShippingConfiguration",
				"ProductSubscriptionConfiguration", "Sku", "Specification"));
	}

}