/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.siteplan.browserfunctions;

import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.browser.swt.BrowserFunction;
import org.eclipse.set.utils.WebBrowser;

/**
 * Browser function to retrieve the current session state
 * 
 * @author Stuecker
 *
 */
public class GetSessionStateBrowserFunction extends BrowserFunction {
	WebBrowser webBrowser;
	IModelSession modelSession;

	/**
	 * Constructor.
	 * 
	 * @param webBrowser
	 *            browser to add this function to
	 * @param name
	 *            name of this function for Javascript
	 * @param modelSession
	 *            the model session
	 */
	public GetSessionStateBrowserFunction(final WebBrowser webBrowser,
			final String name, final IModelSession modelSession) {
		super(webBrowser.getBrowser(), name);
		this.webBrowser = webBrowser;
		this.modelSession = modelSession;
	}

	@Override
	public final Object function(final Object[] arguments) {
		switch (modelSession.getTableType()) {
			case DIFF:
				return "diff"; //$NON-NLS-1$
			case FINAL:
				return "final"; //$NON-NLS-1$
			case INITIAL:
				return "initial"; //$NON-NLS-1$
			case SINGLE:
				return "single"; //$NON-NLS-1$
			default:
				return null;
		}
	}
}
