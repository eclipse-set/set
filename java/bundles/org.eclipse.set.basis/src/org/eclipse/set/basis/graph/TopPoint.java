/********************************************************************************
 * Copyright (c) 2024 DB InfraGO AG and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0 
 * 
 ********************************************************************************/
package org.eclipse.set.basis.graph;

import java.math.BigDecimal;

import org.eclipse.set.model.planpro.Basisobjekte.Punkt_Objekt;
import org.eclipse.set.model.planpro.Basisobjekte.Punkt_Objekt_TOP_Kante_AttributeGroup;
import org.eclipse.set.model.planpro.Geodaten.TOP_Kante;

/**
 * Helper record to indicate a point on a TOP_Kante (class instead of record due
 * to xtend limitations)
 */
public class TopPoint {
	private final TOP_Kante edge;
	private final BigDecimal distance;

	/**
	 * @return the distance from TOP_Kante.IDTOPKnotenA
	 */
	public BigDecimal distance() {
		return distance;
	}

	/**
	 * @return the TOP_Kante
	 */
	public TOP_Kante edge() {
		return edge;
	}

	/**
	 * @param edge
	 *            the TOP_Kante
	 * @param distance
	 *            the distance from TOP_Kante.IDTOPKnotenA
	 */
	public TopPoint(final TOP_Kante edge, final BigDecimal distance) {
		this.edge = edge;
		this.distance = distance;
	}

	/**
	 * @param po
	 *            a Punkt_Objekt
	 */
	public TopPoint(final Punkt_Objekt po) {
		this(po.getPunktObjektTOPKante().get(0));
	}

	/**
	 * @param potk
	 *            a Punkt_Objekt_TOP_Kante_AttributeGroup
	 */
	public TopPoint(final Punkt_Objekt_TOP_Kante_AttributeGroup potk) {
		this(potk.getIDTOPKante().getValue(), potk.getAbstand().getWert());
	}
}