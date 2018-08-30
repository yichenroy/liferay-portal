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

package com.liferay.talend.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.avro.JsonProperties;
import org.apache.avro.Schema;

import org.talend.components.api.component.Connector;
import org.talend.components.api.properties.ComponentProperties;
import org.talend.daikon.avro.SchemaConstants;
import org.talend.daikon.exception.TalendRuntimeException;
import org.talend.daikon.exception.TalendRuntimeException.TalendRuntimeExceptionBuilder;
import org.talend.daikon.exception.error.CommonErrorCodes;

/**
 * @author Zoltán Takács
 */
public class SchemaUtils {

	public static Schema appendFields(
		Schema schema, List<Schema.Field> fields) {

		if (schema.getType() != Schema.Type.RECORD) {
			TalendRuntimeExceptionBuilder talendRuntimeExceptionBuilder =
				TalendRuntimeException.build(
					CommonErrorCodes.UNEXPECTED_EXCEPTION);

			talendRuntimeExceptionBuilder.setAndThrow(
				"Schema type is not supported: " + schema.getType()
			);
		}

		Schema newSchema = Schema.createRecord(
			schema.getName(), schema.getDoc(), schema.getNamespace(),
			schema.isError());

		List<Schema.Field> copiedFieldList = new ArrayList<>();

		_copyFields(schema.getFields(), copiedFieldList);
		_copyFields(fields, copiedFieldList);

		newSchema.setFields(copiedFieldList);

		_copyFieldProperties(schema.getObjectProps(), newSchema);

		return newSchema;
	}

	/**
	 * Gets the main schema from the input connector of output components.
	 *
	 * @param  componentProperties
	 * @return Schema
	 */
	public static Schema getMainSchemaFromInputConnector(
		ComponentProperties componentProperties) {

		Set<? extends Connector> inputConnectors =
			componentProperties.getPossibleConnectors(false);

		if (inputConnectors == null) {
			return null;
		}

		for (Connector connector : inputConnectors) {
			if (Connector.MAIN_NAME.equals(connector.getName())) {
				return componentProperties.getSchema(connector, false);
			}
		}

		return null;
	}

	/**
	 * Gets main schema from the out connector of input components
	 *
	 * @param  componentProperties
	 * @return Schema
	 */
	public static Schema getMainSchemaFromOutputConnector(
		ComponentProperties componentProperties) {

		return getOutputSchema(componentProperties);
	}

	/**
	 * Gets the output schema from the properties
	 *
	 * @param  componentProperties
	 * @return Schema
	 */
	public static Schema getOutputSchema(
		ComponentProperties componentProperties) {

		Set<? extends Connector> outputConnectors =
			componentProperties.getPossibleConnectors(true);

		if (outputConnectors == null) {
			return null;
		}

		for (Connector connector : outputConnectors) {
			if (Connector.MAIN_NAME.equals(connector.getName())) {
				return componentProperties.getSchema(connector, true);
			}
		}

		return null;
	}

	/**
	 * Gets the reject schema from the given properties
	 *
	 * @param componentProperties
	 * @return
	 */
	public static Schema getRejectSchema(
		ComponentProperties componentProperties) {

		Set<? extends Connector> outputConnectors =
			componentProperties.getPossibleConnectors(true);

		if (outputConnectors == null) {
			return null;
		}

		for (Connector connector : outputConnectors) {
			if (Connector.REJECT_NAME.equals(connector.getName())) {
				return componentProperties.getSchema(connector, true);
			}
		}

		return null;
	}

	public static Schema mergeRuntimeSchemaWithDesignSchemaDynamic(
		Schema designSchema, Schema runtimeSchema) {

		List<Schema.Field> designFields = designSchema.getFields();
		Set<String> designFieldSet = new HashSet<>();

		for (Schema.Field designField : designFields) {
			String name = designField.getProp(
				SchemaConstants.TALEND_COLUMN_DB_COLUMN_NAME);

			designFieldSet.add(name);
		}

		List<Schema.Field> dynamicFields = new ArrayList<>();

		for (Schema.Field runtimeField : runtimeSchema.getFields()) {
			String name = runtimeField.getProp(
				SchemaConstants.TALEND_COLUMN_DB_COLUMN_NAME);

			if (!designFieldSet.contains(name)) {
				dynamicFields.add(runtimeField);
			}
		}

		dynamicFields = _cloneFieldsAndResetPosition(dynamicFields);

		int dynamicPosition = Integer.valueOf(
			designSchema.getProp("di.dynamic.column.position"));

		return newSchema(
			designSchema, designSchema.getName(), dynamicFields,
			dynamicPosition);
	}

	/**
	 * Clones the source schema with a new name and add some additional fields
	 *
	 * @param  metadataSchema
	 * @param  newSchemaName
	 * @param  moreFields
	 * @return Schema
	 */
	public static Schema newSchema(
		Schema metadataSchema, String newSchemaName,
		List<Schema.Field> moreFields) {

		List<Schema.Field> fields = metadataSchema.getFields();

		return newSchema(
			metadataSchema, newSchemaName, moreFields, fields.size());
	}

	public static Schema newSchema(
		Schema metadataSchema, String newSchemaName,
		List<Schema.Field> moreFields, int insertPoint) {

		Schema newSchema = Schema.createRecord(
			newSchemaName, metadataSchema.getDoc(),
			metadataSchema.getNamespace(), metadataSchema.isError());

		List<Schema.Field> fields = metadataSchema.getFields();

		List<Schema.Field> copyFieldList = _cloneFieldsAndResetPosition(fields);

		copyFieldList.addAll(insertPoint, moreFields);

		newSchema.setFields(copyFieldList);

		Map<String, Object> objectProperties = metadataSchema.getObjectProps();

		for (Map.Entry<String, Object> entry : objectProperties.entrySet()) {
			newSchema.addProp(entry.getKey(), entry.getValue());
		}

		return newSchema;
	}

	private static List<Schema.Field> _cloneFieldsAndResetPosition(
		List<Schema.Field> fields) {

		List<Schema.Field> copyFieldList = new ArrayList<>();

		for (Schema.Field schemaEntry : fields) {
			Schema.Field field = new Schema.Field(
				schemaEntry.name(), schemaEntry.schema(), schemaEntry.doc(),
				schemaEntry.defaultVal(), schemaEntry.order());

			Map<String, Object> objectProperties = schemaEntry.getObjectProps();

			for (Map.Entry<String, Object> entry :
					objectProperties.entrySet()) {

				field.addProp(entry.getKey(), entry.getValue());
			}

			copyFieldList.add(field);
		}

		return copyFieldList;
	}

	private static <T extends JsonProperties> void _copyFieldProperties(
		Map<String, Object> objectProperties, T avroDataType) {

		for (Map.Entry<String, Object> entry : objectProperties.entrySet()) {
			avroDataType.addProp(entry.getKey(), entry.getValue());
		}
	}

	private static void _copyFields(
		List<Schema.Field> fields, List<Schema.Field> copiedFieldList) {

		for (Schema.Field field : fields) {
			Schema.Field newField = new Schema.Field(
				field.name(), field.schema(), field.doc(), field.defaultVal());

			_copyFieldProperties(field.getObjectProps(), newField);
			copiedFieldList.add(newField);
		}
	}

	private SchemaUtils() {
	}

}