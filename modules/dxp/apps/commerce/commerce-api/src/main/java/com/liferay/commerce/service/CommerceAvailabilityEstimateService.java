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

package com.liferay.commerce.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.model.CommerceAvailabilityEstimate;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.service.BaseService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Provides the remote service interface for CommerceAvailabilityEstimate. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceAvailabilityEstimateServiceUtil
 * @see com.liferay.commerce.service.base.CommerceAvailabilityEstimateServiceBaseImpl
 * @see com.liferay.commerce.service.impl.CommerceAvailabilityEstimateServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@OSGiBeanProperties(property =  {
	"json.web.service.context.name=commerce", "json.web.service.context.path=CommerceAvailabilityEstimate"}, service = CommerceAvailabilityEstimateService.class)
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface CommerceAvailabilityEstimateService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceAvailabilityEstimateServiceUtil} to access the commerce availability estimate remote service. Add custom service methods to {@link com.liferay.commerce.service.impl.CommerceAvailabilityEstimateServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public CommerceAvailabilityEstimate addCommerceAvailabilityEstimate(
		Map<Locale, String> titleMap, double priority,
		ServiceContext serviceContext) throws PortalException;

	public void deleteCommerceAvailabilityEstimate(
		long commerceAvailabilityEstimateId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceAvailabilityEstimate getCommerceAvailabilityEstimate(
		long commerceAvailabilityEstimateId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceAvailabilityEstimate> getCommerceAvailabilityEstimates(
		long groupId, int start, int end,
		OrderByComparator<CommerceAvailabilityEstimate> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommerceAvailabilityEstimatesCount(long groupId);

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public String getOSGiServiceIdentifier();

	public CommerceAvailabilityEstimate updateCommerceAvailabilityEstimate(
		long commerceAvailabilityEstimateId, Map<Locale, String> titleMap,
		double priority, ServiceContext serviceContext)
		throws PortalException;
}