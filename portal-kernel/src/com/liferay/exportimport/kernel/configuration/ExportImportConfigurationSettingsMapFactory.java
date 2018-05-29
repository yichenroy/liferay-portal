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

package com.liferay.exportimport.kernel.configuration;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;

import java.io.Serializable;

import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.portlet.PortletRequest;

/**
 * Provides a way to build a settings map for an {@link
 * com.liferay.exportimport.kernel.model.ExportImportConfiguration}, which can
 * be used to start and control an export, import, or staging process.
 *
 * @author Daniel Kocsis
 * @author Akos Thurzo
 * @since  7.0
 */
@ProviderType
public interface ExportImportConfigurationSettingsMapFactory {

	public Map<String, Serializable> buildExportLayoutSettingsMap(
		long userId, long groupId, boolean privateLayout, long[] layoutIds,
		Map<String, String[]> parameterMap, Locale locale, TimeZone timeZone);

	public Map<String, Serializable> buildExportLayoutSettingsMap(
		User user, long groupId, boolean privateLayout, long[] layoutIds,
		Map<String, String[]> parameterMap);

	public Map<String, Serializable> buildExportPortletSettingsMap(
		long userId, long sourcePlid, long sourceGroupId, String portletId,
		Map<String, String[]> parameterMap, Locale locale, TimeZone timeZone,
		String fileName);

	public Map<String, Serializable> buildExportPortletSettingsMap(
		User user, long sourcePlid, long sourceGroupId, String portletId,
		Map<String, String[]> parameterMap, String fileName);

	public Map<String, Serializable> buildImportLayoutSettingsMap(
		long userId, long targetGroupId, boolean privateLayout,
		long[] layoutIds, Map<String, String[]> parameterMap, Locale locale,
		TimeZone timeZone);

	public Map<String, Serializable> buildImportLayoutSettingsMap(
		User user, long targetGroupId, boolean privateLayout, long[] layoutIds,
		Map<String, String[]> parameterMap);

	public Map<String, Serializable> buildImportPortletSettingsMap(
		long userId, long targetPlid, long targetGroupId, String portletId,
		Map<String, String[]> parameterMap, Locale locale, TimeZone timeZone);

	public Map<String, Serializable> buildImportPortletSettingsMap(
		User user, long targetPlid, long targetGroupId, String portletId,
		Map<String, String[]> parameterMap);

	public Map<String, Serializable> buildPublishLayoutLocalSettingsMap(
		long userId, long sourceGroupId, long targetGroupId,
		boolean privateLayout, long[] layoutIds,
		Map<String, String[]> parameterMap, Locale locale, TimeZone timeZone);

	public Map<String, Serializable> buildPublishLayoutLocalSettingsMap(
		User user, long sourceGroupId, long targetGroupId,
		boolean privateLayout, long[] layoutIds,
		Map<String, String[]> parameterMap);

	public Map<String, Serializable> buildPublishLayoutRemoteSettingsMap(
		long userId, long sourceGroupId, boolean privateLayout,
		Map<Long, Boolean> layoutIdMap, Map<String, String[]> parameterMap,
		String remoteAddress, int remotePort, String remotePathContext,
		boolean secureConnection, long remoteGroupId,
		boolean remotePrivateLayout, Locale locale, TimeZone timeZone);

	public Map<String, Serializable> buildPublishLayoutRemoteSettingsMap(
		User user, long sourceGroupId, boolean privateLayout,
		Map<Long, Boolean> layoutIdMap, Map<String, String[]> parameterMap,
		String remoteAddress, int remotePort, String remotePathContext,
		boolean secureConnection, long remoteGroupId,
		boolean remotePrivateLayout);

	public Map<String, Serializable> buildPublishPortletSettingsMap(
		long userId, long sourceGroupId, long sourcePlid, long targetGroupId,
		long targetPlid, String portletId, Map<String, String[]> parameterMap,
		Locale locale, TimeZone timeZone);

	public Map<String, Serializable> buildPublishPortletSettingsMap(
		User user, long sourceGroupId, long sourcePlid, long targetGroupId,
		long targetPlid, String portletId, Map<String, String[]> parameterMap);

	/**
	 * Returns an export layout settings map if the type is {@link
	 * ExportImportConfigurationConstants#TYPE_EXPORT_LAYOUT}; otherwise,
	 * returns either a local or remote publish layout settings map, depending
	 * on the staging type.
	 *
	 * @param  portletRequest the portlet request
	 * @param  groupId the primary key of the group
	 * @param  type the export/import option type
	 * @return an export layout settings map if the type is an export layout;
	 *         otherwise, returns either a local or remote publish layout
	 *         settings map, depending on the staging type
	 */
	public Map<String, Serializable> buildSettingsMap(
			PortletRequest portletRequest, long groupId, int type)
		throws PortalException;

}