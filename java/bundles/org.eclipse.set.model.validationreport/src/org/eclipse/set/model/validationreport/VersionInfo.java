/**
 * Copyright (c) 2018 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.validationreport;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Version
 * Info</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.set.model.validationreport.VersionInfo#getPlanPro
 * <em>Plan Pro</em>}</li>
 * <li>{@link org.eclipse.set.model.validationreport.VersionInfo#getSignals
 * <em>Signals</em>}</li>
 * </ul>
 *
 * @see org.eclipse.set.model.validationreport.ValidationreportPackage#getVersionInfo()
 * @model
 * @generated
 */
public interface VersionInfo extends EObject {
	/**
	 * Returns the value of the '<em><b>Plan Pro</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Plan Pro</em>' attribute isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Plan Pro</em>' attribute.
	 * @see #setPlanPro(String)
	 * @see org.eclipse.set.model.validationreport.ValidationreportPackage#getVersionInfo_PlanPro()
	 * @model
	 * @generated
	 */
	String getPlanPro();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.set.model.validationreport.VersionInfo#getPlanPro
	 * <em>Plan Pro</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @param value
	 *            the new value of the '<em>Plan Pro</em>' attribute.
	 * @see #getPlanPro()
	 * @generated
	 */
	void setPlanPro(String value);

	/**
	 * Returns the value of the '<em><b>Signals</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Signals</em>' attribute isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Signals</em>' attribute.
	 * @see #setSignals(String)
	 * @see org.eclipse.set.model.validationreport.ValidationreportPackage#getVersionInfo_Signals()
	 * @model
	 * @generated
	 */
	String getSignals();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.set.model.validationreport.VersionInfo#getSignals
	 * <em>Signals</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @param value
	 *            the new value of the '<em>Signals</em>' attribute.
	 * @see #getSignals()
	 * @generated
	 */
	void setSignals(String value);

} // VersionInfo
