/**
 * Copyright (c) 2024 DB InfraGO AG and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 */
package org.eclipse.set.feature.projectdata.utils;

import javax.inject.Inject;

import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.set.core.services.dialog.DialogService;
import org.eclipse.set.core.services.files.ToolboxFileService;
import org.eclipse.set.core.services.modelloader.ModelLoader;
import org.eclipse.set.feature.projectdata.Messages;

/**
 * Injection helper class
 */
@SuppressWarnings("javadoc")
public class ServiceProvider {

	@Inject
	public DialogService dialogService;

	@Inject
	public ToolboxFileService fileService;

	@Inject
	@Translation
	public Messages messages;
	@Inject
	public ModelLoader modelLoader;

	public ServiceProvider() {

	}
}
