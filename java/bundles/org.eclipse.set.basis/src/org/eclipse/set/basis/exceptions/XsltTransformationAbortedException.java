/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis.exceptions;

/**
 * An exception to be thrown if an Xslt Transformation is aborted by the user
 * 
 * @author Stuecker
 */
public class XsltTransformationAbortedException extends RuntimeException {
	/**
	 * Constructor
	 */
	public XsltTransformationAbortedException() {
		super();
	}
}
