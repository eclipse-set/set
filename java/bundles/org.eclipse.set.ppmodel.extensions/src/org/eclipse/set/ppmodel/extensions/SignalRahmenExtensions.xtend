/**
 * Copyright (c) 2015 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import com.google.common.collect.Sets
import org.eclipse.set.model.planpro.Basisobjekte.Basis_Objekt
import org.eclipse.set.model.planpro.Signale.Signal
import org.eclipse.set.model.planpro.Signale.Signal_Befestigung
import org.eclipse.set.model.planpro.Signale.Signal_Rahmen
import org.eclipse.set.model.planpro.Signale.Signal_Signalbegriff
import java.util.Iterator
import java.util.List
import java.util.NoSuchElementException
import java.util.Set
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import static org.eclipse.set.model.planpro.Signale.ENUMBefestigungArt.*

import static extension org.eclipse.set.ppmodel.extensions.SignalBefestigungExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.SignalbegriffExtensions.*

/**
 * This class extends {@link Signal_Rahmen}.
 */
class SignalRahmenExtensions extends BasisObjektExtensions {

	static final Logger LOGGER = LoggerFactory.getLogger(
		typeof(SignalRahmenExtensions));

	private static class SignalBefestigungIterator implements Iterator<Signal_Befestigung> {

		Basis_Objekt lastBasisObjekt
		Basis_Objekt nextBasisObjekt
		Set<Basis_Objekt> visited = Sets.newHashSet

		new(Signal_Rahmen rahmen) {
			lastBasisObjekt = rahmen
			visit(rahmen)
		}

		def void visit(Basis_Objekt object) {
			if (visited.contains(object)) {
				throw new IllegalArgumentException('''«object» already visited''')
			}
			visited.add(object)
		}

		override hasNext() {
			nextBasisObjekt = lastBasisObjekt.findNextBefestigung
			return nextBasisObjekt !== null &&
				nextBasisObjekt !== lastBasisObjekt
		}

		private static def dispatch Basis_Objekt findNextBefestigung(
			Basis_Objekt objekt) {
			throw new IllegalArgumentException(objekt.class.simpleName)
		}

		private static def dispatch Basis_Objekt findNextBefestigung(
			Signal_Rahmen rahmen) {
			if (rahmen.IDSignalBefestigung === null) {
				return null
			}
			return rahmen.signalBefestigung
		}

		private static def dispatch Basis_Objekt findNextBefestigung(
			Signal_Befestigung befestigung) {
			if (befestigung.IDSignalBefestigung === null) {
				return null
			}
			return befestigung.signalBefestigung
		}

		override next() {
			if (hasNext) {
				lastBasisObjekt = nextBasisObjekt
				visit(nextBasisObjekt)
				return lastBasisObjekt as Signal_Befestigung
			}
			throw new NoSuchElementException
		}

		override remove() {
			throw new UnsupportedOperationException
		}
	}

	/**
	 * @param signalRahmen this Signalrahmen
	 * 
	 * @returns list of Signalbegriffe
	 */
	def static List<Signal_Signalbegriff> getSignalbegriffe(
		Signal_Rahmen signalRahmen
	) {
		return signalRahmen.container.signalSignalbegriff.filter [ b |
			b.signalRahmen == signalRahmen
		].toList
	}

	/**
	 * @param signalRahmen this Signalrahmen
	 * 
	 * @returns the Signal for this Signalrahmen
	 */
	def static Signal getSignal(Signal_Rahmen signalRahmen) {
		return signalRahmen.IDSignal?.value
	}

	/**
	 * @param signalRahmen this Signalrahmen
	 * 
	 * @returns the Signal for this Signalrahmen
	 */
	def static Signal getSignalNachordnung(Signal_Rahmen signalRahmen) {
		return signalRahmen.IDSignalNachordnung?.value
	}

	/**
	 * @param signalRahmen this Signalrahmen
	 * 
	 * @returns the Signalbefestigung for this Signalrahmen
	 */
	def static Signal_Befestigung getSignalBefestigung(
		Signal_Rahmen signalRahmen) {
		return signalRahmen.IDSignalBefestigung?.value
	}

	/**
	 * @param signalRahmen this Signalrahmen
	 * 
	 * @returns the Fundament for this Signalrahmen
	 */
	def static Signal_Befestigung getFundament(Signal_Rahmen signalRahmen) {
		LOGGER.debug("getFundament started...")
		val befestigung = signalRahmen?.signalBefestigungIterator?.findFirst [
			signalBefestigungAllg?.befestigungArt?.wert ==
				ENUM_BEFESTIGUNG_ART_FUNDAMENT
		]
		LOGGER.debug("getFundament finished.")
		return befestigung
	}

	/**
	 * @param signalRahmen this Signalrahmen
	 * 
	 * @returns iterator for chain of Signalbefestigungen
	 */
	def static Iterator<Signal_Befestigung> getSignalBefestigungIterator(
		Signal_Rahmen signalRahmen) {
		return new SignalBefestigungIterator(signalRahmen)
	}
}
