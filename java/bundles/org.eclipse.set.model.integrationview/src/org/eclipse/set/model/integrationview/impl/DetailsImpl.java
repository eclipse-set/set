/**
 * Copyright (c) 2018 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.integrationview.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.set.model.integrationview.Details;
import org.eclipse.set.model.integrationview.IntegrationviewPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object
 * '<em><b>Details</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.set.model.integrationview.impl.DetailsImpl#getAttributePath
 * <em>Attribute Path</em>}</li>
 * <li>{@link org.eclipse.set.model.integrationview.impl.DetailsImpl#getValuePrimaryPlanning
 * <em>Value Primary Planning</em>}</li>
 * <li>{@link org.eclipse.set.model.integrationview.impl.DetailsImpl#getValueSecondaryPlanning
 * <em>Value Secondary Planning</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DetailsImpl extends MinimalEObjectImpl.Container
		implements Details {
	/**
	 * The default value of the '{@link #getAttributePath() <em>Attribute
	 * Path</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getAttributePath()
	 * @generated
	 * @ordered
	 */
	protected static final String ATTRIBUTE_PATH_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getAttributePath() <em>Attribute
	 * Path</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getAttributePath()
	 * @generated
	 * @ordered
	 */
	protected String attributePath = ATTRIBUTE_PATH_EDEFAULT;

	/**
	 * The default value of the '{@link #getValuePrimaryPlanning() <em>Value
	 * Primary Planning</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getValuePrimaryPlanning()
	 * @generated
	 * @ordered
	 */
	protected static final String VALUE_PRIMARY_PLANNING_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getValuePrimaryPlanning() <em>Value
	 * Primary Planning</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getValuePrimaryPlanning()
	 * @generated
	 * @ordered
	 */
	protected String valuePrimaryPlanning = VALUE_PRIMARY_PLANNING_EDEFAULT;

	/**
	 * The default value of the '{@link #getValueSecondaryPlanning() <em>Value
	 * Secondary Planning</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getValueSecondaryPlanning()
	 * @generated
	 * @ordered
	 */
	protected static final String VALUE_SECONDARY_PLANNING_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getValueSecondaryPlanning() <em>Value
	 * Secondary Planning</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getValueSecondaryPlanning()
	 * @generated
	 * @ordered
	 */
	protected String valueSecondaryPlanning = VALUE_SECONDARY_PLANNING_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected DetailsImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IntegrationviewPackage.Literals.DETAILS;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getAttributePath() {
		return attributePath;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setAttributePath(String newAttributePath) {
		String oldAttributePath = attributePath;
		attributePath = newAttributePath;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IntegrationviewPackage.DETAILS__ATTRIBUTE_PATH,
					oldAttributePath, attributePath));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getValuePrimaryPlanning() {
		return valuePrimaryPlanning;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setValuePrimaryPlanning(String newValuePrimaryPlanning) {
		String oldValuePrimaryPlanning = valuePrimaryPlanning;
		valuePrimaryPlanning = newValuePrimaryPlanning;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IntegrationviewPackage.DETAILS__VALUE_PRIMARY_PLANNING,
					oldValuePrimaryPlanning, valuePrimaryPlanning));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getValueSecondaryPlanning() {
		return valueSecondaryPlanning;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setValueSecondaryPlanning(String newValueSecondaryPlanning) {
		String oldValueSecondaryPlanning = valueSecondaryPlanning;
		valueSecondaryPlanning = newValueSecondaryPlanning;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IntegrationviewPackage.DETAILS__VALUE_SECONDARY_PLANNING,
					oldValueSecondaryPlanning, valueSecondaryPlanning));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case IntegrationviewPackage.DETAILS__ATTRIBUTE_PATH:
				return getAttributePath();
			case IntegrationviewPackage.DETAILS__VALUE_PRIMARY_PLANNING:
				return getValuePrimaryPlanning();
			case IntegrationviewPackage.DETAILS__VALUE_SECONDARY_PLANNING:
				return getValueSecondaryPlanning();
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
			case IntegrationviewPackage.DETAILS__ATTRIBUTE_PATH:
				setAttributePath((String) newValue);
				return;
			case IntegrationviewPackage.DETAILS__VALUE_PRIMARY_PLANNING:
				setValuePrimaryPlanning((String) newValue);
				return;
			case IntegrationviewPackage.DETAILS__VALUE_SECONDARY_PLANNING:
				setValueSecondaryPlanning((String) newValue);
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
			case IntegrationviewPackage.DETAILS__ATTRIBUTE_PATH:
				setAttributePath(ATTRIBUTE_PATH_EDEFAULT);
				return;
			case IntegrationviewPackage.DETAILS__VALUE_PRIMARY_PLANNING:
				setValuePrimaryPlanning(VALUE_PRIMARY_PLANNING_EDEFAULT);
				return;
			case IntegrationviewPackage.DETAILS__VALUE_SECONDARY_PLANNING:
				setValueSecondaryPlanning(VALUE_SECONDARY_PLANNING_EDEFAULT);
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
			case IntegrationviewPackage.DETAILS__ATTRIBUTE_PATH:
				return ATTRIBUTE_PATH_EDEFAULT == null ? attributePath != null
						: !ATTRIBUTE_PATH_EDEFAULT.equals(attributePath);
			case IntegrationviewPackage.DETAILS__VALUE_PRIMARY_PLANNING:
				return VALUE_PRIMARY_PLANNING_EDEFAULT == null
						? valuePrimaryPlanning != null
						: !VALUE_PRIMARY_PLANNING_EDEFAULT
								.equals(valuePrimaryPlanning);
			case IntegrationviewPackage.DETAILS__VALUE_SECONDARY_PLANNING:
				return VALUE_SECONDARY_PLANNING_EDEFAULT == null
						? valueSecondaryPlanning != null
						: !VALUE_SECONDARY_PLANNING_EDEFAULT
								.equals(valueSecondaryPlanning);
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
		result.append(" (attributePath: ");
		result.append(attributePath);
		result.append(", valuePrimaryPlanning: ");
		result.append(valuePrimaryPlanning);
		result.append(", valueSecondaryPlanning: ");
		result.append(valueSecondaryPlanning);
		result.append(')');
		return result.toString();
	}

} // DetailsImpl
