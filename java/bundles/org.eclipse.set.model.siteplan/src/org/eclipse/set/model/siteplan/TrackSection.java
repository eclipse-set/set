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
 * A representation of the model object '<em><b>Track Section</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.set.model.siteplan.TrackSection#getGuid <em>Guid</em>}</li>
 *   <li>{@link org.eclipse.set.model.siteplan.TrackSection#getShape <em>Shape</em>}</li>
 *   <li>{@link org.eclipse.set.model.siteplan.TrackSection#getSegments <em>Segments</em>}</li>
 * </ul>
 *
 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getTrackSection()
 * @model
 * @generated
 */
public interface TrackSection extends EObject {
	/**
	 * Returns the value of the '<em><b>Guid</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Guid</em>' attribute.
	 * @see #setGuid(String)
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getTrackSection_Guid()
	 * @model
	 * @generated
	 */
	String getGuid();

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.siteplan.TrackSection#getGuid <em>Guid</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Guid</em>' attribute.
	 * @see #getGuid()
	 * @generated
	 */
	void setGuid(String value);

	/**
	 * Returns the value of the '<em><b>Shape</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.set.model.siteplan.TrackShape}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Shape</em>' attribute.
	 * @see org.eclipse.set.model.siteplan.TrackShape
	 * @see #setShape(TrackShape)
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getTrackSection_Shape()
	 * @model
	 * @generated
	 */
	TrackShape getShape();

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.siteplan.TrackSection#getShape <em>Shape</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Shape</em>' attribute.
	 * @see org.eclipse.set.model.siteplan.TrackShape
	 * @see #getShape()
	 * @generated
	 */
	void setShape(TrackShape value);

	/**
	 * Returns the value of the '<em><b>Segments</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.set.model.siteplan.TrackSegment}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Segments</em>' containment reference list.
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getTrackSection_Segments()
	 * @model containment="true"
	 * @generated
	 */
	EList<TrackSegment> getSegments();

} // TrackSection
