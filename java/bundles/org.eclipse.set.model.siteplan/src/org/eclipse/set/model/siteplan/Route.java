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
 * '<em><b>Route</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.set.model.siteplan.Route#getSections <em>Sections</em>}</li>
 *   <li>{@link org.eclipse.set.model.siteplan.Route#getMarkers <em>Markers</em>}</li>
 * </ul>
 *
 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getRoute()
 * @model
 * @generated
 */
public interface Route extends SiteplanObject {
	/**
	 * Returns the value of the '<em><b>Sections</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.set.model.siteplan.RouteSection}.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @return the value of the '<em>Sections</em>' containment reference list.
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getRoute_Sections()
	 * @model containment="true"
	 * @generated
	 */
	EList<RouteSection> getSections();

	/**
	 * Returns the value of the '<em><b>Markers</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.set.model.siteplan.KMMarker}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Markers</em>' containment reference list.
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getRoute_Markers()
	 * @model containment="true"
	 * @generated
	 */
	EList<KMMarker> getMarkers();

} // Route
