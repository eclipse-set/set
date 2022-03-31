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
 * A representation of the model object '<em><b>Table Row</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Representation of one row in the table.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.set.model.tablemodel.TableRow#getCells <em>Cells</em>}</li>
 *   <li>{@link org.eclipse.set.model.tablemodel.TableRow#getFootnotes <em>Footnotes</em>}</li>
 * </ul>
 *
 * @see org.eclipse.set.model.tablemodel.TablemodelPackage#getTableRow()
 * @model
 * @generated
 */
public interface TableRow extends EObject {
	/**
	 * Returns the value of the '<em><b>Cells</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.set.model.tablemodel.TableCell}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * List of cells of the row.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Cells</em>' containment reference list.
	 * @see org.eclipse.set.model.tablemodel.TablemodelPackage#getTableRow_Cells()
	 * @model containment="true" required="true"
	 * @generated
	 */
	EList<TableCell> getCells();

	/**
	 * Returns the value of the '<em><b>Footnotes</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.set.model.tablemodel.Footnote}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * List of footnotes for the row.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Footnotes</em>' containment reference list.
	 * @see org.eclipse.set.model.tablemodel.TablemodelPackage#getTableRow_Footnotes()
	 * @model containment="true"
	 * @generated
	 */
	EList<Footnote> getFootnotes();

} // TableRow
