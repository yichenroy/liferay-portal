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

package com.liferay.osb.koroneiki.xylem.distributed.messaging.internal.factory;

import com.liferay.osb.distributed.messaging.Message;
import com.liferay.osb.koroneiki.phloem.rest.dto.v1_0.Product;
import com.liferay.osb.koroneiki.phloem.rest.dto.v1_0.util.AccountUtil;
import com.liferay.osb.koroneiki.phloem.rest.dto.v1_0.util.ContactRoleUtil;
import com.liferay.osb.koroneiki.phloem.rest.dto.v1_0.util.ContactUtil;
import com.liferay.osb.koroneiki.phloem.rest.dto.v1_0.util.ProductConsumptionUtil;
import com.liferay.osb.koroneiki.phloem.rest.dto.v1_0.util.ProductPurchaseUtil;
import com.liferay.osb.koroneiki.phloem.rest.dto.v1_0.util.ProductUtil;
import com.liferay.osb.koroneiki.phloem.rest.dto.v1_0.util.TeamRoleUtil;
import com.liferay.osb.koroneiki.phloem.rest.dto.v1_0.util.TeamUtil;
import com.liferay.osb.koroneiki.taproot.model.Account;
import com.liferay.osb.koroneiki.taproot.model.Contact;
import com.liferay.osb.koroneiki.taproot.model.ContactAccountRole;
import com.liferay.osb.koroneiki.taproot.model.ContactRole;
import com.liferay.osb.koroneiki.taproot.model.Team;
import com.liferay.osb.koroneiki.taproot.model.TeamAccountRole;
import com.liferay.osb.koroneiki.taproot.model.TeamRole;
import com.liferay.osb.koroneiki.trunk.model.ProductConsumption;
import com.liferay.osb.koroneiki.trunk.model.ProductEntry;
import com.liferay.osb.koroneiki.trunk.model.ProductPurchase;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.LocaleUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Amos Fong
 */
@Component(immediate = true, service = MessageFactory.class)
public class MessageFactory {

	public Message create(Account account) throws Exception {
		com.liferay.osb.koroneiki.phloem.rest.dto.v1_0.Account dtoAccount =
			AccountUtil.toAccount(account, LocaleUtil.US, null);

		return new Message(dtoAccount.toString());
	}

	public Message create(Contact contact) throws Exception {
		com.liferay.osb.koroneiki.phloem.rest.dto.v1_0.Contact dtoContact =
			ContactUtil.toContact(contact, null);

		return new Message(dtoContact.toString());
	}

	public Message create(ContactAccountRole contactAccountRole)
		throws Exception {

		JSONObject jsonObject = JSONUtil.put(
			"account", toJSONObject(contactAccountRole.getAccount())
		).put(
			"contact", toJSONObject(contactAccountRole.getContact())
		).put(
			"contactRole", toJSONObject(contactAccountRole.getContactRole())
		);

		return new Message(jsonObject.toString());
	}

	public Message create(ContactRole contactRole) throws Exception {
		com.liferay.osb.koroneiki.phloem.rest.dto.v1_0.ContactRole
			dtoContactRole = ContactRoleUtil.toContactRole(contactRole);

		return new Message(dtoContactRole.toString());
	}

	public Message create(ProductConsumption productConsumption)
		throws Exception {

		com.liferay.osb.koroneiki.phloem.rest.dto.v1_0.ProductConsumption
			dtoProductConsumption = ProductConsumptionUtil.toProductConsumption(
				productConsumption);

		return new Message(dtoProductConsumption.toString());
	}

	public Message create(ProductEntry productEntry) throws Exception {
		Product dtoProduct = ProductUtil.toProduct(productEntry);

		return new Message(dtoProduct.toString());
	}

	public Message create(ProductPurchase productPurchase) throws Exception {
		com.liferay.osb.koroneiki.phloem.rest.dto.v1_0.ProductPurchase
			dtoProductPurchase = ProductPurchaseUtil.toProductPurchase(
				productPurchase);

		return new Message(dtoProductPurchase.toString());
	}

	public Message create(Team team) throws Exception {
		com.liferay.osb.koroneiki.phloem.rest.dto.v1_0.Team dtoTeam =
			TeamUtil.toTeam(team);

		return new Message(dtoTeam.toString());
	}

	public Message create(TeamAccountRole teamAccountRole) throws Exception {
		JSONObject jsonObject = JSONUtil.put(
			"account", toJSONObject(teamAccountRole.getAccount())
		).put(
			"team", toJSONObject(teamAccountRole.getTeam())
		).put(
			"teamRole", toJSONObject(teamAccountRole.getTeamRole())
		);

		return new Message(jsonObject.toString());
	}

	public Message create(TeamRole teamRole) throws Exception {
		com.liferay.osb.koroneiki.phloem.rest.dto.v1_0.TeamRole dtoTeamRole =
			TeamRoleUtil.toTeamRole(teamRole);

		return new Message(dtoTeamRole.toString());
	}

	protected JSONObject toJSONObject(Account account) throws Exception {
		com.liferay.osb.koroneiki.phloem.rest.dto.v1_0.Account dtoAccount =
			AccountUtil.toAccount(account, LocaleUtil.US, null);

		return _jsonFactory.createJSONObject(dtoAccount.toString());
	}

	protected JSONObject toJSONObject(Contact contact) throws Exception {
		com.liferay.osb.koroneiki.phloem.rest.dto.v1_0.Contact dtoContact =
			ContactUtil.toContact(contact, null);

		return _jsonFactory.createJSONObject(dtoContact.toString());
	}

	protected JSONObject toJSONObject(ContactRole contactRole)
		throws Exception {

		com.liferay.osb.koroneiki.phloem.rest.dto.v1_0.ContactRole
			dtoContactRole = ContactRoleUtil.toContactRole(contactRole);

		return _jsonFactory.createJSONObject(dtoContactRole.toString());
	}

	protected JSONObject toJSONObject(Team team) throws Exception {
		com.liferay.osb.koroneiki.phloem.rest.dto.v1_0.Team dtoTeam =
			TeamUtil.toTeam(team);

		return _jsonFactory.createJSONObject(dtoTeam.toString());
	}

	protected JSONObject toJSONObject(TeamRole teamRole) throws Exception {
		com.liferay.osb.koroneiki.phloem.rest.dto.v1_0.TeamRole dtoTeamRole =
			TeamRoleUtil.toTeamRole(teamRole);

		return _jsonFactory.createJSONObject(dtoTeamRole.toString());
	}

	@Reference
	private JSONFactory _jsonFactory;

}