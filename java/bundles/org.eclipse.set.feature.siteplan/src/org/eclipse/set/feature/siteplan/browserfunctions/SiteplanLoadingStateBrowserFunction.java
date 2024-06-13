/**
 * Copyright (c) 2024 DB InfraGO AG and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 */
package org.eclipse.set.feature.siteplan.browserfunctions;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.set.basis.constants.Events;
import org.eclipse.set.browser.swt.BrowserFunction;
import org.eclipse.set.utils.WebBrowser;

/**
 * 
 */
public class SiteplanLoadingStateBrowserFunction extends BrowserFunction {
	IEventBroker broker;

	/**
	 * @param webbrowser
	 *            the {@link WebBrowser}
	 * @param name
	 *            the functoin name
	 * @param broker
	 *            the {@link IEventBroker}
	 */
	public SiteplanLoadingStateBrowserFunction(final WebBrowser webbrowser,
			final String name, final IEventBroker broker) {
		super(webbrowser.getBrowser(), name);
		this.broker = broker;
	}

	@Override
	public final Object function(final Object[] arguments) {
		// Types are enforced via TypeScript in the frontend.
		if (arguments[0] instanceof final Boolean loadingState) {
			execute(loadingState);
		}
		return null;
	}

	private final void execute(final Boolean loadingState) {
		broker.send(Events.SITEPLAN_OPENING, loadingState);
	}
}
