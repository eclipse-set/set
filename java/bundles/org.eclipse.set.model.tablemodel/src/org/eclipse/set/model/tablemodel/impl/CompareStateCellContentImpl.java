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
import org.eclipse.set.model.tablemodel.CompareStateCellContent;
import org.eclipse.set.model.tablemodel.TablemodelPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Compare
 * State Cell Content</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.set.model.tablemodel.impl.CompareStateCellContentImpl#getOldValue
 * <em>Old Value</em>}</li>
 * <li>{@link org.eclipse.set.model.tablemodel.impl.CompareStateCellContentImpl#getNewValue
 * <em>New Value</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CompareStateCellContentImpl extends CellContentImpl
		implements CompareStateCellContent {
	/**
	 * The cached value of the '{@link #getOldValue() <em>Old Value</em>}'
	 * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getOldValue()
	 * @generated
	 * @ordered
	 */
	protected CellContent oldValue;

	/**
	 * The cached value of the '{@link #getNewValue() <em>New Value</em>}'
	 * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getNewValue()
	 * @generated
	 * @ordered
	 */
	protected CellContent newValue;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected CompareStateCellContentImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TablemodelPackage.Literals.COMPARE_STATE_CELL_CONTENT;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public CellContent getOldValue() {
		return oldValue;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetOldValue(CellContent newOldValue,
			NotificationChain msgs) {
		CellContent oldOldValue = oldValue;
		oldValue = newOldValue;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this,
					Notification.SET,
					TablemodelPackage.COMPARE_STATE_CELL_CONTENT__OLD_VALUE,
					oldOldValue, newOldValue);
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
	public void setOldValue(CellContent newOldValue) {
		if (newOldValue != oldValue) {
			NotificationChain msgs = null;
			if (oldValue != null)
				msgs = ((InternalEObject) oldValue).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE
								- TablemodelPackage.COMPARE_STATE_CELL_CONTENT__OLD_VALUE,
						null, msgs);
			if (newOldValue != null)
				msgs = ((InternalEObject) newOldValue).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE
								- TablemodelPackage.COMPARE_STATE_CELL_CONTENT__OLD_VALUE,
						null, msgs);
			msgs = basicSetOldValue(newOldValue, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					TablemodelPackage.COMPARE_STATE_CELL_CONTENT__OLD_VALUE,
					newOldValue, newOldValue));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public CellContent getNewValue() {
		return newValue;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetNewValue(CellContent newNewValue,
			NotificationChain msgs) {
		CellContent oldNewValue = newValue;
		newValue = newNewValue;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this,
					Notification.SET,
					TablemodelPackage.COMPARE_STATE_CELL_CONTENT__NEW_VALUE,
					oldNewValue, newNewValue);
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
	public void setNewValue(CellContent newNewValue) {
		if (newNewValue != newValue) {
			NotificationChain msgs = null;
			if (newValue != null)
				msgs = ((InternalEObject) newValue).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE
								- TablemodelPackage.COMPARE_STATE_CELL_CONTENT__NEW_VALUE,
						null, msgs);
			if (newNewValue != null)
				msgs = ((InternalEObject) newNewValue).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE
								- TablemodelPackage.COMPARE_STATE_CELL_CONTENT__NEW_VALUE,
						null, msgs);
			msgs = basicSetNewValue(newNewValue, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					TablemodelPackage.COMPARE_STATE_CELL_CONTENT__NEW_VALUE,
					newNewValue, newNewValue));
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
			case TablemodelPackage.COMPARE_STATE_CELL_CONTENT__OLD_VALUE:
				return basicSetOldValue(null, msgs);
			case TablemodelPackage.COMPARE_STATE_CELL_CONTENT__NEW_VALUE:
				return basicSetNewValue(null, msgs);
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
			case TablemodelPackage.COMPARE_STATE_CELL_CONTENT__OLD_VALUE:
				return getOldValue();
			case TablemodelPackage.COMPARE_STATE_CELL_CONTENT__NEW_VALUE:
				return getNewValue();
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
			case TablemodelPackage.COMPARE_STATE_CELL_CONTENT__OLD_VALUE:
				setOldValue((CellContent) newValue);
				return;
			case TablemodelPackage.COMPARE_STATE_CELL_CONTENT__NEW_VALUE:
				setNewValue((CellContent) newValue);
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
			case TablemodelPackage.COMPARE_STATE_CELL_CONTENT__OLD_VALUE:
				setOldValue((CellContent) null);
				return;
			case TablemodelPackage.COMPARE_STATE_CELL_CONTENT__NEW_VALUE:
				setNewValue((CellContent) null);
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
			case TablemodelPackage.COMPARE_STATE_CELL_CONTENT__OLD_VALUE:
				return oldValue != null;
			case TablemodelPackage.COMPARE_STATE_CELL_CONTENT__NEW_VALUE:
				return newValue != null;
		}
		return super.eIsSet(featureID);
	}

} // CompareStateCellContentImpl
