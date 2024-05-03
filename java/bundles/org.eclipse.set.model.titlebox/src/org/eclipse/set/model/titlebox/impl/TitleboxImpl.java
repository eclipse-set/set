/**
 * Copyright (c) 2017 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.titlebox.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EDataTypeEList;

import org.eclipse.set.model.titlebox.PlanningOffice;
import org.eclipse.set.model.titlebox.Titlebox;
import org.eclipse.set.model.titlebox.TitleboxPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Titlebox</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.set.model.titlebox.impl.TitleboxImpl#getFieldList <em>Field</em>}</li>
 *   <li>{@link org.eclipse.set.model.titlebox.impl.TitleboxImpl#getPlanningOffice <em>Planning Office</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TitleboxImpl extends MinimalEObjectImpl.Container implements Titlebox {
	/**
	 * The cached value of the '{@link #getFieldList() <em>Field</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFieldList()
	 * @generated
	 * @ordered
	 */
	protected EList<String> field;

	/**
	 * The empty value for the '{@link #getField() <em>Field</em>}' array accessor.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getField()
	 * @generated
	 * @ordered
	 */
	protected static final String[] FIELD_EEMPTY_ARRAY = new String [0];

	/**
	 * The cached value of the '{@link #getPlanningOffice() <em>Planning Office</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPlanningOffice()
	 * @generated
	 * @ordered
	 */
	protected PlanningOffice planningOffice;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TitleboxImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TitleboxPackage.Literals.TITLEBOX;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String[] getField() {
		if (field == null || field.isEmpty()) return FIELD_EEMPTY_ARRAY;
		BasicEList<String> list = (BasicEList<String>)field;
		list.shrink();
		return (String[])list.data();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getField(int index) {
		return getFieldList().get(index);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getFieldLength() {
		return field == null ? 0 : field.size();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setField(String[] newField) {
		((BasicEList<String>)getFieldList()).setData(newField.length, newField);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setField(int index, String element) {
		getFieldList().set(index, element);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<String> getFieldList() {
		if (field == null) {
			field = new EDataTypeEList<String>(String.class, this, TitleboxPackage.TITLEBOX__FIELD);
		}
		return field;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public PlanningOffice getPlanningOffice() {
		return planningOffice;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPlanningOffice(PlanningOffice newPlanningOffice, NotificationChain msgs) {
		PlanningOffice oldPlanningOffice = planningOffice;
		planningOffice = newPlanningOffice;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TitleboxPackage.TITLEBOX__PLANNING_OFFICE, oldPlanningOffice, newPlanningOffice);
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
	public void setPlanningOffice(PlanningOffice newPlanningOffice) {
		if (newPlanningOffice != planningOffice) {
			NotificationChain msgs = null;
			if (planningOffice != null)
				msgs = ((InternalEObject)planningOffice).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TitleboxPackage.TITLEBOX__PLANNING_OFFICE, null, msgs);
			if (newPlanningOffice != null)
				msgs = ((InternalEObject)newPlanningOffice).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TitleboxPackage.TITLEBOX__PLANNING_OFFICE, null, msgs);
			msgs = basicSetPlanningOffice(newPlanningOffice, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TitleboxPackage.TITLEBOX__PLANNING_OFFICE, newPlanningOffice, newPlanningOffice));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TitleboxPackage.TITLEBOX__PLANNING_OFFICE:
				return basicSetPlanningOffice(null, msgs);
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
			case TitleboxPackage.TITLEBOX__FIELD:
				return getFieldList();
			case TitleboxPackage.TITLEBOX__PLANNING_OFFICE:
				return getPlanningOffice();
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
			case TitleboxPackage.TITLEBOX__FIELD:
				getFieldList().clear();
				getFieldList().addAll((Collection<? extends String>)newValue);
				return;
			case TitleboxPackage.TITLEBOX__PLANNING_OFFICE:
				setPlanningOffice((PlanningOffice)newValue);
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
			case TitleboxPackage.TITLEBOX__FIELD:
				getFieldList().clear();
				return;
			case TitleboxPackage.TITLEBOX__PLANNING_OFFICE:
				setPlanningOffice((PlanningOffice)null);
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
			case TitleboxPackage.TITLEBOX__FIELD:
				return field != null && !field.isEmpty();
			case TitleboxPackage.TITLEBOX__PLANNING_OFFICE:
				return planningOffice != null;
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
		result.append(" (field: ");
		result.append(field);
		result.append(')');
		return result.toString();
	}

} //TitleboxImpl
