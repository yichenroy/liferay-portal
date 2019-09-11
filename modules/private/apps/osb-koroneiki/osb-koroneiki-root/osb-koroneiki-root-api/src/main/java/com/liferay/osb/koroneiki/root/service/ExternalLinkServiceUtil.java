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
 * Provides the remote service utility for ExternalLink. This utility wraps
 * <code>com.liferay.osb.koroneiki.root.service.impl.ExternalLinkServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see ExternalLinkService
 * @generated
 */
public class ExternalLinkServiceUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.osb.koroneiki.root.service.impl.ExternalLinkServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ExternalLinkServiceUtil} to access the external link remote service. Add custom service methods to <code>com.liferay.osb.koroneiki.root.service.impl.ExternalLinkServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static com.liferay.osb.koroneiki.root.model.ExternalLink
			addExternalLink(
				long classNameId, long classPK, String domain,
				String entityName, String entityId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addExternalLink(
			classNameId, classPK, domain, entityName, entityId);
	}

	public static com.liferay.osb.koroneiki.root.model.ExternalLink
			deleteExternalLink(long externalLinkId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteExternalLink(externalLinkId);
	}

	public static com.liferay.osb.koroneiki.root.model.ExternalLink
			deleteExternalLink(String externalLinkKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteExternalLink(externalLinkKey);
	}

	public static com.liferay.osb.koroneiki.root.model.ExternalLink
			getExternalLink(long externalLinkId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getExternalLink(externalLinkId);
	}

	public static com.liferay.osb.koroneiki.root.model.ExternalLink
			getExternalLink(String externalLinkKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getExternalLink(externalLinkKey);
	}

	public static java.util.List
		<com.liferay.osb.koroneiki.root.model.ExternalLink> getExternalLinks(
				long classNameId, long classPK, int start, int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getExternalLinks(classNameId, classPK, start, end);
	}

	public static int getExternalLinksCount(long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getExternalLinksCount(classNameId, classPK);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.osb.koroneiki.root.model.ExternalLink
			updateExternalLink(long externalLinkId, String entityId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateExternalLink(externalLinkId, entityId);
	}

	public static ExternalLinkService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<ExternalLinkService, ExternalLinkService>
		_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(ExternalLinkService.class);

		ServiceTracker<ExternalLinkService, ExternalLinkService>
			serviceTracker =
				new ServiceTracker<ExternalLinkService, ExternalLinkService>(
					bundle.getBundleContext(), ExternalLinkService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}