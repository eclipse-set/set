/**
 * Copyright (c) 2021 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.siteplan;

import org.eclipse.set.model.planpro.Weichen_und_Gleissperren.ENUMWKrArt;

/**
 * Container for track switch metadata
 * 
 * @author Stuecker
 *
 */
public class TrackSwitchMetadata {
	private static final int COLUMN_TYPE = 5; // F column
	private static final int COLUMN_DESIGN = 6; // G column
	private static final int COLUMN_LENGTH_MAIN = 8; // I column
	private static final int COLUMN_LENGTH_SIDE = 9; // J column
	private static final int COLUMN_CROSSING_RIGHT_MAIN = 13;
	private static final int COLUMN_CROSSING_RIGHT_SIDE = 14;
	private static final int COLUMN_CROSSING_LEFT_MAIN = 18;
	private static final int COLUMN_CROSSING_LEFT_SIDE = 19;
	private static final int COLUMN_CROSSING_TRIANGLE_RIGHT_MAIN = 15;
	private static final int COLUMN_CROSSING_TRIANGLE_RIGHT_SIDE = 16;
	private static final int COLUMN_CROSSING_TRIANGLE_LEFT_MAIN = 20;
	private static final int COLUMN_CROSSING_TRIANGLE_LEFT_SIDE = 21;

	/**
	 * Private constructor, use fromCSVLine instead
	 */
	private TrackSwitchMetadata() {
	}

	/**
	 * Parses a CSV line into a TrackSwitchMetadata
	 * 
	 * @param line
	 *            the CSV line
	 * @return a TrackSwitchMetadata or null if not a valid csv line
	 */
	public static TrackSwitchMetadata fromCSVLine(final String line) {
		final TrackSwitchMetadata entry = new TrackSwitchMetadata();
		final String[] columns = line
				.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"); //$NON-NLS-1$
		if (columns.length < COLUMN_LENGTH_SIDE + 1) {
			return null;
		}
		try {
			entry.design = columns[COLUMN_DESIGN];
			entry.type = ENUMWKrArt.get(columns[COLUMN_TYPE]);
			if (entry.type == null) {
				return null;
			}

			switch (entry.type) {
				case ENUMW_KR_ART_DKW, ENUMW_KR_ART_EKW:
					// Crossing lengths are required
					entry.rightCrossing = new CrossingSide(
							Double.parseDouble(
									columns[COLUMN_CROSSING_RIGHT_MAIN]),
							Double.parseDouble(
									columns[COLUMN_CROSSING_RIGHT_SIDE]));
					entry.leftCrossing = new CrossingSide(
							Double.parseDouble(
									columns[COLUMN_CROSSING_LEFT_MAIN]),
							Double.parseDouble(
									columns[COLUMN_CROSSING_LEFT_SIDE]));

					// Optionally the crossing triangle may also be given for
					// either
					// (or both) sides
					if (!columns[COLUMN_CROSSING_TRIANGLE_RIGHT_MAIN]
							.isEmpty()) {
						entry.rightCrossing.crossingTriangle = new LegLength(
								Double.parseDouble(
										columns[COLUMN_CROSSING_TRIANGLE_RIGHT_MAIN]),
								Double.parseDouble(
										columns[COLUMN_CROSSING_TRIANGLE_RIGHT_SIDE]));
					}
					if (!columns[COLUMN_CROSSING_TRIANGLE_LEFT_MAIN]
							.isEmpty()) {
						entry.leftCrossing.crossingTriangle = new LegLength(
								Double.parseDouble(
										columns[COLUMN_CROSSING_TRIANGLE_LEFT_MAIN]),
								Double.parseDouble(
										columns[COLUMN_CROSSING_TRIANGLE_LEFT_SIDE]));
					}

					// Optionally the crossing triangle may also be given
					break;
				case ENUMW_KR_ART_EW:
					// For simple track switches, only the leg lengths are
					// required
					entry.trackSwitchLength = new LegLength(
							Double.parseDouble(columns[COLUMN_LENGTH_MAIN]),
							Double.parseDouble(columns[COLUMN_LENGTH_SIDE]));
					break;
				case ENUMW_KR_ART_KR:
					entry.leftCrossing = new CrossingSide(
							columns[COLUMN_CROSSING_LEFT_MAIN],
							columns[COLUMN_CROSSING_LEFT_SIDE]);
					entry.rightCrossing = new CrossingSide(
							columns[COLUMN_CROSSING_RIGHT_MAIN],
							columns[COLUMN_CROSSING_RIGHT_SIDE]);
					break;
				default:
					// Other types are not handled
					return null;
			}
			return entry;
		} catch (final NumberFormatException e) {
			return null;
		}
	}

	/**
	 * Defines a pair of lengths (main and side leg)
	 * 
	 * @author Stuecker
	 *
	 */
	public static class LegLength {
		private LegLength(final double mainLeg, final double sideLeg) {
			this.mainLeg = mainLeg;
			this.sideLeg = sideLeg;
		}

		/**
		 * The main leg length
		 */
		public final double mainLeg;
		/**
		 * The side leg length
		 */
		public final double sideLeg;
	}

	/**
	 * Defines lengths for a single side of a crossing
	 * 
	 * @author Stuecker
	 *
	 */
	public static class CrossingSide {
		private CrossingSide(final double mainLeg, final double sideLeg) {
			crossing = new LegLength(mainLeg, sideLeg);
		}

		private CrossingSide(final String mainLeg, final String sideLeg) {
			this(Double.parseDouble(mainLeg), Double.parseDouble(sideLeg));
		}

		/**
		 * Lengths of the crossing triangle May be null if not definied
		 */
		public LegLength crossingTriangle;
		/**
		 * Lengths of the crossing itself
		 */
		public final LegLength crossing;
	}

	/**
	 * the design name of the track switch
	 */
	public String design;
	/**
	 * the type of the track switch
	 */
	public ENUMWKrArt type;
	/**
	 * the length of the track switch may be null for EKW/DKW
	 */
	public LegLength trackSwitchLength;
	/**
	 * information for the left side of the crossing (if any)
	 */
	public CrossingSide leftCrossing;
	/**
	 * information for the right side of the crossing (if any)
	 */
	public CrossingSide rightCrossing;

	/**
	 * @return the design of the track switch as a full string as it should be
	 *         set as a W_Kr_Grundform in the PlanPro model
	 */
	public String getDesignString() {
		return String.format("%s %s", type.toString(), design); //$NON-NLS-1$
	}
}
