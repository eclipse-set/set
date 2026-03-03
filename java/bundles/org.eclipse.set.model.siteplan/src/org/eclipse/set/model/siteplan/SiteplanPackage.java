/**
 * Copyright (c) 2021 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.siteplan;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc --> The <b>Package</b> for the model. It contains
 * accessors for the meta objects to represent
 * <ul>
 * <li>each class,</li>
 * <li>each feature of each class,</li>
 * <li>each operation of each class,</li>
 * <li>each enum,</li>
 * <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * 
 * @see org.eclipse.set.model.siteplan.SiteplanFactory
 * @model kind="package"
 * @generated
 */
public interface SiteplanPackage extends EPackage {
	/**
	 * The package name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNAME = "siteplan";

	/**
	 * The package namespace URI. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_URI = "http://www.example.org/siteplan";

	/**
	 * The package namespace name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_PREFIX = "siteplan";

	/**
	 * The singleton instance of the package. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	SiteplanPackage eINSTANCE = org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl
			.init();

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.set.model.siteplan.impl.SiteplanImpl
	 * <em>Siteplan</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.set.model.siteplan.impl.SiteplanImpl
	 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getSiteplan()
	 * @generated
	 */
	int SITEPLAN = 0;

	/**
	 * The feature id for the '<em><b>Initial State</b></em>' containment
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SITEPLAN__INITIAL_STATE = 0;

	/**
	 * The feature id for the '<em><b>Changed Initial State</b></em>'
	 * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SITEPLAN__CHANGED_INITIAL_STATE = 1;

	/**
	 * The feature id for the '<em><b>Common State</b></em>' containment
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SITEPLAN__COMMON_STATE = 2;

	/**
	 * The feature id for the '<em><b>Changed Final State</b></em>' containment
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SITEPLAN__CHANGED_FINAL_STATE = 3;

	/**
	 * The feature id for the '<em><b>Final State</b></em>' containment
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SITEPLAN__FINAL_STATE = 4;

	/**
	 * The feature id for the '<em><b>Center Position</b></em>' containment
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SITEPLAN__CENTER_POSITION = 5;

	/**
	 * The feature id for the '<em><b>Object Management</b></em>' containment
	 * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SITEPLAN__OBJECT_MANAGEMENT = 6;

	/**
	 * The feature id for the '<em><b>Layout Info</b></em>' containment
	 * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SITEPLAN__LAYOUT_INFO = 7;

	/**
	 * The number of structural features of the '<em>Siteplan</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SITEPLAN_FEATURE_COUNT = 8;

	/**
	 * The number of operations of the '<em>Siteplan</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SITEPLAN_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.set.model.siteplan.impl.SiteplanStateImpl
	 * <em>State</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.set.model.siteplan.impl.SiteplanStateImpl
	 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getSiteplanState()
	 * @generated
	 */
	int SITEPLAN_STATE = 1;

	/**
	 * The feature id for the '<em><b>Signals</b></em>' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SITEPLAN_STATE__SIGNALS = 0;

	/**
	 * The feature id for the '<em><b>Track Switches</b></em>' containment
	 * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SITEPLAN_STATE__TRACK_SWITCHES = 1;

	/**
	 * The feature id for the '<em><b>Track Switch End Markers</b></em>'
	 * containment reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SITEPLAN_STATE__TRACK_SWITCH_END_MARKERS = 2;

	/**
	 * The feature id for the '<em><b>Tracks</b></em>' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SITEPLAN_STATE__TRACKS = 3;

	/**
	 * The feature id for the '<em><b>Fma Components</b></em>' containment
	 * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SITEPLAN_STATE__FMA_COMPONENTS = 4;

	/**
	 * The feature id for the '<em><b>Pzb</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SITEPLAN_STATE__PZB = 5;

	/**
	 * The feature id for the '<em><b>Pzb GU</b></em>' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SITEPLAN_STATE__PZB_GU = 6;

	/**
	 * The feature id for the '<em><b>Routes</b></em>' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SITEPLAN_STATE__ROUTES = 7;

	/**
	 * The feature id for the '<em><b>Stations</b></em>' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SITEPLAN_STATE__STATIONS = 8;

	/**
	 * The feature id for the '<em><b>Track Lock</b></em>' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SITEPLAN_STATE__TRACK_LOCK = 9;

	/**
	 * The feature id for the '<em><b>Errors</b></em>' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SITEPLAN_STATE__ERRORS = 10;

	/**
	 * The feature id for the '<em><b>Track Closures</b></em>' containment
	 * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SITEPLAN_STATE__TRACK_CLOSURES = 11;

	/**
	 * The feature id for the '<em><b>External Element Controls</b></em>'
	 * containment reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SITEPLAN_STATE__EXTERNAL_ELEMENT_CONTROLS = 12;

	/**
	 * The feature id for the '<em><b>Lockkeys</b></em>' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SITEPLAN_STATE__LOCKKEYS = 13;

	/**
	 * The feature id for the '<em><b>Cants</b></em>' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SITEPLAN_STATE__CANTS = 14;

	/**
	 * The feature id for the '<em><b>Unknown Objects</b></em>' containment
	 * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SITEPLAN_STATE__UNKNOWN_OBJECTS = 15;

	/**
	 * The number of structural features of the '<em>State</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SITEPLAN_STATE_FEATURE_COUNT = 16;

	/**
	 * The number of operations of the '<em>State</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SITEPLAN_STATE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.set.model.siteplan.impl.SiteplanObjectImpl
	 * <em>Object</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.set.model.siteplan.impl.SiteplanObjectImpl
	 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getSiteplanObject()
	 * @generated
	 */
	int SITEPLAN_OBJECT = 2;

	/**
	 * The feature id for the '<em><b>Guid</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SITEPLAN_OBJECT__GUID = 0;

	/**
	 * The number of structural features of the '<em>Object</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SITEPLAN_OBJECT_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Object</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SITEPLAN_OBJECT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.set.model.siteplan.impl.PositionedObjectImpl
	 * <em>Positioned Object</em>}' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see org.eclipse.set.model.siteplan.impl.PositionedObjectImpl
	 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getPositionedObject()
	 * @generated
	 */
	int POSITIONED_OBJECT = 3;

	/**
	 * The feature id for the '<em><b>Guid</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int POSITIONED_OBJECT__GUID = SITEPLAN_OBJECT__GUID;

	/**
	 * The feature id for the '<em><b>Position</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int POSITIONED_OBJECT__POSITION = SITEPLAN_OBJECT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Positioned Object</em>'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int POSITIONED_OBJECT_FEATURE_COUNT = SITEPLAN_OBJECT_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Positioned Object</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int POSITIONED_OBJECT_OPERATION_COUNT = SITEPLAN_OBJECT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.set.model.siteplan.impl.CoordinateImpl
	 * <em>Coordinate</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see org.eclipse.set.model.siteplan.impl.CoordinateImpl
	 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getCoordinate()
	 * @generated
	 */
	int COORDINATE = 4;

	/**
	 * The feature id for the '<em><b>X</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int COORDINATE__X = 0;

	/**
	 * The feature id for the '<em><b>Y</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int COORDINATE__Y = 1;

	/**
	 * The number of structural features of the '<em>Coordinate</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int COORDINATE_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Coordinate</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int COORDINATE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.set.model.siteplan.impl.PositionImpl
	 * <em>Position</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.set.model.siteplan.impl.PositionImpl
	 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getPosition()
	 * @generated
	 */
	int POSITION = 5;

	/**
	 * The feature id for the '<em><b>X</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int POSITION__X = COORDINATE__X;

	/**
	 * The feature id for the '<em><b>Y</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int POSITION__Y = COORDINATE__Y;

	/**
	 * The feature id for the '<em><b>Rotation</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int POSITION__ROTATION = COORDINATE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Position</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int POSITION_FEATURE_COUNT = COORDINATE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Position</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int POSITION_OPERATION_COUNT = COORDINATE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.set.model.siteplan.impl.RouteObjectImpl <em>Route
	 * Object</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.set.model.siteplan.impl.RouteObjectImpl
	 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getRouteObject()
	 * @generated
	 */
	int ROUTE_OBJECT = 6;

	/**
	 * The feature id for the '<em><b>Guid</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ROUTE_OBJECT__GUID = SITEPLAN_OBJECT__GUID;

	/**
	 * The feature id for the '<em><b>Route Locations</b></em>' containment
	 * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ROUTE_OBJECT__ROUTE_LOCATIONS = SITEPLAN_OBJECT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Route Object</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ROUTE_OBJECT_FEATURE_COUNT = SITEPLAN_OBJECT_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Route Object</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ROUTE_OBJECT_OPERATION_COUNT = SITEPLAN_OBJECT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.set.model.siteplan.impl.RouteLocationImpl <em>Route
	 * Location</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.set.model.siteplan.impl.RouteLocationImpl
	 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getRouteLocation()
	 * @generated
	 */
	int ROUTE_LOCATION = 7;

	/**
	 * The feature id for the '<em><b>Km</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ROUTE_LOCATION__KM = 0;

	/**
	 * The feature id for the '<em><b>Route</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ROUTE_LOCATION__ROUTE = 1;

	/**
	 * The number of structural features of the '<em>Route Location</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ROUTE_LOCATION_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Route Location</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ROUTE_LOCATION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.set.model.siteplan.impl.SignalMountImpl <em>Signal
	 * Mount</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.set.model.siteplan.impl.SignalMountImpl
	 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getSignalMount()
	 * @generated
	 */
	int SIGNAL_MOUNT = 8;

	/**
	 * The feature id for the '<em><b>Guid</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SIGNAL_MOUNT__GUID = POSITIONED_OBJECT__GUID;

	/**
	 * The feature id for the '<em><b>Position</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SIGNAL_MOUNT__POSITION = POSITIONED_OBJECT__POSITION;

	/**
	 * The feature id for the '<em><b>Route Locations</b></em>' containment
	 * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SIGNAL_MOUNT__ROUTE_LOCATIONS = POSITIONED_OBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Attached Signals</b></em>' containment
	 * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SIGNAL_MOUNT__ATTACHED_SIGNALS = POSITIONED_OBJECT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Mount Type</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SIGNAL_MOUNT__MOUNT_TYPE = POSITIONED_OBJECT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Signal Mount</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SIGNAL_MOUNT_FEATURE_COUNT = POSITIONED_OBJECT_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>Signal Mount</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SIGNAL_MOUNT_OPERATION_COUNT = POSITIONED_OBJECT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.set.model.siteplan.impl.SignalImpl <em>Signal</em>}'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.set.model.siteplan.impl.SignalImpl
	 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getSignal()
	 * @generated
	 */
	int SIGNAL = 9;

	/**
	 * The feature id for the '<em><b>Guid</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SIGNAL__GUID = ROUTE_OBJECT__GUID;

	/**
	 * The feature id for the '<em><b>Route Locations</b></em>' containment
	 * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SIGNAL__ROUTE_LOCATIONS = ROUTE_OBJECT__ROUTE_LOCATIONS;

	/**
	 * The feature id for the '<em><b>Role</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SIGNAL__ROLE = ROUTE_OBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>System</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SIGNAL__SYSTEM = ROUTE_OBJECT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Screen</b></em>' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SIGNAL__SCREEN = ROUTE_OBJECT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Label</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SIGNAL__LABEL = ROUTE_OBJECT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Lateral Distance</b></em>' attribute list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SIGNAL__LATERAL_DISTANCE = ROUTE_OBJECT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Signal Direction</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SIGNAL__SIGNAL_DIRECTION = ROUTE_OBJECT_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Mount Position</b></em>' containment
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SIGNAL__MOUNT_POSITION = ROUTE_OBJECT_FEATURE_COUNT + 6;

	/**
	 * The number of structural features of the '<em>Signal</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SIGNAL_FEATURE_COUNT = ROUTE_OBJECT_FEATURE_COUNT + 7;

	/**
	 * The number of operations of the '<em>Signal</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SIGNAL_OPERATION_COUNT = ROUTE_OBJECT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.set.model.siteplan.impl.SignalScreenImpl <em>Signal
	 * Screen</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.set.model.siteplan.impl.SignalScreenImpl
	 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getSignalScreen()
	 * @generated
	 */
	int SIGNAL_SCREEN = 10;

	/**
	 * The feature id for the '<em><b>Screen</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SIGNAL_SCREEN__SCREEN = 0;

	/**
	 * The feature id for the '<em><b>Switched</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SIGNAL_SCREEN__SWITCHED = 1;

	/**
	 * The feature id for the '<em><b>Frame Type</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SIGNAL_SCREEN__FRAME_TYPE = 2;

	/**
	 * The number of structural features of the '<em>Signal Screen</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SIGNAL_SCREEN_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Signal Screen</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SIGNAL_SCREEN_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.set.model.siteplan.impl.LabelImpl <em>Label</em>}'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.set.model.siteplan.impl.LabelImpl
	 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getLabel()
	 * @generated
	 */
	int LABEL = 11;

	/**
	 * The feature id for the '<em><b>Text</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LABEL__TEXT = 0;

	/**
	 * The feature id for the '<em><b>Orientation Inverted</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LABEL__ORIENTATION_INVERTED = 1;

	/**
	 * The number of structural features of the '<em>Label</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LABEL_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Label</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LABEL_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.set.model.siteplan.impl.TrackSwitchImpl <em>Track
	 * Switch</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.set.model.siteplan.impl.TrackSwitchImpl
	 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getTrackSwitch()
	 * @generated
	 */
	int TRACK_SWITCH = 12;

	/**
	 * The feature id for the '<em><b>Guid</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRACK_SWITCH__GUID = SITEPLAN_OBJECT__GUID;

	/**
	 * The feature id for the '<em><b>Design</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRACK_SWITCH__DESIGN = SITEPLAN_OBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Components</b></em>' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRACK_SWITCH__COMPONENTS = SITEPLAN_OBJECT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Continuous Segments</b></em>' containment
	 * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRACK_SWITCH__CONTINUOUS_SEGMENTS = SITEPLAN_OBJECT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Switch Type</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRACK_SWITCH__SWITCH_TYPE = SITEPLAN_OBJECT_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Track Switch</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRACK_SWITCH_FEATURE_COUNT = SITEPLAN_OBJECT_FEATURE_COUNT + 4;

	/**
	 * The number of operations of the '<em>Track Switch</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRACK_SWITCH_OPERATION_COUNT = SITEPLAN_OBJECT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.set.model.siteplan.impl.TrackSwitchComponentImpl
	 * <em>Track Switch Component</em>}' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see org.eclipse.set.model.siteplan.impl.TrackSwitchComponentImpl
	 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getTrackSwitchComponent()
	 * @generated
	 */
	int TRACK_SWITCH_COMPONENT = 13;

	/**
	 * The feature id for the '<em><b>Guid</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRACK_SWITCH_COMPONENT__GUID = ROUTE_OBJECT__GUID;

	/**
	 * The feature id for the '<em><b>Route Locations</b></em>' containment
	 * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRACK_SWITCH_COMPONENT__ROUTE_LOCATIONS = ROUTE_OBJECT__ROUTE_LOCATIONS;

	/**
	 * The feature id for the '<em><b>Preferred Location</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRACK_SWITCH_COMPONENT__PREFERRED_LOCATION = ROUTE_OBJECT_FEATURE_COUNT
			+ 0;

	/**
	 * The feature id for the '<em><b>Point Detector Count</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRACK_SWITCH_COMPONENT__POINT_DETECTOR_COUNT = ROUTE_OBJECT_FEATURE_COUNT
			+ 1;

	/**
	 * The feature id for the '<em><b>Start</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRACK_SWITCH_COMPONENT__START = ROUTE_OBJECT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Label Position</b></em>' containment
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRACK_SWITCH_COMPONENT__LABEL_POSITION = ROUTE_OBJECT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Label</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRACK_SWITCH_COMPONENT__LABEL = ROUTE_OBJECT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Operating Mode</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRACK_SWITCH_COMPONENT__OPERATING_MODE = ROUTE_OBJECT_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Main Leg</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRACK_SWITCH_COMPONENT__MAIN_LEG = ROUTE_OBJECT_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Side Leg</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRACK_SWITCH_COMPONENT__SIDE_LEG = ROUTE_OBJECT_FEATURE_COUNT + 7;

	/**
	 * The number of structural features of the '<em>Track Switch
	 * Component</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRACK_SWITCH_COMPONENT_FEATURE_COUNT = ROUTE_OBJECT_FEATURE_COUNT + 8;

	/**
	 * The number of operations of the '<em>Track Switch Component</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRACK_SWITCH_COMPONENT_OPERATION_COUNT = ROUTE_OBJECT_OPERATION_COUNT
			+ 0;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.set.model.siteplan.impl.ContinuousTrackSegmentImpl
	 * <em>Continuous Track Segment</em>}' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see org.eclipse.set.model.siteplan.impl.ContinuousTrackSegmentImpl
	 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getContinuousTrackSegment()
	 * @generated
	 */
	int CONTINUOUS_TRACK_SEGMENT = 14;

	/**
	 * The feature id for the '<em><b>Start</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONTINUOUS_TRACK_SEGMENT__START = 0;

	/**
	 * The feature id for the '<em><b>End</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONTINUOUS_TRACK_SEGMENT__END = 1;

	/**
	 * The number of structural features of the '<em>Continuous Track
	 * Segment</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONTINUOUS_TRACK_SEGMENT_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Continuous Track Segment</em>'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONTINUOUS_TRACK_SEGMENT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.set.model.siteplan.impl.TrackImpl <em>Track</em>}'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.set.model.siteplan.impl.TrackImpl
	 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getTrack()
	 * @generated
	 */
	int TRACK = 15;

	/**
	 * The feature id for the '<em><b>Guid</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRACK__GUID = SITEPLAN_OBJECT__GUID;

	/**
	 * The feature id for the '<em><b>Sections</b></em>' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRACK__SECTIONS = SITEPLAN_OBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Designations</b></em>' containment
	 * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRACK__DESIGNATIONS = SITEPLAN_OBJECT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Start Coordinate</b></em>' containment
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRACK__START_COORDINATE = SITEPLAN_OBJECT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Track</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRACK_FEATURE_COUNT = SITEPLAN_OBJECT_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>Track</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRACK_OPERATION_COUNT = SITEPLAN_OBJECT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.set.model.siteplan.impl.TrackSectionImpl <em>Track
	 * Section</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.set.model.siteplan.impl.TrackSectionImpl
	 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getTrackSection()
	 * @generated
	 */
	int TRACK_SECTION = 16;

	/**
	 * The feature id for the '<em><b>Guid</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRACK_SECTION__GUID = SITEPLAN_OBJECT__GUID;

	/**
	 * The feature id for the '<em><b>Shape</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRACK_SECTION__SHAPE = SITEPLAN_OBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Segments</b></em>' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRACK_SECTION__SEGMENTS = SITEPLAN_OBJECT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Color</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRACK_SECTION__COLOR = SITEPLAN_OBJECT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Start Coordinate</b></em>' containment
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRACK_SECTION__START_COORDINATE = SITEPLAN_OBJECT_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Track Section</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRACK_SECTION_FEATURE_COUNT = SITEPLAN_OBJECT_FEATURE_COUNT + 4;

	/**
	 * The number of operations of the '<em>Track Section</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRACK_SECTION_OPERATION_COUNT = SITEPLAN_OBJECT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.set.model.siteplan.impl.TrackSegmentImpl <em>Track
	 * Segment</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.set.model.siteplan.impl.TrackSegmentImpl
	 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getTrackSegment()
	 * @generated
	 */
	int TRACK_SEGMENT = 17;

	/**
	 * The feature id for the '<em><b>Guid</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRACK_SEGMENT__GUID = SITEPLAN_OBJECT__GUID;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRACK_SEGMENT__TYPE = SITEPLAN_OBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Positions</b></em>' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRACK_SEGMENT__POSITIONS = SITEPLAN_OBJECT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Track Segment</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRACK_SEGMENT_FEATURE_COUNT = SITEPLAN_OBJECT_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Track Segment</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRACK_SEGMENT_OPERATION_COUNT = SITEPLAN_OBJECT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.set.model.siteplan.impl.FMAComponentImpl <em>FMA
	 * Component</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.set.model.siteplan.impl.FMAComponentImpl
	 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getFMAComponent()
	 * @generated
	 */
	int FMA_COMPONENT = 18;

	/**
	 * The feature id for the '<em><b>Guid</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FMA_COMPONENT__GUID = ROUTE_OBJECT__GUID;

	/**
	 * The feature id for the '<em><b>Route Locations</b></em>' containment
	 * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FMA_COMPONENT__ROUTE_LOCATIONS = ROUTE_OBJECT__ROUTE_LOCATIONS;

	/**
	 * The feature id for the '<em><b>Position</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FMA_COMPONENT__POSITION = ROUTE_OBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Label</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FMA_COMPONENT__LABEL = ROUTE_OBJECT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FMA_COMPONENT__TYPE = ROUTE_OBJECT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Right Side</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FMA_COMPONENT__RIGHT_SIDE = ROUTE_OBJECT_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>FMA Component</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FMA_COMPONENT_FEATURE_COUNT = ROUTE_OBJECT_FEATURE_COUNT + 4;

	/**
	 * The number of operations of the '<em>FMA Component</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FMA_COMPONENT_OPERATION_COUNT = ROUTE_OBJECT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.set.model.siteplan.impl.RouteImpl <em>Route</em>}'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.set.model.siteplan.impl.RouteImpl
	 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getRoute()
	 * @generated
	 */
	int ROUTE = 19;

	/**
	 * The feature id for the '<em><b>Guid</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ROUTE__GUID = SITEPLAN_OBJECT__GUID;

	/**
	 * The feature id for the '<em><b>Sections</b></em>' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ROUTE__SECTIONS = SITEPLAN_OBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Markers</b></em>' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ROUTE__MARKERS = SITEPLAN_OBJECT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Route</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ROUTE_FEATURE_COUNT = SITEPLAN_OBJECT_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Route</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ROUTE_OPERATION_COUNT = SITEPLAN_OBJECT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.set.model.siteplan.impl.RouteSectionImpl <em>Route
	 * Section</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.set.model.siteplan.impl.RouteSectionImpl
	 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getRouteSection()
	 * @generated
	 */
	int ROUTE_SECTION = 20;

	/**
	 * The feature id for the '<em><b>Guid</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ROUTE_SECTION__GUID = 0;

	/**
	 * The feature id for the '<em><b>Shape</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ROUTE_SECTION__SHAPE = 1;

	/**
	 * The feature id for the '<em><b>Positions</b></em>' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ROUTE_SECTION__POSITIONS = 2;

	/**
	 * The number of structural features of the '<em>Route Section</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ROUTE_SECTION_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Route Section</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ROUTE_SECTION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.set.model.siteplan.impl.KMMarkerImpl <em>KM
	 * Marker</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.set.model.siteplan.impl.KMMarkerImpl
	 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getKMMarker()
	 * @generated
	 */
	int KM_MARKER = 21;

	/**
	 * The feature id for the '<em><b>Position</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int KM_MARKER__POSITION = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int KM_MARKER__VALUE = 1;

	/**
	 * The number of structural features of the '<em>KM Marker</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int KM_MARKER_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>KM Marker</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int KM_MARKER_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.set.model.siteplan.impl.TrackSwitchEndMarkerImpl
	 * <em>Track Switch End Marker</em>}' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see org.eclipse.set.model.siteplan.impl.TrackSwitchEndMarkerImpl
	 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getTrackSwitchEndMarker()
	 * @generated
	 */
	int TRACK_SWITCH_END_MARKER = 22;

	/**
	 * The feature id for the '<em><b>Leg ACoordinate</b></em>' containment
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRACK_SWITCH_END_MARKER__LEG_ACOORDINATE = 0;

	/**
	 * The feature id for the '<em><b>Leg BCoordinate</b></em>' containment
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRACK_SWITCH_END_MARKER__LEG_BCOORDINATE = 1;

	/**
	 * The number of structural features of the '<em>Track Switch End
	 * Marker</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRACK_SWITCH_END_MARKER_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Track Switch End Marker</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRACK_SWITCH_END_MARKER_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.set.model.siteplan.impl.ErrorImpl <em>Error</em>}'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.set.model.siteplan.impl.ErrorImpl
	 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getError()
	 * @generated
	 */
	int ERROR = 23;

	/**
	 * The feature id for the '<em><b>Position</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR__POSITION = 0;

	/**
	 * The feature id for the '<em><b>Relevant GUI Ds</b></em>' attribute list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR__RELEVANT_GUI_DS = 1;

	/**
	 * The feature id for the '<em><b>Message</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR__MESSAGE = 2;

	/**
	 * The number of structural features of the '<em>Error</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Error</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.set.model.siteplan.impl.PZBImpl <em>PZB</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.set.model.siteplan.impl.PZBImpl
	 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getPZB()
	 * @generated
	 */
	int PZB = 24;

	/**
	 * The feature id for the '<em><b>Guid</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PZB__GUID = ROUTE_OBJECT__GUID;

	/**
	 * The feature id for the '<em><b>Route Locations</b></em>' containment
	 * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PZB__ROUTE_LOCATIONS = ROUTE_OBJECT__ROUTE_LOCATIONS;

	/**
	 * The feature id for the '<em><b>Position</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PZB__POSITION = ROUTE_OBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PZB__TYPE = ROUTE_OBJECT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Element</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PZB__ELEMENT = ROUTE_OBJECT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Right Side</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PZB__RIGHT_SIDE = ROUTE_OBJECT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Effectivity</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PZB__EFFECTIVITY = ROUTE_OBJECT_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>PZB</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PZB_FEATURE_COUNT = ROUTE_OBJECT_FEATURE_COUNT + 5;

	/**
	 * The number of operations of the '<em>PZB</em>' class. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PZB_OPERATION_COUNT = ROUTE_OBJECT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.set.model.siteplan.impl.PZBGUImpl <em>PZBGU</em>}'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.set.model.siteplan.impl.PZBGUImpl
	 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getPZBGU()
	 * @generated
	 */
	int PZBGU = 25;

	/**
	 * The feature id for the '<em><b>Guid</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PZBGU__GUID = SITEPLAN_OBJECT__GUID;

	/**
	 * The feature id for the '<em><b>Pzbs</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PZBGU__PZBS = SITEPLAN_OBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Length</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PZBGU__LENGTH = SITEPLAN_OBJECT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>PZBGU</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PZBGU_FEATURE_COUNT = SITEPLAN_OBJECT_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>PZBGU</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PZBGU_OPERATION_COUNT = SITEPLAN_OBJECT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.set.model.siteplan.impl.TrackDesignationImpl
	 * <em>Track Designation</em>}' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see org.eclipse.set.model.siteplan.impl.TrackDesignationImpl
	 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getTrackDesignation()
	 * @generated
	 */
	int TRACK_DESIGNATION = 26;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRACK_DESIGNATION__NAME = 0;

	/**
	 * The feature id for the '<em><b>Position</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRACK_DESIGNATION__POSITION = 1;

	/**
	 * The number of structural features of the '<em>Track Designation</em>'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRACK_DESIGNATION_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Track Designation</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRACK_DESIGNATION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.set.model.siteplan.impl.TrackSwitchLegImpl <em>Track
	 * Switch Leg</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.set.model.siteplan.impl.TrackSwitchLegImpl
	 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getTrackSwitchLeg()
	 * @generated
	 */
	int TRACK_SWITCH_LEG = 27;

	/**
	 * The feature id for the '<em><b>Connection</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRACK_SWITCH_LEG__CONNECTION = 0;

	/**
	 * The feature id for the '<em><b>Coordinates</b></em>' containment
	 * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRACK_SWITCH_LEG__COORDINATES = 1;

	/**
	 * The number of structural features of the '<em>Track Switch Leg</em>'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRACK_SWITCH_LEG_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Track Switch Leg</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRACK_SWITCH_LEG_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.set.model.siteplan.impl.StationImpl
	 * <em>Station</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.set.model.siteplan.impl.StationImpl
	 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getStation()
	 * @generated
	 */
	int STATION = 28;

	/**
	 * The feature id for the '<em><b>Guid</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int STATION__GUID = SITEPLAN_OBJECT__GUID;

	/**
	 * The feature id for the '<em><b>Platforms</b></em>' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int STATION__PLATFORMS = SITEPLAN_OBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Label</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int STATION__LABEL = SITEPLAN_OBJECT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Station</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int STATION_FEATURE_COUNT = SITEPLAN_OBJECT_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Station</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int STATION_OPERATION_COUNT = SITEPLAN_OBJECT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.set.model.siteplan.impl.PlatformImpl
	 * <em>Platform</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.set.model.siteplan.impl.PlatformImpl
	 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getPlatform()
	 * @generated
	 */
	int PLATFORM = 29;

	/**
	 * The feature id for the '<em><b>Guid</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PLATFORM__GUID = 0;

	/**
	 * The feature id for the '<em><b>Label</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PLATFORM__LABEL = 1;

	/**
	 * The feature id for the '<em><b>Label Position</b></em>' containment
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PLATFORM__LABEL_POSITION = 2;

	/**
	 * The feature id for the '<em><b>Points</b></em>' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PLATFORM__POINTS = 3;

	/**
	 * The number of structural features of the '<em>Platform</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PLATFORM_FEATURE_COUNT = 4;

	/**
	 * The number of operations of the '<em>Platform</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PLATFORM_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.set.model.siteplan.impl.TrackLockImpl <em>Track
	 * Lock</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.set.model.siteplan.impl.TrackLockImpl
	 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getTrackLock()
	 * @generated
	 */
	int TRACK_LOCK = 30;

	/**
	 * The feature id for the '<em><b>Guid</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRACK_LOCK__GUID = SITEPLAN_OBJECT__GUID;

	/**
	 * The feature id for the '<em><b>Components</b></em>' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRACK_LOCK__COMPONENTS = SITEPLAN_OBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Preferred Location</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRACK_LOCK__PREFERRED_LOCATION = SITEPLAN_OBJECT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Operating Mode</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRACK_LOCK__OPERATING_MODE = SITEPLAN_OBJECT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Label</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRACK_LOCK__LABEL = SITEPLAN_OBJECT_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Track Lock</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRACK_LOCK_FEATURE_COUNT = SITEPLAN_OBJECT_FEATURE_COUNT + 4;

	/**
	 * The number of operations of the '<em>Track Lock</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRACK_LOCK_OPERATION_COUNT = SITEPLAN_OBJECT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.set.model.siteplan.impl.TrackLockComponentImpl
	 * <em>Track Lock Component</em>}' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see org.eclipse.set.model.siteplan.impl.TrackLockComponentImpl
	 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getTrackLockComponent()
	 * @generated
	 */
	int TRACK_LOCK_COMPONENT = 31;

	/**
	 * The feature id for the '<em><b>Guid</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRACK_LOCK_COMPONENT__GUID = POSITIONED_OBJECT__GUID;

	/**
	 * The feature id for the '<em><b>Position</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRACK_LOCK_COMPONENT__POSITION = POSITIONED_OBJECT__POSITION;

	/**
	 * The feature id for the '<em><b>Track Lock Signal</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRACK_LOCK_COMPONENT__TRACK_LOCK_SIGNAL = POSITIONED_OBJECT_FEATURE_COUNT
			+ 0;

	/**
	 * The feature id for the '<em><b>Ejection Direction</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRACK_LOCK_COMPONENT__EJECTION_DIRECTION = POSITIONED_OBJECT_FEATURE_COUNT
			+ 1;

	/**
	 * The number of structural features of the '<em>Track Lock Component</em>'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRACK_LOCK_COMPONENT_FEATURE_COUNT = POSITIONED_OBJECT_FEATURE_COUNT
			+ 2;

	/**
	 * The number of operations of the '<em>Track Lock Component</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRACK_LOCK_COMPONENT_OPERATION_COUNT = POSITIONED_OBJECT_OPERATION_COUNT
			+ 0;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.set.model.siteplan.impl.ObjectManagementImpl
	 * <em>Object Management</em>}' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see org.eclipse.set.model.siteplan.impl.ObjectManagementImpl
	 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getObjectManagement()
	 * @generated
	 */
	int OBJECT_MANAGEMENT = 32;

	/**
	 * The feature id for the '<em><b>Planning Object IDs</b></em>' attribute
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int OBJECT_MANAGEMENT__PLANNING_OBJECT_IDS = 0;

	/**
	 * The feature id for the '<em><b>Planning Group ID</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int OBJECT_MANAGEMENT__PLANNING_GROUP_ID = 1;

	/**
	 * The number of structural features of the '<em>Object Management</em>'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int OBJECT_MANAGEMENT_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Object Management</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int OBJECT_MANAGEMENT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.set.model.siteplan.impl.TrackCloseImpl <em>Track
	 * Close</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.set.model.siteplan.impl.TrackCloseImpl
	 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getTrackClose()
	 * @generated
	 */
	int TRACK_CLOSE = 33;

	/**
	 * The feature id for the '<em><b>Guid</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRACK_CLOSE__GUID = POSITIONED_OBJECT__GUID;

	/**
	 * The feature id for the '<em><b>Position</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRACK_CLOSE__POSITION = POSITIONED_OBJECT__POSITION;

	/**
	 * The feature id for the '<em><b>Track Close Type</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRACK_CLOSE__TRACK_CLOSE_TYPE = POSITIONED_OBJECT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Track Close</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRACK_CLOSE_FEATURE_COUNT = POSITIONED_OBJECT_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Track Close</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRACK_CLOSE_OPERATION_COUNT = POSITIONED_OBJECT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.set.model.siteplan.impl.ExternalElementControlImpl
	 * <em>External Element Control</em>}' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see org.eclipse.set.model.siteplan.impl.ExternalElementControlImpl
	 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getExternalElementControl()
	 * @generated
	 */
	int EXTERNAL_ELEMENT_CONTROL = 34;

	/**
	 * The feature id for the '<em><b>Guid</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_ELEMENT_CONTROL__GUID = ROUTE_OBJECT__GUID;

	/**
	 * The feature id for the '<em><b>Route Locations</b></em>' containment
	 * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_ELEMENT_CONTROL__ROUTE_LOCATIONS = ROUTE_OBJECT__ROUTE_LOCATIONS;

	/**
	 * The feature id for the '<em><b>Position</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_ELEMENT_CONTROL__POSITION = ROUTE_OBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Control Art</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_ELEMENT_CONTROL__CONTROL_ART = ROUTE_OBJECT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Element Type</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_ELEMENT_CONTROL__ELEMENT_TYPE = ROUTE_OBJECT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Control Station</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_ELEMENT_CONTROL__CONTROL_STATION = ROUTE_OBJECT_FEATURE_COUNT
			+ 3;

	/**
	 * The feature id for the '<em><b>Label</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_ELEMENT_CONTROL__LABEL = ROUTE_OBJECT_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>External Element
	 * Control</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_ELEMENT_CONTROL_FEATURE_COUNT = ROUTE_OBJECT_FEATURE_COUNT + 5;

	/**
	 * The number of operations of the '<em>External Element Control</em>'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_ELEMENT_CONTROL_OPERATION_COUNT = ROUTE_OBJECT_OPERATION_COUNT
			+ 0;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.set.model.siteplan.impl.LockKeyImpl <em>Lock
	 * Key</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.set.model.siteplan.impl.LockKeyImpl
	 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getLockKey()
	 * @generated
	 */
	int LOCK_KEY = 35;

	/**
	 * The feature id for the '<em><b>Guid</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LOCK_KEY__GUID = ROUTE_OBJECT__GUID;

	/**
	 * The feature id for the '<em><b>Route Locations</b></em>' containment
	 * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LOCK_KEY__ROUTE_LOCATIONS = ROUTE_OBJECT__ROUTE_LOCATIONS;

	/**
	 * The feature id for the '<em><b>Position</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LOCK_KEY__POSITION = ROUTE_OBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Label</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LOCK_KEY__LABEL = ROUTE_OBJECT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LOCK_KEY__TYPE = ROUTE_OBJECT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Locked</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LOCK_KEY__LOCKED = ROUTE_OBJECT_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Lock Key</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LOCK_KEY_FEATURE_COUNT = ROUTE_OBJECT_FEATURE_COUNT + 4;

	/**
	 * The number of operations of the '<em>Lock Key</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LOCK_KEY_OPERATION_COUNT = ROUTE_OBJECT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.set.model.siteplan.impl.LayoutinfoImpl
	 * <em>Layoutinfo</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see org.eclipse.set.model.siteplan.impl.LayoutinfoImpl
	 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getLayoutinfo()
	 * @generated
	 */
	int LAYOUTINFO = 36;

	/**
	 * The feature id for the '<em><b>Guid</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LAYOUTINFO__GUID = SITEPLAN_OBJECT__GUID;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LAYOUTINFO__LABEL = SITEPLAN_OBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Sheets Cut</b></em>' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LAYOUTINFO__SHEETS_CUT = SITEPLAN_OBJECT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Layoutinfo</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LAYOUTINFO_FEATURE_COUNT = SITEPLAN_OBJECT_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Layoutinfo</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LAYOUTINFO_OPERATION_COUNT = SITEPLAN_OBJECT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.set.model.siteplan.impl.SheetCutImpl <em>Sheet
	 * Cut</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.set.model.siteplan.impl.SheetCutImpl
	 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getSheetCut()
	 * @generated
	 */
	int SHEET_CUT = 37;

	/**
	 * The feature id for the '<em><b>Guid</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SHEET_CUT__GUID = SITEPLAN_OBJECT__GUID;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SHEET_CUT__LABEL = SITEPLAN_OBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Polygon Direction</b></em>' containment
	 * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SHEET_CUT__POLYGON_DIRECTION = SITEPLAN_OBJECT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Polygon</b></em>' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SHEET_CUT__POLYGON = SITEPLAN_OBJECT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Sheet Cut</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SHEET_CUT_FEATURE_COUNT = SITEPLAN_OBJECT_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>Sheet Cut</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SHEET_CUT_OPERATION_COUNT = SITEPLAN_OBJECT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.set.model.siteplan.impl.CantImpl <em>Cant</em>}'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.set.model.siteplan.impl.CantImpl
	 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getCant()
	 * @generated
	 */
	int CANT = 38;

	/**
	 * The feature id for the '<em><b>Guid</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CANT__GUID = SITEPLAN_OBJECT__GUID;

	/**
	 * The feature id for the '<em><b>Point A</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CANT__POINT_A = SITEPLAN_OBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Point B</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CANT__POINT_B = SITEPLAN_OBJECT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Form</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CANT__FORM = SITEPLAN_OBJECT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Length</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CANT__LENGTH = SITEPLAN_OBJECT_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Cant</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CANT_FEATURE_COUNT = SITEPLAN_OBJECT_FEATURE_COUNT + 4;

	/**
	 * The number of operations of the '<em>Cant</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CANT_OPERATION_COUNT = SITEPLAN_OBJECT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.set.model.siteplan.impl.CantPointImpl <em>Cant
	 * Point</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.set.model.siteplan.impl.CantPointImpl
	 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getCantPoint()
	 * @generated
	 */
	int CANT_POINT = 39;

	/**
	 * The feature id for the '<em><b>Guid</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CANT_POINT__GUID = POSITIONED_OBJECT__GUID;

	/**
	 * The feature id for the '<em><b>Position</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CANT_POINT__POSITION = POSITIONED_OBJECT__POSITION;

	/**
	 * The feature id for the '<em><b>Height</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CANT_POINT__HEIGHT = POSITIONED_OBJECT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Cant Point</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CANT_POINT_FEATURE_COUNT = POSITIONED_OBJECT_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Cant Point</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CANT_POINT_OPERATION_COUNT = POSITIONED_OBJECT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.set.model.siteplan.impl.UnknownPositionedObjectImpl
	 * <em>Unknown Positioned Object</em>}' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see org.eclipse.set.model.siteplan.impl.UnknownPositionedObjectImpl
	 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getUnknownPositionedObject()
	 * @generated
	 */
	int UNKNOWN_POSITIONED_OBJECT = 40;

	/**
	 * The feature id for the '<em><b>Guid</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int UNKNOWN_POSITIONED_OBJECT__GUID = POSITIONED_OBJECT__GUID;

	/**
	 * The feature id for the '<em><b>Position</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int UNKNOWN_POSITIONED_OBJECT__POSITION = POSITIONED_OBJECT__POSITION;

	/**
	 * The feature id for the '<em><b>Object Type</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int UNKNOWN_POSITIONED_OBJECT__OBJECT_TYPE = POSITIONED_OBJECT_FEATURE_COUNT
			+ 0;

	/**
	 * The number of structural features of the '<em>Unknown Positioned
	 * Object</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int UNKNOWN_POSITIONED_OBJECT_FEATURE_COUNT = POSITIONED_OBJECT_FEATURE_COUNT
			+ 1;

	/**
	 * The number of operations of the '<em>Unknown Positioned Object</em>'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int UNKNOWN_POSITIONED_OBJECT_OPERATION_COUNT = POSITIONED_OBJECT_OPERATION_COUNT
			+ 0;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.set.model.siteplan.SignalMountType <em>Signal Mount
	 * Type</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.set.model.siteplan.SignalMountType
	 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getSignalMountType()
	 * @generated
	 */
	int SIGNAL_MOUNT_TYPE = 41;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.set.model.siteplan.SignalRole <em>Signal Role</em>}'
	 * enum. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.set.model.siteplan.SignalRole
	 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getSignalRole()
	 * @generated
	 */
	int SIGNAL_ROLE = 42;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.set.model.siteplan.SignalSystem <em>Signal
	 * System</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.set.model.siteplan.SignalSystem
	 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getSignalSystem()
	 * @generated
	 */
	int SIGNAL_SYSTEM = 43;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.set.model.siteplan.MountDirection <em>Mount
	 * Direction</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.set.model.siteplan.MountDirection
	 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getMountDirection()
	 * @generated
	 */
	int MOUNT_DIRECTION = 44;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.set.model.siteplan.TurnoutOperatingMode <em>Turnout
	 * Operating Mode</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.set.model.siteplan.TurnoutOperatingMode
	 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getTurnoutOperatingMode()
	 * @generated
	 */
	int TURNOUT_OPERATING_MODE = 45;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.set.model.siteplan.TrackShape <em>Track Shape</em>}'
	 * enum. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.set.model.siteplan.TrackShape
	 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getTrackShape()
	 * @generated
	 */
	int TRACK_SHAPE = 46;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.set.model.siteplan.TrackType <em>Track Type</em>}'
	 * enum. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.set.model.siteplan.TrackType
	 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getTrackType()
	 * @generated
	 */
	int TRACK_TYPE = 47;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.set.model.siteplan.FMAComponentType <em>FMA Component
	 * Type</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.set.model.siteplan.FMAComponentType
	 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getFMAComponentType()
	 * @generated
	 */
	int FMA_COMPONENT_TYPE = 48;

	/**
	 * The meta object id for the '{@link org.eclipse.set.model.siteplan.PZBType
	 * <em>PZB Type</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.set.model.siteplan.PZBType
	 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getPZBType()
	 * @generated
	 */
	int PZB_TYPE = 49;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.set.model.siteplan.PZBElement <em>PZB Element</em>}'
	 * enum. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.set.model.siteplan.PZBElement
	 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getPZBElement()
	 * @generated
	 */
	int PZB_ELEMENT = 50;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.set.model.siteplan.PZBEffectivity <em>PZB
	 * Effectivity</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.set.model.siteplan.PZBEffectivity
	 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getPZBEffectivity()
	 * @generated
	 */
	int PZB_EFFECTIVITY = 51;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.set.model.siteplan.TrackLockLocation <em>Track Lock
	 * Location</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.set.model.siteplan.TrackLockLocation
	 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getTrackLockLocation()
	 * @generated
	 */
	int TRACK_LOCK_LOCATION = 52;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.set.model.siteplan.LeftRight <em>Left Right</em>}'
	 * enum. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.set.model.siteplan.LeftRight
	 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getLeftRight()
	 * @generated
	 */
	int LEFT_RIGHT = 53;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.set.model.siteplan.Direction <em>Direction</em>}'
	 * enum. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.set.model.siteplan.Direction
	 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getDirection()
	 * @generated
	 */
	int DIRECTION = 54;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.set.model.siteplan.TrackCloseType <em>Track Close
	 * Type</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.set.model.siteplan.TrackCloseType
	 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getTrackCloseType()
	 * @generated
	 */
	int TRACK_CLOSE_TYPE = 55;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.set.model.siteplan.ExternalElementControlArt
	 * <em>External Element Control Art</em>}' enum. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.set.model.siteplan.ExternalElementControlArt
	 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getExternalElementControlArt()
	 * @generated
	 */
	int EXTERNAL_ELEMENT_CONTROL_ART = 56;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.set.model.siteplan.ControlStationType <em>Control
	 * Station Type</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.set.model.siteplan.ControlStationType
	 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getControlStationType()
	 * @generated
	 */
	int CONTROL_STATION_TYPE = 57;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.set.model.siteplan.LockKeyType <em>Lock Key
	 * Type</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.set.model.siteplan.LockKeyType
	 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getLockKeyType()
	 * @generated
	 */
	int LOCK_KEY_TYPE = 58;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.set.model.siteplan.SwitchType <em>Switch Type</em>}'
	 * enum. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.set.model.siteplan.SwitchType
	 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getSwitchType()
	 * @generated
	 */
	int SWITCH_TYPE = 59;

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.set.model.siteplan.Siteplan <em>Siteplan</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Siteplan</em>'.
	 * @see org.eclipse.set.model.siteplan.Siteplan
	 * @generated
	 */
	EClass getSiteplan();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.eclipse.set.model.siteplan.Siteplan#getInitialState
	 * <em>Initial State</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Initial
	 *         State</em>'.
	 * @see org.eclipse.set.model.siteplan.Siteplan#getInitialState()
	 * @see #getSiteplan()
	 * @generated
	 */
	EReference getSiteplan_InitialState();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.eclipse.set.model.siteplan.Siteplan#getChangedInitialState
	 * <em>Changed Initial State</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Changed
	 *         Initial State</em>'.
	 * @see org.eclipse.set.model.siteplan.Siteplan#getChangedInitialState()
	 * @see #getSiteplan()
	 * @generated
	 */
	EReference getSiteplan_ChangedInitialState();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.eclipse.set.model.siteplan.Siteplan#getCommonState <em>Common
	 * State</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Common
	 *         State</em>'.
	 * @see org.eclipse.set.model.siteplan.Siteplan#getCommonState()
	 * @see #getSiteplan()
	 * @generated
	 */
	EReference getSiteplan_CommonState();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.eclipse.set.model.siteplan.Siteplan#getChangedFinalState
	 * <em>Changed Final State</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return the meta object for the containment reference '<em>Changed Final
	 *         State</em>'.
	 * @see org.eclipse.set.model.siteplan.Siteplan#getChangedFinalState()
	 * @see #getSiteplan()
	 * @generated
	 */
	EReference getSiteplan_ChangedFinalState();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.eclipse.set.model.siteplan.Siteplan#getFinalState <em>Final
	 * State</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Final
	 *         State</em>'.
	 * @see org.eclipse.set.model.siteplan.Siteplan#getFinalState()
	 * @see #getSiteplan()
	 * @generated
	 */
	EReference getSiteplan_FinalState();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.eclipse.set.model.siteplan.Siteplan#getCenterPosition
	 * <em>Center Position</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Center
	 *         Position</em>'.
	 * @see org.eclipse.set.model.siteplan.Siteplan#getCenterPosition()
	 * @see #getSiteplan()
	 * @generated
	 */
	EReference getSiteplan_CenterPosition();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link org.eclipse.set.model.siteplan.Siteplan#getObjectManagement
	 * <em>Object Management</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return the meta object for the containment reference list '<em>Object
	 *         Management</em>'.
	 * @see org.eclipse.set.model.siteplan.Siteplan#getObjectManagement()
	 * @see #getSiteplan()
	 * @generated
	 */
	EReference getSiteplan_ObjectManagement();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link org.eclipse.set.model.siteplan.Siteplan#getLayoutInfo <em>Layout
	 * Info</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Layout
	 *         Info</em>'.
	 * @see org.eclipse.set.model.siteplan.Siteplan#getLayoutInfo()
	 * @see #getSiteplan()
	 * @generated
	 */
	EReference getSiteplan_LayoutInfo();

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.set.model.siteplan.SiteplanState <em>State</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>State</em>'.
	 * @see org.eclipse.set.model.siteplan.SiteplanState
	 * @generated
	 */
	EClass getSiteplanState();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link org.eclipse.set.model.siteplan.SiteplanState#getSignals
	 * <em>Signals</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list
	 *         '<em>Signals</em>'.
	 * @see org.eclipse.set.model.siteplan.SiteplanState#getSignals()
	 * @see #getSiteplanState()
	 * @generated
	 */
	EReference getSiteplanState_Signals();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link org.eclipse.set.model.siteplan.SiteplanState#getTrackSwitches
	 * <em>Track Switches</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Track
	 *         Switches</em>'.
	 * @see org.eclipse.set.model.siteplan.SiteplanState#getTrackSwitches()
	 * @see #getSiteplanState()
	 * @generated
	 */
	EReference getSiteplanState_TrackSwitches();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link org.eclipse.set.model.siteplan.SiteplanState#getTrackSwitchEndMarkers
	 * <em>Track Switch End Markers</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Track
	 *         Switch End Markers</em>'.
	 * @see org.eclipse.set.model.siteplan.SiteplanState#getTrackSwitchEndMarkers()
	 * @see #getSiteplanState()
	 * @generated
	 */
	EReference getSiteplanState_TrackSwitchEndMarkers();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link org.eclipse.set.model.siteplan.SiteplanState#getTracks
	 * <em>Tracks</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list
	 *         '<em>Tracks</em>'.
	 * @see org.eclipse.set.model.siteplan.SiteplanState#getTracks()
	 * @see #getSiteplanState()
	 * @generated
	 */
	EReference getSiteplanState_Tracks();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link org.eclipse.set.model.siteplan.SiteplanState#getFmaComponents
	 * <em>Fma Components</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Fma
	 *         Components</em>'.
	 * @see org.eclipse.set.model.siteplan.SiteplanState#getFmaComponents()
	 * @see #getSiteplanState()
	 * @generated
	 */
	EReference getSiteplanState_FmaComponents();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link org.eclipse.set.model.siteplan.SiteplanState#getPzb
	 * <em>Pzb</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list
	 *         '<em>Pzb</em>'.
	 * @see org.eclipse.set.model.siteplan.SiteplanState#getPzb()
	 * @see #getSiteplanState()
	 * @generated
	 */
	EReference getSiteplanState_Pzb();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link org.eclipse.set.model.siteplan.SiteplanState#getPzbGU <em>Pzb
	 * GU</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Pzb
	 *         GU</em>'.
	 * @see org.eclipse.set.model.siteplan.SiteplanState#getPzbGU()
	 * @see #getSiteplanState()
	 * @generated
	 */
	EReference getSiteplanState_PzbGU();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link org.eclipse.set.model.siteplan.SiteplanState#getRoutes
	 * <em>Routes</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list
	 *         '<em>Routes</em>'.
	 * @see org.eclipse.set.model.siteplan.SiteplanState#getRoutes()
	 * @see #getSiteplanState()
	 * @generated
	 */
	EReference getSiteplanState_Routes();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link org.eclipse.set.model.siteplan.SiteplanState#getStations
	 * <em>Stations</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list
	 *         '<em>Stations</em>'.
	 * @see org.eclipse.set.model.siteplan.SiteplanState#getStations()
	 * @see #getSiteplanState()
	 * @generated
	 */
	EReference getSiteplanState_Stations();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link org.eclipse.set.model.siteplan.SiteplanState#getTrackLock
	 * <em>Track Lock</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Track
	 *         Lock</em>'.
	 * @see org.eclipse.set.model.siteplan.SiteplanState#getTrackLock()
	 * @see #getSiteplanState()
	 * @generated
	 */
	EReference getSiteplanState_TrackLock();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link org.eclipse.set.model.siteplan.SiteplanState#getErrors
	 * <em>Errors</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list
	 *         '<em>Errors</em>'.
	 * @see org.eclipse.set.model.siteplan.SiteplanState#getErrors()
	 * @see #getSiteplanState()
	 * @generated
	 */
	EReference getSiteplanState_Errors();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link org.eclipse.set.model.siteplan.SiteplanState#getTrackClosures
	 * <em>Track Closures</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Track
	 *         Closures</em>'.
	 * @see org.eclipse.set.model.siteplan.SiteplanState#getTrackClosures()
	 * @see #getSiteplanState()
	 * @generated
	 */
	EReference getSiteplanState_TrackClosures();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link org.eclipse.set.model.siteplan.SiteplanState#getExternalElementControls
	 * <em>External Element Controls</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>External
	 *         Element Controls</em>'.
	 * @see org.eclipse.set.model.siteplan.SiteplanState#getExternalElementControls()
	 * @see #getSiteplanState()
	 * @generated
	 */
	EReference getSiteplanState_ExternalElementControls();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link org.eclipse.set.model.siteplan.SiteplanState#getLockkeys
	 * <em>Lockkeys</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list
	 *         '<em>Lockkeys</em>'.
	 * @see org.eclipse.set.model.siteplan.SiteplanState#getLockkeys()
	 * @see #getSiteplanState()
	 * @generated
	 */
	EReference getSiteplanState_Lockkeys();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link org.eclipse.set.model.siteplan.SiteplanState#getCants
	 * <em>Cants</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list
	 *         '<em>Cants</em>'.
	 * @see org.eclipse.set.model.siteplan.SiteplanState#getCants()
	 * @see #getSiteplanState()
	 * @generated
	 */
	EReference getSiteplanState_Cants();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link org.eclipse.set.model.siteplan.SiteplanState#getUnknownObjects
	 * <em>Unknown Objects</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Unknown
	 *         Objects</em>'.
	 * @see org.eclipse.set.model.siteplan.SiteplanState#getUnknownObjects()
	 * @see #getSiteplanState()
	 * @generated
	 */
	EReference getSiteplanState_UnknownObjects();

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.set.model.siteplan.SiteplanObject <em>Object</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Object</em>'.
	 * @see org.eclipse.set.model.siteplan.SiteplanObject
	 * @generated
	 */
	EClass getSiteplanObject();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.set.model.siteplan.SiteplanObject#getGuid
	 * <em>Guid</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Guid</em>'.
	 * @see org.eclipse.set.model.siteplan.SiteplanObject#getGuid()
	 * @see #getSiteplanObject()
	 * @generated
	 */
	EAttribute getSiteplanObject_Guid();

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.set.model.siteplan.PositionedObject <em>Positioned
	 * Object</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Positioned Object</em>'.
	 * @see org.eclipse.set.model.siteplan.PositionedObject
	 * @generated
	 */
	EClass getPositionedObject();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.eclipse.set.model.siteplan.PositionedObject#getPosition
	 * <em>Position</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference
	 *         '<em>Position</em>'.
	 * @see org.eclipse.set.model.siteplan.PositionedObject#getPosition()
	 * @see #getPositionedObject()
	 * @generated
	 */
	EReference getPositionedObject_Position();

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.set.model.siteplan.Coordinate <em>Coordinate</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Coordinate</em>'.
	 * @see org.eclipse.set.model.siteplan.Coordinate
	 * @generated
	 */
	EClass getCoordinate();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.set.model.siteplan.Coordinate#getX <em>X</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>X</em>'.
	 * @see org.eclipse.set.model.siteplan.Coordinate#getX()
	 * @see #getCoordinate()
	 * @generated
	 */
	EAttribute getCoordinate_X();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.set.model.siteplan.Coordinate#getY <em>Y</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Y</em>'.
	 * @see org.eclipse.set.model.siteplan.Coordinate#getY()
	 * @see #getCoordinate()
	 * @generated
	 */
	EAttribute getCoordinate_Y();

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.set.model.siteplan.Position <em>Position</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Position</em>'.
	 * @see org.eclipse.set.model.siteplan.Position
	 * @generated
	 */
	EClass getPosition();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.set.model.siteplan.Position#getRotation
	 * <em>Rotation</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Rotation</em>'.
	 * @see org.eclipse.set.model.siteplan.Position#getRotation()
	 * @see #getPosition()
	 * @generated
	 */
	EAttribute getPosition_Rotation();

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.set.model.siteplan.RouteObject <em>Route
	 * Object</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Route Object</em>'.
	 * @see org.eclipse.set.model.siteplan.RouteObject
	 * @generated
	 */
	EClass getRouteObject();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link org.eclipse.set.model.siteplan.RouteObject#getRouteLocations
	 * <em>Route Locations</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Route
	 *         Locations</em>'.
	 * @see org.eclipse.set.model.siteplan.RouteObject#getRouteLocations()
	 * @see #getRouteObject()
	 * @generated
	 */
	EReference getRouteObject_RouteLocations();

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.set.model.siteplan.RouteLocation <em>Route
	 * Location</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Route Location</em>'.
	 * @see org.eclipse.set.model.siteplan.RouteLocation
	 * @generated
	 */
	EClass getRouteLocation();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.set.model.siteplan.RouteLocation#getKm <em>Km</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Km</em>'.
	 * @see org.eclipse.set.model.siteplan.RouteLocation#getKm()
	 * @see #getRouteLocation()
	 * @generated
	 */
	EAttribute getRouteLocation_Km();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.set.model.siteplan.RouteLocation#getRoute
	 * <em>Route</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Route</em>'.
	 * @see org.eclipse.set.model.siteplan.RouteLocation#getRoute()
	 * @see #getRouteLocation()
	 * @generated
	 */
	EAttribute getRouteLocation_Route();

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.set.model.siteplan.SignalMount <em>Signal
	 * Mount</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Signal Mount</em>'.
	 * @see org.eclipse.set.model.siteplan.SignalMount
	 * @generated
	 */
	EClass getSignalMount();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link org.eclipse.set.model.siteplan.SignalMount#getAttachedSignals
	 * <em>Attached Signals</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return the meta object for the containment reference list '<em>Attached
	 *         Signals</em>'.
	 * @see org.eclipse.set.model.siteplan.SignalMount#getAttachedSignals()
	 * @see #getSignalMount()
	 * @generated
	 */
	EReference getSignalMount_AttachedSignals();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.set.model.siteplan.SignalMount#getMountType <em>Mount
	 * Type</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Mount Type</em>'.
	 * @see org.eclipse.set.model.siteplan.SignalMount#getMountType()
	 * @see #getSignalMount()
	 * @generated
	 */
	EAttribute getSignalMount_MountType();

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.set.model.siteplan.Signal <em>Signal</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Signal</em>'.
	 * @see org.eclipse.set.model.siteplan.Signal
	 * @generated
	 */
	EClass getSignal();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.set.model.siteplan.Signal#getRole <em>Role</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Role</em>'.
	 * @see org.eclipse.set.model.siteplan.Signal#getRole()
	 * @see #getSignal()
	 * @generated
	 */
	EAttribute getSignal_Role();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.set.model.siteplan.Signal#getSystem
	 * <em>System</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>System</em>'.
	 * @see org.eclipse.set.model.siteplan.Signal#getSystem()
	 * @see #getSignal()
	 * @generated
	 */
	EAttribute getSignal_System();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link org.eclipse.set.model.siteplan.Signal#getScreen
	 * <em>Screen</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list
	 *         '<em>Screen</em>'.
	 * @see org.eclipse.set.model.siteplan.Signal#getScreen()
	 * @see #getSignal()
	 * @generated
	 */
	EReference getSignal_Screen();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.eclipse.set.model.siteplan.Signal#getLabel <em>Label</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Label</em>'.
	 * @see org.eclipse.set.model.siteplan.Signal#getLabel()
	 * @see #getSignal()
	 * @generated
	 */
	EReference getSignal_Label();

	/**
	 * Returns the meta object for the attribute list
	 * '{@link org.eclipse.set.model.siteplan.Signal#getLateralDistance
	 * <em>Lateral Distance</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return the meta object for the attribute list '<em>Lateral
	 *         Distance</em>'.
	 * @see org.eclipse.set.model.siteplan.Signal#getLateralDistance()
	 * @see #getSignal()
	 * @generated
	 */
	EAttribute getSignal_LateralDistance();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.set.model.siteplan.Signal#getSignalDirection
	 * <em>Signal Direction</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return the meta object for the attribute '<em>Signal Direction</em>'.
	 * @see org.eclipse.set.model.siteplan.Signal#getSignalDirection()
	 * @see #getSignal()
	 * @generated
	 */
	EAttribute getSignal_SignalDirection();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.eclipse.set.model.siteplan.Signal#getMountPosition <em>Mount
	 * Position</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Mount
	 *         Position</em>'.
	 * @see org.eclipse.set.model.siteplan.Signal#getMountPosition()
	 * @see #getSignal()
	 * @generated
	 */
	EReference getSignal_MountPosition();

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.set.model.siteplan.SignalScreen <em>Signal
	 * Screen</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Signal Screen</em>'.
	 * @see org.eclipse.set.model.siteplan.SignalScreen
	 * @generated
	 */
	EClass getSignalScreen();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.set.model.siteplan.SignalScreen#getScreen
	 * <em>Screen</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Screen</em>'.
	 * @see org.eclipse.set.model.siteplan.SignalScreen#getScreen()
	 * @see #getSignalScreen()
	 * @generated
	 */
	EAttribute getSignalScreen_Screen();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.set.model.siteplan.SignalScreen#isSwitched
	 * <em>Switched</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Switched</em>'.
	 * @see org.eclipse.set.model.siteplan.SignalScreen#isSwitched()
	 * @see #getSignalScreen()
	 * @generated
	 */
	EAttribute getSignalScreen_Switched();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.set.model.siteplan.SignalScreen#getFrameType
	 * <em>Frame Type</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Frame Type</em>'.
	 * @see org.eclipse.set.model.siteplan.SignalScreen#getFrameType()
	 * @see #getSignalScreen()
	 * @generated
	 */
	EAttribute getSignalScreen_FrameType();

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.set.model.siteplan.Label <em>Label</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Label</em>'.
	 * @see org.eclipse.set.model.siteplan.Label
	 * @generated
	 */
	EClass getLabel();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.set.model.siteplan.Label#getText <em>Text</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Text</em>'.
	 * @see org.eclipse.set.model.siteplan.Label#getText()
	 * @see #getLabel()
	 * @generated
	 */
	EAttribute getLabel_Text();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.set.model.siteplan.Label#isOrientationInverted
	 * <em>Orientation Inverted</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Orientation
	 *         Inverted</em>'.
	 * @see org.eclipse.set.model.siteplan.Label#isOrientationInverted()
	 * @see #getLabel()
	 * @generated
	 */
	EAttribute getLabel_OrientationInverted();

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.set.model.siteplan.TrackSwitch <em>Track
	 * Switch</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Track Switch</em>'.
	 * @see org.eclipse.set.model.siteplan.TrackSwitch
	 * @generated
	 */
	EClass getTrackSwitch();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.set.model.siteplan.TrackSwitch#getDesign
	 * <em>Design</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Design</em>'.
	 * @see org.eclipse.set.model.siteplan.TrackSwitch#getDesign()
	 * @see #getTrackSwitch()
	 * @generated
	 */
	EAttribute getTrackSwitch_Design();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link org.eclipse.set.model.siteplan.TrackSwitch#getComponents
	 * <em>Components</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list
	 *         '<em>Components</em>'.
	 * @see org.eclipse.set.model.siteplan.TrackSwitch#getComponents()
	 * @see #getTrackSwitch()
	 * @generated
	 */
	EReference getTrackSwitch_Components();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link org.eclipse.set.model.siteplan.TrackSwitch#getContinuousSegments
	 * <em>Continuous Segments</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return the meta object for the containment reference list
	 *         '<em>Continuous Segments</em>'.
	 * @see org.eclipse.set.model.siteplan.TrackSwitch#getContinuousSegments()
	 * @see #getTrackSwitch()
	 * @generated
	 */
	EReference getTrackSwitch_ContinuousSegments();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.set.model.siteplan.TrackSwitch#getSwitchType
	 * <em>Switch Type</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Switch Type</em>'.
	 * @see org.eclipse.set.model.siteplan.TrackSwitch#getSwitchType()
	 * @see #getTrackSwitch()
	 * @generated
	 */
	EAttribute getTrackSwitch_SwitchType();

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.set.model.siteplan.TrackSwitchComponent <em>Track
	 * Switch Component</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Track Switch Component</em>'.
	 * @see org.eclipse.set.model.siteplan.TrackSwitchComponent
	 * @generated
	 */
	EClass getTrackSwitchComponent();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.set.model.siteplan.TrackSwitchComponent#getPreferredLocation
	 * <em>Preferred Location</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return the meta object for the attribute '<em>Preferred Location</em>'.
	 * @see org.eclipse.set.model.siteplan.TrackSwitchComponent#getPreferredLocation()
	 * @see #getTrackSwitchComponent()
	 * @generated
	 */
	EAttribute getTrackSwitchComponent_PreferredLocation();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.set.model.siteplan.TrackSwitchComponent#getPointDetectorCount
	 * <em>Point Detector Count</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Point Detector
	 *         Count</em>'.
	 * @see org.eclipse.set.model.siteplan.TrackSwitchComponent#getPointDetectorCount()
	 * @see #getTrackSwitchComponent()
	 * @generated
	 */
	EAttribute getTrackSwitchComponent_PointDetectorCount();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.eclipse.set.model.siteplan.TrackSwitchComponent#getStart
	 * <em>Start</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Start</em>'.
	 * @see org.eclipse.set.model.siteplan.TrackSwitchComponent#getStart()
	 * @see #getTrackSwitchComponent()
	 * @generated
	 */
	EReference getTrackSwitchComponent_Start();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.eclipse.set.model.siteplan.TrackSwitchComponent#getLabelPosition
	 * <em>Label Position</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Label
	 *         Position</em>'.
	 * @see org.eclipse.set.model.siteplan.TrackSwitchComponent#getLabelPosition()
	 * @see #getTrackSwitchComponent()
	 * @generated
	 */
	EReference getTrackSwitchComponent_LabelPosition();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.eclipse.set.model.siteplan.TrackSwitchComponent#getLabel
	 * <em>Label</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Label</em>'.
	 * @see org.eclipse.set.model.siteplan.TrackSwitchComponent#getLabel()
	 * @see #getTrackSwitchComponent()
	 * @generated
	 */
	EReference getTrackSwitchComponent_Label();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.set.model.siteplan.TrackSwitchComponent#getOperatingMode
	 * <em>Operating Mode</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Operating Mode</em>'.
	 * @see org.eclipse.set.model.siteplan.TrackSwitchComponent#getOperatingMode()
	 * @see #getTrackSwitchComponent()
	 * @generated
	 */
	EAttribute getTrackSwitchComponent_OperatingMode();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.eclipse.set.model.siteplan.TrackSwitchComponent#getMainLeg
	 * <em>Main Leg</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Main
	 *         Leg</em>'.
	 * @see org.eclipse.set.model.siteplan.TrackSwitchComponent#getMainLeg()
	 * @see #getTrackSwitchComponent()
	 * @generated
	 */
	EReference getTrackSwitchComponent_MainLeg();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.eclipse.set.model.siteplan.TrackSwitchComponent#getSideLeg
	 * <em>Side Leg</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Side
	 *         Leg</em>'.
	 * @see org.eclipse.set.model.siteplan.TrackSwitchComponent#getSideLeg()
	 * @see #getTrackSwitchComponent()
	 * @generated
	 */
	EReference getTrackSwitchComponent_SideLeg();

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.set.model.siteplan.ContinuousTrackSegment
	 * <em>Continuous Track Segment</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Continuous Track Segment</em>'.
	 * @see org.eclipse.set.model.siteplan.ContinuousTrackSegment
	 * @generated
	 */
	EClass getContinuousTrackSegment();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.eclipse.set.model.siteplan.ContinuousTrackSegment#getStart
	 * <em>Start</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Start</em>'.
	 * @see org.eclipse.set.model.siteplan.ContinuousTrackSegment#getStart()
	 * @see #getContinuousTrackSegment()
	 * @generated
	 */
	EReference getContinuousTrackSegment_Start();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.eclipse.set.model.siteplan.ContinuousTrackSegment#getEnd
	 * <em>End</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>End</em>'.
	 * @see org.eclipse.set.model.siteplan.ContinuousTrackSegment#getEnd()
	 * @see #getContinuousTrackSegment()
	 * @generated
	 */
	EReference getContinuousTrackSegment_End();

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.set.model.siteplan.Track <em>Track</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Track</em>'.
	 * @see org.eclipse.set.model.siteplan.Track
	 * @generated
	 */
	EClass getTrack();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link org.eclipse.set.model.siteplan.Track#getSections
	 * <em>Sections</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list
	 *         '<em>Sections</em>'.
	 * @see org.eclipse.set.model.siteplan.Track#getSections()
	 * @see #getTrack()
	 * @generated
	 */
	EReference getTrack_Sections();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link org.eclipse.set.model.siteplan.Track#getDesignations
	 * <em>Designations</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list
	 *         '<em>Designations</em>'.
	 * @see org.eclipse.set.model.siteplan.Track#getDesignations()
	 * @see #getTrack()
	 * @generated
	 */
	EReference getTrack_Designations();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.eclipse.set.model.siteplan.Track#getStartCoordinate <em>Start
	 * Coordinate</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Start
	 *         Coordinate</em>'.
	 * @see org.eclipse.set.model.siteplan.Track#getStartCoordinate()
	 * @see #getTrack()
	 * @generated
	 */
	EReference getTrack_StartCoordinate();

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.set.model.siteplan.TrackSection <em>Track
	 * Section</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Track Section</em>'.
	 * @see org.eclipse.set.model.siteplan.TrackSection
	 * @generated
	 */
	EClass getTrackSection();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.set.model.siteplan.TrackSection#getShape
	 * <em>Shape</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Shape</em>'.
	 * @see org.eclipse.set.model.siteplan.TrackSection#getShape()
	 * @see #getTrackSection()
	 * @generated
	 */
	EAttribute getTrackSection_Shape();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link org.eclipse.set.model.siteplan.TrackSection#getSegments
	 * <em>Segments</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list
	 *         '<em>Segments</em>'.
	 * @see org.eclipse.set.model.siteplan.TrackSection#getSegments()
	 * @see #getTrackSection()
	 * @generated
	 */
	EReference getTrackSection_Segments();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.set.model.siteplan.TrackSection#getColor
	 * <em>Color</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Color</em>'.
	 * @see org.eclipse.set.model.siteplan.TrackSection#getColor()
	 * @see #getTrackSection()
	 * @generated
	 */
	EAttribute getTrackSection_Color();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.eclipse.set.model.siteplan.TrackSection#getStartCoordinate
	 * <em>Start Coordinate</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return the meta object for the containment reference '<em>Start
	 *         Coordinate</em>'.
	 * @see org.eclipse.set.model.siteplan.TrackSection#getStartCoordinate()
	 * @see #getTrackSection()
	 * @generated
	 */
	EReference getTrackSection_StartCoordinate();

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.set.model.siteplan.TrackSegment <em>Track
	 * Segment</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Track Segment</em>'.
	 * @see org.eclipse.set.model.siteplan.TrackSegment
	 * @generated
	 */
	EClass getTrackSegment();

	/**
	 * Returns the meta object for the attribute list
	 * '{@link org.eclipse.set.model.siteplan.TrackSegment#getType
	 * <em>Type</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute list '<em>Type</em>'.
	 * @see org.eclipse.set.model.siteplan.TrackSegment#getType()
	 * @see #getTrackSegment()
	 * @generated
	 */
	EAttribute getTrackSegment_Type();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link org.eclipse.set.model.siteplan.TrackSegment#getPositions
	 * <em>Positions</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list
	 *         '<em>Positions</em>'.
	 * @see org.eclipse.set.model.siteplan.TrackSegment#getPositions()
	 * @see #getTrackSegment()
	 * @generated
	 */
	EReference getTrackSegment_Positions();

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.set.model.siteplan.FMAComponent <em>FMA
	 * Component</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>FMA Component</em>'.
	 * @see org.eclipse.set.model.siteplan.FMAComponent
	 * @generated
	 */
	EClass getFMAComponent();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.eclipse.set.model.siteplan.FMAComponent#getLabel
	 * <em>Label</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Label</em>'.
	 * @see org.eclipse.set.model.siteplan.FMAComponent#getLabel()
	 * @see #getFMAComponent()
	 * @generated
	 */
	EReference getFMAComponent_Label();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.set.model.siteplan.FMAComponent#getType
	 * <em>Type</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see org.eclipse.set.model.siteplan.FMAComponent#getType()
	 * @see #getFMAComponent()
	 * @generated
	 */
	EAttribute getFMAComponent_Type();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.set.model.siteplan.FMAComponent#isRightSide <em>Right
	 * Side</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Right Side</em>'.
	 * @see org.eclipse.set.model.siteplan.FMAComponent#isRightSide()
	 * @see #getFMAComponent()
	 * @generated
	 */
	EAttribute getFMAComponent_RightSide();

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.set.model.siteplan.Route <em>Route</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Route</em>'.
	 * @see org.eclipse.set.model.siteplan.Route
	 * @generated
	 */
	EClass getRoute();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link org.eclipse.set.model.siteplan.Route#getSections
	 * <em>Sections</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list
	 *         '<em>Sections</em>'.
	 * @see org.eclipse.set.model.siteplan.Route#getSections()
	 * @see #getRoute()
	 * @generated
	 */
	EReference getRoute_Sections();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link org.eclipse.set.model.siteplan.Route#getMarkers
	 * <em>Markers</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list
	 *         '<em>Markers</em>'.
	 * @see org.eclipse.set.model.siteplan.Route#getMarkers()
	 * @see #getRoute()
	 * @generated
	 */
	EReference getRoute_Markers();

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.set.model.siteplan.RouteSection <em>Route
	 * Section</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Route Section</em>'.
	 * @see org.eclipse.set.model.siteplan.RouteSection
	 * @generated
	 */
	EClass getRouteSection();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.set.model.siteplan.RouteSection#getGuid
	 * <em>Guid</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Guid</em>'.
	 * @see org.eclipse.set.model.siteplan.RouteSection#getGuid()
	 * @see #getRouteSection()
	 * @generated
	 */
	EAttribute getRouteSection_Guid();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.set.model.siteplan.RouteSection#getShape
	 * <em>Shape</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Shape</em>'.
	 * @see org.eclipse.set.model.siteplan.RouteSection#getShape()
	 * @see #getRouteSection()
	 * @generated
	 */
	EAttribute getRouteSection_Shape();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link org.eclipse.set.model.siteplan.RouteSection#getPositions
	 * <em>Positions</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list
	 *         '<em>Positions</em>'.
	 * @see org.eclipse.set.model.siteplan.RouteSection#getPositions()
	 * @see #getRouteSection()
	 * @generated
	 */
	EReference getRouteSection_Positions();

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.set.model.siteplan.KMMarker <em>KM Marker</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>KM Marker</em>'.
	 * @see org.eclipse.set.model.siteplan.KMMarker
	 * @generated
	 */
	EClass getKMMarker();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.eclipse.set.model.siteplan.KMMarker#getPosition
	 * <em>Position</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference
	 *         '<em>Position</em>'.
	 * @see org.eclipse.set.model.siteplan.KMMarker#getPosition()
	 * @see #getKMMarker()
	 * @generated
	 */
	EReference getKMMarker_Position();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.set.model.siteplan.KMMarker#getValue
	 * <em>Value</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.set.model.siteplan.KMMarker#getValue()
	 * @see #getKMMarker()
	 * @generated
	 */
	EAttribute getKMMarker_Value();

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.set.model.siteplan.TrackSwitchEndMarker <em>Track
	 * Switch End Marker</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Track Switch End Marker</em>'.
	 * @see org.eclipse.set.model.siteplan.TrackSwitchEndMarker
	 * @generated
	 */
	EClass getTrackSwitchEndMarker();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.eclipse.set.model.siteplan.TrackSwitchEndMarker#getLegACoordinate
	 * <em>Leg ACoordinate</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Leg
	 *         ACoordinate</em>'.
	 * @see org.eclipse.set.model.siteplan.TrackSwitchEndMarker#getLegACoordinate()
	 * @see #getTrackSwitchEndMarker()
	 * @generated
	 */
	EReference getTrackSwitchEndMarker_LegACoordinate();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.eclipse.set.model.siteplan.TrackSwitchEndMarker#getLegBCoordinate
	 * <em>Leg BCoordinate</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Leg
	 *         BCoordinate</em>'.
	 * @see org.eclipse.set.model.siteplan.TrackSwitchEndMarker#getLegBCoordinate()
	 * @see #getTrackSwitchEndMarker()
	 * @generated
	 */
	EReference getTrackSwitchEndMarker_LegBCoordinate();

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.set.model.siteplan.Error <em>Error</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Error</em>'.
	 * @see org.eclipse.set.model.siteplan.Error
	 * @generated
	 */
	EClass getError();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.eclipse.set.model.siteplan.Error#getPosition
	 * <em>Position</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference
	 *         '<em>Position</em>'.
	 * @see org.eclipse.set.model.siteplan.Error#getPosition()
	 * @see #getError()
	 * @generated
	 */
	EReference getError_Position();

	/**
	 * Returns the meta object for the attribute list
	 * '{@link org.eclipse.set.model.siteplan.Error#getRelevantGUIDs
	 * <em>Relevant GUI Ds</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute list '<em>Relevant GUI
	 *         Ds</em>'.
	 * @see org.eclipse.set.model.siteplan.Error#getRelevantGUIDs()
	 * @see #getError()
	 * @generated
	 */
	EAttribute getError_RelevantGUIDs();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.set.model.siteplan.Error#getMessage
	 * <em>Message</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Message</em>'.
	 * @see org.eclipse.set.model.siteplan.Error#getMessage()
	 * @see #getError()
	 * @generated
	 */
	EAttribute getError_Message();

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.set.model.siteplan.PZB <em>PZB</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>PZB</em>'.
	 * @see org.eclipse.set.model.siteplan.PZB
	 * @generated
	 */
	EClass getPZB();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.set.model.siteplan.PZB#getType <em>Type</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see org.eclipse.set.model.siteplan.PZB#getType()
	 * @see #getPZB()
	 * @generated
	 */
	EAttribute getPZB_Type();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.set.model.siteplan.PZB#getElement <em>Element</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Element</em>'.
	 * @see org.eclipse.set.model.siteplan.PZB#getElement()
	 * @see #getPZB()
	 * @generated
	 */
	EAttribute getPZB_Element();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.set.model.siteplan.PZB#isRightSide <em>Right
	 * Side</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Right Side</em>'.
	 * @see org.eclipse.set.model.siteplan.PZB#isRightSide()
	 * @see #getPZB()
	 * @generated
	 */
	EAttribute getPZB_RightSide();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.set.model.siteplan.PZB#getEffectivity
	 * <em>Effectivity</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Effectivity</em>'.
	 * @see org.eclipse.set.model.siteplan.PZB#getEffectivity()
	 * @see #getPZB()
	 * @generated
	 */
	EAttribute getPZB_Effectivity();

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.set.model.siteplan.PZBGU <em>PZBGU</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>PZBGU</em>'.
	 * @see org.eclipse.set.model.siteplan.PZBGU
	 * @generated
	 */
	EClass getPZBGU();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link org.eclipse.set.model.siteplan.PZBGU#getPzbs <em>Pzbs</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list
	 *         '<em>Pzbs</em>'.
	 * @see org.eclipse.set.model.siteplan.PZBGU#getPzbs()
	 * @see #getPZBGU()
	 * @generated
	 */
	EReference getPZBGU_Pzbs();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.set.model.siteplan.PZBGU#getLength <em>Length</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Length</em>'.
	 * @see org.eclipse.set.model.siteplan.PZBGU#getLength()
	 * @see #getPZBGU()
	 * @generated
	 */
	EAttribute getPZBGU_Length();

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.set.model.siteplan.TrackDesignation <em>Track
	 * Designation</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Track Designation</em>'.
	 * @see org.eclipse.set.model.siteplan.TrackDesignation
	 * @generated
	 */
	EClass getTrackDesignation();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.set.model.siteplan.TrackDesignation#getName
	 * <em>Name</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.set.model.siteplan.TrackDesignation#getName()
	 * @see #getTrackDesignation()
	 * @generated
	 */
	EAttribute getTrackDesignation_Name();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.eclipse.set.model.siteplan.TrackDesignation#getPosition
	 * <em>Position</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference
	 *         '<em>Position</em>'.
	 * @see org.eclipse.set.model.siteplan.TrackDesignation#getPosition()
	 * @see #getTrackDesignation()
	 * @generated
	 */
	EReference getTrackDesignation_Position();

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.set.model.siteplan.TrackSwitchLeg <em>Track Switch
	 * Leg</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Track Switch Leg</em>'.
	 * @see org.eclipse.set.model.siteplan.TrackSwitchLeg
	 * @generated
	 */
	EClass getTrackSwitchLeg();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.set.model.siteplan.TrackSwitchLeg#getConnection
	 * <em>Connection</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Connection</em>'.
	 * @see org.eclipse.set.model.siteplan.TrackSwitchLeg#getConnection()
	 * @see #getTrackSwitchLeg()
	 * @generated
	 */
	EAttribute getTrackSwitchLeg_Connection();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link org.eclipse.set.model.siteplan.TrackSwitchLeg#getCoordinates
	 * <em>Coordinates</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list
	 *         '<em>Coordinates</em>'.
	 * @see org.eclipse.set.model.siteplan.TrackSwitchLeg#getCoordinates()
	 * @see #getTrackSwitchLeg()
	 * @generated
	 */
	EReference getTrackSwitchLeg_Coordinates();

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.set.model.siteplan.Station <em>Station</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Station</em>'.
	 * @see org.eclipse.set.model.siteplan.Station
	 * @generated
	 */
	EClass getStation();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link org.eclipse.set.model.siteplan.Station#getPlatforms
	 * <em>Platforms</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list
	 *         '<em>Platforms</em>'.
	 * @see org.eclipse.set.model.siteplan.Station#getPlatforms()
	 * @see #getStation()
	 * @generated
	 */
	EReference getStation_Platforms();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.eclipse.set.model.siteplan.Station#getLabel <em>Label</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Label</em>'.
	 * @see org.eclipse.set.model.siteplan.Station#getLabel()
	 * @see #getStation()
	 * @generated
	 */
	EReference getStation_Label();

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.set.model.siteplan.Platform <em>Platform</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Platform</em>'.
	 * @see org.eclipse.set.model.siteplan.Platform
	 * @generated
	 */
	EClass getPlatform();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.set.model.siteplan.Platform#getGuid <em>Guid</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Guid</em>'.
	 * @see org.eclipse.set.model.siteplan.Platform#getGuid()
	 * @see #getPlatform()
	 * @generated
	 */
	EAttribute getPlatform_Guid();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.eclipse.set.model.siteplan.Platform#getLabel
	 * <em>Label</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Label</em>'.
	 * @see org.eclipse.set.model.siteplan.Platform#getLabel()
	 * @see #getPlatform()
	 * @generated
	 */
	EReference getPlatform_Label();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.eclipse.set.model.siteplan.Platform#getLabelPosition
	 * <em>Label Position</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Label
	 *         Position</em>'.
	 * @see org.eclipse.set.model.siteplan.Platform#getLabelPosition()
	 * @see #getPlatform()
	 * @generated
	 */
	EReference getPlatform_LabelPosition();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link org.eclipse.set.model.siteplan.Platform#getPoints
	 * <em>Points</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list
	 *         '<em>Points</em>'.
	 * @see org.eclipse.set.model.siteplan.Platform#getPoints()
	 * @see #getPlatform()
	 * @generated
	 */
	EReference getPlatform_Points();

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.set.model.siteplan.TrackLock <em>Track Lock</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Track Lock</em>'.
	 * @see org.eclipse.set.model.siteplan.TrackLock
	 * @generated
	 */
	EClass getTrackLock();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link org.eclipse.set.model.siteplan.TrackLock#getComponents
	 * <em>Components</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list
	 *         '<em>Components</em>'.
	 * @see org.eclipse.set.model.siteplan.TrackLock#getComponents()
	 * @see #getTrackLock()
	 * @generated
	 */
	EReference getTrackLock_Components();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.set.model.siteplan.TrackLock#getPreferredLocation
	 * <em>Preferred Location</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return the meta object for the attribute '<em>Preferred Location</em>'.
	 * @see org.eclipse.set.model.siteplan.TrackLock#getPreferredLocation()
	 * @see #getTrackLock()
	 * @generated
	 */
	EAttribute getTrackLock_PreferredLocation();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.set.model.siteplan.TrackLock#getOperatingMode
	 * <em>Operating Mode</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Operating Mode</em>'.
	 * @see org.eclipse.set.model.siteplan.TrackLock#getOperatingMode()
	 * @see #getTrackLock()
	 * @generated
	 */
	EAttribute getTrackLock_OperatingMode();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.eclipse.set.model.siteplan.TrackLock#getLabel
	 * <em>Label</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Label</em>'.
	 * @see org.eclipse.set.model.siteplan.TrackLock#getLabel()
	 * @see #getTrackLock()
	 * @generated
	 */
	EReference getTrackLock_Label();

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.set.model.siteplan.TrackLockComponent <em>Track Lock
	 * Component</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Track Lock Component</em>'.
	 * @see org.eclipse.set.model.siteplan.TrackLockComponent
	 * @generated
	 */
	EClass getTrackLockComponent();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.set.model.siteplan.TrackLockComponent#getTrackLockSignal
	 * <em>Track Lock Signal</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return the meta object for the attribute '<em>Track Lock Signal</em>'.
	 * @see org.eclipse.set.model.siteplan.TrackLockComponent#getTrackLockSignal()
	 * @see #getTrackLockComponent()
	 * @generated
	 */
	EAttribute getTrackLockComponent_TrackLockSignal();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.set.model.siteplan.TrackLockComponent#getEjectionDirection
	 * <em>Ejection Direction</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return the meta object for the attribute '<em>Ejection Direction</em>'.
	 * @see org.eclipse.set.model.siteplan.TrackLockComponent#getEjectionDirection()
	 * @see #getTrackLockComponent()
	 * @generated
	 */
	EAttribute getTrackLockComponent_EjectionDirection();

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.set.model.siteplan.ObjectManagement <em>Object
	 * Management</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Object Management</em>'.
	 * @see org.eclipse.set.model.siteplan.ObjectManagement
	 * @generated
	 */
	EClass getObjectManagement();

	/**
	 * Returns the meta object for the attribute list
	 * '{@link org.eclipse.set.model.siteplan.ObjectManagement#getPlanningObjectIDs
	 * <em>Planning Object IDs</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return the meta object for the attribute list '<em>Planning Object
	 *         IDs</em>'.
	 * @see org.eclipse.set.model.siteplan.ObjectManagement#getPlanningObjectIDs()
	 * @see #getObjectManagement()
	 * @generated
	 */
	EAttribute getObjectManagement_PlanningObjectIDs();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.set.model.siteplan.ObjectManagement#getPlanningGroupID
	 * <em>Planning Group ID</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return the meta object for the attribute '<em>Planning Group ID</em>'.
	 * @see org.eclipse.set.model.siteplan.ObjectManagement#getPlanningGroupID()
	 * @see #getObjectManagement()
	 * @generated
	 */
	EAttribute getObjectManagement_PlanningGroupID();

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.set.model.siteplan.TrackClose <em>Track Close</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Track Close</em>'.
	 * @see org.eclipse.set.model.siteplan.TrackClose
	 * @generated
	 */
	EClass getTrackClose();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.set.model.siteplan.TrackClose#getTrackCloseType
	 * <em>Track Close Type</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return the meta object for the attribute '<em>Track Close Type</em>'.
	 * @see org.eclipse.set.model.siteplan.TrackClose#getTrackCloseType()
	 * @see #getTrackClose()
	 * @generated
	 */
	EAttribute getTrackClose_TrackCloseType();

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.set.model.siteplan.ExternalElementControl
	 * <em>External Element Control</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the meta object for class '<em>External Element Control</em>'.
	 * @see org.eclipse.set.model.siteplan.ExternalElementControl
	 * @generated
	 */
	EClass getExternalElementControl();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.set.model.siteplan.ExternalElementControl#getControlArt
	 * <em>Control Art</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Control Art</em>'.
	 * @see org.eclipse.set.model.siteplan.ExternalElementControl#getControlArt()
	 * @see #getExternalElementControl()
	 * @generated
	 */
	EAttribute getExternalElementControl_ControlArt();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.set.model.siteplan.ExternalElementControl#getElementType
	 * <em>Element Type</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Element Type</em>'.
	 * @see org.eclipse.set.model.siteplan.ExternalElementControl#getElementType()
	 * @see #getExternalElementControl()
	 * @generated
	 */
	EAttribute getExternalElementControl_ElementType();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.set.model.siteplan.ExternalElementControl#getControlStation
	 * <em>Control Station</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Control Station</em>'.
	 * @see org.eclipse.set.model.siteplan.ExternalElementControl#getControlStation()
	 * @see #getExternalElementControl()
	 * @generated
	 */
	EAttribute getExternalElementControl_ControlStation();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.eclipse.set.model.siteplan.ExternalElementControl#getLabel
	 * <em>Label</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Label</em>'.
	 * @see org.eclipse.set.model.siteplan.ExternalElementControl#getLabel()
	 * @see #getExternalElementControl()
	 * @generated
	 */
	EReference getExternalElementControl_Label();

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.set.model.siteplan.LockKey <em>Lock Key</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Lock Key</em>'.
	 * @see org.eclipse.set.model.siteplan.LockKey
	 * @generated
	 */
	EClass getLockKey();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.eclipse.set.model.siteplan.LockKey#getLabel <em>Label</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Label</em>'.
	 * @see org.eclipse.set.model.siteplan.LockKey#getLabel()
	 * @see #getLockKey()
	 * @generated
	 */
	EReference getLockKey_Label();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.set.model.siteplan.LockKey#getType <em>Type</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see org.eclipse.set.model.siteplan.LockKey#getType()
	 * @see #getLockKey()
	 * @generated
	 */
	EAttribute getLockKey_Type();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.set.model.siteplan.LockKey#isLocked
	 * <em>Locked</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Locked</em>'.
	 * @see org.eclipse.set.model.siteplan.LockKey#isLocked()
	 * @see #getLockKey()
	 * @generated
	 */
	EAttribute getLockKey_Locked();

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.set.model.siteplan.Layoutinfo <em>Layoutinfo</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Layoutinfo</em>'.
	 * @see org.eclipse.set.model.siteplan.Layoutinfo
	 * @generated
	 */
	EClass getLayoutinfo();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.set.model.siteplan.Layoutinfo#getLabel
	 * <em>Label</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Label</em>'.
	 * @see org.eclipse.set.model.siteplan.Layoutinfo#getLabel()
	 * @see #getLayoutinfo()
	 * @generated
	 */
	EAttribute getLayoutinfo_Label();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link org.eclipse.set.model.siteplan.Layoutinfo#getSheetsCut <em>Sheets
	 * Cut</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Sheets
	 *         Cut</em>'.
	 * @see org.eclipse.set.model.siteplan.Layoutinfo#getSheetsCut()
	 * @see #getLayoutinfo()
	 * @generated
	 */
	EReference getLayoutinfo_SheetsCut();

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.set.model.siteplan.SheetCut <em>Sheet Cut</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Sheet Cut</em>'.
	 * @see org.eclipse.set.model.siteplan.SheetCut
	 * @generated
	 */
	EClass getSheetCut();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.set.model.siteplan.SheetCut#getLabel
	 * <em>Label</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Label</em>'.
	 * @see org.eclipse.set.model.siteplan.SheetCut#getLabel()
	 * @see #getSheetCut()
	 * @generated
	 */
	EAttribute getSheetCut_Label();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link org.eclipse.set.model.siteplan.SheetCut#getPolygonDirection
	 * <em>Polygon Direction</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return the meta object for the containment reference list '<em>Polygon
	 *         Direction</em>'.
	 * @see org.eclipse.set.model.siteplan.SheetCut#getPolygonDirection()
	 * @see #getSheetCut()
	 * @generated
	 */
	EReference getSheetCut_PolygonDirection();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link org.eclipse.set.model.siteplan.SheetCut#getPolygon
	 * <em>Polygon</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list
	 *         '<em>Polygon</em>'.
	 * @see org.eclipse.set.model.siteplan.SheetCut#getPolygon()
	 * @see #getSheetCut()
	 * @generated
	 */
	EReference getSheetCut_Polygon();

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.set.model.siteplan.Cant <em>Cant</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Cant</em>'.
	 * @see org.eclipse.set.model.siteplan.Cant
	 * @generated
	 */
	EClass getCant();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.eclipse.set.model.siteplan.Cant#getPointA <em>Point A</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Point A</em>'.
	 * @see org.eclipse.set.model.siteplan.Cant#getPointA()
	 * @see #getCant()
	 * @generated
	 */
	EReference getCant_PointA();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.eclipse.set.model.siteplan.Cant#getPointB <em>Point B</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Point B</em>'.
	 * @see org.eclipse.set.model.siteplan.Cant#getPointB()
	 * @see #getCant()
	 * @generated
	 */
	EReference getCant_PointB();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.set.model.siteplan.Cant#getForm <em>Form</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Form</em>'.
	 * @see org.eclipse.set.model.siteplan.Cant#getForm()
	 * @see #getCant()
	 * @generated
	 */
	EAttribute getCant_Form();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.set.model.siteplan.Cant#getLength <em>Length</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Length</em>'.
	 * @see org.eclipse.set.model.siteplan.Cant#getLength()
	 * @see #getCant()
	 * @generated
	 */
	EAttribute getCant_Length();

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.set.model.siteplan.CantPoint <em>Cant Point</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Cant Point</em>'.
	 * @see org.eclipse.set.model.siteplan.CantPoint
	 * @generated
	 */
	EClass getCantPoint();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.set.model.siteplan.CantPoint#getHeight
	 * <em>Height</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Height</em>'.
	 * @see org.eclipse.set.model.siteplan.CantPoint#getHeight()
	 * @see #getCantPoint()
	 * @generated
	 */
	EAttribute getCantPoint_Height();

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.set.model.siteplan.UnknownPositionedObject
	 * <em>Unknown Positioned Object</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Unknown Positioned Object</em>'.
	 * @see org.eclipse.set.model.siteplan.UnknownPositionedObject
	 * @generated
	 */
	EClass getUnknownPositionedObject();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.set.model.siteplan.UnknownPositionedObject#getObjectType
	 * <em>Object Type</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Object Type</em>'.
	 * @see org.eclipse.set.model.siteplan.UnknownPositionedObject#getObjectType()
	 * @see #getUnknownPositionedObject()
	 * @generated
	 */
	EAttribute getUnknownPositionedObject_ObjectType();

	/**
	 * Returns the meta object for enum
	 * '{@link org.eclipse.set.model.siteplan.SignalMountType <em>Signal Mount
	 * Type</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for enum '<em>Signal Mount Type</em>'.
	 * @see org.eclipse.set.model.siteplan.SignalMountType
	 * @generated
	 */
	EEnum getSignalMountType();

	/**
	 * Returns the meta object for enum
	 * '{@link org.eclipse.set.model.siteplan.SignalRole <em>Signal Role</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for enum '<em>Signal Role</em>'.
	 * @see org.eclipse.set.model.siteplan.SignalRole
	 * @generated
	 */
	EEnum getSignalRole();

	/**
	 * Returns the meta object for enum
	 * '{@link org.eclipse.set.model.siteplan.SignalSystem <em>Signal
	 * System</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for enum '<em>Signal System</em>'.
	 * @see org.eclipse.set.model.siteplan.SignalSystem
	 * @generated
	 */
	EEnum getSignalSystem();

	/**
	 * Returns the meta object for enum
	 * '{@link org.eclipse.set.model.siteplan.MountDirection <em>Mount
	 * Direction</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for enum '<em>Mount Direction</em>'.
	 * @see org.eclipse.set.model.siteplan.MountDirection
	 * @generated
	 */
	EEnum getMountDirection();

	/**
	 * Returns the meta object for enum
	 * '{@link org.eclipse.set.model.siteplan.TurnoutOperatingMode <em>Turnout
	 * Operating Mode</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for enum '<em>Turnout Operating Mode</em>'.
	 * @see org.eclipse.set.model.siteplan.TurnoutOperatingMode
	 * @generated
	 */
	EEnum getTurnoutOperatingMode();

	/**
	 * Returns the meta object for enum
	 * '{@link org.eclipse.set.model.siteplan.TrackShape <em>Track Shape</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for enum '<em>Track Shape</em>'.
	 * @see org.eclipse.set.model.siteplan.TrackShape
	 * @generated
	 */
	EEnum getTrackShape();

	/**
	 * Returns the meta object for enum
	 * '{@link org.eclipse.set.model.siteplan.TrackType <em>Track Type</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for enum '<em>Track Type</em>'.
	 * @see org.eclipse.set.model.siteplan.TrackType
	 * @generated
	 */
	EEnum getTrackType();

	/**
	 * Returns the meta object for enum
	 * '{@link org.eclipse.set.model.siteplan.FMAComponentType <em>FMA Component
	 * Type</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for enum '<em>FMA Component Type</em>'.
	 * @see org.eclipse.set.model.siteplan.FMAComponentType
	 * @generated
	 */
	EEnum getFMAComponentType();

	/**
	 * Returns the meta object for enum
	 * '{@link org.eclipse.set.model.siteplan.PZBType <em>PZB Type</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for enum '<em>PZB Type</em>'.
	 * @see org.eclipse.set.model.siteplan.PZBType
	 * @generated
	 */
	EEnum getPZBType();

	/**
	 * Returns the meta object for enum
	 * '{@link org.eclipse.set.model.siteplan.PZBElement <em>PZB Element</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for enum '<em>PZB Element</em>'.
	 * @see org.eclipse.set.model.siteplan.PZBElement
	 * @generated
	 */
	EEnum getPZBElement();

	/**
	 * Returns the meta object for enum
	 * '{@link org.eclipse.set.model.siteplan.PZBEffectivity <em>PZB
	 * Effectivity</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for enum '<em>PZB Effectivity</em>'.
	 * @see org.eclipse.set.model.siteplan.PZBEffectivity
	 * @generated
	 */
	EEnum getPZBEffectivity();

	/**
	 * Returns the meta object for enum
	 * '{@link org.eclipse.set.model.siteplan.TrackLockLocation <em>Track Lock
	 * Location</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for enum '<em>Track Lock Location</em>'.
	 * @see org.eclipse.set.model.siteplan.TrackLockLocation
	 * @generated
	 */
	EEnum getTrackLockLocation();

	/**
	 * Returns the meta object for enum
	 * '{@link org.eclipse.set.model.siteplan.LeftRight <em>Left Right</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for enum '<em>Left Right</em>'.
	 * @see org.eclipse.set.model.siteplan.LeftRight
	 * @generated
	 */
	EEnum getLeftRight();

	/**
	 * Returns the meta object for enum
	 * '{@link org.eclipse.set.model.siteplan.Direction <em>Direction</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for enum '<em>Direction</em>'.
	 * @see org.eclipse.set.model.siteplan.Direction
	 * @generated
	 */
	EEnum getDirection();

	/**
	 * Returns the meta object for enum
	 * '{@link org.eclipse.set.model.siteplan.TrackCloseType <em>Track Close
	 * Type</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for enum '<em>Track Close Type</em>'.
	 * @see org.eclipse.set.model.siteplan.TrackCloseType
	 * @generated
	 */
	EEnum getTrackCloseType();

	/**
	 * Returns the meta object for enum
	 * '{@link org.eclipse.set.model.siteplan.ExternalElementControlArt
	 * <em>External Element Control Art</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the meta object for enum '<em>External Element Control Art</em>'.
	 * @see org.eclipse.set.model.siteplan.ExternalElementControlArt
	 * @generated
	 */
	EEnum getExternalElementControlArt();

	/**
	 * Returns the meta object for enum
	 * '{@link org.eclipse.set.model.siteplan.ControlStationType <em>Control
	 * Station Type</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for enum '<em>Control Station Type</em>'.
	 * @see org.eclipse.set.model.siteplan.ControlStationType
	 * @generated
	 */
	EEnum getControlStationType();

	/**
	 * Returns the meta object for enum
	 * '{@link org.eclipse.set.model.siteplan.LockKeyType <em>Lock Key
	 * Type</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for enum '<em>Lock Key Type</em>'.
	 * @see org.eclipse.set.model.siteplan.LockKeyType
	 * @generated
	 */
	EEnum getLockKeyType();

	/**
	 * Returns the meta object for enum
	 * '{@link org.eclipse.set.model.siteplan.SwitchType <em>Switch Type</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for enum '<em>Switch Type</em>'.
	 * @see org.eclipse.set.model.siteplan.SwitchType
	 * @generated
	 */
	EEnum getSwitchType();

	/**
	 * Returns the factory that creates the instances of the model. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	SiteplanFactory getSiteplanFactory();

	/**
	 * <!-- begin-user-doc --> Defines literals for the meta objects that
	 * represent
	 * <ul>
	 * <li>each class,</li>
	 * <li>each feature of each class,</li>
	 * <li>each operation of each class,</li>
	 * <li>each enum,</li>
	 * <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.set.model.siteplan.impl.SiteplanImpl
		 * <em>Siteplan</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
		 * -->
		 * 
		 * @see org.eclipse.set.model.siteplan.impl.SiteplanImpl
		 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getSiteplan()
		 * @generated
		 */
		EClass SITEPLAN = eINSTANCE.getSiteplan();

		/**
		 * The meta object literal for the '<em><b>Initial State</b></em>'
		 * containment reference feature. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EReference SITEPLAN__INITIAL_STATE = eINSTANCE
				.getSiteplan_InitialState();

		/**
		 * The meta object literal for the '<em><b>Changed Initial
		 * State</b></em>' containment reference feature. <!-- begin-user-doc
		 * --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference SITEPLAN__CHANGED_INITIAL_STATE = eINSTANCE
				.getSiteplan_ChangedInitialState();

		/**
		 * The meta object literal for the '<em><b>Common State</b></em>'
		 * containment reference feature. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EReference SITEPLAN__COMMON_STATE = eINSTANCE.getSiteplan_CommonState();

		/**
		 * The meta object literal for the '<em><b>Changed Final State</b></em>'
		 * containment reference feature. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EReference SITEPLAN__CHANGED_FINAL_STATE = eINSTANCE
				.getSiteplan_ChangedFinalState();

		/**
		 * The meta object literal for the '<em><b>Final State</b></em>'
		 * containment reference feature. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EReference SITEPLAN__FINAL_STATE = eINSTANCE.getSiteplan_FinalState();

		/**
		 * The meta object literal for the '<em><b>Center Position</b></em>'
		 * containment reference feature. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EReference SITEPLAN__CENTER_POSITION = eINSTANCE
				.getSiteplan_CenterPosition();

		/**
		 * The meta object literal for the '<em><b>Object Management</b></em>'
		 * containment reference list feature. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EReference SITEPLAN__OBJECT_MANAGEMENT = eINSTANCE
				.getSiteplan_ObjectManagement();

		/**
		 * The meta object literal for the '<em><b>Layout Info</b></em>'
		 * containment reference list feature. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EReference SITEPLAN__LAYOUT_INFO = eINSTANCE.getSiteplan_LayoutInfo();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.set.model.siteplan.impl.SiteplanStateImpl
		 * <em>State</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.set.model.siteplan.impl.SiteplanStateImpl
		 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getSiteplanState()
		 * @generated
		 */
		EClass SITEPLAN_STATE = eINSTANCE.getSiteplanState();

		/**
		 * The meta object literal for the '<em><b>Signals</b></em>' containment
		 * reference list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference SITEPLAN_STATE__SIGNALS = eINSTANCE
				.getSiteplanState_Signals();

		/**
		 * The meta object literal for the '<em><b>Track Switches</b></em>'
		 * containment reference list feature. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EReference SITEPLAN_STATE__TRACK_SWITCHES = eINSTANCE
				.getSiteplanState_TrackSwitches();

		/**
		 * The meta object literal for the '<em><b>Track Switch End
		 * Markers</b></em>' containment reference list feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference SITEPLAN_STATE__TRACK_SWITCH_END_MARKERS = eINSTANCE
				.getSiteplanState_TrackSwitchEndMarkers();

		/**
		 * The meta object literal for the '<em><b>Tracks</b></em>' containment
		 * reference list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference SITEPLAN_STATE__TRACKS = eINSTANCE.getSiteplanState_Tracks();

		/**
		 * The meta object literal for the '<em><b>Fma Components</b></em>'
		 * containment reference list feature. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EReference SITEPLAN_STATE__FMA_COMPONENTS = eINSTANCE
				.getSiteplanState_FmaComponents();

		/**
		 * The meta object literal for the '<em><b>Pzb</b></em>' containment
		 * reference list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference SITEPLAN_STATE__PZB = eINSTANCE.getSiteplanState_Pzb();

		/**
		 * The meta object literal for the '<em><b>Pzb GU</b></em>' containment
		 * reference list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference SITEPLAN_STATE__PZB_GU = eINSTANCE.getSiteplanState_PzbGU();

		/**
		 * The meta object literal for the '<em><b>Routes</b></em>' containment
		 * reference list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference SITEPLAN_STATE__ROUTES = eINSTANCE.getSiteplanState_Routes();

		/**
		 * The meta object literal for the '<em><b>Stations</b></em>'
		 * containment reference list feature. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EReference SITEPLAN_STATE__STATIONS = eINSTANCE
				.getSiteplanState_Stations();

		/**
		 * The meta object literal for the '<em><b>Track Lock</b></em>'
		 * containment reference list feature. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EReference SITEPLAN_STATE__TRACK_LOCK = eINSTANCE
				.getSiteplanState_TrackLock();

		/**
		 * The meta object literal for the '<em><b>Errors</b></em>' containment
		 * reference list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference SITEPLAN_STATE__ERRORS = eINSTANCE.getSiteplanState_Errors();

		/**
		 * The meta object literal for the '<em><b>Track Closures</b></em>'
		 * containment reference list feature. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EReference SITEPLAN_STATE__TRACK_CLOSURES = eINSTANCE
				.getSiteplanState_TrackClosures();

		/**
		 * The meta object literal for the '<em><b>External Element
		 * Controls</b></em>' containment reference list feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference SITEPLAN_STATE__EXTERNAL_ELEMENT_CONTROLS = eINSTANCE
				.getSiteplanState_ExternalElementControls();

		/**
		 * The meta object literal for the '<em><b>Lockkeys</b></em>'
		 * containment reference list feature. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EReference SITEPLAN_STATE__LOCKKEYS = eINSTANCE
				.getSiteplanState_Lockkeys();

		/**
		 * The meta object literal for the '<em><b>Cants</b></em>' containment
		 * reference list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference SITEPLAN_STATE__CANTS = eINSTANCE.getSiteplanState_Cants();

		/**
		 * The meta object literal for the '<em><b>Unknown Objects</b></em>'
		 * containment reference list feature. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EReference SITEPLAN_STATE__UNKNOWN_OBJECTS = eINSTANCE
				.getSiteplanState_UnknownObjects();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.set.model.siteplan.impl.SiteplanObjectImpl
		 * <em>Object</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
		 * -->
		 * 
		 * @see org.eclipse.set.model.siteplan.impl.SiteplanObjectImpl
		 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getSiteplanObject()
		 * @generated
		 */
		EClass SITEPLAN_OBJECT = eINSTANCE.getSiteplanObject();

		/**
		 * The meta object literal for the '<em><b>Guid</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute SITEPLAN_OBJECT__GUID = eINSTANCE.getSiteplanObject_Guid();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.set.model.siteplan.impl.PositionedObjectImpl
		 * <em>Positioned Object</em>}' class. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @see org.eclipse.set.model.siteplan.impl.PositionedObjectImpl
		 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getPositionedObject()
		 * @generated
		 */
		EClass POSITIONED_OBJECT = eINSTANCE.getPositionedObject();

		/**
		 * The meta object literal for the '<em><b>Position</b></em>'
		 * containment reference feature. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EReference POSITIONED_OBJECT__POSITION = eINSTANCE
				.getPositionedObject_Position();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.set.model.siteplan.impl.CoordinateImpl
		 * <em>Coordinate</em>}' class. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @see org.eclipse.set.model.siteplan.impl.CoordinateImpl
		 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getCoordinate()
		 * @generated
		 */
		EClass COORDINATE = eINSTANCE.getCoordinate();

		/**
		 * The meta object literal for the '<em><b>X</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute COORDINATE__X = eINSTANCE.getCoordinate_X();

		/**
		 * The meta object literal for the '<em><b>Y</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute COORDINATE__Y = eINSTANCE.getCoordinate_Y();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.set.model.siteplan.impl.PositionImpl
		 * <em>Position</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
		 * -->
		 * 
		 * @see org.eclipse.set.model.siteplan.impl.PositionImpl
		 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getPosition()
		 * @generated
		 */
		EClass POSITION = eINSTANCE.getPosition();

		/**
		 * The meta object literal for the '<em><b>Rotation</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute POSITION__ROTATION = eINSTANCE.getPosition_Rotation();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.set.model.siteplan.impl.RouteObjectImpl <em>Route
		 * Object</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.set.model.siteplan.impl.RouteObjectImpl
		 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getRouteObject()
		 * @generated
		 */
		EClass ROUTE_OBJECT = eINSTANCE.getRouteObject();

		/**
		 * The meta object literal for the '<em><b>Route Locations</b></em>'
		 * containment reference list feature. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EReference ROUTE_OBJECT__ROUTE_LOCATIONS = eINSTANCE
				.getRouteObject_RouteLocations();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.set.model.siteplan.impl.RouteLocationImpl
		 * <em>Route Location</em>}' class. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @see org.eclipse.set.model.siteplan.impl.RouteLocationImpl
		 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getRouteLocation()
		 * @generated
		 */
		EClass ROUTE_LOCATION = eINSTANCE.getRouteLocation();

		/**
		 * The meta object literal for the '<em><b>Km</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ROUTE_LOCATION__KM = eINSTANCE.getRouteLocation_Km();

		/**
		 * The meta object literal for the '<em><b>Route</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ROUTE_LOCATION__ROUTE = eINSTANCE.getRouteLocation_Route();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.set.model.siteplan.impl.SignalMountImpl
		 * <em>Signal Mount</em>}' class. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @see org.eclipse.set.model.siteplan.impl.SignalMountImpl
		 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getSignalMount()
		 * @generated
		 */
		EClass SIGNAL_MOUNT = eINSTANCE.getSignalMount();

		/**
		 * The meta object literal for the '<em><b>Attached Signals</b></em>'
		 * containment reference list feature. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EReference SIGNAL_MOUNT__ATTACHED_SIGNALS = eINSTANCE
				.getSignalMount_AttachedSignals();

		/**
		 * The meta object literal for the '<em><b>Mount Type</b></em>'
		 * attribute feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute SIGNAL_MOUNT__MOUNT_TYPE = eINSTANCE
				.getSignalMount_MountType();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.set.model.siteplan.impl.SignalImpl
		 * <em>Signal</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
		 * -->
		 * 
		 * @see org.eclipse.set.model.siteplan.impl.SignalImpl
		 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getSignal()
		 * @generated
		 */
		EClass SIGNAL = eINSTANCE.getSignal();

		/**
		 * The meta object literal for the '<em><b>Role</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute SIGNAL__ROLE = eINSTANCE.getSignal_Role();

		/**
		 * The meta object literal for the '<em><b>System</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute SIGNAL__SYSTEM = eINSTANCE.getSignal_System();

		/**
		 * The meta object literal for the '<em><b>Screen</b></em>' containment
		 * reference list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference SIGNAL__SCREEN = eINSTANCE.getSignal_Screen();

		/**
		 * The meta object literal for the '<em><b>Label</b></em>' containment
		 * reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference SIGNAL__LABEL = eINSTANCE.getSignal_Label();

		/**
		 * The meta object literal for the '<em><b>Lateral Distance</b></em>'
		 * attribute list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute SIGNAL__LATERAL_DISTANCE = eINSTANCE
				.getSignal_LateralDistance();

		/**
		 * The meta object literal for the '<em><b>Signal Direction</b></em>'
		 * attribute feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute SIGNAL__SIGNAL_DIRECTION = eINSTANCE
				.getSignal_SignalDirection();

		/**
		 * The meta object literal for the '<em><b>Mount Position</b></em>'
		 * containment reference feature. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EReference SIGNAL__MOUNT_POSITION = eINSTANCE.getSignal_MountPosition();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.set.model.siteplan.impl.SignalScreenImpl
		 * <em>Signal Screen</em>}' class. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @see org.eclipse.set.model.siteplan.impl.SignalScreenImpl
		 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getSignalScreen()
		 * @generated
		 */
		EClass SIGNAL_SCREEN = eINSTANCE.getSignalScreen();

		/**
		 * The meta object literal for the '<em><b>Screen</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute SIGNAL_SCREEN__SCREEN = eINSTANCE.getSignalScreen_Screen();

		/**
		 * The meta object literal for the '<em><b>Switched</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute SIGNAL_SCREEN__SWITCHED = eINSTANCE
				.getSignalScreen_Switched();

		/**
		 * The meta object literal for the '<em><b>Frame Type</b></em>'
		 * attribute feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute SIGNAL_SCREEN__FRAME_TYPE = eINSTANCE
				.getSignalScreen_FrameType();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.set.model.siteplan.impl.LabelImpl
		 * <em>Label</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.set.model.siteplan.impl.LabelImpl
		 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getLabel()
		 * @generated
		 */
		EClass LABEL = eINSTANCE.getLabel();

		/**
		 * The meta object literal for the '<em><b>Text</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute LABEL__TEXT = eINSTANCE.getLabel_Text();

		/**
		 * The meta object literal for the '<em><b>Orientation
		 * Inverted</b></em>' attribute feature. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute LABEL__ORIENTATION_INVERTED = eINSTANCE
				.getLabel_OrientationInverted();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.set.model.siteplan.impl.TrackSwitchImpl <em>Track
		 * Switch</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.set.model.siteplan.impl.TrackSwitchImpl
		 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getTrackSwitch()
		 * @generated
		 */
		EClass TRACK_SWITCH = eINSTANCE.getTrackSwitch();

		/**
		 * The meta object literal for the '<em><b>Design</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TRACK_SWITCH__DESIGN = eINSTANCE.getTrackSwitch_Design();

		/**
		 * The meta object literal for the '<em><b>Components</b></em>'
		 * containment reference list feature. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TRACK_SWITCH__COMPONENTS = eINSTANCE
				.getTrackSwitch_Components();

		/**
		 * The meta object literal for the '<em><b>Continuous Segments</b></em>'
		 * containment reference list feature. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TRACK_SWITCH__CONTINUOUS_SEGMENTS = eINSTANCE
				.getTrackSwitch_ContinuousSegments();

		/**
		 * The meta object literal for the '<em><b>Switch Type</b></em>'
		 * attribute feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TRACK_SWITCH__SWITCH_TYPE = eINSTANCE
				.getTrackSwitch_SwitchType();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.set.model.siteplan.impl.TrackSwitchComponentImpl
		 * <em>Track Switch Component</em>}' class. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @see org.eclipse.set.model.siteplan.impl.TrackSwitchComponentImpl
		 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getTrackSwitchComponent()
		 * @generated
		 */
		EClass TRACK_SWITCH_COMPONENT = eINSTANCE.getTrackSwitchComponent();

		/**
		 * The meta object literal for the '<em><b>Preferred Location</b></em>'
		 * attribute feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TRACK_SWITCH_COMPONENT__PREFERRED_LOCATION = eINSTANCE
				.getTrackSwitchComponent_PreferredLocation();

		/**
		 * The meta object literal for the '<em><b>Point Detector
		 * Count</b></em>' attribute feature. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TRACK_SWITCH_COMPONENT__POINT_DETECTOR_COUNT = eINSTANCE
				.getTrackSwitchComponent_PointDetectorCount();

		/**
		 * The meta object literal for the '<em><b>Start</b></em>' containment
		 * reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TRACK_SWITCH_COMPONENT__START = eINSTANCE
				.getTrackSwitchComponent_Start();

		/**
		 * The meta object literal for the '<em><b>Label Position</b></em>'
		 * containment reference feature. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TRACK_SWITCH_COMPONENT__LABEL_POSITION = eINSTANCE
				.getTrackSwitchComponent_LabelPosition();

		/**
		 * The meta object literal for the '<em><b>Label</b></em>' containment
		 * reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TRACK_SWITCH_COMPONENT__LABEL = eINSTANCE
				.getTrackSwitchComponent_Label();

		/**
		 * The meta object literal for the '<em><b>Operating Mode</b></em>'
		 * attribute feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TRACK_SWITCH_COMPONENT__OPERATING_MODE = eINSTANCE
				.getTrackSwitchComponent_OperatingMode();

		/**
		 * The meta object literal for the '<em><b>Main Leg</b></em>'
		 * containment reference feature. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TRACK_SWITCH_COMPONENT__MAIN_LEG = eINSTANCE
				.getTrackSwitchComponent_MainLeg();

		/**
		 * The meta object literal for the '<em><b>Side Leg</b></em>'
		 * containment reference feature. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TRACK_SWITCH_COMPONENT__SIDE_LEG = eINSTANCE
				.getTrackSwitchComponent_SideLeg();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.set.model.siteplan.impl.ContinuousTrackSegmentImpl
		 * <em>Continuous Track Segment</em>}' class. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.set.model.siteplan.impl.ContinuousTrackSegmentImpl
		 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getContinuousTrackSegment()
		 * @generated
		 */
		EClass CONTINUOUS_TRACK_SEGMENT = eINSTANCE.getContinuousTrackSegment();

		/**
		 * The meta object literal for the '<em><b>Start</b></em>' containment
		 * reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference CONTINUOUS_TRACK_SEGMENT__START = eINSTANCE
				.getContinuousTrackSegment_Start();

		/**
		 * The meta object literal for the '<em><b>End</b></em>' containment
		 * reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference CONTINUOUS_TRACK_SEGMENT__END = eINSTANCE
				.getContinuousTrackSegment_End();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.set.model.siteplan.impl.TrackImpl
		 * <em>Track</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.set.model.siteplan.impl.TrackImpl
		 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getTrack()
		 * @generated
		 */
		EClass TRACK = eINSTANCE.getTrack();

		/**
		 * The meta object literal for the '<em><b>Sections</b></em>'
		 * containment reference list feature. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TRACK__SECTIONS = eINSTANCE.getTrack_Sections();

		/**
		 * The meta object literal for the '<em><b>Designations</b></em>'
		 * containment reference list feature. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TRACK__DESIGNATIONS = eINSTANCE.getTrack_Designations();

		/**
		 * The meta object literal for the '<em><b>Start Coordinate</b></em>'
		 * containment reference feature. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TRACK__START_COORDINATE = eINSTANCE
				.getTrack_StartCoordinate();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.set.model.siteplan.impl.TrackSectionImpl
		 * <em>Track Section</em>}' class. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @see org.eclipse.set.model.siteplan.impl.TrackSectionImpl
		 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getTrackSection()
		 * @generated
		 */
		EClass TRACK_SECTION = eINSTANCE.getTrackSection();

		/**
		 * The meta object literal for the '<em><b>Shape</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TRACK_SECTION__SHAPE = eINSTANCE.getTrackSection_Shape();

		/**
		 * The meta object literal for the '<em><b>Segments</b></em>'
		 * containment reference list feature. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TRACK_SECTION__SEGMENTS = eINSTANCE
				.getTrackSection_Segments();

		/**
		 * The meta object literal for the '<em><b>Color</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TRACK_SECTION__COLOR = eINSTANCE.getTrackSection_Color();

		/**
		 * The meta object literal for the '<em><b>Start Coordinate</b></em>'
		 * containment reference feature. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TRACK_SECTION__START_COORDINATE = eINSTANCE
				.getTrackSection_StartCoordinate();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.set.model.siteplan.impl.TrackSegmentImpl
		 * <em>Track Segment</em>}' class. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @see org.eclipse.set.model.siteplan.impl.TrackSegmentImpl
		 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getTrackSegment()
		 * @generated
		 */
		EClass TRACK_SEGMENT = eINSTANCE.getTrackSegment();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute list
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TRACK_SEGMENT__TYPE = eINSTANCE.getTrackSegment_Type();

		/**
		 * The meta object literal for the '<em><b>Positions</b></em>'
		 * containment reference list feature. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TRACK_SEGMENT__POSITIONS = eINSTANCE
				.getTrackSegment_Positions();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.set.model.siteplan.impl.FMAComponentImpl <em>FMA
		 * Component</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.set.model.siteplan.impl.FMAComponentImpl
		 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getFMAComponent()
		 * @generated
		 */
		EClass FMA_COMPONENT = eINSTANCE.getFMAComponent();

		/**
		 * The meta object literal for the '<em><b>Label</b></em>' containment
		 * reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference FMA_COMPONENT__LABEL = eINSTANCE.getFMAComponent_Label();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute FMA_COMPONENT__TYPE = eINSTANCE.getFMAComponent_Type();

		/**
		 * The meta object literal for the '<em><b>Right Side</b></em>'
		 * attribute feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute FMA_COMPONENT__RIGHT_SIDE = eINSTANCE
				.getFMAComponent_RightSide();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.set.model.siteplan.impl.RouteImpl
		 * <em>Route</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.set.model.siteplan.impl.RouteImpl
		 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getRoute()
		 * @generated
		 */
		EClass ROUTE = eINSTANCE.getRoute();

		/**
		 * The meta object literal for the '<em><b>Sections</b></em>'
		 * containment reference list feature. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EReference ROUTE__SECTIONS = eINSTANCE.getRoute_Sections();

		/**
		 * The meta object literal for the '<em><b>Markers</b></em>' containment
		 * reference list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference ROUTE__MARKERS = eINSTANCE.getRoute_Markers();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.set.model.siteplan.impl.RouteSectionImpl
		 * <em>Route Section</em>}' class. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @see org.eclipse.set.model.siteplan.impl.RouteSectionImpl
		 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getRouteSection()
		 * @generated
		 */
		EClass ROUTE_SECTION = eINSTANCE.getRouteSection();

		/**
		 * The meta object literal for the '<em><b>Guid</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ROUTE_SECTION__GUID = eINSTANCE.getRouteSection_Guid();

		/**
		 * The meta object literal for the '<em><b>Shape</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ROUTE_SECTION__SHAPE = eINSTANCE.getRouteSection_Shape();

		/**
		 * The meta object literal for the '<em><b>Positions</b></em>'
		 * containment reference list feature. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EReference ROUTE_SECTION__POSITIONS = eINSTANCE
				.getRouteSection_Positions();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.set.model.siteplan.impl.KMMarkerImpl <em>KM
		 * Marker</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.set.model.siteplan.impl.KMMarkerImpl
		 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getKMMarker()
		 * @generated
		 */
		EClass KM_MARKER = eINSTANCE.getKMMarker();

		/**
		 * The meta object literal for the '<em><b>Position</b></em>'
		 * containment reference feature. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EReference KM_MARKER__POSITION = eINSTANCE.getKMMarker_Position();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute KM_MARKER__VALUE = eINSTANCE.getKMMarker_Value();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.set.model.siteplan.impl.TrackSwitchEndMarkerImpl
		 * <em>Track Switch End Marker</em>}' class. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.set.model.siteplan.impl.TrackSwitchEndMarkerImpl
		 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getTrackSwitchEndMarker()
		 * @generated
		 */
		EClass TRACK_SWITCH_END_MARKER = eINSTANCE.getTrackSwitchEndMarker();

		/**
		 * The meta object literal for the '<em><b>Leg ACoordinate</b></em>'
		 * containment reference feature. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TRACK_SWITCH_END_MARKER__LEG_ACOORDINATE = eINSTANCE
				.getTrackSwitchEndMarker_LegACoordinate();

		/**
		 * The meta object literal for the '<em><b>Leg BCoordinate</b></em>'
		 * containment reference feature. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TRACK_SWITCH_END_MARKER__LEG_BCOORDINATE = eINSTANCE
				.getTrackSwitchEndMarker_LegBCoordinate();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.set.model.siteplan.impl.ErrorImpl
		 * <em>Error</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.set.model.siteplan.impl.ErrorImpl
		 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getError()
		 * @generated
		 */
		EClass ERROR = eINSTANCE.getError();

		/**
		 * The meta object literal for the '<em><b>Position</b></em>'
		 * containment reference feature. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EReference ERROR__POSITION = eINSTANCE.getError_Position();

		/**
		 * The meta object literal for the '<em><b>Relevant GUI Ds</b></em>'
		 * attribute list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ERROR__RELEVANT_GUI_DS = eINSTANCE.getError_RelevantGUIDs();

		/**
		 * The meta object literal for the '<em><b>Message</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ERROR__MESSAGE = eINSTANCE.getError_Message();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.set.model.siteplan.impl.PZBImpl <em>PZB</em>}'
		 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.set.model.siteplan.impl.PZBImpl
		 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getPZB()
		 * @generated
		 */
		EClass PZB = eINSTANCE.getPZB();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute PZB__TYPE = eINSTANCE.getPZB_Type();

		/**
		 * The meta object literal for the '<em><b>Element</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute PZB__ELEMENT = eINSTANCE.getPZB_Element();

		/**
		 * The meta object literal for the '<em><b>Right Side</b></em>'
		 * attribute feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute PZB__RIGHT_SIDE = eINSTANCE.getPZB_RightSide();

		/**
		 * The meta object literal for the '<em><b>Effectivity</b></em>'
		 * attribute feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute PZB__EFFECTIVITY = eINSTANCE.getPZB_Effectivity();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.set.model.siteplan.impl.PZBGUImpl
		 * <em>PZBGU</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.set.model.siteplan.impl.PZBGUImpl
		 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getPZBGU()
		 * @generated
		 */
		EClass PZBGU = eINSTANCE.getPZBGU();

		/**
		 * The meta object literal for the '<em><b>Pzbs</b></em>' containment
		 * reference list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference PZBGU__PZBS = eINSTANCE.getPZBGU_Pzbs();

		/**
		 * The meta object literal for the '<em><b>Length</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute PZBGU__LENGTH = eINSTANCE.getPZBGU_Length();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.set.model.siteplan.impl.TrackDesignationImpl
		 * <em>Track Designation</em>}' class. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @see org.eclipse.set.model.siteplan.impl.TrackDesignationImpl
		 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getTrackDesignation()
		 * @generated
		 */
		EClass TRACK_DESIGNATION = eINSTANCE.getTrackDesignation();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TRACK_DESIGNATION__NAME = eINSTANCE
				.getTrackDesignation_Name();

		/**
		 * The meta object literal for the '<em><b>Position</b></em>'
		 * containment reference feature. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TRACK_DESIGNATION__POSITION = eINSTANCE
				.getTrackDesignation_Position();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.set.model.siteplan.impl.TrackSwitchLegImpl
		 * <em>Track Switch Leg</em>}' class. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @see org.eclipse.set.model.siteplan.impl.TrackSwitchLegImpl
		 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getTrackSwitchLeg()
		 * @generated
		 */
		EClass TRACK_SWITCH_LEG = eINSTANCE.getTrackSwitchLeg();

		/**
		 * The meta object literal for the '<em><b>Connection</b></em>'
		 * attribute feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TRACK_SWITCH_LEG__CONNECTION = eINSTANCE
				.getTrackSwitchLeg_Connection();

		/**
		 * The meta object literal for the '<em><b>Coordinates</b></em>'
		 * containment reference list feature. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TRACK_SWITCH_LEG__COORDINATES = eINSTANCE
				.getTrackSwitchLeg_Coordinates();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.set.model.siteplan.impl.StationImpl
		 * <em>Station</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
		 * -->
		 * 
		 * @see org.eclipse.set.model.siteplan.impl.StationImpl
		 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getStation()
		 * @generated
		 */
		EClass STATION = eINSTANCE.getStation();

		/**
		 * The meta object literal for the '<em><b>Platforms</b></em>'
		 * containment reference list feature. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EReference STATION__PLATFORMS = eINSTANCE.getStation_Platforms();

		/**
		 * The meta object literal for the '<em><b>Label</b></em>' containment
		 * reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference STATION__LABEL = eINSTANCE.getStation_Label();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.set.model.siteplan.impl.PlatformImpl
		 * <em>Platform</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
		 * -->
		 * 
		 * @see org.eclipse.set.model.siteplan.impl.PlatformImpl
		 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getPlatform()
		 * @generated
		 */
		EClass PLATFORM = eINSTANCE.getPlatform();

		/**
		 * The meta object literal for the '<em><b>Guid</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute PLATFORM__GUID = eINSTANCE.getPlatform_Guid();

		/**
		 * The meta object literal for the '<em><b>Label</b></em>' containment
		 * reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference PLATFORM__LABEL = eINSTANCE.getPlatform_Label();

		/**
		 * The meta object literal for the '<em><b>Label Position</b></em>'
		 * containment reference feature. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EReference PLATFORM__LABEL_POSITION = eINSTANCE
				.getPlatform_LabelPosition();

		/**
		 * The meta object literal for the '<em><b>Points</b></em>' containment
		 * reference list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference PLATFORM__POINTS = eINSTANCE.getPlatform_Points();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.set.model.siteplan.impl.TrackLockImpl <em>Track
		 * Lock</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.set.model.siteplan.impl.TrackLockImpl
		 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getTrackLock()
		 * @generated
		 */
		EClass TRACK_LOCK = eINSTANCE.getTrackLock();

		/**
		 * The meta object literal for the '<em><b>Components</b></em>'
		 * containment reference list feature. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TRACK_LOCK__COMPONENTS = eINSTANCE.getTrackLock_Components();

		/**
		 * The meta object literal for the '<em><b>Preferred Location</b></em>'
		 * attribute feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TRACK_LOCK__PREFERRED_LOCATION = eINSTANCE
				.getTrackLock_PreferredLocation();

		/**
		 * The meta object literal for the '<em><b>Operating Mode</b></em>'
		 * attribute feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TRACK_LOCK__OPERATING_MODE = eINSTANCE
				.getTrackLock_OperatingMode();

		/**
		 * The meta object literal for the '<em><b>Label</b></em>' containment
		 * reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TRACK_LOCK__LABEL = eINSTANCE.getTrackLock_Label();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.set.model.siteplan.impl.TrackLockComponentImpl
		 * <em>Track Lock Component</em>}' class. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @see org.eclipse.set.model.siteplan.impl.TrackLockComponentImpl
		 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getTrackLockComponent()
		 * @generated
		 */
		EClass TRACK_LOCK_COMPONENT = eINSTANCE.getTrackLockComponent();

		/**
		 * The meta object literal for the '<em><b>Track Lock Signal</b></em>'
		 * attribute feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TRACK_LOCK_COMPONENT__TRACK_LOCK_SIGNAL = eINSTANCE
				.getTrackLockComponent_TrackLockSignal();

		/**
		 * The meta object literal for the '<em><b>Ejection Direction</b></em>'
		 * attribute feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TRACK_LOCK_COMPONENT__EJECTION_DIRECTION = eINSTANCE
				.getTrackLockComponent_EjectionDirection();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.set.model.siteplan.impl.ObjectManagementImpl
		 * <em>Object Management</em>}' class. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @see org.eclipse.set.model.siteplan.impl.ObjectManagementImpl
		 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getObjectManagement()
		 * @generated
		 */
		EClass OBJECT_MANAGEMENT = eINSTANCE.getObjectManagement();

		/**
		 * The meta object literal for the '<em><b>Planning Object IDs</b></em>'
		 * attribute list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute OBJECT_MANAGEMENT__PLANNING_OBJECT_IDS = eINSTANCE
				.getObjectManagement_PlanningObjectIDs();

		/**
		 * The meta object literal for the '<em><b>Planning Group ID</b></em>'
		 * attribute feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute OBJECT_MANAGEMENT__PLANNING_GROUP_ID = eINSTANCE
				.getObjectManagement_PlanningGroupID();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.set.model.siteplan.impl.TrackCloseImpl <em>Track
		 * Close</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.set.model.siteplan.impl.TrackCloseImpl
		 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getTrackClose()
		 * @generated
		 */
		EClass TRACK_CLOSE = eINSTANCE.getTrackClose();

		/**
		 * The meta object literal for the '<em><b>Track Close Type</b></em>'
		 * attribute feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TRACK_CLOSE__TRACK_CLOSE_TYPE = eINSTANCE
				.getTrackClose_TrackCloseType();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.set.model.siteplan.impl.ExternalElementControlImpl
		 * <em>External Element Control</em>}' class. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.set.model.siteplan.impl.ExternalElementControlImpl
		 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getExternalElementControl()
		 * @generated
		 */
		EClass EXTERNAL_ELEMENT_CONTROL = eINSTANCE.getExternalElementControl();

		/**
		 * The meta object literal for the '<em><b>Control Art</b></em>'
		 * attribute feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute EXTERNAL_ELEMENT_CONTROL__CONTROL_ART = eINSTANCE
				.getExternalElementControl_ControlArt();

		/**
		 * The meta object literal for the '<em><b>Element Type</b></em>'
		 * attribute feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute EXTERNAL_ELEMENT_CONTROL__ELEMENT_TYPE = eINSTANCE
				.getExternalElementControl_ElementType();

		/**
		 * The meta object literal for the '<em><b>Control Station</b></em>'
		 * attribute feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute EXTERNAL_ELEMENT_CONTROL__CONTROL_STATION = eINSTANCE
				.getExternalElementControl_ControlStation();

		/**
		 * The meta object literal for the '<em><b>Label</b></em>' containment
		 * reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference EXTERNAL_ELEMENT_CONTROL__LABEL = eINSTANCE
				.getExternalElementControl_Label();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.set.model.siteplan.impl.LockKeyImpl <em>Lock
		 * Key</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.set.model.siteplan.impl.LockKeyImpl
		 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getLockKey()
		 * @generated
		 */
		EClass LOCK_KEY = eINSTANCE.getLockKey();

		/**
		 * The meta object literal for the '<em><b>Label</b></em>' containment
		 * reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference LOCK_KEY__LABEL = eINSTANCE.getLockKey_Label();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute LOCK_KEY__TYPE = eINSTANCE.getLockKey_Type();

		/**
		 * The meta object literal for the '<em><b>Locked</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute LOCK_KEY__LOCKED = eINSTANCE.getLockKey_Locked();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.set.model.siteplan.impl.LayoutinfoImpl
		 * <em>Layoutinfo</em>}' class. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @see org.eclipse.set.model.siteplan.impl.LayoutinfoImpl
		 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getLayoutinfo()
		 * @generated
		 */
		EClass LAYOUTINFO = eINSTANCE.getLayoutinfo();

		/**
		 * The meta object literal for the '<em><b>Label</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute LAYOUTINFO__LABEL = eINSTANCE.getLayoutinfo_Label();

		/**
		 * The meta object literal for the '<em><b>Sheets Cut</b></em>'
		 * containment reference list feature. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EReference LAYOUTINFO__SHEETS_CUT = eINSTANCE.getLayoutinfo_SheetsCut();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.set.model.siteplan.impl.SheetCutImpl <em>Sheet
		 * Cut</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.set.model.siteplan.impl.SheetCutImpl
		 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getSheetCut()
		 * @generated
		 */
		EClass SHEET_CUT = eINSTANCE.getSheetCut();

		/**
		 * The meta object literal for the '<em><b>Label</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute SHEET_CUT__LABEL = eINSTANCE.getSheetCut_Label();

		/**
		 * The meta object literal for the '<em><b>Polygon Direction</b></em>'
		 * containment reference list feature. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EReference SHEET_CUT__POLYGON_DIRECTION = eINSTANCE
				.getSheetCut_PolygonDirection();

		/**
		 * The meta object literal for the '<em><b>Polygon</b></em>' containment
		 * reference list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference SHEET_CUT__POLYGON = eINSTANCE.getSheetCut_Polygon();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.set.model.siteplan.impl.CantImpl <em>Cant</em>}'
		 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.set.model.siteplan.impl.CantImpl
		 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getCant()
		 * @generated
		 */
		EClass CANT = eINSTANCE.getCant();

		/**
		 * The meta object literal for the '<em><b>Point A</b></em>' containment
		 * reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference CANT__POINT_A = eINSTANCE.getCant_PointA();

		/**
		 * The meta object literal for the '<em><b>Point B</b></em>' containment
		 * reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference CANT__POINT_B = eINSTANCE.getCant_PointB();

		/**
		 * The meta object literal for the '<em><b>Form</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute CANT__FORM = eINSTANCE.getCant_Form();

		/**
		 * The meta object literal for the '<em><b>Length</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute CANT__LENGTH = eINSTANCE.getCant_Length();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.set.model.siteplan.impl.CantPointImpl <em>Cant
		 * Point</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.set.model.siteplan.impl.CantPointImpl
		 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getCantPoint()
		 * @generated
		 */
		EClass CANT_POINT = eINSTANCE.getCantPoint();

		/**
		 * The meta object literal for the '<em><b>Height</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute CANT_POINT__HEIGHT = eINSTANCE.getCantPoint_Height();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.set.model.siteplan.impl.UnknownPositionedObjectImpl
		 * <em>Unknown Positioned Object</em>}' class. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.set.model.siteplan.impl.UnknownPositionedObjectImpl
		 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getUnknownPositionedObject()
		 * @generated
		 */
		EClass UNKNOWN_POSITIONED_OBJECT = eINSTANCE
				.getUnknownPositionedObject();

		/**
		 * The meta object literal for the '<em><b>Object Type</b></em>'
		 * attribute feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute UNKNOWN_POSITIONED_OBJECT__OBJECT_TYPE = eINSTANCE
				.getUnknownPositionedObject_ObjectType();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.set.model.siteplan.SignalMountType <em>Signal
		 * Mount Type</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.set.model.siteplan.SignalMountType
		 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getSignalMountType()
		 * @generated
		 */
		EEnum SIGNAL_MOUNT_TYPE = eINSTANCE.getSignalMountType();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.set.model.siteplan.SignalRole <em>Signal
		 * Role</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.set.model.siteplan.SignalRole
		 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getSignalRole()
		 * @generated
		 */
		EEnum SIGNAL_ROLE = eINSTANCE.getSignalRole();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.set.model.siteplan.SignalSystem <em>Signal
		 * System</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.set.model.siteplan.SignalSystem
		 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getSignalSystem()
		 * @generated
		 */
		EEnum SIGNAL_SYSTEM = eINSTANCE.getSignalSystem();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.set.model.siteplan.MountDirection <em>Mount
		 * Direction</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.set.model.siteplan.MountDirection
		 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getMountDirection()
		 * @generated
		 */
		EEnum MOUNT_DIRECTION = eINSTANCE.getMountDirection();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.set.model.siteplan.TurnoutOperatingMode
		 * <em>Turnout Operating Mode</em>}' enum. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @see org.eclipse.set.model.siteplan.TurnoutOperatingMode
		 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getTurnoutOperatingMode()
		 * @generated
		 */
		EEnum TURNOUT_OPERATING_MODE = eINSTANCE.getTurnoutOperatingMode();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.set.model.siteplan.TrackShape <em>Track
		 * Shape</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.set.model.siteplan.TrackShape
		 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getTrackShape()
		 * @generated
		 */
		EEnum TRACK_SHAPE = eINSTANCE.getTrackShape();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.set.model.siteplan.TrackType <em>Track
		 * Type</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.set.model.siteplan.TrackType
		 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getTrackType()
		 * @generated
		 */
		EEnum TRACK_TYPE = eINSTANCE.getTrackType();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.set.model.siteplan.FMAComponentType <em>FMA
		 * Component Type</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc
		 * -->
		 * 
		 * @see org.eclipse.set.model.siteplan.FMAComponentType
		 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getFMAComponentType()
		 * @generated
		 */
		EEnum FMA_COMPONENT_TYPE = eINSTANCE.getFMAComponentType();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.set.model.siteplan.PZBType <em>PZB Type</em>}'
		 * enum. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.set.model.siteplan.PZBType
		 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getPZBType()
		 * @generated
		 */
		EEnum PZB_TYPE = eINSTANCE.getPZBType();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.set.model.siteplan.PZBElement <em>PZB
		 * Element</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.set.model.siteplan.PZBElement
		 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getPZBElement()
		 * @generated
		 */
		EEnum PZB_ELEMENT = eINSTANCE.getPZBElement();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.set.model.siteplan.PZBEffectivity <em>PZB
		 * Effectivity</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc
		 * -->
		 * 
		 * @see org.eclipse.set.model.siteplan.PZBEffectivity
		 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getPZBEffectivity()
		 * @generated
		 */
		EEnum PZB_EFFECTIVITY = eINSTANCE.getPZBEffectivity();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.set.model.siteplan.TrackLockLocation <em>Track
		 * Lock Location</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc
		 * -->
		 * 
		 * @see org.eclipse.set.model.siteplan.TrackLockLocation
		 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getTrackLockLocation()
		 * @generated
		 */
		EEnum TRACK_LOCK_LOCATION = eINSTANCE.getTrackLockLocation();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.set.model.siteplan.LeftRight <em>Left
		 * Right</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.set.model.siteplan.LeftRight
		 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getLeftRight()
		 * @generated
		 */
		EEnum LEFT_RIGHT = eINSTANCE.getLeftRight();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.set.model.siteplan.Direction <em>Direction</em>}'
		 * enum. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.set.model.siteplan.Direction
		 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getDirection()
		 * @generated
		 */
		EEnum DIRECTION = eINSTANCE.getDirection();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.set.model.siteplan.TrackCloseType <em>Track Close
		 * Type</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.set.model.siteplan.TrackCloseType
		 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getTrackCloseType()
		 * @generated
		 */
		EEnum TRACK_CLOSE_TYPE = eINSTANCE.getTrackCloseType();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.set.model.siteplan.ExternalElementControlArt
		 * <em>External Element Control Art</em>}' enum. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.set.model.siteplan.ExternalElementControlArt
		 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getExternalElementControlArt()
		 * @generated
		 */
		EEnum EXTERNAL_ELEMENT_CONTROL_ART = eINSTANCE
				.getExternalElementControlArt();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.set.model.siteplan.ControlStationType <em>Control
		 * Station Type</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc
		 * -->
		 * 
		 * @see org.eclipse.set.model.siteplan.ControlStationType
		 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getControlStationType()
		 * @generated
		 */
		EEnum CONTROL_STATION_TYPE = eINSTANCE.getControlStationType();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.set.model.siteplan.LockKeyType <em>Lock Key
		 * Type</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.set.model.siteplan.LockKeyType
		 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getLockKeyType()
		 * @generated
		 */
		EEnum LOCK_KEY_TYPE = eINSTANCE.getLockKeyType();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.set.model.siteplan.SwitchType <em>Switch
		 * Type</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.set.model.siteplan.SwitchType
		 * @see org.eclipse.set.model.siteplan.impl.SiteplanPackageImpl#getSwitchType()
		 * @generated
		 */
		EEnum SWITCH_TYPE = eINSTANCE.getSwitchType();

	}

} // SiteplanPackage
