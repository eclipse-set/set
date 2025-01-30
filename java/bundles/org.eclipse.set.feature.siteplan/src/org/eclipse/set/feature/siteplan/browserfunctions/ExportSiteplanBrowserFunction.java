/**
 * Copyright (c) 2025 DB InfraGO AG and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 */
package org.eclipse.set.feature.siteplan.browserfunctions;

import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.core.services.dialog.DialogService;
import org.eclipse.set.feature.siteplan.SiteplanBrowser;
import org.eclipse.set.services.export.ExportService;
import org.eclipse.set.utils.ToolboxConfiguration;
import org.eclipse.swt.widgets.Shell;

/**
 * 
 */
public class ExportSiteplanBrowserFunction
		extends SelectFolderDialogBrowserFunction {

	private final List<BufferedImage> exportImages = new LinkedList<>();
	private final IModelSession modelSession;
	private final ExportService exportService;

	/**
	 * Constructor.
	 * 
	 * @param browser
	 *            browser to add this function to
	 * @param name
	 *            name of this function for Javascript
	 * @param modelSession
	 *            the model session
	 * @param exportService
	 *            the export service
	 * @param shell
	 *            a shell to open dialogs with
	 * @param dialogService
	 *            the dialog service
	 */
	public ExportSiteplanBrowserFunction(final SiteplanBrowser browser,
			final String name, final IModelSession modelSession,
			final ExportService exportService, final Shell shell,
			final DialogService dialogService) {
		super(browser, name, shell, dialogService);
		this.modelSession = modelSession;
		this.exportService = exportService;
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
		}, () -> // User did not select a folder
		webBrowser.executeJavascript(String.format("%s('%s', null);", //$NON-NLS-1$
				DIALOG_CALLBACK_FUNCTION, key)));
		return null;
	}
}
