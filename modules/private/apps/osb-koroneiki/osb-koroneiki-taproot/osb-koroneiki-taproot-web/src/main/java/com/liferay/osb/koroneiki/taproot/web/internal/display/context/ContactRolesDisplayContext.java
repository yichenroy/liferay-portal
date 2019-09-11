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

package com.liferay.osb.koroneiki.taproot.web.internal.display.context;

import com.liferay.osb.koroneiki.taproot.constants.ContactRoleType;
import com.liferay.osb.koroneiki.taproot.model.ContactRole;
import com.liferay.osb.koroneiki.taproot.service.ContactRoleLocalServiceUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.SortFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Amos Fong
 */
public class ContactRolesDisplayContext extends BaseSearchDisplayContext {

	public ContactRolesDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse,
		HttpServletRequest httpServletRequest) {

		super(renderRequest, renderResponse, httpServletRequest);
	}

	@Override
	public PortletURL getPortletURL() {
		PortletURL portletURL = super.getPortletURL();

		portletURL.setParameter(
			"tabs1", ParamUtil.getString(renderRequest, "tabs1"));

		return portletURL;
	}

	@Override
	protected SearchContainer createSearchContainer() throws Exception {
		SearchContainer searchContainer = new SearchContainer(
			renderRequest, getPortletURL(), Collections.emptyList(),
			"no-contact-roles-were-found");

		int type = 0;

		String tabs1 = ParamUtil.getString(renderRequest, "tabs1");

		if (tabs1.equals("team-contact-roles")) {
			type = ContactRoleType.TEAM;
		}
		else {
			type = ContactRoleType.ACCOUNT;
		}

		Sort sort = SortFactoryUtil.getSort(
			ContactRole.class, Sort.STRING_TYPE, getOrderByCol(),
			getOrderByType());

		Hits hits = ContactRoleLocalServiceUtil.search(
			themeDisplay.getCompanyId(), type, getKeywords(),
			searchContainer.getStart(), searchContainer.getEnd(), sort);

		List<ContactRole> results = new ArrayList<>();

		for (Document document : hits.getDocs()) {
			long contactRoleId = GetterUtil.getLong(
				document.get(Field.ENTRY_CLASS_PK));

			results.add(
				ContactRoleLocalServiceUtil.getContactRole(contactRoleId));
		}

		searchContainer.setResults(results);
		searchContainer.setTotal(hits.getLength());

		return searchContainer;
	}

}