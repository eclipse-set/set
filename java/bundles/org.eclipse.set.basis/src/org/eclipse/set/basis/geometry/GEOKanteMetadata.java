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
package org.eclipse.set.basis.geometry;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.eclipse.set.model.planpro.Basisobjekte.Bereich_Objekt;
import org.eclipse.set.model.planpro.Geodaten.GEO_Kante;
import org.eclipse.set.model.planpro.Geodaten.GEO_Knoten;
import org.eclipse.set.model.planpro.Geodaten.TOP_Kante;
import org.eclipse.set.model.planpro.Geodaten.TOP_Knoten;
import org.locationtech.jts.geom.LineString;

/**
 * A GEO_Kante with additional cached metadata
 */
public class GEOKanteMetadata {
	/**
	 * The minimal length to segment a track
	 */
	static final double SEGMENT_THRESHOLD = 0.001;
	/**
	 * The referenced GEO_Kante
	 */
	final GEO_Kante geoKante;

	/**
	 * GEO_Knoten
	 */
	final GEO_Knoten geoKnoten;
	/**
	 * The geometry of the GEO_Kante
	 */
	final LineString geometry;
	/**
	 * The segments of the GEO_Kante defined by splitting the GEO_Kante at
	 * either start or end of a Bereich_Objekt
	 */
	final List<GEOKanteSegment> segments = new ArrayList<>();
	/**
	 * The distance of the GEO_Kante from the TOP_Knoten
	 */
	final double start;
	/**
	 * Length of the GEO_Kante on the TOP_Kante
	 */
	final double length;

	/**
	 * @return the GEO_Kante
	 */
	public GEO_Kante getGeoKante() {
		return geoKante;
	}

	/**
	 * @return the geo knoten
	 */
	public GEO_Knoten getGeoKnoten() {
		return geoKnoten;
	}

	/**
	 * @return the geometry of the GEO_Kante
	 */
	public LineString getGeometry() {
		return geometry;
	}

	/**
	 * @return segments defining this GEO_Kante
	 */
	public List<GEOKanteSegment> getSegments() {
		return segments;
	}

	/**
	 * @return the start of the GEO_Kante relative to the start of the TOP_Kante
	 */
	public double getStart() {
		return start;
	}

	/**
	 * @return the end of the GEO_Kante relative to the start of the TOP_Kante
	 */
	public double getEnd() {
		return start + length;
	}

	/**
	 * @return the length of the GEO_Kante on the TOP_Kante
	 */
	public double getLength() {
		return length;
	}

	/**
	 * @param geoKante
	 *            the referenced {@link GEO_Kante}
	 * @param start
	 *            the distace of the GEO_Kante from the {@link TOP_Knoten}
	 * @param length
	 *            the length of the GEO_Kante on the {@link TOP_Kante}
	 * @param bereichObjekt
	 *            the {@link Bereich_Objekt}, which contain this GEO_Kante
	 * @param topKante
	 *            the {@link TOP_Kante}
	 * @param geoKnoten
	 *            the start {@link GEO_Knoten} of this GEO_Kante
	 * @param geometry
	 *            the geometry of this GEO_Kante
	 * 
	 */
	public GEOKanteMetadata(final GEO_Kante geoKante, final double start,
			final double length, final List<Bereich_Objekt> bereichObjekt,
			final TOP_Kante topKante, final GEO_Knoten geoKnoten,
			final LineString geometry) {
		this.geoKante = geoKante;
		this.start = start;
		this.length = length;
		this.geometry = geometry;
		this.geoKnoten = geoKnoten;
		determineSegments(topKante, bereichObjekt);
	}

	/**
	 * @param geoKante
	 *            the referenced {@link GEO_Kante}
	 * @param start
	 *            the distace of the GEO_Kante from the {@link TOP_Knoten}
	 * @param geoKnoten
	 *            the start {@link GEO_Knoten} of this GEO_Kante
	 * @param geometry
	 *            the geometry of this GEO_Kante
	 */
	public GEOKanteMetadata(final GEO_Kante geoKante, final double start,
			final GEO_Knoten geoKnoten, final LineString geometry) {
		this.geoKante = geoKante;
		this.start = start;
		this.length = dermineLength().apply(geoKante).doubleValue();
		this.geometry = geometry;
		this.geoKnoten = geoKnoten;
		// As there is final no segmenting information, only use one segment
		segments.add(new GEOKanteSegment(start, length));

	}

	private static Function<GEO_Kante, Double> dermineLength() {
		return kante -> {
			try {
				return Double.valueOf(kante.getGEOKanteAllg().getGEOLaenge()
						.getWert().doubleValue());
			} catch (final NullPointerException e) {
				return Double.valueOf(0);
			}
		};
	}

	private void determineSegments(final TOP_Kante topKante,
			final List<Bereich_Objekt> bereichObjekt) {
		// Create an initial segment covering the entire length
		segments.add(new GEOKanteSegment(start, length));
		final double end = getEnd();

		// Filter Bereich_Objekte to only include those that are relevant to the
		// GEO_Kante
		final List<Bereich_Objekt> relevantBOs = bereichObjekt.stream().filter(
				bo -> bo.getBereichObjektTeilbereich().stream().anyMatch(tb -> {
					// Only consider Bereich_Objekte which are on the same
					// TOP_Kante
					final boolean isSameTOPKante = tb.getIDTOPKante() != null
							&& tb.getIDTOPKante().getValue() == topKante;
					final boolean existBothOfBound = tb.getBegrenzungA() != null
							&& tb.getBegrenzungA().getWert() != null
							&& tb.getBegrenzungB() != null
							&& tb.getBegrenzungB().getWert() != null;
					return isSameTOPKante && existBothOfBound;
				})).toList();

		// Split the GEO_Kante into segments according to the BOs found
		relevantBOs.forEach(bo -> {
			bo.getBereichObjektTeilbereich().forEach(tb -> {

				final double tbStart = tb.getBegrenzungA().getWert()
						.doubleValue();
				final double tbEnd = tb.getBegrenzungB().getWert()
						.doubleValue();
				// Skip Bereich_Objekte which are not affecting this GEO_Kante
				if (tbStart > end || tbEnd < start) {
					return;
				}
				if (tbStart <= start && tbEnd >= end) {
					// Case 1: Bereich_Objekt covers the entire GEO_Kante
					// No need to split the GEO_Kante
				} else if (tbStart >= start && tbEnd <= end) {
					// Case 2: Bereich_Objekt starts and ends within the
					// GEO_Kante
					// Split the segment at tbStart and tbEnd
					splitSegmentsAt(tbStart);
					splitSegmentsAt(tbEnd);
				} else if (tbStart <= start && tbEnd <= end) {
					// Case 3: Bereich_Objekt starts before the GEO_Kante and
					// ends within it
					// Split the segment at tbEnd
					splitSegmentsAt(tbEnd);
				} else if (tbStart > start && tbEnd >= end) {
					// Case 4: Bereich_Objekt starts within the GEO_Kante and
					// ends after it
					// Split the segment at tbStart
					splitSegmentsAt(tbStart);
				}
			});
		});

		// For the relevant BOs, check if they apply to any segment
		segments.forEach(segment -> {
			final double segStart = segment.getStart();
			final double segEnd = segment.getEnd();
			relevantBOs.forEach(
					bo -> bo.getBereichObjektTeilbereich().forEach(tb -> {
						final double tbStart = tb.getBegrenzungA().getWert()
								.doubleValue();
						final double tbEnd = tb.getBegrenzungB().getWert()
								.doubleValue();
						// Skip Bereich_Objekte which are not affecting this
						// GEO_Kante
						if (tbStart > end || tbEnd < start) {
							return;
						}

						if (tbStart > segEnd) {
							// TB begins after segment ends
							return;
						}

						if (tbEnd < segStart) {
							// TB ends before segment starts
							return;
						}
						segment.bereichObjekte.add(bo);
					}));
		});
	}

	/**
	 * Splits the segments at a given distance
	 * 
	 * @param distance
	 */
	private void splitSegmentsAt(final double distance) {
		final GEOKanteSegment originalSegment = getContainingSegment(distance);
		if (originalSegment == null) {
			throw new RuntimeException("Missing GEO_Kante segment"); //$NON-NLS-1$
		}

		// Split the original segment
		final GEOKanteSegment nextSegment = new GEOKanteSegment(
				originalSegment);
		final double firstSegmentLength = distance - originalSegment.getStart();
		final double secondSegmentLength = originalSegment.getEnd() - distance;

		// Discard segments that are too short
		if (firstSegmentLength < SEGMENT_THRESHOLD
				|| secondSegmentLength < SEGMENT_THRESHOLD) {
			return;
		}
		originalSegment.setLength(firstSegmentLength);
		nextSegment.setStart(distance);
		nextSegment.setLength(secondSegmentLength);
		segments.add(nextSegment);
	}

	/**
	 * Determines the segment which contains a point at a given distance
	 * 
	 * @param distance
	 *            distance to consider
	 * @return the GEOKanteSegment for the given distance or null if distance is
	 *         outside the GEO_Kante
	 */
	public GEOKanteSegment getContainingSegment(final double distance) {
		return segments.stream()
				.filter(segment -> segment.getStart() <= distance
						&& segment.getStart() + segment.getLength() >= distance)
				.findFirst().orElse(null);
	}
}
