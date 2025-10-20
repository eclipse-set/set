/**
 * Copyright (c) 2022 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.tablemodel.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.set.model.tablemodel.*;

/**
 * <!-- begin-user-doc --> The <b>Adapter Factory</b> for the model. It provides
 * an adapter <code>createXXX</code> method for each class of the model. <!--
 * end-user-doc -->
 * 
 * @see org.eclipse.set.model.tablemodel.TablemodelPackage
 * @generated
 */
public class TablemodelAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected static TablemodelPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	public TablemodelAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = TablemodelPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc --> This implementation returns <code>true</code> if
	 * the object is either the model's package or is an instance object of the
	 * model. <!-- end-user-doc -->
	 * 
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject) object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected TablemodelSwitch<Adapter> modelSwitch = new TablemodelSwitch<Adapter>() {
		@Override
		public Adapter caseTable(Table object) {
			return createTableAdapter();
		}

		@Override
		public Adapter caseColumnDescriptor(ColumnDescriptor object) {
			return createColumnDescriptorAdapter();
		}

		@Override
		public Adapter caseTableContent(TableContent object) {
			return createTableContentAdapter();
		}

		@Override
		public Adapter caseRowGroup(RowGroup object) {
			return createRowGroupAdapter();
		}

		@Override
		public Adapter caseTableRow(TableRow object) {
			return createTableRowAdapter();
		}

		@Override
		public Adapter caseTableCell(TableCell object) {
			return createTableCellAdapter();
		}

		@Override
		public Adapter caseCellContent(CellContent object) {
			return createCellContentAdapter();
		}

		@Override
		public Adapter caseStringCellContent(StringCellContent object) {
			return createStringCellContentAdapter();
		}

		@Override
		public Adapter caseCompareCellContent(CompareCellContent object) {
			return createCompareCellContentAdapter();
		}

		@Override
		public Adapter caseCellAnnotation(CellAnnotation object) {
			return createCellAnnotationAdapter();
		}

		@Override
		public Adapter caseMultiColorCellContent(MultiColorCellContent object) {
			return createMultiColorCellContentAdapter();
		}

		@Override
		public Adapter caseMultiColorContent(MultiColorContent object) {
			return createMultiColorContentAdapter();
		}

		@Override
		public Adapter caseFootnoteContainer(FootnoteContainer object) {
			return createFootnoteContainerAdapter();
		}

		@Override
		public Adapter caseCompareFootnoteContainer(
				CompareFootnoteContainer object) {
			return createCompareFootnoteContainerAdapter();
		}

		@Override
		public Adapter caseSimpleFootnoteContainer(
				SimpleFootnoteContainer object) {
			return createSimpleFootnoteContainerAdapter();
		}

		@Override
		public Adapter caseCompareTableCellContent(
				CompareTableCellContent object) {
			return createCompareTableCellContentAdapter();
		}

		@Override
		public Adapter caseCompareTableFootnoteContainer(
				CompareTableFootnoteContainer object) {
			return createCompareTableFootnoteContainerAdapter();
		}

		@Override
		public Adapter casePlanCompareRow(PlanCompareRow object) {
			return createPlanCompareRowAdapter();
		}

		@Override
		public Adapter defaultCase(EObject object) {
			return createEObjectAdapter();
		}
	};

	/**
	 * Creates an adapter for the <code>target</code>. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param target
	 *            the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject) target);
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.eclipse.set.model.tablemodel.Table <em>Table</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we
	 * can easily ignore cases; it's useful to ignore a case when inheritance
	 * will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.set.model.tablemodel.Table
	 * @generated
	 */
	public Adapter createTableAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.eclipse.set.model.tablemodel.ColumnDescriptor <em>Column
	 * Descriptor</em>}'. <!-- begin-user-doc --> This default implementation
	 * returns null so that we can easily ignore cases; it's useful to ignore a
	 * case when inheritance will catch all the cases anyway. <!-- end-user-doc
	 * -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.set.model.tablemodel.ColumnDescriptor
	 * @generated
	 */
	public Adapter createColumnDescriptorAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.eclipse.set.model.tablemodel.TableContent <em>Table
	 * Content</em>}'. <!-- begin-user-doc --> This default implementation
	 * returns null so that we can easily ignore cases; it's useful to ignore a
	 * case when inheritance will catch all the cases anyway. <!-- end-user-doc
	 * -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.set.model.tablemodel.TableContent
	 * @generated
	 */
	public Adapter createTableContentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.eclipse.set.model.tablemodel.RowGroup <em>Row Group</em>}'.
	 * <!-- begin-user-doc --> This default implementation returns null so that
	 * we can easily ignore cases; it's useful to ignore a case when inheritance
	 * will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.set.model.tablemodel.RowGroup
	 * @generated
	 */
	public Adapter createRowGroupAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.eclipse.set.model.tablemodel.TableRow <em>Table Row</em>}'.
	 * <!-- begin-user-doc --> This default implementation returns null so that
	 * we can easily ignore cases; it's useful to ignore a case when inheritance
	 * will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.set.model.tablemodel.TableRow
	 * @generated
	 */
	public Adapter createTableRowAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.eclipse.set.model.tablemodel.TableCell <em>Table Cell</em>}'.
	 * <!-- begin-user-doc --> This default implementation returns null so that
	 * we can easily ignore cases; it's useful to ignore a case when inheritance
	 * will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.set.model.tablemodel.TableCell
	 * @generated
	 */
	public Adapter createTableCellAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.eclipse.set.model.tablemodel.CellContent <em>Cell
	 * Content</em>}'. <!-- begin-user-doc --> This default implementation
	 * returns null so that we can easily ignore cases; it's useful to ignore a
	 * case when inheritance will catch all the cases anyway. <!-- end-user-doc
	 * -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.set.model.tablemodel.CellContent
	 * @generated
	 */
	public Adapter createCellContentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.eclipse.set.model.tablemodel.StringCellContent <em>String
	 * Cell Content</em>}'. <!-- begin-user-doc --> This default implementation
	 * returns null so that we can easily ignore cases; it's useful to ignore a
	 * case when inheritance will catch all the cases anyway. <!-- end-user-doc
	 * -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.set.model.tablemodel.StringCellContent
	 * @generated
	 */
	public Adapter createStringCellContentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.eclipse.set.model.tablemodel.CompareCellContent <em>Compare
	 * Cell Content</em>}'. <!-- begin-user-doc --> This default implementation
	 * returns null so that we can easily ignore cases; it's useful to ignore a
	 * case when inheritance will catch all the cases anyway. <!-- end-user-doc
	 * -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.set.model.tablemodel.CompareCellContent
	 * @generated
	 */
	public Adapter createCompareCellContentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.eclipse.set.model.tablemodel.CellAnnotation <em>Cell
	 * Annotation</em>}'. <!-- begin-user-doc --> This default implementation
	 * returns null so that we can easily ignore cases; it's useful to ignore a
	 * case when inheritance will catch all the cases anyway. <!-- end-user-doc
	 * -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.set.model.tablemodel.CellAnnotation
	 * @generated
	 */
	public Adapter createCellAnnotationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.eclipse.set.model.tablemodel.MultiColorCellContent <em>Multi
	 * Color Cell Content</em>}'. <!-- begin-user-doc --> This default
	 * implementation returns null so that we can easily ignore cases; it's
	 * useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.set.model.tablemodel.MultiColorCellContent
	 * @generated
	 */
	public Adapter createMultiColorCellContentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.eclipse.set.model.tablemodel.MultiColorContent <em>Multi
	 * Color Content</em>}'. <!-- begin-user-doc --> This default implementation
	 * returns null so that we can easily ignore cases; it's useful to ignore a
	 * case when inheritance will catch all the cases anyway. <!-- end-user-doc
	 * -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.set.model.tablemodel.MultiColorContent
	 * @generated
	 */
	public Adapter createMultiColorContentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.eclipse.set.model.tablemodel.FootnoteContainer <em>Footnote
	 * Container</em>}'. <!-- begin-user-doc --> This default implementation
	 * returns null so that we can easily ignore cases; it's useful to ignore a
	 * case when inheritance will catch all the cases anyway. <!-- end-user-doc
	 * -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.set.model.tablemodel.FootnoteContainer
	 * @generated
	 */
	public Adapter createFootnoteContainerAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.eclipse.set.model.tablemodel.CompareFootnoteContainer
	 * <em>Compare Footnote Container</em>}'. <!-- begin-user-doc --> This
	 * default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases
	 * anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.set.model.tablemodel.CompareFootnoteContainer
	 * @generated
	 */
	public Adapter createCompareFootnoteContainerAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.eclipse.set.model.tablemodel.SimpleFootnoteContainer
	 * <em>Simple Footnote Container</em>}'. <!-- begin-user-doc --> This
	 * default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases
	 * anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.set.model.tablemodel.SimpleFootnoteContainer
	 * @generated
	 */
	public Adapter createSimpleFootnoteContainerAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.eclipse.set.model.tablemodel.CompareTableCellContent
	 * <em>Compare Table Cell Content</em>}'. <!-- begin-user-doc --> This
	 * default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases
	 * anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.set.model.tablemodel.CompareTableCellContent
	 * @generated
	 */
	public Adapter createCompareTableCellContentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.eclipse.set.model.tablemodel.CompareTableFootnoteContainer
	 * <em>Compare Table Footnote Container</em>}'. <!-- begin-user-doc --> This
	 * default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases
	 * anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.set.model.tablemodel.CompareTableFootnoteContainer
	 * @generated
	 */
	public Adapter createCompareTableFootnoteContainerAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.eclipse.set.model.tablemodel.PlanCompareRow <em>Plan Compare
	 * Row</em>}'. <!-- begin-user-doc --> This default implementation returns
	 * null so that we can easily ignore cases; it's useful to ignore a case
	 * when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.set.model.tablemodel.PlanCompareRow
	 * @generated
	 */
	public Adapter createPlanCompareRowAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case. <!-- begin-user-doc --> This
	 * default implementation returns null. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} // TablemodelAdapterFactory
