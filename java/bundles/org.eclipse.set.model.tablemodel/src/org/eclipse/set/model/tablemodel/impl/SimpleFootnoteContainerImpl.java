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
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.set.model.planpro.Basisobjekte.Bearbeitungsvermerk;
import org.eclipse.set.model.planpro.Basisobjekte.Ur_Objekt;
import org.eclipse.set.model.tablemodel.SimpleFootnoteContainer;
import org.eclipse.set.model.tablemodel.TablemodelPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Simple
 * Footnote Container</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.set.model.tablemodel.impl.SimpleFootnoteContainerImpl#getFootnotes
 * <em>Footnotes</em>}</li>
 * <li>{@link org.eclipse.set.model.tablemodel.impl.SimpleFootnoteContainerImpl#getOwnerObject
 * <em>Owner Object</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SimpleFootnoteContainerImpl extends FootnoteContainerImpl
		implements SimpleFootnoteContainer {
	/**
	 * The cached value of the '{@link #getFootnotes() <em>Footnotes</em>}'
	 * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getFootnotes()
	 * @generated
	 * @ordered
	 */
	protected EList<Bearbeitungsvermerk> footnotes;

	/**
	 * The cached value of the '{@link #getOwnerObject() <em>Owner Object</em>}'
	 * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getOwnerObject()
	 * @generated
	 * @ordered
	 */
	protected Ur_Objekt ownerObject;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected SimpleFootnoteContainerImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<Bearbeitungsvermerk> getFootnotes() {
		if (footnotes == null) {
			footnotes = new EObjectResolvingEList<Bearbeitungsvermerk>(
					Bearbeitungsvermerk.class, this,
					TablemodelPackage.SIMPLE_FOOTNOTE_CONTAINER__FOOTNOTES);
		}
		return footnotes;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Ur_Objekt getOwnerObject() {
		return ownerObject;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetOwnerObject(Ur_Objekt newOwnerObject,
			NotificationChain msgs) {
		Ur_Objekt oldOwnerObject = ownerObject;
		ownerObject = newOwnerObject;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this,
					Notification.SET,
					TablemodelPackage.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
					oldOwnerObject, newOwnerObject);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setOwnerObject(Ur_Objekt newOwnerObject) {
		if (newOwnerObject != ownerObject) {
			NotificationChain msgs = null;
			if (ownerObject != null)
				msgs = ((InternalEObject) ownerObject).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE
								- TablemodelPackage.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
						null, msgs);
			if (newOwnerObject != null)
				msgs = ((InternalEObject) newOwnerObject).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE
								- TablemodelPackage.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
						null, msgs);
			msgs = basicSetOwnerObject(newOwnerObject, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					TablemodelPackage.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
					newOwnerObject, newOwnerObject));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd,
			int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TablemodelPackage.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT:
				return basicSetOwnerObject(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TablemodelPackage.SIMPLE_FOOTNOTE_CONTAINER__FOOTNOTES:
				return getFootnotes();
			case TablemodelPackage.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT:
				return getOwnerObject();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case TablemodelPackage.SIMPLE_FOOTNOTE_CONTAINER__FOOTNOTES:
				getFootnotes().clear();
				getFootnotes().addAll(
						(Collection<? extends Bearbeitungsvermerk>) newValue);
				return;
			case TablemodelPackage.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT:
				setOwnerObject((Ur_Objekt) newValue);
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
			case TablemodelPackage.SIMPLE_FOOTNOTE_CONTAINER__FOOTNOTES:
				getFootnotes().clear();
				return;
			case TablemodelPackage.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT:
				setOwnerObject((Ur_Objekt) null);
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
			case TablemodelPackage.SIMPLE_FOOTNOTE_CONTAINER__FOOTNOTES:
				return footnotes != null && !footnotes.isEmpty();
			case TablemodelPackage.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT:
				return ownerObject != null;
		}
		return super.eIsSet(featureID);
	}

} // SimpleFootnoteContainerImpl
