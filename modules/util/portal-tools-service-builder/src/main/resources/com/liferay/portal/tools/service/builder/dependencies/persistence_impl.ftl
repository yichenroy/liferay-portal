<#if entity.isHierarchicalTree()>
	<#if entity.hasEntityColumn("groupId")>
		<#assign scopeEntityColumn = entity.getEntityColumn("groupId") />
	<#else>
		<#assign scopeEntityColumn = entity.getEntityColumn("companyId") />
	</#if>

	<#assign pkEntityColumn = entity.PKEntityColumns?first />
</#if>

<#if osgiModule>
	<#assign
		ctPersistenceHelper = "ctPersistenceHelper"
		entityCache = "entityCache"
		finderCache = "finderCache"
	/>
<#else>
	<#assign
		ctPersistenceHelper = "CTPersistenceHelperUtil"
		entityCache = "EntityCacheUtil"
		finderCache = "FinderCacheUtil"
	/>
</#if>

<#assign
	finderFieldSQLSuffix = "_SQL"
	useCache = "useFinderCache"
/>

<#if entity.isChangeTrackingEnabled()>
	<#assign useCache = "useFinderCache && productionMode" />
</#if>

package ${packagePath}.service.persistence.impl;

import ${serviceBuilder.getCompatJavaClassName("StringBundler")};

<#assign noSuchEntity = serviceBuilder.getNoSuchEntityException(entity) />

import ${apiPackagePath}.exception.${noSuchEntity}Exception;
import ${apiPackagePath}.model.${entity.name};

<#if serviceBuilder.isDSLEnabled()>
	import ${apiPackagePath}.model.${entity.name}Table;
</#if>

import ${packagePath}.model.impl.${entity.name}Impl;
import ${packagePath}.model.impl.${entity.name}ModelImpl;
import ${apiPackagePath}.service.persistence.${entity.name}Persistence;

<#if entity.hasCompoundPK()>
	import ${apiPackagePath}.service.persistence.${entity.PKClassName};
</#if>

<#if dependencyInjectorDS>
	import ${packagePath}.service.persistence.impl.constants.${portletShortName}PersistenceConstants;
</#if>

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.change.tracking.CTColumnResolutionType;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.exception.NoSuchModelException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.portal.kernel.sanitizer.Sanitizer;
import com.liferay.portal.kernel.sanitizer.SanitizerException;
import com.liferay.portal.kernel.sanitizer.SanitizerUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.helper.CTPersistenceHelper;
import com.liferay.portal.kernel.service.persistence.change.tracking.helper.CTPersistenceHelperUtil;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.service.persistence.impl.NestedSetsTreeManager;
import com.liferay.portal.kernel.service.persistence.impl.PersistenceNestedSetsTreeManager;
import com.liferay.portal.kernel.service.persistence.impl.TableMapper;
import com.liferay.portal.kernel.service.persistence.impl.TableMapperFactory;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;

import java.math.BigDecimal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

<#list entity.entityColumns as entityColumn>
	<#if entityColumn.isCollection() && entityColumn.isMappingManyToMany()>
		<#assign referenceEntity = serviceBuilder.getEntity(entityColumn.entityName) />

		<#if referenceEntity.hasPersistence()>
			<#if dependencyInjectorDS>
				import ${referenceEntity.apiPackagePath}.model.${referenceEntity.name};
			<#else>
				import ${referenceEntity.apiPackagePath}.service.persistence.${referenceEntity.name}Persistence;
			</#if>
		</#if>
	</#if>
</#list>

/**
 * The persistence implementation for the ${entity.humanName} service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author ${author}
<#if classDeprecated>
 * @deprecated ${classDeprecatedComment}
</#if>
 * @generated
 */

<#assign
	columnBitmaskCacheEnabled = "${entity.name}ModelImpl.COLUMN_BITMASK_ENABLED"
	entityCacheEnabled = "${entity.name}ModelImpl.ENTITY_CACHE_ENABLED"
	finderCacheEnabled = "${entity.name}ModelImpl.FINDER_CACHE_ENABLED"
/>

<#if dependencyInjectorDS>
	@Component(service = ${entity.name}Persistence.class)

	<#assign
		columnBitmaskCacheEnabled = "_columnBitmaskEnabled"
		entityCacheEnabled = "entityCacheEnabled"
		finderCacheEnabled = "finderCacheEnabled"
	/>
</#if>

<#if classDeprecated>
	@Deprecated
</#if>
public class ${entity.name}PersistenceImpl extends BasePersistenceImpl<${entity.name}> implements ${entity.name}Persistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>${entity.name}Util</code> to access the ${entity.humanName} persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	public static final String FINDER_CLASS_NAME_ENTITY = ${entity.name}Impl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY + ".List2";

	<#if serviceBuilder.isVersionGTE_7_3_0()>
		<#assign columnBitmaskEnabled = (entity.databaseRegularEntityColumns?size &lt; 64) && !entity.hasEagerBlobColumn()/>
	<#else>
		<#assign columnBitmaskEnabled = (entity.finderEntityColumns?size &gt; 0) && (entity.finderEntityColumns?size &lt; 64) && !entity.hasEagerBlobColumn()/>
	</#if>

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;

	<#if entity.isHierarchicalTree()>
		private FinderPath _finderPathWithPaginationCountAncestors;
		private FinderPath _finderPathWithPaginationCountDescendants;
		private FinderPath _finderPathWithPaginationGetAncestors;
		private FinderPath _finderPathWithPaginationGetDescendants;
	</#if>

	<#list entity.entityFinders as entityFinder>
		<#include "persistence_impl_finder_finder_path.ftl">

		<#include "persistence_impl_finder_find.ftl">

		<#include "persistence_impl_finder_remove.ftl">

		<#include "persistence_impl_finder_count.ftl">

		<#include "persistence_impl_finder_fields.ftl">
	</#list>

	public ${entity.name}PersistenceImpl() {
		<#if entity.badEntityColumns?size != 0>
			Map<String, String> dbColumnNames = new HashMap<String, String>();

			<#list entity.badEntityColumns as badEntityColumn>
				dbColumnNames.put("${badEntityColumn.name}", "${badEntityColumn.DBName}");
			</#list>

			<#if serviceBuilder.isVersionLTE_7_1_0()>
				try {
					Field field = BasePersistenceImpl.class.getDeclaredField("_dbColumnNames");

					field.setAccessible(true);

					field.set(this, dbColumnNames);
				}
				catch (Exception exception) {
					if (_log.isDebugEnabled()) {
						_log.debug(exception, exception);
					}
				}
			<#else>
				setDBColumnNames(dbColumnNames);
			</#if>
		</#if>

		setModelClass(${entity.name}.class);

		<#if !serviceBuilder.isVersionLTE_7_1_0()>
			setModelImplClass(${entity.name}Impl.class);
			setModelPKClass(${entity.PKClassName}.class);
			<#if serviceBuilder.isVersionLTE_7_2_0() && !dependencyInjectorDS>
				setEntityCacheEnabled(${entityCacheEnabled});
			</#if>
		</#if>

		<#if serviceBuilder.isDSLEnabled()>
			setTable(${entity.name}Table.INSTANCE);
		</#if>
	}

	/**
	 * Caches the ${entity.humanName} in the entity cache if it is enabled.
	 *
	 * @param ${entity.varName} the ${entity.humanName}
	 */
	@Override
	public void cacheResult(${entity.name} ${entity.varName}) {
		<#if entity.isChangeTrackingEnabled()>
			if (${entity.varName}.getCtCollectionId() != 0) {
				<#if serviceBuilder.isVersionLTE_7_2_0()>
					${entity.varName}.resetOriginalValues();
				</#if>

				return;
			}
		</#if>

		${entityCache}.putResult(
				<#if serviceBuilder.isVersionLTE_7_2_0()>
					${entityCacheEnabled},
				</#if>
				${entity.name}Impl.class, ${entity.varName}.getPrimaryKey(), ${entity.varName});

		<#list entity.uniqueEntityFinders as uniqueEntityFinder>
			<#assign entityColumns = uniqueEntityFinder.entityColumns />

			${finderCache}.putResult(
				_finderPathFetchBy${uniqueEntityFinder.name},
				new Object[] {
					<#list entityColumns as entityColumn>
						<#if stringUtil.equals(entityColumn.type, "boolean")>
							${entity.varName}.is${entityColumn.methodName}()
						<#else>
							${entity.varName}.get${entityColumn.methodName}()
						</#if>

						<#if entityColumn_has_next>
							,
						</#if>
					</#list>
				},
				${entity.varName});
		</#list>

		<#if serviceBuilder.isVersionLTE_7_2_0()>
			${entity.varName}.resetOriginalValues();
		</#if>
	}

	/**
	 * Caches the ${entity.pluralHumanName} in the entity cache if it is enabled.
	 *
	 * @param ${entity.pluralVarName} the ${entity.pluralHumanName}
	 */
	@Override
	public void cacheResult(List<${entity.name}> ${entity.pluralVarName}) {
		for (${entity.name} ${entity.varName} : ${entity.pluralVarName}) {
			<#if entity.isChangeTrackingEnabled()>
				if (${entity.varName}.getCtCollectionId() != 0) {
					<#if serviceBuilder.isVersionLTE_7_2_0()>
						${entity.varName}.resetOriginalValues();
					</#if>

					continue;
				}
			</#if>

			if (${entityCache}.getResult(
				<#if serviceBuilder.isVersionLTE_7_2_0()>
					${entityCacheEnabled},
				</#if>
				${entity.name}Impl.class, ${entity.varName}.getPrimaryKey()) == null) {
				cacheResult(${entity.varName});
			}
			<#if serviceBuilder.isVersionLTE_7_2_0()>
				else {
					${entity.varName}.resetOriginalValues();
				}
			</#if>
		}
	}

	/**
	 * Clears the cache for all ${entity.pluralHumanName}.
	 *
	 * <p>
	 * The <code>com.liferay.portal.kernel.dao.orm.EntityCache</code> and <code>com.liferay.portal.kernel.dao.orm.FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		${entityCache}.clearCache(${entity.name}Impl.class);

		${finderCache}.clearCache(FINDER_CLASS_NAME_ENTITY);
		${finderCache}.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		${finderCache}.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the ${entity.humanName}.
	 *
	 * <p>
	 * The <code>com.liferay.portal.kernel.dao.orm.EntityCache</code> and <code>com.liferay.portal.kernel.dao.orm.FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(${entity.name} ${entity.varName}) {
		${entityCache}.removeResult(
			<#if serviceBuilder.isVersionLTE_7_2_0()>
				${entityCacheEnabled},
			</#if>
			${entity.name}Impl.class, ${entity.varName}.getPrimaryKey());

		${finderCache}.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		${finderCache}.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		<#if entity.uniqueEntityFinders?size &gt; 0>
			clearUniqueFindersCache((${entity.name}ModelImpl)${entity.varName}, true);
		</#if>
	}

	@Override
	public void clearCache(List<${entity.name}> ${entity.pluralVarName}) {
		${finderCache}.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		${finderCache}.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (${entity.name} ${entity.varName} : ${entity.pluralVarName}) {
			${entityCache}.removeResult(
				<#if serviceBuilder.isVersionLTE_7_2_0()>
					${entityCacheEnabled},
				</#if>
				${entity.name}Impl.class, ${entity.varName}.getPrimaryKey());

			<#if entity.uniqueEntityFinders?size &gt; 0>
				clearUniqueFindersCache((${entity.name}ModelImpl)${entity.varName}, true);
			</#if>
		}
	}

	<#if serviceBuilder.isVersionGTE_7_3_0()>
		@Override
	</#if>
	public void clearCache(Set<Serializable> primaryKeys) {
		${finderCache}.clearCache(FINDER_CLASS_NAME_ENTITY);
		${finderCache}.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		${finderCache}.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			${entityCache}.removeResult(
				<#if serviceBuilder.isVersionLTE_7_2_0()>
					${entityCacheEnabled},
				</#if>
				${entity.name}Impl.class, primaryKey);
		}
	}

	<#if entity.uniqueEntityFinders?size &gt; 0>
		protected void cacheUniqueFindersCache(${entity.name}ModelImpl ${entity.varName}ModelImpl) {
			<#list entity.uniqueEntityFinders as uniqueEntityFinder>
				<#assign entityColumns = uniqueEntityFinder.entityColumns />

				<#if uniqueEntityFinder_index == 0>
					Object[]
				</#if>
				args = new Object[] {
					<#list entityColumns as entityColumn>
						<#if stringUtil.equals(entityColumn.type, "boolean")>
							${entity.varName}ModelImpl.is${entityColumn.methodName}()
						<#elseif stringUtil.equals(entityColumn.type, "Date")>
							_getTime(${entity.varName}ModelImpl.get${entityColumn.methodName}())
						<#else>
							${entity.varName}ModelImpl.get${entityColumn.methodName}()
						</#if>

						<#if entityColumn_has_next>
							,
						</#if>
					</#list>
				};

				${finderCache}.putResult(_finderPathCountBy${uniqueEntityFinder.name}, args, Long.valueOf(1), false);
				${finderCache}.putResult(_finderPathFetchBy${uniqueEntityFinder.name}, args, ${entity.varName}ModelImpl, false);
			</#list>
		}

		protected void clearUniqueFindersCache(${entity.name}ModelImpl ${entity.varName}ModelImpl, boolean clearCurrent) {
			<#list entity.uniqueEntityFinders as uniqueEntityFinder>
				<#assign entityColumns = uniqueEntityFinder.entityColumns />

				if (clearCurrent) {
					Object[] args = new Object[] {
						<#list entityColumns as entityColumn>
							<#if stringUtil.equals(entityColumn.type, "boolean")>
								${entity.varName}ModelImpl.is${entityColumn.methodName}()
							<#elseif stringUtil.equals(entityColumn.type, "Date")>
								_getTime(${entity.varName}ModelImpl.get${entityColumn.methodName}())
							<#else>
								${entity.varName}ModelImpl.get${entityColumn.methodName}()
							</#if>

							<#if entityColumn_has_next>
								,
							</#if>
						</#list>
					};

					${finderCache}.removeResult(_finderPathCountBy${uniqueEntityFinder.name}, args);
					${finderCache}.removeResult(_finderPathFetchBy${uniqueEntityFinder.name}, args);
				}

				if (
					<#if columnBitmaskEnabled>
						(${entity.varName}ModelImpl.getColumnBitmask() & _finderPathFetchBy${uniqueEntityFinder.name}.getColumnBitmask()) != 0
					<#else>
						<#list entityColumns as entityColumn>
							<#if serviceBuilder.isVersionGTE_7_3_0()>
								!Objects.equals(${entity.varName}ModelImpl.get${entityColumn.methodName}(), ${entity.varName}ModelImpl.getColumnOriginalValue("${entityColumn.DBName}"))
							<#elseif entityColumn.isPrimitiveType()>
								<#if stringUtil.equals(entityColumn.type, "boolean")>
									(${entity.varName}ModelImpl.is${entityColumn.methodName}() != ${entity.varName}ModelImpl.getOriginal${entityColumn.methodName}())
								<#else>
									(${entity.varName}ModelImpl.get${entityColumn.methodName}() != ${entity.varName}ModelImpl.getOriginal${entityColumn.methodName}())
								</#if>
							<#else>
								!Objects.equals(${entity.varName}ModelImpl.get${entityColumn.methodName}(), ${entity.varName}ModelImpl.getOriginal${entityColumn.methodName}())
							</#if>

							<#if entityColumn_has_next>
								||
							</#if>
						</#list>
					</#if>
					) {

					Object[] args = new Object[] {
						<#list entityColumns as entityColumn>
							<#if stringUtil.equals(entityColumn.type, "Date")>
								_getTime(${entity.varName}ModelImpl.
								<#if serviceBuilder.isVersionGTE_7_3_0()>
									getColumnOriginalValue("${entityColumn.DBName}"))
								<#else>
									getOriginal${entityColumn.methodName}())
								</#if>
							<#else>
								${entity.varName}ModelImpl.
								<#if serviceBuilder.isVersionGTE_7_3_0()>
									getColumnOriginalValue("${entityColumn.DBName}")
								<#else>
									getOriginal${entityColumn.methodName}()
								</#if>
							</#if>

							<#if entityColumn_has_next>
								,
							</#if>
						</#list>
					};

					${finderCache}.removeResult(_finderPathCountBy${uniqueEntityFinder.name}, args);
					${finderCache}.removeResult(_finderPathFetchBy${uniqueEntityFinder.name}, args);
				}
			</#list>
		}
	</#if>

	/**
	 * Creates a new ${entity.humanName} with the primary key. Does not add the ${entity.humanName} to the database.
	 *
	 * @param ${entity.PKVarName} the primary key for the new ${entity.humanName}
	 * @return the new ${entity.humanName}
	 */
	@Override
	public ${entity.name} create(${entity.PKClassName} ${entity.PKVarName}) {
		${entity.name} ${entity.varName} = new ${entity.name}Impl();

		${entity.varName}.setNew(true);
		${entity.varName}.setPrimaryKey(${entity.PKVarName});

		<#if entity.hasUuid()>
			String uuid = PortalUUIDUtil.generate();

			${entity.varName}.setUuid(uuid);
		</#if>

		<#if entity.isShardedModel()>
			${entity.varName}.setCompanyId(CompanyThreadLocal.getCompanyId());
		</#if>

		return ${entity.varName};
	}

	/**
	 * Removes the ${entity.humanName} with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ${entity.PKVarName} the primary key of the ${entity.humanName}
	 * @return the ${entity.humanName} that was removed
	 * @throws ${noSuchEntity}Exception if a ${entity.humanName} with the primary key could not be found
	 */
	@Override
	public ${entity.name} remove(${entity.PKClassName} ${entity.PKVarName}) throws ${noSuchEntity}Exception {
		return remove((Serializable)${entity.PKVarName});
	}

	/**
	 * Removes the ${entity.humanName} with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the ${entity.humanName}
	 * @return the ${entity.humanName} that was removed
	 * @throws ${noSuchEntity}Exception if a ${entity.humanName} with the primary key could not be found
	 */
	@Override
	public ${entity.name} remove(Serializable primaryKey) throws ${noSuchEntity}Exception {
		Session session = null;

		try {
			session = openSession();

			${entity.name} ${entity.varName} = (${entity.name})session.get(${entity.name}Impl.class, primaryKey);

			if (${entity.varName} == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new ${noSuchEntity}Exception(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(${entity.varName});
		}
		catch (${noSuchEntity}Exception noSuchEntityException) {
			throw noSuchEntityException;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected ${entity.name} removeImpl(${entity.name} ${entity.varName}) {
		<#list entity.entityColumns as entityColumn>
			<#if entityColumn.isCollection() && entityColumn.isMappingManyToMany()>
				<#assign referenceEntity = serviceBuilder.getEntity(entityColumn.entityName) />

				${entity.varName}To${referenceEntity.name}TableMapper.deleteLeftPrimaryKeyTableMappings(${entity.varName}.getPrimaryKey());
			</#if>
		</#list>

		<#if entity.isChangeTrackingEnabled()>
			if (!${ctPersistenceHelper}.isRemove(${entity.varName})) {
				return ${entity.varName};
			}
		</#if>

		Session session = null;

		try {
			session = openSession();

			<#if entity.isHierarchicalTree()>
				if (rebuildTreeEnabled) {
					if (session.isDirty()) {
						session.flush();
					}

					nestedSetsTreeManager.delete(${entity.varName});

					clearCache();

					session.clear();
				}
			</#if>

			if (!session.contains(${entity.varName})) {
				${entity.varName} = (${entity.name})session.get(${entity.name}Impl.class, ${entity.varName}.getPrimaryKeyObj());
			}

			if (${entity.varName} != null) {
				session.delete(${entity.varName});
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (${entity.varName} != null) {
			clearCache(${entity.varName});
		}

		return ${entity.varName};
	}

	@Override
	public ${entity.name} updateImpl(${apiPackagePath}.model.${entity.name} ${entity.varName}) {
		boolean isNew = ${entity.varName}.isNew();

		<#if entity.isHierarchicalTree() || (entity.collectionEntityFinders?size != 0) || (entity.uniqueEntityFinders?size &gt; 0) || (entity.hasEntityColumn("createDate", "Date") && entity.hasEntityColumn("modifiedDate", "Date"))>
			if (!(${entity.varName} instanceof ${entity.name}ModelImpl)) {
				InvocationHandler invocationHandler = null;

				if (ProxyUtil.isProxyClass(${entity.varName}.getClass())) {
					invocationHandler = ProxyUtil.getInvocationHandler(${entity.varName});

					throw new IllegalArgumentException("Implement ModelWrapper in ${entity.varName} proxy " + invocationHandler.getClass());
				}

				throw new IllegalArgumentException("Implement ModelWrapper in custom ${entity.name} implementation " + ${entity.varName}.getClass());
			}

			${entity.name}ModelImpl ${entity.varName}ModelImpl = (${entity.name}ModelImpl)${entity.varName};
		</#if>

		<#if entity.hasUuid()>
			if (Validator.isNull(${entity.varName}.getUuid())) {
				String uuid = PortalUUIDUtil.generate();

				${entity.varName}.setUuid(uuid);
			}
		</#if>

		<#if entity.hasEntityColumn("createDate", "Date") && entity.hasEntityColumn("modifiedDate", "Date")>
			ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

			Date now = new Date();

			if (isNew && (${entity.varName}.getCreateDate() == null)) {
				if (serviceContext == null) {
					${entity.varName}.setCreateDate(now);
				}
				else {
					${entity.varName}.setCreateDate(serviceContext.getCreateDate(now));
				}
			}

			if (!${entity.varName}ModelImpl.hasSetModifiedDate()) {
				if (serviceContext == null) {
					${entity.varName}.setModifiedDate(now);
				}
				else {
					${entity.varName}.setModifiedDate(serviceContext.getModifiedDate(now));
				}
			}
		</#if>

		<#assign sanitizeTuples = modelHintsUtil.getSanitizeTuples("${apiPackagePath}.model.${entity.name}") />

		<#if sanitizeTuples?size != 0>
			long userId = GetterUtil.getLong(PrincipalThreadLocal.getName());

			if (userId > 0) {
				<#assign companyId = 0 />

				<#if entity.hasEntityColumn("companyId")>
					long companyId = ${entity.varName}.getCompanyId();
				<#else>
					long companyId = 0;
				</#if>

				<#if entity.hasEntityColumn("groupId")>
					long groupId = ${entity.varName}.getGroupId();
				<#else>
					long groupId = 0;
				</#if>

				long ${entity.PKVarName} = 0;

				if (!isNew) {
					${entity.PKVarName} = ${entity.varName}.getPrimaryKey();
				}

				try {
					<#list sanitizeTuples as sanitizeTuple>
						<#assign
							colMethodName = textFormatter.format(sanitizeTuple.getObject(0), 6)

							contentType = "\"" + sanitizeTuple.getObject(1) + "\""
						/>

						<#if contentType == "\"text/html\"">
							<#assign contentType = "ContentTypes.TEXT_HTML" />
						<#elseif contentType == "\"text/plain\"">
							<#assign contentType = "ContentTypes.TEXT_PLAIN" />
						</#if>

						<#assign modes = "\"" + sanitizeTuple.getObject(2) + "\"" />

						<#if modes == "\"ALL\"">
							<#assign modes = "Sanitizer.MODE_ALL" />
						<#elseif modes == "\"BAD_WORDS\"">
							<#assign modes = "Sanitizer.MODE_BAD_WORDS" />
						<#elseif modes == "\"XSS\"">
							<#assign modes = "Sanitizer.MODE_XSS" />
						<#else>
							<#assign modes = "StringUtil.split(\"" + sanitizeTuple.getObject(2) + "\")" />
						</#if>

						${entity.varName}.set${colMethodName}(SanitizerUtil.sanitize(companyId, groupId, userId, ${apiPackagePath}.model.${entity.name}.class.getName(), ${entity.PKVarName}, ${contentType}, ${modes}, ${entity.varName}.get${colMethodName}(), null));
					</#list>
				}
				catch (SanitizerException sanitizerException) {
					throw new SystemException(sanitizerException);
				}
			}
		</#if>

		Session session = null;

		try {
			session = openSession();

			<#if entity.isHierarchicalTree()>
				if (rebuildTreeEnabled) {
					if (session.isDirty()) {
						session.flush();
					}

					if (isNew) {
						nestedSetsTreeManager.insert(${entity.varName}, fetchByPrimaryKey(${entity.varName}.getParent${pkEntityColumn.methodName}()));
					}
					<#if serviceBuilder.isVersionGTE_7_3_0()>
						else if ((${entity.varName}ModelImpl.getColumnOriginalValue("parent${pkEntityColumn.methodName}") != null) && !Objects.equals(${entity.varName}.getParent${pkEntityColumn.methodName}(), ${entity.varName}ModelImpl.getColumnOriginalValue("parent${pkEntityColumn.methodName}"))){
							nestedSetsTreeManager.move(${entity.varName}, fetchByPrimaryKey(${entity.varName}ModelImpl.getColumnOriginalValue("parent${pkEntityColumn.methodName}")), fetchByPrimaryKey(${entity.varName}.getParent${pkEntityColumn.methodName}()));
					<#else>
						else if (${entity.varName}.getParent${pkEntityColumn.methodName}() != ${entity.varName}ModelImpl.getOriginalParent${pkEntityColumn.methodName}()){
							nestedSetsTreeManager.move(${entity.varName}, fetchByPrimaryKey(${entity.varName}ModelImpl.getOriginalParent${pkEntityColumn.methodName}()), fetchByPrimaryKey(${entity.varName}.getParent${pkEntityColumn.methodName}()));
					</#if>
					}

					clearCache();

					session.clear();
				}
			</#if>

			<#if entity.isChangeTrackingEnabled()>
				if (${ctPersistenceHelper}.isInsert(${entity.varName})) {
					if (!isNew) {
						<#if serviceBuilder.isVersionGTE_7_3_0()>
							session.evict(${entity.name}Impl.class, ${entity.varName}.getPrimaryKeyObj());
						<#else>
							${entity.name} old${entity.name} = (${entity.name})session.get(${entity.name}Impl.class, ${entity.varName}.getPrimaryKeyObj());

							if (old${entity.name} != null) {
								session.evict(old${entity.name});
							}
						</#if>
					}
			<#else>
				if (${entity.varName}.isNew()) {
			</#if>

				session.save(${entity.varName});

				${entity.varName}.setNew(false);
			}
			else {
				<#if entity.versionedEntity??>
					throw new IllegalArgumentException("${entity.name} is read only, create a new version instead");
				<#elseif entity.hasLazyBlobEntityColumn()>

					<#-- Workaround for HHH-2680 -->

					<#if serviceBuilder.isVersionGTE_7_3_0()>
						session.evict(${entity.name}Impl.class, ${entity.varName}.getPrimaryKeyObj());
					<#else>
						session.evict(${entity.varName});
					</#if>

					session.saveOrUpdate(${entity.varName});
				<#else>
					${entity.varName} = (${entity.name})session.merge(${entity.varName});
				</#if>
			}

			<#if entity.hasLazyBlobEntityColumn()>
				session.flush();
				session.clear();
			</#if>
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		<#if entity.isChangeTrackingEnabled()>
			if (${entity.varName}.getCtCollectionId() != 0) {
				${entity.varName}.resetOriginalValues();

				return ${entity.varName};
			}
		</#if>

		${finderCache}.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		<#if serviceBuilder.isVersionLTE_7_2_0() && columnBitmaskEnabled>
			if (!${columnBitmaskCacheEnabled}) {
				${finderCache}.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
			}
			else
		</#if>

		if (isNew) {
			<#if entity.finderEntityColumns?size &gt; 64>
				${finderCache}.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
			<#else>
				<#if columnBitmaskEnabled && (entity.collectionEntityFinders?size != 0)>
					Object[]
					<#list entity.collectionEntityFinders as entityFinder>
						<#assign entityColumns = entityFinder.entityColumns />

						args = new Object[] {
							<#list entityColumns as entityColumn>
								<#if stringUtil.equals(entityColumn.type, "boolean")>
									${entity.varName}ModelImpl.is${entityColumn.methodName}()
								<#else>
									${entity.varName}ModelImpl.get${entityColumn.methodName}()
								</#if>

								<#if entityColumn_has_next>
									,
								</#if>
							</#list>
						};

						${finderCache}.removeResult(_finderPathCountBy${entityFinder.name}, args);
						${finderCache}.removeResult(_finderPathWithoutPaginationFindBy${entityFinder.name}, args);
					</#list>
				</#if>

				${finderCache}.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
				${finderCache}.removeResult(_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
			</#if>
		}

		<#if entity.collectionEntityFinders?size != 0>
			else {
				<#list entity.collectionEntityFinders as entityFinder>
					<#assign entityColumns = entityFinder.entityColumns />
					if (
						<#if columnBitmaskEnabled>
							(${entity.varName}ModelImpl.getColumnBitmask() & _finderPathWithoutPaginationFindBy${entityFinder.name}.getColumnBitmask()) != 0
						<#else>
							<#list entityColumns as entityColumn>
								<#if serviceBuilder.isVersionGTE_7_3_0()>
									!Objects.equals(${entity.varName}.get${entityColumn.methodName}(), ${entity.varName}ModelImpl.getColumnOriginalValue("${entityColumn.DBName}"))
								<#elseif entityColumn.isPrimitiveType()>
									<#if stringUtil.equals(entityColumn.type, "boolean")>
										(${entity.varName}.is${entityColumn.methodName}() != ${entity.varName}ModelImpl.getOriginal${entityColumn.methodName}())
									<#else>
										(${entity.varName}.get${entityColumn.methodName}() != ${entity.varName}ModelImpl.getOriginal${entityColumn.methodName}())
									</#if>
								<#else>
									!Objects.equals(${entity.varName}.get${entityColumn.methodName}(), ${entity.varName}ModelImpl.getOriginal${entityColumn.methodName}())
								</#if>

								<#if entityColumn_has_next>
									||
								</#if>
							</#list>
						</#if>
						) {

						Object[] args = new Object[] {
							<#list entityColumns as entityColumn>
								${entity.varName}ModelImpl.
								<#if serviceBuilder.isVersionGTE_7_3_0()>
									getColumnOriginalValue("${entityColumn.DBName}")
								<#else>
									getOriginal${entityColumn.methodName}()
								</#if>

								<#if entityColumn_has_next>
									,
								</#if>
							</#list>
						};

						${finderCache}.removeResult(_finderPathCountBy${entityFinder.name}, args);
						${finderCache}.removeResult(_finderPathWithoutPaginationFindBy${entityFinder.name}, args);

						args = new Object[] {
							<#list entityColumns as entityColumn>
								<#if stringUtil.equals(entityColumn.type, "boolean")>
									${entity.varName}ModelImpl.is${entityColumn.methodName}()
								<#else>
									${entity.varName}ModelImpl.get${entityColumn.methodName}()
								</#if>

								<#if entityColumn_has_next>
									,
								</#if>
							</#list>
						};

						${finderCache}.removeResult(_finderPathCountBy${entityFinder.name}, args);
						${finderCache}.removeResult(_finderPathWithoutPaginationFindBy${entityFinder.name}, args);
					}
				</#list>
			}
		</#if>

		${entityCache}.putResult(
			<#if serviceBuilder.isVersionLTE_7_2_0()>
				${entityCacheEnabled},
			</#if>
			${entity.name}Impl.class, ${entity.varName}.getPrimaryKey(), ${entity.varName}, false);

		<#if entity.uniqueEntityFinders?size &gt; 0>
			clearUniqueFindersCache(${entity.varName}ModelImpl, false);
			cacheUniqueFindersCache(${entity.varName}ModelImpl);
		</#if>

		${entity.varName}.resetOriginalValues();

		return ${entity.varName};
	}

	/**
	 * Returns the ${entity.humanName} with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the ${entity.humanName}
	 * @return the ${entity.humanName}
	 * @throws ${noSuchEntity}Exception if a ${entity.humanName} with the primary key could not be found
	 */
	@Override
	public ${entity.name} findByPrimaryKey(Serializable primaryKey) throws ${noSuchEntity}Exception {
		${entity.name} ${entity.varName} = fetchByPrimaryKey(primaryKey);

		if (${entity.varName} == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new ${noSuchEntity}Exception(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return ${entity.varName};
	}

	/**
	 * Returns the ${entity.humanName} with the primary key or throws a <code>${noSuchEntity}Exception</code> if it could not be found.
	 *
	 * @param ${entity.PKVarName} the primary key of the ${entity.humanName}
	 * @return the ${entity.humanName}
	 * @throws ${noSuchEntity}Exception if a ${entity.humanName} with the primary key could not be found
	 */
	@Override
	public ${entity.name} findByPrimaryKey(${entity.PKClassName} ${entity.PKVarName}) throws ${noSuchEntity}Exception {
		return findByPrimaryKey((Serializable)${entity.PKVarName});
	}

	<#if serviceBuilder.isVersionLTE_7_1_0()>
		/**
		 * Returns the ${entity.humanName} with the primary key or returns <code>null</code> if it could not be found.
		 *
		 * @param primaryKey the primary key of the ${entity.humanName}
		 * @return the ${entity.humanName}, or <code>null</code> if a ${entity.humanName} with the primary key could not be found
		 */
		@Override
		public ${entity.name} fetchByPrimaryKey(Serializable primaryKey) {
			Serializable serializable = ${entityCache}.getResult(${entityCacheEnabled}, ${entity.name}Impl.class, primaryKey);

			if (serializable == nullModel) {
				return null;
			}

			${entity.name} ${entity.varName} = (${entity.name})serializable;

			if (${entity.varName} == null) {
				Session session = null;

				try {
					session = openSession();

					${entity.varName} = (${entity.name})session.get(${entity.name}Impl.class, primaryKey);

					if (${entity.varName} != null) {
						cacheResult(${entity.varName});
					}
					else {
						${entityCache}.putResult(${entityCacheEnabled}, ${entity.name}Impl.class, primaryKey, nullModel);
					}
				}
				catch (Exception exception) {
					<#if serviceBuilder.isVersionLTE_7_2_0()>
						${entityCache}.removeResult(${entityCacheEnabled}, ${entity.name}Impl.class, primaryKey);
					</#if>

					throw processException(exception);
				}
				finally {
					closeSession(session);
				}
			}

			return ${entity.varName};
		}
	<#elseif entity.isChangeTrackingEnabled()>
		/**
		 * Returns the ${entity.humanName} with the primary key or returns <code>null</code> if it could not be found.
		 *
		 * @param primaryKey the primary key of the ${entity.humanName}
		 * @return the ${entity.humanName}, or <code>null</code> if a ${entity.humanName} with the primary key could not be found
		 */
		@Override
		public ${entity.name} fetchByPrimaryKey(Serializable primaryKey) {
			if (${ctPersistenceHelper}.isProductionMode(${entity.name}.class)) {
				return super.fetchByPrimaryKey(primaryKey);
			}

			${entity.name} ${entity.varName} = null;

			Session session = null;

			try {
				session = openSession();

				${entity.varName} = (${entity.name})session.get(${entity.name}Impl.class, primaryKey);

				if (${entity.varName} != null) {
					cacheResult(${entity.varName});
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}

			return ${entity.varName};
		}
	</#if>

	/**
	 * Returns the ${entity.humanName} with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param ${entity.PKVarName} the primary key of the ${entity.humanName}
	 * @return the ${entity.humanName}, or <code>null</code> if a ${entity.humanName} with the primary key could not be found
	 */
	@Override
	public ${entity.name} fetchByPrimaryKey(${entity.PKClassName} ${entity.PKVarName}) {
		return fetchByPrimaryKey((Serializable)${entity.PKVarName});
	}

	<#if serviceBuilder.isVersionLTE_7_1_0()>
		@Override
		public Map<Serializable, ${entity.name}> fetchByPrimaryKeys(Set<Serializable> primaryKeys) {
			if (primaryKeys.isEmpty()) {
				return Collections.emptyMap();
			}

			Map<Serializable, ${entity.name}> map = new HashMap<Serializable, ${entity.name}>();

			<#if entity.hasCompoundPK()>
				for (Serializable primaryKey : primaryKeys) {
					${entity.name} ${entity.varName} = fetchByPrimaryKey(primaryKey);

					if (${entity.varName} != null) {
						map.put(primaryKey, ${entity.varName});
					}
				}

				return map;
			<#else>
				if (primaryKeys.size() == 1) {
					Iterator<Serializable> iterator = primaryKeys.iterator();

					Serializable primaryKey = iterator.next();

					${entity.name} ${entity.varName} = fetchByPrimaryKey(primaryKey);

					if (${entity.varName} != null) {
						map.put(primaryKey, ${entity.varName});
					}

					return map;
				}

				Set<Serializable> uncachedPrimaryKeys = null;

				for (Serializable primaryKey : primaryKeys) {
					Serializable serializable = ${entityCache}.getResult(${entityCacheEnabled}, ${entity.name}Impl.class, primaryKey);

					if (serializable != nullModel) {
						if (serializable == null) {
							if (uncachedPrimaryKeys == null) {
								uncachedPrimaryKeys = new HashSet<Serializable>();
							}

							uncachedPrimaryKeys.add(primaryKey);
						}
						else {
							map.put(primaryKey, (${entity.name})serializable);
						}
					}
				}

				if (uncachedPrimaryKeys == null) {
					return map;
				}

				StringBundler sb = new StringBundler(uncachedPrimaryKeys.size() * 2 + 1);

				sb.append(_SQL_SELECT_${entity.alias?upper_case}_WHERE_PKS_IN);

				<#if stringUtil.equals(entity.PKClassName, "String")>
					for (int i = 0; i < uncachedPrimaryKeys.size(); i++) {
						sb.append("?");

						sb.append(",");
					}
				<#else>
					for (Serializable primaryKey : uncachedPrimaryKeys) {
						sb.append((${entity.PKClassName})primaryKey);

						sb.append(",");
					}
				</#if>

				sb.setIndex(sb.index() - 1);

				sb.append(")");

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					<#if stringUtil.equals(entity.PKClassName, "String")>
						QueryPos queryPos = QueryPos.getInstance(query);

						for (Serializable primaryKey : uncachedPrimaryKeys) {
							queryPos.add((String)primaryKey);
						}
					</#if>

					for (${entity.name} ${entity.varName} : (List<${entity.name}>)query.list()) {
						map.put(${entity.varName}.getPrimaryKeyObj(), ${entity.varName});

						cacheResult(${entity.varName});

						uncachedPrimaryKeys.remove(${entity.varName}.getPrimaryKeyObj());
					}

					for (Serializable primaryKey : uncachedPrimaryKeys) {
						${entityCache}.putResult(${entityCacheEnabled}, ${entity.name}Impl.class, primaryKey, nullModel);
					}
				}
				catch (Exception exception) {
					throw processException(exception);
				}
				finally {
					closeSession(session);
				}

				return map;
			</#if>
		}
	<#elseif entity.isChangeTrackingEnabled()>
		@Override
		public Map<Serializable, ${entity.name}> fetchByPrimaryKeys(Set<Serializable> primaryKeys) {
			if (${ctPersistenceHelper}.isProductionMode(${entity.name}.class)) {
				return super.fetchByPrimaryKeys(primaryKeys);
			}

			if (primaryKeys.isEmpty()) {
				return Collections.emptyMap();
			}

			Map<Serializable, ${entity.name}> map = new HashMap<Serializable, ${entity.name}>();

			if (primaryKeys.size() == 1) {
				Iterator<Serializable> iterator = primaryKeys.iterator();

				Serializable primaryKey = iterator.next();

				${entity.name} ${entity.varName} = fetchByPrimaryKey(primaryKey);

				if (${entity.varName} != null) {
					map.put(primaryKey, ${entity.varName});
				}

				return map;
			}

			StringBundler sb = new StringBundler(primaryKeys.size() * 2 + 1);

			sb.append(getSelectSQL());
			sb.append(" WHERE ");
			sb.append(getPKDBName());
			sb.append(" IN (");

			for (Serializable primaryKey : primaryKeys) {
				sb.append((${entity.PKClassName})primaryKey);

				sb.append(",");
			}

			sb.setIndex(sb.index() - 1);

			sb.append(")");

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				for (${entity.name} ${entity.varName} : (List<${entity.name}>)query.list()) {
					map.put(${entity.varName}.getPrimaryKeyObj(), ${entity.varName});

					cacheResult(${entity.varName});
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}

			return map;
		}
	</#if>

	/**
	 * Returns all the ${entity.pluralHumanName}.
	 *
	 * @return the ${entity.pluralHumanName}
	 */
	@Override
	public List<${entity.name}> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ${entity.pluralHumanName}.
	 *
	 * <p>
	 * <#include "range_comment.ftl">
	 * </p>
	 *
	 * @param start the lower bound of the range of ${entity.pluralHumanName}
	 * @param end the upper bound of the range of ${entity.pluralHumanName} (not inclusive)
	 * @return the range of ${entity.pluralHumanName}
	 */
	@Override
	public List<${entity.name}> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the ${entity.pluralHumanName}.
	 *
	 * <p>
	 * <#include "range_comment.ftl">
	 * </p>
	 *
	 * @param start the lower bound of the range of ${entity.pluralHumanName}
	 * @param end the upper bound of the range of ${entity.pluralHumanName} (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of ${entity.pluralHumanName}
	 */
	@Override
	public List<${entity.name}> findAll(int start, int end, OrderByComparator<${entity.name}> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ${entity.pluralHumanName}.
	 *
	 * <p>
	 * <#include "range_comment.ftl">
	 * </p>
	 *
	 * @param start the lower bound of the range of ${entity.pluralHumanName}
	 * @param end the upper bound of the range of ${entity.pluralHumanName} (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of ${entity.pluralHumanName}
	 */
	@Override
	public List<${entity.name}> findAll(int start, int end, OrderByComparator<${entity.name}> orderByComparator, boolean useFinderCache) {
		<#if entity.isChangeTrackingEnabled()>
			boolean productionMode = ${ctPersistenceHelper}.isProductionMode(${entity.name}.class);
		</#if>

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) && (orderByComparator == null)) {
			if (${useCache}) {
				finderPath = _finderPathWithoutPaginationFindAll;
				finderArgs = FINDER_ARGS_EMPTY;
			}
		}
		else if (${useCache}) {
			finderPath = _finderPathWithPaginationFindAll;
			finderArgs = new Object[] {start, end, orderByComparator};
		}

		List<${entity.name}> list = null;

		if (${useCache}) {
			list = (List<${entity.name}>)${finderCache}.getResult(finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_${entity.alias?upper_case});

				appendOrderByComparator(sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_${entity.alias?upper_case};

				sql = sql.concat(${entity.name}ModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<${entity.name}>)QueryUtil.list(query, getDialect(), start, end);

				cacheResult(list);

				if (${useCache}) {
					${finderCache}.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				<#if serviceBuilder.isVersionLTE_7_2_0()>
					if (${useCache}) {
						${finderCache}.removeResult(finderPath, finderArgs);
					}
				</#if>

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the ${entity.pluralHumanName} from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (${entity.name} ${entity.varName} : findAll()) {
			remove(${entity.varName});
		}
	}

	/**
	 * Returns the number of ${entity.pluralHumanName}.
	 *
	 * @return the number of ${entity.pluralHumanName}
	 */
	@Override
	public int countAll() {
		<#if entity.isChangeTrackingEnabled()>
			boolean productionMode = ${ctPersistenceHelper}.isProductionMode(${entity.name}.class);

			Long count = null;

			if (productionMode) {
				count = (Long)${finderCache}.getResult(_finderPathCountAll, FINDER_ARGS_EMPTY, this);
			}
		<#else>
			Long count = (Long)${finderCache}.getResult(_finderPathCountAll, FINDER_ARGS_EMPTY, this);
		</#if>

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_${entity.alias?upper_case});

				count = (Long)query.uniqueResult();

				<#if entity.isChangeTrackingEnabled()>
					if (productionMode) {
						${finderCache}.putResult(_finderPathCountAll, FINDER_ARGS_EMPTY, count);
					}
				<#else>
					${finderCache}.putResult(_finderPathCountAll, FINDER_ARGS_EMPTY, count);
				</#if>
			}
			catch (Exception exception) {
				<#if serviceBuilder.isVersionLTE_7_2_0()>
					<#if entity.isChangeTrackingEnabled()>
						if (productionMode) {
							${finderCache}.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
						}
					<#else>
						${finderCache}.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
					</#if>
				</#if>

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	<#list entity.entityColumns as entityColumn>
		<#if entityColumn.isCollection() && entityColumn.isMappingManyToMany()>
			<#assign referenceEntity = serviceBuilder.getEntity(entityColumn.entityName) />

			/**
			 * Returns the primaryKeys of ${referenceEntity.pluralHumanName} associated with the ${entity.humanName}.
			 *
			 * @param pk the primary key of the ${entity.humanName}
			 * @return long[] of the primaryKeys of ${referenceEntity.pluralHumanName} associated with the ${entity.humanName}
			 */
			@Override
			public long[] get${referenceEntity.name}PrimaryKeys(${entity.PKClassName} pk) {
				long[] pks = ${entity.varName}To${referenceEntity.name}TableMapper.getRightPrimaryKeys(pk);

				return pks.clone();
			}

			<#if dependencyInjectorDS>
				/**
				 * Returns all the ${entity.humanName} associated with the ${referenceEntity.humanName}.
				 *
				 * @param pk the primary key of the ${referenceEntity.humanName}
				 * @return the ${entity.pluralHumanName} associated with the ${referenceEntity.humanName}
				 */
				@Override
				public List<${entity.name}> get${referenceEntity.name}${entity.pluralName}(${referenceEntity.PKClassName} pk) {
					return get${referenceEntity.name}${entity.pluralName}(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
				}

				/**
				 * Returns all the ${entity.humanName} associated with the ${referenceEntity.humanName}.
				 *
				 * <p>
				 * <#include "range_comment.ftl">
				 * </p>
				 *
				 * @param pk the primary key of the ${referenceEntity.humanName}
				 * @param start the lower bound of the range of ${referenceEntity.pluralHumanName}
				 * @param end the upper bound of the range of ${referenceEntity.pluralHumanName} (not inclusive)
				 * @return the range of ${entity.pluralHumanName} associated with the ${referenceEntity.humanName}
				 */
				@Override
				public List<${entity.name}> get${referenceEntity.name}${entity.pluralName}(${referenceEntity.PKClassName} pk, int start, int end) {
					return get${referenceEntity.name}${entity.pluralName}(pk, start, end, null);
				}

				/**
				 * Returns all the ${entity.humanName} associated with the ${referenceEntity.humanName}.
				 *
				 * <p>
				 * <#include "range_comment.ftl">
				 * </p>
				 *
				 * @param pk the primary key of the ${referenceEntity.humanName}
				 * @param start the lower bound of the range of ${referenceEntity.pluralHumanName}
				 * @param end the upper bound of the range of ${referenceEntity.pluralHumanName} (not inclusive)
				 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
				 * @return the ordered range of ${entity.pluralHumanName} associated with the ${referenceEntity.humanName}
				 */
				@Override
				public List<${entity.name}> get${referenceEntity.name}${entity.pluralName}(${referenceEntity.PKClassName} pk, int start, int end, OrderByComparator<${entity.name}> orderByComparator) {
					return ${entity.varName}To${referenceEntity.name}TableMapper.getLeftBaseModels(pk, start, end, orderByComparator);
				}
			<#else>
				/**
				 * Returns all the ${referenceEntity.pluralHumanName} associated with the ${entity.humanName}.
				 *
				 * @param pk the primary key of the ${entity.humanName}
				 * @return the ${referenceEntity.pluralHumanName} associated with the ${entity.humanName}
				 */
				@Override
				public List<${referenceEntity.apiPackagePath}.model.${referenceEntity.name}> get${referenceEntity.pluralName}(${entity.PKClassName} pk) {
					return get${referenceEntity.pluralName}(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
				}

				/**
				 * Returns a range of all the ${referenceEntity.pluralHumanName} associated with the ${entity.humanName}.
				 *
				 * <p>
				 * <#include "range_comment.ftl">
				 * </p>
				 *
				 * @param pk the primary key of the ${entity.humanName}
				 * @param start the lower bound of the range of ${entity.pluralHumanName}
				 * @param end the upper bound of the range of ${entity.pluralHumanName} (not inclusive)
				 * @return the range of ${referenceEntity.pluralHumanName} associated with the ${entity.humanName}
				 */
				@Override
				public List<${referenceEntity.apiPackagePath}.model.${referenceEntity.name}> get${referenceEntity.pluralName}(${entity.PKClassName} pk, int start, int end) {
					return get${referenceEntity.pluralName}(pk, start, end, null);
				}

				/**
				 * Returns an ordered range of all the ${referenceEntity.pluralHumanName} associated with the ${entity.humanName}.
				 *
				 * <p>
				 * <#include "range_comment.ftl">
				 * </p>
				 *
				 * @param pk the primary key of the ${entity.humanName}
				 * @param start the lower bound of the range of ${entity.pluralHumanName}
				 * @param end the upper bound of the range of ${entity.pluralHumanName} (not inclusive)
				 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
				 * @return the ordered range of ${referenceEntity.pluralHumanName} associated with the ${entity.humanName}
				 */
				@Override
				public List<${referenceEntity.apiPackagePath}.model.${referenceEntity.name}> get${referenceEntity.pluralName}(${entity.PKClassName} pk, int start, int end, OrderByComparator<${referenceEntity.apiPackagePath}.model.${referenceEntity.name}> orderByComparator) {
					return ${entity.varName}To${referenceEntity.name}TableMapper.getRightBaseModels(pk, start, end, orderByComparator);
				}
			</#if>

			/**
			 * Returns the number of ${referenceEntity.pluralHumanName} associated with the ${entity.humanName}.
			 *
			 * @param pk the primary key of the ${entity.humanName}
			 * @return the number of ${referenceEntity.pluralHumanName} associated with the ${entity.humanName}
			 */
			@Override
			public int get${referenceEntity.pluralName}Size(${entity.PKClassName} pk) {
				long[] pks = ${entity.varName}To${referenceEntity.name}TableMapper.getRightPrimaryKeys(pk);

				return pks.length;
			}

			/**
			 * Returns <code>true</code> if the ${referenceEntity.humanName} is associated with the ${entity.humanName}.
			 *
			 * @param pk the primary key of the ${entity.humanName}
			 * @param ${referenceEntity.varName}PK the primary key of the ${referenceEntity.humanName}
			 * @return <code>true</code> if the ${referenceEntity.humanName} is associated with the ${entity.humanName}; <code>false</code> otherwise
			 */
			@Override
			public boolean contains${referenceEntity.name}(${entity.PKClassName} pk, ${referenceEntity.PKClassName} ${referenceEntity.varName}PK) {
				return ${entity.varName}To${referenceEntity.name}TableMapper.containsTableMapping(pk, ${referenceEntity.varName}PK);
			}

			/**
			 * Returns <code>true</code> if the ${entity.humanName} has any ${referenceEntity.pluralHumanName} associated with it.
			 *
			 * @param pk the primary key of the ${entity.humanName} to check for associations with ${referenceEntity.pluralHumanName}
			 * @return <code>true</code> if the ${entity.humanName} has any ${referenceEntity.pluralHumanName} associated with it; <code>false</code> otherwise
			 */
			@Override
			public boolean contains${referenceEntity.pluralName}(${entity.PKClassName} pk) {
				if (get${referenceEntity.pluralName}Size(pk)> 0) {
					return true;
				}
				else {
					return false;
				}
			}

			<#if entityColumn.isMappingManyToMany()>
				<#assign noSuchTempEntity = serviceBuilder.getNoSuchEntityException(referenceEntity) />

				/**
				 * Adds an association between the ${entity.humanName} and the ${referenceEntity.humanName}. Also notifies the appropriate model listeners and clears the mapping table finder cache.
				 *
				 * @param pk the primary key of the ${entity.humanName}
				 * @param ${referenceEntity.varName}PK the primary key of the ${referenceEntity.humanName}
				 */
				@Override
				public void add${referenceEntity.name}(${entity.PKClassName} pk, ${referenceEntity.PKClassName} ${referenceEntity.varName}PK) {
					${entity.name} ${entity.varName} = fetchByPrimaryKey(pk);

					if (${entity.varName} == null) {
						${entity.varName}To${referenceEntity.name}TableMapper.addTableMapping(CompanyThreadLocal.getCompanyId(), pk, ${referenceEntity.varName}PK);
					}
					else {
						${entity.varName}To${referenceEntity.name}TableMapper.addTableMapping(${entity.varName}.getCompanyId(), pk, ${referenceEntity.varName}PK);
					}
				}

				/**
				 * Adds an association between the ${entity.humanName} and the ${referenceEntity.humanName}. Also notifies the appropriate model listeners and clears the mapping table finder cache.
				 *
				 * @param pk the primary key of the ${entity.humanName}
				 * @param ${referenceEntity.varName} the ${referenceEntity.humanName}
				 */
				@Override
				public void add${referenceEntity.name}(${entity.PKClassName} pk, ${referenceEntity.apiPackagePath}.model.${referenceEntity.name} ${referenceEntity.varName}) {
					${entity.name} ${entity.varName} = fetchByPrimaryKey(pk);

					if (${entity.varName} == null) {
						${entity.varName}To${referenceEntity.name}TableMapper.addTableMapping(CompanyThreadLocal.getCompanyId(), pk, ${referenceEntity.varName}.getPrimaryKey());
					}
					else {
						${entity.varName}To${referenceEntity.name}TableMapper.addTableMapping(${entity.varName}.getCompanyId(), pk, ${referenceEntity.varName}.getPrimaryKey());
					}
				}

				/**
				 * Adds an association between the ${entity.humanName} and the ${referenceEntity.pluralHumanName}. Also notifies the appropriate model listeners and clears the mapping table finder cache.
				 *
				 * @param pk the primary key of the ${entity.humanName}
				 * @param ${referenceEntity.varName}PKs the primary keys of the ${referenceEntity.pluralHumanName}
				 */
				@Override
				public void add${referenceEntity.pluralName}(${entity.PKClassName} pk, ${referenceEntity.PKClassName}[] ${referenceEntity.varName}PKs) {
					long companyId = 0;

					${entity.name} ${entity.varName} = fetchByPrimaryKey(pk);

					if (${entity.varName} == null) {
						companyId = CompanyThreadLocal.getCompanyId();
					}
					else {
						companyId = ${entity.varName}.getCompanyId();
					}

					${entity.varName}To${referenceEntity.name}TableMapper.addTableMappings(companyId, pk, ${referenceEntity.varName}PKs);
				}

				/**
				 * Adds an association between the ${entity.humanName} and the ${referenceEntity.pluralHumanName}. Also notifies the appropriate model listeners and clears the mapping table finder cache.
				 *
				 * @param pk the primary key of the ${entity.humanName}
				 * @param ${referenceEntity.pluralVarName} the ${referenceEntity.pluralHumanName}
				 */
				@Override
				public void add${referenceEntity.pluralName}(${entity.PKClassName} pk, List<${referenceEntity.apiPackagePath}.model.${referenceEntity.name}> ${referenceEntity.pluralVarName}) {
					add${referenceEntity.pluralName}(pk, ListUtil.toLongArray(${referenceEntity.pluralVarName}, ${referenceEntity.apiPackagePath}.model.${referenceEntity.name}.${textFormatter.format(textFormatter.format(referenceEntity.getPKVarName(), 7), 0)}_ACCESSOR));
				}

				/**
				 * Clears all associations between the ${entity.humanName} and its ${referenceEntity.pluralHumanName}. Also notifies the appropriate model listeners and clears the mapping table finder cache.
				 *
				 * @param pk the primary key of the ${entity.humanName} to clear the associated ${referenceEntity.pluralHumanName} from
				 */
				@Override
				public void clear${referenceEntity.pluralName}(${entity.PKClassName} pk) {
					${entity.varName}To${referenceEntity.name}TableMapper.deleteLeftPrimaryKeyTableMappings(pk);
				}

				/**
				 * Removes the association between the ${entity.humanName} and the ${referenceEntity.humanName}. Also notifies the appropriate model listeners and clears the mapping table finder cache.
				 *
				 * @param pk the primary key of the ${entity.humanName}
				 * @param ${referenceEntity.varName}PK the primary key of the ${referenceEntity.humanName}
				 */
				@Override
				public void remove${referenceEntity.name}(${entity.PKClassName} pk, ${referenceEntity.PKClassName} ${referenceEntity.varName}PK) {
					${entity.varName}To${referenceEntity.name}TableMapper.deleteTableMapping(pk, ${referenceEntity.varName}PK);
				}

				/**
				 * Removes the association between the ${entity.humanName} and the ${referenceEntity.humanName}. Also notifies the appropriate model listeners and clears the mapping table finder cache.
				 *
				 * @param pk the primary key of the ${entity.humanName}
				 * @param ${referenceEntity.varName} the ${referenceEntity.humanName}
				 */
				@Override
				public void remove${referenceEntity.name}(${entity.PKClassName} pk, ${referenceEntity.apiPackagePath}.model.${referenceEntity.name} ${referenceEntity.varName}) {
					${entity.varName}To${referenceEntity.name}TableMapper.deleteTableMapping(pk, ${referenceEntity.varName}.getPrimaryKey());
				}

				/**
				 * Removes the association between the ${entity.humanName} and the ${referenceEntity.pluralHumanName}. Also notifies the appropriate model listeners and clears the mapping table finder cache.
				 *
				 * @param pk the primary key of the ${entity.humanName}
				 * @param ${referenceEntity.varName}PKs the primary keys of the ${referenceEntity.pluralHumanName}
				 */
				@Override
				public void remove${referenceEntity.pluralName}(${entity.PKClassName} pk, ${referenceEntity.PKClassName}[] ${referenceEntity.varName}PKs) {
					${entity.varName}To${referenceEntity.name}TableMapper.deleteTableMappings(pk, ${referenceEntity.varName}PKs);
				}

				/**
				 * Removes the association between the ${entity.humanName} and the ${referenceEntity.pluralHumanName}. Also notifies the appropriate model listeners and clears the mapping table finder cache.
				 *
				 * @param pk the primary key of the ${entity.humanName}
				 * @param ${referenceEntity.pluralVarName} the ${referenceEntity.pluralHumanName}
				 */
				@Override
				public void remove${referenceEntity.pluralName}(${entity.PKClassName} pk, List<${referenceEntity.apiPackagePath}.model.${referenceEntity.name}> ${referenceEntity.pluralVarName}) {
					remove${referenceEntity.pluralName}(pk, ListUtil.toLongArray(${referenceEntity.pluralVarName}, ${referenceEntity.apiPackagePath}.model.${referenceEntity.name}.${textFormatter.format(textFormatter.format(referenceEntity.getPKVarName(), 7), 0)}_ACCESSOR));
				}

				/**
				 * Sets the ${referenceEntity.pluralHumanName} associated with the ${entity.humanName}, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
				 *
				 * @param pk the primary key of the ${entity.humanName}
				 * @param ${referenceEntity.varName}PKs the primary keys of the ${referenceEntity.pluralHumanName} to be associated with the ${entity.humanName}
				 */
				@Override
				public void set${referenceEntity.pluralName}(${entity.PKClassName} pk, ${referenceEntity.PKClassName}[] ${referenceEntity.varName}PKs) {
					Set<Long> new${referenceEntity.name}PKsSet = SetUtil.fromArray(${referenceEntity.varName}PKs);
					Set<Long> old${referenceEntity.name}PKsSet = SetUtil.fromArray(${entity.varName}To${referenceEntity.name}TableMapper.getRightPrimaryKeys(pk));

					Set<Long> remove${referenceEntity.name}PKsSet = new HashSet<Long>(old${referenceEntity.name}PKsSet);

					remove${referenceEntity.name}PKsSet.removeAll(new${referenceEntity.name}PKsSet);

					${entity.varName}To${referenceEntity.name}TableMapper.deleteTableMappings(pk, ArrayUtil.toLongArray(remove${referenceEntity.name}PKsSet));

					new${referenceEntity.name}PKsSet.removeAll(old${referenceEntity.name}PKsSet);

					long companyId = 0;

					${entity.name} ${entity.varName} = fetchByPrimaryKey(pk);

					if (${entity.varName} == null) {
						companyId = CompanyThreadLocal.getCompanyId();
					}
					else {
						companyId = ${entity.varName}.getCompanyId();
					}

					${entity.varName}To${referenceEntity.name}TableMapper.addTableMappings(companyId, pk, ArrayUtil.toLongArray(new${referenceEntity.name}PKsSet));
				}

				/**
				 * Sets the ${referenceEntity.pluralHumanName} associated with the ${entity.humanName}, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
				 *
				 * @param pk the primary key of the ${entity.humanName}
				 * @param ${referenceEntity.pluralVarName} the ${referenceEntity.pluralHumanName} to be associated with the ${entity.humanName}
				 */
				@Override
				public void set${referenceEntity.pluralName}(${entity.PKClassName} pk, List<${referenceEntity.apiPackagePath}.model.${referenceEntity.name}> ${referenceEntity.pluralVarName}) {
					try {
						${referenceEntity.PKClassName}[] ${referenceEntity.varName}PKs = new ${referenceEntity.PKClassName}[${referenceEntity.pluralVarName}.size()];

						for (int i = 0; i < ${referenceEntity.pluralVarName}.size(); i++) {
							${referenceEntity.apiPackagePath}.model.${referenceEntity.name} ${referenceEntity.varName} = ${referenceEntity.pluralVarName}.get(i);

							${referenceEntity.varName}PKs[i] = ${referenceEntity.varName}.getPrimaryKey();
						}

						set${referenceEntity.pluralName}(pk, ${referenceEntity.varName}PKs);
					}
					catch (Exception exception) {
						throw processException(exception);
					}
				}
			</#if>
		</#if>
	</#list>

	<#if entity.badEntityColumns?size != 0>
		@Override
		public Set<String> getBadColumnNames() {
			return _badColumnNames;
		}
	</#if>

	<#if entity.hasCompoundPK()>
		@Override
		public Set<String> getCompoundPKColumnNames() {
			return _compoundPKColumnNames;
		}
	</#if>

	<#if !serviceBuilder.isVersionLTE_7_1_0()>
		@Override
		protected EntityCache getEntityCache() {
			<#if osgiModule>
				return entityCache;
			<#else>
				return EntityCacheUtil.getEntityCache();
			</#if>
		}

		@Override
		protected String getPKDBName() {
			return "${entity.PKDBName}";
		}

		@Override
		protected String getSelectSQL() {
			return _SQL_SELECT_${entity.alias?upper_case};
		}
	</#if>

	<#if entity.isChangeTrackingEnabled()>
		@Override
		public Set<String> getCTColumnNames(CTColumnResolutionType ctColumnResolutionType) {
			return _ctColumnNamesMap.get(ctColumnResolutionType);
		}

		@Override
		public List<String> getMappingTableNames() {
			return _mappingTableNames;
		}

		@Override
		public Map<String, Integer> getTableColumnsMap() {
			return ${entity.name}ModelImpl.TABLE_COLUMNS_MAP;
		}

		@Override
		public String getTableName() {
			return "${entity.table}";
		}

		@Override
		public List<String[]> getUniqueIndexColumnNames() {
			return _uniqueIndexColumnNames;
		}

		private static final Map<CTColumnResolutionType, Set<String>> _ctColumnNamesMap = new EnumMap<CTColumnResolutionType, Set<String>>(CTColumnResolutionType.class);
		private static final List<String> _mappingTableNames = new ArrayList<String>();
		private static final List<String[]> _uniqueIndexColumnNames = new ArrayList<String[]>();

		static {
			Set<String> ctControlColumnNames = new HashSet<String>();
			Set<String> ctIgnoreColumnNames = new HashSet<String>();
			Set<String> ctMergeColumnNames = new HashSet<String>();
			Set<String> ctStrictColumnNames = new HashSet<String>();

			<#list entity.entityColumns as entityColumn>
				<#if entityColumn.isChangeTrackingControl()>
					ctControlColumnNames.add("${entityColumn.DBName}");
				<#elseif entityColumn.isChangeTrackingIgnore()>
					ctIgnoreColumnNames.add("${entityColumn.DBName}");
				<#elseif entityColumn.isChangeTrackingMerge()>
					ctMergeColumnNames.add("${entityColumn.DBName}");
				<#elseif entityColumn.isChangeTrackingStrict()>
					ctStrictColumnNames.add("${entityColumn.DBName}");
				</#if>
			</#list>

			_ctColumnNamesMap.put(CTColumnResolutionType.CONTROL, ctControlColumnNames);
			_ctColumnNamesMap.put(CTColumnResolutionType.IGNORE, ctIgnoreColumnNames);
			_ctColumnNamesMap.put(CTColumnResolutionType.MERGE, ctMergeColumnNames);
			_ctColumnNamesMap.put(CTColumnResolutionType.PK, Collections.singleton("${entity.PKDBName}"));
			_ctColumnNamesMap.put(CTColumnResolutionType.STRICT, ctStrictColumnNames);

			<#list entity.entityColumns as entityColumn>
				<#if entityColumn.isCollection() && entityColumn.isMappingManyToMany()>
					_mappingTableNames.add("${entityColumn.mappingTableName}");
				</#if>
			</#list>

			<#list entity.entityFinders as entityFinder>
				<#if entityFinder.isUnique()>
					<#assign entityColumns = entityFinder.entityColumns />

					_uniqueIndexColumnNames.add(
						new String[] {
							<#list entityColumns as entityColumn>
								"${entityColumn.DBName}"

								<#if entityColumn_has_next>
									,
								</#if>
							</#list>
						});
				</#if>
			</#list>
		}
	<#else>
		@Override
		protected Map<String, Integer> getTableColumnsMap() {
			return ${entity.name}ModelImpl.TABLE_COLUMNS_MAP;
		}
	</#if>

	<#if entity.isHierarchicalTree()>
		@Override
		public long countAncestors(${entity.name} ${entity.varName}) {
			Object[] finderArgs = new Object[] {${entity.varName}.get${scopeEntityColumn.methodName}(), ${entity.varName}.getLeft${pkEntityColumn.methodName}(), ${entity.varName}.getRight${pkEntityColumn.methodName}()};

			Long count = (Long)${finderCache}.getResult(_finderPathWithPaginationCountAncestors, finderArgs, this);

			if (count == null) {
				try {
					count = nestedSetsTreeManager.countAncestors(${entity.varName});

					${finderCache}.putResult(_finderPathWithPaginationCountAncestors, finderArgs, count);
				}
				catch (SystemException systemException) {
					<#if serviceBuilder.isVersionLTE_7_2_0()>
						${finderCache}.removeResult(_finderPathWithPaginationCountAncestors, finderArgs);
					</#if>

					throw systemException;
				}
			}

			return count.intValue();
		}

		@Override
		public long countDescendants(${entity.name} ${entity.varName}) {
			Object[] finderArgs = new Object[] {${entity.varName}.get${scopeEntityColumn.methodName}(), ${entity.varName}.getLeft${pkEntityColumn.methodName}(), ${entity.varName}.getRight${pkEntityColumn.methodName}()};

			Long count = (Long)${finderCache}.getResult(_finderPathWithPaginationCountDescendants, finderArgs, this);

			if (count == null) {
				try {
					count = nestedSetsTreeManager.countDescendants(${entity.varName});

					${finderCache}.putResult(_finderPathWithPaginationCountDescendants, finderArgs, count);
				}
				catch (SystemException systemException) {
					<#if serviceBuilder.isVersionLTE_7_2_0()>
						${finderCache}.removeResult(_finderPathWithPaginationCountDescendants, finderArgs);
					</#if>

					throw systemException;
				}
			}

			return count.intValue();
		}

		@Override
		public List<${entity.name}> getAncestors(${entity.name} ${entity.varName}) {
			Object[] finderArgs = new Object[] {${entity.varName}.get${scopeEntityColumn.methodName}(), ${entity.varName}.getLeft${pkEntityColumn.methodName}(), ${entity.varName}.getRight${pkEntityColumn.methodName}()};

			List<${entity.name}> list = (List<${entity.name}>)${finderCache}.getResult(_finderPathWithPaginationGetAncestors, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (${entity.name} temp${entity.name} : list) {
					if ((${entity.varName}.getLeft${pkEntityColumn.methodName}() < temp${entity.name}.getLeft${pkEntityColumn.methodName}()) || (${entity.varName}.getRight${pkEntityColumn.methodName}() > temp${entity.name}.getRight${pkEntityColumn.methodName}())) {
						list = null;

						break;
					}
				}
			}

			if (list == null) {
				try {
					list = nestedSetsTreeManager.getAncestors(${entity.varName});

					cacheResult(list);

					${finderCache}.putResult(_finderPathWithPaginationGetAncestors, finderArgs, list);
				}
				catch (SystemException systemException) {
					<#if serviceBuilder.isVersionLTE_7_2_0()>
						${finderCache}.removeResult(_finderPathWithPaginationGetAncestors, finderArgs);
					</#if>

					throw systemException;
				}
			}

			return list;
		}

		@Override
		public List<${entity.name}> getDescendants(${entity.name} ${entity.varName}) {
			Object[] finderArgs = new Object[] {${entity.varName}.get${scopeEntityColumn.methodName}(), ${entity.varName}.getLeft${pkEntityColumn.methodName}(), ${entity.varName}.getRight${pkEntityColumn.methodName}()};

			List<${entity.name}> list = (List<${entity.name}>)${finderCache}.getResult(_finderPathWithPaginationGetDescendants, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (${entity.name} temp${entity.name} : list) {
					if ((${entity.varName}.getLeft${pkEntityColumn.methodName}() > temp${entity.name}.getLeft${pkEntityColumn.methodName}()) || (${entity.varName}.getRight${pkEntityColumn.methodName}() < temp${entity.name}.getRight${pkEntityColumn.methodName}())) {
						list = null;

						break;
					}
				}
			}

			if (list == null) {
				try {
					list = nestedSetsTreeManager.getDescendants(${entity.varName});

					cacheResult(list);

					${finderCache}.putResult(_finderPathWithPaginationGetDescendants, finderArgs, list);
				}
				catch (SystemException systemException) {
					<#if serviceBuilder.isVersionLTE_7_2_0()>
						${finderCache}.removeResult(_finderPathWithPaginationGetDescendants, finderArgs);
					</#if>

					throw systemException;
				}
			}

			return list;
		}

		/**
		 * Rebuilds the ${entity.pluralHumanName} tree for the scope using the modified pre-order tree traversal algorithm.
		 *
		 * <p>
		 * Only call this method if the tree has become stale through operations other than normal CRUD. Under normal circumstances the tree is automatically rebuilt whenver necessary.
		 * </p>
		 *
		 * @param ${scopeEntityColumn.name} the ID of the scope
		 * @param force whether to force the rebuild even if the tree is not stale
		 */
		@Override
		public void rebuildTree(long ${scopeEntityColumn.name}, boolean force) {
			if (!rebuildTreeEnabled) {
				return;
			}

			if (force || (countOrphanTreeNodes(${scopeEntityColumn.name}) > 0)) {
				Session session = null;

				try {
					session = openSession();

					if (session.isDirty()) {
						session.flush();
					}

					SQLQuery selectSQLQuery = session.createSQLQuery("SELECT ${pkEntityColumn.DBName} FROM ${entity.table} WHERE ${scopeEntityColumn.DBName} = ? AND parent${pkEntityColumn.methodName} = ? ORDER BY ${pkEntityColumn.DBName} ASC");

					selectSQLQuery.addScalar("${pkEntityColumn.name}", com.liferay.portal.kernel.dao.orm.Type.LONG);

					SQLQuery updateSQLQuery = session.createSQLQuery("UPDATE ${entity.table} SET left${pkEntityColumn.methodName} = ?, right${pkEntityColumn.methodName} = ? WHERE ${pkEntityColumn.DBName} = ?");

					rebuildTree(session, selectSQLQuery, updateSQLQuery, ${scopeEntityColumn.name}, 0, 0);
				}
				catch (Exception exception) {
					throw processException(exception);
				}
				finally {
					closeSession(session);
				}

				clearCache();
			}
		}

		@Override
		public void setRebuildTreeEnabled(boolean rebuildTreeEnabled) {
			this.rebuildTreeEnabled = rebuildTreeEnabled;
		}

		protected long countOrphanTreeNodes(long ${scopeEntityColumn.name}) {
			Session session = null;

			try {
				session = openSession();

				SQLQuery sqlQuery = session.createSynchronizedSQLQuery("SELECT COUNT(*) AS COUNT_VALUE FROM ${entity.table} WHERE ${scopeEntityColumn.DBName} = ? AND (left${pkEntityColumn.methodName} = 0 OR left${pkEntityColumn.methodName} IS NULL OR right${pkEntityColumn.methodName} = 0 OR right${pkEntityColumn.methodName} IS NULL)");

				sqlQuery.addScalar(COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

				QueryPos queryPos = QueryPos.getInstance(sqlQuery);

				queryPos.add(${scopeEntityColumn.name});

				return (Long)sqlQuery.uniqueResult();
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		protected long rebuildTree(Session session, SQLQuery selectSQLQuery, SQLQuery updateSQLQuery, long ${scopeEntityColumn.name}, long parent${pkEntityColumn.methodName}, long left${pkEntityColumn.methodName}) {
			long right${pkEntityColumn.methodName} = left${pkEntityColumn.methodName} + 1;

			QueryPos queryPos = QueryPos.getInstance(selectSQLQuery);

			queryPos.add(${scopeEntityColumn.name});
			queryPos.add(parent${pkEntityColumn.methodName});

			List<Long> ${pkEntityColumn.pluralName} = selectSQLQuery.list();

			for (long ${pkEntityColumn.name} : ${pkEntityColumn.pluralName}) {
				right${pkEntityColumn.methodName} = rebuildTree(session, selectSQLQuery, updateSQLQuery, ${scopeEntityColumn.name}, ${pkEntityColumn.name}, right${pkEntityColumn.methodName});
			}

			if (parent${pkEntityColumn.methodName} > 0) {
				queryPos = QueryPos.getInstance(updateSQLQuery);

				queryPos.add(left${pkEntityColumn.methodName});
				queryPos.add(right${pkEntityColumn.methodName});
				queryPos.add(parent${pkEntityColumn.methodName});

				updateSQLQuery.executeUpdate();
			}

			return right${pkEntityColumn.methodName} + 1;
		}
	</#if>

	/**
	 * Initializes the ${entity.humanName} persistence.
	 */
	<#if dependencyInjectorDS>
		@Activate
		public void activate() {
			<#if serviceBuilder.isVersionLTE_7_2_0()>
				${entity.name}ModelImpl.setEntityCacheEnabled(entityCacheEnabled);
				${entity.name}ModelImpl.setFinderCacheEnabled(finderCacheEnabled);
			</#if>

	<#else>
		public void afterPropertiesSet() {
	</#if>

		<#list entity.entityColumns as entityColumn>
			<#if entityColumn.isCollection() && entityColumn.isMappingManyToMany()>
				<#assign
					referenceEntity = serviceBuilder.getEntity(entityColumn.entityName)

					entityMapping = serviceBuilder.getEntityMapping(entityColumn.mappingTableName)

					companyEntity = serviceBuilder.getEntity(entityMapping.getEntityName(0))
				/>

				<#if dependencyInjectorDS>
					${entity.varName}To${referenceEntity.name}TableMapper = TableMapperFactory.getTableMapper("${entityColumn.mappingTableName}#${entity.PKDBName}", "${entityColumn.mappingTableName}", "${companyEntity.PKDBName}", "${entity.PKDBName}", "${referenceEntity.PKDBName}", this, ${referenceEntity.name}.class);
				<#else>
					${entity.varName}To${referenceEntity.name}TableMapper = TableMapperFactory.getTableMapper("${entityColumn.mappingTableName}", "${companyEntity.PKDBName}", "${entity.PKDBName}", "${referenceEntity.PKDBName}", this, ${referenceEntity.varName}Persistence);
				</#if>
			</#if>
		</#list>

		_finderPathWithPaginationFindAll = new FinderPath(
			<#if serviceBuilder.isVersionLTE_7_2_0()>
				${entityCacheEnabled},
				${finderCacheEnabled},
			</#if>
			${entity.name}Impl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			<#if serviceBuilder.isVersionLTE_7_2_0()>
				${entityCacheEnabled},
				${finderCacheEnabled},
			</#if>
			${entity.name}Impl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findAll", new String[0]);

		_finderPathCountAll = new FinderPath(
			<#if serviceBuilder.isVersionLTE_7_2_0()>
				${entityCacheEnabled},
				${finderCacheEnabled},
			</#if>
			Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countAll", new String[0]);

		<#if entity.isHierarchicalTree()>
			_finderPathWithPaginationCountAncestors = new FinderPath(
				<#if serviceBuilder.isVersionLTE_7_2_0()>
					${entityCacheEnabled},
					${finderCacheEnabled},
				</#if>
				Long.class,
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
				"countAncestors",
				new String[] {Long.class.getName(), Long.class.getName(), Long.class.getName()});

			_finderPathWithPaginationCountDescendants = new FinderPath(
				<#if serviceBuilder.isVersionLTE_7_2_0()>
					${entityCacheEnabled},
					${finderCacheEnabled},
				</#if>
				Long.class,
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
				"countDescendants",
				new String[] {Long.class.getName(), Long.class.getName(), Long.class.getName()});

			_finderPathWithPaginationGetAncestors = new FinderPath(
				<#if serviceBuilder.isVersionLTE_7_2_0()>
					${entityCacheEnabled},
					${finderCacheEnabled},
				</#if>
				${entity.name}Impl.class,
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
				"getAncestors",
				new String[] {Long.class.getName(), Long.class.getName(), Long.class.getName()});

			_finderPathWithPaginationGetDescendants = new FinderPath(
				<#if serviceBuilder.isVersionLTE_7_2_0()>
					${entityCacheEnabled},
					${finderCacheEnabled},
				</#if>
				${entity.name}Impl.class,
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
				"getDescendants",
				new String[] {Long.class.getName(), Long.class.getName(), Long.class.getName()});
		</#if>

		<#list entity.entityFinders as entityFinder>
			<#assign entityColumns = entityFinder.entityColumns />

			<#if entityFinder.isCollection()>
				_finderPathWithPaginationFindBy${entityFinder.name} = new FinderPath(
					<#if serviceBuilder.isVersionLTE_7_2_0()>
						${entityCacheEnabled},
						${finderCacheEnabled},
					</#if>
					${entity.name}Impl.class,
					FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
					"findBy${entityFinder.name}",
					new String[] {
						<#list entityColumns as entityColumn>
							${serviceBuilder.getPrimitiveObj("${entityColumn.type}")}.class.getName(),
						</#list>

						Integer.class.getName(), Integer.class.getName(), OrderByComparator.class.getName()
					});

				<#if !entityFinder.hasCustomComparator()>
					_finderPathWithoutPaginationFindBy${entityFinder.name} = new FinderPath(
						<#if serviceBuilder.isVersionLTE_7_2_0()>
							${entityCacheEnabled},
							${finderCacheEnabled},
						</#if>
						${entity.name}Impl.class,
						FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
						"findBy${entityFinder.name}",
						new String[] {
							<#list entityColumns as entityColumn>
								${serviceBuilder.getPrimitiveObj("${entityColumn.type}")}.class.getName()

								<#if entityColumn_has_next>
									,
								</#if>
							</#list>
						}

						<#if columnBitmaskEnabled>
							,

							<#list entityColumns as entityColumn>
								<#if serviceBuilder.isVersionGTE_7_3_0()>
									${entity.name}ModelImpl.getColumnBitmask("${entityColumn.DBName}")
								<#else>
									${entity.name}ModelImpl.${entityColumn.name?upper_case}_COLUMN_BITMASK
								</#if>

								<#if entityColumn_has_next>
									|
								</#if>
							</#list>

							<#if entity.entityOrder??>
								<#list entity.entityOrder.entityColumns as entityColumn>
									<#if !entityColumns?seq_contains(entityColumn) && !entity.PKEntityColumns?seq_contains(entityColumn)>
										|
										<#if serviceBuilder.isVersionGTE_7_3_0()>
											${entity.name}ModelImpl.getColumnBitmask("${entityColumn.DBName}")
										<#else>
											${entity.name}ModelImpl.${entityColumn.name?upper_case}_COLUMN_BITMASK
										</#if>
									</#if>
								</#list>
							</#if>
						</#if>

						);
				</#if>
			</#if>

			<#if !entityFinder.isCollection() || entityFinder.isUnique()>
				_finderPathFetchBy${entityFinder.name} = new FinderPath(
					<#if serviceBuilder.isVersionLTE_7_2_0()>
						${entityCacheEnabled},
						${finderCacheEnabled},
					</#if>
					${entity.name}Impl.class,
					FINDER_CLASS_NAME_ENTITY,
					"fetchBy${entityFinder.name}",
					new String[] {
						<#list entityColumns as entityColumn>
							${serviceBuilder.getPrimitiveObj("${entityColumn.type}")}.class.getName()

							<#if entityColumn_has_next>
								,
							</#if>
						</#list>
					}

					<#if columnBitmaskEnabled>
						,

						<#list entityColumns as entityColumn>
							<#if serviceBuilder.isVersionGTE_7_3_0()>
								${entity.name}ModelImpl.getColumnBitmask("${entityColumn.DBName}")
							<#else>
								${entity.name}ModelImpl.${entityColumn.name?upper_case}_COLUMN_BITMASK
							</#if>

							<#if entityColumn_has_next>
								|
							</#if>
						</#list>
					</#if>

					);
			</#if>

			<#if !entityFinder.hasCustomComparator()>
				_finderPathCountBy${entityFinder.name} = new FinderPath(
					<#if serviceBuilder.isVersionLTE_7_2_0()>
						${entityCacheEnabled},
						${finderCacheEnabled},
					</#if>
					Long.class,
					FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
					"countBy${entityFinder.name}",
					new String[] {
						<#list entityColumns as entityColumn>
							${serviceBuilder.getPrimitiveObj("${entityColumn.type}")}.class.getName()

							<#if entityColumn_has_next>
								,
							</#if>
						</#list>
					});
			</#if>

			<#if entityFinder.hasArrayableOperator() || entityFinder.hasCustomComparator()>
				_finderPathWithPaginationCountBy${entityFinder.name} = new FinderPath(
					<#if serviceBuilder.isVersionLTE_7_2_0()>
						${entityCacheEnabled},
						${finderCacheEnabled},
					</#if>
					Long.class,
					FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
					"countBy${entityFinder.name}",
					new String[] {
						<#list entityColumns as entityColumn>
							${serviceBuilder.getPrimitiveObj("${entityColumn.type}")}.class.getName()

							<#if entityColumn_has_next>
								,
							</#if>
						</#list>
					});
			</#if>
		</#list>
	}

	<#if dependencyInjectorDS>
		@Deactivate
		public void deactivate() {
	<#else>
		public void destroy() {
	</#if>

		${entityCache}.removeCache(${entity.name}Impl.class.getName());
		${finderCache}.removeCache(FINDER_CLASS_NAME_ENTITY);
		${finderCache}.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		${finderCache}.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		<#list entity.entityColumns as entityColumn>
			<#if entityColumn.isCollection() && entityColumn.isMappingManyToMany()>
				<#if dependencyInjectorDS>
					TableMapperFactory.removeTableMapper("${entityColumn.mappingTableName}#${entity.PKDBName}");
				<#else>
					TableMapperFactory.removeTableMapper("${entityColumn.mappingTableName}");
				</#if>
			</#if>
		</#list>
	}

	<#if dependencyInjectorDS>
		<#include "persistence_references.ftl">

		<#if serviceBuilder.isVersionLTE_7_2_0()>
			private boolean _columnBitmaskEnabled;
		</#if>
	</#if>

	<#if osgiModule>
		<#if entity.isChangeTrackingEnabled()>
			<#if dependencyInjectorDS>
				@Reference
			<#else>
				@ServiceReference(type = CTPersistenceHelper.class)
			</#if>

			protected CTPersistenceHelper ctPersistenceHelper;
		</#if>

		<#if dependencyInjectorDS>
			@Reference
		<#else>
			@ServiceReference(type = EntityCache.class)
		</#if>
		protected EntityCache entityCache;

		<#if dependencyInjectorDS>
			@Reference
		<#else>
			@ServiceReference(type = FinderCache.class)
		</#if>
		protected FinderCache finderCache;
	</#if>

	<#list entity.entityColumns as entityColumn>
		<#if entityColumn.isCollection() && entityColumn.isMappingManyToMany()>
			<#assign referenceEntity = serviceBuilder.getEntity(entityColumn.entityName) />

			<#if !dependencyInjectorDS>
				@BeanReference(type = ${referenceEntity.name}Persistence.class)
				protected ${referenceEntity.name}Persistence ${referenceEntity.varName}Persistence;
			</#if>

			protected TableMapper<${entity.name}, ${referenceEntity.apiPackagePath}.model.${referenceEntity.name}> ${entity.varName}To${referenceEntity.name}TableMapper;
		</#if>
	</#list>

	<#if entity.isHierarchicalTree()>
		protected NestedSetsTreeManager<${entity.name}> nestedSetsTreeManager = new PersistenceNestedSetsTreeManager<${entity.name}>(this, "${entity.table}", "${entity.name}", ${entity.name}Impl.class, "${pkEntityColumn.DBName}", "${scopeEntityColumn.DBName}", "left${pkEntityColumn.methodName}", "right${pkEntityColumn.methodName}");
		protected boolean rebuildTreeEnabled = true;
	</#if>

	<#assign hasDateFinder = false />

	<#list entity.entityFinders as entityFinder>
		<#assign entityColumns = entityFinder.entityColumns />

		<#list entityColumns as entityColumn>
			<#if stringUtil.equals(entityColumn.type, "Date")>
				<#assign hasDateFinder = true />

				<#break>
			</#if>
		</#list>
	</#list>

	<#if hasDateFinder>
		private Long _getTime(Date date) {
			if (date == null) {
				return null;
			}

			return date.getTime();
		}
	</#if>

	private static final String _SQL_SELECT_${entity.alias?upper_case} = "SELECT ${entity.alias} FROM ${entity.name} ${entity.alias}";

	<#if !entity.hasCompoundPK() && serviceBuilder.isVersionLTE_7_1_0()>
		private static final String _SQL_SELECT_${entity.alias?upper_case}_WHERE_PKS_IN = "SELECT ${entity.alias} FROM ${entity.name} ${entity.alias} WHERE ${entity.PKDBName} IN (";
	</#if>

	<#if entity.entityFinders?size != 0>
		private static final String _SQL_SELECT_${entity.alias?upper_case}_WHERE = "SELECT ${entity.alias} FROM ${entity.name} ${entity.alias} WHERE ";
	</#if>

	private static final String _SQL_COUNT_${entity.alias?upper_case} = "SELECT COUNT(${entity.alias}) FROM ${entity.name} ${entity.alias}";

	<#if entity.entityFinders?size != 0>
		private static final String _SQL_COUNT_${entity.alias?upper_case}_WHERE = "SELECT COUNT(${entity.alias}) FROM ${entity.name} ${entity.alias} WHERE ";
	</#if>

	<#if entity.isPermissionCheckEnabled()>
		private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN = "${entity.alias}.${entity.filterPKEntityColumn.DBName}";

		<#if entity.isPermissionedModel()>
			<#if entity.hasEntityColumn("userId")>
				private static final String _FILTER_ENTITY_TABLE_FILTER_USERID_COLUMN = "${entity.alias}.userId";
			<#else>
				private static final String _FILTER_ENTITY_TABLE_FILTER_USERID_COLUMN = null;
			</#if>
		<#else>
			private static final String _FILTER_SQL_SELECT_${entity.alias?upper_case}_WHERE = "SELECT DISTINCT {${entity.alias}.*} FROM ${entity.table} ${entity.alias} WHERE ";

			private static final String _FILTER_SQL_SELECT_${entity.alias?upper_case}_NO_INLINE_DISTINCT_WHERE_1 = "SELECT {${entity.table}.*} FROM (SELECT DISTINCT ${entity.alias}.${entity.PKDBName} FROM ${entity.table} ${entity.alias} WHERE ";

			private static final String _FILTER_SQL_SELECT_${entity.alias?upper_case}_NO_INLINE_DISTINCT_WHERE_2 = ") TEMP_TABLE INNER JOIN ${entity.table} ON TEMP_TABLE.${entity.PKDBName} = ${entity.table}.${entity.PKDBName}";

			private static final String _FILTER_SQL_COUNT_${entity.alias?upper_case}_WHERE = "SELECT COUNT(DISTINCT ${entity.alias}.${entity.PKDBName}) AS COUNT_VALUE FROM ${entity.table} ${entity.alias} WHERE ";

			private static final String _FILTER_ENTITY_ALIAS = "${entity.alias}";

			private static final String _FILTER_ENTITY_TABLE = "${entity.table}";
		</#if>
	</#if>

	private static final String _ORDER_BY_ENTITY_ALIAS = "${entity.alias}.";

	<#if entity.isPermissionCheckEnabled() && !entity.isPermissionedModel()>
		private static final String _ORDER_BY_ENTITY_TABLE = "${entity.table}.";
	</#if>

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No ${entity.name} exists with the primary key ";

	<#if entity.entityFinders?size != 0>
		private static final String _NO_SUCH_ENTITY_WITH_KEY = "No ${entity.name} exists with the key {";
	</#if>

	private static final Log _log = LogFactoryUtil.getLog(${entity.name}PersistenceImpl.class);

	<#if entity.badEntityColumns?size != 0>
		private static final Set<String> _badColumnNames = SetUtil.fromArray(
			new String[] {
				<#list entity.badEntityColumns as badEntityColumn>
					"${badEntityColumn.name}"

					<#if badEntityColumn_has_next>
						,
					</#if>
				</#list>
			});
	</#if>

	<#if entity.hasCompoundPK()>
		private static final Set<String> _compoundPKColumnNames = SetUtil.fromArray(
			new String[] {
				<#list entity.PKEntityColumns as entityColumn>
					"${entityColumn.name}"

					<#if entityColumn_has_next>
						,
					</#if>
				</#list>
			});
	</#if>

	<#if dependencyInjectorDS>
		static {
			try {
				Class.forName(${portletShortName}PersistenceConstants.class.getName());
			}
			catch (ClassNotFoundException classNotFoundException) {
				throw new ExceptionInInitializerError(classNotFoundException);
			}
		}
	</#if>

}

<#function bindParameter entityColumns>
	<#list entityColumns as entityColumn>
		<#if !entityColumn.hasArrayableOperator() || stringUtil.equals(entityColumn.type, "String")>
			<#return true>
		</#if>
	</#list>

	<#return false>
</#function>

<#macro finderQPos
	_arrayable = false
>
	<#list entityColumns as entityColumn>
		<#if _arrayable && entityColumn.hasArrayableOperator()>
			<#if stringUtil.equals(entityColumn.type, "String")>
				for (String ${entityColumn.name} : ${entityColumn.pluralName}) {
					if (${entityColumn.name} != null && !${entityColumn.name}.isEmpty()) {
						queryPos.add(${entityColumn.name});
					}
				}
			</#if>
		<#elseif entityColumn.isPrimitiveType()>
			queryPos.add(${entityColumn.name}${serviceBuilder.getPrimitiveObjValue("${entityColumn.type}")});

		<#else>
			if (bind${entityColumn.methodName}) {
				queryPos.add(
					<#if stringUtil.equals(entityColumn.type, "Date")>
						new Timestamp(${entityColumn.name}.getTime())
					<#elseif stringUtil.equals(entityColumn.type, "String") && !entityColumn.isCaseSensitive()>
						StringUtil.toLowerCase(${entityColumn.name})
					<#else>
						${entityColumn.name}
					</#if>
				);
			}
		</#if>
	</#list>
</#macro>