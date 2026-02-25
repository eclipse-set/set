/**
 * Copyright (c) 2022 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.tablemodel.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

import org.eclipse.set.model.tablemodel.*;

/**
 * <!-- begin-user-doc --> The <b>Switch</b> for the model's inheritance
 * hierarchy. It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object and proceeding up the
 * inheritance hierarchy until a non-null result is returned, which is the
 * result of the switch. <!-- end-user-doc -->
 * 
 * @see org.eclipse.set.model.tablemodel.TablemodelPackage
 * @generated
 */
public class TablemodelSwitch<T> extends Switch<T> {
	/**
	 * The cached model package <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected static TablemodelPackage modelPackage;

	/**
	 * Creates an instance of the switch. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	public TablemodelSwitch() {
		if (modelPackage == null) {
			modelPackage = TablemodelPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param ePackage
	 *            the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns
	 * a non null result; it yields that result. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the first non-null result returned by a <code>caseXXX</code>
	 *         call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case TablemodelPackage.TABLE: {
				Table table = (Table) theEObject;
				T result = caseTable(table);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case TablemodelPackage.COLUMN_DESCRIPTOR: {
				ColumnDescriptor columnDescriptor = (ColumnDescriptor) theEObject;
				T result = caseColumnDescriptor(columnDescriptor);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case TablemodelPackage.TABLE_CONTENT: {
				TableContent tableContent = (TableContent) theEObject;
				T result = caseTableContent(tableContent);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case TablemodelPackage.ROW_GROUP: {
				RowGroup rowGroup = (RowGroup) theEObject;
				T result = caseRowGroup(rowGroup);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case TablemodelPackage.TABLE_ROW: {
				TableRow tableRow = (TableRow) theEObject;
				T result = caseTableRow(tableRow);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case TablemodelPackage.TABLE_CELL: {
				TableCell tableCell = (TableCell) theEObject;
				T result = caseTableCell(tableCell);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case TablemodelPackage.CELL_CONTENT: {
				CellContent cellContent = (CellContent) theEObject;
				T result = caseCellContent(cellContent);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case TablemodelPackage.STRING_CELL_CONTENT: {
				StringCellContent stringCellContent = (StringCellContent) theEObject;
				T result = caseStringCellContent(stringCellContent);
				if (result == null)
					result = caseCellContent(stringCellContent);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case TablemodelPackage.COMPARE_STATE_CELL_CONTENT: {
				CompareStateCellContent compareStateCellContent = (CompareStateCellContent) theEObject;
				T result = caseCompareStateCellContent(compareStateCellContent);
				if (result == null)
					result = caseCellContent(compareStateCellContent);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case TablemodelPackage.CELL_ANNOTATION: {
				CellAnnotation cellAnnotation = (CellAnnotation) theEObject;
				T result = caseCellAnnotation(cellAnnotation);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case TablemodelPackage.MULTI_COLOR_CELL_CONTENT: {
				MultiColorCellContent multiColorCellContent = (MultiColorCellContent) theEObject;
				T result = caseMultiColorCellContent(multiColorCellContent);
				if (result == null)
					result = caseCellContent(multiColorCellContent);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case TablemodelPackage.MULTI_COLOR_CONTENT: {
				MultiColorContent multiColorContent = (MultiColorContent) theEObject;
				T result = caseMultiColorContent(multiColorContent);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case TablemodelPackage.FOOTNOTE_CONTAINER: {
				FootnoteContainer footnoteContainer = (FootnoteContainer) theEObject;
				T result = caseFootnoteContainer(footnoteContainer);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case TablemodelPackage.COMPARE_FOOTNOTE_CONTAINER: {
				CompareFootnoteContainer compareFootnoteContainer = (CompareFootnoteContainer) theEObject;
				T result = caseCompareFootnoteContainer(
						compareFootnoteContainer);
				if (result == null)
					result = caseFootnoteContainer(compareFootnoteContainer);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case TablemodelPackage.SIMPLE_FOOTNOTE_CONTAINER: {
				SimpleFootnoteContainer simpleFootnoteContainer = (SimpleFootnoteContainer) theEObject;
				T result = caseSimpleFootnoteContainer(simpleFootnoteContainer);
				if (result == null)
					result = caseFootnoteContainer(simpleFootnoteContainer);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case TablemodelPackage.COMPARE_TABLE_CELL_CONTENT: {
				CompareTableCellContent compareTableCellContent = (CompareTableCellContent) theEObject;
				T result = caseCompareTableCellContent(compareTableCellContent);
				if (result == null)
					result = caseCellContent(compareTableCellContent);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case TablemodelPackage.COMPARE_TABLE_FOOTNOTE_CONTAINER: {
				CompareTableFootnoteContainer compareTableFootnoteContainer = (CompareTableFootnoteContainer) theEObject;
				T result = caseCompareTableFootnoteContainer(
						compareTableFootnoteContainer);
				if (result == null)
					result = caseFootnoteContainer(
							compareTableFootnoteContainer);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case TablemodelPackage.PLAN_COMPARE_ROW: {
				PlanCompareRow planCompareRow = (PlanCompareRow) theEObject;
				T result = casePlanCompareRow(planCompareRow);
				if (result == null)
					result = caseTableRow(planCompareRow);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case TablemodelPackage.FOOTNOTE_META_INFORMATION: {
				FootnoteMetaInformation footnoteMetaInformation = (FootnoteMetaInformation) theEObject;
				T result = caseFootnoteMetaInformation(footnoteMetaInformation);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			default:
				return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>Table</em>'. <!-- begin-user-doc --> This implementation returns
	 * null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>Table</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTable(Table object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>Column Descriptor</em>'. <!-- begin-user-doc --> This implementation
	 * returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>Column Descriptor</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseColumnDescriptor(ColumnDescriptor object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>Table Content</em>'. <!-- begin-user-doc --> This implementation
	 * returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>Table Content</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTableContent(TableContent object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Row
	 * Group</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc
	 * -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Row
	 *         Group</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRowGroup(RowGroup object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>Table Row</em>'. <!-- begin-user-doc --> This implementation returns
	 * null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>Table Row</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTableRow(TableRow object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>Table Cell</em>'. <!-- begin-user-doc --> This implementation
	 * returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>Table Cell</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTableCell(TableCell object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Cell
	 * Content</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc
	 * -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Cell
	 *         Content</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCellContent(CellContent object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>String Cell Content</em>'. <!-- begin-user-doc --> This
	 * implementation returns null; returning a non-null result will terminate
	 * the switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>String Cell Content</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStringCellContent(StringCellContent object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>Compare State Cell Content</em>'. <!-- begin-user-doc --> This
	 * implementation returns null; returning a non-null result will terminate
	 * the switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>Compare State Cell Content</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCompareStateCellContent(CompareStateCellContent object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Cell
	 * Annotation</em>'. <!-- begin-user-doc --> This implementation returns
	 * null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Cell
	 *         Annotation</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCellAnnotation(CellAnnotation object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>Multi Color Cell Content</em>'. <!-- begin-user-doc --> This
	 * implementation returns null; returning a non-null result will terminate
	 * the switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>Multi Color Cell Content</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMultiColorCellContent(MultiColorCellContent object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>Multi Color Content</em>'. <!-- begin-user-doc --> This
	 * implementation returns null; returning a non-null result will terminate
	 * the switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>Multi Color Content</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMultiColorContent(MultiColorContent object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>Footnote Container</em>'. <!-- begin-user-doc --> This
	 * implementation returns null; returning a non-null result will terminate
	 * the switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>Footnote Container</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFootnoteContainer(FootnoteContainer object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>Compare Footnote Container</em>'. <!-- begin-user-doc --> This
	 * implementation returns null; returning a non-null result will terminate
	 * the switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>Compare Footnote Container</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCompareFootnoteContainer(CompareFootnoteContainer object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>Simple Footnote Container</em>'. <!-- begin-user-doc --> This
	 * implementation returns null; returning a non-null result will terminate
	 * the switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>Simple Footnote Container</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSimpleFootnoteContainer(SimpleFootnoteContainer object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>Compare Table Cell Content</em>'. <!-- begin-user-doc --> This
	 * implementation returns null; returning a non-null result will terminate
	 * the switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>Compare Table Cell Content</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCompareTableCellContent(CompareTableCellContent object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>Compare Table Footnote Container</em>'. <!-- begin-user-doc --> This
	 * implementation returns null; returning a non-null result will terminate
	 * the switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>Compare Table Footnote Container</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCompareTableFootnoteContainer(
			CompareTableFootnoteContainer object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Plan
	 * Compare Row</em>'. <!-- begin-user-doc --> This implementation returns
	 * null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Plan
	 *         Compare Row</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePlanCompareRow(PlanCompareRow object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>Footnote Meta Information</em>'. <!-- begin-user-doc --> This
	 * implementation returns null; returning a non-null result will terminate
	 * the switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>Footnote Meta Information</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFootnoteMetaInformation(FootnoteMetaInformation object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>EObject</em>'. <!-- begin-user-doc --> This implementation returns
	 * null; returning a non-null result will terminate the switch, but this is
	 * the last case anyway. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} // TablemodelSwitch
