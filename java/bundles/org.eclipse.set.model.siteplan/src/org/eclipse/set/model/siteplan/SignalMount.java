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
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Signal
 * Mount</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.set.model.siteplan.SignalMount#getAttachedSignals <em>Attached Signals</em>}</li>
 *   <li>{@link org.eclipse.set.model.siteplan.SignalMount#getMountType <em>Mount Type</em>}</li>
 * </ul>
 *
 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getSignalMount()
 * @model
 * @generated
 */
public interface SignalMount extends PositionedObject, RouteObject {
	/**
	 * Returns the value of the '<em><b>Attached Signals</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.set.model.siteplan.Signal}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attached Signals</em>' containment reference list.
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getSignalMount_AttachedSignals()
	 * @model containment="true"
	 * @generated
	 */
	EList<Signal> getAttachedSignals();

	/**
	 * Returns the value of the '<em><b>Mount Type</b></em>' attribute. The
	 * literals are from the enumeration
	 * {@link org.eclipse.set.model.siteplan.SignalMountType}. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Mount Type</em>' attribute.
	 * @see org.eclipse.set.model.siteplan.SignalMountType
	 * @see #setMountType(SignalMountType)
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getSignalMount_MountType()
	 * @model
	 * @generated
	 */
	SignalMountType getMountType();

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.siteplan.SignalMount#getMountType <em>Mount Type</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Mount Type</em>' attribute.
	 * @see org.eclipse.set.model.siteplan.SignalMountType
	 * @see #getMountType()
	 * @generated
	 */
	void setMountType(SignalMountType value);

} // SignalMount
