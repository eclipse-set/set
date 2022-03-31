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

import org.eclipse.set.model.test.site.Passage;
import org.eclipse.set.model.test.site.SitePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Passage</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.set.model.test.site.impl.PassageImpl#getRoomID_A <em>Room ID A</em>}</li>
 *   <li>{@link org.eclipse.set.model.test.site.impl.PassageImpl#getRoomID_B <em>Room ID B</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PassageImpl extends IdentifiedImpl implements Passage {
	/**
	 * The default value of the '{@link #getRoomID_A() <em>Room ID A</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRoomID_A()
	 * @generated
	 * @ordered
	 */
	protected static final String ROOM_ID_A_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getRoomID_A() <em>Room ID A</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRoomID_A()
	 * @generated
	 * @ordered
	 */
	protected String roomID_A = ROOM_ID_A_EDEFAULT;

	/**
	 * The default value of the '{@link #getRoomID_B() <em>Room ID B</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRoomID_B()
	 * @generated
	 * @ordered
	 */
	protected static final String ROOM_ID_B_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getRoomID_B() <em>Room ID B</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRoomID_B()
	 * @generated
	 * @ordered
	 */
	protected String roomID_B = ROOM_ID_B_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PassageImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SitePackage.Literals.PASSAGE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getRoomID_A() {
		return roomID_A;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRoomID_A(String newRoomID_A) {
		String oldRoomID_A = roomID_A;
		roomID_A = newRoomID_A;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SitePackage.PASSAGE__ROOM_ID_A, oldRoomID_A, roomID_A));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getRoomID_B() {
		return roomID_B;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRoomID_B(String newRoomID_B) {
		String oldRoomID_B = roomID_B;
		roomID_B = newRoomID_B;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SitePackage.PASSAGE__ROOM_ID_B, oldRoomID_B, roomID_B));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case SitePackage.PASSAGE__ROOM_ID_A:
				return getRoomID_A();
			case SitePackage.PASSAGE__ROOM_ID_B:
				return getRoomID_B();
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
			case SitePackage.PASSAGE__ROOM_ID_A:
				setRoomID_A((String)newValue);
				return;
			case SitePackage.PASSAGE__ROOM_ID_B:
				setRoomID_B((String)newValue);
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
			case SitePackage.PASSAGE__ROOM_ID_A:
				setRoomID_A(ROOM_ID_A_EDEFAULT);
				return;
			case SitePackage.PASSAGE__ROOM_ID_B:
				setRoomID_B(ROOM_ID_B_EDEFAULT);
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
			case SitePackage.PASSAGE__ROOM_ID_A:
				return ROOM_ID_A_EDEFAULT == null ? roomID_A != null : !ROOM_ID_A_EDEFAULT.equals(roomID_A);
			case SitePackage.PASSAGE__ROOM_ID_B:
				return ROOM_ID_B_EDEFAULT == null ? roomID_B != null : !ROOM_ID_B_EDEFAULT.equals(roomID_B);
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
		result.append(" (roomID_A: ");
		result.append(roomID_A);
		result.append(", roomID_B: ");
		result.append(roomID_B);
		result.append(')');
		return result.toString();
	}

} //PassageImpl
