/**
 * Copyright (c) 2024 DB InfraGO AG and others
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 */
package org.eclipse.set.ppmodel.extensions

import org.eclipse.set.ppmodel.extensions.UrObjectExtensions
import org.eclipse.set.model.planpro.Balisentechnik_ETCS.ETCS_Signal
import org.eclipse.set.model.planpro.Balisentechnik_ETCS.RBC

import static extension org.eclipse.set.ppmodel.extensions.SignalExtensions.*
import org.eclipse.set.model.planpro.Signalbegriffe_Ril_301.Ne14

/**
 * Extensions for ETCS-Signal object
 */
class ETCSSignalExtensions extends UrObjectExtensions {
	static def boolean isBelongToRBCArea(RBC rbc, ETCS_Signal etcsSignal) {
		if (etcsSignal.IDRBC.exists[value !== null && value === rbc]) {
			return true
		}
		val refSignal = etcsSignal?.IDSignal?.value
		val etcsKanteBelongToRBC = rbc.container.ETCSKante.filter [ edge |
			edge.IDRBC.exists[value !== null && value === rbc]
		].toSet
		if (!refSignal.hasSignalbegriffID(Ne14) ||
			etcsKanteBelongToRBC.nullOrEmpty) {
			return false
		}

		return etcsKanteBelongToRBC.flatMap [
			IDTOPKante.map[idTopKante|idTopKante.value]
		].filterNull.exists [
			refSignal.topKanten.exists[signalTopKante|signalTopKante === it]
		]
	}
}
