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

package com.liferay.portal.reports.engine.console.model;

import org.osgi.annotation.versioning.ProviderType;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the Definition service. Represents a row in the &quot;Reports_Definition&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see DefinitionModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.portal.reports.engine.console.model.impl.DefinitionImpl"
)
@ProviderType
public interface Definition extends DefinitionModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.portal.reports.engine.console.model.impl.DefinitionImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<Definition, Long> DEFINITION_ID_ACCESSOR =
		new Accessor<Definition, Long>() {

			@Override
			public Long get(Definition definition) {
				return definition.getDefinitionId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<Definition> getTypeClass() {
				return Definition.class;
			}

		};

	public String getAttachmentsDir();

	public String[] getAttachmentsFiles()
		throws com.liferay.portal.kernel.exception.PortalException;

}