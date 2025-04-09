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

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.set.model.tablemodel.MultiColorContent;
import org.eclipse.set.model.tablemodel.TablemodelPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Multi
 * Color Content</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.set.model.tablemodel.impl.MultiColorContentImpl#getMultiColorValue
 * <em>Multi Color Value</em>}</li>
 * <li>{@link org.eclipse.set.model.tablemodel.impl.MultiColorContentImpl#getStringFormat
 * <em>String Format</em>}</li>
 * <li>{@link org.eclipse.set.model.tablemodel.impl.MultiColorContentImpl#isDisableMultiColor
 * <em>Disable Multi Color</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MultiColorContentImpl extends MinimalEObjectImpl.Container
		implements MultiColorContent {
	/**
	 * The default value of the '{@link #getMultiColorValue() <em>Multi Color
	 * Value</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getMultiColorValue()
	 * @generated
	 * @ordered
	 */
	protected static final String MULTI_COLOR_VALUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getMultiColorValue() <em>Multi Color
	 * Value</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getMultiColorValue()
	 * @generated
	 * @ordered
	 */
	protected String multiColorValue = MULTI_COLOR_VALUE_EDEFAULT;

	/**
	 * The default value of the '{@link #getStringFormat() <em>String
	 * Format</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getStringFormat()
	 * @generated
	 * @ordered
	 */
	protected static final String STRING_FORMAT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getStringFormat() <em>String
	 * Format</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getStringFormat()
	 * @generated
	 * @ordered
	 */
	protected String stringFormat = STRING_FORMAT_EDEFAULT;

	/**
	 * The default value of the '{@link #isDisableMultiColor() <em>Disable Multi
	 * Color</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #isDisableMultiColor()
	 * @generated
	 * @ordered
	 */
	protected static final boolean DISABLE_MULTI_COLOR_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isDisableMultiColor() <em>Disable Multi
	 * Color</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #isDisableMultiColor()
	 * @generated
	 * @ordered
	 */
	protected boolean disableMultiColor = DISABLE_MULTI_COLOR_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected MultiColorContentImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TablemodelPackage.Literals.MULTI_COLOR_CONTENT;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getMultiColorValue() {
		return multiColorValue;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setMultiColorValue(String newMultiColorValue) {
		String oldMultiColorValue = multiColorValue;
		multiColorValue = newMultiColorValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					TablemodelPackage.MULTI_COLOR_CONTENT__MULTI_COLOR_VALUE,
					oldMultiColorValue, multiColorValue));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getStringFormat() {
		return stringFormat;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setStringFormat(String newStringFormat) {
		String oldStringFormat = stringFormat;
		stringFormat = newStringFormat;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					TablemodelPackage.MULTI_COLOR_CONTENT__STRING_FORMAT,
					oldStringFormat, stringFormat));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public boolean isDisableMultiColor() {
		return disableMultiColor;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setDisableMultiColor(boolean newDisableMultiColor) {
		boolean oldDisableMultiColor = disableMultiColor;
		disableMultiColor = newDisableMultiColor;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					TablemodelPackage.MULTI_COLOR_CONTENT__DISABLE_MULTI_COLOR,
					oldDisableMultiColor, disableMultiColor));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TablemodelPackage.MULTI_COLOR_CONTENT__MULTI_COLOR_VALUE:
				return getMultiColorValue();
			case TablemodelPackage.MULTI_COLOR_CONTENT__STRING_FORMAT:
				return getStringFormat();
			case TablemodelPackage.MULTI_COLOR_CONTENT__DISABLE_MULTI_COLOR:
				return isDisableMultiColor();
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
			case TablemodelPackage.MULTI_COLOR_CONTENT__MULTI_COLOR_VALUE:
				setMultiColorValue((String) newValue);
				return;
			case TablemodelPackage.MULTI_COLOR_CONTENT__STRING_FORMAT:
				setStringFormat((String) newValue);
				return;
			case TablemodelPackage.MULTI_COLOR_CONTENT__DISABLE_MULTI_COLOR:
				setDisableMultiColor((Boolean) newValue);
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
			case TablemodelPackage.MULTI_COLOR_CONTENT__MULTI_COLOR_VALUE:
				setMultiColorValue(MULTI_COLOR_VALUE_EDEFAULT);
				return;
			case TablemodelPackage.MULTI_COLOR_CONTENT__STRING_FORMAT:
				setStringFormat(STRING_FORMAT_EDEFAULT);
				return;
			case TablemodelPackage.MULTI_COLOR_CONTENT__DISABLE_MULTI_COLOR:
				setDisableMultiColor(DISABLE_MULTI_COLOR_EDEFAULT);
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
			case TablemodelPackage.MULTI_COLOR_CONTENT__MULTI_COLOR_VALUE:
				return MULTI_COLOR_VALUE_EDEFAULT == null
						? multiColorValue != null
						: !MULTI_COLOR_VALUE_EDEFAULT.equals(multiColorValue);
			case TablemodelPackage.MULTI_COLOR_CONTENT__STRING_FORMAT:
				return STRING_FORMAT_EDEFAULT == null ? stringFormat != null
						: !STRING_FORMAT_EDEFAULT.equals(stringFormat);
			case TablemodelPackage.MULTI_COLOR_CONTENT__DISABLE_MULTI_COLOR:
				return disableMultiColor != DISABLE_MULTI_COLOR_EDEFAULT;
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
		result.append(" (multiColorValue: ");
		result.append(multiColorValue);
		result.append(", stringFormat: ");
		result.append(stringFormat);
		result.append(", disableMultiColor: ");
		result.append(disableMultiColor);
		result.append(')');
		return result.toString();
	}

} // MultiColorContentImpl
