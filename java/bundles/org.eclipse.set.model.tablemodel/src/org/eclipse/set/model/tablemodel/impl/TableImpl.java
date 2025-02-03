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

import org.eclipse.set.model.tablemodel.ColumnDescriptor;
import org.eclipse.set.model.tablemodel.Table;
import org.eclipse.set.model.tablemodel.TableContent;
import org.eclipse.set.model.tablemodel.TablemodelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Table</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.set.model.tablemodel.impl.TableImpl#getColumndescriptors <em>Columndescriptors</em>}</li>
 *   <li>{@link org.eclipse.set.model.tablemodel.impl.TableImpl#getTablecontent <em>Tablecontent</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TableImpl extends MinimalEObjectImpl.Container implements Table {
	/**
	 * The cached value of the '{@link #getColumndescriptors() <em>Columndescriptors</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getColumndescriptors()
	 * @generated
	 * @ordered
	 */
	protected EList<ColumnDescriptor> columndescriptors;

	/**
	 * The cached value of the '{@link #getTablecontent() <em>Tablecontent</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTablecontent()
	 * @generated
	 * @ordered
	 */
	protected TableContent tablecontent;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TableImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TablemodelPackage.Literals.TABLE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<ColumnDescriptor> getColumndescriptors() {
		if (columndescriptors == null) {
			columndescriptors = new EObjectContainmentEList<ColumnDescriptor>(ColumnDescriptor.class, this, TablemodelPackage.TABLE__COLUMNDESCRIPTORS);
		}
		return columndescriptors;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TableContent getTablecontent() {
		return tablecontent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTablecontent(TableContent newTablecontent, NotificationChain msgs) {
		TableContent oldTablecontent = tablecontent;
		tablecontent = newTablecontent;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TablemodelPackage.TABLE__TABLECONTENT, oldTablecontent, newTablecontent);
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
	public void setTablecontent(TableContent newTablecontent) {
		if (newTablecontent != tablecontent) {
			NotificationChain msgs = null;
			if (tablecontent != null)
				msgs = ((InternalEObject)tablecontent).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TablemodelPackage.TABLE__TABLECONTENT, null, msgs);
			if (newTablecontent != null)
				msgs = ((InternalEObject)newTablecontent).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TablemodelPackage.TABLE__TABLECONTENT, null, msgs);
			msgs = basicSetTablecontent(newTablecontent, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TablemodelPackage.TABLE__TABLECONTENT, newTablecontent, newTablecontent));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TablemodelPackage.TABLE__COLUMNDESCRIPTORS:
				return ((InternalEList<?>)getColumndescriptors()).basicRemove(otherEnd, msgs);
			case TablemodelPackage.TABLE__TABLECONTENT:
				return basicSetTablecontent(null, msgs);
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
			case TablemodelPackage.TABLE__COLUMNDESCRIPTORS:
				return getColumndescriptors();
			case TablemodelPackage.TABLE__TABLECONTENT:
				return getTablecontent();
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
			case TablemodelPackage.TABLE__COLUMNDESCRIPTORS:
				getColumndescriptors().clear();
				getColumndescriptors().addAll((Collection<? extends ColumnDescriptor>)newValue);
				return;
			case TablemodelPackage.TABLE__TABLECONTENT:
				setTablecontent((TableContent)newValue);
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
			case TablemodelPackage.TABLE__COLUMNDESCRIPTORS:
				getColumndescriptors().clear();
				return;
			case TablemodelPackage.TABLE__TABLECONTENT:
				setTablecontent((TableContent)null);
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
			case TablemodelPackage.TABLE__COLUMNDESCRIPTORS:
				return columndescriptors != null && !columndescriptors.isEmpty();
			case TablemodelPackage.TABLE__TABLECONTENT:
				return tablecontent != null;
		}
		return super.eIsSet(featureID);
	}

} //TableImpl
