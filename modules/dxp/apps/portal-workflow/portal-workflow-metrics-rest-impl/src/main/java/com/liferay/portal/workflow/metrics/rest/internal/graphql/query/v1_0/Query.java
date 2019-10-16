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

package com.liferay.portal.workflow.metrics.rest.internal.graphql.query.v1_0;

import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.workflow.metrics.rest.dto.v1_0.Node;
import com.liferay.portal.workflow.metrics.rest.dto.v1_0.Process;
import com.liferay.portal.workflow.metrics.rest.dto.v1_0.SLA;
import com.liferay.portal.workflow.metrics.rest.resource.v1_0.NodeResource;
import com.liferay.portal.workflow.metrics.rest.resource.v1_0.ProcessResource;
import com.liferay.portal.workflow.metrics.rest.resource.v1_0.SLAResource;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLInvokeDetached;
import graphql.annotations.annotationTypes.GraphQLName;

import java.util.Collection;

import javax.annotation.Generated;

import org.osgi.service.component.ComponentServiceObjects;

/**
 * @author Rafael Praxedes
 * @generated
 */
@Generated("")
public class Query {

	public static void setNodeResourceComponentServiceObjects(
		ComponentServiceObjects<NodeResource>
			nodeResourceComponentServiceObjects) {

		_nodeResourceComponentServiceObjects =
			nodeResourceComponentServiceObjects;
	}

	public static void setProcessResourceComponentServiceObjects(
		ComponentServiceObjects<ProcessResource>
			processResourceComponentServiceObjects) {

		_processResourceComponentServiceObjects =
			processResourceComponentServiceObjects;
	}

	public static void setSLAResourceComponentServiceObjects(
		ComponentServiceObjects<SLAResource>
			slaResourceComponentServiceObjects) {

		_slaResourceComponentServiceObjects =
			slaResourceComponentServiceObjects;
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<Node> getProcessNodesPage(
			@GraphQLName("processId") Long processId)
		throws Exception {

		return _applyComponentServiceObjects(
			_nodeResourceComponentServiceObjects,
			this::_populateResourceContext,
			nodeResource -> {
				Page paginationPage = nodeResource.getProcessNodesPage(
					processId);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<Process> getProcessesPage(
			@GraphQLName("title") String title,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page, @GraphQLName("Sort[]") Sort[] sorts)
		throws Exception {

		return _applyComponentServiceObjects(
			_processResourceComponentServiceObjects,
			this::_populateResourceContext,
			processResource -> {
				Page paginationPage = processResource.getProcessesPage(
					title, Pagination.of(pageSize, page), sorts);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Process getProcess(@GraphQLName("processId") Long processId)
		throws Exception {

		return _applyComponentServiceObjects(
			_processResourceComponentServiceObjects,
			this::_populateResourceContext,
			processResource -> processResource.getProcess(processId));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<SLA> getProcessSLAsPage(
			@GraphQLName("processId") Long processId,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_slaResourceComponentServiceObjects, this::_populateResourceContext,
			slaResource -> {
				Page paginationPage = slaResource.getProcessSLAsPage(
					processId, Pagination.of(pageSize, page));

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public SLA getSLA(@GraphQLName("slaId") Long slaId) throws Exception {
		return _applyComponentServiceObjects(
			_slaResourceComponentServiceObjects, this::_populateResourceContext,
			slaResource -> slaResource.getSLA(slaId));
	}

	private <T, R, E1 extends Throwable, E2 extends Throwable> R
			_applyComponentServiceObjects(
				ComponentServiceObjects<T> componentServiceObjects,
				UnsafeConsumer<T, E1> unsafeConsumer,
				UnsafeFunction<T, R, E2> unsafeFunction)
		throws E1, E2 {

		T resource = componentServiceObjects.getService();

		try {
			unsafeConsumer.accept(resource);

			return unsafeFunction.apply(resource);
		}
		finally {
			componentServiceObjects.ungetService(resource);
		}
	}

	private void _populateResourceContext(NodeResource nodeResource)
		throws Exception {

		nodeResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));
	}

	private void _populateResourceContext(ProcessResource processResource)
		throws Exception {

		processResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));
	}

	private void _populateResourceContext(SLAResource slaResource)
		throws Exception {

		slaResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));
	}

	private static ComponentServiceObjects<NodeResource>
		_nodeResourceComponentServiceObjects;
	private static ComponentServiceObjects<ProcessResource>
		_processResourceComponentServiceObjects;
	private static ComponentServiceObjects<SLAResource>
		_slaResourceComponentServiceObjects;

}