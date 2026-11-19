/**
 * Copyright (c) 2016 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import java.util.List
import org.eclipse.set.model.planpro.Ansteuerung_Element.Stell_Bereich
import org.eclipse.set.model.planpro.Bahnuebergang.BUE_Ausschaltung
import org.eclipse.set.model.planpro.Bahnuebergang.BUE_Einschaltung
import org.eclipse.set.model.planpro.Basisobjekte.Punkt_Objekt
import org.eclipse.set.model.planpro.Ortung.Schaltmittel_Zuordnung
import org.eclipse.set.model.planpro.Ortung.Zugeinwirkung

import static extension org.eclipse.set.ppmodel.extensions.BereichObjektExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.FmaKomponenteExtensions.*

/**
 * This class extends {@link Zugeinwirkung}.
 */
class ZugEinwirkungExtensions extends BasisObjektExtensions {
	def static Punkt_Objekt getBezugspunkt(Zugeinwirkung einwirkung) {
		return einwirkung.IDBezugspunkt?.value?.bezugsPunkt
	}

	def static List<Schaltmittel_Zuordnung> getSchaltMittelZuordnung(
		Zugeinwirkung einwirkung
	) {
		return einwirkung.container.schaltmittelZuordnung.filter [
			einwirkung?.identitaet?.wert == IDSchalter?.value?.identitaet?.wert
		].toList
	}

	def static boolean isBelongToControlArea(Zugeinwirkung zugeinwirkung,
		Stell_Bereich area) {
		val schaltmittle = zugeinwirkung.container.schaltmittelZuordnung.filter [
			IDSchalter?.value instanceof Zugeinwirkung
		].filter[it === zugeinwirkung]
		return (schaltmittle.isNullOrEmpty || schaltmittle.map [
			IDAnforderung?.value
		].exists [
			!(it instanceof BUE_Einschaltung || it instanceof BUE_Ausschaltung)
		]) && area.contains(zugeinwirkung)
	}
}
