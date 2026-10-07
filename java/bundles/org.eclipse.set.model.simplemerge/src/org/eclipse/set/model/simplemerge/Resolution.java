/**
 * Copyright (c) 2018 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.simplemerge;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc --> A representation of the literals of the enumeration
 * '<em><b>Resolution</b></em>', and utility methods for working with them. <!--
 * end-user-doc -->
 * 
 * @see org.eclipse.set.model.simplemerge.SimplemergePackage#getResolution()
 * @model
 * @generated
 */
public enum Resolution implements Enumerator {
	/**
	 * The '<em><b>PRIMARY UNRESOLVED</b></em>' literal object. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #PRIMARY_UNRESOLVED_VALUE
	 * @generated
	 * @ordered
	 */
	PRIMARY_UNRESOLVED(0, "PRIMARY_UNRESOLVED", "PRIMARY_UNRESOLVED"),

	/**
	 * The '<em><b>PRIMARY AUTO</b></em>' literal object. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #PRIMARY_AUTO_VALUE
	 * @generated
	 * @ordered
	 */
	PRIMARY_AUTO(1, "PRIMARY_AUTO", "PRIMARY_AUTO"),

	/**
	 * The '<em><b>PRIMARY MANUAL</b></em>' literal object. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #PRIMARY_MANUAL_VALUE
	 * @generated
	 * @ordered
	 */
	PRIMARY_MANUAL(2, "PRIMARY_MANUAL", "PRIMARY_MANUAL"),

	/**
	 * The '<em><b>SECONDARY AUTO</b></em>' literal object. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #SECONDARY_AUTO_VALUE
	 * @generated
	 * @ordered
	 */
	SECONDARY_AUTO(3, "SECONDARY_AUTO", "SECONDARY_AUTO"),

	/**
	 * The '<em><b>SECONDARY MANUAL</b></em>' literal object. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #SECONDARY_MANUAL_VALUE
	 * @generated
	 * @ordered
	 */
	SECONDARY_MANUAL(4, "SECONDARY_MANUAL", "SECONDARY_MANUAL");

	/**
	 * The '<em><b>PRIMARY UNRESOLVED</b></em>' literal value. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>PRIMARY UNRESOLVED</b></em>' literal object
	 * isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @see #PRIMARY_UNRESOLVED
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int PRIMARY_UNRESOLVED_VALUE = 0;

	/**
	 * The '<em><b>PRIMARY AUTO</b></em>' literal value. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>PRIMARY AUTO</b></em>' literal object isn't
	 * clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @see #PRIMARY_AUTO
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int PRIMARY_AUTO_VALUE = 1;

	/**
	 * The '<em><b>PRIMARY MANUAL</b></em>' literal value. <!-- begin-user-doc
	 * -->
	 * <p>
	 * If the meaning of '<em><b>PRIMARY MANUAL</b></em>' literal object isn't
	 * clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @see #PRIMARY_MANUAL
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int PRIMARY_MANUAL_VALUE = 2;

	/**
	 * The '<em><b>SECONDARY AUTO</b></em>' literal value. <!-- begin-user-doc
	 * -->
	 * <p>
	 * If the meaning of '<em><b>SECONDARY AUTO</b></em>' literal object isn't
	 * clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @see #SECONDARY_AUTO
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int SECONDARY_AUTO_VALUE = 3;

	/**
	 * The '<em><b>SECONDARY MANUAL</b></em>' literal value. <!-- begin-user-doc
	 * -->
	 * <p>
	 * If the meaning of '<em><b>SECONDARY MANUAL</b></em>' literal object isn't
	 * clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @see #SECONDARY_MANUAL
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int SECONDARY_MANUAL_VALUE = 4;

	/**
	 * An array of all the '<em><b>Resolution</b></em>' enumerators. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private static final Resolution[] VALUES_ARRAY = new Resolution[] {
			PRIMARY_UNRESOLVED, PRIMARY_AUTO, PRIMARY_MANUAL, SECONDARY_AUTO,
			SECONDARY_MANUAL, };

	/**
	 * A public read-only list of all the '<em><b>Resolution</b></em>'
	 * enumerators. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static final List<Resolution> VALUES = Collections
			.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Resolution</b></em>' literal with the specified
	 * literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param literal
	 *            the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static Resolution get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			Resolution result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Resolution</b></em>' literal with the specified name.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param name
	 *            the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static Resolution getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			Resolution result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Resolution</b></em>' literal with the specified
	 * integer value. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static Resolution get(int value) {
		switch (value) {
			case PRIMARY_UNRESOLVED_VALUE:
				return PRIMARY_UNRESOLVED;
			case PRIMARY_AUTO_VALUE:
				return PRIMARY_AUTO;
			case PRIMARY_MANUAL_VALUE:
				return PRIMARY_MANUAL;
			case SECONDARY_AUTO_VALUE:
				return SECONDARY_AUTO;
			case SECONDARY_MANUAL_VALUE:
				return SECONDARY_MANUAL;
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
	private Resolution(int value, String name, String literal) {
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

} // Resolution
