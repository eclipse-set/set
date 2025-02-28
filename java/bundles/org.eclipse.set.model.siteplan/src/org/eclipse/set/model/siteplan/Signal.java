/**
 * Copyright (c) 2021 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.siteplan;

import java.math.BigDecimal;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc --> A representation of the model object
 * '<em><b>Signal</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.set.model.siteplan.Signal#getRole <em>Role</em>}</li>
 *   <li>{@link org.eclipse.set.model.siteplan.Signal#getSystem <em>System</em>}</li>
 *   <li>{@link org.eclipse.set.model.siteplan.Signal#getScreen <em>Screen</em>}</li>
 *   <li>{@link org.eclipse.set.model.siteplan.Signal#getLabel <em>Label</em>}</li>
 *   <li>{@link org.eclipse.set.model.siteplan.Signal#getLateralDistance <em>Lateral Distance</em>}</li>
 *   <li>{@link org.eclipse.set.model.siteplan.Signal#getSignalDirection <em>Signal Direction</em>}</li>
 *   <li>{@link org.eclipse.set.model.siteplan.Signal#getMountPosition <em>Mount Position</em>}</li>
 * </ul>
 *
 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getSignal()
 * @model
 * @generated
 */
public interface Signal extends RouteObject {
	/**
	 * Returns the value of the '<em><b>Role</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.set.model.siteplan.SignalRole}.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @return the value of the '<em>Role</em>' attribute.
	 * @see org.eclipse.set.model.siteplan.SignalRole
	 * @see #setRole(SignalRole)
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getSignal_Role()
	 * @model
	 * @generated
	 */
	SignalRole getRole();

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.siteplan.Signal#getRole <em>Role</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Role</em>' attribute.
	 * @see org.eclipse.set.model.siteplan.SignalRole
	 * @see #getRole()
	 * @generated
	 */
	void setRole(SignalRole value);

	/**
	 * Returns the value of the '<em><b>System</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.set.model.siteplan.SignalSystem}.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @return the value of the '<em>System</em>' attribute.
	 * @see org.eclipse.set.model.siteplan.SignalSystem
	 * @see #setSystem(SignalSystem)
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getSignal_System()
	 * @model
	 * @generated
	 */
	SignalSystem getSystem();

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.siteplan.Signal#getSystem <em>System</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>System</em>' attribute.
	 * @see org.eclipse.set.model.siteplan.SignalSystem
	 * @see #getSystem()
	 * @generated
	 */
	void setSystem(SignalSystem value);

	/**
	 * Returns the value of the '<em><b>Screen</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.set.model.siteplan.SignalScreen}.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @return the value of the '<em>Screen</em>' containment reference list.
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getSignal_Screen()
	 * @model containment="true"
	 * @generated
	 */
	EList<SignalScreen> getScreen();

	/**
	 * Returns the value of the '<em><b>Label</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the value of the '<em>Label</em>' containment reference.
	 * @see #setLabel(Label)
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getSignal_Label()
	 * @model containment="true"
	 * @generated
	 */
	Label getLabel();

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.siteplan.Signal#getLabel <em>Label</em>}' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Label</em>' containment reference.
	 * @see #getLabel()
	 * @generated
	 */
	void setLabel(Label value);

	/**
	 * Returns the value of the '<em><b>Lateral Distance</b></em>' attribute
	 * list. The list contents are of type {@link java.math.BigDecimal}. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Lateral Distance</em>' attribute list.
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getSignal_LateralDistance()
	 * @model
	 * @generated
	 */
	EList<BigDecimal> getLateralDistance();

	/**
	 * Returns the value of the '<em><b>Signal Direction</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.set.model.siteplan.Direction}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Signal Direction</em>' attribute.
	 * @see org.eclipse.set.model.siteplan.Direction
	 * @see #setSignalDirection(Direction)
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getSignal_SignalDirection()
	 * @model
	 * @generated
	 */
	Direction getSignalDirection();

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.siteplan.Signal#getSignalDirection <em>Signal Direction</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @param value the new value of the '<em>Signal Direction</em>' attribute.
	 * @see org.eclipse.set.model.siteplan.Direction
	 * @see #getSignalDirection()
	 * @generated
	 */
	void setSignalDirection(Direction value);

	/**
	 * Returns the value of the '<em><b>Mount Position</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the value of the '<em>Mount Position</em>' containment reference.
	 * @see #setMountPosition(Position)
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getSignal_MountPosition()
	 * @model containment="true"
	 * @generated
	 */
	Position getMountPosition();

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.siteplan.Signal#getMountPosition <em>Mount Position</em>}' containment reference.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @param value the new value of the '<em>Mount Position</em>' containment reference.
	 * @see #getMountPosition()
	 * @generated
	 */
	void setMountPosition(Position value);

} // Signal
