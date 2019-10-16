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

package com.liferay.commerce.product.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CPFriendlyURLEntryService}.
 *
 * @author Marco Leo
 * @see CPFriendlyURLEntryService
 * @generated
 */
@ProviderType
public class CPFriendlyURLEntryServiceWrapper
	implements CPFriendlyURLEntryService,
		ServiceWrapper<CPFriendlyURLEntryService> {
	public CPFriendlyURLEntryServiceWrapper(
		CPFriendlyURLEntryService cpFriendlyURLEntryService) {
		_cpFriendlyURLEntryService = cpFriendlyURLEntryService;
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _cpFriendlyURLEntryService.getOSGiServiceIdentifier();
	}

	@Override
	public CPFriendlyURLEntryService getWrappedService() {
		return _cpFriendlyURLEntryService;
	}

	@Override
	public void setWrappedService(
		CPFriendlyURLEntryService cpFriendlyURLEntryService) {
		_cpFriendlyURLEntryService = cpFriendlyURLEntryService;
	}

	private CPFriendlyURLEntryService _cpFriendlyURLEntryService;
}