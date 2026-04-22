/** 
 * Copyright (c) 2023 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table;

import java.util.List;
import java.util.Set;

import org.eclipse.set.model.planpro.Ansteuerung_Element.Stell_Bereich;
import org.eclipse.set.model.planpro.Balisentechnik_ETCS.RBC;
import org.eclipse.set.model.planpro.Basisobjekte.Ur_Objekt;
import org.eclipse.set.model.planpro.PlanPro.LST_Objekte_Planungsbereich_AttributeGroup;
import org.eclipse.set.model.tablemodel.Table;
import org.eclipse.set.ppmodel.extensions.StellBereichExtensions;
import org.eclipse.set.ppmodel.extensions.UrObjectExtensions;
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup;
import org.eclipse.set.ppmodel.extensions.utils.TableNameInfo;
import org.eclipse.set.utils.table.AbstractTableTransformationService;
import org.eclipse.set.utils.table.TableInfo.Pt1TableCategory;

/**
 * Common base for tables in this bundle
 */
public abstract class PlanPro2TableTransformationService extends
		AbstractTableTransformationService<MultiContainer_AttributeGroup> {

	/**
	 * @return the table name info
	 */
	public abstract TableNameInfo getTableNameInfo();

	/**
	 * @return position of fixed columns
	 */
	public abstract Set<Integer> getFixedColumnsPos();

	/**
	 * @return whether filtering shall be enabled for the table or not
	 */
	@SuppressWarnings("static-method")
	public boolean enableFiltering() {
		// by default we disable filtering
		return false;
	}

	protected abstract Pt1TableCategory getTableCategory();

	/**
	 * Check if the object belong to the current selected
	 * {@link Stell_Bereich}/{@link RBC} area
	 * 
	 * @param obj
	 *            the table object
	 * @param areas
	 *            the selected control area
	 * @return true, if the object is relevant
	 */
	public boolean isObjectBelongToRendereArea(final Ur_Objekt obj,
			final List<Stell_Bereich> areas) {
		return switch (getTableCategory()) {
			case ESTW, ESTW_SUPPLEMENT -> {
				yield StellBereichExtensions.isInControlArea(areas, obj);
			}
			default -> true;
		};
	}

	/**
	 * @param obj
	 *            the table object
	 * @return true if the object belong to
	 *         {@link LST_Objekte_Planungsbereich_AttributeGroup#getIDLSTObjektPlanungsbereich()}
	 */
	@SuppressWarnings("static-method")
	public boolean isObjectBelongToRendereArea(final Ur_Objekt obj) {
		return UrObjectExtensions.isPlanningObject(obj);
	}

	/**
	 * Addition row to filtered table after filter the original table thought
	 * rendered area
	 * 
	 * @param originalTable
	 *            the origin table, which contains all value
	 * @param filteredTable
	 *            the filtered table
	 */
	public void addAdditionRow(final Table originalTable,
			final Table filteredTable) {
		// do nothing by default
	}
}
