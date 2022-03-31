/**
 * Copyright (c) 2019 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.application.attachments;

import java.io.IOException;

import javax.inject.Inject;

import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.basis.attachments.Attachment;
import org.eclipse.set.basis.files.AttachmentContentService;
import org.eclipse.set.basis.files.ToolboxFile.Format;
import org.eclipse.set.basis.guid.Guid;

import de.scheidtbachmann.planpro.model.model1902.Basisobjekte.Anhang;

/**
 * Implementation for {@link AttachmentContentService}.
 * 
 * @author Schaefer
 */
class ToolboxAttachmentContentService implements AttachmentContentService {

	@Inject
	private MApplication application;

	@Override
	public byte[] getContent(final Anhang attachment) throws IOException {
		final IModelSession modelSession = getModelSession();
		final Format format = modelSession.getToolboxFile().getFormat();

		if (format.isPlain()) {
			return attachment.getAnhangAllg().getDaten().getWert();
		}

		return modelSession.getToolboxFile()
				.getMedia(Guid.create(attachment.getIdentitaet().getWert()));
	}

	@Override
	public byte[] getContent(final Attachment attachment) throws IOException {
		final Object original = attachment.getOriginal();

		if (original != null) {
			return getContent((Anhang) original);
		}

		return attachment.getData();
	}

	private IModelSession getModelSession() {
		return application.getContext().get(IModelSession.class);
	}
}
