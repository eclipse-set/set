/**
 * Copyright (c) 2026 DB InfraGO AG and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 */
package org.eclipse.set.basis.table;

import org.eclipse.set.model.planpro.Ansteuerung_Element.Stell_Bereich;
import org.eclipse.set.model.planpro.Balisentechnik_ETCS.RBC;
import org.eclipse.set.model.planpro.Basisobjekte.Ur_Objekt;

/**
 * The enum for operational area
 * 
 * @author truong
 * 
 */
public enum OperationalAreaEnum {

	/**
	 * RBC area
	 */
	RBC(RBC.class),
	/**
	 * Interlocking - Stellbereich area
	 */
	Interlocking(Stell_Bereich.class);

	Class<? extends Ur_Objekt> areaClass;

	private OperationalAreaEnum(final Class<? extends Ur_Objekt> clazz) {
		areaClass = clazz;
	}

	/**
	 * @return the class of operational area
	 */
	public Class<? extends Ur_Objekt> getOperationalAreaClass() {
		return areaClass;
	}

	@SuppressWarnings("nls")
	@Override
	public String toString() {
		return switch (this) {
			case RBC -> "RBC";
			case Interlocking -> "Stellbereich";
		};
	}

	/**
	 * @param clazz
	 *            the area class
	 * @return the area enum
	 */
	public static OperationalAreaEnum getOperationalArea(final Class<?> clazz) {
		if (clazz == RBC.class) {
			return RBC;
		}

		if (clazz == Stell_Bereich.class) {
			return Interlocking;
		}

		return null;
	}

	/**
	 * Helper class for define control area
	 * 
	 * @param operationalAreaEnum
	 *            the operation area enum
	 * 
	 * @param areaName
	 *            the name of area
	 * @param areaId
	 *            the guid of operational area object
	 */
	public static record OperationalAreaObject(
			OperationalAreaEnum operationalAreaEnum, String areaName,
			String areaId) {
	}
}
