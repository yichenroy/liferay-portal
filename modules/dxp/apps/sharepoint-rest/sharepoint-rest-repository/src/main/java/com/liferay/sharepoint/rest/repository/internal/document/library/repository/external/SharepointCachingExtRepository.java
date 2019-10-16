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

package com.liferay.sharepoint.rest.repository.internal.document.library.repository.external;

import com.liferay.document.library.repository.external.CredentialsProvider;
import com.liferay.document.library.repository.external.ExtRepository;
import com.liferay.document.library.repository.external.ExtRepositoryFileEntry;
import com.liferay.document.library.repository.external.ExtRepositoryFileVersion;
import com.liferay.document.library.repository.external.ExtRepositoryFileVersionDescriptor;
import com.liferay.document.library.repository.external.ExtRepositoryFolder;
import com.liferay.document.library.repository.external.ExtRepositoryObject;
import com.liferay.document.library.repository.external.ExtRepositoryObjectType;
import com.liferay.document.library.repository.external.ExtRepositorySearchResult;
import com.liferay.document.library.repository.external.search.ExtRepositoryQueryMapper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.util.AutoResetThreadLocal;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.io.InputStream;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Adolfo Pérez
 */
public class SharepointCachingExtRepository implements ExtRepository {

	public SharepointCachingExtRepository(ExtRepository extRepository) {
		_extRepository = extRepository;
	}

	@Override
	public ExtRepositoryFileEntry addExtRepositoryFileEntry(
			String extRepositoryParentFolderKey, String mimeType, String title,
			String description, String changeLog, InputStream inputStream)
		throws PortalException {

		Map<String, List<ExtRepositoryObject>> extRepositoryObjectsCache =
			_extRepositoryObjectsCache.get();

		extRepositoryObjectsCache.remove(extRepositoryParentFolderKey);

		ExtRepositoryFileEntry extRepositoryFileEntry =
			_extRepository.addExtRepositoryFileEntry(
				extRepositoryParentFolderKey, mimeType, title, description,
				changeLog, inputStream);

		Map<String, ExtRepositoryObject> extRepositoryObjectCache =
			_extRepositoryObjectCache.get();

		extRepositoryObjectCache.put(
			extRepositoryFileEntry.getExtRepositoryModelKey(),
			extRepositoryFileEntry);

		return extRepositoryFileEntry;
	}

	@Override
	public ExtRepositoryFolder addExtRepositoryFolder(
			String extRepositoryParentFolderKey, String name,
			String description)
		throws PortalException {

		Map<String, List<ExtRepositoryObject>> extRepositoryObjectsCache =
			_extRepositoryObjectsCache.get();

		extRepositoryObjectsCache.remove(extRepositoryParentFolderKey);

		Map<String, ExtRepositoryObject> extRepositoryObjectCache =
			_extRepositoryObjectCache.get();

		ExtRepositoryFolder extRepositoryFolder =
			_extRepository.addExtRepositoryFolder(
				extRepositoryParentFolderKey, name, description);

		extRepositoryObjectCache.put(
			extRepositoryFolder.getExtRepositoryModelKey(),
			extRepositoryFolder);

		return extRepositoryFolder;
	}

	@Override
	public ExtRepositoryFileVersion cancelCheckOut(
			String extRepositoryFileEntryKey)
		throws PortalException {

		Map<String, ExtRepositoryObject> extRepositoryObjectCache =
			_extRepositoryObjectCache.get();

		extRepositoryObjectCache.remove(extRepositoryFileEntryKey);

		Map<String, List<ExtRepositoryFileVersion>>
			extRepositoryFileVersionCache =
				_extRepositoryFileVersionCache.get();

		extRepositoryFileVersionCache.remove(extRepositoryFileEntryKey);

		return _extRepository.cancelCheckOut(extRepositoryFileEntryKey);
	}

	@Override
	public void checkInExtRepositoryFileEntry(
			String extRepositoryFileEntryKey, boolean createMajorVersion,
			String changeLog)
		throws PortalException {

		Map<String, ExtRepositoryObject> extRepositoryObjectCache =
			_extRepositoryObjectCache.get();

		extRepositoryObjectCache.remove(extRepositoryFileEntryKey);

		Map<String, List<ExtRepositoryFileVersion>>
			extRepositoryFileVersionCache =
				_extRepositoryFileVersionCache.get();

		extRepositoryFileVersionCache.remove(extRepositoryFileEntryKey);

		_extRepository.checkInExtRepositoryFileEntry(
			extRepositoryFileEntryKey, createMajorVersion, changeLog);
	}

	@Override
	public ExtRepositoryFileEntry checkOutExtRepositoryFileEntry(
			String extRepositoryFileEntryKey)
		throws PortalException {

		Map<String, ExtRepositoryObject> extRepositoryObjectCache =
			_extRepositoryObjectCache.get();

		extRepositoryObjectCache.remove(extRepositoryFileEntryKey);

		Map<String, List<ExtRepositoryFileVersion>>
			extRepositoryFileVersionCache =
				_extRepositoryFileVersionCache.get();

		extRepositoryFileVersionCache.remove(extRepositoryFileEntryKey);

		return _extRepository.checkOutExtRepositoryFileEntry(
			extRepositoryFileEntryKey);
	}

	@Override
	public <T extends ExtRepositoryObject> T copyExtRepositoryObject(
			ExtRepositoryObjectType<T> extRepositoryObjectType,
			String extRepositoryFileEntryKey, String newExtRepositoryFolderKey,
			String newTitle)
		throws PortalException {

		return _extRepository.copyExtRepositoryObject(
			extRepositoryObjectType, extRepositoryFileEntryKey,
			newExtRepositoryFolderKey, newTitle);
	}

	@Override
	public void deleteExtRepositoryObject(
			ExtRepositoryObjectType<? extends ExtRepositoryObject>
				extRepositoryObjectType,
			String extRepositoryObjectKey)
		throws PortalException {

		Map<String, ExtRepositoryObject> extRepositoryObjectCache =
			_extRepositoryObjectCache.get();

		extRepositoryObjectCache.remove(extRepositoryObjectKey);

		Map<String, List<ExtRepositoryObject>> extRepositoryObjectsCache =
			_extRepositoryObjectsCache.get();

		extRepositoryObjectsCache.clear();

		Map<String, ExtRepositoryFolder> extRepositoryParentFolderCache =
			_extRepositoryParentFolderCache.get();

		extRepositoryParentFolderCache.remove(extRepositoryObjectKey);

		if (extRepositoryObjectType == ExtRepositoryObjectType.FILE) {
			Map<String, List<ExtRepositoryFileVersion>>
				extRepositoryFileVersionCache =
					_extRepositoryFileVersionCache.get();

			extRepositoryFileVersionCache.clear();
		}

		_extRepository.deleteExtRepositoryObject(
			extRepositoryObjectType, extRepositoryObjectKey);
	}

	@Override
	public String getAuthType() {
		return _extRepository.getAuthType();
	}

	@Override
	public InputStream getContentStream(
			ExtRepositoryFileEntry extRepositoryFileEntry)
		throws PortalException {

		return _extRepository.getContentStream(extRepositoryFileEntry);
	}

	@Override
	public InputStream getContentStream(
			ExtRepositoryFileVersion extRepositoryFileVersion)
		throws PortalException {

		return _extRepository.getContentStream(extRepositoryFileVersion);
	}

	@Override
	public ExtRepositoryFileVersion getExtRepositoryFileVersion(
			ExtRepositoryFileEntry extRepositoryFileEntry, String version)
		throws PortalException {

		return _extRepository.getExtRepositoryFileVersion(
			extRepositoryFileEntry, version);
	}

	@Override
	public ExtRepositoryFileVersionDescriptor
		getExtRepositoryFileVersionDescriptor(
			String extRepositoryFileVersionKey) {

		return _extRepository.getExtRepositoryFileVersionDescriptor(
			extRepositoryFileVersionKey);
	}

	@Override
	public List<ExtRepositoryFileVersion> getExtRepositoryFileVersions(
			ExtRepositoryFileEntry extRepositoryFileEntry)
		throws PortalException {

		Map<String, List<ExtRepositoryFileVersion>>
			extRepositoryFileVersionCache =
				_extRepositoryFileVersionCache.get();

		String extRepositoryModelKey =
			extRepositoryFileEntry.getExtRepositoryModelKey();

		if (extRepositoryFileVersionCache.containsKey(extRepositoryModelKey)) {
			return extRepositoryFileVersionCache.get(extRepositoryModelKey);
		}

		List<ExtRepositoryFileVersion> extRepositoryFileVersions =
			_extRepository.getExtRepositoryFileVersions(extRepositoryFileEntry);

		extRepositoryFileVersionCache.put(
			extRepositoryModelKey, extRepositoryFileVersions);

		return extRepositoryFileVersions;
	}

	@Override
	public <T extends ExtRepositoryObject> T getExtRepositoryObject(
			ExtRepositoryObjectType<T> extRepositoryObjectType,
			String extRepositoryObjectKey)
		throws PortalException {

		Map<String, ExtRepositoryObject> extRepositoryObjectCache =
			_extRepositoryObjectCache.get();

		if (extRepositoryObjectCache.containsKey(extRepositoryObjectKey)) {
			return (T)extRepositoryObjectCache.get(extRepositoryObjectKey);
		}

		T extRepositoryObject = _extRepository.getExtRepositoryObject(
			extRepositoryObjectType, extRepositoryObjectKey);

		extRepositoryObjectCache.put(
			extRepositoryObjectKey, extRepositoryObject);

		return extRepositoryObject;
	}

	@Override
	public <T extends ExtRepositoryObject> T getExtRepositoryObject(
			ExtRepositoryObjectType<T> extRepositoryObjectType,
			String extRepositoryFolderKey, String title)
		throws PortalException {

		return _extRepository.getExtRepositoryObject(
			extRepositoryObjectType, extRepositoryFolderKey, title);
	}

	@Override
	public <T extends ExtRepositoryObject> List<T> getExtRepositoryObjects(
			ExtRepositoryObjectType<T> extRepositoryObjectType,
			String extRepositoryFolderKey)
		throws PortalException {

		Map<String, List<ExtRepositoryObject>> extRepositoryObjectsCache =
			_extRepositoryObjectsCache.get();

		if (extRepositoryObjectsCache.containsKey(extRepositoryFolderKey)) {
			return (List<T>)extRepositoryObjectsCache.get(
				extRepositoryFolderKey);
		}

		List<T> extRepositoryObjects = _extRepository.getExtRepositoryObjects(
			extRepositoryObjectType, extRepositoryFolderKey);

		Map<String, ExtRepositoryObject> extRepositoryObjectCache =
			_extRepositoryObjectCache.get();

		extRepositoryObjects.forEach(
			extRepositoryObject -> extRepositoryObjectCache.put(
				extRepositoryObject.getExtRepositoryModelKey(),
				extRepositoryObject));

		extRepositoryObjectsCache.put(
			extRepositoryFolderKey,
			(List<ExtRepositoryObject>)extRepositoryObjects);

		return extRepositoryObjects;
	}

	@Override
	public int getExtRepositoryObjectsCount(
			ExtRepositoryObjectType<? extends ExtRepositoryObject>
				extRepositoryObjectType,
			String extRepositoryFolderKey)
		throws PortalException {

		return _extRepository.getExtRepositoryObjectsCount(
			extRepositoryObjectType, extRepositoryFolderKey);
	}

	@Override
	public ExtRepositoryFolder getExtRepositoryParentFolder(
			ExtRepositoryObject extRepositoryObject)
		throws PortalException {

		Map<String, ExtRepositoryFolder> extRepositoryParentFolderCache =
			_extRepositoryParentFolderCache.get();

		if (extRepositoryParentFolderCache.containsKey(
				extRepositoryObject.getExtRepositoryModelKey())) {

			return extRepositoryParentFolderCache.get(
				extRepositoryObject.getExtRepositoryModelKey());
		}

		ExtRepositoryFolder extRepositoryParentFolder =
			_extRepository.getExtRepositoryParentFolder(extRepositoryObject);

		extRepositoryParentFolderCache.put(
			extRepositoryObject.getExtRepositoryModelKey(),
			extRepositoryParentFolder);

		return extRepositoryParentFolder;
	}

	@Override
	public String getLiferayLogin(String extRepositoryLogin) {
		return _extRepository.getLiferayLogin(extRepositoryLogin);
	}

	@Override
	public String getRootFolderKey() throws PortalException {
		return _extRepository.getRootFolderKey();
	}

	@Override
	public List<String> getSubfolderKeys(
			String extRepositoryFolderKey, boolean recurse)
		throws PortalException {

		return _extRepository.getSubfolderKeys(extRepositoryFolderKey, recurse);
	}

	@Override
	public String[] getSupportedConfigurations() {
		return _extRepository.getSupportedConfigurations();
	}

	@Override
	public String[][] getSupportedParameters() {
		return _extRepository.getSupportedParameters();
	}

	@Override
	public void initRepository(
			UnicodeProperties typeSettingsProperties,
			CredentialsProvider credentialsProvider)
		throws PortalException {

		_extRepository.initRepository(
			typeSettingsProperties, credentialsProvider);
	}

	@Override
	public <T extends ExtRepositoryObject> T moveExtRepositoryObject(
			ExtRepositoryObjectType<T> extRepositoryObjectType,
			String extRepositoryObjectKey, String newExtRepositoryFolderKey,
			String newTitle)
		throws PortalException {

		Map<String, ExtRepositoryFolder> extRepositoryParentFolderCache =
			_extRepositoryParentFolderCache.get();

		extRepositoryParentFolderCache.remove(extRepositoryObjectKey);

		Map<String, ExtRepositoryObject> extRepositoryObjectCache =
			_extRepositoryObjectCache.get();

		extRepositoryObjectCache.remove(extRepositoryObjectKey);

		Map<String, List<ExtRepositoryObject>> extRepositoryObjectsCache =
			_extRepositoryObjectsCache.get();

		extRepositoryObjectsCache.remove(extRepositoryObjectKey);

		Map<String, List<ExtRepositoryFileVersion>>
			extRepositoryFileVersionCache =
				_extRepositoryFileVersionCache.get();

		extRepositoryFileVersionCache.remove(extRepositoryObjectKey);

		return _extRepository.moveExtRepositoryObject(
			extRepositoryObjectType, extRepositoryObjectKey,
			newExtRepositoryFolderKey, newTitle);
	}

	@Override
	public List<ExtRepositorySearchResult<?>> search(
			SearchContext searchContext, Query query,
			ExtRepositoryQueryMapper extRepositoryQueryMapper)
		throws PortalException {

		return _extRepository.search(
			searchContext, query, extRepositoryQueryMapper);
	}

	@Override
	public ExtRepositoryFileEntry updateExtRepositoryFileEntry(
			String extRepositoryFileEntryKey, String mimeType,
			InputStream inputStream)
		throws PortalException {

		ExtRepositoryFileEntry extRepositoryFileEntry =
			_extRepository.updateExtRepositoryFileEntry(
				extRepositoryFileEntryKey, mimeType, inputStream);

		Map<String, ExtRepositoryObject> extRepositoryObjectCache =
			_extRepositoryObjectCache.get();

		extRepositoryObjectCache.put(
			extRepositoryFileEntryKey, extRepositoryFileEntry);

		Map<String, List<ExtRepositoryObject>> extRepositoryObjectsCache =
			_extRepositoryObjectsCache.get();

		extRepositoryObjectsCache.clear();

		Map<String, List<ExtRepositoryFileVersion>>
			extRepositoryFileVersionCache =
				_extRepositoryFileVersionCache.get();

		extRepositoryFileVersionCache.remove(extRepositoryFileEntryKey);

		return extRepositoryFileEntry;
	}

	private static final ThreadLocal
		<Map<String, List<ExtRepositoryFileVersion>>>
			_extRepositoryFileVersionCache = new AutoResetThreadLocal<>(
				"extRepositoryFileVersionCache", HashMap::new);
	private static final ThreadLocal<Map<String, ExtRepositoryObject>>
		_extRepositoryObjectCache = new AutoResetThreadLocal<>(
			"extRepositoryObjectCache", HashMap::new);
	private static final ThreadLocal<Map<String, List<ExtRepositoryObject>>>
		_extRepositoryObjectsCache = new AutoResetThreadLocal<>(
			"extRepositoryObjectsCache", HashMap::new);
	private static final ThreadLocal<Map<String, ExtRepositoryFolder>>
		_extRepositoryParentFolderCache = new AutoResetThreadLocal<>(
			"extRepositoryParentFolderCache", HashMap::new);

	private final ExtRepository _extRepository;

}