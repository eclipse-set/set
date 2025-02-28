/**
 * Copyright (c) 2021 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.siteplan;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object
 * '<em><b>Siteplan</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.set.model.siteplan.Siteplan#getInitialState <em>Initial State</em>}</li>
 *   <li>{@link org.eclipse.set.model.siteplan.Siteplan#getChangedInitialState <em>Changed Initial State</em>}</li>
 *   <li>{@link org.eclipse.set.model.siteplan.Siteplan#getCommonState <em>Common State</em>}</li>
 *   <li>{@link org.eclipse.set.model.siteplan.Siteplan#getChangedFinalState <em>Changed Final State</em>}</li>
 *   <li>{@link org.eclipse.set.model.siteplan.Siteplan#getFinalState <em>Final State</em>}</li>
 *   <li>{@link org.eclipse.set.model.siteplan.Siteplan#getCenterPosition <em>Center Position</em>}</li>
 *   <li>{@link org.eclipse.set.model.siteplan.Siteplan#getObjectManagement <em>Object Management</em>}</li>
 *   <li>{@link org.eclipse.set.model.siteplan.Siteplan#getLayoutInfo <em>Layout Info</em>}</li>
 * </ul>
 *
 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getSiteplan()
 * @model
 * @generated
 */
public interface Siteplan extends EObject {
	/**
	 * Returns the value of the '<em><b>Initial State</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the value of the '<em>Initial State</em>' containment reference.
	 * @see #setInitialState(SiteplanState)
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getSiteplan_InitialState()
	 * @model containment="true"
	 * @generated
	 */
	SiteplanState getInitialState();

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.siteplan.Siteplan#getInitialState <em>Initial State</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Initial State</em>' containment reference.
	 * @see #getInitialState()
	 * @generated
	 */
	void setInitialState(SiteplanState value);

	/**
	 * Returns the value of the '<em><b>Changed Initial State</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the value of the '<em>Changed Initial State</em>' containment reference.
	 * @see #setChangedInitialState(SiteplanState)
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getSiteplan_ChangedInitialState()
	 * @model containment="true"
	 * @generated
	 */
	SiteplanState getChangedInitialState();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.set.model.siteplan.Siteplan#getChangedInitialState
	 * <em>Changed Initial State</em>}' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Changed Initial State</em>'
	 *            containment reference.
	 * @see #getChangedInitialState()
	 * @generated
	 */
	void setChangedInitialState(SiteplanState value);

	/**
	 * Returns the value of the '<em><b>Common State</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the value of the '<em>Common State</em>' containment reference.
	 * @see #setCommonState(SiteplanState)
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getSiteplan_CommonState()
	 * @model containment="true"
	 * @generated
	 */
	SiteplanState getCommonState();

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.siteplan.Siteplan#getCommonState <em>Common State</em>}' containment reference.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @param value the new value of the '<em>Common State</em>' containment reference.
	 * @see #getCommonState()
	 * @generated
	 */
	void setCommonState(SiteplanState value);

	/**
	 * Returns the value of the '<em><b>Changed Final State</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the value of the '<em>Changed Final State</em>' containment reference.
	 * @see #setChangedFinalState(SiteplanState)
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getSiteplan_ChangedFinalState()
	 * @model containment="true"
	 * @generated
	 */
	SiteplanState getChangedFinalState();

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.siteplan.Siteplan#getChangedFinalState <em>Changed Final State</em>}' containment reference.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Changed Final State</em>' containment reference.
	 * @see #getChangedFinalState()
	 * @generated
	 */
	void setChangedFinalState(SiteplanState value);

	/**
	 * Returns the value of the '<em><b>Final State</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the value of the '<em>Final State</em>' containment reference.
	 * @see #setFinalState(SiteplanState)
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getSiteplan_FinalState()
	 * @model containment="true"
	 * @generated
	 */
	SiteplanState getFinalState();

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.siteplan.Siteplan#getFinalState <em>Final State</em>}' containment reference.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @param value the new value of the '<em>Final State</em>' containment reference.
	 * @see #getFinalState()
	 * @generated
	 */
	void setFinalState(SiteplanState value);

	/**
	 * Returns the value of the '<em><b>Center Position</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the value of the '<em>Center Position</em>' containment reference.
	 * @see #setCenterPosition(Position)
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getSiteplan_CenterPosition()
	 * @model containment="true"
	 * @generated
	 */
	Position getCenterPosition();

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.siteplan.Siteplan#getCenterPosition <em>Center Position</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Center Position</em>' containment reference.
	 * @see #getCenterPosition()
	 * @generated
	 */
	void setCenterPosition(Position value);

	/**
	 * Returns the value of the '<em><b>Object Management</b></em>' containment
	 * reference list. The list contents are of type
	 * {@link org.eclipse.set.model.siteplan.ObjectManagement}. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Object Management</em>' containment
	 *         reference list.
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getSiteplan_ObjectManagement()
	 * @model containment="true"
	 * @generated
	 */
	EList<ObjectManagement> getObjectManagement();

	/**
	 * Returns the value of the '<em><b>Layout Info</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.set.model.siteplan.Layoutinfo}.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @return the value of the '<em>Layout Info</em>' containment reference list.
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getSiteplan_LayoutInfo()
	 * @model containment="true"
	 * @generated
	 */
	EList<Layoutinfo> getLayoutInfo();

} // Siteplan
