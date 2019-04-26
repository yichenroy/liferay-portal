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
public class Build {

    public Build(ProductVersion productVersion, Routine routine, String name) {
        _productVersion = productVersion;
        _routine = routine;
        _name = name;
    }

    public String getDescription() {
        return _description;
    }

    public Integer getId() {
        return _id;
    }

    public String getName() {
        return _name;
    }

    public ProductVersion getProductVersion() {
        return _productVersion;
    }

    public Routine getRoutine() {
        return _routine;
    }

    public void setDescription(String description) {
        _description = description;
    }

    public void setId(Integer id) {
        _id = id;
    }

    public void setName(String name) {
        _name = name;
    }

    public void setProductVersion(ProductVersion productVersion) {
        _productVersion = productVersion;
    }

    public void setRoutine(Routine routine) {
        _routine = routine;
    }

    private String _name;
    private String _description;
    private Integer _id;
    private ProductVersion _productVersion;
    private Routine _routine;

}
