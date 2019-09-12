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

package com.liferay.osb.koroneiki.taproot.service;

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.osb.koroneiki.taproot.model.ContactRole;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.BaseLocalService;
import com.liferay.portal.kernel.service.PersistedModelLocalService;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the local service interface for ContactRole. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see ContactRoleLocalServiceUtil
 * @generated
 */
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface ContactRoleLocalService
	extends BaseLocalService, PersistedModelLocalService {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ContactRoleLocalServiceUtil} to access the contact role local service. Add custom service methods to <code>com.liferay.osb.koroneiki.taproot.service.impl.ContactRoleLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	 * Adds the contact role to the database. Also notifies the appropriate model listeners.
	 *
	 * @param contactRole the contact role
	 * @return the contact role that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	public ContactRole addContactRole(ContactRole contactRole);

	@Indexable(type = IndexableType.REINDEX)
	public ContactRole addContactRole(
			long userId, String name, String description, int type)
		throws PortalException;

	public void checkMemberRoles() throws PortalException;

	/**
	 * Creates a new contact role with the primary key. Does not add the contact role to the database.
	 *
	 * @param contactRoleId the primary key for the new contact role
	 * @return the new contact role
	 */
	@Transactional(enabled = false)
	public ContactRole createContactRole(long contactRoleId);

	/**
	 * Deletes the contact role from the database. Also notifies the appropriate model listeners.
	 *
	 * @param contactRole the contact role
	 * @return the contact role that was removed
	 * @throws PortalException
	 */
	@Indexable(type = IndexableType.DELETE)
	public ContactRole deleteContactRole(ContactRole contactRole)
		throws PortalException;

	/**
	 * Deletes the contact role with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param contactRoleId the primary key of the contact role
	 * @return the contact role that was removed
	 * @throws PortalException if a contact role with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	public ContactRole deleteContactRole(long contactRoleId)
		throws PortalException;

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DynamicQuery dynamicQuery();

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery);

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.osb.koroneiki.taproot.model.impl.ContactRoleModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end);

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.osb.koroneiki.taproot.model.impl.ContactRoleModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<T> orderByComparator);

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long dynamicQueryCount(DynamicQuery dynamicQuery);

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long dynamicQueryCount(
		DynamicQuery dynamicQuery, Projection projection);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ContactRole fetchContactRole(long contactRoleId);

	/**
	 * Returns the contact role with the matching UUID and company.
	 *
	 * @param uuid the contact role's UUID
	 * @param companyId the primary key of the company
	 * @return the matching contact role, or <code>null</code> if a matching contact role could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ContactRole fetchContactRoleByUuidAndCompanyId(
		String uuid, long companyId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ContactRole> getContactAccountContactRoles(
		long accountId, long contactId, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getContactAccountContactRolesCount(
		long accountId, long contactId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ContactRole> getContactContactRoles(
		long contactId, int start, int end);

	/**
	 * Returns the contact role with the primary key.
	 *
	 * @param contactRoleId the primary key of the contact role
	 * @return the contact role
	 * @throws PortalException if a contact role with the primary key could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ContactRole getContactRole(long contactRoleId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ContactRole getContactRole(String contactRoleKey)
		throws PortalException;

	/**
	 * Returns the contact role with the matching UUID and company.
	 *
	 * @param uuid the contact role's UUID
	 * @param companyId the primary key of the company
	 * @return the matching contact role
	 * @throws PortalException if a matching contact role could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ContactRole getContactRoleByUuidAndCompanyId(
			String uuid, long companyId)
		throws PortalException;

	/**
	 * Returns a range of all the contact roles.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.osb.koroneiki.taproot.model.impl.ContactRoleModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of contact roles
	 * @param end the upper bound of the range of contact roles (not inclusive)
	 * @return the range of contact roles
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ContactRole> getContactRoles(int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ContactRole> getContactRoles(int type, int start, int end);

	/**
	 * Returns the number of contact roles.
	 *
	 * @return the number of contact roles
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getContactRolesCount();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getContactRolesCount(int type);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ContactRole> getContactTeamContactRoles(
		long teamId, long contactId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ContactRole getMemberContactRole(int type);

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public String getOSGiServiceIdentifier();

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException;

	@Indexable(type = IndexableType.REINDEX)
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ContactRole reindex(long contactRoleId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Hits search(
			long companyId, int type, String keywords, int start, int end,
			Sort sort)
		throws PortalException;

	/**
	 * Updates the contact role in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param contactRole the contact role
	 * @return the contact role that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	public ContactRole updateContactRole(ContactRole contactRole);

	@Indexable(type = IndexableType.REINDEX)
	public ContactRole updateContactRole(
			long contactRoleId, String name, String description)
		throws PortalException;

}