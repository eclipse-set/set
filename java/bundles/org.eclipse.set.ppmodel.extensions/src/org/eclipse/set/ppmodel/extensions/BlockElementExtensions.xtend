/**
 * Copyright (c) 2015 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import java.util.Set
import org.eclipse.set.model.planpro.Bedienung.Bedien_Anzeige_Element
import org.eclipse.set.model.planpro.Block.Block_Anlage
import org.eclipse.set.model.planpro.Block.Block_Element
import org.eclipse.set.model.planpro.Block.Block_Strecke
import org.eclipse.set.model.planpro.Fahrstrasse.Fstr_Zug_Rangier
import org.eclipse.set.model.planpro.Ortung.FMA_Anlage
import org.eclipse.set.model.planpro.Ortung.FMA_Komponente
import org.eclipse.set.model.planpro.Ortung.Zugeinwirkung
import org.eclipse.set.model.planpro.Signale.Signal

import static extension org.eclipse.set.ppmodel.extensions.FahrwegExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.FmaAnlageExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.FstrZugRangierExtensions.*

/**
 * Extensions for {@link Block_Element}.
 */
class BlockElementExtensions extends BasisObjektExtensions {

	/**
	 * @param blockElement this block element
	 * 
	 * @returns the Block_Strecke
	 */
	def static Block_Strecke getBlockStrecke(Block_Element blockElement) {
		return blockElement.IDBlockStrecke?.value
	}

	/**
	 * @param blockElement this block element
	 * 
	 * @returns the Block_Strecke
	 */
	def static Set<Block_Anlage> getBlockAnlagenStart(
		Block_Element blockElement) {
		return blockElement.container.blockAnlage.filter [
			it.IDBlockElementA?.value.identitaet?.wert == blockElement.identitaet.wert
		].toSet;
	}

	/**
	 * @param blockElement this block element
	 * 
	 * @returns the Block_Strecke
	 */
	def static Set<Block_Anlage> getBlockAnlagenZiel(
		Block_Element blockElement) {
		return blockElement.container.blockAnlage.filter [
			it?.IDBlockElementB?.value?.identitaet?.wert ==
				blockElement.identitaet.wert
		].toSet;
	}

	/**
	 * @param blockElement this block element
	 * 
	 * @returns Name of the "Raeumungspruefung"
	 */
	def static String getRaeumungspruefung(Block_Element blockElement) {
		val schaltmittel = blockElement.IDRaeumungspruefung?.value

		if (schaltmittel !== null &&
			schaltmittel.IDSchalter?.value?.identitaet?.wert !== null) {

			try {
				val fmaAnlage = schaltmittel.IDSchalter?.value as FMA_Anlage
				if (fmaAnlage !== null) {
					return fmaAnlage.bzBezeichner
				}
			} catch (Exception e) {
				// nothing to do
			}
			try {
				val fmaKomponente = schaltmittel.IDSchalter?.value as FMA_Komponente
				if (fmaKomponente !== null) {
					return fmaKomponente.bezeichnung?.bezeichnungTabelle?.wert
				}
			} catch (Exception e) {
				// nothing to do
			}
			try {
				val zugeinwirkung = schaltmittel.IDSchalter?.value as Zugeinwirkung

				if (zugeinwirkung !== null) {
					return zugeinwirkung.bezeichnung?.bezeichnungTabelle?.wert
				}
			} catch (Exception e) {
				// nothing to do
			}

		}
		return ""
	}

	/**
	 * @param blockElement this block element
	 * 
	 * @returns the Bedien_Anzeige_Element
	 */
	def static Bedien_Anzeige_Element getBedienanzeigeElement(
		Block_Element blockElement) {
		return blockElement.IDZugschlussmeldung?.value
	}

	/**
	 * @param blockElement this block element
	 * 
	 * @returns the signal
	 */
	def static Signal getSignal(Block_Element blockElement) {

		try {
			for (Signal signal : blockElement.container.signal) {
				if (signal?.signalFstr?.IDRaZielErlaubnisabhaengig?.value?.identitaet?.
					wert == blockElement?.identitaet?.wert) {
					return signal
				}
			}
		} catch (Exception e) { // nothing to do
		}
		return null
	}

	/**
	 * @param blockElement this block element
	 * 
	 * @returns Fstr_Zug_Rangier
	 */
	def static Fstr_Zug_Rangier getFstrZugRangier(Block_Element blockElement) {
		try {
			for (Fstr_Zug_Rangier zugRangier : blockElement.container.
				fstrZugRangier) {
				if (zugRangier?.fstrFahrweg?.zielPunkt?.
					IDDWegErlaubnisabhaengig?.value?.identitaet?.wert ==
					blockElement?.identitaet?.wert) {
					return zugRangier
				}
			}
		} catch (Exception e) { // nothing to do
		}
		return null
	}
}
