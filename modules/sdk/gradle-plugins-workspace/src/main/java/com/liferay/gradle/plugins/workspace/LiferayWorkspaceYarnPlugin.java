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

package com.liferay.gradle.plugins.workspace;

import com.liferay.gradle.plugins.NodeDefaultsPlugin;
import com.liferay.gradle.plugins.node.NodeExtension;
import com.liferay.gradle.plugins.node.YarnPlugin;
import com.liferay.gradle.plugins.node.tasks.NpmInstallTask;
import com.liferay.gradle.plugins.node.tasks.YarnInstallTask;
import com.liferay.gradle.plugins.workspace.internal.util.GradleUtil;
import com.liferay.gradle.plugins.workspace.tasks.SetUpYarnTask;

import java.io.File;
import java.io.IOException;

import java.nio.file.Files;

import org.gradle.api.Action;
import org.gradle.api.Project;
import org.gradle.api.logging.Logger;
import org.gradle.api.tasks.TaskContainer;
import org.gradle.api.tasks.TaskProvider;

/**
 * @author David Truong
 */
public class LiferayWorkspaceYarnPlugin extends YarnPlugin {

	public static final String SET_UP_YARN_TASK_NAME = "setUpYarn";

	public static final String YARN_INSTALL_TASK_NAME = "yarnInstall";

	@Override
	public void apply(Project project) {
		super.apply(project);

		GradleUtil.applyPlugin(project, NodeDefaultsPlugin.class);

		TaskProvider<SetUpYarnTask> setUpYarnTaskProvider =
			GradleUtil.addTaskProvider(
				project, SET_UP_YARN_TASK_NAME, SetUpYarnTask.class);

		final TaskProvider<YarnInstallTask> yarnInstallTaskProvider =
			GradleUtil.getTaskProvider(
				project, YARN_INSTALL_TASK_NAME, YarnInstallTask.class);

		_configureTaskYarnInstallProvider(
			project, yarnInstallTaskProvider, setUpYarnTaskProvider);

		project.allprojects(
			new Action<Project>() {

				@Override
				public void execute(Project project) {
					_configureNodeProject(project, yarnInstallTaskProvider);
				}

			});
	}

	private void _configureNodeProject(
		Project project,
		TaskProvider<YarnInstallTask> yarnInstallTaskProvider) {

		project.afterEvaluate(
			new Action<Project>() {

				@Override
				public void execute(Project project) {
					TaskContainer taskContainer = project.getTasks();

					taskContainer.withType(
						NpmInstallTask.class,
						new Action<NpmInstallTask>() {

							@Override
							public void execute(NpmInstallTask npmInstallTask) {
								NodeExtension nodeExtension =
									GradleUtil.getExtension(
										npmInstallTask.getProject(),
										NodeExtension.class);

								nodeExtension.setUseNpm(false);

								npmInstallTask.finalizedBy(
									yarnInstallTaskProvider);
							}

						});
				}

			});
	}

	private void _configureTaskYarnInstallProvider(
		final Project project,
		TaskProvider<YarnInstallTask> yarnInstallTaskProvider,
		final TaskProvider<SetUpYarnTask> setUpYarnTaskProvider) {

		yarnInstallTaskProvider.configure(
			new Action<YarnInstallTask>() {

				@Override
				public void execute(YarnInstallTask yarnInstallTask) {
					yarnInstallTask.dependsOn(setUpYarnTaskProvider);

					try {
						File file = new File(
							project.getProjectDir(), "yarn.lock");

						if (file.exists()) {
							String contents = new String(
								Files.readAllBytes(file.toPath()));

							yarnInstallTask.setFrozenLockFile(
								!contents.equals(""));
						}
						else {
							yarnInstallTask.setFrozenLockFile(false);
						}
					}
					catch (IOException ioException) {
						Logger logger = project.getLogger();

						if (logger.isWarnEnabled()) {
							StringBuilder sb = new StringBuilder();

							sb.append("Unable to read yarn.lock.");

							logger.warn(sb.toString());
						}
					}
				}

			});
	}

}