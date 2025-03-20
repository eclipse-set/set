/**
 * Copyright (c) 2021 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.siteplan.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.set.model.siteplan.ContinuousTrackSegment;
import org.eclipse.set.model.siteplan.Coordinate;
import org.eclipse.set.model.siteplan.SiteplanPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object
 * '<em><b>Continuous Track Segment</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.set.model.siteplan.impl.ContinuousTrackSegmentImpl#getStart
 * <em>Start</em>}</li>
 * <li>{@link org.eclipse.set.model.siteplan.impl.ContinuousTrackSegmentImpl#getEnd
 * <em>End</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ContinuousTrackSegmentImpl extends MinimalEObjectImpl.Container
		implements ContinuousTrackSegment {
	/**
	 * The cached value of the '{@link #getStart() <em>Start</em>}' containment
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getStart()
	 * @generated
	 * @ordered
	 */
	protected Coordinate start;

	/**
	 * The cached value of the '{@link #getEnd() <em>End</em>}' containment
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getEnd()
	 * @generated
	 * @ordered
	 */
	protected Coordinate end;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected ContinuousTrackSegmentImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SiteplanPackage.Literals.CONTINUOUS_TRACK_SEGMENT;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Coordinate getStart() {
		return start;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetStart(Coordinate newStart,
			NotificationChain msgs) {
		Coordinate oldStart = start;
		start = newStart;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this,
					Notification.SET,
					SiteplanPackage.CONTINUOUS_TRACK_SEGMENT__START, oldStart,
					newStart);
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
	public void setStart(Coordinate newStart) {
		if (newStart != start) {
			NotificationChain msgs = null;
			if (start != null)
				msgs = ((InternalEObject) start).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE
								- SiteplanPackage.CONTINUOUS_TRACK_SEGMENT__START,
						null, msgs);
			if (newStart != null)
				msgs = ((InternalEObject) newStart).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE
								- SiteplanPackage.CONTINUOUS_TRACK_SEGMENT__START,
						null, msgs);
			msgs = basicSetStart(newStart, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					SiteplanPackage.CONTINUOUS_TRACK_SEGMENT__START, newStart,
					newStart));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Coordinate getEnd() {
		return end;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetEnd(Coordinate newEnd,
			NotificationChain msgs) {
		Coordinate oldEnd = end;
		end = newEnd;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this,
					Notification.SET,
					SiteplanPackage.CONTINUOUS_TRACK_SEGMENT__END, oldEnd,
					newEnd);
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
	public void setEnd(Coordinate newEnd) {
		if (newEnd != end) {
			NotificationChain msgs = null;
			if (end != null)
				msgs = ((InternalEObject) end).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE
								- SiteplanPackage.CONTINUOUS_TRACK_SEGMENT__END,
						null, msgs);
			if (newEnd != null)
				msgs = ((InternalEObject) newEnd).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE
								- SiteplanPackage.CONTINUOUS_TRACK_SEGMENT__END,
						null, msgs);
			msgs = basicSetEnd(newEnd, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					SiteplanPackage.CONTINUOUS_TRACK_SEGMENT__END, newEnd,
					newEnd));
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
			case SiteplanPackage.CONTINUOUS_TRACK_SEGMENT__START:
				return basicSetStart(null, msgs);
			case SiteplanPackage.CONTINUOUS_TRACK_SEGMENT__END:
				return basicSetEnd(null, msgs);
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
			case SiteplanPackage.CONTINUOUS_TRACK_SEGMENT__START:
				return getStart();
			case SiteplanPackage.CONTINUOUS_TRACK_SEGMENT__END:
				return getEnd();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case SiteplanPackage.CONTINUOUS_TRACK_SEGMENT__START:
				setStart((Coordinate) newValue);
				return;
			case SiteplanPackage.CONTINUOUS_TRACK_SEGMENT__END:
				setEnd((Coordinate) newValue);
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
			case SiteplanPackage.CONTINUOUS_TRACK_SEGMENT__START:
				setStart((Coordinate) null);
				return;
			case SiteplanPackage.CONTINUOUS_TRACK_SEGMENT__END:
				setEnd((Coordinate) null);
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
			case SiteplanPackage.CONTINUOUS_TRACK_SEGMENT__START:
				return start != null;
			case SiteplanPackage.CONTINUOUS_TRACK_SEGMENT__END:
				return end != null;
		}
		return super.eIsSet(featureID);
	}

} // ContinuousTrackSegmentImpl
