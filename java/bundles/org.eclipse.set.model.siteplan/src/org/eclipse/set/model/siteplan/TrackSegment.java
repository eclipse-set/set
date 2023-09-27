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
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Track Segment</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.set.model.siteplan.TrackSegment#getType <em>Type</em>}</li>
 *   <li>{@link org.eclipse.set.model.siteplan.TrackSegment#getPositions <em>Positions</em>}</li>
 * </ul>
 *
 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getTrackSegment()
 * @model
 * @generated
 */
public interface TrackSegment extends EObject {
	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute list.
	 * The list contents are of type {@link org.eclipse.set.model.siteplan.TrackType}.
	 * The literals are from the enumeration {@link org.eclipse.set.model.siteplan.TrackType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute list.
	 * @see org.eclipse.set.model.siteplan.TrackType
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getTrackSegment_Type()
	 * @model
	 * @generated
	 */
	EList<TrackType> getType();

	/**
	 * Returns the value of the '<em><b>Positions</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.set.model.siteplan.Coordinate}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Positions</em>' containment reference list.
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getTrackSegment_Positions()
	 * @model containment="true"
	 * @generated
	 */
	EList<Coordinate> getPositions();

} // TrackSegment
