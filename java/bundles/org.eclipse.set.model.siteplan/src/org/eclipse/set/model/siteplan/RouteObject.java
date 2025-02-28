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
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Route
 * Object</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.set.model.siteplan.RouteObject#getRouteLocations <em>Route Locations</em>}</li>
 * </ul>
 *
 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getRouteObject()
 * @model
 * @generated
 */
public interface RouteObject extends SiteplanObject {
	/**
	 * Returns the value of the '<em><b>Route Locations</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.set.model.siteplan.RouteLocation}.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @return the value of the '<em>Route Locations</em>' containment reference list.
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getRouteObject_RouteLocations()
	 * @model containment="true"
	 * @generated
	 */
	EList<RouteLocation> getRouteLocations();

} // RouteObject
