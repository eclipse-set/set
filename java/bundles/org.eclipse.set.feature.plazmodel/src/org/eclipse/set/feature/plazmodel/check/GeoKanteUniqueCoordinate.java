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
package org.eclipse.set.feature.plazmodel.check;

import static org.eclipse.set.ppmodel.extensions.EObjectExtensions.getNullableObject;
import static org.eclipse.set.ppmodel.extensions.GeoKanteExtensions.getGeoKnotenA;
import static org.eclipse.set.ppmodel.extensions.GeoKanteExtensions.getGeoKnotenB;
import static org.eclipse.set.ppmodel.extensions.GeoKnotenExtensions.getGeoPunkte;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.set.model.planpro.Geodaten.ENUMGEOForm;
import org.eclipse.set.model.planpro.Geodaten.ENUMGEOKoordinatensystem;
import org.eclipse.set.model.planpro.Geodaten.GEO_Kante;
import org.eclipse.set.model.planpro.Geodaten.GEO_Knoten;
import org.eclipse.set.model.planpro.Geodaten.GEO_Punkt;
import org.eclipse.set.model.plazmodel.PlazError;
import org.eclipse.set.model.plazmodel.PlazFactory;
import org.eclipse.set.model.validationreport.ValidationSeverity;
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup;
import org.locationtech.jts.geom.Coordinate;
import org.osgi.service.component.annotations.Component;

import com.google.common.collect.Streams;

/**
 * Check, if the GeoKnotenA and the GeoKnotenB of a GEO_Kant have difference
 * geometry coordinate
 * 
 * @author Truong
 */
@SuppressWarnings("nls")
@Component
public class GeoKanteUniqueCoordinate extends AbstractPlazContainerCheck
		implements PlazCheck {
	private static double TOLERANT = 0.001;

	@Override
	public String checkType() {
		return "Koordinaten";
	}

	@Override
	public String getDescription() {
		return "Alle Geo-Kanten haben unterschiedliche Anfangs- und End-Koordinaten.";
	}

	@Override
	public String getGeneralErrMsg() {
		return "Die Geo-Kante {GUID} hat nahezu gleiche Anfangs- und End-Koordinaten (Abstand kleiner "
				+ TOLERANT + ").";
	}

	private static String getGEOKanteGUID(final GEO_Kante geoKante) {
		return getNullableObject(geoKante,
				kante -> kante.getIdentitaet().getWert())
						.orElse("(ohne Identität)");
	}

	@Override
	protected List<PlazError> run(
			final MultiContainer_AttributeGroup container) {
		final List<GEO_Kante> geoKanten = Streams
				.stream(container.getGEOKante())
				.toList();
		final List<PlazError> errors = new ArrayList<>();
		geoKanten.parallelStream().filter(geoKante -> {
			final Optional<BigDecimal> kanteLength = getNullableObject(geoKante,
					kante -> kante.getGEOKanteAllg().getGEOLaenge().getWert());
			// The GEO_Kante have length equal 0 or KM_Sprung GEO_Form isn't
			// considered
			return !(kanteLength.isEmpty()
					|| kanteLength.get().compareTo(BigDecimal.ZERO) == 0
					|| MeridianBetweenGEOKante.isMeridianGEOKante(geoKante)
					|| geoKante.getGEOKanteAllg()
							.getGEOForm()
							.getWert() == ENUMGEOForm.ENUMGEO_FORM_KM_SPRUNG);
		}).forEach(geoKante -> {
			final GEO_Knoten geoKnotenA = getGeoKnotenA(geoKante);
			final GEO_Knoten geoKnotenB = getGeoKnotenB(geoKante);
			if (geoKnotenA == null || geoKnotenB == null) {
				final PlazError error = createError(geoKante);
				error.setMessage(
						String.format("Die GEO_Kante %s fehlt GEO_Knoten",
								getGEOKanteGUID(geoKante)));
				errors.add(error);
				return;
			}
			try {
				final List<GEO_Punkt> geoPunkteA = getGeoPunkte(geoKnotenA);
				final List<GEO_Punkt> geoPunkteB = getGeoPunkte(geoKnotenB);
				geoPunkteA.addAll(geoPunkteB);
				final Map<ENUMGEOKoordinatensystem, List<GEO_Punkt>> geoPunktGroupByCRS = geoPunkteA
						.stream()
						.collect(Collectors.groupingBy(
								geoPunkt -> geoPunkt.getGEOPunktAllg()
										.getGEOKoordinatensystem()
										.getWert()));
				geoPunktGroupByCRS.forEach((crs, geoPunkte) -> {
					if (haveEqualCoordinates(geoPunkte)) {
						errors.add(createError(geoKante));
					}
				});
			} catch (final NullPointerException e) {
				final PlazError error = createError(geoKante);
				error.setMessage(String.format(
						"Die GEO_Punkte der GEO_Knoten von GEO_Kante %s haben ungültige Koordinaten oder ein ungültiges Koordinatensystem.",
						getGEOKanteGUID(geoKante)));
				errors.add(error);
			}

		});

		return errors;
	}

	private static boolean haveEqualCoordinates(
			final List<GEO_Punkt> geoPunkte) {
		if (geoPunkte.size() == 1) {
			return true;
		}
		double maxDistance = 0.0;
		final Function<GEO_Punkt, Coordinate> createCoordFunc = geoPunkt -> new Coordinate(
				geoPunkt.getGEOPunktAllg().getGKX().getWert().doubleValue(),
				geoPunkt.getGEOPunktAllg().getGKY().getWert().doubleValue());
		for (int i = 0; i < geoPunkte.size(); i++) {
			for (int j = i + 1; j < geoPunkte.size(); j++) {
				final Coordinate coord1 = createCoordFunc
						.apply(geoPunkte.get(i));
				final Coordinate coord2 = createCoordFunc
						.apply(geoPunkte.get(j));
				final double distance = coord1.distance(coord2);
				if (distance > maxDistance) {
					maxDistance = distance;
				}
			}
		}
		return maxDistance < TOLERANT;
	}

	private PlazError createError(final GEO_Kante geoKante) {
		final PlazError plazError = PlazFactory.eINSTANCE.createPlazError();
		plazError.setSeverity(ValidationSeverity.ERROR);
		plazError.setObject(geoKante);
		plazError.setType(checkType());
		plazError.setMessage(
				transformErrorMsg(Map.of("GUID", getGEOKanteGUID(geoKante))));
		return plazError;
	}

}
