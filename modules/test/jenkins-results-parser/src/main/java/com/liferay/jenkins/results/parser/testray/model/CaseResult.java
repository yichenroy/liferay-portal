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
public class CaseResult {

    public Case getCase() {
        return _case;
    }

    public String getErrors() {
        return _errors;
    }

    public Run getRun() {
        return _run;
    }

    public Integer getStatus() {
        return _status;
    }

    public void setCase(Case aCase) {
        _case = aCase;
    }

    public void setErrors(String errors) {
        _errors = errors;
    }

    public void setRun(Run run) {
        _run = run;
    }

    public void setStatus(Integer status) {
        _status = status;
    }

    private Case _case;
    private String _errors;
    private Run _run;
    private Integer _status;

}
