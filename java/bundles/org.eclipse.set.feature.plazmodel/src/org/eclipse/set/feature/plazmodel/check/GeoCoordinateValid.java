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

import static org.eclipse.set.basis.geometry.Geometries.getSegmentPosition;
import static org.eclipse.set.ppmodel.extensions.EObjectExtensions.getNullableObject;
import static org.eclipse.set.ppmodel.extensions.GeoKanteExtensions.getCoordinate;
import static org.eclipse.set.ppmodel.extensions.GeoKanteExtensions.getTangent;
import static org.eclipse.set.ppmodel.extensions.GeoKnotenExtensions.getCRS;
import static org.eclipse.set.ppmodel.extensions.GeoKnotenExtensions.getCoordinate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.text.StringSubstitutor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.set.basis.constants.ContainerType;
import org.eclipse.set.basis.constants.Events;
import org.eclipse.set.basis.constants.ToolboxConstants;
import org.eclipse.set.basis.geometry.GEOKanteCoordinate;
import org.eclipse.set.basis.geometry.GEOKanteMetadata;
import org.eclipse.set.basis.geometry.GeoPosition;
import org.eclipse.set.basis.geometry.GeometryCalculationOptions.GeometryCalculationOptionsBuilder;
import org.eclipse.set.basis.geometry.GeometryOptionsBuilder;
import org.eclipse.set.basis.geometry.SegmentPosition;
import org.eclipse.set.core.services.geometry.GeoKanteGeometryService;
import org.eclipse.set.core.services.geometry.PointObjectPositionService;
import org.eclipse.set.feature.plazmodel.export.TopologicalCoordinate;
import org.eclipse.set.model.planpro.BasisTypen.ENUMLinksRechts;
import org.eclipse.set.model.planpro.BasisTypen.ENUMWirkrichtung;
import org.eclipse.set.model.planpro.Basisobjekte.Basis_Objekt;
import org.eclipse.set.model.planpro.Basisobjekte.Bereich_Objekt;
import org.eclipse.set.model.planpro.Basisobjekte.Punkt_Objekt;
import org.eclipse.set.model.planpro.Basisobjekte.Punkt_Objekt_TOP_Kante_AttributeGroup;
import org.eclipse.set.model.planpro.Geodaten.ENUMGEOKoordinatensystem;
import org.eclipse.set.model.planpro.Geodaten.GEO_Kante;
import org.eclipse.set.model.planpro.Geodaten.GEO_Knoten;
import org.eclipse.set.model.planpro.Geodaten.GEO_Punkt;
import org.eclipse.set.model.planpro.Geodaten.TOP_Kante;
import org.eclipse.set.model.planpro.Geodaten.TOP_Knoten;
import org.eclipse.set.model.planpro.Ortung.FMA_Komponente;
import org.eclipse.set.model.planpro.PZB.PZB_Element;
import org.eclipse.set.model.planpro.Verweise.ID_GEO_Punkt_ohne_Proxy_TypeClass;
import org.eclipse.set.model.plazmodel.PlazError;
import org.eclipse.set.model.plazmodel.PlazFactory;
import org.eclipse.set.model.validationreport.ValidationSeverity;
import org.eclipse.set.ppmodel.extensions.BasisAttributExtensions;
import org.eclipse.set.ppmodel.extensions.BasisObjektExtensions;
import org.eclipse.set.ppmodel.extensions.EObjectExtensions;
import org.eclipse.set.ppmodel.extensions.GeoKanteExtensions;
import org.eclipse.set.ppmodel.extensions.GeoKnotenExtensions;
import org.eclipse.set.ppmodel.extensions.GeoPunktExtensions;
import org.eclipse.set.ppmodel.extensions.MultiContainer_AttributeGroupExtensions;
import org.eclipse.set.ppmodel.extensions.PunktObjektExtensions;
import org.eclipse.set.ppmodel.extensions.TopKanteExtensions;
import org.eclipse.set.ppmodel.extensions.TopKnotenExtensions;
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup;
import org.eclipse.set.ppmodel.extensions.geometry.GEOKanteGeometryExtensions;
import org.eclipse.set.ppmodel.extensions.utils.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Pair;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.LineSegment;
import org.locationtech.jts.geom.LineString;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Streams;

/**
 * Plausibilty checked of given geocoordinate from
 * {@link Punkt_Objekt_TOP_Kante_AttributeGroup#getIDGEOPunktBerechnet()}
 * 
 * @author Truong
 */

@Component(service = { PlazCheck.class, EventHandler.class }, property = {
		EventConstants.EVENT_TOPIC + "=" + Events.FIND_GEOMETRY_PROCESS_DONE,
		EventConstants.EVENT_TOPIC + "=" + Events.CLOSE_SESSION,
		"type=geoCoordinate" })
@SuppressWarnings("nls")
public class GeoCoordinateValid extends AbstractPlazContainerCheck
		implements PlazCheck, EventHandler {
	static final Logger logger = LoggerFactory
			.getLogger(GeoCoordinateValid.class);

	private static record GeoKnotenRange(Pair<GEO_Knoten, BigDecimal> start,
			Pair<GEO_Knoten, BigDecimal> end) {
		public boolean contain(final BigDecimal distance) {
			return start.getValue().compareTo(distance) <= 0
					&& end.getValue().compareTo(distance) >= 0;
		}

		public GEO_Knoten startNode() {
			return start.getKey();
		}

		public BigDecimal startDistance() {
			return start.getValue();
		}

		public GEO_Knoten endNode() {
			return end.getKey();
		}

		public GEO_Kante getGeoKante() {
			final GEO_Kante geoKante = Streams
					.stream(GeoKnotenExtensions.getGeoKanten(startNode()))
					.filter(edge -> Streams
							.stream(GeoKnotenExtensions.getGeoKanten(endNode()))
							.anyMatch(e -> e == edge))
					.findFirst()
					.orElse(null);
			if (geoKante == null) {
				throw new IllegalArgumentException();
			}
			return geoKante;
		}
	}

	private static final double TOLERANT = 0.1;
	@Reference
	GeoKanteGeometryService geometryService;
	@Reference
	PointObjectPositionService pointObjectPositionService;
	@Reference
	EventAdmin eventAdmin;
	private Optional<List<TopologicalCoordinate>> topologicalCoordinates;

	// Fixed lateral distance for PZB_Element and FMA_Komponent
	static BigDecimal FMA_LATERAL_DISTANCE = BigDecimal.valueOf(0.85);
	static BigDecimal PZB_LATERAL_DISTANCE = BigDecimal.valueOf(1.05);

	private List<GEOKanteMetadata> alreadyFoundMetaData;

	@Activate
	void active() {
		topologicalCoordinates = Optional.empty();
		alreadyFoundMetaData = new ArrayList<>();
	}

	@Override
	public void handleEvent(final Event event) {
		if (event.getTopic().equals(Events.CLOSE_SESSION)) {
			alreadyFoundMetaData.clear();
			topologicalCoordinates = Optional.empty();
		}
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
		final List<Punkt_Objekt> relevantPOs = getRelevantPOs(container);
		relevantPOs.forEach(po -> po.getPunktObjektTOPKante().forEach(potk -> {
			GEOKanteMetadata geoKanteMetaData = alreadyFoundMetaData
					.parallelStream()
					.filter(md -> isBelongToThisMetadata(md, potk))
					.findFirst()
					.orElse(null);
			if (geoKanteMetaData == null) {
				geoKanteMetaData = getGeoKanteMetaData(potk);
				if (geoKanteMetaData == null) {
					return;
				}
				alreadyFoundMetaData.add(geoKanteMetaData);
			}
		}));
		relevantPOs.parallelStream()
				.forEach(po -> po.getPunktObjektTOPKante().forEach(potk -> {
					if (isNotDistinctCoordinateSystem(potk)) {
						result.add(createGeoCoordinateError(po,
								"Der Punkt_Objekt_Top_Kante des Punkt_Objekt: {GUID} würde auf mehrere GEO_Punkt mit gleichen Koordinatensystem verweisen",
								Map.of("GUID", po.getIdentitaet().getWert())));
						return;
					}
					final GEOKanteCoordinate topCoordinate = calculateCoordinate(
							MultiContainer_AttributeGroupExtensions
									.getContainerType(container),
							po, potk);

					if (topCoordinate == null
							|| getNullableObject(topCoordinate,
									e -> e.getCoordinate()).isEmpty()) {
						result.add(createGeoCoordinateError(po,
								"Es gibt Fehler bei der Berechnung der topologischen Koordinate",
								Map.of()));
						return;
					}

					final PlazError error = validGeoCoordinate(po, potk,
							topCoordinate);
					if (error != null) {
						result.add(error);
					}
				}));
		return result;
	}

	private PlazError validGeoCoordinate(final Punkt_Objekt po,
			final Punkt_Objekt_TOP_Kante_AttributeGroup potk,
			final GEOKanteCoordinate topCoordinate) {
		final GEO_Punkt relevantGeoPunkt = getSameCRSGEOPunkt(potk,
				topCoordinate.getCRS());
		if (relevantGeoPunkt == null) {
			return null;
		}
		final Coordinate coordinate = topCoordinate.getCoordinate();
		final Coordinate geoCoordinate = GeoPunktExtensions
				.getCoordinate(relevantGeoPunkt);
		if (geoCoordinate == null) {
			return creatErrorReport(relevantGeoPunkt);
		}

		final double diff = coordinate.distance(geoCoordinate);
		if (diff > TOLERANT) {
			return createErrorReport(po, potk, relevantGeoPunkt, diff);
		}
		return null;
	}

	private static List<Punkt_Objekt> getRelevantPOs(
			final MultiContainer_AttributeGroup container) {
		return Streams.stream(container.getPunktObjekts())
				.parallel()
				.filter(po -> PunktObjektExtensions.existLateralDistance(po)
						|| po instanceof FMA_Komponente
						|| po instanceof PZB_Element)
				.filter(po -> po.getPunktObjektTOPKante()
						.stream()
						.anyMatch(potk -> !potk.getIDGEOPunktBerechnet()
								.isEmpty()))
				.toList();
	}

	/**
	 * @param state
	 *            the container type
	 * @param po
	 *            the {@link Punkt_Objekt}
	 * @param potk
	 *            the {@link Punkt_Objekt_TOP_Kante_AttributeGroup} of this
	 *            {@link Punkt_Objekt}
	 * @return the {@link GEOKanteCoordinate}
	 */
	public GEOKanteCoordinate calculateCoordinate(final ContainerType state,
			final Punkt_Objekt po,
			final Punkt_Objekt_TOP_Kante_AttributeGroup potk) {
		List<TopologicalCoordinate> alreadyCalulatedCoordinates = topologicalCoordinates
				.orElse(null);
		if (alreadyCalulatedCoordinates == null) {
			alreadyCalulatedCoordinates = new ArrayList<>();
			topologicalCoordinates = Optional.of(alreadyCalulatedCoordinates);
		}
		final Optional<TopologicalCoordinate> target = alreadyCalulatedCoordinates
				.stream()
				.filter(ele -> ele.state() == state && ele.po() == po
						&& ele.potk() == potk)
				.findFirst();
		if (target.isPresent()) {
			return target.get().coordinate();
		}

		final GEOKanteCoordinate calculateCoordinate = calculateCoordinate(po,
				potk);
		alreadyCalulatedCoordinates.add(new TopologicalCoordinate(state, po,
				potk, calculateCoordinate));
		return calculateCoordinate;
	}

	private GEOKanteCoordinate calculateCoordinate(final Punkt_Objekt po,
			final Punkt_Objekt_TOP_Kante_AttributeGroup potk) {
		try {
			BigDecimal lateralDistance = null;
			final GEOKanteMetadata geoKanteMetaData = alreadyFoundMetaData
					.parallelStream()
					.filter(md -> isBelongToThisMetadata(md, potk))
					.findFirst()
					.orElse(null);
			if (geoKanteMetaData == null) {
				return null;
			}

			if (po instanceof PZB_Element) {
				lateralDistance = PZB_LATERAL_DISTANCE;
			} else if (po instanceof FMA_Komponente) {
				lateralDistance = FMA_LATERAL_DISTANCE;
			}
			if (lateralDistance != null) {
				final ENUMLinksRechts side = getNullableObject(potk,
						point -> point.getSeitlicheLage().getWert())
								.orElse(null);
				if (side != null
						&& side == ENUMLinksRechts.ENUM_LINKS_RECHTS_LINKS) {
					return calculateCoordinate(geoKanteMetaData, potk,
							lateralDistance.negate());
				}
				return calculateCoordinate(geoKanteMetaData, potk,
						lateralDistance);

			}
			return calculateCoordinate(geoKanteMetaData, potk);
		} catch (final Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	private static boolean isBelongToThisMetadata(final GEOKanteMetadata md,
			final Punkt_Objekt_TOP_Kante_AttributeGroup potk) {
		final Basis_Objekt geoArt = md.getGeoKante().getIDGEOArt().getValue();
		if (!geoArt.equals(potk.getIDTOPKante().getValue())) {
			return false;
		}

		final BigDecimal distance = potk.getAbstand().getWert();
		final BigDecimal start = md.getStart();
		if (distance.compareTo(start) < 0) {
			return false;
		}

		final BigDecimal end = md.getEnd();
		if (distance.compareTo(end) <= 0) {
			return true;
		}

		// Fall the GEOKanteMetadata is the last Geo_Kante of the Top_Kante and
		// the length of this Top_Kante greater than the sum Geo_Kanten length
		final GEO_Knoten endGeoKnoten = GeoKanteExtensions
				.getOpposite(md.getGeoKante(), md.getGeoKnoten());
		final TOP_Knoten topKnotenB = TopKanteExtensions
				.getTOPKnotenB(potk.getIDTOPKante().getValue());
		if (TopKnotenExtensions.getGEOKnoten(topKnotenB).equals(endGeoKnoten)) {
			final BigDecimal diff = distance.subtract(end);
			return diff.compareTo(BigDecimal
					.valueOf(ToolboxConstants.TOP_GEO_LENGTH_TOLERANCE)) <= 0;
		}
		return false;
	}

	private GEOKanteCoordinate calculateCoordinate(
			final GEOKanteMetadata geoKanteMetadata,
			final Punkt_Objekt_TOP_Kante_AttributeGroup potk) {
		final BigDecimal lateralDistance = pointObjectPositionService
				.getLateralDistance(potk, geoKanteMetadata);
		return calculateCoordinate(geoKanteMetadata, potk, lateralDistance);
	}

	private static GEOKanteCoordinate calculateCoordinate(
			final GEOKanteMetadata geoKanteMetadata,
			final Punkt_Objekt_TOP_Kante_AttributeGroup potk,
			final BigDecimal lateralDistance) {
		ENUMWirkrichtung direction = null;
		if (potk.getWirkrichtung() != null) {
			direction = potk.getWirkrichtung().getWert();
		}
		if (direction == ENUMWirkrichtung.ENUM_WIRKRICHTUNG_BEIDE) {
			direction = ENUMWirkrichtung.ENUM_WIRKRICHTUNG_IN;
		}
		return calculateCoordinate(geoKanteMetadata, potk, lateralDistance,
				direction);
	}

	private static GEOKanteCoordinate calculateCoordinate(
			final GEOKanteMetadata md,
			final Punkt_Objekt_TOP_Kante_AttributeGroup potk,
			final BigDecimal lateralDistance,
			final ENUMWirkrichtung wirkrichtung) {
		final BigDecimal geoLength = BigDecimal
				.valueOf(md.getGeometry().getLength());
		final BigDecimal geoKanteLength = md.getLength();

		BigDecimal localDistance = potk.getAbstand()
				.getWert()
				.subtract(md.getStart());

		// In the case, that the TOP_Kante length is greater than the sum of
		// GEO_Kante length and the different value is in acceptable range, then
		// take end distance of GeoKanteMetadata
		if (potk.getAbstand()
				.getWert()
				.compareTo(TopKanteExtensions
						.getLaenge(potk.getIDTOPKante().getValue())) == 0
				&& potk.getAbstand().getWert().compareTo(md.getEnd()) > 0) {
			final BigDecimal diff = potk.getAbstand()
					.getWert()
					.subtract(md.getEnd());
			if (diff.compareTo(BigDecimal
					.valueOf(ToolboxConstants.TOP_GEO_LENGTH_TOLERANCE)) <= 0) {
				localDistance = md.getLength();
			}
		}

		final BigDecimal scaleDistance = localDistance.doubleValue() != 0
				? localDistance.multiply(geoLength)
						.divide(geoKanteLength,
								ToolboxConstants.ROUNDING_TO_PLACE,
								RoundingMode.HALF_UP)
				: BigDecimal.ZERO;

		final SegmentPosition position = getSegmentPosition(md.getGeometry(),
				getCoordinate(md.getGeoKnoten()), scaleDistance);
		final LineSegment tangent = getTangent(md.getGeoKante(), position);
		final GeoPosition geoPosition = getCoordinate(tangent, position,
				lateralDistance, wirkrichtung);
		return new GEOKanteCoordinate(geoPosition, md,
				getCRS(md.getGeoKnoten()));
	}

	private static GEOKanteMetadata getGeoKanteMetaData(
			final Punkt_Objekt_TOP_Kante_AttributeGroup potk) {
		try {
			final TOP_Kante topkante = potk.getIDTOPKante().getValue();
			final GeoKnotenRange relevantGeoKnotenRange = getRelevantGeoKnotenRange(
					potk);

			final GEO_Kante geoKante = relevantGeoKnotenRange.getGeoKante();
			final BigDecimal geoArtScalingFactor = BasisObjektExtensions
					.getGeoArtScalingFactor(topkante);
			final BigDecimal geoKanteLength = geoKante.getGEOKanteAllg()
					.getGEOLaenge()
					.getWert()
					.multiply(BigDecimal.ONE.divide(geoArtScalingFactor,
							ToolboxConstants.ROUNDING_TO_PLACE,
							RoundingMode.HALF_UP));
			final LineString geometry = GEOKanteGeometryExtensions
					.defineEdgeGeometry(geoKante,
							new GeometryCalculationOptionsBuilder()
									.setChordOptions(
											new GeometryOptionsBuilder()
													.setStepSize(0.0001)
													.build())
									.build());
			final List<Bereich_Objekt> bereichObjekts = Streams
					.stream(BasisAttributExtensions.getContainer(topkante)
							.getBereichObjekt())
					.toList();
			return new GEOKanteMetadata(geoKante,
					relevantGeoKnotenRange.startDistance(), geoKanteLength,
					bereichObjekts, topkante,
					relevantGeoKnotenRange.startNode(), geometry);
		} catch (final NullPointerException | IllegalArgumentException e) {
			return null;
		}
	}

	private static GeoKnotenRange getRelevantGeoKnotenRange(
			final Punkt_Objekt_TOP_Kante_AttributeGroup potk) {
		final TOP_Kante topKante = potk.getIDTOPKante().getValue();
		final BigDecimal distance = potk.getAbstand().getWert();
		final Deque<Pair<GEO_Knoten, BigDecimal>> geoKnotenDeque = TopKanteExtensions
				.getGeoKnotenWithDistance(topKante);
		if (distance.equals(BigDecimal.ZERO)) {
			// Get two first element of the deque
			return new GeoKnotenRange(geoKnotenDeque.poll(),
					geoKnotenDeque.poll());
		}

		if (distance
				.equals(topKante.getTOPKanteAllg().getTOPLaenge().getWert())) {
			final Pair<GEO_Knoten, BigDecimal> lastNode = geoKnotenDeque
					.pollLast();
			final Pair<GEO_Knoten, BigDecimal> previousLastNode = geoKnotenDeque
					.pollLast();
			return new GeoKnotenRange(previousLastNode, lastNode);
		}

		final List<GeoKnotenRange> ranges = new LinkedList<>();
		for (Pair<GEO_Knoten, BigDecimal> currentNode; (currentNode = geoKnotenDeque
				.poll()) != null && !geoKnotenDeque.isEmpty();) {
			final Pair<GEO_Knoten, BigDecimal> nextNode = geoKnotenDeque
					.element();
			ranges.add(new GeoKnotenRange(currentNode, nextNode));
		}

		return ranges.stream()
				.filter(r -> r.contain(distance))
				.findFirst()
				.orElse(null);
	}

	private static GEO_Punkt getSameCRSGEOPunkt(
			final Punkt_Objekt_TOP_Kante_AttributeGroup potk,
			final ENUMGEOKoordinatensystem crs) {
		return potk.getIDGEOPunktBerechnet()
				.stream()
				.map(e -> e.getValue())
				.filter(Objects::nonNull)
				.filter(gp -> {
					final ENUMGEOKoordinatensystem geoPunktCRS = getNullableObject(
							gp,
							e -> e.getGEOPunktAllg()
									.getGEOKoordinatensystem()
									.getWert()).orElse(null);
					return geoPunktCRS != null && geoPunktCRS.equals(crs);
				})
				.findFirst()
				.orElse(null);
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
				.getIDGEOPunktBerechnet()
				.stream()
				.filter(idgp -> idgp.getValue().equals(gp))
				.findFirst()
				.orElse(null);
		if (errorObject == null) {
			throw new IllegalArgumentException(String.format(
					"GEO_Punkt: %s gehört nicht zu Punkt_Objekt: %s",
					gp.getIdentitaet().getWert(),
					po.getIdentitaet().getWert()));
		}
		return createGeoCoordinateError(errorObject,
				"Die Koordinaten des referenzierten GEO_Punkt: {GEO_PUNKT_ID} weichen mehr als {TOLERANT} cm von der topologisch definierten Position ab. (Differenz: {DIFF} cm)",
				Map.of("GEO_PUNKT_ID", errorObject.getWert(), "TOLERANT",
						new DecimalFormat("0.00").format(TOLERANT * 100),
						"DIFF", new DecimalFormat("0.00").format(diff * 100)));
	}

	private PlazError createGeoCoordinateError(final EObject object,
			final String message, final Map<String, String> data) {
		final PlazError plazError = PlazFactory.eINSTANCE.createPlazError();
		plazError.setObject(object);
		plazError.setSeverity(ValidationSeverity.WARNING);
		plazError.setType(checkType());
		plazError
				.setMessage(StringSubstitutor.replace(message, data, "{", "}"));
		return plazError;
	}

	@Override
	public String checkType() {
		return "Verortungsvergleich";
	}

	@Override
	public String getDescription() {
		return "Die topologische Verortung der berechneten Geo-Koordinaten sind plausibilisiert";
	}

	@Override
	public String getGeneralErrMsg() {
		return "Die topologische Verortung des Punkt-Objekts stimmt nicht mit den berechneten Geo-Koordinaten überein.";
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
				.stream()
				.map(gp -> gp.getValue())
				.filter(Objects::nonNull)
				.toList();
		if (givenGeoPunkts.isEmpty()) {
			return false;
		}
		final Iterable<GEO_Punkt> notDistinctBy = IterableExtensions
				.notDistinctBy(givenGeoPunkts,
						gp -> EObjectExtensions.getNullableObject(gp,
								e -> e.getGEOPunktAllg()
										.getGEOKoordinatensystem()
										.getWert()));
		return notDistinctBy.iterator().hasNext();
	}

	/**
	 * @return calculated topological coordinate of Punkt_Objekt
	 */
	public List<TopologicalCoordinate> getTopologischeCoordinaten() {
		return topologicalCoordinates.orElse(null);
	}
}
