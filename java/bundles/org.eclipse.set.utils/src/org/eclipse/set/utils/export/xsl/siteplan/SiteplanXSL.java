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
import static org.eclipse.set.utils.export.xsl.siteplan.SiteplanXSLExtension.RegionPosition.*;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.set.utils.ToolboxConfiguration;
import org.eclipse.set.utils.export.xsl.AbstractTransformTableHeader;
import org.eclipse.set.utils.export.xsl.XMLDocumentExtensions.XMLAttribute;
import org.eclipse.set.utils.export.xsl.XSLConstant;
import org.eclipse.set.utils.export.xsl.XSLConstant.TableAttribute.BorderDirection;
import org.eclipse.set.utils.export.xsl.siteplan.SiteplanXSLExtension.FoldingMark;
import org.eclipse.set.utils.export.xsl.siteplan.SiteplanXSLExtension.PageDIN;
import org.eclipse.set.utils.export.xsl.siteplan.SiteplanXSLExtension.RegionBody;
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

	private final List<BufferedImage> images;
	private final String modelState;

	/**
	 * @param images
	 *            the sheet cut images
	 * @param modelState
	 *            the table state (initial/final/diff)
	 */
	public SiteplanXSL(final List<BufferedImage> images,
			final String modelState) {
		this.images = images;
		this.modelState = modelState;
		this.pageStyle = determinePageStyle();
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
					"Es gibt keine relevant Paper für diese Lageplan mit aktuell Layout, bitte ändern Sie die DPI or teilen Sie die Lageplan kleiner "); //$NON-NLS-1$
		}

		return this.setPageSize(pageStyle.getPageDIN())
				.setRegionBodySize(pageStyle.getRegionBody())
				.setFreeFeldHeight(pageStyle.getTitleBoxRegion())
				.setSignificantSize(pageStyle.getSignificantInformation())
				.setFoldingMarkTemplates(pageStyle.getFoldingMarks())
				.setWatermarkContent().doc;
	}

	private SiteplanXSL setPageSize(final PageDIN pageDIN)
			throws NullPointerException {
		setXSLElementValue(doc, XSL_ATTRIBUTE, ATTR_PAGE_HEIGHT,
				pageDIN.getPageHeight());
		setXSLElementValue(doc, XSL_ATTRIBUTE, ATTR_PAGE_WIDTH,
				pageDIN.getPageWidth());
		return this;
	}

	private SiteplanXSL setRegionBodySize(final RegionBody regionBody)
			throws NullPointerException {
		setXSLElementValue(doc, XSL_VARIABLE, REGION_BODY_HEIGHT,
				regionBody.height());
		setXSLElementValue(doc, XSL_VARIABLE, REGION_BODY_WIDTH,
				regionBody.width());
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

	private SiteplanXSL setWatermarkContent() {
		if (modelState != null) {
			setXSLElementValue(doc, XSL_VARIABLE, WATER_MARK_TEMPLATE_NAME,
					modelState);
		}
		return this;
	}

	private SiteplanXSL setFoldingMarkTemplates(final List<FoldingMark> marks)
			throws NullPointerException {
		final List<FoldingMark> topBottomMarks = marks.stream()
				.filter(mark -> mark.position() == BEFORE
						|| mark.position() == AFTER)
				.toList();
		if (!topBottomMarks.isEmpty()) {
			createFoldingMarkTopBottomTemplate(topBottomMarks.getFirst());
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

	private SiteplanXSLPage determinePageStyle() {
		final Optional<Double> maxHeight = images.stream()
				.map(image -> Double.valueOf(pxToMilimet(image.getHeight())))
				.max(Double::compare);
		final Optional<Double> maxWidth = images.stream()
				.map(image -> Double.valueOf(pxToMilimet(image.getWidth())))
				.max(Double::compare);
		if (maxWidth.isEmpty() || maxHeight.isEmpty()) {
			throw new IllegalArgumentException();
		}
		return avaiablePageSize.stream()
				.filter(page -> page.regionBody.width() >= maxWidth.get()
						.intValue()
						&& page.regionBody.height() >= maxHeight.get()
								.intValue())
				.min((first, second) -> Double.compare(first.regionBody.width(),
						second.regionBody.width()))
				.orElse(null);
	}

	private static double pxToMilimet(final double px) {
		return px * 25.4 / ToolboxConfiguration.getExportDPI();
	}
}
