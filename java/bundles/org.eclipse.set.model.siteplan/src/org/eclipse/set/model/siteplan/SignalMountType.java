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
 * '<em><b>Signal Mount Type</b></em>', and utility methods for working with
 * them. <!-- end-user-doc -->
 * 
 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getSignalMountType()
 * @model
 * @generated
 */
public enum SignalMountType implements Enumerator {
	/**
	 * The '<em><b>Mast</b></em>' literal object. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #MAST_VALUE
	 * @generated
	 * @ordered
	 */
	MAST(0, "Mast", "Mast"),

	/**
	 * The '<em><b>Mehrere Masten</b></em>' literal object. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #MEHRERE_MASTEN_VALUE
	 * @generated
	 * @ordered
	 */
	MEHRERE_MASTEN(1, "MehrereMasten", "MehrereMasten"),

	/**
	 * The '<em><b>Pfosten</b></em>' literal object. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #PFOSTEN_VALUE
	 * @generated
	 * @ordered
	 */
	PFOSTEN(2, "Pfosten", "Pfosten"),

	/**
	 * The '<em><b>Schienenfuss</b></em>' literal object. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #SCHIENENFUSS_VALUE
	 * @generated
	 * @ordered
	 */
	SCHIENENFUSS(3, "Schienenfuss", "Schienenfuss"),

	/**
	 * The '<em><b>Gleisabschluss</b></em>' literal object. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #GLEISABSCHLUSS_VALUE
	 * @generated
	 * @ordered
	 */
	GLEISABSCHLUSS(4, "Gleisabschluss", "Gleisabschluss"),

	/**
	 * The '<em><b>Mast Niedrig</b></em>' literal object. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #MAST_NIEDRIG_VALUE
	 * @generated
	 * @ordered
	 */
	MAST_NIEDRIG(5, "MastNiedrig", "MastNiedrig"),

	/**
	 * The '<em><b>Pfosten Niedrig</b></em>' literal object. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #PFOSTEN_NIEDRIG_VALUE
	 * @generated
	 * @ordered
	 */
	PFOSTEN_NIEDRIG(6, "PfostenNiedrig", "PfostenNiedrig"),

	/**
	 * The '<em><b>Deckenkonstruktion</b></em>' literal object. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #DECKENKONSTRUKTION_VALUE
	 * @generated
	 * @ordered
	 */
	DECKENKONSTRUKTION(7, "Deckenkonstruktion", "Deckenkonstruktion"),

	/**
	 * The '<em><b>Wandkonstruktion</b></em>' literal object. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #WANDKONSTRUKTION_VALUE
	 * @generated
	 * @ordered
	 */
	WANDKONSTRUKTION(8, "Wandkonstruktion", "Wandkonstruktion"),

	/**
	 * The '<em><b>Signalausleger Links</b></em>' literal object. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #SIGNALAUSLEGER_LINKS_VALUE
	 * @generated
	 * @ordered
	 */
	SIGNALAUSLEGER(9, "Signalausleger", "Signalausleger"),

	/**
	 * The '<em><b>Signalbruecke</b></em>' literal object. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #SIGNALBRUECKE_VALUE
	 * @generated
	 * @ordered
	 */
	SIGNALBRUECKE(11, "Signalbruecke", "Signalbruecke"),

	/**
	 * The '<em><b>Sonderkonstruktion</b></em>' literal object. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #SONDERKONSTRUKTION_VALUE
	 * @generated
	 * @ordered
	 */
	SONDERKONSTRUKTION(12, "Sonderkonstruktion", "Sonderkonstruktion");

	/**
	 * The '<em><b>Mast</b></em>' literal value. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #MAST
	 * @model name="Mast"
	 * @generated
	 * @ordered
	 */
	public static final int MAST_VALUE = 0;

	/**
	 * The '<em><b>Mehrere Masten</b></em>' literal value. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #MEHRERE_MASTEN
	 * @model name="MehrereMasten"
	 * @generated
	 * @ordered
	 */
	public static final int MEHRERE_MASTEN_VALUE = 1;

	/**
	 * The '<em><b>Pfosten</b></em>' literal value. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #PFOSTEN
	 * @model name="Pfosten"
	 * @generated
	 * @ordered
	 */
	public static final int PFOSTEN_VALUE = 2;

	/**
	 * The '<em><b>Schienenfuss</b></em>' literal value. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #SCHIENENFUSS
	 * @model name="Schienenfuss"
	 * @generated
	 * @ordered
	 */
	public static final int SCHIENENFUSS_VALUE = 3;

	/**
	 * The '<em><b>Gleisabschluss</b></em>' literal value. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #GLEISABSCHLUSS
	 * @model name="Gleisabschluss"
	 * @generated
	 * @ordered
	 */
	public static final int GLEISABSCHLUSS_VALUE = 4;

	/**
	 * The '<em><b>Mast Niedrig</b></em>' literal value. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #MAST_NIEDRIG
	 * @model name="MastNiedrig"
	 * @generated
	 * @ordered
	 */
	public static final int MAST_NIEDRIG_VALUE = 5;

	/**
	 * The '<em><b>Pfosten Niedrig</b></em>' literal value. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #PFOSTEN_NIEDRIG
	 * @model name="PfostenNiedrig"
	 * @generated
	 * @ordered
	 */
	public static final int PFOSTEN_NIEDRIG_VALUE = 6;

	/**
	 * The '<em><b>Deckenkonstruktion</b></em>' literal value. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #DECKENKONSTRUKTION
	 * @model name="Deckenkonstruktion"
	 * @generated
	 * @ordered
	 */
	public static final int DECKENKONSTRUKTION_VALUE = 7;

	/**
	 * The '<em><b>Wandkonstruktion</b></em>' literal value. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #WANDKONSTRUKTION
	 * @model name="Wandkonstruktion"
	 * @generated
	 * @ordered
	 */
	public static final int WANDKONSTRUKTION_VALUE = 8;

	/**
	 * The '<em><b>Signalausleger Links</b></em>' literal value. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #SIGNALAUSLEGER_LINKS
	 * @model name="SignalauslegerLinks"
	 * @generated
	 * @ordered
	 */
	public static final int SIGNALAUSLEGER_VALUE = 9;

	/**
	 * The '<em><b>Signalbruecke</b></em>' literal value. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #SIGNALBRUECKE
	 * @model name="Signalbruecke"
	 * @generated
	 * @ordered
	 */
	public static final int SIGNALBRUECKE_VALUE = 11;

	/**
	 * The '<em><b>Sonderkonstruktion</b></em>' literal value. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #SONDERKONSTRUKTION
	 * @model name="Sonderkonstruktion"
	 * @generated
	 * @ordered
	 */
	public static final int SONDERKONSTRUKTION_VALUE = 12;

	/**
	 * An array of all the '<em><b>Signal Mount Type</b></em>' enumerators. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private static final SignalMountType[] VALUES_ARRAY = new SignalMountType[] {
			MAST, MEHRERE_MASTEN, PFOSTEN, SCHIENENFUSS, GLEISABSCHLUSS,
			MAST_NIEDRIG, PFOSTEN_NIEDRIG, DECKENKONSTRUKTION, WANDKONSTRUKTION,
			SIGNALAUSLEGER, SIGNALBRUECKE,
			SONDERKONSTRUKTION, };

	/**
	 * A public read-only list of all the '<em><b>Signal Mount Type</b></em>'
	 * enumerators. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static final List<SignalMountType> VALUES = Collections
			.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Signal Mount Type</b></em>' literal with the
	 * specified literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param literal
	 *            the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static SignalMountType get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			SignalMountType result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Signal Mount Type</b></em>' literal with the
	 * specified name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param name
	 *            the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static SignalMountType getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			SignalMountType result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Signal Mount Type</b></em>' literal with the
	 * specified integer value. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static SignalMountType get(int value) {
		switch (value) {
			case MAST_VALUE:
				return MAST;
			case MEHRERE_MASTEN_VALUE:
				return MEHRERE_MASTEN;
			case PFOSTEN_VALUE:
				return PFOSTEN;
			case SCHIENENFUSS_VALUE:
				return SCHIENENFUSS;
			case GLEISABSCHLUSS_VALUE:
				return GLEISABSCHLUSS;
			case MAST_NIEDRIG_VALUE:
				return MAST_NIEDRIG;
			case PFOSTEN_NIEDRIG_VALUE:
				return PFOSTEN_NIEDRIG;
			case DECKENKONSTRUKTION_VALUE:
				return DECKENKONSTRUKTION;
			case WANDKONSTRUKTION_VALUE:
				return WANDKONSTRUKTION;
			case SIGNALAUSLEGER_VALUE:
				return SIGNALAUSLEGER;
			case SIGNALBRUECKE_VALUE:
				return SIGNALBRUECKE;
			case SONDERKONSTRUKTION_VALUE:
				return SONDERKONSTRUKTION;
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
	private SignalMountType(int value, String name, String literal) {
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

} // SignalMountType
