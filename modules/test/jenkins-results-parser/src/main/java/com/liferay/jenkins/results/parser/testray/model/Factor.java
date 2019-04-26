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

package com.liferay.jenkins.results.parser.testray.model;

/**
 * @author Kevin Yen
 */
public class Factor {

    public Factor(FactorCategory factorCategory, FactorOption factorOption) {
        _factorCategory = factorCategory;
        _factorOption = factorOption;
    }

    public FactorCategory getFactorCategory() {
        return _factorCategory;
    }

    public FactorOption getFactorOption() {
        return _factorOption;
    }

    private FactorCategory _factorCategory;
    private FactorOption _factorOption;

}
