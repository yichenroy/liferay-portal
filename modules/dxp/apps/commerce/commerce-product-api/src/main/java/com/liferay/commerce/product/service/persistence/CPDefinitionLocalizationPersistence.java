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

package com.liferay.commerce.product.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.product.exception.NoSuchCPDefinitionLocalizationException;
import com.liferay.commerce.product.model.CPDefinitionLocalization;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the cp definition localization service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see com.liferay.commerce.product.service.persistence.impl.CPDefinitionLocalizationPersistenceImpl
 * @see CPDefinitionLocalizationUtil
 * @generated
 */
@ProviderType
public interface CPDefinitionLocalizationPersistence extends BasePersistence<CPDefinitionLocalization> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CPDefinitionLocalizationUtil} to access the cp definition localization persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the cp definition localizations where CPDefinitionId = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @return the matching cp definition localizations
	*/
	public java.util.List<CPDefinitionLocalization> findByCPDefinitionId(
		long CPDefinitionId);

	/**
	* Returns a range of all the cp definition localizations where CPDefinitionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPDefinitionId the cp definition ID
	* @param start the lower bound of the range of cp definition localizations
	* @param end the upper bound of the range of cp definition localizations (not inclusive)
	* @return the range of matching cp definition localizations
	*/
	public java.util.List<CPDefinitionLocalization> findByCPDefinitionId(
		long CPDefinitionId, int start, int end);

	/**
	* Returns an ordered range of all the cp definition localizations where CPDefinitionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPDefinitionId the cp definition ID
	* @param start the lower bound of the range of cp definition localizations
	* @param end the upper bound of the range of cp definition localizations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp definition localizations
	*/
	public java.util.List<CPDefinitionLocalization> findByCPDefinitionId(
		long CPDefinitionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionLocalization> orderByComparator);

	/**
	* Returns an ordered range of all the cp definition localizations where CPDefinitionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPDefinitionId the cp definition ID
	* @param start the lower bound of the range of cp definition localizations
	* @param end the upper bound of the range of cp definition localizations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp definition localizations
	*/
	public java.util.List<CPDefinitionLocalization> findByCPDefinitionId(
		long CPDefinitionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionLocalization> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first cp definition localization in the ordered set where CPDefinitionId = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition localization
	* @throws NoSuchCPDefinitionLocalizationException if a matching cp definition localization could not be found
	*/
	public CPDefinitionLocalization findByCPDefinitionId_First(
		long CPDefinitionId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionLocalization> orderByComparator)
		throws NoSuchCPDefinitionLocalizationException;

	/**
	* Returns the first cp definition localization in the ordered set where CPDefinitionId = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition localization, or <code>null</code> if a matching cp definition localization could not be found
	*/
	public CPDefinitionLocalization fetchByCPDefinitionId_First(
		long CPDefinitionId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionLocalization> orderByComparator);

	/**
	* Returns the last cp definition localization in the ordered set where CPDefinitionId = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition localization
	* @throws NoSuchCPDefinitionLocalizationException if a matching cp definition localization could not be found
	*/
	public CPDefinitionLocalization findByCPDefinitionId_Last(
		long CPDefinitionId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionLocalization> orderByComparator)
		throws NoSuchCPDefinitionLocalizationException;

	/**
	* Returns the last cp definition localization in the ordered set where CPDefinitionId = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition localization, or <code>null</code> if a matching cp definition localization could not be found
	*/
	public CPDefinitionLocalization fetchByCPDefinitionId_Last(
		long CPDefinitionId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionLocalization> orderByComparator);

	/**
	* Returns the cp definition localizations before and after the current cp definition localization in the ordered set where CPDefinitionId = &#63;.
	*
	* @param cpDefinitionLocalizationId the primary key of the current cp definition localization
	* @param CPDefinitionId the cp definition ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp definition localization
	* @throws NoSuchCPDefinitionLocalizationException if a cp definition localization with the primary key could not be found
	*/
	public CPDefinitionLocalization[] findByCPDefinitionId_PrevAndNext(
		long cpDefinitionLocalizationId, long CPDefinitionId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionLocalization> orderByComparator)
		throws NoSuchCPDefinitionLocalizationException;

	/**
	* Removes all the cp definition localizations where CPDefinitionId = &#63; from the database.
	*
	* @param CPDefinitionId the cp definition ID
	*/
	public void removeByCPDefinitionId(long CPDefinitionId);

	/**
	* Returns the number of cp definition localizations where CPDefinitionId = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @return the number of matching cp definition localizations
	*/
	public int countByCPDefinitionId(long CPDefinitionId);

	/**
	* Returns the cp definition localization where CPDefinitionId = &#63; and languageId = &#63; or throws a {@link NoSuchCPDefinitionLocalizationException} if it could not be found.
	*
	* @param CPDefinitionId the cp definition ID
	* @param languageId the language ID
	* @return the matching cp definition localization
	* @throws NoSuchCPDefinitionLocalizationException if a matching cp definition localization could not be found
	*/
	public CPDefinitionLocalization findByCPDefinitionId_LanguageId(
		long CPDefinitionId, String languageId)
		throws NoSuchCPDefinitionLocalizationException;

	/**
	* Returns the cp definition localization where CPDefinitionId = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param CPDefinitionId the cp definition ID
	* @param languageId the language ID
	* @return the matching cp definition localization, or <code>null</code> if a matching cp definition localization could not be found
	*/
	public CPDefinitionLocalization fetchByCPDefinitionId_LanguageId(
		long CPDefinitionId, String languageId);

	/**
	* Returns the cp definition localization where CPDefinitionId = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param CPDefinitionId the cp definition ID
	* @param languageId the language ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching cp definition localization, or <code>null</code> if a matching cp definition localization could not be found
	*/
	public CPDefinitionLocalization fetchByCPDefinitionId_LanguageId(
		long CPDefinitionId, String languageId, boolean retrieveFromCache);

	/**
	* Removes the cp definition localization where CPDefinitionId = &#63; and languageId = &#63; from the database.
	*
	* @param CPDefinitionId the cp definition ID
	* @param languageId the language ID
	* @return the cp definition localization that was removed
	*/
	public CPDefinitionLocalization removeByCPDefinitionId_LanguageId(
		long CPDefinitionId, String languageId)
		throws NoSuchCPDefinitionLocalizationException;

	/**
	* Returns the number of cp definition localizations where CPDefinitionId = &#63; and languageId = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @param languageId the language ID
	* @return the number of matching cp definition localizations
	*/
	public int countByCPDefinitionId_LanguageId(long CPDefinitionId,
		String languageId);

	/**
	* Caches the cp definition localization in the entity cache if it is enabled.
	*
	* @param cpDefinitionLocalization the cp definition localization
	*/
	public void cacheResult(CPDefinitionLocalization cpDefinitionLocalization);

	/**
	* Caches the cp definition localizations in the entity cache if it is enabled.
	*
	* @param cpDefinitionLocalizations the cp definition localizations
	*/
	public void cacheResult(
		java.util.List<CPDefinitionLocalization> cpDefinitionLocalizations);

	/**
	* Creates a new cp definition localization with the primary key. Does not add the cp definition localization to the database.
	*
	* @param cpDefinitionLocalizationId the primary key for the new cp definition localization
	* @return the new cp definition localization
	*/
	public CPDefinitionLocalization create(long cpDefinitionLocalizationId);

	/**
	* Removes the cp definition localization with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param cpDefinitionLocalizationId the primary key of the cp definition localization
	* @return the cp definition localization that was removed
	* @throws NoSuchCPDefinitionLocalizationException if a cp definition localization with the primary key could not be found
	*/
	public CPDefinitionLocalization remove(long cpDefinitionLocalizationId)
		throws NoSuchCPDefinitionLocalizationException;

	public CPDefinitionLocalization updateImpl(
		CPDefinitionLocalization cpDefinitionLocalization);

	/**
	* Returns the cp definition localization with the primary key or throws a {@link NoSuchCPDefinitionLocalizationException} if it could not be found.
	*
	* @param cpDefinitionLocalizationId the primary key of the cp definition localization
	* @return the cp definition localization
	* @throws NoSuchCPDefinitionLocalizationException if a cp definition localization with the primary key could not be found
	*/
	public CPDefinitionLocalization findByPrimaryKey(
		long cpDefinitionLocalizationId)
		throws NoSuchCPDefinitionLocalizationException;

	/**
	* Returns the cp definition localization with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param cpDefinitionLocalizationId the primary key of the cp definition localization
	* @return the cp definition localization, or <code>null</code> if a cp definition localization with the primary key could not be found
	*/
	public CPDefinitionLocalization fetchByPrimaryKey(
		long cpDefinitionLocalizationId);

	@Override
	public java.util.Map<java.io.Serializable, CPDefinitionLocalization> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the cp definition localizations.
	*
	* @return the cp definition localizations
	*/
	public java.util.List<CPDefinitionLocalization> findAll();

	/**
	* Returns a range of all the cp definition localizations.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp definition localizations
	* @param end the upper bound of the range of cp definition localizations (not inclusive)
	* @return the range of cp definition localizations
	*/
	public java.util.List<CPDefinitionLocalization> findAll(int start, int end);

	/**
	* Returns an ordered range of all the cp definition localizations.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp definition localizations
	* @param end the upper bound of the range of cp definition localizations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of cp definition localizations
	*/
	public java.util.List<CPDefinitionLocalization> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionLocalization> orderByComparator);

	/**
	* Returns an ordered range of all the cp definition localizations.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp definition localizations
	* @param end the upper bound of the range of cp definition localizations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of cp definition localizations
	*/
	public java.util.List<CPDefinitionLocalization> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionLocalization> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the cp definition localizations from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of cp definition localizations.
	*
	* @return the number of cp definition localizations
	*/
	public int countAll();
}