/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.integration.service.actions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.function.Consumer;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.set.basis.constants.ContainerType;
import org.eclipse.set.basis.files.AttachmentContentService;
import org.eclipse.set.core.services.merge.MergeService.Context;
import org.eclipse.set.core.services.merge.MergeService.ContextProvider;
import org.eclipse.set.core.services.name.NameService;
import org.eclipse.set.core.services.part.ToolboxPartService;
import org.eclipse.set.feature.integration.Messages;
import org.eclipse.set.feature.integration.PlanProMergeContextProvider;
import org.eclipse.set.feature.integration.SessionToIntegrationViewTransformation;
import org.eclipse.set.model.integrationview.Conflict;
import org.eclipse.set.model.simplemerge.SMatch;
import org.eclipse.set.model.simplemerge.extensions.SMatchExtensions;
import org.eclipse.set.model.temporaryintegration.TemporaryIntegration;
import org.eclipse.set.model.temporaryintegration.extensions.TemporaryIntegrationExtensions;
import org.eclipse.set.ppmodel.extensions.AnhangExtensions;
import org.eclipse.set.toolboxmodel.Basisobjekte.Anhang;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Table;

/**
 * Show attachments in the conflict detail view.
 * 
 * @author Schaefer
 */
public class ShowAttachmentAction implements Consumer<ColumnViewer> {

	private class ShowPrimaryAttachment extends Action {
		public ShowPrimaryAttachment() {
			super(messages.ShowAttachmentAction_ShowPrimaryAttachment);
			setEnabled(false);
		}

		@Override
		public void run() {
			showPdf(getSelectedPrimaryAttachment().get());
		}
	}

	private class ShowSecondaryAttachment extends Action {
		public ShowSecondaryAttachment() {
			super(messages.ShowAttachmentAction_ShowSecondaryAttachment);
			setEnabled(false);
		}

		@Override
		public void run() {
			showPdf(getSelectedSecondaryAttachment().get());
		}
	}

	private final ConflictsTableAction conflictsTableAction;

	private final AttachmentContentService contentService;
	private final ContextProvider contextProvider;
	private ColumnViewer masterViewer;
	private final ToolboxPartService partService;
	private ShowPrimaryAttachment showPrimaryAttachment;
	private ShowSecondaryAttachment showSecondaryAttachment;
	final private String tempDir;
	private final SessionToIntegrationViewTransformation transformation;
	final Messages messages;

	/**
	 * @param conflictsTableAction
	 *            the conflictsTableAction connected to this action
	 * @param transformation
	 *            the session to integration view transformation
	 * @param tempDir
	 *            the temporary directory
	 * @param messages
	 *            the translations
	 * @param partService
	 *            the toolbox part service
	 * @param nameService
	 *            the name service
	 * @param contentService
	 *            the attachment content service
	 */
	public ShowAttachmentAction(final ConflictsTableAction conflictsTableAction,
			final SessionToIntegrationViewTransformation transformation,
			final String tempDir, final Messages messages,
			final ToolboxPartService partService, final NameService nameService,
			final AttachmentContentService contentService) {
		this.conflictsTableAction = conflictsTableAction;
		this.transformation = transformation;
		this.messages = messages;
		this.tempDir = tempDir;
		this.partService = partService;
		this.contentService = contentService;
		contextProvider = new PlanProMergeContextProvider(nameService);
	}

	@Override
	public void accept(final ColumnViewer viewer) {
		masterViewer = conflictsTableAction.getViewer();

		final MenuManager menuManager = new MenuManager();

		// actions
		showPrimaryAttachment = new ShowPrimaryAttachment();
		showSecondaryAttachment = new ShowSecondaryAttachment();
		menuManager.add(showPrimaryAttachment);
		menuManager.add(showSecondaryAttachment);

		// disable/enable actions
		updateItemState();
		masterViewer
				.addSelectionChangedListener(new ISelectionChangedListener() {
					@Override
					public void selectionChanged(
							final SelectionChangedEvent event) {
						updateItemState();
					}
				});

		final Table table = (Table) viewer.getControl();
		final Menu menu = menuManager.createContextMenu(table);
		table.setMenu(menu);
	}

	private void savePdf(final Anhang attachment, final Path path) {
		try {
			final byte[] content = contentService.getContent(attachment);
			final Path parent = path.getParent();
			if (parent != null) {
				Files.createDirectories(parent);
			}
			Files.write(path, content);
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void startPdfViewer(final Path path) {
		partService.showPdfPart(path);
	}

	protected Optional<Anhang> getSelectedAttachment(final boolean isPrimary) {
		final ISelection selection = masterViewer.getSelection();
		if (!(selection instanceof StructuredSelection)) {
			return Optional.empty();
		}

		final StructuredSelection structuredSelection = (StructuredSelection) selection;
		if (structuredSelection.size() != 1) {
			return Optional.empty();
		}

		final Object element = structuredSelection.getFirstElement();
		if (!(element instanceof Conflict)) {
			return Optional.empty();
		}

		final Conflict conflict = (Conflict) element;
		final SMatch match = transformation.getMatch(conflict);
		final ContainerType containerType = SMatchExtensions
				.getContainerType(match);
		final TemporaryIntegration integration = SMatchExtensions
				.getIntegration(match);
		final Context context = TemporaryIntegrationExtensions
				.getMergeContext(integration, contextProvider, containerType);

		final Optional<EObject> lstObject;
		if (isPrimary) {
			lstObject = SMatchExtensions.getPrimaryElement(match, context);
		} else {
			lstObject = SMatchExtensions.getSecondaryElement(match, context);
		}

		if (lstObject.isPresent()) {
			if (lstObject.get() instanceof Anhang) {
				return Optional.of((Anhang) lstObject.get());
			}
		}

		return Optional.empty();
	}

	protected void updateItemState() {
		showPrimaryAttachment
				.setEnabled(getSelectedPrimaryAttachment().isPresent());
		showSecondaryAttachment
				.setEnabled(getSelectedSecondaryAttachment().isPresent());
	}

	Optional<Anhang> getSelectedPrimaryAttachment() {
		return getSelectedAttachment(true);
	}

	Optional<Anhang> getSelectedSecondaryAttachment() {
		return getSelectedAttachment(false);
	}

	void showPdf(final Anhang attachment) {
		final Path path = Paths.get(tempDir,
				attachment.getIdentitaet().getWert(),
				AnhangExtensions.getFilename(attachment));
		savePdf(attachment, path);
		startPdfViewer(path);
	}
}
