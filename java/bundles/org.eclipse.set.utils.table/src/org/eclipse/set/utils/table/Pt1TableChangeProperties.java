/**
 * Copyright (c) 2024 DB InfraGO AG and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 */
package org.eclipse.set.utils.table;

import java.util.List;

import org.eclipse.set.basis.constants.ContainerType;
import org.eclipse.set.model.planpro.PlanPro.PlanPro_Schnittstelle;
import org.eclipse.set.model.tablemodel.ColumnDescriptor;
import org.eclipse.set.model.tablemodel.TableRow;
import org.eclipse.set.ppmodel.extensions.MultiContainer_AttributeGroupExtensions;
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup;

/**
 * Helper class for change table data at runtime
 */
public class Pt1TableChangeProperties {
	private final ColumnDescriptor changeDataColumn;
	private final List<String> newValues;
	private final TableRow row;

	private final MultiContainer_AttributeGroup container;
	private final String seperator;

	/**
	 * @param container
	 *            the container of the row
	 * @param row
	 *            the row
	 * @param changeDataColumn
	 *            change value of this column
	 * @param newValues
	 *            the new values
	 * @param seperator
	 *            the seperator character
	 */
	public Pt1TableChangeProperties(
			final MultiContainer_AttributeGroup container, final TableRow row,
			final ColumnDescriptor changeDataColumn,
			final List<String> newValues, final String seperator) {
		this.container = container;
		this.row = row;
		this.changeDataColumn = changeDataColumn;
		this.newValues = newValues;
		this.seperator = seperator;
	}

	/**
	 * @return Container type of the row
	 */
	public ContainerType getContainerType() {
		return MultiContainer_AttributeGroupExtensions
				.getContainerType(container);
	}

	/**
	 * @return the column descriptor
	 */
	public ColumnDescriptor getChangeDataColumn() {
		return changeDataColumn;
	}

	/**
	 * @return the {@link PlanPro_Schnittstelle}
	 */
	public PlanPro_Schnittstelle getPlanProSchnittstelle() {
		return MultiContainer_AttributeGroupExtensions
				.getPlanProSchnittstelle(container);
	}

	/**
	 * @return the values
	 */
	public List<String> getNewValues() {
		return newValues;
	}

	/**
	 * @return the rows
	 */
	public TableRow getRow() {
		return row;
	}

	/**
	 * @return the seperator character
	 */
	public String getSeparator() {
		return seperator;
	}
}
