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

import com.liferay.jenkins.results.parser.test.clazz.group.AxisTestClassGroup;
import com.liferay.jenkins.results.parser.test.clazz.group.BatchTestClassGroup;
import com.liferay.jenkins.results.parser.test.clazz.group.SegmentTestClassGroup;

import java.util.List;
import java.util.Properties;
import java.util.Set;

/**
 * @author Michael Hashimoto
 */
public interface Job {

	public List<AxisTestClassGroup> getAxisTestClassGroups();

	public Set<String> getBatchNames();

	public List<BatchTestClassGroup> getBatchTestClassGroups();

	public List<Build> getBuildHistory(JenkinsMaster jenkinsMaster);

	public BuildProfile getBuildProfile();

	public Set<String> getDistTypes();

	public Set<String> getDistTypesExcludingTomcat();

	public String getJobName();

	public Properties getJobProperties();

	public String getJobProperty(String key);

	public String getJobURL(JenkinsMaster jenkinsMaster);

	public Set<String> getSegmentNames();

	public List<SegmentTestClassGroup> getSegmentTestClassGroups();

	public String getTestPropertiesContent();

	public boolean isSegmentEnabled();

	public boolean isValidationRequired();

	public void readJobProperties();

	public static enum BuildProfile {

		DXP("DXP", "dxp"), PORTAL("Portal", "portal");

		public String toDisplayString() {
			return _displayString;
		}

		@Override
		public String toString() {
			return _string;
		}

		private BuildProfile(String displayString, String string) {
			_displayString = displayString;
			_string = string;
		}

		private final String _displayString;
		private final String _string;

	}

	public static enum DistType {

		CI("ci"), RELEASE("release");

		@Override
		public String toString() {
			return _string;
		}

		private DistType(String string) {
			_string = string;
		}

		private final String _string;

	}

}