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
import org.eclipse.set.model.siteplan.SheetCut;
import org.eclipse.set.model.siteplan.SiteplanPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Sheet
 * Cut</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.set.model.siteplan.impl.SheetCutImpl#getLabel <em>Label</em>}</li>
 *   <li>{@link org.eclipse.set.model.siteplan.impl.SheetCutImpl#getPolygonDirection <em>Polygon Direction</em>}</li>
 *   <li>{@link org.eclipse.set.model.siteplan.impl.SheetCutImpl#getPolygon <em>Polygon</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SheetCutImpl extends SiteplanObjectImpl implements SheetCut {
	/**
	 * The default value of the '{@link #getLabel() <em>Label</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getLabel()
	 * @generated
	 * @ordered
	 */
	protected static final String LABEL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLabel() <em>Label</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getLabel()
	 * @generated
	 * @ordered
	 */
	protected String label = LABEL_EDEFAULT;

	/**
	 * The cached value of the '{@link #getPolygonDirection() <em>Polygon Direction</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #getPolygonDirection()
	 * @generated
	 * @ordered
	 */
	protected EList<Coordinate> polygonDirection;

	/**
	 * The cached value of the '{@link #getPolygon() <em>Polygon</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getPolygon()
	 * @generated
	 * @ordered
	 */
	protected EList<Coordinate> polygon;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected SheetCutImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SiteplanPackage.Literals.SHEET_CUT;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getLabel() {
		return label;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setLabel(String newLabel) {
		String oldLabel = label;
		label = newLabel;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SiteplanPackage.SHEET_CUT__LABEL, oldLabel, label));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Coordinate> getPolygonDirection() {
		if (polygonDirection == null) {
			polygonDirection = new EObjectContainmentEList<Coordinate>(Coordinate.class, this, SiteplanPackage.SHEET_CUT__POLYGON_DIRECTION);
		}
		return polygonDirection;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Coordinate> getPolygon() {
		if (polygon == null) {
			polygon = new EObjectContainmentEList<Coordinate>(Coordinate.class, this, SiteplanPackage.SHEET_CUT__POLYGON);
		}
		return polygon;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd,
			int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SiteplanPackage.SHEET_CUT__POLYGON_DIRECTION:
				return ((InternalEList<?>)getPolygonDirection()).basicRemove(otherEnd, msgs);
			case SiteplanPackage.SHEET_CUT__POLYGON:
				return ((InternalEList<?>)getPolygon()).basicRemove(otherEnd, msgs);
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
			case SiteplanPackage.SHEET_CUT__LABEL:
				return getLabel();
			case SiteplanPackage.SHEET_CUT__POLYGON_DIRECTION:
				return getPolygonDirection();
			case SiteplanPackage.SHEET_CUT__POLYGON:
				return getPolygon();
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
			case SiteplanPackage.SHEET_CUT__LABEL:
				setLabel((String)newValue);
				return;
			case SiteplanPackage.SHEET_CUT__POLYGON_DIRECTION:
				getPolygonDirection().clear();
				getPolygonDirection().addAll((Collection<? extends Coordinate>)newValue);
				return;
			case SiteplanPackage.SHEET_CUT__POLYGON:
				getPolygon().clear();
				getPolygon().addAll((Collection<? extends Coordinate>)newValue);
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
			case SiteplanPackage.SHEET_CUT__LABEL:
				setLabel(LABEL_EDEFAULT);
				return;
			case SiteplanPackage.SHEET_CUT__POLYGON_DIRECTION:
				getPolygonDirection().clear();
				return;
			case SiteplanPackage.SHEET_CUT__POLYGON:
				getPolygon().clear();
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
			case SiteplanPackage.SHEET_CUT__LABEL:
				return LABEL_EDEFAULT == null ? label != null : !LABEL_EDEFAULT.equals(label);
			case SiteplanPackage.SHEET_CUT__POLYGON_DIRECTION:
				return polygonDirection != null && !polygonDirection.isEmpty();
			case SiteplanPackage.SHEET_CUT__POLYGON:
				return polygon != null && !polygon.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (label: ");
		result.append(label);
		result.append(')');
		return result.toString();
	}

} // SheetCutImpl
