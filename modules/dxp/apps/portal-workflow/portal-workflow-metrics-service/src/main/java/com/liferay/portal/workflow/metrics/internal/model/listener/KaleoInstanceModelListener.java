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

package com.liferay.portal.workflow.metrics.internal.model.listener;

import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.workflow.kaleo.model.KaleoInstance;
import com.liferay.portal.workflow.metrics.internal.search.index.IndexExecutor;
import com.liferay.portal.workflow.metrics.internal.search.index.InstanceWorkflowMetricsIndexer;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Inácio Nery
 */
@Component(immediate = true, service = ModelListener.class)
public class KaleoInstanceModelListener
	extends BaseModelListener<KaleoInstance> {

	@Override
	public void onAfterCreate(KaleoInstance kaleoInstance) {
		_indexExecutor.execute(
			() -> _instanceWorkflowMetricsIndexer.addDocument(kaleoInstance));
	}

	@Override
	public void onAfterRemove(KaleoInstance kaleoInstance) {
		_indexExecutor.execute(
			() -> _instanceWorkflowMetricsIndexer.deleteDocument(
				kaleoInstance));
	}

	@Override
	public void onAfterUpdate(KaleoInstance kaleoInstance) {
		_indexExecutor.execute(
			() -> _instanceWorkflowMetricsIndexer.updateDocument(
				kaleoInstance));
	}

	@Reference
	private IndexExecutor _indexExecutor;

	@Reference
	private InstanceWorkflowMetricsIndexer _instanceWorkflowMetricsIndexer;

}