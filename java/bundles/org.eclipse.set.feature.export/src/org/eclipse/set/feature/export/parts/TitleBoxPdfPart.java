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
import java.nio.file.Paths;
import java.util.List;

import javax.inject.Inject;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.basis.OverwriteHandling;
import org.eclipse.set.core.services.pdf.PdfRendererService;
import org.eclipse.set.core.services.pdf.PdfViewer;
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

import de.scheidtbachmann.planpro.model.model1902.PlanPro.PlanPro_Schnittstelle;

/**
 * This part renders the titlebox as an PDF-File.
 * 
 * @author Schaefer
 */
public class TitleBoxPdfPart extends BasePart<IModelSession> {

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
		super(IModelSession.class);
	}

	private void createTitlebox(final Composite parent) {
		parent.setLayout(new FillLayout());
		viewer = rendererService.createViewer(parent);
		viewer.show(Paths.get(TITLEBOX_PDF));
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
							createTitleboxPdf(getModelSession()
									.getPlanProSchnittstelle());
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

	@Override
	protected void createView(final Composite parent) {
		createTitleboxPdfMonitor();
		createTitlebox(parent);
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

	void createTitleboxPdf(final PlanPro_Schnittstelle planProSchnittstelle)
			throws Exception {
		final PlanProToTitleboxTransformation planProToTitlebox = PlanProToTitleboxTransformation
				.create();
		final Titlebox titlebox = planProToTitlebox
				.transform(planProSchnittstelle, null);
		exportService.exportTitleboxPdf(titlebox, Paths.get(TITLEBOX_PDF),
				OverwriteHandling.forCheckbox(true),
				new ExceptionHandler(getToolboxShell(), getDialogService()));
	}

	void updatePdfView() {
		createTitleboxPdfMonitor();
		viewer.refresh();
		setOutdated(false);
	}
}
