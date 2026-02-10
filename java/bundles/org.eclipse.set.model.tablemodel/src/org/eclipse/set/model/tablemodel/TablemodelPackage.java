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
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

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
 * @see org.eclipse.set.model.tablemodel.TablemodelFactory
 * @model kind="package"
 * @generated
 */
public interface TablemodelPackage extends EPackage {
	/**
	 * The package name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNAME = "tablemodel";

	/**
	 * The package namespace URI. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_URI = "tag:scheidt-bachmann-st.de,2016-10-11:planpro/tm";

	/**
	 * The package namespace name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_PREFIX = "tm";

	/**
	 * The singleton instance of the package. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	TablemodelPackage eINSTANCE = org.eclipse.set.model.tablemodel.impl.TablemodelPackageImpl
			.init();

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.set.model.tablemodel.impl.TableImpl <em>Table</em>}'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.set.model.tablemodel.impl.TableImpl
	 * @see org.eclipse.set.model.tablemodel.impl.TablemodelPackageImpl#getTable()
	 * @generated
	 */
	int TABLE = 0;

	/**
	 * The feature id for the '<em><b>Columndescriptors</b></em>' containment
	 * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TABLE__COLUMNDESCRIPTORS = 0;

	/**
	 * The feature id for the '<em><b>Tablecontent</b></em>' containment
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TABLE__TABLECONTENT = 1;

	/**
	 * The number of structural features of the '<em>Table</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TABLE_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Table</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TABLE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.set.model.tablemodel.impl.ColumnDescriptorImpl
	 * <em>Column Descriptor</em>}' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see org.eclipse.set.model.tablemodel.impl.ColumnDescriptorImpl
	 * @see org.eclipse.set.model.tablemodel.impl.TablemodelPackageImpl#getColumnDescriptor()
	 * @generated
	 */
	int COLUMN_DESCRIPTOR = 1;

	/**
	 * The feature id for the '<em><b>Width</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int COLUMN_DESCRIPTOR__WIDTH = 0;

	/**
	 * The feature id for the '<em><b>Width Mode</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int COLUMN_DESCRIPTOR__WIDTH_MODE = 1;

	/**
	 * The feature id for the '<em><b>Children</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int COLUMN_DESCRIPTOR__CHILDREN = 2;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int COLUMN_DESCRIPTOR__LABEL = 3;

	/**
	 * The feature id for the '<em><b>Greyed</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int COLUMN_DESCRIPTOR__GREYED = 4;

	/**
	 * The feature id for the '<em><b>Unit</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int COLUMN_DESCRIPTOR__UNIT = 5;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int COLUMN_DESCRIPTOR__PARENT = 6;

	/**
	 * The feature id for the '<em><b>Height</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int COLUMN_DESCRIPTOR__HEIGHT = 7;

	/**
	 * The feature id for the '<em><b>Merge Common Values</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int COLUMN_DESCRIPTOR__MERGE_COMMON_VALUES = 8;

	/**
	 * The feature id for the '<em><b>Column Position</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int COLUMN_DESCRIPTOR__COLUMN_POSITION = 9;

	/**
	 * The number of structural features of the '<em>Column Descriptor</em>'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int COLUMN_DESCRIPTOR_FEATURE_COUNT = 10;

	/**
	 * The number of operations of the '<em>Column Descriptor</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int COLUMN_DESCRIPTOR_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.set.model.tablemodel.impl.TableContentImpl <em>Table
	 * Content</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.set.model.tablemodel.impl.TableContentImpl
	 * @see org.eclipse.set.model.tablemodel.impl.TablemodelPackageImpl#getTableContent()
	 * @generated
	 */
	int TABLE_CONTENT = 2;

	/**
	 * The feature id for the '<em><b>Rowgroups</b></em>' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TABLE_CONTENT__ROWGROUPS = 0;

	/**
	 * The number of structural features of the '<em>Table Content</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TABLE_CONTENT_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Table Content</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TABLE_CONTENT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.set.model.tablemodel.impl.RowGroupImpl <em>Row
	 * Group</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.set.model.tablemodel.impl.RowGroupImpl
	 * @see org.eclipse.set.model.tablemodel.impl.TablemodelPackageImpl#getRowGroup()
	 * @generated
	 */
	int ROW_GROUP = 3;

	/**
	 * The feature id for the '<em><b>Rows</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ROW_GROUP__ROWS = 0;

	/**
	 * The feature id for the '<em><b>Leading Object</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ROW_GROUP__LEADING_OBJECT = 1;

	/**
	 * The feature id for the '<em><b>Leading Object Index</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ROW_GROUP__LEADING_OBJECT_INDEX = 2;

	/**
	 * The number of structural features of the '<em>Row Group</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ROW_GROUP_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Row Group</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ROW_GROUP_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.set.model.tablemodel.impl.TableRowImpl <em>Table
	 * Row</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.set.model.tablemodel.impl.TableRowImpl
	 * @see org.eclipse.set.model.tablemodel.impl.TablemodelPackageImpl#getTableRow()
	 * @generated
	 */
	int TABLE_ROW = 4;

	/**
	 * The feature id for the '<em><b>Cells</b></em>' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TABLE_ROW__CELLS = 0;

	/**
	 * The feature id for the '<em><b>Row Index</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TABLE_ROW__ROW_INDEX = 1;

	/**
	 * The feature id for the '<em><b>Footnotes</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TABLE_ROW__FOOTNOTES = 2;

	/**
	 * The feature id for the '<em><b>Row Object</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TABLE_ROW__ROW_OBJECT = 3;

	/**
	 * The number of structural features of the '<em>Table Row</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TABLE_ROW_FEATURE_COUNT = 4;

	/**
	 * The number of operations of the '<em>Table Row</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TABLE_ROW_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.set.model.tablemodel.impl.TableCellImpl <em>Table
	 * Cell</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.set.model.tablemodel.impl.TableCellImpl
	 * @see org.eclipse.set.model.tablemodel.impl.TablemodelPackageImpl#getTableCell()
	 * @generated
	 */
	int TABLE_CELL = 5;

	/**
	 * The feature id for the '<em><b>Content</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TABLE_CELL__CONTENT = 0;

	/**
	 * The feature id for the '<em><b>Columndescriptor</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TABLE_CELL__COLUMNDESCRIPTOR = 1;

	/**
	 * The feature id for the '<em><b>Cellannotation</b></em>' containment
	 * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TABLE_CELL__CELLANNOTATION = 2;

	/**
	 * The number of structural features of the '<em>Table Cell</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TABLE_CELL_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Table Cell</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TABLE_CELL_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.set.model.tablemodel.impl.CellContentImpl <em>Cell
	 * Content</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.set.model.tablemodel.impl.CellContentImpl
	 * @see org.eclipse.set.model.tablemodel.impl.TablemodelPackageImpl#getCellContent()
	 * @generated
	 */
	int CELL_CONTENT = 6;

	/**
	 * The feature id for the '<em><b>Separator</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CELL_CONTENT__SEPARATOR = 0;

	/**
	 * The number of structural features of the '<em>Cell Content</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CELL_CONTENT_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Cell Content</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CELL_CONTENT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.set.model.tablemodel.impl.StringCellContentImpl
	 * <em>String Cell Content</em>}' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see org.eclipse.set.model.tablemodel.impl.StringCellContentImpl
	 * @see org.eclipse.set.model.tablemodel.impl.TablemodelPackageImpl#getStringCellContent()
	 * @generated
	 */
	int STRING_CELL_CONTENT = 7;

	/**
	 * The feature id for the '<em><b>Separator</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int STRING_CELL_CONTENT__SEPARATOR = CELL_CONTENT__SEPARATOR;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int STRING_CELL_CONTENT__VALUE = CELL_CONTENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>String Cell Content</em>'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int STRING_CELL_CONTENT_FEATURE_COUNT = CELL_CONTENT_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>String Cell Content</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int STRING_CELL_CONTENT_OPERATION_COUNT = CELL_CONTENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.set.model.tablemodel.impl.CompareStateCellContentImpl
	 * <em>Compare State Cell Content</em>}' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see org.eclipse.set.model.tablemodel.impl.CompareStateCellContentImpl
	 * @see org.eclipse.set.model.tablemodel.impl.TablemodelPackageImpl#getCompareStateCellContent()
	 * @generated
	 */
	int COMPARE_STATE_CELL_CONTENT = 8;

	/**
	 * The feature id for the '<em><b>Separator</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int COMPARE_STATE_CELL_CONTENT__SEPARATOR = CELL_CONTENT__SEPARATOR;

	/**
	 * The feature id for the '<em><b>Old Value</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int COMPARE_STATE_CELL_CONTENT__OLD_VALUE = CELL_CONTENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>New Value</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int COMPARE_STATE_CELL_CONTENT__NEW_VALUE = CELL_CONTENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Compare State Cell
	 * Content</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int COMPARE_STATE_CELL_CONTENT_FEATURE_COUNT = CELL_CONTENT_FEATURE_COUNT
			+ 2;

	/**
	 * The number of operations of the '<em>Compare State Cell Content</em>'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int COMPARE_STATE_CELL_CONTENT_OPERATION_COUNT = CELL_CONTENT_OPERATION_COUNT
			+ 0;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.set.model.tablemodel.impl.CellAnnotationImpl <em>Cell
	 * Annotation</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.set.model.tablemodel.impl.CellAnnotationImpl
	 * @see org.eclipse.set.model.tablemodel.impl.TablemodelPackageImpl#getCellAnnotation()
	 * @generated
	 */
	int CELL_ANNOTATION = 9;

	/**
	 * The number of structural features of the '<em>Cell Annotation</em>'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CELL_ANNOTATION_FEATURE_COUNT = 0;

	/**
	 * The number of operations of the '<em>Cell Annotation</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CELL_ANNOTATION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.set.model.tablemodel.impl.MultiColorCellContentImpl
	 * <em>Multi Color Cell Content</em>}' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see org.eclipse.set.model.tablemodel.impl.MultiColorCellContentImpl
	 * @see org.eclipse.set.model.tablemodel.impl.TablemodelPackageImpl#getMultiColorCellContent()
	 * @generated
	 */
	int MULTI_COLOR_CELL_CONTENT = 10;

	/**
	 * The feature id for the '<em><b>Separator</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MULTI_COLOR_CELL_CONTENT__SEPARATOR = CELL_CONTENT__SEPARATOR;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MULTI_COLOR_CELL_CONTENT__VALUE = CELL_CONTENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Multi Color Cell
	 * Content</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MULTI_COLOR_CELL_CONTENT_FEATURE_COUNT = CELL_CONTENT_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Multi Color Cell Content</em>'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MULTI_COLOR_CELL_CONTENT_OPERATION_COUNT = CELL_CONTENT_OPERATION_COUNT
			+ 0;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.set.model.tablemodel.impl.MultiColorContentImpl
	 * <em>Multi Color Content</em>}' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see org.eclipse.set.model.tablemodel.impl.MultiColorContentImpl
	 * @see org.eclipse.set.model.tablemodel.impl.TablemodelPackageImpl#getMultiColorContent()
	 * @generated
	 */
	int MULTI_COLOR_CONTENT = 11;

	/**
	 * The feature id for the '<em><b>Multi Color Value</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MULTI_COLOR_CONTENT__MULTI_COLOR_VALUE = 0;

	/**
	 * The feature id for the '<em><b>String Format</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MULTI_COLOR_CONTENT__STRING_FORMAT = 1;

	/**
	 * The feature id for the '<em><b>Disable Multi Color</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MULTI_COLOR_CONTENT__DISABLE_MULTI_COLOR = 2;

	/**
	 * The number of structural features of the '<em>Multi Color Content</em>'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MULTI_COLOR_CONTENT_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Multi Color Content</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MULTI_COLOR_CONTENT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.set.model.tablemodel.impl.FootnoteContainerImpl
	 * <em>Footnote Container</em>}' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see org.eclipse.set.model.tablemodel.impl.FootnoteContainerImpl
	 * @see org.eclipse.set.model.tablemodel.impl.TablemodelPackageImpl#getFootnoteContainer()
	 * @generated
	 */
	int FOOTNOTE_CONTAINER = 12;

	/**
	 * The number of structural features of the '<em>Footnote Container</em>'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FOOTNOTE_CONTAINER_FEATURE_COUNT = 0;

	/**
	 * The number of operations of the '<em>Footnote Container</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FOOTNOTE_CONTAINER_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.set.model.tablemodel.impl.CompareFootnoteContainerImpl
	 * <em>Compare Footnote Container</em>}' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see org.eclipse.set.model.tablemodel.impl.CompareFootnoteContainerImpl
	 * @see org.eclipse.set.model.tablemodel.impl.TablemodelPackageImpl#getCompareFootnoteContainer()
	 * @generated
	 */
	int COMPARE_FOOTNOTE_CONTAINER = 13;

	/**
	 * The feature id for the '<em><b>Old Footnotes</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int COMPARE_FOOTNOTE_CONTAINER__OLD_FOOTNOTES = FOOTNOTE_CONTAINER_FEATURE_COUNT
			+ 0;

	/**
	 * The feature id for the '<em><b>New Footnotes</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int COMPARE_FOOTNOTE_CONTAINER__NEW_FOOTNOTES = FOOTNOTE_CONTAINER_FEATURE_COUNT
			+ 1;

	/**
	 * The feature id for the '<em><b>Unchanged Footnotes</b></em>' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int COMPARE_FOOTNOTE_CONTAINER__UNCHANGED_FOOTNOTES = FOOTNOTE_CONTAINER_FEATURE_COUNT
			+ 2;

	/**
	 * The number of structural features of the '<em>Compare Footnote
	 * Container</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int COMPARE_FOOTNOTE_CONTAINER_FEATURE_COUNT = FOOTNOTE_CONTAINER_FEATURE_COUNT
			+ 3;

	/**
	 * The number of operations of the '<em>Compare Footnote Container</em>'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int COMPARE_FOOTNOTE_CONTAINER_OPERATION_COUNT = FOOTNOTE_CONTAINER_OPERATION_COUNT
			+ 0;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.set.model.tablemodel.impl.SimpleFootnoteContainerImpl
	 * <em>Simple Footnote Container</em>}' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see org.eclipse.set.model.tablemodel.impl.SimpleFootnoteContainerImpl
	 * @see org.eclipse.set.model.tablemodel.impl.TablemodelPackageImpl#getSimpleFootnoteContainer()
	 * @generated
	 */
	int SIMPLE_FOOTNOTE_CONTAINER = 14;

	/**
	 * The feature id for the '<em><b>Footnotes</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SIMPLE_FOOTNOTE_CONTAINER__FOOTNOTES = FOOTNOTE_CONTAINER_FEATURE_COUNT
			+ 0;

	/**
	 * The feature id for the '<em><b>Owner Object</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT = FOOTNOTE_CONTAINER_FEATURE_COUNT
			+ 1;

	/**
	 * The number of structural features of the '<em>Simple Footnote
	 * Container</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SIMPLE_FOOTNOTE_CONTAINER_FEATURE_COUNT = FOOTNOTE_CONTAINER_FEATURE_COUNT
			+ 2;

	/**
	 * The number of operations of the '<em>Simple Footnote Container</em>'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SIMPLE_FOOTNOTE_CONTAINER_OPERATION_COUNT = FOOTNOTE_CONTAINER_OPERATION_COUNT
			+ 0;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.set.model.tablemodel.impl.CompareTableCellContentImpl
	 * <em>Compare Table Cell Content</em>}' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see org.eclipse.set.model.tablemodel.impl.CompareTableCellContentImpl
	 * @see org.eclipse.set.model.tablemodel.impl.TablemodelPackageImpl#getCompareTableCellContent()
	 * @generated
	 */
	int COMPARE_TABLE_CELL_CONTENT = 15;

	/**
	 * The feature id for the '<em><b>Separator</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int COMPARE_TABLE_CELL_CONTENT__SEPARATOR = CELL_CONTENT__SEPARATOR;

	/**
	 * The feature id for the '<em><b>Main Plan Cell Content</b></em>'
	 * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int COMPARE_TABLE_CELL_CONTENT__MAIN_PLAN_CELL_CONTENT = CELL_CONTENT_FEATURE_COUNT
			+ 0;

	/**
	 * The feature id for the '<em><b>Compare Plan Cell Content</b></em>'
	 * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int COMPARE_TABLE_CELL_CONTENT__COMPARE_PLAN_CELL_CONTENT = CELL_CONTENT_FEATURE_COUNT
			+ 1;

	/**
	 * The number of structural features of the '<em>Compare Table Cell
	 * Content</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int COMPARE_TABLE_CELL_CONTENT_FEATURE_COUNT = CELL_CONTENT_FEATURE_COUNT
			+ 2;

	/**
	 * The number of operations of the '<em>Compare Table Cell Content</em>'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int COMPARE_TABLE_CELL_CONTENT_OPERATION_COUNT = CELL_CONTENT_OPERATION_COUNT
			+ 0;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.set.model.tablemodel.impl.CompareTableFootnoteContainerImpl
	 * <em>Compare Table Footnote Container</em>}' class. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.set.model.tablemodel.impl.CompareTableFootnoteContainerImpl
	 * @see org.eclipse.set.model.tablemodel.impl.TablemodelPackageImpl#getCompareTableFootnoteContainer()
	 * @generated
	 */
	int COMPARE_TABLE_FOOTNOTE_CONTAINER = 16;

	/**
	 * The feature id for the '<em><b>Main Plan Footnote Container</b></em>'
	 * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int COMPARE_TABLE_FOOTNOTE_CONTAINER__MAIN_PLAN_FOOTNOTE_CONTAINER = FOOTNOTE_CONTAINER_FEATURE_COUNT
			+ 0;

	/**
	 * The feature id for the '<em><b>Compare Plan Footnote Container</b></em>'
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int COMPARE_TABLE_FOOTNOTE_CONTAINER__COMPARE_PLAN_FOOTNOTE_CONTAINER = FOOTNOTE_CONTAINER_FEATURE_COUNT
			+ 1;

	/**
	 * The number of structural features of the '<em>Compare Table Footnote
	 * Container</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int COMPARE_TABLE_FOOTNOTE_CONTAINER_FEATURE_COUNT = FOOTNOTE_CONTAINER_FEATURE_COUNT
			+ 2;

	/**
	 * The number of operations of the '<em>Compare Table Footnote
	 * Container</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int COMPARE_TABLE_FOOTNOTE_CONTAINER_OPERATION_COUNT = FOOTNOTE_CONTAINER_OPERATION_COUNT
			+ 0;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.set.model.tablemodel.impl.PlanCompareRowImpl <em>Plan
	 * Compare Row</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.set.model.tablemodel.impl.PlanCompareRowImpl
	 * @see org.eclipse.set.model.tablemodel.impl.TablemodelPackageImpl#getPlanCompareRow()
	 * @generated
	 */
	int PLAN_COMPARE_ROW = 17;

	/**
	 * The feature id for the '<em><b>Cells</b></em>' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PLAN_COMPARE_ROW__CELLS = TABLE_ROW__CELLS;

	/**
	 * The feature id for the '<em><b>Row Index</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PLAN_COMPARE_ROW__ROW_INDEX = TABLE_ROW__ROW_INDEX;

	/**
	 * The feature id for the '<em><b>Footnotes</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PLAN_COMPARE_ROW__FOOTNOTES = TABLE_ROW__FOOTNOTES;

	/**
	 * The feature id for the '<em><b>Row Object</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PLAN_COMPARE_ROW__ROW_OBJECT = TABLE_ROW__ROW_OBJECT;

	/**
	 * The feature id for the '<em><b>Row Type</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PLAN_COMPARE_ROW__ROW_TYPE = TABLE_ROW_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Plan Compare Row</em>'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PLAN_COMPARE_ROW_FEATURE_COUNT = TABLE_ROW_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Plan Compare Row</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PLAN_COMPARE_ROW_OPERATION_COUNT = TABLE_ROW_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.set.model.tablemodel.ColumnWidthMode <em>Column Width
	 * Mode</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.set.model.tablemodel.ColumnWidthMode
	 * @see org.eclipse.set.model.tablemodel.impl.TablemodelPackageImpl#getColumnWidthMode()
	 * @generated
	 */
	int COLUMN_WIDTH_MODE = 18;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.set.model.tablemodel.RowMergeMode <em>Row Merge
	 * Mode</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.set.model.tablemodel.RowMergeMode
	 * @see org.eclipse.set.model.tablemodel.impl.TablemodelPackageImpl#getRowMergeMode()
	 * @generated
	 */
	int ROW_MERGE_MODE = 19;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.set.model.tablemodel.PlanCompareRowType <em>Plan
	 * Compare Row Type</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see org.eclipse.set.model.tablemodel.PlanCompareRowType
	 * @see org.eclipse.set.model.tablemodel.impl.TablemodelPackageImpl#getPlanCompareRowType()
	 * @generated
	 */
	int PLAN_COMPARE_ROW_TYPE = 20;

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.set.model.tablemodel.Table <em>Table</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Table</em>'.
	 * @see org.eclipse.set.model.tablemodel.Table
	 * @generated
	 */
	EClass getTable();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link org.eclipse.set.model.tablemodel.Table#getColumndescriptors
	 * <em>Columndescriptors</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return the meta object for the containment reference list
	 *         '<em>Columndescriptors</em>'.
	 * @see org.eclipse.set.model.tablemodel.Table#getColumndescriptors()
	 * @see #getTable()
	 * @generated
	 */
	EReference getTable_Columndescriptors();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.eclipse.set.model.tablemodel.Table#getTablecontent
	 * <em>Tablecontent</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference
	 *         '<em>Tablecontent</em>'.
	 * @see org.eclipse.set.model.tablemodel.Table#getTablecontent()
	 * @see #getTable()
	 * @generated
	 */
	EReference getTable_Tablecontent();

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.set.model.tablemodel.ColumnDescriptor <em>Column
	 * Descriptor</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Column Descriptor</em>'.
	 * @see org.eclipse.set.model.tablemodel.ColumnDescriptor
	 * @generated
	 */
	EClass getColumnDescriptor();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.set.model.tablemodel.ColumnDescriptor#getWidth
	 * <em>Width</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Width</em>'.
	 * @see org.eclipse.set.model.tablemodel.ColumnDescriptor#getWidth()
	 * @see #getColumnDescriptor()
	 * @generated
	 */
	EAttribute getColumnDescriptor_Width();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.set.model.tablemodel.ColumnDescriptor#getWidthMode
	 * <em>Width Mode</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Width Mode</em>'.
	 * @see org.eclipse.set.model.tablemodel.ColumnDescriptor#getWidthMode()
	 * @see #getColumnDescriptor()
	 * @generated
	 */
	EAttribute getColumnDescriptor_WidthMode();

	/**
	 * Returns the meta object for the reference list
	 * '{@link org.eclipse.set.model.tablemodel.ColumnDescriptor#getChildren
	 * <em>Children</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference list '<em>Children</em>'.
	 * @see org.eclipse.set.model.tablemodel.ColumnDescriptor#getChildren()
	 * @see #getColumnDescriptor()
	 * @generated
	 */
	EReference getColumnDescriptor_Children();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.set.model.tablemodel.ColumnDescriptor#getLabel
	 * <em>Label</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Label</em>'.
	 * @see org.eclipse.set.model.tablemodel.ColumnDescriptor#getLabel()
	 * @see #getColumnDescriptor()
	 * @generated
	 */
	EAttribute getColumnDescriptor_Label();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.set.model.tablemodel.ColumnDescriptor#isGreyed
	 * <em>Greyed</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Greyed</em>'.
	 * @see org.eclipse.set.model.tablemodel.ColumnDescriptor#isGreyed()
	 * @see #getColumnDescriptor()
	 * @generated
	 */
	EAttribute getColumnDescriptor_Greyed();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.set.model.tablemodel.ColumnDescriptor#isUnit
	 * <em>Unit</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Unit</em>'.
	 * @see org.eclipse.set.model.tablemodel.ColumnDescriptor#isUnit()
	 * @see #getColumnDescriptor()
	 * @generated
	 */
	EAttribute getColumnDescriptor_Unit();

	/**
	 * Returns the meta object for the reference
	 * '{@link org.eclipse.set.model.tablemodel.ColumnDescriptor#getParent
	 * <em>Parent</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Parent</em>'.
	 * @see org.eclipse.set.model.tablemodel.ColumnDescriptor#getParent()
	 * @see #getColumnDescriptor()
	 * @generated
	 */
	EReference getColumnDescriptor_Parent();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.set.model.tablemodel.ColumnDescriptor#getHeight
	 * <em>Height</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Height</em>'.
	 * @see org.eclipse.set.model.tablemodel.ColumnDescriptor#getHeight()
	 * @see #getColumnDescriptor()
	 * @generated
	 */
	EAttribute getColumnDescriptor_Height();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.set.model.tablemodel.ColumnDescriptor#getMergeCommonValues
	 * <em>Merge Common Values</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return the meta object for the attribute '<em>Merge Common Values</em>'.
	 * @see org.eclipse.set.model.tablemodel.ColumnDescriptor#getMergeCommonValues()
	 * @see #getColumnDescriptor()
	 * @generated
	 */
	EAttribute getColumnDescriptor_MergeCommonValues();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.set.model.tablemodel.ColumnDescriptor#getColumnPosition
	 * <em>Column Position</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Column Position</em>'.
	 * @see org.eclipse.set.model.tablemodel.ColumnDescriptor#getColumnPosition()
	 * @see #getColumnDescriptor()
	 * @generated
	 */
	EAttribute getColumnDescriptor_ColumnPosition();

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.set.model.tablemodel.TableContent <em>Table
	 * Content</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Table Content</em>'.
	 * @see org.eclipse.set.model.tablemodel.TableContent
	 * @generated
	 */
	EClass getTableContent();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link org.eclipse.set.model.tablemodel.TableContent#getRowgroups
	 * <em>Rowgroups</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list
	 *         '<em>Rowgroups</em>'.
	 * @see org.eclipse.set.model.tablemodel.TableContent#getRowgroups()
	 * @see #getTableContent()
	 * @generated
	 */
	EReference getTableContent_Rowgroups();

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.set.model.tablemodel.RowGroup <em>Row Group</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Row Group</em>'.
	 * @see org.eclipse.set.model.tablemodel.RowGroup
	 * @generated
	 */
	EClass getRowGroup();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link org.eclipse.set.model.tablemodel.RowGroup#getRows
	 * <em>Rows</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list
	 *         '<em>Rows</em>'.
	 * @see org.eclipse.set.model.tablemodel.RowGroup#getRows()
	 * @see #getRowGroup()
	 * @generated
	 */
	EReference getRowGroup_Rows();

	/**
	 * Returns the meta object for the reference
	 * '{@link org.eclipse.set.model.tablemodel.RowGroup#getLeadingObject
	 * <em>Leading Object</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Leading Object</em>'.
	 * @see org.eclipse.set.model.tablemodel.RowGroup#getLeadingObject()
	 * @see #getRowGroup()
	 * @generated
	 */
	EReference getRowGroup_LeadingObject();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.set.model.tablemodel.RowGroup#getLeadingObjectIndex
	 * <em>Leading Object Index</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Leading Object
	 *         Index</em>'.
	 * @see org.eclipse.set.model.tablemodel.RowGroup#getLeadingObjectIndex()
	 * @see #getRowGroup()
	 * @generated
	 */
	EAttribute getRowGroup_LeadingObjectIndex();

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.set.model.tablemodel.TableRow <em>Table Row</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Table Row</em>'.
	 * @see org.eclipse.set.model.tablemodel.TableRow
	 * @generated
	 */
	EClass getTableRow();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link org.eclipse.set.model.tablemodel.TableRow#getCells
	 * <em>Cells</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list
	 *         '<em>Cells</em>'.
	 * @see org.eclipse.set.model.tablemodel.TableRow#getCells()
	 * @see #getTableRow()
	 * @generated
	 */
	EReference getTableRow_Cells();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.eclipse.set.model.tablemodel.TableRow#getFootnotes
	 * <em>Footnotes</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference
	 *         '<em>Footnotes</em>'.
	 * @see org.eclipse.set.model.tablemodel.TableRow#getFootnotes()
	 * @see #getTableRow()
	 * @generated
	 */
	EReference getTableRow_Footnotes();

	/**
	 * Returns the meta object for the reference
	 * '{@link org.eclipse.set.model.tablemodel.TableRow#getRowObject <em>Row
	 * Object</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Row Object</em>'.
	 * @see org.eclipse.set.model.tablemodel.TableRow#getRowObject()
	 * @see #getTableRow()
	 * @generated
	 */
	EReference getTableRow_RowObject();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.set.model.tablemodel.TableRow#getRowIndex <em>Row
	 * Index</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Row Index</em>'.
	 * @see org.eclipse.set.model.tablemodel.TableRow#getRowIndex()
	 * @see #getTableRow()
	 * @generated
	 */
	EAttribute getTableRow_RowIndex();

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.set.model.tablemodel.TableCell <em>Table Cell</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Table Cell</em>'.
	 * @see org.eclipse.set.model.tablemodel.TableCell
	 * @generated
	 */
	EClass getTableCell();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.eclipse.set.model.tablemodel.TableCell#getContent
	 * <em>Content</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Content</em>'.
	 * @see org.eclipse.set.model.tablemodel.TableCell#getContent()
	 * @see #getTableCell()
	 * @generated
	 */
	EReference getTableCell_Content();

	/**
	 * Returns the meta object for the reference
	 * '{@link org.eclipse.set.model.tablemodel.TableCell#getColumndescriptor
	 * <em>Columndescriptor</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return the meta object for the reference '<em>Columndescriptor</em>'.
	 * @see org.eclipse.set.model.tablemodel.TableCell#getColumndescriptor()
	 * @see #getTableCell()
	 * @generated
	 */
	EReference getTableCell_Columndescriptor();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link org.eclipse.set.model.tablemodel.TableCell#getCellannotation
	 * <em>Cellannotation</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list
	 *         '<em>Cellannotation</em>'.
	 * @see org.eclipse.set.model.tablemodel.TableCell#getCellannotation()
	 * @see #getTableCell()
	 * @generated
	 */
	EReference getTableCell_Cellannotation();

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.set.model.tablemodel.CellContent <em>Cell
	 * Content</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Cell Content</em>'.
	 * @see org.eclipse.set.model.tablemodel.CellContent
	 * @generated
	 */
	EClass getCellContent();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.set.model.tablemodel.CellContent#getSeparator
	 * <em>Separator</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Separator</em>'.
	 * @see org.eclipse.set.model.tablemodel.CellContent#getSeparator()
	 * @see #getCellContent()
	 * @generated
	 */
	EAttribute getCellContent_Separator();

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.set.model.tablemodel.StringCellContent <em>String
	 * Cell Content</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>String Cell Content</em>'.
	 * @see org.eclipse.set.model.tablemodel.StringCellContent
	 * @generated
	 */
	EClass getStringCellContent();

	/**
	 * Returns the meta object for the attribute list
	 * '{@link org.eclipse.set.model.tablemodel.StringCellContent#getValue
	 * <em>Value</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute list '<em>Value</em>'.
	 * @see org.eclipse.set.model.tablemodel.StringCellContent#getValue()
	 * @see #getStringCellContent()
	 * @generated
	 */
	EAttribute getStringCellContent_Value();

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.set.model.tablemodel.CompareStateCellContent
	 * <em>Compare State Cell Content</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Compare State Cell Content</em>'.
	 * @see org.eclipse.set.model.tablemodel.CompareStateCellContent
	 * @generated
	 */
	EClass getCompareStateCellContent();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.eclipse.set.model.tablemodel.CompareStateCellContent#getOldValue
	 * <em>Old Value</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Old
	 *         Value</em>'.
	 * @see org.eclipse.set.model.tablemodel.CompareStateCellContent#getOldValue()
	 * @see #getCompareStateCellContent()
	 * @generated
	 */
	EReference getCompareStateCellContent_OldValue();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.eclipse.set.model.tablemodel.CompareStateCellContent#getNewValue
	 * <em>New Value</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>New
	 *         Value</em>'.
	 * @see org.eclipse.set.model.tablemodel.CompareStateCellContent#getNewValue()
	 * @see #getCompareStateCellContent()
	 * @generated
	 */
	EReference getCompareStateCellContent_NewValue();

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.set.model.tablemodel.CellAnnotation <em>Cell
	 * Annotation</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Cell Annotation</em>'.
	 * @see org.eclipse.set.model.tablemodel.CellAnnotation
	 * @generated
	 */
	EClass getCellAnnotation();

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.set.model.tablemodel.MultiColorCellContent <em>Multi
	 * Color Cell Content</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Multi Color Cell Content</em>'.
	 * @see org.eclipse.set.model.tablemodel.MultiColorCellContent
	 * @generated
	 */
	EClass getMultiColorCellContent();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link org.eclipse.set.model.tablemodel.MultiColorCellContent#getValue
	 * <em>Value</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list
	 *         '<em>Value</em>'.
	 * @see org.eclipse.set.model.tablemodel.MultiColorCellContent#getValue()
	 * @see #getMultiColorCellContent()
	 * @generated
	 */
	EReference getMultiColorCellContent_Value();

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.set.model.tablemodel.MultiColorContent <em>Multi
	 * Color Content</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Multi Color Content</em>'.
	 * @see org.eclipse.set.model.tablemodel.MultiColorContent
	 * @generated
	 */
	EClass getMultiColorContent();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.set.model.tablemodel.MultiColorContent#getMultiColorValue
	 * <em>Multi Color Value</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return the meta object for the attribute '<em>Multi Color Value</em>'.
	 * @see org.eclipse.set.model.tablemodel.MultiColorContent#getMultiColorValue()
	 * @see #getMultiColorContent()
	 * @generated
	 */
	EAttribute getMultiColorContent_MultiColorValue();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.set.model.tablemodel.MultiColorContent#getStringFormat
	 * <em>String Format</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>String Format</em>'.
	 * @see org.eclipse.set.model.tablemodel.MultiColorContent#getStringFormat()
	 * @see #getMultiColorContent()
	 * @generated
	 */
	EAttribute getMultiColorContent_StringFormat();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.set.model.tablemodel.MultiColorContent#isDisableMultiColor
	 * <em>Disable Multi Color</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return the meta object for the attribute '<em>Disable Multi Color</em>'.
	 * @see org.eclipse.set.model.tablemodel.MultiColorContent#isDisableMultiColor()
	 * @see #getMultiColorContent()
	 * @generated
	 */
	EAttribute getMultiColorContent_DisableMultiColor();

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.set.model.tablemodel.FootnoteContainer <em>Footnote
	 * Container</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Footnote Container</em>'.
	 * @see org.eclipse.set.model.tablemodel.FootnoteContainer
	 * @generated
	 */
	EClass getFootnoteContainer();

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.set.model.tablemodel.CompareFootnoteContainer
	 * <em>Compare Footnote Container</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Compare Footnote Container</em>'.
	 * @see org.eclipse.set.model.tablemodel.CompareFootnoteContainer
	 * @generated
	 */
	EClass getCompareFootnoteContainer();

	/**
	 * Returns the meta object for the reference
	 * '{@link org.eclipse.set.model.tablemodel.CompareFootnoteContainer#getOldFootnotes
	 * <em>Old Footnotes</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Old Footnotes</em>'.
	 * @see org.eclipse.set.model.tablemodel.CompareFootnoteContainer#getOldFootnotes()
	 * @see #getCompareFootnoteContainer()
	 * @generated
	 */
	EReference getCompareFootnoteContainer_OldFootnotes();

	/**
	 * Returns the meta object for the reference
	 * '{@link org.eclipse.set.model.tablemodel.CompareFootnoteContainer#getNewFootnotes
	 * <em>New Footnotes</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>New Footnotes</em>'.
	 * @see org.eclipse.set.model.tablemodel.CompareFootnoteContainer#getNewFootnotes()
	 * @see #getCompareFootnoteContainer()
	 * @generated
	 */
	EReference getCompareFootnoteContainer_NewFootnotes();

	/**
	 * Returns the meta object for the reference list
	 * '{@link org.eclipse.set.model.tablemodel.CompareFootnoteContainer#getUnchangedFootnotes
	 * <em>Unchanged Footnotes</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return the meta object for the reference list '<em>Unchanged
	 *         Footnotes</em>'.
	 * @see org.eclipse.set.model.tablemodel.CompareFootnoteContainer#getUnchangedFootnotes()
	 * @see #getCompareFootnoteContainer()
	 * @generated
	 */
	EReference getCompareFootnoteContainer_UnchangedFootnotes();

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.set.model.tablemodel.SimpleFootnoteContainer
	 * <em>Simple Footnote Container</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Simple Footnote Container</em>'.
	 * @see org.eclipse.set.model.tablemodel.SimpleFootnoteContainer
	 * @generated
	 */
	EClass getSimpleFootnoteContainer();

	/**
	 * Returns the meta object for the reference list
	 * '{@link org.eclipse.set.model.tablemodel.SimpleFootnoteContainer#getFootnotes
	 * <em>Footnotes</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference list '<em>Footnotes</em>'.
	 * @see org.eclipse.set.model.tablemodel.SimpleFootnoteContainer#getFootnotes()
	 * @see #getSimpleFootnoteContainer()
	 * @generated
	 */
	EReference getSimpleFootnoteContainer_Footnotes();

	/**
	 * Returns the meta object for the reference
	 * '{@link org.eclipse.set.model.tablemodel.SimpleFootnoteContainer#getOwnerObject
	 * <em>Owner Object</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Owner Object</em>'.
	 * @see org.eclipse.set.model.tablemodel.SimpleFootnoteContainer#getOwnerObject()
	 * @see #getSimpleFootnoteContainer()
	 * @generated
	 */
	EReference getSimpleFootnoteContainer_OwnerObject();

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.set.model.tablemodel.CompareTableCellContent
	 * <em>Compare Table Cell Content</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Compare Table Cell Content</em>'.
	 * @see org.eclipse.set.model.tablemodel.CompareTableCellContent
	 * @generated
	 */
	EClass getCompareTableCellContent();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.eclipse.set.model.tablemodel.CompareTableCellContent#getMainPlanCellContent
	 * <em>Main Plan Cell Content</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Main Plan Cell
	 *         Content</em>'.
	 * @see org.eclipse.set.model.tablemodel.CompareTableCellContent#getMainPlanCellContent()
	 * @see #getCompareTableCellContent()
	 * @generated
	 */
	EReference getCompareTableCellContent_MainPlanCellContent();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.eclipse.set.model.tablemodel.CompareTableCellContent#getComparePlanCellContent
	 * <em>Compare Plan Cell Content</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Compare Plan
	 *         Cell Content</em>'.
	 * @see org.eclipse.set.model.tablemodel.CompareTableCellContent#getComparePlanCellContent()
	 * @see #getCompareTableCellContent()
	 * @generated
	 */
	EReference getCompareTableCellContent_ComparePlanCellContent();

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.set.model.tablemodel.CompareTableFootnoteContainer
	 * <em>Compare Table Footnote Container</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Compare Table Footnote
	 *         Container</em>'.
	 * @see org.eclipse.set.model.tablemodel.CompareTableFootnoteContainer
	 * @generated
	 */
	EClass getCompareTableFootnoteContainer();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.eclipse.set.model.tablemodel.CompareTableFootnoteContainer#getMainPlanFootnoteContainer
	 * <em>Main Plan Footnote Container</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Main Plan
	 *         Footnote Container</em>'.
	 * @see org.eclipse.set.model.tablemodel.CompareTableFootnoteContainer#getMainPlanFootnoteContainer()
	 * @see #getCompareTableFootnoteContainer()
	 * @generated
	 */
	EReference getCompareTableFootnoteContainer_MainPlanFootnoteContainer();

	/**
	 * Returns the meta object for the reference
	 * '{@link org.eclipse.set.model.tablemodel.CompareTableFootnoteContainer#getComparePlanFootnoteContainer
	 * <em>Compare Plan Footnote Container</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Compare Plan Footnote
	 *         Container</em>'.
	 * @see org.eclipse.set.model.tablemodel.CompareTableFootnoteContainer#getComparePlanFootnoteContainer()
	 * @see #getCompareTableFootnoteContainer()
	 * @generated
	 */
	EReference getCompareTableFootnoteContainer_ComparePlanFootnoteContainer();

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.set.model.tablemodel.PlanCompareRow <em>Plan Compare
	 * Row</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Plan Compare Row</em>'.
	 * @see org.eclipse.set.model.tablemodel.PlanCompareRow
	 * @generated
	 */
	EClass getPlanCompareRow();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.set.model.tablemodel.PlanCompareRow#getRowType
	 * <em>Row Type</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Row Type</em>'.
	 * @see org.eclipse.set.model.tablemodel.PlanCompareRow#getRowType()
	 * @see #getPlanCompareRow()
	 * @generated
	 */
	EAttribute getPlanCompareRow_RowType();

	/**
	 * Returns the meta object for enum
	 * '{@link org.eclipse.set.model.tablemodel.ColumnWidthMode <em>Column Width
	 * Mode</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for enum '<em>Column Width Mode</em>'.
	 * @see org.eclipse.set.model.tablemodel.ColumnWidthMode
	 * @generated
	 */
	EEnum getColumnWidthMode();

	/**
	 * Returns the meta object for enum
	 * '{@link org.eclipse.set.model.tablemodel.RowMergeMode <em>Row Merge
	 * Mode</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for enum '<em>Row Merge Mode</em>'.
	 * @see org.eclipse.set.model.tablemodel.RowMergeMode
	 * @generated
	 */
	EEnum getRowMergeMode();

	/**
	 * Returns the meta object for enum
	 * '{@link org.eclipse.set.model.tablemodel.PlanCompareRowType <em>Plan
	 * Compare Row Type</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for enum '<em>Plan Compare Row Type</em>'.
	 * @see org.eclipse.set.model.tablemodel.PlanCompareRowType
	 * @generated
	 */
	EEnum getPlanCompareRowType();

	/**
	 * Returns the factory that creates the instances of the model. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	TablemodelFactory getTablemodelFactory();

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
		 * '{@link org.eclipse.set.model.tablemodel.impl.TableImpl
		 * <em>Table</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.set.model.tablemodel.impl.TableImpl
		 * @see org.eclipse.set.model.tablemodel.impl.TablemodelPackageImpl#getTable()
		 * @generated
		 */
		EClass TABLE = eINSTANCE.getTable();

		/**
		 * The meta object literal for the '<em><b>Columndescriptors</b></em>'
		 * containment reference list feature. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TABLE__COLUMNDESCRIPTORS = eINSTANCE
				.getTable_Columndescriptors();

		/**
		 * The meta object literal for the '<em><b>Tablecontent</b></em>'
		 * containment reference feature. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TABLE__TABLECONTENT = eINSTANCE.getTable_Tablecontent();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.set.model.tablemodel.impl.ColumnDescriptorImpl
		 * <em>Column Descriptor</em>}' class. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @see org.eclipse.set.model.tablemodel.impl.ColumnDescriptorImpl
		 * @see org.eclipse.set.model.tablemodel.impl.TablemodelPackageImpl#getColumnDescriptor()
		 * @generated
		 */
		EClass COLUMN_DESCRIPTOR = eINSTANCE.getColumnDescriptor();

		/**
		 * The meta object literal for the '<em><b>Width</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute COLUMN_DESCRIPTOR__WIDTH = eINSTANCE
				.getColumnDescriptor_Width();

		/**
		 * The meta object literal for the '<em><b>Width Mode</b></em>'
		 * attribute feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute COLUMN_DESCRIPTOR__WIDTH_MODE = eINSTANCE
				.getColumnDescriptor_WidthMode();

		/**
		 * The meta object literal for the '<em><b>Children</b></em>' reference
		 * list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference COLUMN_DESCRIPTOR__CHILDREN = eINSTANCE
				.getColumnDescriptor_Children();

		/**
		 * The meta object literal for the '<em><b>Label</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute COLUMN_DESCRIPTOR__LABEL = eINSTANCE
				.getColumnDescriptor_Label();

		/**
		 * The meta object literal for the '<em><b>Greyed</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute COLUMN_DESCRIPTOR__GREYED = eINSTANCE
				.getColumnDescriptor_Greyed();

		/**
		 * The meta object literal for the '<em><b>Unit</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute COLUMN_DESCRIPTOR__UNIT = eINSTANCE
				.getColumnDescriptor_Unit();

		/**
		 * The meta object literal for the '<em><b>Parent</b></em>' reference
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference COLUMN_DESCRIPTOR__PARENT = eINSTANCE
				.getColumnDescriptor_Parent();

		/**
		 * The meta object literal for the '<em><b>Height</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute COLUMN_DESCRIPTOR__HEIGHT = eINSTANCE
				.getColumnDescriptor_Height();

		/**
		 * The meta object literal for the '<em><b>Merge Common Values</b></em>'
		 * attribute feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute COLUMN_DESCRIPTOR__MERGE_COMMON_VALUES = eINSTANCE
				.getColumnDescriptor_MergeCommonValues();

		/**
		 * The meta object literal for the '<em><b>Column Position</b></em>'
		 * attribute feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute COLUMN_DESCRIPTOR__COLUMN_POSITION = eINSTANCE
				.getColumnDescriptor_ColumnPosition();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.set.model.tablemodel.impl.TableContentImpl
		 * <em>Table Content</em>}' class. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @see org.eclipse.set.model.tablemodel.impl.TableContentImpl
		 * @see org.eclipse.set.model.tablemodel.impl.TablemodelPackageImpl#getTableContent()
		 * @generated
		 */
		EClass TABLE_CONTENT = eINSTANCE.getTableContent();

		/**
		 * The meta object literal for the '<em><b>Rowgroups</b></em>'
		 * containment reference list feature. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TABLE_CONTENT__ROWGROUPS = eINSTANCE
				.getTableContent_Rowgroups();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.set.model.tablemodel.impl.RowGroupImpl <em>Row
		 * Group</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.set.model.tablemodel.impl.RowGroupImpl
		 * @see org.eclipse.set.model.tablemodel.impl.TablemodelPackageImpl#getRowGroup()
		 * @generated
		 */
		EClass ROW_GROUP = eINSTANCE.getRowGroup();

		/**
		 * The meta object literal for the '<em><b>Rows</b></em>' containment
		 * reference list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference ROW_GROUP__ROWS = eINSTANCE.getRowGroup_Rows();

		/**
		 * The meta object literal for the '<em><b>Leading Object</b></em>'
		 * reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference ROW_GROUP__LEADING_OBJECT = eINSTANCE
				.getRowGroup_LeadingObject();

		/**
		 * The meta object literal for the '<em><b>Leading Object
		 * Index</b></em>' attribute feature. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ROW_GROUP__LEADING_OBJECT_INDEX = eINSTANCE
				.getRowGroup_LeadingObjectIndex();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.set.model.tablemodel.impl.TableRowImpl <em>Table
		 * Row</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.set.model.tablemodel.impl.TableRowImpl
		 * @see org.eclipse.set.model.tablemodel.impl.TablemodelPackageImpl#getTableRow()
		 * @generated
		 */
		EClass TABLE_ROW = eINSTANCE.getTableRow();

		/**
		 * The meta object literal for the '<em><b>Cells</b></em>' containment
		 * reference list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TABLE_ROW__CELLS = eINSTANCE.getTableRow_Cells();

		/**
		 * The meta object literal for the '<em><b>Footnotes</b></em>'
		 * containment reference feature. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TABLE_ROW__FOOTNOTES = eINSTANCE.getTableRow_Footnotes();

		/**
		 * The meta object literal for the '<em><b>Row Object</b></em>'
		 * reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TABLE_ROW__ROW_OBJECT = eINSTANCE.getTableRow_RowObject();

		/**
		 * The meta object literal for the '<em><b>Row Index</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TABLE_ROW__ROW_INDEX = eINSTANCE.getTableRow_RowIndex();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.set.model.tablemodel.impl.TableCellImpl <em>Table
		 * Cell</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.set.model.tablemodel.impl.TableCellImpl
		 * @see org.eclipse.set.model.tablemodel.impl.TablemodelPackageImpl#getTableCell()
		 * @generated
		 */
		EClass TABLE_CELL = eINSTANCE.getTableCell();

		/**
		 * The meta object literal for the '<em><b>Content</b></em>' containment
		 * reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TABLE_CELL__CONTENT = eINSTANCE.getTableCell_Content();

		/**
		 * The meta object literal for the '<em><b>Columndescriptor</b></em>'
		 * reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TABLE_CELL__COLUMNDESCRIPTOR = eINSTANCE
				.getTableCell_Columndescriptor();

		/**
		 * The meta object literal for the '<em><b>Cellannotation</b></em>'
		 * containment reference list feature. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TABLE_CELL__CELLANNOTATION = eINSTANCE
				.getTableCell_Cellannotation();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.set.model.tablemodel.impl.CellContentImpl
		 * <em>Cell Content</em>}' class. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @see org.eclipse.set.model.tablemodel.impl.CellContentImpl
		 * @see org.eclipse.set.model.tablemodel.impl.TablemodelPackageImpl#getCellContent()
		 * @generated
		 */
		EClass CELL_CONTENT = eINSTANCE.getCellContent();

		/**
		 * The meta object literal for the '<em><b>Separator</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute CELL_CONTENT__SEPARATOR = eINSTANCE
				.getCellContent_Separator();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.set.model.tablemodel.impl.StringCellContentImpl
		 * <em>String Cell Content</em>}' class. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @see org.eclipse.set.model.tablemodel.impl.StringCellContentImpl
		 * @see org.eclipse.set.model.tablemodel.impl.TablemodelPackageImpl#getStringCellContent()
		 * @generated
		 */
		EClass STRING_CELL_CONTENT = eINSTANCE.getStringCellContent();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute
		 * list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute STRING_CELL_CONTENT__VALUE = eINSTANCE
				.getStringCellContent_Value();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.set.model.tablemodel.impl.CompareStateCellContentImpl
		 * <em>Compare State Cell Content</em>}' class. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.set.model.tablemodel.impl.CompareStateCellContentImpl
		 * @see org.eclipse.set.model.tablemodel.impl.TablemodelPackageImpl#getCompareStateCellContent()
		 * @generated
		 */
		EClass COMPARE_STATE_CELL_CONTENT = eINSTANCE
				.getCompareStateCellContent();

		/**
		 * The meta object literal for the '<em><b>Old Value</b></em>'
		 * containment reference feature. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EReference COMPARE_STATE_CELL_CONTENT__OLD_VALUE = eINSTANCE
				.getCompareStateCellContent_OldValue();

		/**
		 * The meta object literal for the '<em><b>New Value</b></em>'
		 * containment reference feature. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EReference COMPARE_STATE_CELL_CONTENT__NEW_VALUE = eINSTANCE
				.getCompareStateCellContent_NewValue();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.set.model.tablemodel.impl.CellAnnotationImpl
		 * <em>Cell Annotation</em>}' class. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @see org.eclipse.set.model.tablemodel.impl.CellAnnotationImpl
		 * @see org.eclipse.set.model.tablemodel.impl.TablemodelPackageImpl#getCellAnnotation()
		 * @generated
		 */
		EClass CELL_ANNOTATION = eINSTANCE.getCellAnnotation();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.set.model.tablemodel.impl.MultiColorCellContentImpl
		 * <em>Multi Color Cell Content</em>}' class. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.set.model.tablemodel.impl.MultiColorCellContentImpl
		 * @see org.eclipse.set.model.tablemodel.impl.TablemodelPackageImpl#getMultiColorCellContent()
		 * @generated
		 */
		EClass MULTI_COLOR_CELL_CONTENT = eINSTANCE.getMultiColorCellContent();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' containment
		 * reference list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference MULTI_COLOR_CELL_CONTENT__VALUE = eINSTANCE
				.getMultiColorCellContent_Value();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.set.model.tablemodel.impl.MultiColorContentImpl
		 * <em>Multi Color Content</em>}' class. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @see org.eclipse.set.model.tablemodel.impl.MultiColorContentImpl
		 * @see org.eclipse.set.model.tablemodel.impl.TablemodelPackageImpl#getMultiColorContent()
		 * @generated
		 */
		EClass MULTI_COLOR_CONTENT = eINSTANCE.getMultiColorContent();

		/**
		 * The meta object literal for the '<em><b>Multi Color Value</b></em>'
		 * attribute feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute MULTI_COLOR_CONTENT__MULTI_COLOR_VALUE = eINSTANCE
				.getMultiColorContent_MultiColorValue();

		/**
		 * The meta object literal for the '<em><b>String Format</b></em>'
		 * attribute feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute MULTI_COLOR_CONTENT__STRING_FORMAT = eINSTANCE
				.getMultiColorContent_StringFormat();

		/**
		 * The meta object literal for the '<em><b>Disable Multi Color</b></em>'
		 * attribute feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute MULTI_COLOR_CONTENT__DISABLE_MULTI_COLOR = eINSTANCE
				.getMultiColorContent_DisableMultiColor();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.set.model.tablemodel.impl.FootnoteContainerImpl
		 * <em>Footnote Container</em>}' class. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @see org.eclipse.set.model.tablemodel.impl.FootnoteContainerImpl
		 * @see org.eclipse.set.model.tablemodel.impl.TablemodelPackageImpl#getFootnoteContainer()
		 * @generated
		 */
		EClass FOOTNOTE_CONTAINER = eINSTANCE.getFootnoteContainer();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.set.model.tablemodel.impl.CompareFootnoteContainerImpl
		 * <em>Compare Footnote Container</em>}' class. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.set.model.tablemodel.impl.CompareFootnoteContainerImpl
		 * @see org.eclipse.set.model.tablemodel.impl.TablemodelPackageImpl#getCompareFootnoteContainer()
		 * @generated
		 */
		EClass COMPARE_FOOTNOTE_CONTAINER = eINSTANCE
				.getCompareFootnoteContainer();

		/**
		 * The meta object literal for the '<em><b>Old Footnotes</b></em>'
		 * reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference COMPARE_FOOTNOTE_CONTAINER__OLD_FOOTNOTES = eINSTANCE
				.getCompareFootnoteContainer_OldFootnotes();

		/**
		 * The meta object literal for the '<em><b>New Footnotes</b></em>'
		 * reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference COMPARE_FOOTNOTE_CONTAINER__NEW_FOOTNOTES = eINSTANCE
				.getCompareFootnoteContainer_NewFootnotes();

		/**
		 * The meta object literal for the '<em><b>Unchanged Footnotes</b></em>'
		 * reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference COMPARE_FOOTNOTE_CONTAINER__UNCHANGED_FOOTNOTES = eINSTANCE
				.getCompareFootnoteContainer_UnchangedFootnotes();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.set.model.tablemodel.impl.SimpleFootnoteContainerImpl
		 * <em>Simple Footnote Container</em>}' class. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.set.model.tablemodel.impl.SimpleFootnoteContainerImpl
		 * @see org.eclipse.set.model.tablemodel.impl.TablemodelPackageImpl#getSimpleFootnoteContainer()
		 * @generated
		 */
		EClass SIMPLE_FOOTNOTE_CONTAINER = eINSTANCE
				.getSimpleFootnoteContainer();

		/**
		 * The meta object literal for the '<em><b>Footnotes</b></em>' reference
		 * list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference SIMPLE_FOOTNOTE_CONTAINER__FOOTNOTES = eINSTANCE
				.getSimpleFootnoteContainer_Footnotes();

		/**
		 * The meta object literal for the '<em><b>Owner Object</b></em>'
		 * reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT = eINSTANCE
				.getSimpleFootnoteContainer_OwnerObject();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.set.model.tablemodel.impl.CompareTableCellContentImpl
		 * <em>Compare Table Cell Content</em>}' class. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.set.model.tablemodel.impl.CompareTableCellContentImpl
		 * @see org.eclipse.set.model.tablemodel.impl.TablemodelPackageImpl#getCompareTableCellContent()
		 * @generated
		 */
		EClass COMPARE_TABLE_CELL_CONTENT = eINSTANCE
				.getCompareTableCellContent();

		/**
		 * The meta object literal for the '<em><b>Main Plan Cell
		 * Content</b></em>' containment reference feature. <!-- begin-user-doc
		 * --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference COMPARE_TABLE_CELL_CONTENT__MAIN_PLAN_CELL_CONTENT = eINSTANCE
				.getCompareTableCellContent_MainPlanCellContent();

		/**
		 * The meta object literal for the '<em><b>Compare Plan Cell
		 * Content</b></em>' containment reference feature. <!-- begin-user-doc
		 * --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference COMPARE_TABLE_CELL_CONTENT__COMPARE_PLAN_CELL_CONTENT = eINSTANCE
				.getCompareTableCellContent_ComparePlanCellContent();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.set.model.tablemodel.impl.CompareTableFootnoteContainerImpl
		 * <em>Compare Table Footnote Container</em>}' class. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.set.model.tablemodel.impl.CompareTableFootnoteContainerImpl
		 * @see org.eclipse.set.model.tablemodel.impl.TablemodelPackageImpl#getCompareTableFootnoteContainer()
		 * @generated
		 */
		EClass COMPARE_TABLE_FOOTNOTE_CONTAINER = eINSTANCE
				.getCompareTableFootnoteContainer();

		/**
		 * The meta object literal for the '<em><b>Main Plan Footnote
		 * Container</b></em>' containment reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference COMPARE_TABLE_FOOTNOTE_CONTAINER__MAIN_PLAN_FOOTNOTE_CONTAINER = eINSTANCE
				.getCompareTableFootnoteContainer_MainPlanFootnoteContainer();

		/**
		 * The meta object literal for the '<em><b>Compare Plan Footnote
		 * Container</b></em>' reference feature. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EReference COMPARE_TABLE_FOOTNOTE_CONTAINER__COMPARE_PLAN_FOOTNOTE_CONTAINER = eINSTANCE
				.getCompareTableFootnoteContainer_ComparePlanFootnoteContainer();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.set.model.tablemodel.impl.PlanCompareRowImpl
		 * <em>Plan Compare Row</em>}' class. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @see org.eclipse.set.model.tablemodel.impl.PlanCompareRowImpl
		 * @see org.eclipse.set.model.tablemodel.impl.TablemodelPackageImpl#getPlanCompareRow()
		 * @generated
		 */
		EClass PLAN_COMPARE_ROW = eINSTANCE.getPlanCompareRow();

		/**
		 * The meta object literal for the '<em><b>Row Type</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute PLAN_COMPARE_ROW__ROW_TYPE = eINSTANCE
				.getPlanCompareRow_RowType();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.set.model.tablemodel.ColumnWidthMode <em>Column
		 * Width Mode</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.set.model.tablemodel.ColumnWidthMode
		 * @see org.eclipse.set.model.tablemodel.impl.TablemodelPackageImpl#getColumnWidthMode()
		 * @generated
		 */
		EEnum COLUMN_WIDTH_MODE = eINSTANCE.getColumnWidthMode();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.set.model.tablemodel.RowMergeMode <em>Row Merge
		 * Mode</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.set.model.tablemodel.RowMergeMode
		 * @see org.eclipse.set.model.tablemodel.impl.TablemodelPackageImpl#getRowMergeMode()
		 * @generated
		 */
		EEnum ROW_MERGE_MODE = eINSTANCE.getRowMergeMode();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.set.model.tablemodel.PlanCompareRowType <em>Plan
		 * Compare Row Type</em>}' enum. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @see org.eclipse.set.model.tablemodel.PlanCompareRowType
		 * @see org.eclipse.set.model.tablemodel.impl.TablemodelPackageImpl#getPlanCompareRowType()
		 * @generated
		 */
		EEnum PLAN_COMPARE_ROW_TYPE = eINSTANCE.getPlanCompareRowType();

	}

} // TablemodelPackage
