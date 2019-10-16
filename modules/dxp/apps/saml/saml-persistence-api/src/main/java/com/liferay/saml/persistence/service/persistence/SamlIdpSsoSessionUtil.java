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

package com.liferay.saml.persistence.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import com.liferay.saml.persistence.model.SamlIdpSsoSession;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

import java.util.Date;
import java.util.List;

/**
 * The persistence utility for the saml idp sso session service. This utility wraps {@link com.liferay.saml.persistence.service.persistence.impl.SamlIdpSsoSessionPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Mika Koivisto
 * @see SamlIdpSsoSessionPersistence
 * @see com.liferay.saml.persistence.service.persistence.impl.SamlIdpSsoSessionPersistenceImpl
 * @generated
 */
@ProviderType
public class SamlIdpSsoSessionUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void clearCache(SamlIdpSsoSession samlIdpSsoSession) {
		getPersistence().clearCache(samlIdpSsoSession);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<SamlIdpSsoSession> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<SamlIdpSsoSession> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<SamlIdpSsoSession> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<SamlIdpSsoSession> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static SamlIdpSsoSession update(SamlIdpSsoSession samlIdpSsoSession) {
		return getPersistence().update(samlIdpSsoSession);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static SamlIdpSsoSession update(
		SamlIdpSsoSession samlIdpSsoSession, ServiceContext serviceContext) {
		return getPersistence().update(samlIdpSsoSession, serviceContext);
	}

	/**
	* Returns all the saml idp sso sessions where createDate &lt; &#63;.
	*
	* @param createDate the create date
	* @return the matching saml idp sso sessions
	*/
	public static List<SamlIdpSsoSession> findByCreateDate(Date createDate) {
		return getPersistence().findByCreateDate(createDate);
	}

	/**
	* Returns a range of all the saml idp sso sessions where createDate &lt; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SamlIdpSsoSessionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param createDate the create date
	* @param start the lower bound of the range of saml idp sso sessions
	* @param end the upper bound of the range of saml idp sso sessions (not inclusive)
	* @return the range of matching saml idp sso sessions
	*/
	public static List<SamlIdpSsoSession> findByCreateDate(Date createDate,
		int start, int end) {
		return getPersistence().findByCreateDate(createDate, start, end);
	}

	/**
	* Returns an ordered range of all the saml idp sso sessions where createDate &lt; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SamlIdpSsoSessionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param createDate the create date
	* @param start the lower bound of the range of saml idp sso sessions
	* @param end the upper bound of the range of saml idp sso sessions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching saml idp sso sessions
	*/
	public static List<SamlIdpSsoSession> findByCreateDate(Date createDate,
		int start, int end,
		OrderByComparator<SamlIdpSsoSession> orderByComparator) {
		return getPersistence()
				   .findByCreateDate(createDate, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the saml idp sso sessions where createDate &lt; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SamlIdpSsoSessionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param createDate the create date
	* @param start the lower bound of the range of saml idp sso sessions
	* @param end the upper bound of the range of saml idp sso sessions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching saml idp sso sessions
	*/
	public static List<SamlIdpSsoSession> findByCreateDate(Date createDate,
		int start, int end,
		OrderByComparator<SamlIdpSsoSession> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCreateDate(createDate, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first saml idp sso session in the ordered set where createDate &lt; &#63;.
	*
	* @param createDate the create date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching saml idp sso session
	* @throws NoSuchIdpSsoSessionException if a matching saml idp sso session could not be found
	*/
	public static SamlIdpSsoSession findByCreateDate_First(Date createDate,
		OrderByComparator<SamlIdpSsoSession> orderByComparator)
		throws com.liferay.saml.persistence.exception.NoSuchIdpSsoSessionException {
		return getPersistence()
				   .findByCreateDate_First(createDate, orderByComparator);
	}

	/**
	* Returns the first saml idp sso session in the ordered set where createDate &lt; &#63;.
	*
	* @param createDate the create date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching saml idp sso session, or <code>null</code> if a matching saml idp sso session could not be found
	*/
	public static SamlIdpSsoSession fetchByCreateDate_First(Date createDate,
		OrderByComparator<SamlIdpSsoSession> orderByComparator) {
		return getPersistence()
				   .fetchByCreateDate_First(createDate, orderByComparator);
	}

	/**
	* Returns the last saml idp sso session in the ordered set where createDate &lt; &#63;.
	*
	* @param createDate the create date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching saml idp sso session
	* @throws NoSuchIdpSsoSessionException if a matching saml idp sso session could not be found
	*/
	public static SamlIdpSsoSession findByCreateDate_Last(Date createDate,
		OrderByComparator<SamlIdpSsoSession> orderByComparator)
		throws com.liferay.saml.persistence.exception.NoSuchIdpSsoSessionException {
		return getPersistence()
				   .findByCreateDate_Last(createDate, orderByComparator);
	}

	/**
	* Returns the last saml idp sso session in the ordered set where createDate &lt; &#63;.
	*
	* @param createDate the create date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching saml idp sso session, or <code>null</code> if a matching saml idp sso session could not be found
	*/
	public static SamlIdpSsoSession fetchByCreateDate_Last(Date createDate,
		OrderByComparator<SamlIdpSsoSession> orderByComparator) {
		return getPersistence()
				   .fetchByCreateDate_Last(createDate, orderByComparator);
	}

	/**
	* Returns the saml idp sso sessions before and after the current saml idp sso session in the ordered set where createDate &lt; &#63;.
	*
	* @param samlIdpSsoSessionId the primary key of the current saml idp sso session
	* @param createDate the create date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next saml idp sso session
	* @throws NoSuchIdpSsoSessionException if a saml idp sso session with the primary key could not be found
	*/
	public static SamlIdpSsoSession[] findByCreateDate_PrevAndNext(
		long samlIdpSsoSessionId, Date createDate,
		OrderByComparator<SamlIdpSsoSession> orderByComparator)
		throws com.liferay.saml.persistence.exception.NoSuchIdpSsoSessionException {
		return getPersistence()
				   .findByCreateDate_PrevAndNext(samlIdpSsoSessionId,
			createDate, orderByComparator);
	}

	/**
	* Removes all the saml idp sso sessions where createDate &lt; &#63; from the database.
	*
	* @param createDate the create date
	*/
	public static void removeByCreateDate(Date createDate) {
		getPersistence().removeByCreateDate(createDate);
	}

	/**
	* Returns the number of saml idp sso sessions where createDate &lt; &#63;.
	*
	* @param createDate the create date
	* @return the number of matching saml idp sso sessions
	*/
	public static int countByCreateDate(Date createDate) {
		return getPersistence().countByCreateDate(createDate);
	}

	/**
	* Returns the saml idp sso session where samlIdpSsoSessionKey = &#63; or throws a {@link NoSuchIdpSsoSessionException} if it could not be found.
	*
	* @param samlIdpSsoSessionKey the saml idp sso session key
	* @return the matching saml idp sso session
	* @throws NoSuchIdpSsoSessionException if a matching saml idp sso session could not be found
	*/
	public static SamlIdpSsoSession findBySamlIdpSsoSessionKey(
		String samlIdpSsoSessionKey)
		throws com.liferay.saml.persistence.exception.NoSuchIdpSsoSessionException {
		return getPersistence().findBySamlIdpSsoSessionKey(samlIdpSsoSessionKey);
	}

	/**
	* Returns the saml idp sso session where samlIdpSsoSessionKey = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param samlIdpSsoSessionKey the saml idp sso session key
	* @return the matching saml idp sso session, or <code>null</code> if a matching saml idp sso session could not be found
	*/
	public static SamlIdpSsoSession fetchBySamlIdpSsoSessionKey(
		String samlIdpSsoSessionKey) {
		return getPersistence().fetchBySamlIdpSsoSessionKey(samlIdpSsoSessionKey);
	}

	/**
	* Returns the saml idp sso session where samlIdpSsoSessionKey = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param samlIdpSsoSessionKey the saml idp sso session key
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching saml idp sso session, or <code>null</code> if a matching saml idp sso session could not be found
	*/
	public static SamlIdpSsoSession fetchBySamlIdpSsoSessionKey(
		String samlIdpSsoSessionKey, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchBySamlIdpSsoSessionKey(samlIdpSsoSessionKey,
			retrieveFromCache);
	}

	/**
	* Removes the saml idp sso session where samlIdpSsoSessionKey = &#63; from the database.
	*
	* @param samlIdpSsoSessionKey the saml idp sso session key
	* @return the saml idp sso session that was removed
	*/
	public static SamlIdpSsoSession removeBySamlIdpSsoSessionKey(
		String samlIdpSsoSessionKey)
		throws com.liferay.saml.persistence.exception.NoSuchIdpSsoSessionException {
		return getPersistence()
				   .removeBySamlIdpSsoSessionKey(samlIdpSsoSessionKey);
	}

	/**
	* Returns the number of saml idp sso sessions where samlIdpSsoSessionKey = &#63;.
	*
	* @param samlIdpSsoSessionKey the saml idp sso session key
	* @return the number of matching saml idp sso sessions
	*/
	public static int countBySamlIdpSsoSessionKey(String samlIdpSsoSessionKey) {
		return getPersistence().countBySamlIdpSsoSessionKey(samlIdpSsoSessionKey);
	}

	/**
	* Caches the saml idp sso session in the entity cache if it is enabled.
	*
	* @param samlIdpSsoSession the saml idp sso session
	*/
	public static void cacheResult(SamlIdpSsoSession samlIdpSsoSession) {
		getPersistence().cacheResult(samlIdpSsoSession);
	}

	/**
	* Caches the saml idp sso sessions in the entity cache if it is enabled.
	*
	* @param samlIdpSsoSessions the saml idp sso sessions
	*/
	public static void cacheResult(List<SamlIdpSsoSession> samlIdpSsoSessions) {
		getPersistence().cacheResult(samlIdpSsoSessions);
	}

	/**
	* Creates a new saml idp sso session with the primary key. Does not add the saml idp sso session to the database.
	*
	* @param samlIdpSsoSessionId the primary key for the new saml idp sso session
	* @return the new saml idp sso session
	*/
	public static SamlIdpSsoSession create(long samlIdpSsoSessionId) {
		return getPersistence().create(samlIdpSsoSessionId);
	}

	/**
	* Removes the saml idp sso session with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param samlIdpSsoSessionId the primary key of the saml idp sso session
	* @return the saml idp sso session that was removed
	* @throws NoSuchIdpSsoSessionException if a saml idp sso session with the primary key could not be found
	*/
	public static SamlIdpSsoSession remove(long samlIdpSsoSessionId)
		throws com.liferay.saml.persistence.exception.NoSuchIdpSsoSessionException {
		return getPersistence().remove(samlIdpSsoSessionId);
	}

	public static SamlIdpSsoSession updateImpl(
		SamlIdpSsoSession samlIdpSsoSession) {
		return getPersistence().updateImpl(samlIdpSsoSession);
	}

	/**
	* Returns the saml idp sso session with the primary key or throws a {@link NoSuchIdpSsoSessionException} if it could not be found.
	*
	* @param samlIdpSsoSessionId the primary key of the saml idp sso session
	* @return the saml idp sso session
	* @throws NoSuchIdpSsoSessionException if a saml idp sso session with the primary key could not be found
	*/
	public static SamlIdpSsoSession findByPrimaryKey(long samlIdpSsoSessionId)
		throws com.liferay.saml.persistence.exception.NoSuchIdpSsoSessionException {
		return getPersistence().findByPrimaryKey(samlIdpSsoSessionId);
	}

	/**
	* Returns the saml idp sso session with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param samlIdpSsoSessionId the primary key of the saml idp sso session
	* @return the saml idp sso session, or <code>null</code> if a saml idp sso session with the primary key could not be found
	*/
	public static SamlIdpSsoSession fetchByPrimaryKey(long samlIdpSsoSessionId) {
		return getPersistence().fetchByPrimaryKey(samlIdpSsoSessionId);
	}

	public static java.util.Map<java.io.Serializable, SamlIdpSsoSession> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the saml idp sso sessions.
	*
	* @return the saml idp sso sessions
	*/
	public static List<SamlIdpSsoSession> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the saml idp sso sessions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SamlIdpSsoSessionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of saml idp sso sessions
	* @param end the upper bound of the range of saml idp sso sessions (not inclusive)
	* @return the range of saml idp sso sessions
	*/
	public static List<SamlIdpSsoSession> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the saml idp sso sessions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SamlIdpSsoSessionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of saml idp sso sessions
	* @param end the upper bound of the range of saml idp sso sessions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of saml idp sso sessions
	*/
	public static List<SamlIdpSsoSession> findAll(int start, int end,
		OrderByComparator<SamlIdpSsoSession> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the saml idp sso sessions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SamlIdpSsoSessionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of saml idp sso sessions
	* @param end the upper bound of the range of saml idp sso sessions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of saml idp sso sessions
	*/
	public static List<SamlIdpSsoSession> findAll(int start, int end,
		OrderByComparator<SamlIdpSsoSession> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the saml idp sso sessions from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of saml idp sso sessions.
	*
	* @return the number of saml idp sso sessions
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static SamlIdpSsoSessionPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<SamlIdpSsoSessionPersistence, SamlIdpSsoSessionPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(SamlIdpSsoSessionPersistence.class);

		ServiceTracker<SamlIdpSsoSessionPersistence, SamlIdpSsoSessionPersistence> serviceTracker =
			new ServiceTracker<SamlIdpSsoSessionPersistence, SamlIdpSsoSessionPersistence>(bundle.getBundleContext(),
				SamlIdpSsoSessionPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}