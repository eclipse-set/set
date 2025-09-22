/**
 * Copyright (c) 2022 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.tablemodel.format;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.set.model.tablemodel.TablemodelPackage;

/**
 * <!-- begin-user-doc --> The <b>Package</b> for the model. It contains
 * accessors for the meta objects to represent
 * <ul>
 * <li>each class,</li>
 * <li>each feature of each class,</li>
 * <li>each operation of each class,</li>
 * <li>each enum,</li>
 * <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * 
 * @see org.eclipse.set.model.tablemodel.format.TableformatFactory
 * @model kind="package"
 * @generated
 */
public interface TableformatPackage extends EPackage {
	/**
	 * The package name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNAME = "format";

	/**
	 * The package namespace URI. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_URI = "tag:scheidt-bachmann-st.de,2017-10-18:planpro/tf";

	/**
	 * The package namespace name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_PREFIX = "tf";

	/**
	 * The singleton instance of the package. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	TableformatPackage eINSTANCE = org.eclipse.set.model.tablemodel.format.impl.TableformatPackageImpl
			.init();

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.set.model.tablemodel.format.impl.CellFormatImpl
	 * <em>Cell Format</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see org.eclipse.set.model.tablemodel.format.impl.CellFormatImpl
	 * @see org.eclipse.set.model.tablemodel.format.impl.TableformatPackageImpl#getCellFormat()
	 * @generated
	 */
	int CELL_FORMAT = 0;

	/**
	 * The feature id for the '<em><b>Text Alignment</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CELL_FORMAT__TEXT_ALIGNMENT = TablemodelPackage.CELL_ANNOTATION_FEATURE_COUNT
			+ 0;

	/**
	 * The feature id for the '<em><b>Topological Calculation</b></em>'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CELL_FORMAT__TOPOLOGICAL_CALCULATION = TablemodelPackage.CELL_ANNOTATION_FEATURE_COUNT
			+ 1;

	/**
	 * The number of structural features of the '<em>Cell Format</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CELL_FORMAT_FEATURE_COUNT = TablemodelPackage.CELL_ANNOTATION_FEATURE_COUNT
			+ 2;

	/**
	 * The number of operations of the '<em>Cell Format</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CELL_FORMAT_OPERATION_COUNT = TablemodelPackage.CELL_ANNOTATION_OPERATION_COUNT
			+ 0;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.set.model.tablemodel.format.TextAlignment <em>Text
	 * Alignment</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.set.model.tablemodel.format.TextAlignment
	 * @see org.eclipse.set.model.tablemodel.format.impl.TableformatPackageImpl#getTextAlignment()
	 * @generated
	 */
	int TEXT_ALIGNMENT = 1;

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.set.model.tablemodel.format.CellFormat <em>Cell
	 * Format</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Cell Format</em>'.
	 * @see org.eclipse.set.model.tablemodel.format.CellFormat
	 * @generated
	 */
	EClass getCellFormat();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.set.model.tablemodel.format.CellFormat#getTextAlignment
	 * <em>Text Alignment</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Text Alignment</em>'.
	 * @see org.eclipse.set.model.tablemodel.format.CellFormat#getTextAlignment()
	 * @see #getCellFormat()
	 * @generated
	 */
	EAttribute getCellFormat_TextAlignment();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.set.model.tablemodel.format.CellFormat#isTopologicalCalculation
	 * <em>Topological Calculation</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Topological
	 *         Calculation</em>'.
	 * @see org.eclipse.set.model.tablemodel.format.CellFormat#isTopologicalCalculation()
	 * @see #getCellFormat()
	 * @generated
	 */
	EAttribute getCellFormat_TopologicalCalculation();

	/**
	 * Returns the meta object for enum
	 * '{@link org.eclipse.set.model.tablemodel.format.TextAlignment <em>Text
	 * Alignment</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for enum '<em>Text Alignment</em>'.
	 * @see org.eclipse.set.model.tablemodel.format.TextAlignment
	 * @generated
	 */
	EEnum getTextAlignment();

	/**
	 * Returns the factory that creates the instances of the model. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	TableformatFactory getTableformatFactory();

	/**
	 * <!-- begin-user-doc --> Defines literals for the meta objects that
	 * represent
	 * <ul>
	 * <li>each class,</li>
	 * <li>each feature of each class,</li>
	 * <li>each operation of each class,</li>
	 * <li>each enum,</li>
	 * <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.set.model.tablemodel.format.impl.CellFormatImpl
		 * <em>Cell Format</em>}' class. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @see org.eclipse.set.model.tablemodel.format.impl.CellFormatImpl
		 * @see org.eclipse.set.model.tablemodel.format.impl.TableformatPackageImpl#getCellFormat()
		 * @generated
		 */
		EClass CELL_FORMAT = eINSTANCE.getCellFormat();

		/**
		 * The meta object literal for the '<em><b>Text Alignment</b></em>'
		 * attribute feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute CELL_FORMAT__TEXT_ALIGNMENT = eINSTANCE
				.getCellFormat_TextAlignment();

		/**
		 * The meta object literal for the '<em><b>Topological
		 * Calculation</b></em>' attribute feature. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute CELL_FORMAT__TOPOLOGICAL_CALCULATION = eINSTANCE
				.getCellFormat_TopologicalCalculation();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.set.model.tablemodel.format.TextAlignment
		 * <em>Text Alignment</em>}' enum. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @see org.eclipse.set.model.tablemodel.format.TextAlignment
		 * @see org.eclipse.set.model.tablemodel.format.impl.TableformatPackageImpl#getTextAlignment()
		 * @generated
		 */
		EEnum TEXT_ALIGNMENT = eINSTANCE.getTextAlignment();

	}

} // TableformatPackage
