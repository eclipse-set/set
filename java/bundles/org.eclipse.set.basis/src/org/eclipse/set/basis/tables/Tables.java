/**
 * Copyright (c) 2019 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis.tables;

/**
 * Basic support functions for tables.
 * 
 * @author Schaefer
 */
public class Tables {

	/**
	 * Determines the column identifier (alphanumerical) for a column index
	 * (number).
	 * 
	 * @param columnIndex
	 *            the column index (zero based)
	 * 
	 * @return the column identifier
	 */
	public static String getColumnIdentifier(final int columnIndex) {
		if (columnIndex < 26) {
			return new String(new char[] { (char) ('A' + columnIndex) });
		}
		final int j = columnIndex / 26;
		final int k = columnIndex % 26;
		return getColumnIdentifier(j - 1) + getColumnIdentifier(k);
	}

	/**
	 * Determines the column index (number) for a column identifier
	 * (alphanumerical).
	 * 
	 * @param columnId
	 *            the column identifier
	 * 
	 * @return the column index (zero based)
	 */
	public static int getColumnIndex(final String columnId) {
		final byte[] bytes = columnId.getBytes();
		long result = 0;
		for (int i = 0; i < bytes.length; i++) {
			final int exp = bytes.length - i - 1;
			if (exp > 0) {
				result = result
						+ (bytes[i] - 'A' + 1) * Math.round(Math.pow(26, exp));
			} else {
				result = result + bytes[i] - 'A';
			}
		}
		return (int) result;
	}
}
