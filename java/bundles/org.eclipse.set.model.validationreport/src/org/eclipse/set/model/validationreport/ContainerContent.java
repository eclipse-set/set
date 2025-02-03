/**
 * Copyright (c) 2023 DB Netz AG and others.
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
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Container Content</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see org.eclipse.set.model.validationreport.ValidationreportPackage#getContainerContent()
 * @model
 * @generated
 */
public enum ContainerContent implements Enumerator {
	/**
	 * The '<em><b>MODEL</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #MODEL_VALUE
	 * @generated
	 * @ordered
	 */
	MODEL(0, "MODEL", "Fachdaten"),

	/**
	 * The '<em><b>LAYOUT</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #LAYOUT_VALUE
	 * @generated
	 * @ordered
	 */
	LAYOUT(1, "LAYOUT", "Layoutdaten"),

	/**
	 * The '<em><b>ATTACHMENT</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ATTACHMENT_VALUE
	 * @generated
	 * @ordered
	 */
	ATTACHMENT(2, "ATTACHMENT", "Anh\u00e4nge");

	/**
	 * The '<em><b>MODEL</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #MODEL
	 * @model literal="Fachdaten"
	 * @generated
	 * @ordered
	 */
	public static final int MODEL_VALUE = 0;

	/**
	 * The '<em><b>LAYOUT</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #LAYOUT
	 * @model literal="Layoutdaten"
	 * @generated
	 * @ordered
	 */
	public static final int LAYOUT_VALUE = 1;

	/**
	 * The '<em><b>ATTACHMENT</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ATTACHMENT
	 * @model literal="Anh\344nge"
	 * @generated
	 * @ordered
	 */
	public static final int ATTACHMENT_VALUE = 2;

	/**
	 * An array of all the '<em><b>Container Content</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final ContainerContent[] VALUES_ARRAY =
		new ContainerContent[] {
			MODEL,
			LAYOUT,
			ATTACHMENT,
		};

	/**
	 * A public read-only list of all the '<em><b>Container Content</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<ContainerContent> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Container Content</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param literal the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static ContainerContent get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ContainerContent result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Container Content</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static ContainerContent getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ContainerContent result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Container Content</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static ContainerContent get(int value) {
		switch (value) {
			case MODEL_VALUE: return MODEL;
			case LAYOUT_VALUE: return LAYOUT;
			case ATTACHMENT_VALUE: return ATTACHMENT;
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
	private ContainerContent(int value, String name, String literal) {
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
	
} //ContainerContent
