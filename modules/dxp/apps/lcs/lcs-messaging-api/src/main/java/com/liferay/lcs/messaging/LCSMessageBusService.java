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

package com.liferay.lcs.messaging;

import java.util.Map;

/**
 * @author Riccardo Ferrari
 */
public interface LCSMessageBusService {

	public boolean registerLCSMessageListener(
		String destinationName, LCSMessageListener lcsMessageListener);

	public void sendMessage(
		String destinationName, Map<String, String> metadata, String payload,
		String responseDestinationName);

	public void sendMessage(String destinationName, String payload);

	public boolean unregisterLCSMessageListener(
		String destinationName, LCSMessageListener lcsMessageListener);

}