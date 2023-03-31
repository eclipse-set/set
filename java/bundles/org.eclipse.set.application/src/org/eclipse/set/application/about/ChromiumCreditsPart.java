/**
 * Copyright (c) 2023 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.application.about;

import org.eclipse.set.utils.WebNoSessionBasePart;
import org.eclipse.swt.widgets.Composite;

/**
 * This part displays chrome://credits
 */
public class ChromiumCreditsPart extends WebNoSessionBasePart {
	private static final String CHROME_CREDITS = "chrome://credits"; //$NON-NLS-1$

	@Override
	protected void createView(final Composite parent) {
		super.createView(parent);
		browser.setUrl(CHROME_CREDITS);
	}
}
