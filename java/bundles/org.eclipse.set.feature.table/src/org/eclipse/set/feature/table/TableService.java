/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table;

import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.basis.constants.TableType;
import org.eclipse.set.basis.part.PartDescription;
import org.eclipse.set.model.tablemodel.Table;
import org.eclipse.set.ppmodel.extensions.utils.TableNameInfo;

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
}
