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

import com.liferay.portal.cache.multiple.configuration.PortalCacheClusterConfiguration;
import com.liferay.portal.cache.multiple.internal.PortalCacheClusterEvent;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.cluster.Priority;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * @author Shuyang Zhou
 */
@Component(
	configurationPid = "com.liferay.portal.cache.cluster.configuration.PortalCacheClusterConfiguration",
	enabled = false, immediate = true, service = PortalCacheClusterLink.class
)
public class PortalCacheClusterLink {

	public long getSubmittedEventNumber() {
		return _portalCacheClusterChannelSelector.getSelectedNumber();
	}

	public void sendEvent(PortalCacheClusterEvent portalCacheClusterEvent) {
		PortalCacheClusterChannel portalCacheClusterChannel =
			_portalCacheClusterChannelSelector.select(
				_portalCacheClusterChannels, portalCacheClusterEvent);

		portalCacheClusterChannel.sendEvent(portalCacheClusterEvent);
	}

	@Reference(unbind = "-")
	public void setPortalCacheClusterChannelFactory(
		PortalCacheClusterChannelFactory portalCacheClusterChannelFactory) {

		_portalCacheClusterChannelFactory = portalCacheClusterChannelFactory;
	}

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policy = ReferencePolicy.DYNAMIC
	)
	public void setPortalCacheClusterChannelSelector(
		PortalCacheClusterChannelSelector portalCacheClusterChannelSelector) {

		_portalCacheClusterChannelSelector = portalCacheClusterChannelSelector;
	}

	@Activate
	@Modified
	protected void activate(ComponentContext componentContext) {
		PortalCacheClusterConfiguration portalCacheClusterConfiguration =
			ConfigurableUtil.createConfigurable(
				PortalCacheClusterConfiguration.class,
				componentContext.getProperties());

		Priority[] priorities = portalCacheClusterConfiguration.priorities();

		_portalCacheClusterChannels = new ArrayList<>(priorities.length);

		for (Priority priority : priorities) {
			PortalCacheClusterChannel portalCacheClusterChannel =
				_portalCacheClusterChannelFactory.
					createPortalCacheClusterChannel(priority);

			_portalCacheClusterChannels.add(portalCacheClusterChannel);
		}

		if (_portalCacheClusterChannelSelector == null) {
			_portalCacheClusterChannelSelector =
				new UniformPortalCacheClusterChannelSelector();
		}
	}

	@Deactivate
	protected void deactivate() {
		for (PortalCacheClusterChannel portalCacheClusterChannel :
				_portalCacheClusterChannels) {

			portalCacheClusterChannel.destroy();
		}

		_portalCacheClusterChannels.clear();

		_portalCacheClusterChannels = null;
	}

	protected void unsetPortalCacheClusterChannelSelector(
		PortalCacheClusterChannelSelector portalCacheClusterChannelSelector) {
	}

	private PortalCacheClusterChannelFactory _portalCacheClusterChannelFactory;
	private volatile List<PortalCacheClusterChannel>
		_portalCacheClusterChannels;
	private PortalCacheClusterChannelSelector
		_portalCacheClusterChannelSelector;

}