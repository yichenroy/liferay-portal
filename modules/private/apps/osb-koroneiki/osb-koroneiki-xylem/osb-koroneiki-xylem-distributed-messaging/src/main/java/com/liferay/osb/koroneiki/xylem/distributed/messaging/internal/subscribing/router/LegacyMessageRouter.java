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

package com.liferay.osb.koroneiki.xylem.distributed.messaging.internal.subscribing.router;

import com.liferay.osb.distributed.messaging.subscribing.router.BaseMessageRouter;
import com.liferay.osb.distributed.messaging.subscribing.router.MessageRouter;
import com.liferay.osb.koroneiki.xylem.distributed.messaging.internal.subscribing.UserUpdateMessageSubscriber;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Amos Fong
 */
@Component(immediate = true, service = MessageRouter.class)
public class LegacyMessageRouter extends BaseMessageRouter {

	@Reference(unbind = "-")
	protected void setUserUpdateMessageSubscriber(
		UserUpdateMessageSubscriber userUpdateMessageSubscriber,
		Map<String, Object> properties) {

		addRoute(userUpdateMessageSubscriber, properties);
	}

}