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
public class Case {

    public Case(CaseType caseType, String name) {
        _caseType = caseType;
        _name = name;
    }

    public CaseType getCaseType() {
        return _caseType;
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

    public Integer getPriority() {
        return _priority;
    }

    public Component getMainComponent() {
        return _mainComponent;
    }

    public void setCaseType(CaseType caseType) {
        _caseType = caseType;
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

    public void setPriority(Integer priority) {
        _priority = priority;
    }

    public void setMainComponent(Component mainComponent) {
        _mainComponent = mainComponent;
    }

    private CaseType _caseType;
    private String _description;
    private Integer _id;
    private String _name;
    private Integer _priority;
    private Component _mainComponent;

}
