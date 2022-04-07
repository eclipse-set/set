/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.application.parts;

import java.util.List;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.set.application.Messages;
import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.basis.constants.ContainerType;
import org.eclipse.set.basis.constants.PlanProFileNature;
import org.eclipse.set.basis.files.AttachmentContentService;
import org.eclipse.set.core.services.enumtranslation.EnumTranslationService;
import org.eclipse.set.core.services.part.ToolboxPartService;
import org.eclipse.set.core.services.pdf.PdfRendererService;
import org.eclipse.set.ppmodel.extensions.AnhangTransformation;
import org.eclipse.set.utils.BasePart;
import org.eclipse.set.utils.RefreshAction;
import org.eclipse.set.utils.SelectableAction;
import org.eclipse.set.utils.attachment.AttachmentModelProvider;
import org.eclipse.set.utils.events.ContainerDataChanged;
import org.eclipse.set.utils.events.ProjectDataChanged;
import org.eclipse.set.utils.widgets.AttachmentTable;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.google.common.collect.Lists;

import org.eclipse.set.toolboxmodel.Basisobjekte.Anhang;
import org.eclipse.set.toolboxmodel.PlanPro.Container_AttributeGroup;

/**
 * This parts lists all attachments available in the document and offers the
 * possibility to open the attachments.
 * 
 * @author bleidiessel
 *
 */
public class AttachmentPart extends BasePart<IModelSession> {

	private static final String HEADING_PATTERN = "%s (%s)"; //$NON-NLS-1$

	@Inject
	private AttachmentContentService contentService;

	private AttachmentTable domainTableFinal;

	private AttachmentTable domainTableInitial;

	private AttachmentTable domainTableSingle;

	private AttachmentTable objectTable;

	@Optional
	@Inject
	private PdfRendererService rendererService;

	@Inject
	private EnumTranslationService translationService;

	@Inject
	@Translation
	Messages messages;

	@Inject
	ToolboxPartService partService;

	@Inject
	@Translation
	org.eclipse.set.utils.Messages utilMessages;

	/**
	 * Create the part.
	 */
	@Inject
	public AttachmentPart() {
		super(IModelSession.class);
	}

	// every call creates a tableviewer-instance for attachments
	private AttachmentTable createAttachmentTableViewerWidget(
			final Composite parent, final List<Anhang> model) {
		final AttachmentTable attachmentTable = new AttachmentTable(parent,
				utilMessages, contentService, getDialogService(),
				getModelSession().getToolboxFile());
		final AnhangTransformation transformation = AnhangTransformation
				.createTransformation(translationService, contentService);
		attachmentTable.setModel(transformation.toAttachment(model));
		attachmentTable.setTempDir(getModelSession().getTempDir().toString());
		if (rendererService != null) {
			attachmentTable.setPdfViewer(path -> partService.showPdfPart(path));
		}
		attachmentTable.createControl();
		return attachmentTable;
	}

	/**
	 * construct the ui. Two tableviewers will be created, one for the object
	 * management and one for the domain.
	 * 
	 * @param parent
	 *            the parent composite
	 */
	@Override
	protected void createView(final Composite parent) {
		// define the layout of the part
		final GridLayout layout = new GridLayout(2, false);
		parent.setLayout(layout);

		// the shared model provider
		final AttachmentModelProvider modelProvider = new AttachmentModelProvider(
				getModelSession());

		// objectmanagement
		final Label objectMgmtHeader = new Label(parent, SWT.NONE);
		final GridData gridData = new GridData(SWT.BEGINNING,
				GridData.BEGINNING, true, false, 2, 1);
		objectMgmtHeader.setLayoutData(gridData);
		objectMgmtHeader
				.setText(messages.AttachmentPart_AttachmentsInObjectmanagement);
		objectTable = createAttachmentTableViewerWidget(parent,
				modelProvider.getObjectManagementAttachments());

		if (Lists
				.newArrayList(PlanProFileNature.INTEGRATION,
						PlanProFileNature.PLANNING)
				.contains(getModelSession().getNature())) {
			// initial container
			final Label initialHeader = new Label(parent, SWT.NONE);
			final GridData domainHeaderGridData = new GridData(SWT.BEGINNING,
					GridData.BEGINNING, true, false, 2, 1);
			initialHeader.setLayoutData(domainHeaderGridData);
			final String containerInitialName = translationService
					.translate(ContainerType.INITIAL).getPresentation();
			initialHeader.setText(String.format(HEADING_PATTERN,
					messages.AttachmentPart_AttachmentsInDomain,
					containerInitialName));
			domainTableInitial = createAttachmentTableViewerWidget(parent,
					modelProvider.getDomainAttachmentsInitial());

			// final container
			final Label finalHeader = new Label(parent, SWT.NONE);
			GridDataFactory.swtDefaults().align(SWT.BEGINNING, SWT.BEGINNING)
					.grab(true, false).span(2, 1).applyTo(finalHeader);
			final String containerFinalName = translationService
					.translate(ContainerType.FINAL).getPresentation();
			finalHeader.setText(String.format(HEADING_PATTERN,
					messages.AttachmentPart_AttachmentsInDomain,
					containerFinalName));
			domainTableFinal = createAttachmentTableViewerWidget(parent,
					modelProvider.getDomainAttachmentsFinal());
		} else {
			// single container
			final Label singleHeader = new Label(parent, SWT.NONE);
			GridDataFactory.swtDefaults().align(SWT.BEGINNING, SWT.BEGINNING)
					.grab(true, false).span(2, 1).applyTo(singleHeader);
			final String containerSingleName = translationService
					.translate(ContainerType.SINGLE).getPresentation();
			singleHeader.setText(String.format(HEADING_PATTERN,
					messages.AttachmentPart_AttachmentsInDomain,
					containerSingleName));
			domainTableSingle = createAttachmentTableViewerWidget(parent,
					modelProvider.getDomainAttachmentsSingle());
		}
	}

	@Override
	protected SelectableAction getOutdatedAction() {
		return new RefreshAction(this, e -> {
			updateTables();
			setOutdated(false);
		});
	}

	@Override
	protected void handleContainerDataChanged(final ContainerDataChanged e) {
		if (e.getContainer() == getContainerInFocus()) {
			setOutdated(true);
		}
	}

	@Override
	protected void handleProjectDataChanged(final ProjectDataChanged e) {
		setOutdated(true);
	}

	@Override
	protected void updateViewContainerDataChanged(
			final List<Container_AttributeGroup> container) {
		if (isOutdated()) {
			updateTables();
		}
		setOutdated(false);
	}

	@Override
	protected void updateViewProjectDataChanged(
			final List<Notification> notifications) {
		if (isOutdated()) {
			updateTables();
		}
		setOutdated(false);
	}

	void updateTables() {
		// transformation
		final AnhangTransformation transformation = AnhangTransformation
				.createTransformation(translationService, contentService);
		final AttachmentModelProvider modelProvider = new AttachmentModelProvider(
				getModelSession());

		// update domain tables
		if (domainTableInitial != null) {
			final List<Anhang> domainAttachments = modelProvider
					.getDomainAttachmentsInitial();
			domainTableInitial.updateModel(
					transformation.toAttachment(domainAttachments));
		}
		if (domainTableFinal != null) {
			final List<Anhang> domainAttachments = modelProvider
					.getDomainAttachmentsFinal();
			domainTableFinal.updateModel(
					transformation.toAttachment(domainAttachments));
		}
		if (domainTableSingle != null) {
			final List<Anhang> domainAttachments = modelProvider
					.getDomainAttachmentsSingle();
			domainTableSingle.updateModel(
					transformation.toAttachment(domainAttachments));
		}

		// update object table
		final List<Anhang> objectAttachments = modelProvider
				.getObjectManagementAttachments();
		objectTable.updateModel(transformation.toAttachment(objectAttachments));
	}
}
