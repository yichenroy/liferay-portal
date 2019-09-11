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

package com.liferay.osb.koroneiki.xylem.distributed.messaging.model.listener;

import com.liferay.osb.distributed.messaging.Message;
import com.liferay.osb.distributed.messaging.publishing.MessagePublisher;
import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackUtil;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Amos Fong
 */
@Component(immediate = true, service = PublishingTasksThreadLocal.class)
public class PublishingTasksThreadLocal {

	public static boolean isImportInProcess() {
		return _importInProcess.get();
	}

	public static void setImportInProcess(boolean importInProcess) {
		_importInProcess.set(importInProcess);
	}

	public void addPublishingTask(
		String key, String topic, Callable<Message> callable) {

		if (isImportInProcess()) {
			return;
		}

		Map<String, PublishingTask> publishingTasksMap =
			_publishingTasksMap.get();

		PublishingTask publishingTask = publishingTasksMap.get(key);

		if (publishingTask == null) {
			publishingTasksMap.put(key, new PublishingTask(topic, callable));
		}
		else {
			publishingTask.setCallable(callable);
		}
	}

	public void flush() throws Exception {
		Map<String, PublishingTask> publishingTasksMap =
			_publishingTasksMap.get();

		for (PublishingTask publishingTask : publishingTasksMap.values()) {
			Message message = publishingTask.createMessage();

			if (message != null) {
				_messagePublisher.publish(
					publishingTask.getTopic(), publishingTask.createMessage());
			}
		}
	}

	private static final ThreadLocal<Boolean> _importInProcess =
		new CentralizedThreadLocal<>(
			PublishingTasksThreadLocal.class + "._importInProcess",
			() -> Boolean.FALSE);

	@Reference
	private MessagePublisher _messagePublisher;

	private final ThreadLocal<Map<String, PublishingTask>> _publishingTasksMap =
		new CentralizedThreadLocal<>(
			PublishingTasksThreadLocal.class + "._publishingTasksMap",
			() -> {
				TransactionCommitCallbackUtil.registerCallback(
					() -> {
						flush();

						return null;
					});

				return new LinkedHashMap<>();
			});

	private class PublishingTask {

		public PublishingTask(String topic, Callable<Message> callable) {
			_topic = topic;
			_callable = callable;
		}

		public Message createMessage() throws Exception {
			return _callable.call();
		}

		public String getTopic() {
			return _topic;
		}

		public void setCallable(Callable<Message> callable) {
			_callable = callable;
		}

		private Callable<Message> _callable;
		private final String _topic;

	}

}