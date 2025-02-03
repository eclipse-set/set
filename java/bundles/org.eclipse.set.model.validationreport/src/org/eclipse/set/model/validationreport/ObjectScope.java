/**
 * Copyright (c) 2021 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.validationreport;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc --> A representation of the literals of the enumeration
 * '<em><b>Object Scope</b></em>', and utility methods for working with them.
 * <!-- end-user-doc -->
 * 
 * @see org.eclipse.set.model.validationreport.ValidationreportPackage#getObjectScope()
 * @model
 * @generated
 */
public enum ObjectScope implements Enumerator {
	/**
	 * The '<em><b>Layout</b></em>' literal object. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #LAYOUT_VALUE
	 * @generated
	 * @ordered
	 */
	LAYOUT(4, "Layout", "Layout"),
	/**
	 * The '<em><b>Content</b></em>' literal object. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #CONTENT_VALUE
	 * @generated
	 * @ordered
	 */
	CONTENT(3, "Content", "Fachdaten"),
	/**
	 * The '<em><b>Betrachtung</b></em>' literal object. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #BETRACHTUNG_VALUE
	 * @generated
	 * @ordered
	 */
	BETRACHTUNG(2, "Betrachtung", "Betrachtung"),
	/**
	 * The '<em><b>Plan</b></em>' literal object. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #PLAN_VALUE
	 * @generated
	 * @ordered
	 */
	PLAN(1, "Plan", "Planung"),

	/**
	 * The '<em><b>Unknown</b></em>' literal object. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #UNKNOWN_VALUE
	 * @generated
	 * @ordered
	 */
	UNKNOWN(0, "Unknown", "");

	/**
	 * The '<em><b>Layout</b></em>' literal value. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #LAYOUT
	 * @model name="Layout"
	 * @generated
	 * @ordered
	 */
	public static final int LAYOUT_VALUE = 4;

	/**
	 * The '<em><b>Content</b></em>' literal value. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #CONTENT
	 * @model name="Content" literal="Fachdaten"
	 * @generated
	 * @ordered
	 */
	public static final int CONTENT_VALUE = 3;

	/**
	 * The '<em><b>Betrachtung</b></em>' literal value. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #BETRACHTUNG
	 * @model name="Betrachtung"
	 * @generated
	 * @ordered
	 */
	public static final int BETRACHTUNG_VALUE = 2;

	/**
	 * The '<em><b>Plan</b></em>' literal value. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #PLAN
	 * @model name="Plan" literal="Planung"
	 * @generated
	 * @ordered
	 */
	public static final int PLAN_VALUE = 1;

	/**
	 * The '<em><b>Unknown</b></em>' literal value. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #UNKNOWN
	 * @model name="Unknown" literal=""
	 * @generated
	 * @ordered
	 */
	public static final int UNKNOWN_VALUE = 0;

	/**
	 * An array of all the '<em><b>Object Scope</b></em>' enumerators. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private static final ObjectScope[] VALUES_ARRAY = new ObjectScope[] {
			LAYOUT, CONTENT, BETRACHTUNG, PLAN, UNKNOWN, };

	/**
	 * A public read-only list of all the '<em><b>Object Scope</b></em>'
	 * enumerators. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static final List<ObjectScope> VALUES = Collections
			.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Object Scope</b></em>' literal with the specified
	 * literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param literal
	 *            the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static ObjectScope get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ObjectScope result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Object Scope</b></em>' literal with the specified
	 * name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param name
	 *            the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static ObjectScope getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ObjectScope result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Object Scope</b></em>' literal with the specified
	 * integer value. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static ObjectScope get(int value) {
		switch (value) {
			case LAYOUT_VALUE:
				return LAYOUT;
			case CONTENT_VALUE:
				return CONTENT;
			case BETRACHTUNG_VALUE:
				return BETRACHTUNG;
			case PLAN_VALUE:
				return PLAN;
			case UNKNOWN_VALUE:
				return UNKNOWN;
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
	private ObjectScope(int value, String name, String literal) {
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

} // ObjectScope
