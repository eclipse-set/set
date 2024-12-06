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
import static org.eclipse.set.ppmodel.extensions.EObjectExtensions.getNullableObject;
import static org.eclipse.set.ppmodel.extensions.GeoKanteExtensions.getOpposite;
import static org.eclipse.set.ppmodel.extensions.GeoKanteExtensions.getTangent;
import static org.eclipse.set.ppmodel.extensions.GeoKnotenExtensions.getCRS;
import static org.eclipse.set.ppmodel.extensions.GeoKnotenExtensions.getGeoKantenOnParentKante;
import static org.eclipse.set.ppmodel.extensions.PunktObjektTopKanteExtensions.getTopKante;
import static org.eclipse.set.ppmodel.extensions.TopKanteExtensions.getTOPKnotenA;
import static org.eclipse.set.ppmodel.extensions.TopKnotenExtensions.getGEOKnoten;
import static org.eclipse.set.ppmodel.extensions.geometry.GEOKanteGeometryExtensions.defineEdgeGeometry;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.StreamSupport;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.set.basis.Pair;
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
import org.eclipse.set.model.planpro.Basisobjekte.Basis_Objekt;
import org.eclipse.set.model.planpro.Basisobjekte.Bereich_Objekt;
import org.eclipse.set.model.planpro.Basisobjekte.Punkt_Objekt;
import org.eclipse.set.model.planpro.Basisobjekte.Punkt_Objekt_TOP_Kante_AttributeGroup;
import org.eclipse.set.model.planpro.Geodaten.GEO_Kante;
import org.eclipse.set.model.planpro.Geodaten.GEO_Knoten;
import org.eclipse.set.model.planpro.Geodaten.Strecke;
import org.eclipse.set.model.planpro.Geodaten.Strecke_Punkt;
import org.eclipse.set.model.planpro.Geodaten.TOP_Kante;
import org.eclipse.set.model.planpro.Geodaten.TOP_Knoten;
import org.eclipse.set.model.planpro.PlanPro.PlanPro_Schnittstelle;
import org.eclipse.set.ppmodel.extensions.GeoKanteExtensions;
import org.eclipse.set.ppmodel.extensions.GeoKnotenExtensions;
import org.eclipse.set.ppmodel.extensions.PlanProSchnittstelleExtensions;
import org.eclipse.set.ppmodel.extensions.StreckeExtensions;
import org.eclipse.set.ppmodel.extensions.TopKanteExtensions;
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup;
import org.eclipse.set.ppmodel.extensions.utils.CacheUtils;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineSegment;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.operation.distance.DistanceOp;
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
		return new GEOKanteCoordinate(coordinate, md,
				getCRS(md.getGeoKnoten()));
	}

	@Override
	public GEOKanteMetadata getGeoKanteAt(final TOP_Kante topKante,
			final TOP_Knoten topKnoten, final BigDecimal distance) {
		final List<GEOKanteMetadata> geoMetadatas = getGeoKanteMetadatas(
				topKante, topKnoten);
		final Optional<GEOKanteMetadata> result = geoMetadatas.stream()
				.filter(md -> md.getStart().compareTo(distance) <= 0
						&& md.getEnd().compareTo(distance) >= 0)
				.findFirst();
		return result.orElse(null);
	}

	@Override
	public List<GEOKanteMetadata> getTopKantenMetaData(
			final TOP_Kante topKante) {
		return getGeoKanteMetadatas(topKante,
				topKante.getIDTOPKnotenA().getValue());
	}

	@Override
	public List<GEOKanteMetadata> getStreckeMetaData(final Strecke strecke) {
		final String key = CacheUtils.getCacheKey(strecke);
		return geoKanteMetadas.computeIfAbsent(key, k -> {
			try {
				final Strecke_Punkt[] startEnd = StreckeExtensions
						.getStartEnd(strecke);
				return getGeoKantenMetadata(strecke,
						startEnd[0].getIDGEOKnoten().getValue(),
						Collections.emptyList());
			} catch (final Exception e) {
				return null;
			}

		});
	}

	@Override
	public GEOKanteMetadata getGeoKanteMetaData(final GEO_Kante geoKante) {
		final Basis_Objekt geoArt = geoKante.getIDGEOArt().getValue();
		final List<GEOKanteMetadata> metaDatas = switch (geoArt) {
		case final TOP_Kante topKante -> getTopKantenMetaData(topKante);
		case final Strecke strecke -> getStreckeMetaData(strecke);
		default -> throw new IllegalArgumentException();
		};
		return metaDatas.stream().filter(md -> md.getGeoKante() == geoKante)
				.findFirst().orElse(null);
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
	private List<GEOKanteMetadata> getGeoKanteMetadatas(final TOP_Kante topEdge,
			final TOP_Knoten start) {
		final String key = CacheUtils.getCacheKey(topEdge, start);

		return geoKanteMetadas.computeIfAbsent(key, k -> {
			final List<Bereich_Objekt> bereichObjekt = StreamSupport.stream(
					getContainer(start).getBereichObjekt().spliterator(), false)
					.toList();
			return getGeoKantenMetadata(topEdge, getGEOKnoten(start),
					bereichObjekt);
		});
	}

	@Override
	public Pair<GEOKanteCoordinate, BigDecimal> getProjectionCoordinateOnTopKante(
			final Coordinate coor, final TOP_Kante topEdge) {
		return getProjectionCoordinate(coor, topEdge);

	}

	private Pair<GEOKanteCoordinate, BigDecimal> getProjectionCoordinate(
			final Coordinate coordinate, final Basis_Objekt object) {
		try {
			final Pair<GEOKanteMetadata, Coordinate> projectionCoorAndGeoKante = getClosestProjection(
					coordinate, object);
			if (projectionCoorAndGeoKante == null) {
				throw new NullPointerException();
			}

			// Find the GEOKanteSegment, which the projection lie on
			final GEOKanteMetadata metadata = projectionCoorAndGeoKante
					.getFirst();
			final Coordinate projectionCoor = projectionCoorAndGeoKante
					.getSecond();
			// Determine topological distance from start of Top_Kante to the
			// projection
			final BigDecimal projectionTopDistance = getCoordinateTopDistance(
					metadata, projectionCoor);
			final GEOKanteSegment relevantSegments = metadata.getSegments()
					.stream()
					.filter(segment -> projectionTopDistance
							.compareTo(segment.getStart()) >= 0
							&& projectionTopDistance
									.compareTo(segment.getEnd()) <= 0)
					.findFirst().orElse(null);
			if (relevantSegments == null) {
				throw new IllegalArgumentException();
			}
			return new Pair<>(
					new GEOKanteCoordinate(projectionCoor, metadata,
							getCRS(metadata.getGeoKnoten())),
					projectionTopDistance);
		} catch (final IllegalArgumentException | NullPointerException e) {
			logger.error(
					"Can\'t find projection of Coordinate: ({}, {}) on Kante {}", //$NON-NLS-1$
					Double.valueOf(coordinate.x), Double.valueOf(coordinate.y),
					object.getIdentitaet().getWert());
			return null;
		}
	}

	private static BigDecimal getCoordinateTopDistance(
			final GEOKanteMetadata metadata, final Coordinate coordinate)
			throws IllegalArgumentException {
		BigDecimal distance = metadata.getStart();
		Coordinate previousCoordinate = null;
		final Queue<Coordinate> geoCoordinates = new ArrayDeque<>();
		geoCoordinates
				.addAll(Arrays.asList(metadata.getGeometry().getCoordinates()));
		final GeometryFactory geometryFactory = new GeometryFactory();
		// Run thought coordinates of Geo_Kante geometry
		for (Coordinate currentCoordinate; (currentCoordinate = geoCoordinates
				.poll()) != null;) {
			if (previousCoordinate == null) {
				previousCoordinate = currentCoordinate;
				// Check if the point is on the segment

			} else {
				final double distanceToCurrentCoord = previousCoordinate
						.distance(currentCoordinate);
				final double distanceToTargetCoord = previousCoordinate
						.distance(coordinate);
				final boolean isOnLine = DistanceOp.isWithinDistance(
						geometryFactory.createLineString(new Coordinate[] {
								previousCoordinate, currentCoordinate }),
						geometryFactory.createPoint(coordinate),
						GEO_LENGTH_DEVIATION_TOLERANCE_RELATIVE);
				// When the target point lies between previous and
				// current coordinate, return the distance to target
				// point
				if (isOnLine
						&& distanceToCurrentCoord >= distanceToTargetCoord) {
					return distance.add(BigDecimal
							.valueOf(distanceToTargetCoord)
							.round(new MathContext(
									ToolboxConstants.ROUNDING_TO_PLACE)));
				}
				distance = distance
						.add(BigDecimal.valueOf(distanceToCurrentCoord));
				previousCoordinate = currentCoordinate;
			}

		}
		throw new IllegalArgumentException(String.format(
				"The coordinate (%f, %f) not lie on the TOP_Kante: %s", //$NON-NLS-1$
				Double.valueOf(coordinate.x), Double.valueOf(coordinate.y),
				metadata.getGeoKante().getIDGEOArt().getWert()));
	}

	@Override
	public Pair<GEOKanteCoordinate, BigDecimal> getProjectionCoordinateOnStrecke(
			final Coordinate coordinate, final Strecke strecke) {
		return getProjectionCoordinate(coordinate, strecke);
	}

	/**
	 * Find the projection coordinate and the GEO_Kante, which the projection
	 * lie on
	 * 
	 * @param coordinate
	 *            the coordinate
	 * @param topEdge
	 *            the Top_Kante
	 * @return Pair<GEOKanteMetadata, Coordinate>
	 */
	private Pair<GEOKanteMetadata, Coordinate> getClosestProjection(
			final Coordinate coordinate, final Basis_Objekt object) {
		final GeometryFactory geometryFactory = new GeometryFactory();
		final List<GEOKanteMetadata> metadatas = switch (object) {
		case final TOP_Kante topKante -> getTopKantenMetaData(topKante);
		case final Strecke strecke -> getStreckeMetaData(strecke);
		default -> throw new IllegalArgumentException();
		};
		// Find out the GEO_Kante closest to the coordinate
		final Pair<GEOKanteMetadata, DistanceOp> metaDataWithDistance = metadatas
				.stream().map(geoKante -> {
					final LineString geometry = geoKante.getGeometry();
					final DistanceOp distanceOp = new DistanceOp(geometry,
							geometryFactory.createPoint(coordinate));
					return new Pair<>(geoKante, distanceOp);
				}).min((fisrt, second) -> {
					final double firstDistance = fisrt.getSecond().distance();
					final double seconDistance = second.getSecond().distance();
					return Double.compare(firstDistance, seconDistance);
				}).orElse(null);
		if (metaDataWithDistance == null) {
			return null;
		}
		// Get the projection of the Point on this GEO_Kante
		final Coordinate[] nearestPoints = metaDataWithDistance.getSecond()
				.nearestPoints();
		if (nearestPoints.length == 0) {
			return null;
		}
		return new Pair<>(metaDataWithDistance.getFirst(), nearestPoints[0]);
	}

	/**
	 * Calculates the GEOKanteMetadata objects for a given TOP_Kante
	 * 
	 * @param geoArt
	 *            the TOP_Kante
	 * @param topKnoten
	 *            the TOP_Knoten to start from
	 * @param bereichObjekte
	 *            a list of Bereich_Objekte to consider for matching to the
	 *            individual GEO_Kanten
	 * @return a list of GEOKanteMetadata for the TOP_Kante
	 */
	private List<GEOKanteMetadata> getGeoKantenMetadata(
			final Basis_Objekt geoArt, final GEO_Knoten startKnoten,
			final List<Bereich_Objekt> bereichObjekte) {

		final BigDecimal distanceScalingFactor = getScalingFactor(geoArt);
		BigDecimal distance = BigDecimal.ZERO;
		GEO_Knoten geoKnoten = startKnoten;
		GEO_Kante geoKante = null;
		final List<GEOKanteMetadata> geoKanteMetadata = new ArrayList<>();

		// Traverse the TOP_Kante from the first GEO_Knoten along until the end
		// of the TOP_Kante is reached
		while (true) {
			final List<GEO_Kante> geoKanten = getGeoKantenOnParentKante(
					geoKnoten, geoArt);
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
			final LineString geometry = getGeometry(geoKante);
			final GEOKanteMetadata metadata = switch (geoArt) {
			case final TOP_Kante topKante -> new GEOKanteMetadata(geoKante,
					distance, geoKanteLength, bereichObjekte, topKante,
					geoKnoten, geometry);
			case final Strecke streck -> new GEOKanteMetadata(geoKante,
					distance, geoKnoten, geometry);
			default -> throw new IllegalArgumentException(
					"Unexpected value: " + geoArt); //$NON-NLS-1$
			};
			geoKanteMetadata.add(metadata);

			distance = distance.add(geoKanteLength);

			// Get the next GEO_Knoten (on the other end of the GEO_Kante)
			geoKnoten = getOpposite(geoKante, geoKnoten);
		}
	}

	private static BigDecimal getScalingFactor(final Basis_Objekt geoArt)
			throws IllegalArgumentException {
		// In some planning data there is a minor deviation between the length
		// of a
		// TOP_Kante and the total length of all GEO_Kanten on the TOP_Kante
		// As objects are positioned on a TOP_Kante but a GEO_Kante is used
		// to determine the geographical position, calculate a scaling factor
		final List<GEO_Kante> geoKantenOnGeoArt = getGeoKanten(geoArt);

		BigDecimal geoLength = BigDecimal.ZERO;
		for (final GEO_Kante geoKante : geoKantenOnGeoArt) {
			try {
				geoLength = geoLength.add(
						geoKante.getGEOKanteAllg().getGEOLaenge().getWert());
			} catch (final NullPointerException e) {
				logger.error("Geo_Kante: {} missing Geo_Laenge", //$NON-NLS-1$
						geoKante.getIdentitaet().getWert());
			}
		}

		final BigDecimal geoArtLength = getGeoArtLength(geoArt);
		final BigDecimal difference = geoLength.subtract(geoArtLength).abs();
		final BigDecimal tolerance = BigDecimal
				.valueOf(GEO_LENGTH_DEVIATION_TOLERANCE)
				.max(geoArtLength.multiply(BigDecimal
						.valueOf(GEO_LENGTH_DEVIATION_TOLERANCE_RELATIVE)));
		// Warn if the length difference is too big
		if (difference.compareTo(tolerance) > 0) {
			logger.debug("lengthGeoArt={}", geoArtLength); //$NON-NLS-1$
			logger.debug("lengthGeoKanten={}", geoLength); //$NON-NLS-1$
			logger.debug("geoKantenOnGeoArt={}", //$NON-NLS-1$
					Integer.valueOf(geoKantenOnGeoArt.size()));
			logger.warn(
					"Difference of GEO Kanten length and GeoArt length for GeoArt {} greater than tolerance {} ({}).", //$NON-NLS-1$
					geoArt.getIdentitaet().getWert(), tolerance, difference);
		}

		if (geoLength.compareTo(BigDecimal.ZERO) <= 0
				|| geoArtLength.compareTo(BigDecimal.ZERO) <= 0) {
			return BigDecimal.ONE;
		}
		final BigDecimal scale = geoLength.divide(geoArtLength,
				ToolboxConstants.ROUNDING_TO_PLACE, RoundingMode.HALF_UP);
		return scale.compareTo(BigDecimal.ZERO) > 0 ? scale : BigDecimal.ONE;
	}

	private static List<GEO_Kante> getGeoKanten(final Basis_Objekt geoArt) {
		return switch (geoArt) {
		case final TOP_Kante topKante -> TopKanteExtensions
				.getGeoKanten(topKante);
		case final Strecke strecke -> StreckeExtensions.getGeoKanten(strecke);
		default -> throw new IllegalArgumentException(
				"Unexpected value: " + geoArt); //$NON-NLS-1$
		};
	}

	private static BigDecimal getGeoArtLength(final Basis_Objekt geoArt) {
		return switch (geoArt) {
		case final TOP_Kante topKante -> topKante.getTOPKanteAllg()
				.getTOPLaenge().getWert();
		case final Strecke strecke -> StreckeExtensions
				.getStreckeLength(strecke);
		default -> throw new IllegalArgumentException(
				"Unexpected value: " + geoArt); //$NON-NLS-1$
		};
	}
}
