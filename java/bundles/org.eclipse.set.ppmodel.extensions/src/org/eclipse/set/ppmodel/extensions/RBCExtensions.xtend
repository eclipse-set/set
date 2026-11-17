/**
 * Copyright (c) 2026 DB InfraGO AG and others
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
import org.eclipse.set.model.planpro.Basisobjekte.Ur_Objekt
import org.eclipse.set.model.planpro.Balisentechnik_ETCS.RBC
import org.eclipse.set.model.planpro.Balisentechnik_ETCS.Datenpunkt
import org.eclipse.set.model.planpro.Balisentechnik_ETCS.ETCS_Signal
import java.util.List
import org.eclipse.set.model.planpro.Balisentechnik_ETCS.ETCS_W_Kr

/**
 * Extensions for RBC area
 */
class RBCExtensions extends UrObjectExtensions {
	def static boolean isBelongToRBCAreas(List<RBC> rbcs, Ur_Objekt obj) {
		return rbcs.exists[isBelongToRBCArea(obj)]
}
	
	def static dispatch boolean isBelongToRBCArea(RBC rbc, Ur_Objekt obj) {
		throw new IllegalArgumentException('''Expected ETCS LST-Object, actual: «obj.class.name»''')
	}

	def static dispatch boolean isBelongToRBCArea(RBC rbc, Datenpunkt dp) {
		return DatenpunktExtensions.isBelongToRBCArea(rbc, dp)
	}

	def static dispatch boolean isBelongToRBCArea(RBC rbc,
		ETCS_Signal etcsSignal) {
		return ETCSSignalExtensions.isBelongToRBCArea(rbc, etcsSignal)
	}
	
	def static dispatch boolean isBelongToRBCArea(RBC rbc, ETCS_W_Kr etcsWKr) {
		// TODO
		 return true
	}
}
