/**
 * Copyright (c) 2015 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.table

import de.scheidtbachmann.planpro.model.model1902.Regelzeichnung.Regelzeichnung
import java.util.Comparator
import org.eclipse.set.model.tablemodel.ColumnDescriptor
import org.eclipse.set.model.tablemodel.Table
import org.eclipse.set.model.tablemodel.TableRow
import org.eclipse.set.model.tablemodel.format.TextAlignment
import org.eclipse.set.ppmodel.extensions.utils.Case
import org.eclipse.set.utils.ToolboxConfiguration
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import static extension com.google.common.base.Throwables.*
import static extension org.eclipse.set.model.tablemodel.extensions.TableExtensions.*
import static extension org.eclipse.set.model.tablemodel.extensions.TableRowExtensions.*
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

	static val Logger logger = LoggerFactory.getLogger(
		typeof(AbstractTableModelTransformator))

	val static String ITERABLE_FILLING_SEPARATOR = String.format("%n")

	val static boolean DEVELOPMENT_MODE = ToolboxConfiguration.developmentMode

	val static String BLANK = ""

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
			for (c : cases) {
				val condition = c.condition.apply(object)
				if (condition !== null && condition.booleanValue) {
					fill(row, column, object, c.filling)
					return
				}
			}
			row.set(column, BLANK)
		} catch (Exception e) {
			handleFillingException(e, row, column)
		}
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
		row.fillIterable(column, object, sequence, comparator, [it])
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
				val list = sequenceIfTrue.apply(object).sortWith(comparator).
					map(elementFilling).filterNull
				row.set(column, list.getIterableFilling(separator))
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

	private def static String getIterableFilling(
		Iterable<String> sequence,
		String separator
	) {
		return '''«FOR element : sequence SEPARATOR separator»«element»«ENDFOR»'''
	}

	def dispatch private handleFillingException(
		Exception e,
		TableRow row,
		ColumnDescriptor column
	) {
		logger.
			error('''«e.class.simpleName» in column "«column.debugString»" for leading object «row.group.leadingObject?.identitaet?.wert». «e.message»«System.lineSeparator»«e.stackTraceAsString»''')
		row.set(
			column, '''Error: «e.class.simpleName»: "«e.message»" for leading object «row.group.leadingObject?.identitaet?.wert»''')
	}

	def dispatch private handleFillingException(
		UnsupportedOperationException e,
		TableRow row,
		ColumnDescriptor column
	) {
		logger.
			error('''«e.class.simpleName» in column "«column.debugString»". «e.message»«System.lineSeparator»«e.stackTraceAsString»''')
		row.set(
			column, '''Error: «e.message» for leading object «row.group.leadingObject?.identitaet?.wert»''')
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

	override void formatTableContent(Table table) {
		table.setTextAlignment(0, TextAlignment.LEFT);
	}
}
