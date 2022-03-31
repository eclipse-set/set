/**
 * Copyright (c) 2019 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis;

import java.util.Date;

/**
 * Describes how to initialize a toolbox session (e.g. a PlanPro project).
 * 
 * @author Schaefer
 */
public interface InitializationData {

	/**
	 * @return the directory for the session file
	 */
	String getDirectory();

	/**
	 * @param date
	 *            the date (may be used in filename construction)
	 */
	void setCreationDate(Date date);
}
