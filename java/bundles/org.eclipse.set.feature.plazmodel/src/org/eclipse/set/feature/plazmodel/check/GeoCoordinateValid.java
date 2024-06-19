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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.StreamSupport;

import org.eclipse.set.basis.constants.Events;
import org.eclipse.set.basis.geometry.GEOKanteCoordinate;
import org.eclipse.set.core.services.geometry.GeoKanteGeometryService;
import org.eclipse.set.model.planpro.Basisobjekte.Punkt_Objekt;
import org.eclipse.set.model.planpro.Basisobjekte.Punkt_Objekt_TOP_Kante_AttributeGroup;
import org.eclipse.set.model.planpro.Geodaten.ENUMGEOKoordinatensystem;
import org.eclipse.set.model.planpro.Geodaten.GEO_Punkt;
import org.eclipse.set.model.planpro.Verweise.ID_GEO_Punkt_ohne_Proxy_TypeClass;
import org.eclipse.set.model.plazmodel.PlazError;
import org.eclipse.set.model.plazmodel.PlazFactory;
import org.eclipse.set.model.validationreport.ValidationSeverity;
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup;
import org.locationtech.jts.geom.Coordinate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;

/**
 * Plausibilty checked of given geocoordinate from
 * {@link Punkt_Objekt_TOP_Kante_AttributeGroup#getIDGEOPunktBerechnet()}
 * 
 * @author Truong
 */
@Component(service = { EventHandler.class, PlazCheck.class }, property = {
		EventConstants.EVENT_TOPIC + "=" + Events.FIND_GEOMETRY_PROCESS_DONE })
public class GeoCoordinateValid extends AbstractPlazContainerCheck
		implements PlazCheck, EventHandler {
	private static final double TOLERANT = 0.1;
	@Reference
	GeoKanteGeometryService geometryService;

	@Reference
	EventAdmin eventAdmin;

	@Override
	public void handleEvent(final Event event) {
		final Map<String, Class<? extends PlazCheck>> properties = new HashMap<>();
		properties.put("org.eclipse.e4.data", this.getClass()); // $NON-NLS-1$ //$NON-NLS-1$
		eventAdmin.sendEvent(new Event(Events.DO_PLAZ_CHECK, properties));
	}

	@Override
	protected List<PlazError> run(
			final MultiContainer_AttributeGroup container) {
		if (!geometryService.isFindGeometryComplete()) {
			return List.of(createProcessingWarning());
		}
		final List<PlazError> result = new ArrayList<>();
		final List<Punkt_Objekt> punktObjekts = StreamSupport
				.stream(container.getAllContents().spliterator(), false)
				.filter(Punkt_Objekt.class::isInstance)
				.map(Punkt_Objekt.class::cast).toList();
		punktObjekts.forEach(po -> po.getPunktObjektTOPKante().forEach(potk -> {
			try {
				final GEOKanteCoordinate geoKanteCoordinate = geometryService
						.getCoordinateAt(potk, 0);
				final Coordinate coordinate = geoKanteCoordinate.getCoordinate()
						.getCoordinate();
				final List<GEO_Punkt> givenGEOPunkts = getGivenCoordinate(potk,
						geoKanteCoordinate.getCRS());
				final GEO_Punkt invalidCoor = givenGEOPunkts.stream()
						.filter(gp -> {
							final double givenCoorX = gp.getGEOPunktAllg()
									.getGKX().getWert().doubleValue();
							final double givenCoorY = gp.getGEOPunktAllg()
									.getGKY().getWert().doubleValue();
							return coordinate.distance(new Coordinate(
									givenCoorX, givenCoorY)) > TOLERANT;
						}).findFirst().orElse(null);
				if (invalidCoor != null) {
					result.add(createErrorReport(po, potk, invalidCoor));
				}
			} catch (final Exception e) {
				return;
			}
		})

		);
		return result;
	}

	private static List<GEO_Punkt> getGivenCoordinate(
			final Punkt_Objekt_TOP_Kante_AttributeGroup potk,
			final ENUMGEOKoordinatensystem targetCRS) {
		final List<GEO_Punkt> givenGeoPunkt = potk.getIDGEOPunktBerechnet()
				.stream().map(e -> e.getValue()).toList();
		return givenGeoPunkt.stream().filter(gp -> {
			final Optional<ENUMGEOKoordinatensystem> crs = getNullableObject(gp,
					e -> e.getGEOPunktAllg().getGEOKoordinatensystem()
							.getWert());
			return crs.isPresent() && crs.get().equals(targetCRS);
		}).toList();
	}

	private PlazError createProcessingWarning() {
		final PlazError err = PlazFactory.eINSTANCE.createPlazError();
		err.setType(checkType());
		err.setMessage("WATTING"); //$NON-NLS-1$
		err.setSeverity(ValidationSeverity.WARNING);
		return err;
	}

	private PlazError createErrorReport(final Punkt_Objekt po,
			final Punkt_Objekt_TOP_Kante_AttributeGroup potk,
			final GEO_Punkt gp) {
		final PlazError plazError = PlazFactory.eINSTANCE.createPlazError();
		plazError.setSeverity(ValidationSeverity.ERROR);
		final ID_GEO_Punkt_ohne_Proxy_TypeClass errorObject = potk
				.getIDGEOPunktBerechnet().stream()
				.filter(idgp -> idgp.getValue().equals(gp)).findFirst()
				.orElse(null);
		if (errorObject == null) {
			throw new IllegalArgumentException(String.format(
					"GEO_Punkt: %s ist nicht zu Punkt_Objekt: %s gehören", //$NON-NLS-1$
					gp.getIdentitaet().getWert(),
					po.getIdentitaet().getWert()));
		}
		plazError.setObject(errorObject);
		plazError.setMessage(
				transformErrorMsg(Map.of("GUID", errorObject.getWert()))); //$NON-NLS-1$
		plazError.setType(checkType());
		return plazError;
	}

	@Override
	public String checkType() {
		return "Geokoordinaten"; //$NON-NLS-1$
	}

	@Override
	public String getDescription() {
		return "Alle vorgegeben GeoKoordinaten von GEO_Punkt sind plausibilisiert"; //$NON-NLS-1$
	}

	@Override
	public String getGeneralErrMsg() {
		return "Die vorgegebene GeoKoordinate: {GUID} weich mehr als 10cm von der berechneten Koordinate ab."; //$NON-NLS-1$
	}

	private static <T, U> Optional<U> getNullableObject(final T t,
			final Function<T, U> func) {
		try {
			final U result = func.apply(t);
			return Optional.ofNullable(result);
		} catch (final NullPointerException e) {
			return Optional.empty();
		}
	}
}
