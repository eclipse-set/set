/**
 * Copyright (c) 2024 DB InfraGO AG and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 */
package org.eclipse.set.core.services.geometry;

import java.math.BigDecimal;

import org.eclipse.set.basis.Pair;
import org.eclipse.set.basis.geometry.GEOKanteCoordinate;
import org.eclipse.set.model.planpro.Basisobjekte.Punkt_Objekt;
import org.eclipse.set.model.planpro.Basisobjekte.Punkt_Objekt_TOP_Kante_AttributeGroup;
import org.eclipse.set.model.planpro.Geodaten.Strecke;

/**
 * 
 * 
 */
public interface PointObjectPositionService {
	/**
	 * @param punktObjekt
	 *            the punkt objekt
	 * @return the coordinate at punkt objekt
	 */
	GEOKanteCoordinate getCoordinate(Punkt_Objekt punktObjekt);

	/**
	 * @param potk
	 *            the punkt objekt top kante
	 * @return the coordinate at punkt objekt
	 */
	GEOKanteCoordinate getCoordinate(
			Punkt_Objekt_TOP_Kante_AttributeGroup potk);

	/**
	 * @param potk
	 *            the punkt objekt top kante
	 * @param lateralDistance
	 *            the lataral distance of potk
	 * @return the coordinate at punkt objekt
	 */
	GEOKanteCoordinate getCoordinate(Punkt_Objekt_TOP_Kante_AttributeGroup potk,
			BigDecimal lateralDistance);

	/**
	 * @param punktObjekt
	 *            the punkt objekt
	 * @param strecke
	 *            the strecke
	 * @return the coordinate of projection on Strecke and topological distance
	 *         to start of Strecke
	 */
	Pair<GEOKanteCoordinate, BigDecimal> getProjectionOnStreck(
			Punkt_Objekt punktObjekt, Strecke strecke);
}
