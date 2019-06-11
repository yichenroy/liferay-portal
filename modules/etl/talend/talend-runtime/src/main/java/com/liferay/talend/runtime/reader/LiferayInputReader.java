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

package com.liferay.talend.runtime.reader;

import com.fasterxml.jackson.databind.JsonNode;

import com.liferay.talend.avro.ResourceNodeConverter;
import com.liferay.talend.runtime.LiferaySource;
import com.liferay.talend.tliferayinput.TLiferayInputProperties;
import com.liferay.talend.utils.URIUtils;

import java.io.IOException;

import java.net.URI;

import java.util.NoSuchElementException;

import javax.ws.rs.core.UriBuilder;

import org.apache.avro.generic.IndexedRecord;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.talend.components.api.container.RuntimeContainer;
import org.talend.components.api.exception.ComponentException;
import org.talend.daikon.avro.converter.AvroConverter;

/**
 * @author Zoltán Takács
 */
public class LiferayInputReader extends LiferayBaseReader<IndexedRecord> {

	public LiferayInputReader(
		RuntimeContainer runtimeContainer, LiferaySource liferaySource,
		TLiferayInputProperties tLiferayInputProperties) {

		super(runtimeContainer, liferaySource);

		liferayConnectionResourceBaseProperties = tLiferayInputProperties;
		_queryCondition = tLiferayInputProperties.resource.condition.getValue();
	}

	@Override
	public boolean advance() throws IOException {
		if (!_started) {
			throw new IllegalStateException("Reader was not started");
		}

		_inputRecordsIndex++;

		// Fast return conditions

		if (_inputRecordsIndex < _inputRecordsJsonNode.size()) {
			dataCount++;
			_hasMore = true;

			return true;
		}

		int actual = _endpointJsonNode.path(
			"page"
		).asInt();
		int last = _endpointJsonNode.path(
			"lastPage"
		).asInt();

		if (actual >= last) {
			_hasMore = false;

			return false;
		}

		_hasMore = true;

		URI endpointURI =
			liferayConnectionResourceBaseProperties.resource.getEndpointURI();

		_endpointJsonNode = _getEndpointJsonNode(endpointURI, ++actual, -1);

		_inputRecordsJsonNode = _endpointJsonNode.path("items");

		_inputRecordsIndex = 0;

		_hasMore = _inputRecordsJsonNode.size() > 0;

		if (_hasMore) {

			// New result set available to retrieve

			dataCount++;
		}

		return _hasMore;
	}

	@Override
	public IndexedRecord getCurrent() throws NoSuchElementException {
		if (!_started) {
			throw new NoSuchElementException("Reader was not started");
		}

		if (!_hasMore) {
			throw new NoSuchElementException(
				"Resource does not have more elements");
		}

		try {
			AvroConverter<Object, IndexedRecord> avroConverter = getConverter();

			return avroConverter.convertToAvro(getCurrentJsonNode());
		}
		catch (IOException ioe) {
			throw new ComponentException(ioe);
		}
	}

	public JsonNode getCurrentJsonNode() throws NoSuchElementException {
		return _inputRecordsJsonNode.get(_inputRecordsIndex);
	}

	@Override
	public boolean start() throws IOException {
		URI endpointURI =
			liferayConnectionResourceBaseProperties.resource.getEndpointURI();

		_endpointJsonNode = _getEndpointJsonNode(endpointURI, 1, -1);

		_inputRecordsJsonNode = _endpointJsonNode.path("items");

		boolean start = false;

		if (_inputRecordsJsonNode.size() > 0) {
			start = true;
		}

		if (!start) {
			return false;
		}

		dataCount++;
		_inputRecordsIndex = 0;
		_started = true;
		_hasMore = true;

		return start;
	}

	/**
	 * Returns implementation of AvroConverter, creates it if it does not exist.
	 *
	 * @return converter
	 * @throws IOException
	 * @review
	 */
	protected AvroConverter<Object, IndexedRecord> getConverter()
		throws IOException {

		if (_resourceEntityAvroConverter == null) {
			_resourceEntityAvroConverter = new ResourceNodeConverter(
				getSchema());
		}

		return _resourceEntityAvroConverter;
	}

	private JsonNode _getEndpointJsonNode(
		URI endpointURI, int page, int pageSize) {

		if (page <= 0) {
			page = 1;
		}

		if (pageSize == -1) {
			pageSize =
				liferayConnectionResourceBaseProperties.connection.itemsPerPage.
					getValue();
		}

		UriBuilder uriBuilder = UriBuilder.fromUri(endpointURI);

		URI resourceURI = uriBuilder.queryParam(
			"page", page
		).queryParam(
			"pageSize", pageSize
		).build();

		URI decoratedResourceURI = URIUtils.addQueryConditionToURL(
			resourceURI.toASCIIString(), _queryCondition);

		LiferaySource liferaySource = (LiferaySource)getCurrentSource();

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Started to process resources at entry point: " +
					decoratedResourceURI.toString());
		}

		return liferaySource.doGetRequest(decoratedResourceURI.toString());
	}

	private static final Logger _log = LoggerFactory.getLogger(
		LiferayInputReader.class);

	private transient JsonNode _endpointJsonNode;

	/**
	 * Represents state of this Reader: whether it has more records
	 *
	 * @review
	 */
	private boolean _hasMore;

	private transient int _inputRecordsIndex;

	/**
	 * Resource collection members field
	 *
	 * @review
	 */
	private transient JsonNode _inputRecordsJsonNode;

	private final String _queryCondition;

	/**
	 * Converts row retrieved from data source to Avro format {@link
	 * IndexedRecord}
	 *
	 * @review
	 */
	private AvroConverter _resourceEntityAvroConverter;

	/**
	 * Represents state of this Reader: whether it was started or not
	 *
	 * @review
	 */
	private boolean _started;

}