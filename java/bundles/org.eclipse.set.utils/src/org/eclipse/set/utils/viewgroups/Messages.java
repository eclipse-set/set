/**
 * Copyright (c) 2020 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.viewgroups;

import org.eclipse.osgi.util.NLS;

/**
 * Translations.
 * 
 * @author Schaefer
 */
@SuppressWarnings("javadoc") // NLS provides tooltip support
public class Messages extends NLS {

	// IMPROVE: We use static message translation here to simplify access to
	// view groups

	public static String SetViewGroups_Development;
	public static String SetViewGroups_Edit;
	public static String SetViewGroups_Export;
	public static String SetViewGroups_Information;
	public static String SetViewGroups_Siteplan;
	public static String SetViewGroups_Table_ESTW;
	public static String SetViewGroups_Table_ETCS;
	public static String SetViewGroups_Table_ESTW_Supplement;
	public static String SetViewGroups_Table_Supplement;

	private static final String BUNDLE_NAME = "org.eclipse.set.utils.viewgroups.messages"; //$NON-NLS-1$
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
