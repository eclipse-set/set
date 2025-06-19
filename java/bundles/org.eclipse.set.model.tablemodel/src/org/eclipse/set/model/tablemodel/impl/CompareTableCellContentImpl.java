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
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.set.model.tablemodel.CellContent;
import org.eclipse.set.model.tablemodel.CompareTableCellContent;
import org.eclipse.set.model.tablemodel.TablemodelPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Compare
 * Table Cell Content</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.set.model.tablemodel.impl.CompareTableCellContentImpl#getMainPlanCellContent
 * <em>Main Plan Cell Content</em>}</li>
 * <li>{@link org.eclipse.set.model.tablemodel.impl.CompareTableCellContentImpl#getComparePlanCellContent
 * <em>Compare Plan Cell Content</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CompareTableCellContentImpl extends CellContentImpl
		implements CompareTableCellContent {
	/**
	 * The cached value of the '{@link #getMainPlanCellContent() <em>Main Plan
	 * Cell Content</em>}' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getMainPlanCellContent()
	 * @generated
	 * @ordered
	 */
	protected CellContent mainPlanCellContent;

	/**
	 * The cached value of the '{@link #getComparePlanCellContent() <em>Compare
	 * Plan Cell Content</em>}' containment reference. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getComparePlanCellContent()
	 * @generated
	 * @ordered
	 */
	protected CellContent comparePlanCellContent;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected CompareTableCellContentImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TablemodelPackage.Literals.COMPARE_TABLE_CELL_CONTENT;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public CellContent getMainPlanCellContent() {
		return mainPlanCellContent;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetMainPlanCellContent(
			CellContent newMainPlanCellContent, NotificationChain msgs) {
		CellContent oldMainPlanCellContent = mainPlanCellContent;
		mainPlanCellContent = newMainPlanCellContent;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this,
					Notification.SET,
					TablemodelPackage.COMPARE_TABLE_CELL_CONTENT__MAIN_PLAN_CELL_CONTENT,
					oldMainPlanCellContent, newMainPlanCellContent);
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
	public void setMainPlanCellContent(CellContent newMainPlanCellContent) {
		if (newMainPlanCellContent != mainPlanCellContent) {
			NotificationChain msgs = null;
			if (mainPlanCellContent != null)
				msgs = ((InternalEObject) mainPlanCellContent).eInverseRemove(
						this,
						EOPPOSITE_FEATURE_BASE
								- TablemodelPackage.COMPARE_TABLE_CELL_CONTENT__MAIN_PLAN_CELL_CONTENT,
						null, msgs);
			if (newMainPlanCellContent != null)
				msgs = ((InternalEObject) newMainPlanCellContent).eInverseAdd(
						this,
						EOPPOSITE_FEATURE_BASE
								- TablemodelPackage.COMPARE_TABLE_CELL_CONTENT__MAIN_PLAN_CELL_CONTENT,
						null, msgs);
			msgs = basicSetMainPlanCellContent(newMainPlanCellContent, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					TablemodelPackage.COMPARE_TABLE_CELL_CONTENT__MAIN_PLAN_CELL_CONTENT,
					newMainPlanCellContent, newMainPlanCellContent));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public CellContent getComparePlanCellContent() {
		return comparePlanCellContent;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetComparePlanCellContent(
			CellContent newComparePlanCellContent, NotificationChain msgs) {
		CellContent oldComparePlanCellContent = comparePlanCellContent;
		comparePlanCellContent = newComparePlanCellContent;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this,
					Notification.SET,
					TablemodelPackage.COMPARE_TABLE_CELL_CONTENT__COMPARE_PLAN_CELL_CONTENT,
					oldComparePlanCellContent, newComparePlanCellContent);
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
	public void setComparePlanCellContent(
			CellContent newComparePlanCellContent) {
		if (newComparePlanCellContent != comparePlanCellContent) {
			NotificationChain msgs = null;
			if (comparePlanCellContent != null)
				msgs = ((InternalEObject) comparePlanCellContent)
						.eInverseRemove(this, EOPPOSITE_FEATURE_BASE
								- TablemodelPackage.COMPARE_TABLE_CELL_CONTENT__COMPARE_PLAN_CELL_CONTENT,
								null, msgs);
			if (newComparePlanCellContent != null)
				msgs = ((InternalEObject) newComparePlanCellContent)
						.eInverseAdd(this, EOPPOSITE_FEATURE_BASE
								- TablemodelPackage.COMPARE_TABLE_CELL_CONTENT__COMPARE_PLAN_CELL_CONTENT,
								null, msgs);
			msgs = basicSetComparePlanCellContent(newComparePlanCellContent,
					msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					TablemodelPackage.COMPARE_TABLE_CELL_CONTENT__COMPARE_PLAN_CELL_CONTENT,
					newComparePlanCellContent, newComparePlanCellContent));
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
			case TablemodelPackage.COMPARE_TABLE_CELL_CONTENT__MAIN_PLAN_CELL_CONTENT:
				return basicSetMainPlanCellContent(null, msgs);
			case TablemodelPackage.COMPARE_TABLE_CELL_CONTENT__COMPARE_PLAN_CELL_CONTENT:
				return basicSetComparePlanCellContent(null, msgs);
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
			case TablemodelPackage.COMPARE_TABLE_CELL_CONTENT__MAIN_PLAN_CELL_CONTENT:
				return getMainPlanCellContent();
			case TablemodelPackage.COMPARE_TABLE_CELL_CONTENT__COMPARE_PLAN_CELL_CONTENT:
				return getComparePlanCellContent();
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
			case TablemodelPackage.COMPARE_TABLE_CELL_CONTENT__MAIN_PLAN_CELL_CONTENT:
				setMainPlanCellContent((CellContent) newValue);
				return;
			case TablemodelPackage.COMPARE_TABLE_CELL_CONTENT__COMPARE_PLAN_CELL_CONTENT:
				setComparePlanCellContent((CellContent) newValue);
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
			case TablemodelPackage.COMPARE_TABLE_CELL_CONTENT__MAIN_PLAN_CELL_CONTENT:
				setMainPlanCellContent((CellContent) null);
				return;
			case TablemodelPackage.COMPARE_TABLE_CELL_CONTENT__COMPARE_PLAN_CELL_CONTENT:
				setComparePlanCellContent((CellContent) null);
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
			case TablemodelPackage.COMPARE_TABLE_CELL_CONTENT__MAIN_PLAN_CELL_CONTENT:
				return mainPlanCellContent != null;
			case TablemodelPackage.COMPARE_TABLE_CELL_CONTENT__COMPARE_PLAN_CELL_CONTENT:
				return comparePlanCellContent != null;
		}
		return super.eIsSet(featureID);
	}

} // CompareTableCellContentImpl
