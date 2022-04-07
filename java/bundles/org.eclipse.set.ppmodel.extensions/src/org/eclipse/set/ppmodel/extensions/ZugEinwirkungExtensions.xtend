/**
 * Copyright (c) 2016 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import org.eclipse.set.toolboxmodel.Fahrstrasse.Markanter_Punkt
import org.eclipse.set.toolboxmodel.Ortung.Schaltmittel_Zuordnung
import org.eclipse.set.toolboxmodel.Ortung.Zugeinwirkung
import java.util.List

/**
 * This class extends {@link Zugeinwirkung}.
 */
class ZugEinwirkungExtensions extends BasisObjektExtensions {
	def static Markanter_Punkt getMarkanterPunkt(Zugeinwirkung einwirkung) {
		return einwirkung.IDBezugspunkt
	}

	def static List<Schaltmittel_Zuordnung> getSchaltMittelZuordnung(
		Zugeinwirkung einwirkung
	) {
		return einwirkung.container.schaltmittelZuordnung.filter [
			einwirkung.identitaet.wert == IDSchalter.identitaet.wert
		].toList
	}
}
