/**
 * Copyright (c) 2021 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.siteplan;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Track Close</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.set.model.siteplan.TrackClose#getTrackCloseType <em>Track Close Type</em>}</li>
 *   <li>{@link org.eclipse.set.model.siteplan.TrackClose#getPosition <em>Position</em>}</li>
 * </ul>
 *
 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getTrackClose()
 * @model
 * @generated
 */
public interface TrackClose extends SiteplanObject {
	/**
	 * Returns the value of the '<em><b>Track Close Type</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.set.model.siteplan.TrackCloseType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Track Close Type</em>' attribute.
	 * @see org.eclipse.set.model.siteplan.TrackCloseType
	 * @see #setTrackCloseType(TrackCloseType)
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getTrackClose_TrackCloseType()
	 * @model
	 * @generated
	 */
	TrackCloseType getTrackCloseType();

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.siteplan.TrackClose#getTrackCloseType <em>Track Close Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Track Close Type</em>' attribute.
	 * @see org.eclipse.set.model.siteplan.TrackCloseType
	 * @see #getTrackCloseType()
	 * @generated
	 */
	void setTrackCloseType(TrackCloseType value);

	/**
	 * Returns the value of the '<em><b>Position</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Position</em>' containment reference.
	 * @see #setPosition(Position)
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getTrackClose_Position()
	 * @model containment="true"
	 * @generated
	 */
	Position getPosition();

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.siteplan.TrackClose#getPosition <em>Position</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Position</em>' containment reference.
	 * @see #getPosition()
	 * @generated
	 */
	void setPosition(Position value);

} // TrackClose
