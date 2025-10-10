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
 * '<em><b>Plan Compare Row Type</b></em>', and utility methods for working with
 * them. <!-- end-user-doc -->
 * 
 * @see org.eclipse.set.model.tablemodel.TablemodelPackage#getPlanCompareRowType()
 * @model
 * @generated
 */
public enum PlanCompareRowType implements Enumerator {
	/**
	 * The '<em><b>NEW ROW</b></em>' literal object. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #NEW_ROW_VALUE
	 * @generated
	 * @ordered
	 */
	NEW_ROW(0, "NEW_ROW", "NEW_ROW"),

	/**
	 * The '<em><b>REMOVED ROW</b></em>' literal object. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #REMOVED_ROW_VALUE
	 * @generated
	 * @ordered
	 */
	REMOVED_ROW(1, "REMOVED_ROW", "REMOVED_ROW"),

	/**
	 * The '<em><b>CHANGED GUID ROW</b></em>' literal object. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #CHANGED_GUID_ROW_VALUE
	 * @generated
	 * @ordered
	 */
	CHANGED_GUID_ROW(2, "CHANGED_GUID_ROW", "CHANGED_GUID_ROW");

	/**
	 * The '<em><b>NEW ROW</b></em>' literal value. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #NEW_ROW
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int NEW_ROW_VALUE = 0;

	/**
	 * The '<em><b>REMOVED ROW</b></em>' literal value. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #REMOVED_ROW
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int REMOVED_ROW_VALUE = 1;

	/**
	 * The '<em><b>CHANGED GUID ROW</b></em>' literal value. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #CHANGED_GUID_ROW
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int CHANGED_GUID_ROW_VALUE = 2;

	/**
	 * An array of all the '<em><b>Plan Compare Row Type</b></em>' enumerators.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private static final PlanCompareRowType[] VALUES_ARRAY = new PlanCompareRowType[] {
			NEW_ROW, REMOVED_ROW, CHANGED_GUID_ROW, };

	/**
	 * A public read-only list of all the '<em><b>Plan Compare Row
	 * Type</b></em>' enumerators. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static final List<PlanCompareRowType> VALUES = Collections
			.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Plan Compare Row Type</b></em>' literal with the
	 * specified literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param literal
	 *            the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static PlanCompareRowType get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			PlanCompareRowType result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Plan Compare Row Type</b></em>' literal with the
	 * specified name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param name
	 *            the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static PlanCompareRowType getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			PlanCompareRowType result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Plan Compare Row Type</b></em>' literal with the
	 * specified integer value. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static PlanCompareRowType get(int value) {
		switch (value) {
			case NEW_ROW_VALUE:
				return NEW_ROW;
			case REMOVED_ROW_VALUE:
				return REMOVED_ROW;
			case CHANGED_GUID_ROW_VALUE:
				return CHANGED_GUID_ROW;
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
	private PlanCompareRowType(int value, String name, String literal) {
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

} // PlanCompareRowType
