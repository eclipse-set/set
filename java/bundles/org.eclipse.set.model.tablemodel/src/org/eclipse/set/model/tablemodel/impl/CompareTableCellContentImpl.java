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
 * <li>{@link org.eclipse.set.model.tablemodel.impl.CompareTableCellContentImpl#getFirstPlanCellContent
 * <em>First Plan Cell Content</em>}</li>
 * <li>{@link org.eclipse.set.model.tablemodel.impl.CompareTableCellContentImpl#getSecondPlanCellContent
 * <em>Second Plan Cell Content</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CompareTableCellContentImpl extends CellContentImpl
		implements CompareTableCellContent {
	/**
	 * The cached value of the '{@link #getFirstPlanCellContent() <em>First Plan
	 * Cell Content</em>}' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getFirstPlanCellContent()
	 * @generated
	 * @ordered
	 */
	protected CellContent firstPlanCellContent;

	/**
	 * The cached value of the '{@link #getSecondPlanCellContent() <em>Second
	 * Plan Cell Content</em>}' containment reference. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getSecondPlanCellContent()
	 * @generated
	 * @ordered
	 */
	protected CellContent secondPlanCellContent;

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
	public CellContent getFirstPlanCellContent() {
		return firstPlanCellContent;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetFirstPlanCellContent(
			CellContent newFirstPlanCellContent, NotificationChain msgs) {
		CellContent oldFirstPlanCellContent = firstPlanCellContent;
		firstPlanCellContent = newFirstPlanCellContent;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this,
					Notification.SET,
					TablemodelPackage.COMPARE_TABLE_CELL_CONTENT__FIRST_PLAN_CELL_CONTENT,
					oldFirstPlanCellContent, newFirstPlanCellContent);
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
	public void setFirstPlanCellContent(CellContent newFirstPlanCellContent) {
		if (newFirstPlanCellContent != firstPlanCellContent) {
			NotificationChain msgs = null;
			if (firstPlanCellContent != null)
				msgs = ((InternalEObject) firstPlanCellContent).eInverseRemove(
						this,
						EOPPOSITE_FEATURE_BASE
								- TablemodelPackage.COMPARE_TABLE_CELL_CONTENT__FIRST_PLAN_CELL_CONTENT,
						null, msgs);
			if (newFirstPlanCellContent != null)
				msgs = ((InternalEObject) newFirstPlanCellContent).eInverseAdd(
						this,
						EOPPOSITE_FEATURE_BASE
								- TablemodelPackage.COMPARE_TABLE_CELL_CONTENT__FIRST_PLAN_CELL_CONTENT,
						null, msgs);
			msgs = basicSetFirstPlanCellContent(newFirstPlanCellContent, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					TablemodelPackage.COMPARE_TABLE_CELL_CONTENT__FIRST_PLAN_CELL_CONTENT,
					newFirstPlanCellContent, newFirstPlanCellContent));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public CellContent getSecondPlanCellContent() {
		return secondPlanCellContent;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetSecondPlanCellContent(
			CellContent newSecondPlanCellContent, NotificationChain msgs) {
		CellContent oldSecondPlanCellContent = secondPlanCellContent;
		secondPlanCellContent = newSecondPlanCellContent;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this,
					Notification.SET,
					TablemodelPackage.COMPARE_TABLE_CELL_CONTENT__SECOND_PLAN_CELL_CONTENT,
					oldSecondPlanCellContent, newSecondPlanCellContent);
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
	public void setSecondPlanCellContent(CellContent newSecondPlanCellContent) {
		if (newSecondPlanCellContent != secondPlanCellContent) {
			NotificationChain msgs = null;
			if (secondPlanCellContent != null)
				msgs = ((InternalEObject) secondPlanCellContent).eInverseRemove(
						this,
						EOPPOSITE_FEATURE_BASE
								- TablemodelPackage.COMPARE_TABLE_CELL_CONTENT__SECOND_PLAN_CELL_CONTENT,
						null, msgs);
			if (newSecondPlanCellContent != null)
				msgs = ((InternalEObject) newSecondPlanCellContent).eInverseAdd(
						this,
						EOPPOSITE_FEATURE_BASE
								- TablemodelPackage.COMPARE_TABLE_CELL_CONTENT__SECOND_PLAN_CELL_CONTENT,
						null, msgs);
			msgs = basicSetSecondPlanCellContent(newSecondPlanCellContent,
					msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					TablemodelPackage.COMPARE_TABLE_CELL_CONTENT__SECOND_PLAN_CELL_CONTENT,
					newSecondPlanCellContent, newSecondPlanCellContent));
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
			case TablemodelPackage.COMPARE_TABLE_CELL_CONTENT__FIRST_PLAN_CELL_CONTENT:
				return basicSetFirstPlanCellContent(null, msgs);
			case TablemodelPackage.COMPARE_TABLE_CELL_CONTENT__SECOND_PLAN_CELL_CONTENT:
				return basicSetSecondPlanCellContent(null, msgs);
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
			case TablemodelPackage.COMPARE_TABLE_CELL_CONTENT__FIRST_PLAN_CELL_CONTENT:
				return getFirstPlanCellContent();
			case TablemodelPackage.COMPARE_TABLE_CELL_CONTENT__SECOND_PLAN_CELL_CONTENT:
				return getSecondPlanCellContent();
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
			case TablemodelPackage.COMPARE_TABLE_CELL_CONTENT__FIRST_PLAN_CELL_CONTENT:
				setFirstPlanCellContent((CellContent) newValue);
				return;
			case TablemodelPackage.COMPARE_TABLE_CELL_CONTENT__SECOND_PLAN_CELL_CONTENT:
				setSecondPlanCellContent((CellContent) newValue);
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
			case TablemodelPackage.COMPARE_TABLE_CELL_CONTENT__FIRST_PLAN_CELL_CONTENT:
				setFirstPlanCellContent((CellContent) null);
				return;
			case TablemodelPackage.COMPARE_TABLE_CELL_CONTENT__SECOND_PLAN_CELL_CONTENT:
				setSecondPlanCellContent((CellContent) null);
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
			case TablemodelPackage.COMPARE_TABLE_CELL_CONTENT__FIRST_PLAN_CELL_CONTENT:
				return firstPlanCellContent != null;
			case TablemodelPackage.COMPARE_TABLE_CELL_CONTENT__SECOND_PLAN_CELL_CONTENT:
				return secondPlanCellContent != null;
		}
		return super.eIsSet(featureID);
	}

} // CompareTableCellContentImpl
