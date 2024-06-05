/**
 * Copyright (c) 2021 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.siteplan.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

import org.eclipse.set.model.siteplan.Cant;
import org.eclipse.set.model.siteplan.CantPoint;
import org.eclipse.set.model.siteplan.ContinuousTrackSegment;
import org.eclipse.set.model.siteplan.Coordinate;
import org.eclipse.set.model.siteplan.ExternalElementControl;
import org.eclipse.set.model.siteplan.FMAComponent;
import org.eclipse.set.model.siteplan.KMMarker;
import org.eclipse.set.model.siteplan.Label;
import org.eclipse.set.model.siteplan.Layoutinfo;
import org.eclipse.set.model.siteplan.LockKey;
import org.eclipse.set.model.siteplan.ObjectManagement;
import org.eclipse.set.model.siteplan.PZB;
import org.eclipse.set.model.siteplan.PZBGU;
import org.eclipse.set.model.siteplan.Platform;
import org.eclipse.set.model.siteplan.Position;
import org.eclipse.set.model.siteplan.PositionedObject;
import org.eclipse.set.model.siteplan.Route;
import org.eclipse.set.model.siteplan.RouteLocation;
import org.eclipse.set.model.siteplan.RouteObject;
import org.eclipse.set.model.siteplan.RouteSection;
import org.eclipse.set.model.siteplan.SheetCut;
import org.eclipse.set.model.siteplan.Signal;
import org.eclipse.set.model.siteplan.SignalMount;
import org.eclipse.set.model.siteplan.SignalScreen;
import org.eclipse.set.model.siteplan.Siteplan;
import org.eclipse.set.model.siteplan.SiteplanObject;
import org.eclipse.set.model.siteplan.SiteplanPackage;
import org.eclipse.set.model.siteplan.SiteplanState;
import org.eclipse.set.model.siteplan.Station;
import org.eclipse.set.model.siteplan.Track;
import org.eclipse.set.model.siteplan.TrackClose;
import org.eclipse.set.model.siteplan.TrackDesignation;
import org.eclipse.set.model.siteplan.TrackLock;
import org.eclipse.set.model.siteplan.TrackLockComponent;
import org.eclipse.set.model.siteplan.TrackSection;
import org.eclipse.set.model.siteplan.TrackSegment;
import org.eclipse.set.model.siteplan.TrackSwitch;
import org.eclipse.set.model.siteplan.TrackSwitchComponent;
import org.eclipse.set.model.siteplan.TrackSwitchEndMarker;
import org.eclipse.set.model.siteplan.TrackSwitchLeg;
import org.eclipse.set.model.siteplan.UnknownPositionedObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see org.eclipse.set.model.siteplan.SiteplanPackage
 * @generated
 */
public class SiteplanSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static SiteplanPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SiteplanSwitch() {
		if (modelPackage == null) {
			modelPackage = SiteplanPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case SiteplanPackage.SITEPLAN: {
				Siteplan siteplan = (Siteplan)theEObject;
				T result = caseSiteplan(siteplan);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SiteplanPackage.SITEPLAN_STATE: {
				SiteplanState siteplanState = (SiteplanState)theEObject;
				T result = caseSiteplanState(siteplanState);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SiteplanPackage.SITEPLAN_OBJECT: {
				SiteplanObject siteplanObject = (SiteplanObject)theEObject;
				T result = caseSiteplanObject(siteplanObject);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SiteplanPackage.POSITIONED_OBJECT: {
				PositionedObject positionedObject = (PositionedObject)theEObject;
				T result = casePositionedObject(positionedObject);
				if (result == null) result = caseSiteplanObject(positionedObject);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SiteplanPackage.COORDINATE: {
				Coordinate coordinate = (Coordinate)theEObject;
				T result = caseCoordinate(coordinate);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SiteplanPackage.POSITION: {
				Position position = (Position)theEObject;
				T result = casePosition(position);
				if (result == null) result = caseCoordinate(position);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SiteplanPackage.ROUTE_OBJECT: {
				RouteObject routeObject = (RouteObject)theEObject;
				T result = caseRouteObject(routeObject);
				if (result == null) result = caseSiteplanObject(routeObject);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SiteplanPackage.ROUTE_LOCATION: {
				RouteLocation routeLocation = (RouteLocation)theEObject;
				T result = caseRouteLocation(routeLocation);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SiteplanPackage.SIGNAL_MOUNT: {
				SignalMount signalMount = (SignalMount)theEObject;
				T result = caseSignalMount(signalMount);
				if (result == null) result = casePositionedObject(signalMount);
				if (result == null) result = caseRouteObject(signalMount);
				if (result == null) result = caseSiteplanObject(signalMount);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SiteplanPackage.SIGNAL: {
				Signal signal = (Signal)theEObject;
				T result = caseSignal(signal);
				if (result == null) result = caseRouteObject(signal);
				if (result == null) result = caseSiteplanObject(signal);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SiteplanPackage.SIGNAL_SCREEN: {
				SignalScreen signalScreen = (SignalScreen)theEObject;
				T result = caseSignalScreen(signalScreen);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SiteplanPackage.LABEL: {
				Label label = (Label)theEObject;
				T result = caseLabel(label);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SiteplanPackage.TRACK_SWITCH: {
				TrackSwitch trackSwitch = (TrackSwitch)theEObject;
				T result = caseTrackSwitch(trackSwitch);
				if (result == null) result = caseSiteplanObject(trackSwitch);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SiteplanPackage.TRACK_SWITCH_COMPONENT: {
				TrackSwitchComponent trackSwitchComponent = (TrackSwitchComponent)theEObject;
				T result = caseTrackSwitchComponent(trackSwitchComponent);
				if (result == null) result = caseRouteObject(trackSwitchComponent);
				if (result == null) result = caseSiteplanObject(trackSwitchComponent);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SiteplanPackage.CONTINUOUS_TRACK_SEGMENT: {
				ContinuousTrackSegment continuousTrackSegment = (ContinuousTrackSegment)theEObject;
				T result = caseContinuousTrackSegment(continuousTrackSegment);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SiteplanPackage.TRACK: {
				Track track = (Track)theEObject;
				T result = caseTrack(track);
				if (result == null) result = caseSiteplanObject(track);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SiteplanPackage.TRACK_SECTION: {
				TrackSection trackSection = (TrackSection)theEObject;
				T result = caseTrackSection(trackSection);
				if (result == null) result = caseSiteplanObject(trackSection);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SiteplanPackage.TRACK_SEGMENT: {
				TrackSegment trackSegment = (TrackSegment)theEObject;
				T result = caseTrackSegment(trackSegment);
				if (result == null) result = caseSiteplanObject(trackSegment);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SiteplanPackage.FMA_COMPONENT: {
				FMAComponent fmaComponent = (FMAComponent)theEObject;
				T result = caseFMAComponent(fmaComponent);
				if (result == null) result = caseRouteObject(fmaComponent);
				if (result == null) result = casePositionedObject(fmaComponent);
				if (result == null) result = caseSiteplanObject(fmaComponent);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SiteplanPackage.ROUTE: {
				Route route = (Route)theEObject;
				T result = caseRoute(route);
				if (result == null) result = caseSiteplanObject(route);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SiteplanPackage.ROUTE_SECTION: {
				RouteSection routeSection = (RouteSection)theEObject;
				T result = caseRouteSection(routeSection);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SiteplanPackage.KM_MARKER: {
				KMMarker kmMarker = (KMMarker)theEObject;
				T result = caseKMMarker(kmMarker);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SiteplanPackage.TRACK_SWITCH_END_MARKER: {
				TrackSwitchEndMarker trackSwitchEndMarker = (TrackSwitchEndMarker)theEObject;
				T result = caseTrackSwitchEndMarker(trackSwitchEndMarker);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SiteplanPackage.ERROR: {
				org.eclipse.set.model.siteplan.Error error = (org.eclipse.set.model.siteplan.Error)theEObject;
				T result = caseError(error);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SiteplanPackage.PZB: {
				PZB pzb = (PZB)theEObject;
				T result = casePZB(pzb);
				if (result == null) result = caseRouteObject(pzb);
				if (result == null) result = casePositionedObject(pzb);
				if (result == null) result = caseSiteplanObject(pzb);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SiteplanPackage.PZBGU: {
				PZBGU pzbgu = (PZBGU)theEObject;
				T result = casePZBGU(pzbgu);
				if (result == null) result = caseSiteplanObject(pzbgu);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SiteplanPackage.TRACK_DESIGNATION: {
				TrackDesignation trackDesignation = (TrackDesignation)theEObject;
				T result = caseTrackDesignation(trackDesignation);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SiteplanPackage.TRACK_SWITCH_LEG: {
				TrackSwitchLeg trackSwitchLeg = (TrackSwitchLeg)theEObject;
				T result = caseTrackSwitchLeg(trackSwitchLeg);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SiteplanPackage.STATION: {
				Station station = (Station)theEObject;
				T result = caseStation(station);
				if (result == null) result = caseSiteplanObject(station);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SiteplanPackage.PLATFORM: {
				Platform platform = (Platform)theEObject;
				T result = casePlatform(platform);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SiteplanPackage.TRACK_LOCK: {
				TrackLock trackLock = (TrackLock)theEObject;
				T result = caseTrackLock(trackLock);
				if (result == null) result = caseSiteplanObject(trackLock);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SiteplanPackage.TRACK_LOCK_COMPONENT: {
				TrackLockComponent trackLockComponent = (TrackLockComponent)theEObject;
				T result = caseTrackLockComponent(trackLockComponent);
				if (result == null) result = casePositionedObject(trackLockComponent);
				if (result == null) result = caseSiteplanObject(trackLockComponent);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SiteplanPackage.OBJECT_MANAGEMENT: {
				ObjectManagement objectManagement = (ObjectManagement)theEObject;
				T result = caseObjectManagement(objectManagement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SiteplanPackage.TRACK_CLOSE: {
				TrackClose trackClose = (TrackClose)theEObject;
				T result = caseTrackClose(trackClose);
				if (result == null) result = casePositionedObject(trackClose);
				if (result == null) result = caseSiteplanObject(trackClose);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SiteplanPackage.EXTERNAL_ELEMENT_CONTROL: {
				ExternalElementControl externalElementControl = (ExternalElementControl)theEObject;
				T result = caseExternalElementControl(externalElementControl);
				if (result == null) result = caseRouteObject(externalElementControl);
				if (result == null) result = casePositionedObject(externalElementControl);
				if (result == null) result = caseSiteplanObject(externalElementControl);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SiteplanPackage.LOCK_KEY: {
				LockKey lockKey = (LockKey)theEObject;
				T result = caseLockKey(lockKey);
				if (result == null) result = caseRouteObject(lockKey);
				if (result == null) result = casePositionedObject(lockKey);
				if (result == null) result = caseSiteplanObject(lockKey);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SiteplanPackage.LAYOUTINFO: {
				Layoutinfo layoutinfo = (Layoutinfo)theEObject;
				T result = caseLayoutinfo(layoutinfo);
				if (result == null) result = caseSiteplanObject(layoutinfo);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SiteplanPackage.SHEET_CUT: {
				SheetCut sheetCut = (SheetCut)theEObject;
				T result = caseSheetCut(sheetCut);
				if (result == null) result = caseSiteplanObject(sheetCut);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SiteplanPackage.CANT: {
				Cant cant = (Cant)theEObject;
				T result = caseCant(cant);
				if (result == null) result = caseSiteplanObject(cant);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SiteplanPackage.CANT_POINT: {
				CantPoint cantPoint = (CantPoint)theEObject;
				T result = caseCantPoint(cantPoint);
				if (result == null) result = casePositionedObject(cantPoint);
				if (result == null) result = caseSiteplanObject(cantPoint);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SiteplanPackage.UNKNOWN_POSITIONED_OBJECT: {
				UnknownPositionedObject unknownPositionedObject = (UnknownPositionedObject)theEObject;
				T result = caseUnknownPositionedObject(unknownPositionedObject);
				if (result == null) result = casePositionedObject(unknownPositionedObject);
				if (result == null) result = caseSiteplanObject(unknownPositionedObject);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Siteplan</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Siteplan</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSiteplan(Siteplan object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>State</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>State</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSiteplanState(SiteplanState object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Object</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Object</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSiteplanObject(SiteplanObject object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Positioned Object</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Positioned Object</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePositionedObject(PositionedObject object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Coordinate</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Coordinate</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCoordinate(Coordinate object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Position</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Position</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePosition(Position object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Route Object</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Route Object</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRouteObject(RouteObject object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Route Location</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Route Location</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRouteLocation(RouteLocation object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Signal Mount</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Signal Mount</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSignalMount(SignalMount object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Signal</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Signal</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSignal(Signal object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Signal Screen</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Signal Screen</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSignalScreen(SignalScreen object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Label</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Label</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLabel(Label object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Track Switch</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Track Switch</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTrackSwitch(TrackSwitch object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Track Switch Component</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Track Switch Component</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTrackSwitchComponent(TrackSwitchComponent object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Continuous Track Segment</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Continuous Track Segment</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseContinuousTrackSegment(ContinuousTrackSegment object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Track</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Track</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTrack(Track object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Track Section</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Track Section</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTrackSection(TrackSection object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Track Segment</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Track Segment</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTrackSegment(TrackSegment object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>FMA Component</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>FMA Component</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFMAComponent(FMAComponent object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Route</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Route</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRoute(Route object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Route Section</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Route Section</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRouteSection(RouteSection object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>KM Marker</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>KM Marker</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseKMMarker(KMMarker object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Track Switch End Marker</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Track Switch End Marker</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTrackSwitchEndMarker(TrackSwitchEndMarker object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Error</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Error</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseError(org.eclipse.set.model.siteplan.Error object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>PZB</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>PZB</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePZB(PZB object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>PZBGU</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>PZBGU</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePZBGU(PZBGU object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Track Designation</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Track Designation</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTrackDesignation(TrackDesignation object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Track Switch Leg</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Track Switch Leg</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTrackSwitchLeg(TrackSwitchLeg object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Station</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Station</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStation(Station object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Platform</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Platform</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePlatform(Platform object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Track Lock</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Track Lock</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTrackLock(TrackLock object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Track Lock Component</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Track Lock Component</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTrackLockComponent(TrackLockComponent object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Object Management</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Object Management</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseObjectManagement(ObjectManagement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Track Close</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Track Close</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTrackClose(TrackClose object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>External Element Control</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>External Element Control</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseExternalElementControl(ExternalElementControl object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Lock Key</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Lock Key</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLockKey(LockKey object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Layoutinfo</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Layoutinfo</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLayoutinfo(Layoutinfo object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Sheet Cut</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Sheet Cut</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSheetCut(SheetCut object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Cant</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Cant</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCant(Cant object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Cant Point</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Cant Point</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCantPoint(CantPoint object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Unknown Positioned Object</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Unknown Positioned Object</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseUnknownPositionedObject(UnknownPositionedObject object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} //SiteplanSwitch
