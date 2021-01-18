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

/**
 * @author Michael Hashimoto
 */
public class AppReleaseFunctionalBatchTestClassGroup
	extends FunctionalBatchTestClassGroup {

	@Override
	public String getTestBatchRunPropertyQuery() {
		String propertyQuery = System.getenv("TEST_BATCH_RUN_PROPERTY_QUERY");

		if ((propertyQuery != null) && !propertyQuery.isEmpty()) {
			return propertyQuery;
		}

		return super.getTestBatchRunPropertyQuery();
	}

	protected AppReleaseFunctionalBatchTestClassGroup(
		String batchName, PortalTestClassJob portalTestClassJob) {

		super(batchName, portalTestClassJob);
	}

}