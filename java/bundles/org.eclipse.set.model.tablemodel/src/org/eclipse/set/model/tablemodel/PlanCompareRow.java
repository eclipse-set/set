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
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Plan
 * Compare Row</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.set.model.tablemodel.PlanCompareRow#getRowType <em>Row
 * Type</em>}</li>
 * </ul>
 *
 * @see org.eclipse.set.model.tablemodel.TablemodelPackage#getPlanCompareRow()
 * @model
 * @generated
 */
public interface PlanCompareRow extends TableRow {
	/**
	 * Returns the value of the '<em><b>Row Type</b></em>' attribute. The
	 * literals are from the enumeration
	 * {@link org.eclipse.set.model.tablemodel.PlanCompareRowType}. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Row Type</em>' attribute.
	 * @see org.eclipse.set.model.tablemodel.PlanCompareRowType
	 * @see #setRowType(PlanCompareRowType)
	 * @see org.eclipse.set.model.tablemodel.TablemodelPackage#getPlanCompareRow_RowType()
	 * @model required="true"
	 * @generated
	 */
	PlanCompareRowType getRowType();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.set.model.tablemodel.PlanCompareRow#getRowType
	 * <em>Row Type</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @param value
	 *            the new value of the '<em>Row Type</em>' attribute.
	 * @see org.eclipse.set.model.tablemodel.PlanCompareRowType
	 * @see #getRowType()
	 * @generated
	 */
	void setRowType(PlanCompareRowType value);

} // PlanCompareRow
