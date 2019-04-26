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
public class Component {

    public Component(Project project, Team team, String name) {
        _project = project;
        _team = team;
        _name = name;
    }

    public Integer getId() {
        return _id;
    }

    public String getName() {
        return _name;
    }

    public Project getProject() {
        return _project;
    }

    public Team getTeam() {
        return _team;
    }

    public void setId(Integer id) {
        _id = id;
    }

    public void setName(String name) {
        _name = name;
    }

    public void setProject(Project project) {
        _project = project;
    }

    public void setTeam(Team team) {
        _team = team;
    }

    private Integer _id;
    private String _name;
    private Project _project;
    private Team _team;

}
