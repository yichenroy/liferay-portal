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

import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken;
import com.liferay.portal.workflow.metrics.internal.search.index.TokenWorkflowMetricsIndexer;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Inácio Nery
 */
@Component(immediate = true, service = ModelListener.class)
public class KaleoTaskInstanceTokenModelListener
	extends BaseModelListener<KaleoTaskInstanceToken> {

	@Override
	public void onAfterCreate(KaleoTaskInstanceToken kaleoTaskInstanceToken)
		throws ModelListenerException {

		try {
			_tokenWorkflowMetricsIndexer.addDocument(kaleoTaskInstanceToken);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	@Override
	public void onAfterRemove(KaleoTaskInstanceToken kaleoTaskInstanceToken)
		throws ModelListenerException {

		try {
			_tokenWorkflowMetricsIndexer.deleteDocument(kaleoTaskInstanceToken);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	@Override
	public void onAfterUpdate(KaleoTaskInstanceToken kaleoTaskInstanceToken)
		throws ModelListenerException {

		try {
			_tokenWorkflowMetricsIndexer.updateDocument(kaleoTaskInstanceToken);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		KaleoTaskInstanceTokenModelListener.class);

	@Reference
	private TokenWorkflowMetricsIndexer _tokenWorkflowMetricsIndexer;

}