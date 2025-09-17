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
 * '<em><b>Switch Type</b></em>', and utility methods for working with them.
 * <!-- end-user-doc -->
 * 
 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getSwitchType()
 * @model
 * @generated
 */
public enum SwitchType implements Enumerator {
	/**
	 * The '<em><b>Simple</b></em>' literal object. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #SIMPLE_VALUE
	 * @generated
	 * @ordered
	 */
	SIMPLE(0, "Simple", "Simple"),

	/**
	 * The '<em><b>Cross Switch</b></em>' literal object. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #CROSS_SWITCH_VALUE
	 * @generated
	 * @ordered
	 */
	CROSS_SWITCH(1, "CrossSwitch", "CrossSwitch"),

	/**
	 * The '<em><b>Dopple Cross Switch</b></em>' literal object. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #DOPPLE_CROSS_SWITCH_VALUE
	 * @generated
	 * @ordered
	 */
	DOPPLE_CROSS_SWITCH(2, "DoppleCrossSwitch", "DoppleCrossSwitch"),

	/**
	 * The '<em><b>Simple Cross</b></em>' literal object. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #SIMPLE_CROSS_VALUE
	 * @generated
	 * @ordered
	 */
	SIMPLE_CROSS(3, "SimpleCross", "SimpleCross"),

	/**
	 * The '<em><b>Flat Cross</b></em>' literal object. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #FLAT_CROSS_VALUE
	 * @generated
	 * @ordered
	 */
	FLAT_CROSS(4, "FlatCross", "FlatCross"),
	/**
	 * The '<em><b>Other</b></em>' literal object. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #OTHER_VALUE
	 * @generated
	 * @ordered
	 */
	OTHER(5, "Other", "Other");

	/**
	 * The '<em><b>Simple</b></em>' literal value. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #SIMPLE
	 * @model name="Simple"
	 * @generated
	 * @ordered
	 */
	public static final int SIMPLE_VALUE = 0;

	/**
	 * The '<em><b>Cross Switch</b></em>' literal value. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #CROSS_SWITCH
	 * @model name="CrossSwitch"
	 * @generated
	 * @ordered
	 */
	public static final int CROSS_SWITCH_VALUE = 1;

	/**
	 * The '<em><b>Dopple Cross Switch</b></em>' literal value. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #DOPPLE_CROSS_SWITCH
	 * @model name="DoppleCrossSwitch"
	 * @generated
	 * @ordered
	 */
	public static final int DOPPLE_CROSS_SWITCH_VALUE = 2;

	/**
	 * The '<em><b>Simple Cross</b></em>' literal value. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #SIMPLE_CROSS
	 * @model name="SimpleCross"
	 * @generated
	 * @ordered
	 */
	public static final int SIMPLE_CROSS_VALUE = 3;

	/**
	 * The '<em><b>Flat Cross</b></em>' literal value. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #FLAT_CROSS
	 * @model name="FlatCross"
	 * @generated
	 * @ordered
	 */
	public static final int FLAT_CROSS_VALUE = 4;

	/**
	 * The '<em><b>Other</b></em>' literal value. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #OTHER
	 * @model name="Other"
	 * @generated
	 * @ordered
	 */
	public static final int OTHER_VALUE = 5;

	/**
	 * An array of all the '<em><b>Switch Type</b></em>' enumerators. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private static final SwitchType[] VALUES_ARRAY = new SwitchType[] { SIMPLE,
			CROSS_SWITCH, DOPPLE_CROSS_SWITCH, SIMPLE_CROSS, FLAT_CROSS,
			OTHER, };

	/**
	 * A public read-only list of all the '<em><b>Switch Type</b></em>'
	 * enumerators. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static final List<SwitchType> VALUES = Collections
			.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Switch Type</b></em>' literal with the specified
	 * literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param literal
	 *            the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static SwitchType get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			SwitchType result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Switch Type</b></em>' literal with the specified
	 * name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param name
	 *            the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static SwitchType getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			SwitchType result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Switch Type</b></em>' literal with the specified
	 * integer value. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static SwitchType get(int value) {
		switch (value) {
			case SIMPLE_VALUE:
				return SIMPLE;
			case CROSS_SWITCH_VALUE:
				return CROSS_SWITCH;
			case DOPPLE_CROSS_SWITCH_VALUE:
				return DOPPLE_CROSS_SWITCH;
			case SIMPLE_CROSS_VALUE:
				return SIMPLE_CROSS;
			case FLAT_CROSS_VALUE:
				return FLAT_CROSS;
			case OTHER_VALUE:
				return OTHER;
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
	private SwitchType(int value, String name, String literal) {
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

} // SwitchType
