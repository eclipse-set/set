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
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Track
 * Switch Leg</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.set.model.siteplan.TrackSwitchLeg#getConnection
 * <em>Connection</em>}</li>
 * <li>{@link org.eclipse.set.model.siteplan.TrackSwitchLeg#getCoordinates
 * <em>Coordinates</em>}</li>
 * </ul>
 *
 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getTrackSwitchLeg()
 * @model
 * @generated
 */
public interface TrackSwitchLeg extends EObject {
	/**
	 * Returns the value of the '<em><b>Connection</b></em>' attribute. The
	 * literals are from the enumeration
	 * {@link org.eclipse.set.model.siteplan.LeftRight}. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Connection</em>' attribute.
	 * @see org.eclipse.set.model.siteplan.LeftRight
	 * @see #setConnection(LeftRight)
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getTrackSwitchLeg_Connection()
	 * @model
	 * @generated
	 */
	LeftRight getConnection();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.set.model.siteplan.TrackSwitchLeg#getConnection
	 * <em>Connection</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Connection</em>' attribute.
	 * @see org.eclipse.set.model.siteplan.LeftRight
	 * @see #getConnection()
	 * @generated
	 */
	void setConnection(LeftRight value);

	/**
	 * Returns the value of the '<em><b>Coordinates</b></em>' containment
	 * reference list. The list contents are of type
	 * {@link org.eclipse.set.model.siteplan.Coordinate}. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Coordinates</em>' containment reference
	 *         list.
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getTrackSwitchLeg_Coordinates()
	 * @model containment="true"
	 * @generated
	 */
	EList<Coordinate> getCoordinates();

} // TrackSwitchLeg
