/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.siteplan.trackservice;

import java.util.Set;

import org.eclipse.set.ppmodel.extensions.utils.GeoPosition;
import org.eclipse.set.model.planpro.Basisobjekte.Bereich_Objekt;
import org.eclipse.set.model.planpro.Geodaten.ENUMGEOKoordinatensystem;
import org.locationtech.jts.geom.Coordinate;

/**
 * A coordinate on a GEO_Kante including information of Bereich_Objekte covering
 * the coordinate
 * 
 * @author Stuecker
 *
 */
public class GEOKanteCoordinate {
	private final Set<Bereich_Objekt> bereichObjekte;
	private final GeoPosition position;
	private final ENUMGEOKoordinatensystem crs;

	GEOKanteCoordinate(final GeoPosition position,
			final Set<Bereich_Objekt> bereichObjekte,
			final ENUMGEOKoordinatensystem crs) {
		this.position = position;
		this.bereichObjekte = bereichObjekte;
		this.crs = crs;
	}

	GEOKanteCoordinate(final Coordinate coordinate,
			final Set<Bereich_Objekt> bereichObjekte,
			final ENUMGEOKoordinatensystem crs) {
		this.position = new GeoPosition(coordinate, 0, 0);
		this.bereichObjekte = bereichObjekte;
		this.crs = crs;
	}

	/**
	 * @return a list of Bereich_Objekte which affect the coordinate
	 */
	public Set<Bereich_Objekt> getBereichObjekte() {
		return bereichObjekte;
	}

	/**
	 * @return the coordinate of the point in the coordinate reference system as
	 *         defined by {@link #getCRS()}
	 */
	public GeoPosition getCoordinate() {
		return position;
	}

	/**
	 * @return coordinate reference system for this point
	 */
	public ENUMGEOKoordinatensystem getCRS() {
		return crs;
	}

	/**
	 * @return rotation of the point
	 */
	public double getEffectiveRotation() {
		return position.getEffectiveRotation();
	}

	/**
	 * @return rotation of the point
	 */
	public double getTopologicalRotation() {
		return position.getTopologicalRotation();
	}
}