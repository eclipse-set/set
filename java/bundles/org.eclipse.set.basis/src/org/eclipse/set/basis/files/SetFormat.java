/**
 * Copyright (c) 2019 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis.files;

import org.eclipse.set.basis.files.ToolboxFile.Format;

/**
 * The SET implementation of {@link Format}.
 * 
 * @author Schaefer
 */
public class SetFormat implements Format {

	/**
	 * @return the plain PlanPro toolbox file format
	 */
	public static Format createPlainPlanPro() {
		return new SetFormat(true, false, false);
	}

	/**
	 * @return the merge toolbox file format
	 */
	public static Format createTemporaryIntegration() {
		return new SetFormat(false, true, true);
	}

	/**
	 * @return the zipped toolbox file format
	 */
	public static Format createZippedPlanPro() {
		return new SetFormat(false, false, true);
	}

	private final boolean plain;
	private final boolean temporaryIntegration;
	private final boolean zippedPlanPro;

	protected SetFormat(final boolean plain, final boolean temporaryIntegration,
			final boolean zippedPlanPro) {
		this.plain = plain;
		this.temporaryIntegration = temporaryIntegration;
		this.zippedPlanPro = zippedPlanPro;
	}

	@Override
	public boolean isPlain() {
		return plain;
	}

	@Override
	public boolean isTemporaryIntegration() {
		return temporaryIntegration;
	}

	@Override
	public boolean isZippedPlanPro() {
		return zippedPlanPro;
	}
}
