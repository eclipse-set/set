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
 * '<em><b>Control Station Type</b></em>', and utility methods for working with
 * them. <!-- end-user-doc -->
 * 
 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getControlStationType()
 * @model
 * @generated
 */
public enum ControlStationType implements Enumerator {
	/**
	 * The '<em><b>Default Control</b></em>' literal object. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #DEFAULT_CONTROL_VALUE
	 * @generated
	 * @ordered
	 */
	DEFAULT_CONTROL(0, "DefaultControl", "DefaultControl"),

	/**
	 * The '<em><b>Emergency Control</b></em>' literal object. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #EMERGENCY_CONTROL_VALUE
	 * @generated
	 * @ordered
	 */
	EMERGENCY_CONTROL(1, "EmergencyControl", "EmergencyControl"),

	/**
	 * The '<em><b>Emergency Control Dispose</b></em>' literal object. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #EMERGENCY_CONTROL_DISPOSE_VALUE
	 * @generated
	 * @ordered
	 */
	EMERGENCY_CONTROL_DISPOSE(2, "EmergencyControlDispose",
			"EmergencyControlDispose"),

	/**
	 * The '<em><b>Default Control Dispose</b></em>' literal object. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #DEFAULT_CONTROL_DISPOSE_VALUE
	 * @generated
	 * @ordered
	 */
	DEFAULT_CONTROL_DISPOSE(3, "DefaultControlDispose",
			"DefaultControlDispose"),

	/**
	 * The '<em><b>Other</b></em>' literal object. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #OTHER_VALUE
	 * @generated
	 * @ordered
	 */
	OTHER(4, "Other", "Other"),

	/**
	 * The '<em><b>Without Control</b></em>' literal object. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #WITHOUT_CONTROL_VALUE
	 * @generated
	 * @ordered
	 */
	WITHOUT_CONTROL(5, "WithoutControl", "WithoutControl"),

	/**
	 * The '<em><b>Only Remote Control</b></em>' literal object. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #ONLY_REMOTE_CONTROL_VALUE
	 * @generated
	 * @ordered
	 */
	ONLY_REMOTE_CONTROL(6, "OnlyRemoteControl", "OnlyRemoteControl");

	/**
	 * The '<em><b>Default Control</b></em>' literal value. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #DEFAULT_CONTROL
	 * @model name="DefaultControl"
	 * @generated
	 * @ordered
	 */
	public static final int DEFAULT_CONTROL_VALUE = 0;

	/**
	 * The '<em><b>Emergency Control</b></em>' literal value. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #EMERGENCY_CONTROL
	 * @model name="EmergencyControl"
	 * @generated
	 * @ordered
	 */
	public static final int EMERGENCY_CONTROL_VALUE = 1;

	/**
	 * The '<em><b>Emergency Control Dispose</b></em>' literal value. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #EMERGENCY_CONTROL_DISPOSE
	 * @model name="EmergencyControlDispose"
	 * @generated
	 * @ordered
	 */
	public static final int EMERGENCY_CONTROL_DISPOSE_VALUE = 2;

	/**
	 * The '<em><b>Default Control Dispose</b></em>' literal value. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #DEFAULT_CONTROL_DISPOSE
	 * @model name="DefaultControlDispose"
	 * @generated
	 * @ordered
	 */
	public static final int DEFAULT_CONTROL_DISPOSE_VALUE = 3;

	/**
	 * The '<em><b>Other</b></em>' literal value. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #OTHER
	 * @model name="Other"
	 * @generated
	 * @ordered
	 */
	public static final int OTHER_VALUE = 4;

	/**
	 * The '<em><b>Without Control</b></em>' literal value. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #WITHOUT_CONTROL
	 * @model name="WithoutControl"
	 * @generated
	 * @ordered
	 */
	public static final int WITHOUT_CONTROL_VALUE = 5;

	/**
	 * The '<em><b>Only Remote Control</b></em>' literal value. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #ONLY_REMOTE_CONTROL
	 * @model name="OnlyRemoteControl"
	 * @generated
	 * @ordered
	 */
	public static final int ONLY_REMOTE_CONTROL_VALUE = 6;

	/**
	 * An array of all the '<em><b>Control Station Type</b></em>' enumerators.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private static final ControlStationType[] VALUES_ARRAY = new ControlStationType[] {
			DEFAULT_CONTROL, EMERGENCY_CONTROL, EMERGENCY_CONTROL_DISPOSE,
			DEFAULT_CONTROL_DISPOSE, OTHER, WITHOUT_CONTROL,
			ONLY_REMOTE_CONTROL, };

	/**
	 * A public read-only list of all the '<em><b>Control Station Type</b></em>'
	 * enumerators. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static final List<ControlStationType> VALUES = Collections
			.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Control Station Type</b></em>' literal with the
	 * specified literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param literal
	 *            the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static ControlStationType get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ControlStationType result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Control Station Type</b></em>' literal with the
	 * specified name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param name
	 *            the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static ControlStationType getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ControlStationType result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Control Station Type</b></em>' literal with the
	 * specified integer value. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static ControlStationType get(int value) {
		switch (value) {
			case DEFAULT_CONTROL_VALUE:
				return DEFAULT_CONTROL;
			case EMERGENCY_CONTROL_VALUE:
				return EMERGENCY_CONTROL;
			case EMERGENCY_CONTROL_DISPOSE_VALUE:
				return EMERGENCY_CONTROL_DISPOSE;
			case DEFAULT_CONTROL_DISPOSE_VALUE:
				return DEFAULT_CONTROL_DISPOSE;
			case OTHER_VALUE:
				return OTHER;
			case WITHOUT_CONTROL_VALUE:
				return WITHOUT_CONTROL;
			case ONLY_REMOTE_CONTROL_VALUE:
				return ONLY_REMOTE_CONTROL;
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
	private ControlStationType(int value, String name, String literal) {
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

} // ControlStationType
