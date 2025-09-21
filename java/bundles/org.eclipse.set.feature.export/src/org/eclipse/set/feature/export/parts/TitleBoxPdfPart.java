/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.export.parts;

import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.set.basis.OverwriteHandling;
import org.eclipse.set.basis.extensions.PathExtensions;
import org.eclipse.set.basis.guid.Guid;
import org.eclipse.set.core.services.pdf.PdfRendererService;
import org.eclipse.set.core.services.pdf.PdfViewer;
import org.eclipse.set.core.services.pdf.PdfViewer.SaveListener;
import org.eclipse.set.feature.export.Messages;
import org.eclipse.set.model.titlebox.Titlebox;
import org.eclipse.set.ppmodel.extensions.utils.PlanProToTitleboxTransformation;
import org.eclipse.set.services.export.ExportService;
import org.eclipse.set.utils.BasePart;
import org.eclipse.set.utils.RefreshAction;
import org.eclipse.set.utils.SelectableAction;
import org.eclipse.set.utils.events.ProjectDataChanged;
import org.eclipse.set.utils.exception.ExceptionHandler;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import jakarta.inject.Inject;

/**
 * This part renders the titlebox as an PDF-File.
 * 
 * @author Schaefer
 */
public class TitleBoxPdfPart extends BasePart implements SaveListener {

	private static final String TITLEBOX_PDF = "titlebox.pdf"; //$NON-NLS-1$

	@Inject
	private ExportService exportService;

	@Inject
	private PdfRendererService rendererService;

	private PdfViewer viewer;

	@Inject
	@Translation
	Messages messages;

	/**
	 * Create the part.
	 */
	@Inject
	public TitleBoxPdfPart() {
		super();
	}

	@Override
	protected void createView(final Composite parent) {
		createTitleboxPdfMonitor();
		createTitlebox(parent);
	}

	private void createTitleboxPdfMonitor() {
		try {
			if (getModelSession() != null) {
				final IRunnableWithProgress runnableWithProgress = new IRunnableWithProgress() {
					@Override
					public void run(final IProgressMonitor monitor)
							throws InvocationTargetException,
							InterruptedException {
						try {
							monitor.beginTask(
									messages.TitleBoxPdfPart_LoadTitlebox,
									IProgressMonitor.UNKNOWN);
							createTitleboxPdf();
							monitor.done();
						} catch (final Exception e) {
							throw new InvocationTargetException(e);
						}
					}
				};
				new ProgressMonitorDialog(getToolboxShell()).run(true, false,
						runnableWithProgress);
			}
		} catch (InvocationTargetException | InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	private Path getAttachmentPath(final String guid) {
		try {
			return getModelSession().getToolboxFile()
					.getMediaPath(Guid.create(guid));
		} catch (final UnsupportedOperationException e) {
			return null; // .ppxml-Files do not support attachments
		}
	}

	void createTitleboxPdf() throws Exception {
		final PlanProToTitleboxTransformation planProToTitlebox = new PlanProToTitleboxTransformation(
				getSessionService());
		final Titlebox titlebox = planProToTitlebox.transform(null,
				this::getAttachmentPath);
		exportService.exportTitleboxPdf(titlebox, getTitleBoxPath(),
				OverwriteHandling.forCheckbox(true),
				new ExceptionHandler(getToolboxShell(), getDialogService()));
	}

	private void createTitlebox(final Composite parent) {
		parent.setLayout(new FillLayout());
		viewer = rendererService.createViewer(parent);
		viewer.show(getTitleBoxPath());
		viewer.setSaveListener(this);
	}

	private Path getTitleBoxPath() {
		final String tmpDir = getModelSession().getTempDir().toString();
		return Paths.get(tmpDir, TITLEBOX_PDF);
	}

	@Override
	protected SelectableAction getOutdatedAction() {
		return new RefreshAction(this, e -> updatePdfView());
	}

	@Override
	protected void handleProjectDataChanged(final ProjectDataChanged e) {
		setOutdated(true);
	}

	@Override
	protected void updateViewProjectDataChanged(
			final List<Notification> notifications) {
		if (isOutdated()) {
			updatePdfView();
		}
	}

	void updatePdfView() {
		createTitleboxPdfMonitor();
		viewer.refresh();
		setOutdated(false);
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
