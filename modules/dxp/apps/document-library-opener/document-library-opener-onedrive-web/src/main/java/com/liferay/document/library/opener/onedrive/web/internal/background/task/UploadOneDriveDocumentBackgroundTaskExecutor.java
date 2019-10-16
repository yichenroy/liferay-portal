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

package com.liferay.document.library.opener.onedrive.web.internal.background.task;

import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.opener.onedrive.web.internal.DLOpenerOneDriveManager;
import com.liferay.document.library.opener.onedrive.web.internal.constants.OneDriveBackgroundTaskConstants;
import com.liferay.document.library.opener.onedrive.web.internal.graph.IAuthenticationProviderImpl;
import com.liferay.document.library.opener.onedrive.web.internal.oauth.AccessToken;
import com.liferay.document.library.opener.onedrive.web.internal.oauth.OAuth2Manager;
import com.liferay.document.library.opener.service.DLOpenerFileEntryReferenceLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskConstants;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskExecutor;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskResult;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatusMessageSender;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskThreadLocal;
import com.liferay.portal.kernel.backgroundtask.BaseBackgroundTaskExecutor;
import com.liferay.portal.kernel.backgroundtask.display.BackgroundTaskDisplay;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.util.GetterUtil;

import com.microsoft.graph.concurrency.ChunkedUploadProvider;
import com.microsoft.graph.concurrency.IProgressCallback;
import com.microsoft.graph.core.ClientException;
import com.microsoft.graph.core.DefaultClientConfig;
import com.microsoft.graph.models.extensions.DriveItem;
import com.microsoft.graph.models.extensions.DriveItemUploadableProperties;
import com.microsoft.graph.models.extensions.File;
import com.microsoft.graph.models.extensions.IGraphServiceClient;
import com.microsoft.graph.requests.extensions.GraphServiceClient;
import com.microsoft.graph.requests.extensions.IDriveItemCollectionRequest;
import com.microsoft.graph.requests.extensions.IDriveItemCreateUploadSessionRequest;

import java.io.IOException;
import java.io.Serializable;

import java.util.Map;
import java.util.Optional;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Uploads content to OneDrive in a background task.
 *
 * @author Cristina González
 * @review
 */
@Component(
	property = "background.task.executor.class.name=com.liferay.document.library.opener.onedrive.web.internal.background.task.UploadOneDriveDocumentBackgroundTaskExecutor",
	service = BackgroundTaskExecutor.class
)
public class UploadOneDriveDocumentBackgroundTaskExecutor
	extends BaseBackgroundTaskExecutor {

	public UploadOneDriveDocumentBackgroundTaskExecutor() {
		setBackgroundTaskStatusMessageTranslator(
			new UploadOneDriveDocumentBackgroundTaskStatusMessageTranslator());
		setIsolationLevel(BackgroundTaskConstants.ISOLATION_LEVEL_TASK_NAME);
	}

	@Override
	public BackgroundTaskExecutor clone() {
		return this;
	}

	@Override
	public BackgroundTaskResult execute(BackgroundTask backgroundTask)
		throws Exception {

		Map<String, Serializable> taskContextMap =
			backgroundTask.getTaskContextMap();

		long fileEntryId = GetterUtil.getLong(
			taskContextMap.get(OneDriveBackgroundTaskConstants.FILE_ENTRY_ID));

		String cmd = (String)taskContextMap.get(
			OneDriveBackgroundTaskConstants.CMD);
		long userId = GetterUtil.getLong(
			taskContextMap.get(OneDriveBackgroundTaskConstants.USER_ID));

		if (cmd.equals(OneDriveBackgroundTaskConstants.CHECKOUT)) {
			_uploadFile(userId, _dlAppLocalService.getFileEntry(fileEntryId));
		}
		else {
			_dlOpenerOneDriveManager.createFile(
				userId, _dlAppLocalService.getFileEntry(fileEntryId));
		}

		return BackgroundTaskResult.SUCCESS;
	}

	@Override
	public BackgroundTaskDisplay getBackgroundTaskDisplay(
		BackgroundTask backgroundTask) {

		return null;
	}

	@Override
	public String handleException(BackgroundTask backgroundTask, Exception e) {
		Map<String, Serializable> taskContextMap =
			backgroundTask.getTaskContextMap();

		long fileEntryId = GetterUtil.getLong(
			taskContextMap.get(OneDriveBackgroundTaskConstants.FILE_ENTRY_ID));

		try {
			_dlOpenerFileEntryReferenceLocalService.
				deleteDLOpenerFileEntryReference(
					_dlAppLocalService.getFileEntry(fileEntryId));
		}
		catch (PortalException pe) {
			_log.error(pe, pe);
		}

		return StringPool.BLANK;
	}

	private IProgressCallback _createIProgressCallback(FileEntry fileEntry) {
		return new IProgressCallback<DriveItem>() {

			@Override
			public void failure(ClientException clientException) {
				throw new RuntimeException(clientException);
			}

			@Override
			public void progress(long current, long max) {
				_sendStatusMessage(
					OneDriveBackgroundTaskConstants.PROGRESS, fileEntry,
					BackgroundTaskConstants.STATUS_IN_PROGRESS);
			}

			@Override
			public void success(DriveItem driveItem) {
			}

		};
	}

	private AccessToken _getAccessToken(long companyId, long userId)
		throws PortalException {

		Optional<AccessToken> accessTokenOptional =
			_oAuth2Manager.getAccessTokenOptional(companyId, userId);

		return accessTokenOptional.orElseThrow(
			() -> new PrincipalException(
				StringBundler.concat(
					"User ", userId,
					" does not have a valid OneDrive access token")));
	}

	private void _sendStatusMessage(
		String phase, FileEntry fileEntry, int status) {

		Message message = new Message();

		message.put(
			BackgroundTaskConstants.BACKGROUND_TASK_ID,
			BackgroundTaskThreadLocal.getBackgroundTaskId());
		message.put(
			OneDriveBackgroundTaskConstants.FILE_ENTRY_ID,
			fileEntry.getFileEntryId());
		message.put(OneDriveBackgroundTaskConstants.PHASE, phase);
		message.put("status", status);

		_backgroundTaskStatusMessageSender.sendBackgroundTaskStatusMessage(
			message);
	}

	private void _uploadFile(long userId, FileEntry fileEntry)
		throws PortalException {

		AccessToken accessToken = _getAccessToken(
			fileEntry.getCompanyId(), userId);

		IGraphServiceClient iGraphServiceClientBuilder =
			GraphServiceClient.fromConfig(
				DefaultClientConfig.createWithAuthenticationProvider(
					new IAuthenticationProviderImpl(accessToken)));

		IDriveItemCollectionRequest iDriveItemCollectionRequest =
			iGraphServiceClientBuilder.me(
			).drive(
			).root(
			).children(
			).buildRequest();

		DriveItem driveItem = new DriveItem();

		driveItem.name = fileEntry.getFileName();

		File file = new File();

		file.mimeType = fileEntry.getMimeType();

		driveItem.file = file;

		DriveItem postedDriveItem = iDriveItemCollectionRequest.post(driveItem);

		DriveItemUploadableProperties driveItemUploadableProperties =
			new DriveItemUploadableProperties();

		IDriveItemCreateUploadSessionRequest
			iDriveItemCreateUploadSessionRequest =
				iGraphServiceClientBuilder.me(
				).drive(
				).items(
					postedDriveItem.id
				).createUploadSession(
					driveItemUploadableProperties
				).buildRequest();

		ChunkedUploadProvider<DriveItem> chunkedUploadProvider =
			new ChunkedUploadProvider<>(
				iDriveItemCreateUploadSessionRequest.post(),
				iGraphServiceClientBuilder, fileEntry.getContentStream(),
				fileEntry.getSize(), DriveItem.class);

		try {
			_sendStatusMessage(
				OneDriveBackgroundTaskConstants.PORTAL_START, fileEntry,
				BackgroundTaskConstants.STATUS_IN_PROGRESS);

			chunkedUploadProvider.upload(
				null, _createIProgressCallback(fileEntry),
				new int[] {10 * 320 * 1024});

			_sendStatusMessage(
				OneDriveBackgroundTaskConstants.PORTAL_END, fileEntry,
				BackgroundTaskConstants.STATUS_IN_PROGRESS);
		}
		catch (IOException ioe) {
			throw new PortalException(ioe);
		}

		_dlOpenerFileEntryReferenceLocalService.
			updateDLOpenerFileEntryReference(postedDriveItem.id, fileEntry);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UploadOneDriveDocumentBackgroundTaskExecutor.class);

	@Reference
	private BackgroundTaskStatusMessageSender
		_backgroundTaskStatusMessageSender;

	@Reference
	private DLAppLocalService _dlAppLocalService;

	@Reference
	private DLOpenerFileEntryReferenceLocalService
		_dlOpenerFileEntryReferenceLocalService;

	@Reference
	private DLOpenerOneDriveManager _dlOpenerOneDriveManager;

	@Reference
	private OAuth2Manager _oAuth2Manager;

}