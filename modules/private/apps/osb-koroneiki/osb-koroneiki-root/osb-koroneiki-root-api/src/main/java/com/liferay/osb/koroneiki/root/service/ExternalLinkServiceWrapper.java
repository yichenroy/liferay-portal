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
 * Provides a wrapper for {@link ExternalLinkService}.
 *
 * @author Brian Wing Shun Chan
 * @see ExternalLinkService
 * @generated
 */
public class ExternalLinkServiceWrapper
	implements ExternalLinkService, ServiceWrapper<ExternalLinkService> {

	public ExternalLinkServiceWrapper(ExternalLinkService externalLinkService) {
		_externalLinkService = externalLinkService;
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ExternalLinkServiceUtil} to access the external link remote service. Add custom service methods to <code>com.liferay.osb.koroneiki.root.service.impl.ExternalLinkServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	@Override
	public com.liferay.osb.koroneiki.root.model.ExternalLink addExternalLink(
			long classNameId, long classPK, String domain, String entityName,
			String entityId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _externalLinkService.addExternalLink(
			classNameId, classPK, domain, entityName, entityId);
	}

	@Override
	public com.liferay.osb.koroneiki.root.model.ExternalLink deleteExternalLink(
			long externalLinkId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _externalLinkService.deleteExternalLink(externalLinkId);
	}

	@Override
	public com.liferay.osb.koroneiki.root.model.ExternalLink deleteExternalLink(
			String externalLinkKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _externalLinkService.deleteExternalLink(externalLinkKey);
	}

	@Override
	public com.liferay.osb.koroneiki.root.model.ExternalLink getExternalLink(
			long externalLinkId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _externalLinkService.getExternalLink(externalLinkId);
	}

	@Override
	public com.liferay.osb.koroneiki.root.model.ExternalLink getExternalLink(
			String externalLinkKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _externalLinkService.getExternalLink(externalLinkKey);
	}

	@Override
	public java.util.List<com.liferay.osb.koroneiki.root.model.ExternalLink>
			getExternalLinks(long classNameId, long classPK, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _externalLinkService.getExternalLinks(
			classNameId, classPK, start, end);
	}

	@Override
	public int getExternalLinksCount(long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _externalLinkService.getExternalLinksCount(classNameId, classPK);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _externalLinkService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.osb.koroneiki.root.model.ExternalLink updateExternalLink(
			long externalLinkId, String entityId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _externalLinkService.updateExternalLink(
			externalLinkId, entityId);
	}

	@Override
	public ExternalLinkService getWrappedService() {
		return _externalLinkService;
	}

	@Override
	public void setWrappedService(ExternalLinkService externalLinkService) {
		_externalLinkService = externalLinkService;
	}

	private ExternalLinkService _externalLinkService;

}