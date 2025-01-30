/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.siteplan.browserfunctions;

import java.nio.file.Path;
import java.util.Optional;

import org.eclipse.set.browser.swt.BrowserFunction;
import org.eclipse.set.core.services.dialog.DialogService;
import org.eclipse.set.feature.siteplan.SiteplanBrowser;
import org.eclipse.set.utils.ToolboxConfiguration;
import org.eclipse.swt.widgets.Shell;

/**
 * Browser function to trigger a TableSelectRowEvent
 * 
 * @author Stuecker
 */
public class SelectFolderDialogBrowserFunction extends BrowserFunction {
	// Name of the Javascript function to call once the user has selected a
	// folder
	protected static final String DIALOG_CALLBACK_FUNCTION = "window.planproSelectFolderDialogCallback"; //$NON-NLS-1$
	protected DialogService dialogService;
	protected Shell shell;
	protected SiteplanBrowser webBrowser;

	/**
	 * Constructor.
	 * 
	 * @param webBrowser
	 *            browser to add this function to
	 * @param name
	 *            name of this function for Javascript
	 * @param shell
	 *            a shell to open dialogs with
	 * @param dialogService
	 *            the dialog service
	 */
	public SelectFolderDialogBrowserFunction(final SiteplanBrowser webBrowser,
			final String name, final Shell shell,
			final DialogService dialogService) {
		super(webBrowser.getBrowser(), name);
		this.shell = shell;
		this.webBrowser = webBrowser;
		this.dialogService = dialogService;
	}

	@Override
	public Object function(final Object[] arguments) {
		final Optional<String> optionalOutputDir = dialogService
				.selectDirectory(shell,
						ToolboxConfiguration.getDefaultPath().toString());
		final String key = (String) arguments[0];
		optionalOutputDir.ifPresentOrElse(outputDir -> {
			// User selected a folder
			webBrowser.executeJavascript(String.format("%s('%s', '%s');", //$NON-NLS-1$
					DIALOG_CALLBACK_FUNCTION, key,
					outputDir.replace("\\", "\\\\"))); //$NON-NLS-1$ //$NON-NLS-2$
			webBrowser.setSelectedFolder(Path.of(outputDir));
		}, () -> { // User did not select a folder
			webBrowser.executeJavascript(String.format("%s('%s', null);", //$NON-NLS-1$
					DIALOG_CALLBACK_FUNCTION, key));
		});
		return null;
	}
}
