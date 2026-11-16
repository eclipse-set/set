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
import org.eclipse.set.basis.table.OperationalAreaEnum;
import org.eclipse.set.basis.table.OperationalAreaEnum.OperationalAreaObject;

/**
 * Changed event of selection control area widges
 * 
 * @author Truong
 */
public class SelectedOperationalAreaChangedEvent implements ToolboxEvent {

	private static final String TOPIC = "toolboxevents/controlarea/selection"; //$NON-NLS-1$
	Set<OperationalAreaObject> areas;
	private final TableType tableType;
	private final boolean isPlanningAreaIgnored;
	private final OperationalAreaEnum operationalAreaType;

	/**
	 * @return the table type
	 */
	public TableType getTableType() {
		return tableType;
	}

	/**
	 * Default constructor
	 */
	public SelectedOperationalAreaChangedEvent() {
		this(null, null, false);
	}

	/**
	 * @param operationalAreaEnum
	 *            the operational area enum
	 * @param tableType
	 *            current tableType
	 * @param isPlanningAreaIgnored
	 *            should displayed all objects in project or only the objects in
	 *            planning area
	 * 
	 */
	public SelectedOperationalAreaChangedEvent(
			final OperationalAreaEnum operationalAreaEnum,
			final TableType tableType, final boolean isPlanningAreaIgnored) {
		this(operationalAreaEnum, Collections.emptySet(), tableType,
				isPlanningAreaIgnored);
	}

	/**
	 * Constructor
	 * 
	 * @param operationalAreaEnum
	 *            the operational area enum
	 * 
	 * 
	 * @param area
	 *            the {@link OperationalAreaObject}
	 * @param tableType
	 *            current tableType
	 * @param isPlanningAreaIgnored
	 *            should displayed all objects in project or only the objects in
	 *            planning area
	 */
	public SelectedOperationalAreaChangedEvent(
			final OperationalAreaEnum operationalAreaEnum,
			final OperationalAreaObject area, final TableType tableType,
			final boolean isPlanningAreaIgnored) {
		this(operationalAreaEnum, Set.of(area), tableType,
				isPlanningAreaIgnored);
	}

	/**
	 * Constructor
	 * 
	 * @param operationalAreaEnum
	 *            the operational area enum
	 * 
	 * 
	 * @param areas
	 *            the list of area
	 * @param tableType
	 *            current tableType
	 * @param isPlanningAreaIgnored
	 *            should displayed all objects in project or only the objects in
	 *            planning area
	 */
	public SelectedOperationalAreaChangedEvent(
			final OperationalAreaEnum operationalAreaEnum,
			final Set<OperationalAreaObject> areas, final TableType tableType,
			final boolean isPlanningAreaIgnored) {
		this.operationalAreaType = operationalAreaEnum;
		this.areas = areas;
		this.tableType = tableType;
		this.isPlanningAreaIgnored = isPlanningAreaIgnored;
	}

	/**
	 * @return the control areas
	 */
	public Set<OperationalAreaObject> getOperationalAreas() {
		return areas;
	}

	/**
	 * @return should displayed all objects in project or only the objects in
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
	 * @return the class of operational area
	 */
	public OperationalAreaEnum getOperationalAreaType() {
		return operationalAreaType;
	}

	/**
	 * @param elementId
	 *            the table part id
	 * @return true, if the table relevant to the event
	 */
	public boolean istRelevantTable(final String elementId) {
		return switch (operationalAreaType) {
			case Interlocking -> List
					.of(ToolboxConstants.ESTW_TABLE_PART_ID_PREFIX,
							ToolboxConstants.ESTW_SUPPLEMENT_PART_ID_PREFIX)
					.stream()
					.anyMatch(elementId::startsWith);
			case RBC -> elementId
					.startsWith(ToolboxConstants.ETCS_TABLE_PART_ID_PREFIX);
			default -> false;
		};
	}

}
