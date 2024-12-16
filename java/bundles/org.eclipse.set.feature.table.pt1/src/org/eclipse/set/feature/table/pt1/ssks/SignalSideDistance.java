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
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.set.basis.geometry.GeoPosition;
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
	 * 
	 */
	public static class SideDistance {
		long distanceToMainTrack;

		/**
		 * @return the distance from signal to main track
		 */
		public double getDistanceToMainTrack() {
			return distanceToMainTrack;
		}

		/**
		 * @return the distance from signal to neighbor track
		 */
		public double getDistanceToNeighborTrack() {
			return distanceToNeighborTrack;
		}

		long distanceToNeighborTrack;

		/**
		 * @param distanceToMainTrack
		 *            the distance from signal to main track
		 * @param distanceToNeighborTrack
		 *            the distance from main track to neighbor track
		 */
		public SideDistance(final long distanceToMainTrack,
				final long distanceToNeighborTrack) {
			this.distanceToMainTrack = distanceToMainTrack;
			this.distanceToNeighborTrack = distanceToNeighborTrack;
		}

		@Override
		public String toString() {
			if (distanceToNeighborTrack > 0) {
				return String.format("%s (%s)", //$NON-NLS-1$
						String.valueOf(distanceToMainTrack),
						String.valueOf(distanceToNeighborTrack));
			}
			return String.valueOf(distanceToMainTrack);
		}
	}

	final Signal signal;
	private final Set<SideDistance> sideDistancesRight = new HashSet<>();

	private final Set<SideDistance> sideDistancesLeft = new HashSet<>();

	/**
	 * Max distance from main track to neighbor track
	 */
	private static final long MAX_DISTANCE_TO_NEIGHBOR = 8000;

	/**
	 * @param signal
	 *            the Signal
	 */
	public SignalSideDistance(final Signal signal) {
		this.signal = signal;
		getSideDistance();
	}

	/**
	 * @return distances of the signal to tracks on right side
	 */
	public Set<SideDistance> getSideDistancesRight() {
		return sideDistancesRight;
	}

	/**
	 * @return distances of the signal to tracks on left side
	 */
	public Set<SideDistance> getSideDistancesLeft() {
		return sideDistancesLeft;
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
	@SuppressWarnings("boxing")
	private void getSideDistance() throws IllegalArgumentException,
			NullPointerException, RuntimeException {
		final List<Signal_Befestigung> signalBefestigung = getSignalBefestigung();
		final Set<Punkt_Objekt_TOP_Kante_AttributeGroup> potks = signalBefestigung
				.stream()
				.flatMap(befestigung -> PunktObjektExtensions
						.getSinglePoints(befestigung).stream()
						.filter(Objects::nonNull))
				.collect(Collectors.toSet());
		potks.forEach(potk -> {
			final Long sideDistance = getNullableObject(potk, p -> Math.round(
					p.getSeitlicherAbstand().getWert().doubleValue() * 1000))
							.orElse(null);
			if (sideDistance == null) {
				throw new NullPointerException(
						"The Seitlicher_Abstand isn't exists"); //$NON-NLS-1$
			}
			final ENUMWirkrichtung direction = getSinglePoint(signal)
					.getWirkrichtung().getWert();
			final long distanceFromPoint = MAX_DISTANCE_TO_NEIGHBOR
					- Math.abs(sideDistance);
			final int perpendicularRotation = getPerpendicularRotation(
					sideDistance, direction);
			if (distanceFromPoint <= 0) {
				setSideDistances(sideDistance, direction, 0);
				return;
			}

			double opposideDistance = 0.0;
			final GeoPosition position = PunktObjektTopKanteExtensions
					.getCoordinate(potk);
			final double signalRotation = PunktObjektExtensions
					.rotation(signal);
			try {
				opposideDistance = getOpposideDistance(potk, position,
						signalRotation + perpendicularRotation,
						distanceFromPoint / 1000);
			} catch (final Exception e) {
				throw new RuntimeException(e);
			}
			final long distanceBetweenTrack = opposideDistance > 0
					? Math.abs(sideDistance)
							+ Math.round(opposideDistance * 1000)
					: 0;
			setSideDistances(sideDistance, direction, distanceBetweenTrack);
		});

	}

	private List<Signal_Befestigung> getSignalBefestigung() {
		return signalRahmen(signal).stream().map(rahmen -> {
			final Iterator<Signal_Befestigung> signalBefestigungIterator = SignalRahmenExtensions
					.getSignalBefestigungIterator(rahmen);
			return Streams.stream(signalBefestigungIterator)
					.filter(befestigung -> {
						final ENUMBefestigungArt befestigungArt = getNullableObject(
								befestigung,
								e -> e.getSignalBefestigungAllg()
										.getBefestigungArt().getWert())
												.orElse(null);
						return befestigungArt != null
								&& (befestigungArt == ENUMBefestigungArt.ENUM_BEFESTIGUNG_ART_REGELANORDNUNG_MAST_HOCH
										|| befestigungArt == ENUMBefestigungArt.ENUM_BEFESTIGUNG_ART_REGELANORDNUNG_MAST_NIEDRIG
										|| befestigungArt == ENUMBefestigungArt.ENUM_BEFESTIGUNG_ART_ARBEITSBUEHNE);
					}).findFirst().orElse(null);
		}).filter(Objects::nonNull).toList();
	}

	private static int getPerpendicularRotation(final long sideDistance,
			final ENUMWirkrichtung direction) throws IllegalArgumentException {
		final boolean isPositiveSideDistance = sideDistance >= 0;
		switch (direction) {
		case ENUM_WIRKRICHTUNG_IN:
			return isPositiveSideDistance ? 90 : -90;
		case ENUM_WIRKRICHTUNG_GEGEN:
			return isPositiveSideDistance ? -90 : 90;
		default: {
			throw new IllegalArgumentException(
					"The Signal_Befestigung have Illegal Wirkrichtung: " //$NON-NLS-1$
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
						.topKante(geoKante) != potk.getIDTOPKante().getValue())
				.map(GEOKanteGeometryExtensions::getGeometry)
				.filter(Objects::nonNull).toList();
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
		return distances.stream().min(Double::compare)
				.orElse(Double.valueOf(0));
	}

	private void setSideDistances(final long sideDistance,
			final ENUMWirkrichtung direction, final long distanceBetweenTracks)
			throws IllegalArgumentException {
		final boolean isPositiveSideDistance = sideDistance >= 0;
		final long positiveSideDistance = Math.abs(sideDistance);
		switch (direction) {
		case ENUM_WIRKRICHTUNG_IN: {
			if (isPositiveSideDistance) {
				sideDistancesLeft.add(new SideDistance(positiveSideDistance,
						distanceBetweenTracks));
			} else {
				sideDistancesRight.add(new SideDistance(positiveSideDistance,
						distanceBetweenTracks));
			}
			break;
		}
		case ENUM_WIRKRICHTUNG_GEGEN: {
			if (!isPositiveSideDistance) {
				sideDistancesLeft.add(new SideDistance(positiveSideDistance,
						distanceBetweenTracks));
			} else {
				sideDistancesRight.add(new SideDistance(positiveSideDistance,
						distanceBetweenTracks));
			}
			break;
		}
		default:
			throw new IllegalArgumentException(
					"The Signal_Befestigung have Illegal Wirkrichtung: " //$NON-NLS-1$
							+ direction);
		}
	}
}
