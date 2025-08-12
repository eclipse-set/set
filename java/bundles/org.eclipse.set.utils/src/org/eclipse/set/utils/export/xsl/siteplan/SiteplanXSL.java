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

import static org.eclipse.set.utils.export.xsl.XMLDocumentExtensions.*;
import static org.eclipse.set.utils.export.xsl.XSLConstant.TableAttrValue.LAYOUT_FIXED;
import static org.eclipse.set.utils.export.xsl.XSLConstant.TableAttribute.*;
import static org.eclipse.set.utils.export.xsl.XSLConstant.XSLFoAttributeName.*;
import static org.eclipse.set.utils.export.xsl.XSLConstant.XSLNodeName.*;
import static org.eclipse.set.utils.export.xsl.XSLConstant.XSLTag.*;
import static org.eclipse.set.utils.export.xsl.siteplan.SiteplanExportPage.*;
import static org.eclipse.set.utils.export.xsl.siteplan.SiteplanXSLExtension.*;
import static org.eclipse.set.utils.export.xsl.siteplan.SiteplanXSLExtension.FoldingMark.DEFAULT_EXTENT_TOP_BOTTOM_RIGHT;
import static org.eclipse.set.utils.export.xsl.siteplan.SiteplanXSLExtension.RegionPosition.*;
import static org.eclipse.set.utils.export.xsl.siteplan.SiteplanXSLExtension.TitleBoxRegion.TITLEBOX_HEIGHT;
import static org.eclipse.set.utils.export.xsl.siteplan.SiteplanXSLExtension.TitleBoxRegion.TITLEBOX_WIDTH;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.set.basis.constants.ToolboxConstants;
import org.eclipse.set.utils.ToolboxConfiguration;
import org.eclipse.set.utils.export.xsl.AbstractTransformTableHeader;
import org.eclipse.set.utils.export.xsl.XMLDocumentExtensions.XMLAttribute;
import org.eclipse.set.utils.export.xsl.XSLConstant;
import org.eclipse.set.utils.export.xsl.XSLConstant.TableAttribute.BorderDirection;
import org.eclipse.set.utils.export.xsl.siteplan.SiteplanXSLExtension.FoldingMark;
import org.eclipse.set.utils.export.xsl.siteplan.SiteplanXSLExtension.RegionBody;
import org.eclipse.set.utils.export.xsl.siteplan.SiteplanXSLExtension.RegionPosition;
import org.eclipse.set.utils.export.xsl.siteplan.SiteplanXSLExtension.SignificantInformation;
import org.eclipse.set.utils.export.xsl.siteplan.SiteplanXSLExtension.TitleBoxRegion;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 * Adjust siteplan_template.xsl to to align with the exported site plan.
 * 
 * @author truong
 */
public class SiteplanXSL {
	private static final String SITEPLAN_XSL_TEMPLATE = "data/export/pdf/siteplan_template.xsl"; //$NON-NLS-1$
	private static final double PAGE_WIDTH_TOLERANCE = 50.0;
	private static final List<SiteplanXSLPage> avaiablePageSize = List.of(
			getSiteplanPageA0(), getSiteplanPageA1(), getSiteplanPageA2(),
			getSiteplanPageA3());

	private Document doc;
	private Node rootNode;
	private final SiteplanXSLPage pageStyle;

	/**
	 * @return page style to export
	 */
	public SiteplanXSLPage getPageStyle() {
		return pageStyle;
	}

	private final BufferedImage image;
	private final double ppm;
	private final int pagePosition;
	private final String pagePostfix;
	private final double customPageWidth;

	/**
	 * @param image
	 *            the sheet cut image
	 * @param ppm
	 *            pixel per physical meter in Openlayer at scale
	 *            {@link ToolboxConfiguration#getSiteplanExportScale()}
	 * @param modelState
	 *            the table state (initial/final/diff)
	 * @param pageIndex
	 *            the page index
	 * @param pagePostfix
	 *            the page indes postfix (+/-)
	 */
	public SiteplanXSL(final BufferedImage image, final double ppm,
			final String modelState, final int pageIndex,
			final String pagePostfix) {
		this.image = image;
		this.ppm = ppm;
		this.pageStyle = determinePageStyle();
		this.pagePosition = pageIndex;
		this.pagePostfix = pagePostfix;
		this.customPageWidth = getCustomPageWidth();
	}

	/**
	 * Get XSL document for export site plan
	 * 
	 * @return the xsl document
	 * @throws ParserConfigurationException
	 *             {@link ParserConfigurationException}
	 * @throws SAXException
	 *             {@link SAXException}
	 * @throws IOException
	 *             {@link IOException}
	 * @throws NullPointerException
	 *             {@link NullPointerException}
	 * @throws UnsupportedOperationException
	 *             when given't relevant page for the image
	 */
	public Document getXSLDocument()
			throws ParserConfigurationException, SAXException, IOException,
			NullPointerException, UnsupportedOperationException {
		doc = AbstractTransformTableHeader.parseTemplate(SITEPLAN_XSL_TEMPLATE);
		rootNode = doc.getElementsByTagName(XSL_STYLESHEET).item(0);
		if (pageStyle == null) {
			throw new UnsupportedOperationException(
					"Es gibt kein passendes Papierformat für diesen Lageplan im aktuellen Layout. Bitte DPI-Zahl or Skalierungsfaktor ändern"); //$NON-NLS-1$
		}

		return this.setPageSize(pageStyle.getPageHeight())
				.setRegionBodySize(pageStyle.getRegionBody())
				.setFreeFeldHeight(pageStyle.getTitleBoxRegion())
				.setSignificantSize(pageStyle.getSignificantInformation())
				.setFoldingMarkTemplates(pageStyle.getFoldingMarks())
				.setFoldingMarkRightRegionWidth(pageStyle.getFoldingMarks())
				.setPagePosition().doc;
	}

	private double getCustomPageWidth() {
		final double imageWidthMilimet = pxToMilimeter(image.getWidth(), ppm);
		final Optional<SiteplanXSLPage> relevantPageWidth = avaiablePageSize
				.stream()
				.filter(page -> Math.abs(page.getRegionBody().width()
						- imageWidthMilimet) < PAGE_WIDTH_TOLERANCE)
				.findFirst();

		if (relevantPageWidth.isEmpty()) {
			return pageStyle.getPageWidth() + imageWidthMilimet
					- pageStyle.getRegionBody().width();
		}
		return relevantPageWidth.get().getPageWidth();
	}

	private SiteplanXSL setPageSize(final double pageHeight)
			throws NullPointerException {
		setXSLElementValue(doc, XSL_ATTRIBUTE, ATTR_PAGE_HEIGHT, pageHeight);
		setXSLElementValue(doc, XSL_ATTRIBUTE, ATTR_PAGE_WIDTH,
				customPageWidth);
		return this;
	}

	private SiteplanXSL setRegionBodySize(final RegionBody regionBody)
			throws NullPointerException {
		setXSLElementValue(doc, XSL_VARIABLE, REGION_BODY_HEIGHT,
				regionBody.height());
		setXSLElementValue(doc, XSL_VARIABLE, REGION_BODY_WIDTH,
				pxToMilimeter(image.getWidth(), ppm));
		return this;
	}

	private SiteplanXSL setSignificantSize(
			final SignificantInformation significant)
			throws NullPointerException {
		setXSLElementValue(doc, XSL_VARIABLE, SIGNIFICANT_HEIGHT,
				significant.height());
		setXSLElementValue(doc, XSL_VARIABLE, SIGNIFICANT_WIDTH,
				significant.width());
		return this;
	}

	private SiteplanXSL setFreeFeldHeight(final TitleBoxRegion titleBox)
			throws NullPointerException {
		setXSLElementValue(doc, XSL_VARIABLE, SITEPLAN_FREEFELD_HEIGHT,
				titleBox.freefeldSize());
		return this;
	}

	private SiteplanXSL setFoldingMarkTemplates(final List<FoldingMark> marks)
			throws NullPointerException {
		final List<Double> customFolding = SiteplanXSLExtension
				.calculateCustomFoldingTopBottom(customPageWidth);
		if (customFolding != null && !customFolding.isEmpty()) {
			createFoldingMarkTopBottomTemplate(
					new FoldingMark(AFTER, customFolding));
		}

		final List<FoldingMark> sideMarks = marks.stream()
				.filter(mark -> mark.position() == START
						|| mark.position() == END)
				.toList();
		if (!sideMarks.isEmpty()) {
			createFoldingMarkSideTemplate(sideMarks.getFirst());
		}

		return this;
	}

	private void createFoldingMarkTopBottomTemplate(
			final FoldingMark topBottomMark) {

		final Element topBottomMarkTemplate = getFoldingMarkTemplate(
				SITEPLAN_FOLDING_MARK_TOP_BOTTOM);

		final Element tableElement = createXMLElementWithAttr(doc, FO_TABLE,
				new XMLAttribute(TABLE_LAYOUT, LAYOUT_FIXED),
				new XMLAttribute(TABLE_WIDTH, "100%")); //$NON-NLS-1$
		topBottomMarkTemplate.appendChild(tableElement);

		final Element tableBodyElement = doc.createElement(FO_TABLE_BODY);
		final Element tableRow = createXMLElementWithAttr(doc, FO_TABLE_ROW,
				new XMLAttribute(ATTR_HEIGHT, topBottomMark.markWidth()));
		tableBodyElement.appendChild(tableRow);
		IntStream.range(0, topBottomMark.distances().size()).forEach(index -> {
			final Double distance = topBottomMark.distances().get(index);
			final Element tableColumn = createXMLElementWithAttr(doc,
					FO_TABLE_COLUMN,
					new XMLAttribute(COLUMN_WIDTH, distance.doubleValue()));
			tableElement.appendChild(tableColumn);
			// No need add style for last mark block
			final Element tableCell = topBottomMark.distances().size()
					- 1 == index
							? doc.createElement(FO_TABLE_CELL)
							: createXMLElementWithAttr(doc, FO_TABLE_CELL,
									new XMLAttribute(
											BorderDirection.RIGHT
													.getDirectionString(),
											XSLConstant.FOLDING_MARK_STYLE));
			tableRow.appendChild(tableCell);
			final Element tableCellBlock = createXMLElementWithAttr(doc,
					FO_BLOCK, new XMLAttribute(ATTR_COLOR, "white")); //$NON-NLS-1$
			tableCellBlock.setTextContent("."); //$NON-NLS-1$
			tableCell.appendChild(tableCellBlock);
		});
		tableElement.appendChild(tableBodyElement);
	}

	private void createFoldingMarkSideTemplate(final FoldingMark sideMark) {
		if (sideMark.distances().isEmpty()) {
			return;
		}
		final Element sideMarkTemplate = getFoldingMarkTemplate(
				SITEPLAN_FOLDING_MARK_SIDE);

		final Element tableElement = createXMLElementWithAttr(doc, FO_TABLE,
				new XMLAttribute(TABLE_LAYOUT, LAYOUT_FIXED),
				new XMLAttribute(ATTR_WIDTH, "100%")); //$NON-NLS-1$
		sideMarkTemplate.appendChild(tableElement);

		final Element tableColumn = createXMLElementWithAttr(doc,
				FO_TABLE_COLUMN,
				new XMLAttribute(COLUMN_WIDTH, sideMark.markWidth()));
		tableElement.appendChild(tableColumn);

		final Element tableBodyElement = doc.createElement(FO_TABLE_BODY);
		tableElement.appendChild(tableBodyElement);

		IntStream.range(0, sideMark.distances().size()).forEach(index -> {
			final Double distance = sideMark.distances().get(index);
			final Element tableRow = createXMLElementWithAttr(doc, FO_TABLE_ROW,
					new XMLAttribute(ATTR_HEIGHT, distance.doubleValue()));
			tableBodyElement.appendChild(tableRow);
			// No need add style for last mark block
			final Element tableCell = sideMark.distances().size() - 1 == index
					? doc.createElement(FO_TABLE_CELL)
					: createXMLElementWithAttr(doc, FO_TABLE_CELL,
							new XMLAttribute(
									BorderDirection.BOTTOM.getDirectionString(),
									XSLConstant.FOLDING_MARK_STYLE));
			tableRow.appendChild(tableCell);
			final Element tableCellBlock = createXMLElementWithAttr(doc,
					FO_BLOCK, new XMLAttribute(ATTR_COLOR, "white")); //$NON-NLS-1$
			tableCellBlock.setTextContent("."); //$NON-NLS-1$
			tableCell.appendChild(tableCellBlock);
		});
	}

	private Element getFoldingMarkTemplate(final String templateName) {
		Element foldingMarkTemplate = AbstractTransformTableHeader
				.findNodebyTagName(doc, XSL_TEMPLATE, ATTR_NAME, templateName)
				.orElse(null);
		// Clear template
		if (foldingMarkTemplate != null) {
			rootNode.removeChild(foldingMarkTemplate);
		}
		foldingMarkTemplate = createXMLElementWithAttrName(doc, XSL_TEMPLATE,
				templateName);
		rootNode.appendChild(foldingMarkTemplate);
		return foldingMarkTemplate;
	}

	private SiteplanXSL setFoldingMarkRightRegionWidth(
			final List<FoldingMark> foldingMarks) {
		final FoldingMark markRight = foldingMarks.stream()
				.filter(mark -> mark.position() == END)
				.findFirst()
				.orElse(null);
		if (markRight != null) {
			setXSLElementValue(doc, XSL_VARIABLE,
					SITEPLAN_FOLDING_MARK_RIGHT_WIDTH, markRight.extent());
		} else {
			setXSLElementValue(doc, XSL_VARIABLE,
					SITEPLAN_FOLDING_MARK_RIGHT_WIDTH,
					DEFAULT_EXTENT_TOP_BOTTOM_RIGHT);
		}

		return this;
	}

	private SiteplanXSL setPagePosition() {
		setXSLElementValue(doc, XSL_VARIABLE, SITEPLAN_PAGEPOSITION,
				String.valueOf(pagePosition));
		setXSLElementValue(doc, XSL_VARIABLE, SITEPLAN_PAGE_POSTFIX,
				pagePostfix);
		return this;
	}

	private SiteplanXSLPage determinePageStyle() {
		final double imgHeightMillimet = pxToMilimeter(image.getHeight(), ppm);
		final Optional<SiteplanXSLPage> xslPage = avaiablePageSize.stream()
				.filter(page -> page.regionBody.height() >= imgHeightMillimet)
				.min((first, second) -> Double.compare(
						first.regionBody.height(), second.regionBody.height()));
		if (xslPage.isPresent()) {
			return xslPage.get();
		}
		final RegionBody customRegion = new RegionBody(
				pxToMilimeter(image.getWidth(), ppm), imgHeightMillimet);
		final double customWidth = customRegion.width() + TITLEBOX_WIDTH
				+ DEFAULT_MARGIN_LEFT + DEFAULT_MARGIN_RIGHT;
		final double customHeight = customRegion.height()
				+ DEFAULT_MARGIN_TOP_BOTTOM * 2;
		final TitleBoxRegion titleBoxRegion = new TitleBoxRegion(
				RegionPosition.END, TITLEBOX_WIDTH + DEFAULT_MARGIN_RIGHT,
				customHeight - DEFAULT_MARGIN_TOP_BOTTOM - TITLEBOX_HEIGHT);
		return new SiteplanXSLPage(customHeight, customWidth, titleBoxRegion,
				customRegion, Collections.emptyList(),
				new SignificantInformation(205));
	}

	/**
	 * Transform pixel to millimeter
	 * 
	 * @param px
	 *            the pixel value
	 * @param ppm
	 *            pixel per physical meter in Openlayer at scale
	 *            {@link ToolboxConfiguration#getSiteplanExportScale()}
	 * @return the millimeter value with scale
	 *         {@link ToolboxConfiguration#getSiteplanExportScale()}
	 */
	public static double pxToMilimeter(final double px, final double ppm) {
		if (ppm <= 0) {
			return px * 25.4 / ToolboxConfiguration.getExportDPI();
		}

		final BigDecimal pixelProMillimeter = BigDecimal.valueOf(ppm)
				.divide(BigDecimal.valueOf(1000),
						ToolboxConstants.ROUNDING_TO_PLACE, RoundingMode.FLOOR);
		final BigDecimal toMillimeter = BigDecimal.valueOf(px)
				.divide(pixelProMillimeter, ToolboxConstants.ROUNDING_TO_PLACE,
						RoundingMode.FLOOR);
		// Scale to given value
		return toMillimeter
				.divide(BigDecimal
						.valueOf(ToolboxConfiguration.getSiteplanExportScale()),
						ToolboxConstants.ROUNDING_TO_PLACE, RoundingMode.FLOOR)
				.doubleValue();
	}
}
