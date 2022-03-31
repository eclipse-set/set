/**
 * Copyright (c) 2018 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.integrationview;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Integration View</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.set.model.integrationview.IntegrationView#getPrimaryPlanning <em>Primary Planning</em>}</li>
 *   <li>{@link org.eclipse.set.model.integrationview.IntegrationView#getSecondaryPlanning <em>Secondary Planning</em>}</li>
 *   <li>{@link org.eclipse.set.model.integrationview.IntegrationView#getCompositePlanning <em>Composite Planning</em>}</li>
 *   <li>{@link org.eclipse.set.model.integrationview.IntegrationView#getObjectquantities <em>Objectquantities</em>}</li>
 *   <li>{@link org.eclipse.set.model.integrationview.IntegrationView#getConflicts <em>Conflicts</em>}</li>
 *   <li>{@link org.eclipse.set.model.integrationview.IntegrationView#getIntegrationDirectory <em>Integration Directory</em>}</li>
 * </ul>
 *
 * @see org.eclipse.set.model.integrationview.IntegrationviewPackage#getIntegrationView()
 * @model
 * @generated
 */
public interface IntegrationView extends EObject {
	/**
	 * Returns the value of the '<em><b>Primary Planning</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Primary Planning</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Primary Planning</em>' attribute.
	 * @see #setPrimaryPlanning(String)
	 * @see org.eclipse.set.model.integrationview.IntegrationviewPackage#getIntegrationView_PrimaryPlanning()
	 * @model
	 * @generated
	 */
	String getPrimaryPlanning();

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.integrationview.IntegrationView#getPrimaryPlanning <em>Primary Planning</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Primary Planning</em>' attribute.
	 * @see #getPrimaryPlanning()
	 * @generated
	 */
	void setPrimaryPlanning(String value);

	/**
	 * Returns the value of the '<em><b>Secondary Planning</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Secondary Planning</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Secondary Planning</em>' attribute.
	 * @see #setSecondaryPlanning(String)
	 * @see org.eclipse.set.model.integrationview.IntegrationviewPackage#getIntegrationView_SecondaryPlanning()
	 * @model
	 * @generated
	 */
	String getSecondaryPlanning();

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.integrationview.IntegrationView#getSecondaryPlanning <em>Secondary Planning</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Secondary Planning</em>' attribute.
	 * @see #getSecondaryPlanning()
	 * @generated
	 */
	void setSecondaryPlanning(String value);

	/**
	 * Returns the value of the '<em><b>Composite Planning</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Composite Planning</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Composite Planning</em>' attribute.
	 * @see #setCompositePlanning(String)
	 * @see org.eclipse.set.model.integrationview.IntegrationviewPackage#getIntegrationView_CompositePlanning()
	 * @model
	 * @generated
	 */
	String getCompositePlanning();

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.integrationview.IntegrationView#getCompositePlanning <em>Composite Planning</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Composite Planning</em>' attribute.
	 * @see #getCompositePlanning()
	 * @generated
	 */
	void setCompositePlanning(String value);

	/**
	 * Returns the value of the '<em><b>Objectquantities</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.set.model.integrationview.ObjectQuantity}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Objectquantities</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Objectquantities</em>' containment reference list.
	 * @see org.eclipse.set.model.integrationview.IntegrationviewPackage#getIntegrationView_Objectquantities()
	 * @model containment="true"
	 * @generated
	 */
	EList<ObjectQuantity> getObjectquantities();

	/**
	 * Returns the value of the '<em><b>Conflicts</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.set.model.integrationview.Conflict}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Conflicts</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Conflicts</em>' containment reference list.
	 * @see org.eclipse.set.model.integrationview.IntegrationviewPackage#getIntegrationView_Conflicts()
	 * @model containment="true"
	 * @generated
	 */
	EList<Conflict> getConflicts();

	/**
	 * Returns the value of the '<em><b>Integration Directory</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Integration Directory</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Integration Directory</em>' attribute.
	 * @see #setIntegrationDirectory(String)
	 * @see org.eclipse.set.model.integrationview.IntegrationviewPackage#getIntegrationView_IntegrationDirectory()
	 * @model
	 * @generated
	 */
	String getIntegrationDirectory();

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.integrationview.IntegrationView#getIntegrationDirectory <em>Integration Directory</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Integration Directory</em>' attribute.
	 * @see #getIntegrationDirectory()
	 * @generated
	 */
	void setIntegrationDirectory(String value);

} // IntegrationView
