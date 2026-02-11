/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.table;

import org.eclipse.set.basis.constants.ContainerType;
import org.eclipse.set.model.planpro.Basisobjekte.Ur_Objekt;
import org.eclipse.set.model.planpro.Basisobjekte.impl.Ur_ObjektImpl;
import org.eclipse.set.model.tablemodel.TableRow;
import org.eclipse.set.model.tablemodel.extensions.TableRowExtensions;
import org.eclipse.set.ppmodel.extensions.EObjectExtensions;

/**
 * Errors that occurred during table transformation
 * 
 * @author Peters
 *
 */
public class TableError extends Ur_ObjektImpl {
	private final Ur_Objekt leadingObject;
	private final String errorIdentifier;
	private String source;
	private final String message;
	private final TableRow row;

	private final ContainerType containerType;

	/**
	 * @param leadingObject
	 *            the leading object of the affected leading object
	 * @param errorIdentifier
	 *            the affected leading object
	 * @param source
	 *            the source table
	 * @param message
	 *            a description of the error
	 * @param row
	 *            the row where the error occurred
	 * @param containerType
	 *            the container type of leading object
	 */
	public TableError(final Ur_Objekt leadingObject,
			final String errorIdentifier, final String source,
			final String message, final TableRow row,
			final ContainerType containerType) {
		this.leadingObject = leadingObject;
		this.errorIdentifier = errorIdentifier;
		this.source = source;
		this.message = message;
		this.row = row;
		this.containerType = containerType;
	}

	/**
	 * @return the guid of the affected leading object
	 */
	public String getGuid() {
		return EObjectExtensions
				.getNullableObject(leadingObject,
						obj -> obj.getIdentitaet().getWert())
				.orElse(""); //$NON-NLS-1$
	}

	/**
	 * @return the error identifier string
	 */
	public String getErrorIdentifier() {
		return errorIdentifier;
	}

	/**
	 * @return the leading object
	 */
	public Ur_Objekt getLeadingObject() {
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
		return String.valueOf(TableRowExtensions.getTrueRowIndex(row) + 1);
	}

	/**
	 * @return the container type of leading object
	 */
	public ContainerType getContainerType() {
		return containerType;
	}
}
