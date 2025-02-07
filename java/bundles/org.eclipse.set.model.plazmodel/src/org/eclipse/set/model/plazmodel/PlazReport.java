/**
 * Copyright (c) 2023 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.plazmodel;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.set.model.validationreport.ValidationProblem;

/**
 * <!-- begin-user-doc --> A representation of the model object
 * '<em><b>Report</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.set.model.plazmodel.PlazReport#getEntries
 * <em>Entries</em>}</li>
 * </ul>
 *
 * @see org.eclipse.set.model.plazmodel.PlazPackage#getPlazReport()
 * @model
 * @generated
 */
public interface PlazReport extends EObject {
	/**
	 * Returns the value of the '<em><b>Entries</b></em>' containment reference
	 * list. The list contents are of type
	 * {@link org.eclipse.set.model.validationreport.ValidationProblem}. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Entries</em>' containment reference list.
	 * @see org.eclipse.set.model.plazmodel.PlazPackage#getPlazReport_Entries()
	 * @model containment="true"
	 * @generated
	 */
	EList<ValidationProblem> getEntries();

} // PlazReport
