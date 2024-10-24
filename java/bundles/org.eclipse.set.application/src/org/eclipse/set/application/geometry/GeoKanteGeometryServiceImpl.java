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

import static org.eclipse.set.ppmodel.extensions.BasisAttributExtensions.getContainer;
import static org.eclipse.set.ppmodel.extensions.GeoKanteExtensions.getOpposite;
import static org.eclipse.set.ppmodel.extensions.GeoKanteExtensions.getTangent;
import static org.eclipse.set.ppmodel.extensions.GeoKnotenExtensions.getCRS;
import static org.eclipse.set.ppmodel.extensions.GeoKnotenExtensions.getGeoKantenOnTopKante;
import static org.eclipse.set.ppmodel.extensions.PunktObjektTopKanteExtensions.getTopKante;
import static org.eclipse.set.ppmodel.extensions.TopKanteExtensions.getTOPKnotenA;
import static org.eclipse.set.ppmodel.extensions.TopKnotenExtensions.getGEOKnoten;
import static org.eclipse.set.ppmodel.extensions.geometry.GEOKanteGeometryExtensions.defineEdgeGeometry;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.StreamSupport;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.set.basis.constants.ContainerType;
import org.eclipse.set.basis.constants.Events;
import org.eclipse.set.basis.constants.ToolboxConstants;
import org.eclipse.set.basis.geometry.GEOKanteCoordinate;
import org.eclipse.set.basis.geometry.GEOKanteMetadata;
import org.eclipse.set.basis.geometry.GEOKanteSegment;
import org.eclipse.set.basis.geometry.GeoPosition;
import org.eclipse.set.basis.geometry.Geometries;
import org.eclipse.set.basis.geometry.GeometryException;
import org.eclipse.set.basis.geometry.SegmentPosition;
import org.eclipse.set.basis.graph.DirectedElement;
import org.eclipse.set.core.services.Services;
import org.eclipse.set.core.services.geometry.GeoKanteGeometryService;
import org.eclipse.set.model.planpro.BasisTypen.ENUMWirkrichtung;
import org.eclipse.set.model.planpro.Basisobjekte.Bereich_Objekt;
import org.eclipse.set.model.planpro.Basisobjekte.Punkt_Objekt;
import org.eclipse.set.model.planpro.Basisobjekte.Punkt_Objekt_TOP_Kante_AttributeGroup;
import org.eclipse.set.model.planpro.Geodaten.GEO_Kante;
import org.eclipse.set.model.planpro.Geodaten.GEO_Knoten;
import org.eclipse.set.model.planpro.Geodaten.TOP_Kante;
import org.eclipse.set.model.planpro.Geodaten.TOP_Knoten;
import org.eclipse.set.model.planpro.PlanPro.PlanPro_Schnittstelle;
import org.eclipse.set.ppmodel.extensions.GeoKanteExtensions;
import org.eclipse.set.ppmodel.extensions.GeoKnotenExtensions;
import org.eclipse.set.ppmodel.extensions.PlanProSchnittstelleExtensions;
import org.eclipse.set.ppmodel.extensions.TopKanteExtensions;
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup;
import org.eclipse.set.ppmodel.extensions.utils.CacheUtils;
import org.locationtech.jts.geom.LineSegment;
import org.locationtech.jts.geom.LineString;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation for {@link GeoKanteGeometryService}
 * 
 * @author Truong
 */
@Component(property = {
		EventConstants.EVENT_TOPIC + "=" + Events.TOPMODEL_CHANGED,
		EventConstants.EVENT_TOPIC + "=" + Events.CLOSE_SESSION }, service = {
				GeoKanteGeometryService.class, EventHandler.class })

public class GeoKanteGeometryServiceImpl
		implements GeoKanteGeometryService, EventHandler {
	private Thread findGeometryThread;
	// Acceptable tolerance between the length of all GEO_Kante on a TOP_Kante
	// and the length of the TOP_Kante
	static double GEO_LENGTH_DEVIATION_TOLERANCE = 0.001;

	// The same tolerance, but as a relative value based on the length of the
	// TOP_Kante
	// a value of 0.01 is equivalent to an acceptable deviation of 1% of the
	// TOP_Kante length
	static double GEO_LENGTH_DEVIATION_TOLERANCE_RELATIVE = 0.0001;
	static final Logger logger = LoggerFactory
			.getLogger(GeoKanteGeometryServiceImpl.class);

	private Map<GEO_Kante, LineString> edgeGeometry;
	private Map<String, List<GEOKanteMetadata>> geoKanteMetadas;
	private boolean isProcessComplete = false;

	@Reference
	private EventAdmin eventAdmin;

	/**
	 * Constructor
	 */
	public GeoKanteGeometryServiceImpl() {
		Services.setGeometryService(this);
	}

	@Override
	public void handleEvent(final Event event) {
		final String topic = event.getTopic();
		if (topic.equals(Events.TOPMODEL_CHANGED) && event.getProperty(
				IEventBroker.DATA) instanceof final PlanPro_Schnittstelle schnitstelle) {
			edgeGeometry = new ConcurrentHashMap<>();
			geoKanteMetadas = new ConcurrentHashMap<>();
			findGeometryThread = new Thread(() -> {
				try {
					logger.debug("Start find geometry of GEO_Kante"); //$NON-NLS-1$
					isProcessComplete = false;
					findGeoKanteGeometry(PlanProSchnittstelleExtensions
							.getContainer(schnitstelle, ContainerType.INITIAL));
					findGeoKanteGeometry(PlanProSchnittstelleExtensions
							.getContainer(schnitstelle, ContainerType.FINAL));
					findGeoKanteGeometry(PlanProSchnittstelleExtensions
							.getContainer(schnitstelle, ContainerType.SINGLE));
					isProcessComplete = true;
					final HashMap<String, Object> properties = new HashMap<>();
					properties.put(EventConstants.EVENT_TOPIC,
							Events.FIND_GEOMETRY_PROCESS_DONE);
					eventAdmin.sendEvent(new Event(
							Events.FIND_GEOMETRY_PROCESS_DONE, properties));
					logger.debug(
							"Find geometry of GEO_Kante process is complete"); //$NON-NLS-1$
				} catch (final InterruptedException e) {
					isProcessComplete = true;
					Thread.currentThread().interrupt();
				}
			}, ToolboxConstants.CacheId.GEOKANTE_GEOMETRY);
			findGeometryThread.start();
		}

		if (topic.equals(Events.CLOSE_SESSION) && findGeometryThread != null
				&& findGeometryThread.isAlive()
				&& !findGeometryThread.isInterrupted()) {
			findGeometryThread.interrupt();
			isProcessComplete = false;
			edgeGeometry.clear();
			geoKanteMetadas.clear();
		}
	}

	private void findGeoKanteGeometry(
			final MultiContainer_AttributeGroup container)
			throws InterruptedException {
		if (container == null) {
			return;
		}
		for (final GEO_Kante edge : container.getGEOKante()) {
			if (Thread.currentThread().isInterrupted()) {
				throw new InterruptedException();
			}
			try {
				final LineString geometry = defineEdgeGeometry(edge);
				if (geometry != null) {
					edgeGeometry.put(edge, geometry);
				}
			} catch (final GeometryException | NullPointerException e) {
				logger.warn("Cannot determine geometry for edge {}.", //$NON-NLS-1$
						edge.getIdentitaet().getWert());
			}
		}
	}

	/**
	 * @param edge
	 *            the geo kante
	 * @return the line string of this GEO Kante
	 */
	@Override
	public LineString getGeometry(final GEO_Kante edge) {
		while (!isFindGeometryComplete()) {
			try {
				Thread.sleep(4000);
			} catch (final InterruptedException e) {
				Thread.currentThread().interrupt();
				return null;
			}
		}
		return edgeGeometry.getOrDefault(edge, null);
	}

	/**
	 * @param directedEdge
	 *            the GEO_Kante with direction
	 * @return the line string of this GEO Kante (with the implied direction of
	 *         the GEO Kante)
	 */
	@Override
	public LineString getGeometry(
			final DirectedElement<GEO_Kante> directedEdge) {
		final LineString geometry = getGeometry(directedEdge.getElement());
		if (geometry == null) {
			return null;
		}
		return directedEdge.isForwards() ? geometry : geometry.reverse();
	}

	/**
	 * Check if find geometry process still runing
	 * 
	 * @return true, if the process is done
	 */
	@Override
	public boolean isFindGeometryComplete() {
		return isProcessComplete;
	}

	@Override
	public GEOKanteCoordinate getCoordinateAt(final Punkt_Objekt punktObjekt,
			final BigDecimal distance) {
		final Punkt_Objekt_TOP_Kante_AttributeGroup singlePoint = punktObjekt
				.getPunktObjektTOPKante().getFirst();
		return getCoordinateAt(singlePoint, distance);
	}

	private GEOKanteCoordinate getCoordinateAt(
			final Punkt_Objekt_TOP_Kante_AttributeGroup singlePoint,
			final BigDecimal distance) {
		if (singlePoint == null || singlePoint.getAbstand() == null
				|| singlePoint.getAbstand().getWert() == null) {
			return null;
		}
		final BigDecimal pointDistance = singlePoint.getAbstand().getWert()
				.add(distance);
		final ENUMWirkrichtung direction = getNullableObject(singlePoint,
				p -> p.getWirkrichtung().getWert()).orElse(null);
		final TOP_Kante topKante = getTopKante(singlePoint);
		if (topKante == null) {
			return null;
		}
		final TOP_Knoten start = getTOPKnotenA(topKante);
		final GEOKanteMetadata geoEdgeMd = getGeoKanteAt(topKante, start,
				pointDistance);
		if (geoEdgeMd == null) {
			logger.error(
					"Can't found Geo_Kante by TOP_Knoten: {} of TOP_Kante: {}", //$NON-NLS-1$
					start.getIdentitaet().getWert(),
					topKante.getIdentitaet().getWert());
			return null;
		}
		BigDecimal lateralDistance = BigDecimal.ZERO;
		if (singlePoint.getSeitlicherAbstand() != null
				&& singlePoint.getSeitlicherAbstand().getWert() != null) {
			lateralDistance = singlePoint.getSeitlicherAbstand().getWert();
		}
		if (direction == ENUMWirkrichtung.ENUM_WIRKRICHTUNG_BEIDE
				|| direction == null) {
			return getCoordinate(geoEdgeMd, pointDistance, lateralDistance,
					ENUMWirkrichtung.ENUM_WIRKRICHTUNG_IN);
		}

		return getCoordinate(geoEdgeMd, pointDistance, lateralDistance,
				direction);
	}

	@Override
	public GEOKanteCoordinate getCoordinate(final TOP_Kante topKante,
			final TOP_Knoten start, final BigDecimal distance,
			final BigDecimal lateralDistance,
			final ENUMWirkrichtung wirkrichtung) {
		final GEOKanteMetadata md = getGeoKanteAt(topKante, start, distance);
		return getCoordinate(md, distance, lateralDistance, wirkrichtung);
	}

	@Override
	public GEOKanteCoordinate getCoordinate(final GEOKanteMetadata md,
			final BigDecimal distance, final BigDecimal lateralDistance,
			final ENUMWirkrichtung wirkrichtung) {
		// Find the segment of the GEO_Kante which contains the coordinate
		final GEOKanteSegment segment = md.getContainingSegment(distance);
		if (segment == null) {
			throw new RuntimeException("Missing GEO_Kante segment"); //$NON-NLS-1$
		}
		// If the geometry size is smaller than the GEO_Laenge of the GEO_Kante
		// adjust the distance to fit within the GEO_Kante
		final BigDecimal edgeLength = md.getLength().abs();
		final BigDecimal geoLength = BigDecimal
				.valueOf(md.getGeometry().getLength());
		final BigDecimal localDistance = distance.subtract(md.getStart());
		final BigDecimal scaledDistance = localDistance.doubleValue() != 0
				? localDistance.multiply(geoLength.divide(edgeLength,
						ToolboxConstants.ROUNDING_TO_PLACE,
						RoundingMode.HALF_UP))
				: BigDecimal.ZERO;
		final SegmentPosition position = Geometries.getSegmentPosition(
				md.getGeometry(),
				GeoKnotenExtensions.getCoordinate(md.getGeoKnoten()),
				scaledDistance);
		final LineSegment tangent = getTangent(md.getGeoKante(), position);
		final GeoPosition coordinate = GeoKanteExtensions.getCoordinate(tangent,
				position, lateralDistance, wirkrichtung);
		return new GEOKanteCoordinate(coordinate, segment.getBereichObjekte(),
				getCRS(md.getGeoKnoten()));
	}

	@Override
	public GEOKanteMetadata getGeoKanteAt(final TOP_Kante topKante,
			final TOP_Knoten topKnoten, final BigDecimal distance) {
		final List<GEOKanteMetadata> geoMetadatas = getTOPKanteMetadata(
				topKante, topKnoten);
		final Optional<GEOKanteMetadata> result = geoMetadatas.stream()
				.filter(md -> md.getStart().compareTo(distance) <= 0
						&& md.getEnd().compareTo(distance) >= 0)
				.findFirst();
		return result.orElse(null);
	}

	@Override
	public List<GEOKanteMetadata> getGeoKanten(final TOP_Kante topKante) {
		return getTOPKanteMetadata(topKante,
				topKante.getIDTOPKnotenA().getValue());
	}

	/**
	 * Returns a list of GEOKanteMetadata objects for a given
	 * TOP_Kante/TOP_Knoten pair
	 * 
	 * If possible a cached value is returned, otherwise the value is calculated
	 * and added to the cache
	 * 
	 * @param topKante
	 *            the TOP_Kante
	 * @param topKnoten
	 *            the TOP_Knoten to start from
	 * @return a list of GEOKanteMetadata for the TOP_Kante
	 */
	private List<GEOKanteMetadata> getTOPKanteMetadata(final TOP_Kante topEdge,
			final TOP_Knoten start) {
		final String key = CacheUtils.getCacheKey(topEdge, start);

		return geoKanteMetadas.computeIfAbsent(key, k -> {
			final List<Bereich_Objekt> bereichObjekt = StreamSupport.stream(
					getContainer(start).getBereichObjekt().spliterator(), false)
					.toList();
			return getTOPKanteMetadata(topEdge, start, bereichObjekt);
		});
	}

	/**
	 * Calculates the GEOKanteMetadata objects for a given TOP_Kante
	 * 
	 * @param topKante
	 *            the TOP_Kante
	 * @param topKnoten
	 *            the TOP_Knoten to start from
	 * @param bereichObjekte
	 *            a list of Bereich_Objekte to consider for matching to the
	 *            individual GEO_Kanten
	 * @return a list of GEOKanteMetadata for the TOP_Kante
	 */
	private List<GEOKanteMetadata> getTOPKanteMetadata(final TOP_Kante topKante,
			final TOP_Knoten start, final List<Bereich_Objekt> bereichObjekte) {

		final BigDecimal distanceScalingFactor = getTOPKanteScalingFactor(
				topKante);
		BigDecimal distance = BigDecimal.ZERO;
		GEO_Knoten geoKnoten = getGEOKnoten(start);
		GEO_Kante geoKante = null;
		final List<GEOKanteMetadata> geoKanteMetadata = new ArrayList<>();

		// Traverse the TOP_Kante from the first GEO_Knoten along until the end
		// of the TOP_Kante is reached
		while (true) {
			final List<GEO_Kante> geoKanten = getGeoKantenOnTopKante(geoKnoten,
					topKante);
			geoKanten.remove(geoKante);
			if (geoKanten.isEmpty()) {
				// If there was exactly one GEO_Kante, then we've reached the
				// end of the TOP_Kante
				return geoKanteMetadata;
			}
			geoKante = geoKanten.get(0);
			// Adjust the length of the GEO_Kante on the TOP_Kante
			final BigDecimal geoKanteLength = geoKante.getGEOKanteAllg()
					.getGEOLaenge().getWert()
					.multiply(BigDecimal.ONE.divide(distanceScalingFactor,
							ToolboxConstants.ROUNDING_TO_PLACE,
							RoundingMode.HALF_UP));
			geoKanteMetadata.add(new GEOKanteMetadata(geoKante, distance,
					geoKanteLength, bereichObjekte, topKante, geoKnoten,
					getGeometry(geoKante)));
			distance = distance.add(geoKanteLength);

			// Get the next GEO_Knoten (on the other end of the GEO_Kante)
			geoKnoten = getOpposite(geoKante, geoKnoten);
		}
	}

	private static BigDecimal getTOPKanteScalingFactor(
			final TOP_Kante topKante) {
		// In some planning data there is a minor deviation between the length
		// of a
		// TOP_Kante and the total length of all GEO_Kanten on the TOP_Kante
		// As objects are positioned on a TOP_Kante but a GEO_Kante is used
		// to determine the geographical position, calculate a scaling factor
		final List<GEO_Kante> geoKantenOnTopKante = TopKanteExtensions
				.getGeoKanten(topKante);

		BigDecimal geoLength = BigDecimal.ZERO;
		for (final GEO_Kante geoKante : geoKantenOnTopKante) {
			try {
				geoLength = geoLength.add(
						geoKante.getGEOKanteAllg().getGEOLaenge().getWert());
			} catch (final NullPointerException e) {
				logger.error("Geo_Kante: {} missing Geo_Laenge", //$NON-NLS-1$
						geoKante.getIdentitaet().getWert());
			}
		}

		final BigDecimal topLength = topKante.getTOPKanteAllg().getTOPLaenge()
				.getWert();
		final BigDecimal difference = geoLength.subtract(topLength).abs();
		final BigDecimal tolerance = BigDecimal
				.valueOf(GEO_LENGTH_DEVIATION_TOLERANCE)
				.max(topLength.multiply(BigDecimal
						.valueOf(GEO_LENGTH_DEVIATION_TOLERANCE_RELATIVE)));
		// Warn if the length difference is too big
		if (difference.compareTo(tolerance) > 0) {
			logger.debug("lengthTopKante={}", topLength); //$NON-NLS-1$
			logger.debug("lengthGeoKanten={}", geoLength); //$NON-NLS-1$
			logger.debug("geoKantenOnTopKante={}", //$NON-NLS-1$
					Integer.valueOf(geoKantenOnTopKante.size()));
			logger.warn(
					"Difference of GEO Kanten length and TOP Kante length for TOP Kante {} greater than tolerance {} ({}).", //$NON-NLS-1$
					topKante.getIdentitaet().getWert(), tolerance, difference);
		}

		if (geoLength.compareTo(BigDecimal.ZERO) <= 0
				|| topLength.compareTo(BigDecimal.ZERO) <= 0) {
			return BigDecimal.ONE;
		}
		final BigDecimal scale = geoLength.divide(topLength,
				ToolboxConstants.ROUNDING_TO_PLACE, RoundingMode.HALF_UP);
		return scale.compareTo(BigDecimal.ZERO) > 0 ? scale : BigDecimal.ONE;
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
