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

import org.eclipse.set.model.tablemodel.CellAnnotation;
import org.eclipse.set.model.tablemodel.CellContent;
import org.eclipse.set.model.tablemodel.ColumnDescriptor;
import org.eclipse.set.model.tablemodel.TableCell;
import org.eclipse.set.model.tablemodel.TablemodelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Table Cell</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.set.model.tablemodel.impl.TableCellImpl#getContent <em>Content</em>}</li>
 *   <li>{@link org.eclipse.set.model.tablemodel.impl.TableCellImpl#getColumndescriptor <em>Columndescriptor</em>}</li>
 *   <li>{@link org.eclipse.set.model.tablemodel.impl.TableCellImpl#getCellannotation <em>Cellannotation</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TableCellImpl extends MinimalEObjectImpl.Container implements TableCell {
	/**
	 * The cached value of the '{@link #getContent() <em>Content</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContent()
	 * @generated
	 * @ordered
	 */
	protected CellContent content;

	/**
	 * The cached value of the '{@link #getColumndescriptor() <em>Columndescriptor</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getColumndescriptor()
	 * @generated
	 * @ordered
	 */
	protected ColumnDescriptor columndescriptor;

	/**
	 * The cached value of the '{@link #getCellannotation() <em>Cellannotation</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCellannotation()
	 * @generated
	 * @ordered
	 */
	protected EList<CellAnnotation> cellannotation;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TableCellImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TablemodelPackage.Literals.TABLE_CELL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public CellContent getContent() {
		return content;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetContent(CellContent newContent, NotificationChain msgs) {
		CellContent oldContent = content;
		content = newContent;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TablemodelPackage.TABLE_CELL__CONTENT, oldContent, newContent);
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
	public void setContent(CellContent newContent) {
		if (newContent != content) {
			NotificationChain msgs = null;
			if (content != null)
				msgs = ((InternalEObject)content).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TablemodelPackage.TABLE_CELL__CONTENT, null, msgs);
			if (newContent != null)
				msgs = ((InternalEObject)newContent).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TablemodelPackage.TABLE_CELL__CONTENT, null, msgs);
			msgs = basicSetContent(newContent, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TablemodelPackage.TABLE_CELL__CONTENT, newContent, newContent));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ColumnDescriptor getColumndescriptor() {
		if (columndescriptor != null && columndescriptor.eIsProxy()) {
			InternalEObject oldColumndescriptor = (InternalEObject)columndescriptor;
			columndescriptor = (ColumnDescriptor)eResolveProxy(oldColumndescriptor);
			if (columndescriptor != oldColumndescriptor) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TablemodelPackage.TABLE_CELL__COLUMNDESCRIPTOR, oldColumndescriptor, columndescriptor));
			}
		}
		return columndescriptor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ColumnDescriptor basicGetColumndescriptor() {
		return columndescriptor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setColumndescriptor(ColumnDescriptor newColumndescriptor) {
		ColumnDescriptor oldColumndescriptor = columndescriptor;
		columndescriptor = newColumndescriptor;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TablemodelPackage.TABLE_CELL__COLUMNDESCRIPTOR, oldColumndescriptor, columndescriptor));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<CellAnnotation> getCellannotation() {
		if (cellannotation == null) {
			cellannotation = new EObjectContainmentEList<CellAnnotation>(CellAnnotation.class, this, TablemodelPackage.TABLE_CELL__CELLANNOTATION);
		}
		return cellannotation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TablemodelPackage.TABLE_CELL__CONTENT:
				return basicSetContent(null, msgs);
			case TablemodelPackage.TABLE_CELL__CELLANNOTATION:
				return ((InternalEList<?>)getCellannotation()).basicRemove(otherEnd, msgs);
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
			case TablemodelPackage.TABLE_CELL__CONTENT:
				return getContent();
			case TablemodelPackage.TABLE_CELL__COLUMNDESCRIPTOR:
				if (resolve) return getColumndescriptor();
				return basicGetColumndescriptor();
			case TablemodelPackage.TABLE_CELL__CELLANNOTATION:
				return getCellannotation();
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
			case TablemodelPackage.TABLE_CELL__CONTENT:
				setContent((CellContent)newValue);
				return;
			case TablemodelPackage.TABLE_CELL__COLUMNDESCRIPTOR:
				setColumndescriptor((ColumnDescriptor)newValue);
				return;
			case TablemodelPackage.TABLE_CELL__CELLANNOTATION:
				getCellannotation().clear();
				getCellannotation().addAll((Collection<? extends CellAnnotation>)newValue);
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
			case TablemodelPackage.TABLE_CELL__CONTENT:
				setContent((CellContent)null);
				return;
			case TablemodelPackage.TABLE_CELL__COLUMNDESCRIPTOR:
				setColumndescriptor((ColumnDescriptor)null);
				return;
			case TablemodelPackage.TABLE_CELL__CELLANNOTATION:
				getCellannotation().clear();
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
			case TablemodelPackage.TABLE_CELL__CONTENT:
				return content != null;
			case TablemodelPackage.TABLE_CELL__COLUMNDESCRIPTOR:
				return columndescriptor != null;
			case TablemodelPackage.TABLE_CELL__CELLANNOTATION:
				return cellannotation != null && !cellannotation.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //TableCellImpl
