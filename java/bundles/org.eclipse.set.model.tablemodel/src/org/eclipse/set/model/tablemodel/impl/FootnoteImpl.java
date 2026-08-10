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
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.set.model.planpro.Basisobjekte.Bearbeitungsvermerk;

import org.eclipse.set.model.tablemodel.Footnote;
import org.eclipse.set.model.tablemodel.TablemodelPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object
 * '<em><b>Footnote</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.set.model.tablemodel.impl.FootnoteImpl#getOwnerObject
 * <em>Owner Object</em>}</li>
 * <li>{@link org.eclipse.set.model.tablemodel.impl.FootnoteImpl#getBearbeitungsvermerk
 * <em>Bearbeitungsvermerk</em>}</li>
 * <li>{@link org.eclipse.set.model.tablemodel.impl.FootnoteImpl#getReferenceColumn
 * <em>Reference Column</em>}</li>
 * </ul>
 *
 * @generated
 */
public class FootnoteImpl extends MinimalEObjectImpl.Container
		implements Footnote {
	/**
	 * The cached value of the '{@link #getOwnerObject() <em>Owner Object</em>}'
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getOwnerObject()
	 * @generated
	 * @ordered
	 */
	protected EObject ownerObject;

	/**
	 * The cached value of the '{@link #getBearbeitungsvermerk()
	 * <em>Bearbeitungsvermerk</em>}' reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getBearbeitungsvermerk()
	 * @generated
	 * @ordered
	 */
	protected Bearbeitungsvermerk bearbeitungsvermerk;

	/**
	 * The default value of the '{@link #getReferenceColumn() <em>Reference
	 * Column</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getReferenceColumn()
	 * @generated
	 * @ordered
	 */
	protected static final String REFERENCE_COLUMN_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getReferenceColumn() <em>Reference
	 * Column</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getReferenceColumn()
	 * @generated
	 * @ordered
	 */
	protected String referenceColumn = REFERENCE_COLUMN_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected FootnoteImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TablemodelPackage.Literals.FOOTNOTE;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EObject getOwnerObject() {
		if (ownerObject != null && ownerObject.eIsProxy()) {
			InternalEObject oldOwnerObject = (InternalEObject) ownerObject;
			ownerObject = eResolveProxy(oldOwnerObject);
			if (ownerObject != oldOwnerObject) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							TablemodelPackage.FOOTNOTE__OWNER_OBJECT,
							oldOwnerObject, ownerObject));
			}
		}
		return ownerObject;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EObject basicGetOwnerObject() {
		return ownerObject;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setOwnerObject(EObject newOwnerObject) {
		EObject oldOwnerObject = ownerObject;
		ownerObject = newOwnerObject;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					TablemodelPackage.FOOTNOTE__OWNER_OBJECT, oldOwnerObject,
					ownerObject));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Bearbeitungsvermerk getBearbeitungsvermerk() {
		if (bearbeitungsvermerk != null && bearbeitungsvermerk.eIsProxy()) {
			InternalEObject oldBearbeitungsvermerk = (InternalEObject) bearbeitungsvermerk;
			bearbeitungsvermerk = (Bearbeitungsvermerk) eResolveProxy(
					oldBearbeitungsvermerk);
			if (bearbeitungsvermerk != oldBearbeitungsvermerk) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							TablemodelPackage.FOOTNOTE__BEARBEITUNGSVERMERK,
							oldBearbeitungsvermerk, bearbeitungsvermerk));
			}
		}
		return bearbeitungsvermerk;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Bearbeitungsvermerk basicGetBearbeitungsvermerk() {
		return bearbeitungsvermerk;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setBearbeitungsvermerk(
			Bearbeitungsvermerk newBearbeitungsvermerk) {
		Bearbeitungsvermerk oldBearbeitungsvermerk = bearbeitungsvermerk;
		bearbeitungsvermerk = newBearbeitungsvermerk;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					TablemodelPackage.FOOTNOTE__BEARBEITUNGSVERMERK,
					oldBearbeitungsvermerk, bearbeitungsvermerk));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getReferenceColumn() {
		return referenceColumn;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setReferenceColumn(String newReferenceColumn) {
		String oldReferenceColumn = referenceColumn;
		referenceColumn = newReferenceColumn;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					TablemodelPackage.FOOTNOTE__REFERENCE_COLUMN,
					oldReferenceColumn, referenceColumn));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TablemodelPackage.FOOTNOTE__OWNER_OBJECT:
				if (resolve)
					return getOwnerObject();
				return basicGetOwnerObject();
			case TablemodelPackage.FOOTNOTE__BEARBEITUNGSVERMERK:
				if (resolve)
					return getBearbeitungsvermerk();
				return basicGetBearbeitungsvermerk();
			case TablemodelPackage.FOOTNOTE__REFERENCE_COLUMN:
				return getReferenceColumn();
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
			case TablemodelPackage.FOOTNOTE__OWNER_OBJECT:
				setOwnerObject((EObject) newValue);
				return;
			case TablemodelPackage.FOOTNOTE__BEARBEITUNGSVERMERK:
				setBearbeitungsvermerk((Bearbeitungsvermerk) newValue);
				return;
			case TablemodelPackage.FOOTNOTE__REFERENCE_COLUMN:
				setReferenceColumn((String) newValue);
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
			case TablemodelPackage.FOOTNOTE__OWNER_OBJECT:
				setOwnerObject((EObject) null);
				return;
			case TablemodelPackage.FOOTNOTE__BEARBEITUNGSVERMERK:
				setBearbeitungsvermerk((Bearbeitungsvermerk) null);
				return;
			case TablemodelPackage.FOOTNOTE__REFERENCE_COLUMN:
				setReferenceColumn(REFERENCE_COLUMN_EDEFAULT);
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
			case TablemodelPackage.FOOTNOTE__OWNER_OBJECT:
				return ownerObject != null;
			case TablemodelPackage.FOOTNOTE__BEARBEITUNGSVERMERK:
				return bearbeitungsvermerk != null;
			case TablemodelPackage.FOOTNOTE__REFERENCE_COLUMN:
				return REFERENCE_COLUMN_EDEFAULT == null
						? referenceColumn != null
						: !REFERENCE_COLUMN_EDEFAULT.equals(referenceColumn);
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
		result.append(" (referenceColumn: ");
		result.append(referenceColumn);
		result.append(')');
		return result.toString();
	}

} // FootnoteImpl
