/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.siteplan.browserfunctions;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.set.browser.swt.BrowserFunction;
import org.eclipse.set.utils.WebBrowser;
import org.eclipse.set.utils.events.TableSelectRowByGuidEvent;
import org.eclipse.set.utils.events.ToolboxEvents;

/**
 * Browser function to trigger a TableSelectRowEvent
 * 
 * @author Stuecker
 */
public class TableSelectRowBrowserFunction extends BrowserFunction {
	IEventBroker broker;

	/**
	 * Constructor.
	 * 
	 * @param webBrowser
	 *            browser to add this function to
	 * @param name
	 *            name of this function for Javascript
	 * @param broker
	 *            event broker to use to send the event
	 */
	public TableSelectRowBrowserFunction(final WebBrowser webBrowser,
			final String name, final IEventBroker broker) {
		super(webBrowser.getBrowser(), name);
		this.broker = broker;
	}

	@Override
	public final Object function(final Object[] arguments) {
		// Types are enforced via TypeScript in the frontend.
		execute((String) arguments[0]);
		return null;
	}

	private final void execute(final String searchKey) {
		ToolboxEvents.send(broker, new TableSelectRowByGuidEvent(searchKey));
	}
}
