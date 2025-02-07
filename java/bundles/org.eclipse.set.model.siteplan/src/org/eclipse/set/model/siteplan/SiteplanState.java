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
 * <!-- begin-user-doc --> A representation of the model object
 * '<em><b>State</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.set.model.siteplan.SiteplanState#getSignals
 * <em>Signals</em>}</li>
 * <li>{@link org.eclipse.set.model.siteplan.SiteplanState#getTrackSwitches
 * <em>Track Switches</em>}</li>
 * <li>{@link org.eclipse.set.model.siteplan.SiteplanState#getTrackSwitchEndMarkers
 * <em>Track Switch End Markers</em>}</li>
 * <li>{@link org.eclipse.set.model.siteplan.SiteplanState#getTracks
 * <em>Tracks</em>}</li>
 * <li>{@link org.eclipse.set.model.siteplan.SiteplanState#getFmaComponents
 * <em>Fma Components</em>}</li>
 * <li>{@link org.eclipse.set.model.siteplan.SiteplanState#getPzb
 * <em>Pzb</em>}</li>
 * <li>{@link org.eclipse.set.model.siteplan.SiteplanState#getPzbGU <em>Pzb
 * GU</em>}</li>
 * <li>{@link org.eclipse.set.model.siteplan.SiteplanState#getRoutes
 * <em>Routes</em>}</li>
 * <li>{@link org.eclipse.set.model.siteplan.SiteplanState#getStations
 * <em>Stations</em>}</li>
 * <li>{@link org.eclipse.set.model.siteplan.SiteplanState#getTrackLock
 * <em>Track Lock</em>}</li>
 * <li>{@link org.eclipse.set.model.siteplan.SiteplanState#getErrors
 * <em>Errors</em>}</li>
 * <li>{@link org.eclipse.set.model.siteplan.SiteplanState#getTrackClosures
 * <em>Track Closures</em>}</li>
 * <li>{@link org.eclipse.set.model.siteplan.SiteplanState#getExternalElementControls
 * <em>External Element Controls</em>}</li>
 * <li>{@link org.eclipse.set.model.siteplan.SiteplanState#getLockkeys
 * <em>Lockkeys</em>}</li>
 * <li>{@link org.eclipse.set.model.siteplan.SiteplanState#getCants
 * <em>Cants</em>}</li>
 * <li>{@link org.eclipse.set.model.siteplan.SiteplanState#getUnknownObjects
 * <em>Unknown Objects</em>}</li>
 * </ul>
 *
 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getSiteplanState()
 * @model
 * @generated
 */
public interface SiteplanState extends EObject {
	/**
	 * Returns the value of the '<em><b>Signals</b></em>' containment reference
	 * list. The list contents are of type
	 * {@link org.eclipse.set.model.siteplan.SignalMount}. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Signals</em>' containment reference list.
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getSiteplanState_Signals()
	 * @model containment="true"
	 * @generated
	 */
	EList<SignalMount> getSignals();

	/**
	 * Returns the value of the '<em><b>Track Switches</b></em>' containment
	 * reference list. The list contents are of type
	 * {@link org.eclipse.set.model.siteplan.TrackSwitch}. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Track Switches</em>' containment reference
	 *         list.
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getSiteplanState_TrackSwitches()
	 * @model containment="true"
	 * @generated
	 */
	EList<TrackSwitch> getTrackSwitches();

	/**
	 * Returns the value of the '<em><b>Track Switch End Markers</b></em>'
	 * containment reference list. The list contents are of type
	 * {@link org.eclipse.set.model.siteplan.TrackSwitchEndMarker}. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Track Switch End Markers</em>' containment
	 *         reference list.
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getSiteplanState_TrackSwitchEndMarkers()
	 * @model containment="true"
	 * @generated
	 */
	EList<TrackSwitchEndMarker> getTrackSwitchEndMarkers();

	/**
	 * Returns the value of the '<em><b>Tracks</b></em>' containment reference
	 * list. The list contents are of type
	 * {@link org.eclipse.set.model.siteplan.Track}. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Tracks</em>' containment reference list.
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getSiteplanState_Tracks()
	 * @model containment="true"
	 * @generated
	 */
	EList<Track> getTracks();

	/**
	 * Returns the value of the '<em><b>Fma Components</b></em>' containment
	 * reference list. The list contents are of type
	 * {@link org.eclipse.set.model.siteplan.FMAComponent}. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Fma Components</em>' containment reference
	 *         list.
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getSiteplanState_FmaComponents()
	 * @model containment="true"
	 * @generated
	 */
	EList<FMAComponent> getFmaComponents();

	/**
	 * Returns the value of the '<em><b>Pzb</b></em>' containment reference
	 * list. The list contents are of type
	 * {@link org.eclipse.set.model.siteplan.PZB}. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the value of the '<em>Pzb</em>' containment reference list.
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getSiteplanState_Pzb()
	 * @model containment="true" required="true"
	 * @generated
	 */
	EList<PZB> getPzb();

	/**
	 * Returns the value of the '<em><b>Pzb GU</b></em>' containment reference
	 * list. The list contents are of type
	 * {@link org.eclipse.set.model.siteplan.PZBGU}. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Pzb GU</em>' containment reference list.
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getSiteplanState_PzbGU()
	 * @model containment="true" required="true"
	 * @generated
	 */
	EList<PZBGU> getPzbGU();

	/**
	 * Returns the value of the '<em><b>Routes</b></em>' containment reference
	 * list. The list contents are of type
	 * {@link org.eclipse.set.model.siteplan.Route}. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Routes</em>' containment reference list.
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getSiteplanState_Routes()
	 * @model containment="true"
	 * @generated
	 */
	EList<Route> getRoutes();

	/**
	 * Returns the value of the '<em><b>Stations</b></em>' containment reference
	 * list. The list contents are of type
	 * {@link org.eclipse.set.model.siteplan.Station}. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Stations</em>' containment reference list.
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getSiteplanState_Stations()
	 * @model containment="true"
	 * @generated
	 */
	EList<Station> getStations();

	/**
	 * Returns the value of the '<em><b>Track Lock</b></em>' containment
	 * reference list. The list contents are of type
	 * {@link org.eclipse.set.model.siteplan.TrackLock}. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Track Lock</em>' containment reference
	 *         list.
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getSiteplanState_TrackLock()
	 * @model containment="true"
	 * @generated
	 */
	EList<TrackLock> getTrackLock();

	/**
	 * Returns the value of the '<em><b>Errors</b></em>' containment reference
	 * list. The list contents are of type
	 * {@link org.eclipse.set.model.siteplan.Error}. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Errors</em>' containment reference list.
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getSiteplanState_Errors()
	 * @model containment="true"
	 * @generated
	 */
	EList<org.eclipse.set.model.siteplan.Error> getErrors();

	/**
	 * Returns the value of the '<em><b>Track Closures</b></em>' containment
	 * reference list. The list contents are of type
	 * {@link org.eclipse.set.model.siteplan.TrackClose}. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Track Closures</em>' containment reference
	 *         list.
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getSiteplanState_TrackClosures()
	 * @model containment="true"
	 * @generated
	 */
	EList<TrackClose> getTrackClosures();

	/**
	 * Returns the value of the '<em><b>External Element Controls</b></em>'
	 * containment reference list. The list contents are of type
	 * {@link org.eclipse.set.model.siteplan.ExternalElementControl}. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>External Element Controls</em>' containment
	 *         reference list.
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getSiteplanState_ExternalElementControls()
	 * @model containment="true"
	 * @generated
	 */
	EList<ExternalElementControl> getExternalElementControls();

	/**
	 * Returns the value of the '<em><b>Lockkeys</b></em>' containment reference
	 * list. The list contents are of type
	 * {@link org.eclipse.set.model.siteplan.LockKey}. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Lockkeys</em>' containment reference list.
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getSiteplanState_Lockkeys()
	 * @model containment="true"
	 * @generated
	 */
	EList<LockKey> getLockkeys();

	/**
	 * Returns the value of the '<em><b>Cants</b></em>' containment reference
	 * list. The list contents are of type
	 * {@link org.eclipse.set.model.siteplan.Cant}. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the value of the '<em>Cants</em>' containment reference list.
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getSiteplanState_Cants()
	 * @model containment="true"
	 * @generated
	 */
	EList<Cant> getCants();

	/**
	 * Returns the value of the '<em><b>Unknown Objects</b></em>' containment
	 * reference list. The list contents are of type
	 * {@link org.eclipse.set.model.siteplan.UnknownPositionedObject}. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Unknown Objects</em>' containment reference
	 *         list.
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getSiteplanState_UnknownObjects()
	 * @model containment="true"
	 * @generated
	 */
	EList<UnknownPositionedObject> getUnknownObjects();

} // SiteplanState
