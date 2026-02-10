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

/**
 * <!-- begin-user-doc --> A representation of the model object
 * '<em><b>Track</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.set.model.siteplan.Track#getSections
 * <em>Sections</em>}</li>
 * <li>{@link org.eclipse.set.model.siteplan.Track#getDesignations
 * <em>Designations</em>}</li>
 * <li>{@link org.eclipse.set.model.siteplan.Track#getStartCoordinate <em>Start
 * Coordinate</em>}</li>
 * </ul>
 *
 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getTrack()
 * @model
 * @generated
 */
public interface Track extends SiteplanObject {
	/**
	 * Returns the value of the '<em><b>Sections</b></em>' containment reference
	 * list. The list contents are of type
	 * {@link org.eclipse.set.model.siteplan.TrackSection}. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Sections</em>' containment reference list.
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getTrack_Sections()
	 * @model containment="true"
	 * @generated
	 */
	EList<TrackSection> getSections();

	/**
	 * Returns the value of the '<em><b>Designations</b></em>' containment
	 * reference list. The list contents are of type
	 * {@link org.eclipse.set.model.siteplan.TrackDesignation}. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Designations</em>' containment reference
	 *         list.
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getTrack_Designations()
	 * @model containment="true"
	 * @generated
	 */
	EList<TrackDesignation> getDesignations();

	/**
	 * Returns the value of the '<em><b>Start Coordinate</b></em>' containment
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Start Coordinate</em>' containment
	 *         reference.
	 * @see #setStartCoordinate(Coordinate)
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getTrack_StartCoordinate()
	 * @model containment="true"
	 * @generated
	 */
	Coordinate getStartCoordinate();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.set.model.siteplan.Track#getStartCoordinate <em>Start
	 * Coordinate</em>}' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Start Coordinate</em>' containment
	 *            reference.
	 * @see #getStartCoordinate()
	 * @generated
	 */
	void setStartCoordinate(Coordinate value);

} // Track
