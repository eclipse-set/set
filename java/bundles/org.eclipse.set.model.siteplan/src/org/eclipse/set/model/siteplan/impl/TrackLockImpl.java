/**
 * Copyright (c) 2021 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.siteplan.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.set.model.siteplan.Label;
import org.eclipse.set.model.siteplan.SiteplanPackage;
import org.eclipse.set.model.siteplan.TrackLock;
import org.eclipse.set.model.siteplan.TrackLockComponent;
import org.eclipse.set.model.siteplan.TrackLockLocation;
import org.eclipse.set.model.siteplan.TurnoutOperatingMode;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Track
 * Lock</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.set.model.siteplan.impl.TrackLockImpl#getComponents
 * <em>Components</em>}</li>
 * <li>{@link org.eclipse.set.model.siteplan.impl.TrackLockImpl#getPreferredLocation
 * <em>Preferred Location</em>}</li>
 * <li>{@link org.eclipse.set.model.siteplan.impl.TrackLockImpl#getOperatingMode
 * <em>Operating Mode</em>}</li>
 * <li>{@link org.eclipse.set.model.siteplan.impl.TrackLockImpl#getLabel
 * <em>Label</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TrackLockImpl extends SiteplanObjectImpl implements TrackLock {
	/**
	 * The cached value of the '{@link #getComponents() <em>Components</em>}'
	 * containment reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getComponents()
	 * @generated
	 * @ordered
	 */
	protected EList<TrackLockComponent> components;

	/**
	 * The default value of the '{@link #getPreferredLocation() <em>Preferred
	 * Location</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getPreferredLocation()
	 * @generated
	 * @ordered
	 */
	protected static final TrackLockLocation PREFERRED_LOCATION_EDEFAULT = TrackLockLocation.BESIDE_TRACK;

	/**
	 * The cached value of the '{@link #getPreferredLocation() <em>Preferred
	 * Location</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getPreferredLocation()
	 * @generated
	 * @ordered
	 */
	protected TrackLockLocation preferredLocation = PREFERRED_LOCATION_EDEFAULT;

	/**
	 * The default value of the '{@link #getOperatingMode() <em>Operating
	 * Mode</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getOperatingMode()
	 * @generated
	 * @ordered
	 */
	protected static final TurnoutOperatingMode OPERATING_MODE_EDEFAULT = TurnoutOperatingMode.UNDEFINED;

	/**
	 * The cached value of the '{@link #getOperatingMode() <em>Operating
	 * Mode</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getOperatingMode()
	 * @generated
	 * @ordered
	 */
	protected TurnoutOperatingMode operatingMode = OPERATING_MODE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getLabel() <em>Label</em>}' containment
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getLabel()
	 * @generated
	 * @ordered
	 */
	protected Label label;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected TrackLockImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SiteplanPackage.Literals.TRACK_LOCK;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<TrackLockComponent> getComponents() {
		if (components == null) {
			components = new EObjectContainmentEList<TrackLockComponent>(
					TrackLockComponent.class, this,
					SiteplanPackage.TRACK_LOCK__COMPONENTS);
		}
		return components;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public TrackLockLocation getPreferredLocation() {
		return preferredLocation;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setPreferredLocation(TrackLockLocation newPreferredLocation) {
		TrackLockLocation oldPreferredLocation = preferredLocation;
		preferredLocation = newPreferredLocation == null
				? PREFERRED_LOCATION_EDEFAULT
				: newPreferredLocation;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					SiteplanPackage.TRACK_LOCK__PREFERRED_LOCATION,
					oldPreferredLocation, preferredLocation));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public TurnoutOperatingMode getOperatingMode() {
		return operatingMode;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setOperatingMode(TurnoutOperatingMode newOperatingMode) {
		TurnoutOperatingMode oldOperatingMode = operatingMode;
		operatingMode = newOperatingMode == null ? OPERATING_MODE_EDEFAULT
				: newOperatingMode;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					SiteplanPackage.TRACK_LOCK__OPERATING_MODE,
					oldOperatingMode, operatingMode));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Label getLabel() {
		return label;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetLabel(Label newLabel,
			NotificationChain msgs) {
		Label oldLabel = label;
		label = newLabel;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this,
					Notification.SET, SiteplanPackage.TRACK_LOCK__LABEL,
					oldLabel, newLabel);
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
	public void setLabel(Label newLabel) {
		if (newLabel != label) {
			NotificationChain msgs = null;
			if (label != null)
				msgs = ((InternalEObject) label).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE
								- SiteplanPackage.TRACK_LOCK__LABEL,
						null, msgs);
			if (newLabel != null)
				msgs = ((InternalEObject) newLabel).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE
								- SiteplanPackage.TRACK_LOCK__LABEL,
						null, msgs);
			msgs = basicSetLabel(newLabel, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					SiteplanPackage.TRACK_LOCK__LABEL, newLabel, newLabel));
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
			case SiteplanPackage.TRACK_LOCK__COMPONENTS:
				return ((InternalEList<?>) getComponents())
						.basicRemove(otherEnd, msgs);
			case SiteplanPackage.TRACK_LOCK__LABEL:
				return basicSetLabel(null, msgs);
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
			case SiteplanPackage.TRACK_LOCK__COMPONENTS:
				return getComponents();
			case SiteplanPackage.TRACK_LOCK__PREFERRED_LOCATION:
				return getPreferredLocation();
			case SiteplanPackage.TRACK_LOCK__OPERATING_MODE:
				return getOperatingMode();
			case SiteplanPackage.TRACK_LOCK__LABEL:
				return getLabel();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case SiteplanPackage.TRACK_LOCK__COMPONENTS:
				getComponents().clear();
				getComponents().addAll(
						(Collection<? extends TrackLockComponent>) newValue);
				return;
			case SiteplanPackage.TRACK_LOCK__PREFERRED_LOCATION:
				setPreferredLocation((TrackLockLocation) newValue);
				return;
			case SiteplanPackage.TRACK_LOCK__OPERATING_MODE:
				setOperatingMode((TurnoutOperatingMode) newValue);
				return;
			case SiteplanPackage.TRACK_LOCK__LABEL:
				setLabel((Label) newValue);
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
			case SiteplanPackage.TRACK_LOCK__COMPONENTS:
				getComponents().clear();
				return;
			case SiteplanPackage.TRACK_LOCK__PREFERRED_LOCATION:
				setPreferredLocation(PREFERRED_LOCATION_EDEFAULT);
				return;
			case SiteplanPackage.TRACK_LOCK__OPERATING_MODE:
				setOperatingMode(OPERATING_MODE_EDEFAULT);
				return;
			case SiteplanPackage.TRACK_LOCK__LABEL:
				setLabel((Label) null);
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
			case SiteplanPackage.TRACK_LOCK__COMPONENTS:
				return components != null && !components.isEmpty();
			case SiteplanPackage.TRACK_LOCK__PREFERRED_LOCATION:
				return preferredLocation != PREFERRED_LOCATION_EDEFAULT;
			case SiteplanPackage.TRACK_LOCK__OPERATING_MODE:
				return operatingMode != OPERATING_MODE_EDEFAULT;
			case SiteplanPackage.TRACK_LOCK__LABEL:
				return label != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy())
			return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (preferredLocation: ");
		result.append(preferredLocation);
		result.append(", operatingMode: ");
		result.append(operatingMode);
		result.append(')');
		return result.toString();
	}

} // TrackLockImpl
