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
package org.eclipse.set.feature.table.pt1.ssks;

import static org.eclipse.set.ppmodel.extensions.BasisAttributExtensions.getContainer;
import static org.eclipse.set.ppmodel.extensions.EObjectExtensions.getNullableObject;
import static org.eclipse.set.ppmodel.extensions.PunktObjektExtensions.getSinglePoint;
import static org.eclipse.set.ppmodel.extensions.SignalExtensions.signalRahmen;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.set.basis.geometry.GeoPosition;
import org.eclipse.set.model.planpro.BasisTypen.ENUMLinksRechts;
import org.eclipse.set.model.planpro.BasisTypen.ENUMWirkrichtung;
import org.eclipse.set.model.planpro.Basisobjekte.Punkt_Objekt_TOP_Kante_AttributeGroup;
import org.eclipse.set.model.planpro.Signale.ENUMBefestigungArt;
import org.eclipse.set.model.planpro.Signale.Signal;
import org.eclipse.set.model.planpro.Signale.Signal_Befestigung;
import org.eclipse.set.ppmodel.extensions.GeoKanteExtensions;
import org.eclipse.set.ppmodel.extensions.PunktObjektExtensions;
import org.eclipse.set.ppmodel.extensions.PunktObjektTopKanteExtensions;
import org.eclipse.set.ppmodel.extensions.SignalRahmenExtensions;
import org.eclipse.set.ppmodel.extensions.geometry.GEOKanteGeometryExtensions;
import org.eclipse.set.utils.math.DoubleExtensions;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;

import com.google.common.collect.Streams;

/**
 * Helper class for determine signal side distance. Not a Java record due to
 * Xtend limitations
 */
public class SignalSideDistance {

	/**
	 * Helper class for determine side distance
	 */
	public static class SideDistance {
		Optional<Long> distanceToMainTrack;
		Optional<Long> distanceToNeighborTrack;
		Optional<ENUMLinksRechts> position;

		/**
		 * @return the distance from signal to main track
		 */
		public Double getDistanceToMainTrack() {
			if (distanceToMainTrack.isPresent()) {
				return Double.valueOf(
						Math.abs(distanceToMainTrack.get().doubleValue()));
			}
			return null;
		}

		/**
		 * @return the distance from signal to neighbor track
		 */
		public Double getDistanceToNeighborTrack() {
			if (distanceToNeighborTrack.isEmpty()) {
				return null;
			}
			return Double.valueOf(distanceToNeighborTrack.get().doubleValue());
		}

		/**
		 * @param distanceToMainTrack
		 *            the distance from signal to main track
		 * @param distanceToNeighborTrack
		 *            the distance from main track to neighbor track
		 * @param position
		 *            the position of signal
		 */
		public SideDistance(final Optional<Long> distanceToMainTrack,
				final Optional<Long> distanceToNeighborTrack,
				final Optional<ENUMLinksRechts> position) {
			this.distanceToMainTrack = distanceToMainTrack;
			this.distanceToNeighborTrack = distanceToNeighborTrack;
			this.position = position;
		}

		@SuppressWarnings({ "nls", "boxing" })
		@Override
		public String toString() {
			if (distanceToMainTrack.isEmpty() && position.isEmpty()) {
				return "";
			}
			final StringBuilder builder = new StringBuilder();
			builder.append(distanceToMainTrack.isPresent()
					? DoubleExtensions.toTableDecimal(
							Math.abs(distanceToMainTrack.get().doubleValue()))
					: "x");
			if (distanceToNeighborTrack.isPresent()
					&& distanceToNeighborTrack.get().doubleValue() > 0) {
				builder.append(" (");
				builder.append(DoubleExtensions.toTableDecimal(
						distanceToNeighborTrack.get().doubleValue()));
				builder.append(")");
			}
			return builder.toString();
		}
	}

	final Signal signal;
	private final Set<SideDistance> sideDistances = new HashSet<>();
	private final List<ENUMBefestigungArt> relevantMastType;

	/**
	 * Max distance from main track to neighbor track
	 */
	private static final long MAX_DISTANCE_TO_NEIGHBOR = 8000;

	/**
	 * @param signal
	 *            the Signal
	 * @param relevantMastType
	 *            the relevant mast type for calculate side distance
	 */
	public SignalSideDistance(final Signal signal,
			final List<ENUMBefestigungArt> relevantMastType) {
		this.signal = signal;
		this.relevantMastType = relevantMastType;
		getSideDistance();
	}

	/**
	 * @return distances of the signal to tracks on right side
	 */
	public Set<SideDistance> getSideDistancesRight() {
		return sideDistances.stream()
				.filter(e -> e.position.isPresent() && e.position
						.get() == ENUMLinksRechts.ENUM_LINKS_RECHTS_RECHTS)
				.collect(Collectors.toSet());
	}

	/**
	 * @return distances of the signal to tracks on left side
	 */
	public Set<SideDistance> getSideDistancesLeft() {
		return sideDistances.stream()
				.filter(e -> e.position.isPresent() && e.position
						.get() == ENUMLinksRechts.ENUM_LINKS_RECHTS_LINKS)
				.collect(Collectors.toSet());
	}

	/**
	 * Determine distance signal to main and neighbor track
	 * 
	 * @throws IllegalArgumentException
	 *             the {@link IllegalArgumentException}
	 * @throws NullPointerException
	 *             the {@link NullPointerException}
	 * @throws RuntimeException
	 *             the {@link RuntimeException}
	 * 
	 */
	private void getSideDistance() throws IllegalArgumentException,
			NullPointerException, RuntimeException {
		final Set<Signal_Befestigung> signalBefestigung = getSignalBefestigung();
		final Set<Punkt_Objekt_TOP_Kante_AttributeGroup> potks = signalBefestigung
				.stream()
				.flatMap(befestigung -> PunktObjektExtensions
						.getSinglePoints(befestigung)
						.stream()
						.filter(Objects::nonNull))
				.collect(Collectors.toSet());
		potks.forEach(potk -> {
			final ENUMWirkrichtung direction = getSinglePoint(signal)
					.getWirkrichtung()
					.getWert();
			final double signalRotation = PunktObjektExtensions
					.rotation(signal);
			final SideDistance result = determinSideDistanceValue(potk,
					direction, signalRotation);
			if (result == null) {
				return;
			}

			dertermineSignalPosition(result, direction);
		});
	}

	/**
	 * Get the side distance of element and the distance between two track,
	 * which the element lie between
	 * 
	 * @param potk
	 *            the Punkt_Objekt_Top_Kante of the owner element
	 * @param direction
	 *            the direction of the owner element
	 * @param rotation
	 *            the rotation of the owner element
	 * @return <SideDistance, DistanceBetweenTrack>
	 */
	@SuppressWarnings("boxing")
	public static SideDistance determinSideDistanceValue(
			final Punkt_Objekt_TOP_Kante_AttributeGroup potk,
			final ENUMWirkrichtung direction, final double rotation) {
		final Optional<Long> sideDistance = getNullableObject(potk,
				p -> Math.round(p.getSeitlicherAbstand().getWert().doubleValue()
						* 1000));
		final Optional<ENUMLinksRechts> position = getNullableObject(potk,
				p -> p.getSeitlicheLage().getWert());
		if (sideDistance.isEmpty() && position.isEmpty()) {
			return new SideDistance(Optional.empty(), Optional.empty(),
					Optional.empty());
		}
		final long distanceFromPoint = MAX_DISTANCE_TO_NEIGHBOR
				- Math.abs(sideDistance.orElse((long) 0));
		final int perpendicularRotation = getPerpendicularRotation(sideDistance,
				direction, position);

		if (distanceFromPoint <= 0) {
			return new SideDistance(sideDistance, Optional.empty(), position);
		}

		double opposideDistance = 0.0;
		final GeoPosition geoPosition = PunktObjektTopKanteExtensions
				.getCoordinate(potk);
		try {
			opposideDistance = getOpposideDistance(potk, geoPosition,
					rotation + perpendicularRotation,
					distanceFromPoint / 1000.0f);
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
		final long distanceBetweenTrack = opposideDistance > 0
				? Math.abs(sideDistance.orElse((long) 0))
						+ Math.round(opposideDistance * 1000)
				: 0;
		return new SideDistance(sideDistance, Optional.of(distanceBetweenTrack),
				position);
	}

	private Set<Signal_Befestigung> getSignalBefestigung() {
		return Streams.stream(signalRahmen(signal)).map(rahmen -> {
			final Iterator<Signal_Befestigung> signalBefestigungIterator = SignalRahmenExtensions
					.getSignalBefestigungIterator(rahmen);
			return Streams.stream(signalBefestigungIterator)
					.filter(befestigung -> {
						final ENUMBefestigungArt befestigungArt = getNullableObject(
								befestigung,
								e -> e.getSignalBefestigungAllg()
										.getBefestigungArt()
										.getWert()).orElse(null);
						return befestigungArt != null
								&& relevantMastType.contains(befestigungArt);
					})
					.findFirst()
					.orElse(null);
		}).filter(Objects::nonNull).collect(Collectors.toSet());
	}

	private static int getPerpendicularRotation(
			final Optional<Long> sideDistance, final ENUMWirkrichtung direction,
			final Optional<ENUMLinksRechts> position)
			throws IllegalArgumentException {
		final Optional<Boolean> isPositiveSideDistance = sideDistance
				.isPresent()
						? Optional.of(Boolean
								.valueOf(sideDistance.get().doubleValue() >= 0))
						: Optional.empty();
		if (isPositiveSideDistance.isEmpty() && position.isEmpty()) {
			throw new IllegalArgumentException(
					"The Punkt_Objekt haven't either side distcane or side position"); //$NON-NLS-1$
		}
		switch (direction) {
			case ENUM_WIRKRICHTUNG_IN:
				if (isPositiveSideDistance.isPresent()
						&& isPositiveSideDistance.get().booleanValue()
						|| position.isPresent() && position
								.get() == ENUMLinksRechts.ENUM_LINKS_RECHTS_RECHTS) {
					return 90;
				}
				return -90;
			case ENUM_WIRKRICHTUNG_GEGEN:
				if (isPositiveSideDistance.isPresent()
						&& isPositiveSideDistance.get().booleanValue()
						|| position.isPresent() && position
								.get() == ENUMLinksRechts.ENUM_LINKS_RECHTS_LINKS) {
					return -90;
				}
				return 90;
			default: {
				throw new IllegalArgumentException(
						"The Punkt_Objekt have Illegal Wirkrichtung: " //$NON-NLS-1$
								+ direction);
			}
		}
	}

	/**
	 * To find the distance from signal to neighbor track, first determine the
	 * perpendicular line of signal, then find the intersection point of this
	 * line with a GEO_Kante, which have shortest distance to the signal
	 * 
	 * 
	 * @param potk
	 *            the {@link Punkt_Objekt_TOP_Kante_AttributeGroup}
	 * @param position
	 *            the position of the signal
	 * @param angle
	 *            the angle of perpendicular line (90 or -90)
	 * @param maxDistance
	 *            max distance from signal to neighbor track
	 * @return distance from signal to neighbor track
	 */
	private static double getOpposideDistance(
			final Punkt_Objekt_TOP_Kante_AttributeGroup potk,
			final GeoPosition position, final double angle,
			final double maxDistance) {
		final double rad = angle * Math.PI / 180;
		final double transformX = Math.sin(rad) * maxDistance
				+ position.getCoordinate().x;
		final double transformY = Math.cos(rad) * maxDistance
				+ position.getCoordinate().y;
		final GeometryFactory geometryFactory = new GeometryFactory();
		final LineString perpendicularLine = geometryFactory
				.createLineString(new Coordinate[] { position.getCoordinate(),
						new Coordinate(transformX, transformY) });
		final List<LineString> relevantGeometries = Streams
				.stream(getContainer(potk).getGEOKante())
				.filter(geoKante -> GeoKanteExtensions
						.topKante(geoKante) != null)
				.filter(geoKante -> GeoKanteExtensions
						.topKante(geoKante) != potk.getIDTOPKante().getValue())
				.map(GEOKanteGeometryExtensions::getGeometry)
				.filter(Objects::nonNull)
				.toList();
		return getOpposideDistance(relevantGeometries, perpendicularLine,
				position);

	}

	@SuppressWarnings("boxing")
	private static double getOpposideDistance(
			final List<LineString> relevantGeometries,
			final LineString perpendicularLine, final GeoPosition position) {
		final List<Geometry> intersctionPoints = relevantGeometries.stream()
				.filter(geometry -> geometry.intersects(perpendicularLine))
				.map(geometry -> geometry.intersection(perpendicularLine))
				.toList();
		final List<Double> distances = intersctionPoints.stream()
				.map(point -> Double.valueOf(point.getCoordinate()
						.distance(position.getCoordinate())))
				.toList();
		return distances.stream()
				.min(Double::compare)
				.orElse(Double.valueOf(0));
	}

	private void dertermineSignalPosition(final SideDistance sideDistance,
			final ENUMWirkrichtung direction) throws IllegalArgumentException {
		final Optional<Boolean> isPositiveSideDistance = sideDistance.distanceToMainTrack
				.isPresent()
						? Optional.of(Boolean
								.valueOf(sideDistance.distanceToMainTrack.get()
										.doubleValue() >= 0))
						: Optional.empty();
		final ENUMLinksRechts signalPosition = switch (direction) {
			case ENUM_WIRKRICHTUNG_IN: {
				if (isPositiveSideDistance.isPresent()
						&& isPositiveSideDistance.get().booleanValue()
						|| sideDistance.position.isPresent()
								&& sideDistance.position
										.get() == ENUMLinksRechts.ENUM_LINKS_RECHTS_RECHTS) {
					yield ENUMLinksRechts.ENUM_LINKS_RECHTS_LINKS;
				}
				yield ENUMLinksRechts.ENUM_LINKS_RECHTS_RECHTS;
			}
			case ENUM_WIRKRICHTUNG_GEGEN: {
				if (isPositiveSideDistance.isPresent()
						&& isPositiveSideDistance.get().booleanValue()
						|| sideDistance.position.isPresent()
								&& sideDistance.position
										.get() == ENUMLinksRechts.ENUM_LINKS_RECHTS_LINKS) {
					yield ENUMLinksRechts.ENUM_LINKS_RECHTS_RECHTS;
				}
				yield ENUMLinksRechts.ENUM_LINKS_RECHTS_LINKS;
			}
			default:
				throw new IllegalArgumentException(
						"The Signal_Befestigung have Illegal Wirkrichtung: " //$NON-NLS-1$
								+ direction);
		};
		sideDistances.add(new SideDistance(sideDistance.distanceToMainTrack,
				sideDistance.distanceToNeighborTrack,
				Optional.of(signalPosition)));
	}
}
