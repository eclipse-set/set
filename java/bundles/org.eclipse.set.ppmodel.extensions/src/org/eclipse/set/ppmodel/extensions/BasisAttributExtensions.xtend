/**
 * Copyright (c) 2017 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import org.eclipse.set.toolboxmodel.BasisTypen.BasisAttribut_AttributeGroup
import org.eclipse.set.toolboxmodel.Basisobjekte.Bearbeitungsvermerk
import org.eclipse.set.toolboxmodel.PlanPro.Container_AttributeGroup
import org.eclipse.set.toolboxmodel.PlanPro.LST_Zustand
import org.eclipse.set.toolboxmodel.PlanPro.impl.Ausgabe_FachdatenImpl
import org.eclipse.set.toolboxmodel.PlanPro.impl.Fachdaten_AttributeGroupImpl
import org.eclipse.set.toolboxmodel.PlanPro.impl.LST_ZustandImpl
import java.util.List
import org.eclipse.emf.ecore.EObject
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup

/**
 * Extensions for {@link BasisAttribut_AttributeGroup}.
 * 
 * @author Schaefer
 */
class BasisAttributExtensions {
	def static Container_AttributeGroup getLocalContainer(EObject object) {
		if (object === null) {
			throw new NullPointerException("contained object must not be null")
		}
		return getLocalContainerDispatch(object)
	}

	def static dispatch private Container_AttributeGroup getLocalContainerDispatch(
		Void object
	) {
		return null
	}

	def static dispatch private Container_AttributeGroup getLocalContainerDispatch(
		EObject object
	) {
		return getLocalContainerDispatch(object.eContainer)
	}

	def static dispatch private Container_AttributeGroup getLocalContainerDispatch(
		Container_AttributeGroup object
	) {
		return object
	}

	def static dispatch private Container_AttributeGroup getLocalContainerDispatch(
		LST_Zustand object
	) {
		return object.container
	}

	def static MultiContainer_AttributeGroup getContainer(EObject object) {
		if (object === null) {
			throw new NullPointerException("contained object must not be null")
		}

		// Find nearest LST_ZustandImpl
		var container = object
		while (container !== null && !(container instanceof LST_ZustandImpl)) {
			container = container.eContainer
		}

		if (container === null) {
			throw new RuntimeException("unable to find containing LST_Zustand")
		}

		if (container.eContainer === null ||
			!(container.eContainer instanceof Ausgabe_FachdatenImpl)) {
			// If this LST_Zustand is not contained within a Ausgabe_Fachdaten, then 
			// other LST_Zustand instances of the same kind (current/planned) cannot 
			// be determined. 
			// Therefore just create a MultiContainer which only contains this single container
			return new MultiContainer_AttributeGroup(
				#[(container as LST_Zustand).container])
		}
		val ausgabeFachdaten = container.eContainer as Ausgabe_FachdatenImpl
		// Check whether object is in the start or target LST_ZustandImpl 
		val ausgabeFachdatenContainer = ausgabeFachdaten.
			eContainer as Fachdaten_AttributeGroupImpl
		if (container === ausgabeFachdaten.LSTZustandStart) {
			return new MultiContainer_AttributeGroup(ausgabeFachdatenContainer.
				ausgabeFachdaten.map[LSTZustandStart.container])
		} else if (container === ausgabeFachdaten.LSTZustandZiel) {
			return new MultiContainer_AttributeGroup(ausgabeFachdatenContainer.
				ausgabeFachdaten.map[LSTZustandZiel.container])
		}

		throw new RuntimeException("unable to find LST_ZustandImpl container")
	}

	/**
	 * @param befestigung the Signalbefestigung
	 * 
	 * @returns the list of Bearbeitungsvermerke
	 */
	def static List<Bearbeitungsvermerk> getBearbeitungsvermerke(
		BasisAttribut_AttributeGroup basisAttribut
	) {
		return basisAttribut.IDBearbeitungsvermerk
	}

}
