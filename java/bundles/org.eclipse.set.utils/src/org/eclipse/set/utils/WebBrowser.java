/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.utils;

import java.awt.Desktop;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.eclipse.set.browser.Browser;
import org.eclipse.set.browser.cef.ChromiumStatic;
import org.eclipse.set.browser.swt.BrowserFunction;
import org.eclipse.set.browser.swt.WindowEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.LocationEvent;
import org.eclipse.swt.browser.LocationListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * Wrapper for the SWT WebBrowser
 * 
 * @author Stuecker
 *
 */
public class WebBrowser {
	private final Browser browser;

	private final List<BrowserFunction> browserFunctions = new ArrayList<>();

	/**
	 * Creates a new chromium web browser with Javascript enabled
	 * 
	 * @param parent
	 *            The SWT parent object
	 */
	public WebBrowser(final Composite parent) {
		// Enable browser debug in development mode
		if (ToolboxConfiguration.isDevelopmentMode()) {
			ChromiumStatic.getCEFConfiguration().DebugPort = 9999;
		}
		browser = createBrowser(parent);
	}

	private Browser createBrowser(final Composite parent) {
		final Browser browserInstance = new Browser(parent, SWT.NONE);

		// Enable Javascript
		browserInstance.setJavascriptEnabled(true);

		// Listen to location changes
		browserInstance.addLocationListener(new LocationListener() {
			@Override
			public void changing(final LocationEvent event) {
				onLocationChange(event);
			}

			@Override
			public void changed(final LocationEvent event) {
				// Do nothing
			}
		});

		// Also apply the configuration to any sub browser created in a new
		// window
		browserInstance.addOpenWindowListener((final WindowEvent event) -> {
			if (event.browser == null) {
				event.browser = createBrowser(parent);
			}
		});
		return browserInstance;
	}

	/**
	 * Validates whether a location change is allowed, if not it attempts to
	 * open the URL in the system browser
	 * 
	 * @param event
	 *            the location event
	 */
	private void onLocationChange(final LocationEvent event) {
		event.doit = isURLAllowed(event.location);

		if (!event.doit) {
			// Attempt to use the system browser instead
			if (Desktop.isDesktopSupported() && Desktop.getDesktop()
					.isSupported(Desktop.Action.BROWSE)) {
				try {
					Desktop.getDesktop().browse(new URI(event.location));
				} catch (final Exception e) {
					// Ignore failure to open the system browser
				}
			}
		}
	}

	@SuppressWarnings({ "nls", "static-method" })
	protected List<String> getAllowedPrefixes() {
		// URLs must start with one of the allowed prefixes in order to be
		// opened
		return List.of("file://", "http://localhost:", "http://localhost/",
				"chrome://");
	}

	/**
	 * Checks whether the browser should be allowed to open a given URL
	 * 
	 * @param url
	 *            the url to check
	 * @return whether opening the URL is allowed
	 */
	private boolean isURLAllowed(final String url) {
		final List<String> allowedPrefixes = getAllowedPrefixes();

		return allowedPrefixes.stream().anyMatch(url::startsWith);
	}

	/**
	 * Executes Javascript in the WebBrowser context
	 * 
	 * @param javascript
	 *            the script to execute
	 */
	public void executeJavascript(final String javascript) {
		browser.execute(javascript);
	}

	/**
	 * Returns the internal browser component
	 * 
	 * @return the internal browser component
	 */
	public Browser getBrowser() {
		return browser;
	}

	/**
	 * @return the browsers control
	 */
	public Control getControl() {
		return getBrowser();
	}

	/**
	 * Registers a callback with the browser which can be called from Javascript
	 * Note: Called from worker thread
	 * 
	 * @param browserFunction
	 *            function to register
	 */
	public void registerJSFunction(final BrowserFunction browserFunction) {
		browserFunctions.add(browserFunction);
	}

	/**
	 * Registers a callback with the browser which can be called from Javascript
	 * Note: Called from worker thread
	 * 
	 * @param <R>
	 *            Return type of function
	 * @param name
	 *            Name of the javascript function
	 * @param function
	 *            Functional interface to call
	 */
	public <R> void registerJSFunction(final String name,
			final Function<String, R> function) {
		browserFunctions.add(new BrowserFunction(browser, name) {
			@Override
			public final Object function(final Object[] arguments) {
				if (arguments.length != 1
						&& !(arguments[0] instanceof String)) {
					throw new RuntimeException("Incorrect parameter."); //$NON-NLS-1$
				}
				return function.apply((String) arguments[0]);
			}
		});
	}

	/**
	 * @see{Browser.setLayoutData}
	 * @param gridData
	 *            the grid data
	 */
	public void setLayoutData(final GridData gridData) {
		browser.setLayoutData(gridData);
	}

	/**
	 * @see{Browser.setUrl}
	 * @param url
	 *            the new url
	 */
	public void setUrl(final String url) {
		browser.setUrl(url);
	}

	/**
	 * @see{Browser.refresh}
	 */
	public void refresh() {
		browser.refresh();
	}

	/**
	 * Stops the browser
	 */
	public void stop() {
		// Browser functions must be disposed explicitly
		for (final BrowserFunction function : browserFunctions) {
			function.dispose();
		}
	}

}
