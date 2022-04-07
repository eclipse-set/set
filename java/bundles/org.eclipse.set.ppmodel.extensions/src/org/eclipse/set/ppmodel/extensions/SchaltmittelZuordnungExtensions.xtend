/**
 * Copyright (c) 2017 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import org.eclipse.set.toolboxmodel.Basisobjekte.Basis_Objekt
import org.eclipse.set.toolboxmodel.Ortung.Schaltmittel_Zuordnung
import static extension org.eclipse.set.ppmodel.extensions.ZeigerExtensions.*

/**
 * This class extends {@link Schaltmittel_Zuordnung}.
 */
class SchaltmittelZuordnungExtensions extends BasisObjektExtensions {

	/**
	 * @param zuordnung this Schaltmittel Zuordnung
	 * 
	 * @return the object operating the Schaltanforderung
	 */
	static def Basis_Objekt getSchalter(Schaltmittel_Zuordnung zuordnung) {
		return zuordnung.IDSchalter.resolve(Basis_Objekt)
	}
}
