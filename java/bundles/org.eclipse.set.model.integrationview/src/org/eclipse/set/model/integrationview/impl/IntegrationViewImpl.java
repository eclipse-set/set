/**
 * Copyright (c) 2018 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.integrationview.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.set.model.integrationview.Conflict;
import org.eclipse.set.model.integrationview.IntegrationView;
import org.eclipse.set.model.integrationview.IntegrationviewPackage;
import org.eclipse.set.model.integrationview.ObjectQuantity;

/**
 * <!-- begin-user-doc --> An implementation of the model object
 * '<em><b>Integration View</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.set.model.integrationview.impl.IntegrationViewImpl#getPrimaryPlanning
 * <em>Primary Planning</em>}</li>
 * <li>{@link org.eclipse.set.model.integrationview.impl.IntegrationViewImpl#getSecondaryPlanning
 * <em>Secondary Planning</em>}</li>
 * <li>{@link org.eclipse.set.model.integrationview.impl.IntegrationViewImpl#getCompositePlanning
 * <em>Composite Planning</em>}</li>
 * <li>{@link org.eclipse.set.model.integrationview.impl.IntegrationViewImpl#getObjectquantities
 * <em>Objectquantities</em>}</li>
 * <li>{@link org.eclipse.set.model.integrationview.impl.IntegrationViewImpl#getConflicts
 * <em>Conflicts</em>}</li>
 * <li>{@link org.eclipse.set.model.integrationview.impl.IntegrationViewImpl#getIntegrationDirectory
 * <em>Integration Directory</em>}</li>
 * </ul>
 *
 * @generated
 */
public class IntegrationViewImpl extends MinimalEObjectImpl.Container
		implements IntegrationView {
	/**
	 * The default value of the '{@link #getPrimaryPlanning() <em>Primary
	 * Planning</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getPrimaryPlanning()
	 * @generated
	 * @ordered
	 */
	protected static final String PRIMARY_PLANNING_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPrimaryPlanning() <em>Primary
	 * Planning</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getPrimaryPlanning()
	 * @generated
	 * @ordered
	 */
	protected String primaryPlanning = PRIMARY_PLANNING_EDEFAULT;

	/**
	 * The default value of the '{@link #getSecondaryPlanning() <em>Secondary
	 * Planning</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getSecondaryPlanning()
	 * @generated
	 * @ordered
	 */
	protected static final String SECONDARY_PLANNING_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSecondaryPlanning() <em>Secondary
	 * Planning</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getSecondaryPlanning()
	 * @generated
	 * @ordered
	 */
	protected String secondaryPlanning = SECONDARY_PLANNING_EDEFAULT;

	/**
	 * The default value of the '{@link #getCompositePlanning() <em>Composite
	 * Planning</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getCompositePlanning()
	 * @generated
	 * @ordered
	 */
	protected static final String COMPOSITE_PLANNING_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCompositePlanning() <em>Composite
	 * Planning</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getCompositePlanning()
	 * @generated
	 * @ordered
	 */
	protected String compositePlanning = COMPOSITE_PLANNING_EDEFAULT;

	/**
	 * The cached value of the '{@link #getObjectquantities()
	 * <em>Objectquantities</em>}' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getObjectquantities()
	 * @generated
	 * @ordered
	 */
	protected EList<ObjectQuantity> objectquantities;

	/**
	 * The cached value of the '{@link #getConflicts() <em>Conflicts</em>}'
	 * containment reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getConflicts()
	 * @generated
	 * @ordered
	 */
	protected EList<Conflict> conflicts;

	/**
	 * The default value of the '{@link #getIntegrationDirectory()
	 * <em>Integration Directory</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getIntegrationDirectory()
	 * @generated
	 * @ordered
	 */
	protected static final String INTEGRATION_DIRECTORY_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getIntegrationDirectory()
	 * <em>Integration Directory</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getIntegrationDirectory()
	 * @generated
	 * @ordered
	 */
	protected String integrationDirectory = INTEGRATION_DIRECTORY_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected IntegrationViewImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IntegrationviewPackage.Literals.INTEGRATION_VIEW;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getPrimaryPlanning() {
		return primaryPlanning;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setPrimaryPlanning(String newPrimaryPlanning) {
		String oldPrimaryPlanning = primaryPlanning;
		primaryPlanning = newPrimaryPlanning;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IntegrationviewPackage.INTEGRATION_VIEW__PRIMARY_PLANNING,
					oldPrimaryPlanning, primaryPlanning));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getSecondaryPlanning() {
		return secondaryPlanning;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setSecondaryPlanning(String newSecondaryPlanning) {
		String oldSecondaryPlanning = secondaryPlanning;
		secondaryPlanning = newSecondaryPlanning;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IntegrationviewPackage.INTEGRATION_VIEW__SECONDARY_PLANNING,
					oldSecondaryPlanning, secondaryPlanning));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getCompositePlanning() {
		return compositePlanning;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setCompositePlanning(String newCompositePlanning) {
		String oldCompositePlanning = compositePlanning;
		compositePlanning = newCompositePlanning;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IntegrationviewPackage.INTEGRATION_VIEW__COMPOSITE_PLANNING,
					oldCompositePlanning, compositePlanning));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<ObjectQuantity> getObjectquantities() {
		if (objectquantities == null) {
			objectquantities = new EObjectContainmentEList<ObjectQuantity>(
					ObjectQuantity.class, this,
					IntegrationviewPackage.INTEGRATION_VIEW__OBJECTQUANTITIES);
		}
		return objectquantities;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<Conflict> getConflicts() {
		if (conflicts == null) {
			conflicts = new EObjectContainmentEList<Conflict>(Conflict.class,
					this, IntegrationviewPackage.INTEGRATION_VIEW__CONFLICTS);
		}
		return conflicts;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getIntegrationDirectory() {
		return integrationDirectory;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setIntegrationDirectory(String newIntegrationDirectory) {
		String oldIntegrationDirectory = integrationDirectory;
		integrationDirectory = newIntegrationDirectory;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IntegrationviewPackage.INTEGRATION_VIEW__INTEGRATION_DIRECTORY,
					oldIntegrationDirectory, integrationDirectory));
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
			case IntegrationviewPackage.INTEGRATION_VIEW__OBJECTQUANTITIES:
				return ((InternalEList<?>) getObjectquantities())
						.basicRemove(otherEnd, msgs);
			case IntegrationviewPackage.INTEGRATION_VIEW__CONFLICTS:
				return ((InternalEList<?>) getConflicts()).basicRemove(otherEnd,
						msgs);
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
			case IntegrationviewPackage.INTEGRATION_VIEW__PRIMARY_PLANNING:
				return getPrimaryPlanning();
			case IntegrationviewPackage.INTEGRATION_VIEW__SECONDARY_PLANNING:
				return getSecondaryPlanning();
			case IntegrationviewPackage.INTEGRATION_VIEW__COMPOSITE_PLANNING:
				return getCompositePlanning();
			case IntegrationviewPackage.INTEGRATION_VIEW__OBJECTQUANTITIES:
				return getObjectquantities();
			case IntegrationviewPackage.INTEGRATION_VIEW__CONFLICTS:
				return getConflicts();
			case IntegrationviewPackage.INTEGRATION_VIEW__INTEGRATION_DIRECTORY:
				return getIntegrationDirectory();
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
			case IntegrationviewPackage.INTEGRATION_VIEW__PRIMARY_PLANNING:
				setPrimaryPlanning((String) newValue);
				return;
			case IntegrationviewPackage.INTEGRATION_VIEW__SECONDARY_PLANNING:
				setSecondaryPlanning((String) newValue);
				return;
			case IntegrationviewPackage.INTEGRATION_VIEW__COMPOSITE_PLANNING:
				setCompositePlanning((String) newValue);
				return;
			case IntegrationviewPackage.INTEGRATION_VIEW__OBJECTQUANTITIES:
				getObjectquantities().clear();
				getObjectquantities().addAll(
						(Collection<? extends ObjectQuantity>) newValue);
				return;
			case IntegrationviewPackage.INTEGRATION_VIEW__CONFLICTS:
				getConflicts().clear();
				getConflicts()
						.addAll((Collection<? extends Conflict>) newValue);
				return;
			case IntegrationviewPackage.INTEGRATION_VIEW__INTEGRATION_DIRECTORY:
				setIntegrationDirectory((String) newValue);
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
			case IntegrationviewPackage.INTEGRATION_VIEW__PRIMARY_PLANNING:
				setPrimaryPlanning(PRIMARY_PLANNING_EDEFAULT);
				return;
			case IntegrationviewPackage.INTEGRATION_VIEW__SECONDARY_PLANNING:
				setSecondaryPlanning(SECONDARY_PLANNING_EDEFAULT);
				return;
			case IntegrationviewPackage.INTEGRATION_VIEW__COMPOSITE_PLANNING:
				setCompositePlanning(COMPOSITE_PLANNING_EDEFAULT);
				return;
			case IntegrationviewPackage.INTEGRATION_VIEW__OBJECTQUANTITIES:
				getObjectquantities().clear();
				return;
			case IntegrationviewPackage.INTEGRATION_VIEW__CONFLICTS:
				getConflicts().clear();
				return;
			case IntegrationviewPackage.INTEGRATION_VIEW__INTEGRATION_DIRECTORY:
				setIntegrationDirectory(INTEGRATION_DIRECTORY_EDEFAULT);
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
			case IntegrationviewPackage.INTEGRATION_VIEW__PRIMARY_PLANNING:
				return PRIMARY_PLANNING_EDEFAULT == null
						? primaryPlanning != null
						: !PRIMARY_PLANNING_EDEFAULT.equals(primaryPlanning);
			case IntegrationviewPackage.INTEGRATION_VIEW__SECONDARY_PLANNING:
				return SECONDARY_PLANNING_EDEFAULT == null
						? secondaryPlanning != null
						: !SECONDARY_PLANNING_EDEFAULT
								.equals(secondaryPlanning);
			case IntegrationviewPackage.INTEGRATION_VIEW__COMPOSITE_PLANNING:
				return COMPOSITE_PLANNING_EDEFAULT == null
						? compositePlanning != null
						: !COMPOSITE_PLANNING_EDEFAULT
								.equals(compositePlanning);
			case IntegrationviewPackage.INTEGRATION_VIEW__OBJECTQUANTITIES:
				return objectquantities != null && !objectquantities.isEmpty();
			case IntegrationviewPackage.INTEGRATION_VIEW__CONFLICTS:
				return conflicts != null && !conflicts.isEmpty();
			case IntegrationviewPackage.INTEGRATION_VIEW__INTEGRATION_DIRECTORY:
				return INTEGRATION_DIRECTORY_EDEFAULT == null
						? integrationDirectory != null
						: !INTEGRATION_DIRECTORY_EDEFAULT
								.equals(integrationDirectory);
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
		result.append(" (primaryPlanning: ");
		result.append(primaryPlanning);
		result.append(", secondaryPlanning: ");
		result.append(secondaryPlanning);
		result.append(", compositePlanning: ");
		result.append(compositePlanning);
		result.append(", integrationDirectory: ");
		result.append(integrationDirectory);
		result.append(')');
		return result.toString();
	}

} // IntegrationViewImpl
