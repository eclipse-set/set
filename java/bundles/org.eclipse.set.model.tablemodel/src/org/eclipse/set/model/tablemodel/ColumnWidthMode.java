/**
 * Copyright (c) 2022 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.tablemodel;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc --> A representation of the literals of the enumeration
 * '<em><b>Column Width Mode</b></em>', and utility methods for working with
 * them. <!-- end-user-doc -->
 * 
 * @see org.eclipse.set.model.tablemodel.TablemodelPackage#getColumnWidthMode()
 * @model
 * @generated
 */
public enum ColumnWidthMode implements Enumerator {
	/**
	 * The '<em><b>WIDTH CM</b></em>' literal object. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #WIDTH_CM_VALUE
	 * @generated
	 * @ordered
	 */
	WIDTH_CM(0, "WIDTH_CM", "WIDTH_CM"),

	/**
	 * The '<em><b>WIDTH PERCENT</b></em>' literal object. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #WIDTH_PERCENT_VALUE
	 * @generated
	 * @ordered
	 */
	WIDTH_PERCENT(1, "WIDTH_PERCENT", "WIDTH_PERCENT");

	/**
	 * The '<em><b>WIDTH CM</b></em>' literal value. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #WIDTH_CM
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int WIDTH_CM_VALUE = 0;

	/**
	 * The '<em><b>WIDTH PERCENT</b></em>' literal value. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #WIDTH_PERCENT
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int WIDTH_PERCENT_VALUE = 1;

	/**
	 * An array of all the '<em><b>Column Width Mode</b></em>' enumerators. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private static final ColumnWidthMode[] VALUES_ARRAY = new ColumnWidthMode[] {
			WIDTH_CM, WIDTH_PERCENT, };

	/**
	 * A public read-only list of all the '<em><b>Column Width Mode</b></em>'
	 * enumerators. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static final List<ColumnWidthMode> VALUES = Collections
			.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Column Width Mode</b></em>' literal with the
	 * specified literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param literal
	 *            the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static ColumnWidthMode get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ColumnWidthMode result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Column Width Mode</b></em>' literal with the
	 * specified name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param name
	 *            the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static ColumnWidthMode getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ColumnWidthMode result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Column Width Mode</b></em>' literal with the
	 * specified integer value. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static ColumnWidthMode get(int value) {
		switch (value) {
			case WIDTH_CM_VALUE:
				return WIDTH_CM;
			case WIDTH_PERCENT_VALUE:
				return WIDTH_PERCENT;
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
	private ColumnWidthMode(int value, String name, String literal) {
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

} // ColumnWidthMode
