/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis.attachments;

/**
 * A PlanPro-Version independent description of file kinds.
 * 
 * @author Schaefer
 */
public class FileKind {

	private final int id;
	private final String translation;

	/**
	 * @param id
	 *            the (enumeration) id
	 * @param translation
	 *            the translation
	 */
	public FileKind(final int id, final String translation) {
		this.id = id;
		this.translation = translation;
	}

	/**
	 * @return the (enumeration) id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the translation
	 */
	public String getTranslation() {
		return translation;
	}
}
