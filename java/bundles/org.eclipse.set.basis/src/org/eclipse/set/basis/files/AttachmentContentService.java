/**
 * Copyright (c) 2019 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis.files;

import java.io.IOException;

import de.scheidtbachmann.planpro.model.model1902.Basisobjekte.Anhang;
import org.eclipse.set.basis.attachments.Attachment;

/**
 * Manages content for attachments.
 * 
 * @author Schaefer
 */
public interface AttachmentContentService {

	/**
	 * @param attachment
	 *            the attachment
	 * 
	 * @return the content
	 * 
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	byte[] getContent(Anhang attachment) throws IOException;

	/**
	 * @param attachment
	 *            the attachment
	 * 
	 * @return the content
	 * 
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	byte[] getContent(Attachment attachment) throws IOException;
}
