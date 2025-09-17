/**
 * Copyright (c) 2021 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.siteplan.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

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
import org.eclipse.set.model.siteplan.PZB;
import org.eclipse.set.model.siteplan.PZBEffectivity;
import org.eclipse.set.model.siteplan.PZBElement;
import org.eclipse.set.model.siteplan.PZBGU;
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
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!--
 * end-user-doc -->
 * 
 * @generated
 */
public class SiteplanFactoryImpl extends EFactoryImpl
		implements SiteplanFactory {
	/**
	 * Creates the default factory implementation. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	public static SiteplanFactory init() {
		try {
			SiteplanFactory theSiteplanFactory = (SiteplanFactory) EPackage.Registry.INSTANCE
					.getEFactory(SiteplanPackage.eNS_URI);
			if (theSiteplanFactory != null) {
				return theSiteplanFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new SiteplanFactoryImpl();
	}

	/**
	 * Creates an instance of the factory. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	public SiteplanFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case SiteplanPackage.SITEPLAN:
				return createSiteplan();
			case SiteplanPackage.SITEPLAN_STATE:
				return createSiteplanState();
			case SiteplanPackage.SITEPLAN_OBJECT:
				return createSiteplanObject();
			case SiteplanPackage.POSITIONED_OBJECT:
				return createPositionedObject();
			case SiteplanPackage.COORDINATE:
				return createCoordinate();
			case SiteplanPackage.POSITION:
				return createPosition();
			case SiteplanPackage.ROUTE_OBJECT:
				return createRouteObject();
			case SiteplanPackage.ROUTE_LOCATION:
				return createRouteLocation();
			case SiteplanPackage.SIGNAL_MOUNT:
				return createSignalMount();
			case SiteplanPackage.SIGNAL:
				return createSignal();
			case SiteplanPackage.SIGNAL_SCREEN:
				return createSignalScreen();
			case SiteplanPackage.LABEL:
				return createLabel();
			case SiteplanPackage.TRACK_SWITCH:
				return createTrackSwitch();
			case SiteplanPackage.TRACK_SWITCH_COMPONENT:
				return createTrackSwitchComponent();
			case SiteplanPackage.CONTINUOUS_TRACK_SEGMENT:
				return createContinuousTrackSegment();
			case SiteplanPackage.TRACK:
				return createTrack();
			case SiteplanPackage.TRACK_SECTION:
				return createTrackSection();
			case SiteplanPackage.TRACK_SEGMENT:
				return createTrackSegment();
			case SiteplanPackage.FMA_COMPONENT:
				return createFMAComponent();
			case SiteplanPackage.ROUTE:
				return createRoute();
			case SiteplanPackage.ROUTE_SECTION:
				return createRouteSection();
			case SiteplanPackage.KM_MARKER:
				return createKMMarker();
			case SiteplanPackage.TRACK_SWITCH_END_MARKER:
				return createTrackSwitchEndMarker();
			case SiteplanPackage.ERROR:
				return createError();
			case SiteplanPackage.PZB:
				return createPZB();
			case SiteplanPackage.PZBGU:
				return createPZBGU();
			case SiteplanPackage.TRACK_DESIGNATION:
				return createTrackDesignation();
			case SiteplanPackage.TRACK_SWITCH_LEG:
				return createTrackSwitchLeg();
			case SiteplanPackage.STATION:
				return createStation();
			case SiteplanPackage.PLATFORM:
				return createPlatform();
			case SiteplanPackage.TRACK_LOCK:
				return createTrackLock();
			case SiteplanPackage.TRACK_LOCK_COMPONENT:
				return createTrackLockComponent();
			case SiteplanPackage.OBJECT_MANAGEMENT:
				return createObjectManagement();
			case SiteplanPackage.TRACK_CLOSE:
				return createTrackClose();
			case SiteplanPackage.EXTERNAL_ELEMENT_CONTROL:
				return createExternalElementControl();
			case SiteplanPackage.LOCK_KEY:
				return createLockKey();
			case SiteplanPackage.LAYOUTINFO:
				return createLayoutinfo();
			case SiteplanPackage.SHEET_CUT:
				return createSheetCut();
			case SiteplanPackage.CANT:
				return createCant();
			case SiteplanPackage.CANT_POINT:
				return createCantPoint();
			case SiteplanPackage.UNKNOWN_POSITIONED_OBJECT:
				return createUnknownPositionedObject();
			default:
				throw new IllegalArgumentException("The class '"
						+ eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case SiteplanPackage.SIGNAL_MOUNT_TYPE:
				return createSignalMountTypeFromString(eDataType, initialValue);
			case SiteplanPackage.SIGNAL_ROLE:
				return createSignalRoleFromString(eDataType, initialValue);
			case SiteplanPackage.SIGNAL_SYSTEM:
				return createSignalSystemFromString(eDataType, initialValue);
			case SiteplanPackage.MOUNT_DIRECTION:
				return createMountDirectionFromString(eDataType, initialValue);
			case SiteplanPackage.TURNOUT_OPERATING_MODE:
				return createTurnoutOperatingModeFromString(eDataType,
						initialValue);
			case SiteplanPackage.TRACK_SHAPE:
				return createTrackShapeFromString(eDataType, initialValue);
			case SiteplanPackage.TRACK_TYPE:
				return createTrackTypeFromString(eDataType, initialValue);
			case SiteplanPackage.FMA_COMPONENT_TYPE:
				return createFMAComponentTypeFromString(eDataType,
						initialValue);
			case SiteplanPackage.PZB_TYPE:
				return createPZBTypeFromString(eDataType, initialValue);
			case SiteplanPackage.PZB_ELEMENT:
				return createPZBElementFromString(eDataType, initialValue);
			case SiteplanPackage.PZB_EFFECTIVITY:
				return createPZBEffectivityFromString(eDataType, initialValue);
			case SiteplanPackage.TRACK_LOCK_LOCATION:
				return createTrackLockLocationFromString(eDataType,
						initialValue);
			case SiteplanPackage.LEFT_RIGHT:
				return createLeftRightFromString(eDataType, initialValue);
			case SiteplanPackage.DIRECTION:
				return createDirectionFromString(eDataType, initialValue);
			case SiteplanPackage.TRACK_CLOSE_TYPE:
				return createTrackCloseTypeFromString(eDataType, initialValue);
			case SiteplanPackage.EXTERNAL_ELEMENT_CONTROL_ART:
				return createExternalElementControlArtFromString(eDataType,
						initialValue);
			case SiteplanPackage.CONTROL_STATION_TYPE:
				return createControlStationTypeFromString(eDataType,
						initialValue);
			case SiteplanPackage.LOCK_KEY_TYPE:
				return createLockKeyTypeFromString(eDataType, initialValue);
			case SiteplanPackage.SWITCH_TYPE:
				return createSwitchTypeFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '"
						+ eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case SiteplanPackage.SIGNAL_MOUNT_TYPE:
				return convertSignalMountTypeToString(eDataType, instanceValue);
			case SiteplanPackage.SIGNAL_ROLE:
				return convertSignalRoleToString(eDataType, instanceValue);
			case SiteplanPackage.SIGNAL_SYSTEM:
				return convertSignalSystemToString(eDataType, instanceValue);
			case SiteplanPackage.MOUNT_DIRECTION:
				return convertMountDirectionToString(eDataType, instanceValue);
			case SiteplanPackage.TURNOUT_OPERATING_MODE:
				return convertTurnoutOperatingModeToString(eDataType,
						instanceValue);
			case SiteplanPackage.TRACK_SHAPE:
				return convertTrackShapeToString(eDataType, instanceValue);
			case SiteplanPackage.TRACK_TYPE:
				return convertTrackTypeToString(eDataType, instanceValue);
			case SiteplanPackage.FMA_COMPONENT_TYPE:
				return convertFMAComponentTypeToString(eDataType,
						instanceValue);
			case SiteplanPackage.PZB_TYPE:
				return convertPZBTypeToString(eDataType, instanceValue);
			case SiteplanPackage.PZB_ELEMENT:
				return convertPZBElementToString(eDataType, instanceValue);
			case SiteplanPackage.PZB_EFFECTIVITY:
				return convertPZBEffectivityToString(eDataType, instanceValue);
			case SiteplanPackage.TRACK_LOCK_LOCATION:
				return convertTrackLockLocationToString(eDataType,
						instanceValue);
			case SiteplanPackage.LEFT_RIGHT:
				return convertLeftRightToString(eDataType, instanceValue);
			case SiteplanPackage.DIRECTION:
				return convertDirectionToString(eDataType, instanceValue);
			case SiteplanPackage.TRACK_CLOSE_TYPE:
				return convertTrackCloseTypeToString(eDataType, instanceValue);
			case SiteplanPackage.EXTERNAL_ELEMENT_CONTROL_ART:
				return convertExternalElementControlArtToString(eDataType,
						instanceValue);
			case SiteplanPackage.CONTROL_STATION_TYPE:
				return convertControlStationTypeToString(eDataType,
						instanceValue);
			case SiteplanPackage.LOCK_KEY_TYPE:
				return convertLockKeyTypeToString(eDataType, instanceValue);
			case SiteplanPackage.SWITCH_TYPE:
				return convertSwitchTypeToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '"
						+ eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Siteplan createSiteplan() {
		SiteplanImpl siteplan = new SiteplanImpl();
		return siteplan;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public SiteplanState createSiteplanState() {
		SiteplanStateImpl siteplanState = new SiteplanStateImpl();
		return siteplanState;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public SiteplanObject createSiteplanObject() {
		SiteplanObjectImpl siteplanObject = new SiteplanObjectImpl();
		return siteplanObject;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public PositionedObject createPositionedObject() {
		PositionedObjectImpl positionedObject = new PositionedObjectImpl();
		return positionedObject;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Coordinate createCoordinate() {
		CoordinateImpl coordinate = new CoordinateImpl();
		return coordinate;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Position createPosition() {
		PositionImpl position = new PositionImpl();
		return position;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public RouteObject createRouteObject() {
		RouteObjectImpl routeObject = new RouteObjectImpl();
		return routeObject;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public RouteLocation createRouteLocation() {
		RouteLocationImpl routeLocation = new RouteLocationImpl();
		return routeLocation;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public SignalMount createSignalMount() {
		SignalMountImpl signalMount = new SignalMountImpl();
		return signalMount;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Signal createSignal() {
		SignalImpl signal = new SignalImpl();
		return signal;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public SignalScreen createSignalScreen() {
		SignalScreenImpl signalScreen = new SignalScreenImpl();
		return signalScreen;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Label createLabel() {
		LabelImpl label = new LabelImpl();
		return label;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public TrackSwitch createTrackSwitch() {
		TrackSwitchImpl trackSwitch = new TrackSwitchImpl();
		return trackSwitch;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public TrackSwitchComponent createTrackSwitchComponent() {
		TrackSwitchComponentImpl trackSwitchComponent = new TrackSwitchComponentImpl();
		return trackSwitchComponent;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public ContinuousTrackSegment createContinuousTrackSegment() {
		ContinuousTrackSegmentImpl continuousTrackSegment = new ContinuousTrackSegmentImpl();
		return continuousTrackSegment;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Track createTrack() {
		TrackImpl track = new TrackImpl();
		return track;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public TrackSection createTrackSection() {
		TrackSectionImpl trackSection = new TrackSectionImpl();
		return trackSection;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public TrackSegment createTrackSegment() {
		TrackSegmentImpl trackSegment = new TrackSegmentImpl();
		return trackSegment;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public FMAComponent createFMAComponent() {
		FMAComponentImpl fmaComponent = new FMAComponentImpl();
		return fmaComponent;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Route createRoute() {
		RouteImpl route = new RouteImpl();
		return route;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public RouteSection createRouteSection() {
		RouteSectionImpl routeSection = new RouteSectionImpl();
		return routeSection;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public KMMarker createKMMarker() {
		KMMarkerImpl kmMarker = new KMMarkerImpl();
		return kmMarker;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public TrackSwitchEndMarker createTrackSwitchEndMarker() {
		TrackSwitchEndMarkerImpl trackSwitchEndMarker = new TrackSwitchEndMarkerImpl();
		return trackSwitchEndMarker;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public org.eclipse.set.model.siteplan.Error createError() {
		ErrorImpl error = new ErrorImpl();
		return error;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public PZB createPZB() {
		PZBImpl pzb = new PZBImpl();
		return pzb;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public PZBGU createPZBGU() {
		PZBGUImpl pzbgu = new PZBGUImpl();
		return pzbgu;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public TrackDesignation createTrackDesignation() {
		TrackDesignationImpl trackDesignation = new TrackDesignationImpl();
		return trackDesignation;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public TrackSwitchLeg createTrackSwitchLeg() {
		TrackSwitchLegImpl trackSwitchLeg = new TrackSwitchLegImpl();
		return trackSwitchLeg;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Station createStation() {
		StationImpl station = new StationImpl();
		return station;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Platform createPlatform() {
		PlatformImpl platform = new PlatformImpl();
		return platform;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public TrackLock createTrackLock() {
		TrackLockImpl trackLock = new TrackLockImpl();
		return trackLock;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public TrackLockComponent createTrackLockComponent() {
		TrackLockComponentImpl trackLockComponent = new TrackLockComponentImpl();
		return trackLockComponent;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public ObjectManagement createObjectManagement() {
		ObjectManagementImpl objectManagement = new ObjectManagementImpl();
		return objectManagement;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public TrackClose createTrackClose() {
		TrackCloseImpl trackClose = new TrackCloseImpl();
		return trackClose;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public ExternalElementControl createExternalElementControl() {
		ExternalElementControlImpl externalElementControl = new ExternalElementControlImpl();
		return externalElementControl;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public LockKey createLockKey() {
		LockKeyImpl lockKey = new LockKeyImpl();
		return lockKey;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Layoutinfo createLayoutinfo() {
		LayoutinfoImpl layoutinfo = new LayoutinfoImpl();
		return layoutinfo;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public SheetCut createSheetCut() {
		SheetCutImpl sheetCut = new SheetCutImpl();
		return sheetCut;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Cant createCant() {
		CantImpl cant = new CantImpl();
		return cant;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public CantPoint createCantPoint() {
		CantPointImpl cantPoint = new CantPointImpl();
		return cantPoint;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public UnknownPositionedObject createUnknownPositionedObject() {
		UnknownPositionedObjectImpl unknownPositionedObject = new UnknownPositionedObjectImpl();
		return unknownPositionedObject;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public SignalMountType createSignalMountTypeFromString(EDataType eDataType,
			String initialValue) {
		SignalMountType result = SignalMountType.get(initialValue);
		if (result == null)
			throw new IllegalArgumentException("The value '" + initialValue
					+ "' is not a valid enumerator of '" + eDataType.getName()
					+ "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String convertSignalMountTypeToString(EDataType eDataType,
			Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public SignalRole createSignalRoleFromString(EDataType eDataType,
			String initialValue) {
		SignalRole result = SignalRole.get(initialValue);
		if (result == null)
			throw new IllegalArgumentException("The value '" + initialValue
					+ "' is not a valid enumerator of '" + eDataType.getName()
					+ "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String convertSignalRoleToString(EDataType eDataType,
			Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public SignalSystem createSignalSystemFromString(EDataType eDataType,
			String initialValue) {
		SignalSystem result = SignalSystem.get(initialValue);
		if (result == null)
			throw new IllegalArgumentException("The value '" + initialValue
					+ "' is not a valid enumerator of '" + eDataType.getName()
					+ "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String convertSignalSystemToString(EDataType eDataType,
			Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public MountDirection createMountDirectionFromString(EDataType eDataType,
			String initialValue) {
		MountDirection result = MountDirection.get(initialValue);
		if (result == null)
			throw new IllegalArgumentException("The value '" + initialValue
					+ "' is not a valid enumerator of '" + eDataType.getName()
					+ "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String convertMountDirectionToString(EDataType eDataType,
			Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public TurnoutOperatingMode createTurnoutOperatingModeFromString(
			EDataType eDataType, String initialValue) {
		TurnoutOperatingMode result = TurnoutOperatingMode.get(initialValue);
		if (result == null)
			throw new IllegalArgumentException("The value '" + initialValue
					+ "' is not a valid enumerator of '" + eDataType.getName()
					+ "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String convertTurnoutOperatingModeToString(EDataType eDataType,
			Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public TrackShape createTrackShapeFromString(EDataType eDataType,
			String initialValue) {
		TrackShape result = TrackShape.get(initialValue);
		if (result == null)
			throw new IllegalArgumentException("The value '" + initialValue
					+ "' is not a valid enumerator of '" + eDataType.getName()
					+ "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String convertTrackShapeToString(EDataType eDataType,
			Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public TrackType createTrackTypeFromString(EDataType eDataType,
			String initialValue) {
		TrackType result = TrackType.get(initialValue);
		if (result == null)
			throw new IllegalArgumentException("The value '" + initialValue
					+ "' is not a valid enumerator of '" + eDataType.getName()
					+ "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String convertTrackTypeToString(EDataType eDataType,
			Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public FMAComponentType createFMAComponentTypeFromString(
			EDataType eDataType, String initialValue) {
		FMAComponentType result = FMAComponentType.get(initialValue);
		if (result == null)
			throw new IllegalArgumentException("The value '" + initialValue
					+ "' is not a valid enumerator of '" + eDataType.getName()
					+ "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String convertFMAComponentTypeToString(EDataType eDataType,
			Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public PZBType createPZBTypeFromString(EDataType eDataType,
			String initialValue) {
		PZBType result = PZBType.get(initialValue);
		if (result == null)
			throw new IllegalArgumentException("The value '" + initialValue
					+ "' is not a valid enumerator of '" + eDataType.getName()
					+ "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String convertPZBTypeToString(EDataType eDataType,
			Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public PZBElement createPZBElementFromString(EDataType eDataType,
			String initialValue) {
		PZBElement result = PZBElement.get(initialValue);
		if (result == null)
			throw new IllegalArgumentException("The value '" + initialValue
					+ "' is not a valid enumerator of '" + eDataType.getName()
					+ "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String convertPZBElementToString(EDataType eDataType,
			Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public PZBEffectivity createPZBEffectivityFromString(EDataType eDataType,
			String initialValue) {
		PZBEffectivity result = PZBEffectivity.get(initialValue);
		if (result == null)
			throw new IllegalArgumentException("The value '" + initialValue
					+ "' is not a valid enumerator of '" + eDataType.getName()
					+ "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String convertPZBEffectivityToString(EDataType eDataType,
			Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public TrackLockLocation createTrackLockLocationFromString(
			EDataType eDataType, String initialValue) {
		TrackLockLocation result = TrackLockLocation.get(initialValue);
		if (result == null)
			throw new IllegalArgumentException("The value '" + initialValue
					+ "' is not a valid enumerator of '" + eDataType.getName()
					+ "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String convertTrackLockLocationToString(EDataType eDataType,
			Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public LeftRight createLeftRightFromString(EDataType eDataType,
			String initialValue) {
		LeftRight result = LeftRight.get(initialValue);
		if (result == null)
			throw new IllegalArgumentException("The value '" + initialValue
					+ "' is not a valid enumerator of '" + eDataType.getName()
					+ "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String convertLeftRightToString(EDataType eDataType,
			Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Direction createDirectionFromString(EDataType eDataType,
			String initialValue) {
		Direction result = Direction.get(initialValue);
		if (result == null)
			throw new IllegalArgumentException("The value '" + initialValue
					+ "' is not a valid enumerator of '" + eDataType.getName()
					+ "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String convertDirectionToString(EDataType eDataType,
			Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public TrackCloseType createTrackCloseTypeFromString(EDataType eDataType,
			String initialValue) {
		TrackCloseType result = TrackCloseType.get(initialValue);
		if (result == null)
			throw new IllegalArgumentException("The value '" + initialValue
					+ "' is not a valid enumerator of '" + eDataType.getName()
					+ "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String convertTrackCloseTypeToString(EDataType eDataType,
			Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ExternalElementControlArt createExternalElementControlArtFromString(
			EDataType eDataType, String initialValue) {
		ExternalElementControlArt result = ExternalElementControlArt
				.get(initialValue);
		if (result == null)
			throw new IllegalArgumentException("The value '" + initialValue
					+ "' is not a valid enumerator of '" + eDataType.getName()
					+ "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String convertExternalElementControlArtToString(EDataType eDataType,
			Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ControlStationType createControlStationTypeFromString(
			EDataType eDataType, String initialValue) {
		ControlStationType result = ControlStationType.get(initialValue);
		if (result == null)
			throw new IllegalArgumentException("The value '" + initialValue
					+ "' is not a valid enumerator of '" + eDataType.getName()
					+ "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String convertControlStationTypeToString(EDataType eDataType,
			Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public LockKeyType createLockKeyTypeFromString(EDataType eDataType,
			String initialValue) {
		LockKeyType result = LockKeyType.get(initialValue);
		if (result == null)
			throw new IllegalArgumentException("The value '" + initialValue
					+ "' is not a valid enumerator of '" + eDataType.getName()
					+ "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String convertLockKeyTypeToString(EDataType eDataType,
			Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public SwitchType createSwitchTypeFromString(EDataType eDataType,
			String initialValue) {
		SwitchType result = SwitchType.get(initialValue);
		if (result == null)
			throw new IllegalArgumentException("The value '" + initialValue
					+ "' is not a valid enumerator of '" + eDataType.getName()
					+ "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String convertSwitchTypeToString(EDataType eDataType,
			Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public SiteplanPackage getSiteplanPackage() {
		return (SiteplanPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static SiteplanPackage getPackage() {
		return SiteplanPackage.eINSTANCE;
	}

} // SiteplanFactoryImpl
