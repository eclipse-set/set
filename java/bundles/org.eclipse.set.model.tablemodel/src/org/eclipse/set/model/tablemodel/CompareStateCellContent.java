/**
 * Copyright (c) 2022 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.tablemodel;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Compare
 * State Cell Content</b></em>'. <!-- end-user-doc -->
 *
 * <!-- begin-model-doc --> Result of a compare operation between two cells.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.set.model.tablemodel.CompareStateCellContent#getOldValue
 * <em>Old Value</em>}</li>
 * <li>{@link org.eclipse.set.model.tablemodel.CompareStateCellContent#getNewValue
 * <em>New Value</em>}</li>
 * </ul>
 *
 * @see org.eclipse.set.model.tablemodel.TablemodelPackage#getCompareStateCellContent()
 * @model
 * @generated
 */
public interface CompareStateCellContent extends CellContent {
	/**
	 * Returns the value of the '<em><b>Old Value</b></em>' containment
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Old Value</em>' containment reference.
	 * @see #setOldValue(CellContent)
	 * @see org.eclipse.set.model.tablemodel.TablemodelPackage#getCompareStateCellContent_OldValue()
	 * @model containment="true"
	 * @generated
	 */
	CellContent getOldValue();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.set.model.tablemodel.CompareStateCellContent#getOldValue
	 * <em>Old Value</em>}' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Old Value</em>' containment
	 *            reference.
	 * @see #getOldValue()
	 * @generated
	 */
	void setOldValue(CellContent value);

	/**
	 * Returns the value of the '<em><b>New Value</b></em>' containment
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>New Value</em>' containment reference.
	 * @see #setNewValue(CellContent)
	 * @see org.eclipse.set.model.tablemodel.TablemodelPackage#getCompareStateCellContent_NewValue()
	 * @model containment="true"
	 * @generated
	 */
	CellContent getNewValue();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.set.model.tablemodel.CompareStateCellContent#getNewValue
	 * <em>New Value</em>}' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>New Value</em>' containment
	 *            reference.
	 * @see #getNewValue()
	 * @generated
	 */
	void setNewValue(CellContent value);

} // CompareStateCellContent
