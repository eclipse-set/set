/**
 * Copyright (c) 2021 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.siteplan.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.set.model.siteplan.Cant;
import org.eclipse.set.model.siteplan.CantPoint;
import org.eclipse.set.model.siteplan.ContinuousTrackSegment;
import org.eclipse.set.model.siteplan.ControlStationType;
import org.eclipse.set.model.siteplan.Coordinate;
import org.eclipse.set.model.siteplan.Direction;
import org.eclipse.set.model.siteplan.ExternalElementControl;
import org.eclipse.set.model.siteplan.ExternalElementControlArt;
import org.eclipse.set.model.siteplan.FMAComponent;
import org.eclipse.set.model.siteplan.FMAComponentType;
import org.eclipse.set.model.siteplan.KMMarker;
import org.eclipse.set.model.siteplan.Label;
import org.eclipse.set.model.siteplan.Layoutinfo;
import org.eclipse.set.model.siteplan.LeftRight;
import org.eclipse.set.model.siteplan.LockKey;
import org.eclipse.set.model.siteplan.LockKeyType;
import org.eclipse.set.model.siteplan.MountDirection;
import org.eclipse.set.model.siteplan.ObjectManagement;
import org.eclipse.set.model.siteplan.PZBEffectivity;
import org.eclipse.set.model.siteplan.PZBElement;
import org.eclipse.set.model.siteplan.PZBType;
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
import org.eclipse.set.model.siteplan.SignalMountType;
import org.eclipse.set.model.siteplan.SignalRole;
import org.eclipse.set.model.siteplan.SignalScreen;
import org.eclipse.set.model.siteplan.SignalSystem;
import org.eclipse.set.model.siteplan.Siteplan;
import org.eclipse.set.model.siteplan.SiteplanFactory;
import org.eclipse.set.model.siteplan.SiteplanObject;
import org.eclipse.set.model.siteplan.SiteplanPackage;
import org.eclipse.set.model.siteplan.SiteplanState;
import org.eclipse.set.model.siteplan.Station;
import org.eclipse.set.model.siteplan.SwitchType;
import org.eclipse.set.model.siteplan.Track;
import org.eclipse.set.model.siteplan.TrackClose;
import org.eclipse.set.model.siteplan.TrackCloseType;
import org.eclipse.set.model.siteplan.TrackDesignation;
import org.eclipse.set.model.siteplan.TrackLock;
import org.eclipse.set.model.siteplan.TrackLockComponent;
import org.eclipse.set.model.siteplan.TrackLockLocation;
import org.eclipse.set.model.siteplan.TrackSection;
import org.eclipse.set.model.siteplan.TrackSegment;
import org.eclipse.set.model.siteplan.TrackShape;
import org.eclipse.set.model.siteplan.TrackSwitch;
import org.eclipse.set.model.siteplan.TrackSwitchComponent;
import org.eclipse.set.model.siteplan.TrackSwitchEndMarker;
import org.eclipse.set.model.siteplan.TrackSwitchLeg;
import org.eclipse.set.model.siteplan.TrackType;
import org.eclipse.set.model.siteplan.TurnoutOperatingMode;
import org.eclipse.set.model.siteplan.UnknownPositionedObject;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Package</b>. <!--
 * end-user-doc -->
 * 
 * @generated
 */
public class SiteplanPackageImpl extends EPackageImpl
		implements SiteplanPackage {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass siteplanEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass siteplanStateEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass siteplanObjectEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass positionedObjectEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass coordinateEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass positionEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass routeObjectEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass routeLocationEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass signalMountEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass signalEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass signalScreenEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass labelEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass trackSwitchEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass trackSwitchComponentEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass continuousTrackSegmentEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass trackEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass trackSectionEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass trackSegmentEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass fmaComponentEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass routeEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass routeSectionEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass kmMarkerEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass trackSwitchEndMarkerEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass errorEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass pzbEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass pzbguEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass trackDesignationEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass trackSwitchLegEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass stationEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass platformEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass trackLockEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass trackLockComponentEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass objectManagementEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass trackCloseEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass externalElementControlEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass lockKeyEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass layoutinfoEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass sheetCutEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass cantEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass cantPointEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass unknownPositionedObjectEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EEnum signalMountTypeEEnum = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EEnum signalRoleEEnum = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EEnum signalSystemEEnum = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EEnum mountDirectionEEnum = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EEnum turnoutOperatingModeEEnum = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EEnum trackShapeEEnum = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EEnum trackTypeEEnum = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EEnum fmaComponentTypeEEnum = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EEnum pzbTypeEEnum = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EEnum pzbElementEEnum = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EEnum pzbEffectivityEEnum = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EEnum trackLockLocationEEnum = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EEnum leftRightEEnum = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EEnum directionEEnum = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EEnum trackCloseTypeEEnum = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EEnum externalElementControlArtEEnum = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EEnum controlStationTypeEEnum = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EEnum lockKeyTypeEEnum = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EEnum switchTypeEEnum = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the
	 * package package URI value.
	 * <p>
	 * Note: the correct way to create the package is via the static factory
	 * method {@link #init init()}, which also performs initialization of the
	 * package, or returns the registered package, if one already exists. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private SiteplanPackageImpl() {
		super(eNS_URI, SiteplanFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model,
	 * and for any others upon which it depends.
	 *
	 * <p>
	 * This method is used to initialize {@link SiteplanPackage#eINSTANCE} when
	 * that field is accessed. Clients should not invoke it directly. Instead,
	 * they should simply access that field to obtain the package. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static SiteplanPackage init() {
		if (isInited)
			return (SiteplanPackage) EPackage.Registry.INSTANCE
					.getEPackage(SiteplanPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredSiteplanPackage = EPackage.Registry.INSTANCE
				.get(eNS_URI);
		SiteplanPackageImpl theSiteplanPackage = registeredSiteplanPackage instanceof SiteplanPackageImpl
				? (SiteplanPackageImpl) registeredSiteplanPackage
				: new SiteplanPackageImpl();

		isInited = true;

		// Create package meta-data objects
		theSiteplanPackage.createPackageContents();

		// Initialize created meta-data
		theSiteplanPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theSiteplanPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(SiteplanPackage.eNS_URI,
				theSiteplanPackage);
		return theSiteplanPackage;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getSiteplan() {
		return siteplanEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getSiteplan_InitialState() {
		return (EReference) siteplanEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getSiteplan_ChangedInitialState() {
		return (EReference) siteplanEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getSiteplan_CommonState() {
		return (EReference) siteplanEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getSiteplan_ChangedFinalState() {
		return (EReference) siteplanEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getSiteplan_FinalState() {
		return (EReference) siteplanEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getSiteplan_CenterPosition() {
		return (EReference) siteplanEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getSiteplan_ObjectManagement() {
		return (EReference) siteplanEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getSiteplan_LayoutInfo() {
		return (EReference) siteplanEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getSiteplanState() {
		return siteplanStateEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getSiteplanState_Signals() {
		return (EReference) siteplanStateEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getSiteplanState_TrackSwitches() {
		return (EReference) siteplanStateEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getSiteplanState_TrackSwitchEndMarkers() {
		return (EReference) siteplanStateEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getSiteplanState_Tracks() {
		return (EReference) siteplanStateEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getSiteplanState_FmaComponents() {
		return (EReference) siteplanStateEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getSiteplanState_Pzb() {
		return (EReference) siteplanStateEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getSiteplanState_PzbGU() {
		return (EReference) siteplanStateEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getSiteplanState_Routes() {
		return (EReference) siteplanStateEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getSiteplanState_Stations() {
		return (EReference) siteplanStateEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getSiteplanState_TrackLock() {
		return (EReference) siteplanStateEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getSiteplanState_Errors() {
		return (EReference) siteplanStateEClass.getEStructuralFeatures()
				.get(10);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getSiteplanState_TrackClosures() {
		return (EReference) siteplanStateEClass.getEStructuralFeatures()
				.get(11);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getSiteplanState_ExternalElementControls() {
		return (EReference) siteplanStateEClass.getEStructuralFeatures()
				.get(12);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getSiteplanState_Lockkeys() {
		return (EReference) siteplanStateEClass.getEStructuralFeatures()
				.get(13);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getSiteplanState_Cants() {
		return (EReference) siteplanStateEClass.getEStructuralFeatures()
				.get(14);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getSiteplanState_UnknownObjects() {
		return (EReference) siteplanStateEClass.getEStructuralFeatures()
				.get(15);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getSiteplanObject() {
		return siteplanObjectEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getSiteplanObject_Guid() {
		return (EAttribute) siteplanObjectEClass.getEStructuralFeatures()
				.get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getPositionedObject() {
		return positionedObjectEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getPositionedObject_Position() {
		return (EReference) positionedObjectEClass.getEStructuralFeatures()
				.get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getCoordinate() {
		return coordinateEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getCoordinate_X() {
		return (EAttribute) coordinateEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getCoordinate_Y() {
		return (EAttribute) coordinateEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getPosition() {
		return positionEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getPosition_Rotation() {
		return (EAttribute) positionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getRouteObject() {
		return routeObjectEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getRouteObject_RouteLocations() {
		return (EReference) routeObjectEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getRouteLocation() {
		return routeLocationEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getRouteLocation_Km() {
		return (EAttribute) routeLocationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getRouteLocation_Route() {
		return (EAttribute) routeLocationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getSignalMount() {
		return signalMountEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getSignalMount_AttachedSignals() {
		return (EReference) signalMountEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getSignalMount_MountType() {
		return (EAttribute) signalMountEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getSignal() {
		return signalEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getSignal_Role() {
		return (EAttribute) signalEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getSignal_System() {
		return (EAttribute) signalEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getSignal_Screen() {
		return (EReference) signalEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getSignal_Label() {
		return (EReference) signalEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getSignal_LateralDistance() {
		return (EAttribute) signalEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getSignal_SignalDirection() {
		return (EAttribute) signalEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getSignal_MountPosition() {
		return (EReference) signalEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getSignalScreen() {
		return signalScreenEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getSignalScreen_Screen() {
		return (EAttribute) signalScreenEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getSignalScreen_Switched() {
		return (EAttribute) signalScreenEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getSignalScreen_FrameType() {
		return (EAttribute) signalScreenEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getLabel() {
		return labelEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getLabel_Text() {
		return (EAttribute) labelEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getLabel_OrientationInverted() {
		return (EAttribute) labelEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getTrackSwitch() {
		return trackSwitchEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getTrackSwitch_Design() {
		return (EAttribute) trackSwitchEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getTrackSwitch_Components() {
		return (EReference) trackSwitchEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getTrackSwitch_ContinuousSegments() {
		return (EReference) trackSwitchEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getTrackSwitch_SwitchType() {
		return (EAttribute) trackSwitchEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getTrackSwitchComponent() {
		return trackSwitchComponentEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getTrackSwitchComponent_PreferredLocation() {
		return (EAttribute) trackSwitchComponentEClass.getEStructuralFeatures()
				.get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getTrackSwitchComponent_PointDetectorCount() {
		return (EAttribute) trackSwitchComponentEClass.getEStructuralFeatures()
				.get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getTrackSwitchComponent_Start() {
		return (EReference) trackSwitchComponentEClass.getEStructuralFeatures()
				.get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getTrackSwitchComponent_LabelPosition() {
		return (EReference) trackSwitchComponentEClass.getEStructuralFeatures()
				.get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getTrackSwitchComponent_Label() {
		return (EReference) trackSwitchComponentEClass.getEStructuralFeatures()
				.get(4);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getTrackSwitchComponent_OperatingMode() {
		return (EAttribute) trackSwitchComponentEClass.getEStructuralFeatures()
				.get(5);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getTrackSwitchComponent_MainLeg() {
		return (EReference) trackSwitchComponentEClass.getEStructuralFeatures()
				.get(6);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getTrackSwitchComponent_SideLeg() {
		return (EReference) trackSwitchComponentEClass.getEStructuralFeatures()
				.get(7);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getContinuousTrackSegment() {
		return continuousTrackSegmentEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getContinuousTrackSegment_Start() {
		return (EReference) continuousTrackSegmentEClass
				.getEStructuralFeatures()
				.get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getContinuousTrackSegment_End() {
		return (EReference) continuousTrackSegmentEClass
				.getEStructuralFeatures()
				.get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getTrack() {
		return trackEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getTrack_Sections() {
		return (EReference) trackEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getTrack_Designations() {
		return (EReference) trackEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getTrack_StartCoordinate() {
		return (EReference) trackEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getTrackSection() {
		return trackSectionEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getTrackSection_Shape() {
		return (EAttribute) trackSectionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getTrackSection_Segments() {
		return (EReference) trackSectionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getTrackSection_Color() {
		return (EAttribute) trackSectionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getTrackSection_StartCoordinate() {
		return (EReference) trackSectionEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getTrackSegment() {
		return trackSegmentEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getTrackSegment_Type() {
		return (EAttribute) trackSegmentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getTrackSegment_Positions() {
		return (EReference) trackSegmentEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getFMAComponent() {
		return fmaComponentEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getFMAComponent_Label() {
		return (EReference) fmaComponentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getFMAComponent_Type() {
		return (EAttribute) fmaComponentEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getFMAComponent_RightSide() {
		return (EAttribute) fmaComponentEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getRoute() {
		return routeEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getRoute_Sections() {
		return (EReference) routeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getRoute_Markers() {
		return (EReference) routeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getRouteSection() {
		return routeSectionEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getRouteSection_Guid() {
		return (EAttribute) routeSectionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getRouteSection_Shape() {
		return (EAttribute) routeSectionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getRouteSection_Positions() {
		return (EReference) routeSectionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getKMMarker() {
		return kmMarkerEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getKMMarker_Position() {
		return (EReference) kmMarkerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getKMMarker_Value() {
		return (EAttribute) kmMarkerEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getTrackSwitchEndMarker() {
		return trackSwitchEndMarkerEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getTrackSwitchEndMarker_LegACoordinate() {
		return (EReference) trackSwitchEndMarkerEClass.getEStructuralFeatures()
				.get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getTrackSwitchEndMarker_LegBCoordinate() {
		return (EReference) trackSwitchEndMarkerEClass.getEStructuralFeatures()
				.get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getError() {
		return errorEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getError_Position() {
		return (EReference) errorEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getError_RelevantGUIDs() {
		return (EAttribute) errorEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getError_Message() {
		return (EAttribute) errorEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getPZB() {
		return pzbEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getPZB_Type() {
		return (EAttribute) pzbEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getPZB_Element() {
		return (EAttribute) pzbEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getPZB_RightSide() {
		return (EAttribute) pzbEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getPZB_Effectivity() {
		return (EAttribute) pzbEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getPZBGU() {
		return pzbguEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getPZBGU_Pzbs() {
		return (EReference) pzbguEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getPZBGU_Length() {
		return (EAttribute) pzbguEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getTrackDesignation() {
		return trackDesignationEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getTrackDesignation_Name() {
		return (EAttribute) trackDesignationEClass.getEStructuralFeatures()
				.get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getTrackDesignation_Position() {
		return (EReference) trackDesignationEClass.getEStructuralFeatures()
				.get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getTrackSwitchLeg() {
		return trackSwitchLegEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getTrackSwitchLeg_Connection() {
		return (EAttribute) trackSwitchLegEClass.getEStructuralFeatures()
				.get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getTrackSwitchLeg_Coordinates() {
		return (EReference) trackSwitchLegEClass.getEStructuralFeatures()
				.get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getStation() {
		return stationEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getStation_Platforms() {
		return (EReference) stationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getStation_Label() {
		return (EReference) stationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getPlatform() {
		return platformEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getPlatform_Guid() {
		return (EAttribute) platformEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getPlatform_Label() {
		return (EReference) platformEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getPlatform_LabelPosition() {
		return (EReference) platformEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getPlatform_Points() {
		return (EReference) platformEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getTrackLock() {
		return trackLockEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getTrackLock_Components() {
		return (EReference) trackLockEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getTrackLock_PreferredLocation() {
		return (EAttribute) trackLockEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getTrackLock_OperatingMode() {
		return (EAttribute) trackLockEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getTrackLock_Label() {
		return (EReference) trackLockEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getTrackLockComponent() {
		return trackLockComponentEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getTrackLockComponent_TrackLockSignal() {
		return (EAttribute) trackLockComponentEClass.getEStructuralFeatures()
				.get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getTrackLockComponent_EjectionDirection() {
		return (EAttribute) trackLockComponentEClass.getEStructuralFeatures()
				.get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getObjectManagement() {
		return objectManagementEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getObjectManagement_PlanningObjectIDs() {
		return (EAttribute) objectManagementEClass.getEStructuralFeatures()
				.get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getObjectManagement_PlanningGroupID() {
		return (EAttribute) objectManagementEClass.getEStructuralFeatures()
				.get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getTrackClose() {
		return trackCloseEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getTrackClose_TrackCloseType() {
		return (EAttribute) trackCloseEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getExternalElementControl() {
		return externalElementControlEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getExternalElementControl_ControlArt() {
		return (EAttribute) externalElementControlEClass
				.getEStructuralFeatures()
				.get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getExternalElementControl_ElementType() {
		return (EAttribute) externalElementControlEClass
				.getEStructuralFeatures()
				.get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getExternalElementControl_ControlStation() {
		return (EAttribute) externalElementControlEClass
				.getEStructuralFeatures()
				.get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getExternalElementControl_Label() {
		return (EReference) externalElementControlEClass
				.getEStructuralFeatures()
				.get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getLockKey() {
		return lockKeyEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getLockKey_Label() {
		return (EReference) lockKeyEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getLockKey_Type() {
		return (EAttribute) lockKeyEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getLockKey_Locked() {
		return (EAttribute) lockKeyEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getLayoutinfo() {
		return layoutinfoEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getLayoutinfo_Label() {
		return (EAttribute) layoutinfoEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getLayoutinfo_SheetsCut() {
		return (EReference) layoutinfoEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getSheetCut() {
		return sheetCutEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getSheetCut_Label() {
		return (EAttribute) sheetCutEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getSheetCut_PolygonDirection() {
		return (EReference) sheetCutEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getSheetCut_Polygon() {
		return (EReference) sheetCutEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getCant() {
		return cantEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getCant_PointA() {
		return (EReference) cantEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getCant_PointB() {
		return (EReference) cantEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getCant_Form() {
		return (EAttribute) cantEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getCant_Length() {
		return (EAttribute) cantEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getCantPoint() {
		return cantPointEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getCantPoint_Height() {
		return (EAttribute) cantPointEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getUnknownPositionedObject() {
		return unknownPositionedObjectEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getUnknownPositionedObject_ObjectType() {
		return (EAttribute) unknownPositionedObjectEClass
				.getEStructuralFeatures()
				.get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EEnum getSignalMountType() {
		return signalMountTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EEnum getSignalRole() {
		return signalRoleEEnum;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EEnum getSignalSystem() {
		return signalSystemEEnum;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EEnum getMountDirection() {
		return mountDirectionEEnum;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EEnum getTurnoutOperatingMode() {
		return turnoutOperatingModeEEnum;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EEnum getTrackShape() {
		return trackShapeEEnum;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EEnum getTrackType() {
		return trackTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EEnum getFMAComponentType() {
		return fmaComponentTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EEnum getPZBType() {
		return pzbTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EEnum getPZBElement() {
		return pzbElementEEnum;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EEnum getPZBEffectivity() {
		return pzbEffectivityEEnum;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EEnum getTrackLockLocation() {
		return trackLockLocationEEnum;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EEnum getLeftRight() {
		return leftRightEEnum;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EEnum getDirection() {
		return directionEEnum;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EEnum getTrackCloseType() {
		return trackCloseTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EEnum getExternalElementControlArt() {
		return externalElementControlArtEEnum;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EEnum getControlStationType() {
		return controlStationTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EEnum getLockKeyType() {
		return lockKeyTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EEnum getSwitchType() {
		return switchTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public SiteplanFactory getSiteplanFactory() {
		return (SiteplanFactory) getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package. This method is guarded to
	 * have no affect on any invocation but its first. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated)
			return;
		isCreated = true;

		// Create classes and their features
		siteplanEClass = createEClass(SITEPLAN);
		createEReference(siteplanEClass, SITEPLAN__INITIAL_STATE);
		createEReference(siteplanEClass, SITEPLAN__CHANGED_INITIAL_STATE);
		createEReference(siteplanEClass, SITEPLAN__COMMON_STATE);
		createEReference(siteplanEClass, SITEPLAN__CHANGED_FINAL_STATE);
		createEReference(siteplanEClass, SITEPLAN__FINAL_STATE);
		createEReference(siteplanEClass, SITEPLAN__CENTER_POSITION);
		createEReference(siteplanEClass, SITEPLAN__OBJECT_MANAGEMENT);
		createEReference(siteplanEClass, SITEPLAN__LAYOUT_INFO);

		siteplanStateEClass = createEClass(SITEPLAN_STATE);
		createEReference(siteplanStateEClass, SITEPLAN_STATE__SIGNALS);
		createEReference(siteplanStateEClass, SITEPLAN_STATE__TRACK_SWITCHES);
		createEReference(siteplanStateEClass,
				SITEPLAN_STATE__TRACK_SWITCH_END_MARKERS);
		createEReference(siteplanStateEClass, SITEPLAN_STATE__TRACKS);
		createEReference(siteplanStateEClass, SITEPLAN_STATE__FMA_COMPONENTS);
		createEReference(siteplanStateEClass, SITEPLAN_STATE__PZB);
		createEReference(siteplanStateEClass, SITEPLAN_STATE__PZB_GU);
		createEReference(siteplanStateEClass, SITEPLAN_STATE__ROUTES);
		createEReference(siteplanStateEClass, SITEPLAN_STATE__STATIONS);
		createEReference(siteplanStateEClass, SITEPLAN_STATE__TRACK_LOCK);
		createEReference(siteplanStateEClass, SITEPLAN_STATE__ERRORS);
		createEReference(siteplanStateEClass, SITEPLAN_STATE__TRACK_CLOSURES);
		createEReference(siteplanStateEClass,
				SITEPLAN_STATE__EXTERNAL_ELEMENT_CONTROLS);
		createEReference(siteplanStateEClass, SITEPLAN_STATE__LOCKKEYS);
		createEReference(siteplanStateEClass, SITEPLAN_STATE__CANTS);
		createEReference(siteplanStateEClass, SITEPLAN_STATE__UNKNOWN_OBJECTS);

		siteplanObjectEClass = createEClass(SITEPLAN_OBJECT);
		createEAttribute(siteplanObjectEClass, SITEPLAN_OBJECT__GUID);

		positionedObjectEClass = createEClass(POSITIONED_OBJECT);
		createEReference(positionedObjectEClass, POSITIONED_OBJECT__POSITION);

		coordinateEClass = createEClass(COORDINATE);
		createEAttribute(coordinateEClass, COORDINATE__X);
		createEAttribute(coordinateEClass, COORDINATE__Y);

		positionEClass = createEClass(POSITION);
		createEAttribute(positionEClass, POSITION__ROTATION);

		routeObjectEClass = createEClass(ROUTE_OBJECT);
		createEReference(routeObjectEClass, ROUTE_OBJECT__ROUTE_LOCATIONS);

		routeLocationEClass = createEClass(ROUTE_LOCATION);
		createEAttribute(routeLocationEClass, ROUTE_LOCATION__KM);
		createEAttribute(routeLocationEClass, ROUTE_LOCATION__ROUTE);

		signalMountEClass = createEClass(SIGNAL_MOUNT);
		createEReference(signalMountEClass, SIGNAL_MOUNT__ATTACHED_SIGNALS);
		createEAttribute(signalMountEClass, SIGNAL_MOUNT__MOUNT_TYPE);

		signalEClass = createEClass(SIGNAL);
		createEAttribute(signalEClass, SIGNAL__ROLE);
		createEAttribute(signalEClass, SIGNAL__SYSTEM);
		createEReference(signalEClass, SIGNAL__SCREEN);
		createEReference(signalEClass, SIGNAL__LABEL);
		createEAttribute(signalEClass, SIGNAL__LATERAL_DISTANCE);
		createEAttribute(signalEClass, SIGNAL__SIGNAL_DIRECTION);
		createEReference(signalEClass, SIGNAL__MOUNT_POSITION);

		signalScreenEClass = createEClass(SIGNAL_SCREEN);
		createEAttribute(signalScreenEClass, SIGNAL_SCREEN__SCREEN);
		createEAttribute(signalScreenEClass, SIGNAL_SCREEN__SWITCHED);
		createEAttribute(signalScreenEClass, SIGNAL_SCREEN__FRAME_TYPE);

		labelEClass = createEClass(LABEL);
		createEAttribute(labelEClass, LABEL__TEXT);
		createEAttribute(labelEClass, LABEL__ORIENTATION_INVERTED);

		trackSwitchEClass = createEClass(TRACK_SWITCH);
		createEAttribute(trackSwitchEClass, TRACK_SWITCH__DESIGN);
		createEReference(trackSwitchEClass, TRACK_SWITCH__COMPONENTS);
		createEReference(trackSwitchEClass, TRACK_SWITCH__CONTINUOUS_SEGMENTS);
		createEAttribute(trackSwitchEClass, TRACK_SWITCH__SWITCH_TYPE);

		trackSwitchComponentEClass = createEClass(TRACK_SWITCH_COMPONENT);
		createEAttribute(trackSwitchComponentEClass,
				TRACK_SWITCH_COMPONENT__PREFERRED_LOCATION);
		createEAttribute(trackSwitchComponentEClass,
				TRACK_SWITCH_COMPONENT__POINT_DETECTOR_COUNT);
		createEReference(trackSwitchComponentEClass,
				TRACK_SWITCH_COMPONENT__START);
		createEReference(trackSwitchComponentEClass,
				TRACK_SWITCH_COMPONENT__LABEL_POSITION);
		createEReference(trackSwitchComponentEClass,
				TRACK_SWITCH_COMPONENT__LABEL);
		createEAttribute(trackSwitchComponentEClass,
				TRACK_SWITCH_COMPONENT__OPERATING_MODE);
		createEReference(trackSwitchComponentEClass,
				TRACK_SWITCH_COMPONENT__MAIN_LEG);
		createEReference(trackSwitchComponentEClass,
				TRACK_SWITCH_COMPONENT__SIDE_LEG);

		continuousTrackSegmentEClass = createEClass(CONTINUOUS_TRACK_SEGMENT);
		createEReference(continuousTrackSegmentEClass,
				CONTINUOUS_TRACK_SEGMENT__START);
		createEReference(continuousTrackSegmentEClass,
				CONTINUOUS_TRACK_SEGMENT__END);

		trackEClass = createEClass(TRACK);
		createEReference(trackEClass, TRACK__SECTIONS);
		createEReference(trackEClass, TRACK__DESIGNATIONS);
		createEReference(trackEClass, TRACK__START_COORDINATE);

		trackSectionEClass = createEClass(TRACK_SECTION);
		createEAttribute(trackSectionEClass, TRACK_SECTION__SHAPE);
		createEReference(trackSectionEClass, TRACK_SECTION__SEGMENTS);
		createEAttribute(trackSectionEClass, TRACK_SECTION__COLOR);
		createEReference(trackSectionEClass, TRACK_SECTION__START_COORDINATE);

		trackSegmentEClass = createEClass(TRACK_SEGMENT);
		createEAttribute(trackSegmentEClass, TRACK_SEGMENT__TYPE);
		createEReference(trackSegmentEClass, TRACK_SEGMENT__POSITIONS);

		fmaComponentEClass = createEClass(FMA_COMPONENT);
		createEReference(fmaComponentEClass, FMA_COMPONENT__LABEL);
		createEAttribute(fmaComponentEClass, FMA_COMPONENT__TYPE);
		createEAttribute(fmaComponentEClass, FMA_COMPONENT__RIGHT_SIDE);

		routeEClass = createEClass(ROUTE);
		createEReference(routeEClass, ROUTE__SECTIONS);
		createEReference(routeEClass, ROUTE__MARKERS);

		routeSectionEClass = createEClass(ROUTE_SECTION);
		createEAttribute(routeSectionEClass, ROUTE_SECTION__GUID);
		createEAttribute(routeSectionEClass, ROUTE_SECTION__SHAPE);
		createEReference(routeSectionEClass, ROUTE_SECTION__POSITIONS);

		kmMarkerEClass = createEClass(KM_MARKER);
		createEReference(kmMarkerEClass, KM_MARKER__POSITION);
		createEAttribute(kmMarkerEClass, KM_MARKER__VALUE);

		trackSwitchEndMarkerEClass = createEClass(TRACK_SWITCH_END_MARKER);
		createEReference(trackSwitchEndMarkerEClass,
				TRACK_SWITCH_END_MARKER__LEG_ACOORDINATE);
		createEReference(trackSwitchEndMarkerEClass,
				TRACK_SWITCH_END_MARKER__LEG_BCOORDINATE);

		errorEClass = createEClass(ERROR);
		createEReference(errorEClass, ERROR__POSITION);
		createEAttribute(errorEClass, ERROR__RELEVANT_GUI_DS);
		createEAttribute(errorEClass, ERROR__MESSAGE);

		pzbEClass = createEClass(PZB);
		createEAttribute(pzbEClass, PZB__TYPE);
		createEAttribute(pzbEClass, PZB__ELEMENT);
		createEAttribute(pzbEClass, PZB__RIGHT_SIDE);
		createEAttribute(pzbEClass, PZB__EFFECTIVITY);

		pzbguEClass = createEClass(PZBGU);
		createEReference(pzbguEClass, PZBGU__PZBS);
		createEAttribute(pzbguEClass, PZBGU__LENGTH);

		trackDesignationEClass = createEClass(TRACK_DESIGNATION);
		createEAttribute(trackDesignationEClass, TRACK_DESIGNATION__NAME);
		createEReference(trackDesignationEClass, TRACK_DESIGNATION__POSITION);

		trackSwitchLegEClass = createEClass(TRACK_SWITCH_LEG);
		createEAttribute(trackSwitchLegEClass, TRACK_SWITCH_LEG__CONNECTION);
		createEReference(trackSwitchLegEClass, TRACK_SWITCH_LEG__COORDINATES);

		stationEClass = createEClass(STATION);
		createEReference(stationEClass, STATION__PLATFORMS);
		createEReference(stationEClass, STATION__LABEL);

		platformEClass = createEClass(PLATFORM);
		createEAttribute(platformEClass, PLATFORM__GUID);
		createEReference(platformEClass, PLATFORM__LABEL);
		createEReference(platformEClass, PLATFORM__LABEL_POSITION);
		createEReference(platformEClass, PLATFORM__POINTS);

		trackLockEClass = createEClass(TRACK_LOCK);
		createEReference(trackLockEClass, TRACK_LOCK__COMPONENTS);
		createEAttribute(trackLockEClass, TRACK_LOCK__PREFERRED_LOCATION);
		createEAttribute(trackLockEClass, TRACK_LOCK__OPERATING_MODE);
		createEReference(trackLockEClass, TRACK_LOCK__LABEL);

		trackLockComponentEClass = createEClass(TRACK_LOCK_COMPONENT);
		createEAttribute(trackLockComponentEClass,
				TRACK_LOCK_COMPONENT__TRACK_LOCK_SIGNAL);
		createEAttribute(trackLockComponentEClass,
				TRACK_LOCK_COMPONENT__EJECTION_DIRECTION);

		objectManagementEClass = createEClass(OBJECT_MANAGEMENT);
		createEAttribute(objectManagementEClass,
				OBJECT_MANAGEMENT__PLANNING_OBJECT_IDS);
		createEAttribute(objectManagementEClass,
				OBJECT_MANAGEMENT__PLANNING_GROUP_ID);

		trackCloseEClass = createEClass(TRACK_CLOSE);
		createEAttribute(trackCloseEClass, TRACK_CLOSE__TRACK_CLOSE_TYPE);

		externalElementControlEClass = createEClass(EXTERNAL_ELEMENT_CONTROL);
		createEAttribute(externalElementControlEClass,
				EXTERNAL_ELEMENT_CONTROL__CONTROL_ART);
		createEAttribute(externalElementControlEClass,
				EXTERNAL_ELEMENT_CONTROL__ELEMENT_TYPE);
		createEAttribute(externalElementControlEClass,
				EXTERNAL_ELEMENT_CONTROL__CONTROL_STATION);
		createEReference(externalElementControlEClass,
				EXTERNAL_ELEMENT_CONTROL__LABEL);

		lockKeyEClass = createEClass(LOCK_KEY);
		createEReference(lockKeyEClass, LOCK_KEY__LABEL);
		createEAttribute(lockKeyEClass, LOCK_KEY__TYPE);
		createEAttribute(lockKeyEClass, LOCK_KEY__LOCKED);

		layoutinfoEClass = createEClass(LAYOUTINFO);
		createEAttribute(layoutinfoEClass, LAYOUTINFO__LABEL);
		createEReference(layoutinfoEClass, LAYOUTINFO__SHEETS_CUT);

		sheetCutEClass = createEClass(SHEET_CUT);
		createEAttribute(sheetCutEClass, SHEET_CUT__LABEL);
		createEReference(sheetCutEClass, SHEET_CUT__POLYGON_DIRECTION);
		createEReference(sheetCutEClass, SHEET_CUT__POLYGON);

		cantEClass = createEClass(CANT);
		createEReference(cantEClass, CANT__POINT_A);
		createEReference(cantEClass, CANT__POINT_B);
		createEAttribute(cantEClass, CANT__FORM);
		createEAttribute(cantEClass, CANT__LENGTH);

		cantPointEClass = createEClass(CANT_POINT);
		createEAttribute(cantPointEClass, CANT_POINT__HEIGHT);

		unknownPositionedObjectEClass = createEClass(UNKNOWN_POSITIONED_OBJECT);
		createEAttribute(unknownPositionedObjectEClass,
				UNKNOWN_POSITIONED_OBJECT__OBJECT_TYPE);

		// Create enums
		signalMountTypeEEnum = createEEnum(SIGNAL_MOUNT_TYPE);
		signalRoleEEnum = createEEnum(SIGNAL_ROLE);
		signalSystemEEnum = createEEnum(SIGNAL_SYSTEM);
		mountDirectionEEnum = createEEnum(MOUNT_DIRECTION);
		turnoutOperatingModeEEnum = createEEnum(TURNOUT_OPERATING_MODE);
		trackShapeEEnum = createEEnum(TRACK_SHAPE);
		trackTypeEEnum = createEEnum(TRACK_TYPE);
		fmaComponentTypeEEnum = createEEnum(FMA_COMPONENT_TYPE);
		pzbTypeEEnum = createEEnum(PZB_TYPE);
		pzbElementEEnum = createEEnum(PZB_ELEMENT);
		pzbEffectivityEEnum = createEEnum(PZB_EFFECTIVITY);
		trackLockLocationEEnum = createEEnum(TRACK_LOCK_LOCATION);
		leftRightEEnum = createEEnum(LEFT_RIGHT);
		directionEEnum = createEEnum(DIRECTION);
		trackCloseTypeEEnum = createEEnum(TRACK_CLOSE_TYPE);
		externalElementControlArtEEnum = createEEnum(
				EXTERNAL_ELEMENT_CONTROL_ART);
		controlStationTypeEEnum = createEEnum(CONTROL_STATION_TYPE);
		lockKeyTypeEEnum = createEEnum(LOCK_KEY_TYPE);
		switchTypeEEnum = createEEnum(SWITCH_TYPE);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model. This
	 * method is guarded to have no affect on any invocation but its first. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized)
			return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		positionedObjectEClass.getESuperTypes().add(this.getSiteplanObject());
		positionEClass.getESuperTypes().add(this.getCoordinate());
		routeObjectEClass.getESuperTypes().add(this.getSiteplanObject());
		signalMountEClass.getESuperTypes().add(this.getPositionedObject());
		signalMountEClass.getESuperTypes().add(this.getRouteObject());
		signalEClass.getESuperTypes().add(this.getRouteObject());
		trackSwitchEClass.getESuperTypes().add(this.getSiteplanObject());
		trackSwitchComponentEClass.getESuperTypes().add(this.getRouteObject());
		trackEClass.getESuperTypes().add(this.getSiteplanObject());
		trackSectionEClass.getESuperTypes().add(this.getSiteplanObject());
		trackSegmentEClass.getESuperTypes().add(this.getSiteplanObject());
		fmaComponentEClass.getESuperTypes().add(this.getRouteObject());
		fmaComponentEClass.getESuperTypes().add(this.getPositionedObject());
		routeEClass.getESuperTypes().add(this.getSiteplanObject());
		pzbEClass.getESuperTypes().add(this.getRouteObject());
		pzbEClass.getESuperTypes().add(this.getPositionedObject());
		pzbguEClass.getESuperTypes().add(this.getSiteplanObject());
		stationEClass.getESuperTypes().add(this.getSiteplanObject());
		trackLockEClass.getESuperTypes().add(this.getSiteplanObject());
		trackLockComponentEClass.getESuperTypes()
				.add(this.getPositionedObject());
		trackCloseEClass.getESuperTypes().add(this.getPositionedObject());
		externalElementControlEClass.getESuperTypes()
				.add(this.getRouteObject());
		externalElementControlEClass.getESuperTypes()
				.add(this.getPositionedObject());
		lockKeyEClass.getESuperTypes().add(this.getRouteObject());
		lockKeyEClass.getESuperTypes().add(this.getPositionedObject());
		layoutinfoEClass.getESuperTypes().add(this.getSiteplanObject());
		sheetCutEClass.getESuperTypes().add(this.getSiteplanObject());
		cantEClass.getESuperTypes().add(this.getSiteplanObject());
		cantPointEClass.getESuperTypes().add(this.getPositionedObject());
		unknownPositionedObjectEClass.getESuperTypes()
				.add(this.getPositionedObject());

		// Initialize classes, features, and operations; add parameters
		initEClass(siteplanEClass, Siteplan.class, "Siteplan", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getSiteplan_InitialState(), this.getSiteplanState(),
				null, "initialState", null, 0, 1, Siteplan.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSiteplan_ChangedInitialState(),
				this.getSiteplanState(), null, "changedInitialState", null, 0,
				1, Siteplan.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEReference(getSiteplan_CommonState(), this.getSiteplanState(), null,
				"commonState", null, 0, 1, Siteplan.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSiteplan_ChangedFinalState(), this.getSiteplanState(),
				null, "changedFinalState", null, 0, 1, Siteplan.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEReference(getSiteplan_FinalState(), this.getSiteplanState(), null,
				"finalState", null, 0, 1, Siteplan.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSiteplan_CenterPosition(), this.getPosition(), null,
				"centerPosition", null, 0, 1, Siteplan.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSiteplan_ObjectManagement(),
				this.getObjectManagement(), null, "objectManagement", null, 0,
				-1, Siteplan.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEReference(getSiteplan_LayoutInfo(), this.getLayoutinfo(), null,
				"layoutInfo", null, 0, -1, Siteplan.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(siteplanStateEClass, SiteplanState.class, "SiteplanState",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getSiteplanState_Signals(), this.getSignalMount(), null,
				"signals", null, 0, -1, SiteplanState.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSiteplanState_TrackSwitches(), this.getTrackSwitch(),
				null, "trackSwitches", null, 0, -1, SiteplanState.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEReference(getSiteplanState_TrackSwitchEndMarkers(),
				this.getTrackSwitchEndMarker(), null, "trackSwitchEndMarkers",
				null, 0, -1, SiteplanState.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSiteplanState_Tracks(), this.getTrack(), null,
				"tracks", null, 0, -1, SiteplanState.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSiteplanState_FmaComponents(), this.getFMAComponent(),
				null, "fmaComponents", null, 0, -1, SiteplanState.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEReference(getSiteplanState_Pzb(), this.getPZB(), null, "pzb", null,
				1, -1, SiteplanState.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSiteplanState_PzbGU(), this.getPZBGU(), null, "pzbGU",
				null, 1, -1, SiteplanState.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSiteplanState_Routes(), this.getRoute(), null,
				"routes", null, 0, -1, SiteplanState.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSiteplanState_Stations(), this.getStation(), null,
				"stations", null, 0, -1, SiteplanState.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSiteplanState_TrackLock(), this.getTrackLock(), null,
				"trackLock", null, 0, -1, SiteplanState.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSiteplanState_Errors(), this.getError(), null,
				"errors", null, 0, -1, SiteplanState.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSiteplanState_TrackClosures(), this.getTrackClose(),
				null, "trackClosures", null, 0, -1, SiteplanState.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEReference(getSiteplanState_ExternalElementControls(),
				this.getExternalElementControl(), null,
				"externalElementControls", null, 0, -1, SiteplanState.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEReference(getSiteplanState_Lockkeys(), this.getLockKey(), null,
				"lockkeys", null, 0, -1, SiteplanState.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSiteplanState_Cants(), this.getCant(), null, "cants",
				null, 0, -1, SiteplanState.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSiteplanState_UnknownObjects(),
				this.getUnknownPositionedObject(), null, "unknownObjects", null,
				0, -1, SiteplanState.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(siteplanObjectEClass, SiteplanObject.class, "SiteplanObject",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getSiteplanObject_Guid(), ecorePackage.getEString(),
				"guid", null, 0, 1, SiteplanObject.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		initEClass(positionedObjectEClass, PositionedObject.class,
				"PositionedObject", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getPositionedObject_Position(), this.getPosition(), null,
				"position", null, 0, 1, PositionedObject.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(coordinateEClass, Coordinate.class, "Coordinate",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getCoordinate_X(), ecorePackage.getEDouble(), "x", "0",
				0, 1, Coordinate.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEAttribute(getCoordinate_Y(), ecorePackage.getEDouble(), "y", "0",
				0, 1, Coordinate.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);

		initEClass(positionEClass, Position.class, "Position", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getPosition_Rotation(), ecorePackage.getEDouble(),
				"rotation", "0", 0, 1, Position.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		initEClass(routeObjectEClass, RouteObject.class, "RouteObject",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getRouteObject_RouteLocations(), this.getRouteLocation(),
				null, "routeLocations", null, 0, -1, RouteObject.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);

		initEClass(routeLocationEClass, RouteLocation.class, "RouteLocation",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getRouteLocation_Km(), ecorePackage.getEString(), "km",
				null, 0, 1, RouteLocation.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEAttribute(getRouteLocation_Route(), ecorePackage.getEString(),
				"route", null, 0, 1, RouteLocation.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		initEClass(signalMountEClass, SignalMount.class, "SignalMount",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getSignalMount_AttachedSignals(), this.getSignal(), null,
				"attachedSignals", null, 0, -1, SignalMount.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEAttribute(getSignalMount_MountType(), this.getSignalMountType(),
				"mountType", null, 0, 1, SignalMount.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		initEClass(signalEClass, Signal.class, "Signal", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getSignal_Role(), this.getSignalRole(), "role", null, 0,
				1, Signal.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSignal_System(), this.getSignalSystem(), "system",
				null, 0, 1, Signal.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEReference(getSignal_Screen(), this.getSignalScreen(), null,
				"screen", null, 0, -1, Signal.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSignal_Label(), this.getLabel(), null, "label", null,
				0, 1, Signal.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getSignal_LateralDistance(),
				ecorePackage.getEBigDecimal(), "lateralDistance", null, 0, -1,
				Signal.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSignal_SignalDirection(), this.getDirection(),
				"signalDirection", null, 0, 1, Signal.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEReference(getSignal_MountPosition(), this.getPosition(), null,
				"mountPosition", null, 0, 1, Signal.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(signalScreenEClass, SignalScreen.class, "SignalScreen",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getSignalScreen_Screen(), ecorePackage.getEString(),
				"screen", null, 0, 1, SignalScreen.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getSignalScreen_Switched(), ecorePackage.getEBoolean(),
				"switched", null, 0, 1, SignalScreen.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getSignalScreen_FrameType(), ecorePackage.getEString(),
				"frameType", null, 0, 1, SignalScreen.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		initEClass(labelEClass, Label.class, "Label", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getLabel_Text(), ecorePackage.getEString(), "text", null,
				0, 1, Label.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getLabel_OrientationInverted(),
				ecorePackage.getEBoolean(), "orientationInverted", null, 0, 1,
				Label.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(trackSwitchEClass, TrackSwitch.class, "TrackSwitch",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTrackSwitch_Design(), ecorePackage.getEString(),
				"design", null, 0, 1, TrackSwitch.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEReference(getTrackSwitch_Components(),
				this.getTrackSwitchComponent(), null, "components", null, 0, 2,
				TrackSwitch.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEReference(getTrackSwitch_ContinuousSegments(),
				this.getContinuousTrackSegment(), null, "continuousSegments",
				null, 0, 2, TrackSwitch.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTrackSwitch_SwitchType(), this.getSwitchType(),
				"switchType", null, 0, 1, TrackSwitch.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		initEClass(trackSwitchComponentEClass, TrackSwitchComponent.class,
				"TrackSwitchComponent", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTrackSwitchComponent_PreferredLocation(),
				this.getLeftRight(), "preferredLocation", null, 0, 1,
				TrackSwitchComponent.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEAttribute(getTrackSwitchComponent_PointDetectorCount(),
				ecorePackage.getEInt(), "pointDetectorCount", null, 0, 1,
				TrackSwitchComponent.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEReference(getTrackSwitchComponent_Start(), this.getPosition(),
				null, "start", null, 0, 1, TrackSwitchComponent.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEReference(getTrackSwitchComponent_LabelPosition(),
				this.getPosition(), null, "labelPosition", null, 0, 1,
				TrackSwitchComponent.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTrackSwitchComponent_Label(), this.getLabel(), null,
				"label", null, 0, 1, TrackSwitchComponent.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTrackSwitchComponent_OperatingMode(),
				this.getTurnoutOperatingMode(), "operatingMode", "Undefined", 1,
				1, TrackSwitchComponent.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEReference(getTrackSwitchComponent_MainLeg(),
				this.getTrackSwitchLeg(), null, "mainLeg", null, 0, 1,
				TrackSwitchComponent.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTrackSwitchComponent_SideLeg(),
				this.getTrackSwitchLeg(), null, "sideLeg", null, 0, 1,
				TrackSwitchComponent.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(continuousTrackSegmentEClass, ContinuousTrackSegment.class,
				"ContinuousTrackSegment", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getContinuousTrackSegment_Start(), this.getCoordinate(),
				null, "start", null, 0, 1, ContinuousTrackSegment.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEReference(getContinuousTrackSegment_End(), this.getCoordinate(),
				null, "end", null, 0, 1, ContinuousTrackSegment.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);

		initEClass(trackEClass, Track.class, "Track", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTrack_Sections(), this.getTrackSection(), null,
				"sections", null, 0, -1, Track.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTrack_Designations(), this.getTrackDesignation(),
				null, "designations", null, 0, -1, Track.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTrack_StartCoordinate(), this.getCoordinate(), null,
				"startCoordinate", null, 0, 1, Track.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(trackSectionEClass, TrackSection.class, "TrackSection",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTrackSection_Shape(), this.getTrackShape(), "shape",
				null, 0, 1, TrackSection.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEReference(getTrackSection_Segments(), this.getTrackSegment(), null,
				"segments", null, 0, -1, TrackSection.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTrackSection_Color(), ecorePackage.getEString(),
				"color", null, 0, 1, TrackSection.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEReference(getTrackSection_StartCoordinate(), this.getCoordinate(),
				null, "startCoordinate", null, 0, 1, TrackSection.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);

		initEClass(trackSegmentEClass, TrackSegment.class, "TrackSegment",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTrackSegment_Type(), this.getTrackType(), "type",
				null, 0, -1, TrackSegment.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEReference(getTrackSegment_Positions(), this.getCoordinate(), null,
				"positions", null, 0, -1, TrackSegment.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(fmaComponentEClass, FMAComponent.class, "FMAComponent",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getFMAComponent_Label(), this.getLabel(), null, "label",
				null, 0, 1, FMAComponent.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFMAComponent_Type(), this.getFMAComponentType(),
				"type", null, 0, 1, FMAComponent.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getFMAComponent_RightSide(), ecorePackage.getEBoolean(),
				"rightSide", null, 0, 1, FMAComponent.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		initEClass(routeEClass, Route.class, "Route", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getRoute_Sections(), this.getRouteSection(), null,
				"sections", null, 0, -1, Route.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRoute_Markers(), this.getKMMarker(), null, "markers",
				null, 0, -1, Route.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(routeSectionEClass, RouteSection.class, "RouteSection",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getRouteSection_Guid(), ecorePackage.getEString(),
				"guid", null, 0, 1, RouteSection.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getRouteSection_Shape(), this.getTrackShape(), "shape",
				null, 0, 1, RouteSection.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEReference(getRouteSection_Positions(), this.getCoordinate(), null,
				"positions", null, 0, -1, RouteSection.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(kmMarkerEClass, KMMarker.class, "KMMarker", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getKMMarker_Position(), this.getPosition(), null,
				"position", null, 0, 1, KMMarker.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getKMMarker_Value(), ecorePackage.getEInt(), "value",
				null, 0, 1, KMMarker.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);

		initEClass(trackSwitchEndMarkerEClass, TrackSwitchEndMarker.class,
				"TrackSwitchEndMarker", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTrackSwitchEndMarker_LegACoordinate(),
				this.getCoordinate(), null, "legACoordinate", null, 1, 1,
				TrackSwitchEndMarker.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTrackSwitchEndMarker_LegBCoordinate(),
				this.getCoordinate(), null, "legBCoordinate", null, 1, 1,
				TrackSwitchEndMarker.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(errorEClass, org.eclipse.set.model.siteplan.Error.class,
				"Error", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getError_Position(), this.getCoordinate(), null,
				"position", null, 0, 1,
				org.eclipse.set.model.siteplan.Error.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getError_RelevantGUIDs(), ecorePackage.getEString(),
				"relevantGUIDs", null, 0, -1,
				org.eclipse.set.model.siteplan.Error.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getError_Message(), ecorePackage.getEString(), "message",
				null, 0, 1, org.eclipse.set.model.siteplan.Error.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
				!IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(pzbEClass, org.eclipse.set.model.siteplan.PZB.class, "PZB",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getPZB_Type(), this.getPZBType(), "type", null, 0, 1,
				org.eclipse.set.model.siteplan.PZB.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getPZB_Element(), this.getPZBElement(), "element", null,
				0, 1, org.eclipse.set.model.siteplan.PZB.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getPZB_RightSide(), ecorePackage.getEBoolean(),
				"rightSide", null, 0, 1,
				org.eclipse.set.model.siteplan.PZB.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getPZB_Effectivity(), this.getPZBEffectivity(),
				"effectivity", null, 0, 1,
				org.eclipse.set.model.siteplan.PZB.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		initEClass(pzbguEClass, org.eclipse.set.model.siteplan.PZBGU.class,
				"PZBGU", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getPZBGU_Pzbs(), this.getPZB(), null, "pzbs", null, 0,
				-1, org.eclipse.set.model.siteplan.PZBGU.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPZBGU_Length(), ecorePackage.getEInt(), "length",
				null, 0, 1, org.eclipse.set.model.siteplan.PZBGU.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
				!IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(trackDesignationEClass, TrackDesignation.class,
				"TrackDesignation", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTrackDesignation_Name(), ecorePackage.getEString(),
				"name", null, 0, 1, TrackDesignation.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEReference(getTrackDesignation_Position(), this.getCoordinate(),
				null, "position", null, 0, 1, TrackDesignation.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);

		initEClass(trackSwitchLegEClass, TrackSwitchLeg.class, "TrackSwitchLeg",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTrackSwitchLeg_Connection(), this.getLeftRight(),
				"connection", null, 0, 1, TrackSwitchLeg.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEReference(getTrackSwitchLeg_Coordinates(), this.getCoordinate(),
				null, "coordinates", null, 0, -1, TrackSwitchLeg.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);

		initEClass(stationEClass, Station.class, "Station", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getStation_Platforms(), this.getPlatform(), null,
				"platforms", null, 0, -1, Station.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getStation_Label(), this.getLabel(), null, "label", null,
				1, 1, Station.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		initEClass(platformEClass, Platform.class, "Platform", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getPlatform_Guid(), ecorePackage.getEString(), "guid",
				null, 0, 1, Platform.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEReference(getPlatform_Label(), this.getLabel(), null, "label",
				null, 1, 1, Platform.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPlatform_LabelPosition(), this.getPosition(), null,
				"labelPosition", null, 1, 1, Platform.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPlatform_Points(), this.getCoordinate(), null,
				"points", null, 0, -1, Platform.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(trackLockEClass, TrackLock.class, "TrackLock", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTrackLock_Components(), this.getTrackLockComponent(),
				null, "components", null, 1, 2, TrackLock.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTrackLock_PreferredLocation(),
				this.getTrackLockLocation(), "preferredLocation", null, 0, 1,
				TrackLock.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTrackLock_OperatingMode(),
				this.getTurnoutOperatingMode(), "operatingMode", null, 0, 1,
				TrackLock.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTrackLock_Label(), this.getLabel(), null, "label",
				null, 0, 1, TrackLock.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(trackLockComponentEClass, TrackLockComponent.class,
				"TrackLockComponent", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTrackLockComponent_TrackLockSignal(),
				ecorePackage.getEString(), "trackLockSignal", null, 0, 1,
				TrackLockComponent.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEAttribute(getTrackLockComponent_EjectionDirection(),
				this.getLeftRight(), "ejectionDirection", null, 0, 1,
				TrackLockComponent.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);

		initEClass(objectManagementEClass, ObjectManagement.class,
				"ObjectManagement", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getObjectManagement_PlanningObjectIDs(),
				ecorePackage.getEString(), "planningObjectIDs", null, 0, -1,
				ObjectManagement.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEAttribute(getObjectManagement_PlanningGroupID(),
				ecorePackage.getEString(), "planningGroupID", null, 0, 1,
				ObjectManagement.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);

		initEClass(trackCloseEClass, TrackClose.class, "TrackClose",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTrackClose_TrackCloseType(), this.getTrackCloseType(),
				"trackCloseType", null, 0, 1, TrackClose.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		initEClass(externalElementControlEClass, ExternalElementControl.class,
				"ExternalElementControl", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getExternalElementControl_ControlArt(),
				this.getExternalElementControlArt(), "controlArt", null, 0, 1,
				ExternalElementControl.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEAttribute(getExternalElementControl_ElementType(),
				this.getExternalElementControlArt(), "elementType", null, 0, 1,
				ExternalElementControl.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEAttribute(getExternalElementControl_ControlStation(),
				this.getControlStationType(), "controlStation", null, 0, 1,
				ExternalElementControl.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEReference(getExternalElementControl_Label(), this.getLabel(), null,
				"label", null, 0, 1, ExternalElementControl.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);

		initEClass(lockKeyEClass, LockKey.class, "LockKey", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getLockKey_Label(), this.getLabel(), null, "label", null,
				0, 1, LockKey.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getLockKey_Type(), this.getLockKeyType(), "type", null,
				0, 1, LockKey.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getLockKey_Locked(), ecorePackage.getEBoolean(),
				"locked", null, 0, 1, LockKey.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		initEClass(layoutinfoEClass, Layoutinfo.class, "Layoutinfo",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getLayoutinfo_Label(), ecorePackage.getEString(),
				"label", null, 0, 1, Layoutinfo.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEReference(getLayoutinfo_SheetsCut(), this.getSheetCut(), null,
				"sheetsCut", null, 0, -1, Layoutinfo.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(sheetCutEClass, SheetCut.class, "SheetCut", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getSheetCut_Label(), ecorePackage.getEString(), "label",
				null, 0, 1, SheetCut.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEReference(getSheetCut_PolygonDirection(), this.getCoordinate(),
				null, "polygonDirection", null, 0, 2, SheetCut.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEReference(getSheetCut_Polygon(), this.getCoordinate(), null,
				"polygon", null, 0, -1, SheetCut.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(cantEClass, Cant.class, "Cant", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCant_PointA(), this.getCantPoint(), null, "pointA",
				null, 0, 1, Cant.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCant_PointB(), this.getCantPoint(), null, "pointB",
				null, 0, 1, Cant.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCant_Form(), ecorePackage.getEString(), "form", null,
				0, 1, Cant.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCant_Length(), ecorePackage.getEDouble(), "length",
				null, 0, 1, Cant.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);

		initEClass(cantPointEClass, CantPoint.class, "CantPoint", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getCantPoint_Height(), ecorePackage.getEDouble(),
				"height", null, 0, 1, CantPoint.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		initEClass(unknownPositionedObjectEClass, UnknownPositionedObject.class,
				"UnknownPositionedObject", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getUnknownPositionedObject_ObjectType(),
				ecorePackage.getEString(), "objectType", null, 0, 1,
				UnknownPositionedObject.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(signalMountTypeEEnum, SignalMountType.class,
				"SignalMountType");
		addEEnumLiteral(signalMountTypeEEnum, SignalMountType.MAST);
		addEEnumLiteral(signalMountTypeEEnum, SignalMountType.MEHRERE_MASTEN);
		addEEnumLiteral(signalMountTypeEEnum, SignalMountType.PFOSTEN);
		addEEnumLiteral(signalMountTypeEEnum, SignalMountType.SCHIENENFUSS);
		addEEnumLiteral(signalMountTypeEEnum, SignalMountType.GLEISABSCHLUSS);
		addEEnumLiteral(signalMountTypeEEnum, SignalMountType.MAST_NIEDRIG);
		addEEnumLiteral(signalMountTypeEEnum, SignalMountType.PFOSTEN_NIEDRIG);
		addEEnumLiteral(signalMountTypeEEnum,
				SignalMountType.DECKENKONSTRUKTION);
		addEEnumLiteral(signalMountTypeEEnum, SignalMountType.WANDKONSTRUKTION);
		addEEnumLiteral(signalMountTypeEEnum, SignalMountType.SIGNALAUSLEGER);
		addEEnumLiteral(signalMountTypeEEnum, SignalMountType.SIGNALBRUECKE);
		addEEnumLiteral(signalMountTypeEEnum,
				SignalMountType.SONDERKONSTRUKTION);

		initEEnum(signalRoleEEnum, SignalRole.class, "SignalRole");
		addEEnumLiteral(signalRoleEEnum, SignalRole.MULTI_SECTION);
		addEEnumLiteral(signalRoleEEnum, SignalRole.MAIN);
		addEEnumLiteral(signalRoleEEnum, SignalRole.PRE);
		addEEnumLiteral(signalRoleEEnum, SignalRole.NONE);
		addEEnumLiteral(signalRoleEEnum, SignalRole.TRAIN_COVER);
		addEEnumLiteral(signalRoleEEnum, SignalRole.LOCK);
		addEEnumLiteral(signalRoleEEnum, SignalRole.SONSTIGE);

		initEEnum(signalSystemEEnum, SignalSystem.class, "SignalSystem");
		addEEnumLiteral(signalSystemEEnum, SignalSystem.HL);
		addEEnumLiteral(signalSystemEEnum, SignalSystem.HV);
		addEEnumLiteral(signalSystemEEnum, SignalSystem.KS);
		addEEnumLiteral(signalSystemEEnum, SignalSystem.SV);
		addEEnumLiteral(signalSystemEEnum, SignalSystem.NONE);

		initEEnum(mountDirectionEEnum, MountDirection.class, "MountDirection");
		addEEnumLiteral(mountDirectionEEnum, MountDirection.NONE);
		addEEnumLiteral(mountDirectionEEnum, MountDirection.UP);
		addEEnumLiteral(mountDirectionEEnum, MountDirection.DOWN);

		initEEnum(turnoutOperatingModeEEnum, TurnoutOperatingMode.class,
				"TurnoutOperatingMode");
		addEEnumLiteral(turnoutOperatingModeEEnum,
				TurnoutOperatingMode.UNDEFINED);
		addEEnumLiteral(turnoutOperatingModeEEnum,
				TurnoutOperatingMode.ELECTRIC_REMOTE);
		addEEnumLiteral(turnoutOperatingModeEEnum,
				TurnoutOperatingMode.ELECTRIC_LOCAL);
		addEEnumLiteral(turnoutOperatingModeEEnum,
				TurnoutOperatingMode.MECHANICAL_REMOTE);
		addEEnumLiteral(turnoutOperatingModeEEnum,
				TurnoutOperatingMode.MECHANICAL_LOCAL);
		addEEnumLiteral(turnoutOperatingModeEEnum,
				TurnoutOperatingMode.NON_OPERATIONAL);
		addEEnumLiteral(turnoutOperatingModeEEnum,
				TurnoutOperatingMode.TRAILABLE);
		addEEnumLiteral(turnoutOperatingModeEEnum, TurnoutOperatingMode.OTHER);
		addEEnumLiteral(turnoutOperatingModeEEnum,
				TurnoutOperatingMode.DEAD_LEFT);
		addEEnumLiteral(turnoutOperatingModeEEnum,
				TurnoutOperatingMode.DEAD_RIGHT);

		initEEnum(trackShapeEEnum, TrackShape.class, "TrackShape");
		addEEnumLiteral(trackShapeEEnum, TrackShape.STRAIGHT);
		addEEnumLiteral(trackShapeEEnum, TrackShape.CURVE);
		addEEnumLiteral(trackShapeEEnum, TrackShape.CLOTHOID);
		addEEnumLiteral(trackShapeEEnum, TrackShape.BLOSSCURVE);
		addEEnumLiteral(trackShapeEEnum, TrackShape.BLOSS_CURVED_SIMPLE);
		addEEnumLiteral(trackShapeEEnum, TrackShape.OTHER);
		addEEnumLiteral(trackShapeEEnum,
				TrackShape.DIRECTIONAL_STRAIGHT_KINK_END);
		addEEnumLiteral(trackShapeEEnum, TrackShape.KM_JUMP);
		addEEnumLiteral(trackShapeEEnum, TrackShape.TRANSITION_CURVE_SFORM);
		addEEnumLiteral(trackShapeEEnum, TrackShape.SFORM_SIMPLE_CURVED);
		addEEnumLiteral(trackShapeEEnum, TrackShape.UNKNOWN);

		initEEnum(trackTypeEEnum, TrackType.class, "TrackType");
		addEEnumLiteral(trackTypeEEnum, TrackType.NONE);
		addEEnumLiteral(trackTypeEEnum, TrackType.MAIN_TRACK);
		addEEnumLiteral(trackTypeEEnum, TrackType.SIDE_TRACK);
		addEEnumLiteral(trackTypeEEnum, TrackType.CONNECTING_TRACK);
		addEEnumLiteral(trackTypeEEnum, TrackType.ROUTE_TRACK);
		addEEnumLiteral(trackTypeEEnum, TrackType.OTHER);
		addEEnumLiteral(trackTypeEEnum, TrackType.PASSING_MAIN_TRACK);

		initEEnum(fmaComponentTypeEEnum, FMAComponentType.class,
				"FMAComponentType");
		addEEnumLiteral(fmaComponentTypeEEnum, FMAComponentType.NONE);
		addEEnumLiteral(fmaComponentTypeEEnum, FMAComponentType.AXLE);
		addEEnumLiteral(fmaComponentTypeEEnum, FMAComponentType.NFDC_CIRCUIT);
		addEEnumLiteral(fmaComponentTypeEEnum, FMAComponentType.TFDC_CIRCUIT);

		initEEnum(pzbTypeEEnum, PZBType.class, "PZBType");
		addEEnumLiteral(pzbTypeEEnum, PZBType.GM);
		addEEnumLiteral(pzbTypeEEnum, PZBType.GUE_GSA);
		addEEnumLiteral(pzbTypeEEnum, PZBType.GUE_GSE);

		initEEnum(pzbElementEEnum, PZBElement.class, "PZBElement");
		addEEnumLiteral(pzbElementEEnum, PZBElement.NONE);
		addEEnumLiteral(pzbElementEEnum, PZBElement.F500_HZ);
		addEEnumLiteral(pzbElementEEnum, PZBElement.F1000_HZ);
		addEEnumLiteral(pzbElementEEnum, PZBElement.F2000_HZ);
		addEEnumLiteral(pzbElementEEnum, PZBElement.F1000_HZ2000_HZ);

		initEEnum(pzbEffectivityEEnum, PZBEffectivity.class, "PZBEffectivity");
		addEEnumLiteral(pzbEffectivityEEnum, PZBEffectivity.NONE);
		addEEnumLiteral(pzbEffectivityEEnum, PZBEffectivity.SIGNAL);
		addEEnumLiteral(pzbEffectivityEEnum, PZBEffectivity.ROUTE);
		addEEnumLiteral(pzbEffectivityEEnum, PZBEffectivity.ALWAYS);

		initEEnum(trackLockLocationEEnum, TrackLockLocation.class,
				"TrackLockLocation");
		addEEnumLiteral(trackLockLocationEEnum, TrackLockLocation.BESIDE_TRACK);
		addEEnumLiteral(trackLockLocationEEnum, TrackLockLocation.ON_TRACK);

		initEEnum(leftRightEEnum, LeftRight.class, "LeftRight");
		addEEnumLiteral(leftRightEEnum, LeftRight.LEFT);
		addEEnumLiteral(leftRightEEnum, LeftRight.RIGHT);

		initEEnum(directionEEnum, Direction.class, "Direction");
		addEEnumLiteral(directionEEnum, Direction.FORWARD);
		addEEnumLiteral(directionEEnum, Direction.OPPOSITE);

		initEEnum(trackCloseTypeEEnum, TrackCloseType.class, "TrackCloseType");
		addEEnumLiteral(trackCloseTypeEEnum,
				TrackCloseType.FRICTION_BUFFER_STOP);
		addEEnumLiteral(trackCloseTypeEEnum, TrackCloseType.FIXED_BUFFER_STOP);
		addEEnumLiteral(trackCloseTypeEEnum, TrackCloseType.HEAD_RAMP);
		addEEnumLiteral(trackCloseTypeEEnum, TrackCloseType.THRESHOLD_CROSS);
		addEEnumLiteral(trackCloseTypeEEnum, TrackCloseType.TURN_TABLE);
		addEEnumLiteral(trackCloseTypeEEnum, TrackCloseType.SLIDING_STAGE);
		addEEnumLiteral(trackCloseTypeEEnum, TrackCloseType.FERRY_DOCK);
		addEEnumLiteral(trackCloseTypeEEnum,
				TrackCloseType.INFRASTRUCTURE_BORDER);
		addEEnumLiteral(trackCloseTypeEEnum, TrackCloseType.OTHER);

		initEEnum(externalElementControlArtEEnum,
				ExternalElementControlArt.class, "ExternalElementControlArt");
		addEEnumLiteral(externalElementControlArtEEnum,
				ExternalElementControlArt.FE_AK);
		addEEnumLiteral(externalElementControlArtEEnum,
				ExternalElementControlArt.GFK);
		addEEnumLiteral(externalElementControlArtEEnum,
				ExternalElementControlArt.GLEISFREIMELDE_INNENANLAGE);
		addEEnumLiteral(externalElementControlArtEEnum,
				ExternalElementControlArt.ESTW_A);
		addEEnumLiteral(externalElementControlArtEEnum,
				ExternalElementControlArt.OBJEKTCONTROLLER);
		addEEnumLiteral(externalElementControlArtEEnum,
				ExternalElementControlArt.RELAISSTELLWERK);
		addEEnumLiteral(externalElementControlArtEEnum,
				ExternalElementControlArt.SONSTIGE);
		addEEnumLiteral(externalElementControlArtEEnum,
				ExternalElementControlArt.VIRTUELLE_AUSSENELEMENTANSTEUERUNG);

		initEEnum(controlStationTypeEEnum, ControlStationType.class,
				"ControlStationType");
		addEEnumLiteral(controlStationTypeEEnum,
				ControlStationType.DEFAULT_CONTROL);
		addEEnumLiteral(controlStationTypeEEnum,
				ControlStationType.EMERGENCY_CONTROL);
		addEEnumLiteral(controlStationTypeEEnum,
				ControlStationType.EMERGENCY_CONTROL_DISPOSE);
		addEEnumLiteral(controlStationTypeEEnum,
				ControlStationType.DEFAULT_CONTROL_DISPOSE);
		addEEnumLiteral(controlStationTypeEEnum, ControlStationType.OTHER);
		addEEnumLiteral(controlStationTypeEEnum,
				ControlStationType.WITHOUT_CONTROL);
		addEEnumLiteral(controlStationTypeEEnum,
				ControlStationType.ONLY_REMOTE_CONTROL);

		initEEnum(lockKeyTypeEEnum, LockKeyType.class, "LockKeyType");
		addEEnumLiteral(lockKeyTypeEEnum, LockKeyType.INSIDE);
		addEEnumLiteral(lockKeyTypeEEnum, LockKeyType.OUTSIDE);

		initEEnum(switchTypeEEnum, SwitchType.class, "SwitchType");
		addEEnumLiteral(switchTypeEEnum, SwitchType.SIMPLE);
		addEEnumLiteral(switchTypeEEnum, SwitchType.CROSS_SWITCH);
		addEEnumLiteral(switchTypeEEnum, SwitchType.DOPPLE_CROSS_SWITCH);
		addEEnumLiteral(switchTypeEEnum, SwitchType.SIMPLE_CROSS);
		addEEnumLiteral(switchTypeEEnum, SwitchType.FLAT_CROSS);
		addEEnumLiteral(switchTypeEEnum, SwitchType.OTHER);

		// Create resource
		createResource(eNS_URI);
	}

} // SiteplanPackageImpl
