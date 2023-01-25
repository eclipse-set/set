/**
 * Copyright (c) 2022 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.temporaryintegration;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.set.model.model11001.PlanPro.PlanPro_Schnittstelle;
import org.eclipse.set.model.simplemerge.SComparison;

/**
 * <!-- begin-user-doc --> A representation of the model object
 * '<em><b>Temporary Integration</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.set.model.temporaryintegration.TemporaryIntegration#getPrimaryPlanning
 * <em>Primary Planning</em>}</li>
 * <li>{@link org.eclipse.set.model.temporaryintegration.TemporaryIntegration#getPrimaryPlanningFilename
 * <em>Primary Planning Filename</em>}</li>
 * <li>{@link org.eclipse.set.model.temporaryintegration.TemporaryIntegration#isPrimaryPlanningWasValid
 * <em>Primary Planning Was Valid</em>}</li>
 * <li>{@link org.eclipse.set.model.temporaryintegration.TemporaryIntegration#getSecondaryPlanning
 * <em>Secondary Planning</em>}</li>
 * <li>{@link org.eclipse.set.model.temporaryintegration.TemporaryIntegration#getSecondaryPlanningFilename
 * <em>Secondary Planning Filename</em>}</li>
 * <li>{@link org.eclipse.set.model.temporaryintegration.TemporaryIntegration#isSecondaryPlanningWasValid
 * <em>Secondary Planning Was Valid</em>}</li>
 * <li>{@link org.eclipse.set.model.temporaryintegration.TemporaryIntegration#getCompositePlanning
 * <em>Composite Planning</em>}</li>
 * <li>{@link org.eclipse.set.model.temporaryintegration.TemporaryIntegration#getIntegrationDirectory
 * <em>Integration Directory</em>}</li>
 * <li>{@link org.eclipse.set.model.temporaryintegration.TemporaryIntegration#getComparisonInitialState
 * <em>Comparison Initial State</em>}</li>
 * <li>{@link org.eclipse.set.model.temporaryintegration.TemporaryIntegration#getComparisonFinalState
 * <em>Comparison Final State</em>}</li>
 * </ul>
 *
 * @see org.eclipse.set.model.temporaryintegration.TemporaryintegrationPackage#getTemporaryIntegration()
 * @model
 * @generated
 */
public interface TemporaryIntegration extends EObject {
	/**
	 * Returns the value of the '<em><b>Comparison Final State</b></em>'
	 * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Comparison Final State</em>' containment
	 *         reference.
	 * @see #setComparisonFinalState(SComparison)
	 * @see org.eclipse.set.model.temporaryintegration.TemporaryintegrationPackage#getTemporaryIntegration_ComparisonFinalState()
	 * @model containment="true"
	 * @generated
	 */
	SComparison getComparisonFinalState();

	/**
	 * Returns the value of the '<em><b>Comparison Initial State</b></em>'
	 * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Comparison Initial State</em>' containment
	 *         reference.
	 * @see #setComparisonInitialState(SComparison)
	 * @see org.eclipse.set.model.temporaryintegration.TemporaryintegrationPackage#getTemporaryIntegration_ComparisonInitialState()
	 * @model containment="true"
	 * @generated
	 */
	SComparison getComparisonInitialState();

	/**
	 * Returns the value of the '<em><b>Composite Planning</b></em>' containment
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Composite Planning</em>' containment
	 *         reference.
	 * @see #setCompositePlanning(PlanPro_Schnittstelle)
	 * @see org.eclipse.set.model.temporaryintegration.TemporaryintegrationPackage#getTemporaryIntegration_CompositePlanning()
	 * @model containment="true" required="true"
	 * @generated
	 */
	PlanPro_Schnittstelle getCompositePlanning();

	/**
	 * Returns the value of the '<em><b>Integration Directory</b></em>'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Integration Directory</em>' attribute.
	 * @see #setIntegrationDirectory(String)
	 * @see org.eclipse.set.model.temporaryintegration.TemporaryintegrationPackage#getTemporaryIntegration_IntegrationDirectory()
	 * @model
	 * @generated
	 */
	String getIntegrationDirectory();

	/**
	 * Returns the value of the '<em><b>Primary Planning</b></em>' containment
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Primary Planning</em>' containment
	 *         reference.
	 * @see #setPrimaryPlanning(PlanPro_Schnittstelle)
	 * @see org.eclipse.set.model.temporaryintegration.TemporaryintegrationPackage#getTemporaryIntegration_PrimaryPlanning()
	 * @model containment="true" required="true"
	 * @generated
	 */
	PlanPro_Schnittstelle getPrimaryPlanning();

	/**
	 * Returns the value of the '<em><b>Primary Planning Filename</b></em>'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Primary Planning Filename</em>' attribute.
	 * @see #setPrimaryPlanningFilename(String)
	 * @see org.eclipse.set.model.temporaryintegration.TemporaryintegrationPackage#getTemporaryIntegration_PrimaryPlanningFilename()
	 * @model
	 * @generated
	 */
	String getPrimaryPlanningFilename();

	/**
	 * Returns the value of the '<em><b>Secondary Planning</b></em>' containment
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Secondary Planning</em>' containment
	 *         reference.
	 * @see #setSecondaryPlanning(PlanPro_Schnittstelle)
	 * @see org.eclipse.set.model.temporaryintegration.TemporaryintegrationPackage#getTemporaryIntegration_SecondaryPlanning()
	 * @model containment="true" required="true"
	 * @generated
	 */
	PlanPro_Schnittstelle getSecondaryPlanning();

	/**
	 * Returns the value of the '<em><b>Secondary Planning Filename</b></em>'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Secondary Planning Filename</em>'
	 *         attribute.
	 * @see #setSecondaryPlanningFilename(String)
	 * @see org.eclipse.set.model.temporaryintegration.TemporaryintegrationPackage#getTemporaryIntegration_SecondaryPlanningFilename()
	 * @model
	 * @generated
	 */
	String getSecondaryPlanningFilename();

	/**
	 * Returns the value of the '<em><b>Primary Planning Was Valid</b></em>'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Primary Planning Was Valid</em>' attribute.
	 * @see #setPrimaryPlanningWasValid(boolean)
	 * @see org.eclipse.set.model.temporaryintegration.TemporaryintegrationPackage#getTemporaryIntegration_PrimaryPlanningWasValid()
	 * @model
	 * @generated
	 */
	boolean isPrimaryPlanningWasValid();

	/**
	 * Returns the value of the '<em><b>Secondary Planning Was Valid</b></em>'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Secondary Planning Was Valid</em>'
	 *         attribute.
	 * @see #setSecondaryPlanningWasValid(boolean)
	 * @see org.eclipse.set.model.temporaryintegration.TemporaryintegrationPackage#getTemporaryIntegration_SecondaryPlanningWasValid()
	 * @model
	 * @generated
	 */
	boolean isSecondaryPlanningWasValid();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.set.model.temporaryintegration.TemporaryIntegration#getComparisonFinalState
	 * <em>Comparison Final State</em>}' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Comparison Final State</em>'
	 *            containment reference.
	 * @see #getComparisonFinalState()
	 * @generated
	 */
	void setComparisonFinalState(SComparison value);

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.set.model.temporaryintegration.TemporaryIntegration#getComparisonInitialState
	 * <em>Comparison Initial State</em>}' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Comparison Initial State</em>'
	 *            containment reference.
	 * @see #getComparisonInitialState()
	 * @generated
	 */
	void setComparisonInitialState(SComparison value);

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.set.model.temporaryintegration.TemporaryIntegration#getCompositePlanning
	 * <em>Composite Planning</em>}' containment reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Composite Planning</em>' containment
	 *            reference.
	 * @see #getCompositePlanning()
	 * @generated
	 */
	void setCompositePlanning(PlanPro_Schnittstelle value);

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.set.model.temporaryintegration.TemporaryIntegration#getIntegrationDirectory
	 * <em>Integration Directory</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Integration Directory</em>'
	 *            attribute.
	 * @see #getIntegrationDirectory()
	 * @generated
	 */
	void setIntegrationDirectory(String value);

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.set.model.temporaryintegration.TemporaryIntegration#getPrimaryPlanning
	 * <em>Primary Planning</em>}' containment reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Primary Planning</em>' containment
	 *            reference.
	 * @see #getPrimaryPlanning()
	 * @generated
	 */
	void setPrimaryPlanning(PlanPro_Schnittstelle value);

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.set.model.temporaryintegration.TemporaryIntegration#getPrimaryPlanningFilename
	 * <em>Primary Planning Filename</em>}' attribute. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Primary Planning Filename</em>'
	 *            attribute.
	 * @see #getPrimaryPlanningFilename()
	 * @generated
	 */
	void setPrimaryPlanningFilename(String value);

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.set.model.temporaryintegration.TemporaryIntegration#isPrimaryPlanningWasValid
	 * <em>Primary Planning Was Valid</em>}' attribute. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Primary Planning Was Valid</em>'
	 *            attribute.
	 * @see #isPrimaryPlanningWasValid()
	 * @generated
	 */
	void setPrimaryPlanningWasValid(boolean value);

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.set.model.temporaryintegration.TemporaryIntegration#getSecondaryPlanning
	 * <em>Secondary Planning</em>}' containment reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Secondary Planning</em>' containment
	 *            reference.
	 * @see #getSecondaryPlanning()
	 * @generated
	 */
	void setSecondaryPlanning(PlanPro_Schnittstelle value);

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.set.model.temporaryintegration.TemporaryIntegration#getSecondaryPlanningFilename
	 * <em>Secondary Planning Filename</em>}' attribute. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Secondary Planning Filename</em>'
	 *            attribute.
	 * @see #getSecondaryPlanningFilename()
	 * @generated
	 */
	void setSecondaryPlanningFilename(String value);

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.set.model.temporaryintegration.TemporaryIntegration#isSecondaryPlanningWasValid
	 * <em>Secondary Planning Was Valid</em>}' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Secondary Planning Was Valid</em>'
	 *            attribute.
	 * @see #isSecondaryPlanningWasValid()
	 * @generated
	 */
	void setSecondaryPlanningWasValid(boolean value);

} // TemporaryIntegration
