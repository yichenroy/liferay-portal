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

package com.liferay.document.library.asset.auto.tagger.microsoft.cognitive.services.internal;

import com.liferay.asset.auto.tagger.AssetAutoTagProvider;
import com.liferay.document.library.asset.auto.tagger.microsoft.cognitive.services.internal.configuration.MSCognitiveServicesAssetAutoTagProviderCompanyConfiguration;
import com.liferay.document.library.asset.auto.tagger.microsoft.cognitive.services.internal.constants.MSCognitiveServicesAssetAutoTagProviderConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.repository.capabilities.TemporaryFileEntriesCapability;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.settings.CompanyServiceSettingsLocator;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.InputStream;
import java.io.OutputStream;

import java.net.HttpURLConnection;
import java.net.URL;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	property = "model.class.name=com.liferay.document.library.kernel.model.DLFileEntry",
	service = AssetAutoTagProvider.class
)
public class MSCognitiveServicesImageAssetAutoTagProvider
	implements AssetAutoTagProvider<FileEntry> {

	@Override
	public List<String> getTagNames(FileEntry fileEntry) {
		try {
			MSCognitiveServicesAssetAutoTagProviderCompanyConfiguration
				msCognitiveServicesAssetAutoTagProviderCompanyConfiguration =
					_getConfiguration(fileEntry);

			if (!msCognitiveServicesAssetAutoTagProviderCompanyConfiguration.
					enabled() ||
				_isTemporary(fileEntry) || (fileEntry.getSize() > _MAX_SIZE) ||
				!_isSupportedFormat(fileEntry)) {

				return Collections.emptyList();
			}

			FileVersion fileVersion = fileEntry.getFileVersion();

			JSONObject responseJSONObject = _queryComputerVisionJSONObject(
				msCognitiveServicesAssetAutoTagProviderCompanyConfiguration.
					apiEndpoint(),
				msCognitiveServicesAssetAutoTagProviderCompanyConfiguration.
					apiKey(),
				fileVersion);

			JSONArray tagsJSONArray = responseJSONObject.getJSONArray("tags");

			return JSONUtil.toStringList(tagsJSONArray, "name");
		}
		catch (Exception e) {
			_log.error(e, e);

			return Collections.emptyList();
		}
	}

	private MSCognitiveServicesAssetAutoTagProviderCompanyConfiguration
		_getConfiguration(FileEntry fileEntry) throws ConfigurationException {

		return _configurationProvider.getConfiguration(
			MSCognitiveServicesAssetAutoTagProviderCompanyConfiguration.class,
			new CompanyServiceSettingsLocator(
				fileEntry.getCompanyId(),
				MSCognitiveServicesAssetAutoTagProviderConstants.SERVICE_NAME));
	}

	private boolean _isSupportedFormat(FileEntry fileEntry) {
		String extension = fileEntry.getExtension();

		return _supportedFormats.contains(StringUtil.toUpperCase(extension));
	}

	private boolean _isTemporary(FileEntry fileEntry) {
		return fileEntry.isRepositoryCapabilityProvided(
			TemporaryFileEntriesCapability.class);
	}

	private JSONObject _queryComputerVisionJSONObject(
			String apiEnpoint, String apiKey, FileVersion fileVersion)
		throws Exception {

		URL url = new URL(apiEnpoint + "/tag");

		HttpURLConnection httpURLConnection =
			(HttpURLConnection)url.openConnection();

		httpURLConnection.setDoOutput(true);
		httpURLConnection.setRequestMethod("POST");
		httpURLConnection.setRequestProperty(
			"Content-Type", "application/octet-stream");
		httpURLConnection.setRequestProperty(
			"Ocp-Apim-Subscription-Key", apiKey);

		try (OutputStream outputStream = httpURLConnection.getOutputStream()) {
			outputStream.write(
				FileUtil.getBytes(fileVersion.getContentStream(false)));
		}

		httpURLConnection.getResponseMessage();

		try (InputStream inputStream = httpURLConnection.getInputStream()) {
			return JSONFactoryUtil.createJSONObject(
				StringUtil.read(inputStream));
		}
		catch (Exception e) {
			try (InputStream inputStream = httpURLConnection.getErrorStream()) {
				throw new PortalException(
					"Response code " + httpURLConnection.getResponseCode() +
						":" + StringUtil.read(inputStream),
					e);
			}
		}
	}

	private static final int _MAX_SIZE = 4 * 1024 * 1024;

	private static final Log _log = LogFactoryUtil.getLog(
		MSCognitiveServicesImageAssetAutoTagProvider.class);

	private static final Set<String> _supportedFormats = new HashSet<>(
		Arrays.asList("BMP", "GIF", "JPEG", "JPG", "PNG"));

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private Http _http;

}