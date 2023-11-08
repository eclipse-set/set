/**
 * Copyright (c) 2021 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.siteplan;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Track Lock Component</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.set.model.siteplan.TrackLockComponent#getPosition <em>Position</em>}</li>
 *   <li>{@link org.eclipse.set.model.siteplan.TrackLockComponent#getTrackLockSignal <em>Track Lock Signal</em>}</li>
 *   <li>{@link org.eclipse.set.model.siteplan.TrackLockComponent#getEjectionDirection <em>Ejection Direction</em>}</li>
 *   <li>{@link org.eclipse.set.model.siteplan.TrackLockComponent#getGuid <em>Guid</em>}</li>
 * </ul>
 *
 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getTrackLockComponent()
 * @model
 * @generated
 */
public interface TrackLockComponent extends EObject {
	/**
	 * Returns the value of the '<em><b>Position</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Position</em>' containment reference.
	 * @see #setPosition(Position)
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getTrackLockComponent_Position()
	 * @model containment="true"
	 * @generated
	 */
	Position getPosition();

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.siteplan.TrackLockComponent#getPosition <em>Position</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Position</em>' containment reference.
	 * @see #getPosition()
	 * @generated
	 */
	void setPosition(Position value);

	/**
	 * Returns the value of the '<em><b>Track Lock Signal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Track Lock Signal</em>' attribute.
	 * @see #setTrackLockSignal(String)
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getTrackLockComponent_TrackLockSignal()
	 * @model
	 * @generated
	 */
	String getTrackLockSignal();

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.siteplan.TrackLockComponent#getTrackLockSignal <em>Track Lock Signal</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Track Lock Signal</em>' attribute.
	 * @see #getTrackLockSignal()
	 * @generated
	 */
	void setTrackLockSignal(String value);

	/**
	 * Returns the value of the '<em><b>Ejection Direction</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.set.model.siteplan.LeftRight}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ejection Direction</em>' attribute.
	 * @see org.eclipse.set.model.siteplan.LeftRight
	 * @see #setEjectionDirection(LeftRight)
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getTrackLockComponent_EjectionDirection()
	 * @model
	 * @generated
	 */
	LeftRight getEjectionDirection();

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.siteplan.TrackLockComponent#getEjectionDirection <em>Ejection Direction</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ejection Direction</em>' attribute.
	 * @see org.eclipse.set.model.siteplan.LeftRight
	 * @see #getEjectionDirection()
	 * @generated
	 */
	void setEjectionDirection(LeftRight value);

	/**
	 * Returns the value of the '<em><b>Guid</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Guid</em>' attribute.
	 * @see #setGuid(String)
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getTrackLockComponent_Guid()
	 * @model
	 * @generated
	 */
	String getGuid();

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.siteplan.TrackLockComponent#getGuid <em>Guid</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Guid</em>' attribute.
	 * @see #getGuid()
	 * @generated
	 */
	void setGuid(String value);

} // TrackLockComponent
