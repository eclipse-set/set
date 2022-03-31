/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis.constants;

/**
 * The state of a toolbox view.
 * 
 * @author Schaefer
 */
public enum ToolboxViewState {

	/**
	 * The creation of the view was canceled.
	 */
	CANCELED,

	/**
	 * The creation of the view was complete.
	 */
	CREATED,

	/**
	 * The creation of the view was aborted due to an error.
	 */
	ERROR,

	/**
	 * The state of the view is unknown.
	 */
	UNKNOWN
}
