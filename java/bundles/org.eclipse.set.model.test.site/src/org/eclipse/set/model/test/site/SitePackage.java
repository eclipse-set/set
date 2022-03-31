/**
 * Copyright (c) 2018 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.test.site;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.eclipse.set.model.test.site.SiteFactory
 * @model kind="package"
 * @generated
 */
public interface SitePackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "site";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "tag:scheidt-bachmann-st.de,2018-05-18:planpro/site";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "site";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	SitePackage eINSTANCE = org.eclipse.set.model.test.site.impl.SitePackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.set.model.test.site.impl.SiteImpl <em>Site</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.set.model.test.site.impl.SiteImpl
	 * @see org.eclipse.set.model.test.site.impl.SitePackageImpl#getSite()
	 * @generated
	 */
	int SITE = 0;

	/**
	 * The feature id for the '<em><b>All Buildings</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SITE__ALL_BUILDINGS = 0;

	/**
	 * The feature id for the '<em><b>All Rooms</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SITE__ALL_ROOMS = 1;

	/**
	 * The feature id for the '<em><b>All Floors</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SITE__ALL_FLOORS = 2;

	/**
	 * The feature id for the '<em><b>All Passages</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SITE__ALL_PASSAGES = 3;

	/**
	 * The number of structural features of the '<em>Site</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SITE_FEATURE_COUNT = 4;

	/**
	 * The number of operations of the '<em>Site</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SITE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.set.model.test.site.impl.IdentifiedImpl <em>Identified</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.set.model.test.site.impl.IdentifiedImpl
	 * @see org.eclipse.set.model.test.site.impl.SitePackageImpl#getIdentified()
	 * @generated
	 */
	int IDENTIFIED = 3;

	/**
	 * The feature id for the '<em><b>Guid</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDENTIFIED__GUID = 0;

	/**
	 * The number of structural features of the '<em>Identified</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDENTIFIED_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Identified</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDENTIFIED_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.set.model.test.site.impl.BuildingImpl <em>Building</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.set.model.test.site.impl.BuildingImpl
	 * @see org.eclipse.set.model.test.site.impl.SitePackageImpl#getBuilding()
	 * @generated
	 */
	int BUILDING = 1;

	/**
	 * The feature id for the '<em><b>Guid</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUILDING__GUID = IDENTIFIED__GUID;

	/**
	 * The feature id for the '<em><b>Names</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUILDING__NAMES = IDENTIFIED_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Buildings In The Same Street</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUILDING__BUILDINGS_IN_THE_SAME_STREET = IDENTIFIED_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Data</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUILDING__DATA = IDENTIFIED_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Building</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUILDING_FEATURE_COUNT = IDENTIFIED_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>Building</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUILDING_OPERATION_COUNT = IDENTIFIED_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.set.model.test.site.impl.RoomImpl <em>Room</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.set.model.test.site.impl.RoomImpl
	 * @see org.eclipse.set.model.test.site.impl.SitePackageImpl#getRoom()
	 * @generated
	 */
	int ROOM = 2;

	/**
	 * The feature id for the '<em><b>Guid</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOM__GUID = IDENTIFIED__GUID;

	/**
	 * The feature id for the '<em><b>Names</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOM__NAMES = IDENTIFIED_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Floor ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOM__FLOOR_ID = IDENTIFIED_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Windows</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOM__WINDOWS = IDENTIFIED_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Room</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOM_FEATURE_COUNT = IDENTIFIED_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>Room</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOM_OPERATION_COUNT = IDENTIFIED_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.set.model.test.site.impl.FloorImpl <em>Floor</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.set.model.test.site.impl.FloorImpl
	 * @see org.eclipse.set.model.test.site.impl.SitePackageImpl#getFloor()
	 * @generated
	 */
	int FLOOR = 4;

	/**
	 * The feature id for the '<em><b>Guid</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOOR__GUID = IDENTIFIED__GUID;

	/**
	 * The feature id for the '<em><b>Names</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOOR__NAMES = IDENTIFIED_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Building ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOOR__BUILDING_ID = IDENTIFIED_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Floor</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOOR_FEATURE_COUNT = IDENTIFIED_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Floor</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOOR_OPERATION_COUNT = IDENTIFIED_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.set.model.test.site.impl.BuildingNamesImpl <em>Building Names</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.set.model.test.site.impl.BuildingNamesImpl
	 * @see org.eclipse.set.model.test.site.impl.SitePackageImpl#getBuildingNames()
	 * @generated
	 */
	int BUILDING_NAMES = 5;

	/**
	 * The feature id for the '<em><b>Address</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUILDING_NAMES__ADDRESS = 0;

	/**
	 * The feature id for the '<em><b>Entrance</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUILDING_NAMES__ENTRANCE = 1;

	/**
	 * The feature id for the '<em><b>Site Plan</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUILDING_NAMES__SITE_PLAN = 2;

	/**
	 * The number of structural features of the '<em>Building Names</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUILDING_NAMES_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Building Names</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUILDING_NAMES_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.set.model.test.site.impl.FloorNamesImpl <em>Floor Names</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.set.model.test.site.impl.FloorNamesImpl
	 * @see org.eclipse.set.model.test.site.impl.SitePackageImpl#getFloorNames()
	 * @generated
	 */
	int FLOOR_NAMES = 6;

	/**
	 * The feature id for the '<em><b>Elevator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOOR_NAMES__ELEVATOR = 0;

	/**
	 * The feature id for the '<em><b>Floor Plan Title</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOOR_NAMES__FLOOR_PLAN_TITLE = 1;

	/**
	 * The number of structural features of the '<em>Floor Names</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOOR_NAMES_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Floor Names</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOOR_NAMES_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.set.model.test.site.impl.RoomNamesImpl <em>Room Names</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.set.model.test.site.impl.RoomNamesImpl
	 * @see org.eclipse.set.model.test.site.impl.SitePackageImpl#getRoomNames()
	 * @generated
	 */
	int ROOM_NAMES = 7;

	/**
	 * The feature id for the '<em><b>Door Sign</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOM_NAMES__DOOR_SIGN = 0;

	/**
	 * The feature id for the '<em><b>Floor Plan</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOM_NAMES__FLOOR_PLAN = 1;

	/**
	 * The number of structural features of the '<em>Room Names</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOM_NAMES_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Room Names</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOM_NAMES_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.set.model.test.site.impl.PassageImpl <em>Passage</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.set.model.test.site.impl.PassageImpl
	 * @see org.eclipse.set.model.test.site.impl.SitePackageImpl#getPassage()
	 * @generated
	 */
	int PASSAGE = 8;

	/**
	 * The feature id for the '<em><b>Guid</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PASSAGE__GUID = IDENTIFIED__GUID;

	/**
	 * The feature id for the '<em><b>Room ID A</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PASSAGE__ROOM_ID_A = IDENTIFIED_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Room ID B</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PASSAGE__ROOM_ID_B = IDENTIFIED_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Passage</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PASSAGE_FEATURE_COUNT = IDENTIFIED_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Passage</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PASSAGE_OPERATION_COUNT = IDENTIFIED_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.set.model.test.site.impl.WindowImpl <em>Window</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.set.model.test.site.impl.WindowImpl
	 * @see org.eclipse.set.model.test.site.impl.SitePackageImpl#getWindow()
	 * @generated
	 */
	int WINDOW = 9;

	/**
	 * The feature id for the '<em><b>Position</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WINDOW__POSITION = 0;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WINDOW__TYPE = 1;

	/**
	 * The number of structural features of the '<em>Window</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WINDOW_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Window</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WINDOW_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.set.model.test.site.WindowType <em>Window Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.set.model.test.site.WindowType
	 * @see org.eclipse.set.model.test.site.impl.SitePackageImpl#getWindowType()
	 * @generated
	 */
	int WINDOW_TYPE = 10;


	/**
	 * Returns the meta object for class '{@link org.eclipse.set.model.test.site.Site <em>Site</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Site</em>'.
	 * @see org.eclipse.set.model.test.site.Site
	 * @generated
	 */
	EClass getSite();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.set.model.test.site.Site#getAllBuildings <em>All Buildings</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>All Buildings</em>'.
	 * @see org.eclipse.set.model.test.site.Site#getAllBuildings()
	 * @see #getSite()
	 * @generated
	 */
	EReference getSite_AllBuildings();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.set.model.test.site.Site#getAllRooms <em>All Rooms</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>All Rooms</em>'.
	 * @see org.eclipse.set.model.test.site.Site#getAllRooms()
	 * @see #getSite()
	 * @generated
	 */
	EReference getSite_AllRooms();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.set.model.test.site.Site#getAllFloors <em>All Floors</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>All Floors</em>'.
	 * @see org.eclipse.set.model.test.site.Site#getAllFloors()
	 * @see #getSite()
	 * @generated
	 */
	EReference getSite_AllFloors();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.set.model.test.site.Site#getAllPassages <em>All Passages</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>All Passages</em>'.
	 * @see org.eclipse.set.model.test.site.Site#getAllPassages()
	 * @see #getSite()
	 * @generated
	 */
	EReference getSite_AllPassages();

	/**
	 * Returns the meta object for class '{@link org.eclipse.set.model.test.site.Building <em>Building</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Building</em>'.
	 * @see org.eclipse.set.model.test.site.Building
	 * @generated
	 */
	EClass getBuilding();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.set.model.test.site.Building#getNames <em>Names</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Names</em>'.
	 * @see org.eclipse.set.model.test.site.Building#getNames()
	 * @see #getBuilding()
	 * @generated
	 */
	EReference getBuilding_Names();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.set.model.test.site.Building#getBuildingsInTheSameStreet <em>Buildings In The Same Street</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Buildings In The Same Street</em>'.
	 * @see org.eclipse.set.model.test.site.Building#getBuildingsInTheSameStreet()
	 * @see #getBuilding()
	 * @generated
	 */
	EReference getBuilding_BuildingsInTheSameStreet();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.set.model.test.site.Building#getData <em>Data</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Data</em>'.
	 * @see org.eclipse.set.model.test.site.Building#getData()
	 * @see #getBuilding()
	 * @generated
	 */
	EAttribute getBuilding_Data();

	/**
	 * Returns the meta object for class '{@link org.eclipse.set.model.test.site.Room <em>Room</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Room</em>'.
	 * @see org.eclipse.set.model.test.site.Room
	 * @generated
	 */
	EClass getRoom();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.set.model.test.site.Room#getNames <em>Names</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Names</em>'.
	 * @see org.eclipse.set.model.test.site.Room#getNames()
	 * @see #getRoom()
	 * @generated
	 */
	EReference getRoom_Names();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.set.model.test.site.Room#getFloorID <em>Floor ID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Floor ID</em>'.
	 * @see org.eclipse.set.model.test.site.Room#getFloorID()
	 * @see #getRoom()
	 * @generated
	 */
	EAttribute getRoom_FloorID();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.set.model.test.site.Room#getWindows <em>Windows</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Windows</em>'.
	 * @see org.eclipse.set.model.test.site.Room#getWindows()
	 * @see #getRoom()
	 * @generated
	 */
	EReference getRoom_Windows();

	/**
	 * Returns the meta object for class '{@link org.eclipse.set.model.test.site.Identified <em>Identified</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Identified</em>'.
	 * @see org.eclipse.set.model.test.site.Identified
	 * @generated
	 */
	EClass getIdentified();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.set.model.test.site.Identified#getGuid <em>Guid</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Guid</em>'.
	 * @see org.eclipse.set.model.test.site.Identified#getGuid()
	 * @see #getIdentified()
	 * @generated
	 */
	EAttribute getIdentified_Guid();

	/**
	 * Returns the meta object for class '{@link org.eclipse.set.model.test.site.Floor <em>Floor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Floor</em>'.
	 * @see org.eclipse.set.model.test.site.Floor
	 * @generated
	 */
	EClass getFloor();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.set.model.test.site.Floor#getNames <em>Names</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Names</em>'.
	 * @see org.eclipse.set.model.test.site.Floor#getNames()
	 * @see #getFloor()
	 * @generated
	 */
	EReference getFloor_Names();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.set.model.test.site.Floor#getBuildingID <em>Building ID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Building ID</em>'.
	 * @see org.eclipse.set.model.test.site.Floor#getBuildingID()
	 * @see #getFloor()
	 * @generated
	 */
	EAttribute getFloor_BuildingID();

	/**
	 * Returns the meta object for class '{@link org.eclipse.set.model.test.site.BuildingNames <em>Building Names</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Building Names</em>'.
	 * @see org.eclipse.set.model.test.site.BuildingNames
	 * @generated
	 */
	EClass getBuildingNames();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.set.model.test.site.BuildingNames#getAddress <em>Address</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Address</em>'.
	 * @see org.eclipse.set.model.test.site.BuildingNames#getAddress()
	 * @see #getBuildingNames()
	 * @generated
	 */
	EAttribute getBuildingNames_Address();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.set.model.test.site.BuildingNames#getEntrance <em>Entrance</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Entrance</em>'.
	 * @see org.eclipse.set.model.test.site.BuildingNames#getEntrance()
	 * @see #getBuildingNames()
	 * @generated
	 */
	EAttribute getBuildingNames_Entrance();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.set.model.test.site.BuildingNames#getSitePlan <em>Site Plan</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Site Plan</em>'.
	 * @see org.eclipse.set.model.test.site.BuildingNames#getSitePlan()
	 * @see #getBuildingNames()
	 * @generated
	 */
	EAttribute getBuildingNames_SitePlan();

	/**
	 * Returns the meta object for class '{@link org.eclipse.set.model.test.site.FloorNames <em>Floor Names</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Floor Names</em>'.
	 * @see org.eclipse.set.model.test.site.FloorNames
	 * @generated
	 */
	EClass getFloorNames();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.set.model.test.site.FloorNames#getElevator <em>Elevator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Elevator</em>'.
	 * @see org.eclipse.set.model.test.site.FloorNames#getElevator()
	 * @see #getFloorNames()
	 * @generated
	 */
	EAttribute getFloorNames_Elevator();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.set.model.test.site.FloorNames#getFloorPlanTitle <em>Floor Plan Title</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Floor Plan Title</em>'.
	 * @see org.eclipse.set.model.test.site.FloorNames#getFloorPlanTitle()
	 * @see #getFloorNames()
	 * @generated
	 */
	EAttribute getFloorNames_FloorPlanTitle();

	/**
	 * Returns the meta object for class '{@link org.eclipse.set.model.test.site.RoomNames <em>Room Names</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Room Names</em>'.
	 * @see org.eclipse.set.model.test.site.RoomNames
	 * @generated
	 */
	EClass getRoomNames();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.set.model.test.site.RoomNames#getDoorSign <em>Door Sign</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Door Sign</em>'.
	 * @see org.eclipse.set.model.test.site.RoomNames#getDoorSign()
	 * @see #getRoomNames()
	 * @generated
	 */
	EAttribute getRoomNames_DoorSign();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.set.model.test.site.RoomNames#getFloorPlan <em>Floor Plan</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Floor Plan</em>'.
	 * @see org.eclipse.set.model.test.site.RoomNames#getFloorPlan()
	 * @see #getRoomNames()
	 * @generated
	 */
	EAttribute getRoomNames_FloorPlan();

	/**
	 * Returns the meta object for class '{@link org.eclipse.set.model.test.site.Passage <em>Passage</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Passage</em>'.
	 * @see org.eclipse.set.model.test.site.Passage
	 * @generated
	 */
	EClass getPassage();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.set.model.test.site.Passage#getRoomID_A <em>Room ID A</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Room ID A</em>'.
	 * @see org.eclipse.set.model.test.site.Passage#getRoomID_A()
	 * @see #getPassage()
	 * @generated
	 */
	EAttribute getPassage_RoomID_A();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.set.model.test.site.Passage#getRoomID_B <em>Room ID B</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Room ID B</em>'.
	 * @see org.eclipse.set.model.test.site.Passage#getRoomID_B()
	 * @see #getPassage()
	 * @generated
	 */
	EAttribute getPassage_RoomID_B();

	/**
	 * Returns the meta object for class '{@link org.eclipse.set.model.test.site.Window <em>Window</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Window</em>'.
	 * @see org.eclipse.set.model.test.site.Window
	 * @generated
	 */
	EClass getWindow();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.set.model.test.site.Window#getPosition <em>Position</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Position</em>'.
	 * @see org.eclipse.set.model.test.site.Window#getPosition()
	 * @see #getWindow()
	 * @generated
	 */
	EAttribute getWindow_Position();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.set.model.test.site.Window#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see org.eclipse.set.model.test.site.Window#getType()
	 * @see #getWindow()
	 * @generated
	 */
	EAttribute getWindow_Type();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.set.model.test.site.WindowType <em>Window Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Window Type</em>'.
	 * @see org.eclipse.set.model.test.site.WindowType
	 * @generated
	 */
	EEnum getWindowType();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	SiteFactory getSiteFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.eclipse.set.model.test.site.impl.SiteImpl <em>Site</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.set.model.test.site.impl.SiteImpl
		 * @see org.eclipse.set.model.test.site.impl.SitePackageImpl#getSite()
		 * @generated
		 */
		EClass SITE = eINSTANCE.getSite();

		/**
		 * The meta object literal for the '<em><b>All Buildings</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SITE__ALL_BUILDINGS = eINSTANCE.getSite_AllBuildings();

		/**
		 * The meta object literal for the '<em><b>All Rooms</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SITE__ALL_ROOMS = eINSTANCE.getSite_AllRooms();

		/**
		 * The meta object literal for the '<em><b>All Floors</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SITE__ALL_FLOORS = eINSTANCE.getSite_AllFloors();

		/**
		 * The meta object literal for the '<em><b>All Passages</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SITE__ALL_PASSAGES = eINSTANCE.getSite_AllPassages();

		/**
		 * The meta object literal for the '{@link org.eclipse.set.model.test.site.impl.BuildingImpl <em>Building</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.set.model.test.site.impl.BuildingImpl
		 * @see org.eclipse.set.model.test.site.impl.SitePackageImpl#getBuilding()
		 * @generated
		 */
		EClass BUILDING = eINSTANCE.getBuilding();

		/**
		 * The meta object literal for the '<em><b>Names</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BUILDING__NAMES = eINSTANCE.getBuilding_Names();

		/**
		 * The meta object literal for the '<em><b>Buildings In The Same Street</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BUILDING__BUILDINGS_IN_THE_SAME_STREET = eINSTANCE.getBuilding_BuildingsInTheSameStreet();

		/**
		 * The meta object literal for the '<em><b>Data</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BUILDING__DATA = eINSTANCE.getBuilding_Data();

		/**
		 * The meta object literal for the '{@link org.eclipse.set.model.test.site.impl.RoomImpl <em>Room</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.set.model.test.site.impl.RoomImpl
		 * @see org.eclipse.set.model.test.site.impl.SitePackageImpl#getRoom()
		 * @generated
		 */
		EClass ROOM = eINSTANCE.getRoom();

		/**
		 * The meta object literal for the '<em><b>Names</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ROOM__NAMES = eINSTANCE.getRoom_Names();

		/**
		 * The meta object literal for the '<em><b>Floor ID</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ROOM__FLOOR_ID = eINSTANCE.getRoom_FloorID();

		/**
		 * The meta object literal for the '<em><b>Windows</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ROOM__WINDOWS = eINSTANCE.getRoom_Windows();

		/**
		 * The meta object literal for the '{@link org.eclipse.set.model.test.site.impl.IdentifiedImpl <em>Identified</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.set.model.test.site.impl.IdentifiedImpl
		 * @see org.eclipse.set.model.test.site.impl.SitePackageImpl#getIdentified()
		 * @generated
		 */
		EClass IDENTIFIED = eINSTANCE.getIdentified();

		/**
		 * The meta object literal for the '<em><b>Guid</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IDENTIFIED__GUID = eINSTANCE.getIdentified_Guid();

		/**
		 * The meta object literal for the '{@link org.eclipse.set.model.test.site.impl.FloorImpl <em>Floor</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.set.model.test.site.impl.FloorImpl
		 * @see org.eclipse.set.model.test.site.impl.SitePackageImpl#getFloor()
		 * @generated
		 */
		EClass FLOOR = eINSTANCE.getFloor();

		/**
		 * The meta object literal for the '<em><b>Names</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FLOOR__NAMES = eINSTANCE.getFloor_Names();

		/**
		 * The meta object literal for the '<em><b>Building ID</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FLOOR__BUILDING_ID = eINSTANCE.getFloor_BuildingID();

		/**
		 * The meta object literal for the '{@link org.eclipse.set.model.test.site.impl.BuildingNamesImpl <em>Building Names</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.set.model.test.site.impl.BuildingNamesImpl
		 * @see org.eclipse.set.model.test.site.impl.SitePackageImpl#getBuildingNames()
		 * @generated
		 */
		EClass BUILDING_NAMES = eINSTANCE.getBuildingNames();

		/**
		 * The meta object literal for the '<em><b>Address</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BUILDING_NAMES__ADDRESS = eINSTANCE.getBuildingNames_Address();

		/**
		 * The meta object literal for the '<em><b>Entrance</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BUILDING_NAMES__ENTRANCE = eINSTANCE.getBuildingNames_Entrance();

		/**
		 * The meta object literal for the '<em><b>Site Plan</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BUILDING_NAMES__SITE_PLAN = eINSTANCE.getBuildingNames_SitePlan();

		/**
		 * The meta object literal for the '{@link org.eclipse.set.model.test.site.impl.FloorNamesImpl <em>Floor Names</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.set.model.test.site.impl.FloorNamesImpl
		 * @see org.eclipse.set.model.test.site.impl.SitePackageImpl#getFloorNames()
		 * @generated
		 */
		EClass FLOOR_NAMES = eINSTANCE.getFloorNames();

		/**
		 * The meta object literal for the '<em><b>Elevator</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FLOOR_NAMES__ELEVATOR = eINSTANCE.getFloorNames_Elevator();

		/**
		 * The meta object literal for the '<em><b>Floor Plan Title</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FLOOR_NAMES__FLOOR_PLAN_TITLE = eINSTANCE.getFloorNames_FloorPlanTitle();

		/**
		 * The meta object literal for the '{@link org.eclipse.set.model.test.site.impl.RoomNamesImpl <em>Room Names</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.set.model.test.site.impl.RoomNamesImpl
		 * @see org.eclipse.set.model.test.site.impl.SitePackageImpl#getRoomNames()
		 * @generated
		 */
		EClass ROOM_NAMES = eINSTANCE.getRoomNames();

		/**
		 * The meta object literal for the '<em><b>Door Sign</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ROOM_NAMES__DOOR_SIGN = eINSTANCE.getRoomNames_DoorSign();

		/**
		 * The meta object literal for the '<em><b>Floor Plan</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ROOM_NAMES__FLOOR_PLAN = eINSTANCE.getRoomNames_FloorPlan();

		/**
		 * The meta object literal for the '{@link org.eclipse.set.model.test.site.impl.PassageImpl <em>Passage</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.set.model.test.site.impl.PassageImpl
		 * @see org.eclipse.set.model.test.site.impl.SitePackageImpl#getPassage()
		 * @generated
		 */
		EClass PASSAGE = eINSTANCE.getPassage();

		/**
		 * The meta object literal for the '<em><b>Room ID A</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PASSAGE__ROOM_ID_A = eINSTANCE.getPassage_RoomID_A();

		/**
		 * The meta object literal for the '<em><b>Room ID B</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PASSAGE__ROOM_ID_B = eINSTANCE.getPassage_RoomID_B();

		/**
		 * The meta object literal for the '{@link org.eclipse.set.model.test.site.impl.WindowImpl <em>Window</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.set.model.test.site.impl.WindowImpl
		 * @see org.eclipse.set.model.test.site.impl.SitePackageImpl#getWindow()
		 * @generated
		 */
		EClass WINDOW = eINSTANCE.getWindow();

		/**
		 * The meta object literal for the '<em><b>Position</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute WINDOW__POSITION = eINSTANCE.getWindow_Position();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute WINDOW__TYPE = eINSTANCE.getWindow_Type();

		/**
		 * The meta object literal for the '{@link org.eclipse.set.model.test.site.WindowType <em>Window Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.set.model.test.site.WindowType
		 * @see org.eclipse.set.model.test.site.impl.SitePackageImpl#getWindowType()
		 * @generated
		 */
		EEnum WINDOW_TYPE = eINSTANCE.getWindowType();

	}

} //SitePackage
