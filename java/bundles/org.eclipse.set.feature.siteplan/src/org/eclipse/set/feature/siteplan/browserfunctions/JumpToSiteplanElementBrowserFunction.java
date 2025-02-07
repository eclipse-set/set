/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.feature.siteplan.browserfunctions;

import java.util.Optional;

import org.eclipse.set.core.services.siteplan.SiteplanService;
import org.eclipse.set.model.planpro.Basisobjekte.Ur_Objekt;
import org.eclipse.set.model.siteplan.SiteplanObject;
import org.eclipse.set.model.tablemodel.extensions.TableRowExtensions;
import org.eclipse.set.utils.WebBrowser;
import org.eclipse.set.utils.events.JumpToSiteplanEvent;

/**
 * Browser function to trigger a {@RightClickOnSelectedRowEvent}
 * 
 * @author Truong
 *
 */
public class JumpToSiteplanElementBrowserFunction {
	private static final String DESELECTED = "DESELECTED"; //$NON-NLS-1$

	// Name of the Javascript function to call, when the user right click on
	// select row
	static final private String JUMP_TO_ELEMENT_FUNCTION = "window.planproJumpToElement"; //$NON-NLS-1$

	private final WebBrowser webBrowser;

	private final SiteplanService siteplanService;

	/**
	 * @param siteplanService
	 *            the {@link SiteplanService}
	 * @param webbrowser
	 *            browser to add this function to
	 */
	public JumpToSiteplanElementBrowserFunction(
			final SiteplanService siteplanService,
			final WebBrowser webbrowser) {
		this.siteplanService = siteplanService;
		this.webBrowser = webbrowser;
	}

	/**
	 * Binden and execute javascript function
	 * 
	 * @param guid
	 *            signal Guid
	 */
	public final void execute(final String guid) {
		final String jsFunction = String.format("""
				{
					let intervalId = 0
					const jumpToElement = () => {
						if(%s) {
							%s('%s');
							clearInterval(intervalId);
						}
					}
					intervalId = setInterval(jumpToElement, 200);

				}
				""", JUMP_TO_ELEMENT_FUNCTION, JUMP_TO_ELEMENT_FUNCTION, guid); //$NON-NLS-1$
		webBrowser.executeJavascript(jsFunction);
	}

	/**
	 * @param selectedRowEvent
	 *            the event to be executed
	 */
	public void execute(final JumpToSiteplanEvent selectedRowEvent) {
		final String objectGuid = getObjectGuid(selectedRowEvent);
		if (objectGuid != null) {
			execute(objectGuid);
		} else {
			execute(DESELECTED);
		}
	}

	private String getObjectGuid(final JumpToSiteplanEvent event) {
		if (event.getRow() != null) {
			final Ur_Objekt leadingObject = TableRowExtensions
					.getLeadingObject(event.getRow());
			final Optional<SiteplanObject> relevantSiteplanElement = siteplanService
					.getSiteplanElement(leadingObject);
			if (relevantSiteplanElement.isPresent()) {
				return relevantSiteplanElement.get().getGuid();
			}
			return null;
		}

		if (event.getGuid() != null && !event.getGuid().isBlank()) {
			return event.getGuid();
		}
		return null;
	}
}
