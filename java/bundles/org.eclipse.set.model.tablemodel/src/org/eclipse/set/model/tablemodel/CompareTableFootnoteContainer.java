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
 * Table Footnote Container</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.set.model.tablemodel.CompareTableFootnoteContainer#getMainPlanFootnoteContainer
 * <em>Main Plan Footnote Container</em>}</li>
 * <li>{@link org.eclipse.set.model.tablemodel.CompareTableFootnoteContainer#getComparePlanFootnoteContainer
 * <em>Compare Plan Footnote Container</em>}</li>
 * </ul>
 *
 * @see org.eclipse.set.model.tablemodel.TablemodelPackage#getCompareTableFootnoteContainer()
 * @model
 * @generated
 */
public interface CompareTableFootnoteContainer extends FootnoteContainer {
	/**
	 * Returns the value of the '<em><b>Main Plan Footnote Container</b></em>'
	 * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Main Plan Footnote Container</em>'
	 *         containment reference.
	 * @see #setMainPlanFootnoteContainer(FootnoteContainer)
	 * @see org.eclipse.set.model.tablemodel.TablemodelPackage#getCompareTableFootnoteContainer_MainPlanFootnoteContainer()
	 * @model containment="true"
	 * @generated
	 */
	FootnoteContainer getMainPlanFootnoteContainer();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.set.model.tablemodel.CompareTableFootnoteContainer#getMainPlanFootnoteContainer
	 * <em>Main Plan Footnote Container</em>}' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Main Plan Footnote Container</em>'
	 *            containment reference.
	 * @see #getMainPlanFootnoteContainer()
	 * @generated
	 */
	void setMainPlanFootnoteContainer(FootnoteContainer value);

	/**
	 * Returns the value of the '<em><b>Compare Plan Footnote
	 * Container</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return the value of the '<em>Compare Plan Footnote Container</em>'
	 *         reference.
	 * @see #setComparePlanFootnoteContainer(FootnoteContainer)
	 * @see org.eclipse.set.model.tablemodel.TablemodelPackage#getCompareTableFootnoteContainer_ComparePlanFootnoteContainer()
	 * @model
	 * @generated
	 */
	FootnoteContainer getComparePlanFootnoteContainer();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.set.model.tablemodel.CompareTableFootnoteContainer#getComparePlanFootnoteContainer
	 * <em>Compare Plan Footnote Container</em>}' reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Compare Plan Footnote
	 *            Container</em>' reference.
	 * @see #getComparePlanFootnoteContainer()
	 * @generated
	 */
	void setComparePlanFootnoteContainer(FootnoteContainer value);

} // CompareTableFootnoteContainer
