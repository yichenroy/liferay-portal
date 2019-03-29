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

package com.liferay.arquillian.extension.junit.bridge.server;

import com.liferay.arquillian.extension.junit.bridge.command.TimingData;

import java.io.ObjectOutputStream;

import org.junit.runners.model.Statement;

/**
 * @author Matthew Tambara
 */
public class TestTimingStatement extends Statement {

	public TestTimingStatement(
		Statement statement, String methodName, String className,
		ObjectOutputStream objectOutputStream, boolean withRules) {

		_statement = statement;
		_methodName = methodName;
		_className = className;
		_objectOutputStream = objectOutputStream;
		_withRules = withRules;
	}

	@Override
	public void evaluate() throws Throwable {
		long startTime = System.currentTimeMillis();

		try {
			_statement.evaluate();
		}
		finally {
			_objectOutputStream.writeObject(
				new TimingData(
					_methodName, _className,
					System.currentTimeMillis() - startTime, _withRules));
		}
	}

	private final String _className;
	private final String _methodName;
	private final ObjectOutputStream _objectOutputStream;
	private final Statement _statement;
	private final boolean _withRules;

}