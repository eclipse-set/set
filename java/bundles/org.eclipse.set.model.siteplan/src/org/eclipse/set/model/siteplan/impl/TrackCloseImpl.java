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
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.set.model.siteplan.SiteplanPackage;
import org.eclipse.set.model.siteplan.TrackClose;
import org.eclipse.set.model.siteplan.TrackCloseType;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Track
 * Close</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.set.model.siteplan.impl.TrackCloseImpl#getTrackCloseType <em>Track Close Type</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TrackCloseImpl extends PositionedObjectImpl implements TrackClose {
	/**
	 * The default value of the '{@link #getTrackCloseType() <em>Track Close Type</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getTrackCloseType()
	 * @generated
	 * @ordered
	 */
	protected static final TrackCloseType TRACK_CLOSE_TYPE_EDEFAULT = TrackCloseType.FRICTION_BUFFER_STOP;

	/**
	 * The cached value of the '{@link #getTrackCloseType() <em>Track Close Type</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getTrackCloseType()
	 * @generated
	 * @ordered
	 */
	protected TrackCloseType trackCloseType = TRACK_CLOSE_TYPE_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected TrackCloseImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SiteplanPackage.Literals.TRACK_CLOSE;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TrackCloseType getTrackCloseType() {
		return trackCloseType;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setTrackCloseType(TrackCloseType newTrackCloseType) {
		TrackCloseType oldTrackCloseType = trackCloseType;
		trackCloseType = newTrackCloseType == null ? TRACK_CLOSE_TYPE_EDEFAULT : newTrackCloseType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SiteplanPackage.TRACK_CLOSE__TRACK_CLOSE_TYPE, oldTrackCloseType, trackCloseType));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case SiteplanPackage.TRACK_CLOSE__TRACK_CLOSE_TYPE:
				return getTrackCloseType();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case SiteplanPackage.TRACK_CLOSE__TRACK_CLOSE_TYPE:
				setTrackCloseType((TrackCloseType)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case SiteplanPackage.TRACK_CLOSE__TRACK_CLOSE_TYPE:
				setTrackCloseType(TRACK_CLOSE_TYPE_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case SiteplanPackage.TRACK_CLOSE__TRACK_CLOSE_TYPE:
				return trackCloseType != TRACK_CLOSE_TYPE_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (trackCloseType: ");
		result.append(trackCloseType);
		result.append(')');
		return result.toString();
	}

} // TrackCloseImpl
