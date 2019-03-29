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

package com.liferay.arquillian.extension.junit.bridge.command;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;

import java.io.Serializable;

/**
 * @author Matthew Tambara
 */
public class TimingData implements Serializable {

	public TimingData(
		String methodName, String className, long time,
		boolean withMethodRules) {

		_methodName = methodName;
		_className = className;
		_time = time;
		_withMethodRules = withMethodRules;
	}

	public String getClassName() {
		return _className;
	}

	public String getMethodName() {
		return _methodName;
	}

	public long getTime() {
		return _time;
	}

	public boolean isWithMethodRules() {
		return _withMethodRules;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(7);

		sb.append(_className);

		if (_methodName != null) {
			sb.append(StringPool.PERIOD);
			sb.append(_methodName);
		}

		if (_withMethodRules) {
			sb.append(" (with rules)");
		}

		sb.append(" ################################ took ");
		sb.append(_time);
		sb.append("ms");

		return sb.toString();
	}

	private final String _className;
	private final String _methodName;
	private final long _time;
	private final boolean _withMethodRules;

}