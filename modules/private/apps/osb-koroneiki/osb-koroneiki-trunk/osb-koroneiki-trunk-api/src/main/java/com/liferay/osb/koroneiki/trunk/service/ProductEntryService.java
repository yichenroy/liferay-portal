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

package com.liferay.osb.koroneiki.trunk.service;

import com.liferay.osb.koroneiki.trunk.model.ProductEntry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.service.BaseService;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the remote service interface for ProductEntry. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see ProductEntryServiceUtil
 * @generated
 */
@AccessControlled
@JSONWebService
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface ProductEntryService extends BaseService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ProductEntryServiceUtil} to access the product entry remote service. Add custom service methods to <code>com.liferay.osb.koroneiki.trunk.service.impl.ProductEntryServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public ProductEntry addProductEntry(String name) throws PortalException;

	public ProductEntry deleteProductEntry(long productEntryId)
		throws PortalException;

	public ProductEntry deleteProductEntry(String productEntryKey)
		throws PortalException;

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public String getOSGiServiceIdentifier();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ProductEntry> getProductEntries(int start, int end)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getProductEntriesCount() throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ProductEntry getProductEntry(long productEntryId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ProductEntry getProductEntry(String productEntryKey)
		throws PortalException;

	public ProductEntry updateProductEntry(long productEntryId, String name)
		throws PortalException;

	public ProductEntry updateProductEntry(String productEntryKey, String name)
		throws PortalException;

}