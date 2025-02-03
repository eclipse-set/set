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

import java.util.List;

import org.eclipse.set.utils.export.xsl.siteplan.SiteplanXSLExtension.FoldingMark;
import org.eclipse.set.utils.export.xsl.siteplan.SiteplanXSLExtension.PageSize;
import org.eclipse.set.utils.export.xsl.siteplan.SiteplanXSLExtension.RegionBody;
import org.eclipse.set.utils.export.xsl.siteplan.SiteplanXSLExtension.SignificantInformation;
import org.eclipse.set.utils.export.xsl.siteplan.SiteplanXSLExtension.TitleBoxRegion;

/**
 * Helper class for define xsl to export site plan
 * 
 * @author Truong
 */
public class SiteplanXSLPage {

	PageSize pageSize;
	List<FoldingMark> foldingMarks;
	TitleBoxRegion titleBoxRegion;
	RegionBody regionBody;
	SignificantInformation significantInformation;

	/**
	 * @param pageSize
	 *            {@link PageSize}
	 * @param titleBoxRegion
	 *            {@link TitleBoxRegion}
	 * @param regionBody
	 *            {@link RegionBody}
	 * @param foldingMarks
	 *            list of {@link FoldingMark}
	 * @param significant
	 *            {@link SignificantInformation}
	 */
	public SiteplanXSLPage(final PageSize pageSize,
			final TitleBoxRegion titleBoxRegion,
			final List<FoldingMark> foldingMarks, final RegionBody regionBody,
			final SignificantInformation significant) {
		this.pageSize = pageSize;
		this.foldingMarks = foldingMarks;
		this.titleBoxRegion = titleBoxRegion;
		this.regionBody = regionBody;
		this.significantInformation = significant;
	}

	/**
	 * @return {@link PageSize}
	 */
	public PageSize getPageSize() {
		return pageSize;
	}

	/**
	 * @return list of {@link FoldingMark}
	 */
	public List<FoldingMark> getFoldingMarks() {
		return foldingMarks;
	}

	/**
	 * @return {@link TitleBoxRegion}
	 */
	public TitleBoxRegion getTitleBoxRegion() {
		return titleBoxRegion;
	}

	/**
	 * @return {@link RegionBody}
	 */
	public RegionBody getRegionBody() {
		return regionBody;
	}

	/**
	 * @return {@link SignificantInformation}
	 */
	public SignificantInformation getSignificantInformation() {
		return significantInformation;
	}
}
