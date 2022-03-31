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

import org.eclipse.set.model.test.site.BuildingNames;
import org.eclipse.set.model.test.site.SitePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Building Names</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.set.model.test.site.impl.BuildingNamesImpl#getAddress <em>Address</em>}</li>
 *   <li>{@link org.eclipse.set.model.test.site.impl.BuildingNamesImpl#getEntrance <em>Entrance</em>}</li>
 *   <li>{@link org.eclipse.set.model.test.site.impl.BuildingNamesImpl#getSitePlan <em>Site Plan</em>}</li>
 * </ul>
 *
 * @generated
 */
public class BuildingNamesImpl extends MinimalEObjectImpl.Container implements BuildingNames {
	/**
	 * The default value of the '{@link #getAddress() <em>Address</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAddress()
	 * @generated
	 * @ordered
	 */
	protected static final String ADDRESS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getAddress() <em>Address</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAddress()
	 * @generated
	 * @ordered
	 */
	protected String address = ADDRESS_EDEFAULT;

	/**
	 * The default value of the '{@link #getEntrance() <em>Entrance</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEntrance()
	 * @generated
	 * @ordered
	 */
	protected static final String ENTRANCE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getEntrance() <em>Entrance</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEntrance()
	 * @generated
	 * @ordered
	 */
	protected String entrance = ENTRANCE_EDEFAULT;

	/**
	 * The default value of the '{@link #getSitePlan() <em>Site Plan</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSitePlan()
	 * @generated
	 * @ordered
	 */
	protected static final String SITE_PLAN_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSitePlan() <em>Site Plan</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSitePlan()
	 * @generated
	 * @ordered
	 */
	protected String sitePlan = SITE_PLAN_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BuildingNamesImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SitePackage.Literals.BUILDING_NAMES;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAddress(String newAddress) {
		String oldAddress = address;
		address = newAddress;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SitePackage.BUILDING_NAMES__ADDRESS, oldAddress, address));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getEntrance() {
		return entrance;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEntrance(String newEntrance) {
		String oldEntrance = entrance;
		entrance = newEntrance;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SitePackage.BUILDING_NAMES__ENTRANCE, oldEntrance, entrance));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getSitePlan() {
		return sitePlan;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSitePlan(String newSitePlan) {
		String oldSitePlan = sitePlan;
		sitePlan = newSitePlan;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SitePackage.BUILDING_NAMES__SITE_PLAN, oldSitePlan, sitePlan));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case SitePackage.BUILDING_NAMES__ADDRESS:
				return getAddress();
			case SitePackage.BUILDING_NAMES__ENTRANCE:
				return getEntrance();
			case SitePackage.BUILDING_NAMES__SITE_PLAN:
				return getSitePlan();
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
			case SitePackage.BUILDING_NAMES__ADDRESS:
				setAddress((String)newValue);
				return;
			case SitePackage.BUILDING_NAMES__ENTRANCE:
				setEntrance((String)newValue);
				return;
			case SitePackage.BUILDING_NAMES__SITE_PLAN:
				setSitePlan((String)newValue);
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
			case SitePackage.BUILDING_NAMES__ADDRESS:
				setAddress(ADDRESS_EDEFAULT);
				return;
			case SitePackage.BUILDING_NAMES__ENTRANCE:
				setEntrance(ENTRANCE_EDEFAULT);
				return;
			case SitePackage.BUILDING_NAMES__SITE_PLAN:
				setSitePlan(SITE_PLAN_EDEFAULT);
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
			case SitePackage.BUILDING_NAMES__ADDRESS:
				return ADDRESS_EDEFAULT == null ? address != null : !ADDRESS_EDEFAULT.equals(address);
			case SitePackage.BUILDING_NAMES__ENTRANCE:
				return ENTRANCE_EDEFAULT == null ? entrance != null : !ENTRANCE_EDEFAULT.equals(entrance);
			case SitePackage.BUILDING_NAMES__SITE_PLAN:
				return SITE_PLAN_EDEFAULT == null ? sitePlan != null : !SITE_PLAN_EDEFAULT.equals(sitePlan);
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
		result.append(" (address: ");
		result.append(address);
		result.append(", entrance: ");
		result.append(entrance);
		result.append(", sitePlan: ");
		result.append(sitePlan);
		result.append(')');
		return result.toString();
	}

} //BuildingNamesImpl
