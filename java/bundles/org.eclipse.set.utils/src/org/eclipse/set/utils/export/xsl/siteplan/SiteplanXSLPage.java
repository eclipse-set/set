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
import org.eclipse.set.utils.export.xsl.siteplan.SiteplanXSLExtension.PageDIN;
import org.eclipse.set.utils.export.xsl.siteplan.SiteplanXSLExtension.RegionBody;
import org.eclipse.set.utils.export.xsl.siteplan.SiteplanXSLExtension.SignificantInformation;
import org.eclipse.set.utils.export.xsl.siteplan.SiteplanXSLExtension.TitleBoxRegion;

/**
 * Helper class for define xsl to export site plan
 * 
 * @author Truong
 */
public class SiteplanXSLPage {

	List<FoldingMark> foldingMarks;
	TitleBoxRegion titleBoxRegion;
	RegionBody regionBody;
	SignificantInformation significantInformation;

	PageDIN pageDIN;
	private final double pageWidth;
	private final double pageHeight;

	/**
	 * @param pageDIN
	 *            the page DIN
	 * @param titleBoxRegion
	 *            {@link TitleBoxRegion}
	 * @param regionBody
	 *            {@link RegionBody}
	 * @param foldingMarks
	 *            list of {@link FoldingMark}
	 * @param significant
	 *            {@link SignificantInformation}
	 */
	public SiteplanXSLPage(final PageDIN pageDIN,
			final TitleBoxRegion titleBoxRegion,
			final List<FoldingMark> foldingMarks, final RegionBody regionBody,
			final SignificantInformation significant) {
		this.pageDIN = pageDIN;
		this.foldingMarks = foldingMarks;
		this.titleBoxRegion = titleBoxRegion;
		this.regionBody = regionBody;
		this.significantInformation = significant;
		this.pageHeight = pageDIN.getPageHeight();
		this.pageWidth = pageDIN.getPageWidth();
	}

	/**
	 * @param pageHeight
	 *            the page height
	 * @param pageWidth
	 *            the page width
	 * @param titleBoxRegion
	 *            {@link TitleBoxRegion}
	 * @param regionBody
	 *            {@link RegionBody}
	 * @param foldingMarks
	 *            list of {@link FoldingMark}
	 * @param signfInformation
	 *            {@link SignificantInformation}
	 */
	public SiteplanXSLPage(final double pageHeight, final double pageWidth,
			final TitleBoxRegion titleBoxRegion, final RegionBody regionBody,
			final List<FoldingMark> foldingMarks,
			final SignificantInformation signfInformation) {
		this.pageDIN = null;
		this.foldingMarks = foldingMarks;
		this.titleBoxRegion = titleBoxRegion;
		this.regionBody = regionBody;
		this.significantInformation = signfInformation;
		this.pageHeight = pageHeight;
		this.pageWidth = pageWidth;
	}

	/**
	 * @return {@link PageDIN#toString()}
	 */
	public String getPageDIN() {
		try {
			return pageDIN.toString();
		} catch (final Exception e) {
			return "Custom"; //$NON-NLS-1$
		}

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
