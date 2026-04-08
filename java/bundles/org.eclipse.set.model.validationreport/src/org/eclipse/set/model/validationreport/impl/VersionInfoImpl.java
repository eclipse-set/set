/**
 * Copyright (c) 2018 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.validationreport.impl;

import java.util.Collection;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.eclipse.set.model.validationreport.ValidationreportPackage;
import org.eclipse.set.model.validationreport.VersionInfo;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Version
 * Info</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.set.model.validationreport.impl.VersionInfoImpl#getPlanProVersions
 * <em>Plan Pro Versions</em>}</li>
 * <li>{@link org.eclipse.set.model.validationreport.impl.VersionInfoImpl#getSignalbegriffeVersions
 * <em>Signalbegriffe Versions</em>}</li>
 * </ul>
 *
 * @generated
 */
public class VersionInfoImpl extends MinimalEObjectImpl.Container
		implements VersionInfo {
	/**
	 * The cached value of the '{@link #getPlanProVersions() <em>Plan Pro
	 * Versions</em>}' attribute list. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see #getPlanProVersions()
	 * @generated
	 * @ordered
	 */
	protected EList<String> planProVersions;

	/**
	 * The cached value of the '{@link #getSignalbegriffeVersions()
	 * <em>Signalbegriffe Versions</em>}' attribute list. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #getSignalbegriffeVersions()
	 * @generated
	 * @ordered
	 */
	protected EList<String> signalbegriffeVersions;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected VersionInfoImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ValidationreportPackage.Literals.VERSION_INFO;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<String> getPlanProVersions() {
		if (planProVersions == null) {
			planProVersions = new EDataTypeUniqueEList<String>(String.class,
					this,
					ValidationreportPackage.VERSION_INFO__PLAN_PRO_VERSIONS);
		}
		return planProVersions;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<String> getSignalbegriffeVersions() {
		if (signalbegriffeVersions == null) {
			signalbegriffeVersions = new EDataTypeUniqueEList<String>(
					String.class, this,
					ValidationreportPackage.VERSION_INFO__SIGNALBEGRIFFE_VERSIONS);
		}
		return signalbegriffeVersions;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ValidationreportPackage.VERSION_INFO__PLAN_PRO_VERSIONS:
				return getPlanProVersions();
			case ValidationreportPackage.VERSION_INFO__SIGNALBEGRIFFE_VERSIONS:
				return getSignalbegriffeVersions();
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
			case ValidationreportPackage.VERSION_INFO__PLAN_PRO_VERSIONS:
				getPlanProVersions().clear();
				getPlanProVersions()
						.addAll((Collection<? extends String>) newValue);
				return;
			case ValidationreportPackage.VERSION_INFO__SIGNALBEGRIFFE_VERSIONS:
				getSignalbegriffeVersions().clear();
				getSignalbegriffeVersions()
						.addAll((Collection<? extends String>) newValue);
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
			case ValidationreportPackage.VERSION_INFO__PLAN_PRO_VERSIONS:
				getPlanProVersions().clear();
				return;
			case ValidationreportPackage.VERSION_INFO__SIGNALBEGRIFFE_VERSIONS:
				getSignalbegriffeVersions().clear();
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
			case ValidationreportPackage.VERSION_INFO__PLAN_PRO_VERSIONS:
				return planProVersions != null && !planProVersions.isEmpty();
			case ValidationreportPackage.VERSION_INFO__SIGNALBEGRIFFE_VERSIONS:
				return signalbegriffeVersions != null
						&& !signalbegriffeVersions.isEmpty();
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
		result.append(" (planProVersions: ");
		result.append(planProVersions);
		result.append(", signalbegriffeVersions: ");
		result.append(signalbegriffeVersions);
		result.append(')');
		return result.toString();
	}

} // VersionInfoImpl
