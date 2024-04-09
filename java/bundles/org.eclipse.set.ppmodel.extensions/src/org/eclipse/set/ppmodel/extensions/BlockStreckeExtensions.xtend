/**
 * Copyright (c) 2015 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import org.eclipse.set.model.planpro.Block.Block_Strecke
import org.eclipse.set.model.planpro.Geodaten.Oertlichkeit
import org.eclipse.set.model.planpro.Geodaten.Strecke

/**
 * Extensions for {@link Block_Strecke}.
 */
class BlockStreckeExtensions extends BasisObjektExtensions {

	/**
	 * @param blockStrecke this block strecke
	 * 
	 * @returns the strecke
	 */
	def static Strecke getStrecke(Block_Strecke blockStrecke) {
		return blockStrecke.IDStrecke?.value
	}

	/**
	 * @param blockStrecke this block strecke
	 * 
	 * @returns the oertlichkeit
	 */
	def static Oertlichkeit getOertlichkeit(Block_Strecke blockStrecke) {
		return blockStrecke.IDBetriebsstelleNachbar?.value
	}

}
