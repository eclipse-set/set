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
 * '<em><b>Track Shape</b></em>', and utility methods for working with them.
 * <!-- end-user-doc -->
 * 
 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getTrackShape()
 * @model
 * @generated
 */
public enum TrackShape implements Enumerator {
	/**
	 * The '<em><b>Straight</b></em>' literal object. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #STRAIGHT_VALUE
	 * @generated
	 * @ordered
	 */
	STRAIGHT(0, "Straight", "Straight"),

	/**
	 * The '<em><b>Curve</b></em>' literal object. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #CURVE_VALUE
	 * @generated
	 * @ordered
	 */
	CURVE(1, "Curve", "Curve"),

	/**
	 * The '<em><b>Clothoid</b></em>' literal object. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #CLOTHOID_VALUE
	 * @generated
	 * @ordered
	 */
	CLOTHOID(2, "Clothoid", "Clothoid"),

	/**
	 * The '<em><b>Blosscurve</b></em>' literal object. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #BLOSSCURVE_VALUE
	 * @generated
	 * @ordered
	 */
	BLOSSCURVE(3, "Blosscurve", "Blosscurve"),

	/**
	 * The '<em><b>Bloss Curved Simple</b></em>' literal object. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #BLOSS_CURVED_SIMPLE_VALUE
	 * @generated
	 * @ordered
	 */
	BLOSS_CURVED_SIMPLE(4, "BlossCurvedSimple", "BlossCurvedSimple"),

	/**
	 * The '<em><b>Other</b></em>' literal object. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #OTHER_VALUE
	 * @generated
	 * @ordered
	 */
	OTHER(5, "Other", "Other"),

	/**
	 * The '<em><b>Directional Straight Kink End</b></em>' literal object. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #DIRECTIONAL_STRAIGHT_KINK_END_VALUE
	 * @generated
	 * @ordered
	 */
	DIRECTIONAL_STRAIGHT_KINK_END(6, "DirectionalStraightKinkEnd",
			"DirectionalStraightKinkEnd"),

	/**
	 * The '<em><b>Km Jump</b></em>' literal object. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #KM_JUMP_VALUE
	 * @generated
	 * @ordered
	 */
	KM_JUMP(7, "KmJump", "KmJump"),

	/**
	 * The '<em><b>Transition Curve SForm</b></em>' literal object. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #TRANSITION_CURVE_SFORM_VALUE
	 * @generated
	 * @ordered
	 */
	TRANSITION_CURVE_SFORM(8, "TransitionCurveSForm", "TransitionCurveSForm"),

	/**
	 * The '<em><b>SForm Simple Curved</b></em>' literal object. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #SFORM_SIMPLE_CURVED_VALUE
	 * @generated
	 * @ordered
	 */
	SFORM_SIMPLE_CURVED(9, "SFormSimpleCurved", "SFormSimpleCurved"),

	/**
	 * The '<em><b>Unknown</b></em>' literal object. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #UNKNOWN_VALUE
	 * @generated
	 * @ordered
	 */
	UNKNOWN(10, "Unknown", "Unknown");

	/**
	 * The '<em><b>Straight</b></em>' literal value. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #STRAIGHT
	 * @model name="Straight"
	 * @generated
	 * @ordered
	 */
	public static final int STRAIGHT_VALUE = 0;

	/**
	 * The '<em><b>Curve</b></em>' literal value. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #CURVE
	 * @model name="Curve"
	 * @generated
	 * @ordered
	 */
	public static final int CURVE_VALUE = 1;

	/**
	 * The '<em><b>Clothoid</b></em>' literal value. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #CLOTHOID
	 * @model name="Clothoid"
	 * @generated
	 * @ordered
	 */
	public static final int CLOTHOID_VALUE = 2;

	/**
	 * The '<em><b>Blosscurve</b></em>' literal value. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #BLOSSCURVE
	 * @model name="Blosscurve"
	 * @generated
	 * @ordered
	 */
	public static final int BLOSSCURVE_VALUE = 3;

	/**
	 * The '<em><b>Bloss Curved Simple</b></em>' literal value. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #BLOSS_CURVED_SIMPLE
	 * @model name="BlossCurvedSimple"
	 * @generated
	 * @ordered
	 */
	public static final int BLOSS_CURVED_SIMPLE_VALUE = 4;

	/**
	 * The '<em><b>Other</b></em>' literal value. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #OTHER
	 * @model name="Other"
	 * @generated
	 * @ordered
	 */
	public static final int OTHER_VALUE = 5;

	/**
	 * The '<em><b>Directional Straight Kink End</b></em>' literal value. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #DIRECTIONAL_STRAIGHT_KINK_END
	 * @model name="DirectionalStraightKinkEnd"
	 * @generated
	 * @ordered
	 */
	public static final int DIRECTIONAL_STRAIGHT_KINK_END_VALUE = 6;

	/**
	 * The '<em><b>Km Jump</b></em>' literal value. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #KM_JUMP
	 * @model name="KmJump"
	 * @generated
	 * @ordered
	 */
	public static final int KM_JUMP_VALUE = 7;

	/**
	 * The '<em><b>Transition Curve SForm</b></em>' literal value. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #TRANSITION_CURVE_SFORM
	 * @model name="TransitionCurveSForm"
	 * @generated
	 * @ordered
	 */
	public static final int TRANSITION_CURVE_SFORM_VALUE = 8;

	/**
	 * The '<em><b>SForm Simple Curved</b></em>' literal value. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #SFORM_SIMPLE_CURVED
	 * @model name="SFormSimpleCurved"
	 * @generated
	 * @ordered
	 */
	public static final int SFORM_SIMPLE_CURVED_VALUE = 9;

	/**
	 * The '<em><b>Unknown</b></em>' literal value. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #UNKNOWN
	 * @model name="Unknown"
	 * @generated
	 * @ordered
	 */
	public static final int UNKNOWN_VALUE = 10;

	/**
	 * An array of all the '<em><b>Track Shape</b></em>' enumerators. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private static final TrackShape[] VALUES_ARRAY = new TrackShape[] {
			STRAIGHT, CURVE, CLOTHOID, BLOSSCURVE, BLOSS_CURVED_SIMPLE, OTHER,
			DIRECTIONAL_STRAIGHT_KINK_END, KM_JUMP, TRANSITION_CURVE_SFORM,
			SFORM_SIMPLE_CURVED, UNKNOWN, };

	/**
	 * A public read-only list of all the '<em><b>Track Shape</b></em>'
	 * enumerators. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static final List<TrackShape> VALUES = Collections
			.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Track Shape</b></em>' literal with the specified
	 * literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param literal
	 *            the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static TrackShape get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			TrackShape result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Track Shape</b></em>' literal with the specified
	 * name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param name
	 *            the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static TrackShape getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			TrackShape result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Track Shape</b></em>' literal with the specified
	 * integer value. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static TrackShape get(int value) {
		switch (value) {
			case STRAIGHT_VALUE:
				return STRAIGHT;
			case CURVE_VALUE:
				return CURVE;
			case CLOTHOID_VALUE:
				return CLOTHOID;
			case BLOSSCURVE_VALUE:
				return BLOSSCURVE;
			case BLOSS_CURVED_SIMPLE_VALUE:
				return BLOSS_CURVED_SIMPLE;
			case OTHER_VALUE:
				return OTHER;
			case DIRECTIONAL_STRAIGHT_KINK_END_VALUE:
				return DIRECTIONAL_STRAIGHT_KINK_END;
			case KM_JUMP_VALUE:
				return KM_JUMP;
			case TRANSITION_CURVE_SFORM_VALUE:
				return TRANSITION_CURVE_SFORM;
			case SFORM_SIMPLE_CURVED_VALUE:
				return SFORM_SIMPLE_CURVED;
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
	private TrackShape(int value, String name, String literal) {
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

} // TrackShape
