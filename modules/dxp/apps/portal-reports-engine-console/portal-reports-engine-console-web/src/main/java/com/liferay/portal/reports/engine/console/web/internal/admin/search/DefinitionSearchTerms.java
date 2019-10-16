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

package com.liferay.portal.reports.engine.console.web.internal.admin.search;

import com.liferay.portal.kernel.dao.search.DAOParamUtil;

import javax.portlet.PortletRequest;

/**
 * @author Rafael Praxedes
 */
public class DefinitionSearchTerms extends DefinitionDisplayTerms {

	public DefinitionSearchTerms(PortletRequest portletRequest) {
		super(portletRequest);

		definitionName = DAOParamUtil.getString(
			portletRequest, DEFINITION_NAME);
		description = DAOParamUtil.getString(portletRequest, DESCRIPTION);
		sourceId = DAOParamUtil.getString(portletRequest, SOURCE_ID);
		reportName = DAOParamUtil.getString(portletRequest, REPORT_NAME);
	}

}