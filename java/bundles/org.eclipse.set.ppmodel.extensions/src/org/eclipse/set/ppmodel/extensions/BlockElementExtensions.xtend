/**
 * Copyright (c) 2015 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import de.scheidtbachmann.planpro.model.model1902.Block.Block_Element
import de.scheidtbachmann.planpro.model.model1902.Block.Block_Strecke
import de.scheidtbachmann.planpro.model.model1902.Block.Block_Anlage
import java.util.Set
import de.scheidtbachmann.planpro.model.model1902.Bedienung.Bedien_Anzeige_Element
import de.scheidtbachmann.planpro.model.model1902.Signale.Signal
import de.scheidtbachmann.planpro.model.model1902.Fahrstrasse.Fstr_Zug_Rangier
import static extension org.eclipse.set.ppmodel.extensions.FstrZugRangierExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.FahrwegExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.FmaAnlageExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.ZeigerExtensions.*
import de.scheidtbachmann.planpro.model.model1902.Ortung.Zugeinwirkung
import de.scheidtbachmann.planpro.model.model1902.Ortung.FMA_Komponente
import de.scheidtbachmann.planpro.model.model1902.Ortung.FMA_Anlage
import de.scheidtbachmann.planpro.model.model1902.Ortung.Schaltmittel_Zuordnung

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
		return blockElement.IDBlockStrecke.resolve(Block_Strecke)
	}

	/**
	 * @param blockElement this block element
	 * 
	 * @returns the Block_Strecke
	 */
	def static Set<Block_Anlage> getBlockAnlagenStart(
		Block_Element blockElement) {
		return blockElement.container.blockAnlage.filter [
			it.IDBlockElementA.wert == blockElement.identitaet.wert
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
			it?.IDBlockElementB?.wert == blockElement.identitaet.wert
		].toSet;
	}

	/**
	 * @param blockElement this block element
	 * 
	 * @returns Name of the "Raeumungspruefung"
	 */
	def static String getRaeumungspruefung(Block_Element blockElement) {
		val schaltmittel = blockElement.IDRaeumungspruefung.resolve(
			Schaltmittel_Zuordnung)

		if (schaltmittel !== null && schaltmittel.IDSchalter.wert !== null) {

			try {
				val fmaAnlage = schaltmittel.IDSchalter.resolve(FMA_Anlage)
				if (fmaAnlage !== null) {
					return fmaAnlage.bzBezeichner
				}
			} catch (Exception e) {
				// nothing to do
			}
			try {
				val fmaKomponente = schaltmittel.IDSchalter.resolve(
					FMA_Komponente)
				if (fmaKomponente !== null) {
					return fmaKomponente.bezeichnung?.bezeichnungTabelle?.wert
				}
			} catch (Exception e) {
				// nothing to do
			}
			try {
				val zugeinwirkung = schaltmittel.IDSchalter.resolve(
					Zugeinwirkung)

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
		return blockElement.IDZugschlussmeldung.resolve(Bedien_Anzeige_Element)
	}

	/**
	 * @param blockElement this block element
	 * 
	 * @returns the signal
	 */
	def static Signal getSignal(Block_Element blockElement) {

		try {
			for (Signal signal : blockElement.container.signal) {
				if (signal?.signalFstr?.IDRaZielErlaubnisabhaengig?.wert ==
					blockElement?.identitaet?.wert) {
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
					IDDWegErlaubnisabhaengig?.wert ==
					blockElement?.identitaet?.wert) {
					return zugRangier
				}
			}
		} catch (Exception e) { // nothing to do
		}
		return null
	}
}
