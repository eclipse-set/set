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

import org.eclipse.set.model.siteplan.Coordinate;
import org.eclipse.set.model.siteplan.SiteplanPackage;
import org.eclipse.set.model.siteplan.TrackSwitchEndMarker;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Track
 * Switch End Marker</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.set.model.siteplan.impl.TrackSwitchEndMarkerImpl#getLegACoordinate
 * <em>Leg ACoordinate</em>}</li>
 * <li>{@link org.eclipse.set.model.siteplan.impl.TrackSwitchEndMarkerImpl#getLegBCoordinate
 * <em>Leg BCoordinate</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TrackSwitchEndMarkerImpl extends MinimalEObjectImpl.Container
		implements TrackSwitchEndMarker {
	/**
	 * The cached value of the '{@link #getLegACoordinate() <em>Leg
	 * ACoordinate</em>}' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getLegACoordinate()
	 * @generated
	 * @ordered
	 */
	protected Coordinate legACoordinate;

	/**
	 * The cached value of the '{@link #getLegBCoordinate() <em>Leg
	 * BCoordinate</em>}' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getLegBCoordinate()
	 * @generated
	 * @ordered
	 */
	protected Coordinate legBCoordinate;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected TrackSwitchEndMarkerImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SiteplanPackage.Literals.TRACK_SWITCH_END_MARKER;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Coordinate getLegACoordinate() {
		return legACoordinate;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetLegACoordinate(
			Coordinate newLegACoordinate, NotificationChain msgs) {
		Coordinate oldLegACoordinate = legACoordinate;
		legACoordinate = newLegACoordinate;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this,
					Notification.SET,
					SiteplanPackage.TRACK_SWITCH_END_MARKER__LEG_ACOORDINATE,
					oldLegACoordinate, newLegACoordinate);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setLegACoordinate(Coordinate newLegACoordinate) {
		if (newLegACoordinate != legACoordinate) {
			NotificationChain msgs = null;
			if (legACoordinate != null)
				msgs = ((InternalEObject) legACoordinate).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE
								- SiteplanPackage.TRACK_SWITCH_END_MARKER__LEG_ACOORDINATE,
						null, msgs);
			if (newLegACoordinate != null)
				msgs = ((InternalEObject) newLegACoordinate).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE
								- SiteplanPackage.TRACK_SWITCH_END_MARKER__LEG_ACOORDINATE,
						null, msgs);
			msgs = basicSetLegACoordinate(newLegACoordinate, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					SiteplanPackage.TRACK_SWITCH_END_MARKER__LEG_ACOORDINATE,
					newLegACoordinate, newLegACoordinate));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Coordinate getLegBCoordinate() {
		return legBCoordinate;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetLegBCoordinate(
			Coordinate newLegBCoordinate, NotificationChain msgs) {
		Coordinate oldLegBCoordinate = legBCoordinate;
		legBCoordinate = newLegBCoordinate;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this,
					Notification.SET,
					SiteplanPackage.TRACK_SWITCH_END_MARKER__LEG_BCOORDINATE,
					oldLegBCoordinate, newLegBCoordinate);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setLegBCoordinate(Coordinate newLegBCoordinate) {
		if (newLegBCoordinate != legBCoordinate) {
			NotificationChain msgs = null;
			if (legBCoordinate != null)
				msgs = ((InternalEObject) legBCoordinate).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE
								- SiteplanPackage.TRACK_SWITCH_END_MARKER__LEG_BCOORDINATE,
						null, msgs);
			if (newLegBCoordinate != null)
				msgs = ((InternalEObject) newLegBCoordinate).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE
								- SiteplanPackage.TRACK_SWITCH_END_MARKER__LEG_BCOORDINATE,
						null, msgs);
			msgs = basicSetLegBCoordinate(newLegBCoordinate, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					SiteplanPackage.TRACK_SWITCH_END_MARKER__LEG_BCOORDINATE,
					newLegBCoordinate, newLegBCoordinate));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd,
			int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SiteplanPackage.TRACK_SWITCH_END_MARKER__LEG_ACOORDINATE:
				return basicSetLegACoordinate(null, msgs);
			case SiteplanPackage.TRACK_SWITCH_END_MARKER__LEG_BCOORDINATE:
				return basicSetLegBCoordinate(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case SiteplanPackage.TRACK_SWITCH_END_MARKER__LEG_ACOORDINATE:
				return getLegACoordinate();
			case SiteplanPackage.TRACK_SWITCH_END_MARKER__LEG_BCOORDINATE:
				return getLegBCoordinate();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case SiteplanPackage.TRACK_SWITCH_END_MARKER__LEG_ACOORDINATE:
				setLegACoordinate((Coordinate) newValue);
				return;
			case SiteplanPackage.TRACK_SWITCH_END_MARKER__LEG_BCOORDINATE:
				setLegBCoordinate((Coordinate) newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case SiteplanPackage.TRACK_SWITCH_END_MARKER__LEG_ACOORDINATE:
				setLegACoordinate((Coordinate) null);
				return;
			case SiteplanPackage.TRACK_SWITCH_END_MARKER__LEG_BCOORDINATE:
				setLegBCoordinate((Coordinate) null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case SiteplanPackage.TRACK_SWITCH_END_MARKER__LEG_ACOORDINATE:
				return legACoordinate != null;
			case SiteplanPackage.TRACK_SWITCH_END_MARKER__LEG_BCOORDINATE:
				return legBCoordinate != null;
		}
		return super.eIsSet(featureID);
	}

} // TrackSwitchEndMarkerImpl
