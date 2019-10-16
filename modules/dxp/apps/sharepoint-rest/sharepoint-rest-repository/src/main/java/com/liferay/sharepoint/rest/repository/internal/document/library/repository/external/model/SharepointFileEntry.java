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

package com.liferay.sharepoint.rest.repository.internal.document.library.repository.external.model;

import com.liferay.document.library.repository.external.ExtRepositoryFileEntry;
import com.liferay.document.library.repository.external.ExtRepositoryFileVersion;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.sharepoint.rest.repository.internal.util.SharepointURLHelper;

import java.util.Date;

/**
 * @author Adolfo Pérez
 */
public class SharepointFileEntry
	implements ExtRepositoryFileEntry, SharepointModel {

	public SharepointFileEntry(
		String extRepositoryModelKey, String name, String title,
		Date createDate, Date modifiedDate, long size,
		String fileVersionExtRepositoryModelKey, String version, String owner,
		String checkedOutBy, long effectiveBasePermissionsBits,
		SharepointURLHelper sharepointURLHelper) {

		_extRepositoryModelKey = extRepositoryModelKey;
		_name = name;
		_title = title;
		_createDate = createDate;
		_modifiedDate = modifiedDate;
		_size = size;
		_fileVersionExtRepositoryModelKey = fileVersionExtRepositoryModelKey;
		_version = version;
		_owner = owner;
		_checkedOutBy = checkedOutBy;
		_effectiveBasePermissionsBits = effectiveBasePermissionsBits;
		_sharepointURLHelper = sharepointURLHelper;
	}

	@Override
	public ExtRepositoryFileVersion asFileVersion() {
		if (_extRepositoryFileVersion == null) {
			_extRepositoryFileVersion = new SharepointFileEntryFileVersion(
				this, _fileVersionExtRepositoryModelKey, _version);
		}

		return _extRepositoryFileVersion;
	}

	@Override
	public boolean containsPermission(
		ExtRepositoryPermission extRepositoryPermission) {

		if ((extRepositoryPermission == ExtRepositoryPermission.ACCESS) ||
			(extRepositoryPermission ==
				ExtRepositoryPermission.ADD_DISCUSSION) ||
			(extRepositoryPermission == ExtRepositoryPermission.ADD_DOCUMENT) ||
			(extRepositoryPermission == ExtRepositoryPermission.ADD_FOLDER) ||
			(extRepositoryPermission == ExtRepositoryPermission.ADD_SHORTCUT) ||
			(extRepositoryPermission ==
				ExtRepositoryPermission.ADD_SUBFOLDER) ||
			(extRepositoryPermission ==
				ExtRepositoryPermission.DELETE_DISCUSSION) ||
			(extRepositoryPermission == ExtRepositoryPermission.PERMISSIONS) ||
			(extRepositoryPermission ==
				ExtRepositoryPermission.UPDATE_DISCUSSION)) {

			return false;
		}

		int bitIndex = 0;

		if (extRepositoryPermission == ExtRepositoryPermission.DELETE) {
			bitIndex = 4;
		}
		else if (extRepositoryPermission == ExtRepositoryPermission.UPDATE) {
			bitIndex = 3;
		}
		else if (extRepositoryPermission == ExtRepositoryPermission.VIEW) {
			bitIndex = 1;
		}

		if (bitIndex == 0) {
			return false;
		}

		if ((_effectiveBasePermissionsBits & (1 << (bitIndex - 1))) == 0) {
			return false;
		}

		return true;
	}

	@Override
	public String getCanonicalContentURL() {
		return _sharepointURLHelper.getFileEntryContentURL(this);
	}

	@Override
	public String getCheckedOutBy() {
		return _checkedOutBy;
	}

	@Override
	public Date getCreateDate() {
		return _createDate;
	}

	@Override
	public String getDescription() {
		return StringPool.BLANK;
	}

	@Override
	public String getExtension() {
		return FileUtil.getExtension(_name);
	}

	@Override
	public ExtRepositoryFileEntry getExtRepositoryFileEntry() {
		return this;
	}

	@Override
	public String getExtRepositoryModelKey() {
		return _extRepositoryModelKey;
	}

	@Override
	public String getMimeType() {
		return MimeTypesUtil.getContentType(_name);
	}

	@Override
	public Date getModifiedDate() {
		return _modifiedDate;
	}

	@Override
	public String getOwner() {
		return _owner;
	}

	@Override
	public long getSize() {
		return _size;
	}

	@Override
	public String getTitle() {
		return _title;
	}

	private final String _checkedOutBy;
	private final Date _createDate;
	private final long _effectiveBasePermissionsBits;
	private ExtRepositoryFileVersion _extRepositoryFileVersion;
	private final String _extRepositoryModelKey;
	private final String _fileVersionExtRepositoryModelKey;
	private final Date _modifiedDate;
	private final String _name;
	private final String _owner;
	private final SharepointURLHelper _sharepointURLHelper;
	private final long _size;
	private final String _title;
	private final String _version;

}