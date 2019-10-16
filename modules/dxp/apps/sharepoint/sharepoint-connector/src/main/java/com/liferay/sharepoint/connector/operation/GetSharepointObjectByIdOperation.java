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

package com.liferay.sharepoint.connector.operation;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.sharepoint.connector.SharepointException;
import com.liferay.sharepoint.connector.SharepointObject;
import com.liferay.sharepoint.connector.schema.query.Query;
import com.liferay.sharepoint.connector.schema.query.QueryField;
import com.liferay.sharepoint.connector.schema.query.QueryOptionsList;
import com.liferay.sharepoint.connector.schema.query.QueryValue;
import com.liferay.sharepoint.connector.schema.query.operator.EqOperator;
import com.liferay.sharepoint.connector.schema.query.option.FolderQueryOption;
import com.liferay.sharepoint.connector.schema.query.option.ViewAttributesQueryOption;

/**
 * @author Iván Zaera
 */
public class GetSharepointObjectByIdOperation extends BaseOperation {

	@Override
	public void afterPropertiesSet() {
		_getSharepointObjectsByQueryOperation = getOperation(
			GetSharepointObjectsByQueryOperation.class);
	}

	public SharepointObject execute(long sharepointObjectId)
		throws SharepointException {

		Query query = new Query(
			new EqOperator(
				new QueryField("ID"),
				new QueryValue(String.valueOf(sharepointObjectId))));
		QueryOptionsList queryOptionsList = new QueryOptionsList(
			new FolderQueryOption(StringPool.BLANK),
			new ViewAttributesQueryOption(true));

		return getSharepointObject(
			_getSharepointObjectsByQueryOperation.execute(
				query, queryOptionsList));
	}

	private GetSharepointObjectsByQueryOperation
		_getSharepointObjectsByQueryOperation;

}