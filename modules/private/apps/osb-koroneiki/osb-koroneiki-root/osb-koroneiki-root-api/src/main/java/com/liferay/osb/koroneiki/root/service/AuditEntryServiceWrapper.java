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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link AuditEntryService}.
 *
 * @author Brian Wing Shun Chan
 * @see AuditEntryService
 * @generated
 */
public class AuditEntryServiceWrapper
	implements AuditEntryService, ServiceWrapper<AuditEntryService> {

	public AuditEntryServiceWrapper(AuditEntryService auditEntryService) {
		_auditEntryService = auditEntryService;
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link AuditEntryServiceUtil} to access the audit entry remote service. Add custom service methods to <code>com.liferay.osb.koroneiki.root.service.impl.AuditEntryServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	@Override
	public java.util.List<com.liferay.osb.koroneiki.root.model.AuditEntry>
			getAuditEntries(long classNameId, long classPK, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _auditEntryService.getAuditEntries(
			classNameId, classPK, start, end);
	}

	@Override
	public int getAuditEntriesCount(long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _auditEntryService.getAuditEntriesCount(classNameId, classPK);
	}

	@Override
	public com.liferay.osb.koroneiki.root.model.AuditEntry getAuditEntry(
			long auditEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _auditEntryService.getAuditEntry(auditEntryId);
	}

	@Override
	public com.liferay.osb.koroneiki.root.model.AuditEntry getAuditEntry(
			String auditEntryKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _auditEntryService.getAuditEntry(auditEntryKey);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _auditEntryService.getOSGiServiceIdentifier();
	}

	@Override
	public AuditEntryService getWrappedService() {
		return _auditEntryService;
	}

	@Override
	public void setWrappedService(AuditEntryService auditEntryService) {
		_auditEntryService = auditEntryService;
	}

	private AuditEntryService _auditEntryService;

}