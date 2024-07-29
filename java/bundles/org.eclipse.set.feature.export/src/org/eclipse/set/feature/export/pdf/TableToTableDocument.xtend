/**
 * Copyright (c) 2017 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.export.pdf

import java.util.List
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.parsers.ParserConfigurationException
import org.eclipse.set.basis.FreeFieldInfo
import org.eclipse.set.model.tablemodel.CellContent
import org.eclipse.set.model.tablemodel.CompareCellContent
import org.eclipse.set.model.tablemodel.CompareFootnoteContainer
import org.eclipse.set.model.tablemodel.FootnoteContainer
import org.eclipse.set.model.tablemodel.MultiColorCellContent
import org.eclipse.set.model.tablemodel.MultiColorContent
import org.eclipse.set.model.tablemodel.SimpleFootnoteContainer
import org.eclipse.set.model.tablemodel.Table
import org.eclipse.set.model.tablemodel.TableContent
import org.eclipse.set.model.tablemodel.TableRow
import org.eclipse.set.model.titlebox.PlanningOffice
import org.eclipse.set.model.titlebox.StringField
import org.eclipse.set.model.titlebox.Titlebox
import org.eclipse.set.utils.ToolboxConfiguration
import org.eclipse.set.utils.table.TableSpanUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.w3c.dom.Attr
import org.w3c.dom.Document
import org.w3c.dom.Element

import static extension org.eclipse.set.model.tablemodel.extensions.CellContentExtensions.*
import static extension org.eclipse.set.model.tablemodel.extensions.TableContentExtensions.*
import static extension org.eclipse.set.model.tablemodel.extensions.TableExtensions.*
import static extension org.eclipse.set.model.tablemodel.extensions.TableRowExtensions.*
import static extension org.eclipse.set.utils.StringExtensions.*
import java.util.Collections
import java.util.function.Consumer
import java.util.function.Function
import java.util.function.BiConsumer

/**
 * Transformation from {@link Table} to TableDocument {@link Document}.
 * 
 * @author Schaefer
 */
class TableToTableDocument {

	static final Logger logger = LoggerFactory.getLogger(
		typeof(TableToTableDocument));

	val Document doc
	var String tablename
	var int groupNumber
	var TableSpanUtils spanUtils

	static val String FOOTNOTE_SEPARATOR = ", "

	private new() throws ParserConfigurationException {
		val docFactory = DocumentBuilderFactory.newInstance
		val docBuilder = docFactory.newDocumentBuilder
		doc = docBuilder.newDocument
	}

	/**
	 * Creates a new Transformation.
	 */
	static def TableToTableDocument createTransformation() throws ParserConfigurationException {
		return new TableToTableDocument
	}

	/**
	 * @param table the table model
	 * @param titlebox the titlebox model
	 * 
	 * @return the table document
	 */
	def Document transformToDocument(Table table, Titlebox titlebox,
		FreeFieldInfo freeFieldInfo) {
		tablename = table?.rootDescriptor?.label
		logger.debug('''tablename=«tablename»''')
		doc.appendChild(table.transform(titlebox, freeFieldInfo))
		return doc
	}

	/**
	 * @param titlebox the titlebox model
	 * 
	 * @return the titlebox document
	 */
	def Document transformToDocument(Titlebox titlebox) {
		tablename = "titlebox export"
		doc.appendChild(titlebox.transform)
		return doc
	}

	private def Element create doc.createElement("Table") transform(Table table,
		Titlebox titlebox, FreeFieldInfo freeFieldInfo) {
		appendChild(table.tablecontent.transform)
		appendChild(transformToFootnotes(table))
		appendChild(titlebox.transform)
		appendChild(freeFieldInfo.transform)
		return
	}

	private def Element create doc.createElement("Rows") transform(
		TableContent content) {
		val rowsElement = it
		val rows = content.table.tableRows
		spanUtils = new TableSpanUtils(rows)
		rows.forEach[rowsElement.appendChild(transform(rows))]
		if (ToolboxConfiguration.pdfExportTestFilling &&
			content.rowgroups.empty) {
			rowsElement.appendChild(createTestRowElement("1", content))
			rowsElement.appendChild(createTestRowElement("2", content))
			rowsElement.appendChild(createTestRowElement("3", content))
		}
		return
	}

	private def Element createTestRowElement(Element rowsElement,
		String groupNumber, TableContent content) {
		val rowElement = doc.createElement("Row")
		val numberAttr = doc.createAttribute("group-number")
		numberAttr.value = groupNumber
		rowElement.attributeNode = numberAttr
		content.table.columns.indexed.forEach [
			rowElement.appendChild(createTestCell(key + 1))
		]
		return rowElement
	}

	private def Element create doc.createElement("Row") transform(TableRow row,
		List<TableRow> rows) {

		// row number
		attributeNode = row.transformToGroupNumber(rows)

		// cells
		val rowElement = it
		val rowIndex = rows.indexOf(row)
		val cells = row.content

		logger.
			debug('''groupNumber=«groupNumber» («FOR c : cells SEPARATOR " "»«c.plainStringValue»«ENDFOR»)''')

		cells.indexed.forEach [
			logger.debug('''column=«key»''')
			val isRemarkColumn = value.isRemarkColumn(cells)

			// Check for required span merges
			var rowSpan = 1
			if (spanUtils.isMergeAllowed(key, rowIndex)) {
				val spanUp = spanUtils.getRowSpanUp(key, rowIndex);
				val spanDown = spanUtils.getRowSpanDown(key, rowIndex);

				// If spanUp > 0, we have already merged this span
				// in a previous iteration. Otherwise adjust the rowSpan
				if (spanUp == 0 && spanDown > 0) {
					rowSpan = spanDown + 1;
				} else if (spanUp > 0) {
					rowSpan = 0
				}
			}

			if (rowSpan > 0) {
				rowElement.appendChild(
					value.createCell(row.footnotes, key + 1, rowSpan,
						isRemarkColumn))
			}
		]
		return
	}

	private def Element createTestCell(int columnNumber) {
		val cellElement = doc.createElement("Cell")
		cellElement.attributeNode = createColumnAttribute(columnNumber)
		cellElement.appendChild(createTestContent(columnNumber))
		return cellElement
	}

	private def dispatch Element createCell(CellContent content,
		FootnoteContainer fc, int columnNumber, int rowSpan,
		boolean isRemarkColumn) {
		val cellElement = doc.createElement("Cell")

		cellElement.attributeNode = createColumnAttribute(columnNumber)
		cellElement.attributeNode = createRowSpanAttribute(rowSpan)
		cellElement.appendChild(
			content.createContent(fc, columnNumber, isRemarkColumn))

		return cellElement
	}

	private def dispatch Element createCell(Void content, FootnoteContainer fc,
		int columnNumber, int rowSpan, boolean isRemarkColumn) {
		val cellElement = doc.createElement("Cell")

		cellElement.attributeNode = createColumnAttribute(columnNumber)
		cellElement.attributeNode = createRowSpanAttribute(rowSpan)
		cellElement.appendChild(
			null.createContent(fc, columnNumber, isRemarkColumn))

		return cellElement
	}

	private def boolean isRemarkColumn(CellContent content,
		List<CellContent> rowContent) {
		return rowContent.last === content
	}

	private def Attr create doc.createAttribute("group-number") transformToGroupNumber(
		TableRow row, List<TableRow> rows) {
		groupNumber = rows.indexOf(row) + 1
		value = groupNumber.toString
		logger.debug('''group-number=«value»''')
		return
	}

	private def String checkForTestOutput(String text, int columnNumber) {
		if (ToolboxConfiguration.isPdfExportTestFilling && text.nullOrEmpty) {
			return Integer.toString(columnNumber)
		}
		return text
	}

	private def Element createTestContent(int columnNumber) {
		val element = doc.createElement("StringContent")
		element.textContent = Integer.toString(columnNumber)
		return element
	}

	private def dispatch Element createContent(CellContent content,
		FootnoteContainer fc, int columnNumber, boolean isRemarkColumn) {
		var element = doc.createElement("StringContent")
		if (isRemarkColumn)
			element = doc.createElement("DiffContent")

		val stringValue = content.plainStringValue
		if (isRemarkColumn) {
			val child = doc.createElement("UnchangedValue")
			element.appendChild(
				stringValue.addContentToElement(child, columnNumber,
					isRemarkColumn))
			element.addFootnoteContent(fc, columnNumber, isRemarkColumn)
		} else {
			element.textContent = stringValue.checkForTestOutput(columnNumber).
				intersperseWithZeroSpacesSC

		}

		return element
	}

	private def dispatch Element createContent(Void content,
		FootnoteContainer fc, int columnNumber, boolean isRemarkColumn) {
		val element = doc.createElement("StringContent")
		element.textContent = "".checkForTestOutput(columnNumber)
		if (isRemarkColumn) {
			element.addFootnoteContent(fc, columnNumber, isRemarkColumn)
		} else
			logger.
				warn('''no content at groupNumber=«groupNumber» column=«columnNumber»''')
		return element
	}

	private def Element createCompareValueElement(String warning_mark,
		String value) {
		switch (warning_mark) {
			case WARNING_MARK_BLACK:
				return doc.createElement("UnchangedValue")
			case WARNING_MARK_YELLOW:
				return doc.createElement("OldValue")
			case WARNING_MARK_RED:
				return doc.createElement("NewValue")
			default:
				return null
		}
	}

	private def dispatch Element createContent(CompareCellContent content,
		FootnoteContainer fc, int columnNumber, boolean isRemarkColumn) {
		val element = doc.createElement("DiffContent")
		formatCompareContent(
			content.oldValue,
			content.newValue,
			[doc.createElement("OldValue")],
			[doc.createElement("UnchangedValue")],
			[doc.createElement("NewValue")],
			[ text, child |
				text.addContentToElement(child, columnNumber, isRemarkColumn)
			]
		).forEach[element.appendChild(it)]

		if (isRemarkColumn) {
			element.addFootnoteContent(fc, columnNumber, isRemarkColumn)
		}
		return element
	}

	private def dispatch Element createContent(MultiColorCellContent content,
		FootnoteContainer fc, int columnNumber, boolean isRemarkColumn) {
		val element = doc.createElement("MultiColorContent")
		for (var i = 0; i < content.value.size; i++) {
			element.appendChild(
				content.value.get(i).createMultiColorElement(columnNumber,
					isRemarkColumn))
			if (i < content.value.size - 1) {
				val separator = doc.createElement("SimpleValue")
				separator.textContent = content.separator
				element.appendChild(separator)
			}
		}

		if (isRemarkColumn) {
			element.addFootnoteContent(fc, columnNumber, isRemarkColumn)
		}
		return element
	}

	private def void addFootnoteChild(Element element, String content,
		String mark, int columnNumber, boolean isRemarkColumn) {
		val child = createCompareValueElement(mark, content)
		element.appendChild(
			content.addContentToElement(child, columnNumber, isRemarkColumn))
	}

	private dispatch def void addFootnoteContent(Element element, Void fc,
		int columnNumber, boolean isRemarkColumn) {
		// No footnotes
	}

	private dispatch def void addFootnoteContent(Element element,
		SimpleFootnoteContainer fc, int columnNumber, boolean isRemarkColumn) {

		val footnotes = fc.footnotes.map['''*«getFootnoteNumber(fc, it)»'''].
			iterableToString(FOOTNOTE_SEPARATOR)
		element.addFootnoteChild(footnotes, WARNING_MARK_BLACK, columnNumber,
			isRemarkColumn)
	}

	private dispatch def void addFootnoteContent(Element element,
		CompareFootnoteContainer fc, int columnNumber, boolean isRemarkColumn) {

		val oldFootnotes = fc.oldFootnotes.map [
			'''*«getFootnoteNumber(fc, it)»'''
		].iterableToString(FOOTNOTE_SEPARATOR)
		val newFootnotes = fc.newFootnotes.map [
			'''*«getFootnoteNumber(fc, it)»'''
		].iterableToString(FOOTNOTE_SEPARATOR)
		val unchangedFootnotes = fc.unchangedFootnotes.map [
			'''*«getFootnoteNumber(fc, it)»'''
		].iterableToString(FOOTNOTE_SEPARATOR)

		element.addFootnoteChild(oldFootnotes, WARNING_MARK_YELLOW,
			columnNumber, isRemarkColumn)
		element.addFootnoteChild(unchangedFootnotes, WARNING_MARK_BLACK,
			columnNumber, isRemarkColumn)
		element.addFootnoteChild(newFootnotes, WARNING_MARK_RED, columnNumber,
			isRemarkColumn)
	}

	private def Element createMultiColorElement(MultiColorContent content,
		int columnNumber, boolean isRemarkColumn) {
		if (content.multiColorValue === null) {
			return content.stringFormat.createContentElement("SimpleValue",
				columnNumber, isRemarkColumn)

		}
		// IMPROVE: currently the order of multicolor content is static.
		// The underlying issue is a limitation in XSL 1.0 and string splitting.
		val multiColorElement = content.stringFormat.replace("%s", "").
			createContentElement("MultiColorValue", columnNumber,
				isRemarkColumn)
		multiColorElement.setAttribute("multicolorValue",
			content.multiColorValue)
		return multiColorElement
	}

	private def Attr createColumnAttribute(int columnNumber) {
		val columnAttr = doc.createAttribute("column-number")
		columnAttr.value = Integer.toString(columnNumber)
		return columnAttr
	}

	private def Attr createRowSpanAttribute(int rowSpan) {
		val attr = doc.createAttribute("number-rows-spanned")
		attr.value = Integer.toString(rowSpan)
		return attr
	}

	private def Element createContentElement(String content, String elementName,
		int columnNumber, boolean isRemarkColumn) {
		val element = doc.createElement(elementName)
		return content.addContentToElement(element, columnNumber,
			isRemarkColumn)
	}

	private def Element addContentToElement(String content, Element element,
		int columnNumber, boolean isRemarkColumn) {
		val checkOutput = content.checkForTestOutput(columnNumber)
		element.textContent = if (isRemarkColumn)
			checkOutput
		else
			checkOutput.intersperseWithZeroSpacesSC
		return element
	}

	private def Element create doc.createElement("TitleBox") transform(
		Titlebox titlebox) {
		val titleboxElement = it
		titlebox.field.indexed.forEach [
			titleboxElement.appendChild(transform(it.key, it.value))
		]

		titleboxElement.appendChild(transform(titlebox.planningOffice))
		return
	}

	private def Element create doc.createElement("PlanningOffice") transform(
		PlanningOffice po) {
		attributeNode = po.variant.transformToAttr("variant")
		attributeNode = po.logo.transformToAttr("logo")
		appendChild(po.name.transform("Name"))
		appendChild(po.group.transform("Group"))
		appendChild(po.location.transform("Location"))
		appendChild(po.phone.transform("Phone"))
		appendChild(po.email.transform("Email"))
		return
	}

	private def Element create doc.createElement(name) transform(StringField sf,
		String name) {
		textContent = sf.text
		attributeNode = sf.fontsize.transformToAttr("fontsize")
	}

	private def Element create doc.createElement("Field") transform(int index,
		String value) {
		attributeNode = index.transformToAddressAttr
		textContent = value?.intersperseWithZeroSpacesSC
		return
	}

	private def Attr transformToAddressAttr(int index) {
		val address = index + 1
		return transformToAttr(address.toString, "address")
	}

	private def Attr transformToAttr(String value, String attr) {
		val it = doc.createAttribute(attr)
		it.value = value
		return it
	}

	private def Element create doc.createElement("Freefield") transform(
		FreeFieldInfo freeFieldInfo) {
		val significantInformation = freeFieldInfo.significantInformation
		if (significantInformation !== null) {
			appendChild(
				significantInformation.transformToSignificantInformation)
		}
		return
	}

	private def Element create doc.createElement("SignificantInformation") transformToSignificantInformation(
		String significantInformation) {
		textContent = significantInformation
		return
	}

	private def Element create doc.createElement("Footnotes")
	transformToFootnotes(Table table) {
		val element = it
		table.allFootnotes.forEach [
			element.appendChild(transform(it))
		]
		return
	}

	private def Element transform(Pair<Integer, String> footnote) {
		val it = doc.createElement("Footnote")
		attributeNode = createFootnoteAttribute(footnote.key)
		textContent = footnote.value
		return it
	}

	private def Attr createFootnoteAttribute(Integer number) {
		val footnoteAttr = doc.createAttribute("footnote-number")
		footnoteAttr.value = Integer.toString(number)
		return footnoteAttr
	}
}
