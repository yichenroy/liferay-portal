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

package com.liferay.portal.vulcan.internal.graphql.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.NoSuchModelException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.filter.ExpressionConvert;
import com.liferay.portal.odata.filter.FilterParserProvider;
import com.liferay.portal.odata.sort.SortParserProvider;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLTypeExtension;
import com.liferay.portal.vulcan.graphql.contributor.GraphQLContributor;
import com.liferay.portal.vulcan.graphql.servlet.ServletData;
import com.liferay.portal.vulcan.internal.accept.language.AcceptLanguageImpl;
import com.liferay.portal.vulcan.internal.configuration.VulcanConfiguration;
import com.liferay.portal.vulcan.internal.configuration.util.ConfigurationUtil;
import com.liferay.portal.vulcan.internal.jaxrs.context.provider.ContextProviderUtil;
import com.liferay.portal.vulcan.internal.jaxrs.context.provider.FilterContextProvider;
import com.liferay.portal.vulcan.internal.jaxrs.context.provider.SortContextProvider;
import com.liferay.portal.vulcan.internal.jaxrs.param.converter.provider.SiteParamConverterProvider;
import com.liferay.portal.vulcan.internal.jaxrs.validation.ValidationUtil;
import com.liferay.portal.vulcan.internal.multipart.MultipartUtil;
import com.liferay.portal.vulcan.multipart.BinaryFile;
import com.liferay.portal.vulcan.multipart.MultipartBody;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import graphql.ExceptionWhileDataFetching;
import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.Scalars;
import graphql.TypeResolutionEnvironment;

import graphql.annotations.annotationTypes.GraphQLTypeResolver;
import graphql.annotations.annotationTypes.GraphQLUnion;
import graphql.annotations.directives.DirectiveWirer;
import graphql.annotations.directives.DirectiveWiringMapRetriever;
import graphql.annotations.processor.ProcessingElementsContainer;
import graphql.annotations.processor.exceptions.CannotCastMemberException;
import graphql.annotations.processor.exceptions.GraphQLAnnotationsException;
import graphql.annotations.processor.graphQLProcessors.GraphQLInputProcessor;
import graphql.annotations.processor.graphQLProcessors.GraphQLOutputProcessor;
import graphql.annotations.processor.retrievers.GraphQLExtensionsHandler;
import graphql.annotations.processor.retrievers.GraphQLFieldRetriever;
import graphql.annotations.processor.retrievers.GraphQLInterfaceRetriever;
import graphql.annotations.processor.retrievers.GraphQLObjectInfoRetriever;
import graphql.annotations.processor.retrievers.GraphQLTypeRetriever;
import graphql.annotations.processor.retrievers.fieldBuilders.ArgumentBuilder;
import graphql.annotations.processor.retrievers.fieldBuilders.DeprecateBuilder;
import graphql.annotations.processor.retrievers.fieldBuilders.DirectivesBuilder;
import graphql.annotations.processor.retrievers.fieldBuilders.field.FieldNameBuilder;
import graphql.annotations.processor.retrievers.fieldBuilders.method.MethodNameBuilder;
import graphql.annotations.processor.retrievers.fieldBuilders.method.MethodTypeBuilder;
import graphql.annotations.processor.searchAlgorithms.BreadthFirstSearch;
import graphql.annotations.processor.searchAlgorithms.ParentalSearch;
import graphql.annotations.processor.typeBuilders.EnumBuilder;
import graphql.annotations.processor.typeBuilders.InputObjectBuilder;
import graphql.annotations.processor.typeBuilders.InterfaceBuilder;
import graphql.annotations.processor.typeBuilders.OutputObjectBuilder;
import graphql.annotations.processor.typeBuilders.UnionBuilder;
import graphql.annotations.processor.typeFunctions.DefaultTypeFunction;
import graphql.annotations.processor.typeFunctions.TypeFunction;
import graphql.annotations.processor.util.NamingKit;
import graphql.annotations.processor.util.ReflectionKit;

import graphql.execution.AsyncExecutionStrategy;
import graphql.execution.DataFetcherExceptionHandler;
import graphql.execution.DataFetcherExceptionHandlerParameters;
import graphql.execution.DataFetcherExceptionHandlerResult;
import graphql.execution.ExecutionStrategy;

import graphql.language.ArrayValue;
import graphql.language.BooleanValue;
import graphql.language.EnumValue;
import graphql.language.FloatValue;
import graphql.language.IntValue;
import graphql.language.NullValue;
import graphql.language.ObjectField;
import graphql.language.ObjectValue;
import graphql.language.StringValue;
import graphql.language.Value;

import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.DataFetchingEnvironmentImpl;
import graphql.schema.FieldCoordinates;
import graphql.schema.GraphQLArgument;
import graphql.schema.GraphQLCodeRegistry;
import graphql.schema.GraphQLDirectiveContainer;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLFieldsContainer;
import graphql.schema.GraphQLInputType;
import graphql.schema.GraphQLInterfaceType;
import graphql.schema.GraphQLList;
import graphql.schema.GraphQLNonNull;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLOutputType;
import graphql.schema.GraphQLScalarType;
import graphql.schema.GraphQLSchema;
import graphql.schema.GraphQLType;
import graphql.schema.GraphQLTypeReference;
import graphql.schema.PropertyDataFetcher;
import graphql.schema.TypeResolver;

import graphql.servlet.ApolloScalars;
import graphql.servlet.DefaultExecutionStrategyProvider;
import graphql.servlet.ExecutionStrategyProvider;
import graphql.servlet.GraphQLConfiguration;
import graphql.servlet.GraphQLContext;
import graphql.servlet.GraphQLErrorHandler;
import graphql.servlet.GraphQLHttpServlet;
import graphql.servlet.GraphQLObjectMapper;
import graphql.servlet.GraphQLQueryInvoker;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import javax.validation.ValidationException;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import javax.xml.bind.DatatypeConverter;

import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.jaxrs.impl.UriInfoImpl;
import org.apache.cxf.message.ExchangeImpl;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageImpl;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.http.context.ServletContextHelper;
import org.osgi.service.http.whiteboard.HttpWhiteboardConstants;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Preston Crary
 */
@Component(immediate = true, service = {})
public class GraphQLServletExtender {

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_graphQLFieldRetriever = new LiferayGraphQLFieldRetriever();

		GraphQLInterfaceRetriever graphQLInterfaceRetriever =
			new GraphQLInterfaceRetriever();

		GraphQLObjectInfoRetriever graphQLObjectInfoRetriever =
			new GraphQLObjectInfoRetriever() {

				@Override
				public String getTypeName(Class<?> objectClass) {
					String graphQLName = _getGraphQLNameValue(objectClass);

					if (graphQLName == null) {
						return NamingKit.toGraphqlName(objectClass.getName());
					}

					return NamingKit.toGraphqlName(graphQLName);
				}

				public Boolean isGraphQLField(AnnotatedElement element) {
					GraphQLField graphQLField = element.getAnnotation(
						GraphQLField.class);

					if (graphQLField == null) {
						return false;
					}

					return graphQLField.value();
				}

			};

		BreadthFirstSearch breadthFirstSearch = new BreadthFirstSearch(
			graphQLObjectInfoRetriever);
		ParentalSearch parentalSearch = new ParentalSearch(
			graphQLObjectInfoRetriever);

		GraphQLExtensionsHandler graphQLExtensionsHandler =
			new GraphQLExtensionsHandler() {
				{
					setFieldRetriever(_graphQLFieldRetriever);
					setFieldSearchAlgorithm(parentalSearch);
					setGraphQLObjectInfoRetriever(graphQLObjectInfoRetriever);
					setMethodSearchAlgorithm(breadthFirstSearch);
				}
			};

		GraphQLTypeRetriever graphQLTypeRetriever = new GraphQLTypeRetriever() {

			public GraphQLType getGraphQLType(
					Class<?> clazz,
					ProcessingElementsContainer processingElementsContainer,
					boolean input)
				throws CannotCastMemberException, GraphQLAnnotationsException {

				Map<String, GraphQLType> graphQLTypes =
					processingElementsContainer.getTypeRegistry();

				String typeName = _getTypeName(
					input, processingElementsContainer,
					graphQLObjectInfoRetriever.getTypeName(clazz));

				GraphQLType graphQLType = graphQLTypes.get(typeName);

				if ((graphQLType != null) &&
					!_typeKeys.contains(clazz.getName())) {

					String name = clazz.getName();

					name = name.replaceAll("\\.", "_");

					typeName = _getTypeName(
						input, processingElementsContainer,
						StringUtil.replace(name, '$', '_'));
				}

				Stack<String> processingStack =
					processingElementsContainer.getProcessing();

				if (processingStack.contains(typeName)) {
					return new GraphQLTypeReference(typeName);
				}

				graphQLType = graphQLTypes.get(typeName);

				if (graphQLType != null) {
					return graphQLType;
				}

				processingStack.push(typeName);

				_typeKeys.add(clazz.getName());

				if (clazz.getAnnotation(GraphQLUnion.class) != null) {
					graphQLType = new UnionBuilder(
						graphQLObjectInfoRetriever
					).getUnionBuilder(
						clazz, processingElementsContainer
					).build();
				}
				else if (clazz.isAnnotationPresent(
							GraphQLTypeResolver.class)) {

					graphQLType = new InterfaceBuilder(
						graphQLObjectInfoRetriever, _graphQLFieldRetriever,
						graphQLExtensionsHandler
					).getInterfaceBuilder(
						clazz, processingElementsContainer
					).build();
				}
				else if (Enum.class.isAssignableFrom(clazz)) {
					graphQLType = new EnumBuilder(
						graphQLObjectInfoRetriever
					).getEnumBuilder(
						clazz
					).build();
				}
				else {
					if (input) {
						graphQLType = new InputObjectBuilder(
							graphQLObjectInfoRetriever, parentalSearch,
							breadthFirstSearch, _graphQLFieldRetriever
						).getInputObjectBuilder(
							clazz, processingElementsContainer
						).build();
					}
					else {
						graphQLType = new OutputObjectBuilder(
							graphQLObjectInfoRetriever, parentalSearch,
							breadthFirstSearch, _graphQLFieldRetriever,
							graphQLInterfaceRetriever, graphQLExtensionsHandler
						).getOutputObjectBuilder(
							clazz, processingElementsContainer
						).build();
					}
				}

				if (!StringUtil.equals(graphQLType.getName(), typeName)) {
					if (!_typesAreEqual(
							graphQLTypes.get(graphQLType.getName()),
							graphQLType)) {

						try {
							Class<? extends GraphQLType> graphQLTypeClass =
								graphQLType.getClass();

							Field field = graphQLTypeClass.getDeclaredField("name");

							field.setAccessible(true);

							field.set(graphQLType, typeName);
						}
						catch (Exception exception) {
							if (_log.isDebugEnabled()) {
								_log.debug(exception, exception);
							}
						}
					}
					else {
						graphQLType = graphQLTypes.get(graphQLType.getName());
					}
				}

				DirectiveWirer directiveWirer = new DirectiveWirer();

				DirectiveWiringMapRetriever directiveWiringMapRetriever =
					new DirectiveWiringMapRetriever();

				graphQLType = directiveWirer.wire(
					(GraphQLDirectiveContainer)graphQLType,
					directiveWiringMapRetriever.getDirectiveWiringMap(
						clazz, processingElementsContainer),
					processingElementsContainer.getCodeRegistryBuilder(), null);

				graphQLTypes.put(graphQLType.getName(), graphQLType);

				processingStack.pop();

				return graphQLType;
			}

			private String _getTypeName(
				boolean input,
				ProcessingElementsContainer processingElementsContainer,
				String typeName) {

				if (input) {
					typeName =
						processingElementsContainer.getInputPrefix() +
							typeName +
								processingElementsContainer.getInputSuffix();
				}

				return typeName;
			}

			private boolean _typesAreEqual(
				GraphQLType graphQLType1, GraphQLType graphQLType2) {

				List<GraphQLType> childrenGraphQLType1 =
					graphQLType1.getChildren();

				List<GraphQLType> childrenGraphQLType2 =
					graphQLType2.getChildren();

				for (GraphQLType childGraphQLType1 : childrenGraphQLType1) {
					boolean found = false;

					for (GraphQLType childGraphQLType2 : childrenGraphQLType2) {
						if (StringUtil.equals(
								childGraphQLType2.getName(),
								childGraphQLType1.getName())) {

							found = true;

							break;
						}
					}

					if (!found) {
						return false;
					}
				}

				if (childrenGraphQLType1.size() ==
						childrenGraphQLType2.size()) {

					return true;
				}

				return false;
			}

			private final Set<String> _typeKeys = new HashSet<>();

			{
				setExtensionsHandler(graphQLExtensionsHandler);
				setFieldSearchAlgorithm(parentalSearch);
				setGraphQLFieldRetriever(_graphQLFieldRetriever);
				setGraphQLInterfaceRetriever(graphQLInterfaceRetriever);
				setGraphQLObjectInfoRetriever(graphQLObjectInfoRetriever);
				setMethodSearchAlgorithm(breadthFirstSearch);
			}
		};

		// Handle Circular reference between GraphQLInterfaceRetriever and
		// GraphQLTypeRetriever

		graphQLInterfaceRetriever.setGraphQLTypeRetriever(graphQLTypeRetriever);

		_defaultTypeFunction = new DefaultTypeFunction(
			new GraphQLInputProcessor() {
				{
					setGraphQLTypeRetriever(graphQLTypeRetriever);
				}
			},
			new GraphQLOutputProcessor() {
				{
					setGraphQLTypeRetriever(graphQLTypeRetriever);
				}
			}) {

			@Override
			public GraphQLType buildType(
				boolean input, Class<?> clazz, AnnotatedType annotatedType,
				ProcessingElementsContainer processingElementsContainer) {

				GraphQLType graphQLType = super.buildType(
					input, clazz, annotatedType, processingElementsContainer);

				if ((annotatedType != null) &&
					(annotatedType.isAnnotationPresent(NotEmpty.class) ||
					 annotatedType.isAnnotationPresent(NotNull.class))) {

					graphQLType = new GraphQLNonNull(graphQLType);
				}

				return graphQLType;
			}

		};

		_defaultTypeFunction.register(new DateTypeFunction());
		_defaultTypeFunction.register(new MapTypeFunction());
		_defaultTypeFunction.register(new ObjectTypeFunction());

		_graphQLContributorServiceTracker = new ServiceTracker<>(
			bundleContext, GraphQLContributor.class,
			new GraphQLContributorServiceTrackerCustomizer());

		_graphQLContributorServiceTracker.open();

		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_NAME, "GraphQL");
		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_PATH, "/graphql");
		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_FILTER_SERVLET, "GraphQL");

		_servletContextHelperServiceRegistration =
			bundleContext.registerService(
				ServletContextHelper.class,
				new ServletContextHelper(bundleContext.getBundle()) {
				},
				properties);

		_servletDataServiceTracker = new ServiceTracker<>(
			bundleContext, ServletData.class,
			new ServletDataServiceTrackerCustomizer());

		_servletDataServiceTracker.open();

		properties = new HashMapDictionary<>();

		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_SELECT, "GraphQL");
		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_NAME, "GraphQL");
		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_PATTERN, "/*");

		_servletServiceRegistration = _bundleContext.registerService(
			Servlet.class,
			(Servlet)ProxyUtil.newProxyInstance(
				GraphQLServletExtender.class.getClassLoader(),
				new Class<?>[] {Servlet.class},
				new InvocationHandler() {

					@Override
					public Object invoke(
							Object proxy, Method method, Object[] arguments)
						throws Throwable {

						String methodName = method.getName();

						if (methodName.equals("destroy")) {
							return null;
						}

						if (methodName.equals("getServletInfo")) {
							return StringPool.BLANK;
						}

						if (methodName.equals("hashCode")) {
							return hashCode();
						}

						if (methodName.equals("init") &&
							(arguments.length > 0)) {

							_servletConfig = (ServletConfig)arguments[0];

							return null;
						}

						Servlet servlet = _createServlet();

						servlet.init(_servletConfig);

						try {
							return method.invoke(servlet, arguments);
						}
						catch (InvocationTargetException
									invocationTargetException) {

							throw invocationTargetException.getCause();
						}
					}

					private ServletConfig _servletConfig;

				}),
			properties);
	}

	@Deactivate
	protected void deactivate() {
		_graphQLContributorServiceTracker.close();

		_servletDataServiceTracker.close();

		_servletServiceRegistration.unregister();

		_servletContextHelperServiceRegistration.unregister();
	}

	private static Object _getAnnotationValue(
		AnnotatedElement annotatedElement, Class<?> clazz) {

		for (Annotation annotation :
				annotatedElement.getDeclaredAnnotations()) {

			Class<? extends Annotation> typeClass = annotation.annotationType();

			String name = typeClass.getName();

			if (name.equals(clazz.getName())) {
				try {
					Method method = typeClass.getMethod("value");

					return method.invoke(annotation);
				}
				catch (Exception exception) {
					throw new RuntimeException(exception);
				}
			}
		}

		return null;
	}

	private static String _getGraphQLNameValue(
		AnnotatedElement annotatedElement) {

		GraphQLName graphQLName = annotatedElement.getAnnotation(
			GraphQLName.class);

		if (graphQLName != null) {
			return graphQLName.value();
		}

		Object value = _getAnnotationValue(
			annotatedElement,
			graphql.annotations.annotationTypes.GraphQLName.class);

		if (value == null) {
			return null;
		}

		return (String)value;
	}

	private static boolean _isMultipartBody(Parameter parameter) {
		Class<?> clazz = parameter.getType();

		String typeName = clazz.getTypeName();

		return typeName.contains("MultipartBody");
	}

	private void _collectObjectFields(
		GraphQLObjectType.Builder builder,
		Map<String, Configuration> configurations,
		Function<ServletData, Object> function,
		ProcessingElementsContainer processingElementsContainer,
		List<ServletData> servletDatas) {

		Stream<ServletData> stream = servletDatas.stream();

		Map<String, Optional<Method>> methods = stream.flatMap(
			servletData -> Stream.of(
				function.apply(servletData)
			).filter(
				Objects::nonNull
			).map(
				Object::getClass
			).map(
				Class::getMethods
			).flatMap(
				Arrays::stream
			).filter(
				method -> _isMethodEnabled(
					configurations, method, servletData.getPath())
			)
		).collect(
			Collectors.groupingBy(
				Method::getName,
				Collectors.maxBy(Comparator.comparingInt(this::_getVersion)))
		);

		for (Optional<Method> methodOptional : methods.values()) {
			if (methodOptional.isPresent()) {
				Method method = methodOptional.get();

				Class<?> clazz = method.getDeclaringClass();

				builder.field(
					_graphQLFieldRetriever.getField(
						clazz.getSimpleName(), method,
						processingElementsContainer));
			}
		}
	}

	private Message _createMessage(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		Message message = new MessageImpl();

		String requestURL = String.valueOf(httpServletRequest.getRequestURL());

		message.put(Message.ENDPOINT_ADDRESS, requestURL);

		String contextPath = GetterUtil.getString(
			httpServletRequest.getContextPath());
		String servletPath = GetterUtil.getString(
			httpServletRequest.getServletPath());

		message.put(
			Message.PATH_INFO,
			contextPath + servletPath + httpServletRequest.getPathInfo());

		message.put(Message.QUERY_STRING, httpServletRequest.getQueryString());
		message.put("Accept", httpServletRequest.getHeader("Accept"));
		message.put("Content-Type", httpServletRequest.getContentType());
		message.put("HTTP.REQUEST", httpServletRequest);
		message.put("HTTP.RESPONSE", httpServletResponse);
		message.put("org.apache.cxf.async.post.response.dispatch", true);
		message.put(
			"org.apache.cxf.request.method", httpServletRequest.getMethod());
		message.put(
			"org.apache.cxf.request.uri", httpServletRequest.getRequestURI());
		message.put("org.apache.cxf.request.url", requestURL);
		message.put(
			"http.base.path",
			_getBasePath(
				contextPath, httpServletRequest.getRequestURI(), requestURL,
				servletPath));

		message.setExchange(new ExchangeImpl());

		return message;
	}

	private GraphQLFieldDefinition _createNodeGraphQLFieldDefinition(
		GraphQLOutputType graphQLOutputType) {

		GraphQLFieldDefinition.Builder graphQLFieldDefinitionbuilder =
			GraphQLFieldDefinition.newFieldDefinition();

		GraphQLArgument.Builder graphQLArgumentBuilder =
			GraphQLArgument.newArgument();

		graphQLFieldDefinitionbuilder.argument(
			graphQLArgumentBuilder.name(
				"dataType"
			).type(
				Scalars.GraphQLString
			).build());

		graphQLArgumentBuilder = GraphQLArgument.newArgument();

		graphQLFieldDefinitionbuilder.argument(
			graphQLArgumentBuilder.name(
				"id"
			).type(
				Scalars.GraphQLLong
			).build());

		graphQLFieldDefinitionbuilder.name("graphQLNode");
		graphQLFieldDefinitionbuilder.type(graphQLOutputType);

		return graphQLFieldDefinitionbuilder.build();
	}

	private GraphQLInterfaceType _createNodeGraphQLInterfaceType() {
		GraphQLInterfaceType.Builder interfaceBuilder =
			GraphQLInterfaceType.newInterface();

		GraphQLFieldDefinition.Builder fieldBuilder =
			GraphQLFieldDefinition.newFieldDefinition();

		interfaceBuilder.field(
			fieldBuilder.name(
				"id"
			).type(
				Scalars.GraphQLLong
			).build());

		interfaceBuilder.name("GraphQLNode");

		return interfaceBuilder.build();
	}

	private Object _createObject(
			DataFetchingEnvironment dataFetchingEnvironment, Method method)
		throws Exception {

		Map<String, Object> argumentsMap =
			dataFetchingEnvironment.getArguments();

		Parameter[] parameters = method.getParameters();

		Object[] arguments = new Object[parameters.length];

		Object instance = null;

		GraphQLFieldDefinition graphQLFieldDefinition =
			dataFetchingEnvironment.getFieldDefinition();

		if ((dataFetchingEnvironment.getRoot() ==
				dataFetchingEnvironment.getSource()) ||
			Objects.equals(graphQLFieldDefinition.getName(), "graphQLNode")) {

			instance = _createQueryInstance(
				method.getDeclaringClass(), dataFetchingEnvironment);
		}
		else {
			Class<?> declaringClass = method.getDeclaringClass();

			Field field = declaringClass.getDeclaredField("this$0");

			Object queryInstance = _createQueryInstance(
				field.getType(), dataFetchingEnvironment);

			Constructor<?>[] constructors = declaringClass.getConstructors();

			instance = ReflectionKit.constructNewInstance(
				constructors[0], queryInstance,
				dataFetchingEnvironment.getSource());
		}

		SiteParamConverterProvider siteParamConverterProvider =
			new SiteParamConverterProvider(
				_depotEntryLocalService, _groupLocalService);

		for (int i = 0; i < parameters.length; i++) {
			Parameter parameter = parameters[i];

			String parameterName = null;

			String graphQLName = _getGraphQLNameValue(parameter);

			if (graphQLName == null) {
				parameterName = NamingKit.toGraphqlName(parameter.getName());
			}
			else {
				parameterName = NamingKit.toGraphqlName(graphQLName);
			}

			Object argument = argumentsMap.get(parameterName);

			if (argument == null) {
				if (parameter.isAnnotationPresent(NotNull.class)) {
					throw new ValidationException(parameterName + " is null");
				}
				else if (parameterName.equals("page")) {
					argument = 1;
				}
				else if (parameterName.equals("pageSize")) {
					argument = 20;
				}
			}

			if (parameterName.equals("assetLibraryId") && (argument != null)) {
				try {
					argument = String.valueOf(
						siteParamConverterProvider.getDepotGroupId(
							(String)argument,
							CompanyThreadLocal.getCompanyId()));
				}
				catch (Exception exception) {
					throw new Exception(
						"Unable to convert asset library \"" + argument +
							"\" to group ID",
						exception);
				}
			}

			if (parameterName.equals("siteKey") && (argument != null)) {
				try {
					argument = String.valueOf(
						siteParamConverterProvider.getGroupId(
							CompanyThreadLocal.getCompanyId(),
							(String)argument));
				}
				catch (Exception exception) {
					throw new Exception(
						"Unable to convert site key \"" + argument +
							"\" to group ID",
						exception);
				}
			}

			if (_isMultipartBody(parameter)) {
				List<Part> parts = (List<Part>)argument;

				if ((parts != null) && !parts.isEmpty()) {
					Map<String, BinaryFile> binaryFiles = HashMapBuilder.put(
						"file",
						() -> {
							Part part = parts.get(0);

							return new BinaryFile(
								part.getContentType(),
								MultipartUtil.getFileName(part),
								part.getInputStream(), part.getSize());
						}
					).build();

					Map<String, String> values = new HashMap<>();

					if (parts.size() > 1) {
						Part metadataPart = parts.get(1);

						String metadata = StringUtil.read(
							metadataPart.getInputStream());

						int index = metadata.indexOf("=");

						if (index != -1) {
							values.put(
								metadata.substring(0, index),
								metadata.substring(index + 1));
						}
					}

					argument = MultipartBody.of(
						binaryFiles, __ -> _objectMapper, values);
				}
			}

			Class<? extends Parameter> parameterClass = parameter.getClass();

			if ((argument instanceof Map) &&
				!parameterClass.isAssignableFrom(Map.class)) {

				argument = _objectMapper.convertValue(
					argument, parameter.getType());

				ValidationUtil.validate(argument);
			}

			arguments[i] = argument;
		}

		ValidationUtil.validateArguments(instance, method, arguments);

		return method.invoke(instance, arguments);
	}

	private Object _createQueryInstance(
			Class<?> clazz, DataFetchingEnvironment dataFetchingEnvironment)
		throws Exception {

		GraphQLContext graphQLContext = dataFetchingEnvironment.getContext();

		Optional<HttpServletRequest> httpServletRequestOptional =
			graphQLContext.getHttpServletRequest();

		HttpServletRequest httpServletRequest =
			httpServletRequestOptional.orElse(null);

		Optional<HttpServletResponse> httpServletResponseOptional =
			graphQLContext.getHttpServletResponse();

		HttpServletResponse httpServletResponse =
			httpServletResponseOptional.orElse(null);

		AcceptLanguage acceptLanguage = new AcceptLanguageImpl(
			httpServletRequest, _language, _portal);

		Object instance = clazz.newInstance();

		for (Field field : clazz.getDeclaredFields()) {
			if (Modifier.isFinal(field.getModifiers()) ||
				Modifier.isStatic(field.getModifiers())) {

				continue;
			}

			Class<?> fieldClass = field.getType();

			if (fieldClass.equals(Object.class) &&
				Objects.equals(field.getName(), "contextScopeChecker")) {

				field.setAccessible(true);

				field.set(instance, _getScopeChecker());

				continue;
			}

			if (fieldClass.isAssignableFrom(AcceptLanguage.class)) {
				field.setAccessible(true);

				field.set(instance, acceptLanguage);
			}
			else if (fieldClass.isAssignableFrom(Company.class)) {
				field.setAccessible(true);

				field.set(
					instance,
					_companyLocalService.getCompany(
						CompanyThreadLocal.getCompanyId()));
			}
			else if (fieldClass.isAssignableFrom(GroupLocalService.class)) {
				field.setAccessible(true);

				field.set(instance, _groupLocalService);
			}
			else if (fieldClass.isAssignableFrom(HttpServletRequest.class)) {
				field.setAccessible(true);

				field.set(instance, httpServletRequest);
			}
			else if (fieldClass.isAssignableFrom(HttpServletResponse.class)) {
				field.setAccessible(true);

				field.set(instance, httpServletResponseOptional.orElse(null));
			}
			else if (fieldClass.isAssignableFrom(
						ResourceActionLocalService.class)) {

				field.setAccessible(true);

				field.set(instance, _resourceActionLocalService);
			}
			else if (fieldClass.isAssignableFrom(
						ResourcePermissionLocalService.class)) {

				field.setAccessible(true);

				field.set(instance, _resourcePermissionLocalService);
			}
			else if (fieldClass.isAssignableFrom(RoleLocalService.class)) {
				field.setAccessible(true);

				field.set(instance, _roleLocalService);
			}
			else if (fieldClass.isAssignableFrom(UriInfo.class)) {
				field.setAccessible(true);

				field.set(
					instance,
					new UriInfoImpl(
						_createMessage(
							httpServletRequest, httpServletResponse)));
			}
			else if (fieldClass.isAssignableFrom(User.class)) {
				field.setAccessible(true);

				field.set(
					instance,
					_portal.getUser(httpServletRequestOptional.orElse(null)));
			}
			else if (Objects.equals(field.getName(), "_filterBiFunction")) {
				field.setAccessible(true);

				BiFunction<Object, String, Filter> filterBiFunction =
					(resource, filterString) -> {
						try {
							FilterContextProvider filterContextProvider =
								new FilterContextProvider(
									_expressionConvert, _filterParserProvider,
									_language, _portal);

							return filterContextProvider.createContext(
								acceptLanguage,
								_getEntityModel(
									resource,
									httpServletRequest.getParameterMap()),
								filterString);
						}
						catch (Exception exception) {
							throw new BadRequestException(exception);
						}
					};

				field.set(instance, filterBiFunction);
			}
			else if (Objects.equals(field.getName(), "_sortsBiFunction")) {
				field.setAccessible(true);

				BiFunction<Object, String, Sort[]> sortsBiFunction =
					(resource, sortsString) -> {
						try {
							SortContextProvider sortContextProvider =
								new SortContextProvider(
									_language, _portal, _sortParserProvider);

							return sortContextProvider.createContext(
								acceptLanguage,
								_getEntityModel(
									resource,
									httpServletRequest.getParameterMap()),
								sortsString);
						}
						catch (Exception exception) {
							throw new BadRequestException(exception);
						}
					};

				field.set(instance, sortsBiFunction);
			}
		}

		return instance;
	}

	private Servlet _createServlet() throws Exception {
		Servlet servlet = _servlet;

		if (servlet != null) {
			return servlet;
		}

		synchronized (_servletDataList) {
			if (_servlet != null) {
				return _servlet;
			}

			PropertyDataFetcher.clearReflectionCache();

			ProcessingElementsContainer processingElementsContainer =
				new ProcessingElementsContainer(_defaultTypeFunction);

			Map<Class<?>, Set<Class<?>>> classesMap =
				processingElementsContainer.getExtensionsTypeRegistry();

			List<ServletData> servletDatas = new ArrayList<>();

			for (ServletData servletData : _servletDataList) {
				if (_isGraphQLEnabled(servletData.getPath())) {
					servletDatas.add(servletData);
				}

				Object query = servletData.getQuery();

				Class<?> queryClass = query.getClass();

				for (Class<?> innerClasses : queryClass.getClasses()) {
					if (innerClasses.isAnnotationPresent(
							GraphQLTypeExtension.class)) {

						GraphQLTypeExtension graphQLTypeExtension =
							innerClasses.getAnnotation(
								GraphQLTypeExtension.class);

						Class<?> clazz = graphQLTypeExtension.value();

						if (!classesMap.containsKey(clazz)) {
							classesMap.put(clazz, new HashSet<>());
						}

						Set<Class<?>> classes = classesMap.get(clazz);

						classes.add(innerClasses);
					}
				}
			}

			GraphQLSchema.Builder graphQLSchemaBuilder =
				GraphQLSchema.newSchema();

			GraphQLObjectType.Builder mutationBuilder =
				GraphQLObjectType.newObject();

			mutationBuilder.name("mutation");

			GraphQLObjectType.Builder graphQLObjectTypeBuilder =
				GraphQLObjectType.newObject();

			graphQLObjectTypeBuilder.name("query");

			Map<String, Configuration> configurations =
				ConfigurationUtil.getConfigurations(_configurationAdmin);

			_collectObjectFields(
				mutationBuilder, configurations, ServletData::getMutation,
				processingElementsContainer, servletDatas);

			_collectObjectFields(
				graphQLObjectTypeBuilder, configurations, ServletData::getQuery,
				processingElementsContainer, servletDatas);

			graphQLSchemaBuilder.mutation(mutationBuilder.build());
			graphQLSchemaBuilder.query(graphQLObjectTypeBuilder.build());

			_registerInterfaces(
				processingElementsContainer, graphQLObjectTypeBuilder,
				graphQLSchemaBuilder);

			GraphQLConfiguration.Builder graphQLConfigurationBuilder =
				GraphQLConfiguration.with(graphQLSchemaBuilder.build());

			ExecutionStrategy executionStrategy = new AsyncExecutionStrategy(
				new SanitizedDataFetcherExceptionHandler());

			ExecutionStrategyProvider executionStrategyProvider =
				new DefaultExecutionStrategyProvider(executionStrategy);

			GraphQLQueryInvoker graphQLQueryInvoker =
				GraphQLQueryInvoker.newBuilder(
				).withExecutionStrategyProvider(
					executionStrategyProvider
				).build();

			graphQLConfigurationBuilder.with(graphQLQueryInvoker);

			GraphQLObjectMapper.Builder objectMapperBuilder =
				GraphQLObjectMapper.newBuilder();

			objectMapperBuilder.withGraphQLErrorHandler(
				new LiferayGraphQLErrorHandler());

			graphQLConfigurationBuilder.with(objectMapperBuilder.build());

			_servlet = GraphQLHttpServlet.with(
				graphQLConfigurationBuilder.build());

			return _servlet;
		}
	}

	private String _getBasePath(
		String contextPath, String requestURI, String requestURL,
		String servletPath) {

		if (!StringUtils.isEmpty(requestURI)) {
			int index = requestURL.indexOf(requestURI);

			if (index > 0) {
				return requestURL.substring(0, index) + contextPath;
			}
		}
		else if (!StringUtils.isEmpty(servletPath) &&
				 requestURL.endsWith(servletPath)) {

			int index = requestURL.lastIndexOf(servletPath);

			if (index > 0) {
				return requestURL.substring(0, index);
			}
		}

		return null;
	}

	private EntityModel _getEntityModel(
			Object resource, Map<String, String[]> parameterMap)
		throws Exception {

		EntityModelResource entityModelResource = (EntityModelResource)resource;

		return entityModelResource.getEntityModel(
			ContextProviderUtil.getMultivaluedHashMap(parameterMap));
	}

	private Field _getFieldDefinitionsByNameField(
			GraphQLObjectType graphQLObjectType)
		throws Exception {

		Class<? extends GraphQLObjectType> clazz = graphQLObjectType.getClass();

		Field field = clazz.getDeclaredField("fieldDefinitionsByName");

		field.setAccessible(true);

		return field;
	}

	private Boolean _getGraphQLFieldValue(AnnotatedElement annotatedElement) {
		GraphQLField graphQLField = annotatedElement.getAnnotation(
			GraphQLField.class);

		if (graphQLField != null) {
			return graphQLField.value();
		}

		Object value = _getAnnotationValue(
			annotatedElement,
			graphql.annotations.annotationTypes.GraphQLField.class);

		if (value == null) {
			return false;
		}

		return (Boolean)value;
	}

	private Object _getScopeChecker() {
		ServiceReference<?> serviceReference =
			_bundleContext.getServiceReference(
				"com.liferay.oauth2.provider.scope.ScopeChecker");

		if (serviceReference != null) {
			return _bundleContext.getService(serviceReference);
		}

		return null;
	}

	private Integer _getVersion(Method method) {
		Class<?> clazz = method.getDeclaringClass();

		Package pkg = clazz.getPackage();

		String packageString = pkg.toString();

		String[] packageNames = packageString.split("\\.");

		String version = packageNames[packageNames.length - 1];

		return Integer.valueOf(version.replaceAll("\\D", ""));
	}

	private boolean _isGraphQLEnabled(String path) throws Exception {
		String filterString = String.format(
			"(&(path=%s)(service.factoryPid=%s))",
			path.substring(0, path.indexOf("-graphql")),
			VulcanConfiguration.class.getName());

		Configuration[] configurations = _configurationAdmin.listConfigurations(
			filterString);

		if (!ArrayUtil.isEmpty(configurations)) {
			Dictionary<String, Object> dictionary =
				configurations[0].getProperties();

			return (Boolean)dictionary.get("graphQLEnabled");
		}

		return true;
	}

	private boolean _isMethodEnabled(
		Map<String, Configuration> configurations, Method method, String path) {

		String substring = path.substring(0, path.indexOf("-graphql"));

		if (configurations.containsKey(substring)) {
			Configuration configuration = configurations.get(substring);

			Dictionary<String, Object> properties =
				configuration.getProperties();

			String excludedOperationIds = GetterUtil.getString(
				properties.get("excludedOperationIds"));

			Set<String> excludedOperationIdsList = SetUtil.fromArray(
				excludedOperationIds.split(","));

			if (excludedOperationIdsList.contains(method.getName())) {
				return false;
			}
		}

		return Boolean.TRUE.equals(_getGraphQLFieldValue(method));
	}

	private void _registerInterfaces(
		ProcessingElementsContainer processingElementsContainer,
		GraphQLObjectType.Builder graphQLObjectTypeBuilder,
		GraphQLSchema.Builder graphQLSchemaBuilder) {

		try {
			Map<String, GraphQLType> graphQLTypes =
				processingElementsContainer.getTypeRegistry();

			GraphQLInterfaceType graphQLInterfaceType =
				_createNodeGraphQLInterfaceType();

			graphQLTypes.put("GraphQLNode", graphQLInterfaceType);

			graphQLObjectTypeBuilder.field(
				_createNodeGraphQLFieldDefinition(graphQLInterfaceType));

			GraphQLCodeRegistry.Builder builder =
				processingElementsContainer.getCodeRegistryBuilder();

			graphQLSchemaBuilder.codeRegistry(
				builder.dataFetcher(
					FieldCoordinates.coordinates("query", "graphQLNode"),
					new NodeDataFetcher()
				).typeResolver(
					"GraphQLNode", new GraphQLNodeTypeResolver()
				).build());

			for (Map.Entry<String, GraphQLType> entry :
					graphQLTypes.entrySet()) {

				GraphQLType graphQLType = entry.getValue();

				if (graphQLType instanceof GraphQLObjectType) {
					GraphQLObjectType graphQLObjectType =
						(GraphQLObjectType)graphQLType;

					GraphQLFieldDefinition graphQLFieldDefinition =
						graphQLObjectType.getFieldDefinition("id");

					if ((graphQLFieldDefinition == null) ||
						(graphQLFieldDefinition.getType() !=
							Scalars.GraphQLLong)) {

						continue;
					}

					_replaceFieldDefinition(
						graphQLInterfaceType, graphQLObjectType);
					_replaceFieldNodes(
						builder, graphQLInterfaceType, graphQLObjectType,
						graphQLSchemaBuilder);
					_replaceInterface(graphQLInterfaceType, graphQLObjectType);
				}
			}
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	private void _replaceFieldDefinition(
			GraphQLInterfaceType graphQLInterfaceType,
			GraphQLObjectType graphQLObjectType)
		throws Exception {

		Field field = _getFieldDefinitionsByNameField(graphQLObjectType);

		Map<String, GraphQLFieldDefinition> graphQLFieldDefinitions =
			(Map<String, GraphQLFieldDefinition>)field.get(graphQLObjectType);

		for (GraphQLFieldDefinition graphQLFieldDefinition :
				graphQLObjectType.getFieldDefinitions()) {

			GraphQLOutputType graphQLOutputType =
				graphQLFieldDefinition.getType();

			String typeName = graphQLOutputType.getName();

			if ((typeName != null) && typeName.equals("Object") &&
				StringUtil.endsWith(graphQLFieldDefinition.getName(), "Id")) {

				Class<? extends GraphQLFieldDefinition> fieldClass =
					graphQLFieldDefinition.getClass();

				Method method = fieldClass.getDeclaredMethod(
					"replaceType", GraphQLOutputType.class);

				method.setAccessible(true);

				method.invoke(graphQLFieldDefinition, graphQLInterfaceType);

				graphQLFieldDefinitions.put(
					graphQLFieldDefinition.getName(), graphQLFieldDefinition);
			}
		}

		field.set(graphQLObjectType, graphQLFieldDefinitions);
	}

	private void _replaceFieldNodes(
			GraphQLCodeRegistry.Builder builder,
			GraphQLInterfaceType graphQLInterfaceType,
			GraphQLObjectType graphQLObjectType,
			GraphQLSchema.Builder graphQLSchemaBuilder)
		throws Exception {

		GraphQLFieldDefinition graphQLFieldDefinition =
			graphQLObjectType.getFieldDefinition("contentType");

		if (graphQLFieldDefinition == null) {
			return;
		}

		Field field = _getFieldDefinitionsByNameField(graphQLObjectType);

		Map<String, GraphQLFieldDefinition> graphQLFieldDefinitions =
			(Map<String, GraphQLFieldDefinition>)field.get(graphQLObjectType);

		GraphQLFieldDefinition.Builder graphQLFieldDefinitionBuilder =
			GraphQLFieldDefinition.newFieldDefinition();

		graphQLFieldDefinitions.put(
			"graphQLNode",
			graphQLFieldDefinitionBuilder.name(
				"graphQLNode"
			).type(
				graphQLInterfaceType
			).build());

		graphQLSchemaBuilder.codeRegistry(
			builder.dataFetcher(
				FieldCoordinates.coordinates(
					graphQLObjectType.getName(), "graphQLNode"),
				new GraphQLNodePropertyDataFetcher()
			).typeResolver(
				"GraphQLNode", new GraphQLNodeTypeResolver()
			).build());

		field.set(graphQLObjectType, graphQLFieldDefinitions);
	}

	private void _replaceInterface(
			GraphQLInterfaceType graphQLInterfaceType,
			GraphQLObjectType graphQLObjectType)
		throws Exception {

		Class<? extends GraphQLObjectType> clazz = graphQLObjectType.getClass();

		Method method = clazz.getDeclaredMethod(
			"replaceInterfaces", List.class);

		method.setAccessible(true);

		method.invoke(
			graphQLObjectType, Collections.singletonList(graphQLInterfaceType));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GraphQLServletExtender.class);

	private static final GraphQLScalarType _dateGraphQLScalarType;
	private static final GraphQLType _mapGraphQLType;
	private static final GraphQLScalarType _objectGraphQLScalarType;
	private static final ObjectMapper _objectMapper = new ObjectMapper();

	static {
		GraphQLScalarType.Builder dateBuilder = new GraphQLScalarType.Builder();

		_dateGraphQLScalarType = dateBuilder.name(
			"Date"
		).description(
			"An RFC-3339 compliant date time scalar"
		).coercing(
			new Coercing<Date, String>() {

				@Override
				public Date parseLiteral(Object value)
					throws CoercingParseLiteralException {

					return _toDate(value);
				}

				@Override
				public Date parseValue(Object value)
					throws CoercingParseValueException {

					return _toDate(value);
				}

				@Override
				public String serialize(Object value)
					throws CoercingSerializeException {

					SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
						"yyyy-MM-dd'T'HH:mm:ss'Z'");

					return simpleDateFormat.format((Date)value);
				}

				private Date _toDate(Object value) {
					if (value instanceof Date) {
						return (Date)value;
					}

					if (value instanceof StringValue) {
						StringValue stringValue = (StringValue)value;

						Calendar calendar = DatatypeConverter.parseDateTime(
							stringValue.getValue());

						return calendar.getTime();
					}

					Calendar calendar = DatatypeConverter.parseDateTime(
						value.toString());

					return calendar.getTime();
				}

			}
		).build();

		GraphQLScalarType.Builder objectBuilder =
			new GraphQLScalarType.Builder();

		_mapGraphQLType = objectBuilder.name(
			"Map"
		).description(
			"Any kind of object supported by a Map"
		).coercing(
			new Coercing<Object, Object>() {

				@Override
				public Object parseLiteral(Object value)
					throws CoercingParseLiteralException {

					return value;
				}

				@Override
				public Object parseValue(Object value)
					throws CoercingParseValueException {

					return value;
				}

				@Override
				public Object serialize(Object value)
					throws CoercingSerializeException {

					return value;
				}

			}
		).build();

		_objectGraphQLScalarType = objectBuilder.name(
			"Object"
		).description(
			"Any kind of object supported by basic scalar types"
		).coercing(
			new Coercing<Object, Object>() {

				@Override
				public Object parseLiteral(Object value)
					throws CoercingParseLiteralException {

					if (value instanceof ArrayValue) {
						ArrayValue arrayValue = (ArrayValue)value;

						List<Value> values = arrayValue.getValues();

						Stream<Value> stream = values.stream();

						return stream.map(
							this::parseLiteral
						).collect(
							Collectors.toList()
						);
					}
					else if (value instanceof BooleanValue) {
						BooleanValue booleanValue = (BooleanValue)value;

						return booleanValue.isValue();
					}
					else if (value instanceof EnumValue) {
						EnumValue enumValue = (EnumValue)value;

						return enumValue.getName();
					}
					else if (value instanceof FloatValue) {
						FloatValue floatValue = (FloatValue)value;

						return floatValue.getValue();
					}
					else if (value instanceof IntValue) {
						IntValue intValue = (IntValue)value;

						return intValue.getValue();
					}
					else if (value instanceof NullValue) {
						return null;
					}
					else if (value instanceof ObjectValue) {
						ObjectValue objectValue = (ObjectValue)value;

						List<ObjectField> objectFields =
							objectValue.getObjectFields();

						Stream<ObjectField> stream = objectFields.stream();

						return stream.collect(
							Collectors.toMap(
								ObjectField::getName,
								objectField -> parseLiteral(
									objectField.getValue())));
					}
					else if (value instanceof StringValue) {
						StringValue stringValue = (StringValue)value;

						return stringValue.getValue();
					}

					throw new CoercingSerializeException(
						"Unable to parse " + value);
				}

				@Override
				public Object parseValue(Object value)
					throws CoercingParseValueException {

					return value;
				}

				@Override
				public Object serialize(Object value)
					throws CoercingSerializeException {

					return value;
				}

			}
		).build();
	}

	private BundleContext _bundleContext;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private ConfigurationAdmin _configurationAdmin;

	private DefaultTypeFunction _defaultTypeFunction;

	@Reference
	private DepotEntryLocalService _depotEntryLocalService;

	@Reference(
		target = "(result.class.name=com.liferay.portal.kernel.search.filter.Filter)"
	)
	private ExpressionConvert<Filter> _expressionConvert;

	@Reference
	private FilterParserProvider _filterParserProvider;

	private ServiceTracker<GraphQLContributor, GraphQLContributor>
		_graphQLContributorServiceTracker;
	private GraphQLFieldRetriever _graphQLFieldRetriever;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

	@Reference
	private ResourceActionLocalService _resourceActionLocalService;

	@Reference
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

	private volatile Servlet _servlet;
	private ServiceRegistration<ServletContextHelper>
		_servletContextHelperServiceRegistration;
	private final List<ServletData> _servletDataList = new ArrayList<>();
	private ServiceTracker<ServletData, ServletData> _servletDataServiceTracker;
	private ServiceRegistration<Servlet> _servletServiceRegistration;

	@Reference
	private SortParserProvider _sortParserProvider;

	private static class DateTypeFunction implements TypeFunction {

		@Override
		public GraphQLType buildType(
			boolean input, Class<?> clazz, AnnotatedType annotatedType,
			ProcessingElementsContainer processingElementsContainer) {

			return _dateGraphQLScalarType;
		}

		@Override
		public boolean canBuildType(
			Class<?> clazz, AnnotatedType annotatedType) {

			if (clazz == Date.class) {
				return true;
			}

			return false;
		}

	}

	private static class GraphQLNodePropertyDataFetcher
		implements DataFetcher<Object> {

		@Override
		public Object get(DataFetchingEnvironment dataFetchingEnvironment)
			throws Exception {

			GraphQLSchema graphQLSchema =
				dataFetchingEnvironment.getGraphQLSchema();

			GraphQLCodeRegistry graphQLCodeRegistry =
				graphQLSchema.getCodeRegistry();

			Map<String, GraphQLType> graphQLTypes = graphQLSchema.getTypeMap();

			GraphQLFieldDefinition.Builder builder =
				GraphQLFieldDefinition.newFieldDefinition();

			Object source = dataFetchingEnvironment.getSource();

			Class<?> clazz = source.getClass();

			Method getContentTypeMethod = clazz.getMethod("getContentType");

			String fieldName = StringUtil.lowerCaseFirstLetter(
				(String)getContentTypeMethod.invoke(source));

			GraphQLFieldDefinition graphQLFieldDefinition =
				dataFetchingEnvironment.getFieldDefinition();

			DataFetcher<?> dataFetcher = graphQLCodeRegistry.getDataFetcher(
				(GraphQLFieldsContainer)graphQLTypes.get("query"),
				builder.name(
					fieldName
				).type(
					graphQLFieldDefinition.getType()
				).build());

			DataFetchingEnvironmentImpl.Builder dataFetchingEnvironmentBuilder =
				DataFetchingEnvironmentImpl.newDataFetchingEnvironment(
					dataFetchingEnvironment);

			Method method = clazz.getMethod("getId");

			Method[] methods = clazz.getMethods();

			for (Method existingMethod : methods) {
				if (StringUtil.equals(
						existingMethod.getName(), "getContentId")) {

					method = existingMethod;
				}
			}

			return dataFetcher.get(
				dataFetchingEnvironmentBuilder.arguments(
					Collections.singletonMap(
						fieldName + "Id", method.invoke(source))
				).build());
		}

	}

	private static class GraphQLNodeTypeResolver implements TypeResolver {

		@Override
		public GraphQLObjectType getType(
			TypeResolutionEnvironment typeResolutionEnvironment) {

			GraphQLSchema graphQLSchema = typeResolutionEnvironment.getSchema();

			Map<String, GraphQLType> graphQLTypes = graphQLSchema.getTypeMap();

			GraphQLType graphQLType = graphQLTypes.get(
				_getClassName(typeResolutionEnvironment.getObject()));

			return (GraphQLObjectType)graphQLType;
		}

		private String _getClassName(Object object) {
			Class<?> clazz = object.getClass();

			String name = clazz.getName();

			if (!name.contains("$")) {
				return clazz.getSimpleName();
			}

			Class<?> parentClass = clazz.getSuperclass();

			return parentClass.getSimpleName();
		}

	}

	private static class LiferayArgumentBuilder extends ArgumentBuilder {

		public LiferayArgumentBuilder(
			Method method, TypeFunction typeFunction,
			GraphQLFieldDefinition.Builder builder,
			ProcessingElementsContainer processingElementsContainer,
			GraphQLOutputType graphQLOutputType) {

			super(
				method, typeFunction, builder, processingElementsContainer,
				graphQLOutputType);

			_method = method;
			_typeFunction = typeFunction;
			_processingElementsContainer = processingElementsContainer;
		}

		@Override
		public List<GraphQLArgument> build() {
			Stream<Parameter> stream = Arrays.stream(_method.getParameters());

			return stream.filter(
				parameter -> !DataFetchingEnvironment.class.isAssignableFrom(
					parameter.getType())
			).map(
				parameter -> {
					if (_isMultipartBody(parameter)) {
						GraphQLArgument.Builder builder =
							new GraphQLArgument.Builder();

						return builder.type(
							new GraphQLList(ApolloScalars.Upload)
						).name(
							"multipartBody"
						).build();
					}

					return _createGraphQLArgument(
						parameter,
						(GraphQLInputType)_typeFunction.buildType(
							true, parameter.getType(),
							parameter.getAnnotatedType(),
							_processingElementsContainer));
				}
			).collect(
				Collectors.toList()
			);
		}

		private GraphQLArgument _createGraphQLArgument(
				Parameter parameter, GraphQLInputType graphQLInputType)
			throws GraphQLAnnotationsException {

			DirectiveWirer directiveWirer = new DirectiveWirer();

			GraphQLArgument.Builder builder = GraphQLArgument.newArgument();

			String graphQLName = _getGraphQLNameValue(parameter);

			if (graphQLName != null) {
				builder.name(NamingKit.toGraphqlName(graphQLName));
			}
			else {
				builder.name(NamingKit.toGraphqlName(parameter.getName()));
			}

			builder.type(graphQLInputType);

			DirectivesBuilder directivesBuilder = new DirectivesBuilder(
				parameter, _processingElementsContainer);

			builder.withDirectives(directivesBuilder.build());

			DirectiveWiringMapRetriever directiveWiringMapRetriever =
				new DirectiveWiringMapRetriever();

			return (GraphQLArgument)directiveWirer.wire(
				builder.build(),
				directiveWiringMapRetriever.getDirectiveWiringMap(
					parameter, _processingElementsContainer),
				_processingElementsContainer.getCodeRegistryBuilder(),
				graphQLInputType.getName());
		}

		private final Method _method;
		private final ProcessingElementsContainer _processingElementsContainer;
		private final TypeFunction _typeFunction;

	}

	private static class LiferayDeprecateBuilder extends DeprecateBuilder {

		public LiferayDeprecateBuilder(AccessibleObject accessibleObject) {
			super(accessibleObject);

			_accessibleObject = accessibleObject;
		}

		public String build() {
			Deprecated deprecated = _accessibleObject.getAnnotation(
				Deprecated.class);

			if (deprecated != null) {
				return "Deprecated";
			}

			return null;
		}

		private final AccessibleObject _accessibleObject;

	}

	private static class LiferayGraphQLErrorHandler
		implements GraphQLErrorHandler {

		@Override
		public List<GraphQLError> processErrors(
			List<GraphQLError> graphQLErrors) {

			Stream<GraphQLError> stream = graphQLErrors.stream();

			return stream.filter(
				graphQLError ->
					!_isNoSuchModelException(graphQLError) ||
					_isRequiredField(graphQLError)
			).map(
				graphQLError -> {
					String message = graphQLError.getMessage();

					if (message.contains("SecurityException")) {
						return _getExtendedGraphQLError(
							graphQLError, Response.Status.UNAUTHORIZED);
					}
					else if (_isNoSuchModelException(graphQLError)) {
						return _getExtendedGraphQLError(
							graphQLError, Response.Status.NOT_FOUND);
					}

					if (!_isClientErrorException(graphQLError)) {
						return _getExtendedGraphQLError(
							graphQLError,
							Response.Status.INTERNAL_SERVER_ERROR);
					}

					return _getExtendedGraphQLError(
						graphQLError, Response.Status.BAD_REQUEST);
				}
			).collect(
				Collectors.toList()
			);
		}

		private GraphQLError _getExtendedGraphQLError(
			GraphQLError graphQLError, Response.Status status) {

			GraphqlErrorBuilder graphqlErrorBuilder =
				GraphqlErrorBuilder.newError();

			String message = graphQLError.getMessage();

			return graphqlErrorBuilder.message(
				message.replace("%", "")
			).extensions(
				HashMapBuilder.put(
					"code", (Object)status.getReasonPhrase()
				).put(
					"exception",
					HashMapBuilder.put(
						"errno", status.getStatusCode()
					).build()
				).build()
			).build();
		}

		private boolean _isClientErrorException(GraphQLError graphQLError) {
			if (graphQLError instanceof ExceptionWhileDataFetching) {
				ExceptionWhileDataFetching exceptionWhileDataFetching =
					(ExceptionWhileDataFetching)graphQLError;

				return exceptionWhileDataFetching.getException() instanceof
					GraphQLError;
			}

			String message = graphQLError.getMessage();

			if (!(graphQLError instanceof Throwable) ||
				message.contains("ClientErrorException")) {

				return true;
			}

			return false;
		}

		private boolean _isNoSuchModelException(GraphQLError graphQLError) {
			if (!(graphQLError instanceof ExceptionWhileDataFetching)) {
				return false;
			}

			ExceptionWhileDataFetching exceptionWhileDataFetching =
				(ExceptionWhileDataFetching)graphQLError;

			Throwable throwable = exceptionWhileDataFetching.getException();

			if ((throwable != null) &&
				(throwable.getCause() instanceof NoSuchModelException)) {

				return true;
			}

			return false;
		}

		private boolean _isRequiredField(GraphQLError graphQLError) {
			List<Object> path = Optional.ofNullable(
				graphQLError.getPath()
			).orElse(
				Collections.emptyList()
			);

			if (path.size() <= 1) {
				return true;
			}

			return StringUtil.containsIgnoreCase(
				(String)path.get(path.size() - 1), "parent");
		}

	}

	private static class MapTypeFunction implements TypeFunction {

		@Override
		public GraphQLType buildType(
			boolean input, Class<?> clazz, AnnotatedType annotatedType,
			ProcessingElementsContainer processingElementsContainer) {

			return _mapGraphQLType;
		}

		@Override
		public boolean canBuildType(
			Class<?> clazz, AnnotatedType annotatedType) {

			if (clazz == Map.class) {
				return true;
			}

			return false;
		}

	}

	private static class NodeDataFetcher implements DataFetcher<Object> {

		@Override
		public Object get(DataFetchingEnvironment dataFetchingEnvironment)
			throws Exception {

			GraphQLSchema graphQLSchema =
				dataFetchingEnvironment.getGraphQLSchema();

			GraphQLCodeRegistry graphQLCodeRegistry =
				graphQLSchema.getCodeRegistry();

			GraphQLFieldDefinition.Builder builder =
				GraphQLFieldDefinition.newFieldDefinition();

			GraphQLFieldDefinition graphQLFieldDefinition =
				dataFetchingEnvironment.getFieldDefinition();

			String fieldName = _getFieldName(
				dataFetchingEnvironment, graphQLSchema);

			DataFetcher<?> dataFetcher = graphQLCodeRegistry.getDataFetcher(
				(GraphQLFieldsContainer)dataFetchingEnvironment.getParentType(),
				builder.argument(
					graphQLFieldDefinition.getArgument("id")
				).name(
					fieldName
				).type(
					graphQLFieldDefinition.getType()
				).build());

			DataFetchingEnvironmentImpl.Builder dataFetchingEnvironmentBuilder =
				DataFetchingEnvironmentImpl.newDataFetchingEnvironment(
					dataFetchingEnvironment);

			return dataFetcher.get(
				dataFetchingEnvironmentBuilder.arguments(
					Collections.singletonMap(
						fieldName + "Id",
						dataFetchingEnvironment.getArgument("id"))
				).build());
		}

		private String _getFieldName(
			DataFetchingEnvironment dataFetchingEnvironment,
			GraphQLSchema graphQLSchema) {

			Map<String, GraphQLType> graphQLTypes = graphQLSchema.getTypeMap();

			GraphQLType graphQLType = graphQLTypes.get(
				dataFetchingEnvironment.getArgument("dataType"));

			return StringUtil.lowerCaseFirstLetter(graphQLType.getName());
		}

	}

	private static class ObjectTypeFunction implements TypeFunction {

		@Override
		public GraphQLType buildType(
			boolean input, Class<?> clazz, AnnotatedType annotatedType,
			ProcessingElementsContainer processingElementsContainer) {

			return _objectGraphQLScalarType;
		}

		@Override
		public boolean canBuildType(
			Class<?> clazz, AnnotatedType annotatedType) {

			if ((clazz == MultipartBody.class) || (clazz == Object.class) ||
				(clazz == Response.class)) {

				return true;
			}

			return false;
		}

	}

	private static class SanitizedDataFetcherExceptionHandler
		implements DataFetcherExceptionHandler {

		@Override
		public DataFetcherExceptionHandlerResult onException(
			DataFetcherExceptionHandlerParameters
				dataFetcherExceptionHandlerParameters) {

			DataFetcherExceptionHandlerResult.Builder builder =
				DataFetcherExceptionHandlerResult.newResult();

			return builder.error(
				new ExceptionWhileDataFetching(
					dataFetcherExceptionHandlerParameters.getPath(),
					dataFetcherExceptionHandlerParameters.getException(),
					dataFetcherExceptionHandlerParameters.getSourceLocation())
			).build();
		}

	}

	private class GraphQLContributorServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<GraphQLContributor, GraphQLContributor> {

		@Override
		public GraphQLContributor addingService(
			ServiceReference<GraphQLContributor> serviceReference) {

			GraphQLContributor graphQLContributor = _bundleContext.getService(
				serviceReference);

			ServiceRegistration<ServletData> servletDataServiceRegistration =
				_bundleContext.registerService(
					ServletData.class,
					ServletDataAdapter.of(graphQLContributor), null);

			_servletDataServiceRegistrations.put(
				graphQLContributor, servletDataServiceRegistration);

			return graphQLContributor;
		}

		@Override
		public void modifiedService(
			ServiceReference<GraphQLContributor> serviceReference,
			GraphQLContributor graphQLContributor) {
		}

		@Override
		public void removedService(
			ServiceReference<GraphQLContributor> serviceReference,
			GraphQLContributor graphQLContributor) {

			Optional.ofNullable(
				_servletDataServiceRegistrations.remove(graphQLContributor)
			).ifPresent(
				ServiceRegistration::unregister
			);

			_bundleContext.ungetService(serviceReference);
		}

		private final Map<GraphQLContributor, ServiceRegistration<ServletData>>
			_servletDataServiceRegistrations = new ConcurrentHashMap<>();

	}

	private class LiferayGraphQLFieldRetriever extends GraphQLFieldRetriever {

		@Override
		public GraphQLFieldDefinition getField(
				String parentName, Field field,
				ProcessingElementsContainer processingElementsContainer)
			throws GraphQLAnnotationsException {

			GraphQLFieldDefinition.Builder builder =
				GraphQLFieldDefinition.newFieldDefinition();

			builder.deprecate(
				new DeprecateBuilder(
					field
				).build());

			GraphQLField graphQLField = field.getAnnotation(GraphQLField.class);

			if (graphQLField != null) {
				builder.description(graphQLField.description());
			}

			builder.name(
				new FieldNameBuilder(
					field
				).build());
			builder.type(
				(GraphQLOutputType)_defaultTypeFunction.buildType(
					field.getType(), field.getAnnotatedType(),
					processingElementsContainer));

			return builder.build();
		}

		@Override
		public GraphQLFieldDefinition getField(
			String parentName, Method method,
			ProcessingElementsContainer processingElementsContainer) {

			GraphQLFieldDefinition.Builder builder =
				GraphQLFieldDefinition.newFieldDefinition();

			MethodTypeBuilder methodTypeBuilder = new MethodTypeBuilder(
				method, processingElementsContainer.getDefaultTypeFunction(),
				processingElementsContainer, false);

			GraphQLOutputType graphQLOutputType =
				(GraphQLOutputType)methodTypeBuilder.build();

			ArgumentBuilder argumentBuilder = new LiferayArgumentBuilder(
				method, processingElementsContainer.getDefaultTypeFunction(),
				builder, processingElementsContainer, graphQLOutputType);

			builder.arguments(argumentBuilder.build());

			builder.dataFetcher(new LiferayMethodDataFetcher(method));

			DeprecateBuilder deprecateBuilder = new LiferayDeprecateBuilder(
				method);

			builder.deprecate(deprecateBuilder.build());

			GraphQLField graphQLField = method.getAnnotation(
				GraphQLField.class);

			if (graphQLField != null) {
				builder.description(graphQLField.description());
			}

			MethodNameBuilder methodNameBuilder = new MethodNameBuilder(method);

			builder.name(methodNameBuilder.build());

			builder.type(graphQLOutputType);

			return builder.build();
		}

	}

	private class LiferayMethodDataFetcher implements DataFetcher<Object> {

		@Override
		public Object get(DataFetchingEnvironment dataFetchingEnvironment) {
			try {
				return _createObject(dataFetchingEnvironment, _method);
			}
			catch (InvocationTargetException invocationTargetException) {
				throw new RuntimeException(
					invocationTargetException.getTargetException());
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		}

		private LiferayMethodDataFetcher(Method method) {
			_method = method;
		}

		private final Method _method;

	}

	private class ServletDataServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<ServletData, ServletData> {

		@Override
		public ServletData addingService(
			ServiceReference<ServletData> serviceReference) {

			ServletData servletData = _bundleContext.getService(
				serviceReference);

			synchronized (_servletDataList) {
				_servletDataList.add(servletData);

				_servlet = null;
			}

			return servletData;
		}

		@Override
		public void modifiedService(
			ServiceReference<ServletData> serviceReference,
			ServletData servletData) {
		}

		@Override
		public void removedService(
			ServiceReference<ServletData> serviceReference,
			ServletData servletData) {

			synchronized (_servletDataList) {
				_servletDataList.remove(servletData);

				_servlet = null;
			}

			_bundleContext.ungetService(serviceReference);
		}

	}

}