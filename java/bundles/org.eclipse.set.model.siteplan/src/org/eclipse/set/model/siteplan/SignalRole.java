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
 * '<em><b>Signal Role</b></em>', and utility methods for working with them.
 * <!-- end-user-doc -->
 * 
 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getSignalRole()
 * @model
 * @generated
 */
public enum SignalRole implements Enumerator {
	/**
	 * The '<em><b>Multi Section</b></em>' literal object. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #MULTI_SECTION_VALUE
	 * @generated
	 * @ordered
	 */
	MULTI_SECTION(0, "MultiSection", "MultiSection"),

	/**
	 * The '<em><b>Main</b></em>' literal object. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #MAIN_VALUE
	 * @generated
	 * @ordered
	 */
	MAIN(1, "Main", "Main"),

	/**
	 * The '<em><b>Pre</b></em>' literal object. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #PRE_VALUE
	 * @generated
	 * @ordered
	 */
	PRE(2, "Pre", "Pre"),

	/**
	 * The '<em><b>None</b></em>' literal object. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #NONE_VALUE
	 * @generated
	 * @ordered
	 */
	NONE(3, "None", "None"),

	/**
	 * The '<em><b>Train Cover</b></em>' literal object. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #TRAIN_COVER_VALUE
	 * @generated
	 * @ordered
	 */
	TRAIN_COVER(4, "TrainCover", "TrainCover"),

	/**
	 * The '<em><b>Lock</b></em>' literal object. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #LOCK_VALUE
	 * @generated
	 * @ordered
	 */
	LOCK(5, "Lock", "Lock"),
	/**
	 * The '<em><b>Sonstige</b></em>' literal object. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #SONSTIGE_VALUE
	 * @generated
	 * @ordered
	 */
	SONSTIGE(6, "Sonstige", "Sonstige");

	/**
	 * The '<em><b>Multi Section</b></em>' literal value. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #MULTI_SECTION
	 * @model name="MultiSection"
	 * @generated
	 * @ordered
	 */
	public static final int MULTI_SECTION_VALUE = 0;

	/**
	 * The '<em><b>Main</b></em>' literal value. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #MAIN
	 * @model name="Main"
	 * @generated
	 * @ordered
	 */
	public static final int MAIN_VALUE = 1;

	/**
	 * The '<em><b>Pre</b></em>' literal value. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #PRE
	 * @model name="Pre"
	 * @generated
	 * @ordered
	 */
	public static final int PRE_VALUE = 2;

	/**
	 * The '<em><b>None</b></em>' literal value. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #NONE
	 * @model name="None"
	 * @generated
	 * @ordered
	 */
	public static final int NONE_VALUE = 3;

	/**
	 * The '<em><b>Train Cover</b></em>' literal value. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #TRAIN_COVER
	 * @model name="TrainCover"
	 * @generated
	 * @ordered
	 */
	public static final int TRAIN_COVER_VALUE = 4;

	/**
	 * The '<em><b>Lock</b></em>' literal value. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #LOCK
	 * @model name="Lock"
	 * @generated
	 * @ordered
	 */
	public static final int LOCK_VALUE = 5;

	/**
	 * The '<em><b>Sonstige</b></em>' literal value. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #SONSTIGE
	 * @model name="Sonstige"
	 * @generated
	 * @ordered
	 */
	public static final int SONSTIGE_VALUE = 6;

	/**
	 * An array of all the '<em><b>Signal Role</b></em>' enumerators. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private static final SignalRole[] VALUES_ARRAY = new SignalRole[] {
			MULTI_SECTION, MAIN, PRE, NONE, TRAIN_COVER, LOCK, SONSTIGE, };

	/**
	 * A public read-only list of all the '<em><b>Signal Role</b></em>'
	 * enumerators. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static final List<SignalRole> VALUES = Collections
			.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Signal Role</b></em>' literal with the specified
	 * literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param literal
	 *            the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static SignalRole get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			SignalRole result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Signal Role</b></em>' literal with the specified
	 * name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param name
	 *            the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static SignalRole getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			SignalRole result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Signal Role</b></em>' literal with the specified
	 * integer value. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static SignalRole get(int value) {
		switch (value) {
			case MULTI_SECTION_VALUE:
				return MULTI_SECTION;
			case MAIN_VALUE:
				return MAIN;
			case PRE_VALUE:
				return PRE;
			case NONE_VALUE:
				return NONE;
			case TRAIN_COVER_VALUE:
				return TRAIN_COVER;
			case LOCK_VALUE:
				return LOCK;
			case SONSTIGE_VALUE:
				return SONSTIGE;
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
	private SignalRole(int value, String name, String literal) {
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

} // SignalRole
