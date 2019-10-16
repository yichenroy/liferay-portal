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

package com.liferay.commerce.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.model.CommercePaymentMethod;

import com.liferay.osgi.util.ServiceTrackerFactory;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the commerce payment method service. This utility wraps {@link com.liferay.commerce.service.persistence.impl.CommercePaymentMethodPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommercePaymentMethodPersistence
 * @see com.liferay.commerce.service.persistence.impl.CommercePaymentMethodPersistenceImpl
 * @generated
 */
@ProviderType
public class CommercePaymentMethodUtil {
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
	public static void clearCache(CommercePaymentMethod commercePaymentMethod) {
		getPersistence().clearCache(commercePaymentMethod);
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
	public static List<CommercePaymentMethod> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CommercePaymentMethod> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CommercePaymentMethod> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CommercePaymentMethod> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CommercePaymentMethod update(
		CommercePaymentMethod commercePaymentMethod) {
		return getPersistence().update(commercePaymentMethod);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CommercePaymentMethod update(
		CommercePaymentMethod commercePaymentMethod,
		ServiceContext serviceContext) {
		return getPersistence().update(commercePaymentMethod, serviceContext);
	}

	/**
	* Returns all the commerce payment methods where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching commerce payment methods
	*/
	public static List<CommercePaymentMethod> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the commerce payment methods where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePaymentMethodModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce payment methods
	* @param end the upper bound of the range of commerce payment methods (not inclusive)
	* @return the range of matching commerce payment methods
	*/
	public static List<CommercePaymentMethod> findByGroupId(long groupId,
		int start, int end) {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the commerce payment methods where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePaymentMethodModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce payment methods
	* @param end the upper bound of the range of commerce payment methods (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce payment methods
	*/
	public static List<CommercePaymentMethod> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<CommercePaymentMethod> orderByComparator) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce payment methods where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePaymentMethodModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce payment methods
	* @param end the upper bound of the range of commerce payment methods (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce payment methods
	*/
	public static List<CommercePaymentMethod> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<CommercePaymentMethod> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first commerce payment method in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce payment method
	* @throws NoSuchPaymentMethodException if a matching commerce payment method could not be found
	*/
	public static CommercePaymentMethod findByGroupId_First(long groupId,
		OrderByComparator<CommercePaymentMethod> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchPaymentMethodException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first commerce payment method in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce payment method, or <code>null</code> if a matching commerce payment method could not be found
	*/
	public static CommercePaymentMethod fetchByGroupId_First(long groupId,
		OrderByComparator<CommercePaymentMethod> orderByComparator) {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last commerce payment method in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce payment method
	* @throws NoSuchPaymentMethodException if a matching commerce payment method could not be found
	*/
	public static CommercePaymentMethod findByGroupId_Last(long groupId,
		OrderByComparator<CommercePaymentMethod> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchPaymentMethodException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last commerce payment method in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce payment method, or <code>null</code> if a matching commerce payment method could not be found
	*/
	public static CommercePaymentMethod fetchByGroupId_Last(long groupId,
		OrderByComparator<CommercePaymentMethod> orderByComparator) {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the commerce payment methods before and after the current commerce payment method in the ordered set where groupId = &#63;.
	*
	* @param commercePaymentMethodId the primary key of the current commerce payment method
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce payment method
	* @throws NoSuchPaymentMethodException if a commerce payment method with the primary key could not be found
	*/
	public static CommercePaymentMethod[] findByGroupId_PrevAndNext(
		long commercePaymentMethodId, long groupId,
		OrderByComparator<CommercePaymentMethod> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchPaymentMethodException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(commercePaymentMethodId, groupId,
			orderByComparator);
	}

	/**
	* Removes all the commerce payment methods where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Returns the number of commerce payment methods where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching commerce payment methods
	*/
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns the commerce payment method where groupId = &#63; and engineKey = &#63; or throws a {@link NoSuchPaymentMethodException} if it could not be found.
	*
	* @param groupId the group ID
	* @param engineKey the engine key
	* @return the matching commerce payment method
	* @throws NoSuchPaymentMethodException if a matching commerce payment method could not be found
	*/
	public static CommercePaymentMethod findByG_E(long groupId,
		java.lang.String engineKey)
		throws com.liferay.commerce.exception.NoSuchPaymentMethodException {
		return getPersistence().findByG_E(groupId, engineKey);
	}

	/**
	* Returns the commerce payment method where groupId = &#63; and engineKey = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param engineKey the engine key
	* @return the matching commerce payment method, or <code>null</code> if a matching commerce payment method could not be found
	*/
	public static CommercePaymentMethod fetchByG_E(long groupId,
		java.lang.String engineKey) {
		return getPersistence().fetchByG_E(groupId, engineKey);
	}

	/**
	* Returns the commerce payment method where groupId = &#63; and engineKey = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param engineKey the engine key
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching commerce payment method, or <code>null</code> if a matching commerce payment method could not be found
	*/
	public static CommercePaymentMethod fetchByG_E(long groupId,
		java.lang.String engineKey, boolean retrieveFromCache) {
		return getPersistence().fetchByG_E(groupId, engineKey, retrieveFromCache);
	}

	/**
	* Removes the commerce payment method where groupId = &#63; and engineKey = &#63; from the database.
	*
	* @param groupId the group ID
	* @param engineKey the engine key
	* @return the commerce payment method that was removed
	*/
	public static CommercePaymentMethod removeByG_E(long groupId,
		java.lang.String engineKey)
		throws com.liferay.commerce.exception.NoSuchPaymentMethodException {
		return getPersistence().removeByG_E(groupId, engineKey);
	}

	/**
	* Returns the number of commerce payment methods where groupId = &#63; and engineKey = &#63;.
	*
	* @param groupId the group ID
	* @param engineKey the engine key
	* @return the number of matching commerce payment methods
	*/
	public static int countByG_E(long groupId, java.lang.String engineKey) {
		return getPersistence().countByG_E(groupId, engineKey);
	}

	/**
	* Returns all the commerce payment methods where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @return the matching commerce payment methods
	*/
	public static List<CommercePaymentMethod> findByG_A(long groupId,
		boolean active) {
		return getPersistence().findByG_A(groupId, active);
	}

	/**
	* Returns a range of all the commerce payment methods where groupId = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePaymentMethodModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param active the active
	* @param start the lower bound of the range of commerce payment methods
	* @param end the upper bound of the range of commerce payment methods (not inclusive)
	* @return the range of matching commerce payment methods
	*/
	public static List<CommercePaymentMethod> findByG_A(long groupId,
		boolean active, int start, int end) {
		return getPersistence().findByG_A(groupId, active, start, end);
	}

	/**
	* Returns an ordered range of all the commerce payment methods where groupId = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePaymentMethodModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param active the active
	* @param start the lower bound of the range of commerce payment methods
	* @param end the upper bound of the range of commerce payment methods (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce payment methods
	*/
	public static List<CommercePaymentMethod> findByG_A(long groupId,
		boolean active, int start, int end,
		OrderByComparator<CommercePaymentMethod> orderByComparator) {
		return getPersistence()
				   .findByG_A(groupId, active, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce payment methods where groupId = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePaymentMethodModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param active the active
	* @param start the lower bound of the range of commerce payment methods
	* @param end the upper bound of the range of commerce payment methods (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce payment methods
	*/
	public static List<CommercePaymentMethod> findByG_A(long groupId,
		boolean active, int start, int end,
		OrderByComparator<CommercePaymentMethod> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_A(groupId, active, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first commerce payment method in the ordered set where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce payment method
	* @throws NoSuchPaymentMethodException if a matching commerce payment method could not be found
	*/
	public static CommercePaymentMethod findByG_A_First(long groupId,
		boolean active,
		OrderByComparator<CommercePaymentMethod> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchPaymentMethodException {
		return getPersistence()
				   .findByG_A_First(groupId, active, orderByComparator);
	}

	/**
	* Returns the first commerce payment method in the ordered set where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce payment method, or <code>null</code> if a matching commerce payment method could not be found
	*/
	public static CommercePaymentMethod fetchByG_A_First(long groupId,
		boolean active,
		OrderByComparator<CommercePaymentMethod> orderByComparator) {
		return getPersistence()
				   .fetchByG_A_First(groupId, active, orderByComparator);
	}

	/**
	* Returns the last commerce payment method in the ordered set where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce payment method
	* @throws NoSuchPaymentMethodException if a matching commerce payment method could not be found
	*/
	public static CommercePaymentMethod findByG_A_Last(long groupId,
		boolean active,
		OrderByComparator<CommercePaymentMethod> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchPaymentMethodException {
		return getPersistence()
				   .findByG_A_Last(groupId, active, orderByComparator);
	}

	/**
	* Returns the last commerce payment method in the ordered set where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce payment method, or <code>null</code> if a matching commerce payment method could not be found
	*/
	public static CommercePaymentMethod fetchByG_A_Last(long groupId,
		boolean active,
		OrderByComparator<CommercePaymentMethod> orderByComparator) {
		return getPersistence()
				   .fetchByG_A_Last(groupId, active, orderByComparator);
	}

	/**
	* Returns the commerce payment methods before and after the current commerce payment method in the ordered set where groupId = &#63; and active = &#63;.
	*
	* @param commercePaymentMethodId the primary key of the current commerce payment method
	* @param groupId the group ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce payment method
	* @throws NoSuchPaymentMethodException if a commerce payment method with the primary key could not be found
	*/
	public static CommercePaymentMethod[] findByG_A_PrevAndNext(
		long commercePaymentMethodId, long groupId, boolean active,
		OrderByComparator<CommercePaymentMethod> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchPaymentMethodException {
		return getPersistence()
				   .findByG_A_PrevAndNext(commercePaymentMethodId, groupId,
			active, orderByComparator);
	}

	/**
	* Removes all the commerce payment methods where groupId = &#63; and active = &#63; from the database.
	*
	* @param groupId the group ID
	* @param active the active
	*/
	public static void removeByG_A(long groupId, boolean active) {
		getPersistence().removeByG_A(groupId, active);
	}

	/**
	* Returns the number of commerce payment methods where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @return the number of matching commerce payment methods
	*/
	public static int countByG_A(long groupId, boolean active) {
		return getPersistence().countByG_A(groupId, active);
	}

	/**
	* Caches the commerce payment method in the entity cache if it is enabled.
	*
	* @param commercePaymentMethod the commerce payment method
	*/
	public static void cacheResult(CommercePaymentMethod commercePaymentMethod) {
		getPersistence().cacheResult(commercePaymentMethod);
	}

	/**
	* Caches the commerce payment methods in the entity cache if it is enabled.
	*
	* @param commercePaymentMethods the commerce payment methods
	*/
	public static void cacheResult(
		List<CommercePaymentMethod> commercePaymentMethods) {
		getPersistence().cacheResult(commercePaymentMethods);
	}

	/**
	* Creates a new commerce payment method with the primary key. Does not add the commerce payment method to the database.
	*
	* @param commercePaymentMethodId the primary key for the new commerce payment method
	* @return the new commerce payment method
	*/
	public static CommercePaymentMethod create(long commercePaymentMethodId) {
		return getPersistence().create(commercePaymentMethodId);
	}

	/**
	* Removes the commerce payment method with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commercePaymentMethodId the primary key of the commerce payment method
	* @return the commerce payment method that was removed
	* @throws NoSuchPaymentMethodException if a commerce payment method with the primary key could not be found
	*/
	public static CommercePaymentMethod remove(long commercePaymentMethodId)
		throws com.liferay.commerce.exception.NoSuchPaymentMethodException {
		return getPersistence().remove(commercePaymentMethodId);
	}

	public static CommercePaymentMethod updateImpl(
		CommercePaymentMethod commercePaymentMethod) {
		return getPersistence().updateImpl(commercePaymentMethod);
	}

	/**
	* Returns the commerce payment method with the primary key or throws a {@link NoSuchPaymentMethodException} if it could not be found.
	*
	* @param commercePaymentMethodId the primary key of the commerce payment method
	* @return the commerce payment method
	* @throws NoSuchPaymentMethodException if a commerce payment method with the primary key could not be found
	*/
	public static CommercePaymentMethod findByPrimaryKey(
		long commercePaymentMethodId)
		throws com.liferay.commerce.exception.NoSuchPaymentMethodException {
		return getPersistence().findByPrimaryKey(commercePaymentMethodId);
	}

	/**
	* Returns the commerce payment method with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param commercePaymentMethodId the primary key of the commerce payment method
	* @return the commerce payment method, or <code>null</code> if a commerce payment method with the primary key could not be found
	*/
	public static CommercePaymentMethod fetchByPrimaryKey(
		long commercePaymentMethodId) {
		return getPersistence().fetchByPrimaryKey(commercePaymentMethodId);
	}

	public static java.util.Map<java.io.Serializable, CommercePaymentMethod> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the commerce payment methods.
	*
	* @return the commerce payment methods
	*/
	public static List<CommercePaymentMethod> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the commerce payment methods.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePaymentMethodModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce payment methods
	* @param end the upper bound of the range of commerce payment methods (not inclusive)
	* @return the range of commerce payment methods
	*/
	public static List<CommercePaymentMethod> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the commerce payment methods.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePaymentMethodModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce payment methods
	* @param end the upper bound of the range of commerce payment methods (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of commerce payment methods
	*/
	public static List<CommercePaymentMethod> findAll(int start, int end,
		OrderByComparator<CommercePaymentMethod> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce payment methods.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePaymentMethodModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce payment methods
	* @param end the upper bound of the range of commerce payment methods (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of commerce payment methods
	*/
	public static List<CommercePaymentMethod> findAll(int start, int end,
		OrderByComparator<CommercePaymentMethod> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the commerce payment methods from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of commerce payment methods.
	*
	* @return the number of commerce payment methods
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<java.lang.String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static CommercePaymentMethodPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommercePaymentMethodPersistence, CommercePaymentMethodPersistence> _serviceTracker =
		ServiceTrackerFactory.open(CommercePaymentMethodPersistence.class);
}