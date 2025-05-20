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
import static org.eclipse.set.utils.export.xsl.siteplan.SiteplanXSLExtension.RegionPosition.END;

import org.eclipse.set.utils.export.xsl.XMLDocumentExtensions;

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
				.setTitleBoxRegion(END, 180, 197)
				.setSignificantInformation(205)
				.build();

		SITEPLAN_PAGE_A2 = new SiteplanXSLPageBuilder(A2)
				.setRegionBody(377, 400)
				.setTitleBoxRegion(END, 190, 320)
				.setSignificantInformation(187)
				.build();

		SITEPLAN_PAGE_A1 = new SiteplanXSLPageBuilder(A1)
				.setRegionBody(626, 574)
				.setTitleBoxRegion(END, 190, 494)
				.setSignificantInformation(246)
				.build();

		SITEPLAN_PAGE_A0 = new SiteplanXSLPageBuilder(A0)
				.setRegionBody(974, 821)
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
