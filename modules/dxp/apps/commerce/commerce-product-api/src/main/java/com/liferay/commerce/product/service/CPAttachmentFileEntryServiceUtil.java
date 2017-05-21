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

package com.liferay.commerce.product.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.osgi.util.ServiceTrackerFactory;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for CPAttachmentFileEntry. This utility wraps
 * {@link com.liferay.commerce.product.service.impl.CPAttachmentFileEntryServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Marco Leo
 * @see CPAttachmentFileEntryService
 * @see com.liferay.commerce.product.service.base.CPAttachmentFileEntryServiceBaseImpl
 * @see com.liferay.commerce.product.service.impl.CPAttachmentFileEntryServiceImpl
 * @generated
 */
@ProviderType
public class CPAttachmentFileEntryServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.product.service.impl.CPAttachmentFileEntryServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.commerce.product.model.CPAttachmentFileEntry addCPAttachmentFileEntry(
		long classNameId, long classPK, long fileEntryId, int displayDateMonth,
		int displayDateDay, int displayDateYear, int displayDateHour,
		int displayDateMinute, int expirationDateMonth, int expirationDateDay,
		int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean neverExpire, java.lang.String json,
		int priority, int type,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCPAttachmentFileEntry(classNameId, classPK, fileEntryId,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, json, priority, type, serviceContext);
	}

	public static com.liferay.commerce.product.model.CPAttachmentFileEntry deleteCPAttachmentFileEntry(
		com.liferay.commerce.product.model.CPAttachmentFileEntry cpAttachmentFileEntry)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteCPAttachmentFileEntry(cpAttachmentFileEntry);
	}

	public static com.liferay.commerce.product.model.CPAttachmentFileEntry deleteCPAttachmentFileEntry(
		long cpAttachmentFileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteCPAttachmentFileEntry(cpAttachmentFileEntryId);
	}

	public static com.liferay.commerce.product.model.CPAttachmentFileEntry fetchCPAttachmentFileEntry(
		long cpAttachmentFileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().fetchCPAttachmentFileEntry(cpAttachmentFileEntryId);
	}

	public static com.liferay.commerce.product.model.CPAttachmentFileEntry updateCPAttachmentFileEntry(
		long cpAttachmentFileEntryId, int displayDateMonth, int displayDateDay,
		int displayDateYear, int displayDateHour, int displayDateMinute,
		int expirationDateMonth, int expirationDateDay, int expirationDateYear,
		int expirationDateHour, int expirationDateMinute, boolean neverExpire,
		java.lang.String json, int priority, int type,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCPAttachmentFileEntry(cpAttachmentFileEntryId,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, json, priority, type, serviceContext);
	}

	public static int getCPAttachmentFileEntriesCount(long classNameId,
		int classPK) {
		return getService().getCPAttachmentFileEntriesCount(classNameId, classPK);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static java.util.List<com.liferay.commerce.product.model.CPAttachmentFileEntry> getCPAttachmentFileEntries(
		long classNameId, int classPK, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCPAttachmentFileEntries(classNameId, classPK, start, end);
	}

	public static java.util.List<com.liferay.commerce.product.model.CPAttachmentFileEntry> getCPAttachmentFileEntries(
		long classNameId, int classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CPAttachmentFileEntry> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCPAttachmentFileEntries(classNameId, classPK, start,
			end, orderByComparator);
	}

	public static CPAttachmentFileEntryService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CPAttachmentFileEntryService, CPAttachmentFileEntryService> _serviceTracker =
		ServiceTrackerFactory.open(CPAttachmentFileEntryService.class);
}