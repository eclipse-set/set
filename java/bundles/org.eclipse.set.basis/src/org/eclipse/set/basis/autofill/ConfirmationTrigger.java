/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis.autofill;

import java.util.function.BooleanSupplier;

/**
 * A filling trigger using a confirmation.
 * 
 * @author Schaefer
 */
public class ConfirmationTrigger extends ConditionalTrigger {

	private final BooleanSupplier confirmation;

	/**
	 * @param confirmation
	 *            the confirmation
	 */
	public ConfirmationTrigger(final BooleanSupplier confirmation) {
		this.confirmation = confirmation;
	}

	@Override
	public void activate() {
		if (getAsBoolean()) {
			if (confirmation.getAsBoolean()) {
				super.activate();
			}
		}
	}
}
