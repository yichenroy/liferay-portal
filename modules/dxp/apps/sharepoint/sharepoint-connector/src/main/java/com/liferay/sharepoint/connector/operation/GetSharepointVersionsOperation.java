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

package com.liferay.sharepoint.connector.operation;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.sharepoint.connector.SharepointException;
import com.liferay.sharepoint.connector.SharepointObject;
import com.liferay.sharepoint.connector.SharepointVersion;
import com.liferay.sharepoint.connector.internal.util.RemoteExceptionUtil;

import com.microsoft.schemas.sharepoint.soap.GetVersionsResponseGetVersionsResult;

import java.rmi.RemoteException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.xml.bind.DatatypeConverter;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author Iván Zaera
 */
public class GetSharepointVersionsOperation extends BaseOperation {

	@Override
	public void afterPropertiesSet() {
		_getSharepointObjectByPathOperation = getOperation(
			GetSharepointObjectByPathOperation.class);
	}

	public List<SharepointVersion> execute(String filePath)
		throws SharepointException {

		try {
			SharepointObject sharepointObject =
				_getSharepointObjectByPathOperation.execute(filePath);

			if (sharepointObject == null) {
				throw new SharepointException(
					"Unable to find Sharepoint object at " + filePath);
			}

			String fileFullPath = toFullPath(filePath);

			GetVersionsResponseGetVersionsResult
				getVersionsResponseGetVersionsResult = versionsSoap.getVersions(
					fileFullPath);

			Element getVersionsResponseGetVersionsResultElement =
				xmlHelper.getElement(getVersionsResponseGetVersionsResult);

			return getSharepointVersions(
				sharepointObject.getSharepointObjectId(),
				getVersionsResponseGetVersionsResultElement);
		}
		catch (RemoteException re) {
			RemoteExceptionUtil.handleRemoteException(re);

			throw new IllegalStateException();
		}
	}

	protected Date getDate(String dateString) {
		Calendar calendar = DatatypeConverter.parseDateTime(dateString);

		return calendar.getTime();
	}

	protected String getSharepointVersionId(
		long sharepointObjectId, String version) {

		return sharepointObjectId + StringPool.AT + version;
	}

	protected List<SharepointVersion> getSharepointVersions(
		long sharepointObjectId,
		Element getVersionsResponseGetVersionsResultElement) {

		List<SharepointVersion> sharepointVersions = new ArrayList<>();

		NodeList nodeList =
			getVersionsResponseGetVersionsResultElement.getChildNodes();

		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);

			String localName = node.getLocalName();

			if ((localName == null) ||
				!StringUtil.equalsIgnoreCase(localName, "result")) {

				continue;
			}

			NamedNodeMap namedNodeMap = node.getAttributes();

			Node commentsNode = namedNodeMap.getNamedItem("comments");
			Node createdByNode = namedNodeMap.getNamedItem("createdBy");
			Node createdRawNode = namedNodeMap.getNamedItem("createdRaw");
			Node versionNode = namedNodeMap.getNamedItem("version");
			Node urlNode = namedNodeMap.getNamedItem("url");
			Node sizeNode = namedNodeMap.getNamedItem("size");

			SharepointVersion sharepointVersion = new SharepointVersion(
				commentsNode.getNodeValue(), createdByNode.getNodeValue(),
				getDate(createdRawNode.getNodeValue()),
				getSharepointVersionId(
					sharepointObjectId, versionNode.getNodeValue()),
				GetterUtil.getLong(sizeNode.getNodeValue()),
				urlHelper.toURL(urlNode.getNodeValue()),
				getVersion(versionNode.getNodeValue()));

			sharepointVersions.add(sharepointVersion);
		}

		Collections.sort(sharepointVersions, _comparator);

		return sharepointVersions;
	}

	protected String getVersion(String version) {
		if (version.startsWith(StringPool.AT)) {
			version = version.substring(1);
		}

		return version;
	}

	private static final Comparator<SharepointVersion> _comparator =
		new SharepointVersionComparator();

	private GetSharepointObjectByPathOperation
		_getSharepointObjectByPathOperation;

}