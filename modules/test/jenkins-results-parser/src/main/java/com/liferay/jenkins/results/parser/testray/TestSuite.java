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

import com.liferay.jenkins.results.parser.testray.model.Attachment;
import com.liferay.jenkins.results.parser.testray.model.Build;
import com.liferay.jenkins.results.parser.testray.model.Case;
import com.liferay.jenkins.results.parser.testray.model.CaseResult;
import com.liferay.jenkins.results.parser.testray.model.CaseType;
import com.liferay.jenkins.results.parser.testray.model.Component;
import com.liferay.jenkins.results.parser.testray.model.Factor;
import com.liferay.jenkins.results.parser.testray.model.FactorCategory;
import com.liferay.jenkins.results.parser.testray.model.FactorOption;
import com.liferay.jenkins.results.parser.testray.model.ProductVersion;
import com.liferay.jenkins.results.parser.testray.model.Project;
import com.liferay.jenkins.results.parser.testray.model.Routine;
import com.liferay.jenkins.results.parser.testray.model.Run;
import com.liferay.jenkins.results.parser.testray.model.Team;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kevin Yen
 */
public class TestSuite {

    public List<Attachment> getAttachments() {
        return _attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        _attachments = new ArrayList<>(attachments);
    }

    public List<Build> getBuilds() {
        return _builds;
    }

    public void setBuilds(List<Build> builds) {
        _builds = new ArrayList<>(builds);
    }

    public List<Case> getCase() {
        return _case;
    }

    public void setCase(List<Case> aCase) {
        _case = new ArrayList<>(aCase);
    }

    public List<CaseResult> getCaseResults() {
        return _caseResults;
    }

    public void setCaseResults(List<CaseResult> caseResults) {
        _caseResults = new ArrayList<>(caseResults);
    }

    public List<CaseType> getCaseTypes() {
        return _caseTypes;
    }

    public void setCaseTypes(List<CaseType> caseTypes) {
        _caseTypes = new ArrayList<>(caseTypes);
    }

    public List<Component> getComponents() {
        return _components;
    }

    public void setComponents(List<Component> components) {
        _components = new ArrayList<>(components);
    }

    public List<Factor> getFactors() {
        return _factors;
    }

    public void setFactors(List<Factor> factors) {
        _factors = new ArrayList<>(factors);
    }

    public List<FactorCategory> getFactorCategories() {
        return _factorCategories;
    }

    public void setFactorCategories(List<FactorCategory> factorCategories) {
        _factorCategories = new ArrayList<>(factorCategories);
    }

    public List<FactorOption> getFactorOptions() {
        return _factorOptions;
    }

    public void setFactorOptions(List<FactorOption> factorOptions) {
        _factorOptions = new ArrayList<>(factorOptions);
    }

    public List<ProductVersion> getProductVersions() {
        return _productVersions;
    }

    public void setProductVersions(List<ProductVersion> productVersions) {
        _productVersions = new ArrayList<>(productVersions);
    }

    public List<Project> getProjects() {
        return _projects;
    }

    public void setProjects(List<Project> projects) {
        _projects = new ArrayList<>(projects);
    }

    public List<Routine> getRoutines() {
        return _routines;
    }

    public void setRoutines(List<Routine> routines) {
        _routines = new ArrayList<>(routines);
    }

    public List<Run> getRuns() {
        return _runs;
    }

    public void setRuns(List<Run> runs) {
        _runs = new ArrayList<>(runs);
    }

    public List<Team> getTeams() {
        return _teams;
    }

    public void setTeams(List<Team> teams) {
        _teams = new ArrayList<>(teams);
    }

    private List<Attachment> _attachments = new ArrayList<>();
    private List<Build> _builds = new ArrayList<>();
    private List<Case> _case = new ArrayList<>();
    private List<CaseResult> _caseResults = new ArrayList<>();
    private List<CaseType> _caseTypes = new ArrayList<>();
    private List<Component> _components = new ArrayList<>();
    private List<Factor> _factors = new ArrayList<>();
    private List<FactorCategory> _factorCategories = new ArrayList<>();
    private List<FactorOption> _factorOptions = new ArrayList<>();
    private List<ProductVersion> _productVersions = new ArrayList<>();
    private List<Project> _projects = new ArrayList<>();
    private List<Routine> _routines = new ArrayList<>();
    private List<Run> _runs = new ArrayList<>();
    private List<Team> _teams = new ArrayList<>();

}
