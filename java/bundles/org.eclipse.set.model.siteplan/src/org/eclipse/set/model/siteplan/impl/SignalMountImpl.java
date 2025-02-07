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
import org.eclipse.set.model.siteplan.RouteLocation;
import org.eclipse.set.model.siteplan.RouteObject;
import org.eclipse.set.model.siteplan.Signal;
import org.eclipse.set.model.siteplan.SignalMount;
import org.eclipse.set.model.siteplan.SignalMountType;
import org.eclipse.set.model.siteplan.SiteplanPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Signal
 * Mount</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.set.model.siteplan.impl.SignalMountImpl#getRouteLocations
 * <em>Route Locations</em>}</li>
 * <li>{@link org.eclipse.set.model.siteplan.impl.SignalMountImpl#getAttachedSignals
 * <em>Attached Signals</em>}</li>
 * <li>{@link org.eclipse.set.model.siteplan.impl.SignalMountImpl#getMountType
 * <em>Mount Type</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SignalMountImpl extends PositionedObjectImpl
		implements SignalMount {
	/**
	 * The cached value of the '{@link #getRouteLocations() <em>Route
	 * Locations</em>}' containment reference list. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getRouteLocations()
	 * @generated
	 * @ordered
	 */
	protected EList<RouteLocation> routeLocations;

	/**
	 * The cached value of the '{@link #getAttachedSignals() <em>Attached
	 * Signals</em>}' containment reference list. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getAttachedSignals()
	 * @generated
	 * @ordered
	 */
	protected EList<Signal> attachedSignals;

	/**
	 * The default value of the '{@link #getMountType() <em>Mount Type</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getMountType()
	 * @generated
	 * @ordered
	 */
	protected static final SignalMountType MOUNT_TYPE_EDEFAULT = SignalMountType.MAST;

	/**
	 * The cached value of the '{@link #getMountType() <em>Mount Type</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getMountType()
	 * @generated
	 * @ordered
	 */
	protected SignalMountType mountType = MOUNT_TYPE_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected SignalMountImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SiteplanPackage.Literals.SIGNAL_MOUNT;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<RouteLocation> getRouteLocations() {
		if (routeLocations == null) {
			routeLocations = new EObjectContainmentEList<RouteLocation>(
					RouteLocation.class, this,
					SiteplanPackage.SIGNAL_MOUNT__ROUTE_LOCATIONS);
		}
		return routeLocations;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<Signal> getAttachedSignals() {
		if (attachedSignals == null) {
			attachedSignals = new EObjectContainmentEList<Signal>(Signal.class,
					this, SiteplanPackage.SIGNAL_MOUNT__ATTACHED_SIGNALS);
		}
		return attachedSignals;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public SignalMountType getMountType() {
		return mountType;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setMountType(SignalMountType newMountType) {
		SignalMountType oldMountType = mountType;
		mountType = newMountType == null ? MOUNT_TYPE_EDEFAULT : newMountType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					SiteplanPackage.SIGNAL_MOUNT__MOUNT_TYPE, oldMountType,
					mountType));
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
			case SiteplanPackage.SIGNAL_MOUNT__ROUTE_LOCATIONS:
				return ((InternalEList<?>) getRouteLocations())
						.basicRemove(otherEnd, msgs);
			case SiteplanPackage.SIGNAL_MOUNT__ATTACHED_SIGNALS:
				return ((InternalEList<?>) getAttachedSignals())
						.basicRemove(otherEnd, msgs);
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
			case SiteplanPackage.SIGNAL_MOUNT__ROUTE_LOCATIONS:
				return getRouteLocations();
			case SiteplanPackage.SIGNAL_MOUNT__ATTACHED_SIGNALS:
				return getAttachedSignals();
			case SiteplanPackage.SIGNAL_MOUNT__MOUNT_TYPE:
				return getMountType();
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
			case SiteplanPackage.SIGNAL_MOUNT__ROUTE_LOCATIONS:
				getRouteLocations().clear();
				getRouteLocations()
						.addAll((Collection<? extends RouteLocation>) newValue);
				return;
			case SiteplanPackage.SIGNAL_MOUNT__ATTACHED_SIGNALS:
				getAttachedSignals().clear();
				getAttachedSignals()
						.addAll((Collection<? extends Signal>) newValue);
				return;
			case SiteplanPackage.SIGNAL_MOUNT__MOUNT_TYPE:
				setMountType((SignalMountType) newValue);
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
			case SiteplanPackage.SIGNAL_MOUNT__ROUTE_LOCATIONS:
				getRouteLocations().clear();
				return;
			case SiteplanPackage.SIGNAL_MOUNT__ATTACHED_SIGNALS:
				getAttachedSignals().clear();
				return;
			case SiteplanPackage.SIGNAL_MOUNT__MOUNT_TYPE:
				setMountType(MOUNT_TYPE_EDEFAULT);
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
			case SiteplanPackage.SIGNAL_MOUNT__ROUTE_LOCATIONS:
				return routeLocations != null && !routeLocations.isEmpty();
			case SiteplanPackage.SIGNAL_MOUNT__ATTACHED_SIGNALS:
				return attachedSignals != null && !attachedSignals.isEmpty();
			case SiteplanPackage.SIGNAL_MOUNT__MOUNT_TYPE:
				return mountType != MOUNT_TYPE_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID,
			Class<?> baseClass) {
		if (baseClass == RouteObject.class) {
			switch (derivedFeatureID) {
				case SiteplanPackage.SIGNAL_MOUNT__ROUTE_LOCATIONS:
					return SiteplanPackage.ROUTE_OBJECT__ROUTE_LOCATIONS;
				default:
					return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID,
			Class<?> baseClass) {
		if (baseClass == RouteObject.class) {
			switch (baseFeatureID) {
				case SiteplanPackage.ROUTE_OBJECT__ROUTE_LOCATIONS:
					return SiteplanPackage.SIGNAL_MOUNT__ROUTE_LOCATIONS;
				default:
					return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
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
		result.append(" (mountType: ");
		result.append(mountType);
		result.append(')');
		return result.toString();
	}

} // SignalMountImpl
