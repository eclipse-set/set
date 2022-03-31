/**
 * Copyright (c) 2015 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import com.google.common.collect.Lists
import de.scheidtbachmann.planpro.model.model1902.BasisTypen.BasisAttribut_AttributeGroup
import de.scheidtbachmann.planpro.model.model1902.Basisobjekte.Basis_Objekt
import de.scheidtbachmann.planpro.model.model1902.Basisobjekte.Bearbeitungsvermerk
import java.util.List
import org.eclipse.set.ppmodel.extensions.utils.LstObjectAttribute

import static extension org.eclipse.set.ppmodel.extensions.ZeigerExtensions.*

/**
 * Diese Klasse erweitert {@link Basis_Objekt}.
 */
class BasisObjektExtensions extends UrObjectExtensions {

	/**
	 * @param object dieses Basis-Objekt
	 * 
	 * @returns Liste der Bearbeitungsvermerke
	 */
	def static List<Bearbeitungsvermerk> comments(Basis_Objekt object) {
		return object.IDBearbeitungsvermerk.map [
			resolve(Bearbeitungsvermerk)
		]
	}

	/**
	 * @param object this {@link Basis_Objekt}
	 * 
	 * @return list of all LST object/attributes
	 */
	def static List<LstObjectAttribute> getObjectAttributes(
		Basis_Objekt object
	) {
		val List<LstObjectAttribute> result = Lists.newLinkedList

		// first we list this object without an attribute
		result.add(new LstObjectAttribute(object))

		// then we add a combination for each contained Basis_Attribut
		object.eAllContents.filter(BasisAttribut_AttributeGroup).forEach [
			result.add(new LstObjectAttribute(object, it))
		]

		return result
	}
}
