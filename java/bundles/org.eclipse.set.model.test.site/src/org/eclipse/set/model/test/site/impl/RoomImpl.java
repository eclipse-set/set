/**
 * Copyright (c) 2018 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.test.site.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.set.model.test.site.Room;
import org.eclipse.set.model.test.site.RoomNames;
import org.eclipse.set.model.test.site.SitePackage;
import org.eclipse.set.model.test.site.Window;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Room</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.set.model.test.site.impl.RoomImpl#getNames <em>Names</em>}</li>
 *   <li>{@link org.eclipse.set.model.test.site.impl.RoomImpl#getFloorID <em>Floor ID</em>}</li>
 *   <li>{@link org.eclipse.set.model.test.site.impl.RoomImpl#getWindows <em>Windows</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RoomImpl extends IdentifiedImpl implements Room {
	/**
	 * The cached value of the '{@link #getNames() <em>Names</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNames()
	 * @generated
	 * @ordered
	 */
	protected RoomNames names;

	/**
	 * The default value of the '{@link #getFloorID() <em>Floor ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFloorID()
	 * @generated
	 * @ordered
	 */
	protected static final String FLOOR_ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getFloorID() <em>Floor ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFloorID()
	 * @generated
	 * @ordered
	 */
	protected String floorID = FLOOR_ID_EDEFAULT;

	/**
	 * The cached value of the '{@link #getWindows() <em>Windows</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWindows()
	 * @generated
	 * @ordered
	 */
	protected EList<Window> windows;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RoomImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SitePackage.Literals.ROOM;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RoomNames getNames() {
		return names;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetNames(RoomNames newNames, NotificationChain msgs) {
		RoomNames oldNames = names;
		names = newNames;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SitePackage.ROOM__NAMES, oldNames, newNames);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNames(RoomNames newNames) {
		if (newNames != names) {
			NotificationChain msgs = null;
			if (names != null)
				msgs = ((InternalEObject)names).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - SitePackage.ROOM__NAMES, null, msgs);
			if (newNames != null)
				msgs = ((InternalEObject)newNames).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - SitePackage.ROOM__NAMES, null, msgs);
			msgs = basicSetNames(newNames, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SitePackage.ROOM__NAMES, newNames, newNames));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getFloorID() {
		return floorID;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFloorID(String newFloorID) {
		String oldFloorID = floorID;
		floorID = newFloorID;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SitePackage.ROOM__FLOOR_ID, oldFloorID, floorID));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Window> getWindows() {
		if (windows == null) {
			windows = new EObjectContainmentEList<Window>(Window.class, this, SitePackage.ROOM__WINDOWS);
		}
		return windows;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SitePackage.ROOM__NAMES:
				return basicSetNames(null, msgs);
			case SitePackage.ROOM__WINDOWS:
				return ((InternalEList<?>)getWindows()).basicRemove(otherEnd, msgs);
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
			case SitePackage.ROOM__NAMES:
				return getNames();
			case SitePackage.ROOM__FLOOR_ID:
				return getFloorID();
			case SitePackage.ROOM__WINDOWS:
				return getWindows();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case SitePackage.ROOM__NAMES:
				setNames((RoomNames)newValue);
				return;
			case SitePackage.ROOM__FLOOR_ID:
				setFloorID((String)newValue);
				return;
			case SitePackage.ROOM__WINDOWS:
				getWindows().clear();
				getWindows().addAll((Collection<? extends Window>)newValue);
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
			case SitePackage.ROOM__NAMES:
				setNames((RoomNames)null);
				return;
			case SitePackage.ROOM__FLOOR_ID:
				setFloorID(FLOOR_ID_EDEFAULT);
				return;
			case SitePackage.ROOM__WINDOWS:
				getWindows().clear();
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
			case SitePackage.ROOM__NAMES:
				return names != null;
			case SitePackage.ROOM__FLOOR_ID:
				return FLOOR_ID_EDEFAULT == null ? floorID != null : !FLOOR_ID_EDEFAULT.equals(floorID);
			case SitePackage.ROOM__WINDOWS:
				return windows != null && !windows.isEmpty();
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
		result.append(" (floorID: ");
		result.append(floorID);
		result.append(')');
		return result.toString();
	}

} //RoomImpl
