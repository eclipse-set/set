/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis.extensions

import org.eclipse.set.basis.part.PartDescription

import static org.eclipse.set.basis.constants.PlanProFileNature.*

/**
 * Extensions for {@link PartDescription}.
 * 
 * @author Schaefer
 */
class PartDescriptionExtensions {

	/**
	 * @param description the part description
	 * 
	 * @return the debug string
	 */
	static def String debugString(PartDescription description, String text) {
		val d = description
		// ||Sichttyp||Sicht||Planungsdatei||Zustandsdatei||Zusammenführung||Nicht verarbeitbar||
		return '''|«text»|«d.toolboxViewName»|«d.canProcess(PLANNING).mark»|«d.canProcess(INFORMATION_STATE).mark»|«d.canProcess(INTEGRATION).mark»|«(!d.needsLoadedModel).mark»|'''
	}

	/**
	 * @param description the part description
	 * 
	 * @return the debug string
	 */
	static def String debugHtml(PartDescription description, String text) {
		val d = description
		// <tr>
		// <th>Sichttyp</th>
		// <th>Sicht</th>
		// <th>Planungsdatei</th>
		// <th>Zustandsdatei</th>
		// <th>Zusammenführung</th>
		// <th>Nicht verarbeitbar</th>
		// </tr>
		return '''<tr><td>«text»</td><td>«d.toolboxViewName»</td><td>«d.canProcess(PLANNING).htmlMark»</td><td>«d.canProcess(INFORMATION_STATE).htmlMark»</td><td>«d.canProcess(INTEGRATION).htmlMark»</td><td>«(!d.needsLoadedModel).htmlMark»</td></tr>'''
	}

	private static def String mark(boolean value) {
		return ''' «IF (value)»(/)«ELSE»(x)«ENDIF» '''
	}

	private static def String htmlMark(boolean value) {
		return ''' «IF (value)»&#9745«ELSE»&#9744«ENDIF» '''
	}
}
