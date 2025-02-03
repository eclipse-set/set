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
 * A representation of the model object '<em><b>Table Cell</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Representation of a cell in the table.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.set.model.tablemodel.TableCell#getContent <em>Content</em>}</li>
 *   <li>{@link org.eclipse.set.model.tablemodel.TableCell#getColumndescriptor <em>Columndescriptor</em>}</li>
 *   <li>{@link org.eclipse.set.model.tablemodel.TableCell#getCellannotation <em>Cellannotation</em>}</li>
 * </ul>
 *
 * @see org.eclipse.set.model.tablemodel.TablemodelPackage#getTableCell()
 * @model
 * @generated
 */
public interface TableCell extends EObject {
	/**
	 * Returns the value of the '<em><b>Content</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The content of the cell
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Content</em>' containment reference.
	 * @see #setContent(CellContent)
	 * @see org.eclipse.set.model.tablemodel.TablemodelPackage#getTableCell_Content()
	 * @model containment="true"
	 * @generated
	 */
	CellContent getContent();

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.tablemodel.TableCell#getContent <em>Content</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Content</em>' containment reference.
	 * @see #getContent()
	 * @generated
	 */
	void setContent(CellContent value);

	/**
	 * Returns the value of the '<em><b>Columndescriptor</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The column header information.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Columndescriptor</em>' reference.
	 * @see #setColumndescriptor(ColumnDescriptor)
	 * @see org.eclipse.set.model.tablemodel.TablemodelPackage#getTableCell_Columndescriptor()
	 * @model required="true"
	 * @generated
	 */
	ColumnDescriptor getColumndescriptor();

	/**
	 * Sets the value of the '{@link org.eclipse.set.model.tablemodel.TableCell#getColumndescriptor <em>Columndescriptor</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Columndescriptor</em>' reference.
	 * @see #getColumndescriptor()
	 * @generated
	 */
	void setColumndescriptor(ColumnDescriptor value);

	/**
	 * Returns the value of the '<em><b>Cellannotation</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.set.model.tablemodel.CellAnnotation}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cellannotation</em>' containment reference list.
	 * @see org.eclipse.set.model.tablemodel.TablemodelPackage#getTableCell_Cellannotation()
	 * @model containment="true"
	 * @generated
	 */
	EList<CellAnnotation> getCellannotation();

} // TableCell
