/**
 * Copyright (c) 2021 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.siteplan.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.set.model.siteplan.Cant;
import org.eclipse.set.model.siteplan.ExternalElementControl;
import org.eclipse.set.model.siteplan.FMAComponent;
import org.eclipse.set.model.siteplan.LockKey;
import org.eclipse.set.model.siteplan.PZB;
import org.eclipse.set.model.siteplan.PZBGU;
import org.eclipse.set.model.siteplan.Route;
import org.eclipse.set.model.siteplan.SignalMount;
import org.eclipse.set.model.siteplan.SiteplanPackage;
import org.eclipse.set.model.siteplan.SiteplanState;
import org.eclipse.set.model.siteplan.Station;
import org.eclipse.set.model.siteplan.Track;
import org.eclipse.set.model.siteplan.TrackClose;
import org.eclipse.set.model.siteplan.TrackLock;
import org.eclipse.set.model.siteplan.TrackSwitch;
import org.eclipse.set.model.siteplan.TrackSwitchEndMarker;
import org.eclipse.set.model.siteplan.UnknownPositionedObject;

/**
 * <!-- begin-user-doc --> An implementation of the model object
 * '<em><b>State</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.set.model.siteplan.impl.SiteplanStateImpl#getSignals <em>Signals</em>}</li>
 *   <li>{@link org.eclipse.set.model.siteplan.impl.SiteplanStateImpl#getTrackSwitches <em>Track Switches</em>}</li>
 *   <li>{@link org.eclipse.set.model.siteplan.impl.SiteplanStateImpl#getTrackSwitchEndMarkers <em>Track Switch End Markers</em>}</li>
 *   <li>{@link org.eclipse.set.model.siteplan.impl.SiteplanStateImpl#getTracks <em>Tracks</em>}</li>
 *   <li>{@link org.eclipse.set.model.siteplan.impl.SiteplanStateImpl#getFmaComponents <em>Fma Components</em>}</li>
 *   <li>{@link org.eclipse.set.model.siteplan.impl.SiteplanStateImpl#getPzb <em>Pzb</em>}</li>
 *   <li>{@link org.eclipse.set.model.siteplan.impl.SiteplanStateImpl#getPzbGU <em>Pzb GU</em>}</li>
 *   <li>{@link org.eclipse.set.model.siteplan.impl.SiteplanStateImpl#getRoutes <em>Routes</em>}</li>
 *   <li>{@link org.eclipse.set.model.siteplan.impl.SiteplanStateImpl#getStations <em>Stations</em>}</li>
 *   <li>{@link org.eclipse.set.model.siteplan.impl.SiteplanStateImpl#getTrackLock <em>Track Lock</em>}</li>
 *   <li>{@link org.eclipse.set.model.siteplan.impl.SiteplanStateImpl#getErrors <em>Errors</em>}</li>
 *   <li>{@link org.eclipse.set.model.siteplan.impl.SiteplanStateImpl#getTrackClosures <em>Track Closures</em>}</li>
 *   <li>{@link org.eclipse.set.model.siteplan.impl.SiteplanStateImpl#getExternalElementControls <em>External Element Controls</em>}</li>
 *   <li>{@link org.eclipse.set.model.siteplan.impl.SiteplanStateImpl#getLockkeys <em>Lockkeys</em>}</li>
 *   <li>{@link org.eclipse.set.model.siteplan.impl.SiteplanStateImpl#getCants <em>Cants</em>}</li>
 *   <li>{@link org.eclipse.set.model.siteplan.impl.SiteplanStateImpl#getUnknownObjects <em>Unknown Objects</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SiteplanStateImpl extends MinimalEObjectImpl.Container
		implements SiteplanState {
	/**
	 * The cached value of the '{@link #getSignals() <em>Signals</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getSignals()
	 * @generated
	 * @ordered
	 */
	protected EList<SignalMount> signals;

	/**
	 * The cached value of the '{@link #getTrackSwitches() <em>Track Switches</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #getTrackSwitches()
	 * @generated
	 * @ordered
	 */
	protected EList<TrackSwitch> trackSwitches;

	/**
	 * The cached value of the '{@link #getTrackSwitchEndMarkers() <em>Track Switch End Markers</em>}' containment reference list.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @see #getTrackSwitchEndMarkers()
	 * @generated
	 * @ordered
	 */
	protected EList<TrackSwitchEndMarker> trackSwitchEndMarkers;

	/**
	 * The cached value of the '{@link #getTracks() <em>Tracks</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getTracks()
	 * @generated
	 * @ordered
	 */
	protected EList<Track> tracks;

	/**
	 * The cached value of the '{@link #getFmaComponents() <em>Fma Components</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFmaComponents()
	 * @generated
	 * @ordered
	 */
	protected EList<FMAComponent> fmaComponents;

	/**
	 * The cached value of the '{@link #getPzb() <em>Pzb</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getPzb()
	 * @generated
	 * @ordered
	 */
	protected EList<PZB> pzb;

	/**
	 * The cached value of the '{@link #getPzbGU() <em>Pzb GU</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getPzbGU()
	 * @generated
	 * @ordered
	 */
	protected EList<PZBGU> pzbGU;

	/**
	 * The cached value of the '{@link #getRoutes() <em>Routes</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getRoutes()
	 * @generated
	 * @ordered
	 */
	protected EList<Route> routes;

	/**
	 * The cached value of the '{@link #getStations() <em>Stations</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getStations()
	 * @generated
	 * @ordered
	 */
	protected EList<Station> stations;

	/**
	 * The cached value of the '{@link #getTrackLock() <em>Track Lock</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getTrackLock()
	 * @generated
	 * @ordered
	 */
	protected EList<TrackLock> trackLock;

	/**
	 * The cached value of the '{@link #getErrors() <em>Errors</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getErrors()
	 * @generated
	 * @ordered
	 */
	protected EList<org.eclipse.set.model.siteplan.Error> errors;

	/**
	 * The cached value of the '{@link #getTrackClosures() <em>Track Closures</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #getTrackClosures()
	 * @generated
	 * @ordered
	 */
	protected EList<TrackClose> trackClosures;

	/**
	 * The cached value of the '{@link #getExternalElementControls()
	 * <em>External Element Controls</em>}' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getExternalElementControls()
	 * @generated
	 * @ordered
	 */
	protected EList<ExternalElementControl> externalElementControls;

	/**
	 * The cached value of the '{@link #getLockkeys() <em>Lockkeys</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getLockkeys()
	 * @generated
	 * @ordered
	 */
	protected EList<LockKey> lockkeys;

	/**
	 * The cached value of the '{@link #getCants() <em>Cants</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getCants()
	 * @generated
	 * @ordered
	 */
	protected EList<Cant> cants;

	/**
	 * The cached value of the '{@link #getUnknownObjects() <em>Unknown Objects</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #getUnknownObjects()
	 * @generated
	 * @ordered
	 */
	protected EList<UnknownPositionedObject> unknownObjects;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected SiteplanStateImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SiteplanPackage.Literals.SITEPLAN_STATE;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<SignalMount> getSignals() {
		if (signals == null) {
			signals = new EObjectContainmentEList<SignalMount>(SignalMount.class, this, SiteplanPackage.SITEPLAN_STATE__SIGNALS);
		}
		return signals;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<TrackSwitch> getTrackSwitches() {
		if (trackSwitches == null) {
			trackSwitches = new EObjectContainmentEList<TrackSwitch>(TrackSwitch.class, this, SiteplanPackage.SITEPLAN_STATE__TRACK_SWITCHES);
		}
		return trackSwitches;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<TrackSwitchEndMarker> getTrackSwitchEndMarkers() {
		if (trackSwitchEndMarkers == null) {
			trackSwitchEndMarkers = new EObjectContainmentEList<TrackSwitchEndMarker>(TrackSwitchEndMarker.class, this, SiteplanPackage.SITEPLAN_STATE__TRACK_SWITCH_END_MARKERS);
		}
		return trackSwitchEndMarkers;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Track> getTracks() {
		if (tracks == null) {
			tracks = new EObjectContainmentEList<Track>(Track.class, this, SiteplanPackage.SITEPLAN_STATE__TRACKS);
		}
		return tracks;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<FMAComponent> getFmaComponents() {
		if (fmaComponents == null) {
			fmaComponents = new EObjectContainmentEList<FMAComponent>(FMAComponent.class, this, SiteplanPackage.SITEPLAN_STATE__FMA_COMPONENTS);
		}
		return fmaComponents;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<PZB> getPzb() {
		if (pzb == null) {
			pzb = new EObjectContainmentEList<PZB>(PZB.class, this, SiteplanPackage.SITEPLAN_STATE__PZB);
		}
		return pzb;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<PZBGU> getPzbGU() {
		if (pzbGU == null) {
			pzbGU = new EObjectContainmentEList<PZBGU>(PZBGU.class, this, SiteplanPackage.SITEPLAN_STATE__PZB_GU);
		}
		return pzbGU;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Route> getRoutes() {
		if (routes == null) {
			routes = new EObjectContainmentEList<Route>(Route.class, this, SiteplanPackage.SITEPLAN_STATE__ROUTES);
		}
		return routes;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Station> getStations() {
		if (stations == null) {
			stations = new EObjectContainmentEList<Station>(Station.class, this, SiteplanPackage.SITEPLAN_STATE__STATIONS);
		}
		return stations;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<TrackLock> getTrackLock() {
		if (trackLock == null) {
			trackLock = new EObjectContainmentEList<TrackLock>(TrackLock.class, this, SiteplanPackage.SITEPLAN_STATE__TRACK_LOCK);
		}
		return trackLock;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<org.eclipse.set.model.siteplan.Error> getErrors() {
		if (errors == null) {
			errors = new EObjectContainmentEList<org.eclipse.set.model.siteplan.Error>(org.eclipse.set.model.siteplan.Error.class, this, SiteplanPackage.SITEPLAN_STATE__ERRORS);
		}
		return errors;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<TrackClose> getTrackClosures() {
		if (trackClosures == null) {
			trackClosures = new EObjectContainmentEList<TrackClose>(TrackClose.class, this, SiteplanPackage.SITEPLAN_STATE__TRACK_CLOSURES);
		}
		return trackClosures;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<ExternalElementControl> getExternalElementControls() {
		if (externalElementControls == null) {
			externalElementControls = new EObjectContainmentEList<ExternalElementControl>(ExternalElementControl.class, this, SiteplanPackage.SITEPLAN_STATE__EXTERNAL_ELEMENT_CONTROLS);
		}
		return externalElementControls;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<LockKey> getLockkeys() {
		if (lockkeys == null) {
			lockkeys = new EObjectContainmentEList<LockKey>(LockKey.class, this, SiteplanPackage.SITEPLAN_STATE__LOCKKEYS);
		}
		return lockkeys;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Cant> getCants() {
		if (cants == null) {
			cants = new EObjectContainmentEList<Cant>(Cant.class, this, SiteplanPackage.SITEPLAN_STATE__CANTS);
		}
		return cants;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<UnknownPositionedObject> getUnknownObjects() {
		if (unknownObjects == null) {
			unknownObjects = new EObjectContainmentEList<UnknownPositionedObject>(UnknownPositionedObject.class, this, SiteplanPackage.SITEPLAN_STATE__UNKNOWN_OBJECTS);
		}
		return unknownObjects;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd,
			int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SiteplanPackage.SITEPLAN_STATE__SIGNALS:
				return ((InternalEList<?>)getSignals()).basicRemove(otherEnd, msgs);
			case SiteplanPackage.SITEPLAN_STATE__TRACK_SWITCHES:
				return ((InternalEList<?>)getTrackSwitches()).basicRemove(otherEnd, msgs);
			case SiteplanPackage.SITEPLAN_STATE__TRACK_SWITCH_END_MARKERS:
				return ((InternalEList<?>)getTrackSwitchEndMarkers()).basicRemove(otherEnd, msgs);
			case SiteplanPackage.SITEPLAN_STATE__TRACKS:
				return ((InternalEList<?>)getTracks()).basicRemove(otherEnd, msgs);
			case SiteplanPackage.SITEPLAN_STATE__FMA_COMPONENTS:
				return ((InternalEList<?>)getFmaComponents()).basicRemove(otherEnd, msgs);
			case SiteplanPackage.SITEPLAN_STATE__PZB:
				return ((InternalEList<?>)getPzb()).basicRemove(otherEnd, msgs);
			case SiteplanPackage.SITEPLAN_STATE__PZB_GU:
				return ((InternalEList<?>)getPzbGU()).basicRemove(otherEnd, msgs);
			case SiteplanPackage.SITEPLAN_STATE__ROUTES:
				return ((InternalEList<?>)getRoutes()).basicRemove(otherEnd, msgs);
			case SiteplanPackage.SITEPLAN_STATE__STATIONS:
				return ((InternalEList<?>)getStations()).basicRemove(otherEnd, msgs);
			case SiteplanPackage.SITEPLAN_STATE__TRACK_LOCK:
				return ((InternalEList<?>)getTrackLock()).basicRemove(otherEnd, msgs);
			case SiteplanPackage.SITEPLAN_STATE__ERRORS:
				return ((InternalEList<?>)getErrors()).basicRemove(otherEnd, msgs);
			case SiteplanPackage.SITEPLAN_STATE__TRACK_CLOSURES:
				return ((InternalEList<?>)getTrackClosures()).basicRemove(otherEnd, msgs);
			case SiteplanPackage.SITEPLAN_STATE__EXTERNAL_ELEMENT_CONTROLS:
				return ((InternalEList<?>)getExternalElementControls()).basicRemove(otherEnd, msgs);
			case SiteplanPackage.SITEPLAN_STATE__LOCKKEYS:
				return ((InternalEList<?>)getLockkeys()).basicRemove(otherEnd, msgs);
			case SiteplanPackage.SITEPLAN_STATE__CANTS:
				return ((InternalEList<?>)getCants()).basicRemove(otherEnd, msgs);
			case SiteplanPackage.SITEPLAN_STATE__UNKNOWN_OBJECTS:
				return ((InternalEList<?>)getUnknownObjects()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case SiteplanPackage.SITEPLAN_STATE__SIGNALS:
				return getSignals();
			case SiteplanPackage.SITEPLAN_STATE__TRACK_SWITCHES:
				return getTrackSwitches();
			case SiteplanPackage.SITEPLAN_STATE__TRACK_SWITCH_END_MARKERS:
				return getTrackSwitchEndMarkers();
			case SiteplanPackage.SITEPLAN_STATE__TRACKS:
				return getTracks();
			case SiteplanPackage.SITEPLAN_STATE__FMA_COMPONENTS:
				return getFmaComponents();
			case SiteplanPackage.SITEPLAN_STATE__PZB:
				return getPzb();
			case SiteplanPackage.SITEPLAN_STATE__PZB_GU:
				return getPzbGU();
			case SiteplanPackage.SITEPLAN_STATE__ROUTES:
				return getRoutes();
			case SiteplanPackage.SITEPLAN_STATE__STATIONS:
				return getStations();
			case SiteplanPackage.SITEPLAN_STATE__TRACK_LOCK:
				return getTrackLock();
			case SiteplanPackage.SITEPLAN_STATE__ERRORS:
				return getErrors();
			case SiteplanPackage.SITEPLAN_STATE__TRACK_CLOSURES:
				return getTrackClosures();
			case SiteplanPackage.SITEPLAN_STATE__EXTERNAL_ELEMENT_CONTROLS:
				return getExternalElementControls();
			case SiteplanPackage.SITEPLAN_STATE__LOCKKEYS:
				return getLockkeys();
			case SiteplanPackage.SITEPLAN_STATE__CANTS:
				return getCants();
			case SiteplanPackage.SITEPLAN_STATE__UNKNOWN_OBJECTS:
				return getUnknownObjects();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case SiteplanPackage.SITEPLAN_STATE__SIGNALS:
				getSignals().clear();
				getSignals().addAll((Collection<? extends SignalMount>)newValue);
				return;
			case SiteplanPackage.SITEPLAN_STATE__TRACK_SWITCHES:
				getTrackSwitches().clear();
				getTrackSwitches().addAll((Collection<? extends TrackSwitch>)newValue);
				return;
			case SiteplanPackage.SITEPLAN_STATE__TRACK_SWITCH_END_MARKERS:
				getTrackSwitchEndMarkers().clear();
				getTrackSwitchEndMarkers().addAll((Collection<? extends TrackSwitchEndMarker>)newValue);
				return;
			case SiteplanPackage.SITEPLAN_STATE__TRACKS:
				getTracks().clear();
				getTracks().addAll((Collection<? extends Track>)newValue);
				return;
			case SiteplanPackage.SITEPLAN_STATE__FMA_COMPONENTS:
				getFmaComponents().clear();
				getFmaComponents().addAll((Collection<? extends FMAComponent>)newValue);
				return;
			case SiteplanPackage.SITEPLAN_STATE__PZB:
				getPzb().clear();
				getPzb().addAll((Collection<? extends PZB>)newValue);
				return;
			case SiteplanPackage.SITEPLAN_STATE__PZB_GU:
				getPzbGU().clear();
				getPzbGU().addAll((Collection<? extends PZBGU>)newValue);
				return;
			case SiteplanPackage.SITEPLAN_STATE__ROUTES:
				getRoutes().clear();
				getRoutes().addAll((Collection<? extends Route>)newValue);
				return;
			case SiteplanPackage.SITEPLAN_STATE__STATIONS:
				getStations().clear();
				getStations().addAll((Collection<? extends Station>)newValue);
				return;
			case SiteplanPackage.SITEPLAN_STATE__TRACK_LOCK:
				getTrackLock().clear();
				getTrackLock().addAll((Collection<? extends TrackLock>)newValue);
				return;
			case SiteplanPackage.SITEPLAN_STATE__ERRORS:
				getErrors().clear();
				getErrors().addAll((Collection<? extends org.eclipse.set.model.siteplan.Error>)newValue);
				return;
			case SiteplanPackage.SITEPLAN_STATE__TRACK_CLOSURES:
				getTrackClosures().clear();
				getTrackClosures().addAll((Collection<? extends TrackClose>)newValue);
				return;
			case SiteplanPackage.SITEPLAN_STATE__EXTERNAL_ELEMENT_CONTROLS:
				getExternalElementControls().clear();
				getExternalElementControls().addAll((Collection<? extends ExternalElementControl>)newValue);
				return;
			case SiteplanPackage.SITEPLAN_STATE__LOCKKEYS:
				getLockkeys().clear();
				getLockkeys().addAll((Collection<? extends LockKey>)newValue);
				return;
			case SiteplanPackage.SITEPLAN_STATE__CANTS:
				getCants().clear();
				getCants().addAll((Collection<? extends Cant>)newValue);
				return;
			case SiteplanPackage.SITEPLAN_STATE__UNKNOWN_OBJECTS:
				getUnknownObjects().clear();
				getUnknownObjects().addAll((Collection<? extends UnknownPositionedObject>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case SiteplanPackage.SITEPLAN_STATE__SIGNALS:
				getSignals().clear();
				return;
			case SiteplanPackage.SITEPLAN_STATE__TRACK_SWITCHES:
				getTrackSwitches().clear();
				return;
			case SiteplanPackage.SITEPLAN_STATE__TRACK_SWITCH_END_MARKERS:
				getTrackSwitchEndMarkers().clear();
				return;
			case SiteplanPackage.SITEPLAN_STATE__TRACKS:
				getTracks().clear();
				return;
			case SiteplanPackage.SITEPLAN_STATE__FMA_COMPONENTS:
				getFmaComponents().clear();
				return;
			case SiteplanPackage.SITEPLAN_STATE__PZB:
				getPzb().clear();
				return;
			case SiteplanPackage.SITEPLAN_STATE__PZB_GU:
				getPzbGU().clear();
				return;
			case SiteplanPackage.SITEPLAN_STATE__ROUTES:
				getRoutes().clear();
				return;
			case SiteplanPackage.SITEPLAN_STATE__STATIONS:
				getStations().clear();
				return;
			case SiteplanPackage.SITEPLAN_STATE__TRACK_LOCK:
				getTrackLock().clear();
				return;
			case SiteplanPackage.SITEPLAN_STATE__ERRORS:
				getErrors().clear();
				return;
			case SiteplanPackage.SITEPLAN_STATE__TRACK_CLOSURES:
				getTrackClosures().clear();
				return;
			case SiteplanPackage.SITEPLAN_STATE__EXTERNAL_ELEMENT_CONTROLS:
				getExternalElementControls().clear();
				return;
			case SiteplanPackage.SITEPLAN_STATE__LOCKKEYS:
				getLockkeys().clear();
				return;
			case SiteplanPackage.SITEPLAN_STATE__CANTS:
				getCants().clear();
				return;
			case SiteplanPackage.SITEPLAN_STATE__UNKNOWN_OBJECTS:
				getUnknownObjects().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case SiteplanPackage.SITEPLAN_STATE__SIGNALS:
				return signals != null && !signals.isEmpty();
			case SiteplanPackage.SITEPLAN_STATE__TRACK_SWITCHES:
				return trackSwitches != null && !trackSwitches.isEmpty();
			case SiteplanPackage.SITEPLAN_STATE__TRACK_SWITCH_END_MARKERS:
				return trackSwitchEndMarkers != null && !trackSwitchEndMarkers.isEmpty();
			case SiteplanPackage.SITEPLAN_STATE__TRACKS:
				return tracks != null && !tracks.isEmpty();
			case SiteplanPackage.SITEPLAN_STATE__FMA_COMPONENTS:
				return fmaComponents != null && !fmaComponents.isEmpty();
			case SiteplanPackage.SITEPLAN_STATE__PZB:
				return pzb != null && !pzb.isEmpty();
			case SiteplanPackage.SITEPLAN_STATE__PZB_GU:
				return pzbGU != null && !pzbGU.isEmpty();
			case SiteplanPackage.SITEPLAN_STATE__ROUTES:
				return routes != null && !routes.isEmpty();
			case SiteplanPackage.SITEPLAN_STATE__STATIONS:
				return stations != null && !stations.isEmpty();
			case SiteplanPackage.SITEPLAN_STATE__TRACK_LOCK:
				return trackLock != null && !trackLock.isEmpty();
			case SiteplanPackage.SITEPLAN_STATE__ERRORS:
				return errors != null && !errors.isEmpty();
			case SiteplanPackage.SITEPLAN_STATE__TRACK_CLOSURES:
				return trackClosures != null && !trackClosures.isEmpty();
			case SiteplanPackage.SITEPLAN_STATE__EXTERNAL_ELEMENT_CONTROLS:
				return externalElementControls != null && !externalElementControls.isEmpty();
			case SiteplanPackage.SITEPLAN_STATE__LOCKKEYS:
				return lockkeys != null && !lockkeys.isEmpty();
			case SiteplanPackage.SITEPLAN_STATE__CANTS:
				return cants != null && !cants.isEmpty();
			case SiteplanPackage.SITEPLAN_STATE__UNKNOWN_OBJECTS:
				return unknownObjects != null && !unknownObjects.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} // SiteplanStateImpl
