/**
 * Copyright (c) 2019 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import org.eclipse.set.model.planpro.PlanPro.Ausgabe_Fachdaten
import org.eclipse.set.model.planpro.PlanPro.LST_Planung_AttributeGroup
import org.eclipse.set.model.planpro.PlanPro.LST_Zustand
import org.eclipse.set.model.planpro.PlanPro.PlanProPackage
import org.eclipse.set.model.planpro.PlanPro.Planung_Einzel
import org.eclipse.set.basis.exceptions.IllegalReference
import org.eclipse.emf.ecore.EObject

/** 
 * Extensions for {@link Planung_Einzel}.
 * 
 * @author Schaefer
 */
class PlanungEinzelExtensions {

	/**
	 *  @param einzelplanung the Planung Einzel
	 * 
	 *  @return the Zustand Start of the Ausgabe Fachdaten of this Einzelplanung
	 */
	static def LST_Zustand LSTZustandStart(Planung_Einzel einzelplanung) {
		return einzelplanung.ausgabeFachdaten.LSTZustandStart
	}

	/**
	 *  @param einzelplanung the Planung Einzel
	 * 
	 *  @return the Zustand Ziel of the Ausgabe Fachdaten of this Einzelplanung
	 */
	static def LST_Zustand LSTZustandZiel(Planung_Einzel einzelplanung) {
		return einzelplanung.ausgabeFachdaten.LSTZustandZiel
	}

	/**
	 *  @param einzelplanung the Planung Einzel
	 * 
	 *  @return the Ausgabe Fachdaten of this Einzelplanung
	 */
	static def Ausgabe_Fachdaten getAusgabeFachdaten(
		Planung_Einzel einzelplanung
	) {
		val result = einzelplanung.LSTPlanung.fachdaten.ausgabeFachdaten.filter [
			identitaet?.wert !== null &&
				identitaet?.wert == einzelplanung?.IDAusgabeFachdaten?.value?.identitaet?.wert
		]
		if (result.size !== 1) {
			throw new IllegalReference(
				einzelplanung,
				PlanProPackage.eINSTANCE.planung_Einzel_IDAusgabeFachdaten
			)
		}
		return result.get(0)
	}

	/**
	 *  @param einzelplanung the Planung Einzel
	 * 
	 *  @return the LST Planung containing this Einzelplanung
	 */
	static def LST_Planung_AttributeGroup LSTPlanung(
		Planung_Einzel einzelplanung
	) {
		return einzelplanung.LSTPlanungDispatch
	}

	private static def dispatch LST_Planung_AttributeGroup LSTPlanungDispatch(
		EObject object
	) {
		return object.eContainer.LSTPlanungDispatch
	}

	private static def dispatch LST_Planung_AttributeGroup LSTPlanungDispatch(
		Void object
	) {
		return null
	}

	private static def dispatch LST_Planung_AttributeGroup LSTPlanungDispatch(
		LST_Planung_AttributeGroup lstPlanung
	) {
		return lstPlanung
	}
}
