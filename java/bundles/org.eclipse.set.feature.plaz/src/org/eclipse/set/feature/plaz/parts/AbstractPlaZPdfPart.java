/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.plaz.parts;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import jakarta.inject.Inject;

import org.eclipse.set.basis.constants.ToolboxViewState;
import org.eclipse.set.basis.exceptions.UserAbortion;
import org.eclipse.set.basis.extensions.MApplicationElementExtensions;
import org.eclipse.set.basis.extensions.PathExtensions;
import org.eclipse.set.core.services.pdf.PdfRendererService;
import org.eclipse.set.core.services.pdf.PdfViewer;
import org.eclipse.set.core.services.pdf.PdfViewer.SaveListener;
import org.eclipse.set.utils.SaveAndRefreshAction;
import org.eclipse.set.utils.SelectableAction;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * Abstract part to create PlaZ PDF Reports
 * 
 * @author Stuecker
 */
public abstract class AbstractPlaZPdfPart extends AbstractPlaZPart
		implements SaveListener {

	@Inject
	private PdfRendererService rendererService;

	private PdfViewer viewer;

	@Override
	protected void createView(final Composite parent) {
		try {
			viewer = rendererService.createViewer(parent);
			viewer.setSaveListener(this);
			updateViewer();
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
		// regenerate report
		executePlaZ(getPlaZConfiguration());

		// Reload the view
		viewer.show(getPdfPath());
	}

	abstract PlaZConfiguration getPlaZConfiguration();

	abstract Path getPdfPath();

	@Override
	protected SelectableAction getOutdatedAction() {
		return new SaveAndRefreshAction(this);
	}

	@Override
	protected void resetReportAvailability() {
		getPlaZConfiguration().setAvailable(false);
	}

	@Override
	protected void updatePlazView() throws UserAbortion {
		if (isOutdated()) {
			updateViewer();
			setOutdated(false);
		}
	}

	@Override
	public Optional<Path> saveFile(final String filename) {
		final Shell shell = getToolboxShell();
		final Path location = getModelSession().getToolboxFile().getPath();
		final Path parent = location.getParent();
		final String defaultPath = parent == null ? "" : parent.toString(); //$NON-NLS-1$
		final String defaultFileName = String.format(filename,
				PathExtensions.getBaseFileName(location));

		return getDialogService().saveFileDialog(shell,
				getDialogService().getDokumentFileFilters(),
				Paths.get(defaultPath, defaultFileName));
	}

	@Override
	public void saveCompleted(final Path path) {
		getDialogService().reportSavedFile(getToolboxShell(), path);
	}

}
