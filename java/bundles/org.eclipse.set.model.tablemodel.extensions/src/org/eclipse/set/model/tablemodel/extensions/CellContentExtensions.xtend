/**
 * Copyright (c) 2017 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.tablemodel.extensions

import com.google.common.base.Strings
import com.google.common.html.HtmlEscapers
import org.eclipse.set.model.tablemodel.CellContent
import org.eclipse.set.model.tablemodel.CompareCellContent
import org.eclipse.set.model.tablemodel.StringCellContent
import org.eclipse.set.model.tablemodel.TableCell
import org.eclipse.set.utils.ToolboxConfiguration

import static extension org.eclipse.set.model.tablemodel.extensions.TableCellExtensions.*
import static extension org.eclipse.set.utils.StringExtensions.*
import org.eclipse.set.model.tablemodel.MultiColorCellContent
import org.eclipse.set.model.tablemodel.MultiColorContent

/**
 * Extensions for {@link CellContent}.
 * 
 * @author Schaefer
 */
class CellContentExtensions {

	public static val String WARNING_MARK_YELLOW = "<!-- warning-mark-yellow -->"
	public static val String WARNING_MARK_RED = "<!-- warning-mark-red -->"
	public static val String WARNING_MARK_BLACK = "<!-- warning-mark-black -->"
	static val String ERROR_PREFIX = "Error:"

	/**
	 * Returns a formatted string representation intended for rendering as
	 * rich text. This method should only be called in the context of rendering
	 * the content, e.g. in NatTable content providers.
	 * 
	 * @param content this cell content
	 * 
	 * @return a formatted string representation of the cell content
	 */
	static def dispatch String getRichTextValue(CellContent content) {
		return '''Content «content.class.simpleName» not supported.'''
	}

	static def dispatch String getRichTextValue(Void content) {
		return ""
	}

	static def dispatch String getRichTextValue(StringCellContent content) {
		return '''<p style="text-align:«content.textAlign»">«content.valueFormat»</p>'''
	}

	static def dispatch String getRichTextValue(CompareCellContent content) {
		val oldFormat = content.oldFormat
		val newFormat = content.newFormat
		val oldAndNewExists = !oldFormat.empty && !newFormat.empty
		return '''<p style="text-align:«content.textAlign»">«oldFormat»«IF oldAndNewExists»<br></br>«ENDIF»«content.newFormat»</p>'''
	}
	
	static def dispatch String getRichTextValue(MultiColorCellContent content) {
		return '''<p style="text-align:«content.textAlign»">«content.multiColorFormat»</p>'''
	}

	/**
	 * @param content this cell content
	 * 
	 * @return an unformatted string representation intended for rendering as simple text
	 */
	static def dispatch String getPlainStringValue(CellContent content) {
		return '''Content «content.class.simpleName» not supported.'''
	}

	static def dispatch String getPlainStringValue(Void content) {
		return ""
	}

	static def dispatch String getPlainStringValue(StringCellContent content) {
		return content.value
	}

	static def dispatch String getPlainStringValue(CompareCellContent content) {
		return '''«content.oldValue»/«content.newValue»'''
	}

	/**
	 * @param text the text
	 * 
	 * @return whether the text should be displayed as a warning mark
	 */
	static def boolean isErrorText(String text) {
		return text !== null && text.startsWith(ERROR_PREFIX)
	}

	/**
	 * @param this cell content
	 * 
	 * @return the table cell of this cell content
	 */
	static def TableCell getTableCell(CellContent content) {
		return content.eContainer as TableCell
	}

	private static def String getTextAlign(CellContent content) {
		return content.tableCell.format.textAlignment.literal
	}

	private static def dispatch String getValueFormat(StringCellContent content) {
		return '''<span>«content.valueHtmlString»</span>'''
	}
	
	private static def dispatch String getValueFormat(MultiColorCellContent content) {
		return '''<span>«content.multiColorFormat.htmlString»</span>'''
	}

	private static def String getOldFormat(CompareCellContent content) {
		if (Strings.isNullOrEmpty(content.oldValue)) {
			return ""
		}
		return '''<span style="background-color:rgb(255,255, 0)"><s>«content.oldValueHtmlString»</s></span>'''
	}

	private static def String getNewFormat(CompareCellContent content) {
		if (Strings.isNullOrEmpty(content.newValue)) {
			return ""
		}
		return '''<span style="color:rgb(255, 0, 0)">«content.newValueHtmlString»</span>'''
	}
	
	private static def String getMultiColorFormat(MultiColorCellContent content) {
		if (content.value.isEmpty) {
			return ""
		}
		return '''«FOR element : content.value SEPARATOR content.seperator»«element.multiColorFormat»«ENDFOR»''' 
	}
	
	private static def String getMultiColorFormat(MultiColorContent content) {
		if (Strings.isNullOrEmpty(content.multiColorValue)) {
			return Strings.isNullOrEmpty(content.stringFormat)
				? ""
				: content.stringFormat.htmlString
		}
		
		val value = '''<span style="background-color:rgb(255,255, 0)">«content
			.getMultiColorValueHtmlString(WARNING_MARK_YELLOW)»</span><span style="color:rgb(255, 0, 0)">«content
			.getMultiColorValueHtmlString(WARNING_MARK_RED)»</span>'''
		return '''<span>«String.format(content.stringFormat, value)»</span>'''
	}
	

	private static def String getValueHtmlString(StringCellContent content) {
		if (content.value.isErrorText &&
			!ToolboxConfiguration.developmentMode) {
			return WARNING_MARK_BLACK
		} else {
			return content.value.htmlString
		}
	}

	private static def String getOldValueHtmlString(
		CompareCellContent content) {
		if (content.oldValue.isErrorText &&
			!ToolboxConfiguration.developmentMode) {
			return WARNING_MARK_YELLOW
		} else {
			return content.oldValue.htmlString
		}
	}

	private static def String getNewValueHtmlString(
		CompareCellContent content) {
		if (content.newValue.isErrorText &&
			!ToolboxConfiguration.developmentMode) {
			return WARNING_MARK_RED
		} else {
			return content.newValue.htmlString
		}
	}
	
	private static def String getMultiColorValueHtmlString(MultiColorContent content, String warningColor) {
		if (content.multiColorValue.isErrorText && !ToolboxConfiguration.developmentMode) {
			return warningColor
		}
		return content.multiColorValue.htmlString
	}

	private static def String getHtmlString(String value) {
		return HtmlEscapers.htmlEscaper.escape(
			value.intersperseWithZeroSpacesSC
		).replaceAll("\n", "<br></br>")
	}

	/**
	 * @param content this cell content
	 * @param other another cell content
	 * 
	 * @return whether the content has an equal plain text representation
	 */
	def static boolean isEqual(CellContent content, CellContent other) {
		return content.plainStringValue.equals(other.plainStringValue)
	}
}
