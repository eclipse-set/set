/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.feature.plaz.parts;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.basis.constants.ToolboxViewState;
import org.eclipse.set.basis.constants.ValidationResult.Outcome;
import org.eclipse.set.basis.exceptions.UserAbortion;
import org.eclipse.set.basis.extensions.MApplicationElementExtensions;
import org.eclipse.set.basis.extensions.PathExtensions;
import org.eclipse.set.browser.DownloadListener;
import org.eclipse.set.model.planpro.PlanPro.PlanPro_Schnittstelle;
import org.eclipse.set.utils.FileWebBrowser;
import org.eclipse.set.utils.SaveAndRefreshAction;
import org.eclipse.set.utils.SelectableAction;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * Browser-Widget for displaying a PlaZ HTML report.
 * 
 * @author Bleidiessel / Schaefer
 */
public class PlazHtmlPart extends AbstractPlaZPart implements DownloadListener {

	private static final String EXTENSION = ".html"; //$NON-NLS-1$

	private FileWebBrowser browser;

	private Path htmlLocation;

	private void prepare(final IModelSession modelSession) throws UserAbortion {
		executePlaZ(new PlaZConfiguration() {

			@Override
			public void create(final IProgressMonitor monitor) {
				plaZService.createHtmlReport(monitor, modelSession.getTempDir(),
						modelSession.getToolboxFile(), htmlLocation,
						modelSession
								.getValidationResult(
										PlanPro_Schnittstelle.class)
								.getXsdOutcome() == Outcome.VALID,
						plaZService.isSvrlAvailable(
								modelSession.getPlanProSchnittstelle()));
			}

			@Override
			public boolean isAvailable() {
				return plaZService.isHtmlReportAvailable(
						modelSession.getPlanProSchnittstelle());
			}

			@Override
			public void setAvailable(final boolean value) {
				plaZService.setHtmlReportAvailable(
						modelSession.getPlanProSchnittstelle(), value);
				plaZService.setSvrlFileAvailable(
						modelSession.getPlanProSchnittstelle(), value);
			}
		});
	}

	@Override
	protected void createView(final Composite parent) {
		try {
			browser = new FileWebBrowser(parent);
			browser.getBrowser().setDownloadListener(this);
			updateViewer();
			GridDataFactory.swtDefaults().align(SWT.FILL, SWT.FILL)
					.grab(true, true).span(2, 1).applyTo(browser.getControl());
			setOutdated(getModelSession().isDirty());
		} catch (final UserAbortion e) {
			// Close the part view
			MApplicationElementExtensions.setViewState(getToolboxPart(),
					ToolboxViewState.CANCELED);
		} catch (final Exception e) {

			final String message = handleException(getToolboxShell(),
					getToolboxPart(), e);
			parent.setLayout(new FillLayout());
			final Text text = new Text(parent, SWT.READ_ONLY | SWT.WRAP);
			text.setText(message);
		}
	}

	private void updateViewer() throws UserAbortion {
		final IModelSession modelSession = getModelSession();
		final Path modelLocation = modelSession.getToolboxFile().getModelPath();
		final String filename = modelLocation.getFileName().toString()
				+ EXTENSION;
		htmlLocation = Paths.get(modelSession.getTempDir().toString(),
				filename);

		// regenerate report
		prepare(modelSession);

		// Reload the view
		try {
			browser.serveRootDirectory(modelSession.getTempDir());
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
		browser.setToolboxUrl(filename);
	}

	@Override
	protected SelectableAction getOutdatedAction() {
		return new SaveAndRefreshAction(this);
	}

	@Override
	protected void resetReportAvailability() {
		plaZService.setHtmlReportAvailable(
				getModelSession().getPlanProSchnittstelle(), false);
		plaZService.setSvrlFileAvailable(
				getModelSession().getPlanProSchnittstelle(), false);
	}

	@Override
	protected void updatePlazView() throws UserAbortion {
		if (isOutdated()) {
			updateViewer();
			setOutdated(false);
		}
	}

	@Override
	public Optional<Path> beforeDownload(final String suggestedName,
			final String url) {
		final Shell shell = getToolboxShell();
		final Path location = getModelSession().getToolboxFile().getPath();
		final Path parent = location.getParent();
		final String defaultPath = parent == null ? "" : parent.toString(); //$NON-NLS-1$
		final String defaultFileName = String.format(suggestedName,
				PathExtensions.getBaseFileName(location));

		return getDialogService().saveFileDialog(shell,
				getDialogService().getDokumentFileFilters(),
				Paths.get(defaultPath, defaultFileName));
	}

	@Override
	public void downloadFinished(final boolean success, final Path path) {
		getDialogService().reportSavedFile(getToolboxShell(), path);
	}
}
