/**
 * Copyright (c) 2022 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.temporaryintegration.impl;

import java.util.Collection;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.eclipse.set.model.simplemerge.SComparison;

import org.eclipse.set.model.temporaryintegration.TemporaryintegrationPackage;
import org.eclipse.set.model.temporaryintegration.ToolboxTemporaryIntegration;

import org.eclipse.set.toolboxmodel.PlanPro.PlanPro_Schnittstelle;
import org.eclipse.set.toolboxmodel.PlanPro.util.IDReference;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Toolbox
 * Temporary Integration</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.set.model.temporaryintegration.impl.ToolboxTemporaryIntegrationImpl#getPrimaryPlanning
 * <em>Primary Planning</em>}</li>
 * <li>{@link org.eclipse.set.model.temporaryintegration.impl.ToolboxTemporaryIntegrationImpl#getPrimaryPlanningFilename
 * <em>Primary Planning Filename</em>}</li>
 * <li>{@link org.eclipse.set.model.temporaryintegration.impl.ToolboxTemporaryIntegrationImpl#isPrimaryPlanningWasValid
 * <em>Primary Planning Was Valid</em>}</li>
 * <li>{@link org.eclipse.set.model.temporaryintegration.impl.ToolboxTemporaryIntegrationImpl#getPrimaryPlanningIDReferences
 * <em>Primary Planning ID References</em>}</li>
 * <li>{@link org.eclipse.set.model.temporaryintegration.impl.ToolboxTemporaryIntegrationImpl#getSecondaryPlanning
 * <em>Secondary Planning</em>}</li>
 * <li>{@link org.eclipse.set.model.temporaryintegration.impl.ToolboxTemporaryIntegrationImpl#getSecondaryPlanningFilename
 * <em>Secondary Planning Filename</em>}</li>
 * <li>{@link org.eclipse.set.model.temporaryintegration.impl.ToolboxTemporaryIntegrationImpl#isSecondaryPlanningWasValid
 * <em>Secondary Planning Was Valid</em>}</li>
 * <li>{@link org.eclipse.set.model.temporaryintegration.impl.ToolboxTemporaryIntegrationImpl#getSecondaryPlanningIDReferences
 * <em>Secondary Planning ID References</em>}</li>
 * <li>{@link org.eclipse.set.model.temporaryintegration.impl.ToolboxTemporaryIntegrationImpl#getCompositePlanning
 * <em>Composite Planning</em>}</li>
 * <li>{@link org.eclipse.set.model.temporaryintegration.impl.ToolboxTemporaryIntegrationImpl#getCompositePlanningIDReferences
 * <em>Composite Planning ID References</em>}</li>
 * <li>{@link org.eclipse.set.model.temporaryintegration.impl.ToolboxTemporaryIntegrationImpl#getIntegrationDirectory
 * <em>Integration Directory</em>}</li>
 * <li>{@link org.eclipse.set.model.temporaryintegration.impl.ToolboxTemporaryIntegrationImpl#getComparisonInitialState
 * <em>Comparison Initial State</em>}</li>
 * <li>{@link org.eclipse.set.model.temporaryintegration.impl.ToolboxTemporaryIntegrationImpl#getComparisonFinalState
 * <em>Comparison Final State</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ToolboxTemporaryIntegrationImpl extends
		MinimalEObjectImpl.Container implements ToolboxTemporaryIntegration {
	/**
	 * The cached value of the '{@link #getPrimaryPlanning() <em>Primary
	 * Planning</em>}' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getPrimaryPlanning()
	 * @generated
	 * @ordered
	 */
	protected PlanPro_Schnittstelle primaryPlanning;

	/**
	 * The default value of the '{@link #getPrimaryPlanningFilename()
	 * <em>Primary Planning Filename</em>}' attribute. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getPrimaryPlanningFilename()
	 * @generated
	 * @ordered
	 */
	protected static final String PRIMARY_PLANNING_FILENAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPrimaryPlanningFilename() <em>Primary
	 * Planning Filename</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getPrimaryPlanningFilename()
	 * @generated
	 * @ordered
	 */
	protected String primaryPlanningFilename = PRIMARY_PLANNING_FILENAME_EDEFAULT;

	/**
	 * The default value of the '{@link #isPrimaryPlanningWasValid() <em>Primary
	 * Planning Was Valid</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #isPrimaryPlanningWasValid()
	 * @generated
	 * @ordered
	 */
	protected static final boolean PRIMARY_PLANNING_WAS_VALID_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isPrimaryPlanningWasValid() <em>Primary
	 * Planning Was Valid</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #isPrimaryPlanningWasValid()
	 * @generated
	 * @ordered
	 */
	protected boolean primaryPlanningWasValid = PRIMARY_PLANNING_WAS_VALID_EDEFAULT;

	/**
	 * The cached value of the '{@link #getPrimaryPlanningIDReferences()
	 * <em>Primary Planning ID References</em>}' attribute list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getPrimaryPlanningIDReferences()
	 * @generated
	 * @ordered
	 */
	protected EList<IDReference> primaryPlanningIDReferences;

	/**
	 * The cached value of the '{@link #getSecondaryPlanning() <em>Secondary
	 * Planning</em>}' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getSecondaryPlanning()
	 * @generated
	 * @ordered
	 */
	protected PlanPro_Schnittstelle secondaryPlanning;

	/**
	 * The default value of the '{@link #getSecondaryPlanningFilename()
	 * <em>Secondary Planning Filename</em>}' attribute. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getSecondaryPlanningFilename()
	 * @generated
	 * @ordered
	 */
	protected static final String SECONDARY_PLANNING_FILENAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSecondaryPlanningFilename()
	 * <em>Secondary Planning Filename</em>}' attribute. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getSecondaryPlanningFilename()
	 * @generated
	 * @ordered
	 */
	protected String secondaryPlanningFilename = SECONDARY_PLANNING_FILENAME_EDEFAULT;

	/**
	 * The default value of the '{@link #isSecondaryPlanningWasValid()
	 * <em>Secondary Planning Was Valid</em>}' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #isSecondaryPlanningWasValid()
	 * @generated
	 * @ordered
	 */
	protected static final boolean SECONDARY_PLANNING_WAS_VALID_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isSecondaryPlanningWasValid()
	 * <em>Secondary Planning Was Valid</em>}' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #isSecondaryPlanningWasValid()
	 * @generated
	 * @ordered
	 */
	protected boolean secondaryPlanningWasValid = SECONDARY_PLANNING_WAS_VALID_EDEFAULT;

	/**
	 * The cached value of the '{@link #getSecondaryPlanningIDReferences()
	 * <em>Secondary Planning ID References</em>}' attribute list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getSecondaryPlanningIDReferences()
	 * @generated
	 * @ordered
	 */
	protected EList<IDReference> secondaryPlanningIDReferences;

	/**
	 * The cached value of the '{@link #getCompositePlanning() <em>Composite
	 * Planning</em>}' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getCompositePlanning()
	 * @generated
	 * @ordered
	 */
	protected PlanPro_Schnittstelle compositePlanning;

	/**
	 * The cached value of the '{@link #getCompositePlanningIDReferences()
	 * <em>Composite Planning ID References</em>}' attribute list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getCompositePlanningIDReferences()
	 * @generated
	 * @ordered
	 */
	protected EList<IDReference> compositePlanningIDReferences;

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
	 * The cached value of the '{@link #getComparisonInitialState()
	 * <em>Comparison Initial State</em>}' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getComparisonInitialState()
	 * @generated
	 * @ordered
	 */
	protected SComparison comparisonInitialState;

	/**
	 * The cached value of the '{@link #getComparisonFinalState() <em>Comparison
	 * Final State</em>}' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getComparisonFinalState()
	 * @generated
	 * @ordered
	 */
	protected SComparison comparisonFinalState;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected ToolboxTemporaryIntegrationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TemporaryintegrationPackage.Literals.TOOLBOX_TEMPORARY_INTEGRATION;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public PlanPro_Schnittstelle getPrimaryPlanning() {
		return primaryPlanning;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetPrimaryPlanning(
			PlanPro_Schnittstelle newPrimaryPlanning, NotificationChain msgs) {
		PlanPro_Schnittstelle oldPrimaryPlanning = primaryPlanning;
		primaryPlanning = newPrimaryPlanning;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this,
					Notification.SET,
					TemporaryintegrationPackage.TOOLBOX_TEMPORARY_INTEGRATION__PRIMARY_PLANNING,
					oldPrimaryPlanning, newPrimaryPlanning);
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
	public void setPrimaryPlanning(PlanPro_Schnittstelle newPrimaryPlanning) {
		if (newPrimaryPlanning != primaryPlanning) {
			NotificationChain msgs = null;
			if (primaryPlanning != null)
				msgs = ((InternalEObject) primaryPlanning).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE
								- TemporaryintegrationPackage.TOOLBOX_TEMPORARY_INTEGRATION__PRIMARY_PLANNING,
						null, msgs);
			if (newPrimaryPlanning != null)
				msgs = ((InternalEObject) newPrimaryPlanning).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE
								- TemporaryintegrationPackage.TOOLBOX_TEMPORARY_INTEGRATION__PRIMARY_PLANNING,
						null, msgs);
			msgs = basicSetPrimaryPlanning(newPrimaryPlanning, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					TemporaryintegrationPackage.TOOLBOX_TEMPORARY_INTEGRATION__PRIMARY_PLANNING,
					newPrimaryPlanning, newPrimaryPlanning));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getPrimaryPlanningFilename() {
		return primaryPlanningFilename;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setPrimaryPlanningFilename(String newPrimaryPlanningFilename) {
		String oldPrimaryPlanningFilename = primaryPlanningFilename;
		primaryPlanningFilename = newPrimaryPlanningFilename;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					TemporaryintegrationPackage.TOOLBOX_TEMPORARY_INTEGRATION__PRIMARY_PLANNING_FILENAME,
					oldPrimaryPlanningFilename, primaryPlanningFilename));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public boolean isPrimaryPlanningWasValid() {
		return primaryPlanningWasValid;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setPrimaryPlanningWasValid(boolean newPrimaryPlanningWasValid) {
		boolean oldPrimaryPlanningWasValid = primaryPlanningWasValid;
		primaryPlanningWasValid = newPrimaryPlanningWasValid;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					TemporaryintegrationPackage.TOOLBOX_TEMPORARY_INTEGRATION__PRIMARY_PLANNING_WAS_VALID,
					oldPrimaryPlanningWasValid, primaryPlanningWasValid));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<IDReference> getPrimaryPlanningIDReferences() {
		if (primaryPlanningIDReferences == null) {
			primaryPlanningIDReferences = new EDataTypeUniqueEList<IDReference>(
					IDReference.class, this,
					TemporaryintegrationPackage.TOOLBOX_TEMPORARY_INTEGRATION__PRIMARY_PLANNING_ID_REFERENCES);
		}
		return primaryPlanningIDReferences;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public PlanPro_Schnittstelle getSecondaryPlanning() {
		return secondaryPlanning;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetSecondaryPlanning(
			PlanPro_Schnittstelle newSecondaryPlanning,
			NotificationChain msgs) {
		PlanPro_Schnittstelle oldSecondaryPlanning = secondaryPlanning;
		secondaryPlanning = newSecondaryPlanning;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this,
					Notification.SET,
					TemporaryintegrationPackage.TOOLBOX_TEMPORARY_INTEGRATION__SECONDARY_PLANNING,
					oldSecondaryPlanning, newSecondaryPlanning);
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
	public void setSecondaryPlanning(
			PlanPro_Schnittstelle newSecondaryPlanning) {
		if (newSecondaryPlanning != secondaryPlanning) {
			NotificationChain msgs = null;
			if (secondaryPlanning != null)
				msgs = ((InternalEObject) secondaryPlanning).eInverseRemove(
						this,
						EOPPOSITE_FEATURE_BASE
								- TemporaryintegrationPackage.TOOLBOX_TEMPORARY_INTEGRATION__SECONDARY_PLANNING,
						null, msgs);
			if (newSecondaryPlanning != null)
				msgs = ((InternalEObject) newSecondaryPlanning).eInverseAdd(
						this,
						EOPPOSITE_FEATURE_BASE
								- TemporaryintegrationPackage.TOOLBOX_TEMPORARY_INTEGRATION__SECONDARY_PLANNING,
						null, msgs);
			msgs = basicSetSecondaryPlanning(newSecondaryPlanning, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					TemporaryintegrationPackage.TOOLBOX_TEMPORARY_INTEGRATION__SECONDARY_PLANNING,
					newSecondaryPlanning, newSecondaryPlanning));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getSecondaryPlanningFilename() {
		return secondaryPlanningFilename;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setSecondaryPlanningFilename(
			String newSecondaryPlanningFilename) {
		String oldSecondaryPlanningFilename = secondaryPlanningFilename;
		secondaryPlanningFilename = newSecondaryPlanningFilename;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					TemporaryintegrationPackage.TOOLBOX_TEMPORARY_INTEGRATION__SECONDARY_PLANNING_FILENAME,
					oldSecondaryPlanningFilename, secondaryPlanningFilename));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public boolean isSecondaryPlanningWasValid() {
		return secondaryPlanningWasValid;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setSecondaryPlanningWasValid(
			boolean newSecondaryPlanningWasValid) {
		boolean oldSecondaryPlanningWasValid = secondaryPlanningWasValid;
		secondaryPlanningWasValid = newSecondaryPlanningWasValid;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					TemporaryintegrationPackage.TOOLBOX_TEMPORARY_INTEGRATION__SECONDARY_PLANNING_WAS_VALID,
					oldSecondaryPlanningWasValid, secondaryPlanningWasValid));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<IDReference> getSecondaryPlanningIDReferences() {
		if (secondaryPlanningIDReferences == null) {
			secondaryPlanningIDReferences = new EDataTypeUniqueEList<IDReference>(
					IDReference.class, this,
					TemporaryintegrationPackage.TOOLBOX_TEMPORARY_INTEGRATION__SECONDARY_PLANNING_ID_REFERENCES);
		}
		return secondaryPlanningIDReferences;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public PlanPro_Schnittstelle getCompositePlanning() {
		return compositePlanning;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetCompositePlanning(
			PlanPro_Schnittstelle newCompositePlanning,
			NotificationChain msgs) {
		PlanPro_Schnittstelle oldCompositePlanning = compositePlanning;
		compositePlanning = newCompositePlanning;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this,
					Notification.SET,
					TemporaryintegrationPackage.TOOLBOX_TEMPORARY_INTEGRATION__COMPOSITE_PLANNING,
					oldCompositePlanning, newCompositePlanning);
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
	public void setCompositePlanning(
			PlanPro_Schnittstelle newCompositePlanning) {
		if (newCompositePlanning != compositePlanning) {
			NotificationChain msgs = null;
			if (compositePlanning != null)
				msgs = ((InternalEObject) compositePlanning).eInverseRemove(
						this,
						EOPPOSITE_FEATURE_BASE
								- TemporaryintegrationPackage.TOOLBOX_TEMPORARY_INTEGRATION__COMPOSITE_PLANNING,
						null, msgs);
			if (newCompositePlanning != null)
				msgs = ((InternalEObject) newCompositePlanning).eInverseAdd(
						this,
						EOPPOSITE_FEATURE_BASE
								- TemporaryintegrationPackage.TOOLBOX_TEMPORARY_INTEGRATION__COMPOSITE_PLANNING,
						null, msgs);
			msgs = basicSetCompositePlanning(newCompositePlanning, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					TemporaryintegrationPackage.TOOLBOX_TEMPORARY_INTEGRATION__COMPOSITE_PLANNING,
					newCompositePlanning, newCompositePlanning));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<IDReference> getCompositePlanningIDReferences() {
		if (compositePlanningIDReferences == null) {
			compositePlanningIDReferences = new EDataTypeUniqueEList<IDReference>(
					IDReference.class, this,
					TemporaryintegrationPackage.TOOLBOX_TEMPORARY_INTEGRATION__COMPOSITE_PLANNING_ID_REFERENCES);
		}
		return compositePlanningIDReferences;
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
					TemporaryintegrationPackage.TOOLBOX_TEMPORARY_INTEGRATION__INTEGRATION_DIRECTORY,
					oldIntegrationDirectory, integrationDirectory));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public SComparison getComparisonInitialState() {
		return comparisonInitialState;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetComparisonInitialState(
			SComparison newComparisonInitialState, NotificationChain msgs) {
		SComparison oldComparisonInitialState = comparisonInitialState;
		comparisonInitialState = newComparisonInitialState;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this,
					Notification.SET,
					TemporaryintegrationPackage.TOOLBOX_TEMPORARY_INTEGRATION__COMPARISON_INITIAL_STATE,
					oldComparisonInitialState, newComparisonInitialState);
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
	public void setComparisonInitialState(
			SComparison newComparisonInitialState) {
		if (newComparisonInitialState != comparisonInitialState) {
			NotificationChain msgs = null;
			if (comparisonInitialState != null)
				msgs = ((InternalEObject) comparisonInitialState)
						.eInverseRemove(this, EOPPOSITE_FEATURE_BASE
								- TemporaryintegrationPackage.TOOLBOX_TEMPORARY_INTEGRATION__COMPARISON_INITIAL_STATE,
								null, msgs);
			if (newComparisonInitialState != null)
				msgs = ((InternalEObject) newComparisonInitialState)
						.eInverseAdd(this, EOPPOSITE_FEATURE_BASE
								- TemporaryintegrationPackage.TOOLBOX_TEMPORARY_INTEGRATION__COMPARISON_INITIAL_STATE,
								null, msgs);
			msgs = basicSetComparisonInitialState(newComparisonInitialState,
					msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					TemporaryintegrationPackage.TOOLBOX_TEMPORARY_INTEGRATION__COMPARISON_INITIAL_STATE,
					newComparisonInitialState, newComparisonInitialState));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public SComparison getComparisonFinalState() {
		return comparisonFinalState;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetComparisonFinalState(
			SComparison newComparisonFinalState, NotificationChain msgs) {
		SComparison oldComparisonFinalState = comparisonFinalState;
		comparisonFinalState = newComparisonFinalState;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this,
					Notification.SET,
					TemporaryintegrationPackage.TOOLBOX_TEMPORARY_INTEGRATION__COMPARISON_FINAL_STATE,
					oldComparisonFinalState, newComparisonFinalState);
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
	public void setComparisonFinalState(SComparison newComparisonFinalState) {
		if (newComparisonFinalState != comparisonFinalState) {
			NotificationChain msgs = null;
			if (comparisonFinalState != null)
				msgs = ((InternalEObject) comparisonFinalState).eInverseRemove(
						this,
						EOPPOSITE_FEATURE_BASE
								- TemporaryintegrationPackage.TOOLBOX_TEMPORARY_INTEGRATION__COMPARISON_FINAL_STATE,
						null, msgs);
			if (newComparisonFinalState != null)
				msgs = ((InternalEObject) newComparisonFinalState).eInverseAdd(
						this,
						EOPPOSITE_FEATURE_BASE
								- TemporaryintegrationPackage.TOOLBOX_TEMPORARY_INTEGRATION__COMPARISON_FINAL_STATE,
						null, msgs);
			msgs = basicSetComparisonFinalState(newComparisonFinalState, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					TemporaryintegrationPackage.TOOLBOX_TEMPORARY_INTEGRATION__COMPARISON_FINAL_STATE,
					newComparisonFinalState, newComparisonFinalState));
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
			case TemporaryintegrationPackage.TOOLBOX_TEMPORARY_INTEGRATION__PRIMARY_PLANNING:
				return basicSetPrimaryPlanning(null, msgs);
			case TemporaryintegrationPackage.TOOLBOX_TEMPORARY_INTEGRATION__SECONDARY_PLANNING:
				return basicSetSecondaryPlanning(null, msgs);
			case TemporaryintegrationPackage.TOOLBOX_TEMPORARY_INTEGRATION__COMPOSITE_PLANNING:
				return basicSetCompositePlanning(null, msgs);
			case TemporaryintegrationPackage.TOOLBOX_TEMPORARY_INTEGRATION__COMPARISON_INITIAL_STATE:
				return basicSetComparisonInitialState(null, msgs);
			case TemporaryintegrationPackage.TOOLBOX_TEMPORARY_INTEGRATION__COMPARISON_FINAL_STATE:
				return basicSetComparisonFinalState(null, msgs);
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
			case TemporaryintegrationPackage.TOOLBOX_TEMPORARY_INTEGRATION__PRIMARY_PLANNING:
				return getPrimaryPlanning();
			case TemporaryintegrationPackage.TOOLBOX_TEMPORARY_INTEGRATION__PRIMARY_PLANNING_FILENAME:
				return getPrimaryPlanningFilename();
			case TemporaryintegrationPackage.TOOLBOX_TEMPORARY_INTEGRATION__PRIMARY_PLANNING_WAS_VALID:
				return isPrimaryPlanningWasValid();
			case TemporaryintegrationPackage.TOOLBOX_TEMPORARY_INTEGRATION__PRIMARY_PLANNING_ID_REFERENCES:
				return getPrimaryPlanningIDReferences();
			case TemporaryintegrationPackage.TOOLBOX_TEMPORARY_INTEGRATION__SECONDARY_PLANNING:
				return getSecondaryPlanning();
			case TemporaryintegrationPackage.TOOLBOX_TEMPORARY_INTEGRATION__SECONDARY_PLANNING_FILENAME:
				return getSecondaryPlanningFilename();
			case TemporaryintegrationPackage.TOOLBOX_TEMPORARY_INTEGRATION__SECONDARY_PLANNING_WAS_VALID:
				return isSecondaryPlanningWasValid();
			case TemporaryintegrationPackage.TOOLBOX_TEMPORARY_INTEGRATION__SECONDARY_PLANNING_ID_REFERENCES:
				return getSecondaryPlanningIDReferences();
			case TemporaryintegrationPackage.TOOLBOX_TEMPORARY_INTEGRATION__COMPOSITE_PLANNING:
				return getCompositePlanning();
			case TemporaryintegrationPackage.TOOLBOX_TEMPORARY_INTEGRATION__COMPOSITE_PLANNING_ID_REFERENCES:
				return getCompositePlanningIDReferences();
			case TemporaryintegrationPackage.TOOLBOX_TEMPORARY_INTEGRATION__INTEGRATION_DIRECTORY:
				return getIntegrationDirectory();
			case TemporaryintegrationPackage.TOOLBOX_TEMPORARY_INTEGRATION__COMPARISON_INITIAL_STATE:
				return getComparisonInitialState();
			case TemporaryintegrationPackage.TOOLBOX_TEMPORARY_INTEGRATION__COMPARISON_FINAL_STATE:
				return getComparisonFinalState();
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
			case TemporaryintegrationPackage.TOOLBOX_TEMPORARY_INTEGRATION__PRIMARY_PLANNING:
				setPrimaryPlanning((PlanPro_Schnittstelle) newValue);
				return;
			case TemporaryintegrationPackage.TOOLBOX_TEMPORARY_INTEGRATION__PRIMARY_PLANNING_FILENAME:
				setPrimaryPlanningFilename((String) newValue);
				return;
			case TemporaryintegrationPackage.TOOLBOX_TEMPORARY_INTEGRATION__PRIMARY_PLANNING_WAS_VALID:
				setPrimaryPlanningWasValid((Boolean) newValue);
				return;
			case TemporaryintegrationPackage.TOOLBOX_TEMPORARY_INTEGRATION__PRIMARY_PLANNING_ID_REFERENCES:
				getPrimaryPlanningIDReferences().clear();
				getPrimaryPlanningIDReferences()
						.addAll((Collection<? extends IDReference>) newValue);
				return;
			case TemporaryintegrationPackage.TOOLBOX_TEMPORARY_INTEGRATION__SECONDARY_PLANNING:
				setSecondaryPlanning((PlanPro_Schnittstelle) newValue);
				return;
			case TemporaryintegrationPackage.TOOLBOX_TEMPORARY_INTEGRATION__SECONDARY_PLANNING_FILENAME:
				setSecondaryPlanningFilename((String) newValue);
				return;
			case TemporaryintegrationPackage.TOOLBOX_TEMPORARY_INTEGRATION__SECONDARY_PLANNING_WAS_VALID:
				setSecondaryPlanningWasValid((Boolean) newValue);
				return;
			case TemporaryintegrationPackage.TOOLBOX_TEMPORARY_INTEGRATION__SECONDARY_PLANNING_ID_REFERENCES:
				getSecondaryPlanningIDReferences().clear();
				getSecondaryPlanningIDReferences()
						.addAll((Collection<? extends IDReference>) newValue);
				return;
			case TemporaryintegrationPackage.TOOLBOX_TEMPORARY_INTEGRATION__COMPOSITE_PLANNING:
				setCompositePlanning((PlanPro_Schnittstelle) newValue);
				return;
			case TemporaryintegrationPackage.TOOLBOX_TEMPORARY_INTEGRATION__COMPOSITE_PLANNING_ID_REFERENCES:
				getCompositePlanningIDReferences().clear();
				getCompositePlanningIDReferences()
						.addAll((Collection<? extends IDReference>) newValue);
				return;
			case TemporaryintegrationPackage.TOOLBOX_TEMPORARY_INTEGRATION__INTEGRATION_DIRECTORY:
				setIntegrationDirectory((String) newValue);
				return;
			case TemporaryintegrationPackage.TOOLBOX_TEMPORARY_INTEGRATION__COMPARISON_INITIAL_STATE:
				setComparisonInitialState((SComparison) newValue);
				return;
			case TemporaryintegrationPackage.TOOLBOX_TEMPORARY_INTEGRATION__COMPARISON_FINAL_STATE:
				setComparisonFinalState((SComparison) newValue);
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
			case TemporaryintegrationPackage.TOOLBOX_TEMPORARY_INTEGRATION__PRIMARY_PLANNING:
				setPrimaryPlanning((PlanPro_Schnittstelle) null);
				return;
			case TemporaryintegrationPackage.TOOLBOX_TEMPORARY_INTEGRATION__PRIMARY_PLANNING_FILENAME:
				setPrimaryPlanningFilename(PRIMARY_PLANNING_FILENAME_EDEFAULT);
				return;
			case TemporaryintegrationPackage.TOOLBOX_TEMPORARY_INTEGRATION__PRIMARY_PLANNING_WAS_VALID:
				setPrimaryPlanningWasValid(PRIMARY_PLANNING_WAS_VALID_EDEFAULT);
				return;
			case TemporaryintegrationPackage.TOOLBOX_TEMPORARY_INTEGRATION__PRIMARY_PLANNING_ID_REFERENCES:
				getPrimaryPlanningIDReferences().clear();
				return;
			case TemporaryintegrationPackage.TOOLBOX_TEMPORARY_INTEGRATION__SECONDARY_PLANNING:
				setSecondaryPlanning((PlanPro_Schnittstelle) null);
				return;
			case TemporaryintegrationPackage.TOOLBOX_TEMPORARY_INTEGRATION__SECONDARY_PLANNING_FILENAME:
				setSecondaryPlanningFilename(
						SECONDARY_PLANNING_FILENAME_EDEFAULT);
				return;
			case TemporaryintegrationPackage.TOOLBOX_TEMPORARY_INTEGRATION__SECONDARY_PLANNING_WAS_VALID:
				setSecondaryPlanningWasValid(
						SECONDARY_PLANNING_WAS_VALID_EDEFAULT);
				return;
			case TemporaryintegrationPackage.TOOLBOX_TEMPORARY_INTEGRATION__SECONDARY_PLANNING_ID_REFERENCES:
				getSecondaryPlanningIDReferences().clear();
				return;
			case TemporaryintegrationPackage.TOOLBOX_TEMPORARY_INTEGRATION__COMPOSITE_PLANNING:
				setCompositePlanning((PlanPro_Schnittstelle) null);
				return;
			case TemporaryintegrationPackage.TOOLBOX_TEMPORARY_INTEGRATION__COMPOSITE_PLANNING_ID_REFERENCES:
				getCompositePlanningIDReferences().clear();
				return;
			case TemporaryintegrationPackage.TOOLBOX_TEMPORARY_INTEGRATION__INTEGRATION_DIRECTORY:
				setIntegrationDirectory(INTEGRATION_DIRECTORY_EDEFAULT);
				return;
			case TemporaryintegrationPackage.TOOLBOX_TEMPORARY_INTEGRATION__COMPARISON_INITIAL_STATE:
				setComparisonInitialState((SComparison) null);
				return;
			case TemporaryintegrationPackage.TOOLBOX_TEMPORARY_INTEGRATION__COMPARISON_FINAL_STATE:
				setComparisonFinalState((SComparison) null);
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
			case TemporaryintegrationPackage.TOOLBOX_TEMPORARY_INTEGRATION__PRIMARY_PLANNING:
				return primaryPlanning != null;
			case TemporaryintegrationPackage.TOOLBOX_TEMPORARY_INTEGRATION__PRIMARY_PLANNING_FILENAME:
				return PRIMARY_PLANNING_FILENAME_EDEFAULT == null
						? primaryPlanningFilename != null
						: !PRIMARY_PLANNING_FILENAME_EDEFAULT
								.equals(primaryPlanningFilename);
			case TemporaryintegrationPackage.TOOLBOX_TEMPORARY_INTEGRATION__PRIMARY_PLANNING_WAS_VALID:
				return primaryPlanningWasValid != PRIMARY_PLANNING_WAS_VALID_EDEFAULT;
			case TemporaryintegrationPackage.TOOLBOX_TEMPORARY_INTEGRATION__PRIMARY_PLANNING_ID_REFERENCES:
				return primaryPlanningIDReferences != null
						&& !primaryPlanningIDReferences.isEmpty();
			case TemporaryintegrationPackage.TOOLBOX_TEMPORARY_INTEGRATION__SECONDARY_PLANNING:
				return secondaryPlanning != null;
			case TemporaryintegrationPackage.TOOLBOX_TEMPORARY_INTEGRATION__SECONDARY_PLANNING_FILENAME:
				return SECONDARY_PLANNING_FILENAME_EDEFAULT == null
						? secondaryPlanningFilename != null
						: !SECONDARY_PLANNING_FILENAME_EDEFAULT
								.equals(secondaryPlanningFilename);
			case TemporaryintegrationPackage.TOOLBOX_TEMPORARY_INTEGRATION__SECONDARY_PLANNING_WAS_VALID:
				return secondaryPlanningWasValid != SECONDARY_PLANNING_WAS_VALID_EDEFAULT;
			case TemporaryintegrationPackage.TOOLBOX_TEMPORARY_INTEGRATION__SECONDARY_PLANNING_ID_REFERENCES:
				return secondaryPlanningIDReferences != null
						&& !secondaryPlanningIDReferences.isEmpty();
			case TemporaryintegrationPackage.TOOLBOX_TEMPORARY_INTEGRATION__COMPOSITE_PLANNING:
				return compositePlanning != null;
			case TemporaryintegrationPackage.TOOLBOX_TEMPORARY_INTEGRATION__COMPOSITE_PLANNING_ID_REFERENCES:
				return compositePlanningIDReferences != null
						&& !compositePlanningIDReferences.isEmpty();
			case TemporaryintegrationPackage.TOOLBOX_TEMPORARY_INTEGRATION__INTEGRATION_DIRECTORY:
				return INTEGRATION_DIRECTORY_EDEFAULT == null
						? integrationDirectory != null
						: !INTEGRATION_DIRECTORY_EDEFAULT
								.equals(integrationDirectory);
			case TemporaryintegrationPackage.TOOLBOX_TEMPORARY_INTEGRATION__COMPARISON_INITIAL_STATE:
				return comparisonInitialState != null;
			case TemporaryintegrationPackage.TOOLBOX_TEMPORARY_INTEGRATION__COMPARISON_FINAL_STATE:
				return comparisonFinalState != null;
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
		result.append(" (primaryPlanningFilename: ");
		result.append(primaryPlanningFilename);
		result.append(", primaryPlanningWasValid: ");
		result.append(primaryPlanningWasValid);
		result.append(", primaryPlanningIDReferences: ");
		result.append(primaryPlanningIDReferences);
		result.append(", secondaryPlanningFilename: ");
		result.append(secondaryPlanningFilename);
		result.append(", secondaryPlanningWasValid: ");
		result.append(secondaryPlanningWasValid);
		result.append(", secondaryPlanningIDReferences: ");
		result.append(secondaryPlanningIDReferences);
		result.append(", compositePlanningIDReferences: ");
		result.append(compositePlanningIDReferences);
		result.append(", integrationDirectory: ");
		result.append(integrationDirectory);
		result.append(')');
		return result.toString();
	}

} // ToolboxTemporaryIntegrationImpl
