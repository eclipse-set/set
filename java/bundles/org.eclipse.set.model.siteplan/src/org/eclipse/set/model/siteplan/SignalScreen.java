/**
 * Copyright (c) 2021 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.siteplan;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Signal
 * Screen</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.set.model.siteplan.SignalScreen#getScreen <em>Screen</em>}</li>
 *   <li>{@link org.eclipse.set.model.siteplan.SignalScreen#isSwitched <em>Switched</em>}</li>
 *   <li>{@link org.eclipse.set.model.siteplan.SignalScreen#getFrameType <em>Frame Type</em>}</li>
 * </ul>
 *
 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getSignalScreen()
 * @model
 * @generated
 */
public interface SignalScreen extends EObject {
	/**
	 * Returns the value of the '<em><b>Screen</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Screen</em>' attribute.
	 * @see #setScreen(String)
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getSignalScreen_Screen()
	 * @model
	 * @generated
	 */
	String getScreen();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.set.model.siteplan.SignalScreen#getScreen
	 * <em>Screen</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @param value
	 *            the new value of the '<em>Screen</em>' attribute.
	 * @see #getScreen()
	 * @generated
	 */
	void setScreen(String value);

	/**
	 * Returns the value of the '<em><b>Switched</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Switched</em>' attribute.
	 * @see #setSwitched(boolean)
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getSignalScreen_Switched()
	 * @model
	 * @generated
	 */
	boolean isSwitched();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.set.model.siteplan.SignalScreen#isSwitched
	 * <em>Switched</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @param value
	 *            the new value of the '<em>Switched</em>' attribute.
	 * @see #isSwitched()
	 * @generated
	 */
	void setSwitched(boolean value);

	/**
	 * Returns the value of the '<em><b>Frame Type</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Frame Type</em>' attribute.
	 * @see #setFrameType(String)
	 * @see org.eclipse.set.model.siteplan.SiteplanPackage#getSignalScreen_FrameType()
	 * @model
	 * @generated
	 */
	String getFrameType();

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.siteplan.SignalScreen#getFrameType <em>Frame Type</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @param value the new value of the '<em>Frame Type</em>' attribute.
	 * @see #getFrameType()
	 * @generated
	 */
	void setFrameType(String value);

} // SignalScreen
