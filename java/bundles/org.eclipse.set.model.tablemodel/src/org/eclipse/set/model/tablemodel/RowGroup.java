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
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Row
 * Group</b></em>'. <!-- end-user-doc -->
 *
 * <!-- begin-model-doc --> Leading object of a group of table rows. <!--
 * end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.set.model.tablemodel.RowGroup#getRows
 * <em>Rows</em>}</li>
 * <li>{@link org.eclipse.set.model.tablemodel.RowGroup#getLeadingObject
 * <em>Leading Object</em>}</li>
 * <li>{@link org.eclipse.set.model.tablemodel.RowGroup#getLeadingObjectIndex
 * <em>Leading Object Index</em>}</li>
 * </ul>
 *
 * @see org.eclipse.set.model.tablemodel.TablemodelPackage#getRowGroup()
 * @model
 * @generated
 */
public interface RowGroup extends EObject {
	/**
	 * Returns the value of the '<em><b>Rows</b></em>' containment reference
	 * list. The list contents are of type
	 * {@link org.eclipse.set.model.tablemodel.TableRow}. <!-- begin-user-doc
	 * --> <!-- end-user-doc --> <!-- begin-model-doc --> List of table rows
	 * belonging to the leading object. <!-- end-model-doc -->
	 * 
	 * @return the value of the '<em>Rows</em>' containment reference list.
	 * @see org.eclipse.set.model.tablemodel.TablemodelPackage#getRowGroup_Rows()
	 * @model containment="true"
	 * @generated
	 */
	EList<TableRow> getRows();

	/**
	 * Returns the value of the '<em><b>Leading Object</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc --> The
	 * guid of the leading object. <!-- end-model-doc -->
	 * 
	 * @return the value of the '<em>Leading Object</em>' reference.
	 * @see #setLeadingObject(Ur_Objekt)
	 * @see org.eclipse.set.model.tablemodel.TablemodelPackage#getRowGroup_LeadingObject()
	 * @model
	 * @generated
	 */
	Ur_Objekt getLeadingObject();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.set.model.tablemodel.RowGroup#getLeadingObject
	 * <em>Leading Object</em>}' reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Leading Object</em>' reference.
	 * @see #getLeadingObject()
	 * @generated
	 */
	void setLeadingObject(Ur_Objekt value);

	/**
	 * Returns the value of the '<em><b>Leading Object Index</b></em>'
	 * attribute. The default value is <code>"0"</code>. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Leading Object Index</em>' attribute.
	 * @see #setLeadingObjectIndex(int)
	 * @see org.eclipse.set.model.tablemodel.TablemodelPackage#getRowGroup_LeadingObjectIndex()
	 * @model default="0" required="true"
	 * @generated
	 */
	int getLeadingObjectIndex();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.set.model.tablemodel.RowGroup#getLeadingObjectIndex
	 * <em>Leading Object Index</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Leading Object Index</em>'
	 *            attribute.
	 * @see #getLeadingObjectIndex()
	 * @generated
	 */
	void setLeadingObjectIndex(int value);

} // RowGroup
