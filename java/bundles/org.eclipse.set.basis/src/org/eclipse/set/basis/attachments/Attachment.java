/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis.attachments;

/**
 * A PlanPro-Version independent description of attachments.
 * 
 * @author Schaefer
 */
public interface Attachment {

	/**
	 * @return the filename (excluding the extension)
	 */
	String getBaseFilename();

	/**
	 * @return the data
	 */
	byte[] getData();

	/**
	 * @return the extension
	 */
	String getFileExtension();

	/**
	 * @return the file kind
	 */
	FileKind getFileKind();

	/**
	 * @return the filename (including the extension)
	 */
	String getFullFilename();

	/**
	 * @return the id of the attachment
	 */
	String getId();

	/**
	 * @return the original PlanPro Anhang, if this attachment is a proxy for a
	 *         PlanPro Anhang; <code>null</code> otherwise
	 */
	Object getOriginal();

	/**
	 * @return whether the attachment is of pdf type
	 */
	boolean isPdf();

	/**
	 * @param fileKind
	 *            the file kind
	 */
	void setFileKind(FileKind fileKind);
}
