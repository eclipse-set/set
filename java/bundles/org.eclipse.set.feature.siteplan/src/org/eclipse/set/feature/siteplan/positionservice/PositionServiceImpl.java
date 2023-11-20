/**
 * Copyright (c) 2021 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.siteplan.positionservice;

import static org.eclipse.set.ppmodel.extensions.utils.CoordinateExtensions.CRS_CR0_PARAMETER;
import static org.eclipse.set.ppmodel.extensions.utils.CoordinateExtensions.CRS_DR0_PARAMETER;
import static org.eclipse.set.ppmodel.extensions.utils.CoordinateExtensions.CRS_ERO_PARAMETER;
import static org.eclipse.set.ppmodel.extensions.utils.CoordinateExtensions.CRS_FRO_PARAMETER;

import java.util.EnumMap;

import org.eclipse.set.feature.siteplan.trackservice.GEOKanteCoordinate;
import org.eclipse.set.model.siteplan.Coordinate;
import org.eclipse.set.model.siteplan.Position;
import org.eclipse.set.model.siteplan.SiteplanFactory;
import org.eclipse.set.ppmodel.extensions.utils.GeoPosition;
import org.eclipse.set.toolboxmodel.Geodaten.ENUMGEOKoordinatensystem;
import org.locationtech.proj4j.CRSFactory;
import org.locationtech.proj4j.CoordinateReferenceSystem;
import org.locationtech.proj4j.CoordinateTransformFactory;
import org.locationtech.proj4j.ProjCoordinate;
import org.osgi.service.component.annotations.Component;

/**
 * Helper to transform between coordinate representations
 * 
 * @author Stuecker
 */
@Component
public class PositionServiceImpl implements PositionService {
	private static final String TARGET_CRS_DEFINITION = "+proj=merc +a=6378137 +b=6378137 +lat_ts=0.0 +lon_0=0.0 +x_0=0.0 +y_0=0 +k=1.0 +units=m +nadgrids=@null +wktext +no_defs"; //$NON-NLS-1$
	private static final String TARGET_CRS = "EPSG:3857"; //$NON-NLS-1$
	final CRSFactory crsFactory;
	final EnumMap<ENUMGEOKoordinatensystem, CoordinateReferenceSystem> crsReferences = new EnumMap<>(
			ENUMGEOKoordinatensystem.class);
	final CoordinateReferenceSystem proj4TargetCRS;

	/**
	 * 
	 */
	public PositionServiceImpl() {
		crsFactory = new CRSFactory();
		setProj4CRS();
		proj4TargetCRS = crsFactory.createFromParameters(TARGET_CRS,
				TARGET_CRS_DEFINITION);
	}

	@SuppressWarnings("nls")
	private void setProj4CRS() {
		crsReferences.put(
				ENUMGEOKoordinatensystem.ENUMGEO_KOORDINATENSYSTEM_CR0,
				crsFactory.createFromParameters("EPSG:31466",
						CRS_CR0_PARAMETER));
		crsReferences.put(
				ENUMGEOKoordinatensystem.ENUMGEO_KOORDINATENSYSTEM_DR0,
				crsFactory.createFromParameters("EPSG:31467",
						CRS_DR0_PARAMETER));
		crsReferences.put(
				ENUMGEOKoordinatensystem.ENUMGEO_KOORDINATENSYSTEM_ER0,
				crsFactory.createFromParameters("EPSG:31468",
						CRS_ERO_PARAMETER));
		crsReferences.put(
				ENUMGEOKoordinatensystem.ENUMGEO_KOORDINATENSYSTEM_FR0,
				crsFactory.createFromParameters("EPSG:31468",
						CRS_FRO_PARAMETER));
	}

	@Override
	public Coordinate transformCoordinate(final Coordinate result,
			final double x, final double y,
			final ENUMGEOKoordinatensystem crs) {
		final CoordinateTransformFactory transformFactory = new CoordinateTransformFactory();
		final CoordinateReferenceSystem sourceRef = getCRSReference(crs);
		final ProjCoordinate targetCoor = new ProjCoordinate();
		transformFactory.createTransform(sourceRef, proj4TargetCRS)
				.transform(new ProjCoordinate(x, y), targetCoor);
		result.setX(targetCoor.x);
		result.setY(targetCoor.y);
		return result;
	}

	@Override
	public Coordinate transformCoordinate(final double x, final double y,
			final ENUMGEOKoordinatensystem crs) {
		return transformCoordinate(SiteplanFactory.eINSTANCE.createCoordinate(),
				x, y, crs);
	}

	@Override
	public Coordinate transformCoordinate(
			final org.locationtech.jts.geom.Coordinate coordinate,
			final ENUMGEOKoordinatensystem crs) {
		return transformCoordinate(coordinate.x, coordinate.y, crs);
	}

	@Override
	public Position transformPosition(final GeoPosition coordinate,
			final ENUMGEOKoordinatensystem crs) {
		final Position position = SiteplanFactory.eINSTANCE.createPosition();
		transformCoordinate(position, coordinate.getCoordinate().x,
				coordinate.getCoordinate().y, crs);
		position.setRotation(coordinate.getTopologicalRotation());
		return position;
	}

	@Override
	public Position transformPosition(final GEOKanteCoordinate geoPosition) {
		return transformPosition(geoPosition.getCoordinate(),
				geoPosition.getCRS());
	}

	private CoordinateReferenceSystem getCRSReference(
			final ENUMGEOKoordinatensystem crs) {
		if (crsReferences.isEmpty()) {
			return null;
		}
		return crsReferences.get(crs);
	}
}
