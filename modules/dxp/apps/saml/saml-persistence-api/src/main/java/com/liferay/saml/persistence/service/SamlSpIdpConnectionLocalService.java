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

package com.liferay.saml.persistence.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.BaseLocalService;
import com.liferay.portal.kernel.service.PersistedModelLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import com.liferay.saml.persistence.model.SamlSpIdpConnection;

import java.io.InputStream;
import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service interface for SamlSpIdpConnection. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Mika Koivisto
 * @see SamlSpIdpConnectionLocalServiceUtil
 * @see com.liferay.saml.persistence.service.base.SamlSpIdpConnectionLocalServiceBaseImpl
 * @see com.liferay.saml.persistence.service.impl.SamlSpIdpConnectionLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface SamlSpIdpConnectionLocalService extends BaseLocalService,
	PersistedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link SamlSpIdpConnectionLocalServiceUtil} to access the saml sp idp connection local service. Add custom service methods to {@link com.liferay.saml.persistence.service.impl.SamlSpIdpConnectionLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* Adds the saml sp idp connection to the database. Also notifies the appropriate model listeners.
	*
	* @param samlSpIdpConnection the saml sp idp connection
	* @return the saml sp idp connection that was added
	*/
	@Indexable(type = IndexableType.REINDEX)
	public SamlSpIdpConnection addSamlSpIdpConnection(
		SamlSpIdpConnection samlSpIdpConnection);

	public SamlSpIdpConnection addSamlSpIdpConnection(String samlIdpEntityId,
		boolean assertionSignatureRequired, long clockSkew, boolean enabled,
		boolean forceAuthn, boolean ldapImportEnabled, String metadataUrl,
		InputStream metadataXmlInputStream, String name, String nameIdFormat,
		boolean signAuthnRequest, String userAttributeMappings,
		ServiceContext serviceContext) throws PortalException;

	/**
	* Creates a new saml sp idp connection with the primary key. Does not add the saml sp idp connection to the database.
	*
	* @param samlSpIdpConnectionId the primary key for the new saml sp idp connection
	* @return the new saml sp idp connection
	*/
	@Transactional(enabled = false)
	public SamlSpIdpConnection createSamlSpIdpConnection(
		long samlSpIdpConnectionId);

	/**
	* @throws PortalException
	*/
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException;

	/**
	* Deletes the saml sp idp connection with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param samlSpIdpConnectionId the primary key of the saml sp idp connection
	* @return the saml sp idp connection that was removed
	* @throws PortalException if a saml sp idp connection with the primary key could not be found
	*/
	@Indexable(type = IndexableType.DELETE)
	public SamlSpIdpConnection deleteSamlSpIdpConnection(
		long samlSpIdpConnectionId) throws PortalException;

	/**
	* Deletes the saml sp idp connection from the database. Also notifies the appropriate model listeners.
	*
	* @param samlSpIdpConnection the saml sp idp connection
	* @return the saml sp idp connection that was removed
	*/
	@Indexable(type = IndexableType.DELETE)
	public SamlSpIdpConnection deleteSamlSpIdpConnection(
		SamlSpIdpConnection samlSpIdpConnection);

	public DynamicQuery dynamicQuery();

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery);

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.saml.persistence.model.impl.SamlSpIdpConnectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	*/
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end);

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.saml.persistence.model.impl.SamlSpIdpConnectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	*/
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end, OrderByComparator<T> orderByComparator);

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	public long dynamicQueryCount(DynamicQuery dynamicQuery);

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	public long dynamicQueryCount(DynamicQuery dynamicQuery,
		Projection projection);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SamlSpIdpConnection fetchSamlSpIdpConnection(
		long samlSpIdpConnectionId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery();

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

	/**
	* Returns the saml sp idp connection with the primary key.
	*
	* @param samlSpIdpConnectionId the primary key of the saml sp idp connection
	* @return the saml sp idp connection
	* @throws PortalException if a saml sp idp connection with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SamlSpIdpConnection getSamlSpIdpConnection(
		long samlSpIdpConnectionId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SamlSpIdpConnection getSamlSpIdpConnection(long companyId,
		String samlIdpEntityId) throws PortalException;

	/**
	* Returns a range of all the saml sp idp connections.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.saml.persistence.model.impl.SamlSpIdpConnectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of saml sp idp connections
	* @param end the upper bound of the range of saml sp idp connections (not inclusive)
	* @return the range of saml sp idp connections
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<SamlSpIdpConnection> getSamlSpIdpConnections(int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<SamlSpIdpConnection> getSamlSpIdpConnections(long companyId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<SamlSpIdpConnection> getSamlSpIdpConnections(long companyId,
		int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<SamlSpIdpConnection> getSamlSpIdpConnections(long companyId,
		int start, int end, OrderByComparator orderByComparator);

	/**
	* Returns the number of saml sp idp connections.
	*
	* @return the number of saml sp idp connections
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getSamlSpIdpConnectionsCount();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getSamlSpIdpConnectionsCount(long companyId);

	public void updateMetadata(long samlSpIdpConnectionId)
		throws PortalException;

	public SamlSpIdpConnection updateSamlSpIdpConnection(
		long samlSpIdpConnectionId, String samlIdpEntityId,
		boolean assertionSignatureRequired, long clockSkew, boolean enabled,
		boolean forceAuthn, boolean ldapImportEnabled, String metadataUrl,
		InputStream metadataXmlInputStream, String name, String nameIdFormat,
		boolean signAuthnRequest, String userAttributeMappings,
		ServiceContext serviceContext) throws PortalException;

	/**
	* Updates the saml sp idp connection in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param samlSpIdpConnection the saml sp idp connection
	* @return the saml sp idp connection that was updated
	*/
	@Indexable(type = IndexableType.REINDEX)
	public SamlSpIdpConnection updateSamlSpIdpConnection(
		SamlSpIdpConnection samlSpIdpConnection);
}