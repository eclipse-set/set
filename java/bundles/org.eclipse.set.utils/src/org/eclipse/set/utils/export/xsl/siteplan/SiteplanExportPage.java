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

import static org.eclipse.set.utils.export.xsl.siteplan.SiteplanXSLExtension.PageDIN.*;
import static org.eclipse.set.utils.export.xsl.siteplan.SiteplanXSLExtension.RegionPosition.*;

import java.util.List;

import org.eclipse.set.utils.export.xsl.XMLDocumentExtensions;
import org.eclipse.set.utils.export.xsl.siteplan.SiteplanXSLExtension.FoldingMark;

/**
 * Contains site plan export page
 * 
 * @author truong
 */
public class SiteplanExportPage {

	private SiteplanExportPage() {

	}

	private static final SiteplanXSLPage SITEPLAN_PAGE_A3;
	private static final SiteplanXSLPage SITEPLAN_PAGE_A2;
	private static final SiteplanXSLPage SITEPLAN_PAGE_A1;
	private static final SiteplanXSLPage SITEPLAN_PAGE_A0;

	static {
		SITEPLAN_PAGE_A3 = new SiteplanXSLPageBuilder(A3)
				.setRegionBody(205, 277)
				.setFoldingMarks(BEFORE, 125, 105, 190)
				.setFoldingMarks(AFTER, 125, 105, 190)
				.setTitleBoxRegion(END, 180, 197)
				.setSignificantInformation(205)
				.build();

		SITEPLAN_PAGE_A2 = new SiteplanXSLPageBuilder(A2)
				.setRegionBody(377, 400)
				.setFoldingMarks(BEFORE, 210, 192, 192)
				.setFoldingMarks(AFTER, 210, 192, 192)
				.setFoldingMarks(START, 113, 287)
				.setFoldingMarks(new FoldingMark(END,
						List.of(Double.valueOf(113), Double.valueOf(287)), 12,
						FoldingMark.DEFAULT_MARK_WIDTH))
				.setTitleBoxRegion(END, 190, 320)
				.setSignificantInformation(187)
				.build();

		SITEPLAN_PAGE_A1 = new SiteplanXSLPageBuilder(A1)
				.setRegionBody(626, 574)
				.setFoldingMarks(BEFORE, 210, 190, 125.5, 125.5, 190)
				.setFoldingMarks(AFTER, 210, 190, 125.5, 125.5, 190)
				.setFoldingMarks(START, 287, 287)
				.setFoldingMarks(END, 287, 287)
				.setTitleBoxRegion(END, 190, 494)
				.setSignificantInformation(246)
				.build();

		SITEPLAN_PAGE_A0 = new SiteplanXSLPageBuilder(A0)
				.setRegionBody(974, 821)
				.setFoldingMarks(BEFORE, 210, 190, 190, 190, 109.5, 109.5, 190)
				.setFoldingMarks(AFTER, 210, 190, 190, 190, 109.5, 109.5, 190)
				.setFoldingMarks(START, 237, 297, 287)
				.setFoldingMarks(END, 237, 297, 287)
				.setTitleBoxRegion(END, 190, 741)
				.setSignificantInformation(214)
				.build();
	}

	/**
	 * A3 Page with siteplan size 205mm X 277mm
	 * 
	 * @return {@link XMLDocumentExtensions #SITEPLAN_PAGE_A3}
	 */
	public static SiteplanXSLPage getSiteplanPageA3() {
		return SITEPLAN_PAGE_A3;
	}

	/**
	 * A2 Page with siteplan size 377mm X 400mm
	 * 
	 * @return {@link XMLDocumentExtensions #SITEPLAN_PAGE_A2}
	 */
	public static SiteplanXSLPage getSiteplanPageA2() {
		return SITEPLAN_PAGE_A2;
	}

	/**
	 * A1 Page with siteplan size 626mm X 574mm
	 * 
	 * @return {@link XMLDocumentExtensions #SITEPLAN_PAGE_A3}
	 */
	public static SiteplanXSLPage getSiteplanPageA1() {
		return SITEPLAN_PAGE_A1;
	}

	/**
	 * A0 Page with siteplan size 974mm X 821mm
	 * 
	 * @return {@link XMLDocumentExtensions #SITEPLAN_PAGE_A3}
	 */
	public static SiteplanXSLPage getSiteplanPageA0() {
		return SITEPLAN_PAGE_A0;
	}
}
