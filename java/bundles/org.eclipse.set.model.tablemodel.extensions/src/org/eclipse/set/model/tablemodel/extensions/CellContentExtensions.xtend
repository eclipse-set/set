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
import java.util.function.BiFunction
import java.util.function.Function
import org.eclipse.set.model.planpro.Basisobjekte.Bearbeitungsvermerk
import org.eclipse.set.model.tablemodel.CellContent
import org.eclipse.set.model.tablemodel.CompareCellContent
import org.eclipse.set.model.tablemodel.CompareFootnoteContainer
import org.eclipse.set.model.tablemodel.CompareTableCellContent
import org.eclipse.set.model.tablemodel.CompareTableFootnoteContainer
import org.eclipse.set.model.tablemodel.FootnoteContainer
import org.eclipse.set.model.tablemodel.MultiColorCellContent
import org.eclipse.set.model.tablemodel.MultiColorContent
import org.eclipse.set.model.tablemodel.SimpleFootnoteContainer
import org.eclipse.set.model.tablemodel.StringCellContent
import org.eclipse.set.model.tablemodel.TableCell
import org.eclipse.set.utils.ToolboxConfiguration

import static org.eclipse.set.model.tablemodel.extensions.Utils.*

import static extension org.eclipse.set.model.tablemodel.extensions.ColumnDescriptorExtensions.*
import static extension org.eclipse.set.model.tablemodel.extensions.FootnoteContainerExtensions.*
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
	public static val String HOURGLASS_ICON = "⏳"
	static val String FOOTNOTE_SEPARATOR = ", "
	static val String ERROR_PREFIX = "Error:"
	static val String YELLOW_COLOR_RGB = "rgb(255,255, 0)"
	static val String RED_COLOR_RGB = "rgb(255, 0, 0)"

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
		return '''<p style="text-align:«content.textAlign»">«
		»«content.valueFormat»</p>'''
	}

	static def dispatch String getRichTextValue(CompareCellContent content) {
		val result = formatCompareContent(
			content.oldValue,
			content.newValue,
			[WARNING_MARK_YELLOW],
			[WARNING_MARK_BLACK],
			[WARNING_MARK_RED],
			[text, mark|getCompareValueFormat(mark, text)]
		).filter[!nullOrEmpty]

		return '''<p style="text-align:«content.textAlign»">«
		»«result.iterableToString(content.separator === null || content.separator.equals("\r\n")
			? "<br></br>" 
			: content.separator
		)»</p>'''
	}

	static def dispatch String getRichTextValue(MultiColorCellContent content) {
		return '''<p style="text-align:«content.textAlign»">«content.multiColorFormat»</p>'''
	}

	static def dispatch String getRichTextValue(
		CompareTableCellContent content) {
		if (content.mainPlanCellContent.plainStringValue ==
			content.comparePlanCellContent.plainStringValue) {
			return '''<s>«content.mainPlanCellContent.richTextValue»</s>'''
		}
		return content.mainPlanCellContent.richTextValue
	}

	/**
	 * Returns a formatted string representation intended for rendering as
	 * rich text. This method should only be called in the context of rendering
	 * the content, e.g. in NatTable content providers.
	 * 
	 * @param content this cell content
	 * 
	 * @return a formatted string representation of the cell content
	 */
	static def dispatch String getRichTextValueWithFootnotes(
		CellContent content, FootnoteContainer fc) {
		return '''Content «content.class.simpleName» not supported.'''
	}

	static def dispatch String getRichTextValueWithFootnotes(Void content,
		Void fc) {
		return ""
	}

	static def dispatch String getRichTextValueWithFootnotes(
		StringCellContent content, Void fc) {
		return '''<p style="text-align:«content.textAlign»">«content.valueFormat»</p>'''
	}

	static def dispatch String getRichTextValueWithFootnotes(
		StringCellContent content, SimpleFootnoteContainer fc) {
		val footnoteText = fc.footnotes.map [
			'''*«getFootnoteNumber(content, it)»'''
		].iterableToString(FOOTNOTE_SEPARATOR)

		if (footnoteText != "")
			return '''<p style="text-align:«content.textAlign»">«content.valueFormat» «footnoteText»</p>'''
		return '''<p style="text-align:«content.textAlign»">«content.valueFormat»</p>'''
	}

	static def dispatch String getRichTextValueWithFootnotes(
		CellContent content, CompareFootnoteContainer fc) {

		var result = formatCompareContent(
			content,
			[WARNING_MARK_YELLOW],
			[WARNING_MARK_BLACK],
			[WARNING_MARK_RED],
			[text, mark|getCompareValueFormat(mark, text)]
		)

		val footnotes = formatCompareContent(
			fc,
			[WARNING_MARK_YELLOW],
			[WARNING_MARK_BLACK],
			[WARNING_MARK_RED],
			[ text, mark |
				getCompareValueFormat(
					mark, '''*«getFootnoteNumber(content, text)»''')
			]
		)

		result = result + #[footnotes.iterableToString(FOOTNOTE_SEPARATOR)]

		return '''<p style="text-align:«content.textAlign»">«
		»«result.filter[it.length > 0].iterableToString(content.separator === null || content.separator.equals("\r\n")
			? "<br></br>" 
			: content.separator
		)»</p>'''
	}

	static def dispatch String getRichTextValueWithFootnotes(
		CompareTableCellContent content, CompareTableFootnoteContainer fc) {

		val mainTableFootnotes = fc.mainPlanFootnoteContainer
		val compareTableFootnotes = fc.comparePlanFootnoteContainer
		val result = content.mainPlanCellContent.
			getRichTextValueWithFootnotes(fc.mainPlanFootnoteContainer)
		if (mainTableFootnotes.isSameFootnotesComment(compareTableFootnotes)) {
			return '''<s>«result»</s>'''
		}
		return result
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

	static def dispatch String getPlainStringValue(
		CompareTableCellContent content) {
		if (content.mainPlanCellContent === null) {
			return content.mainPlanCellContent.plainStringValue
		}

		return '''«content.mainPlanCellContent.plainStringValue»/«content.comparePlanCellContent.plainStringValue»'''
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
	static def dispatch TableCell getTableCell(CellContent content) {
		if (content.eContainer instanceof CompareTableCellContent) {
			return content.eContainer.eContainer as TableCell
		}
		return content.eContainer as TableCell
	}

	static def dispatch TableCell getTableCell(MultiColorContent content) {
		return content.eContainer.eContainer as TableCell
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

	static def <S extends Comparable<? super S>, R, T, U> Iterable<U> formatCompareContent(
		Iterable<R> oldContent,
		Iterable<R> newContent,
		Function<R, T> oldFormatter,
		Function<R, T> commonFormatter,
		Function<R, T> newFormatter,
		BiFunction<R, T, U> postFormatter,
		Function<R, S> sorter
	) {
		return formatCompareContent(
			oldContent.filterNull.toSet.toList.sortBy(sorter),
			newContent.filterNull.toSet.toList.sortBy(sorter),
			oldFormatter,
			commonFormatter,
			newFormatter,
			postFormatter
		)
	}

	static def <R, T, U> Iterable<U> formatCompareContent(
		Iterable<R> oldContent,
		Iterable<R> newContent,
		Function<R, T> oldFormatter,
		Function<R, T> commonFormatter,
		Function<R, T> newFormatter,
		BiFunction<R, T, U> postFormatter
	) {
		// new and unchanged content is sorted together 
		val result = newContent.filterNull.toSet.toList.map [
			if (oldContent.contains(it))
				return postFormatter.apply(it, commonFormatter.apply(it))
			else
				return postFormatter.apply(it, newFormatter.apply(it))
		]

		// old content is appended after that
		return result + oldContent.filterNull.toSet.toList.filter [
			!newContent.contains(it)
		].map [
			postFormatter.apply(it, oldFormatter.apply(it))
		]
	}

	static def <T, U> Iterable<U> formatCompareContent(
		Object content,
		Function<String, T> oldFormatter,
		Function<String, T> commonFormatter,
		Function<String, T> newFormatter,
		BiFunction<String, T, U> postFormatter
	) {
		switch content {
			StringCellContent:
				content.value.filterNull.toSet.toList.map [
					postFormatter.apply(it, commonFormatter.apply(it))
				]
			CompareCellContent:
				formatCompareContent(content.oldValue, content.newValue,
					oldFormatter, commonFormatter, newFormatter, postFormatter)
			CompareTableCellContent:
				formatCompareContent(content.mainPlanCellContent, oldFormatter,
					commonFormatter, newFormatter, postFormatter)
		}
	}

	static def <T, U> Iterable<U> formatCompareContent(
		CompareFootnoteContainer content,
		Function<Bearbeitungsvermerk, T> oldFormatter,
		Function<Bearbeitungsvermerk, T> commonFormatter,
		Function<Bearbeitungsvermerk, T> newFormatter,
		BiFunction<Bearbeitungsvermerk, T, U> postFormatter
	) {
		formatCompareContent(
			(content.oldFootnotes + content.unchangedFootnotes),
			(content.newFootnotes + content.unchangedFootnotes),
			oldFormatter,
			commonFormatter,
			newFormatter,
			postFormatter,
			[it?.bearbeitungsvermerkAllg?.kommentar?.wert]
		)
	}

	private static def String getCompareValueFormat(String warning_mark,
		String value) {
		if (Strings.isNullOrEmpty(value)) {
			return ""
		}
		if (value.isErrorText) {
			return warning_mark
		}

		if (value.contains(HOURGLASS_ICON)) {
			return '''<span>«value»</span>'''
		}

		switch (warning_mark) {
			case WARNING_MARK_BLACK: {
				return '''<span>«value.htmlString»</span>'''
			}
			case WARNING_MARK_YELLOW: {
				return '''<span style="background-color:«YELLOW_COLOR_RGB»"><s>«value.htmlString»</s></span>'''
			}
			case WARNING_MARK_RED: {
				return '''<span style="color:«RED_COLOR_RGB»">«value.htmlString»</span>'''
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

		if (content.isDisableMultiColor) {
			return '''<span>«String.format(content.stringFormat, content.multiColorValue)»</span>'''
		}

		val value = '''<span style="background-color:«YELLOW_COLOR_RGB»">«content
			.getMultiColorValueHtmlString(WARNING_MARK_YELLOW)»</span><span style="color:«RED_COLOR_RGB»">«content
			.getMultiColorValueHtmlString(WARNING_MARK_RED)»</span>'''
		return '''<span>«String.format(content.stringFormat, value)»</span>'''
	}

	private static def String getValueHtmlString(StringCellContent content) {
		val contentValue = content.value.iterableToString(content.separator)
		if (contentValue.isErrorText && !ToolboxConfiguration.developmentMode) {
			return WARNING_MARK_BLACK
		} else if (contentValue.equals(HOURGLASS_ICON)) {
			return contentValue
		}
		return contentValue.htmlString
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
