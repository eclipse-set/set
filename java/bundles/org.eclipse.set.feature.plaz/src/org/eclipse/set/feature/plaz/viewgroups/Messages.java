/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.plaz.viewgroups;

import org.eclipse.osgi.util.NLS;

/**
 * Translations.
 * 
 * @author Stuecker
 */
@SuppressWarnings("javadoc") // NLS provides tooltip support
public class Messages extends NLS {
	public static String PlazViewGroups_Plaz;

	private static final String BUNDLE_NAME = "org.eclipse.set.feature.plaz.viewgroups.messages"; //$NON-NLS-1$
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}