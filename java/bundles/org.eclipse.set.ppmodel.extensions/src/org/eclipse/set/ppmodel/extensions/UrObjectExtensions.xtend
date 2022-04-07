/**
 * Copyright (c) 2015 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import org.eclipse.set.toolboxmodel.Basisobjekte.Ur_Objekt
import org.eclipse.emf.ecore.EObject
import org.eclipse.set.toolboxmodel.PlanPro.LST_Zustand

/**
 * Diese Klasse erweitert {@link Ur_Objekt}.
 */
class UrObjectExtensions extends BasisAttributExtensions {

	/**
	 * @param object this object
	 * 
	 * @return the type name of the object
	 */
	def static String getTypeName(EObject object) {
		val interfaces = object.class.interfaces
		return '''«FOR i : interfaces»«i.simpleName»«ENDFOR»'''
	}
	
	/**
	 * Returns the LST_Zustand this Ur_Objekt is contained within
	 * 
	 * @param object this object
	 * @return the LST_Zustand or null
	 */
	def static LST_Zustand getLSTZustand(Ur_Objekt object) {
		var EObject container = object
		while(container !== null && !(container instanceof LST_Zustand))
		{
			container = container.eContainer
		}
		return container as LST_Zustand
	}
}
