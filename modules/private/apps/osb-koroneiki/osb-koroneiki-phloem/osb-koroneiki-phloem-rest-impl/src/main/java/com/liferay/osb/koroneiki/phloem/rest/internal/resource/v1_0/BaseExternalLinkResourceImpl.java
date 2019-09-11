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

import com.liferay.osb.koroneiki.phloem.rest.dto.v1_0.ExternalLink;
import com.liferay.osb.koroneiki.phloem.rest.resource.v1_0.ExternalLinkResource;
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

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
public abstract class BaseExternalLinkResourceImpl
	implements ExternalLinkResource {

	@Override
	@GET
	@Operation(description = "Retrieves the account's external links.")
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "accountKey"),
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize")
		}
	)
	@Path("/accounts/{accountKey}/external-links")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "ExternalLink")})
	public Page<ExternalLink> getAccountAccountKeyExternalLinksPage(
			@NotNull @Parameter(hidden = true) @PathParam("accountKey") String
				accountKey,
			@Context Pagination pagination)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	@Override
	@Consumes({"application/json", "application/xml"})
	@Operation(description = "Adds an external link to the account.")
	@POST
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "accountKey")}
	)
	@Path("/accounts/{accountKey}/external-links")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "ExternalLink")})
	public ExternalLink postAccountAccountKeyExternalLink(
			@NotNull @Parameter(hidden = true) @PathParam("accountKey") String
				accountKey,
			ExternalLink externalLink)
		throws Exception {

		return new ExternalLink();
	}

	@Override
	@GET
	@Operation(description = "Retrieves the contact's external links.")
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "oktaId"),
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize")
		}
	)
	@Path("/contacts/by-okta-id/{oktaId}/external-links")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "ExternalLink")})
	public Page<ExternalLink> getContactByOktaExternalLinksPage(
			@NotNull @Parameter(hidden = true) @PathParam("oktaId") String
				oktaId,
			@Context Pagination pagination)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	@Override
	@Consumes({"application/json", "application/xml"})
	@Operation(description = "Adds an external link to the contact.")
	@POST
	@Parameters(value = {@Parameter(in = ParameterIn.PATH, name = "oktaId")})
	@Path("/contacts/by-okta-id/{oktaId}/external-links")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "ExternalLink")})
	public ExternalLink postContactByOktaExternalLink(
			@NotNull @Parameter(hidden = true) @PathParam("oktaId") String
				oktaId,
			ExternalLink externalLink)
		throws Exception {

		return new ExternalLink();
	}

	@Override
	@GET
	@Operation(description = "Retrieves the contact's external links.")
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "uuid"),
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize")
		}
	)
	@Path("/contacts/by-uuid/{uuid}/external-links")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "ExternalLink")})
	public Page<ExternalLink> getContactByUuidExternalLinksPage(
			@NotNull @Parameter(hidden = true) @PathParam("uuid") String uuid,
			@Context Pagination pagination)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	@Override
	@Consumes({"application/json", "application/xml"})
	@Operation(description = "Adds an external link to the contact.")
	@POST
	@Parameters(value = {@Parameter(in = ParameterIn.PATH, name = "uuid")})
	@Path("/contacts/by-uuid/{uuid}/external-links")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "ExternalLink")})
	public ExternalLink postContactByUuidExternalLink(
			@NotNull @Parameter(hidden = true) @PathParam("uuid") String uuid,
			ExternalLink externalLink)
		throws Exception {

		return new ExternalLink();
	}

	@Override
	@DELETE
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "externalLinkKey")}
	)
	@Path("/external-links/{externalLinkKey}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "ExternalLink")})
	public void deleteExternalLink(
			@NotNull @Parameter(hidden = true) @PathParam("externalLinkKey")
				String externalLinkKey)
		throws Exception {
	}

	@Override
	@GET
	@Operation(description = "Retrieves the external link.")
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "externalLinkKey")}
	)
	@Path("/external-links/{externalLinkKey}")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "ExternalLink")})
	public ExternalLink getExternalLink(
			@NotNull @Parameter(hidden = true) @PathParam("externalLinkKey")
				String externalLinkKey)
		throws Exception {

		return new ExternalLink();
	}

	@Override
	@GET
	@Operation(
		description = "Retrieves the product consumption's external links."
	)
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "productConsumptionKey"),
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize")
		}
	)
	@Path("/product-consumptions/{productConsumptionKey}/external-links")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "ExternalLink")})
	public Page<ExternalLink>
			getProductConsumptionProductConsumptionKeyExternalLinksPage(
				@NotNull @Parameter(hidden = true)
				@PathParam("productConsumptionKey") String
					productConsumptionKey,
				@Context Pagination pagination)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	@Override
	@Consumes({"application/json", "application/xml"})
	@Operation(
		description = "Adds an external link to the product consumption."
	)
	@POST
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "productConsumptionKey")
		}
	)
	@Path("/product-consumptions/{productConsumptionKey}/external-links")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "ExternalLink")})
	public ExternalLink postProductConsumptionProductConsumptionKeyExternalLink(
			@NotNull @Parameter(hidden = true)
			@PathParam("productConsumptionKey") String productConsumptionKey,
			ExternalLink externalLink)
		throws Exception {

		return new ExternalLink();
	}

	@Override
	@GET
	@Operation(description = "Retrieves the product purchase's external links.")
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "productPurchaseKey"),
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize")
		}
	)
	@Path("/product-purchases/{productPurchaseKey}/external-links")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "ExternalLink")})
	public Page<ExternalLink>
			getProductPurchaseProductPurchaseKeyExternalLinksPage(
				@NotNull @Parameter(hidden = true)
				@PathParam("productPurchaseKey") String productPurchaseKey,
				@Context Pagination pagination)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	@Override
	@Consumes({"application/json", "application/xml"})
	@Operation(description = "Adds an external link to the product purchase.")
	@POST
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "productPurchaseKey")}
	)
	@Path("/product-purchases/{productPurchaseKey}/external-links")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "ExternalLink")})
	public ExternalLink postProductPurchaseProductPurchaseKeyExternalLink(
			@NotNull @Parameter(hidden = true) @PathParam("productPurchaseKey")
				String productPurchaseKey,
			ExternalLink externalLink)
		throws Exception {

		return new ExternalLink();
	}

	@Override
	@GET
	@Operation(description = "Retrieves the product's external links.")
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "productKey"),
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize")
		}
	)
	@Path("/products/{productKey}/external-links")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "ExternalLink")})
	public Page<ExternalLink> getProductProductKeyExternalLinksPage(
			@NotNull @Parameter(hidden = true) @PathParam("productKey") String
				productKey,
			@Context Pagination pagination)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	@Override
	@Consumes({"application/json", "application/xml"})
	@Operation(description = "Adds an external link to the product.")
	@POST
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "productKey")}
	)
	@Path("/products/{productKey}/external-links")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "ExternalLink")})
	public ExternalLink postProductProductKeyExternalLink(
			@NotNull @Parameter(hidden = true) @PathParam("productKey") String
				productKey,
			ExternalLink externalLink)
		throws Exception {

		return new ExternalLink();
	}

	@Override
	@GET
	@Operation(description = "Retrieves the team's external links.")
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "teamKey"),
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize")
		}
	)
	@Path("/teams/{teamKey}/external-links")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "ExternalLink")})
	public Page<ExternalLink> getTeamTeamKeyExternalLinksPage(
			@NotNull @Parameter(hidden = true) @PathParam("teamKey") String
				teamKey,
			@Context Pagination pagination)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	@Override
	@Consumes({"application/json", "application/xml"})
	@Operation(description = "Adds an external link to the team.")
	@POST
	@Parameters(value = {@Parameter(in = ParameterIn.PATH, name = "teamKey")})
	@Path("/teams/{teamKey}/external-links")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "ExternalLink")})
	public ExternalLink postTeamTeamKeyExternalLink(
			@NotNull @Parameter(hidden = true) @PathParam("teamKey") String
				teamKey,
			ExternalLink externalLink)
		throws Exception {

		return new ExternalLink();
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
		ExternalLink externalLink, ExternalLink existingExternalLink) {
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