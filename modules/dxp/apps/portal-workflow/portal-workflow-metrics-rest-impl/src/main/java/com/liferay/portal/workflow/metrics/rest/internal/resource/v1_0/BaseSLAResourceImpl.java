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

package com.liferay.portal.workflow.metrics.rest.internal.resource.v1_0;

import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.TransformUtil;
import com.liferay.portal.workflow.metrics.rest.dto.v1_0.SLA;
import com.liferay.portal.workflow.metrics.rest.resource.v1_0.SLAResource;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.annotation.Generated;

import javax.validation.constraints.NotNull;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

/**
 * @author Rafael Praxedes
 * @generated
 */
@Generated("")
@Path("/v1.0")
public abstract class BaseSLAResourceImpl implements SLAResource {

	@Override
	@GET
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize")
		}
	)
	@Path("/processes/{processId}/slas")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "SLA")})
	public Page<SLA> getProcessSLAsPage(
			@NotNull @PathParam("processId") Long processId,
			@Context Pagination pagination)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	@Override
	@Consumes("application/json")
	@POST
	@Path("/processes/{processId}/slas")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "SLA")})
	public SLA postProcessSLA(
			@NotNull @PathParam("processId") Long processId, SLA sla)
		throws Exception {

		return new SLA();
	}

	@Override
	@DELETE
	@Path("/slas/{slaId}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "SLA")})
	public void deleteSLA(@NotNull @PathParam("slaId") Long slaId)
		throws Exception {
	}

	@Override
	@GET
	@Path("/slas/{slaId}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "SLA")})
	public SLA getSLA(@NotNull @PathParam("slaId") Long slaId)
		throws Exception {

		return new SLA();
	}

	@Override
	@Consumes("application/json")
	@PUT
	@Path("/slas/{slaId}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "SLA")})
	public SLA putSLA(@NotNull @PathParam("slaId") Long slaId, SLA sla)
		throws Exception {

		return new SLA();
	}

	public void setContextCompany(Company contextCompany) {
		this.contextCompany = contextCompany;
	}

	protected void preparePatch(SLA sla) {
	}

	protected <T, R> List<R> transform(
		Collection<T> collection,
		UnsafeFunction<T, R, Exception> unsafeFunction) {

		return TransformUtil.transform(collection, unsafeFunction);
	}

	protected <T, R> R[] transform(
		T[] array, UnsafeFunction<T, R, Exception> unsafeFunction,
		Class<?> clazz) {

		return TransformUtil.transform(array, unsafeFunction, clazz);
	}

	protected <T, R> R[] transformToArray(
		Collection<T> collection,
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

}