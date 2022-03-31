/**
 * Copyright (c) 2018 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.test.site.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.set.model.test.site.RoomNames;
import org.eclipse.set.model.test.site.SitePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Room Names</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.set.model.test.site.impl.RoomNamesImpl#getDoorSign <em>Door Sign</em>}</li>
 *   <li>{@link org.eclipse.set.model.test.site.impl.RoomNamesImpl#getFloorPlan <em>Floor Plan</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RoomNamesImpl extends MinimalEObjectImpl.Container implements RoomNames {
	/**
	 * The default value of the '{@link #getDoorSign() <em>Door Sign</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDoorSign()
	 * @generated
	 * @ordered
	 */
	protected static final String DOOR_SIGN_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDoorSign() <em>Door Sign</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDoorSign()
	 * @generated
	 * @ordered
	 */
	protected String doorSign = DOOR_SIGN_EDEFAULT;

	/**
	 * The default value of the '{@link #getFloorPlan() <em>Floor Plan</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFloorPlan()
	 * @generated
	 * @ordered
	 */
	protected static final String FLOOR_PLAN_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getFloorPlan() <em>Floor Plan</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFloorPlan()
	 * @generated
	 * @ordered
	 */
	protected String floorPlan = FLOOR_PLAN_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RoomNamesImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SitePackage.Literals.ROOM_NAMES;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDoorSign() {
		return doorSign;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDoorSign(String newDoorSign) {
		String oldDoorSign = doorSign;
		doorSign = newDoorSign;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SitePackage.ROOM_NAMES__DOOR_SIGN, oldDoorSign, doorSign));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getFloorPlan() {
		return floorPlan;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFloorPlan(String newFloorPlan) {
		String oldFloorPlan = floorPlan;
		floorPlan = newFloorPlan;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SitePackage.ROOM_NAMES__FLOOR_PLAN, oldFloorPlan, floorPlan));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case SitePackage.ROOM_NAMES__DOOR_SIGN:
				return getDoorSign();
			case SitePackage.ROOM_NAMES__FLOOR_PLAN:
				return getFloorPlan();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case SitePackage.ROOM_NAMES__DOOR_SIGN:
				setDoorSign((String)newValue);
				return;
			case SitePackage.ROOM_NAMES__FLOOR_PLAN:
				setFloorPlan((String)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case SitePackage.ROOM_NAMES__DOOR_SIGN:
				setDoorSign(DOOR_SIGN_EDEFAULT);
				return;
			case SitePackage.ROOM_NAMES__FLOOR_PLAN:
				setFloorPlan(FLOOR_PLAN_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case SitePackage.ROOM_NAMES__DOOR_SIGN:
				return DOOR_SIGN_EDEFAULT == null ? doorSign != null : !DOOR_SIGN_EDEFAULT.equals(doorSign);
			case SitePackage.ROOM_NAMES__FLOOR_PLAN:
				return FLOOR_PLAN_EDEFAULT == null ? floorPlan != null : !FLOOR_PLAN_EDEFAULT.equals(floorPlan);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (doorSign: ");
		result.append(doorSign);
		result.append(", floorPlan: ");
		result.append(floorPlan);
		result.append(')');
		return result.toString();
	}

} //RoomNamesImpl
