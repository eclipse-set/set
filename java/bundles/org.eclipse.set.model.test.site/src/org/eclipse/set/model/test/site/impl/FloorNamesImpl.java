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

import org.eclipse.set.model.test.site.FloorNames;
import org.eclipse.set.model.test.site.SitePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Floor Names</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.set.model.test.site.impl.FloorNamesImpl#getElevator <em>Elevator</em>}</li>
 *   <li>{@link org.eclipse.set.model.test.site.impl.FloorNamesImpl#getFloorPlanTitle <em>Floor Plan Title</em>}</li>
 * </ul>
 *
 * @generated
 */
public class FloorNamesImpl extends MinimalEObjectImpl.Container implements FloorNames {
	/**
	 * The default value of the '{@link #getElevator() <em>Elevator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getElevator()
	 * @generated
	 * @ordered
	 */
	protected static final String ELEVATOR_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getElevator() <em>Elevator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getElevator()
	 * @generated
	 * @ordered
	 */
	protected String elevator = ELEVATOR_EDEFAULT;

	/**
	 * The default value of the '{@link #getFloorPlanTitle() <em>Floor Plan Title</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFloorPlanTitle()
	 * @generated
	 * @ordered
	 */
	protected static final String FLOOR_PLAN_TITLE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getFloorPlanTitle() <em>Floor Plan Title</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFloorPlanTitle()
	 * @generated
	 * @ordered
	 */
	protected String floorPlanTitle = FLOOR_PLAN_TITLE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FloorNamesImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SitePackage.Literals.FLOOR_NAMES;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getElevator() {
		return elevator;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setElevator(String newElevator) {
		String oldElevator = elevator;
		elevator = newElevator;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SitePackage.FLOOR_NAMES__ELEVATOR, oldElevator, elevator));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getFloorPlanTitle() {
		return floorPlanTitle;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFloorPlanTitle(String newFloorPlanTitle) {
		String oldFloorPlanTitle = floorPlanTitle;
		floorPlanTitle = newFloorPlanTitle;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SitePackage.FLOOR_NAMES__FLOOR_PLAN_TITLE, oldFloorPlanTitle, floorPlanTitle));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case SitePackage.FLOOR_NAMES__ELEVATOR:
				return getElevator();
			case SitePackage.FLOOR_NAMES__FLOOR_PLAN_TITLE:
				return getFloorPlanTitle();
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
			case SitePackage.FLOOR_NAMES__ELEVATOR:
				setElevator((String)newValue);
				return;
			case SitePackage.FLOOR_NAMES__FLOOR_PLAN_TITLE:
				setFloorPlanTitle((String)newValue);
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
			case SitePackage.FLOOR_NAMES__ELEVATOR:
				setElevator(ELEVATOR_EDEFAULT);
				return;
			case SitePackage.FLOOR_NAMES__FLOOR_PLAN_TITLE:
				setFloorPlanTitle(FLOOR_PLAN_TITLE_EDEFAULT);
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
			case SitePackage.FLOOR_NAMES__ELEVATOR:
				return ELEVATOR_EDEFAULT == null ? elevator != null : !ELEVATOR_EDEFAULT.equals(elevator);
			case SitePackage.FLOOR_NAMES__FLOOR_PLAN_TITLE:
				return FLOOR_PLAN_TITLE_EDEFAULT == null ? floorPlanTitle != null : !FLOOR_PLAN_TITLE_EDEFAULT.equals(floorPlanTitle);
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
		result.append(" (elevator: ");
		result.append(elevator);
		result.append(", floorPlanTitle: ");
		result.append(floorPlanTitle);
		result.append(')');
		return result.toString();
	}

} //FloorNamesImpl
