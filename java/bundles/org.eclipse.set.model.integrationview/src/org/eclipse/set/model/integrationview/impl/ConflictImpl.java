/**
 * Copyright (c) 2018 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.integrationview.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.set.model.integrationview.Conflict;
import org.eclipse.set.model.integrationview.Details;
import org.eclipse.set.model.integrationview.IntegrationviewPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object
 * '<em><b>Conflict</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.set.model.integrationview.impl.ConflictImpl#getId
 * <em>Id</em>}</li>
 * <li>{@link org.eclipse.set.model.integrationview.impl.ConflictImpl#getName
 * <em>Name</em>}</li>
 * <li>{@link org.eclipse.set.model.integrationview.impl.ConflictImpl#getContainer
 * <em>Container</em>}</li>
 * <li>{@link org.eclipse.set.model.integrationview.impl.ConflictImpl#getVersion
 * <em>Version</em>}</li>
 * <li>{@link org.eclipse.set.model.integrationview.impl.ConflictImpl#getResolution
 * <em>Resolution</em>}</li>
 * <li>{@link org.eclipse.set.model.integrationview.impl.ConflictImpl#getDetails
 * <em>Details</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ConflictImpl extends MinimalEObjectImpl.Container
		implements Conflict {
	/**
	 * The default value of the '{@link #getId() <em>Id</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected static final int ID_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getId() <em>Id</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected int id = ID_EDEFAULT;

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getContainer() <em>Container</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getContainer()
	 * @generated
	 * @ordered
	 */
	protected static final String CONTAINER_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getContainer() <em>Container</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getContainer()
	 * @generated
	 * @ordered
	 */
	protected String container = CONTAINER_EDEFAULT;

	/**
	 * The default value of the '{@link #getVersion() <em>Version</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getVersion()
	 * @generated
	 * @ordered
	 */
	protected static final String VERSION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getVersion() <em>Version</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getVersion()
	 * @generated
	 * @ordered
	 */
	protected String version = VERSION_EDEFAULT;

	/**
	 * The default value of the '{@link #getResolution() <em>Resolution</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getResolution()
	 * @generated
	 * @ordered
	 */
	protected static final String RESOLUTION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getResolution() <em>Resolution</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getResolution()
	 * @generated
	 * @ordered
	 */
	protected String resolution = RESOLUTION_EDEFAULT;

	/**
	 * The cached value of the '{@link #getDetails() <em>Details</em>}'
	 * containment reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getDetails()
	 * @generated
	 * @ordered
	 */
	protected EList<Details> details;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected ConflictImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IntegrationviewPackage.Literals.CONFLICT;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public int getId() {
		return id;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setId(int newId) {
		int oldId = id;
		id = newId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IntegrationviewPackage.CONFLICT__ID, oldId, id));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IntegrationviewPackage.CONFLICT__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getContainer() {
		return container;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setContainer(String newContainer) {
		String oldContainer = container;
		container = newContainer;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IntegrationviewPackage.CONFLICT__CONTAINER, oldContainer,
					container));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getVersion() {
		return version;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setVersion(String newVersion) {
		String oldVersion = version;
		version = newVersion;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IntegrationviewPackage.CONFLICT__VERSION, oldVersion,
					version));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getResolution() {
		return resolution;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setResolution(String newResolution) {
		String oldResolution = resolution;
		resolution = newResolution;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IntegrationviewPackage.CONFLICT__RESOLUTION, oldResolution,
					resolution));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<Details> getDetails() {
		if (details == null) {
			details = new EObjectContainmentEList<Details>(Details.class, this,
					IntegrationviewPackage.CONFLICT__DETAILS);
		}
		return details;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd,
			int featureID, NotificationChain msgs) {
		switch (featureID) {
			case IntegrationviewPackage.CONFLICT__DETAILS:
				return ((InternalEList<?>) getDetails()).basicRemove(otherEnd,
						msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case IntegrationviewPackage.CONFLICT__ID:
				return getId();
			case IntegrationviewPackage.CONFLICT__NAME:
				return getName();
			case IntegrationviewPackage.CONFLICT__CONTAINER:
				return getContainer();
			case IntegrationviewPackage.CONFLICT__VERSION:
				return getVersion();
			case IntegrationviewPackage.CONFLICT__RESOLUTION:
				return getResolution();
			case IntegrationviewPackage.CONFLICT__DETAILS:
				return getDetails();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case IntegrationviewPackage.CONFLICT__ID:
				setId((Integer) newValue);
				return;
			case IntegrationviewPackage.CONFLICT__NAME:
				setName((String) newValue);
				return;
			case IntegrationviewPackage.CONFLICT__CONTAINER:
				setContainer((String) newValue);
				return;
			case IntegrationviewPackage.CONFLICT__VERSION:
				setVersion((String) newValue);
				return;
			case IntegrationviewPackage.CONFLICT__RESOLUTION:
				setResolution((String) newValue);
				return;
			case IntegrationviewPackage.CONFLICT__DETAILS:
				getDetails().clear();
				getDetails().addAll((Collection<? extends Details>) newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case IntegrationviewPackage.CONFLICT__ID:
				setId(ID_EDEFAULT);
				return;
			case IntegrationviewPackage.CONFLICT__NAME:
				setName(NAME_EDEFAULT);
				return;
			case IntegrationviewPackage.CONFLICT__CONTAINER:
				setContainer(CONTAINER_EDEFAULT);
				return;
			case IntegrationviewPackage.CONFLICT__VERSION:
				setVersion(VERSION_EDEFAULT);
				return;
			case IntegrationviewPackage.CONFLICT__RESOLUTION:
				setResolution(RESOLUTION_EDEFAULT);
				return;
			case IntegrationviewPackage.CONFLICT__DETAILS:
				getDetails().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case IntegrationviewPackage.CONFLICT__ID:
				return id != ID_EDEFAULT;
			case IntegrationviewPackage.CONFLICT__NAME:
				return NAME_EDEFAULT == null ? name != null
						: !NAME_EDEFAULT.equals(name);
			case IntegrationviewPackage.CONFLICT__CONTAINER:
				return CONTAINER_EDEFAULT == null ? container != null
						: !CONTAINER_EDEFAULT.equals(container);
			case IntegrationviewPackage.CONFLICT__VERSION:
				return VERSION_EDEFAULT == null ? version != null
						: !VERSION_EDEFAULT.equals(version);
			case IntegrationviewPackage.CONFLICT__RESOLUTION:
				return RESOLUTION_EDEFAULT == null ? resolution != null
						: !RESOLUTION_EDEFAULT.equals(resolution);
			case IntegrationviewPackage.CONFLICT__DETAILS:
				return details != null && !details.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy())
			return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (id: ");
		result.append(id);
		result.append(", name: ");
		result.append(name);
		result.append(", container: ");
		result.append(container);
		result.append(", version: ");
		result.append(version);
		result.append(", resolution: ");
		result.append(resolution);
		result.append(')');
		return result.toString();
	}

} // ConflictImpl
