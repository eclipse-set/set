/**
 * Copyright (c) 2026 DB InfraGO AG and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 */
package org.eclipse.set.feature.export.xlsx;

import org.apache.poi.ss.usermodel.ClientAnchor.AnchorType;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFSimpleShape;
import org.apache.xmlbeans.XmlException;
import org.openxmlformats.schemas.drawingml.x2006.main.CTPositiveSize2D;
import org.openxmlformats.schemas.drawingml.x2006.spreadsheetDrawing.CTMarker;
import org.openxmlformats.schemas.drawingml.x2006.spreadsheetDrawing.CTShape;

/**
 * The water mark was manually created in Excel using WordArt. This class only
 * recreates that WordArt through XML code.
 * 
 * @author truong
 */
@SuppressWarnings("nls")
public class ExcelWaterMark {

	private class CustomXSSFSimpleShape extends XSSFSimpleShape {
		public CustomXSSFSimpleShape(final XSSFDrawing drawing,
				final CTShape ctShape, final XSSFClientAnchor anchor) {
			super(drawing, ctShape);
			this.anchor = anchor;
		}

	}

	String markText;
	XSSFSheet sheet;

	public ExcelWaterMark(final XSSFSheet sheet, final String markText) {
		this.sheet = sheet;
		this.markText = markText;
	}

	private XSSFSimpleShape createSimleShape() throws XmlException {
		final XSSFDrawing drawing = sheet.createDrawingPatriarch();
		final CTMarker marker = getMarker();

		final XSSFClientAnchor xssfClientAnchor = new XSSFClientAnchor(
				Integer.parseInt(marker.getColOff().toString()),
				Integer.parseInt(marker.getRowOff().toString()), 0, 0,
				marker.getCol(), marker.getRow(), 0, 0);
		xssfClientAnchor.setAnchorType(AnchorType.DONT_MOVE_AND_RESIZE);
		xssfClientAnchor.setSize(getPositiveSize());
		final XSSFSimpleShape simpleShape = new CustomXSSFSimpleShape(drawing,
				getCTShape(), xssfClientAnchor);

		simpleShape.setText(markText);
		return simpleShape;
	}

	private static CTMarker getMarker() throws XmlException {
		return CTMarker.Factory
				.parse("""
								<xml-fragment xmlns:xdr="http://schemas.openxmlformats.org/drawingml/2006/spreadsheetDrawing" xmlns:a="http://schemas.openxmlformats.org/drawingml/2006/main">
								  <xdr:col>0</xdr:col>
								  <xdr:colOff>166688</xdr:colOff>
								  <xdr:row>25</xdr:row>
								  <xdr:rowOff>60050</xdr:rowOff>
								</xml-fragment>
						""");
	}

	private static CTPositiveSize2D getPositiveSize() throws XmlException {
		return CTPositiveSize2D.Factory.parse(
				"<xml-fragment cx=\"13327673\" cy=\"2597058\" xmlns:xdr=\"http://schemas.openxmlformats.org/drawingml/2006/spreadsheetDrawing\" xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\"/>");
	}

	private static CTShape getCTShape() throws XmlException {
		return CTShape.Factory
				.parse("""
							<xml-fragment macro="" textlink="" xmlns:xdr="http://schemas.openxmlformats.org/drawingml/2006/spreadsheetDrawing" xmlns:a="http://schemas.openxmlformats.org/drawingml/2006/main">
							  <xdr:nvSpPr>
							    <xdr:cNvPr id="2" name="Rechteck 1">
							      <a:extLst>
							        <a:ext uri="{FF2B5EF4-FFF2-40B4-BE49-F238E27FC236}">
							          <a16:creationId id="{DD41A36F-F8AC-43AE-8DB4-6A834AE8E09D}" xmlns:a16="http://schemas.microsoft.com/office/drawing/2014/main"/>
							        </a:ext>
							      </a:extLst>
							    </xdr:cNvPr>
							    <xdr:cNvSpPr/>
							  </xdr:nvSpPr>
							  <xdr:spPr>
							    <a:xfrm rot="1779400">
							      <a:off x="166688" y="4203425"/>
							      <a:ext cx="13327673" cy="2597058"/>
							    </a:xfrm>
							    <a:prstGeom prst="rect">
							      <a:avLst/>
							    </a:prstGeom>
							    <a:noFill/>
							  </xdr:spPr>
							  <xdr:txBody>
							    <a:bodyPr wrap="square" lIns="91440" tIns="45720" rIns="91440" bIns="45720">
							      <a:spAutoFit/>
							    </a:bodyPr>
							    <a:lstStyle/>
							    <a:p>
							      <a:pPr algn="ctr"/>
							      <a:endParaRPr lang="de-DE" sz="16000" b="0" cap="none" spc="0">
							        <a:ln w="0"/>
							        <a:solidFill>
							          <a:schemeClr val="tx1">
							            <a:alpha val="25000"/>
							          </a:schemeClr>
							        </a:solidFill>
							        <a:effectLst>
							          <a:outerShdw blurRad="38100" dist="19050" dir="2700000" algn="tl" rotWithShape="0">
							            <a:schemeClr val="dk1">
							              <a:alpha val="40000"/>
							            </a:schemeClr>
							          </a:outerShdw>
							        </a:effectLst>
							      </a:endParaRPr>
							    </a:p>
							  </xdr:txBody>
							</xml-fragment>
						""");
	}

	/**
	 * @param targetSheet
	 * @param markText
	 */
	public static void createWaterMark(final XSSFSheet targetSheet,
			final String markText) {
		try {
			final ExcelWaterMark waterMark = new ExcelWaterMark(targetSheet,
					markText);
			waterMark.createSimleShape();
		} catch (final Exception e) {
			throw new RuntimeException(e.getMessage());
		}

	}
}
