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

package com.liferay.osb.koroneiki.root.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link AuditEntryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see AuditEntryLocalService
 * @generated
 */
public class AuditEntryLocalServiceWrapper
	implements AuditEntryLocalService, ServiceWrapper<AuditEntryLocalService> {

	public AuditEntryLocalServiceWrapper(
		AuditEntryLocalService auditEntryLocalService) {

		_auditEntryLocalService = auditEntryLocalService;
	}

	/**
	 * Adds the audit entry to the database. Also notifies the appropriate model listeners.
	 *
	 * @param auditEntry the audit entry
	 * @return the audit entry that was added
	 */
	@Override
	public com.liferay.osb.koroneiki.root.model.AuditEntry addAuditEntry(
		com.liferay.osb.koroneiki.root.model.AuditEntry auditEntry) {

		return _auditEntryLocalService.addAuditEntry(auditEntry);
	}

	@Override
	public com.liferay.osb.koroneiki.root.model.AuditEntry addAuditEntry(
			long userId, long classNameId, long classPK, long auditSetId,
			long fieldClassNameId, long fieldClassPK, String action,
			String field, String oldLabel, String oldValue, String newLabel,
			String newValue, String description)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _auditEntryLocalService.addAuditEntry(
			userId, classNameId, classPK, auditSetId, fieldClassNameId,
			fieldClassPK, action, field, oldLabel, oldValue, newLabel, newValue,
			description);
	}

	/**
	 * Creates a new audit entry with the primary key. Does not add the audit entry to the database.
	 *
	 * @param auditEntryId the primary key for the new audit entry
	 * @return the new audit entry
	 */
	@Override
	public com.liferay.osb.koroneiki.root.model.AuditEntry createAuditEntry(
		long auditEntryId) {

		return _auditEntryLocalService.createAuditEntry(auditEntryId);
	}

	/**
	 * Deletes the audit entry from the database. Also notifies the appropriate model listeners.
	 *
	 * @param auditEntry the audit entry
	 * @return the audit entry that was removed
	 */
	@Override
	public com.liferay.osb.koroneiki.root.model.AuditEntry deleteAuditEntry(
		com.liferay.osb.koroneiki.root.model.AuditEntry auditEntry) {

		return _auditEntryLocalService.deleteAuditEntry(auditEntry);
	}

	/**
	 * Deletes the audit entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param auditEntryId the primary key of the audit entry
	 * @return the audit entry that was removed
	 * @throws PortalException if a audit entry with the primary key could not be found
	 */
	@Override
	public com.liferay.osb.koroneiki.root.model.AuditEntry deleteAuditEntry(
			long auditEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _auditEntryLocalService.deleteAuditEntry(auditEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _auditEntryLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _auditEntryLocalService.dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _auditEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.osb.koroneiki.root.model.impl.AuditEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return _auditEntryLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.osb.koroneiki.root.model.impl.AuditEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return _auditEntryLocalService.dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _auditEntryLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return _auditEntryLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.osb.koroneiki.root.model.AuditEntry fetchAuditEntry(
		long auditEntryId) {

		return _auditEntryLocalService.fetchAuditEntry(auditEntryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _auditEntryLocalService.getActionableDynamicQuery();
	}

	/**
	 * Returns a range of all the audit entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.osb.koroneiki.root.model.impl.AuditEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of audit entries
	 * @param end the upper bound of the range of audit entries (not inclusive)
	 * @return the range of audit entries
	 */
	@Override
	public java.util.List<com.liferay.osb.koroneiki.root.model.AuditEntry>
		getAuditEntries(int start, int end) {

		return _auditEntryLocalService.getAuditEntries(start, end);
	}

	@Override
	public java.util.List<com.liferay.osb.koroneiki.root.model.AuditEntry>
		getAuditEntries(long classNameId, long classPK, int start, int end) {

		return _auditEntryLocalService.getAuditEntries(
			classNameId, classPK, start, end);
	}

	/**
	 * Returns the number of audit entries.
	 *
	 * @return the number of audit entries
	 */
	@Override
	public int getAuditEntriesCount() {
		return _auditEntryLocalService.getAuditEntriesCount();
	}

	@Override
	public int getAuditEntriesCount(long classNameId, long classPK) {
		return _auditEntryLocalService.getAuditEntriesCount(
			classNameId, classPK);
	}

	/**
	 * Returns the audit entry with the primary key.
	 *
	 * @param auditEntryId the primary key of the audit entry
	 * @return the audit entry
	 * @throws PortalException if a audit entry with the primary key could not be found
	 */
	@Override
	public com.liferay.osb.koroneiki.root.model.AuditEntry getAuditEntry(
			long auditEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _auditEntryLocalService.getAuditEntry(auditEntryId);
	}

	@Override
	public com.liferay.osb.koroneiki.root.model.AuditEntry getAuditEntry(
			String auditEntryKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _auditEntryLocalService.getAuditEntry(auditEntryKey);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _auditEntryLocalService.getIndexableActionableDynamicQuery();
	}

	@Override
	public long getNextAuditSetId(long classNameId, long classPK) {
		return _auditEntryLocalService.getNextAuditSetId(classNameId, classPK);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _auditEntryLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _auditEntryLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the audit entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param auditEntry the audit entry
	 * @return the audit entry that was updated
	 */
	@Override
	public com.liferay.osb.koroneiki.root.model.AuditEntry updateAuditEntry(
		com.liferay.osb.koroneiki.root.model.AuditEntry auditEntry) {

		return _auditEntryLocalService.updateAuditEntry(auditEntry);
	}

	@Override
	public AuditEntryLocalService getWrappedService() {
		return _auditEntryLocalService;
	}

	@Override
	public void setWrappedService(
		AuditEntryLocalService auditEntryLocalService) {

		_auditEntryLocalService = auditEntryLocalService;
	}

	private AuditEntryLocalService _auditEntryLocalService;

}