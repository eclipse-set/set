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
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.set.model.test.site.Floor;
import org.eclipse.set.model.test.site.FloorNames;
import org.eclipse.set.model.test.site.SitePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Floor</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.set.model.test.site.impl.FloorImpl#getNames <em>Names</em>}</li>
 *   <li>{@link org.eclipse.set.model.test.site.impl.FloorImpl#getBuildingID <em>Building ID</em>}</li>
 * </ul>
 *
 * @generated
 */
public class FloorImpl extends IdentifiedImpl implements Floor {
	/**
	 * The cached value of the '{@link #getNames() <em>Names</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNames()
	 * @generated
	 * @ordered
	 */
	protected FloorNames names;

	/**
	 * The default value of the '{@link #getBuildingID() <em>Building ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBuildingID()
	 * @generated
	 * @ordered
	 */
	protected static final String BUILDING_ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getBuildingID() <em>Building ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBuildingID()
	 * @generated
	 * @ordered
	 */
	protected String buildingID = BUILDING_ID_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FloorImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SitePackage.Literals.FLOOR;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FloorNames getNames() {
		return names;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetNames(FloorNames newNames, NotificationChain msgs) {
		FloorNames oldNames = names;
		names = newNames;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SitePackage.FLOOR__NAMES, oldNames, newNames);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNames(FloorNames newNames) {
		if (newNames != names) {
			NotificationChain msgs = null;
			if (names != null)
				msgs = ((InternalEObject)names).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - SitePackage.FLOOR__NAMES, null, msgs);
			if (newNames != null)
				msgs = ((InternalEObject)newNames).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - SitePackage.FLOOR__NAMES, null, msgs);
			msgs = basicSetNames(newNames, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SitePackage.FLOOR__NAMES, newNames, newNames));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getBuildingID() {
		return buildingID;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBuildingID(String newBuildingID) {
		String oldBuildingID = buildingID;
		buildingID = newBuildingID;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SitePackage.FLOOR__BUILDING_ID, oldBuildingID, buildingID));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SitePackage.FLOOR__NAMES:
				return basicSetNames(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case SitePackage.FLOOR__NAMES:
				return getNames();
			case SitePackage.FLOOR__BUILDING_ID:
				return getBuildingID();
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
			case SitePackage.FLOOR__NAMES:
				setNames((FloorNames)newValue);
				return;
			case SitePackage.FLOOR__BUILDING_ID:
				setBuildingID((String)newValue);
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
			case SitePackage.FLOOR__NAMES:
				setNames((FloorNames)null);
				return;
			case SitePackage.FLOOR__BUILDING_ID:
				setBuildingID(BUILDING_ID_EDEFAULT);
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
			case SitePackage.FLOOR__NAMES:
				return names != null;
			case SitePackage.FLOOR__BUILDING_ID:
				return BUILDING_ID_EDEFAULT == null ? buildingID != null : !BUILDING_ID_EDEFAULT.equals(buildingID);
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
		result.append(" (buildingID: ");
		result.append(buildingID);
		result.append(')');
		return result.toString();
	}

} //FloorImpl
