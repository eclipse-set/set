/**
 * Copyright (c) 2015 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.table

import java.util.Collection
import java.util.Collections
import java.util.Comparator
import java.util.List
import org.eclipse.set.basis.constants.ToolboxConstants
import org.eclipse.set.model.planpro.Regelzeichnung.Regelzeichnung
import org.eclipse.set.model.tablemodel.ColumnDescriptor
import org.eclipse.set.model.tablemodel.MultiColorContent
import org.eclipse.set.model.tablemodel.TableRow
import org.eclipse.set.ppmodel.extensions.utils.Case
import org.eclipse.set.utils.ToolboxConfiguration
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import static extension com.google.common.base.Throwables.*
import static extension org.eclipse.set.model.tablemodel.extensions.TableRowExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.BasisAttributExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.MultiContainer_AttributeGroupExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.utils.Debug.*

/**
 * Provides common functions for table transformations.
 * 
 * @author Schaefer
 */
abstract class AbstractTableModelTransformator<T> implements TableModelTransformator<T> {

	/**
	 * constant for non-filled content.
	 */
	static final String NOT_FILLED = "n.b.";

	/**
	 * constant prefix for cells that show errors 
	 */
	protected static final String ERROR_PREFIX = "Error: ";

	static val Logger logger = LoggerFactory.getLogger(
		typeof(AbstractTableModelTransformator))

	protected val static String ITERABLE_FILLING_SEPARATOR = String.format("%n")

	val static boolean DEVELOPMENT_MODE = ToolboxConfiguration.developmentMode

	val static String BLANK = ""

	protected val List<TableError> tableErrors = Collections.
		synchronizedList(newArrayList)

	/**
	 * Errors that occurred during transformation
	 */
	override Collection<TableError> getTableErrors() {
		return tableErrors
	}

	/**
	 * Place holder for pending implementations.
	 */
	def String toDo() {
		if (DEVELOPMENT_MODE) {
			throw new UnsupportedOperationException("to do")
		}
		return NOT_FILLED;
	}

	/**
	 * Place holder for implementation in future plan pro version.
	 */
	def String futureVersion() {
		if (DEVELOPMENT_MODE) {
			throw new UnsupportedOperationException("future Version")
		}
		return NOT_FILLED;
	}

	/**
	 * Place holder for missing test data.
	 */
	def String noTestData() {
		if (DEVELOPMENT_MODE) {
			throw new UnsupportedOperationException("no test data")
		}
		return NOT_FILLED;
	}

	/**
	 * Place holder for pending specifications.
	 */
	def String toBeSpecified() {
		if (DEVELOPMENT_MODE) {
			throw new UnsupportedOperationException("to be specified")
		}
		return NOT_FILLED;
	}

	def <T> void fill(
		TableRow row,
		ColumnDescriptor column,
		T object,
		(T)=>String filling
	) {
		try {
			if (object === null) {
				row.set(column, BLANK)
				return
			}
			var text = filling.apply(object)
			if (text !== null) {
				row.set(column, text)
			} else {
				row.set(column, BLANK)
			}
		} catch (Exception e) {
			handleFillingException(e, row, column)
		}
	}

	def <T> void fillNumeric(
		TableRow row,
		ColumnDescriptor column,
		T object,
		(T)=>Number filling
	) {
		fill(row, column, object, [filling.apply(object)?.toString])
	}

	def <T> void fillMultiColor(
		TableRow row,
		ColumnDescriptor column,
		T object,
		(T)=>MultiColorContent fillMultiColorContent
	) {
		val multicolorContent = fillMultiColorContent.apply(object)
		try {
			if (multicolorContent !== null &&
				multicolorContent.stringFormat !== null) {
				row.set(column, #[multicolorContent], "")
			} else {
				row.set(column, BLANK)
			}
		} catch (Exception e) {
			handleFillingException(e, row, column)
		}
	}

	/**
	 * Fill a row conditional and handle exceptions.
	 * 
	 * @param row the row
	 * @param column the column to be filled
	 * @param object the object to be transformed
	 * @param condition the condition for filling
	 * @param filling filling function
	 */
	def <T> void fillConditional(
		TableRow row,
		ColumnDescriptor column,
		T object,
		(T)=>Boolean condition,
		(T)=>String filling
	) {
		try {
			if (condition.apply(object).booleanValue) {
				fill(row, column, object, filling)
			} else {
				row.set(column, BLANK)
			}
		} catch (Exception e) {
			handleFillingException(e, row, column)
		}
	}

	/**
	 * Fill a row conditional and handle exceptions.
	 * 
	 * @param row the row
	 * @param object the object to be transformed
	 * @param condition the condition for filling
	 * @param fillingIfTrue the filling
	 * @param fillingIfFalse the alternative filling
	 */
	def <T> void fillConditional(
		TableRow row,
		ColumnDescriptor column,
		T object,
		(T)=>Boolean condition,
		(T)=>String fillingIfTrue,
		(T)=>String fillingIfFalse
	) {
		try {
			if (condition.apply(object).booleanValue) {
				fill(row, column, object, fillingIfTrue)
			} else {
				fill(row, column, object, fillingIfFalse)
			}
		} catch (Exception e) {
			handleFillingException(e, row, column)
		}
	}

	def <T> void fillSwitch(
		TableRow row,
		ColumnDescriptor column,
		T object,
		Case<T>... cases
	) {

		try {
			val switchCase = getSwitchCase(object, cases)
			val content = switchCase?.filling?.apply(object)
			if (content === null || content.empty) {
				row.set(column, BLANK)
			} else if (content.size === 1) {
				fill(row, column, object, [content.get(0)])
			} else {
				fillIterable(row, column, object, [content],
					switchCase.comparator, [it], switchCase.seperator === null
						? ITERABLE_FILLING_SEPARATOR
						: switchCase.seperator)
			}
		} catch (Exception e) {
			handleFillingException(e, row, column)
		}
	}

	/**
	 *  Fills using Case-lists. For each Case list a result is passed
	 * 	to fillingCombiner. If a case list does not contain any matching cases,
	 *  null is passed to fillingCombiner
	 * 
	 * @param row The row to fill
	 * @param column The column to fill
	 * @param object The object to fill for
	 * @param fillingCombiner the aggregation function to combine case lists
	 * @param a list of case lists
	 */
	def <T> void fillSwitchGrouped(
		TableRow row,
		ColumnDescriptor column,
		T object,
		(Iterable<Case<T>>)=>Iterable<String> fillingCombiner,
		Iterable<Case<T>>... caseGroups
	) {
		try {
			val switchResult = caseGroups.map[getSwitchCase(object, it)]
			val content = fillingCombiner.apply(switchResult)
			if (content === null || content.empty) {
				row.set(column, BLANK)
			} else if (content.size === 1) {
				fill(row, column, object, [content.get(0)])
			} else {
				fillIterable(row, column, object, [content], null, [it],
					ITERABLE_FILLING_SEPARATOR)
			}
		} catch (Exception e) {
			handleFillingException(e, row, column)
		}
	}

	def <T> Case<T> getSwitchCase(
		T object,
		Case<T>... cases
	) {
		for (c : cases) {
			val condition = c.condition.apply(object)
			if (condition !== null && condition.booleanValue) {
				return c
			}
		}
		return null

	}

	/**
	 * Fill a row with a sequence of string values and handle exceptions.
	 * 
	 * @param row the row
	 * @param column the column
	 * @param object the object to be transformed
	 * @param sequence the sequence
	 * @param comparator The comparator for sorting the sequence. May be
	 * <code>null</code> to indicate that the natural ordering of the elements
	 * should be used.
	 */
	def <S, T> void fillIterable(
		TableRow row,
		ColumnDescriptor column,
		S object,
		(S)=>Iterable<String> sequence,
		Comparator<String> comparator
	) {
		row.fillIterable(column, object, sequence,
			comparator ?: ToolboxConstants.LST_OBJECT_NAME_COMPARATOR, [it])
	}

	def <S, T> void fillMultiColorIterable(
		TableRow row,
		ColumnDescriptor column,
		S object,
		(S)=>List<MultiColorContent> sequence
	) {
		row.fillMultiColorIterable(column, object, sequence,
			ITERABLE_FILLING_SEPARATOR)
	}

	def <S> void fillMultiColorIterable(
		TableRow row,
		ColumnDescriptor column,
		S object,
		(S)=>List<MultiColorContent> sequence,
		String separator
	) {
		try {
			val list = sequence.apply(object)
			if (!list.empty) {
				row.set(column, list, separator)
			} else {
				fill(row, column, object, [])
			}
		} catch (Exception e) {
			handleFillingException(e, row, column)
		}
	}

	/**
	 * Fill a row with a sequence of values and handle exceptions.
	 * 
	 * @param row the row
	 * @param column the column
	 * @param object the object to be transformed
	 * @param sequence the sequence
	 * @param comparator The comparator for sorting the sequence. May be
	 * <code>null</code> to indicate that the natural ordering of the elements
	 * should be used.
	 * @param elementFilling the filling for an element of the sequence
	 */
	def <S, T> void fillIterable(
		TableRow row,
		ColumnDescriptor column,
		S object,
		(S)=>Iterable<T> sequence,
		Comparator<T> comparator,
		(T)=>String elementFilling
	) {
		row.fillIterable(column, object, sequence, comparator, elementFilling,
			ITERABLE_FILLING_SEPARATOR)
	}

	/**
	 * Fill a row with a sequence of values and handle exceptions.
	 * 
	 * @param row the row
	 * @param column the column
	 * @param object the object to be transformed
	 * @param sequence the sequence
	 * @param comparator The comparator for sorting the sequence. May be
	 * <code>null</code> to indicate that the natural ordering of the elements
	 * should be used.
	 * @param elementFilling the filling for an element of the sequence
	 * @param separator the separator
	 */
	def <S, T> void fillIterable(
		TableRow row,
		ColumnDescriptor column,
		S object,
		(S)=>Iterable<T> sequence,
		Comparator<T> comparator,
		(T)=>String elementFilling,
		String separator
	) {
		row.fillIterableWithSeparatorConditional(column, object, [true],
			sequence, comparator, elementFilling, [], separator)
	}

	/**
	 * Fill a row with a sequence of string values depending on a condition and handle exceptions.
	 * 
	 * @param row the row
	 * @param column the column
	 * @param object the object to be transformed
	 * @param condition the condition for filling
	 * @param sequenceIfTrue the filling sequence, in the case of a true condition
	 * @param comparator The comparator for sorting the sequence. May be
	 * @param separator the separator
	 */
	def <S, T> void fillIterableWithConditional(
		TableRow row,
		ColumnDescriptor column,
		S object,
		(S)=>Boolean condition,
		(S)=>Iterable<String> sequenceIfTrue,
		Comparator<String> comparator,
		String separtor
	) {
		row.fillIterableWithSeparatorConditional(column, object, condition,
			sequenceIfTrue, comparator, [], separtor)
	}

	/**
	 * Fill a row with a sequence of string values depending on a condition and handle exceptions.
	 * 
	 * @param row the row
	 * @param column the column
	 * @param object the object to be transformed
	 * @param condition the condition for filling
	 * @param sequenceIfTrue the filling sequence, in the case of a true condition
	 * @param comparator The comparator for sorting the sequence. May be
	 * <code>null</code> to indicate that the natural ordering of the elements
	 * should be used.
	 * @param fillingIfFalse the alternative filling
	 * @param separator the separator
	 */
	def <S, T> void fillIterableWithSeparatorConditional(
		TableRow row,
		ColumnDescriptor column,
		S object,
		(S)=>Boolean condition,
		(S)=>Iterable<String> sequenceIfTrue,
		Comparator<String> comparator,
		(S)=>String fillingIfFalse,
		String separator
	) {
		row.fillIterableWithSeparatorConditional(column, object, condition,
			sequenceIfTrue, comparator, [it], fillingIfFalse, separator)
	}

	/**
	 * Fill a row with a sequence of values depending on a condition and handle exceptions.
	 * 
	 * @param row the row
	 * @param column the column
	 * @param object the object to be transformed
	 * @param condition the condition for filling
	 * @param sequenceIfTrue the filling sequence, in the case of a true condition
	 * @param comparator The comparator for sorting the sequence. May be
	 * <code>null</code> to indicate that the natural ordering of the elements
	 * should be used.
	 * @param elementFilling the filling for an element of the sequence
	 * @param fillingIfFalse the alternative filling
	 * @param separator the separator
	 */
	def <S, T> void fillIterableWithSeparatorConditional(
		TableRow row,
		ColumnDescriptor column,
		S object,
		(S)=>Boolean condition,
		(S)=>Iterable<T> sequenceIfTrue,
		Comparator<T> comparator,
		(T)=>String elementFilling,
		(S)=>String fillingIfFalse,
		String separator
	) {
		try {
			if (condition.apply(object).booleanValue) {
				val list = sequenceIfTrue.apply(object).filterNull.sortWith(
					comparator).map(elementFilling).filterNull
				row.set(column, list, separator)
			} else {
				fill(row, column, object, fillingIfFalse)
			}
		} catch (Exception e) {
			handleFillingException(e, row, column)
		}
	}

	def static String fillRegelzeichnung(Regelzeichnung regelzeichnung) {
		val bild = regelzeichnung?.regelzeichnungAllg?.bild
		var rzNummer = regelzeichnung?.regelzeichnungAllg?.RZNummer?.wert
		if (rzNummer === null) {
			rzNummer = "null"
		}
		return '''«rzNummer»«IF bild !== null» («bild.wert»)«ENDIF»'''
	}

	/**
	 * Fills a sequence of iterables ordered by the natural ordering of the elements
	 * 
	 * @param sequence the sequence
	 */
	def static String getIterableFilling(
		Iterable<String> sequence
	) {
		return sequence.getIterableFilling(
			null,
			ITERABLE_FILLING_SEPARATOR
		)
	}

	/**
	 * @param sequence the sequence
	 * @param comparator The comparator for sorting the sequence. May be
	 * <code>null</code> to indicate that the natural ordering of the elements
	 * should be used.
	 */
	def static String getIterableFilling(
		Iterable<String> sequence,
		Comparator<String> comparator
	) {
		return sequence.getIterableFilling(
			comparator,
			ITERABLE_FILLING_SEPARATOR
		)
	}

	/**
	 * @param sequence the sequence
	 * @param comparator The comparator for sorting the sequence. May be
	 * <code>null</code> to indicate that the natural ordering of the elements
	 * should be used.
	 * @param separator the separator
	 */
	def static String getIterableFilling(
		Iterable<String> sequence,
		Comparator<String> comparator,
		String separator
	) {
		return '''«FOR element : sequence.sortWith(comparator) SEPARATOR separator»«element»«ENDFOR»'''
	}

	/**
	 * @params row the table row for which the leading object shall be delivered
	 * @returns the leading object identifier of the given row which is either the value of the first cell
	 *          or the GUID of the leading object entity if the first cell is empty or shows an error
	 */
	def String getLeadingObjectIdentifier(TableRow row, String guid) {
		var firstCellValue = row.getPlainStringValue(0)
		if (firstCellValue === null ||
			firstCellValue.startsWith(ERROR_PREFIX)) {
			return guid
		}
		return firstCellValue
	}

	def void handleFillingException(
		Exception e,
		TableRow row,
		ColumnDescriptor column
	) {
		var leadingObject = row.group.leadingObject
		var errorIdentiefer = getLeadingObjectIdentifier(row,
			leadingObject?.identitaet?.wert)
		var errorMsg = e.createErrorMsg(row)
		val error = new TableError(leadingObject, errorIdentiefer, "", errorMsg,
			row, leadingObject.container.containerType)
		tableErrors.add(error)
		row.set(column, '''«ERROR_PREFIX»«errorMsg»''')
		logger.
			error('''«e.class.simpleName» in column "«column.debugString»" for leading object "«leadingObject»" («leadingObject?.identitaet?.wert»). «e.message»«System.lineSeparator»«e.stackTraceAsString»''')
	}

	def String createErrorMsg(
		Exception e,
		TableRow row
	) {
		var guid = row.group.leadingObject?.identitaet?.wert
		var leadingObject = getLeadingObjectIdentifier(row, guid)
		return e.createErrorMsg(leadingObject)
	}

	def String createErrorMsg(
		Exception e,
		String leadingObjectGuid
	) {
		var errorMsg = '''«e.class.simpleName»: "«e.message»" for leading object "«leadingObjectGuid»"'''
		return errorMsg
	}

	/**
	 * Evaluates the given function with the given value for use in sorting.
	 * Exceptions are catched and move the entry to the end of the list and
	 * <code>null</code> values are interpreted as empty strings.
	 * 
	 * @param value the value
	 * @param valueToString the value to string function
	 * 
	 * @return sort evaluation of <b>valueToString</b> with <b>value</b>
	 */
	static def <T> String sortEvaluation(T value, (T)=>String valueToString) {
		var result = "ZZZZZ"
		try {
			result = valueToString.apply(value)
			if (result === null) {
				result = ""
			}
		} catch (Exception e) {
			// ignore exception
		}
		return result
	}

	/**
	 * Fill a column with a blank.
	 * 
	 * @param row the row
	 * @param int the column
	 */
	def <T> void fillBlank(
		TableRow row,
		int column
	) {
		row.set(column, BLANK)
	}
}
