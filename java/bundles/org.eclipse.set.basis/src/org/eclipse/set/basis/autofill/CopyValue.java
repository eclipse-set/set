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
 * Copy attribute.
 * 
 * @author Schaefer
 */
public class CopyValue implements FillInstruction {

	private Autofill autofill;

	private final FillSetting source;
	private final FillSetting target;

	/**
	 * @param trigger
	 *            the trigger
	 * @param source
	 *            the source setting
	 * @param target
	 *            the target setting
	 */
	public CopyValue(final FillTrigger trigger, final FillSetting source,
			final FillSetting target) {
		this.source = source;
		this.target = target;
		if (trigger != null) {
			trigger.addFillInstruction(this);
		}
	}

	@Override
	public void execute() {
		if (autofill == null) {
			throw new IllegalStateException("No autofill set."); //$NON-NLS-1$
		}
		autofill.execute(this);
	}

	@Override
	public FillSetting getSourceSetting() {
		return source;
	}

	@Override
	public FillSetting getTargetSetting() {
		return target;
	}

	@Override
	public void setAutofill(final Autofill autofill) {
		this.autofill = autofill;
	}
}
