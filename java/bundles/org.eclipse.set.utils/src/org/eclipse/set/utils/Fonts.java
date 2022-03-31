/**
 * Copyright (c) 2015 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils;

import org.eclipse.jface.resource.FontDescriptor;
import org.eclipse.swt.SWT;

/**
 * This interface provides font descriptions for used fonts.
 * 
 * @author Schaefer
 */
@SuppressWarnings("nls") // font names are not translated
public interface Fonts {

	/**
	 * Arial 10
	 */
	FontDescriptor ARIAL_10 = FontDescriptor.createFrom("Arial", 10, SWT.NONE);

	/**
	 * Arial 10 Bold
	 */
	FontDescriptor ARIAL_10_BOLD = FontDescriptor.createFrom("Arial", 10,
			SWT.BOLD);

	/**
	 * Arial Narrow 10
	 */
	FontDescriptor ARIAL_NARROW_10 = FontDescriptor.createFrom("Arial Narrow",
			10, SWT.NONE);

	/**
	 * font used for group headings
	 */
	FontDescriptor GROUP_HEADING = FontDescriptor.createFrom("Arial", 9,
			SWT.BOLD);

	/**
	 * font used for table headings
	 */
	FontDescriptor TABLE_HEADING = FontDescriptor
			.createFrom("Segoe UI Semibold", 12, SWT.BOLD);

}
