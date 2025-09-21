/**
 * Copyright (c) 2025 DB InfraGO AG and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 */
package org.eclipse.set.utils.export.xsl.siteplan;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * Contains helper class for define siteplan xsl
 * 
 * @author Truong
 */
public class SiteplanXSLExtension {
	/**
	 * Default margin top/bottom value (mm)
	 */
	public static final double DEFAULT_MARGIN_TOP_BOTTOM = 10;
	/**
	 * Default margin left value (mm)
	 */
	public static final double DEFAULT_MARGIN_LEFT = 20;
	/**
	 * Default margin right value (mm)
	 */
	public static final double DEFAULT_MARGIN_RIGHT = 10;

	/**
	 * Region position of FOP
	 */
	public enum RegionPosition {
		/**
		 * Page top position
		 */
		BEFORE("region-before"), //$NON-NLS-1$
		/**
		 * Page bottom position
		 */
		AFTER("region-after"), //$NON-NLS-1$
		/**
		 * Page left position
		 */
		START("region-start"), //$NON-NLS-1$
		/**
		 * Page right position
		 */
		END("region-end"), //$NON-NLS-1$
		/**
		 * Page body
		 */
		BODY("region-body"); //$NON-NLS-1$

		private final String regionName;

		private RegionPosition(final String name) {
			this.regionName = name;
		}

		/**
		 * @return the xsl fop region tag
		 */
		public String getRegionTag() {
			return "fo:" + regionName; //$NON-NLS-1$
		}

		/**
		 * @return the region name
		 */
		public String getRegionName() {
			return regionName;
		}
	}

	/**
	 * Available page DIN to export
	 */
	public enum PageDIN {
		/**
		 * DIN A3
		 */
		A3("A3", 420, 297), //$NON-NLS-1$
		/**
		 * DIN A2
		 */
		A2("A2", 594, 420), //$NON-NLS-1$
		/**
		 * DIN A1
		 */
		A1("A1", 841, 594), //$NON-NLS-1$
		/**
		 * DIN A0
		 */
		A0("A0", 1189, 841); //$NON-NLS-1$

		private final double pageWidth;
		private final double pageHeight;
		private final String pageDIN;

		private PageDIN(final String din, final double width,
				final double height) {
			this.pageHeight = height;
			this.pageWidth = width;
			this.pageDIN = din;
		}

		/**
		 * @return the page width
		 */
		public double getPageWidth() {
			return pageWidth;
		}

		/**
		 * @return the page height
		 */
		public double getPageHeight() {
			return pageHeight;
		}

		/**
		 * @return page folding mark position at top/bottom
		 */
		@SuppressWarnings({ "boxing", "nls" })
		public List<Double> getFoldingMarkTopBottomWidth() {
			return switch (pageDIN) {
				case "A3" -> List.of(125d, 105d, 190d);
				case "A2" -> List.of(210d, 192d, 192d);
				case "A1" -> List.of(210d, 190d, 125.5, 125.5, 190d);
				case "A0" -> List.of(210d, 190d, 190d, 190d, 109.5, 109.5,
						190d);
				default -> Collections.emptyList();
			};
		}

		/**
		 * @return page folding mark position by side
		 */
		@SuppressWarnings({ "boxing", "nls" })
		public List<Double> getFoldingMarkSide() {
			return switch (pageDIN) {
				case "A2" -> List.of(113d, 287d);
				case "A1" -> List.of(287d, 287d);
				case "A0" -> List.of(237d, 297d, 287d);
				default -> Collections.emptyList();
			};
		}

		/**
		 * @return the page DIN
		 */
		@Override
		public String toString() {
			return pageDIN;
		}
	}

	/**
	 * The page folding mark
	 * 
	 * @param position
	 *            the region of folding mark
	 * @param distances
	 *            distance between folding marks
	 * @param extent
	 *            the size of the folding mark region
	 * @param markWidth
	 *            the width of the folding mark
	 */
	public record FoldingMark(RegionPosition position, List<Double> distances,
			double extent, double markWidth) {

		public static final double DEFAULT_EXTENT_TOP_BOTTOM_RIGHT = 10;
		public static final double DEFAULT_EXTENT_LEFT = 20;
		public static final double DEFAULT_MARK_WIDTH = 5;

		/**
		 * @param position
		 *            the region of the folding mark
		 * @param distances
		 *            the distance between folding mark
		 */
		public FoldingMark(final RegionPosition position,
				final List<Double> distances) {
			this(position, distances, switch (position) {
				case START -> DEFAULT_EXTENT_LEFT;
				default -> DEFAULT_EXTENT_TOP_BOTTOM_RIGHT;
			}, DEFAULT_MARK_WIDTH);
		}

		/**
		 * @param position
		 *            the region of the folding mark
		 * @param distance
		 *            the distance between folding mark
		 */
		public FoldingMark(final RegionPosition position,
				final double... distance) {
			this(position,
					new LinkedList<>(Arrays.stream(distance).boxed().toList()),
					switch (position) {
						case START -> DEFAULT_EXTENT_LEFT;
						default -> DEFAULT_EXTENT_TOP_BOTTOM_RIGHT;
					}, DEFAULT_MARK_WIDTH);
		}
	}

	/**
	 * The region of title box and free field
	 * 
	 * @param position
	 *            the page region
	 * @param extent
	 *            the region size
	 * @param freefeldSize
	 *            the size of free field , which is width at
	 *            {@link RegionPosition#BEFORE or RegionPosition#AFTER} and
	 *            height at {@link RegionPosition#START or RegionPosition#END}
	 */
	public record TitleBoxRegion(RegionPosition position, double extent,
			double freefeldSize) {
		/**
		 * Default width of the title box
		 */
		public static final double TITLEBOX_WIDTH = 180;

		/**
		 * Default height of the title box
		 */
		public static final double TITLEBOX_HEIGHT = 75;
	}

	/**
	 * The region body, where the site plan sheet cut will be place
	 * 
	 * @param width
	 *            the region width
	 * @param height
	 *            the region height
	 */
	public record RegionBody(double width, double height) {

	}

	/**
	 * The place for significant information
	 * 
	 * @param width
	 *            the width of this place
	 * @param height
	 *            the height of this place
	 */
	public record SignificantInformation(double width, double height) {
		private static final double DEFAULT_HEIGHT = 10;

		/**
		 * * @param width the width of this place
		 */
		public SignificantInformation(final double width) {
			this(width, DEFAULT_HEIGHT);
		}
	}

	private static double FIRST_FOLD_PAGE_WIDTH_GREATER_THAN_A3 = 210;
	private static double LAST_FOLD_WIDTH = 190;
	private static double MAX_BETWEEN_FOLD_WIDTH = 190;
	private static double MIN_BETWEEN_FOLD_WIDTH = 80;

	/**
	 * Determine custom folding top/bottom for custom page width
	 * 
	 * @param pageWidth
	 *            the page width
	 * @return the foldings width
	 */
	@SuppressWarnings("boxing")
	public static List<Double> calculateCustomFoldingTopBottom(
			final double pageWidth) {
		final List<Double> dinFolding = getDINFolding(pageWidth);
		if (dinFolding != null) {
			return dinFolding;
		}

		if (pageWidth <= PageDIN.A3.getPageWidth()) {
			return calculateCustomFoldingSmallThanA3(pageWidth);
		}
		final double firstFold = FIRST_FOLD_PAGE_WIDTH_GREATER_THAN_A3;
		final double lastFold = LAST_FOLD_WIDTH;
		final double remainingWidth = pageWidth - firstFold - lastFold;
		for (int foldCount = 1; foldCount < 99; foldCount++) {
			final int totalFoldCount = foldCount + 2;
			// The fold count should be odd
			if (totalFoldCount % 2 == 0) {
				continue;
			}
			final List<Double> result = new LinkedList<>();
			double remaining = remainingWidth;
			for (int i = 0; i < foldCount; i++) {
				final int remainingFoldCount = foldCount - i;
				// when the remaining width greater than the max fold width and
				// the next fold width should be greater than the min width
				if (remaining >= MAX_BETWEEN_FOLD_WIDTH && remaining
						- MAX_BETWEEN_FOLD_WIDTH > MIN_BETWEEN_FOLD_WIDTH) {
					result.add(MAX_BETWEEN_FOLD_WIDTH);
					remaining -= MAX_BETWEEN_FOLD_WIDTH;
					continue;
				}
				BigDecimal currentFoldWidth = BigDecimal
						.valueOf(Math.min(MAX_BETWEEN_FOLD_WIDTH,
								remaining / remainingFoldCount));
				currentFoldWidth = currentFoldWidth.setScale(2,
						RoundingMode.HALF_UP);
				result.add(currentFoldWidth.doubleValue());
				remaining -= currentFoldWidth.doubleValue();
			}
			if (Math.abs(remaining) < 0.5) {
				result.addFirst(firstFold);
				result.addLast(lastFold);
				return result;
			}
		}
		return Collections.emptyList();
	}

	private static List<Double> getDINFolding(final double width) {
		final Optional<PageDIN> relevantDIN = Arrays.stream(PageDIN.values())
				.filter(din -> din.getPageWidth() == width)
				.findFirst();
		if (relevantDIN.isEmpty()) {
			return null;
		}
		return relevantDIN.get().getFoldingMarkTopBottomWidth();
	}

	@SuppressWarnings("boxing")
	/**
	 * Determine up/down folding for the page with the width smaller than DIN
	 * A3.
	 * 
	 * @param totalWidth
	 * @return the list of folding width
	 */
	private static List<Double> calculateCustomFoldingSmallThanA3(
			final double totalWidth) {
		final double lastFold = 190;
		final double remainingWidth = totalWidth - lastFold;
		BigDecimal firstFold = BigDecimal.valueOf((remainingWidth + 20) / 2);
		firstFold = firstFold.setScale(2, RoundingMode.HALF_UP);
		return List.of(firstFold.doubleValue(),
				remainingWidth - firstFold.doubleValue(), lastFold);
	}
}
