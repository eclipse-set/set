/**
 * Copyright (c) 2016 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import de.scheidtbachmann.planpro.model.model1902.Fahrstrasse.Markanter_Punkt
import de.scheidtbachmann.planpro.model.model1902.Ortung.Schaltmittel_Zuordnung
import de.scheidtbachmann.planpro.model.model1902.Ortung.Zugeinwirkung
import java.util.List
import static extension org.eclipse.set.ppmodel.extensions.ZeigerExtensions.*

/**
 * This class extends {@link Zugeinwirkung}.
 */
class ZugEinwirkungExtensions extends BasisObjektExtensions {
	def static Markanter_Punkt getMarkanterPunkt(Zugeinwirkung einwirkung) {
		return einwirkung.IDBezugspunkt.resolve(Markanter_Punkt)
	}

	def static List<Schaltmittel_Zuordnung> getSchaltMittelZuordnung(
		Zugeinwirkung einwirkung
	) {
		return einwirkung.container.schaltmittelZuordnung.filter [
			einwirkung.identitaet.wert == IDSchalter.wert
		].toList
	}
}
