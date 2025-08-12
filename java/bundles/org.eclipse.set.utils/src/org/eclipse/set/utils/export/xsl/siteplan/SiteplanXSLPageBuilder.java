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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.set.utils.export.xsl.siteplan.SiteplanXSLExtension.FoldingMark;
import org.eclipse.set.utils.export.xsl.siteplan.SiteplanXSLExtension.PageDIN;
import org.eclipse.set.utils.export.xsl.siteplan.SiteplanXSLExtension.RegionBody;
import org.eclipse.set.utils.export.xsl.siteplan.SiteplanXSLExtension.RegionPosition;
import org.eclipse.set.utils.export.xsl.siteplan.SiteplanXSLExtension.SignificantInformation;
import org.eclipse.set.utils.export.xsl.siteplan.SiteplanXSLExtension.TitleBoxRegion;

/**
 * Builder for {@linkplain SiteplanXSLPage}
 * 
 * @author Truong
 */
@SuppressWarnings("javadoc")
public class SiteplanXSLPageBuilder {
	private final PageDIN pageDIN;
	private List<FoldingMark> builderFoldingMarks;
	private TitleBoxRegion builderTilteBox;
	private RegionBody builderRegionBody;
	private SignificantInformation builderSignificant;

	public SiteplanXSLPageBuilder(final PageDIN pageDIN) {
		this.pageDIN = pageDIN;
		setFoldingMarks();
	}

	public SiteplanXSLPageBuilder setFoldingMarks(
			final FoldingMark... builderFoldingMarks) {
		// Each region should have only one FoldingMark
		final Set<RegionPosition> positions = Arrays.stream(builderFoldingMarks)
				.map(FoldingMark::position)
				.collect(Collectors.toSet());
		if (positions.size() < builderFoldingMarks.length) {
			throw new IllegalArgumentException(
					"Each region should have only a FoldingMark"); //$NON-NLS-1$
		}
		this.builderFoldingMarks = new ArrayList<>(
				Arrays.stream(builderFoldingMarks).toList());
		return this;
	}

	private void setFoldingMarks() {
		for (final RegionPosition region : RegionPosition.values()) {
			if (region == RegionPosition.BEFORE
					|| region == RegionPosition.AFTER) {
				setFoldingMarks(new FoldingMark(region,
						pageDIN.getFoldingMarkTopBottomWidth()));
			} else if (region == RegionPosition.START
					|| region == RegionPosition.END) {
				setFoldingMarks(
						new FoldingMark(region, pageDIN.getFoldingMarkSide()));
			}
		}
	}

	public SiteplanXSLPageBuilder setFoldingMarks(
			final FoldingMark foldingMark) {
		if (this.builderFoldingMarks == null) {
			this.builderFoldingMarks = new ArrayList<>();
		}
		// Each region should have only one FoldingMark
		if (this.builderFoldingMarks.stream()
				.anyMatch(mark -> mark.position() == foldingMark.position())) {
			throw new IllegalArgumentException("The FoldingMark for " //$NON-NLS-1$
					+ foldingMark.position().getRegionName()
					+ " is already added"); //$NON-NLS-1$
		}
		this.builderFoldingMarks.add(foldingMark);
		return this;
	}

	public SiteplanXSLPageBuilder setTitleBoxRegion(
			final TitleBoxRegion builderTitleBoxRegion) {
		this.builderTilteBox = builderTitleBoxRegion;
		return this;
	}

	public SiteplanXSLPageBuilder setTitleBoxRegion(
			final RegionPosition position, final double exent,
			final double freefeldSize) {
		return this.setTitleBoxRegion(
				new TitleBoxRegion(position, exent, freefeldSize));
	}

	public SiteplanXSLPageBuilder setRegionBody(final RegionBody regionBody) {
		this.builderRegionBody = regionBody;
		return this;
	}

	public SiteplanXSLPageBuilder setRegionBody(final double width,
			final double height) {
		return this.setRegionBody(new RegionBody(width, height));
	}

	public SiteplanXSLPageBuilder setSignificantInformation(
			final SignificantInformation significant) {
		this.builderSignificant = significant;
		return this;
	}

	public SiteplanXSLPageBuilder setSignificantInformation(final double width,
			final double height) {
		return this.setSignificantInformation(
				new SignificantInformation(width, height));
	}

	public SiteplanXSLPageBuilder setSignificantInformation(
			final double width) {
		return this
				.setSignificantInformation(new SignificantInformation(width));
	}

	public SiteplanXSLPage build() {
		return new SiteplanXSLPage(pageDIN, builderTilteBox,
				builderFoldingMarks, builderRegionBody, builderSignificant);
	}
}
