/**
 * Copyright (c) 2019 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.application.handler;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.swt.widgets.Shell;

import org.eclipse.set.core.services.dialog.DialogService;

/**
 * News and release notes.
 * 
 * @author Schaefer
 */
public class ToolboxNews {

	@Inject
	private DialogService dialogService;

	@Execute
	private void execute(final Shell shell) {
		dialogService.toolboxNews(shell);
	}
}
