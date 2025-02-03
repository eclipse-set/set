/**
 * Copyright (c) 2022 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.tablemodel;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Table Content</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * The dynamic content of the table.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.set.model.tablemodel.TableContent#getRowgroups <em>Rowgroups</em>}</li>
 * </ul>
 *
 * @see org.eclipse.set.model.tablemodel.TablemodelPackage#getTableContent()
 * @model
 * @generated
 */
public interface TableContent extends EObject {
	/**
	 * Returns the value of the '<em><b>Rowgroups</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.set.model.tablemodel.RowGroup}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * List of row groups.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Rowgroups</em>' containment reference list.
	 * @see org.eclipse.set.model.tablemodel.TablemodelPackage#getTableContent_Rowgroups()
	 * @model containment="true"
	 * @generated
	 */
	EList<RowGroup> getRowgroups();

} // TableContent
