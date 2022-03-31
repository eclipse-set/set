/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis.autofill;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;

/**
 * Automatically fill {@link EObject}s.
 * 
 * @author Schaefer
 */
public interface Autofill {

	/**
	 * @param instruction
	 *            the filling instruction
	 */
	void addFillingInstruction(final FillInstruction instruction);

	/**
	 * Execute the given instruction.
	 * 
	 * @param instruction
	 *            the instruction
	 */
	void execute(FillInstruction instruction);

	/**
	 * @param editingDomain
	 *            the editing domain
	 */
	void setEditingDomain(final EditingDomain editingDomain);
}
