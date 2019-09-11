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

package com.liferay.osb.koroneiki.xylem.distributed.messaging.internal.model.listener;

import com.liferay.osb.distributed.messaging.Message;
import com.liferay.osb.koroneiki.root.model.ExternalLink;
import com.liferay.osb.koroneiki.taproot.model.Account;
import com.liferay.osb.koroneiki.taproot.model.Contact;
import com.liferay.osb.koroneiki.taproot.model.Team;
import com.liferay.osb.koroneiki.taproot.service.AccountLocalService;
import com.liferay.osb.koroneiki.taproot.service.ContactLocalService;
import com.liferay.osb.koroneiki.taproot.service.TeamLocalService;
import com.liferay.osb.koroneiki.trunk.model.ProductConsumption;
import com.liferay.osb.koroneiki.trunk.model.ProductEntry;
import com.liferay.osb.koroneiki.trunk.model.ProductPurchase;
import com.liferay.osb.koroneiki.trunk.service.ProductConsumptionLocalService;
import com.liferay.osb.koroneiki.trunk.service.ProductEntryLocalService;
import com.liferay.osb.koroneiki.trunk.service.ProductPurchaseLocalService;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.service.ClassNameLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Amos Fong
 */
@Component(immediate = true, service = ModelListener.class)
public class ExternalLinkModelListener
	extends BaseXylemModelListener<ExternalLink> {

	@Override
	public Message createMessage(ExternalLink externalLink) throws Exception {
		if (externalLink.getClassNameId() ==
				_classNameLocalService.getClassNameId(Account.class)) {

			Account account = _accountLocalService.getAccount(
				externalLink.getClassPK());

			return messageFactory.create(account);
		}
		else if (externalLink.getClassNameId() ==
					_classNameLocalService.getClassNameId(Contact.class)) {

			Contact contact = _contactLocalService.getContact(
				externalLink.getClassPK());

			return messageFactory.create(contact);
		}
		else if (externalLink.getClassNameId() ==
					_classNameLocalService.getClassNameId(
						ProductConsumption.class)) {

			ProductConsumption productConsumption =
				_productConsumptionLocalService.getProductConsumption(
					externalLink.getClassPK());

			return messageFactory.create(productConsumption);
		}
		else if (externalLink.getClassNameId() ==
					_classNameLocalService.getClassNameId(ProductEntry.class)) {

			ProductEntry productEntry =
				_productEntryLocalService.getProductEntry(
					externalLink.getClassPK());

			return messageFactory.create(productEntry);
		}
		else if (externalLink.getClassNameId() ==
					_classNameLocalService.getClassNameId(
						ProductPurchase.class)) {

			ProductPurchase productPurchase =
				_productPurchaseLocalService.getProductPurchase(
					externalLink.getClassPK());

			return messageFactory.create(productPurchase);
		}
		else if (externalLink.getClassNameId() ==
					_classNameLocalService.getClassNameId(Team.class)) {

			Team team = _teamLocalService.getTeam(externalLink.getClassPK());

			return messageFactory.create(team);
		}

		return null;
	}

	@Override
	protected String getCreateTopic(ExternalLink externalLink) {
		return _getTopic(externalLink);
	}

	@Override
	protected String getRemoveTopic(ExternalLink externalLink) {
		return _getTopic(externalLink);
	}

	@Override
	protected String getUpdateTopic(ExternalLink externalLink) {
		return _getTopic(externalLink);
	}

	private String _getTopic(ExternalLink externalLink) {
		if (externalLink.getClassNameId() ==
				_classNameLocalService.getClassNameId(Account.class)) {

			return "koroneiki.account.update";
		}
		else if (externalLink.getClassNameId() ==
					_classNameLocalService.getClassNameId(Contact.class)) {

			return "koroneiki.contact.update";
		}
		else if (externalLink.getClassNameId() ==
					_classNameLocalService.getClassNameId(
						ProductConsumption.class)) {

			return "koroneiki.productconsumption.update";
		}
		else if (externalLink.getClassNameId() ==
					_classNameLocalService.getClassNameId(ProductEntry.class)) {

			return "koroneiki.productentry.update";
		}
		else if (externalLink.getClassNameId() ==
					_classNameLocalService.getClassNameId(
						ProductPurchase.class)) {

			return "koroneiki.productpurchase.update";
		}
		else if (externalLink.getClassNameId() ==
					_classNameLocalService.getClassNameId(Team.class)) {

			return "koroneiki.team.update";
		}

		return null;
	}

	@Reference
	private AccountLocalService _accountLocalService;

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private ContactLocalService _contactLocalService;

	@Reference
	private ProductConsumptionLocalService _productConsumptionLocalService;

	@Reference
	private ProductEntryLocalService _productEntryLocalService;

	@Reference
	private ProductPurchaseLocalService _productPurchaseLocalService;

	@Reference
	private TeamLocalService _teamLocalService;

}