/**
 * Copyright (c) 2018 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.validationreport;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Version
 * Info</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.set.model.validationreport.VersionInfo#getPlanProVersions
 * <em>Plan Pro Versions</em>}</li>
 * <li>{@link org.eclipse.set.model.validationreport.VersionInfo#getSignalbegriffeVersions
 * <em>Signalbegriffe Versions</em>}</li>
 * </ul>
 *
 * @see org.eclipse.set.model.validationreport.ValidationreportPackage#getVersionInfo()
 * @model
 * @generated
 */
public interface VersionInfo extends EObject {
	/**
	 * Returns the value of the '<em><b>Plan Pro Versions</b></em>' attribute
	 * list. The list contents are of type {@link java.lang.String}. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Plan Pro Versions</em>' attribute list.
	 * @see org.eclipse.set.model.validationreport.ValidationreportPackage#getVersionInfo_PlanProVersions()
	 * @model
	 * @generated
	 */
	EList<String> getPlanProVersions();

	/**
	 * Returns the value of the '<em><b>Signalbegriffe Versions</b></em>'
	 * attribute list. The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Signalbegriffe Versions</em>' attribute
	 *         list.
	 * @see org.eclipse.set.model.validationreport.ValidationreportPackage#getVersionInfo_SignalbegriffeVersions()
	 * @model
	 * @generated
	 */
	EList<String> getSignalbegriffeVersions();

} // VersionInfo
