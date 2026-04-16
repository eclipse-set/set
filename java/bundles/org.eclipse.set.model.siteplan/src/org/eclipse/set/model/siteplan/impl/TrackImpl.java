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

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.set.model.siteplan.Coordinate;
import org.eclipse.set.model.siteplan.SiteplanPackage;
import org.eclipse.set.model.siteplan.Track;
import org.eclipse.set.model.siteplan.TrackDesignation;
import org.eclipse.set.model.siteplan.TrackSection;

/**
 * <!-- begin-user-doc --> An implementation of the model object
 * '<em><b>Track</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.set.model.siteplan.impl.TrackImpl#getSections
 * <em>Sections</em>}</li>
 * <li>{@link org.eclipse.set.model.siteplan.impl.TrackImpl#getDesignations
 * <em>Designations</em>}</li>
 * <li>{@link org.eclipse.set.model.siteplan.impl.TrackImpl#getStartCoordinate
 * <em>Start Coordinate</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TrackImpl extends SiteplanObjectImpl implements Track {
	/**
	 * The cached value of the '{@link #getSections() <em>Sections</em>}'
	 * containment reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getSections()
	 * @generated
	 * @ordered
	 */
	protected EList<TrackSection> sections;

	/**
	 * The cached value of the '{@link #getDesignations()
	 * <em>Designations</em>}' containment reference list. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #getDesignations()
	 * @generated
	 * @ordered
	 */
	protected EList<TrackDesignation> designations;

	/**
	 * The cached value of the '{@link #getStartCoordinate() <em>Start
	 * Coordinate</em>}' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getStartCoordinate()
	 * @generated
	 * @ordered
	 */
	protected Coordinate startCoordinate;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected TrackImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SiteplanPackage.Literals.TRACK;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<TrackSection> getSections() {
		if (sections == null) {
			sections = new EObjectContainmentEList<TrackSection>(
					TrackSection.class, this, SiteplanPackage.TRACK__SECTIONS);
		}
		return sections;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<TrackDesignation> getDesignations() {
		if (designations == null) {
			designations = new EObjectContainmentEList<TrackDesignation>(
					TrackDesignation.class, this,
					SiteplanPackage.TRACK__DESIGNATIONS);
		}
		return designations;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Coordinate getStartCoordinate() {
		return startCoordinate;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetStartCoordinate(
			Coordinate newStartCoordinate, NotificationChain msgs) {
		Coordinate oldStartCoordinate = startCoordinate;
		startCoordinate = newStartCoordinate;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this,
					Notification.SET, SiteplanPackage.TRACK__START_COORDINATE,
					oldStartCoordinate, newStartCoordinate);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setStartCoordinate(Coordinate newStartCoordinate) {
		if (newStartCoordinate != startCoordinate) {
			NotificationChain msgs = null;
			if (startCoordinate != null)
				msgs = ((InternalEObject) startCoordinate).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE
								- SiteplanPackage.TRACK__START_COORDINATE,
						null, msgs);
			if (newStartCoordinate != null)
				msgs = ((InternalEObject) newStartCoordinate).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE
								- SiteplanPackage.TRACK__START_COORDINATE,
						null, msgs);
			msgs = basicSetStartCoordinate(newStartCoordinate, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					SiteplanPackage.TRACK__START_COORDINATE, newStartCoordinate,
					newStartCoordinate));
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
			case SiteplanPackage.TRACK__SECTIONS:
				return ((InternalEList<?>) getSections()).basicRemove(otherEnd,
						msgs);
			case SiteplanPackage.TRACK__DESIGNATIONS:
				return ((InternalEList<?>) getDesignations())
						.basicRemove(otherEnd, msgs);
			case SiteplanPackage.TRACK__START_COORDINATE:
				return basicSetStartCoordinate(null, msgs);
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
			case SiteplanPackage.TRACK__SECTIONS:
				return getSections();
			case SiteplanPackage.TRACK__DESIGNATIONS:
				return getDesignations();
			case SiteplanPackage.TRACK__START_COORDINATE:
				return getStartCoordinate();
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
			case SiteplanPackage.TRACK__SECTIONS:
				getSections().clear();
				getSections()
						.addAll((Collection<? extends TrackSection>) newValue);
				return;
			case SiteplanPackage.TRACK__DESIGNATIONS:
				getDesignations().clear();
				getDesignations().addAll(
						(Collection<? extends TrackDesignation>) newValue);
				return;
			case SiteplanPackage.TRACK__START_COORDINATE:
				setStartCoordinate((Coordinate) newValue);
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
			case SiteplanPackage.TRACK__SECTIONS:
				getSections().clear();
				return;
			case SiteplanPackage.TRACK__DESIGNATIONS:
				getDesignations().clear();
				return;
			case SiteplanPackage.TRACK__START_COORDINATE:
				setStartCoordinate((Coordinate) null);
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
			case SiteplanPackage.TRACK__SECTIONS:
				return sections != null && !sections.isEmpty();
			case SiteplanPackage.TRACK__DESIGNATIONS:
				return designations != null && !designations.isEmpty();
			case SiteplanPackage.TRACK__START_COORDINATE:
				return startCoordinate != null;
		}
		return super.eIsSet(featureID);
	}

} // TrackImpl
