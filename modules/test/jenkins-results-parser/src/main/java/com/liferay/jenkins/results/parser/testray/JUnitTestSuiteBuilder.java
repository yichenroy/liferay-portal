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

package com.liferay.jenkins.results.parser.testray;

import com.liferay.jenkins.results.parser.testray.model.CaseType;
import com.liferay.jenkins.results.parser.testray.model.Component;
import com.liferay.jenkins.results.parser.testray.model.ProductVersion;
import com.liferay.jenkins.results.parser.testray.model.Project;
import com.liferay.jenkins.results.parser.testray.model.Team;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kevin Yen
 */
public class JUnitTestSuiteBuilder implements TestSuiteBuilder {

    public void addJUnitFile(File file) {
        jUnitFiles.add(file);
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void setProductVersion(String productVersionName) {
        this.productVersionName = productVersionName;
    }

    public void setCaseType(String caseTypeName) {
        this.caseTypeName = caseTypeName;
    }

    public void setMainComponentName(String mainComponentName) {
        this.mainComponentName = mainComponentName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    @Override
    public TestSuite build() {
        TestSuite testSuite = new TestSuite();

        Project project = new Project(projectName);
        ProductVersion productVersion = new ProductVersion(project, productVersionName);

        CaseType caseType = new CaseType(caseTypeName);
        Team team = new Team(project, teamName);
        Component mainComponent = new Component(project, team, mainComponentName);

        return testSuite;
    }

    protected List<File> jUnitFiles = new ArrayList<>();
    protected String projectName;
    protected String productVersionName;
    protected String caseTypeName;
    protected String mainComponentName;
    protected String teamName;

}

