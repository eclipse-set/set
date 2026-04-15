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
import org.eclipse.set.model.tablemodel.CompareFootnoteContainer;
import org.eclipse.set.model.tablemodel.SimpleFootnoteContainer;
import org.eclipse.set.model.tablemodel.TablemodelPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Compare
 * Footnote Container</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.set.model.tablemodel.impl.CompareFootnoteContainerImpl#getOldFootnotes
 * <em>Old Footnotes</em>}</li>
 * <li>{@link org.eclipse.set.model.tablemodel.impl.CompareFootnoteContainerImpl#getNewFootnotes
 * <em>New Footnotes</em>}</li>
 * <li>{@link org.eclipse.set.model.tablemodel.impl.CompareFootnoteContainerImpl#getUnchangedFootnotes
 * <em>Unchanged Footnotes</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CompareFootnoteContainerImpl extends FootnoteContainerImpl
		implements CompareFootnoteContainer {
	/**
	 * The cached value of the '{@link #getOldFootnotes() <em>Old
	 * Footnotes</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getOldFootnotes()
	 * @generated
	 * @ordered
	 */
	protected SimpleFootnoteContainer oldFootnotes;

	/**
	 * The cached value of the '{@link #getNewFootnotes() <em>New
	 * Footnotes</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getNewFootnotes()
	 * @generated
	 * @ordered
	 */
	protected SimpleFootnoteContainer newFootnotes;

	/**
	 * The cached value of the '{@link #getUnchangedFootnotes() <em>Unchanged
	 * Footnotes</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getUnchangedFootnotes()
	 * @generated
	 * @ordered
	 */
	protected SimpleFootnoteContainer unchangedFootnotes;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected CompareFootnoteContainerImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TablemodelPackage.Literals.COMPARE_FOOTNOTE_CONTAINER;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public SimpleFootnoteContainer getOldFootnotes() {
		if (oldFootnotes != null && oldFootnotes.eIsProxy()) {
			InternalEObject oldOldFootnotes = (InternalEObject) oldFootnotes;
			oldFootnotes = (SimpleFootnoteContainer) eResolveProxy(
					oldOldFootnotes);
			if (oldFootnotes != oldOldFootnotes) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							TablemodelPackage.COMPARE_FOOTNOTE_CONTAINER__OLD_FOOTNOTES,
							oldOldFootnotes, oldFootnotes));
			}
		}
		return oldFootnotes;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public SimpleFootnoteContainer basicGetOldFootnotes() {
		return oldFootnotes;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setOldFootnotes(SimpleFootnoteContainer newOldFootnotes) {
		SimpleFootnoteContainer oldOldFootnotes = oldFootnotes;
		oldFootnotes = newOldFootnotes;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					TablemodelPackage.COMPARE_FOOTNOTE_CONTAINER__OLD_FOOTNOTES,
					oldOldFootnotes, oldFootnotes));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public SimpleFootnoteContainer getNewFootnotes() {
		if (newFootnotes != null && newFootnotes.eIsProxy()) {
			InternalEObject oldNewFootnotes = (InternalEObject) newFootnotes;
			newFootnotes = (SimpleFootnoteContainer) eResolveProxy(
					oldNewFootnotes);
			if (newFootnotes != oldNewFootnotes) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							TablemodelPackage.COMPARE_FOOTNOTE_CONTAINER__NEW_FOOTNOTES,
							oldNewFootnotes, newFootnotes));
			}
		}
		return newFootnotes;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public SimpleFootnoteContainer basicGetNewFootnotes() {
		return newFootnotes;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setNewFootnotes(SimpleFootnoteContainer newNewFootnotes) {
		SimpleFootnoteContainer oldNewFootnotes = newFootnotes;
		newFootnotes = newNewFootnotes;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					TablemodelPackage.COMPARE_FOOTNOTE_CONTAINER__NEW_FOOTNOTES,
					oldNewFootnotes, newFootnotes));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public SimpleFootnoteContainer getUnchangedFootnotes() {
		if (unchangedFootnotes != null && unchangedFootnotes.eIsProxy()) {
			InternalEObject oldUnchangedFootnotes = (InternalEObject) unchangedFootnotes;
			unchangedFootnotes = (SimpleFootnoteContainer) eResolveProxy(
					oldUnchangedFootnotes);
			if (unchangedFootnotes != oldUnchangedFootnotes) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							TablemodelPackage.COMPARE_FOOTNOTE_CONTAINER__UNCHANGED_FOOTNOTES,
							oldUnchangedFootnotes, unchangedFootnotes));
			}
		}
		return unchangedFootnotes;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public SimpleFootnoteContainer basicGetUnchangedFootnotes() {
		return unchangedFootnotes;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setUnchangedFootnotes(
			SimpleFootnoteContainer newUnchangedFootnotes) {
		SimpleFootnoteContainer oldUnchangedFootnotes = unchangedFootnotes;
		unchangedFootnotes = newUnchangedFootnotes;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					TablemodelPackage.COMPARE_FOOTNOTE_CONTAINER__UNCHANGED_FOOTNOTES,
					oldUnchangedFootnotes, unchangedFootnotes));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TablemodelPackage.COMPARE_FOOTNOTE_CONTAINER__OLD_FOOTNOTES:
				if (resolve)
					return getOldFootnotes();
				return basicGetOldFootnotes();
			case TablemodelPackage.COMPARE_FOOTNOTE_CONTAINER__NEW_FOOTNOTES:
				if (resolve)
					return getNewFootnotes();
				return basicGetNewFootnotes();
			case TablemodelPackage.COMPARE_FOOTNOTE_CONTAINER__UNCHANGED_FOOTNOTES:
				if (resolve)
					return getUnchangedFootnotes();
				return basicGetUnchangedFootnotes();
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
			case TablemodelPackage.COMPARE_FOOTNOTE_CONTAINER__OLD_FOOTNOTES:
				setOldFootnotes((SimpleFootnoteContainer) newValue);
				return;
			case TablemodelPackage.COMPARE_FOOTNOTE_CONTAINER__NEW_FOOTNOTES:
				setNewFootnotes((SimpleFootnoteContainer) newValue);
				return;
			case TablemodelPackage.COMPARE_FOOTNOTE_CONTAINER__UNCHANGED_FOOTNOTES:
				setUnchangedFootnotes((SimpleFootnoteContainer) newValue);
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
			case TablemodelPackage.COMPARE_FOOTNOTE_CONTAINER__OLD_FOOTNOTES:
				setOldFootnotes((SimpleFootnoteContainer) null);
				return;
			case TablemodelPackage.COMPARE_FOOTNOTE_CONTAINER__NEW_FOOTNOTES:
				setNewFootnotes((SimpleFootnoteContainer) null);
				return;
			case TablemodelPackage.COMPARE_FOOTNOTE_CONTAINER__UNCHANGED_FOOTNOTES:
				setUnchangedFootnotes((SimpleFootnoteContainer) null);
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
			case TablemodelPackage.COMPARE_FOOTNOTE_CONTAINER__OLD_FOOTNOTES:
				return oldFootnotes != null;
			case TablemodelPackage.COMPARE_FOOTNOTE_CONTAINER__NEW_FOOTNOTES:
				return newFootnotes != null;
			case TablemodelPackage.COMPARE_FOOTNOTE_CONTAINER__UNCHANGED_FOOTNOTES:
				return unchangedFootnotes != null;
		}
		return super.eIsSet(featureID);
	}

} // CompareFootnoteContainerImpl
