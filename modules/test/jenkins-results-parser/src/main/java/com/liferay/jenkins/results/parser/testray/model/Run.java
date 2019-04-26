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

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kevin Yen
 */
public class Run {

    public Run(Build build, List<Factor> factors) {
        _build = build;
        _factors = new ArrayList<>(factors);
    }

    public boolean addFactor(Factor factor) {
        return _factors.add(factor);
    }

    public Build getBuild() {
        return _build;
    }

    public String getDescription() {
        return _description;
    }

    public List<Factor> getFactors() {
        return _factors;
    }

    public Integer getId() {
        return _id;
    }

    public Integer getNumber() {
        return _number;
    }

    public void setBuild(Build build) {
        _build = build;
    }

    public void setDescription(String description) {
        _description = description;
    }

    public void setId(Integer id) {
        _id = id;
    }

    public void setNumber(Integer number) {
        _number = number;
    }

    private Build _build;
    private String _description;
    private List<Factor> _factors = new ArrayList<>();
    private Integer _id;
    private Integer _number;

}
