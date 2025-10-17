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
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.set.model.tablemodel.CompareTableFootnoteContainer;
import org.eclipse.set.model.tablemodel.FootnoteContainer;
import org.eclipse.set.model.tablemodel.TablemodelPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Compare
 * Table Footnote Container</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.set.model.tablemodel.impl.CompareTableFootnoteContainerImpl#getMainPlanFootnoteContainer
 * <em>Main Plan Footnote Container</em>}</li>
 * <li>{@link org.eclipse.set.model.tablemodel.impl.CompareTableFootnoteContainerImpl#getComparePlanFootnoteContainer
 * <em>Compare Plan Footnote Container</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CompareTableFootnoteContainerImpl extends FootnoteContainerImpl
		implements CompareTableFootnoteContainer {
	/**
	 * The cached value of the '{@link #getMainPlanFootnoteContainer() <em>Main
	 * Plan Footnote Container</em>}' containment reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #getMainPlanFootnoteContainer()
	 * @generated
	 * @ordered
	 */
	protected FootnoteContainer mainPlanFootnoteContainer;

	/**
	 * The cached value of the '{@link #getComparePlanFootnoteContainer()
	 * <em>Compare Plan Footnote Container</em>}' reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #getComparePlanFootnoteContainer()
	 * @generated
	 * @ordered
	 */
	protected FootnoteContainer comparePlanFootnoteContainer;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected CompareTableFootnoteContainerImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TablemodelPackage.Literals.COMPARE_TABLE_FOOTNOTE_CONTAINER;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public FootnoteContainer getMainPlanFootnoteContainer() {
		return mainPlanFootnoteContainer;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetMainPlanFootnoteContainer(
			FootnoteContainer newMainPlanFootnoteContainer,
			NotificationChain msgs) {
		FootnoteContainer oldMainPlanFootnoteContainer = mainPlanFootnoteContainer;
		mainPlanFootnoteContainer = newMainPlanFootnoteContainer;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this,
					Notification.SET,
					TablemodelPackage.COMPARE_TABLE_FOOTNOTE_CONTAINER__MAIN_PLAN_FOOTNOTE_CONTAINER,
					oldMainPlanFootnoteContainer, newMainPlanFootnoteContainer);
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
	public void setMainPlanFootnoteContainer(
			FootnoteContainer newMainPlanFootnoteContainer) {
		if (newMainPlanFootnoteContainer != mainPlanFootnoteContainer) {
			NotificationChain msgs = null;
			if (mainPlanFootnoteContainer != null)
				msgs = ((InternalEObject) mainPlanFootnoteContainer)
						.eInverseRemove(this, EOPPOSITE_FEATURE_BASE
								- TablemodelPackage.COMPARE_TABLE_FOOTNOTE_CONTAINER__MAIN_PLAN_FOOTNOTE_CONTAINER,
								null, msgs);
			if (newMainPlanFootnoteContainer != null)
				msgs = ((InternalEObject) newMainPlanFootnoteContainer)
						.eInverseAdd(this, EOPPOSITE_FEATURE_BASE
								- TablemodelPackage.COMPARE_TABLE_FOOTNOTE_CONTAINER__MAIN_PLAN_FOOTNOTE_CONTAINER,
								null, msgs);
			msgs = basicSetMainPlanFootnoteContainer(
					newMainPlanFootnoteContainer, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					TablemodelPackage.COMPARE_TABLE_FOOTNOTE_CONTAINER__MAIN_PLAN_FOOTNOTE_CONTAINER,
					newMainPlanFootnoteContainer,
					newMainPlanFootnoteContainer));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public FootnoteContainer getComparePlanFootnoteContainer() {
		if (comparePlanFootnoteContainer != null
				&& comparePlanFootnoteContainer.eIsProxy()) {
			InternalEObject oldComparePlanFootnoteContainer = (InternalEObject) comparePlanFootnoteContainer;
			comparePlanFootnoteContainer = (FootnoteContainer) eResolveProxy(
					oldComparePlanFootnoteContainer);
			if (comparePlanFootnoteContainer != oldComparePlanFootnoteContainer) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							TablemodelPackage.COMPARE_TABLE_FOOTNOTE_CONTAINER__COMPARE_PLAN_FOOTNOTE_CONTAINER,
							oldComparePlanFootnoteContainer,
							comparePlanFootnoteContainer));
			}
		}
		return comparePlanFootnoteContainer;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public FootnoteContainer basicGetComparePlanFootnoteContainer() {
		return comparePlanFootnoteContainer;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setComparePlanFootnoteContainer(
			FootnoteContainer newComparePlanFootnoteContainer) {
		FootnoteContainer oldComparePlanFootnoteContainer = comparePlanFootnoteContainer;
		comparePlanFootnoteContainer = newComparePlanFootnoteContainer;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					TablemodelPackage.COMPARE_TABLE_FOOTNOTE_CONTAINER__COMPARE_PLAN_FOOTNOTE_CONTAINER,
					oldComparePlanFootnoteContainer,
					comparePlanFootnoteContainer));
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
			case TablemodelPackage.COMPARE_TABLE_FOOTNOTE_CONTAINER__MAIN_PLAN_FOOTNOTE_CONTAINER:
				return basicSetMainPlanFootnoteContainer(null, msgs);
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
			case TablemodelPackage.COMPARE_TABLE_FOOTNOTE_CONTAINER__MAIN_PLAN_FOOTNOTE_CONTAINER:
				return getMainPlanFootnoteContainer();
			case TablemodelPackage.COMPARE_TABLE_FOOTNOTE_CONTAINER__COMPARE_PLAN_FOOTNOTE_CONTAINER:
				if (resolve)
					return getComparePlanFootnoteContainer();
				return basicGetComparePlanFootnoteContainer();
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
			case TablemodelPackage.COMPARE_TABLE_FOOTNOTE_CONTAINER__MAIN_PLAN_FOOTNOTE_CONTAINER:
				setMainPlanFootnoteContainer((FootnoteContainer) newValue);
				return;
			case TablemodelPackage.COMPARE_TABLE_FOOTNOTE_CONTAINER__COMPARE_PLAN_FOOTNOTE_CONTAINER:
				setComparePlanFootnoteContainer((FootnoteContainer) newValue);
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
			case TablemodelPackage.COMPARE_TABLE_FOOTNOTE_CONTAINER__MAIN_PLAN_FOOTNOTE_CONTAINER:
				setMainPlanFootnoteContainer((FootnoteContainer) null);
				return;
			case TablemodelPackage.COMPARE_TABLE_FOOTNOTE_CONTAINER__COMPARE_PLAN_FOOTNOTE_CONTAINER:
				setComparePlanFootnoteContainer((FootnoteContainer) null);
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
			case TablemodelPackage.COMPARE_TABLE_FOOTNOTE_CONTAINER__MAIN_PLAN_FOOTNOTE_CONTAINER:
				return mainPlanFootnoteContainer != null;
			case TablemodelPackage.COMPARE_TABLE_FOOTNOTE_CONTAINER__COMPARE_PLAN_FOOTNOTE_CONTAINER:
				return comparePlanFootnoteContainer != null;
		}
		return super.eIsSet(featureID);
	}

} // CompareTableFootnoteContainerImpl
