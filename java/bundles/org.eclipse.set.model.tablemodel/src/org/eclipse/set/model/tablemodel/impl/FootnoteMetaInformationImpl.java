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
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.set.model.planpro.Basisobjekte.Bearbeitungsvermerk;
import org.eclipse.set.model.planpro.Basisobjekte.Ur_Objekt;

import org.eclipse.set.model.tablemodel.FootnoteMetaInformation;
import org.eclipse.set.model.tablemodel.TablemodelPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object
 * '<em><b>Footnote Meta Information</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.set.model.tablemodel.impl.FootnoteMetaInformationImpl#getOwnerObject
 * <em>Owner Object</em>}</li>
 * <li>{@link org.eclipse.set.model.tablemodel.impl.FootnoteMetaInformationImpl#getFootnote
 * <em>Footnote</em>}</li>
 * </ul>
 *
 * @generated
 */
public class FootnoteMetaInformationImpl extends MinimalEObjectImpl.Container
		implements FootnoteMetaInformation {
	/**
	 * The cached value of the '{@link #getOwnerObject() <em>Owner Object</em>}'
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getOwnerObject()
	 * @generated
	 * @ordered
	 */
	protected Ur_Objekt ownerObject;

	/**
	 * The cached value of the '{@link #getFootnote() <em>Footnote</em>}'
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getFootnote()
	 * @generated
	 * @ordered
	 */
	protected Bearbeitungsvermerk footnote;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected FootnoteMetaInformationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TablemodelPackage.Literals.FOOTNOTE_META_INFORMATION;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Ur_Objekt getOwnerObject() {
		if (ownerObject != null && ownerObject.eIsProxy()) {
			InternalEObject oldOwnerObject = (InternalEObject) ownerObject;
			ownerObject = (Ur_Objekt) eResolveProxy(oldOwnerObject);
			if (ownerObject != oldOwnerObject) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							TablemodelPackage.FOOTNOTE_META_INFORMATION__OWNER_OBJECT,
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
	public Ur_Objekt basicGetOwnerObject() {
		return ownerObject;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setOwnerObject(Ur_Objekt newOwnerObject) {
		Ur_Objekt oldOwnerObject = ownerObject;
		ownerObject = newOwnerObject;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					TablemodelPackage.FOOTNOTE_META_INFORMATION__OWNER_OBJECT,
					oldOwnerObject, ownerObject));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Bearbeitungsvermerk getFootnote() {
		if (footnote != null && footnote.eIsProxy()) {
			InternalEObject oldFootnote = (InternalEObject) footnote;
			footnote = (Bearbeitungsvermerk) eResolveProxy(oldFootnote);
			if (footnote != oldFootnote) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							TablemodelPackage.FOOTNOTE_META_INFORMATION__FOOTNOTE,
							oldFootnote, footnote));
			}
		}
		return footnote;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Bearbeitungsvermerk basicGetFootnote() {
		return footnote;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setFootnote(Bearbeitungsvermerk newFootnote) {
		Bearbeitungsvermerk oldFootnote = footnote;
		footnote = newFootnote;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					TablemodelPackage.FOOTNOTE_META_INFORMATION__FOOTNOTE,
					oldFootnote, footnote));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TablemodelPackage.FOOTNOTE_META_INFORMATION__OWNER_OBJECT:
				if (resolve)
					return getOwnerObject();
				return basicGetOwnerObject();
			case TablemodelPackage.FOOTNOTE_META_INFORMATION__FOOTNOTE:
				if (resolve)
					return getFootnote();
				return basicGetFootnote();
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
			case TablemodelPackage.FOOTNOTE_META_INFORMATION__OWNER_OBJECT:
				setOwnerObject((Ur_Objekt) newValue);
				return;
			case TablemodelPackage.FOOTNOTE_META_INFORMATION__FOOTNOTE:
				setFootnote((Bearbeitungsvermerk) newValue);
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
			case TablemodelPackage.FOOTNOTE_META_INFORMATION__OWNER_OBJECT:
				setOwnerObject((Ur_Objekt) null);
				return;
			case TablemodelPackage.FOOTNOTE_META_INFORMATION__FOOTNOTE:
				setFootnote((Bearbeitungsvermerk) null);
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
			case TablemodelPackage.FOOTNOTE_META_INFORMATION__OWNER_OBJECT:
				return ownerObject != null;
			case TablemodelPackage.FOOTNOTE_META_INFORMATION__FOOTNOTE:
				return footnote != null;
		}
		return super.eIsSet(featureID);
	}

} // FootnoteMetaInformationImpl
