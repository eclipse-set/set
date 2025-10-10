/**
 * Copyright (c) 2022 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.tablemodel.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.set.model.tablemodel.PlanCompareRow;
import org.eclipse.set.model.tablemodel.PlanCompareRowType;
import org.eclipse.set.model.tablemodel.TablemodelPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Plan
 * Compare Row</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.set.model.tablemodel.impl.PlanCompareRowImpl#getRowType
 * <em>Row Type</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PlanCompareRowImpl extends TableRowImpl implements PlanCompareRow {
	/**
	 * The default value of the '{@link #getRowType() <em>Row Type</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getRowType()
	 * @generated
	 * @ordered
	 */
	protected static final PlanCompareRowType ROW_TYPE_EDEFAULT = PlanCompareRowType.NEW_ROW;

	/**
	 * The cached value of the '{@link #getRowType() <em>Row Type</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getRowType()
	 * @generated
	 * @ordered
	 */
	protected PlanCompareRowType rowType = ROW_TYPE_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected PlanCompareRowImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TablemodelPackage.Literals.PLAN_COMPARE_ROW;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public PlanCompareRowType getRowType() {
		return rowType;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setRowType(PlanCompareRowType newRowType) {
		PlanCompareRowType oldRowType = rowType;
		rowType = newRowType == null ? ROW_TYPE_EDEFAULT : newRowType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					TablemodelPackage.PLAN_COMPARE_ROW__ROW_TYPE, oldRowType,
					rowType));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TablemodelPackage.PLAN_COMPARE_ROW__ROW_TYPE:
				return getRowType();
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
			case TablemodelPackage.PLAN_COMPARE_ROW__ROW_TYPE:
				setRowType((PlanCompareRowType) newValue);
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
			case TablemodelPackage.PLAN_COMPARE_ROW__ROW_TYPE:
				setRowType(ROW_TYPE_EDEFAULT);
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
			case TablemodelPackage.PLAN_COMPARE_ROW__ROW_TYPE:
				return rowType != ROW_TYPE_EDEFAULT;
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
		result.append(" (rowType: ");
		result.append(rowType);
		result.append(')');
		return result.toString();
	}

} // PlanCompareRowImpl
