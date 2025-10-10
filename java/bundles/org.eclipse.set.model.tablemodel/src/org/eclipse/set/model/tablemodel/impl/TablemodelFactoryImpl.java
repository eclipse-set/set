/**
 * Copyright (c) 2022 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.tablemodel.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.eclipse.set.model.tablemodel.*;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!--
 * end-user-doc -->
 * 
 * @generated
 */
public class TablemodelFactoryImpl extends EFactoryImpl
		implements TablemodelFactory {
	/**
	 * Creates the default factory implementation. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	public static TablemodelFactory init() {
		try {
			TablemodelFactory theTablemodelFactory = (TablemodelFactory) EPackage.Registry.INSTANCE
					.getEFactory(TablemodelPackage.eNS_URI);
			if (theTablemodelFactory != null) {
				return theTablemodelFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new TablemodelFactoryImpl();
	}

	/**
	 * Creates an instance of the factory. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	public TablemodelFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case TablemodelPackage.TABLE:
				return createTable();
			case TablemodelPackage.COLUMN_DESCRIPTOR:
				return createColumnDescriptor();
			case TablemodelPackage.TABLE_CONTENT:
				return createTableContent();
			case TablemodelPackage.ROW_GROUP:
				return createRowGroup();
			case TablemodelPackage.TABLE_ROW:
				return createTableRow();
			case TablemodelPackage.TABLE_CELL:
				return createTableCell();
			case TablemodelPackage.STRING_CELL_CONTENT:
				return createStringCellContent();
			case TablemodelPackage.COMPARE_CELL_CONTENT:
				return createCompareCellContent();
			case TablemodelPackage.CELL_ANNOTATION:
				return createCellAnnotation();
			case TablemodelPackage.MULTI_COLOR_CELL_CONTENT:
				return createMultiColorCellContent();
			case TablemodelPackage.MULTI_COLOR_CONTENT:
				return createMultiColorContent();
			case TablemodelPackage.COMPARE_FOOTNOTE_CONTAINER:
				return createCompareFootnoteContainer();
			case TablemodelPackage.SIMPLE_FOOTNOTE_CONTAINER:
				return createSimpleFootnoteContainer();
			case TablemodelPackage.COMPARE_TABLE_CELL_CONTENT:
				return createCompareTableCellContent();
			case TablemodelPackage.COMPARE_TABLE_FOOTNOTE_CONTAINER:
				return createCompareTableFootnoteContainer();
			case TablemodelPackage.PLAN_COMPARE_ROW:
				return createPlanCompareRow();
			default:
				throw new IllegalArgumentException("The class '"
						+ eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case TablemodelPackage.COLUMN_WIDTH_MODE:
				return createColumnWidthModeFromString(eDataType, initialValue);
			case TablemodelPackage.ROW_MERGE_MODE:
				return createRowMergeModeFromString(eDataType, initialValue);
			case TablemodelPackage.PLAN_COMPARE_ROW_TYPE:
				return createPlanCompareRowTypeFromString(eDataType,
						initialValue);
			default:
				throw new IllegalArgumentException("The datatype '"
						+ eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case TablemodelPackage.COLUMN_WIDTH_MODE:
				return convertColumnWidthModeToString(eDataType, instanceValue);
			case TablemodelPackage.ROW_MERGE_MODE:
				return convertRowMergeModeToString(eDataType, instanceValue);
			case TablemodelPackage.PLAN_COMPARE_ROW_TYPE:
				return convertPlanCompareRowTypeToString(eDataType,
						instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '"
						+ eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Table createTable() {
		TableImpl table = new TableImpl();
		return table;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public ColumnDescriptor createColumnDescriptor() {
		ColumnDescriptorImpl columnDescriptor = new ColumnDescriptorImpl();
		return columnDescriptor;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public TableContent createTableContent() {
		TableContentImpl tableContent = new TableContentImpl();
		return tableContent;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public RowGroup createRowGroup() {
		RowGroupImpl rowGroup = new RowGroupImpl();
		return rowGroup;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public TableRow createTableRow() {
		TableRowImpl tableRow = new TableRowImpl();
		return tableRow;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public TableCell createTableCell() {
		TableCellImpl tableCell = new TableCellImpl();
		return tableCell;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public StringCellContent createStringCellContent() {
		StringCellContentImpl stringCellContent = new StringCellContentImpl();
		return stringCellContent;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public CompareCellContent createCompareCellContent() {
		CompareCellContentImpl compareCellContent = new CompareCellContentImpl();
		return compareCellContent;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public CellAnnotation createCellAnnotation() {
		CellAnnotationImpl cellAnnotation = new CellAnnotationImpl();
		return cellAnnotation;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public MultiColorCellContent createMultiColorCellContent() {
		MultiColorCellContentImpl multiColorCellContent = new MultiColorCellContentImpl();
		return multiColorCellContent;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public MultiColorContent createMultiColorContent() {
		MultiColorContentImpl multiColorContent = new MultiColorContentImpl();
		return multiColorContent;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public CompareFootnoteContainer createCompareFootnoteContainer() {
		CompareFootnoteContainerImpl compareFootnoteContainer = new CompareFootnoteContainerImpl();
		return compareFootnoteContainer;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public SimpleFootnoteContainer createSimpleFootnoteContainer() {
		SimpleFootnoteContainerImpl simpleFootnoteContainer = new SimpleFootnoteContainerImpl();
		return simpleFootnoteContainer;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public CompareTableCellContent createCompareTableCellContent() {
		CompareTableCellContentImpl compareTableCellContent = new CompareTableCellContentImpl();
		return compareTableCellContent;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public CompareTableFootnoteContainer createCompareTableFootnoteContainer() {
		CompareTableFootnoteContainerImpl compareTableFootnoteContainer = new CompareTableFootnoteContainerImpl();
		return compareTableFootnoteContainer;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public PlanCompareRow createPlanCompareRow() {
		PlanCompareRowImpl planCompareRow = new PlanCompareRowImpl();
		return planCompareRow;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ColumnWidthMode createColumnWidthModeFromString(EDataType eDataType,
			String initialValue) {
		ColumnWidthMode result = ColumnWidthMode.get(initialValue);
		if (result == null)
			throw new IllegalArgumentException("The value '" + initialValue
					+ "' is not a valid enumerator of '" + eDataType.getName()
					+ "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String convertColumnWidthModeToString(EDataType eDataType,
			Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public RowMergeMode createRowMergeModeFromString(EDataType eDataType,
			String initialValue) {
		RowMergeMode result = RowMergeMode.get(initialValue);
		if (result == null)
			throw new IllegalArgumentException("The value '" + initialValue
					+ "' is not a valid enumerator of '" + eDataType.getName()
					+ "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String convertRowMergeModeToString(EDataType eDataType,
			Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public PlanCompareRowType createPlanCompareRowTypeFromString(
			EDataType eDataType, String initialValue) {
		PlanCompareRowType result = PlanCompareRowType.get(initialValue);
		if (result == null)
			throw new IllegalArgumentException("The value '" + initialValue
					+ "' is not a valid enumerator of '" + eDataType.getName()
					+ "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String convertPlanCompareRowTypeToString(EDataType eDataType,
			Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public TablemodelPackage getTablemodelPackage() {
		return (TablemodelPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static TablemodelPackage getPackage() {
		return TablemodelPackage.eINSTANCE;
	}

} // TablemodelFactoryImpl
