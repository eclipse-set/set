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
 * A representation of the literals of the enumeration '<em><b>External Element Control Art</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getExternalElementControlArt()
 * @model
 * @generated
 */
public enum ExternalElementControlArt implements Enumerator {
	/**
	 * The '<em><b>Fe Ak</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #FE_AK_VALUE
	 * @generated
	 * @ordered
	 */
	FE_AK(0, "FeAk", "FeAk"),

	/**
	 * The '<em><b>GFK</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #GFK_VALUE
	 * @generated
	 * @ordered
	 */
	GFK(1, "GFK", "GFK"),

	/**
	 * The '<em><b>Gleisfreimelde Innenanlage</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #GLEISFREIMELDE_INNENANLAGE_VALUE
	 * @generated
	 * @ordered
	 */
	GLEISFREIMELDE_INNENANLAGE(2, "Gleisfreimelde_Innenanlage", "Gleisfreimelde_Innenanlage"),

	/**
	 * The '<em><b>ESTW A</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ESTW_A_VALUE
	 * @generated
	 * @ordered
	 */
	ESTW_A(3, "ESTW_A", "ESTW_A"),

	/**
	 * The '<em><b>Objektcontroller</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #OBJEKTCONTROLLER_VALUE
	 * @generated
	 * @ordered
	 */
	OBJEKTCONTROLLER(4, "Objektcontroller", "Objektcontroller"),

	/**
	 * The '<em><b>Relaisstellwerk</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #RELAISSTELLWERK_VALUE
	 * @generated
	 * @ordered
	 */
	RELAISSTELLWERK(5, "Relaisstellwerk", "Relaisstellwerk"),

	/**
	 * The '<em><b>Sonstige</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SONSTIGE_VALUE
	 * @generated
	 * @ordered
	 */
	SONSTIGE(6, "sonstige", "sonstige"),

	/**
	 * The '<em><b>Virtuelle Aussenelementansteuerung</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #VIRTUELLE_AUSSENELEMENTANSTEUERUNG_VALUE
	 * @generated
	 * @ordered
	 */
	VIRTUELLE_AUSSENELEMENTANSTEUERUNG(7, "virtuelle_Aussenelementansteuerung", "virtuelle_Aussenelementansteuerung");

	/**
	 * The '<em><b>Fe Ak</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #FE_AK
	 * @model name="FeAk"
	 * @generated
	 * @ordered
	 */
	public static final int FE_AK_VALUE = 0;

	/**
	 * The '<em><b>GFK</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #GFK
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int GFK_VALUE = 1;

	/**
	 * The '<em><b>Gleisfreimelde Innenanlage</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #GLEISFREIMELDE_INNENANLAGE
	 * @model name="Gleisfreimelde_Innenanlage"
	 * @generated
	 * @ordered
	 */
	public static final int GLEISFREIMELDE_INNENANLAGE_VALUE = 2;

	/**
	 * The '<em><b>ESTW A</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ESTW_A
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int ESTW_A_VALUE = 3;

	/**
	 * The '<em><b>Objektcontroller</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #OBJEKTCONTROLLER
	 * @model name="Objektcontroller"
	 * @generated
	 * @ordered
	 */
	public static final int OBJEKTCONTROLLER_VALUE = 4;

	/**
	 * The '<em><b>Relaisstellwerk</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #RELAISSTELLWERK
	 * @model name="Relaisstellwerk"
	 * @generated
	 * @ordered
	 */
	public static final int RELAISSTELLWERK_VALUE = 5;

	/**
	 * The '<em><b>Sonstige</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SONSTIGE
	 * @model name="sonstige"
	 * @generated
	 * @ordered
	 */
	public static final int SONSTIGE_VALUE = 6;

	/**
	 * The '<em><b>Virtuelle Aussenelementansteuerung</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #VIRTUELLE_AUSSENELEMENTANSTEUERUNG
	 * @model name="virtuelle_Aussenelementansteuerung"
	 * @generated
	 * @ordered
	 */
	public static final int VIRTUELLE_AUSSENELEMENTANSTEUERUNG_VALUE = 7;

	/**
	 * An array of all the '<em><b>External Element Control Art</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final ExternalElementControlArt[] VALUES_ARRAY =
		new ExternalElementControlArt[] {
			FE_AK,
			GFK,
			GLEISFREIMELDE_INNENANLAGE,
			ESTW_A,
			OBJEKTCONTROLLER,
			RELAISSTELLWERK,
			SONSTIGE,
			VIRTUELLE_AUSSENELEMENTANSTEUERUNG,
		};

	/**
	 * A public read-only list of all the '<em><b>External Element Control Art</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<ExternalElementControlArt> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>External Element Control Art</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param literal the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static ExternalElementControlArt get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ExternalElementControlArt result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>External Element Control Art</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static ExternalElementControlArt getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ExternalElementControlArt result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>External Element Control Art</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static ExternalElementControlArt get(int value) {
		switch (value) {
			case FE_AK_VALUE: return FE_AK;
			case GFK_VALUE: return GFK;
			case GLEISFREIMELDE_INNENANLAGE_VALUE: return GLEISFREIMELDE_INNENANLAGE;
			case ESTW_A_VALUE: return ESTW_A;
			case OBJEKTCONTROLLER_VALUE: return OBJEKTCONTROLLER;
			case RELAISSTELLWERK_VALUE: return RELAISSTELLWERK;
			case SONSTIGE_VALUE: return SONSTIGE;
			case VIRTUELLE_AUSSENELEMENTANSTEUERUNG_VALUE: return VIRTUELLE_AUSSENELEMENTANSTEUERUNG;
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
	private ExternalElementControlArt(int value, String name, String literal) {
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
	
} //ExternalElementControlArt
