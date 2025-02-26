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
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.set.model.planpro.Geodaten.ENUMGEOKoordinatensystem;
import org.eclipse.set.model.planpro.Geodaten.GEO_Kante;
import org.eclipse.set.model.planpro.Geodaten.GEO_Knoten;
import org.eclipse.set.model.planpro.Geodaten.GEO_Punkt;
import org.eclipse.set.model.plazmodel.PlazError;
import org.eclipse.set.model.plazmodel.PlazFactory;
import org.eclipse.set.model.validationreport.ValidationSeverity;
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup;
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
		return "Die Geo-Kante {GUID} hat gleiche Anfangs- und End-Koordinaten.";
	}

	@Override
	protected List<PlazError> run(
			final MultiContainer_AttributeGroup container) {
		final List<GEO_Kante> geoKanten = Streams
				.stream(container.getGEOKante())
				.toList();
		final List<PlazError> errors = new ArrayList<>();
		geoKanten.stream().filter(geoKante -> {
			final Optional<BigDecimal> kanteLength = getNullableObject(geoKante,
					kante -> kante.getGEOKanteAllg().getGEOLaenge().getWert());
			// The GEO_Kante with length equal 0 isn't considered
			return kanteLength.isEmpty()
					|| kanteLength.get().compareTo(BigDecimal.ZERO) > 0;
		}).forEach(geoKante -> {
			final GEO_Knoten geoKnotenA = getGeoKnotenA(geoKante);
			final GEO_Knoten geoKnotenB = getGeoKnotenB(geoKante);
			final List<GEO_Punkt> geoPunkteA = getGeoPunkte(geoKnotenA);
			final List<GEO_Punkt> geoPunkteB = getGeoPunkte(geoKnotenB);
			geoPunkteA.addAll(geoPunkteB);
			final Map<ENUMGEOKoordinatensystem, List<GEO_Punkt>> geoPunktGroupByCRS = geoPunkteA
					.stream()
					.collect(Collectors
							.groupingBy(geoPunkt -> geoPunkt.getGEOPunktAllg()
									.getGEOKoordinatensystem()
									.getWert()));
			geoPunktGroupByCRS.forEach((crs, geoPunkte) -> {
				if (!isUniqueCoordinatens(geoPunkte)) {
					errors.add(createError(geoKante));
				}
			});
		});

		return errors;
	}

	private static boolean isUniqueCoordinatens(
			final List<GEO_Punkt> geoPunkte) {
		if (geoPunkte.size() == 1) {
			return true;
		}
		final Set<BigDecimal> xValues = geoPunkte.stream()
				.map(geoPunkt -> geoPunkt.getGEOPunktAllg().getGKX().getWert())
				.collect(Collectors.toSet());
		final Set<BigDecimal> yValues = geoPunkte.stream()
				.map(geoPunkt -> geoPunkt.getGEOPunktAllg().getGKY().getWert())
				.collect(Collectors.toSet());
		return xValues.size() > 1 && yValues.size() > 1;
	}

	private PlazError createError(final GEO_Kante geoKante) {
		final PlazError plazError = PlazFactory.eINSTANCE.createPlazError();
		plazError.setSeverity(ValidationSeverity.ERROR);
		plazError.setObject(geoKante);
		plazError.setType(checkType());
		plazError.setMessage(transformErrorMsg(Map.of("GUID",
				getNullableObject(geoKante,
						kante -> kante.getIdentitaet().getWert())
								.orElse("(ohne Identität)"))));
		return plazError;
	}

}
