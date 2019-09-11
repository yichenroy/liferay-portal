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

package com.liferay.osb.koroneiki.xylem.distributed.messaging.internal.model.listener;

import com.liferay.osb.distributed.messaging.Message;
import com.liferay.osb.koroneiki.xylem.distributed.messaging.internal.factory.MessageFactory;
import com.liferay.osb.koroneiki.xylem.distributed.messaging.model.listener.PublishingTasksThreadLocal;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.BaseModelListener;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Amos Fong
 */
public abstract class BaseXylemModelListener<T extends BaseModel<T>>
	extends BaseModelListener<T> {

	public abstract Message createMessage(T model) throws Exception;

	@Override
	public void onAfterCreate(final T model) throws ModelListenerException {
		try {
			publishingTasksThreadLocal.addPublishingTask(
				getCreateKey(model), getCreateTopic(model),
				() -> createMessage(model));
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new ModelListenerException(e);
		}
	}

	@Override
	public void onAfterRemove(final T model) throws ModelListenerException {
		try {
			publishingTasksThreadLocal.addPublishingTask(
				getRemoveKey(model), getRemoveTopic(model),
				() -> createMessage(model));
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new ModelListenerException(e);
		}
	}

	@Override
	public void onAfterUpdate(final T model) throws ModelListenerException {
		try {
			publishingTasksThreadLocal.addPublishingTask(
				getUpdateKey(model), getUpdateTopic(model),
				() -> createMessage(model));
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new ModelListenerException(e);
		}
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_createTopic = (String)properties.get("create.topic");
		_removeTopic = (String)properties.get("remove.topic");
		_updateTopic = (String)properties.get("update.topic");
	}

	protected String getCreateKey(T model) throws PortalException {
		return _updateTopic;
	}

	protected String getCreateTopic(T model) throws PortalException {
		return _createTopic;
	}

	protected String getRemoveKey(T model) throws PortalException {
		return _removeTopic;
	}

	protected String getRemoveTopic(T model) throws PortalException {
		return _removeTopic;
	}

	protected String getUpdateKey(T model) throws PortalException {
		return _updateTopic;
	}

	protected String getUpdateTopic(T model) throws PortalException {
		return _updateTopic;
	}

	@Reference
	protected MessageFactory messageFactory;

	@Reference
	protected PublishingTasksThreadLocal publishingTasksThreadLocal;

	private static final Log _log = LogFactoryUtil.getLog(
		BaseXylemModelListener.class);

	private String _createTopic;
	private String _removeTopic;
	private String _updateTopic;

}