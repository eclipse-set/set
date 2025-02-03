/**
 * Copyright (c) 2021 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.siteplan;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Track Close Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getTrackCloseType()
 * @model
 * @generated
 */
public enum TrackCloseType implements Enumerator {
	/**
	 * The '<em><b>Friction Buffer Stop</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #FRICTION_BUFFER_STOP_VALUE
	 * @generated
	 * @ordered
	 */
	FRICTION_BUFFER_STOP(0, "FrictionBufferStop", "Bremsprellbock"),

	/**
	 * The '<em><b>Fixed Buffer Stop</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #FIXED_BUFFER_STOP_VALUE
	 * @generated
	 * @ordered
	 */
	FIXED_BUFFER_STOP(1, "FixedBufferStop", "Festprellbock"),

	/**
	 * The '<em><b>Head Ramp</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #HEAD_RAMP_VALUE
	 * @generated
	 * @ordered
	 */
	HEAD_RAMP(2, "HeadRamp", "Kopframpe"),

	/**
	 * The '<em><b>Threshold Cross</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #THRESHOLD_CROSS_VALUE
	 * @generated
	 * @ordered
	 */
	THRESHOLD_CROSS(3, "ThresholdCross", "Schwellenkreuz"),

	/**
	 * The '<em><b>Turn Table</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #TURN_TABLE_VALUE
	 * @generated
	 * @ordered
	 */
	TURN_TABLE(4, "TurnTable", "Drehscheibe"),

	/**
	 * The '<em><b>Sliding Stage</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SLIDING_STAGE_VALUE
	 * @generated
	 * @ordered
	 */
	SLIDING_STAGE(5, "SlidingStage", "Schiebebuehne"),

	/**
	 * The '<em><b>Ferry Dock</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #FERRY_DOCK_VALUE
	 * @generated
	 * @ordered
	 */
	FERRY_DOCK(6, "FerryDock", "Faehranleger"),

	/**
	 * The '<em><b>Infrastructure Border</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #INFRASTRUCTURE_BORDER_VALUE
	 * @generated
	 * @ordered
	 */
	INFRASTRUCTURE_BORDER(7, "InfrastructureBorder", "Infrastrukturgrenze"),

	/**
	 * The '<em><b>Other</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #OTHER_VALUE
	 * @generated
	 * @ordered
	 */
	OTHER(8, "Other", "sonstige");

	/**
	 * The '<em><b>Friction Buffer Stop</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #FRICTION_BUFFER_STOP
	 * @model name="FrictionBufferStop" literal="Bremsprellbock"
	 * @generated
	 * @ordered
	 */
	public static final int FRICTION_BUFFER_STOP_VALUE = 0;

	/**
	 * The '<em><b>Fixed Buffer Stop</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #FIXED_BUFFER_STOP
	 * @model name="FixedBufferStop" literal="Festprellbock"
	 * @generated
	 * @ordered
	 */
	public static final int FIXED_BUFFER_STOP_VALUE = 1;

	/**
	 * The '<em><b>Head Ramp</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #HEAD_RAMP
	 * @model name="HeadRamp" literal="Kopframpe"
	 * @generated
	 * @ordered
	 */
	public static final int HEAD_RAMP_VALUE = 2;

	/**
	 * The '<em><b>Threshold Cross</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #THRESHOLD_CROSS
	 * @model name="ThresholdCross" literal="Schwellenkreuz"
	 * @generated
	 * @ordered
	 */
	public static final int THRESHOLD_CROSS_VALUE = 3;

	/**
	 * The '<em><b>Turn Table</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #TURN_TABLE
	 * @model name="TurnTable" literal="Drehscheibe"
	 * @generated
	 * @ordered
	 */
	public static final int TURN_TABLE_VALUE = 4;

	/**
	 * The '<em><b>Sliding Stage</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SLIDING_STAGE
	 * @model name="SlidingStage" literal="Schiebebuehne"
	 * @generated
	 * @ordered
	 */
	public static final int SLIDING_STAGE_VALUE = 5;

	/**
	 * The '<em><b>Ferry Dock</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #FERRY_DOCK
	 * @model name="FerryDock" literal="Faehranleger"
	 * @generated
	 * @ordered
	 */
	public static final int FERRY_DOCK_VALUE = 6;

	/**
	 * The '<em><b>Infrastructure Border</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #INFRASTRUCTURE_BORDER
	 * @model name="InfrastructureBorder" literal="Infrastrukturgrenze"
	 * @generated
	 * @ordered
	 */
	public static final int INFRASTRUCTURE_BORDER_VALUE = 7;

	/**
	 * The '<em><b>Other</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #OTHER
	 * @model name="Other" literal="sonstige"
	 * @generated
	 * @ordered
	 */
	public static final int OTHER_VALUE = 8;

	/**
	 * An array of all the '<em><b>Track Close Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final TrackCloseType[] VALUES_ARRAY =
		new TrackCloseType[] {
			FRICTION_BUFFER_STOP,
			FIXED_BUFFER_STOP,
			HEAD_RAMP,
			THRESHOLD_CROSS,
			TURN_TABLE,
			SLIDING_STAGE,
			FERRY_DOCK,
			INFRASTRUCTURE_BORDER,
			OTHER,
		};

	/**
	 * A public read-only list of all the '<em><b>Track Close Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<TrackCloseType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Track Close Type</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param literal the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static TrackCloseType get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			TrackCloseType result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Track Close Type</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static TrackCloseType getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			TrackCloseType result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Track Close Type</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static TrackCloseType get(int value) {
		switch (value) {
			case FRICTION_BUFFER_STOP_VALUE: return FRICTION_BUFFER_STOP;
			case FIXED_BUFFER_STOP_VALUE: return FIXED_BUFFER_STOP;
			case HEAD_RAMP_VALUE: return HEAD_RAMP;
			case THRESHOLD_CROSS_VALUE: return THRESHOLD_CROSS;
			case TURN_TABLE_VALUE: return TURN_TABLE;
			case SLIDING_STAGE_VALUE: return SLIDING_STAGE;
			case FERRY_DOCK_VALUE: return FERRY_DOCK;
			case INFRASTRUCTURE_BORDER_VALUE: return INFRASTRUCTURE_BORDER;
			case OTHER_VALUE: return OTHER;
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final int value;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String name;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String literal;

	/**
	 * Only this class can construct instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private TrackCloseType(int value, String name, String literal) {
		this.value = value;
		this.name = name;
		this.literal = literal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getValue() {
	  return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getName() {
	  return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getLiteral() {
	  return literal;
	}

	/**
	 * Returns the literal value of the enumerator, which is its string representation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		return literal;
	}
	
} //TrackCloseType
