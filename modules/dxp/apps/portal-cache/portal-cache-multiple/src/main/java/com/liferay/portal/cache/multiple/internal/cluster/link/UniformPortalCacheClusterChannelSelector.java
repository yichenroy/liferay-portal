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

package com.liferay.portal.cache.multiple.internal.cluster.link;

import com.liferay.portal.cache.multiple.internal.PortalCacheClusterEvent;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Shuyang Zhou
 */
public class UniformPortalCacheClusterChannelSelector
	implements PortalCacheClusterChannelSelector {

	@Override
	public long getSelectedNumber() {
		return _eventCounter.get();
	}

	@Override
	public PortalCacheClusterChannel select(
		List<PortalCacheClusterChannel> portalCacheClusterChannels,
		PortalCacheClusterEvent portalCacheClusterEvent) {

		long count = _eventCounter.getAndIncrement();
		int size = portalCacheClusterChannels.size();

		return portalCacheClusterChannels.get((int)(count % size));
	}

	private final AtomicLong _eventCounter = new AtomicLong(0);

}