/**
 * Copyright (c) 2016 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import org.eclipse.set.model.planpro.Ansteuerung_Element.Unterbringung
import org.eclipse.set.model.planpro.PZB.PZB_Element
import org.eclipse.set.model.planpro.PZB.PZB_Element_Zuordnung
import org.eclipse.set.model.planpro.Basisobjekte.Basis_Objekt
import java.util.List
import org.eclipse.set.model.planpro.Signale.Signal
import org.eclipse.set.model.planpro.Fahrstrasse.Fstr_DWeg

import static extension org.eclipse.set.ppmodel.extensions.FstrFahrwegExtensions.getFstrDweg
import org.eclipse.set.model.planpro.PZB.PZB_Element_Zuordnung_BP_AttributeGroup
import org.eclipse.set.model.planpro.PZB.PZB_Element_Zuordnung_Fstr_AttributeGroup
import org.eclipse.set.model.planpro.PZB.PZB_Zuordnung_Signal

/**
 * Extensions for {@link PZB_Element}.
 */
class PZBElementExtensions extends BasisObjektExtensions {

	/**
	 * @param pzb this pzb
	 * 
	 * @return the PZB_Element_Zuordnung
	 */
	def static PZB_Element_Zuordnung getPZBElementZuordnung(PZB_Element pzb) {
		return pzb.IDPZBElementZuordnung?.value
	}

	/**
	 * @param pzb this pzb
	 * 
	 * @return the Unterbringung
	 */
	def static Unterbringung getUnterbringung(PZB_Element pzb) {
		return pzb.IDUnterbringung?.value
	}

	def static List<Basis_Objekt> getPZBElementBezugspunkt(PZB_Element pzb) {
		return pzb.IDPZBElementZuordnung?.value?.PZBElementZuordnungBP?.map [
			IDPZBElementBezugspunkt?.value
		]
	}

	def static Iterable<Fstr_DWeg> getFstrDWegs(PZB_Element pzb) {
		val bezugspunkts = pzb.PZBElementBezugspunkt?.filter(Signal)
		val fstrFahrwegs = pzb.container.fstrFahrweg.filter [ fstrFarhweg |
			bezugspunkts.exists[it === fstrFarhweg.IDStart?.value]
		]
		return fstrFahrwegs.map[fstrDweg].flatten
	}

	def static Iterable<PZB_Element_Zuordnung_BP_AttributeGroup> getPZBElementZuordnungBP(
		PZB_Element pzb) {
		return pzb.IDPZBElementZuordnung?.value?.PZBElementZuordnungBP
	}

	def static Iterable<PZB_Element_Zuordnung_Fstr_AttributeGroup> getPZBElementZuordnungFstr(
		PZB_Element pzb) {
		return pzb.IDPZBElementZuordnung?.value?.PZBElementZuordnungFstr
	}

	def static Iterable<PZB_Zuordnung_Signal> getPZBZuordnungSignal(
		PZB_Element pzb) {
		return pzb.container.PZBZuordnungSignal.filter [
			IDPZBElementZuordnung === pzb.PZBElementZuordnung
		]
	}
}
