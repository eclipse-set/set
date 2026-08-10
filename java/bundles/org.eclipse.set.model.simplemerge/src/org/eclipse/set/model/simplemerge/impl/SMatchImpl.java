/**
 * Copyright (c) 2018 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.simplemerge.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.set.model.simplemerge.Resolution;
import org.eclipse.set.model.simplemerge.SMatch;
import org.eclipse.set.model.simplemerge.SimplemergePackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object
 * '<em><b>SMatch</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.set.model.simplemerge.impl.SMatchImpl#getId
 * <em>Id</em>}</li>
 * <li>{@link org.eclipse.set.model.simplemerge.impl.SMatchImpl#getGuidPrimary
 * <em>Guid Primary</em>}</li>
 * <li>{@link org.eclipse.set.model.simplemerge.impl.SMatchImpl#getGuidSecondary
 * <em>Guid Secondary</em>}</li>
 * <li>{@link org.eclipse.set.model.simplemerge.impl.SMatchImpl#getResolution
 * <em>Resolution</em>}</li>
 * <li>{@link org.eclipse.set.model.simplemerge.impl.SMatchImpl#getElementType
 * <em>Element Type</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SMatchImpl extends MinimalEObjectImpl.Container implements SMatch {
	/**
	 * The default value of the '{@link #getId() <em>Id</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected static final int ID_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getId() <em>Id</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected int id = ID_EDEFAULT;

	/**
	 * The default value of the '{@link #getGuidPrimary() <em>Guid
	 * Primary</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getGuidPrimary()
	 * @generated
	 * @ordered
	 */
	protected static final String GUID_PRIMARY_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getGuidPrimary() <em>Guid Primary</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getGuidPrimary()
	 * @generated
	 * @ordered
	 */
	protected String guidPrimary = GUID_PRIMARY_EDEFAULT;

	/**
	 * The default value of the '{@link #getGuidSecondary() <em>Guid
	 * Secondary</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getGuidSecondary()
	 * @generated
	 * @ordered
	 */
	protected static final String GUID_SECONDARY_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getGuidSecondary() <em>Guid
	 * Secondary</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getGuidSecondary()
	 * @generated
	 * @ordered
	 */
	protected String guidSecondary = GUID_SECONDARY_EDEFAULT;

	/**
	 * The default value of the '{@link #getResolution() <em>Resolution</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getResolution()
	 * @generated
	 * @ordered
	 */
	protected static final Resolution RESOLUTION_EDEFAULT = Resolution.PRIMARY_UNRESOLVED;

	/**
	 * The cached value of the '{@link #getResolution() <em>Resolution</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getResolution()
	 * @generated
	 * @ordered
	 */
	protected Resolution resolution = RESOLUTION_EDEFAULT;

	/**
	 * The default value of the '{@link #getElementType() <em>Element
	 * Type</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getElementType()
	 * @generated
	 * @ordered
	 */
	protected static final String ELEMENT_TYPE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getElementType() <em>Element Type</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getElementType()
	 * @generated
	 * @ordered
	 */
	protected String elementType = ELEMENT_TYPE_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected SMatchImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SimplemergePackage.Literals.SMATCH;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public int getId() {
		return id;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setId(int newId) {
		int oldId = id;
		id = newId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					SimplemergePackage.SMATCH__ID, oldId, id));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getGuidPrimary() {
		return guidPrimary;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setGuidPrimary(String newGuidPrimary) {
		String oldGuidPrimary = guidPrimary;
		guidPrimary = newGuidPrimary;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					SimplemergePackage.SMATCH__GUID_PRIMARY, oldGuidPrimary,
					guidPrimary));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getGuidSecondary() {
		return guidSecondary;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setGuidSecondary(String newGuidSecondary) {
		String oldGuidSecondary = guidSecondary;
		guidSecondary = newGuidSecondary;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					SimplemergePackage.SMATCH__GUID_SECONDARY, oldGuidSecondary,
					guidSecondary));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Resolution getResolution() {
		return resolution;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setResolution(Resolution newResolution) {
		Resolution oldResolution = resolution;
		resolution = newResolution == null ? RESOLUTION_EDEFAULT
				: newResolution;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					SimplemergePackage.SMATCH__RESOLUTION, oldResolution,
					resolution));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getElementType() {
		return elementType;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setElementType(String newElementType) {
		String oldElementType = elementType;
		elementType = newElementType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					SimplemergePackage.SMATCH__ELEMENT_TYPE, oldElementType,
					elementType));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case SimplemergePackage.SMATCH__ID:
				return getId();
			case SimplemergePackage.SMATCH__GUID_PRIMARY:
				return getGuidPrimary();
			case SimplemergePackage.SMATCH__GUID_SECONDARY:
				return getGuidSecondary();
			case SimplemergePackage.SMATCH__RESOLUTION:
				return getResolution();
			case SimplemergePackage.SMATCH__ELEMENT_TYPE:
				return getElementType();
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
			case SimplemergePackage.SMATCH__ID:
				setId((Integer) newValue);
				return;
			case SimplemergePackage.SMATCH__GUID_PRIMARY:
				setGuidPrimary((String) newValue);
				return;
			case SimplemergePackage.SMATCH__GUID_SECONDARY:
				setGuidSecondary((String) newValue);
				return;
			case SimplemergePackage.SMATCH__RESOLUTION:
				setResolution((Resolution) newValue);
				return;
			case SimplemergePackage.SMATCH__ELEMENT_TYPE:
				setElementType((String) newValue);
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
			case SimplemergePackage.SMATCH__ID:
				setId(ID_EDEFAULT);
				return;
			case SimplemergePackage.SMATCH__GUID_PRIMARY:
				setGuidPrimary(GUID_PRIMARY_EDEFAULT);
				return;
			case SimplemergePackage.SMATCH__GUID_SECONDARY:
				setGuidSecondary(GUID_SECONDARY_EDEFAULT);
				return;
			case SimplemergePackage.SMATCH__RESOLUTION:
				setResolution(RESOLUTION_EDEFAULT);
				return;
			case SimplemergePackage.SMATCH__ELEMENT_TYPE:
				setElementType(ELEMENT_TYPE_EDEFAULT);
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
			case SimplemergePackage.SMATCH__ID:
				return id != ID_EDEFAULT;
			case SimplemergePackage.SMATCH__GUID_PRIMARY:
				return GUID_PRIMARY_EDEFAULT == null ? guidPrimary != null
						: !GUID_PRIMARY_EDEFAULT.equals(guidPrimary);
			case SimplemergePackage.SMATCH__GUID_SECONDARY:
				return GUID_SECONDARY_EDEFAULT == null ? guidSecondary != null
						: !GUID_SECONDARY_EDEFAULT.equals(guidSecondary);
			case SimplemergePackage.SMATCH__RESOLUTION:
				return resolution != RESOLUTION_EDEFAULT;
			case SimplemergePackage.SMATCH__ELEMENT_TYPE:
				return ELEMENT_TYPE_EDEFAULT == null ? elementType != null
						: !ELEMENT_TYPE_EDEFAULT.equals(elementType);
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
		result.append(" (id: ");
		result.append(id);
		result.append(", guidPrimary: ");
		result.append(guidPrimary);
		result.append(", guidSecondary: ");
		result.append(guidSecondary);
		result.append(", resolution: ");
		result.append(resolution);
		result.append(", elementType: ");
		result.append(elementType);
		result.append(')');
		return result.toString();
	}

} // SMatchImpl
