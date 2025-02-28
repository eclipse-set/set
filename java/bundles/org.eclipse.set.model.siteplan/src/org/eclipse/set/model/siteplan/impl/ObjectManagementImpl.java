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

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;

import org.eclipse.set.model.siteplan.ObjectManagement;
import org.eclipse.set.model.siteplan.SiteplanPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Object
 * Management</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.set.model.siteplan.impl.ObjectManagementImpl#getPlanningObjectIDs <em>Planning Object IDs</em>}</li>
 *   <li>{@link org.eclipse.set.model.siteplan.impl.ObjectManagementImpl#getPlanningGroupID <em>Planning Group ID</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ObjectManagementImpl extends MinimalEObjectImpl.Container
		implements ObjectManagement {
	/**
	 * The cached value of the '{@link #getPlanningObjectIDs() <em>Planning Object IDs</em>}' attribute list.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #getPlanningObjectIDs()
	 * @generated
	 * @ordered
	 */
	protected EList<String> planningObjectIDs;

	/**
	 * The default value of the '{@link #getPlanningGroupID() <em>Planning Group ID</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getPlanningGroupID()
	 * @generated
	 * @ordered
	 */
	protected static final String PLANNING_GROUP_ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPlanningGroupID() <em>Planning Group ID</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getPlanningGroupID()
	 * @generated
	 * @ordered
	 */
	protected String planningGroupID = PLANNING_GROUP_ID_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected ObjectManagementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SiteplanPackage.Literals.OBJECT_MANAGEMENT;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<String> getPlanningObjectIDs() {
		if (planningObjectIDs == null) {
			planningObjectIDs = new EDataTypeUniqueEList<String>(String.class, this, SiteplanPackage.OBJECT_MANAGEMENT__PLANNING_OBJECT_IDS);
		}
		return planningObjectIDs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getPlanningGroupID() {
		return planningGroupID;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setPlanningGroupID(String newPlanningGroupID) {
		String oldPlanningGroupID = planningGroupID;
		planningGroupID = newPlanningGroupID;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SiteplanPackage.OBJECT_MANAGEMENT__PLANNING_GROUP_ID, oldPlanningGroupID, planningGroupID));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case SiteplanPackage.OBJECT_MANAGEMENT__PLANNING_OBJECT_IDS:
				return getPlanningObjectIDs();
			case SiteplanPackage.OBJECT_MANAGEMENT__PLANNING_GROUP_ID:
				return getPlanningGroupID();
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
			case SiteplanPackage.OBJECT_MANAGEMENT__PLANNING_OBJECT_IDS:
				getPlanningObjectIDs().clear();
				getPlanningObjectIDs().addAll((Collection<? extends String>)newValue);
				return;
			case SiteplanPackage.OBJECT_MANAGEMENT__PLANNING_GROUP_ID:
				setPlanningGroupID((String)newValue);
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
			case SiteplanPackage.OBJECT_MANAGEMENT__PLANNING_OBJECT_IDS:
				getPlanningObjectIDs().clear();
				return;
			case SiteplanPackage.OBJECT_MANAGEMENT__PLANNING_GROUP_ID:
				setPlanningGroupID(PLANNING_GROUP_ID_EDEFAULT);
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
			case SiteplanPackage.OBJECT_MANAGEMENT__PLANNING_OBJECT_IDS:
				return planningObjectIDs != null && !planningObjectIDs.isEmpty();
			case SiteplanPackage.OBJECT_MANAGEMENT__PLANNING_GROUP_ID:
				return PLANNING_GROUP_ID_EDEFAULT == null ? planningGroupID != null : !PLANNING_GROUP_ID_EDEFAULT.equals(planningGroupID);
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
		result.append(" (planningObjectIDs: ");
		result.append(planningObjectIDs);
		result.append(", planningGroupID: ");
		result.append(planningGroupID);
		result.append(')');
		return result.toString();
	}

} // ObjectManagementImpl
