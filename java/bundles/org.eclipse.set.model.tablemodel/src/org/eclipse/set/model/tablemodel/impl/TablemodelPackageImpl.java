/**
 * Copyright (c) 2022 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.tablemodel.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.emf.ecore.xml.type.XMLTypePackage;
import org.eclipse.set.model.planpro.ATO.ATOPackage;
import org.eclipse.set.model.tablemodel.CellAnnotation;
import org.eclipse.set.model.tablemodel.CellContent;
import org.eclipse.set.model.tablemodel.ColumnDescriptor;
import org.eclipse.set.model.tablemodel.ColumnWidthMode;
import org.eclipse.set.model.tablemodel.CompareFootnoteContainer;
import org.eclipse.set.model.tablemodel.CompareStateCellContent;
import org.eclipse.set.model.tablemodel.CompareTableCellContent;
import org.eclipse.set.model.tablemodel.CompareTableFootnoteContainer;
import org.eclipse.set.model.tablemodel.FootnoteContainer;
import org.eclipse.set.model.tablemodel.FootnoteMetaInformation;
import org.eclipse.set.model.tablemodel.MultiColorCellContent;
import org.eclipse.set.model.tablemodel.MultiColorContent;
import org.eclipse.set.model.tablemodel.PlanCompareRow;
import org.eclipse.set.model.tablemodel.PlanCompareRowType;
import org.eclipse.set.model.tablemodel.RowGroup;
import org.eclipse.set.model.tablemodel.RowMergeMode;
import org.eclipse.set.model.tablemodel.SimpleFootnoteContainer;
import org.eclipse.set.model.tablemodel.StringCellContent;
import org.eclipse.set.model.tablemodel.Table;
import org.eclipse.set.model.tablemodel.TableCell;
import org.eclipse.set.model.tablemodel.TableContent;
import org.eclipse.set.model.tablemodel.TableRow;
import org.eclipse.set.model.tablemodel.TablemodelFactory;
import org.eclipse.set.model.tablemodel.TablemodelPackage;
import org.eclipse.set.model.planpro.Ansteuerung_Element.Ansteuerung_ElementPackage;
import org.eclipse.set.model.planpro.Bahnsteig.BahnsteigPackage;
import org.eclipse.set.model.planpro.Bahnuebergang.BahnuebergangPackage;
import org.eclipse.set.model.planpro.Balisentechnik_ETCS.Balisentechnik_ETCSPackage;
import org.eclipse.set.model.planpro.BasisTypen.BasisTypenPackage;
import org.eclipse.set.model.planpro.Basisobjekte.BasisobjektePackage;
import org.eclipse.set.model.planpro.Bedienung.BedienungPackage;
import org.eclipse.set.model.planpro.Block.BlockPackage;
import org.eclipse.set.model.planpro.Fahrstrasse.FahrstrassePackage;
import org.eclipse.set.model.planpro.Flankenschutz.FlankenschutzPackage;
import org.eclipse.set.model.planpro.Geodaten.GeodatenPackage;
import org.eclipse.set.model.planpro.Gleis.GleisPackage;
import org.eclipse.set.model.planpro.Layoutinformationen.LayoutinformationenPackage;
import org.eclipse.set.model.planpro.Medien_und_Trassen.Medien_und_TrassenPackage;
import org.eclipse.set.model.planpro.Nahbedienung.NahbedienungPackage;
import org.eclipse.set.model.planpro.Ortung.OrtungPackage;
import org.eclipse.set.model.planpro.PZB.PZBPackage;
import org.eclipse.set.model.planpro.PlanPro.PlanProPackage;
import org.eclipse.set.model.planpro.Regelzeichnung.RegelzeichnungPackage;
import org.eclipse.set.model.planpro.Schluesselabhaengigkeiten.SchluesselabhaengigkeitenPackage;
import org.eclipse.set.model.planpro.Signalbegriffe_Struktur.Signalbegriffe_StrukturPackage;
import org.eclipse.set.model.planpro.Signale.SignalePackage;
import org.eclipse.set.model.planpro.Verweise.VerweisePackage;
import org.eclipse.set.model.planpro.Weichen_und_Gleissperren.Weichen_und_GleissperrenPackage;
import org.eclipse.set.model.planpro.Zuglenkung.ZuglenkungPackage;
import org.eclipse.set.model.planpro.Zugnummernmeldeanlage.ZugnummernmeldeanlagePackage;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Package</b>. <!--
 * end-user-doc -->
 * 
 * @generated
 */
public class TablemodelPackageImpl extends EPackageImpl
		implements TablemodelPackage {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass tableEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass columnDescriptorEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass tableContentEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass rowGroupEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass tableRowEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass tableCellEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass cellContentEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass stringCellContentEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass compareStateCellContentEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass cellAnnotationEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass multiColorCellContentEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass multiColorContentEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass footnoteContainerEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass compareFootnoteContainerEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass simpleFootnoteContainerEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass compareTableCellContentEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass compareTableFootnoteContainerEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass planCompareRowEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass footnoteMetaInformationEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EEnum columnWidthModeEEnum = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EEnum rowMergeModeEEnum = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EEnum planCompareRowTypeEEnum = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the
	 * package package URI value.
	 * <p>
	 * Note: the correct way to create the package is via the static factory
	 * method {@link #init init()}, which also performs initialization of the
	 * package, or returns the registered package, if one already exists. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.eclipse.set.model.tablemodel.TablemodelPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private TablemodelPackageImpl() {
		super(eNS_URI, TablemodelFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model,
	 * and for any others upon which it depends.
	 *
	 * <p>
	 * This method is used to initialize {@link TablemodelPackage#eINSTANCE}
	 * when that field is accessed. Clients should not invoke it directly.
	 * Instead, they should simply access that field to obtain the package. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static TablemodelPackage init() {
		if (isInited)
			return (TablemodelPackage) EPackage.Registry.INSTANCE
					.getEPackage(TablemodelPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredTablemodelPackage = EPackage.Registry.INSTANCE
				.get(eNS_URI);
		TablemodelPackageImpl theTablemodelPackage = registeredTablemodelPackage instanceof TablemodelPackageImpl
				? (TablemodelPackageImpl) registeredTablemodelPackage
				: new TablemodelPackageImpl();

		isInited = true;

		// Initialize simple dependencies
		BasisobjektePackage.eINSTANCE.eClass();
		BasisTypenPackage.eINSTANCE.eClass();
		GeodatenPackage.eINSTANCE.eClass();
		GleisPackage.eINSTANCE.eClass();
		BedienungPackage.eINSTANCE.eClass();
		Ansteuerung_ElementPackage.eINSTANCE.eClass();
		Medien_und_TrassenPackage.eINSTANCE.eClass();
		VerweisePackage.eINSTANCE.eClass();
		ATOPackage.eINSTANCE.eClass();
		PlanProPackage.eINSTANCE.eClass();
		BahnsteigPackage.eINSTANCE.eClass();
		Balisentechnik_ETCSPackage.eINSTANCE.eClass();
		BlockPackage.eINSTANCE.eClass();
		BahnuebergangPackage.eINSTANCE.eClass();
		OrtungPackage.eINSTANCE.eClass();
		FlankenschutzPackage.eINSTANCE.eClass();
		FahrstrassePackage.eINSTANCE.eClass();
		SignalePackage.eINSTANCE.eClass();
		LayoutinformationenPackage.eINSTANCE.eClass();
		NahbedienungPackage.eINSTANCE.eClass();
		PZBPackage.eINSTANCE.eClass();
		RegelzeichnungPackage.eINSTANCE.eClass();
		SchluesselabhaengigkeitenPackage.eINSTANCE.eClass();
		Weichen_und_GleissperrenPackage.eINSTANCE.eClass();
		ZuglenkungPackage.eINSTANCE.eClass();
		ZugnummernmeldeanlagePackage.eINSTANCE.eClass();
		Signalbegriffe_StrukturPackage.eINSTANCE.eClass();
		XMLTypePackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theTablemodelPackage.createPackageContents();

		// Initialize created meta-data
		theTablemodelPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theTablemodelPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(TablemodelPackage.eNS_URI,
				theTablemodelPackage);
		return theTablemodelPackage;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getTable() {
		return tableEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getTable_Columndescriptors() {
		return (EReference) tableEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getTable_Tablecontent() {
		return (EReference) tableEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getColumnDescriptor() {
		return columnDescriptorEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getColumnDescriptor_Width() {
		return (EAttribute) columnDescriptorEClass.getEStructuralFeatures()
				.get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getColumnDescriptor_WidthMode() {
		return (EAttribute) columnDescriptorEClass.getEStructuralFeatures()
				.get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getColumnDescriptor_Children() {
		return (EReference) columnDescriptorEClass.getEStructuralFeatures()
				.get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getColumnDescriptor_Label() {
		return (EAttribute) columnDescriptorEClass.getEStructuralFeatures()
				.get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getColumnDescriptor_Greyed() {
		return (EAttribute) columnDescriptorEClass.getEStructuralFeatures()
				.get(4);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getColumnDescriptor_Unit() {
		return (EAttribute) columnDescriptorEClass.getEStructuralFeatures()
				.get(5);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getColumnDescriptor_Parent() {
		return (EReference) columnDescriptorEClass.getEStructuralFeatures()
				.get(6);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getColumnDescriptor_Height() {
		return (EAttribute) columnDescriptorEClass.getEStructuralFeatures()
				.get(7);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getColumnDescriptor_MergeCommonValues() {
		return (EAttribute) columnDescriptorEClass.getEStructuralFeatures()
				.get(8);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getColumnDescriptor_ColumnPosition() {
		return (EAttribute) columnDescriptorEClass.getEStructuralFeatures()
				.get(9);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getTableContent() {
		return tableContentEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getTableContent_Rowgroups() {
		return (EReference) tableContentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getRowGroup() {
		return rowGroupEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getRowGroup_Rows() {
		return (EReference) rowGroupEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getRowGroup_LeadingObject() {
		return (EReference) rowGroupEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getRowGroup_LeadingObjectIndex() {
		return (EAttribute) rowGroupEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getTableRow() {
		return tableRowEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getTableRow_Cells() {
		return (EReference) tableRowEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getTableRow_Footnotes() {
		return (EReference) tableRowEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getTableRow_RowObject() {
		return (EReference) tableRowEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getTableRow_RowIndex() {
		return (EAttribute) tableRowEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getTableCell() {
		return tableCellEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getTableCell_Content() {
		return (EReference) tableCellEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getTableCell_Columndescriptor() {
		return (EReference) tableCellEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getTableCell_Cellannotation() {
		return (EReference) tableCellEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getCellContent() {
		return cellContentEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getCellContent_Separator() {
		return (EAttribute) cellContentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getStringCellContent() {
		return stringCellContentEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getStringCellContent_Value() {
		return (EAttribute) stringCellContentEClass.getEStructuralFeatures()
				.get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getCompareStateCellContent() {
		return compareStateCellContentEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getCompareStateCellContent_OldValue() {
		return (EReference) compareStateCellContentEClass
				.getEStructuralFeatures()
				.get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getCompareStateCellContent_NewValue() {
		return (EReference) compareStateCellContentEClass
				.getEStructuralFeatures()
				.get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getCellAnnotation() {
		return cellAnnotationEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getMultiColorCellContent() {
		return multiColorCellContentEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getMultiColorCellContent_Value() {
		return (EReference) multiColorCellContentEClass.getEStructuralFeatures()
				.get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getMultiColorContent() {
		return multiColorContentEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getMultiColorContent_MultiColorValue() {
		return (EAttribute) multiColorContentEClass.getEStructuralFeatures()
				.get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getMultiColorContent_StringFormat() {
		return (EAttribute) multiColorContentEClass.getEStructuralFeatures()
				.get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getMultiColorContent_DisableMultiColor() {
		return (EAttribute) multiColorContentEClass.getEStructuralFeatures()
				.get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getFootnoteContainer() {
		return footnoteContainerEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getCompareFootnoteContainer() {
		return compareFootnoteContainerEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getCompareFootnoteContainer_OldFootnotes() {
		return (EReference) compareFootnoteContainerEClass
				.getEStructuralFeatures()
				.get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getCompareFootnoteContainer_NewFootnotes() {
		return (EReference) compareFootnoteContainerEClass
				.getEStructuralFeatures()
				.get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getCompareFootnoteContainer_UnchangedFootnotes() {
		return (EReference) compareFootnoteContainerEClass
				.getEStructuralFeatures()
				.get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getSimpleFootnoteContainer() {
		return simpleFootnoteContainerEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getSimpleFootnoteContainer_Footnotes() {
		return (EReference) simpleFootnoteContainerEClass
				.getEStructuralFeatures()
				.get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getCompareTableCellContent() {
		return compareTableCellContentEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getCompareTableCellContent_MainPlanCellContent() {
		return (EReference) compareTableCellContentEClass
				.getEStructuralFeatures()
				.get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getCompareTableCellContent_ComparePlanCellContent() {
		return (EReference) compareTableCellContentEClass
				.getEStructuralFeatures()
				.get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getCompareTableFootnoteContainer() {
		return compareTableFootnoteContainerEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getCompareTableFootnoteContainer_MainPlanFootnoteContainer() {
		return (EReference) compareTableFootnoteContainerEClass
				.getEStructuralFeatures()
				.get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getCompareTableFootnoteContainer_ComparePlanFootnoteContainer() {
		return (EReference) compareTableFootnoteContainerEClass
				.getEStructuralFeatures()
				.get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getPlanCompareRow() {
		return planCompareRowEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getPlanCompareRow_RowType() {
		return (EAttribute) planCompareRowEClass.getEStructuralFeatures()
				.get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getFootnoteMetaInformation() {
		return footnoteMetaInformationEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getFootnoteMetaInformation_OwnerObject() {
		return (EReference) footnoteMetaInformationEClass
				.getEStructuralFeatures()
				.get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getFootnoteMetaInformation_Footnote() {
		return (EReference) footnoteMetaInformationEClass
				.getEStructuralFeatures()
				.get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EEnum getColumnWidthMode() {
		return columnWidthModeEEnum;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EEnum getRowMergeMode() {
		return rowMergeModeEEnum;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EEnum getPlanCompareRowType() {
		return planCompareRowTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public TablemodelFactory getTablemodelFactory() {
		return (TablemodelFactory) getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package. This method is guarded to
	 * have no affect on any invocation but its first. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated)
			return;
		isCreated = true;

		// Create classes and their features
		tableEClass = createEClass(TABLE);
		createEReference(tableEClass, TABLE__COLUMNDESCRIPTORS);
		createEReference(tableEClass, TABLE__TABLECONTENT);

		columnDescriptorEClass = createEClass(COLUMN_DESCRIPTOR);
		createEAttribute(columnDescriptorEClass, COLUMN_DESCRIPTOR__WIDTH);
		createEAttribute(columnDescriptorEClass, COLUMN_DESCRIPTOR__WIDTH_MODE);
		createEReference(columnDescriptorEClass, COLUMN_DESCRIPTOR__CHILDREN);
		createEAttribute(columnDescriptorEClass, COLUMN_DESCRIPTOR__LABEL);
		createEAttribute(columnDescriptorEClass, COLUMN_DESCRIPTOR__GREYED);
		createEAttribute(columnDescriptorEClass, COLUMN_DESCRIPTOR__UNIT);
		createEReference(columnDescriptorEClass, COLUMN_DESCRIPTOR__PARENT);
		createEAttribute(columnDescriptorEClass, COLUMN_DESCRIPTOR__HEIGHT);
		createEAttribute(columnDescriptorEClass,
				COLUMN_DESCRIPTOR__MERGE_COMMON_VALUES);
		createEAttribute(columnDescriptorEClass,
				COLUMN_DESCRIPTOR__COLUMN_POSITION);

		tableContentEClass = createEClass(TABLE_CONTENT);
		createEReference(tableContentEClass, TABLE_CONTENT__ROWGROUPS);

		rowGroupEClass = createEClass(ROW_GROUP);
		createEReference(rowGroupEClass, ROW_GROUP__ROWS);
		createEReference(rowGroupEClass, ROW_GROUP__LEADING_OBJECT);
		createEAttribute(rowGroupEClass, ROW_GROUP__LEADING_OBJECT_INDEX);

		tableRowEClass = createEClass(TABLE_ROW);
		createEReference(tableRowEClass, TABLE_ROW__CELLS);
		createEAttribute(tableRowEClass, TABLE_ROW__ROW_INDEX);
		createEReference(tableRowEClass, TABLE_ROW__FOOTNOTES);
		createEReference(tableRowEClass, TABLE_ROW__ROW_OBJECT);

		tableCellEClass = createEClass(TABLE_CELL);
		createEReference(tableCellEClass, TABLE_CELL__CONTENT);
		createEReference(tableCellEClass, TABLE_CELL__COLUMNDESCRIPTOR);
		createEReference(tableCellEClass, TABLE_CELL__CELLANNOTATION);

		cellContentEClass = createEClass(CELL_CONTENT);
		createEAttribute(cellContentEClass, CELL_CONTENT__SEPARATOR);

		stringCellContentEClass = createEClass(STRING_CELL_CONTENT);
		createEAttribute(stringCellContentEClass, STRING_CELL_CONTENT__VALUE);

		compareStateCellContentEClass = createEClass(
				COMPARE_STATE_CELL_CONTENT);
		createEReference(compareStateCellContentEClass,
				COMPARE_STATE_CELL_CONTENT__OLD_VALUE);
		createEReference(compareStateCellContentEClass,
				COMPARE_STATE_CELL_CONTENT__NEW_VALUE);

		cellAnnotationEClass = createEClass(CELL_ANNOTATION);

		multiColorCellContentEClass = createEClass(MULTI_COLOR_CELL_CONTENT);
		createEReference(multiColorCellContentEClass,
				MULTI_COLOR_CELL_CONTENT__VALUE);

		multiColorContentEClass = createEClass(MULTI_COLOR_CONTENT);
		createEAttribute(multiColorContentEClass,
				MULTI_COLOR_CONTENT__MULTI_COLOR_VALUE);
		createEAttribute(multiColorContentEClass,
				MULTI_COLOR_CONTENT__STRING_FORMAT);
		createEAttribute(multiColorContentEClass,
				MULTI_COLOR_CONTENT__DISABLE_MULTI_COLOR);

		footnoteContainerEClass = createEClass(FOOTNOTE_CONTAINER);

		compareFootnoteContainerEClass = createEClass(
				COMPARE_FOOTNOTE_CONTAINER);
		createEReference(compareFootnoteContainerEClass,
				COMPARE_FOOTNOTE_CONTAINER__OLD_FOOTNOTES);
		createEReference(compareFootnoteContainerEClass,
				COMPARE_FOOTNOTE_CONTAINER__NEW_FOOTNOTES);
		createEReference(compareFootnoteContainerEClass,
				COMPARE_FOOTNOTE_CONTAINER__UNCHANGED_FOOTNOTES);

		simpleFootnoteContainerEClass = createEClass(SIMPLE_FOOTNOTE_CONTAINER);
		createEReference(simpleFootnoteContainerEClass,
				SIMPLE_FOOTNOTE_CONTAINER__FOOTNOTES);

		compareTableCellContentEClass = createEClass(
				COMPARE_TABLE_CELL_CONTENT);
		createEReference(compareTableCellContentEClass,
				COMPARE_TABLE_CELL_CONTENT__MAIN_PLAN_CELL_CONTENT);
		createEReference(compareTableCellContentEClass,
				COMPARE_TABLE_CELL_CONTENT__COMPARE_PLAN_CELL_CONTENT);

		compareTableFootnoteContainerEClass = createEClass(
				COMPARE_TABLE_FOOTNOTE_CONTAINER);
		createEReference(compareTableFootnoteContainerEClass,
				COMPARE_TABLE_FOOTNOTE_CONTAINER__MAIN_PLAN_FOOTNOTE_CONTAINER);
		createEReference(compareTableFootnoteContainerEClass,
				COMPARE_TABLE_FOOTNOTE_CONTAINER__COMPARE_PLAN_FOOTNOTE_CONTAINER);

		planCompareRowEClass = createEClass(PLAN_COMPARE_ROW);
		createEAttribute(planCompareRowEClass, PLAN_COMPARE_ROW__ROW_TYPE);

		footnoteMetaInformationEClass = createEClass(FOOTNOTE_META_INFORMATION);
		createEReference(footnoteMetaInformationEClass,
				FOOTNOTE_META_INFORMATION__OWNER_OBJECT);
		createEReference(footnoteMetaInformationEClass,
				FOOTNOTE_META_INFORMATION__FOOTNOTE);

		// Create enums
		columnWidthModeEEnum = createEEnum(COLUMN_WIDTH_MODE);
		rowMergeModeEEnum = createEEnum(ROW_MERGE_MODE);
		planCompareRowTypeEEnum = createEEnum(PLAN_COMPARE_ROW_TYPE);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model. This
	 * method is guarded to have no affect on any invocation but its first. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized)
			return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		BasisobjektePackage theBasisobjektePackage = (BasisobjektePackage) EPackage.Registry.INSTANCE
				.getEPackage(BasisobjektePackage.eNS_URI);
		XMLTypePackage theXMLTypePackage = (XMLTypePackage) EPackage.Registry.INSTANCE
				.getEPackage(XMLTypePackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		stringCellContentEClass.getESuperTypes().add(this.getCellContent());
		compareStateCellContentEClass.getESuperTypes()
				.add(this.getCellContent());
		multiColorCellContentEClass.getESuperTypes().add(this.getCellContent());
		compareFootnoteContainerEClass.getESuperTypes()
				.add(this.getFootnoteContainer());
		simpleFootnoteContainerEClass.getESuperTypes()
				.add(this.getFootnoteContainer());
		compareTableCellContentEClass.getESuperTypes()
				.add(this.getCellContent());
		compareTableFootnoteContainerEClass.getESuperTypes()
				.add(this.getFootnoteContainer());
		planCompareRowEClass.getESuperTypes().add(this.getTableRow());

		// Initialize classes, features, and operations; add parameters
		initEClass(tableEClass, Table.class, "Table", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTable_Columndescriptors(), this.getColumnDescriptor(),
				null, "columndescriptors", null, 1, -1, Table.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEReference(getTable_Tablecontent(), this.getTableContent(), null,
				"tablecontent", null, 1, 1, Table.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(columnDescriptorEClass, ColumnDescriptor.class,
				"ColumnDescriptor", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getColumnDescriptor_Width(),
				ecorePackage.getEFloatObject(), "width", null, 0, 1,
				ColumnDescriptor.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEAttribute(getColumnDescriptor_WidthMode(),
				this.getColumnWidthMode(), "widthMode", "WIDTH_CM", 0, 1,
				ColumnDescriptor.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEReference(getColumnDescriptor_Children(),
				this.getColumnDescriptor(), this.getColumnDescriptor_Parent(),
				"children", null, 0, -1, ColumnDescriptor.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getColumnDescriptor_Label(), ecorePackage.getEString(),
				"label", null, 1, 1, ColumnDescriptor.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getColumnDescriptor_Greyed(), ecorePackage.getEBoolean(),
				"greyed", null, 1, 1, ColumnDescriptor.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getColumnDescriptor_Unit(), ecorePackage.getEBoolean(),
				"unit", null, 1, 1, ColumnDescriptor.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEReference(getColumnDescriptor_Parent(), this.getColumnDescriptor(),
				this.getColumnDescriptor_Children(), "parent", null, 0, 1,
				ColumnDescriptor.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getColumnDescriptor_Height(), ecorePackage.getEDouble(),
				"height", null, 0, 1, ColumnDescriptor.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getColumnDescriptor_MergeCommonValues(),
				this.getRowMergeMode(), "mergeCommonValues", "DEFAULT", 0, 1,
				ColumnDescriptor.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEAttribute(getColumnDescriptor_ColumnPosition(),
				ecorePackage.getEString(), "columnPosition", null, 0, 1,
				ColumnDescriptor.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);

		initEClass(tableContentEClass, TableContent.class, "TableContent",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTableContent_Rowgroups(), this.getRowGroup(), null,
				"rowgroups", null, 0, -1, TableContent.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(rowGroupEClass, RowGroup.class, "RowGroup", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getRowGroup_Rows(), this.getTableRow(), null, "rows",
				null, 0, -1, RowGroup.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRowGroup_LeadingObject(),
				theBasisobjektePackage.getUr_Objekt(), null, "leadingObject",
				null, 0, 1, RowGroup.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRowGroup_LeadingObjectIndex(), ecorePackage.getEInt(),
				"leadingObjectIndex", "0", 1, 1, RowGroup.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		initEClass(tableRowEClass, TableRow.class, "TableRow", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTableRow_Cells(), this.getTableCell(), null, "cells",
				null, 1, -1, TableRow.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTableRow_RowIndex(), ecorePackage.getEInt(),
				"rowIndex", null, 0, 1, TableRow.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEReference(getTableRow_Footnotes(), this.getFootnoteContainer(),
				null, "footnotes", null, 0, 1, TableRow.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTableRow_RowObject(), ecorePackage.getEObject(), null,
				"rowObject", null, 0, 1, TableRow.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tableCellEClass, TableCell.class, "TableCell", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTableCell_Content(), this.getCellContent(), null,
				"content", null, 0, 1, TableCell.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTableCell_Columndescriptor(),
				this.getColumnDescriptor(), null, "columndescriptor", null, 1,
				1, TableCell.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEReference(getTableCell_Cellannotation(), this.getCellAnnotation(),
				null, "cellannotation", null, 0, -1, TableCell.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);

		initEClass(cellContentEClass, CellContent.class, "CellContent",
				IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getCellContent_Separator(), ecorePackage.getEString(),
				"separator", null, 0, 1, CellContent.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		initEClass(stringCellContentEClass, StringCellContent.class,
				"StringCellContent", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getStringCellContent_Value(), ecorePackage.getEString(),
				"value", null, 0, -1, StringCellContent.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		initEClass(compareStateCellContentEClass, CompareStateCellContent.class,
				"CompareStateCellContent", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCompareStateCellContent_OldValue(),
				this.getCellContent(), null, "oldValue", null, 0, 1,
				CompareStateCellContent.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCompareStateCellContent_NewValue(),
				this.getCellContent(), null, "newValue", null, 0, 1,
				CompareStateCellContent.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(cellAnnotationEClass, CellAnnotation.class, "CellAnnotation",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(multiColorCellContentEClass, MultiColorCellContent.class,
				"MultiColorCellContent", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getMultiColorCellContent_Value(),
				this.getMultiColorContent(), null, "value", null, 0, -1,
				MultiColorCellContent.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(multiColorContentEClass, MultiColorContent.class,
				"MultiColorContent", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMultiColorContent_MultiColorValue(),
				ecorePackage.getEString(), "multiColorValue", null, 0, 1,
				MultiColorContent.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEAttribute(getMultiColorContent_StringFormat(),
				ecorePackage.getEString(), "stringFormat", null, 0, 1,
				MultiColorContent.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEAttribute(getMultiColorContent_DisableMultiColor(),
				theXMLTypePackage.getBoolean(), "disableMultiColor", null, 0, 1,
				MultiColorContent.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);

		initEClass(footnoteContainerEClass, FootnoteContainer.class,
				"FootnoteContainer", IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);

		initEClass(compareFootnoteContainerEClass,
				CompareFootnoteContainer.class, "CompareFootnoteContainer",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCompareFootnoteContainer_OldFootnotes(),
				this.getSimpleFootnoteContainer(), null, "oldFootnotes", null,
				0, 1, CompareFootnoteContainer.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCompareFootnoteContainer_NewFootnotes(),
				this.getSimpleFootnoteContainer(), null, "newFootnotes", null,
				0, 1, CompareFootnoteContainer.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCompareFootnoteContainer_UnchangedFootnotes(),
				this.getSimpleFootnoteContainer(), null, "unchangedFootnotes",
				null, 0, 1, CompareFootnoteContainer.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(simpleFootnoteContainerEClass, SimpleFootnoteContainer.class,
				"SimpleFootnoteContainer", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getSimpleFootnoteContainer_Footnotes(),
				this.getFootnoteMetaInformation(), null, "footnotes", null, 0,
				-1, SimpleFootnoteContainer.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(compareTableCellContentEClass, CompareTableCellContent.class,
				"CompareTableCellContent", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCompareTableCellContent_MainPlanCellContent(),
				this.getCellContent(), null, "mainPlanCellContent", null, 0, 1,
				CompareTableCellContent.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCompareTableCellContent_ComparePlanCellContent(),
				this.getCellContent(), null, "comparePlanCellContent", null, 0,
				1, CompareTableCellContent.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(compareTableFootnoteContainerEClass,
				CompareTableFootnoteContainer.class,
				"CompareTableFootnoteContainer", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(
				getCompareTableFootnoteContainer_MainPlanFootnoteContainer(),
				this.getFootnoteContainer(), null, "mainPlanFootnoteContainer",
				null, 0, 1, CompareTableFootnoteContainer.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(
				getCompareTableFootnoteContainer_ComparePlanFootnoteContainer(),
				this.getFootnoteContainer(), null,
				"comparePlanFootnoteContainer", null, 0, 1,
				CompareTableFootnoteContainer.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(planCompareRowEClass, PlanCompareRow.class, "PlanCompareRow",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getPlanCompareRow_RowType(),
				this.getPlanCompareRowType(), "rowType", null, 1, 1,
				PlanCompareRow.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);

		initEClass(footnoteMetaInformationEClass, FootnoteMetaInformation.class,
				"FootnoteMetaInformation", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getFootnoteMetaInformation_OwnerObject(),
				ecorePackage.getEObject(), null, "ownerObject", null, 0, 1,
				FootnoteMetaInformation.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getFootnoteMetaInformation_Footnote(),
				theBasisobjektePackage.getBearbeitungsvermerk(), null,
				"footnote", null, 0, 1, FootnoteMetaInformation.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(columnWidthModeEEnum, ColumnWidthMode.class,
				"ColumnWidthMode");
		addEEnumLiteral(columnWidthModeEEnum, ColumnWidthMode.WIDTH_CM);
		addEEnumLiteral(columnWidthModeEEnum, ColumnWidthMode.WIDTH_PERCENT);

		initEEnum(rowMergeModeEEnum, RowMergeMode.class, "RowMergeMode");
		addEEnumLiteral(rowMergeModeEEnum, RowMergeMode.DEFAULT);
		addEEnumLiteral(rowMergeModeEEnum, RowMergeMode.ENABLED);
		addEEnumLiteral(rowMergeModeEEnum, RowMergeMode.DISABLED);

		initEEnum(planCompareRowTypeEEnum, PlanCompareRowType.class,
				"PlanCompareRowType");
		addEEnumLiteral(planCompareRowTypeEEnum, PlanCompareRowType.NEW_ROW);
		addEEnumLiteral(planCompareRowTypeEEnum,
				PlanCompareRowType.REMOVED_ROW);
		addEEnumLiteral(planCompareRowTypeEEnum,
				PlanCompareRowType.CHANGED_GUID_ROW);

		// Create resource
		createResource(eNS_URI);
	}

} // TablemodelPackageImpl
