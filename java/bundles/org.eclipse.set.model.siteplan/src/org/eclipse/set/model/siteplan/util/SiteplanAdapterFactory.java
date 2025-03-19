/**
 * Copyright (c) 2021 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.siteplan.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

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
 * <!-- begin-user-doc --> The <b>Adapter Factory</b> for the model. It provides
 * an adapter <code>createXXX</code> method for each class of the model. <!--
 * end-user-doc -->
 * 
 * @see org.eclipse.set.model.siteplan.SiteplanPackage
 * @generated
 */
public class SiteplanAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected static SiteplanPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	public SiteplanAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = SiteplanPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc --> This implementation returns <code>true</code> if
	 * the object is either the model's package or is an instance object of the
	 * model. <!-- end-user-doc -->
	 * 
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject) object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected SiteplanSwitch<Adapter> modelSwitch = new SiteplanSwitch<Adapter>() {
		@Override
		public Adapter caseSiteplan(Siteplan object) {
			return createSiteplanAdapter();
		}

		@Override
		public Adapter caseSiteplanState(SiteplanState object) {
			return createSiteplanStateAdapter();
		}

		@Override
		public Adapter caseSiteplanObject(SiteplanObject object) {
			return createSiteplanObjectAdapter();
		}

		@Override
		public Adapter casePositionedObject(PositionedObject object) {
			return createPositionedObjectAdapter();
		}

		@Override
		public Adapter caseCoordinate(Coordinate object) {
			return createCoordinateAdapter();
		}

		@Override
		public Adapter casePosition(Position object) {
			return createPositionAdapter();
		}

		@Override
		public Adapter caseRouteObject(RouteObject object) {
			return createRouteObjectAdapter();
		}

		@Override
		public Adapter caseRouteLocation(RouteLocation object) {
			return createRouteLocationAdapter();
		}

		@Override
		public Adapter caseSignalMount(SignalMount object) {
			return createSignalMountAdapter();
		}

		@Override
		public Adapter caseSignal(Signal object) {
			return createSignalAdapter();
		}

		@Override
		public Adapter caseSignalScreen(SignalScreen object) {
			return createSignalScreenAdapter();
		}

		@Override
		public Adapter caseLabel(Label object) {
			return createLabelAdapter();
		}

		@Override
		public Adapter caseTrackSwitch(TrackSwitch object) {
			return createTrackSwitchAdapter();
		}

		@Override
		public Adapter caseTrackSwitchComponent(TrackSwitchComponent object) {
			return createTrackSwitchComponentAdapter();
		}

		@Override
		public Adapter caseContinuousTrackSegment(
				ContinuousTrackSegment object) {
			return createContinuousTrackSegmentAdapter();
		}

		@Override
		public Adapter caseTrack(Track object) {
			return createTrackAdapter();
		}

		@Override
		public Adapter caseTrackSection(TrackSection object) {
			return createTrackSectionAdapter();
		}

		@Override
		public Adapter caseTrackSegment(TrackSegment object) {
			return createTrackSegmentAdapter();
		}

		@Override
		public Adapter caseFMAComponent(FMAComponent object) {
			return createFMAComponentAdapter();
		}

		@Override
		public Adapter caseRoute(Route object) {
			return createRouteAdapter();
		}

		@Override
		public Adapter caseRouteSection(RouteSection object) {
			return createRouteSectionAdapter();
		}

		@Override
		public Adapter caseKMMarker(KMMarker object) {
			return createKMMarkerAdapter();
		}

		@Override
		public Adapter caseTrackSwitchEndMarker(TrackSwitchEndMarker object) {
			return createTrackSwitchEndMarkerAdapter();
		}

		@Override
		public Adapter caseError(org.eclipse.set.model.siteplan.Error object) {
			return createErrorAdapter();
		}

		@Override
		public Adapter casePZB(PZB object) {
			return createPZBAdapter();
		}

		@Override
		public Adapter casePZBGU(PZBGU object) {
			return createPZBGUAdapter();
		}

		@Override
		public Adapter caseTrackDesignation(TrackDesignation object) {
			return createTrackDesignationAdapter();
		}

		@Override
		public Adapter caseTrackSwitchLeg(TrackSwitchLeg object) {
			return createTrackSwitchLegAdapter();
		}

		@Override
		public Adapter caseStation(Station object) {
			return createStationAdapter();
		}

		@Override
		public Adapter casePlatform(Platform object) {
			return createPlatformAdapter();
		}

		@Override
		public Adapter caseTrackLock(TrackLock object) {
			return createTrackLockAdapter();
		}

		@Override
		public Adapter caseTrackLockComponent(TrackLockComponent object) {
			return createTrackLockComponentAdapter();
		}

		@Override
		public Adapter caseObjectManagement(ObjectManagement object) {
			return createObjectManagementAdapter();
		}

		@Override
		public Adapter caseTrackClose(TrackClose object) {
			return createTrackCloseAdapter();
		}

		@Override
		public Adapter caseExternalElementControl(
				ExternalElementControl object) {
			return createExternalElementControlAdapter();
		}

		@Override
		public Adapter caseLockKey(LockKey object) {
			return createLockKeyAdapter();
		}

		@Override
		public Adapter caseLayoutinfo(Layoutinfo object) {
			return createLayoutinfoAdapter();
		}

		@Override
		public Adapter caseSheetCut(SheetCut object) {
			return createSheetCutAdapter();
		}

		@Override
		public Adapter caseCant(Cant object) {
			return createCantAdapter();
		}

		@Override
		public Adapter caseCantPoint(CantPoint object) {
			return createCantPointAdapter();
		}

		@Override
		public Adapter caseUnknownPositionedObject(
				UnknownPositionedObject object) {
			return createUnknownPositionedObjectAdapter();
		}

		@Override
		public Adapter defaultCase(EObject object) {
			return createEObjectAdapter();
		}
	};

	/**
	 * Creates an adapter for the <code>target</code>. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param target
	 *            the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject) target);
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.eclipse.set.model.siteplan.Siteplan <em>Siteplan</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we
	 * can easily ignore cases; it's useful to ignore a case when inheritance
	 * will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.set.model.siteplan.Siteplan
	 * @generated
	 */
	public Adapter createSiteplanAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.eclipse.set.model.siteplan.SiteplanState <em>State</em>}'.
	 * <!-- begin-user-doc --> This default implementation returns null so that
	 * we can easily ignore cases; it's useful to ignore a case when inheritance
	 * will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.set.model.siteplan.SiteplanState
	 * @generated
	 */
	public Adapter createSiteplanStateAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.eclipse.set.model.siteplan.SiteplanObject <em>Object</em>}'.
	 * <!-- begin-user-doc --> This default implementation returns null so that
	 * we can easily ignore cases; it's useful to ignore a case when inheritance
	 * will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.set.model.siteplan.SiteplanObject
	 * @generated
	 */
	public Adapter createSiteplanObjectAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.eclipse.set.model.siteplan.PositionedObject <em>Positioned
	 * Object</em>}'. <!-- begin-user-doc --> This default implementation
	 * returns null so that we can easily ignore cases; it's useful to ignore a
	 * case when inheritance will catch all the cases anyway. <!-- end-user-doc
	 * -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.set.model.siteplan.PositionedObject
	 * @generated
	 */
	public Adapter createPositionedObjectAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.eclipse.set.model.siteplan.Coordinate <em>Coordinate</em>}'.
	 * <!-- begin-user-doc --> This default implementation returns null so that
	 * we can easily ignore cases; it's useful to ignore a case when inheritance
	 * will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.set.model.siteplan.Coordinate
	 * @generated
	 */
	public Adapter createCoordinateAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.eclipse.set.model.siteplan.Position <em>Position</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we
	 * can easily ignore cases; it's useful to ignore a case when inheritance
	 * will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.set.model.siteplan.Position
	 * @generated
	 */
	public Adapter createPositionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.eclipse.set.model.siteplan.RouteObject <em>Route
	 * Object</em>}'. <!-- begin-user-doc --> This default implementation
	 * returns null so that we can easily ignore cases; it's useful to ignore a
	 * case when inheritance will catch all the cases anyway. <!-- end-user-doc
	 * -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.set.model.siteplan.RouteObject
	 * @generated
	 */
	public Adapter createRouteObjectAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.eclipse.set.model.siteplan.RouteLocation <em>Route
	 * Location</em>}'. <!-- begin-user-doc --> This default implementation
	 * returns null so that we can easily ignore cases; it's useful to ignore a
	 * case when inheritance will catch all the cases anyway. <!-- end-user-doc
	 * -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.set.model.siteplan.RouteLocation
	 * @generated
	 */
	public Adapter createRouteLocationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.eclipse.set.model.siteplan.SignalMount <em>Signal
	 * Mount</em>}'. <!-- begin-user-doc --> This default implementation returns
	 * null so that we can easily ignore cases; it's useful to ignore a case
	 * when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.set.model.siteplan.SignalMount
	 * @generated
	 */
	public Adapter createSignalMountAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.eclipse.set.model.siteplan.Signal <em>Signal</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we
	 * can easily ignore cases; it's useful to ignore a case when inheritance
	 * will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.set.model.siteplan.Signal
	 * @generated
	 */
	public Adapter createSignalAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.eclipse.set.model.siteplan.SignalScreen <em>Signal
	 * Screen</em>}'. <!-- begin-user-doc --> This default implementation
	 * returns null so that we can easily ignore cases; it's useful to ignore a
	 * case when inheritance will catch all the cases anyway. <!-- end-user-doc
	 * -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.set.model.siteplan.SignalScreen
	 * @generated
	 */
	public Adapter createSignalScreenAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.eclipse.set.model.siteplan.Label <em>Label</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we
	 * can easily ignore cases; it's useful to ignore a case when inheritance
	 * will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.set.model.siteplan.Label
	 * @generated
	 */
	public Adapter createLabelAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.eclipse.set.model.siteplan.TrackSwitch <em>Track
	 * Switch</em>}'. <!-- begin-user-doc --> This default implementation
	 * returns null so that we can easily ignore cases; it's useful to ignore a
	 * case when inheritance will catch all the cases anyway. <!-- end-user-doc
	 * -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.set.model.siteplan.TrackSwitch
	 * @generated
	 */
	public Adapter createTrackSwitchAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.eclipse.set.model.siteplan.TrackSwitchComponent <em>Track
	 * Switch Component</em>}'. <!-- begin-user-doc --> This default
	 * implementation returns null so that we can easily ignore cases; it's
	 * useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.set.model.siteplan.TrackSwitchComponent
	 * @generated
	 */
	public Adapter createTrackSwitchComponentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.eclipse.set.model.siteplan.ContinuousTrackSegment
	 * <em>Continuous Track Segment</em>}'. <!-- begin-user-doc --> This default
	 * implementation returns null so that we can easily ignore cases; it's
	 * useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.set.model.siteplan.ContinuousTrackSegment
	 * @generated
	 */
	public Adapter createContinuousTrackSegmentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.eclipse.set.model.siteplan.Track <em>Track</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we
	 * can easily ignore cases; it's useful to ignore a case when inheritance
	 * will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.set.model.siteplan.Track
	 * @generated
	 */
	public Adapter createTrackAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.eclipse.set.model.siteplan.TrackSection <em>Track
	 * Section</em>}'. <!-- begin-user-doc --> This default implementation
	 * returns null so that we can easily ignore cases; it's useful to ignore a
	 * case when inheritance will catch all the cases anyway. <!-- end-user-doc
	 * -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.set.model.siteplan.TrackSection
	 * @generated
	 */
	public Adapter createTrackSectionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.eclipse.set.model.siteplan.TrackSegment <em>Track
	 * Segment</em>}'. <!-- begin-user-doc --> This default implementation
	 * returns null so that we can easily ignore cases; it's useful to ignore a
	 * case when inheritance will catch all the cases anyway. <!-- end-user-doc
	 * -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.set.model.siteplan.TrackSegment
	 * @generated
	 */
	public Adapter createTrackSegmentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.eclipse.set.model.siteplan.FMAComponent <em>FMA
	 * Component</em>}'. <!-- begin-user-doc --> This default implementation
	 * returns null so that we can easily ignore cases; it's useful to ignore a
	 * case when inheritance will catch all the cases anyway. <!-- end-user-doc
	 * -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.set.model.siteplan.FMAComponent
	 * @generated
	 */
	public Adapter createFMAComponentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.eclipse.set.model.siteplan.Route <em>Route</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we
	 * can easily ignore cases; it's useful to ignore a case when inheritance
	 * will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.set.model.siteplan.Route
	 * @generated
	 */
	public Adapter createRouteAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.eclipse.set.model.siteplan.RouteSection <em>Route
	 * Section</em>}'. <!-- begin-user-doc --> This default implementation
	 * returns null so that we can easily ignore cases; it's useful to ignore a
	 * case when inheritance will catch all the cases anyway. <!-- end-user-doc
	 * -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.set.model.siteplan.RouteSection
	 * @generated
	 */
	public Adapter createRouteSectionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.eclipse.set.model.siteplan.KMMarker <em>KM Marker</em>}'.
	 * <!-- begin-user-doc --> This default implementation returns null so that
	 * we can easily ignore cases; it's useful to ignore a case when inheritance
	 * will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.set.model.siteplan.KMMarker
	 * @generated
	 */
	public Adapter createKMMarkerAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.eclipse.set.model.siteplan.TrackSwitchEndMarker <em>Track
	 * Switch End Marker</em>}'. <!-- begin-user-doc --> This default
	 * implementation returns null so that we can easily ignore cases; it's
	 * useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.set.model.siteplan.TrackSwitchEndMarker
	 * @generated
	 */
	public Adapter createTrackSwitchEndMarkerAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.eclipse.set.model.siteplan.Error <em>Error</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we
	 * can easily ignore cases; it's useful to ignore a case when inheritance
	 * will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.set.model.siteplan.Error
	 * @generated
	 */
	public Adapter createErrorAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.eclipse.set.model.siteplan.PZB <em>PZB</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we
	 * can easily ignore cases; it's useful to ignore a case when inheritance
	 * will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.set.model.siteplan.PZB
	 * @generated
	 */
	public Adapter createPZBAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.eclipse.set.model.siteplan.PZBGU <em>PZBGU</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we
	 * can easily ignore cases; it's useful to ignore a case when inheritance
	 * will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.set.model.siteplan.PZBGU
	 * @generated
	 */
	public Adapter createPZBGUAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.eclipse.set.model.siteplan.TrackDesignation <em>Track
	 * Designation</em>}'. <!-- begin-user-doc --> This default implementation
	 * returns null so that we can easily ignore cases; it's useful to ignore a
	 * case when inheritance will catch all the cases anyway. <!-- end-user-doc
	 * -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.set.model.siteplan.TrackDesignation
	 * @generated
	 */
	public Adapter createTrackDesignationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.eclipse.set.model.siteplan.TrackSwitchLeg <em>Track Switch
	 * Leg</em>}'. <!-- begin-user-doc --> This default implementation returns
	 * null so that we can easily ignore cases; it's useful to ignore a case
	 * when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.set.model.siteplan.TrackSwitchLeg
	 * @generated
	 */
	public Adapter createTrackSwitchLegAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.eclipse.set.model.siteplan.Station <em>Station</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we
	 * can easily ignore cases; it's useful to ignore a case when inheritance
	 * will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.set.model.siteplan.Station
	 * @generated
	 */
	public Adapter createStationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.eclipse.set.model.siteplan.Platform <em>Platform</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we
	 * can easily ignore cases; it's useful to ignore a case when inheritance
	 * will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.set.model.siteplan.Platform
	 * @generated
	 */
	public Adapter createPlatformAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.eclipse.set.model.siteplan.TrackLock <em>Track Lock</em>}'.
	 * <!-- begin-user-doc --> This default implementation returns null so that
	 * we can easily ignore cases; it's useful to ignore a case when inheritance
	 * will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.set.model.siteplan.TrackLock
	 * @generated
	 */
	public Adapter createTrackLockAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.eclipse.set.model.siteplan.TrackLockComponent <em>Track Lock
	 * Component</em>}'. <!-- begin-user-doc --> This default implementation
	 * returns null so that we can easily ignore cases; it's useful to ignore a
	 * case when inheritance will catch all the cases anyway. <!-- end-user-doc
	 * -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.set.model.siteplan.TrackLockComponent
	 * @generated
	 */
	public Adapter createTrackLockComponentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.eclipse.set.model.siteplan.ObjectManagement <em>Object
	 * Management</em>}'. <!-- begin-user-doc --> This default implementation
	 * returns null so that we can easily ignore cases; it's useful to ignore a
	 * case when inheritance will catch all the cases anyway. <!-- end-user-doc
	 * -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.set.model.siteplan.ObjectManagement
	 * @generated
	 */
	public Adapter createObjectManagementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.eclipse.set.model.siteplan.TrackClose <em>Track Close</em>}'.
	 * <!-- begin-user-doc --> This default implementation returns null so that
	 * we can easily ignore cases; it's useful to ignore a case when inheritance
	 * will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.set.model.siteplan.TrackClose
	 * @generated
	 */
	public Adapter createTrackCloseAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.eclipse.set.model.siteplan.ExternalElementControl
	 * <em>External Element Control</em>}'. <!-- begin-user-doc --> This default
	 * implementation returns null so that we can easily ignore cases; it's
	 * useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.set.model.siteplan.ExternalElementControl
	 * @generated
	 */
	public Adapter createExternalElementControlAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.eclipse.set.model.siteplan.LockKey <em>Lock Key</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we
	 * can easily ignore cases; it's useful to ignore a case when inheritance
	 * will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.set.model.siteplan.LockKey
	 * @generated
	 */
	public Adapter createLockKeyAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.eclipse.set.model.siteplan.Layoutinfo <em>Layoutinfo</em>}'.
	 * <!-- begin-user-doc --> This default implementation returns null so that
	 * we can easily ignore cases; it's useful to ignore a case when inheritance
	 * will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.set.model.siteplan.Layoutinfo
	 * @generated
	 */
	public Adapter createLayoutinfoAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.eclipse.set.model.siteplan.SheetCut <em>Sheet Cut</em>}'.
	 * <!-- begin-user-doc --> This default implementation returns null so that
	 * we can easily ignore cases; it's useful to ignore a case when inheritance
	 * will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.set.model.siteplan.SheetCut
	 * @generated
	 */
	public Adapter createSheetCutAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.eclipse.set.model.siteplan.Cant <em>Cant</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we
	 * can easily ignore cases; it's useful to ignore a case when inheritance
	 * will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.set.model.siteplan.Cant
	 * @generated
	 */
	public Adapter createCantAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.eclipse.set.model.siteplan.CantPoint <em>Cant Point</em>}'.
	 * <!-- begin-user-doc --> This default implementation returns null so that
	 * we can easily ignore cases; it's useful to ignore a case when inheritance
	 * will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.set.model.siteplan.CantPoint
	 * @generated
	 */
	public Adapter createCantPointAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.eclipse.set.model.siteplan.UnknownPositionedObject
	 * <em>Unknown Positioned Object</em>}'. <!-- begin-user-doc --> This
	 * default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases
	 * anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.set.model.siteplan.UnknownPositionedObject
	 * @generated
	 */
	public Adapter createUnknownPositionedObjectAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case. <!-- begin-user-doc --> This
	 * default implementation returns null. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} // SiteplanAdapterFactory
