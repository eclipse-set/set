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
import org.eclipse.set.model.planpro.Basisobjekte.Ur_Objekt;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Table
 * Row</b></em>'. <!-- end-user-doc -->
 *
 * <!-- begin-model-doc --> Representation of one row in the table. <!--
 * end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.set.model.tablemodel.TableRow#getCells
 * <em>Cells</em>}</li>
 * <li>{@link org.eclipse.set.model.tablemodel.TableRow#getRowIndex <em>Row
 * Index</em>}</li>
 * <li>{@link org.eclipse.set.model.tablemodel.TableRow#getFootnotes
 * <em>Footnotes</em>}</li>
 * <li>{@link org.eclipse.set.model.tablemodel.TableRow#getRowObject <em>Row
 * Object</em>}</li>
 * </ul>
 *
 * @see org.eclipse.set.model.tablemodel.TablemodelPackage#getTableRow()
 * @model
 * @generated
 */
public interface TableRow extends EObject {
	/**
	 * Returns the value of the '<em><b>Cells</b></em>' containment reference
	 * list. The list contents are of type
	 * {@link org.eclipse.set.model.tablemodel.TableCell}. <!-- begin-user-doc
	 * --> <!-- end-user-doc --> <!-- begin-model-doc --> List of cells of the
	 * row. <!-- end-model-doc -->
	 * 
	 * @return the value of the '<em>Cells</em>' containment reference list.
	 * @see org.eclipse.set.model.tablemodel.TablemodelPackage#getTableRow_Cells()
	 * @model containment="true" required="true"
	 * @generated
	 */
	EList<TableCell> getCells();

	/**
	 * Returns the value of the '<em><b>Footnotes</b></em>' containment
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Footnotes</em>' containment reference.
	 * @see #setFootnotes(FootnoteContainer)
	 * @see org.eclipse.set.model.tablemodel.TablemodelPackage#getTableRow_Footnotes()
	 * @model containment="true"
	 * @generated
	 */
	FootnoteContainer getFootnotes();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.set.model.tablemodel.TableRow#getFootnotes
	 * <em>Footnotes</em>}' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Footnotes</em>' containment
	 *            reference.
	 * @see #getFootnotes()
	 * @generated
	 */
	void setFootnotes(FootnoteContainer value);

	/**
	 * Returns the value of the '<em><b>Row Object</b></em>' containment
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Row Object</em>' containment reference.
	 * @see #setRowObject(Ur_Objekt)
	 * @see org.eclipse.set.model.tablemodel.TablemodelPackage#getTableRow_RowObject()
	 * @model containment="true"
	 * @generated
	 */
	Ur_Objekt getRowObject();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.set.model.tablemodel.TableRow#getRowObject <em>Row
	 * Object</em>}' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Row Object</em>' containment
	 *            reference.
	 * @see #getRowObject()
	 * @generated
	 */
	void setRowObject(Ur_Objekt value);

	/**
	 * Returns the value of the '<em><b>Row Index</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Row Index</em>' attribute.
	 * @see #setRowIndex(int)
	 * @see org.eclipse.set.model.tablemodel.TablemodelPackage#getTableRow_RowIndex()
	 * @model
	 * @generated
	 */
	int getRowIndex();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.set.model.tablemodel.TableRow#getRowIndex <em>Row
	 * Index</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Row Index</em>' attribute.
	 * @see #getRowIndex()
	 * @generated
	 */
	void setRowIndex(int value);

} // TableRow
