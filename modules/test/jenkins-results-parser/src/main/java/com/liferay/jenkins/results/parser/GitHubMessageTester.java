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

package com.liferay.jenkins.results.parser;

import org.dom4j.Element;

import java.io.IOException;

/**
 * @author Yi-Chen Tsai
 */
public class GitHubMessageTester {
    public static void main() throws IOException {
        Build topLevelBuild = BuildFactory.newBuild("https://test-1-6.liferay.com/job/test-portal-acceptance-pullrequest(ee-7.0.x)/398/", null);
        Element message = topLevelBuild.getGitHubMessageElement();
        System.out.println(Dom4JUtil.format(message, true));
    }
}
