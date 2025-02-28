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
 * '<em><b>FMA Component Type</b></em>', and utility methods for working with
 * them. <!-- end-user-doc -->
 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getFMAComponentType()
 * @model
 * @generated
 */
public enum FMAComponentType implements Enumerator {
	/**
	 * The '<em><b>None</b></em>' literal object.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #NONE_VALUE
	 * @generated
	 * @ordered
	 */
	NONE(0, "None", "None"),

	/**
	 * The '<em><b>Axle</b></em>' literal object.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #AXLE_VALUE
	 * @generated
	 * @ordered
	 */
	AXLE(1, "Axle", "Axle"),

	/**
	 * The '<em><b>NFDC Circuit</b></em>' literal object.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @see #NFDC_CIRCUIT_VALUE
	 * @generated
	 * @ordered
	 */
	NFDC_CIRCUIT(2, "NFDCCircuit", "NFDCCircuit"),

	/**
	 * The '<em><b>TFDC Circuit</b></em>' literal object.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @see #TFDC_CIRCUIT_VALUE
	 * @generated
	 * @ordered
	 */
	TFDC_CIRCUIT(3, "TFDCCircuit", "TFDCCircuit");

	/**
	 * The '<em><b>None</b></em>' literal value.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #NONE
	 * @model name="None"
	 * @generated
	 * @ordered
	 */
	public static final int NONE_VALUE = 0;

	/**
	 * The '<em><b>Axle</b></em>' literal value.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #AXLE
	 * @model name="Axle"
	 * @generated
	 * @ordered
	 */
	public static final int AXLE_VALUE = 1;

	/**
	 * The '<em><b>NFDC Circuit</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #NFDC_CIRCUIT
	 * @model name="NFDCCircuit"
	 * @generated
	 * @ordered
	 */
	public static final int NFDC_CIRCUIT_VALUE = 2;

	/**
	 * The '<em><b>TFDC Circuit</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #TFDC_CIRCUIT
	 * @model name="TFDCCircuit"
	 * @generated
	 * @ordered
	 */
	public static final int TFDC_CIRCUIT_VALUE = 3;

	/**
	 * An array of all the '<em><b>FMA Component Type</b></em>' enumerators.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private static final FMAComponentType[] VALUES_ARRAY = new FMAComponentType[] {
			NONE,
			AXLE,
			NFDC_CIRCUIT,
			TFDC_CIRCUIT,
		};

	/**
	 * A public read-only list of all the '<em><b>FMA Component Type</b></em>' enumerators.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<FMAComponentType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>FMA Component Type</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param literal the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static FMAComponentType get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			FMAComponentType result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>FMA Component Type</b></em>' literal with the specified name.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param name the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static FMAComponentType getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			FMAComponentType result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>FMA Component Type</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static FMAComponentType get(int value) {
		switch (value) {
			case NONE_VALUE: return NONE;
			case AXLE_VALUE: return AXLE;
			case NFDC_CIRCUIT_VALUE: return NFDC_CIRCUIT;
			case TFDC_CIRCUIT_VALUE: return TFDC_CIRCUIT;
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private final int value;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private final String name;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private final String literal;

	/**
	 * Only this class can construct instances.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 */
	private FMAComponentType(int value, String name, String literal) {
		this.value = value;
		this.name = name;
		this.literal = literal;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getValue() {
	  return value;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getName() {
	  return name;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getLiteral() {
	  return literal;
	}

	/**
	 * Returns the literal value of the enumerator, which is its string representation.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		return literal;
	}

} // FMAComponentType
