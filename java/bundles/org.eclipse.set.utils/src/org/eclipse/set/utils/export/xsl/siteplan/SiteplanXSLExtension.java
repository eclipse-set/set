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

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Contains helper class for define siteplan xsl
 * 
 * @author Truong
 */
public class SiteplanXSLExtension {

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

		private static final double DEFAULT_EXTENT_TOP_BOTTOM_RIGHT = 10;
		private static final double DEFAULT_EXTENT_LEFT = 20;
		private static final double DEFAULT_MARK_WIDTH = 5;

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
}
