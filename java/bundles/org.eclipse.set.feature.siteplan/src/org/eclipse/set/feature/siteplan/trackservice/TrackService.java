/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.siteplan.trackservice;

import java.util.List;

import org.eclipse.set.model.planpro.BasisTypen.ENUMWirkrichtung;
import org.eclipse.set.model.planpro.Basisobjekte.Punkt_Objekt;
import org.eclipse.set.model.planpro.Basisobjekte.Punkt_Objekt_TOP_Kante_AttributeGroup;
import org.eclipse.set.model.planpro.Geodaten.Strecke;
import org.eclipse.set.model.planpro.Geodaten.TOP_Kante;
import org.eclipse.set.model.planpro.Geodaten.TOP_Knoten;
import org.eclipse.xtext.xbase.lib.Pair;

/**
 * Interface for a service providing additional metadata for locating objects on
 * (or relative to) tracks
 * 
 * @author Stuecker
 *
 */
public interface TrackService {

	/**
	 * @param punktObjekt
	 *            the punkt objekt
	 * @return the coordinate at punkt objekt
	 */
	GEOKanteCoordinate getCoordinate(Punkt_Objekt punktObjekt);

	/**
	 * @param punktObjekt
	 *            the punkt objekt
	 * @param distance
	 *            the distance from the punktObjekt
	 * @return the coordinate at punkt objekt with the given distance as offset
	 */
	GEOKanteCoordinate getCoordinateAt(Punkt_Objekt punktObjekt,
			double distance);

	/**
	 * @param singlePoint
	 *            the punkt objekt top kante
	 * @param distance
	 *            the distance from the punktObjekt
	 * @return the coordinate at punkt objekt with the given distance as offset
	 */
	GEOKanteCoordinate getCoordinateAt(
			Punkt_Objekt_TOP_Kante_AttributeGroup singlePoint, double distance);

	/**
	 * @param topKante
	 *            the TOP_Kante to consider
	 * @param start
	 *            the start of the TOP_Kante from which to measure the distance
	 * @param distance
	 *            the distance from the start of the TOP_Kante
	 * @param lateralDistance
	 *            the lateral distance from the track
	 * @param wirkrichtung
	 *            the direction on the track
	 * @return the coordinate
	 */
	GEOKanteCoordinate getCoordinate(TOP_Kante topKante, TOP_Knoten start,
			double distance, double lateralDistance,
			ENUMWirkrichtung wirkrichtung);

	/**
	 * @param topKante
	 *            the TOP_Kante to consider
	 * @param topKnoten
	 *            the start of the TOP_Kante from which to measure the distance
	 * @param distance
	 *            the distance from the start of the TOP_Kante
	 * @return the GEO_Kante with metadata at the specified distance
	 */
	GEOKanteMetadata getGeoKanteAt(TOP_Kante topKante, TOP_Knoten topKnoten,
			double distance);

	/**
	 * @param topKante
	 *            the TOP_Kante to consider
	 * @return a list of GEO_Kanten with metadata for the given TOP_Kante
	 */
	List<GEOKanteMetadata> getGeoKanten(TOP_Kante topKante);

	/**
	 * @param strecke
	 *            a Stracke
	 * @return a list of GEOKanteCoordinates spaced according to the kilometer
	 *         marker distance
	 */
	List<Pair<GEOKanteCoordinate, Double>> getStreckeKilometers(
			Strecke strecke);

	/**
	 * Remove cache
	 */
	void clearMetaData();

}
