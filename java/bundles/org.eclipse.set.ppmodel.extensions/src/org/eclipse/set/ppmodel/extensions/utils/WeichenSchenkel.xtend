/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions.utils

import org.eclipse.set.basis.graph.DirectedEdge
import org.eclipse.set.toolboxmodel.Basisobjekte.Punkt_Objekt_TOP_Kante_AttributeGroup
import org.eclipse.set.toolboxmodel.Geodaten.TOP_Kante
import org.eclipse.set.toolboxmodel.Geodaten.TOP_Knoten

/**
 * A {@link DirectedEdge} with a Lage.
 * 
 * @author Schaefer
 */
class WeichenSchenkel {

	/**
	 * The Lage of a Weichenschenkel.
	 */
	static enum Lage {
		L,
		R
	}

	/**
	 * the edge
	 */
	public DirectedEdge<TOP_Kante, TOP_Knoten, Punkt_Objekt_TOP_Kante_AttributeGroup> edge

	/**
	 * the Lage of this Weichenschenkel
	 */
	public Lage lage

	/**
	 * @param edge the edge
	 * @param lage the Lage of this Weichenschenkel 
	 */
	new(
		DirectedEdge<TOP_Kante, TOP_Knoten, Punkt_Objekt_TOP_Kante_AttributeGroup> edge,
		Lage lage) {
		this.edge = edge
		this.lage = lage
	}
}
