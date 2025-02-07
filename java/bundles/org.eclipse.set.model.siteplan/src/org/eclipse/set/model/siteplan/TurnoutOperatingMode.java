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
 * <!-- begin-user-doc --> A representation of the literals of the enumeration
 * '<em><b>Turnout Operating Mode</b></em>', and utility methods for working
 * with them. <!-- end-user-doc -->
 * 
 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getTurnoutOperatingMode()
 * @model
 * @generated
 */
public enum TurnoutOperatingMode implements Enumerator {
	/**
	 * The '<em><b>Undefined</b></em>' literal object. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #UNDEFINED_VALUE
	 * @generated
	 * @ordered
	 */
	UNDEFINED(0, "Undefined", "Undefined"),

	/**
	 * The '<em><b>Electric Remote</b></em>' literal object. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #ELECTRIC_REMOTE_VALUE
	 * @generated
	 * @ordered
	 */
	ELECTRIC_REMOTE(1, "ElectricRemote", "ElectricRemote"),

	/**
	 * The '<em><b>Electric Local</b></em>' literal object. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #ELECTRIC_LOCAL_VALUE
	 * @generated
	 * @ordered
	 */
	ELECTRIC_LOCAL(2, "ElectricLocal", "ElectricLocal"),

	/**
	 * The '<em><b>Mechanical Remote</b></em>' literal object. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #MECHANICAL_REMOTE_VALUE
	 * @generated
	 * @ordered
	 */
	MECHANICAL_REMOTE(3, "MechanicalRemote", "MechanicalRemote"),

	/**
	 * The '<em><b>Mechanical Local</b></em>' literal object. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #MECHANICAL_LOCAL_VALUE
	 * @generated
	 * @ordered
	 */
	MECHANICAL_LOCAL(4, "MechanicalLocal", "MechanicalLocal"),

	/**
	 * The '<em><b>Non Operational</b></em>' literal object. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #NON_OPERATIONAL_VALUE
	 * @generated
	 * @ordered
	 */
	NON_OPERATIONAL(5, "NonOperational", "NonOperational"),

	/**
	 * The '<em><b>Trailable</b></em>' literal object. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #TRAILABLE_VALUE
	 * @generated
	 * @ordered
	 */
	TRAILABLE(6, "Trailable", "Trailable"),

	/**
	 * The '<em><b>Other</b></em>' literal object. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #OTHER_VALUE
	 * @generated
	 * @ordered
	 */
	OTHER(7, "Other", "Other"),

	/**
	 * The '<em><b>Dead Left</b></em>' literal object. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #DEAD_LEFT_VALUE
	 * @generated
	 * @ordered
	 */
	DEAD_LEFT(8, "DeadLeft", "DeadLeft"),

	/**
	 * The '<em><b>Dead Right</b></em>' literal object. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #DEAD_RIGHT_VALUE
	 * @generated
	 * @ordered
	 */
	DEAD_RIGHT(9, "DeadRight", "DeadRight");

	/**
	 * The '<em><b>Undefined</b></em>' literal value. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #UNDEFINED
	 * @model name="Undefined"
	 * @generated
	 * @ordered
	 */
	public static final int UNDEFINED_VALUE = 0;

	/**
	 * The '<em><b>Electric Remote</b></em>' literal value. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #ELECTRIC_REMOTE
	 * @model name="ElectricRemote"
	 * @generated
	 * @ordered
	 */
	public static final int ELECTRIC_REMOTE_VALUE = 1;

	/**
	 * The '<em><b>Electric Local</b></em>' literal value. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #ELECTRIC_LOCAL
	 * @model name="ElectricLocal"
	 * @generated
	 * @ordered
	 */
	public static final int ELECTRIC_LOCAL_VALUE = 2;

	/**
	 * The '<em><b>Mechanical Remote</b></em>' literal value. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #MECHANICAL_REMOTE
	 * @model name="MechanicalRemote"
	 * @generated
	 * @ordered
	 */
	public static final int MECHANICAL_REMOTE_VALUE = 3;

	/**
	 * The '<em><b>Mechanical Local</b></em>' literal value. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #MECHANICAL_LOCAL
	 * @model name="MechanicalLocal"
	 * @generated
	 * @ordered
	 */
	public static final int MECHANICAL_LOCAL_VALUE = 4;

	/**
	 * The '<em><b>Non Operational</b></em>' literal value. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #NON_OPERATIONAL
	 * @model name="NonOperational"
	 * @generated
	 * @ordered
	 */
	public static final int NON_OPERATIONAL_VALUE = 5;

	/**
	 * The '<em><b>Trailable</b></em>' literal value. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #TRAILABLE
	 * @model name="Trailable"
	 * @generated
	 * @ordered
	 */
	public static final int TRAILABLE_VALUE = 6;

	/**
	 * The '<em><b>Other</b></em>' literal value. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #OTHER
	 * @model name="Other"
	 * @generated
	 * @ordered
	 */
	public static final int OTHER_VALUE = 7;

	/**
	 * The '<em><b>Dead Left</b></em>' literal value. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #DEAD_LEFT
	 * @model name="DeadLeft"
	 * @generated
	 * @ordered
	 */
	public static final int DEAD_LEFT_VALUE = 8;

	/**
	 * The '<em><b>Dead Right</b></em>' literal value. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #DEAD_RIGHT
	 * @model name="DeadRight"
	 * @generated
	 * @ordered
	 */
	public static final int DEAD_RIGHT_VALUE = 9;

	/**
	 * An array of all the '<em><b>Turnout Operating Mode</b></em>' enumerators.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private static final TurnoutOperatingMode[] VALUES_ARRAY = new TurnoutOperatingMode[] {
			UNDEFINED, ELECTRIC_REMOTE, ELECTRIC_LOCAL, MECHANICAL_REMOTE,
			MECHANICAL_LOCAL, NON_OPERATIONAL, TRAILABLE, OTHER, DEAD_LEFT,
			DEAD_RIGHT, };

	/**
	 * A public read-only list of all the '<em><b>Turnout Operating
	 * Mode</b></em>' enumerators. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static final List<TurnoutOperatingMode> VALUES = Collections
			.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Turnout Operating Mode</b></em>' literal with the
	 * specified literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param literal
	 *            the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static TurnoutOperatingMode get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			TurnoutOperatingMode result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Turnout Operating Mode</b></em>' literal with the
	 * specified name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param name
	 *            the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static TurnoutOperatingMode getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			TurnoutOperatingMode result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Turnout Operating Mode</b></em>' literal with the
	 * specified integer value. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static TurnoutOperatingMode get(int value) {
		switch (value) {
			case UNDEFINED_VALUE:
				return UNDEFINED;
			case ELECTRIC_REMOTE_VALUE:
				return ELECTRIC_REMOTE;
			case ELECTRIC_LOCAL_VALUE:
				return ELECTRIC_LOCAL;
			case MECHANICAL_REMOTE_VALUE:
				return MECHANICAL_REMOTE;
			case MECHANICAL_LOCAL_VALUE:
				return MECHANICAL_LOCAL;
			case NON_OPERATIONAL_VALUE:
				return NON_OPERATIONAL;
			case TRAILABLE_VALUE:
				return TRAILABLE;
			case OTHER_VALUE:
				return OTHER;
			case DEAD_LEFT_VALUE:
				return DEAD_LEFT;
			case DEAD_RIGHT_VALUE:
				return DEAD_RIGHT;
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private final int value;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private final String name;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private final String literal;

	/**
	 * Only this class can construct instances. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	private TurnoutOperatingMode(int value, String name, String literal) {
		this.value = value;
		this.name = name;
		this.literal = literal;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public int getValue() {
		return value;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getLiteral() {
		return literal;
	}

	/**
	 * Returns the literal value of the enumerator, which is its string
	 * representation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String toString() {
		return literal;
	}

} // TurnoutOperatingMode
