/**
 * Copyright (c) 2021 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.siteplan.impl;

import java.math.BigDecimal;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.set.model.siteplan.Direction;
import org.eclipse.set.model.siteplan.Label;
import org.eclipse.set.model.siteplan.Position;
import org.eclipse.set.model.siteplan.Signal;
import org.eclipse.set.model.siteplan.SignalRole;
import org.eclipse.set.model.siteplan.SignalScreen;
import org.eclipse.set.model.siteplan.SignalSystem;
import org.eclipse.set.model.siteplan.SiteplanPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object
 * '<em><b>Signal</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.set.model.siteplan.impl.SignalImpl#getRole <em>Role</em>}</li>
 *   <li>{@link org.eclipse.set.model.siteplan.impl.SignalImpl#getSystem <em>System</em>}</li>
 *   <li>{@link org.eclipse.set.model.siteplan.impl.SignalImpl#getScreen <em>Screen</em>}</li>
 *   <li>{@link org.eclipse.set.model.siteplan.impl.SignalImpl#getLabel <em>Label</em>}</li>
 *   <li>{@link org.eclipse.set.model.siteplan.impl.SignalImpl#getLateralDistance <em>Lateral Distance</em>}</li>
 *   <li>{@link org.eclipse.set.model.siteplan.impl.SignalImpl#getSignalDirection <em>Signal Direction</em>}</li>
 *   <li>{@link org.eclipse.set.model.siteplan.impl.SignalImpl#getMountPosition <em>Mount Position</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SignalImpl extends RouteObjectImpl implements Signal {
	/**
	 * The default value of the '{@link #getRole() <em>Role</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getRole()
	 * @generated
	 * @ordered
	 */
	protected static final SignalRole ROLE_EDEFAULT = SignalRole.MULTI_SECTION;

	/**
	 * The cached value of the '{@link #getRole() <em>Role</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getRole()
	 * @generated
	 * @ordered
	 */
	protected SignalRole role = ROLE_EDEFAULT;

	/**
	 * The default value of the '{@link #getSystem() <em>System</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getSystem()
	 * @generated
	 * @ordered
	 */
	protected static final SignalSystem SYSTEM_EDEFAULT = SignalSystem.HL;

	/**
	 * The cached value of the '{@link #getSystem() <em>System</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getSystem()
	 * @generated
	 * @ordered
	 */
	protected SignalSystem system = SYSTEM_EDEFAULT;

	/**
	 * The cached value of the '{@link #getScreen() <em>Screen</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getScreen()
	 * @generated
	 * @ordered
	 */
	protected EList<SignalScreen> screen;

	/**
	 * The cached value of the '{@link #getLabel() <em>Label</em>}' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getLabel()
	 * @generated
	 * @ordered
	 */
	protected Label label;

	/**
	 * The cached value of the '{@link #getLateralDistance() <em>Lateral
	 * Distance</em>}' attribute list. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see #getLateralDistance()
	 * @generated
	 * @ordered
	 */
	protected EList<BigDecimal> lateralDistance;

	/**
	 * The default value of the '{@link #getSignalDirection() <em>Signal Direction</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getSignalDirection()
	 * @generated
	 * @ordered
	 */
	protected static final Direction SIGNAL_DIRECTION_EDEFAULT = Direction.FORWARD;

	/**
	 * The cached value of the '{@link #getSignalDirection() <em>Signal Direction</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getSignalDirection()
	 * @generated
	 * @ordered
	 */
	protected Direction signalDirection = SIGNAL_DIRECTION_EDEFAULT;

	/**
	 * The cached value of the '{@link #getMountPosition() <em>Mount Position</em>}' containment reference.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #getMountPosition()
	 * @generated
	 * @ordered
	 */
	protected Position mountPosition;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected SignalImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SiteplanPackage.Literals.SIGNAL;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SignalRole getRole() {
		return role;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setRole(SignalRole newRole) {
		SignalRole oldRole = role;
		role = newRole == null ? ROLE_EDEFAULT : newRole;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SiteplanPackage.SIGNAL__ROLE, oldRole, role));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SignalSystem getSystem() {
		return system;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSystem(SignalSystem newSystem) {
		SignalSystem oldSystem = system;
		system = newSystem == null ? SYSTEM_EDEFAULT : newSystem;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SiteplanPackage.SIGNAL__SYSTEM, oldSystem, system));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<SignalScreen> getScreen() {
		if (screen == null) {
			screen = new EObjectContainmentEList<SignalScreen>(SignalScreen.class, this, SiteplanPackage.SIGNAL__SCREEN);
		}
		return screen;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Label getLabel() {
		return label;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLabel(Label newLabel,
			NotificationChain msgs) {
		Label oldLabel = label;
		label = newLabel;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SiteplanPackage.SIGNAL__LABEL, oldLabel, newLabel);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setLabel(Label newLabel) {
		if (newLabel != label) {
			NotificationChain msgs = null;
			if (label != null)
				msgs = ((InternalEObject)label).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - SiteplanPackage.SIGNAL__LABEL, null, msgs);
			if (newLabel != null)
				msgs = ((InternalEObject)newLabel).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - SiteplanPackage.SIGNAL__LABEL, null, msgs);
			msgs = basicSetLabel(newLabel, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SiteplanPackage.SIGNAL__LABEL, newLabel, newLabel));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<BigDecimal> getLateralDistance() {
		if (lateralDistance == null) {
			lateralDistance = new EDataTypeUniqueEList<BigDecimal>(BigDecimal.class, this, SiteplanPackage.SIGNAL__LATERAL_DISTANCE);
		}
		return lateralDistance;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Direction getSignalDirection() {
		return signalDirection;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSignalDirection(Direction newSignalDirection) {
		Direction oldSignalDirection = signalDirection;
		signalDirection = newSignalDirection == null ? SIGNAL_DIRECTION_EDEFAULT : newSignalDirection;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SiteplanPackage.SIGNAL__SIGNAL_DIRECTION, oldSignalDirection, signalDirection));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Position getMountPosition() {
		return mountPosition;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMountPosition(Position newMountPosition,
			NotificationChain msgs) {
		Position oldMountPosition = mountPosition;
		mountPosition = newMountPosition;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SiteplanPackage.SIGNAL__MOUNT_POSITION, oldMountPosition, newMountPosition);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setMountPosition(Position newMountPosition) {
		if (newMountPosition != mountPosition) {
			NotificationChain msgs = null;
			if (mountPosition != null)
				msgs = ((InternalEObject)mountPosition).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - SiteplanPackage.SIGNAL__MOUNT_POSITION, null, msgs);
			if (newMountPosition != null)
				msgs = ((InternalEObject)newMountPosition).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - SiteplanPackage.SIGNAL__MOUNT_POSITION, null, msgs);
			msgs = basicSetMountPosition(newMountPosition, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SiteplanPackage.SIGNAL__MOUNT_POSITION, newMountPosition, newMountPosition));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd,
			int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SiteplanPackage.SIGNAL__SCREEN:
				return ((InternalEList<?>)getScreen()).basicRemove(otherEnd, msgs);
			case SiteplanPackage.SIGNAL__LABEL:
				return basicSetLabel(null, msgs);
			case SiteplanPackage.SIGNAL__MOUNT_POSITION:
				return basicSetMountPosition(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case SiteplanPackage.SIGNAL__ROLE:
				return getRole();
			case SiteplanPackage.SIGNAL__SYSTEM:
				return getSystem();
			case SiteplanPackage.SIGNAL__SCREEN:
				return getScreen();
			case SiteplanPackage.SIGNAL__LABEL:
				return getLabel();
			case SiteplanPackage.SIGNAL__LATERAL_DISTANCE:
				return getLateralDistance();
			case SiteplanPackage.SIGNAL__SIGNAL_DIRECTION:
				return getSignalDirection();
			case SiteplanPackage.SIGNAL__MOUNT_POSITION:
				return getMountPosition();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case SiteplanPackage.SIGNAL__ROLE:
				setRole((SignalRole)newValue);
				return;
			case SiteplanPackage.SIGNAL__SYSTEM:
				setSystem((SignalSystem)newValue);
				return;
			case SiteplanPackage.SIGNAL__SCREEN:
				getScreen().clear();
				getScreen().addAll((Collection<? extends SignalScreen>)newValue);
				return;
			case SiteplanPackage.SIGNAL__LABEL:
				setLabel((Label)newValue);
				return;
			case SiteplanPackage.SIGNAL__LATERAL_DISTANCE:
				getLateralDistance().clear();
				getLateralDistance().addAll((Collection<? extends BigDecimal>)newValue);
				return;
			case SiteplanPackage.SIGNAL__SIGNAL_DIRECTION:
				setSignalDirection((Direction)newValue);
				return;
			case SiteplanPackage.SIGNAL__MOUNT_POSITION:
				setMountPosition((Position)newValue);
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
			case SiteplanPackage.SIGNAL__ROLE:
				setRole(ROLE_EDEFAULT);
				return;
			case SiteplanPackage.SIGNAL__SYSTEM:
				setSystem(SYSTEM_EDEFAULT);
				return;
			case SiteplanPackage.SIGNAL__SCREEN:
				getScreen().clear();
				return;
			case SiteplanPackage.SIGNAL__LABEL:
				setLabel((Label)null);
				return;
			case SiteplanPackage.SIGNAL__LATERAL_DISTANCE:
				getLateralDistance().clear();
				return;
			case SiteplanPackage.SIGNAL__SIGNAL_DIRECTION:
				setSignalDirection(SIGNAL_DIRECTION_EDEFAULT);
				return;
			case SiteplanPackage.SIGNAL__MOUNT_POSITION:
				setMountPosition((Position)null);
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
			case SiteplanPackage.SIGNAL__ROLE:
				return role != ROLE_EDEFAULT;
			case SiteplanPackage.SIGNAL__SYSTEM:
				return system != SYSTEM_EDEFAULT;
			case SiteplanPackage.SIGNAL__SCREEN:
				return screen != null && !screen.isEmpty();
			case SiteplanPackage.SIGNAL__LABEL:
				return label != null;
			case SiteplanPackage.SIGNAL__LATERAL_DISTANCE:
				return lateralDistance != null && !lateralDistance.isEmpty();
			case SiteplanPackage.SIGNAL__SIGNAL_DIRECTION:
				return signalDirection != SIGNAL_DIRECTION_EDEFAULT;
			case SiteplanPackage.SIGNAL__MOUNT_POSITION:
				return mountPosition != null;
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
		result.append(" (role: ");
		result.append(role);
		result.append(", system: ");
		result.append(system);
		result.append(", lateralDistance: ");
		result.append(lateralDistance);
		result.append(", signalDirection: ");
		result.append(signalDirection);
		result.append(')');
		return result.toString();
	}

} // SignalImpl
