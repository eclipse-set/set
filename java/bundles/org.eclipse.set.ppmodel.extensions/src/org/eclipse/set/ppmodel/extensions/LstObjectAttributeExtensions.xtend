/**
 * Copyright (c) 2019 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import org.eclipse.set.toolboxmodel.BasisTypen.BasisAttribut_AttributeGroup
import org.eclipse.set.toolboxmodel.Basisobjekte.Bearbeitungsvermerk
import java.util.List
import org.eclipse.set.ppmodel.extensions.utils.LstObjectAttribute

import static extension org.eclipse.set.ppmodel.extensions.BasisAttributExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.BasisObjektExtensions.*
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup
import static extension org.eclipse.set.ppmodel.extensions.ZeigerExtensions.*

/**
 * This class extends {@link LstObjectAttribute}.
 * 
 * @author Schaefer
 */
class LstObjectAttributeExtensions {

	/**
	 * @param objectAttribute this LST object/attribute
	 * 
	 * @return the comments of this LST object/attribute
	 */
	static def List<Bearbeitungsvermerk> getComments(
		LstObjectAttribute objectAttribute
	) {
		if (objectAttribute.attribute !== null) {
			return objectAttribute.attribute.getComments(
				objectAttribute.object.container)
		}
		return objectAttribute.object.comments
	}

	private static def List<Bearbeitungsvermerk> getComments(
		BasisAttribut_AttributeGroup attribute,
		MultiContainer_AttributeGroup container
	) {
		return attribute.IDBearbeitungsvermerk.map [
			resolve(Bearbeitungsvermerk)
		]
	}
}
