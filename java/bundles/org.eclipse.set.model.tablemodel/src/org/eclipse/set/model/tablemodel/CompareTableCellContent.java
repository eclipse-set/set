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
 * Table Cell Content</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.set.model.tablemodel.CompareTableCellContent#getFirstPlanCellContent
 * <em>First Plan Cell Content</em>}</li>
 * <li>{@link org.eclipse.set.model.tablemodel.CompareTableCellContent#getSecondPlanCellContent
 * <em>Second Plan Cell Content</em>}</li>
 * </ul>
 *
 * @see org.eclipse.set.model.tablemodel.TablemodelPackage#getCompareTableCellContent()
 * @model
 * @generated
 */
public interface CompareTableCellContent extends CellContent {
	/**
	 * Returns the value of the '<em><b>First Plan Cell Content</b></em>'
	 * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>First Plan Cell Content</em>' containment
	 *         reference.
	 * @see #setFirstPlanCellContent(CellContent)
	 * @see org.eclipse.set.model.tablemodel.TablemodelPackage#getCompareTableCellContent_FirstPlanCellContent()
	 * @model containment="true"
	 * @generated
	 */
	CellContent getFirstPlanCellContent();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.set.model.tablemodel.CompareTableCellContent#getFirstPlanCellContent
	 * <em>First Plan Cell Content</em>}' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>First Plan Cell Content</em>'
	 *            containment reference.
	 * @see #getFirstPlanCellContent()
	 * @generated
	 */
	void setFirstPlanCellContent(CellContent value);

	/**
	 * Returns the value of the '<em><b>Second Plan Cell Content</b></em>'
	 * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Second Plan Cell Content</em>' containment
	 *         reference.
	 * @see #setSecondPlanCellContent(CellContent)
	 * @see org.eclipse.set.model.tablemodel.TablemodelPackage#getCompareTableCellContent_SecondPlanCellContent()
	 * @model containment="true"
	 * @generated
	 */
	CellContent getSecondPlanCellContent();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.set.model.tablemodel.CompareTableCellContent#getSecondPlanCellContent
	 * <em>Second Plan Cell Content</em>}' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Second Plan Cell Content</em>'
	 *            containment reference.
	 * @see #getSecondPlanCellContent()
	 * @generated
	 */
	void setSecondPlanCellContent(CellContent value);

} // CompareTableCellContent
