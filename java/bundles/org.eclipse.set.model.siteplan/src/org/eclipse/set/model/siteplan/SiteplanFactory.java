/**
 * Copyright (c) 2021 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.siteplan;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc --> The <b>Factory</b> for the model. It provides a
 * create method for each non-abstract class of the model. <!-- end-user-doc -->
 * 
 * @see org.eclipse.set.model.siteplan.SiteplanPackage
 * @generated
 */
public interface SiteplanFactory extends EFactory {
	/**
	 * The singleton instance of the factory. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	SiteplanFactory eINSTANCE = org.eclipse.set.model.siteplan.impl.SiteplanFactoryImpl
			.init();

	/**
	 * Returns a new object of class '<em>Siteplan</em>'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Siteplan</em>'.
	 * @generated
	 */
	Siteplan createSiteplan();

	/**
	 * Returns a new object of class '<em>State</em>'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>State</em>'.
	 * @generated
	 */
	SiteplanState createSiteplanState();

	/**
	 * Returns a new object of class '<em>Object</em>'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Object</em>'.
	 * @generated
	 */
	SiteplanObject createSiteplanObject();

	/**
	 * Returns a new object of class '<em>Positioned Object</em>'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Positioned Object</em>'.
	 * @generated
	 */
	PositionedObject createPositionedObject();

	/**
	 * Returns a new object of class '<em>Coordinate</em>'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Coordinate</em>'.
	 * @generated
	 */
	Coordinate createCoordinate();

	/**
	 * Returns a new object of class '<em>Position</em>'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Position</em>'.
	 * @generated
	 */
	Position createPosition();

	/**
	 * Returns a new object of class '<em>Route Object</em>'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Route Object</em>'.
	 * @generated
	 */
	RouteObject createRouteObject();

	/**
	 * Returns a new object of class '<em>Route Location</em>'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Route Location</em>'.
	 * @generated
	 */
	RouteLocation createRouteLocation();

	/**
	 * Returns a new object of class '<em>Signal Mount</em>'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Signal Mount</em>'.
	 * @generated
	 */
	SignalMount createSignalMount();

	/**
	 * Returns a new object of class '<em>Signal</em>'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Signal</em>'.
	 * @generated
	 */
	Signal createSignal();

	/**
	 * Returns a new object of class '<em>Signal Screen</em>'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Signal Screen</em>'.
	 * @generated
	 */
	SignalScreen createSignalScreen();

	/**
	 * Returns a new object of class '<em>Label</em>'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Label</em>'.
	 * @generated
	 */
	Label createLabel();

	/**
	 * Returns a new object of class '<em>Track Switch</em>'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Track Switch</em>'.
	 * @generated
	 */
	TrackSwitch createTrackSwitch();

	/**
	 * Returns a new object of class '<em>Track Switch Component</em>'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Track Switch Component</em>'.
	 * @generated
	 */
	TrackSwitchComponent createTrackSwitchComponent();

	/**
	 * Returns a new object of class '<em>Continuous Track Segment</em>'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Continuous Track Segment</em>'.
	 * @generated
	 */
	ContinuousTrackSegment createContinuousTrackSegment();

	/**
	 * Returns a new object of class '<em>Track</em>'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Track</em>'.
	 * @generated
	 */
	Track createTrack();

	/**
	 * Returns a new object of class '<em>Track Section</em>'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Track Section</em>'.
	 * @generated
	 */
	TrackSection createTrackSection();

	/**
	 * Returns a new object of class '<em>Track Segment</em>'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Track Segment</em>'.
	 * @generated
	 */
	TrackSegment createTrackSegment();

	/**
	 * Returns a new object of class '<em>FMA Component</em>'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>FMA Component</em>'.
	 * @generated
	 */
	FMAComponent createFMAComponent();

	/**
	 * Returns a new object of class '<em>Route</em>'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Route</em>'.
	 * @generated
	 */
	Route createRoute();

	/**
	 * Returns a new object of class '<em>Route Section</em>'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Route Section</em>'.
	 * @generated
	 */
	RouteSection createRouteSection();

	/**
	 * Returns a new object of class '<em>KM Marker</em>'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>KM Marker</em>'.
	 * @generated
	 */
	KMMarker createKMMarker();

	/**
	 * Returns a new object of class '<em>Track Switch End Marker</em>'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Track Switch End Marker</em>'.
	 * @generated
	 */
	TrackSwitchEndMarker createTrackSwitchEndMarker();

	/**
	 * Returns a new object of class '<em>Error</em>'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Error</em>'.
	 * @generated
	 */
	Error createError();

	/**
	 * Returns a new object of class '<em>PZB</em>'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>PZB</em>'.
	 * @generated
	 */
	PZB createPZB();

	/**
	 * Returns a new object of class '<em>PZBGU</em>'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>PZBGU</em>'.
	 * @generated
	 */
	PZBGU createPZBGU();

	/**
	 * Returns a new object of class '<em>Track Designation</em>'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Track Designation</em>'.
	 * @generated
	 */
	TrackDesignation createTrackDesignation();

	/**
	 * Returns a new object of class '<em>Track Switch Leg</em>'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Track Switch Leg</em>'.
	 * @generated
	 */
	TrackSwitchLeg createTrackSwitchLeg();

	/**
	 * Returns a new object of class '<em>Station</em>'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Station</em>'.
	 * @generated
	 */
	Station createStation();

	/**
	 * Returns a new object of class '<em>Platform</em>'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Platform</em>'.
	 * @generated
	 */
	Platform createPlatform();

	/**
	 * Returns a new object of class '<em>Track Lock</em>'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Track Lock</em>'.
	 * @generated
	 */
	TrackLock createTrackLock();

	/**
	 * Returns a new object of class '<em>Track Lock Component</em>'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Track Lock Component</em>'.
	 * @generated
	 */
	TrackLockComponent createTrackLockComponent();

	/**
	 * Returns a new object of class '<em>Object Management</em>'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Object Management</em>'.
	 * @generated
	 */
	ObjectManagement createObjectManagement();

	/**
	 * Returns a new object of class '<em>Track Close</em>'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Track Close</em>'.
	 * @generated
	 */
	TrackClose createTrackClose();

	/**
	 * Returns a new object of class '<em>External Element Control</em>'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>External Element Control</em>'.
	 * @generated
	 */
	ExternalElementControl createExternalElementControl();

	/**
	 * Returns a new object of class '<em>Lock Key</em>'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Lock Key</em>'.
	 * @generated
	 */
	LockKey createLockKey();

	/**
	 * Returns a new object of class '<em>Layoutinfo</em>'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Layoutinfo</em>'.
	 * @generated
	 */
	Layoutinfo createLayoutinfo();

	/**
	 * Returns a new object of class '<em>Sheet Cut</em>'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Sheet Cut</em>'.
	 * @generated
	 */
	SheetCut createSheetCut();

	/**
	 * Returns a new object of class '<em>Cant</em>'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Cant</em>'.
	 * @generated
	 */
	Cant createCant();

	/**
	 * Returns a new object of class '<em>Cant Point</em>'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Cant Point</em>'.
	 * @generated
	 */
	CantPoint createCantPoint();

	/**
	 * Returns a new object of class '<em>Unknown Positioned Object</em>'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Unknown Positioned Object</em>'.
	 * @generated
	 */
	UnknownPositionedObject createUnknownPositionedObject();

	/**
	 * Returns the package supported by this factory. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the package supported by this factory.
	 * @generated
	 */
	SiteplanPackage getSiteplanPackage();

} // SiteplanFactory
