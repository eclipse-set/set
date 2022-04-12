/**
 * Copyright (c) 2022 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.temporaryintegration;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.eclipse.set.model.temporaryintegration.TemporaryintegrationFactory
 * @model kind="package"
 * @generated
 */
public interface TemporaryintegrationPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "temporaryintegration";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "tag:scheidt-bachmann-st.de,2018-06-29:planpro/temporaryintegration/1.8.0.1";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "ppixml";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	TemporaryintegrationPackage eINSTANCE = org.eclipse.set.model.temporaryintegration.impl.TemporaryintegrationPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.set.model.temporaryintegration.impl.TemporaryIntegrationImpl <em>Temporary Integration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.set.model.temporaryintegration.impl.TemporaryIntegrationImpl
	 * @see org.eclipse.set.model.temporaryintegration.impl.TemporaryintegrationPackageImpl#getTemporaryIntegration()
	 * @generated
	 */
	int TEMPORARY_INTEGRATION = 0;

	/**
	 * The feature id for the '<em><b>Primary Planning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEMPORARY_INTEGRATION__PRIMARY_PLANNING = 0;

	/**
	 * The feature id for the '<em><b>Primary Planning Filename</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEMPORARY_INTEGRATION__PRIMARY_PLANNING_FILENAME = 1;

	/**
	 * The feature id for the '<em><b>Primary Planning Was Valid</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEMPORARY_INTEGRATION__PRIMARY_PLANNING_WAS_VALID = 2;

	/**
	 * The feature id for the '<em><b>Secondary Planning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEMPORARY_INTEGRATION__SECONDARY_PLANNING = 3;

	/**
	 * The feature id for the '<em><b>Secondary Planning Filename</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEMPORARY_INTEGRATION__SECONDARY_PLANNING_FILENAME = 4;

	/**
	 * The feature id for the '<em><b>Secondary Planning Was Valid</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEMPORARY_INTEGRATION__SECONDARY_PLANNING_WAS_VALID = 5;

	/**
	 * The feature id for the '<em><b>Composite Planning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEMPORARY_INTEGRATION__COMPOSITE_PLANNING = 6;

	/**
	 * The feature id for the '<em><b>Integration Directory</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEMPORARY_INTEGRATION__INTEGRATION_DIRECTORY = 7;

	/**
	 * The feature id for the '<em><b>Comparison Initial State</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEMPORARY_INTEGRATION__COMPARISON_INITIAL_STATE = 8;

	/**
	 * The feature id for the '<em><b>Comparison Final State</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEMPORARY_INTEGRATION__COMPARISON_FINAL_STATE = 9;

	/**
	 * The number of structural features of the '<em>Temporary Integration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEMPORARY_INTEGRATION_FEATURE_COUNT = 10;

	/**
	 * The number of operations of the '<em>Temporary Integration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEMPORARY_INTEGRATION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.set.model.temporaryintegration.impl.ToolboxTemporaryIntegrationImpl <em>Toolbox Temporary Integration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.set.model.temporaryintegration.impl.ToolboxTemporaryIntegrationImpl
	 * @see org.eclipse.set.model.temporaryintegration.impl.TemporaryintegrationPackageImpl#getToolboxTemporaryIntegration()
	 * @generated
	 */
	int TOOLBOX_TEMPORARY_INTEGRATION = 1;

	/**
	 * The feature id for the '<em><b>Primary Planning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TOOLBOX_TEMPORARY_INTEGRATION__PRIMARY_PLANNING = 0;

	/**
	 * The feature id for the '<em><b>Primary Planning Filename</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TOOLBOX_TEMPORARY_INTEGRATION__PRIMARY_PLANNING_FILENAME = 1;

	/**
	 * The feature id for the '<em><b>Primary Planning Was Valid</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TOOLBOX_TEMPORARY_INTEGRATION__PRIMARY_PLANNING_WAS_VALID = 2;

	/**
	 * The feature id for the '<em><b>Secondary Planning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TOOLBOX_TEMPORARY_INTEGRATION__SECONDARY_PLANNING = 3;

	/**
	 * The feature id for the '<em><b>Secondary Planning Filename</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TOOLBOX_TEMPORARY_INTEGRATION__SECONDARY_PLANNING_FILENAME = 4;

	/**
	 * The feature id for the '<em><b>Secondary Planning Was Valid</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TOOLBOX_TEMPORARY_INTEGRATION__SECONDARY_PLANNING_WAS_VALID = 5;

	/**
	 * The feature id for the '<em><b>Composite Planning</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TOOLBOX_TEMPORARY_INTEGRATION__COMPOSITE_PLANNING = 6;

	/**
	 * The feature id for the '<em><b>Integration Directory</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TOOLBOX_TEMPORARY_INTEGRATION__INTEGRATION_DIRECTORY = 7;

	/**
	 * The feature id for the '<em><b>Comparison Initial State</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TOOLBOX_TEMPORARY_INTEGRATION__COMPARISON_INITIAL_STATE = 8;

	/**
	 * The feature id for the '<em><b>Comparison Final State</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TOOLBOX_TEMPORARY_INTEGRATION__COMPARISON_FINAL_STATE = 9;

	/**
	 * The number of structural features of the '<em>Toolbox Temporary Integration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TOOLBOX_TEMPORARY_INTEGRATION_FEATURE_COUNT = 10;

	/**
	 * The number of operations of the '<em>Toolbox Temporary Integration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TOOLBOX_TEMPORARY_INTEGRATION_OPERATION_COUNT = 0;


	/**
	 * Returns the meta object for class '{@link org.eclipse.set.model.temporaryintegration.TemporaryIntegration <em>Temporary Integration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Temporary Integration</em>'.
	 * @see org.eclipse.set.model.temporaryintegration.TemporaryIntegration
	 * @generated
	 */
	EClass getTemporaryIntegration();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.set.model.temporaryintegration.TemporaryIntegration#getPrimaryPlanning <em>Primary Planning</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Primary Planning</em>'.
	 * @see org.eclipse.set.model.temporaryintegration.TemporaryIntegration#getPrimaryPlanning()
	 * @see #getTemporaryIntegration()
	 * @generated
	 */
	EReference getTemporaryIntegration_PrimaryPlanning();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.set.model.temporaryintegration.TemporaryIntegration#getPrimaryPlanningFilename <em>Primary Planning Filename</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Primary Planning Filename</em>'.
	 * @see org.eclipse.set.model.temporaryintegration.TemporaryIntegration#getPrimaryPlanningFilename()
	 * @see #getTemporaryIntegration()
	 * @generated
	 */
	EAttribute getTemporaryIntegration_PrimaryPlanningFilename();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.set.model.temporaryintegration.TemporaryIntegration#isPrimaryPlanningWasValid <em>Primary Planning Was Valid</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Primary Planning Was Valid</em>'.
	 * @see org.eclipse.set.model.temporaryintegration.TemporaryIntegration#isPrimaryPlanningWasValid()
	 * @see #getTemporaryIntegration()
	 * @generated
	 */
	EAttribute getTemporaryIntegration_PrimaryPlanningWasValid();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.set.model.temporaryintegration.TemporaryIntegration#getSecondaryPlanning <em>Secondary Planning</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Secondary Planning</em>'.
	 * @see org.eclipse.set.model.temporaryintegration.TemporaryIntegration#getSecondaryPlanning()
	 * @see #getTemporaryIntegration()
	 * @generated
	 */
	EReference getTemporaryIntegration_SecondaryPlanning();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.set.model.temporaryintegration.TemporaryIntegration#getSecondaryPlanningFilename <em>Secondary Planning Filename</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Secondary Planning Filename</em>'.
	 * @see org.eclipse.set.model.temporaryintegration.TemporaryIntegration#getSecondaryPlanningFilename()
	 * @see #getTemporaryIntegration()
	 * @generated
	 */
	EAttribute getTemporaryIntegration_SecondaryPlanningFilename();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.set.model.temporaryintegration.TemporaryIntegration#isSecondaryPlanningWasValid <em>Secondary Planning Was Valid</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Secondary Planning Was Valid</em>'.
	 * @see org.eclipse.set.model.temporaryintegration.TemporaryIntegration#isSecondaryPlanningWasValid()
	 * @see #getTemporaryIntegration()
	 * @generated
	 */
	EAttribute getTemporaryIntegration_SecondaryPlanningWasValid();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.set.model.temporaryintegration.TemporaryIntegration#getCompositePlanning <em>Composite Planning</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Composite Planning</em>'.
	 * @see org.eclipse.set.model.temporaryintegration.TemporaryIntegration#getCompositePlanning()
	 * @see #getTemporaryIntegration()
	 * @generated
	 */
	EReference getTemporaryIntegration_CompositePlanning();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.set.model.temporaryintegration.TemporaryIntegration#getIntegrationDirectory <em>Integration Directory</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Integration Directory</em>'.
	 * @see org.eclipse.set.model.temporaryintegration.TemporaryIntegration#getIntegrationDirectory()
	 * @see #getTemporaryIntegration()
	 * @generated
	 */
	EAttribute getTemporaryIntegration_IntegrationDirectory();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.set.model.temporaryintegration.TemporaryIntegration#getComparisonInitialState <em>Comparison Initial State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Comparison Initial State</em>'.
	 * @see org.eclipse.set.model.temporaryintegration.TemporaryIntegration#getComparisonInitialState()
	 * @see #getTemporaryIntegration()
	 * @generated
	 */
	EReference getTemporaryIntegration_ComparisonInitialState();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.set.model.temporaryintegration.TemporaryIntegration#getComparisonFinalState <em>Comparison Final State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Comparison Final State</em>'.
	 * @see org.eclipse.set.model.temporaryintegration.TemporaryIntegration#getComparisonFinalState()
	 * @see #getTemporaryIntegration()
	 * @generated
	 */
	EReference getTemporaryIntegration_ComparisonFinalState();

	/**
	 * Returns the meta object for class '{@link org.eclipse.set.model.temporaryintegration.ToolboxTemporaryIntegration <em>Toolbox Temporary Integration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Toolbox Temporary Integration</em>'.
	 * @see org.eclipse.set.model.temporaryintegration.ToolboxTemporaryIntegration
	 * @generated
	 */
	EClass getToolboxTemporaryIntegration();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.set.model.temporaryintegration.ToolboxTemporaryIntegration#getPrimaryPlanning <em>Primary Planning</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Primary Planning</em>'.
	 * @see org.eclipse.set.model.temporaryintegration.ToolboxTemporaryIntegration#getPrimaryPlanning()
	 * @see #getToolboxTemporaryIntegration()
	 * @generated
	 */
	EReference getToolboxTemporaryIntegration_PrimaryPlanning();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.set.model.temporaryintegration.ToolboxTemporaryIntegration#getPrimaryPlanningFilename <em>Primary Planning Filename</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Primary Planning Filename</em>'.
	 * @see org.eclipse.set.model.temporaryintegration.ToolboxTemporaryIntegration#getPrimaryPlanningFilename()
	 * @see #getToolboxTemporaryIntegration()
	 * @generated
	 */
	EAttribute getToolboxTemporaryIntegration_PrimaryPlanningFilename();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.set.model.temporaryintegration.ToolboxTemporaryIntegration#isPrimaryPlanningWasValid <em>Primary Planning Was Valid</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Primary Planning Was Valid</em>'.
	 * @see org.eclipse.set.model.temporaryintegration.ToolboxTemporaryIntegration#isPrimaryPlanningWasValid()
	 * @see #getToolboxTemporaryIntegration()
	 * @generated
	 */
	EAttribute getToolboxTemporaryIntegration_PrimaryPlanningWasValid();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.set.model.temporaryintegration.ToolboxTemporaryIntegration#getSecondaryPlanning <em>Secondary Planning</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Secondary Planning</em>'.
	 * @see org.eclipse.set.model.temporaryintegration.ToolboxTemporaryIntegration#getSecondaryPlanning()
	 * @see #getToolboxTemporaryIntegration()
	 * @generated
	 */
	EReference getToolboxTemporaryIntegration_SecondaryPlanning();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.set.model.temporaryintegration.ToolboxTemporaryIntegration#getSecondaryPlanningFilename <em>Secondary Planning Filename</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Secondary Planning Filename</em>'.
	 * @see org.eclipse.set.model.temporaryintegration.ToolboxTemporaryIntegration#getSecondaryPlanningFilename()
	 * @see #getToolboxTemporaryIntegration()
	 * @generated
	 */
	EAttribute getToolboxTemporaryIntegration_SecondaryPlanningFilename();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.set.model.temporaryintegration.ToolboxTemporaryIntegration#isSecondaryPlanningWasValid <em>Secondary Planning Was Valid</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Secondary Planning Was Valid</em>'.
	 * @see org.eclipse.set.model.temporaryintegration.ToolboxTemporaryIntegration#isSecondaryPlanningWasValid()
	 * @see #getToolboxTemporaryIntegration()
	 * @generated
	 */
	EAttribute getToolboxTemporaryIntegration_SecondaryPlanningWasValid();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.set.model.temporaryintegration.ToolboxTemporaryIntegration#getCompositePlanning <em>Composite Planning</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Composite Planning</em>'.
	 * @see org.eclipse.set.model.temporaryintegration.ToolboxTemporaryIntegration#getCompositePlanning()
	 * @see #getToolboxTemporaryIntegration()
	 * @generated
	 */
	EReference getToolboxTemporaryIntegration_CompositePlanning();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.set.model.temporaryintegration.ToolboxTemporaryIntegration#getIntegrationDirectory <em>Integration Directory</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Integration Directory</em>'.
	 * @see org.eclipse.set.model.temporaryintegration.ToolboxTemporaryIntegration#getIntegrationDirectory()
	 * @see #getToolboxTemporaryIntegration()
	 * @generated
	 */
	EAttribute getToolboxTemporaryIntegration_IntegrationDirectory();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.set.model.temporaryintegration.ToolboxTemporaryIntegration#getComparisonInitialState <em>Comparison Initial State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Comparison Initial State</em>'.
	 * @see org.eclipse.set.model.temporaryintegration.ToolboxTemporaryIntegration#getComparisonInitialState()
	 * @see #getToolboxTemporaryIntegration()
	 * @generated
	 */
	EReference getToolboxTemporaryIntegration_ComparisonInitialState();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.set.model.temporaryintegration.ToolboxTemporaryIntegration#getComparisonFinalState <em>Comparison Final State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Comparison Final State</em>'.
	 * @see org.eclipse.set.model.temporaryintegration.ToolboxTemporaryIntegration#getComparisonFinalState()
	 * @see #getToolboxTemporaryIntegration()
	 * @generated
	 */
	EReference getToolboxTemporaryIntegration_ComparisonFinalState();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	TemporaryintegrationFactory getTemporaryintegrationFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.eclipse.set.model.temporaryintegration.impl.TemporaryIntegrationImpl <em>Temporary Integration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.set.model.temporaryintegration.impl.TemporaryIntegrationImpl
		 * @see org.eclipse.set.model.temporaryintegration.impl.TemporaryintegrationPackageImpl#getTemporaryIntegration()
		 * @generated
		 */
		EClass TEMPORARY_INTEGRATION = eINSTANCE.getTemporaryIntegration();

		/**
		 * The meta object literal for the '<em><b>Primary Planning</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TEMPORARY_INTEGRATION__PRIMARY_PLANNING = eINSTANCE.getTemporaryIntegration_PrimaryPlanning();

		/**
		 * The meta object literal for the '<em><b>Primary Planning Filename</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TEMPORARY_INTEGRATION__PRIMARY_PLANNING_FILENAME = eINSTANCE.getTemporaryIntegration_PrimaryPlanningFilename();

		/**
		 * The meta object literal for the '<em><b>Primary Planning Was Valid</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TEMPORARY_INTEGRATION__PRIMARY_PLANNING_WAS_VALID = eINSTANCE.getTemporaryIntegration_PrimaryPlanningWasValid();

		/**
		 * The meta object literal for the '<em><b>Secondary Planning</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TEMPORARY_INTEGRATION__SECONDARY_PLANNING = eINSTANCE.getTemporaryIntegration_SecondaryPlanning();

		/**
		 * The meta object literal for the '<em><b>Secondary Planning Filename</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TEMPORARY_INTEGRATION__SECONDARY_PLANNING_FILENAME = eINSTANCE.getTemporaryIntegration_SecondaryPlanningFilename();

		/**
		 * The meta object literal for the '<em><b>Secondary Planning Was Valid</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TEMPORARY_INTEGRATION__SECONDARY_PLANNING_WAS_VALID = eINSTANCE.getTemporaryIntegration_SecondaryPlanningWasValid();

		/**
		 * The meta object literal for the '<em><b>Composite Planning</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TEMPORARY_INTEGRATION__COMPOSITE_PLANNING = eINSTANCE.getTemporaryIntegration_CompositePlanning();

		/**
		 * The meta object literal for the '<em><b>Integration Directory</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TEMPORARY_INTEGRATION__INTEGRATION_DIRECTORY = eINSTANCE.getTemporaryIntegration_IntegrationDirectory();

		/**
		 * The meta object literal for the '<em><b>Comparison Initial State</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TEMPORARY_INTEGRATION__COMPARISON_INITIAL_STATE = eINSTANCE.getTemporaryIntegration_ComparisonInitialState();

		/**
		 * The meta object literal for the '<em><b>Comparison Final State</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TEMPORARY_INTEGRATION__COMPARISON_FINAL_STATE = eINSTANCE.getTemporaryIntegration_ComparisonFinalState();

		/**
		 * The meta object literal for the '{@link org.eclipse.set.model.temporaryintegration.impl.ToolboxTemporaryIntegrationImpl <em>Toolbox Temporary Integration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.set.model.temporaryintegration.impl.ToolboxTemporaryIntegrationImpl
		 * @see org.eclipse.set.model.temporaryintegration.impl.TemporaryintegrationPackageImpl#getToolboxTemporaryIntegration()
		 * @generated
		 */
		EClass TOOLBOX_TEMPORARY_INTEGRATION = eINSTANCE.getToolboxTemporaryIntegration();

		/**
		 * The meta object literal for the '<em><b>Primary Planning</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TOOLBOX_TEMPORARY_INTEGRATION__PRIMARY_PLANNING = eINSTANCE.getToolboxTemporaryIntegration_PrimaryPlanning();

		/**
		 * The meta object literal for the '<em><b>Primary Planning Filename</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TOOLBOX_TEMPORARY_INTEGRATION__PRIMARY_PLANNING_FILENAME = eINSTANCE.getToolboxTemporaryIntegration_PrimaryPlanningFilename();

		/**
		 * The meta object literal for the '<em><b>Primary Planning Was Valid</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TOOLBOX_TEMPORARY_INTEGRATION__PRIMARY_PLANNING_WAS_VALID = eINSTANCE.getToolboxTemporaryIntegration_PrimaryPlanningWasValid();

		/**
		 * The meta object literal for the '<em><b>Secondary Planning</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TOOLBOX_TEMPORARY_INTEGRATION__SECONDARY_PLANNING = eINSTANCE.getToolboxTemporaryIntegration_SecondaryPlanning();

		/**
		 * The meta object literal for the '<em><b>Secondary Planning Filename</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TOOLBOX_TEMPORARY_INTEGRATION__SECONDARY_PLANNING_FILENAME = eINSTANCE.getToolboxTemporaryIntegration_SecondaryPlanningFilename();

		/**
		 * The meta object literal for the '<em><b>Secondary Planning Was Valid</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TOOLBOX_TEMPORARY_INTEGRATION__SECONDARY_PLANNING_WAS_VALID = eINSTANCE.getToolboxTemporaryIntegration_SecondaryPlanningWasValid();

		/**
		 * The meta object literal for the '<em><b>Composite Planning</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TOOLBOX_TEMPORARY_INTEGRATION__COMPOSITE_PLANNING = eINSTANCE.getToolboxTemporaryIntegration_CompositePlanning();

		/**
		 * The meta object literal for the '<em><b>Integration Directory</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TOOLBOX_TEMPORARY_INTEGRATION__INTEGRATION_DIRECTORY = eINSTANCE.getToolboxTemporaryIntegration_IntegrationDirectory();

		/**
		 * The meta object literal for the '<em><b>Comparison Initial State</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TOOLBOX_TEMPORARY_INTEGRATION__COMPARISON_INITIAL_STATE = eINSTANCE.getToolboxTemporaryIntegration_ComparisonInitialState();

		/**
		 * The meta object literal for the '<em><b>Comparison Final State</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TOOLBOX_TEMPORARY_INTEGRATION__COMPARISON_FINAL_STATE = eINSTANCE.getToolboxTemporaryIntegration_ComparisonFinalState();

	}

} //TemporaryintegrationPackage
