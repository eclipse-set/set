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
import java.util.Set;

import org.eclipse.set.basis.constants.TableType;
import org.eclipse.set.model.planpro.Ansteuerung_Element.Stell_Bereich;

/**
 * Changed event of selection control area widges
 * 
 * @author Truong
 */
public class SelectedControlAreaChangedEvent implements ToolboxEvent {

	/**
	 * Helper class for define control area
	 * 
	 * @param areaName
	 *            the name of area
	 * @param areaId
	 *            the guid {@link Stell_Bereich}
	 */
	public record ControlAreaValue(String areaName, String areaId) {
	}

	private static final String TOPIC = "toolboxevents/controlarea/selection"; //$NON-NLS-1$
	Set<ControlAreaValue> areas;
	private final TableType tableType;
	private final boolean isDisplayedAllObjects;

	/**
	 * @return the table type
	 */
	public TableType getTableType() {
		return tableType;
	}

	/**
	 * Default constructor
	 */
	public SelectedControlAreaChangedEvent() {
		this.areas = null;
		this.tableType = null;
		this.isDisplayedAllObjects = false;

	}

	/**
	 * @param tableType
	 *            current tableType
	 * @param isDisplayedAllObjects
	 *            should displayed all objects in project or only the objects in
	 *            planning area
	 * 
	 */
	public SelectedControlAreaChangedEvent(final TableType tableType,
			final boolean isDisplayedAllObjects) {
		this(Collections.emptySet(), tableType, isDisplayedAllObjects);
	}

	/**
	 * Constructor
	 * 
	 * @param area
	 *            the {@link ControlAreaValue}
	 * @param tableType
	 *            current tableType
	 * @param isDisplayedAllObjects
	 *            should displayed all objects in project or only the objects in
	 *            planning area
	 */
	public SelectedControlAreaChangedEvent(final ControlAreaValue area,
			final TableType tableType, final boolean isDisplayedAllObjects) {
		this(Set.of(area), tableType, isDisplayedAllObjects);
	}

	/**
	 * Constructor
	 * 
	 * @param areas
	 *            the list of area
	 * @param tableType
	 *            current tableType
	 * @param isDisplayedAllObjects
	 *            should displayed all objects in project or only the objects in
	 *            planning area
	 */
	public SelectedControlAreaChangedEvent(final Set<ControlAreaValue> areas,
			final TableType tableType, final boolean isDisplayedAllObjects) {
		this.areas = areas;
		this.tableType = tableType;
		this.isDisplayedAllObjects = isDisplayedAllObjects;
	}

	/**
	 * @return the control areas
	 */
	public Set<ControlAreaValue> getControlAreas() {
		return areas;
	}

	/**
	 * @return should displayed all obejects in project or only the objects in
	 *         planning area
	 */
	public boolean isDisplayedAllObjects() {
		return isDisplayedAllObjects;
	}

	@Override
	public String getTopic() {
		return TOPIC;
	}

}
