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
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.eclipse.set.model.tablemodel.CompareCellContent;
import org.eclipse.set.model.tablemodel.TablemodelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Compare Cell Content</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.set.model.tablemodel.impl.CompareCellContentImpl#getOldValue <em>Old Value</em>}</li>
 *   <li>{@link org.eclipse.set.model.tablemodel.impl.CompareCellContentImpl#getNewValue <em>New Value</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CompareCellContentImpl extends CellContentImpl implements CompareCellContent {
	/**
	 * The cached value of the '{@link #getOldValue() <em>Old Value</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOldValue()
	 * @generated
	 * @ordered
	 */
	protected EList<String> oldValue;

	/**
	 * The cached value of the '{@link #getNewValue() <em>New Value</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNewValue()
	 * @generated
	 * @ordered
	 */
	protected EList<String> newValue;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CompareCellContentImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TablemodelPackage.Literals.COMPARE_CELL_CONTENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<String> getOldValue() {
		if (oldValue == null) {
			oldValue = new EDataTypeUniqueEList<String>(String.class, this, TablemodelPackage.COMPARE_CELL_CONTENT__OLD_VALUE);
		}
		return oldValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<String> getNewValue() {
		if (newValue == null) {
			newValue = new EDataTypeUniqueEList<String>(String.class, this, TablemodelPackage.COMPARE_CELL_CONTENT__NEW_VALUE);
		}
		return newValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TablemodelPackage.COMPARE_CELL_CONTENT__OLD_VALUE:
				return getOldValue();
			case TablemodelPackage.COMPARE_CELL_CONTENT__NEW_VALUE:
				return getNewValue();
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
			case TablemodelPackage.COMPARE_CELL_CONTENT__OLD_VALUE:
				getOldValue().clear();
				getOldValue().addAll((Collection<? extends String>)newValue);
				return;
			case TablemodelPackage.COMPARE_CELL_CONTENT__NEW_VALUE:
				getNewValue().clear();
				getNewValue().addAll((Collection<? extends String>)newValue);
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
			case TablemodelPackage.COMPARE_CELL_CONTENT__OLD_VALUE:
				getOldValue().clear();
				return;
			case TablemodelPackage.COMPARE_CELL_CONTENT__NEW_VALUE:
				getNewValue().clear();
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
			case TablemodelPackage.COMPARE_CELL_CONTENT__OLD_VALUE:
				return oldValue != null && !oldValue.isEmpty();
			case TablemodelPackage.COMPARE_CELL_CONTENT__NEW_VALUE:
				return newValue != null && !newValue.isEmpty();
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
		result.append(" (oldValue: ");
		result.append(oldValue);
		result.append(", newValue: ");
		result.append(newValue);
		result.append(')');
		return result.toString();
	}

} //CompareCellContentImpl
