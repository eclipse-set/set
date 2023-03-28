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
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.set.model.tablemodel.MultiColorCellContent;
import org.eclipse.set.model.tablemodel.MultiColorContent;
import org.eclipse.set.model.tablemodel.TablemodelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Multi Color Cell Content</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.set.model.tablemodel.impl.MultiColorCellContentImpl#getValue <em>Value</em>}</li>
 *   <li>{@link org.eclipse.set.model.tablemodel.impl.MultiColorCellContentImpl#getSeperator <em>Seperator</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MultiColorCellContentImpl extends CellContentImpl implements MultiColorCellContent {
	/**
	 * The cached value of the '{@link #getValue() <em>Value</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
	protected EList<MultiColorContent> value;

	/**
	 * The default value of the '{@link #getSeperator() <em>Seperator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSeperator()
	 * @generated
	 * @ordered
	 */
	protected static final String SEPERATOR_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getSeperator() <em>Seperator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSeperator()
	 * @generated
	 * @ordered
	 */
	protected String seperator = SEPERATOR_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MultiColorCellContentImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TablemodelPackage.Literals.MULTI_COLOR_CELL_CONTENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<MultiColorContent> getValue() {
		if (value == null) {
			value = new EObjectResolvingEList<MultiColorContent>(MultiColorContent.class, this, TablemodelPackage.MULTI_COLOR_CELL_CONTENT__VALUE);
		}
		return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getSeperator() {
		return seperator;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSeperator(String newSeperator) {
		String oldSeperator = seperator;
		seperator = newSeperator;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TablemodelPackage.MULTI_COLOR_CELL_CONTENT__SEPERATOR, oldSeperator, seperator));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TablemodelPackage.MULTI_COLOR_CELL_CONTENT__VALUE:
				return getValue();
			case TablemodelPackage.MULTI_COLOR_CELL_CONTENT__SEPERATOR:
				return getSeperator();
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
			case TablemodelPackage.MULTI_COLOR_CELL_CONTENT__VALUE:
				getValue().clear();
				getValue().addAll((Collection<? extends MultiColorContent>)newValue);
				return;
			case TablemodelPackage.MULTI_COLOR_CELL_CONTENT__SEPERATOR:
				setSeperator((String)newValue);
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
			case TablemodelPackage.MULTI_COLOR_CELL_CONTENT__VALUE:
				getValue().clear();
				return;
			case TablemodelPackage.MULTI_COLOR_CELL_CONTENT__SEPERATOR:
				setSeperator(SEPERATOR_EDEFAULT);
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
			case TablemodelPackage.MULTI_COLOR_CELL_CONTENT__VALUE:
				return value != null && !value.isEmpty();
			case TablemodelPackage.MULTI_COLOR_CELL_CONTENT__SEPERATOR:
				return SEPERATOR_EDEFAULT == null ? seperator != null : !SEPERATOR_EDEFAULT.equals(seperator);
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
		result.append(" (seperator: ");
		result.append(seperator);
		result.append(')');
		return result.toString();
	}

} //MultiColorCellContentImpl
