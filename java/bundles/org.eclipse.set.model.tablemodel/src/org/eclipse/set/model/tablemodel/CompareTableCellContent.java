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
 * <li>{@link org.eclipse.set.model.tablemodel.CompareTableCellContent#getMainPlanCellContent
 * <em>Main Plan Cell Content</em>}</li>
 * <li>{@link org.eclipse.set.model.tablemodel.CompareTableCellContent#getComparePlanCellContent
 * <em>Compare Plan Cell Content</em>}</li>
 * </ul>
 *
 * @see org.eclipse.set.model.tablemodel.TablemodelPackage#getCompareTableCellContent()
 * @model
 * @generated
 */
public interface CompareTableCellContent extends CellContent {
	/**
	 * Returns the value of the '<em><b>Main Plan Cell Content</b></em>'
	 * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Main Plan Cell Content</em>' containment
	 *         reference.
	 * @see #setMainPlanCellContent(CellContent)
	 * @see org.eclipse.set.model.tablemodel.TablemodelPackage#getCompareTableCellContent_MainPlanCellContent()
	 * @model containment="true"
	 * @generated
	 */
	CellContent getMainPlanCellContent();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.set.model.tablemodel.CompareTableCellContent#getMainPlanCellContent
	 * <em>Main Plan Cell Content</em>}' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Main Plan Cell Content</em>'
	 *            containment reference.
	 * @see #getMainPlanCellContent()
	 * @generated
	 */
	void setMainPlanCellContent(CellContent value);

	/**
	 * Returns the value of the '<em><b>Compare Plan Cell Content</b></em>'
	 * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Compare Plan Cell Content</em>' containment
	 *         reference.
	 * @see #setComparePlanCellContent(CellContent)
	 * @see org.eclipse.set.model.tablemodel.TablemodelPackage#getCompareTableCellContent_ComparePlanCellContent()
	 * @model containment="true"
	 * @generated
	 */
	CellContent getComparePlanCellContent();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.set.model.tablemodel.CompareTableCellContent#getComparePlanCellContent
	 * <em>Compare Plan Cell Content</em>}' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Compare Plan Cell Content</em>'
	 *            containment reference.
	 * @see #getComparePlanCellContent()
	 * @generated
	 */
	void setComparePlanCellContent(CellContent value);

} // CompareTableCellContent
