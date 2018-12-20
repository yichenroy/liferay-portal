/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.document.library.search.test;

import com.liferay.document.library.kernel.model.DLFileEntryMetadata;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.kernel.service.DLFileEntryTypeService;
import com.liferay.document.library.test.util.search.FileEntryBlueprint;
import com.liferay.document.library.test.util.search.FileEntrySearchFixture;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Igor Fabiano Nazar
 * @author Lucas Marques de Paula
 */
public class DLFileEntryMetadataDDMStructureFixture {

	public DLFileEntryMetadataDDMStructureFixture(
		DLFixture dlFixture, DLAppLocalService dlAppLocalService,
		DLFileEntryTypeService dlFileEntryTypeService) {

		_dlFixture = dlFixture;
		_dlFileEntryTypeService = dlFileEntryTypeService;

		_fileEntrySearchFixture = new FileEntrySearchFixture(dlAppLocalService);

		_fileEntrySearchFixture.setUp();
	}

	public void setUp() {
		_fileEntrySearchFixture.setUp();
	}

	public void tearDown() throws Exception {
		_fileEntrySearchFixture.tearDown();
		deleteAllFileEntryTypes();
		deleteAllDDMStructures();
	}

	protected DLFileEntryType addDLFileEntryType(
			Long groupId, DDMStructure ddmStructure)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		String name = RandomTestUtil.randomString();
		String description = StringPool.BLANK;
		long[] structureIds = {ddmStructure.getStructureId()};

		DLFileEntryType type = _dlFileEntryTypeService.addFileEntryType(
			groupId, name, description, structureIds, serviceContext);

		_fileEntryTypes.add(type);

		return type;
	}

	protected FileEntry addFileEntry(String fileName1, long fileEntryTypeId)
		throws IOException, PortalException {

		Class<?> clazz = getClass();

		try (InputStream inputStream1 = clazz.getResourceAsStream(
				"dependencies/" + fileName1)) {

			Map<String, Serializable> fileAttributes = new HashMap<>();

			fileAttributes.put("fileEntryTypeId", fileEntryTypeId);

			FileEntry fileEntry = _fileEntrySearchFixture.addFileEntry(
				new FileEntryBlueprint() {
					{
						fileName = fileName1;
						groupId = _dlFixture.getGroupId();
						inputStream = inputStream1;
						title = fileName1;
						userId = _dlFixture.getUserId();
						attributes = fileAttributes;
					}
				});

			return fileEntry;
		}
	}

	protected DDMStructure createStructureWithdDLFileEntry(
			String fileName, Locale locale)
		throws Exception {

		DDMForm ddmForm = DDMStructureTestUtil.getSampleDDMForm(
			new Locale[] {locale}, locale);

		long groupId = _dlFixture.getGroupId();

		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			groupId, DLFileEntryMetadata.class.getName(), ddmForm, locale);

		_ddmStructures.add(ddmStructure);

		DLFileEntryType dlFileEntryType = addDLFileEntryType(
			groupId, ddmStructure);

		addFileEntry(fileName, dlFileEntryType.getFileEntryTypeId());

		return ddmStructure;
	}

	protected void deleteAllDDMStructures() throws PortalException {
		for (DDMStructure ddmStructure : _ddmStructures) {
			long id = ddmStructure.getStructureId();

			DDMStructureLocalServiceUtil.deleteDDMStructure(id);
		}

		_ddmStructures.clear();
	}

	protected void deleteAllFileEntryTypes() throws PortalException {
		for (DLFileEntryType dlFileEntryType : _fileEntryTypes) {
			long id = dlFileEntryType.getFileEntryTypeId();

			_dlFileEntryTypeService.deleteFileEntryType(id);
		}

		_fileEntryTypes.clear();
	}

	private final List<DDMStructure> _ddmStructures = new ArrayList<>();
	private final DLFileEntryTypeService _dlFileEntryTypeService;
	private final DLFixture _dlFixture;
	private final FileEntrySearchFixture _fileEntrySearchFixture;
	private final List<DLFileEntryType> _fileEntryTypes = new ArrayList<>();

}