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

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

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
 *   <li>{@link org.eclipse.set.model.siteplan.impl.TrackImpl#getSections <em>Sections</em>}</li>
 *   <li>{@link org.eclipse.set.model.siteplan.impl.TrackImpl#getDesignations <em>Designations</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TrackImpl extends SiteplanObjectImpl implements Track {
	/**
	 * The cached value of the '{@link #getSections() <em>Sections</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getSections()
	 * @generated
	 * @ordered
	 */
	protected EList<TrackSection> sections;

	/**
	 * The cached value of the '{@link #getDesignations() <em>Designations</em>}' containment reference list.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @see #getDesignations()
	 * @generated
	 * @ordered
	 */
	protected EList<TrackDesignation> designations;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected TrackImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SiteplanPackage.Literals.TRACK;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<TrackSection> getSections() {
		if (sections == null) {
			sections = new EObjectContainmentEList<TrackSection>(TrackSection.class, this, SiteplanPackage.TRACK__SECTIONS);
		}
		return sections;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<TrackDesignation> getDesignations() {
		if (designations == null) {
			designations = new EObjectContainmentEList<TrackDesignation>(TrackDesignation.class, this, SiteplanPackage.TRACK__DESIGNATIONS);
		}
		return designations;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd,
			int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SiteplanPackage.TRACK__SECTIONS:
				return ((InternalEList<?>)getSections()).basicRemove(otherEnd, msgs);
			case SiteplanPackage.TRACK__DESIGNATIONS:
				return ((InternalEList<?>)getDesignations()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case SiteplanPackage.TRACK__SECTIONS:
				return getSections();
			case SiteplanPackage.TRACK__DESIGNATIONS:
				return getDesignations();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case SiteplanPackage.TRACK__SECTIONS:
				getSections().clear();
				getSections().addAll((Collection<? extends TrackSection>)newValue);
				return;
			case SiteplanPackage.TRACK__DESIGNATIONS:
				getDesignations().clear();
				getDesignations().addAll((Collection<? extends TrackDesignation>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
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
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case SiteplanPackage.TRACK__SECTIONS:
				return sections != null && !sections.isEmpty();
			case SiteplanPackage.TRACK__DESIGNATIONS:
				return designations != null && !designations.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} // TrackImpl
