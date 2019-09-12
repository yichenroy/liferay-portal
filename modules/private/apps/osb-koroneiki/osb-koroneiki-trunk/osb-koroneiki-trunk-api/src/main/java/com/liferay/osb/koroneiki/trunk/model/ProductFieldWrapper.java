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

package com.liferay.osb.koroneiki.trunk.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.HashMap;
import java.util.Map;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <p>
 * This class is a wrapper for {@link ProductField}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ProductField
 * @generated
 */
@ProviderType
public class ProductFieldWrapper
	extends BaseModelWrapper<ProductField>
	implements ProductField, ModelWrapper<ProductField> {

	public ProductFieldWrapper(ProductField productField) {
		super(productField);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("productFieldId", getProductFieldId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("name", getName());
		attributes.put("value", getValue());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long productFieldId = (Long)attributes.get("productFieldId");

		if (productFieldId != null) {
			setProductFieldId(productFieldId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		Long classNameId = (Long)attributes.get("classNameId");

		if (classNameId != null) {
			setClassNameId(classNameId);
		}

		Long classPK = (Long)attributes.get("classPK");

		if (classPK != null) {
			setClassPK(classPK);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String value = (String)attributes.get("value");

		if (value != null) {
			setValue(value);
		}
	}

	/**
	 * Returns the fully qualified class name of this product field.
	 *
	 * @return the fully qualified class name of this product field
	 */
	@Override
	public String getClassName() {
		return model.getClassName();
	}

	/**
	 * Returns the class name ID of this product field.
	 *
	 * @return the class name ID of this product field
	 */
	@Override
	public long getClassNameId() {
		return model.getClassNameId();
	}

	/**
	 * Returns the class pk of this product field.
	 *
	 * @return the class pk of this product field
	 */
	@Override
	public long getClassPK() {
		return model.getClassPK();
	}

	/**
	 * Returns the company ID of this product field.
	 *
	 * @return the company ID of this product field
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the name of this product field.
	 *
	 * @return the name of this product field
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the primary key of this product field.
	 *
	 * @return the primary key of this product field
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the product field ID of this product field.
	 *
	 * @return the product field ID of this product field
	 */
	@Override
	public long getProductFieldId() {
		return model.getProductFieldId();
	}

	/**
	 * Returns the user ID of this product field.
	 *
	 * @return the user ID of this product field
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user uuid of this product field.
	 *
	 * @return the user uuid of this product field
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the value of this product field.
	 *
	 * @return the value of this product field
	 */
	@Override
	public String getValue() {
		return model.getValue();
	}

	@Override
	public void persist() {
		model.persist();
	}

	@Override
	public void setClassName(String className) {
		model.setClassName(className);
	}

	/**
	 * Sets the class name ID of this product field.
	 *
	 * @param classNameId the class name ID of this product field
	 */
	@Override
	public void setClassNameId(long classNameId) {
		model.setClassNameId(classNameId);
	}

	/**
	 * Sets the class pk of this product field.
	 *
	 * @param classPK the class pk of this product field
	 */
	@Override
	public void setClassPK(long classPK) {
		model.setClassPK(classPK);
	}

	/**
	 * Sets the company ID of this product field.
	 *
	 * @param companyId the company ID of this product field
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the name of this product field.
	 *
	 * @param name the name of this product field
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the primary key of this product field.
	 *
	 * @param primaryKey the primary key of this product field
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the product field ID of this product field.
	 *
	 * @param productFieldId the product field ID of this product field
	 */
	@Override
	public void setProductFieldId(long productFieldId) {
		model.setProductFieldId(productFieldId);
	}

	/**
	 * Sets the user ID of this product field.
	 *
	 * @param userId the user ID of this product field
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user uuid of this product field.
	 *
	 * @param userUuid the user uuid of this product field
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the value of this product field.
	 *
	 * @param value the value of this product field
	 */
	@Override
	public void setValue(String value) {
		model.setValue(value);
	}

	@Override
	protected ProductFieldWrapper wrap(ProductField productField) {
		return new ProductFieldWrapper(productField);
	}

}