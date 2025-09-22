/**
 * Copyright (c) 2022 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.tablemodel.format.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.set.model.tablemodel.format.CellFormat;
import org.eclipse.set.model.tablemodel.format.TableformatPackage;
import org.eclipse.set.model.tablemodel.format.TextAlignment;

import org.eclipse.set.model.tablemodel.impl.CellAnnotationImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Cell
 * Format</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.set.model.tablemodel.format.impl.CellFormatImpl#getTextAlignment
 * <em>Text Alignment</em>}</li>
 * <li>{@link org.eclipse.set.model.tablemodel.format.impl.CellFormatImpl#isTopologicalCalculation
 * <em>Topological Calculation</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CellFormatImpl extends CellAnnotationImpl implements CellFormat {
	/**
	 * The default value of the '{@link #getTextAlignment() <em>Text
	 * Alignment</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getTextAlignment()
	 * @generated
	 * @ordered
	 */
	protected static final TextAlignment TEXT_ALIGNMENT_EDEFAULT = TextAlignment.CENTER;

	/**
	 * The cached value of the '{@link #getTextAlignment() <em>Text
	 * Alignment</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getTextAlignment()
	 * @generated
	 * @ordered
	 */
	protected TextAlignment textAlignment = TEXT_ALIGNMENT_EDEFAULT;

	/**
	 * The default value of the '{@link #isTopologicalCalculation()
	 * <em>Topological Calculation</em>}' attribute. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #isTopologicalCalculation()
	 * @generated
	 * @ordered
	 */
	protected static final boolean TOPOLOGICAL_CALCULATION_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isTopologicalCalculation()
	 * <em>Topological Calculation</em>}' attribute. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #isTopologicalCalculation()
	 * @generated
	 * @ordered
	 */
	protected boolean topologicalCalculation = TOPOLOGICAL_CALCULATION_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected CellFormatImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TableformatPackage.Literals.CELL_FORMAT;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public TextAlignment getTextAlignment() {
		return textAlignment;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setTextAlignment(TextAlignment newTextAlignment) {
		TextAlignment oldTextAlignment = textAlignment;
		textAlignment = newTextAlignment == null ? TEXT_ALIGNMENT_EDEFAULT
				: newTextAlignment;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					TableformatPackage.CELL_FORMAT__TEXT_ALIGNMENT,
					oldTextAlignment, textAlignment));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public boolean isTopologicalCalculation() {
		return topologicalCalculation;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setTopologicalCalculation(boolean newTopologicalCalculation) {
		boolean oldTopologicalCalculation = topologicalCalculation;
		topologicalCalculation = newTopologicalCalculation;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					TableformatPackage.CELL_FORMAT__TOPOLOGICAL_CALCULATION,
					oldTopologicalCalculation, topologicalCalculation));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TableformatPackage.CELL_FORMAT__TEXT_ALIGNMENT:
				return getTextAlignment();
			case TableformatPackage.CELL_FORMAT__TOPOLOGICAL_CALCULATION:
				return isTopologicalCalculation();
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
			case TableformatPackage.CELL_FORMAT__TEXT_ALIGNMENT:
				setTextAlignment((TextAlignment) newValue);
				return;
			case TableformatPackage.CELL_FORMAT__TOPOLOGICAL_CALCULATION:
				setTopologicalCalculation((Boolean) newValue);
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
			case TableformatPackage.CELL_FORMAT__TEXT_ALIGNMENT:
				setTextAlignment(TEXT_ALIGNMENT_EDEFAULT);
				return;
			case TableformatPackage.CELL_FORMAT__TOPOLOGICAL_CALCULATION:
				setTopologicalCalculation(TOPOLOGICAL_CALCULATION_EDEFAULT);
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
			case TableformatPackage.CELL_FORMAT__TEXT_ALIGNMENT:
				return textAlignment != TEXT_ALIGNMENT_EDEFAULT;
			case TableformatPackage.CELL_FORMAT__TOPOLOGICAL_CALCULATION:
				return topologicalCalculation != TOPOLOGICAL_CALCULATION_EDEFAULT;
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
		result.append(" (textAlignment: ");
		result.append(textAlignment);
		result.append(", topologicalCalculation: ");
		result.append(topologicalCalculation);
		result.append(')');
		return result.toString();
	}

} // CellFormatImpl
