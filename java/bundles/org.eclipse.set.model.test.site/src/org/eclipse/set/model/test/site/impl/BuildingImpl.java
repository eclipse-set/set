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

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import org.eclipse.set.model.test.site.Building;
import org.eclipse.set.model.test.site.BuildingNames;
import org.eclipse.set.model.test.site.SitePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Building</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.set.model.test.site.impl.BuildingImpl#getNames <em>Names</em>}</li>
 *   <li>{@link org.eclipse.set.model.test.site.impl.BuildingImpl#getBuildingsInTheSameStreet <em>Buildings In The Same Street</em>}</li>
 *   <li>{@link org.eclipse.set.model.test.site.impl.BuildingImpl#getData <em>Data</em>}</li>
 * </ul>
 *
 * @generated
 */
public class BuildingImpl extends IdentifiedImpl implements Building {
	/**
	 * The cached value of the '{@link #getNames() <em>Names</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNames()
	 * @generated
	 * @ordered
	 */
	protected BuildingNames names;

	/**
	 * The cached value of the '{@link #getBuildingsInTheSameStreet() <em>Buildings In The Same Street</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBuildingsInTheSameStreet()
	 * @generated
	 * @ordered
	 */
	protected EList<Building> buildingsInTheSameStreet;

	/**
	 * The default value of the '{@link #getData() <em>Data</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getData()
	 * @generated
	 * @ordered
	 */
	protected static final byte[] DATA_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getData() <em>Data</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getData()
	 * @generated
	 * @ordered
	 */
	protected byte[] data = DATA_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BuildingImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SitePackage.Literals.BUILDING;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BuildingNames getNames() {
		return names;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetNames(BuildingNames newNames, NotificationChain msgs) {
		BuildingNames oldNames = names;
		names = newNames;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SitePackage.BUILDING__NAMES, oldNames, newNames);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNames(BuildingNames newNames) {
		if (newNames != names) {
			NotificationChain msgs = null;
			if (names != null)
				msgs = ((InternalEObject)names).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - SitePackage.BUILDING__NAMES, null, msgs);
			if (newNames != null)
				msgs = ((InternalEObject)newNames).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - SitePackage.BUILDING__NAMES, null, msgs);
			msgs = basicSetNames(newNames, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SitePackage.BUILDING__NAMES, newNames, newNames));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Building> getBuildingsInTheSameStreet() {
		if (buildingsInTheSameStreet == null) {
			buildingsInTheSameStreet = new EObjectResolvingEList<Building>(Building.class, this, SitePackage.BUILDING__BUILDINGS_IN_THE_SAME_STREET);
		}
		return buildingsInTheSameStreet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public byte[] getData() {
		return data;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setData(byte[] newData) {
		byte[] oldData = data;
		data = newData;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SitePackage.BUILDING__DATA, oldData, data));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SitePackage.BUILDING__NAMES:
				return basicSetNames(null, msgs);
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
			case SitePackage.BUILDING__NAMES:
				return getNames();
			case SitePackage.BUILDING__BUILDINGS_IN_THE_SAME_STREET:
				return getBuildingsInTheSameStreet();
			case SitePackage.BUILDING__DATA:
				return getData();
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
			case SitePackage.BUILDING__NAMES:
				setNames((BuildingNames)newValue);
				return;
			case SitePackage.BUILDING__BUILDINGS_IN_THE_SAME_STREET:
				getBuildingsInTheSameStreet().clear();
				getBuildingsInTheSameStreet().addAll((Collection<? extends Building>)newValue);
				return;
			case SitePackage.BUILDING__DATA:
				setData((byte[])newValue);
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
			case SitePackage.BUILDING__NAMES:
				setNames((BuildingNames)null);
				return;
			case SitePackage.BUILDING__BUILDINGS_IN_THE_SAME_STREET:
				getBuildingsInTheSameStreet().clear();
				return;
			case SitePackage.BUILDING__DATA:
				setData(DATA_EDEFAULT);
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
			case SitePackage.BUILDING__NAMES:
				return names != null;
			case SitePackage.BUILDING__BUILDINGS_IN_THE_SAME_STREET:
				return buildingsInTheSameStreet != null && !buildingsInTheSameStreet.isEmpty();
			case SitePackage.BUILDING__DATA:
				return DATA_EDEFAULT == null ? data != null : !DATA_EDEFAULT.equals(data);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (data: ");
		result.append(data);
		result.append(')');
		return result.toString();
	}

} //BuildingImpl
