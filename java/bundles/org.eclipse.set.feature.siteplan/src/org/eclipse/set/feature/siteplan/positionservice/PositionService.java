/**
 * Copyright (c) 2023 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.feature.siteplan.positionservice;

import org.eclipse.set.model.siteplan.Coordinate;
import org.eclipse.set.model.siteplan.Position;
import org.eclipse.set.basis.geometry.GEOKanteCoordinate;
import org.eclipse.set.basis.geometry.GeoPosition;
import org.eclipse.set.model.planpro.Geodaten.ENUMGEOKoordinatensystem;

/**
 * @author truong
 *
 */
public interface PositionService {
	/**
	 * Transforms a coordinate defined by x and y coordinate into the siteplan
	 * coordinate system
	 * 
	 * @param result
	 *            the result object
	 * @param x
	 *            x-coordinate
	 * @param y
	 *            y-coordinate
	 * @param crs
	 *            The coordinate reference system
	 * @return the coordinate in siteplan
	 */
	public Coordinate transformCoordinate(Coordinate result, double x, double y,
			ENUMGEOKoordinatensystem crs);

	/**
	 * Transform coordinate from DBRef System into siteplan CRS
	 * 
	 * @param coordinate
	 *            the coordinate
	 * @param crs
	 *            The coordinate reference system
	 * @return the siteplan model coordinate in EPSG:3857 System
	 */
	Coordinate transformCoordinate(
			org.locationtech.jts.geom.Coordinate coordinate,
			ENUMGEOKoordinatensystem crs);

	/**
	 * Transforms a coordinate defined by x and y coordinate into the siteplan
	 * coordinate
	 * 
	 * @param x
	 *            x-coordinate
	 * @param y
	 *            y-coordinate
	 * @param crs
	 *            The coordinate reference system
	 * @return the coordinate in siteplan
	 */
	public Coordinate transformCoordinate(final double x, final double y,
			final ENUMGEOKoordinatensystem crs);

	/**
	 * Transforms a GeoPosition into the siteplan model
	 * 
	 * @param coordinate
	 *            coordinate to transform
	 * @param crs
	 *            The coordinate reference system to transform from
	 * @return the same position in the siteplan model
	 */
	public Position transformPosition(final GeoPosition coordinate,
			final ENUMGEOKoordinatensystem crs);

	/**
	 * Transforms a GEOKanteCoordinate into the siteplan model
	 * 
	 * @param geoPosition
	 *            coordinate to transform
	 * @return the same position in the siteplan model
	 */
	public Position transformPosition(final GEOKanteCoordinate geoPosition);
}
