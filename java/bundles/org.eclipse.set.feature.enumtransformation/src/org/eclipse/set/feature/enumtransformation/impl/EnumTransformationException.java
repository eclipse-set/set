/**
 * Copyright (c) 2020 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.feature.enumtransformation.impl;

import org.apache.poi.hssf.usermodel.HSSFRow;

/**
 * Indicates a problem with the enumeration transformation.
 * 
 * @author Schaefer
 */
public class EnumTransformationException extends RuntimeException {

	private static String getMessage(final HSSFRow row) {
		return "Exception while transforming rowNum=" + row.getRowNum(); //$NON-NLS-1$
	}

	/**
	 * @param cause
	 *            the cause
	 * @param row
	 *            the row being transformed
	 */
	public EnumTransformationException(final Exception cause,
			final HSSFRow row) {
		super(getMessage(row), cause);
	}
}
