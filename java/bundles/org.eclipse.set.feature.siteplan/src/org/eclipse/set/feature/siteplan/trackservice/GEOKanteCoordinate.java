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

import org.eclipse.set.ppmodel.extensions.utils.SymbolArrangement;
import org.eclipse.set.toolboxmodel.Basisobjekte.Bereich_Objekt;
import org.eclipse.set.toolboxmodel.Geodaten.ENUMGEOKoordinatensystem;
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
	private final Coordinate coordinate;
	private final ENUMGEOKoordinatensystem crs;
	private double rotation;

	GEOKanteCoordinate(final SymbolArrangement<Coordinate> coordinate,
			final Set<Bereich_Objekt> bereichObjekte,
			final ENUMGEOKoordinatensystem crs) {
		this.coordinate = coordinate.getGeometricInformation();
		this.bereichObjekte = bereichObjekte;
		this.rotation = coordinate.getRotation();
		this.crs = crs;
	}

	GEOKanteCoordinate(final Coordinate coordinate,
			final Set<Bereich_Objekt> bereichObjekte,
			final ENUMGEOKoordinatensystem crs) {
		this.coordinate = coordinate;
		this.rotation = 0;
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
	public Coordinate getCoordinate() {
		return coordinate;
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
	public double getRotation() {
		return rotation;
	}

	/**
	 * Sets the rotation for the coordinate
	 * 
	 * @param rotation
	 *            the rotation to set
	 */
	public void setRotation(final double rotation) {
		this.rotation = rotation;
	}
}