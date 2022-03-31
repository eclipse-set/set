/**
 * Copyright (c) 2018 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.test.site.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.set.model.test.site.Building;
import org.eclipse.set.model.test.site.Floor;
import org.eclipse.set.model.test.site.Passage;
import org.eclipse.set.model.test.site.Room;
import org.eclipse.set.model.test.site.Site;
import org.eclipse.set.model.test.site.SitePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Site</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.set.model.test.site.impl.SiteImpl#getAllBuildings <em>All Buildings</em>}</li>
 *   <li>{@link org.eclipse.set.model.test.site.impl.SiteImpl#getAllRooms <em>All Rooms</em>}</li>
 *   <li>{@link org.eclipse.set.model.test.site.impl.SiteImpl#getAllFloors <em>All Floors</em>}</li>
 *   <li>{@link org.eclipse.set.model.test.site.impl.SiteImpl#getAllPassages <em>All Passages</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SiteImpl extends MinimalEObjectImpl.Container implements Site {
	/**
	 * The cached value of the '{@link #getAllBuildings() <em>All Buildings</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAllBuildings()
	 * @generated
	 * @ordered
	 */
	protected EList<Building> allBuildings;

	/**
	 * The cached value of the '{@link #getAllRooms() <em>All Rooms</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAllRooms()
	 * @generated
	 * @ordered
	 */
	protected EList<Room> allRooms;

	/**
	 * The cached value of the '{@link #getAllFloors() <em>All Floors</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAllFloors()
	 * @generated
	 * @ordered
	 */
	protected EList<Floor> allFloors;

	/**
	 * The cached value of the '{@link #getAllPassages() <em>All Passages</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAllPassages()
	 * @generated
	 * @ordered
	 */
	protected EList<Passage> allPassages;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SiteImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SitePackage.Literals.SITE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Building> getAllBuildings() {
		if (allBuildings == null) {
			allBuildings = new EObjectContainmentEList<Building>(Building.class, this, SitePackage.SITE__ALL_BUILDINGS);
		}
		return allBuildings;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Room> getAllRooms() {
		if (allRooms == null) {
			allRooms = new EObjectContainmentEList<Room>(Room.class, this, SitePackage.SITE__ALL_ROOMS);
		}
		return allRooms;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Floor> getAllFloors() {
		if (allFloors == null) {
			allFloors = new EObjectContainmentEList<Floor>(Floor.class, this, SitePackage.SITE__ALL_FLOORS);
		}
		return allFloors;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Passage> getAllPassages() {
		if (allPassages == null) {
			allPassages = new EObjectContainmentEList<Passage>(Passage.class, this, SitePackage.SITE__ALL_PASSAGES);
		}
		return allPassages;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SitePackage.SITE__ALL_BUILDINGS:
				return ((InternalEList<?>)getAllBuildings()).basicRemove(otherEnd, msgs);
			case SitePackage.SITE__ALL_ROOMS:
				return ((InternalEList<?>)getAllRooms()).basicRemove(otherEnd, msgs);
			case SitePackage.SITE__ALL_FLOORS:
				return ((InternalEList<?>)getAllFloors()).basicRemove(otherEnd, msgs);
			case SitePackage.SITE__ALL_PASSAGES:
				return ((InternalEList<?>)getAllPassages()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case SitePackage.SITE__ALL_BUILDINGS:
				return getAllBuildings();
			case SitePackage.SITE__ALL_ROOMS:
				return getAllRooms();
			case SitePackage.SITE__ALL_FLOORS:
				return getAllFloors();
			case SitePackage.SITE__ALL_PASSAGES:
				return getAllPassages();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case SitePackage.SITE__ALL_BUILDINGS:
				getAllBuildings().clear();
				getAllBuildings().addAll((Collection<? extends Building>)newValue);
				return;
			case SitePackage.SITE__ALL_ROOMS:
				getAllRooms().clear();
				getAllRooms().addAll((Collection<? extends Room>)newValue);
				return;
			case SitePackage.SITE__ALL_FLOORS:
				getAllFloors().clear();
				getAllFloors().addAll((Collection<? extends Floor>)newValue);
				return;
			case SitePackage.SITE__ALL_PASSAGES:
				getAllPassages().clear();
				getAllPassages().addAll((Collection<? extends Passage>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case SitePackage.SITE__ALL_BUILDINGS:
				getAllBuildings().clear();
				return;
			case SitePackage.SITE__ALL_ROOMS:
				getAllRooms().clear();
				return;
			case SitePackage.SITE__ALL_FLOORS:
				getAllFloors().clear();
				return;
			case SitePackage.SITE__ALL_PASSAGES:
				getAllPassages().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case SitePackage.SITE__ALL_BUILDINGS:
				return allBuildings != null && !allBuildings.isEmpty();
			case SitePackage.SITE__ALL_ROOMS:
				return allRooms != null && !allRooms.isEmpty();
			case SitePackage.SITE__ALL_FLOORS:
				return allFloors != null && !allFloors.isEmpty();
			case SitePackage.SITE__ALL_PASSAGES:
				return allPassages != null && !allPassages.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //SiteImpl
