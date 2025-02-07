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
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.set.model.planpro.Basisobjekte.Bearbeitungsvermerk;
import org.eclipse.set.model.tablemodel.CompareFootnoteContainer;
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
	 * Footnotes</em>}' reference list. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getOldFootnotes()
	 * @generated
	 * @ordered
	 */
	protected EList<Bearbeitungsvermerk> oldFootnotes;

	/**
	 * The cached value of the '{@link #getNewFootnotes() <em>New
	 * Footnotes</em>}' reference list. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getNewFootnotes()
	 * @generated
	 * @ordered
	 */
	protected EList<Bearbeitungsvermerk> newFootnotes;

	/**
	 * The cached value of the '{@link #getUnchangedFootnotes() <em>Unchanged
	 * Footnotes</em>}' reference list. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getUnchangedFootnotes()
	 * @generated
	 * @ordered
	 */
	protected EList<Bearbeitungsvermerk> unchangedFootnotes;

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
	public EList<Bearbeitungsvermerk> getOldFootnotes() {
		if (oldFootnotes == null) {
			oldFootnotes = new EObjectResolvingEList<Bearbeitungsvermerk>(
					Bearbeitungsvermerk.class, this,
					TablemodelPackage.COMPARE_FOOTNOTE_CONTAINER__OLD_FOOTNOTES);
		}
		return oldFootnotes;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<Bearbeitungsvermerk> getNewFootnotes() {
		if (newFootnotes == null) {
			newFootnotes = new EObjectResolvingEList<Bearbeitungsvermerk>(
					Bearbeitungsvermerk.class, this,
					TablemodelPackage.COMPARE_FOOTNOTE_CONTAINER__NEW_FOOTNOTES);
		}
		return newFootnotes;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<Bearbeitungsvermerk> getUnchangedFootnotes() {
		if (unchangedFootnotes == null) {
			unchangedFootnotes = new EObjectResolvingEList<Bearbeitungsvermerk>(
					Bearbeitungsvermerk.class, this,
					TablemodelPackage.COMPARE_FOOTNOTE_CONTAINER__UNCHANGED_FOOTNOTES);
		}
		return unchangedFootnotes;
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
				return getOldFootnotes();
			case TablemodelPackage.COMPARE_FOOTNOTE_CONTAINER__NEW_FOOTNOTES:
				return getNewFootnotes();
			case TablemodelPackage.COMPARE_FOOTNOTE_CONTAINER__UNCHANGED_FOOTNOTES:
				return getUnchangedFootnotes();
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
				getOldFootnotes().clear();
				getOldFootnotes().addAll(
						(Collection<? extends Bearbeitungsvermerk>) newValue);
				return;
			case TablemodelPackage.COMPARE_FOOTNOTE_CONTAINER__NEW_FOOTNOTES:
				getNewFootnotes().clear();
				getNewFootnotes().addAll(
						(Collection<? extends Bearbeitungsvermerk>) newValue);
				return;
			case TablemodelPackage.COMPARE_FOOTNOTE_CONTAINER__UNCHANGED_FOOTNOTES:
				getUnchangedFootnotes().clear();
				getUnchangedFootnotes().addAll(
						(Collection<? extends Bearbeitungsvermerk>) newValue);
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
				getOldFootnotes().clear();
				return;
			case TablemodelPackage.COMPARE_FOOTNOTE_CONTAINER__NEW_FOOTNOTES:
				getNewFootnotes().clear();
				return;
			case TablemodelPackage.COMPARE_FOOTNOTE_CONTAINER__UNCHANGED_FOOTNOTES:
				getUnchangedFootnotes().clear();
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
				return oldFootnotes != null && !oldFootnotes.isEmpty();
			case TablemodelPackage.COMPARE_FOOTNOTE_CONTAINER__NEW_FOOTNOTES:
				return newFootnotes != null && !newFootnotes.isEmpty();
			case TablemodelPackage.COMPARE_FOOTNOTE_CONTAINER__UNCHANGED_FOOTNOTES:
				return unchangedFootnotes != null
						&& !unchangedFootnotes.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} // CompareFootnoteContainerImpl
