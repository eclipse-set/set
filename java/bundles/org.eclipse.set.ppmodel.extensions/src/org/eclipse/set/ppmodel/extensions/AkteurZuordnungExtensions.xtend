/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import org.eclipse.set.model.planpro.PlanPro.Akteur_Zuordnung
import java.util.Optional
import javax.xml.datatype.XMLGregorianCalendar

/**
 * This class extends {@link Akteur_Zuordnung}.
 */
class AkteurZuordnungExtensions {
	
	static def Optional<XMLGregorianCalendar> getDatum(
		Akteur_Zuordnung zuordnung) {
		return Optional.ofNullable(zuordnung?.datum?.wert)
	}
}
