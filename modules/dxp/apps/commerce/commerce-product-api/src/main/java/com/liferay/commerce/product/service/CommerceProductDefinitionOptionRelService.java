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

import com.liferay.commerce.product.model.CommerceProductDefinitionOptionRel;

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
 * Provides the remote service interface for CommerceProductDefinitionOptionRel. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Marco Leo
 * @see CommerceProductDefinitionOptionRelServiceUtil
 * @see com.liferay.commerce.product.service.base.CommerceProductDefinitionOptionRelServiceBaseImpl
 * @see com.liferay.commerce.product.service.impl.CommerceProductDefinitionOptionRelServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@OSGiBeanProperties(property =  {
	"json.web.service.context.name=commerce", "json.web.service.context.path=CommerceProductDefinitionOptionRel"}, service = CommerceProductDefinitionOptionRelService.class)
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface CommerceProductDefinitionOptionRelService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceProductDefinitionOptionRelServiceUtil} to access the commerce product definition option rel remote service. Add custom service methods to {@link com.liferay.commerce.product.service.impl.CommerceProductDefinitionOptionRelServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public CommerceProductDefinitionOptionRel addCommerceProductDefinitionOptionRel(
		long commerceProductDefinitionId, long commerceProductOptionId,
		Map<Locale, java.lang.String> nameMap,
		Map<Locale, java.lang.String> descriptionMap,
		java.lang.String ddmFormFieldTypeName, int priority,
		ServiceContext serviceContext) throws PortalException;

	public CommerceProductDefinitionOptionRel deleteCommerceProductDefinitionOptionRel(
		CommerceProductDefinitionOptionRel commerceProductDefinitionOptionRel)
		throws PortalException;

	public CommerceProductDefinitionOptionRel deleteCommerceProductDefinitionOptionRel(
		long commerceProductDefinitionOptionRelId) throws PortalException;

	public CommerceProductDefinitionOptionRel updateCommerceProductDefinitionOptionRel(
		long commerceProductDefinitionOptionRelId,
		long commerceProductOptionId, Map<Locale, java.lang.String> nameMap,
		Map<Locale, java.lang.String> descriptionMap,
		java.lang.String ddmFormFieldTypeName, int priority,
		ServiceContext serviceContext) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommerceProductDefinitionOptionRelsCount(
		long commerceProductDefinitionId) throws PortalException;

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceProductDefinitionOptionRel> getCommerceProductDefinitionOptionRels(
		long commerceProductDefinitionId, int start, int end)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceProductDefinitionOptionRel> getCommerceProductDefinitionOptionRels(
		long commerceProductDefinitionId, int start, int end,
		OrderByComparator<CommerceProductDefinitionOptionRel> orderByComparator)
		throws PortalException;
}