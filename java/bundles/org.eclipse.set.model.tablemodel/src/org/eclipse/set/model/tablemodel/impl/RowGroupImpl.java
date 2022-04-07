/**
 * Copyright (c) 2022 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.tablemodel.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.set.model.tablemodel.RowGroup;
import org.eclipse.set.model.tablemodel.TableRow;
import org.eclipse.set.model.tablemodel.TablemodelPackage;
import org.eclipse.set.toolboxmodel.Basisobjekte.Ur_Objekt;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Row Group</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.set.model.tablemodel.impl.RowGroupImpl#getRows <em>Rows</em>}</li>
 *   <li>{@link org.eclipse.set.model.tablemodel.impl.RowGroupImpl#getLeadingObject <em>Leading Object</em>}</li>
 *   <li>{@link org.eclipse.set.model.tablemodel.impl.RowGroupImpl#getLeadingObjectIndex <em>Leading Object Index</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RowGroupImpl extends MinimalEObjectImpl.Container implements RowGroup {
	/**
	 * The cached value of the '{@link #getRows() <em>Rows</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRows()
	 * @generated
	 * @ordered
	 */
	protected EList<TableRow> rows;

	/**
	 * The cached value of the '{@link #getLeadingObject() <em>Leading Object</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLeadingObject()
	 * @generated
	 * @ordered
	 */
	protected Ur_Objekt leadingObject;

	/**
	 * The default value of the '{@link #getLeadingObjectIndex() <em>Leading Object Index</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLeadingObjectIndex()
	 * @generated
	 * @ordered
	 */
	protected static final int LEADING_OBJECT_INDEX_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getLeadingObjectIndex() <em>Leading Object Index</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLeadingObjectIndex()
	 * @generated
	 * @ordered
	 */
	protected int leadingObjectIndex = LEADING_OBJECT_INDEX_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RowGroupImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TablemodelPackage.Literals.ROW_GROUP;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<TableRow> getRows() {
		if (rows == null) {
			rows = new EObjectContainmentEList<TableRow>(TableRow.class, this, TablemodelPackage.ROW_GROUP__ROWS);
		}
		return rows;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Ur_Objekt getLeadingObject() {
		if (leadingObject != null && leadingObject.eIsProxy()) {
			InternalEObject oldLeadingObject = (InternalEObject)leadingObject;
			leadingObject = (Ur_Objekt)eResolveProxy(oldLeadingObject);
			if (leadingObject != oldLeadingObject) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TablemodelPackage.ROW_GROUP__LEADING_OBJECT, oldLeadingObject, leadingObject));
			}
		}
		return leadingObject;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Ur_Objekt basicGetLeadingObject() {
		return leadingObject;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setLeadingObject(Ur_Objekt newLeadingObject) {
		Ur_Objekt oldLeadingObject = leadingObject;
		leadingObject = newLeadingObject;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TablemodelPackage.ROW_GROUP__LEADING_OBJECT, oldLeadingObject, leadingObject));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getLeadingObjectIndex() {
		return leadingObjectIndex;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setLeadingObjectIndex(int newLeadingObjectIndex) {
		int oldLeadingObjectIndex = leadingObjectIndex;
		leadingObjectIndex = newLeadingObjectIndex;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TablemodelPackage.ROW_GROUP__LEADING_OBJECT_INDEX, oldLeadingObjectIndex, leadingObjectIndex));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TablemodelPackage.ROW_GROUP__ROWS:
				return ((InternalEList<?>)getRows()).basicRemove(otherEnd, msgs);
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
			case TablemodelPackage.ROW_GROUP__ROWS:
				return getRows();
			case TablemodelPackage.ROW_GROUP__LEADING_OBJECT:
				if (resolve) return getLeadingObject();
				return basicGetLeadingObject();
			case TablemodelPackage.ROW_GROUP__LEADING_OBJECT_INDEX:
				return getLeadingObjectIndex();
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
			case TablemodelPackage.ROW_GROUP__ROWS:
				getRows().clear();
				getRows().addAll((Collection<? extends TableRow>)newValue);
				return;
			case TablemodelPackage.ROW_GROUP__LEADING_OBJECT:
				setLeadingObject((Ur_Objekt)newValue);
				return;
			case TablemodelPackage.ROW_GROUP__LEADING_OBJECT_INDEX:
				setLeadingObjectIndex((Integer)newValue);
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
			case TablemodelPackage.ROW_GROUP__ROWS:
				getRows().clear();
				return;
			case TablemodelPackage.ROW_GROUP__LEADING_OBJECT:
				setLeadingObject((Ur_Objekt)null);
				return;
			case TablemodelPackage.ROW_GROUP__LEADING_OBJECT_INDEX:
				setLeadingObjectIndex(LEADING_OBJECT_INDEX_EDEFAULT);
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
			case TablemodelPackage.ROW_GROUP__ROWS:
				return rows != null && !rows.isEmpty();
			case TablemodelPackage.ROW_GROUP__LEADING_OBJECT:
				return leadingObject != null;
			case TablemodelPackage.ROW_GROUP__LEADING_OBJECT_INDEX:
				return leadingObjectIndex != LEADING_OBJECT_INDEX_EDEFAULT;
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
		result.append(" (leadingObjectIndex: ");
		result.append(leadingObjectIndex);
		result.append(')');
		return result.toString();
	}

} //RowGroupImpl
