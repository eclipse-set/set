/**
 * Copyright (c) 2022 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.plazmodel.check

import java.util.Map
import org.eclipse.set.basis.IModelSession
import org.eclipse.set.model.plazmodel.PlazFactory
import org.eclipse.set.ppmodel.extensions.PlanProSchnittstelleExtensions
import org.osgi.service.component.annotations.Component

import static extension org.eclipse.set.ppmodel.extensions.PlanungEinzelExtensions.*

/**
 * Checks if GUIDs of LST states are unique.
 * The LST states in a Subwork can have same GUID
 */
@Component(immediate=true)
class GuidStateUnique implements PlazCheck {

	override run(IModelSession modelSession) {
		val planungGruppe = PlanProSchnittstelleExtensions.getLSTPlanungGruppe(
			modelSession.planProSchnittstelle)
		if (planungGruppe.present) {
			// Grouped the LST states by GUID
			val sameGUIDStates = planungGruppe.get.flatMap [
				#[LSTPlanungEinzel?.LSTZustandStart,
					LSTPlanungEinzel?.LSTZustandZiel]
			].groupBy[identitaet?.wert]
			// Filter the same guid states, which states empty or the states in same subwork 
			return sameGUIDStates.filter [ guid, states |
				!states.nullOrEmpty && (states.size > 2 || states.map [
					eContainer
				].toSet.size > 1)
			].values.flatten.map [
				val err = PlazFactory.eINSTANCE.createPlazError
				err.message = transformErrorMsg(
					Map.of("GUID", identitaet?.wert))
				err.type = checkType
				err.object = identitaet
				return err
			].toList
		}
		return #[]
	}

	override checkType() {
		return "GUID-Zustände"
	}

	override getDescription() {
		return "Die GUIDs aller Zustände sind eindeutig."
	}

	override getGeneralErrMsg() {
		return "Die GUID des Zustands {GUID} ist nicht eindeutig!"
	}
}
