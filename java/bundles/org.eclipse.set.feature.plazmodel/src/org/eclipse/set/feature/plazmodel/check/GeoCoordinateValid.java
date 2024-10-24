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

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.text.StringSubstitutor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.set.basis.constants.Events;
import org.eclipse.set.basis.geometry.GEOKanteCoordinate;
import org.eclipse.set.core.services.geometry.GeoKanteGeometryService;
import org.eclipse.set.core.services.geometry.PointObjectPositionService;
import org.eclipse.set.model.planpro.BasisTypen.ENUMLinksRechts;
import org.eclipse.set.model.planpro.Basisobjekte.Punkt_Objekt;
import org.eclipse.set.model.planpro.Basisobjekte.Punkt_Objekt_TOP_Kante_AttributeGroup;
import org.eclipse.set.model.planpro.Geodaten.ENUMGEOKoordinatensystem;
import org.eclipse.set.model.planpro.Geodaten.GEO_Punkt;
import org.eclipse.set.model.planpro.Ortung.FMA_Komponente;
import org.eclipse.set.model.planpro.PZB.PZB_Element;
import org.eclipse.set.model.planpro.Verweise.ID_GEO_Punkt_ohne_Proxy_TypeClass;
import org.eclipse.set.model.plazmodel.PlazError;
import org.eclipse.set.model.plazmodel.PlazFactory;
import org.eclipse.set.model.validationreport.ValidationSeverity;
import org.eclipse.set.ppmodel.extensions.EObjectExtensions;
import org.eclipse.set.ppmodel.extensions.GeoPunktExtensions;
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup;
import org.eclipse.set.ppmodel.extensions.utils.IterableExtensions;
import org.locationtech.jts.geom.Coordinate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;

import com.google.common.collect.Streams;

/**
 * Plausibilty checked of given geocoordinate from
 * {@link Punkt_Objekt_TOP_Kante_AttributeGroup#getIDGEOPunktBerechnet()}
 * 
 * @author Truong
 */

@Component(service = { PlazCheck.class, EventHandler.class }, property = {
		EventConstants.EVENT_TOPIC + "=" + Events.FIND_GEOMETRY_PROCESS_DONE })
@SuppressWarnings("nls")
public class GeoCoordinateValid extends AbstractPlazContainerCheck
		implements PlazCheck, EventHandler {
	private static final double TOLERANT = 0.1;
	@Reference
	GeoKanteGeometryService geometryService;
	@Reference
	PointObjectPositionService pointObjectPositionService;
	@Reference
	EventAdmin eventAdmin;

	// The half of track width is lateral distance for PZB_Element and
	// FMA_Komponent
	static double FMA_PZB_LATERAL_DISTANCE = 1.435 / 2;

	@Override
	public void handleEvent(final Event event) {
		final Map<String, Class<? extends PlazCheck>> properties = new HashMap<>();
		properties.put("org.eclipse.e4.data", this.getClass()); // $NON-NLS-1$
		eventAdmin.sendEvent(new Event(Events.DO_PLAZ_CHECK, properties));
	}

	@Override
	protected List<PlazError> run(
			final MultiContainer_AttributeGroup container) {
		if (!geometryService.isFindGeometryComplete()) {
			return List.of(createProcessingWarning());
		}

		final List<PlazError> result = new ArrayList<>();
		getRelevantPOs(container)
				.forEach(po -> po.getPunktObjektTOPKante().forEach(potk -> {
					if (isNotDistinctCoordinateSystem(potk)) {
						result.add(createGeoCoordinateError(po,
								"Der Punkt_Objekt_Top_Kante des Punkt_Objekt: {GUID} würde auf mehrere GEO_Punkt mit gleichen Koordinatensystem verweisen",
								Map.of("GUID", po.getIdentitaet().getWert())));
						return;
					}
					final GEOKanteCoordinate geoKanteCoordinate = getPointGEOCoordinate(
							po, potk);
					if (geoKanteCoordinate == null
							|| getNullableObject(geoKanteCoordinate,
									e -> e.getCoordinate().getCoordinate())
											.isEmpty()) {
						return;
					}

					final GEO_Punkt relevantGeoPunkt = getSameCRSGEOPunkt(potk,
							geoKanteCoordinate.getCRS());
					if (relevantGeoPunkt == null) {
						return;
					}
					final Coordinate coordinate = geoKanteCoordinate
							.getCoordinate().getCoordinate();
					final Coordinate gpCoordinate = GeoPunktExtensions
							.getCoordinate(relevantGeoPunkt);
					if (gpCoordinate == null) {
						result.add(creatErrorReport(relevantGeoPunkt));
					}
					final double diff = coordinate.distance(gpCoordinate);
					if (diff > TOLERANT) {
						result.add(createErrorReport(po, potk, relevantGeoPunkt,
								diff));
					}
				}));
		return result;
	}

	private static List<Punkt_Objekt> getRelevantPOs(
			final MultiContainer_AttributeGroup container) {
		return Streams.stream(container.getPunktObjekts()).parallel()
				.filter(po -> po.getPunktObjektTOPKante().stream()
						.anyMatch(potk -> getNullableObject(potk,
								ele -> ele.getSeitlicherAbstand().getWert())
										.isPresent())
						|| po instanceof FMA_Komponente
						|| po instanceof PZB_Element)
				.toList();
	}

	private GEOKanteCoordinate getPointGEOCoordinate(final Punkt_Objekt po,
			final Punkt_Objekt_TOP_Kante_AttributeGroup potk) {
		if (po instanceof PZB_Element || po instanceof FMA_Komponente) {
			final ENUMLinksRechts side = getNullableObject(potk,
					point -> point.getSeitlicheLage().getWert()).orElse(null);
			final BigDecimal lateralDistance = BigDecimal
					.valueOf(FMA_PZB_LATERAL_DISTANCE);
			if (side != null
					&& side == ENUMLinksRechts.ENUM_LINKS_RECHTS_LINKS) {
				return pointObjectPositionService.getCoordinate(potk,
						lateralDistance.negate());
			}
			return pointObjectPositionService.getCoordinate(potk,
					lateralDistance);

		}
		return pointObjectPositionService.getCoordinate(potk);
	}

	private static GEO_Punkt getSameCRSGEOPunkt(
			final Punkt_Objekt_TOP_Kante_AttributeGroup potk,
			final ENUMGEOKoordinatensystem crs) {
		return potk.getIDGEOPunktBerechnet().stream().map(e -> e.getValue())
				.filter(Objects::nonNull).filter(gp -> {
					final ENUMGEOKoordinatensystem geoPunktCRS = getNullableObject(
							gp, e -> e.getGEOPunktAllg()
									.getGEOKoordinatensystem().getWert())
											.orElse(null);
					return geoPunktCRS != null && geoPunktCRS.equals(crs);
				}).findFirst().orElse(null);
	}

	private PlazError creatErrorReport(final GEO_Punkt gp) {
		EObject errObj = gp;
		if (gp.getGEOPunktAllg() == null) {
			errObj = gp.getGEOPunktAllg();
		} else if (gp.getGEOPunktAllg().getGKX() == null) {
			errObj = gp.getGEOPunktAllg().getGKX();
		} else if (gp.getGEOPunktAllg().getGKY() == null) {
			errObj = gp.getGEOPunktAllg().getGKY();
		}
		return createGeoCoordinateError(errObj,
				"GEO_Punkt: {GUID} fehlt Koordinate",
				Map.of(gp.getIdentitaet().getWert(), "GUID"));
	}

	private PlazError createErrorReport(final Punkt_Objekt po,
			final Punkt_Objekt_TOP_Kante_AttributeGroup potk,
			final GEO_Punkt gp, final double diff) {
		final ID_GEO_Punkt_ohne_Proxy_TypeClass errorObject = potk
				.getIDGEOPunktBerechnet().stream()
				.filter(idgp -> idgp.getValue().equals(gp)).findFirst()
				.orElse(null);
		if (errorObject == null) {
			throw new IllegalArgumentException(String.format(
					"GEO_Punkt: %s gehört nicht zu Punkt_Objekt: %s",
					gp.getIdentitaet().getWert(),
					po.getIdentitaet().getWert()));
		}
		return createGeoCoordinateError(errorObject,
				"Die Koordinaten des GEO_Punkt: {GEO_PUNKT_ID}, der auf die Punkt_Objekt: {GUID} verweisen würde, weicht {DIFF}cm von der berechneten Koordinate ab.",
				Map.of("GEO_PUNKT_ID", errorObject.getWert(), "GUID",
						po.getIdentitaet().getWert(), "DIFF",
						new DecimalFormat("0.00").format(diff * 100)));
	}

	private PlazError createGeoCoordinateError(final EObject object,
			final String message, final Map<String, String> data) {
		final PlazError plazError = PlazFactory.eINSTANCE.createPlazError();
		plazError.setObject(object);
		plazError.setSeverity(ValidationSeverity.ERROR);
		plazError.setType(checkType());
		plazError
				.setMessage(StringSubstitutor.replace(message, data, "{", "}"));
		return plazError;
	}

	@Override
	public String checkType() {
		return "Koordinate";
	}

	@Override
	public String getDescription() {
		return "Die Verweise vom Punkt_Objekt auf den Geo_Punkt durch ID_Geo_Punkt_Berechnen sind plausibilisiert";
	}

	@Override
	public String getGeneralErrMsg() {
		return "Die Verweise vom Punkt_Objekt auf den Geo_Punkt durch ID_Geo_Punkt_Berechnen sind nicht plausibilisiert";
	}

	private PlazError createProcessingWarning() {
		final PlazError plazError = PlazFactory.eINSTANCE.createPlazError();
		plazError.setType(checkType());
		plazError.setSeverity(ValidationSeverity.WARNING);
		plazError.setMessage(
				"Die Prüfung auf ID_GEO_Punkt_Berechnen is noch nicht beenden");
		return plazError;
	}

	/**
	 * @param potk
	 *            the {@link Punkt_Objekt_TOP_Kante_AttributeGroup}
	 * @return true, when exist predetermined coordinates through
	 *         {@link Punkt_Objekt_TOP_Kante_AttributeGroup#getIDGEOPunktBerechnet}
	 *         and the coordinates haven't same coordinate system
	 */
	public static boolean isNotDistinctCoordinateSystem(
			final Punkt_Objekt_TOP_Kante_AttributeGroup potk) {
		final List<GEO_Punkt> givenGeoPunkts = potk.getIDGEOPunktBerechnet()
				.stream().map(gp -> gp.getValue()).filter(Objects::nonNull)
				.toList();
		if (givenGeoPunkts.isEmpty()) {
			return false;
		}
		final Iterable<GEO_Punkt> notDistinctBy = IterableExtensions
				.notDistinctBy(givenGeoPunkts,
						gp -> EObjectExtensions.getNullableObject(gp,
								e -> e.getGEOPunktAllg()
										.getGEOKoordinatensystem().getWert()));
		return notDistinctBy.iterator().hasNext();
	}
}
