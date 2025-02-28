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
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Route
 * Location</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.set.model.siteplan.RouteLocation#getKm <em>Km</em>}</li>
 *   <li>{@link org.eclipse.set.model.siteplan.RouteLocation#getRoute <em>Route</em>}</li>
 * </ul>
 *
 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getRouteLocation()
 * @model
 * @generated
 */
public interface RouteLocation extends EObject {
	/**
	 * Returns the value of the '<em><b>Km</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Km</em>' attribute.
	 * @see #setKm(String)
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getRouteLocation_Km()
	 * @model
	 * @generated
	 */
	String getKm();

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.siteplan.RouteLocation#getKm <em>Km</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Km</em>' attribute.
	 * @see #getKm()
	 * @generated
	 */
	void setKm(String value);

	/**
	 * Returns the value of the '<em><b>Route</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Route</em>' attribute.
	 * @see #setRoute(String)
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getRouteLocation_Route()
	 * @model
	 * @generated
	 */
	String getRoute();

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.siteplan.RouteLocation#getRoute <em>Route</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Route</em>' attribute.
	 * @see #getRoute()
	 * @generated
	 */
	void setRoute(String value);

} // RouteLocation
