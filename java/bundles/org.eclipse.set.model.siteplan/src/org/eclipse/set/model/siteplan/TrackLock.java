/**
 * Copyright (c) 2021 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.siteplan;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Track
 * Lock</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.set.model.siteplan.TrackLock#getComponents <em>Components</em>}</li>
 *   <li>{@link org.eclipse.set.model.siteplan.TrackLock#getPreferredLocation <em>Preferred Location</em>}</li>
 *   <li>{@link org.eclipse.set.model.siteplan.TrackLock#getOperatingMode <em>Operating Mode</em>}</li>
 *   <li>{@link org.eclipse.set.model.siteplan.TrackLock#getLabel <em>Label</em>}</li>
 * </ul>
 *
 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getTrackLock()
 * @model
 * @generated
 */
public interface TrackLock extends SiteplanObject {
	/**
	 * Returns the value of the '<em><b>Components</b></em>' containment
	 * reference list. The list contents are of type
	 * {@link org.eclipse.set.model.siteplan.TrackLockComponent}. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Components</em>' containment reference
	 *         list.
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getTrackLock_Components()
	 * @model containment="true" required="true" upper="2"
	 * @generated
	 */
	EList<TrackLockComponent> getComponents();

	/**
	 * Returns the value of the '<em><b>Preferred Location</b></em>' attribute.
	 * The literals are from the enumeration
	 * {@link org.eclipse.set.model.siteplan.TrackLockLocation}. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Preferred Location</em>' attribute.
	 * @see org.eclipse.set.model.siteplan.TrackLockLocation
	 * @see #setPreferredLocation(TrackLockLocation)
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getTrackLock_PreferredLocation()
	 * @model
	 * @generated
	 */
	TrackLockLocation getPreferredLocation();

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.siteplan.TrackLock#getPreferredLocation <em>Preferred Location</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @param value the new value of the '<em>Preferred Location</em>' attribute.
	 * @see org.eclipse.set.model.siteplan.TrackLockLocation
	 * @see #getPreferredLocation()
	 * @generated
	 */
	void setPreferredLocation(TrackLockLocation value);

	/**
	 * Returns the value of the '<em><b>Operating Mode</b></em>' attribute. The
	 * literals are from the enumeration
	 * {@link org.eclipse.set.model.siteplan.TurnoutOperatingMode}. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Operating Mode</em>' attribute.
	 * @see org.eclipse.set.model.siteplan.TurnoutOperatingMode
	 * @see #setOperatingMode(TurnoutOperatingMode)
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getTrackLock_OperatingMode()
	 * @model
	 * @generated
	 */
	TurnoutOperatingMode getOperatingMode();

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.siteplan.TrackLock#getOperatingMode <em>Operating Mode</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @param value the new value of the '<em>Operating Mode</em>' attribute.
	 * @see org.eclipse.set.model.siteplan.TurnoutOperatingMode
	 * @see #getOperatingMode()
	 * @generated
	 */
	void setOperatingMode(TurnoutOperatingMode value);

	/**
	 * Returns the value of the '<em><b>Label</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the value of the '<em>Label</em>' containment reference.
	 * @see #setLabel(Label)
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getTrackLock_Label()
	 * @model containment="true"
	 * @generated
	 */
	Label getLabel();

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.siteplan.TrackLock#getLabel <em>Label</em>}' containment reference.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @param value the new value of the '<em>Label</em>' containment reference.
	 * @see #getLabel()
	 * @generated
	 */
	void setLabel(Label value);

} // TrackLock
