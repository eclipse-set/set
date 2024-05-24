/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.table;

/**
 * Errors that occured during table transformation
 * 
 * @author Peters
 *
 */
public class TableError {
	private final String guid;
	private final String leadingObject;
	private String source;
	private final String message;
	private final int rowNumber;

	/**
	 * @param guid
	 *            the guid of the affected leading object
	 * @param leadingObject
	 *            the affected leading object
	 * @param source
	 *            the source table
	 * @param message
	 *            a description of the error
	 * @param rowNumber
	 *            the row number where the error occurred
	 */
	public TableError(final String guid, final String leadingObject,
			final String source, final String message, final int rowNumber) {
		this.guid = guid;
		this.leadingObject = leadingObject;
		this.source = source;
		this.message = message;
		this.rowNumber = rowNumber;
	}

	/**
	 * @return the guid of the affected leading object
	 */
	public String getGuid() {
		return guid;
	}

	/**
	 * @return the affected leading object
	 */
	public String getLeadingObject() {
		return leadingObject;
	}

	/**
	 * @return the source table
	 */
	public String getSource() {
		return source;
	}

	/**
	 * @param source
	 *            sets the source table
	 */
	public void setSource(final String source) {
		this.source = source;
	}

	/**
	 * @return the message of the error
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @return the row number where the error occurred
	 */
	public String getRowNumber() {
		return String.valueOf(rowNumber);
	}
}
