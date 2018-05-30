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

package com.liferay.commerce.organization.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.EmailAddress;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.service.BaseService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;

/**
 * Provides the remote service interface for CommerceOrganization. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Marco Leo
 * @see CommerceOrganizationServiceUtil
 * @see com.liferay.commerce.organization.service.base.CommerceOrganizationServiceBaseImpl
 * @see com.liferay.commerce.organization.service.impl.CommerceOrganizationServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@OSGiBeanProperties(property =  {
	"json.web.service.context.name=commerce", "json.web.service.context.path=CommerceOrganization"}, service = CommerceOrganizationService.class)
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface CommerceOrganizationService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceOrganizationServiceUtil} to access the commerce organization remote service. Add custom service methods to {@link com.liferay.commerce.organization.service.impl.CommerceOrganizationServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public Organization addOrganization(long parentOrganizationId, String name,
		String type, ServiceContext serviceContext) throws PortalException;

	public void addOrganizationUsers(long organizationId,
		String[] emailAddresses, ServiceContext serviceContext)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Organization getOrganization(long organizationId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Address getOrganizationPrimaryAddress(long organizationId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public EmailAddress getOrganizationPrimaryEmailAddress(long organizationId)
		throws PortalException;

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public String getOSGiServiceIdentifier();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BaseModelSearchResult<Organization> searchOrganizations(
		long userId, long organizationId, String type, String keywords,
		int start, int end, Sort[] sorts) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BaseModelSearchResult<Organization> searchOrganizationsByGroup(
		long groupId, long userId, String type, String keywords, int start,
		int end, Sort[] sorts) throws PortalException;

	public void unsetOrganizationUsers(long organizationId, long[] userIds)
		throws PortalException;

	public Organization updateOrganization(long organizationId, String name,
		long emailAddressId, String address, long addressId, String street1,
		String street2, String street3, String city, String zip, long regionId,
		long countryId, boolean logo, byte[] logoBytes,
		ServiceContext serviceContext) throws PortalException;
}