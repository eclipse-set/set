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
import org.eclipse.set.model.tablemodel.MultiColorCellContent
import org.eclipse.set.model.tablemodel.MultiColorContent
import org.eclipse.set.model.tablemodel.StringCellContent
import org.eclipse.set.model.tablemodel.TableCell
import org.eclipse.set.utils.ToolboxConfiguration

import static extension org.eclipse.set.model.tablemodel.extensions.TableCellExtensions.*
import static extension org.eclipse.set.utils.StringExtensions.*

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
		if (content.oldValue.equals(content.newValue)) {
			return '''<p style="text-align:«content.textAlign»">«
			»«content.newValue.iterableToString(content.separator).htmlString»</p>'''
		}
		val result = <String>newLinkedList
		#[content.oldValue, content.newValue].flatten.filterNull.toSet.sort.
			forEach [
				result.add(content.getCompareContentValueFormat([
					getCompareValueFormat($0, $1)
				], it))
			]

		return '''<p style="text-align:«content.textAlign»">«
		»«result.iterableToString(content.separator === null || content.separator.equals("\r\n")
			? "<br></br>" 
			: content.separator
		)»</p>'''
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
		return content.value.iterableToString(content.separator)
	}

	static def dispatch String getPlainStringValue(CompareCellContent content) {
		return '''«content.oldValue»/«content.newValue»'''
	}

	static def dispatch String getPlainStringValue(
		MultiColorCellContent content) {
		return '''«FOR value : content.value SEPARATOR content.separator»«
		»«String.format(value.stringFormat, value.multiColorValue)»«ENDFOR»'''
	}

	static def dispatch Iterable<String> getStringValueIterable(Void content) {
		return #[]
	}

	static def dispatch Iterable<String> getStringValueIterable(
		CellContent content) {
		return #[content.plainStringValue]
	}

	static def dispatch Iterable<String> getStringValueIterable(
		StringCellContent content) {
		return content.value
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

	private static def dispatch String getValueFormat(
		StringCellContent content) {
		return '''<span>«content.valueHtmlString»</span>'''
	}

	private static def dispatch String getValueFormat(
		MultiColorCellContent content) {
		return '''<span>«content.multiColorFormat.htmlString»</span>'''
	}

	static def <T> T getCompareContentValueFormat(CompareCellContent content,
		(String, String)=>T getFormatFunction, String value) {
		if (content.oldValue.contains(value) &&
			content.newValue.contains(value)) {
			return getFormatFunction.apply(WARNING_MARK_BLACK, value)
		} else if (content.oldValue.contains(value)) {
			return getFormatFunction.apply(WARNING_MARK_YELLOW, value)
		} else {
			return getFormatFunction.apply(WARNING_MARK_RED, value)
		}
	}

	private static def String getCompareValueFormat(String warning_mark,
		String value) {
		if (Strings.isNullOrEmpty(value)) {
			return ""
		}
		if (value.isErrorText) {
			return warning_mark
		}

		switch (warning_mark) {
			case WARNING_MARK_BLACK: {
				return '''<span>«value.htmlString»</span>'''
			}
			case WARNING_MARK_YELLOW: {
				return '''<span style="background-color:rgb(255,255, 0)"><s>«value.htmlString»</s></span>'''
			}
			case WARNING_MARK_RED: {
				return '''<span style="color:rgb(255, 0, 0)">«value.htmlString»</span>'''
			}
		}
	}

	private static def String getMultiColorFormat(
		MultiColorCellContent content) {
		if (content.value.isEmpty) {
			return ""
		}
		return '''«FOR element : content.value SEPARATOR content.separator»«element.multiColorFormat»«ENDFOR»'''
	}

	private static def String getMultiColorFormat(MultiColorContent content) {
		if (Strings.isNullOrEmpty(content.multiColorValue)) {
			return Strings.isNullOrEmpty(content.stringFormat) ? "" : content.
				stringFormat.htmlString
		}

		val value = '''<span style="background-color:rgb(255,255, 0)">«content
			.getMultiColorValueHtmlString(WARNING_MARK_YELLOW)»</span><span style="color:rgb(255, 0, 0)">«content
			.getMultiColorValueHtmlString(WARNING_MARK_RED)»</span>'''
		return '''<span>«String.format(content.stringFormat, value)»</span>'''
	}

	private static def String getValueHtmlString(StringCellContent content) {
		val contentValue = content.value.iterableToString(content.separator)
		if (contentValue.isErrorText && !ToolboxConfiguration.developmentMode) {
			return WARNING_MARK_BLACK
		} else {
			return contentValue.htmlString
		}
	}

	private static def String getMultiColorValueHtmlString(
		MultiColorContent content, String warningColor) {
		if (content.multiColorValue.isErrorText &&
			!ToolboxConfiguration.developmentMode) {
			return warningColor
		}
		return content.multiColorValue.htmlString
	}

	private static def String getHtmlString(String value) {
		return HtmlEscapers.htmlEscaper.escape(
			value.intersperseWithZeroSpacesSC
		).replaceAll("\n", "<br></br>")
	}

	def static String iterableToString(
		Iterable<String> sequence,
		String separator
	) {
		return '''«FOR element : sequence SEPARATOR separator === null ? "\n" : separator»«element»«ENDFOR»'''
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
