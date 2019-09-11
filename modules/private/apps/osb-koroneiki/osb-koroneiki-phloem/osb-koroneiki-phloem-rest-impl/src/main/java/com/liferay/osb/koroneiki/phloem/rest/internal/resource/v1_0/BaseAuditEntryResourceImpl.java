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

package com.liferay.osb.koroneiki.phloem.rest.internal.resource.v1_0;

import com.liferay.osb.koroneiki.phloem.rest.dto.v1_0.AuditEntry;
import com.liferay.osb.koroneiki.phloem.rest.resource.v1_0.AuditEntryResource;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.TransformUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;

import java.util.Collections;
import java.util.List;

import javax.annotation.Generated;

import javax.validation.constraints.NotNull;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

/**
 * @author Amos Fong
 * @generated
 */
@Generated("")
@Path("/v1.0")
public abstract class BaseAuditEntryResourceImpl implements AuditEntryResource {

	@Override
	@GET
	@Operation(description = "Retrieves the account's audit history.")
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "accountKey"),
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize")
		}
	)
	@Path("/accounts/{accountKey}/audit-entries")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "AuditEntry")})
	public Page<AuditEntry> getAccountAccountKeyAuditEntriesPage(
			@NotNull @Parameter(hidden = true) @PathParam("accountKey") String
				accountKey,
			@Context Pagination pagination)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	@Override
	@GET
	@Operation(description = "Retrieves the audit entry.")
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "auditEntryKey")}
	)
	@Path("/audit-entries/{auditEntryKey}")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "AuditEntry")})
	public AuditEntry getAuditEntry(
			@NotNull @Parameter(hidden = true) @PathParam("auditEntryKey")
				String auditEntryKey)
		throws Exception {

		return new AuditEntry();
	}

	@Override
	@GET
	@Operation(description = "Retrieves the contact role's audit history.")
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "contactRoleKey"),
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize")
		}
	)
	@Path("/contact-roles/{contactRoleKey}/audit-entries")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "AuditEntry")})
	public Page<AuditEntry> getContactRoleContactRoleKeyAuditEntriesPage(
			@NotNull @Parameter(hidden = true) @PathParam("contactRoleKey")
				String contactRoleKey,
			@Context Pagination pagination)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	@Override
	@GET
	@Operation(description = "Retrieves the contact's audit history.")
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "oktaId"),
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize")
		}
	)
	@Path("/contacts/by-okta-id/{oktaId}/audit-entries")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "AuditEntry")})
	public Page<AuditEntry> getContactByOktaAuditEntriesPage(
			@NotNull @Parameter(hidden = true) @PathParam("oktaId") String
				oktaId,
			@Context Pagination pagination)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	@Override
	@GET
	@Operation(description = "Retrieves the contact's audit history.")
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "uuid"),
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize")
		}
	)
	@Path("/contacts/by-uuid/{uuid}/audit-entries")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "AuditEntry")})
	public Page<AuditEntry> getContactByUuidAuditEntriesPage(
			@NotNull @Parameter(hidden = true) @PathParam("uuid") String uuid,
			@Context Pagination pagination)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	@Override
	@GET
	@Operation(description = "Retrieves the team role's audit history.")
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "teamRoleKey"),
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize")
		}
	)
	@Path("/team-roles/{teamRoleKey}/audit-entries")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "AuditEntry")})
	public Page<AuditEntry> getTeamRoleTeamRoleKeyAuditEntriesPage(
			@NotNull @Parameter(hidden = true) @PathParam("teamRoleKey") String
				teamRoleKey,
			@Context Pagination pagination)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	@Override
	@GET
	@Operation(description = "Retrieves the team's audit history.")
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "teamKey"),
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize")
		}
	)
	@Path("/teams/{teamKey}/audit-entries")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "AuditEntry")})
	public Page<AuditEntry> getTeamTeamKeyAuditEntriesPage(
			@NotNull @Parameter(hidden = true) @PathParam("teamKey") String
				teamKey,
			@Context Pagination pagination)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	public void setContextAcceptLanguage(AcceptLanguage contextAcceptLanguage) {
		this.contextAcceptLanguage = contextAcceptLanguage;
	}

	public void setContextCompany(Company contextCompany) {
		this.contextCompany = contextCompany;
	}

	public void setContextUser(User contextUser) {
		this.contextUser = contextUser;
	}

	protected void preparePatch(
		AuditEntry auditEntry, AuditEntry existingAuditEntry) {
	}

	protected <T, R> List<R> transform(
		java.util.Collection<T> collection,
		UnsafeFunction<T, R, Exception> unsafeFunction) {

		return TransformUtil.transform(collection, unsafeFunction);
	}

	protected <T, R> R[] transform(
		T[] array, UnsafeFunction<T, R, Exception> unsafeFunction,
		Class<?> clazz) {

		return TransformUtil.transform(array, unsafeFunction, clazz);
	}

	protected <T, R> R[] transformToArray(
		java.util.Collection<T> collection,
		UnsafeFunction<T, R, Exception> unsafeFunction, Class<?> clazz) {

		return TransformUtil.transformToArray(
			collection, unsafeFunction, clazz);
	}

	protected <T, R> List<R> transformToList(
		T[] array, UnsafeFunction<T, R, Exception> unsafeFunction) {

		return TransformUtil.transformToList(array, unsafeFunction);
	}

	@Context
	protected AcceptLanguage contextAcceptLanguage;

	@Context
	protected Company contextCompany;

	@Context
	protected UriInfo contextUriInfo;

	@Context
	protected User contextUser;

}