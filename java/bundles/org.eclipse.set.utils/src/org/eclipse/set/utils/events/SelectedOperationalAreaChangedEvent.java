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
package org.eclipse.set.utils.events;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.eclipse.set.basis.constants.TableType;
import org.eclipse.set.basis.constants.ToolboxConstants;
import org.eclipse.set.model.planpro.Ansteuerung_Element.Stell_Bereich;
import org.eclipse.set.model.planpro.Balisentechnik_ETCS.RBC;
import org.eclipse.set.model.planpro.Basisobjekte.Ur_Objekt;

/**
 * Changed event of selection control area widges
 * 
 * @author Truong
 * @param <T>
 *            type of operation area
 */
public class SelectedOperationalAreaChangedEvent<T extends Ur_Objekt>
		implements ToolboxEvent {

	/**
	 * Helper class for define control area
	 * 
	 * @param areaName
	 *            the name of area
	 * @param areaId
	 *            the guid {@link Stell_Bereich}
	 */
	public record OperationalAreaComboValue(String areaName, String areaId) {
	}

	private static final String TOPIC = "toolboxevents/controlarea/selection"; //$NON-NLS-1$
	Set<OperationalAreaComboValue> areas;
	private final TableType tableType;
	private final boolean isPlanningAreaIgnored;
	private Class<T> areaClass;

	/**
	 * @return the table type
	 */
	public TableType getTableType() {
		return tableType;
	}

	/**
	 * @param areaClass
	 *            the class of the operation area
	 * @param tableType
	 *            current tableType
	 * @param isPlanningAreaIgnored
	 *            should displayed all objects in project or only the objects in
	 *            planning area
	 * 
	 */
	public SelectedOperationalAreaChangedEvent(final Class<T> areaClass,
			final TableType tableType, final boolean isPlanningAreaIgnored) {
		this(areaClass, Collections.emptySet(), tableType,
				isPlanningAreaIgnored);
	}

	/**
	 * Constructor
	 * 
	 * @param areaClass
	 *            the class of the operation area
	 * 
	 * @param area
	 *            the {@link OperationalAreaComboValue}
	 * @param tableType
	 *            current tableType
	 * @param isPlanningAreaIgnored
	 *            should displayed all objects in project or only the objects in
	 *            planning area
	 */
	public SelectedOperationalAreaChangedEvent(final Class<T> areaClass,
			final OperationalAreaComboValue area, final TableType tableType,
			final boolean isPlanningAreaIgnored) {
		this(areaClass, Set.of(area), tableType, isPlanningAreaIgnored);
	}

	/**
	 * Constructor
	 * 
	 * @param areaClass
	 *            the class of the operation area
	 * 
	 * @param areas
	 *            the list of area
	 * @param tableType
	 *            current tableType
	 * @param isPlanningAreaIgnored
	 *            should displayed all objects in project or only the objects in
	 *            planning area
	 */
	public SelectedOperationalAreaChangedEvent(final Class<T> areaClass,
			final Set<OperationalAreaComboValue> areas,
			final TableType tableType, final boolean isPlanningAreaIgnored) {
		this.areaClass = areaClass;
		this.areas = areas;
		this.tableType = tableType;
		this.isPlanningAreaIgnored = isPlanningAreaIgnored;
	}

	/**
	 * @return the control areas
	 */
	public Set<OperationalAreaComboValue> getOperationalAreas() {
		return areas;
	}

	/**
	 * @return should displayed all obejects in project or only the objects in
	 *         planning area
	 */
	public boolean isPlanningAreaIgnored() {
		return isPlanningAreaIgnored;
	}

	@Override
	public String getTopic() {
		return TOPIC;
	}

	/**
	 * @param elementId
	 *            the table part id
	 * @return true, if the table relevant to the event
	 */
	public boolean istRelevantTable(final String elementId) {
		if (areaClass == Stell_Bereich.class) {
			return List
					.of(ToolboxConstants.ESTW_TABLE_PART_ID_PREFIX,
							ToolboxConstants.ESTW_SUPPLEMENT_PART_ID_PREFIX)
					.stream()
					.anyMatch(elementId::startsWith);
		}

		if (areaClass == RBC.class) {
			return elementId
					.startsWith(ToolboxConstants.ETCS_TABLE_PART_ID_PREFIX);
		}

		return false;
	}

}
