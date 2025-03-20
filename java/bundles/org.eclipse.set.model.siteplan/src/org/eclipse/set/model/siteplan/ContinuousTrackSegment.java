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
 * <!-- begin-user-doc --> A representation of the model object
 * '<em><b>Continuous Track Segment</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.set.model.siteplan.ContinuousTrackSegment#getStart
 * <em>Start</em>}</li>
 * <li>{@link org.eclipse.set.model.siteplan.ContinuousTrackSegment#getEnd
 * <em>End</em>}</li>
 * </ul>
 *
 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getContinuousTrackSegment()
 * @model
 * @generated
 */
public interface ContinuousTrackSegment extends EObject {
	/**
	 * Returns the value of the '<em><b>Start</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Start</em>' containment reference.
	 * @see #setStart(Coordinate)
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getContinuousTrackSegment_Start()
	 * @model containment="true"
	 * @generated
	 */
	Coordinate getStart();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.set.model.siteplan.ContinuousTrackSegment#getStart
	 * <em>Start</em>}' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Start</em>' containment reference.
	 * @see #getStart()
	 * @generated
	 */
	void setStart(Coordinate value);

	/**
	 * Returns the value of the '<em><b>End</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>End</em>' containment reference.
	 * @see #setEnd(Coordinate)
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getContinuousTrackSegment_End()
	 * @model containment="true"
	 * @generated
	 */
	Coordinate getEnd();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.set.model.siteplan.ContinuousTrackSegment#getEnd
	 * <em>End</em>}' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>End</em>' containment reference.
	 * @see #getEnd()
	 * @generated
	 */
	void setEnd(Coordinate value);

} // ContinuousTrackSegment
