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
 * A filling instruction.
 * 
 * @author Schaefer
 */
public interface FillInstruction {

	/**
	 * Execute the instruction.
	 */
	void execute();

	/**
	 * @return the source setting
	 */
	FillSetting getSourceSetting();

	/**
	 * @return the target setting
	 */
	FillSetting getTargetSetting();

	/**
	 * @param autofill
	 *            the autofill
	 */
	void setAutofill(Autofill autofill);
}
