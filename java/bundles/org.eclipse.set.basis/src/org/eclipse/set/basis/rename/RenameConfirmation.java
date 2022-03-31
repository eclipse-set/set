/**
 * Copyright (c) 2019 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis.rename;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * User confirmation about renaming.
 * 
 * @author Schaefer
 */
public enum RenameConfirmation {

	/**
	 * Abort renaming/saving.
	 */
	ABORT(2),

	/**
	 * Rename the resource, write the new one and delete the old.
	 */
	CHANGE(0),

	/**
	 * Leave the old resource with the old name as it is and write a new
	 * resource with a new name.
	 */
	NEW(1);

	/**
	 * @param dialogResult
	 *            the dialog result
	 * 
	 * @return the rename confirmation
	 * 
	 * @throws NoSuchElementException
	 *             if there is no confirmation with the given dialog result
	 */
	public static RenameConfirmation fromDialogResult(final int dialogResult) {
		final Optional<RenameConfirmation> findAny = Arrays
				.stream(RenameConfirmation.values())
				.filter(c -> c.getDialogResult() == dialogResult).findAny();
		return findAny.get();
	}

	private int dialogResult;

	private RenameConfirmation(final int dialogResult) {
		this.dialogResult = dialogResult;
	}

	/**
	 * @return the dialogResult
	 */
	public int getDialogResult() {
		return dialogResult;
	}
}
