/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.basis.constants.TableType;
import org.eclipse.set.basis.part.PartDescription;
import org.eclipse.set.model.tablemodel.Table;
import org.eclipse.set.ppmodel.extensions.utils.TableNameInfo;
import org.eclipse.set.utils.BasePart;
import org.eclipse.set.utils.table.TableError;

/**
 * service for managing model services.
 * 
 * @author rumpf
 */
public interface TableService {

	/**
	 * Extract the shortcut from an configuration element.
	 * 
	 * @param element
	 *            the configuration element
	 * 
	 * @return the shortcut
	 */
	String extractShortcut(PartDescription element);

	/**
	 * Extract the shortcut from an element id.
	 * 
	 * @param elementId
	 *            the element id
	 * 
	 * @return the shortcut
	 */
	String extractShortcut(String elementId);

	/**
	 * @param shortcut
	 *            the shortcut
	 * 
	 * @return the name info
	 */
	TableNameInfo getTableNameInfo(String shortcut);

	/**
	 * Gets information about all available tables.
	 * 
	 * @return the table information
	 */
	Collection<String> getAvailableTables();

	/**
	 * Get all collected table errors.
	 * 
	 * @return collected table errors
	 */
	Map<String, Collection<TableError>> getTableErrors();

	/**
	 * Transform the selected container to a string with CSV format.
	 * 
	 * @param elementId
	 *            the elementId
	 * @param tableType
	 *            the table type
	 * @param modelSession
	 *            the model session
	 * 
	 * @return the table
	 */
	String transformToCsv(final String elementId, TableType tableType,
			final IModelSession modelSession);

	/**
	 * Transform the selected container to a table model.
	 * 
	 * @param elementId
	 *            the elementId
	 * @param tableType
	 *            the table type
	 * @param modelSession
	 *            the model session
	 * 
	 * @return the table
	 */
	Table transformToTable(final String elementId, TableType tableType,
			final IModelSession modelSession);

	/**
	 * @param part
	 * @param updateThread
	 * @param clearInstance
	 */
	void updateTable(final BasePart part, final Runnable updateThread,
			Runnable clearInstance);

	/**
	 * Get fixed columns
	 * 
	 * @param elementID
	 * @return position of fixed columns
	 */
	Set<Integer> getFixedColumns(final String elementID);
}
