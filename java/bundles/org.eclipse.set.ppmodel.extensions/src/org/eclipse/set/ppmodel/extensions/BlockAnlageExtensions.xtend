/**
 * Copyright (c) 2015 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import org.eclipse.set.ppmodel.extensions.BasisObjektExtensions
import org.eclipse.set.toolboxmodel.Gleis.Gleis_Bezeichnung
import org.eclipse.set.toolboxmodel.Block.Block_Anlage

/**
 * Extensions for {@link Block_Anlage}.
 */
class BlockAnlageExtensions extends BasisObjektExtensions {
	/**
	 * @param blockAnlage this block anlage
	 * 
	 * @returns the Gleis_Bezeichnung
	 */
	def static Gleis_Bezeichnung getGleisBezeichnung(Block_Anlage blockAnlage) {
		return blockAnlage.IDGleisBezeichnung
	}
}
