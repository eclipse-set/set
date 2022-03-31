/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis.autofill;

/**
 * Describes, how to trigger automatic filling.
 * 
 * @author Schaefer
 */
public interface FillTrigger {

	/**
	 * Activate this trigger.
	 */
	void activate();

	/**
	 * @param instruction
	 *            the filling instruction
	 */
	void addFillInstruction(FillInstruction instruction);
}
