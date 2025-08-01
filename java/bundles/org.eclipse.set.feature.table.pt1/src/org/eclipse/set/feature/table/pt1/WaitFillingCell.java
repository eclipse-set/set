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
package org.eclipse.set.feature.table.pt1;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import org.eclipse.set.model.planpro.Basisobjekte.Ur_Objekt;
import org.eclipse.set.model.tablemodel.ColumnDescriptor;
import org.eclipse.set.model.tablemodel.TableRow;

/**
 * The table cell, which will be filled later
 * 
 * @author truong
 * @param <T>
 *            the Ur_Objekt
 * 
 */
public class WaitFillingCell<T extends Ur_Objekt> {
	private final ColumnDescriptor column;
	private final TableRow row;
	private final Function<T, List<String>> sequenceFunction;
	private final Supplier<Boolean> shouldFill;
	private final T object;

	/**
	 * @param column
	 *            the {@link ColumnDescriptor}
	 * @param row
	 *            the {@link TableRow}
	 * @param object
	 *            the Ur_Objekt
	 * @param sequenceFunction
	 *            the fill function
	 * @param shouldFill
	 *            should fill test
	 */
	public WaitFillingCell(final ColumnDescriptor column, final TableRow row,
			final T object, final Function<T, List<String>> sequenceFunction,
			final Supplier<Boolean> shouldFill) {
		this.column = column;
		this.row = row;
		this.sequenceFunction = sequenceFunction;
		this.object = object;
		this.shouldFill = shouldFill;

	}

	/**
	 * @return the Ur_Objekt
	 */
	public T getObject() {
		return object;
	}

	/**
	 * @return the {@link ColumnDescriptor}
	 */
	public ColumnDescriptor getColumn() {
		return column;
	}

	/**
	 * @return the {@link TableRow}
	 */
	public TableRow getRow() {
		return row;
	}

	/**
	 * the Ur_Objekt
	 * 
	 * @return the fill value
	 */
	public List<String> getFillValue() {
		if (object == null) {
			return Collections.emptyList();
		}
		return sequenceFunction.apply(object);
	}

	/**
	 * @return true, when the cell should fill
	 */
	public boolean getShouldFill() {
		return shouldFill.get().booleanValue();
	}
}
