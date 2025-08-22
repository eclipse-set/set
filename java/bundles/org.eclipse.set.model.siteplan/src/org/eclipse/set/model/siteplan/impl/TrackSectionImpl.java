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
import org.eclipse.set.model.siteplan.TrackSection;
import org.eclipse.set.model.siteplan.TrackSegment;
import org.eclipse.set.model.siteplan.TrackShape;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Track
 * Section</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.set.model.siteplan.impl.TrackSectionImpl#getShape
 * <em>Shape</em>}</li>
 * <li>{@link org.eclipse.set.model.siteplan.impl.TrackSectionImpl#getSegments
 * <em>Segments</em>}</li>
 * <li>{@link org.eclipse.set.model.siteplan.impl.TrackSectionImpl#getColor
 * <em>Color</em>}</li>
 * <li>{@link org.eclipse.set.model.siteplan.impl.TrackSectionImpl#getStartCoordinate
 * <em>Start Coordinate</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TrackSectionImpl extends SiteplanObjectImpl
		implements TrackSection {
	/**
	 * The default value of the '{@link #getShape() <em>Shape</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getShape()
	 * @generated
	 * @ordered
	 */
	protected static final TrackShape SHAPE_EDEFAULT = TrackShape.STRAIGHT;

	/**
	 * The cached value of the '{@link #getShape() <em>Shape</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getShape()
	 * @generated
	 * @ordered
	 */
	protected TrackShape shape = SHAPE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getSegments() <em>Segments</em>}'
	 * containment reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getSegments()
	 * @generated
	 * @ordered
	 */
	protected EList<TrackSegment> segments;

	/**
	 * The default value of the '{@link #getColor() <em>Color</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getColor()
	 * @generated
	 * @ordered
	 */
	protected static final String COLOR_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getColor() <em>Color</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getColor()
	 * @generated
	 * @ordered
	 */
	protected String color = COLOR_EDEFAULT;

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
	protected TrackSectionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SiteplanPackage.Literals.TRACK_SECTION;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public TrackShape getShape() {
		return shape;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setShape(TrackShape newShape) {
		TrackShape oldShape = shape;
		shape = newShape == null ? SHAPE_EDEFAULT : newShape;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					SiteplanPackage.TRACK_SECTION__SHAPE, oldShape, shape));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<TrackSegment> getSegments() {
		if (segments == null) {
			segments = new EObjectContainmentEList<TrackSegment>(
					TrackSegment.class, this,
					SiteplanPackage.TRACK_SECTION__SEGMENTS);
		}
		return segments;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getColor() {
		return color;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setColor(String newColor) {
		String oldColor = color;
		color = newColor;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					SiteplanPackage.TRACK_SECTION__COLOR, oldColor, color));
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
					Notification.SET,
					SiteplanPackage.TRACK_SECTION__START_COORDINATE,
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
								- SiteplanPackage.TRACK_SECTION__START_COORDINATE,
						null, msgs);
			if (newStartCoordinate != null)
				msgs = ((InternalEObject) newStartCoordinate).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE
								- SiteplanPackage.TRACK_SECTION__START_COORDINATE,
						null, msgs);
			msgs = basicSetStartCoordinate(newStartCoordinate, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					SiteplanPackage.TRACK_SECTION__START_COORDINATE,
					newStartCoordinate, newStartCoordinate));
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
			case SiteplanPackage.TRACK_SECTION__SEGMENTS:
				return ((InternalEList<?>) getSegments()).basicRemove(otherEnd,
						msgs);
			case SiteplanPackage.TRACK_SECTION__START_COORDINATE:
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
			case SiteplanPackage.TRACK_SECTION__SHAPE:
				return getShape();
			case SiteplanPackage.TRACK_SECTION__SEGMENTS:
				return getSegments();
			case SiteplanPackage.TRACK_SECTION__COLOR:
				return getColor();
			case SiteplanPackage.TRACK_SECTION__START_COORDINATE:
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
			case SiteplanPackage.TRACK_SECTION__SHAPE:
				setShape((TrackShape) newValue);
				return;
			case SiteplanPackage.TRACK_SECTION__SEGMENTS:
				getSegments().clear();
				getSegments()
						.addAll((Collection<? extends TrackSegment>) newValue);
				return;
			case SiteplanPackage.TRACK_SECTION__COLOR:
				setColor((String) newValue);
				return;
			case SiteplanPackage.TRACK_SECTION__START_COORDINATE:
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
			case SiteplanPackage.TRACK_SECTION__SHAPE:
				setShape(SHAPE_EDEFAULT);
				return;
			case SiteplanPackage.TRACK_SECTION__SEGMENTS:
				getSegments().clear();
				return;
			case SiteplanPackage.TRACK_SECTION__COLOR:
				setColor(COLOR_EDEFAULT);
				return;
			case SiteplanPackage.TRACK_SECTION__START_COORDINATE:
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
			case SiteplanPackage.TRACK_SECTION__SHAPE:
				return shape != SHAPE_EDEFAULT;
			case SiteplanPackage.TRACK_SECTION__SEGMENTS:
				return segments != null && !segments.isEmpty();
			case SiteplanPackage.TRACK_SECTION__COLOR:
				return COLOR_EDEFAULT == null ? color != null
						: !COLOR_EDEFAULT.equals(color);
			case SiteplanPackage.TRACK_SECTION__START_COORDINATE:
				return startCoordinate != null;
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
		result.append(" (shape: ");
		result.append(shape);
		result.append(", color: ");
		result.append(color);
		result.append(')');
		return result.toString();
	}

} // TrackSectionImpl
