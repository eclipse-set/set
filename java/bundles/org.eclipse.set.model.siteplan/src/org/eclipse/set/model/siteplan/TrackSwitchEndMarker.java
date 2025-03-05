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
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Track
 * Switch End Marker</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.set.model.siteplan.TrackSwitchEndMarker#getLegACoordinate <em>Leg ACoordinate</em>}</li>
 *   <li>{@link org.eclipse.set.model.siteplan.TrackSwitchEndMarker#getLegBCoordinate <em>Leg BCoordinate</em>}</li>
 * </ul>
 *
 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getTrackSwitchEndMarker()
 * @model
 * @generated
 */
public interface TrackSwitchEndMarker extends EObject {
	/**
	 * Returns the value of the '<em><b>Leg ACoordinate</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the value of the '<em>Leg ACoordinate</em>' containment reference.
	 * @see #setLegACoordinate(Coordinate)
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getTrackSwitchEndMarker_LegACoordinate()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Coordinate getLegACoordinate();

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.siteplan.TrackSwitchEndMarker#getLegACoordinate <em>Leg ACoordinate</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Leg ACoordinate</em>' containment reference.
	 * @see #getLegACoordinate()
	 * @generated
	 */
	void setLegACoordinate(Coordinate value);

	/**
	 * Returns the value of the '<em><b>Leg BCoordinate</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the value of the '<em>Leg BCoordinate</em>' containment reference.
	 * @see #setLegBCoordinate(Coordinate)
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getTrackSwitchEndMarker_LegBCoordinate()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Coordinate getLegBCoordinate();

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.siteplan.TrackSwitchEndMarker#getLegBCoordinate <em>Leg BCoordinate</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Leg BCoordinate</em>' containment reference.
	 * @see #getLegBCoordinate()
	 * @generated
	 */
	void setLegBCoordinate(Coordinate value);

} // TrackSwitchEndMarker
