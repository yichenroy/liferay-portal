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

package com.liferay.osb.koroneiki.taproot.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the TeamRole service. Represents a row in the &quot;Koroneiki_TeamRole&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see TeamRoleModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.osb.koroneiki.taproot.model.impl.TeamRoleImpl"
)
@ProviderType
public interface TeamRole extends PersistedModel, TeamRoleModel {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.osb.koroneiki.taproot.model.impl.TeamRoleImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<TeamRole, Long> TEAM_ROLE_ID_ACCESSOR =
		new Accessor<TeamRole, Long>() {

			@Override
			public Long get(TeamRole teamRole) {
				return teamRole.getTeamRoleId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<TeamRole> getTypeClass() {
				return TeamRole.class;
			}

		};
	public static final Accessor<TeamRole, String> NAME_ACCESSOR =
		new Accessor<TeamRole, String>() {

			@Override
			public String get(TeamRole teamRole) {
				return teamRole.getName();
			}

			@Override
			public Class<String> getAttributeClass() {
				return String.class;
			}

			@Override
			public Class<TeamRole> getTypeClass() {
				return TeamRole.class;
			}

		};

	public String getTypeLabel();

}