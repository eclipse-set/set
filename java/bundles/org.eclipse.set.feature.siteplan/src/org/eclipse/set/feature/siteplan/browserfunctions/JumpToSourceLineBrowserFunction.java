/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.feature.siteplan.browserfunctions;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.set.browser.swt.BrowserFunction;
import org.eclipse.set.core.services.part.ToolboxPartService;
import org.eclipse.set.utils.WebBrowser;
import org.eclipse.set.utils.events.JumpToSourceLineEvent;
import org.eclipse.set.utils.events.ToolboxEvents;

/**
 * Browser function to trigger a {@link JumpToSourceLineEvent}
 * 
 * @author Truong
 */
public class JumpToSourceLineBrowserFunction extends BrowserFunction {
	private static final String SOURCE_TEXT_VIEWER_PART_ID = "org.eclipse.set.application.descriptions.SourceWebTextViewDescriptionService"; //$NON-NLS-1$
	IEventBroker broker;
	ToolboxPartService partService;

	/**
	 * @param webBrowser
	 *            the browser
	 * @param name
	 *            javascript function name
	 * @param broker
	 *            the event broker
	 * @param partService
	 *            the toolbox part service
	 */
	public JumpToSourceLineBrowserFunction(final WebBrowser webBrowser,
			final String name, final IEventBroker broker,
			final ToolboxPartService partService) {
		super(webBrowser.getBrowser(), name);
		this.broker = broker;
		this.partService = partService;
	}

	@Override
	public final Object function(final Object[] arguments) {
		execute((String) arguments[0]);
		return null;
	}

	private final void execute(final String searchGuid) {
		partService.showPart(SOURCE_TEXT_VIEWER_PART_ID);
		ToolboxEvents.send(broker, new JumpToSourceLineEvent(searchGuid, null));
	}

}
