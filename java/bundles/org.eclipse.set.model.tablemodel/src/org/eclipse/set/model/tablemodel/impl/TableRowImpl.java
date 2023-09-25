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

import org.eclipse.set.model.tablemodel.Footnote;
import org.eclipse.set.model.tablemodel.TableCell;
import org.eclipse.set.model.tablemodel.TableRow;
import org.eclipse.set.model.tablemodel.TablemodelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Table Row</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.set.model.tablemodel.impl.TableRowImpl#getCells <em>Cells</em>}</li>
 *   <li>{@link org.eclipse.set.model.tablemodel.impl.TableRowImpl#getFootnotes <em>Footnotes</em>}</li>
 *   <li>{@link org.eclipse.set.model.tablemodel.impl.TableRowImpl#getRowIndex <em>Row Index</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TableRowImpl extends MinimalEObjectImpl.Container implements TableRow {
	/**
	 * The cached value of the '{@link #getCells() <em>Cells</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCells()
	 * @generated
	 * @ordered
	 */
	protected EList<TableCell> cells;

	/**
	 * The cached value of the '{@link #getFootnotes() <em>Footnotes</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFootnotes()
	 * @generated
	 * @ordered
	 */
	protected EList<Footnote> footnotes;

	/**
	 * The default value of the '{@link #getRowIndex() <em>Row Index</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRowIndex()
	 * @generated
	 * @ordered
	 */
	protected static final int ROW_INDEX_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getRowIndex() <em>Row Index</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRowIndex()
	 * @generated
	 * @ordered
	 */
	protected int rowIndex = ROW_INDEX_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TableRowImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TablemodelPackage.Literals.TABLE_ROW;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<TableCell> getCells() {
		if (cells == null) {
			cells = new EObjectContainmentEList<TableCell>(TableCell.class, this, TablemodelPackage.TABLE_ROW__CELLS);
		}
		return cells;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Footnote> getFootnotes() {
		if (footnotes == null) {
			footnotes = new EObjectContainmentEList<Footnote>(Footnote.class, this, TablemodelPackage.TABLE_ROW__FOOTNOTES);
		}
		return footnotes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getRowIndex() {
		return rowIndex;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setRowIndex(int newRowIndex) {
		int oldRowIndex = rowIndex;
		rowIndex = newRowIndex;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TablemodelPackage.TABLE_ROW__ROW_INDEX, oldRowIndex, rowIndex));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TablemodelPackage.TABLE_ROW__CELLS:
				return ((InternalEList<?>)getCells()).basicRemove(otherEnd, msgs);
			case TablemodelPackage.TABLE_ROW__FOOTNOTES:
				return ((InternalEList<?>)getFootnotes()).basicRemove(otherEnd, msgs);
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
			case TablemodelPackage.TABLE_ROW__CELLS:
				return getCells();
			case TablemodelPackage.TABLE_ROW__FOOTNOTES:
				return getFootnotes();
			case TablemodelPackage.TABLE_ROW__ROW_INDEX:
				return getRowIndex();
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
			case TablemodelPackage.TABLE_ROW__CELLS:
				getCells().clear();
				getCells().addAll((Collection<? extends TableCell>)newValue);
				return;
			case TablemodelPackage.TABLE_ROW__FOOTNOTES:
				getFootnotes().clear();
				getFootnotes().addAll((Collection<? extends Footnote>)newValue);
				return;
			case TablemodelPackage.TABLE_ROW__ROW_INDEX:
				setRowIndex((Integer)newValue);
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
			case TablemodelPackage.TABLE_ROW__CELLS:
				getCells().clear();
				return;
			case TablemodelPackage.TABLE_ROW__FOOTNOTES:
				getFootnotes().clear();
				return;
			case TablemodelPackage.TABLE_ROW__ROW_INDEX:
				setRowIndex(ROW_INDEX_EDEFAULT);
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
			case TablemodelPackage.TABLE_ROW__CELLS:
				return cells != null && !cells.isEmpty();
			case TablemodelPackage.TABLE_ROW__FOOTNOTES:
				return footnotes != null && !footnotes.isEmpty();
			case TablemodelPackage.TABLE_ROW__ROW_INDEX:
				return rowIndex != ROW_INDEX_EDEFAULT;
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
		result.append(" (rowIndex: ");
		result.append(rowIndex);
		result.append(')');
		return result.toString();
	}

} //TableRowImpl
