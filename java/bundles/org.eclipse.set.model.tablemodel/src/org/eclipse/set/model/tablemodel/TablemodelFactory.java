/**
 * Copyright (c) 2022 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.tablemodel;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc --> The <b>Factory</b> for the model. It provides a
 * create method for each non-abstract class of the model. <!-- end-user-doc -->
 * 
 * @see org.eclipse.set.model.tablemodel.TablemodelPackage
 * @generated
 */
public interface TablemodelFactory extends EFactory {
	/**
	 * The singleton instance of the factory. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	TablemodelFactory eINSTANCE = org.eclipse.set.model.tablemodel.impl.TablemodelFactoryImpl
			.init();

	/**
	 * Returns a new object of class '<em>Table</em>'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Table</em>'.
	 * @generated
	 */
	Table createTable();

	/**
	 * Returns a new object of class '<em>Column Descriptor</em>'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Column Descriptor</em>'.
	 * @generated
	 */
	ColumnDescriptor createColumnDescriptor();

	/**
	 * Returns a new object of class '<em>Table Content</em>'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Table Content</em>'.
	 * @generated
	 */
	TableContent createTableContent();

	/**
	 * Returns a new object of class '<em>Row Group</em>'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Row Group</em>'.
	 * @generated
	 */
	RowGroup createRowGroup();

	/**
	 * Returns a new object of class '<em>Table Row</em>'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Table Row</em>'.
	 * @generated
	 */
	TableRow createTableRow();

	/**
	 * Returns a new object of class '<em>Table Cell</em>'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Table Cell</em>'.
	 * @generated
	 */
	TableCell createTableCell();

	/**
	 * Returns a new object of class '<em>String Cell Content</em>'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>String Cell Content</em>'.
	 * @generated
	 */
	StringCellContent createStringCellContent();

	/**
	 * Returns a new object of class '<em>Compare State Cell Content</em>'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Compare State Cell Content</em>'.
	 * @generated
	 */
	CompareStateCellContent createCompareStateCellContent();

	/**
	 * Returns a new object of class '<em>Cell Annotation</em>'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Cell Annotation</em>'.
	 * @generated
	 */
	CellAnnotation createCellAnnotation();

	/**
	 * Returns a new object of class '<em>Multi Color Cell Content</em>'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Multi Color Cell Content</em>'.
	 * @generated
	 */
	MultiColorCellContent createMultiColorCellContent();

	/**
	 * Returns a new object of class '<em>Multi Color Content</em>'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Multi Color Content</em>'.
	 * @generated
	 */
	MultiColorContent createMultiColorContent();

	/**
	 * Returns a new object of class '<em>Compare Footnote Container</em>'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Compare Footnote Container</em>'.
	 * @generated
	 */
	CompareFootnoteContainer createCompareFootnoteContainer();

	/**
	 * Returns a new object of class '<em>Simple Footnote Container</em>'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Simple Footnote Container</em>'.
	 * @generated
	 */
	SimpleFootnoteContainer createSimpleFootnoteContainer();

	/**
	 * Returns a new object of class '<em>Compare Table Cell Content</em>'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Compare Table Cell Content</em>'.
	 * @generated
	 */
	CompareTableCellContent createCompareTableCellContent();

	/**
	 * Returns a new object of class '<em>Compare Table Footnote
	 * Container</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Compare Table Footnote
	 *         Container</em>'.
	 * @generated
	 */
	CompareTableFootnoteContainer createCompareTableFootnoteContainer();

	/**
	 * Returns a new object of class '<em>Plan Compare Row</em>'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Plan Compare Row</em>'.
	 * @generated
	 */
	PlanCompareRow createPlanCompareRow();

	/**
	 * Returns the package supported by this factory. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the package supported by this factory.
	 * @generated
	 */
	TablemodelPackage getTablemodelPackage();

} // TablemodelFactory
