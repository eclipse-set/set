/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.tablemodel.extensions

import org.eclipse.set.model.tablemodel.TableRow
import java.util.List
import org.eclipse.set.model.tablemodel.TableCell
import org.eclipse.set.model.tablemodel.StringCellContent

/**
 * Tablemodel utilities.
 * 
 * @author Schaefer
 */
class Utils {

	/**
	 * @param object the object
	 * 
	 * @return debug output of the object
	 */
	static def dispatch String debugString(Object object) {
		return object?.toString
	}

	static def dispatch String debugString(TableRow tableRow) {
		if (tableRow.footnotes === null)
			return '''«tableRow.cells.debugString»'''
		return '''«tableRow.cells.debugString» «tableRow.footnotes.debugString»'''
	}

	static def dispatch String debugString(List<?> list) {
		return '''(«FOR item : list SEPARATOR " "»«item.debugString»«ENDFOR»)'''
	}

	static def dispatch String debugString(TableCell cell) {
		if (cell.content === null)
			return ""
		return cell.content.debugString
	}

	static def dispatch String debugString(StringCellContent stringColumn) {
		if (stringColumn.value === null)
			return ""
		return '''"«stringColumn.value»"'''
	}
}
