/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.osb.koroneiki.root.service;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for AuditEntry. This utility wraps
 * <code>com.liferay.osb.koroneiki.root.service.impl.AuditEntryServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see AuditEntryService
 * @generated
 */
public class AuditEntryServiceUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.osb.koroneiki.root.service.impl.AuditEntryServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link AuditEntryServiceUtil} to access the audit entry remote service. Add custom service methods to <code>com.liferay.osb.koroneiki.root.service.impl.AuditEntryServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static java.util.List
		<com.liferay.osb.koroneiki.root.model.AuditEntry> getAuditEntries(
				long classNameId, long classPK, int start, int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getAuditEntries(classNameId, classPK, start, end);
	}

	public static int getAuditEntriesCount(long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getAuditEntriesCount(classNameId, classPK);
	}

	public static com.liferay.osb.koroneiki.root.model.AuditEntry getAuditEntry(
			long auditEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getAuditEntry(auditEntryId);
	}

	public static com.liferay.osb.koroneiki.root.model.AuditEntry getAuditEntry(
			String auditEntryKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getAuditEntry(auditEntryKey);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static AuditEntryService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<AuditEntryService, AuditEntryService>
		_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(AuditEntryService.class);

		ServiceTracker<AuditEntryService, AuditEntryService> serviceTracker =
			new ServiceTracker<AuditEntryService, AuditEntryService>(
				bundle.getBundleContext(), AuditEntryService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}