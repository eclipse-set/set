/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions.exception

import org.eclipse.set.basis.graph.DirectedEdge
import de.scheidtbachmann.planpro.model.model1902.Basisobjekte.Punkt_Objekt_TOP_Kante_AttributeGroup
import de.scheidtbachmann.planpro.model.model1902.Geodaten.TOP_Kante
import de.scheidtbachmann.planpro.model.model1902.Geodaten.TOP_Knoten
import java.util.List

import static extension org.eclipse.set.ppmodel.extensions.utils.Debug.*

/**
 * Constructing a path run out of edges, but the end was not reached.
 */
class EndOfTopPathNotFound extends RuntimeException {

	/**
	 * @param start the start Punkt Objekt
	 * @param end the end Punkt Objekt
	 * @param path the constructed path
	 */
	new(Punkt_Objekt_TOP_Kante_AttributeGroup start,
		Punkt_Objekt_TOP_Kante_AttributeGroup end,
		List<DirectedEdge<TOP_Kante, TOP_Knoten, Punkt_Objekt_TOP_Kante_AttributeGroup>> path
	) {
		super(createMessage(start, end, path))
	}

	private static def createMessage(
		Punkt_Objekt_TOP_Kante_AttributeGroup start,
		Punkt_Objekt_TOP_Kante_AttributeGroup end,
		List<DirectedEdge<TOP_Kante, TOP_Knoten, Punkt_Objekt_TOP_Kante_AttributeGroup>> path
	) {
		return '''start=«start.debugString» end=«end.debugString» path=«path.debugString»'''
	}
}
