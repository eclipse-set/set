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

import org.eclipse.set.model.siteplan.KMMarker;
import org.eclipse.set.model.siteplan.Route;
import org.eclipse.set.model.siteplan.RouteSection;
import org.eclipse.set.model.siteplan.SiteplanPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object
 * '<em><b>Route</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.set.model.siteplan.impl.RouteImpl#getSections
 * <em>Sections</em>}</li>
 * <li>{@link org.eclipse.set.model.siteplan.impl.RouteImpl#getMarkers
 * <em>Markers</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RouteImpl extends SiteplanObjectImpl implements Route {
	/**
	 * The cached value of the '{@link #getSections() <em>Sections</em>}'
	 * containment reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getSections()
	 * @generated
	 * @ordered
	 */
	protected EList<RouteSection> sections;

	/**
	 * The cached value of the '{@link #getMarkers() <em>Markers</em>}'
	 * containment reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getMarkers()
	 * @generated
	 * @ordered
	 */
	protected EList<KMMarker> markers;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected RouteImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SiteplanPackage.Literals.ROUTE;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<RouteSection> getSections() {
		if (sections == null) {
			sections = new EObjectContainmentEList<RouteSection>(
					RouteSection.class, this, SiteplanPackage.ROUTE__SECTIONS);
		}
		return sections;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<KMMarker> getMarkers() {
		if (markers == null) {
			markers = new EObjectContainmentEList<KMMarker>(KMMarker.class,
					this, SiteplanPackage.ROUTE__MARKERS);
		}
		return markers;
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
			case SiteplanPackage.ROUTE__SECTIONS:
				return ((InternalEList<?>) getSections()).basicRemove(otherEnd,
						msgs);
			case SiteplanPackage.ROUTE__MARKERS:
				return ((InternalEList<?>) getMarkers()).basicRemove(otherEnd,
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
			case SiteplanPackage.ROUTE__SECTIONS:
				return getSections();
			case SiteplanPackage.ROUTE__MARKERS:
				return getMarkers();
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
			case SiteplanPackage.ROUTE__SECTIONS:
				getSections().clear();
				getSections()
						.addAll((Collection<? extends RouteSection>) newValue);
				return;
			case SiteplanPackage.ROUTE__MARKERS:
				getMarkers().clear();
				getMarkers().addAll((Collection<? extends KMMarker>) newValue);
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
			case SiteplanPackage.ROUTE__SECTIONS:
				getSections().clear();
				return;
			case SiteplanPackage.ROUTE__MARKERS:
				getMarkers().clear();
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
			case SiteplanPackage.ROUTE__SECTIONS:
				return sections != null && !sections.isEmpty();
			case SiteplanPackage.ROUTE__MARKERS:
				return markers != null && !markers.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} // RouteImpl
