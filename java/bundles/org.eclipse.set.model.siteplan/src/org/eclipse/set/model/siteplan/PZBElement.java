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
 * A representation of the literals of the enumeration '<em><b>PZB Element</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getPZBElement()
 * @model
 * @generated
 */
public enum PZBElement implements Enumerator {
	/**
	 * The '<em><b>None</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #NONE_VALUE
	 * @generated
	 * @ordered
	 */
	NONE(0, "None", "None"),

	/**
	 * The '<em><b>F500 Hz</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #F500_HZ_VALUE
	 * @generated
	 * @ordered
	 */
	F500_HZ(1, "F500Hz", "500Hz"),

	/**
	 * The '<em><b>F1000 Hz</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #F1000_HZ_VALUE
	 * @generated
	 * @ordered
	 */
	F1000_HZ(2, "F1000Hz", "1000Hz"),

	/**
	 * The '<em><b>F2000 Hz</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #F2000_HZ_VALUE
	 * @generated
	 * @ordered
	 */
	F2000_HZ(3, "F2000Hz", "2000Hz"),

	/**
	 * The '<em><b>F1000 Hz2000 Hz</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #F1000_HZ2000_HZ_VALUE
	 * @generated
	 * @ordered
	 */
	F1000_HZ2000_HZ(4, "F1000Hz2000Hz", "1000Hz2000Hz");

	/**
	 * The '<em><b>None</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #NONE
	 * @model name="None"
	 * @generated
	 * @ordered
	 */
	public static final int NONE_VALUE = 0;

	/**
	 * The '<em><b>F500 Hz</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #F500_HZ
	 * @model name="F500Hz" literal="500Hz"
	 * @generated
	 * @ordered
	 */
	public static final int F500_HZ_VALUE = 1;

	/**
	 * The '<em><b>F1000 Hz</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #F1000_HZ
	 * @model name="F1000Hz" literal="1000Hz"
	 * @generated
	 * @ordered
	 */
	public static final int F1000_HZ_VALUE = 2;

	/**
	 * The '<em><b>F2000 Hz</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #F2000_HZ
	 * @model name="F2000Hz" literal="2000Hz"
	 * @generated
	 * @ordered
	 */
	public static final int F2000_HZ_VALUE = 3;

	/**
	 * The '<em><b>F1000 Hz2000 Hz</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #F1000_HZ2000_HZ
	 * @model name="F1000Hz2000Hz" literal="1000Hz2000Hz"
	 * @generated
	 * @ordered
	 */
	public static final int F1000_HZ2000_HZ_VALUE = 4;

	/**
	 * An array of all the '<em><b>PZB Element</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final PZBElement[] VALUES_ARRAY =
		new PZBElement[] {
			NONE,
			F500_HZ,
			F1000_HZ,
			F2000_HZ,
			F1000_HZ2000_HZ,
		};

	/**
	 * A public read-only list of all the '<em><b>PZB Element</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<PZBElement> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>PZB Element</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param literal the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static PZBElement get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			PZBElement result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>PZB Element</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static PZBElement getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			PZBElement result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>PZB Element</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static PZBElement get(int value) {
		switch (value) {
			case NONE_VALUE: return NONE;
			case F500_HZ_VALUE: return F500_HZ;
			case F1000_HZ_VALUE: return F1000_HZ;
			case F2000_HZ_VALUE: return F2000_HZ;
			case F1000_HZ2000_HZ_VALUE: return F1000_HZ2000_HZ;
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
	private PZBElement(int value, String name, String literal) {
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
	
} //PZBElement
