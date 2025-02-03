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
 * A representation of the literals of the enumeration '<em><b>Track Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getTrackType()
 * @model
 * @generated
 */
public enum TrackType implements Enumerator {
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
	 * The '<em><b>Main Track</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #MAIN_TRACK_VALUE
	 * @generated
	 * @ordered
	 */
	MAIN_TRACK(2, "MainTrack", "MainTrack"),

	/**
	 * The '<em><b>Side Track</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SIDE_TRACK_VALUE
	 * @generated
	 * @ordered
	 */
	SIDE_TRACK(4, "SideTrack", "SideTrack"),

	/**
	 * The '<em><b>Connecting Track</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CONNECTING_TRACK_VALUE
	 * @generated
	 * @ordered
	 */
	CONNECTING_TRACK(5, "ConnectingTrack", "ConnectingTrack"),

	/**
	 * The '<em><b>Route Track</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ROUTE_TRACK_VALUE
	 * @generated
	 * @ordered
	 */
	ROUTE_TRACK(3, "RouteTrack", "RouteTrack"),

	/**
	 * The '<em><b>Other</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #OTHER_VALUE
	 * @generated
	 * @ordered
	 */
	OTHER(6, "Other", "Other"),

	/**
	 * The '<em><b>Passing Main Track</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PASSING_MAIN_TRACK_VALUE
	 * @generated
	 * @ordered
	 */
	PASSING_MAIN_TRACK(1, "PassingMainTrack", "PassingMainTrack");

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
	 * The '<em><b>Main Track</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #MAIN_TRACK
	 * @model name="MainTrack"
	 * @generated
	 * @ordered
	 */
	public static final int MAIN_TRACK_VALUE = 2;

	/**
	 * The '<em><b>Side Track</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SIDE_TRACK
	 * @model name="SideTrack"
	 * @generated
	 * @ordered
	 */
	public static final int SIDE_TRACK_VALUE = 4;

	/**
	 * The '<em><b>Connecting Track</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CONNECTING_TRACK
	 * @model name="ConnectingTrack"
	 * @generated
	 * @ordered
	 */
	public static final int CONNECTING_TRACK_VALUE = 5;

	/**
	 * The '<em><b>Route Track</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ROUTE_TRACK
	 * @model name="RouteTrack"
	 * @generated
	 * @ordered
	 */
	public static final int ROUTE_TRACK_VALUE = 3;

	/**
	 * The '<em><b>Other</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #OTHER
	 * @model name="Other"
	 * @generated
	 * @ordered
	 */
	public static final int OTHER_VALUE = 6;

	/**
	 * The '<em><b>Passing Main Track</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PASSING_MAIN_TRACK
	 * @model name="PassingMainTrack"
	 * @generated
	 * @ordered
	 */
	public static final int PASSING_MAIN_TRACK_VALUE = 1;

	/**
	 * An array of all the '<em><b>Track Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final TrackType[] VALUES_ARRAY =
		new TrackType[] {
			NONE,
			MAIN_TRACK,
			SIDE_TRACK,
			CONNECTING_TRACK,
			ROUTE_TRACK,
			OTHER,
			PASSING_MAIN_TRACK,
		};

	/**
	 * A public read-only list of all the '<em><b>Track Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<TrackType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Track Type</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param literal the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static TrackType get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			TrackType result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Track Type</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static TrackType getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			TrackType result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Track Type</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static TrackType get(int value) {
		switch (value) {
			case NONE_VALUE: return NONE;
			case MAIN_TRACK_VALUE: return MAIN_TRACK;
			case SIDE_TRACK_VALUE: return SIDE_TRACK;
			case CONNECTING_TRACK_VALUE: return CONNECTING_TRACK;
			case ROUTE_TRACK_VALUE: return ROUTE_TRACK;
			case OTHER_VALUE: return OTHER;
			case PASSING_MAIN_TRACK_VALUE: return PASSING_MAIN_TRACK;
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
	private TrackType(int value, String name, String literal) {
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
	
} //TrackType
