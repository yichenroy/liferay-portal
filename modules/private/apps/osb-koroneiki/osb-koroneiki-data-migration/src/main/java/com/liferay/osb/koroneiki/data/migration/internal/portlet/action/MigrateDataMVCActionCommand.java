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

package com.liferay.osb.koroneiki.data.migration.internal.portlet.action;

import com.liferay.osb.koroneiki.data.migration.internal.constants.DataMigrationPortletKeys;
import com.liferay.osb.koroneiki.data.migration.internal.migration.CorpEntryMigration;
import com.liferay.osb.koroneiki.data.migration.internal.migration.CorpProjectMigration;
import com.liferay.osb.koroneiki.data.migration.internal.migration.UserMigration;
import com.liferay.osb.koroneiki.xylem.distributed.messaging.model.listener.PublishingTasksThreadLocal;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.apache.commons.lang.time.StopWatch;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Amos Fong
 */
@Component(
	property = {
		"javax.portlet.name=" + DataMigrationPortletKeys.ADMIN,
		"mvc.command.name=/migrate_data"
	},
	service = MVCActionCommand.class
)
public class MigrateDataMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			StopWatch stopWatch = new StopWatch();

			stopWatch.start();

			PublishingTasksThreadLocal.setImportInProcess(true);

			ThemeDisplay themeDisplay =
				(ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

			_corpEntryMigration.migrate(themeDisplay.getUserId());

			_corpProjectMigration.migrate(themeDisplay.getUserId());

			_userMigration.migrate(themeDisplay.getUserId());

			if (_log.isInfoEnabled()) {
				_log.info("Migration took " + stopWatch.getTime() + " ms");
			}

			sendRedirect(actionRequest, actionResponse);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw e;
		}
		finally {
			PublishingTasksThreadLocal.setImportInProcess(false);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MigrateDataMVCActionCommand.class);

	@Reference
	private CorpEntryMigration _corpEntryMigration;

	@Reference
	private CorpProjectMigration _corpProjectMigration;

	@Reference
	private UserMigration _userMigration;

}