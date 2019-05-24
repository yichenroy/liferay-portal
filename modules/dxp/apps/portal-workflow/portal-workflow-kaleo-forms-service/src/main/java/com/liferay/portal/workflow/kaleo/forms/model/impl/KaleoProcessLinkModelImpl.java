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

package com.liferay.portal.workflow.kaleo.forms.model.impl;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.impl.BaseModelImpl;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.workflow.kaleo.forms.model.KaleoProcessLink;
import com.liferay.portal.workflow.kaleo.forms.model.KaleoProcessLinkModel;

import java.io.Serializable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;

import java.sql.Types;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The base model implementation for the KaleoProcessLink service. Represents a row in the &quot;KaleoProcessLink&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface </code>KaleoProcessLinkModel</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link KaleoProcessLinkImpl}.
 * </p>
 *
 * @author Marcellus Tavares
 * @see KaleoProcessLinkImpl
 * @generated
 */
@ProviderType
public class KaleoProcessLinkModelImpl
	extends BaseModelImpl<KaleoProcessLink> implements KaleoProcessLinkModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a kaleo process link model instance should use the <code>KaleoProcessLink</code> interface instead.
	 */
	public static final String TABLE_NAME = "KaleoProcessLink";

	public static final Object[][] TABLE_COLUMNS = {
		{"kaleoProcessLinkId", Types.BIGINT}, {"kaleoProcessId", Types.BIGINT},
		{"workflowTaskName", Types.VARCHAR}, {"DDMTemplateId", Types.BIGINT}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
		new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("kaleoProcessLinkId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("kaleoProcessId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("workflowTaskName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("DDMTemplateId", Types.BIGINT);
	}

	public static final String TABLE_SQL_CREATE =
		"create table KaleoProcessLink (kaleoProcessLinkId LONG not null primary key,kaleoProcessId LONG,workflowTaskName VARCHAR(75) null,DDMTemplateId LONG)";

	public static final String TABLE_SQL_DROP = "drop table KaleoProcessLink";

	public static final String ORDER_BY_JPQL =
		" ORDER BY kaleoProcessLink.kaleoProcessLinkId ASC";

	public static final String ORDER_BY_SQL =
		" ORDER BY KaleoProcessLink.kaleoProcessLinkId ASC";

	public static final String DATA_SOURCE = "liferayDataSource";

	public static final String SESSION_FACTORY = "liferaySessionFactory";

	public static final String TX_MANAGER = "liferayTransactionManager";

	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(
		com.liferay.portal.workflow.kaleo.forms.service.util.ServiceProps.get(
			"value.object.entity.cache.enabled.com.liferay.portal.workflow.kaleo.forms.model.KaleoProcessLink"),
		true);

	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(
		com.liferay.portal.workflow.kaleo.forms.service.util.ServiceProps.get(
			"value.object.finder.cache.enabled.com.liferay.portal.workflow.kaleo.forms.model.KaleoProcessLink"),
		true);

	public static final boolean COLUMN_BITMASK_ENABLED = GetterUtil.getBoolean(
		com.liferay.portal.workflow.kaleo.forms.service.util.ServiceProps.get(
			"value.object.column.bitmask.enabled.com.liferay.portal.workflow.kaleo.forms.model.KaleoProcessLink"),
		true);

	public static final long KALEOPROCESSID_COLUMN_BITMASK = 1L;

	public static final long WORKFLOWTASKNAME_COLUMN_BITMASK = 2L;

	public static final long KALEOPROCESSLINKID_COLUMN_BITMASK = 4L;

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(
		com.liferay.portal.workflow.kaleo.forms.service.util.ServiceProps.get(
			"lock.expiration.time.com.liferay.portal.workflow.kaleo.forms.model.KaleoProcessLink"));

	public KaleoProcessLinkModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _kaleoProcessLinkId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setKaleoProcessLinkId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _kaleoProcessLinkId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return KaleoProcessLink.class;
	}

	@Override
	public String getModelClassName() {
		return KaleoProcessLink.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		Map<String, Function<KaleoProcessLink, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		for (Map.Entry<String, Function<KaleoProcessLink, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<KaleoProcessLink, Object> attributeGetterFunction =
				entry.getValue();

			attributes.put(
				attributeName,
				attributeGetterFunction.apply((KaleoProcessLink)this));
		}

		attributes.put("entityCacheEnabled", isEntityCacheEnabled());
		attributes.put("finderCacheEnabled", isFinderCacheEnabled());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Map<String, BiConsumer<KaleoProcessLink, Object>>
			attributeSetterBiConsumers = getAttributeSetterBiConsumers();

		for (Map.Entry<String, Object> entry : attributes.entrySet()) {
			String attributeName = entry.getKey();

			BiConsumer<KaleoProcessLink, Object> attributeSetterBiConsumer =
				attributeSetterBiConsumers.get(attributeName);

			if (attributeSetterBiConsumer != null) {
				attributeSetterBiConsumer.accept(
					(KaleoProcessLink)this, entry.getValue());
			}
		}
	}

	public Map<String, Function<KaleoProcessLink, Object>>
		getAttributeGetterFunctions() {

		return _attributeGetterFunctions;
	}

	public Map<String, BiConsumer<KaleoProcessLink, Object>>
		getAttributeSetterBiConsumers() {

		return _attributeSetterBiConsumers;
	}

	private static Function<InvocationHandler, KaleoProcessLink>
		_getProxyProviderFunction() {

		Class<?> proxyClass = ProxyUtil.getProxyClass(
			KaleoProcessLink.class.getClassLoader(), KaleoProcessLink.class,
			ModelWrapper.class);

		try {
			Constructor<KaleoProcessLink> constructor =
				(Constructor<KaleoProcessLink>)proxyClass.getConstructor(
					InvocationHandler.class);

			return invocationHandler -> {
				try {
					return constructor.newInstance(invocationHandler);
				}
				catch (ReflectiveOperationException roe) {
					throw new InternalError(roe);
				}
			};
		}
		catch (NoSuchMethodException nsme) {
			throw new InternalError(nsme);
		}
	}

	private static final Map<String, Function<KaleoProcessLink, Object>>
		_attributeGetterFunctions;
	private static final Map<String, BiConsumer<KaleoProcessLink, Object>>
		_attributeSetterBiConsumers;

	static {
		Map<String, Function<KaleoProcessLink, Object>>
			attributeGetterFunctions =
				new LinkedHashMap<String, Function<KaleoProcessLink, Object>>();
		Map<String, BiConsumer<KaleoProcessLink, ?>>
			attributeSetterBiConsumers =
				new LinkedHashMap<String, BiConsumer<KaleoProcessLink, ?>>();

		attributeGetterFunctions.put(
			"kaleoProcessLinkId", KaleoProcessLink::getKaleoProcessLinkId);
		attributeSetterBiConsumers.put(
			"kaleoProcessLinkId",
			(BiConsumer<KaleoProcessLink, Long>)
				KaleoProcessLink::setKaleoProcessLinkId);
		attributeGetterFunctions.put(
			"kaleoProcessId", KaleoProcessLink::getKaleoProcessId);
		attributeSetterBiConsumers.put(
			"kaleoProcessId",
			(BiConsumer<KaleoProcessLink, Long>)
				KaleoProcessLink::setKaleoProcessId);
		attributeGetterFunctions.put(
			"workflowTaskName", KaleoProcessLink::getWorkflowTaskName);
		attributeSetterBiConsumers.put(
			"workflowTaskName",
			(BiConsumer<KaleoProcessLink, String>)
				KaleoProcessLink::setWorkflowTaskName);
		attributeGetterFunctions.put(
			"DDMTemplateId", KaleoProcessLink::getDDMTemplateId);
		attributeSetterBiConsumers.put(
			"DDMTemplateId",
			(BiConsumer<KaleoProcessLink, Long>)
				KaleoProcessLink::setDDMTemplateId);

		_attributeGetterFunctions = Collections.unmodifiableMap(
			attributeGetterFunctions);
		_attributeSetterBiConsumers = Collections.unmodifiableMap(
			(Map)attributeSetterBiConsumers);
	}

	@Override
	public long getKaleoProcessLinkId() {
		return _kaleoProcessLinkId;
	}

	@Override
	public void setKaleoProcessLinkId(long kaleoProcessLinkId) {
		_kaleoProcessLinkId = kaleoProcessLinkId;
	}

	@Override
	public long getKaleoProcessId() {
		return _kaleoProcessId;
	}

	@Override
	public void setKaleoProcessId(long kaleoProcessId) {
		_columnBitmask |= KALEOPROCESSID_COLUMN_BITMASK;

		if (!_setOriginalKaleoProcessId) {
			_setOriginalKaleoProcessId = true;

			_originalKaleoProcessId = _kaleoProcessId;
		}

		_kaleoProcessId = kaleoProcessId;
	}

	public long getOriginalKaleoProcessId() {
		return _originalKaleoProcessId;
	}

	@Override
	public String getWorkflowTaskName() {
		if (_workflowTaskName == null) {
			return "";
		}
		else {
			return _workflowTaskName;
		}
	}

	@Override
	public void setWorkflowTaskName(String workflowTaskName) {
		_columnBitmask |= WORKFLOWTASKNAME_COLUMN_BITMASK;

		if (_originalWorkflowTaskName == null) {
			_originalWorkflowTaskName = _workflowTaskName;
		}

		_workflowTaskName = workflowTaskName;
	}

	public String getOriginalWorkflowTaskName() {
		return GetterUtil.getString(_originalWorkflowTaskName);
	}

	@Override
	public long getDDMTemplateId() {
		return _DDMTemplateId;
	}

	@Override
	public void setDDMTemplateId(long DDMTemplateId) {
		_DDMTemplateId = DDMTemplateId;
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(
			0, KaleoProcessLink.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public KaleoProcessLink toEscapedModel() {
		if (_escapedModel == null) {
			_escapedModel = _escapedModelProxyProviderFunction.apply(
				new AutoEscapeBeanHandler(this));
		}

		return _escapedModel;
	}

	@Override
	public Object clone() {
		KaleoProcessLinkImpl kaleoProcessLinkImpl = new KaleoProcessLinkImpl();

		kaleoProcessLinkImpl.setKaleoProcessLinkId(getKaleoProcessLinkId());
		kaleoProcessLinkImpl.setKaleoProcessId(getKaleoProcessId());
		kaleoProcessLinkImpl.setWorkflowTaskName(getWorkflowTaskName());
		kaleoProcessLinkImpl.setDDMTemplateId(getDDMTemplateId());

		kaleoProcessLinkImpl.resetOriginalValues();

		return kaleoProcessLinkImpl;
	}

	@Override
	public int compareTo(KaleoProcessLink kaleoProcessLink) {
		long primaryKey = kaleoProcessLink.getPrimaryKey();

		if (getPrimaryKey() < primaryKey) {
			return -1;
		}
		else if (getPrimaryKey() > primaryKey) {
			return 1;
		}
		else {
			return 0;
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof KaleoProcessLink)) {
			return false;
		}

		KaleoProcessLink kaleoProcessLink = (KaleoProcessLink)obj;

		long primaryKey = kaleoProcessLink.getPrimaryKey();

		if (getPrimaryKey() == primaryKey) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return (int)getPrimaryKey();
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return ENTITY_CACHE_ENABLED;
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return FINDER_CACHE_ENABLED;
	}

	@Override
	public void resetOriginalValues() {
		KaleoProcessLinkModelImpl kaleoProcessLinkModelImpl = this;

		kaleoProcessLinkModelImpl._originalKaleoProcessId =
			kaleoProcessLinkModelImpl._kaleoProcessId;

		kaleoProcessLinkModelImpl._setOriginalKaleoProcessId = false;

		kaleoProcessLinkModelImpl._originalWorkflowTaskName =
			kaleoProcessLinkModelImpl._workflowTaskName;

		kaleoProcessLinkModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<KaleoProcessLink> toCacheModel() {
		KaleoProcessLinkCacheModel kaleoProcessLinkCacheModel =
			new KaleoProcessLinkCacheModel();

		kaleoProcessLinkCacheModel.kaleoProcessLinkId = getKaleoProcessLinkId();

		kaleoProcessLinkCacheModel.kaleoProcessId = getKaleoProcessId();

		kaleoProcessLinkCacheModel.workflowTaskName = getWorkflowTaskName();

		String workflowTaskName = kaleoProcessLinkCacheModel.workflowTaskName;

		if ((workflowTaskName != null) && (workflowTaskName.length() == 0)) {
			kaleoProcessLinkCacheModel.workflowTaskName = null;
		}

		kaleoProcessLinkCacheModel.DDMTemplateId = getDDMTemplateId();

		return kaleoProcessLinkCacheModel;
	}

	@Override
	public String toString() {
		Map<String, Function<KaleoProcessLink, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			4 * attributeGetterFunctions.size() + 2);

		sb.append("{");

		for (Map.Entry<String, Function<KaleoProcessLink, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<KaleoProcessLink, Object> attributeGetterFunction =
				entry.getValue();

			sb.append(attributeName);
			sb.append("=");
			sb.append(attributeGetterFunction.apply((KaleoProcessLink)this));
			sb.append(", ");
		}

		if (sb.index() > 1) {
			sb.setIndex(sb.index() - 1);
		}

		sb.append("}");

		return sb.toString();
	}

	@Override
	public String toXmlString() {
		Map<String, Function<KaleoProcessLink, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			5 * attributeGetterFunctions.size() + 4);

		sb.append("<model><model-name>");
		sb.append(getModelClassName());
		sb.append("</model-name>");

		for (Map.Entry<String, Function<KaleoProcessLink, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<KaleoProcessLink, Object> attributeGetterFunction =
				entry.getValue();

			sb.append("<column><column-name>");
			sb.append(attributeName);
			sb.append("</column-name><column-value><![CDATA[");
			sb.append(attributeGetterFunction.apply((KaleoProcessLink)this));
			sb.append("]]></column-value></column>");
		}

		sb.append("</model>");

		return sb.toString();
	}

	private static final Function<InvocationHandler, KaleoProcessLink>
		_escapedModelProxyProviderFunction = _getProxyProviderFunction();

	private long _kaleoProcessLinkId;
	private long _kaleoProcessId;
	private long _originalKaleoProcessId;
	private boolean _setOriginalKaleoProcessId;
	private String _workflowTaskName;
	private String _originalWorkflowTaskName;
	private long _DDMTemplateId;
	private long _columnBitmask;
	private KaleoProcessLink _escapedModel;

}