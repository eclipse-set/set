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
 * '<em><b>Track Lock Location</b></em>', and utility methods for working with
 * them. <!-- end-user-doc -->
 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getTrackLockLocation()
 * @model
 * @generated
 */
public enum TrackLockLocation implements Enumerator {
	/**
	 * The '<em><b>Beside Track</b></em>' literal object.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @see #BESIDE_TRACK_VALUE
	 * @generated
	 * @ordered
	 */
	BESIDE_TRACK(0, "BesideTrack", "BesideTrack"),

	/**
	 * The '<em><b>On Track</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ON_TRACK_VALUE
	 * @generated
	 * @ordered
	 */
	ON_TRACK(1, "OnTrack", "OnTrack");

	/**
	 * The '<em><b>Beside Track</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #BESIDE_TRACK
	 * @model name="BesideTrack"
	 * @generated
	 * @ordered
	 */
	public static final int BESIDE_TRACK_VALUE = 0;

	/**
	 * The '<em><b>On Track</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ON_TRACK
	 * @model name="OnTrack"
	 * @generated
	 * @ordered
	 */
	public static final int ON_TRACK_VALUE = 1;

	/**
	 * An array of all the '<em><b>Track Lock Location</b></em>' enumerators.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private static final TrackLockLocation[] VALUES_ARRAY = new TrackLockLocation[] {
			BESIDE_TRACK,
			ON_TRACK,
		};

	/**
	 * A public read-only list of all the '<em><b>Track Lock Location</b></em>' enumerators.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<TrackLockLocation> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Track Lock Location</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param literal the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static TrackLockLocation get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			TrackLockLocation result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Track Lock Location</b></em>' literal with the specified name.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param name the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static TrackLockLocation getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			TrackLockLocation result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Track Lock Location</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static TrackLockLocation get(int value) {
		switch (value) {
			case BESIDE_TRACK_VALUE: return BESIDE_TRACK;
			case ON_TRACK_VALUE: return ON_TRACK;
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
	private TrackLockLocation(int value, String name, String literal) {
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

} // TrackLockLocation
