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
 * <!-- begin-user-doc --> A representation of the model object
 * '<em><b>Table</b></em>'. <!-- end-user-doc -->
 *
 * <!-- begin-model-doc --> The table model. <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.set.model.tablemodel.Table#getColumndescriptors
 * <em>Columndescriptors</em>}</li>
 * <li>{@link org.eclipse.set.model.tablemodel.Table#getTablecontent
 * <em>Tablecontent</em>}</li>
 * </ul>
 *
 * @see org.eclipse.set.model.tablemodel.TablemodelPackage#getTable()
 * @model
 * @generated
 */
public interface Table extends EObject {
	/**
	 * Returns the value of the '<em><b>Columndescriptors</b></em>' containment
	 * reference list. The list contents are of type
	 * {@link org.eclipse.set.model.tablemodel.ColumnDescriptor}. <!--
	 * begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc --> The
	 * description of the column headers. <!-- end-model-doc -->
	 * 
	 * @return the value of the '<em>Columndescriptors</em>' containment
	 *         reference list.
	 * @see org.eclipse.set.model.tablemodel.TablemodelPackage#getTable_Columndescriptors()
	 * @model containment="true" required="true"
	 * @generated
	 */
	EList<ColumnDescriptor> getColumndescriptors();

	/**
	 * Returns the value of the '<em><b>Tablecontent</b></em>' containment
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc --> <!--
	 * begin-model-doc --> The dynamic content of the table. <!-- end-model-doc
	 * -->
	 * 
	 * @return the value of the '<em>Tablecontent</em>' containment reference.
	 * @see #setTablecontent(TableContent)
	 * @see org.eclipse.set.model.tablemodel.TablemodelPackage#getTable_Tablecontent()
	 * @model containment="true" required="true"
	 * @generated
	 */
	TableContent getTablecontent();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.set.model.tablemodel.Table#getTablecontent
	 * <em>Tablecontent</em>}' containment reference. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Tablecontent</em>' containment
	 *            reference.
	 * @see #getTablecontent()
	 * @generated
	 */
	void setTablecontent(TableContent value);

} // Table
