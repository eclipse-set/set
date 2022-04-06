/**
 * Copyright (c) 2019 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions.utils

import org.eclipse.set.toolboxmodel.BasisTypen.BasisAttribut_AttributeGroup
import org.eclipse.set.toolboxmodel.Basisobjekte.Basis_Objekt
import org.eclipse.xtend.lib.annotations.Accessors

/**
 * An LST object/attribute combination.
 * 
 * @author schaefer
 */
class LstObjectAttribute {

	new(Basis_Objekt object) {
		this.object = object
	}

	new(Basis_Objekt object, BasisAttribut_AttributeGroup attribute) {
		this.object = object
		this.attribute = attribute
	}

	@Accessors
	Basis_Objekt object

	@Accessors
	BasisAttribut_AttributeGroup attribute
}
