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

package com.liferay.osb.koroneiki.root.audit.model;

import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.osb.koroneiki.root.audit.constants.AuditEntryConstants;
import com.liferay.osb.koroneiki.root.model.AuditEntry;
import com.liferay.osb.koroneiki.root.service.AuditEntryLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.instances.service.PortalInstancesLocalService;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.UserLocalService;

import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Kyle Bischof
 */
public abstract class BaseAuditModelListener<T extends BaseModel<T>>
	extends BaseModelListener<T> {

	@Override
	public void onAfterCreate(T model) throws ModelListenerException {
		try {
			Map<String, Object> attributes = model.getModelAttributes();

			for (Map.Entry<String, Object> entry : attributes.entrySet()) {
				String field = entry.getKey();

				auditEntryLocalService.addAuditEntry(
					getUserId(), getClassNameId(model), getClassPK(model),
					_auditSetId.get(), getFieldClassNameId(model),
					getFieldClassPK(model), AuditEntryConstants.ACTION_ADD,
					field, StringPool.BLANK, StringPool.BLANK, StringPool.BLANK,
					String.valueOf(entry.getValue()), StringPool.BLANK);
			}
		}
		catch (PortalException pe) {
			throw new ModelListenerException(pe);
		}
	}

	@Override
	public void onBeforeRemove(T model) throws ModelListenerException {
		try {
			Map<String, Object> attributes = model.getModelAttributes();

			for (Map.Entry<String, Object> entry : attributes.entrySet()) {
				String field = entry.getKey();

				auditEntryLocalService.addAuditEntry(
					getUserId(), getClassNameId(model), getClassPK(model),
					_auditSetId.get(), getFieldClassNameId(model),
					getFieldClassPK(model), AuditEntryConstants.ACTION_DELETE,
					field, StringPool.BLANK, String.valueOf(entry.getValue()),
					StringPool.BLANK, StringPool.BLANK, StringPool.BLANK);
			}
		}
		catch (PortalException pe) {
			throw new ModelListenerException(pe);
		}
	}

	@Override
	public void onBeforeUpdate(T model) throws ModelListenerException {
		try {
			long classPK = (Long)model.getPrimaryKeyObj();

			T oldModel = getModel(classPK);

			Map<String, Object> oldAttributes = oldModel.getModelAttributes();

			Map<String, Object> attributes = model.getModelAttributes();

			for (Map.Entry<String, Object> entry : attributes.entrySet()) {
				String field = entry.getKey();

				Object oldValue = oldAttributes.get(field);

				Object value = entry.getValue();

				if (!Objects.equals(oldValue, value)) {
					auditEntryLocalService.addAuditEntry(
						getUserId(), getClassNameId(model), getClassPK(model),
						_auditSetId.get(), getFieldClassNameId(model),
						getFieldClassPK(model),
						AuditEntryConstants.ACTION_UPDATE, field,
						StringPool.BLANK, String.valueOf(oldValue),
						StringPool.BLANK, String.valueOf(value),
						StringPool.BLANK);
				}
			}
		}
		catch (PortalException pe) {
			throw new ModelListenerException(pe);
		}
	}

	protected long getClassNameId(T model) {
		return classNameLocalService.getClassNameId(model.getClass());
	}

	protected long getClassPK(T model) {
		return (Long)model.getPrimaryKeyObj();
	}

	protected long getFieldClassNameId(T model) {
		return classNameLocalService.getClassNameId(model.getClass());
	}

	protected long getFieldClassPK(T model) {
		return (Long)model.getPrimaryKeyObj();
	}

	protected <T> T getModel(long classPK) throws PortalException {
		return null;
	}

	protected long getUserId() throws PortalException {
		long userId = PrincipalThreadLocal.getUserId();

		if (userId <= 0) {
			long companyId = portalInstancesLocalService.getDefaultCompanyId();

			userId = userLocalService.getDefaultUserId(companyId);
		}

		return userId;
	}

	@Reference
	protected AuditEntryLocalService auditEntryLocalService;

	@Reference
	protected ClassNameLocalService classNameLocalService;

	@Reference
	protected PortalInstancesLocalService portalInstancesLocalService;

	@Reference
	protected UserLocalService userLocalService;

	private static final ThreadLocal<Long> _auditSetId =
		new ThreadLocal<Long>() {

			@Override
			public Long initialValue() {
				return CounterLocalServiceUtil.increment(
					AuditEntry.class.getName());
			}

		};

}