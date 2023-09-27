/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.feature.siteplan.browserfunctions;

import org.eclipse.set.model.tablemodel.extensions.TableRowExtensions;
import org.eclipse.set.utils.WebBrowser;
import org.eclipse.set.utils.events.SelectedRowEvent;

/**
 * Browser function to trigger a {@RightClickOnSelectedRowEvent}
 * 
 * @author Truong
 *
 */
public class SignalSelectBrowserFunction {
	private static final String DESELECTED = "DESELECTED"; //$NON-NLS-1$

	// Name of the Javascript function to call, when the user right click on
	// select row
	static final private String SELECT_SIGNAL_FUNCTION = "window.planproSelectSignal"; //$NON-NLS-1$

	private final WebBrowser webBrowser;

	/**
	 * @param webbrowser
	 *            browser to add this function to
	 */
	public SignalSelectBrowserFunction(final WebBrowser webbrowser) {

		this.webBrowser = webbrowser;
	}

	/**
	 * Binden and execute javascript function
	 * 
	 * @param guid
	 *            signal Guid
	 */
	public final void execute(final String guid) {
		webBrowser.executeJavascript(
				String.format("%s('%s')", SELECT_SIGNAL_FUNCTION, guid)); //$NON-NLS-1$
	}

	/**
	 * @param selectedRowEvent
	 *            the event to be executed
	 */
	public void execute(final SelectedRowEvent selectedRowEvent) {
		if (isRowSelected(selectedRowEvent)) {
			execute(TableRowExtensions
					.getLeadingObjectGuid(selectedRowEvent.getRow()));
		} else {
			execute(DESELECTED);
		}
	}

	private static boolean isRowSelected(
			final SelectedRowEvent selectedRowEvent) {
		return selectedRowEvent.getRow() != null;
	}
}
