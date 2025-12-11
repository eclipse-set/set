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
import org.eclipse.set.model.planpro.Ansteuerung_Element.Stellelement
import org.eclipse.set.model.planpro.Ansteuerung_Element.Unterbringung
import org.eclipse.set.model.planpro.Basisobjekte.Basis_Objekt
import org.eclipse.set.model.planpro.Fahrstrasse.Fstr_DWeg
import org.eclipse.set.model.planpro.PZB.PZB_Element
import org.eclipse.set.model.planpro.PZB.PZB_Element_Zuordnung
import org.eclipse.set.model.planpro.PZB.PZB_Element_Zuordnung_BP_AttributeGroup
import org.eclipse.set.model.planpro.PZB.PZB_Element_Zuordnung_Fstr_AttributeGroup
import org.eclipse.set.model.planpro.PZB.PZB_Zuordnung_Signal
import org.eclipse.set.model.planpro.Signale.Signal
import org.eclipse.set.model.planpro.Weichen_und_Gleissperren.W_Kr_Gsp_Element

import static extension org.eclipse.set.ppmodel.extensions.AussenelementansteuerungExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.BereichObjektExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.FstrFahrwegExtensions.getFstrDweg

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
		] ?: emptyList
	}

	def static Iterable<Fstr_DWeg> getFstrDWegs(PZB_Element pzb) {
		val bezugspunkts = pzb.PZBElementBezugspunkt?.filter(Signal)
		val fstrFahrwegs = pzb.container.fstrFahrweg.filter [ fstrFahrweg |
			(bezugspunkts ?: #[]).filterNull.exists [
				it === fstrFahrweg?.IDStart?.value
			]
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
			IDPZBElementZuordnung?.value === pzb.PZBElementZuordnung
		]
	}

	def static boolean isBelongToControlArea(PZB_Element pzb,
		Stell_Bereich controlArea) {
		val signals = pzb?.PZBElementBezugspunkt?.filter(Signal)?.filter [
			signalReal !== null && signalReal.signalRealAktiv === null
		]
		return !signals.nullOrEmpty && !pzb?.stellelements?.filterNull?.filter [
			IDInformation?.value.isBelongToControlArea(controlArea)
		].nullOrEmpty && signals.exists[controlArea.contains(it)]
	}

	def static Iterable<Stellelement> getStellelements(PZB_Element pzb) {
		return pzb?.PZBElementBezugspunkt?.map[stellelement]
	}

	private def static dispatch Stellelement getStellelement(
		Basis_Objekt object) {
		throw new IllegalArgumentException()
	}

	private def static dispatch Stellelement getStellelement(Signal object) {
		return SignalExtensions.getStellelement(object)
	}

	private def static dispatch Stellelement getStellelement(
		W_Kr_Gsp_Element object) {
		return object.IDStellelement?.value
	}
}
