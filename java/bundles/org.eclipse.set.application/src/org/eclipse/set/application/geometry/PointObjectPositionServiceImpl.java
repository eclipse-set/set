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
package org.eclipse.set.application.geometry;

import java.util.List;

import org.eclipse.set.basis.geometry.GEOKanteCoordinate;
import org.eclipse.set.basis.geometry.GEOKanteMetadata;
import org.eclipse.set.basis.geometry.GEOKanteSegment;
import org.eclipse.set.core.services.geometry.GeoKanteGeometryService;
import org.eclipse.set.core.services.geometry.PointObjectPositionService;
import org.eclipse.set.model.planpro.BasisTypen.ENUMLinksRechts;
import org.eclipse.set.model.planpro.BasisTypen.ENUMWirkrichtung;
import org.eclipse.set.model.planpro.Basisobjekte.Punkt_Objekt;
import org.eclipse.set.model.planpro.Basisobjekte.Punkt_Objekt_TOP_Kante_AttributeGroup;
import org.eclipse.set.model.planpro.Geodaten.TOP_Kante;
import org.eclipse.set.model.planpro.Geodaten.TOP_Knoten;
import org.eclipse.set.model.planpro.Gleis.ENUMGleisart;
import org.eclipse.set.model.planpro.Gleis.Gleis_Art;
import org.eclipse.set.model.planpro.Signale.Signal;
import org.eclipse.set.model.planpro.Signale.Signal_Befestigung;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Helper class to find point object locations
 */
@Component
public class PointObjectPositionServiceImpl
		implements PointObjectPositionService {
	// Default lateral distances of signals
	static double PUNKT_OBJEKT_LATERAL_DISTANCE_IN_STATION = 2.25;
	static double PUNKT_OBJEKT_LATERAL_DISTANCE_OTHER = 3.50;

	@Reference
	GeoKanteGeometryService geometryService;

	/*
	 * Special case for signals: The lateral position may be given indirectly
	 * via local track types
	 */
	private GEOKanteCoordinate getSignalObjectCoordinate(
			final Punkt_Objekt punktObjekt) {
		final List<Punkt_Objekt_TOP_Kante_AttributeGroup> singlePoints = punktObjekt
				.getPunktObjektTOPKante();
		if (singlePoints == null || singlePoints.isEmpty()) {
			return null;
		}

		final Punkt_Objekt_TOP_Kante_AttributeGroup singlePoint = singlePoints
				.getFirst();
		return getCoordinate(singlePoint);
	}

	@Override
	public GEOKanteCoordinate getCoordinate(final Punkt_Objekt punktObjekt) {
		if (punktObjekt instanceof Signal
				|| punktObjekt instanceof Signal_Befestigung) {
			return getSignalObjectCoordinate(punktObjekt);
		}
		return geometryService.getCoordinateAt(punktObjekt, 0.0);
	}

	@Override
	public GEOKanteCoordinate getCoordinate(
			final Punkt_Objekt_TOP_Kante_AttributeGroup singlePoint) {
		if (singlePoint == null || singlePoint.getAbstand() == null
				|| singlePoint.getAbstand().getWert() == null) {
			return null;
		}

		final double distance = singlePoint.getAbstand().getWert()
				.doubleValue();
		ENUMWirkrichtung direction = null;
		if (singlePoint.getWirkrichtung() != null) {
			direction = singlePoint.getWirkrichtung().getWert();
		}

		final TOP_Kante topKante = singlePoint.getIDTOPKante().getValue();
		final TOP_Knoten topKnotenA = topKante.getIDTOPKnotenA().getValue();

		final GEOKanteMetadata geoKante = geometryService
				.getGeoKanteAt(topKante, topKnotenA, distance);
		if (geoKante == null) {
			return null;
		}

		double lateralDistance = 0;
		if (singlePoint.getSeitlicherAbstand() != null
				&& singlePoint.getSeitlicherAbstand().getWert() != null) {
			lateralDistance = singlePoint.getSeitlicherAbstand().getWert()
					.doubleValue();
		} else {
			// Determine the track type
			final GEOKanteSegment segment = geoKante
					.getContainingSegment(distance);
			final List<ENUMGleisart> trackType = segment.getBereichObjekte()
					.stream().filter(Gleis_Art.class::isInstance)
					.map(ga -> ((Gleis_Art) ga).getGleisart().getWert())
					.filter(c -> c != null).toList();

			// Determine the object distance according to the local track type
			if (trackType.isEmpty()) {
				// No local track type. Default to 0 and record an error
				lateralDistance = 0;
				return geometryService.getCoordinate(geoKante, distance,
						lateralDistance, direction);
			}

			if (trackType
					.getFirst() == ENUMGleisart.ENUM_GLEISART_STRECKENGLEIS) {
				lateralDistance = PUNKT_OBJEKT_LATERAL_DISTANCE_OTHER;
			} else {
				lateralDistance = PUNKT_OBJEKT_LATERAL_DISTANCE_IN_STATION;
			}

			// If the object should be positioned to the left of the track,
			// invert the lateral distance
			if (singlePoint.getSeitlicheLage() != null && singlePoint
					.getSeitlicheLage()
					.getWert() == ENUMLinksRechts.ENUM_LINKS_RECHTS_LINKS) {
				lateralDistance = -lateralDistance;
			}
		}
		if (direction == ENUMWirkrichtung.ENUM_WIRKRICHTUNG_BEIDE) {
			// For Punkt_Objekte with a bilateral direction fall back to
			// ENUM_WIRKRICHTUNG_IN
			// to orient the Punkt_Objekt along the track axis
			return geometryService.getCoordinate(geoKante, distance,
					lateralDistance, ENUMWirkrichtung.ENUM_WIRKRICHTUNG_IN);
		}
		return geometryService.getCoordinate(geoKante, distance,
				lateralDistance, direction);
	}
}
