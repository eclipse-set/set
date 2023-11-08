/**
 * Copyright (c) 2021 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.siteplan.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.set.model.siteplan.LeftRight;
import org.eclipse.set.model.siteplan.Position;
import org.eclipse.set.model.siteplan.SiteplanPackage;
import org.eclipse.set.model.siteplan.TrackLockComponent;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Track Lock Component</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.set.model.siteplan.impl.TrackLockComponentImpl#getPosition <em>Position</em>}</li>
 *   <li>{@link org.eclipse.set.model.siteplan.impl.TrackLockComponentImpl#getTrackLockSignal <em>Track Lock Signal</em>}</li>
 *   <li>{@link org.eclipse.set.model.siteplan.impl.TrackLockComponentImpl#getEjectionDirection <em>Ejection Direction</em>}</li>
 *   <li>{@link org.eclipse.set.model.siteplan.impl.TrackLockComponentImpl#getGuid <em>Guid</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TrackLockComponentImpl extends MinimalEObjectImpl.Container implements TrackLockComponent {
	/**
	 * The cached value of the '{@link #getPosition() <em>Position</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPosition()
	 * @generated
	 * @ordered
	 */
	protected Position position;

	/**
	 * The default value of the '{@link #getTrackLockSignal() <em>Track Lock Signal</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTrackLockSignal()
	 * @generated
	 * @ordered
	 */
	protected static final String TRACK_LOCK_SIGNAL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTrackLockSignal() <em>Track Lock Signal</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTrackLockSignal()
	 * @generated
	 * @ordered
	 */
	protected String trackLockSignal = TRACK_LOCK_SIGNAL_EDEFAULT;

	/**
	 * The default value of the '{@link #getEjectionDirection() <em>Ejection Direction</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEjectionDirection()
	 * @generated
	 * @ordered
	 */
	protected static final LeftRight EJECTION_DIRECTION_EDEFAULT = LeftRight.LEFT;

	/**
	 * The cached value of the '{@link #getEjectionDirection() <em>Ejection Direction</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEjectionDirection()
	 * @generated
	 * @ordered
	 */
	protected LeftRight ejectionDirection = EJECTION_DIRECTION_EDEFAULT;

	/**
	 * The default value of the '{@link #getGuid() <em>Guid</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGuid()
	 * @generated
	 * @ordered
	 */
	protected static final String GUID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getGuid() <em>Guid</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGuid()
	 * @generated
	 * @ordered
	 */
	protected String guid = GUID_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TrackLockComponentImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SiteplanPackage.Literals.TRACK_LOCK_COMPONENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Position getPosition() {
		return position;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPosition(Position newPosition, NotificationChain msgs) {
		Position oldPosition = position;
		position = newPosition;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SiteplanPackage.TRACK_LOCK_COMPONENT__POSITION, oldPosition, newPosition);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setPosition(Position newPosition) {
		if (newPosition != position) {
			NotificationChain msgs = null;
			if (position != null)
				msgs = ((InternalEObject)position).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - SiteplanPackage.TRACK_LOCK_COMPONENT__POSITION, null, msgs);
			if (newPosition != null)
				msgs = ((InternalEObject)newPosition).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - SiteplanPackage.TRACK_LOCK_COMPONENT__POSITION, null, msgs);
			msgs = basicSetPosition(newPosition, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SiteplanPackage.TRACK_LOCK_COMPONENT__POSITION, newPosition, newPosition));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getTrackLockSignal() {
		return trackLockSignal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setTrackLockSignal(String newTrackLockSignal) {
		String oldTrackLockSignal = trackLockSignal;
		trackLockSignal = newTrackLockSignal;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SiteplanPackage.TRACK_LOCK_COMPONENT__TRACK_LOCK_SIGNAL, oldTrackLockSignal, trackLockSignal));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public LeftRight getEjectionDirection() {
		return ejectionDirection;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setEjectionDirection(LeftRight newEjectionDirection) {
		LeftRight oldEjectionDirection = ejectionDirection;
		ejectionDirection = newEjectionDirection == null ? EJECTION_DIRECTION_EDEFAULT : newEjectionDirection;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SiteplanPackage.TRACK_LOCK_COMPONENT__EJECTION_DIRECTION, oldEjectionDirection, ejectionDirection));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getGuid() {
		return guid;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setGuid(String newGuid) {
		String oldGuid = guid;
		guid = newGuid;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SiteplanPackage.TRACK_LOCK_COMPONENT__GUID, oldGuid, guid));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SiteplanPackage.TRACK_LOCK_COMPONENT__POSITION:
				return basicSetPosition(null, msgs);
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
			case SiteplanPackage.TRACK_LOCK_COMPONENT__POSITION:
				return getPosition();
			case SiteplanPackage.TRACK_LOCK_COMPONENT__TRACK_LOCK_SIGNAL:
				return getTrackLockSignal();
			case SiteplanPackage.TRACK_LOCK_COMPONENT__EJECTION_DIRECTION:
				return getEjectionDirection();
			case SiteplanPackage.TRACK_LOCK_COMPONENT__GUID:
				return getGuid();
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
			case SiteplanPackage.TRACK_LOCK_COMPONENT__POSITION:
				setPosition((Position)newValue);
				return;
			case SiteplanPackage.TRACK_LOCK_COMPONENT__TRACK_LOCK_SIGNAL:
				setTrackLockSignal((String)newValue);
				return;
			case SiteplanPackage.TRACK_LOCK_COMPONENT__EJECTION_DIRECTION:
				setEjectionDirection((LeftRight)newValue);
				return;
			case SiteplanPackage.TRACK_LOCK_COMPONENT__GUID:
				setGuid((String)newValue);
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
			case SiteplanPackage.TRACK_LOCK_COMPONENT__POSITION:
				setPosition((Position)null);
				return;
			case SiteplanPackage.TRACK_LOCK_COMPONENT__TRACK_LOCK_SIGNAL:
				setTrackLockSignal(TRACK_LOCK_SIGNAL_EDEFAULT);
				return;
			case SiteplanPackage.TRACK_LOCK_COMPONENT__EJECTION_DIRECTION:
				setEjectionDirection(EJECTION_DIRECTION_EDEFAULT);
				return;
			case SiteplanPackage.TRACK_LOCK_COMPONENT__GUID:
				setGuid(GUID_EDEFAULT);
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
			case SiteplanPackage.TRACK_LOCK_COMPONENT__POSITION:
				return position != null;
			case SiteplanPackage.TRACK_LOCK_COMPONENT__TRACK_LOCK_SIGNAL:
				return TRACK_LOCK_SIGNAL_EDEFAULT == null ? trackLockSignal != null : !TRACK_LOCK_SIGNAL_EDEFAULT.equals(trackLockSignal);
			case SiteplanPackage.TRACK_LOCK_COMPONENT__EJECTION_DIRECTION:
				return ejectionDirection != EJECTION_DIRECTION_EDEFAULT;
			case SiteplanPackage.TRACK_LOCK_COMPONENT__GUID:
				return GUID_EDEFAULT == null ? guid != null : !GUID_EDEFAULT.equals(guid);
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
		result.append(" (trackLockSignal: ");
		result.append(trackLockSignal);
		result.append(", ejectionDirection: ");
		result.append(ejectionDirection);
		result.append(", guid: ");
		result.append(guid);
		result.append(')');
		return result.toString();
	}

} //TrackLockComponentImpl
