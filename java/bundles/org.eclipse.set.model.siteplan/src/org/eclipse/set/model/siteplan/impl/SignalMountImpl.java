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

import org.eclipse.set.model.siteplan.Position;
import org.eclipse.set.model.siteplan.Signal;
import org.eclipse.set.model.siteplan.SignalMount;
import org.eclipse.set.model.siteplan.SignalMountType;
import org.eclipse.set.model.siteplan.SiteplanPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Signal Mount</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.set.model.siteplan.impl.SignalMountImpl#getAttachedSignals <em>Attached Signals</em>}</li>
 *   <li>{@link org.eclipse.set.model.siteplan.impl.SignalMountImpl#getMountType <em>Mount Type</em>}</li>
 *   <li>{@link org.eclipse.set.model.siteplan.impl.SignalMountImpl#getPosition <em>Position</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SignalMountImpl extends SiteplanObjectImpl implements SignalMount {
	/**
	 * The cached value of the '{@link #getAttachedSignals() <em>Attached Signals</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAttachedSignals()
	 * @generated
	 * @ordered
	 */
	protected EList<Signal> attachedSignals;

	/**
	 * The default value of the '{@link #getMountType() <em>Mount Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMountType()
	 * @generated
	 * @ordered
	 */
	protected static final SignalMountType MOUNT_TYPE_EDEFAULT = SignalMountType.MAST;

	/**
	 * The cached value of the '{@link #getMountType() <em>Mount Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMountType()
	 * @generated
	 * @ordered
	 */
	protected SignalMountType mountType = MOUNT_TYPE_EDEFAULT;

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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SignalMountImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SiteplanPackage.Literals.SIGNAL_MOUNT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Signal> getAttachedSignals() {
		if (attachedSignals == null) {
			attachedSignals = new EObjectContainmentEList<Signal>(Signal.class, this, SiteplanPackage.SIGNAL_MOUNT__ATTACHED_SIGNALS);
		}
		return attachedSignals;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SignalMountType getMountType() {
		return mountType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setMountType(SignalMountType newMountType) {
		SignalMountType oldMountType = mountType;
		mountType = newMountType == null ? MOUNT_TYPE_EDEFAULT : newMountType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SiteplanPackage.SIGNAL_MOUNT__MOUNT_TYPE, oldMountType, mountType));
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SiteplanPackage.SIGNAL_MOUNT__POSITION, oldPosition, newPosition);
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
				msgs = ((InternalEObject)position).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - SiteplanPackage.SIGNAL_MOUNT__POSITION, null, msgs);
			if (newPosition != null)
				msgs = ((InternalEObject)newPosition).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - SiteplanPackage.SIGNAL_MOUNT__POSITION, null, msgs);
			msgs = basicSetPosition(newPosition, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SiteplanPackage.SIGNAL_MOUNT__POSITION, newPosition, newPosition));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SiteplanPackage.SIGNAL_MOUNT__ATTACHED_SIGNALS:
				return ((InternalEList<?>)getAttachedSignals()).basicRemove(otherEnd, msgs);
			case SiteplanPackage.SIGNAL_MOUNT__POSITION:
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
			case SiteplanPackage.SIGNAL_MOUNT__ATTACHED_SIGNALS:
				return getAttachedSignals();
			case SiteplanPackage.SIGNAL_MOUNT__MOUNT_TYPE:
				return getMountType();
			case SiteplanPackage.SIGNAL_MOUNT__POSITION:
				return getPosition();
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
			case SiteplanPackage.SIGNAL_MOUNT__ATTACHED_SIGNALS:
				getAttachedSignals().clear();
				getAttachedSignals().addAll((Collection<? extends Signal>)newValue);
				return;
			case SiteplanPackage.SIGNAL_MOUNT__MOUNT_TYPE:
				setMountType((SignalMountType)newValue);
				return;
			case SiteplanPackage.SIGNAL_MOUNT__POSITION:
				setPosition((Position)newValue);
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
			case SiteplanPackage.SIGNAL_MOUNT__ATTACHED_SIGNALS:
				getAttachedSignals().clear();
				return;
			case SiteplanPackage.SIGNAL_MOUNT__MOUNT_TYPE:
				setMountType(MOUNT_TYPE_EDEFAULT);
				return;
			case SiteplanPackage.SIGNAL_MOUNT__POSITION:
				setPosition((Position)null);
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
			case SiteplanPackage.SIGNAL_MOUNT__ATTACHED_SIGNALS:
				return attachedSignals != null && !attachedSignals.isEmpty();
			case SiteplanPackage.SIGNAL_MOUNT__MOUNT_TYPE:
				return mountType != MOUNT_TYPE_EDEFAULT;
			case SiteplanPackage.SIGNAL_MOUNT__POSITION:
				return position != null;
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
		result.append(" (mountType: ");
		result.append(mountType);
		result.append(')');
		return result.toString();
	}

} //SignalMountImpl
