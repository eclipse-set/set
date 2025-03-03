/**
 * Copyright (c) 2021 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.siteplan.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.set.model.siteplan.Cant;
import org.eclipse.set.model.siteplan.CantPoint;
import org.eclipse.set.model.siteplan.SiteplanPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object
 * '<em><b>Cant</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.set.model.siteplan.impl.CantImpl#getPointA <em>Point A</em>}</li>
 *   <li>{@link org.eclipse.set.model.siteplan.impl.CantImpl#getPointB <em>Point B</em>}</li>
 *   <li>{@link org.eclipse.set.model.siteplan.impl.CantImpl#getForm <em>Form</em>}</li>
 *   <li>{@link org.eclipse.set.model.siteplan.impl.CantImpl#getLength <em>Length</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CantImpl extends SiteplanObjectImpl implements Cant {
	/**
	 * The cached value of the '{@link #getPointA() <em>Point A</em>}' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getPointA()
	 * @generated
	 * @ordered
	 */
	protected CantPoint pointA;

	/**
	 * The cached value of the '{@link #getPointB() <em>Point B</em>}' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getPointB()
	 * @generated
	 * @ordered
	 */
	protected CantPoint pointB;

	/**
	 * The default value of the '{@link #getForm() <em>Form</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getForm()
	 * @generated
	 * @ordered
	 */
	protected static final String FORM_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getForm() <em>Form</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getForm()
	 * @generated
	 * @ordered
	 */
	protected String form = FORM_EDEFAULT;

	/**
	 * The default value of the '{@link #getLength() <em>Length</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getLength()
	 * @generated
	 * @ordered
	 */
	protected static final double LENGTH_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getLength() <em>Length</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getLength()
	 * @generated
	 * @ordered
	 */
	protected double length = LENGTH_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected CantImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SiteplanPackage.Literals.CANT;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public CantPoint getPointA() {
		return pointA;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPointA(CantPoint newPointA,
			NotificationChain msgs) {
		CantPoint oldPointA = pointA;
		pointA = newPointA;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SiteplanPackage.CANT__POINT_A, oldPointA, newPointA);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setPointA(CantPoint newPointA) {
		if (newPointA != pointA) {
			NotificationChain msgs = null;
			if (pointA != null)
				msgs = ((InternalEObject)pointA).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - SiteplanPackage.CANT__POINT_A, null, msgs);
			if (newPointA != null)
				msgs = ((InternalEObject)newPointA).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - SiteplanPackage.CANT__POINT_A, null, msgs);
			msgs = basicSetPointA(newPointA, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SiteplanPackage.CANT__POINT_A, newPointA, newPointA));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public CantPoint getPointB() {
		return pointB;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPointB(CantPoint newPointB,
			NotificationChain msgs) {
		CantPoint oldPointB = pointB;
		pointB = newPointB;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SiteplanPackage.CANT__POINT_B, oldPointB, newPointB);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setPointB(CantPoint newPointB) {
		if (newPointB != pointB) {
			NotificationChain msgs = null;
			if (pointB != null)
				msgs = ((InternalEObject)pointB).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - SiteplanPackage.CANT__POINT_B, null, msgs);
			if (newPointB != null)
				msgs = ((InternalEObject)newPointB).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - SiteplanPackage.CANT__POINT_B, null, msgs);
			msgs = basicSetPointB(newPointB, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SiteplanPackage.CANT__POINT_B, newPointB, newPointB));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getForm() {
		return form;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setForm(String newForm) {
		String oldForm = form;
		form = newForm;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SiteplanPackage.CANT__FORM, oldForm, form));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public double getLength() {
		return length;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setLength(double newLength) {
		double oldLength = length;
		length = newLength;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SiteplanPackage.CANT__LENGTH, oldLength, length));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd,
			int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SiteplanPackage.CANT__POINT_A:
				return basicSetPointA(null, msgs);
			case SiteplanPackage.CANT__POINT_B:
				return basicSetPointB(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case SiteplanPackage.CANT__POINT_A:
				return getPointA();
			case SiteplanPackage.CANT__POINT_B:
				return getPointB();
			case SiteplanPackage.CANT__FORM:
				return getForm();
			case SiteplanPackage.CANT__LENGTH:
				return getLength();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case SiteplanPackage.CANT__POINT_A:
				setPointA((CantPoint)newValue);
				return;
			case SiteplanPackage.CANT__POINT_B:
				setPointB((CantPoint)newValue);
				return;
			case SiteplanPackage.CANT__FORM:
				setForm((String)newValue);
				return;
			case SiteplanPackage.CANT__LENGTH:
				setLength((Double)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case SiteplanPackage.CANT__POINT_A:
				setPointA((CantPoint)null);
				return;
			case SiteplanPackage.CANT__POINT_B:
				setPointB((CantPoint)null);
				return;
			case SiteplanPackage.CANT__FORM:
				setForm(FORM_EDEFAULT);
				return;
			case SiteplanPackage.CANT__LENGTH:
				setLength(LENGTH_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case SiteplanPackage.CANT__POINT_A:
				return pointA != null;
			case SiteplanPackage.CANT__POINT_B:
				return pointB != null;
			case SiteplanPackage.CANT__FORM:
				return FORM_EDEFAULT == null ? form != null : !FORM_EDEFAULT.equals(form);
			case SiteplanPackage.CANT__LENGTH:
				return length != LENGTH_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (form: ");
		result.append(form);
		result.append(", length: ");
		result.append(length);
		result.append(')');
		return result.toString();
	}

} // CantImpl
