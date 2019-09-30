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

import java.io.IOException;

/**
 * @author Yi-Chen Tsai
 */
public class SandBox {

	public static void main(String[] args) throws IOException {
		Build topLevelBuild = BuildFactory.newBuild(
			"https://test-1-7.liferay.com/job/test-portal-acceptance-pullrequest(master)/4361/",
			null);

		//		Build topLevelBuild = BuildFactory.newBuild(
		//			"https://test-1-3.liferay.com/job/test-portal-acceptance-pullrequest(master)/6049/",
		//			null);

		//		Build topLevelBuild = BuildFactory.newBuild(
		//			"https://test-1-19.liferay.com/job/test-portal-acceptance-pullrequest(7.1.x)/392/",
		//			null);

		System.out.println(
			Dom4JUtil.format(topLevelBuild.getGitHubMessageElement()));
	}

}