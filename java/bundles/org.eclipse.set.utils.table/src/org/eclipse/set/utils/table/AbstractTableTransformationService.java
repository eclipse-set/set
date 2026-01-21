/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.table;

import static org.eclipse.nebula.widgets.nattable.sort.SortDirectionEnum.ASC;
import static org.eclipse.set.model.tablemodel.extensions.TableExtensions.getColumns;
import static org.eclipse.set.model.tablemodel.extensions.TableExtensions.setTextAlignment;
import static org.eclipse.set.utils.table.sorting.ComparatorBuilder.CellComparatorType.LEXICOGRAPHICAL;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import org.eclipse.set.model.tablemodel.ColumnDescriptor;
import org.eclipse.set.model.tablemodel.RowGroup;
import org.eclipse.set.model.tablemodel.Table;
import org.eclipse.set.model.tablemodel.TablemodelFactory;
import org.eclipse.set.model.tablemodel.extensions.TableExtensions;
import org.eclipse.set.model.tablemodel.extensions.TableRowExtensions;
import org.eclipse.set.model.tablemodel.format.TextAlignment;
import org.eclipse.set.utils.table.sorting.TableRowGroupComparator;

/**
 * Abstract superclass for table specific model services.
 * 
 * @author rumpf
 * @param <T>
 *            The model to be transformed by this service
 */
public abstract class AbstractTableTransformationService<T>
		implements TableTransformationService<T> {

	protected static final float LINE_HEIGHT = 0.6f;

	protected TableModelTransformator<T> transformator;

	/**
	 * constructor.
	 */
	protected AbstractTableTransformationService() {
		super();
	}

	/**
	 * returns the transformation object which is capable of transforming the
	 * planpro model into the table specific model instance.
	 * 
	 * @return the transformation instance
	 */
	public abstract TableModelTransformator<T> createTransformator();

	/**
	 * fills the header part of the table model.
	 * 
	 * @param builder
	 *            the builder class
	 * @return the root descriptor
	 */
	protected abstract ColumnDescriptor fillHeaderDescriptions(
			final ColumnDescriptorModelBuilder builder);

	@SuppressWarnings("static-method")
	protected void setColumnTextAlignment(final Table table) {
		final List<ColumnDescriptor> columns = getColumns(table);
		// By default set first and last columnd align left
		setTextAlignment(table, 0, TextAlignment.LEFT);
		setTextAlignment(table, columns.size() - 1, TextAlignment.LEFT);
	}

	@Override
	public void format(final Table table) {
		if (table == null) {
			return;
		}
		if (transformator == null) {
			transformator = createTransformator();
		}
		setColumnTextAlignment(table);
	}

	@Override
	public Comparator<RowGroup> getRowGroupComparator() {
		// default comparator
		return TableRowGroupComparator.builder()
				.sort("A", LEXICOGRAPHICAL, ASC) //$NON-NLS-1$
				.build();
	}

	@Override
	public Table transform(final T model) {
		final Table table = TablemodelFactory.eINSTANCE.createTable();
		buildHeading(table);
		transformator = createTransformator();
		transformator.transformTableContent(model, new TMFactory(table));
		TableExtensions.getTableRows(table).forEach(row -> {
			for (int i = 0; i < row.getCells().size(); i++) {
				if (row.getCells().get(i).getContent() == null) {
					TableRowExtensions.set(row, i, ""); //$NON-NLS-1$
				}
			}
		});
		setColumnTextAlignment(table);
		return table;
	}

	@Override
	public Collection<TableError> getTableErrors() {
		if (transformator != null) {
			return transformator.getTableErrors();
		}
		return new ArrayList<>();
	}

	/**
	 * Create table header
	 * 
	 * @param table
	 *            the table
	 * @return the column descriptor
	 */
	public ColumnDescriptor buildHeading(final Table table) {
		final ColumnDescriptorModelBuilder builder = new ColumnDescriptorModelBuilder(
				table);
		return fillHeaderDescriptions(builder);
	}

}