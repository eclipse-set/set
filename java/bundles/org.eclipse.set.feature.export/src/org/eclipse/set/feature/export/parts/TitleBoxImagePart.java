/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.export.parts;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.set.basis.OverwriteHandling;
import org.eclipse.set.basis.guid.Guid;
import org.eclipse.set.model.planpro.PlanPro.PlanPro_Schnittstelle;
import org.eclipse.set.model.titlebox.Titlebox;
import org.eclipse.set.ppmodel.extensions.utils.PlanProToTitleboxTransformation;
import org.eclipse.set.services.export.ExportService;
import org.eclipse.set.utils.BasePart;
import org.eclipse.set.utils.exception.ExceptionHandler;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import jakarta.inject.Inject;

/**
 * This part renders the titlebox as an image.
 * 
 * @author Schaefer
 */
public class TitleBoxImagePart extends BasePart {

	private static final String TITLEBOX_IMAGE = "titlebox.png"; //$NON-NLS-1$

	private static Image createImage(final String image,
			final LocalResourceManager localResourceManager) {
		final ImageDescriptor titleboxDescriptor = ImageDescriptor
				.createFromFile(null, image);
		return localResourceManager.create(titleboxDescriptor);
	}

	private static void createTitlebox(final Composite parent) {
		parent.setLayout(new GridLayout(1, true));
		final Label label = new Label(parent, SWT.NONE);
		final LocalResourceManager localResourceManager = new LocalResourceManager(
				JFaceResources.getResources(), label);
		label.setImage(createImage(TITLEBOX_IMAGE, localResourceManager));
	}

	@Inject
	private ExportService exportService;

	/**
	 * Create the part.
	 */
	@Inject
	public TitleBoxImagePart() {
		super();
	}

	private Path getAttachmentPath(final String guid) {
		try {
			return getModelSession().getToolboxFile()
					.getMediaPath(Guid.create(guid));
		} catch (final UnsupportedOperationException e) {
			return null; // .ppxml-Files do not support attachments
		}
	}

	private void createTitleboxImage(
			final PlanPro_Schnittstelle planProSchnittstelle) throws Exception {
		final PlanProToTitleboxTransformation planProToTitlebox = PlanProToTitleboxTransformation
				.create();
		final Titlebox titlebox = planProToTitlebox
				.transform(planProSchnittstelle, null, this::getAttachmentPath);
		exportService.exportTitleboxImage(titlebox,
				Paths.get(getModelSession().getTempDir().toString(),
						TITLEBOX_IMAGE),
				OverwriteHandling.forCheckbox(true),
				new ExceptionHandler(getToolboxShell(), getDialogService()));
	}

	@Override
	protected void createView(final Composite parent) {
		if (getModelSession() != null) {
			try {
				createTitleboxImage(
						getModelSession().getPlanProSchnittstelle());
				createTitlebox(parent);
			} catch (final Exception e) {
				throw new RuntimeException(e);
			}
		}
	}
}
