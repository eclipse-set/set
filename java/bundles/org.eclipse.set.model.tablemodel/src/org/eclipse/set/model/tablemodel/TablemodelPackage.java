/**
 * Copyright (c) 2022 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.tablemodel;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.eclipse.set.model.tablemodel.TablemodelFactory
 * @model kind="package"
 * @generated
 */
public interface TablemodelPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "tablemodel";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "tag:scheidt-bachmann-st.de,2016-10-11:planpro/tm";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "tm";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	TablemodelPackage eINSTANCE = org.eclipse.set.model.tablemodel.impl.TablemodelPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.set.model.tablemodel.impl.TableImpl <em>Table</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.set.model.tablemodel.impl.TableImpl
	 * @see org.eclipse.set.model.tablemodel.impl.TablemodelPackageImpl#getTable()
	 * @generated
	 */
	int TABLE = 0;

	/**
	 * The feature id for the '<em><b>Columndescriptors</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TABLE__COLUMNDESCRIPTORS = 0;

	/**
	 * The feature id for the '<em><b>Tablecontent</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TABLE__TABLECONTENT = 1;

	/**
	 * The number of structural features of the '<em>Table</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TABLE_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Table</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TABLE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.set.model.tablemodel.impl.ColumnDescriptorImpl <em>Column Descriptor</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.set.model.tablemodel.impl.ColumnDescriptorImpl
	 * @see org.eclipse.set.model.tablemodel.impl.TablemodelPackageImpl#getColumnDescriptor()
	 * @generated
	 */
	int COLUMN_DESCRIPTOR = 1;

	/**
	 * The feature id for the '<em><b>Width</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COLUMN_DESCRIPTOR__WIDTH = 0;

	/**
	 * The feature id for the '<em><b>Children</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COLUMN_DESCRIPTOR__CHILDREN = 1;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COLUMN_DESCRIPTOR__LABEL = 2;

	/**
	 * The feature id for the '<em><b>Greyed</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COLUMN_DESCRIPTOR__GREYED = 3;

	/**
	 * The feature id for the '<em><b>Unit</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COLUMN_DESCRIPTOR__UNIT = 4;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COLUMN_DESCRIPTOR__PARENT = 5;

	/**
	 * The feature id for the '<em><b>Height</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COLUMN_DESCRIPTOR__HEIGHT = 6;

	/**
	 * The feature id for the '<em><b>Merge Common Values</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COLUMN_DESCRIPTOR__MERGE_COMMON_VALUES = 7;

	/**
	 * The number of structural features of the '<em>Column Descriptor</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COLUMN_DESCRIPTOR_FEATURE_COUNT = 8;

	/**
	 * The number of operations of the '<em>Column Descriptor</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COLUMN_DESCRIPTOR_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.set.model.tablemodel.impl.TableContentImpl <em>Table Content</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.set.model.tablemodel.impl.TableContentImpl
	 * @see org.eclipse.set.model.tablemodel.impl.TablemodelPackageImpl#getTableContent()
	 * @generated
	 */
	int TABLE_CONTENT = 2;

	/**
	 * The feature id for the '<em><b>Rowgroups</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TABLE_CONTENT__ROWGROUPS = 0;

	/**
	 * The number of structural features of the '<em>Table Content</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TABLE_CONTENT_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Table Content</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TABLE_CONTENT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.set.model.tablemodel.impl.RowGroupImpl <em>Row Group</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.set.model.tablemodel.impl.RowGroupImpl
	 * @see org.eclipse.set.model.tablemodel.impl.TablemodelPackageImpl#getRowGroup()
	 * @generated
	 */
	int ROW_GROUP = 3;

	/**
	 * The feature id for the '<em><b>Rows</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROW_GROUP__ROWS = 0;

	/**
	 * The feature id for the '<em><b>Leading Object</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROW_GROUP__LEADING_OBJECT = 1;

	/**
	 * The feature id for the '<em><b>Leading Object Index</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROW_GROUP__LEADING_OBJECT_INDEX = 2;

	/**
	 * The number of structural features of the '<em>Row Group</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROW_GROUP_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Row Group</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROW_GROUP_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.set.model.tablemodel.impl.TableRowImpl <em>Table Row</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.set.model.tablemodel.impl.TableRowImpl
	 * @see org.eclipse.set.model.tablemodel.impl.TablemodelPackageImpl#getTableRow()
	 * @generated
	 */
	int TABLE_ROW = 4;

	/**
	 * The feature id for the '<em><b>Cells</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TABLE_ROW__CELLS = 0;

	/**
	 * The feature id for the '<em><b>Footnotes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TABLE_ROW__FOOTNOTES = 1;

	/**
	 * The number of structural features of the '<em>Table Row</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TABLE_ROW_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Table Row</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TABLE_ROW_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.set.model.tablemodel.impl.TableCellImpl <em>Table Cell</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.set.model.tablemodel.impl.TableCellImpl
	 * @see org.eclipse.set.model.tablemodel.impl.TablemodelPackageImpl#getTableCell()
	 * @generated
	 */
	int TABLE_CELL = 5;

	/**
	 * The feature id for the '<em><b>Content</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TABLE_CELL__CONTENT = 0;

	/**
	 * The feature id for the '<em><b>Columndescriptor</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TABLE_CELL__COLUMNDESCRIPTOR = 1;

	/**
	 * The feature id for the '<em><b>Cellannotation</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TABLE_CELL__CELLANNOTATION = 2;

	/**
	 * The number of structural features of the '<em>Table Cell</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TABLE_CELL_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Table Cell</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TABLE_CELL_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.set.model.tablemodel.impl.CellContentImpl <em>Cell Content</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.set.model.tablemodel.impl.CellContentImpl
	 * @see org.eclipse.set.model.tablemodel.impl.TablemodelPackageImpl#getCellContent()
	 * @generated
	 */
	int CELL_CONTENT = 6;

	/**
	 * The number of structural features of the '<em>Cell Content</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CELL_CONTENT_FEATURE_COUNT = 0;

	/**
	 * The number of operations of the '<em>Cell Content</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CELL_CONTENT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.set.model.tablemodel.impl.StringCellContentImpl <em>String Cell Content</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.set.model.tablemodel.impl.StringCellContentImpl
	 * @see org.eclipse.set.model.tablemodel.impl.TablemodelPackageImpl#getStringCellContent()
	 * @generated
	 */
	int STRING_CELL_CONTENT = 7;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_CELL_CONTENT__VALUE = CELL_CONTENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>String Cell Content</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_CELL_CONTENT_FEATURE_COUNT = CELL_CONTENT_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>String Cell Content</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_CELL_CONTENT_OPERATION_COUNT = CELL_CONTENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.set.model.tablemodel.impl.CompareCellContentImpl <em>Compare Cell Content</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.set.model.tablemodel.impl.CompareCellContentImpl
	 * @see org.eclipse.set.model.tablemodel.impl.TablemodelPackageImpl#getCompareCellContent()
	 * @generated
	 */
	int COMPARE_CELL_CONTENT = 8;

	/**
	 * The feature id for the '<em><b>Old Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPARE_CELL_CONTENT__OLD_VALUE = CELL_CONTENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>New Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPARE_CELL_CONTENT__NEW_VALUE = CELL_CONTENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Compare Cell Content</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPARE_CELL_CONTENT_FEATURE_COUNT = CELL_CONTENT_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Compare Cell Content</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPARE_CELL_CONTENT_OPERATION_COUNT = CELL_CONTENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.set.model.tablemodel.impl.CellAnnotationImpl <em>Cell Annotation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.set.model.tablemodel.impl.CellAnnotationImpl
	 * @see org.eclipse.set.model.tablemodel.impl.TablemodelPackageImpl#getCellAnnotation()
	 * @generated
	 */
	int CELL_ANNOTATION = 9;

	/**
	 * The number of structural features of the '<em>Cell Annotation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CELL_ANNOTATION_FEATURE_COUNT = 0;

	/**
	 * The number of operations of the '<em>Cell Annotation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CELL_ANNOTATION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.set.model.tablemodel.impl.FootnoteImpl <em>Footnote</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.set.model.tablemodel.impl.FootnoteImpl
	 * @see org.eclipse.set.model.tablemodel.impl.TablemodelPackageImpl#getFootnote()
	 * @generated
	 */
	int FOOTNOTE = 10;

	/**
	 * The feature id for the '<em><b>Number</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOOTNOTE__NUMBER = 0;

	/**
	 * The feature id for the '<em><b>Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOOTNOTE__TEXT = 1;

	/**
	 * The number of structural features of the '<em>Footnote</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOOTNOTE_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Footnote</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOOTNOTE_OPERATION_COUNT = 0;


	/**
	 * Returns the meta object for class '{@link org.eclipse.set.model.tablemodel.Table <em>Table</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Table</em>'.
	 * @see org.eclipse.set.model.tablemodel.Table
	 * @generated
	 */
	EClass getTable();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.set.model.tablemodel.Table#getColumndescriptors <em>Columndescriptors</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Columndescriptors</em>'.
	 * @see org.eclipse.set.model.tablemodel.Table#getColumndescriptors()
	 * @see #getTable()
	 * @generated
	 */
	EReference getTable_Columndescriptors();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.set.model.tablemodel.Table#getTablecontent <em>Tablecontent</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Tablecontent</em>'.
	 * @see org.eclipse.set.model.tablemodel.Table#getTablecontent()
	 * @see #getTable()
	 * @generated
	 */
	EReference getTable_Tablecontent();

	/**
	 * Returns the meta object for class '{@link org.eclipse.set.model.tablemodel.ColumnDescriptor <em>Column Descriptor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Column Descriptor</em>'.
	 * @see org.eclipse.set.model.tablemodel.ColumnDescriptor
	 * @generated
	 */
	EClass getColumnDescriptor();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.set.model.tablemodel.ColumnDescriptor#getWidth <em>Width</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Width</em>'.
	 * @see org.eclipse.set.model.tablemodel.ColumnDescriptor#getWidth()
	 * @see #getColumnDescriptor()
	 * @generated
	 */
	EAttribute getColumnDescriptor_Width();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.set.model.tablemodel.ColumnDescriptor#getChildren <em>Children</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Children</em>'.
	 * @see org.eclipse.set.model.tablemodel.ColumnDescriptor#getChildren()
	 * @see #getColumnDescriptor()
	 * @generated
	 */
	EReference getColumnDescriptor_Children();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.set.model.tablemodel.ColumnDescriptor#getLabel <em>Label</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Label</em>'.
	 * @see org.eclipse.set.model.tablemodel.ColumnDescriptor#getLabel()
	 * @see #getColumnDescriptor()
	 * @generated
	 */
	EAttribute getColumnDescriptor_Label();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.set.model.tablemodel.ColumnDescriptor#isGreyed <em>Greyed</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Greyed</em>'.
	 * @see org.eclipse.set.model.tablemodel.ColumnDescriptor#isGreyed()
	 * @see #getColumnDescriptor()
	 * @generated
	 */
	EAttribute getColumnDescriptor_Greyed();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.set.model.tablemodel.ColumnDescriptor#isUnit <em>Unit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Unit</em>'.
	 * @see org.eclipse.set.model.tablemodel.ColumnDescriptor#isUnit()
	 * @see #getColumnDescriptor()
	 * @generated
	 */
	EAttribute getColumnDescriptor_Unit();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.set.model.tablemodel.ColumnDescriptor#getParent <em>Parent</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Parent</em>'.
	 * @see org.eclipse.set.model.tablemodel.ColumnDescriptor#getParent()
	 * @see #getColumnDescriptor()
	 * @generated
	 */
	EReference getColumnDescriptor_Parent();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.set.model.tablemodel.ColumnDescriptor#getHeight <em>Height</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Height</em>'.
	 * @see org.eclipse.set.model.tablemodel.ColumnDescriptor#getHeight()
	 * @see #getColumnDescriptor()
	 * @generated
	 */
	EAttribute getColumnDescriptor_Height();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.set.model.tablemodel.ColumnDescriptor#isMergeCommonValues <em>Merge Common Values</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Merge Common Values</em>'.
	 * @see org.eclipse.set.model.tablemodel.ColumnDescriptor#isMergeCommonValues()
	 * @see #getColumnDescriptor()
	 * @generated
	 */
	EAttribute getColumnDescriptor_MergeCommonValues();

	/**
	 * Returns the meta object for class '{@link org.eclipse.set.model.tablemodel.TableContent <em>Table Content</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Table Content</em>'.
	 * @see org.eclipse.set.model.tablemodel.TableContent
	 * @generated
	 */
	EClass getTableContent();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.set.model.tablemodel.TableContent#getRowgroups <em>Rowgroups</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Rowgroups</em>'.
	 * @see org.eclipse.set.model.tablemodel.TableContent#getRowgroups()
	 * @see #getTableContent()
	 * @generated
	 */
	EReference getTableContent_Rowgroups();

	/**
	 * Returns the meta object for class '{@link org.eclipse.set.model.tablemodel.RowGroup <em>Row Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Row Group</em>'.
	 * @see org.eclipse.set.model.tablemodel.RowGroup
	 * @generated
	 */
	EClass getRowGroup();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.set.model.tablemodel.RowGroup#getRows <em>Rows</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Rows</em>'.
	 * @see org.eclipse.set.model.tablemodel.RowGroup#getRows()
	 * @see #getRowGroup()
	 * @generated
	 */
	EReference getRowGroup_Rows();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.set.model.tablemodel.RowGroup#getLeadingObject <em>Leading Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Leading Object</em>'.
	 * @see org.eclipse.set.model.tablemodel.RowGroup#getLeadingObject()
	 * @see #getRowGroup()
	 * @generated
	 */
	EReference getRowGroup_LeadingObject();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.set.model.tablemodel.RowGroup#getLeadingObjectIndex <em>Leading Object Index</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Leading Object Index</em>'.
	 * @see org.eclipse.set.model.tablemodel.RowGroup#getLeadingObjectIndex()
	 * @see #getRowGroup()
	 * @generated
	 */
	EAttribute getRowGroup_LeadingObjectIndex();

	/**
	 * Returns the meta object for class '{@link org.eclipse.set.model.tablemodel.TableRow <em>Table Row</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Table Row</em>'.
	 * @see org.eclipse.set.model.tablemodel.TableRow
	 * @generated
	 */
	EClass getTableRow();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.set.model.tablemodel.TableRow#getCells <em>Cells</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Cells</em>'.
	 * @see org.eclipse.set.model.tablemodel.TableRow#getCells()
	 * @see #getTableRow()
	 * @generated
	 */
	EReference getTableRow_Cells();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.set.model.tablemodel.TableRow#getFootnotes <em>Footnotes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Footnotes</em>'.
	 * @see org.eclipse.set.model.tablemodel.TableRow#getFootnotes()
	 * @see #getTableRow()
	 * @generated
	 */
	EReference getTableRow_Footnotes();

	/**
	 * Returns the meta object for class '{@link org.eclipse.set.model.tablemodel.TableCell <em>Table Cell</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Table Cell</em>'.
	 * @see org.eclipse.set.model.tablemodel.TableCell
	 * @generated
	 */
	EClass getTableCell();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.set.model.tablemodel.TableCell#getContent <em>Content</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Content</em>'.
	 * @see org.eclipse.set.model.tablemodel.TableCell#getContent()
	 * @see #getTableCell()
	 * @generated
	 */
	EReference getTableCell_Content();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.set.model.tablemodel.TableCell#getColumndescriptor <em>Columndescriptor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Columndescriptor</em>'.
	 * @see org.eclipse.set.model.tablemodel.TableCell#getColumndescriptor()
	 * @see #getTableCell()
	 * @generated
	 */
	EReference getTableCell_Columndescriptor();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.set.model.tablemodel.TableCell#getCellannotation <em>Cellannotation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Cellannotation</em>'.
	 * @see org.eclipse.set.model.tablemodel.TableCell#getCellannotation()
	 * @see #getTableCell()
	 * @generated
	 */
	EReference getTableCell_Cellannotation();

	/**
	 * Returns the meta object for class '{@link org.eclipse.set.model.tablemodel.CellContent <em>Cell Content</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Cell Content</em>'.
	 * @see org.eclipse.set.model.tablemodel.CellContent
	 * @generated
	 */
	EClass getCellContent();

	/**
	 * Returns the meta object for class '{@link org.eclipse.set.model.tablemodel.StringCellContent <em>String Cell Content</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>String Cell Content</em>'.
	 * @see org.eclipse.set.model.tablemodel.StringCellContent
	 * @generated
	 */
	EClass getStringCellContent();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.set.model.tablemodel.StringCellContent#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.set.model.tablemodel.StringCellContent#getValue()
	 * @see #getStringCellContent()
	 * @generated
	 */
	EAttribute getStringCellContent_Value();

	/**
	 * Returns the meta object for class '{@link org.eclipse.set.model.tablemodel.CompareCellContent <em>Compare Cell Content</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Compare Cell Content</em>'.
	 * @see org.eclipse.set.model.tablemodel.CompareCellContent
	 * @generated
	 */
	EClass getCompareCellContent();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.set.model.tablemodel.CompareCellContent#getOldValue <em>Old Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Old Value</em>'.
	 * @see org.eclipse.set.model.tablemodel.CompareCellContent#getOldValue()
	 * @see #getCompareCellContent()
	 * @generated
	 */
	EAttribute getCompareCellContent_OldValue();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.set.model.tablemodel.CompareCellContent#getNewValue <em>New Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>New Value</em>'.
	 * @see org.eclipse.set.model.tablemodel.CompareCellContent#getNewValue()
	 * @see #getCompareCellContent()
	 * @generated
	 */
	EAttribute getCompareCellContent_NewValue();

	/**
	 * Returns the meta object for class '{@link org.eclipse.set.model.tablemodel.CellAnnotation <em>Cell Annotation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Cell Annotation</em>'.
	 * @see org.eclipse.set.model.tablemodel.CellAnnotation
	 * @generated
	 */
	EClass getCellAnnotation();

	/**
	 * Returns the meta object for class '{@link org.eclipse.set.model.tablemodel.Footnote <em>Footnote</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Footnote</em>'.
	 * @see org.eclipse.set.model.tablemodel.Footnote
	 * @generated
	 */
	EClass getFootnote();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.set.model.tablemodel.Footnote#getNumber <em>Number</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Number</em>'.
	 * @see org.eclipse.set.model.tablemodel.Footnote#getNumber()
	 * @see #getFootnote()
	 * @generated
	 */
	EAttribute getFootnote_Number();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.set.model.tablemodel.Footnote#getText <em>Text</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Text</em>'.
	 * @see org.eclipse.set.model.tablemodel.Footnote#getText()
	 * @see #getFootnote()
	 * @generated
	 */
	EAttribute getFootnote_Text();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	TablemodelFactory getTablemodelFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.eclipse.set.model.tablemodel.impl.TableImpl <em>Table</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.set.model.tablemodel.impl.TableImpl
		 * @see org.eclipse.set.model.tablemodel.impl.TablemodelPackageImpl#getTable()
		 * @generated
		 */
		EClass TABLE = eINSTANCE.getTable();

		/**
		 * The meta object literal for the '<em><b>Columndescriptors</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TABLE__COLUMNDESCRIPTORS = eINSTANCE.getTable_Columndescriptors();

		/**
		 * The meta object literal for the '<em><b>Tablecontent</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TABLE__TABLECONTENT = eINSTANCE.getTable_Tablecontent();

		/**
		 * The meta object literal for the '{@link org.eclipse.set.model.tablemodel.impl.ColumnDescriptorImpl <em>Column Descriptor</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.set.model.tablemodel.impl.ColumnDescriptorImpl
		 * @see org.eclipse.set.model.tablemodel.impl.TablemodelPackageImpl#getColumnDescriptor()
		 * @generated
		 */
		EClass COLUMN_DESCRIPTOR = eINSTANCE.getColumnDescriptor();

		/**
		 * The meta object literal for the '<em><b>Width</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COLUMN_DESCRIPTOR__WIDTH = eINSTANCE.getColumnDescriptor_Width();

		/**
		 * The meta object literal for the '<em><b>Children</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COLUMN_DESCRIPTOR__CHILDREN = eINSTANCE.getColumnDescriptor_Children();

		/**
		 * The meta object literal for the '<em><b>Label</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COLUMN_DESCRIPTOR__LABEL = eINSTANCE.getColumnDescriptor_Label();

		/**
		 * The meta object literal for the '<em><b>Greyed</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COLUMN_DESCRIPTOR__GREYED = eINSTANCE.getColumnDescriptor_Greyed();

		/**
		 * The meta object literal for the '<em><b>Unit</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COLUMN_DESCRIPTOR__UNIT = eINSTANCE.getColumnDescriptor_Unit();

		/**
		 * The meta object literal for the '<em><b>Parent</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COLUMN_DESCRIPTOR__PARENT = eINSTANCE.getColumnDescriptor_Parent();

		/**
		 * The meta object literal for the '<em><b>Height</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COLUMN_DESCRIPTOR__HEIGHT = eINSTANCE.getColumnDescriptor_Height();

		/**
		 * The meta object literal for the '<em><b>Merge Common Values</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COLUMN_DESCRIPTOR__MERGE_COMMON_VALUES = eINSTANCE.getColumnDescriptor_MergeCommonValues();

		/**
		 * The meta object literal for the '{@link org.eclipse.set.model.tablemodel.impl.TableContentImpl <em>Table Content</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.set.model.tablemodel.impl.TableContentImpl
		 * @see org.eclipse.set.model.tablemodel.impl.TablemodelPackageImpl#getTableContent()
		 * @generated
		 */
		EClass TABLE_CONTENT = eINSTANCE.getTableContent();

		/**
		 * The meta object literal for the '<em><b>Rowgroups</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TABLE_CONTENT__ROWGROUPS = eINSTANCE.getTableContent_Rowgroups();

		/**
		 * The meta object literal for the '{@link org.eclipse.set.model.tablemodel.impl.RowGroupImpl <em>Row Group</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.set.model.tablemodel.impl.RowGroupImpl
		 * @see org.eclipse.set.model.tablemodel.impl.TablemodelPackageImpl#getRowGroup()
		 * @generated
		 */
		EClass ROW_GROUP = eINSTANCE.getRowGroup();

		/**
		 * The meta object literal for the '<em><b>Rows</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ROW_GROUP__ROWS = eINSTANCE.getRowGroup_Rows();

		/**
		 * The meta object literal for the '<em><b>Leading Object</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ROW_GROUP__LEADING_OBJECT = eINSTANCE.getRowGroup_LeadingObject();

		/**
		 * The meta object literal for the '<em><b>Leading Object Index</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ROW_GROUP__LEADING_OBJECT_INDEX = eINSTANCE.getRowGroup_LeadingObjectIndex();

		/**
		 * The meta object literal for the '{@link org.eclipse.set.model.tablemodel.impl.TableRowImpl <em>Table Row</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.set.model.tablemodel.impl.TableRowImpl
		 * @see org.eclipse.set.model.tablemodel.impl.TablemodelPackageImpl#getTableRow()
		 * @generated
		 */
		EClass TABLE_ROW = eINSTANCE.getTableRow();

		/**
		 * The meta object literal for the '<em><b>Cells</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TABLE_ROW__CELLS = eINSTANCE.getTableRow_Cells();

		/**
		 * The meta object literal for the '<em><b>Footnotes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TABLE_ROW__FOOTNOTES = eINSTANCE.getTableRow_Footnotes();

		/**
		 * The meta object literal for the '{@link org.eclipse.set.model.tablemodel.impl.TableCellImpl <em>Table Cell</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.set.model.tablemodel.impl.TableCellImpl
		 * @see org.eclipse.set.model.tablemodel.impl.TablemodelPackageImpl#getTableCell()
		 * @generated
		 */
		EClass TABLE_CELL = eINSTANCE.getTableCell();

		/**
		 * The meta object literal for the '<em><b>Content</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TABLE_CELL__CONTENT = eINSTANCE.getTableCell_Content();

		/**
		 * The meta object literal for the '<em><b>Columndescriptor</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TABLE_CELL__COLUMNDESCRIPTOR = eINSTANCE.getTableCell_Columndescriptor();

		/**
		 * The meta object literal for the '<em><b>Cellannotation</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TABLE_CELL__CELLANNOTATION = eINSTANCE.getTableCell_Cellannotation();

		/**
		 * The meta object literal for the '{@link org.eclipse.set.model.tablemodel.impl.CellContentImpl <em>Cell Content</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.set.model.tablemodel.impl.CellContentImpl
		 * @see org.eclipse.set.model.tablemodel.impl.TablemodelPackageImpl#getCellContent()
		 * @generated
		 */
		EClass CELL_CONTENT = eINSTANCE.getCellContent();

		/**
		 * The meta object literal for the '{@link org.eclipse.set.model.tablemodel.impl.StringCellContentImpl <em>String Cell Content</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.set.model.tablemodel.impl.StringCellContentImpl
		 * @see org.eclipse.set.model.tablemodel.impl.TablemodelPackageImpl#getStringCellContent()
		 * @generated
		 */
		EClass STRING_CELL_CONTENT = eINSTANCE.getStringCellContent();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STRING_CELL_CONTENT__VALUE = eINSTANCE.getStringCellContent_Value();

		/**
		 * The meta object literal for the '{@link org.eclipse.set.model.tablemodel.impl.CompareCellContentImpl <em>Compare Cell Content</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.set.model.tablemodel.impl.CompareCellContentImpl
		 * @see org.eclipse.set.model.tablemodel.impl.TablemodelPackageImpl#getCompareCellContent()
		 * @generated
		 */
		EClass COMPARE_CELL_CONTENT = eINSTANCE.getCompareCellContent();

		/**
		 * The meta object literal for the '<em><b>Old Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMPARE_CELL_CONTENT__OLD_VALUE = eINSTANCE.getCompareCellContent_OldValue();

		/**
		 * The meta object literal for the '<em><b>New Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMPARE_CELL_CONTENT__NEW_VALUE = eINSTANCE.getCompareCellContent_NewValue();

		/**
		 * The meta object literal for the '{@link org.eclipse.set.model.tablemodel.impl.CellAnnotationImpl <em>Cell Annotation</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.set.model.tablemodel.impl.CellAnnotationImpl
		 * @see org.eclipse.set.model.tablemodel.impl.TablemodelPackageImpl#getCellAnnotation()
		 * @generated
		 */
		EClass CELL_ANNOTATION = eINSTANCE.getCellAnnotation();

		/**
		 * The meta object literal for the '{@link org.eclipse.set.model.tablemodel.impl.FootnoteImpl <em>Footnote</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.set.model.tablemodel.impl.FootnoteImpl
		 * @see org.eclipse.set.model.tablemodel.impl.TablemodelPackageImpl#getFootnote()
		 * @generated
		 */
		EClass FOOTNOTE = eINSTANCE.getFootnote();

		/**
		 * The meta object literal for the '<em><b>Number</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FOOTNOTE__NUMBER = eINSTANCE.getFootnote_Number();

		/**
		 * The meta object literal for the '<em><b>Text</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FOOTNOTE__TEXT = eINSTANCE.getFootnote_Text();

	}

} //TablemodelPackage
