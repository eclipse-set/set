/**
 * Copyright (c) 2018 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.validationreport.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.set.model.validationreport.ValidationreportPackage;
import org.eclipse.set.model.validationreport.VersionInfo;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Version Info</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.set.model.validationreport.impl.VersionInfoImpl#getPlanPro <em>Plan Pro</em>}</li>
 *   <li>{@link org.eclipse.set.model.validationreport.impl.VersionInfoImpl#getSignals <em>Signals</em>}</li>
 * </ul>
 *
 * @generated
 */
public class VersionInfoImpl extends MinimalEObjectImpl.Container implements VersionInfo {
	/**
	 * The default value of the '{@link #getPlanPro() <em>Plan Pro</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPlanPro()
	 * @generated
	 * @ordered
	 */
	protected static final String PLAN_PRO_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPlanPro() <em>Plan Pro</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPlanPro()
	 * @generated
	 * @ordered
	 */
	protected String planPro = PLAN_PRO_EDEFAULT;

	/**
	 * The default value of the '{@link #getSignals() <em>Signals</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSignals()
	 * @generated
	 * @ordered
	 */
	protected static final String SIGNALS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSignals() <em>Signals</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSignals()
	 * @generated
	 * @ordered
	 */
	protected String signals = SIGNALS_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected VersionInfoImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ValidationreportPackage.Literals.VERSION_INFO;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getPlanPro() {
		return planPro;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setPlanPro(String newPlanPro) {
		String oldPlanPro = planPro;
		planPro = newPlanPro;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ValidationreportPackage.VERSION_INFO__PLAN_PRO, oldPlanPro, planPro));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getSignals() {
		return signals;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSignals(String newSignals) {
		String oldSignals = signals;
		signals = newSignals;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ValidationreportPackage.VERSION_INFO__SIGNALS, oldSignals, signals));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ValidationreportPackage.VERSION_INFO__PLAN_PRO:
				return getPlanPro();
			case ValidationreportPackage.VERSION_INFO__SIGNALS:
				return getSignals();
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
			case ValidationreportPackage.VERSION_INFO__PLAN_PRO:
				setPlanPro((String)newValue);
				return;
			case ValidationreportPackage.VERSION_INFO__SIGNALS:
				setSignals((String)newValue);
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
			case ValidationreportPackage.VERSION_INFO__PLAN_PRO:
				setPlanPro(PLAN_PRO_EDEFAULT);
				return;
			case ValidationreportPackage.VERSION_INFO__SIGNALS:
				setSignals(SIGNALS_EDEFAULT);
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
			case ValidationreportPackage.VERSION_INFO__PLAN_PRO:
				return PLAN_PRO_EDEFAULT == null ? planPro != null : !PLAN_PRO_EDEFAULT.equals(planPro);
			case ValidationreportPackage.VERSION_INFO__SIGNALS:
				return SIGNALS_EDEFAULT == null ? signals != null : !SIGNALS_EDEFAULT.equals(signals);
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
		result.append(" (planPro: ");
		result.append(planPro);
		result.append(", signals: ");
		result.append(signals);
		result.append(')');
		return result.toString();
	}

} //VersionInfoImpl
