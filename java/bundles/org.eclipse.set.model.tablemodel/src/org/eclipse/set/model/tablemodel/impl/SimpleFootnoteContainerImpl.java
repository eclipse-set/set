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
import org.eclipse.set.model.tablemodel.Footnote;
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
	protected EList<Footnote> footnotes;

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
	public EList<Footnote> getFootnotes() {
		if (footnotes == null) {
			footnotes = new EObjectResolvingEList<Footnote>(Footnote.class,
					this,
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
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TablemodelPackage.SIMPLE_FOOTNOTE_CONTAINER__FOOTNOTES:
				return getFootnotes();
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
				getFootnotes()
						.addAll((Collection<? extends Footnote>) newValue);
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
		}
		return super.eIsSet(featureID);
	}

} // SimpleFootnoteContainerImpl
