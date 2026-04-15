/**
 * Copyright (c) 2022 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.tablemodel.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.ResourceLocator;

import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.edit.provider.ViewerNotification;

import org.eclipse.set.model.planpro.ATO.ATOFactory;
import org.eclipse.set.model.planpro.Ansteuerung_Element.Ansteuerung_ElementFactory;
import org.eclipse.set.model.planpro.Bahnsteig.BahnsteigFactory;
import org.eclipse.set.model.planpro.Bahnuebergang.BahnuebergangFactory;
import org.eclipse.set.model.planpro.Balisentechnik_ETCS.Balisentechnik_ETCSFactory;
import org.eclipse.set.model.planpro.BasisTypen.BasisTypenFactory;
import org.eclipse.set.model.planpro.Basisobjekte.BasisobjekteFactory;
import org.eclipse.set.model.planpro.Bedienung.BedienungFactory;
import org.eclipse.set.model.planpro.Block.BlockFactory;
import org.eclipse.set.model.planpro.Fahrstrasse.FahrstrasseFactory;
import org.eclipse.set.model.planpro.Flankenschutz.FlankenschutzFactory;
import org.eclipse.set.model.planpro.Geodaten.GeodatenFactory;
import org.eclipse.set.model.planpro.Gleis.GleisFactory;
import org.eclipse.set.model.planpro.Layoutinformationen.LayoutinformationenFactory;
import org.eclipse.set.model.planpro.Medien_und_Trassen.Medien_und_TrassenFactory;
import org.eclipse.set.model.planpro.Nahbedienung.NahbedienungFactory;
import org.eclipse.set.model.planpro.Ortung.OrtungFactory;
import org.eclipse.set.model.planpro.PZB.PZBFactory;
import org.eclipse.set.model.planpro.PlanPro.PlanProFactory;
import org.eclipse.set.model.planpro.Regelzeichnung.RegelzeichnungFactory;
import org.eclipse.set.model.planpro.Schluesselabhaengigkeiten.SchluesselabhaengigkeitenFactory;
import org.eclipse.set.model.planpro.Signale.SignaleFactory;
import org.eclipse.set.model.planpro.Verweise.VerweiseFactory;
import org.eclipse.set.model.planpro.Weichen_und_Gleissperren.Weichen_und_GleissperrenFactory;
import org.eclipse.set.model.planpro.Zuglenkung.ZuglenkungFactory;
import org.eclipse.set.model.planpro.Zugnummernmeldeanlage.ZugnummernmeldeanlageFactory;
import org.eclipse.set.model.tablemodel.TableRow;
import org.eclipse.set.model.tablemodel.TablemodelFactory;
import org.eclipse.set.model.tablemodel.TablemodelPackage;

/**
 * This is the item provider adapter for a
 * {@link org.eclipse.set.model.tablemodel.TableRow} object. <!-- begin-user-doc
 * --> <!-- end-user-doc -->
 * 
 * @generated
 */
public class TableRowItemProvider extends ItemProviderAdapter
		implements IEditingDomainItemProvider, IStructuredItemContentProvider,
		ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource {
	/**
	 * This constructs an instance from a factory and a notifier. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public TableRowItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/**
	 * This returns the property descriptors for the adapted class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
		if (itemPropertyDescriptors == null) {
			super.getPropertyDescriptors(object);

			addRowIndexPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Row Index feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addRowIndexPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(
				((ComposeableAdapterFactory) adapterFactory)
						.getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_TableRow_rowIndex_feature"),
				getString("_UI_PropertyDescriptor_description",
						"_UI_TableRow_rowIndex_feature", "_UI_TableRow_type"),
				TablemodelPackage.Literals.TABLE_ROW__ROW_INDEX, true, false,
				false, ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE, null,
				null));
	}

	/**
	 * This specifies how to implement {@link #getChildren} and is used to
	 * deduce an appropriate feature for an
	 * {@link org.eclipse.emf.edit.command.AddCommand},
	 * {@link org.eclipse.emf.edit.command.RemoveCommand} or
	 * {@link org.eclipse.emf.edit.command.MoveCommand} in
	 * {@link #createCommand}. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Collection<? extends EStructuralFeature> getChildrenFeatures(
			Object object) {
		if (childrenFeatures == null) {
			super.getChildrenFeatures(object);
			childrenFeatures.add(TablemodelPackage.Literals.TABLE_ROW__CELLS);
			childrenFeatures
					.add(TablemodelPackage.Literals.TABLE_ROW__FOOTNOTES);
			childrenFeatures
					.add(TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT);
		}
		return childrenFeatures;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EStructuralFeature getChildFeature(Object object, Object child) {
		// Check the type of the specified child object and return the proper
		// feature to use for
		// adding (see {@link AddCommand}) it as a child.

		return super.getChildFeature(object, child);
	}

	/**
	 * This returns TableRow.gif. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object,
				getResourceLocator().getImage("full/obj16/TableRow"));
	}

	/**
	 * This returns the label text for the adapted class. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		TableRow tableRow = (TableRow) object;
		return getString("_UI_TableRow_type") + " " + tableRow.getRowIndex();
	}

	/**
	 * This handles model notifications by calling {@link #updateChildren} to
	 * update any cached children and by creating a viewer notification, which
	 * it passes to {@link #fireNotifyChanged}. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void notifyChanged(Notification notification) {
		updateChildren(notification);

		switch (notification.getFeatureID(TableRow.class)) {
			case TablemodelPackage.TABLE_ROW__ROW_INDEX:
				fireNotifyChanged(new ViewerNotification(notification,
						notification.getNotifier(), false, true));
				return;
			case TablemodelPackage.TABLE_ROW__CELLS:
			case TablemodelPackage.TABLE_ROW__FOOTNOTES:
			case TablemodelPackage.TABLE_ROW__ROW_OBJECT:
				fireNotifyChanged(new ViewerNotification(notification,
						notification.getNotifier(), true, false));
				return;
		}
		super.notifyChanged(notification);
	}

	/**
	 * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s
	 * describing the children that can be created under this object. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected void collectNewChildDescriptors(
			Collection<Object> newChildDescriptors, Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__CELLS,
				TablemodelFactory.eINSTANCE.createTableCell()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__FOOTNOTES,
				TablemodelFactory.eINSTANCE.createCompareFootnoteContainer()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__FOOTNOTES,
				TablemodelFactory.eINSTANCE.createSimpleFootnoteContainer()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__FOOTNOTES,
				TablemodelFactory.eINSTANCE
						.createCompareTableFootnoteContainer()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				TablemodelFactory.eINSTANCE.createTable()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				TablemodelFactory.eINSTANCE.createColumnDescriptor()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				TablemodelFactory.eINSTANCE.createTableContent()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				TablemodelFactory.eINSTANCE.createRowGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				TablemodelFactory.eINSTANCE.createTableRow()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				TablemodelFactory.eINSTANCE.createTableCell()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				TablemodelFactory.eINSTANCE.createStringCellContent()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				TablemodelFactory.eINSTANCE.createCompareStateCellContent()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				TablemodelFactory.eINSTANCE.createCellAnnotation()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				TablemodelFactory.eINSTANCE.createMultiColorCellContent()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				TablemodelFactory.eINSTANCE.createMultiColorContent()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				TablemodelFactory.eINSTANCE.createCompareFootnoteContainer()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				TablemodelFactory.eINSTANCE.createSimpleFootnoteContainer()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				TablemodelFactory.eINSTANCE.createCompareTableCellContent()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				TablemodelFactory.eINSTANCE
						.createCompareTableFootnoteContainer()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				TablemodelFactory.eINSTANCE.createPlanCompareRow()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				TablemodelFactory.eINSTANCE.createFootnote()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BasisobjekteFactory.eINSTANCE.createAbstand_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BasisobjekteFactory.eINSTANCE.createAnhang()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BasisobjekteFactory.eINSTANCE
						.createAnhang_Allg_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BasisobjekteFactory.eINSTANCE.createAnhang_Art_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BasisobjekteFactory.eINSTANCE
						.createBasis_Objekt_Allg_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BasisobjekteFactory.eINSTANCE.createBearbeitungsvermerk()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BasisobjekteFactory.eINSTANCE
						.createBearbeitungsvermerk_Allg_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BasisobjekteFactory.eINSTANCE
						.createBearbeitungsvermerk_Rolle_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BasisobjekteFactory.eINSTANCE.createBegrenzung_A_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BasisobjekteFactory.eINSTANCE.createBegrenzung_B_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BasisobjekteFactory.eINSTANCE
						.createBereich_Objekt_Teilbereich_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BasisobjekteFactory.eINSTANCE.createBeschreibung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BasisobjekteFactory.eINSTANCE
						.createBestandsrelevanz_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BasisobjekteFactory.eINSTANCE
						.createBestandsschutz_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BasisobjekteFactory.eINSTANCE
						.createBV_Darstellung_In_Plan_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BasisobjekteFactory.eINSTANCE.createBV_Kategorie_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BasisobjekteFactory.eINSTANCE.createDateiname_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BasisobjekteFactory.eINSTANCE.createDateityp_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BasisobjekteFactory.eINSTANCE
						.createDatum_Regelwerk_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BasisobjekteFactory.eINSTANCE
						.createDB_GDI_Referenz_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BasisobjekteFactory.eINSTANCE.createIdentitaet_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BasisobjekteFactory.eINSTANCE.createKm_Massgebend_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BasisobjekteFactory.eINSTANCE.createKommentar_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BasisobjekteFactory.eINSTANCE.createKurztext_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BasisobjekteFactory.eINSTANCE.createLieferobjekt()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BasisobjekteFactory.eINSTANCE
						.createLO_Ausgabestand_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BasisobjekteFactory.eINSTANCE
						.createLO_Datum_Herstellung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BasisobjekteFactory.eINSTANCE
						.createLO_DB_Freigabe_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BasisobjekteFactory.eINSTANCE.createLO_EMA_Nr_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BasisobjekteFactory.eINSTANCE.createLO_Ersatz_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BasisobjekteFactory.eINSTANCE
						.createLO_Firmensachnummer_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BasisobjekteFactory.eINSTANCE
						.createLO_Material_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BasisobjekteFactory.eINSTANCE
						.createLO_Seriennummer_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BasisobjekteFactory.eINSTANCE
						.createLST_Objekt_Art_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BasisobjekteFactory.eINSTANCE
						.createObjektreferenzen_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BasisobjekteFactory.eINSTANCE
						.createObjektzustand_Besonders_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BasisobjekteFactory.eINSTANCE.createProxy_Objekt()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BasisobjekteFactory.eINSTANCE
						.createPunkt_Objekt_Strecke_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BasisobjekteFactory.eINSTANCE
						.createPunkt_Objekt_TOP_Kante_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BasisobjekteFactory.eINSTANCE
						.createRichtungsbezug_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BasisobjekteFactory.eINSTANCE
						.createSeitliche_Lage_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BasisobjekteFactory.eINSTANCE
						.createSeitlicher_Abstand_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BasisobjekteFactory.eINSTANCE.createStrecke_Km_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BasisobjekteFactory.eINSTANCE
						.createTechnischer_Platz_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BasisobjekteFactory.eINSTANCE.createWirkrichtung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BasisobjekteFactory.eINSTANCE
						.createZeit_Bearbeitungsvermerk_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BasisTypenFactory.eINSTANCE
						.createBezeichnung_Aussenanlage_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BasisTypenFactory.eINSTANCE
						.createBezeichnung_Element_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BasisTypenFactory.eINSTANCE
						.createBezeichnung_Lageplan_Kurz_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BasisTypenFactory.eINSTANCE
						.createBezeichnung_Lageplan_Lang_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BasisTypenFactory.eINSTANCE
						.createBezeichnung_Tabelle_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BasisTypenFactory.eINSTANCE
						.createDatum_Auslieferung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BasisTypenFactory.eINSTANCE
						.createID_Bearbeitungsvermerk_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BasisTypenFactory.eINSTANCE.createKennzahl_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BasisTypenFactory.eINSTANCE
						.createOertlicher_Elementname_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BasisTypenFactory.eINSTANCE
						.createPruefmerkmale_Daten_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BasisTypenFactory.eINSTANCE.createPruefsumme_Art_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BasisTypenFactory.eINSTANCE.createPruefsumme_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BasisTypenFactory.eINSTANCE
						.createVersion_Auslieferung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BasisTypenFactory.eINSTANCE.createZeiger_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				GeodatenFactory.eINSTANCE
						.createAnzeigegefuehrt_ES_Kategorie_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				GeodatenFactory.eINSTANCE
						.createBezeichnung_Strecke_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				GeodatenFactory.eINSTANCE.createBremsweg_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				GeodatenFactory.eINSTANCE.createGEO_Form_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				GeodatenFactory.eINSTANCE.createGEO_KAD_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				GeodatenFactory.eINSTANCE.createGEO_Kante()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				GeodatenFactory.eINSTANCE
						.createGEO_Kante_Allg_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				GeodatenFactory.eINSTANCE.createGEO_Knoten()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				GeodatenFactory.eINSTANCE
						.createGEO_Koordinatensystem_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				GeodatenFactory.eINSTANCE.createGEO_Laenge_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				GeodatenFactory.eINSTANCE.createGEO_PAD_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				GeodatenFactory.eINSTANCE.createGEO_Punkt()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				GeodatenFactory.eINSTANCE
						.createGEO_Punkt_Allg_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				GeodatenFactory.eINSTANCE.createGEO_Radius_A_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				GeodatenFactory.eINSTANCE.createGEO_Radius_B_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				GeodatenFactory.eINSTANCE
						.createGEO_Richtungswinkel_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				GeodatenFactory.eINSTANCE.createGeschwindigkeit_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				GeodatenFactory.eINSTANCE.createGeschwindigkeitsprofil()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				GeodatenFactory.eINSTANCE
						.createGeschwindigkeitsprofil_Allg_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				GeodatenFactory.eINSTANCE.createGK_X_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				GeodatenFactory.eINSTANCE.createGK_Y_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				GeodatenFactory.eINSTANCE.createGK_Z_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				GeodatenFactory.eINSTANCE.createHoehenlinie()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				GeodatenFactory.eINSTANCE
						.createHoehenlinie_Allg_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				GeodatenFactory.eINSTANCE.createHoehenlinie_Form_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				GeodatenFactory.eINSTANCE
						.createHoehenlinie_Laenge_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				GeodatenFactory.eINSTANCE.createHoehenpunkt()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				GeodatenFactory.eINSTANCE
						.createHoehenpunkt_Allg_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				GeodatenFactory.eINSTANCE.createHoehenpunkt_Datum_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				GeodatenFactory.eINSTANCE.createHoehenpunkt_Hoehe_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				GeodatenFactory.eINSTANCE.createHSystem_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				GeodatenFactory.eINSTANCE.createKantenname_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				GeodatenFactory.eINSTANCE.createKnotenname_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				GeodatenFactory.eINSTANCE.createNeigung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				GeodatenFactory.eINSTANCE.createOertlichkeit()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				GeodatenFactory.eINSTANCE
						.createOertlichkeit_Abkuerzung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				GeodatenFactory.eINSTANCE
						.createOertlichkeit_Allg_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				GeodatenFactory.eINSTANCE.createOertlichkeit_Art_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				GeodatenFactory.eINSTANCE
						.createOertlichkeit_Bezeichnung_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				GeodatenFactory.eINSTANCE
						.createOertlichkeit_Gueltig_Ab_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				GeodatenFactory.eINSTANCE
						.createOertlichkeit_Gueltig_Bis_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				GeodatenFactory.eINSTANCE
						.createOertlichkeit_Kurzname_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				GeodatenFactory.eINSTANCE
						.createOertlichkeit_Langname_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				GeodatenFactory.eINSTANCE.createPlan_Quelle_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				GeodatenFactory.eINSTANCE.createStrecke()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				GeodatenFactory.eINSTANCE
						.createStrecke_Bezeichnung_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				GeodatenFactory.eINSTANCE.createStrecke_Bremsweg()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				GeodatenFactory.eINSTANCE.createStrecke_Meter_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				GeodatenFactory.eINSTANCE.createStrecke_Punkt()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				GeodatenFactory.eINSTANCE.createStrecke_Richtung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				GeodatenFactory.eINSTANCE.createTB_Art_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				GeodatenFactory.eINSTANCE.createTB_Beschreibung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				GeodatenFactory.eINSTANCE.createTechnischer_Bereich()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				GeodatenFactory.eINSTANCE.createTechnischer_Punkt()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				GeodatenFactory.eINSTANCE.createTOP_Anschluss_A_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				GeodatenFactory.eINSTANCE.createTOP_Anschluss_B_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				GeodatenFactory.eINSTANCE.createTOP_Kante()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				GeodatenFactory.eINSTANCE
						.createTOP_Kante_Allg_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				GeodatenFactory.eINSTANCE.createTOP_Knoten()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				GeodatenFactory.eINSTANCE.createTOP_Laenge_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				GeodatenFactory.eINSTANCE.createTP_Art_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				GeodatenFactory.eINSTANCE.createTP_Beschreibung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				GeodatenFactory.eINSTANCE
						.createTrasse_Kante_child_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				GeodatenFactory.eINSTANCE.createUeberhoehung()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				GeodatenFactory.eINSTANCE
						.createUeberhoehung_Allg_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				GeodatenFactory.eINSTANCE
						.createUeberhoehung_Datum_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				GeodatenFactory.eINSTANCE
						.createUeberhoehung_Hoehe_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				GeodatenFactory.eINSTANCE.createUeberhoehungslinie()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				GeodatenFactory.eINSTANCE
						.createUeberhoehungslinie_Allg_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				GeodatenFactory.eINSTANCE
						.createUeberhoehungslinie_Form_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				GeodatenFactory.eINSTANCE
						.createUeberhoehungslinie_Laenge_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				GeodatenFactory.eINSTANCE.createV_Profil_Art_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				GleisFactory.eINSTANCE.createBaubereich_Art_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				GleisFactory.eINSTANCE
						.createBez_Gleis_Bezeichnung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				GleisFactory.eINSTANCE.createFahrstrom_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				GleisFactory.eINSTANCE.createGeschwindigkeit_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				GleisFactory.eINSTANCE.createGleis_Abschnitt()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				GleisFactory.eINSTANCE.createGleis_Art()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				GleisFactory.eINSTANCE.createGleis_Baubereich()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				GleisFactory.eINSTANCE.createGleis_Bezeichnung()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				GleisFactory.eINSTANCE
						.createGleis_Bezeichnung_Bezeichnung_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				GleisFactory.eINSTANCE.createGleis_Fahrbahn()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				GleisFactory.eINSTANCE.createGleis_Lichtraum()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				GleisFactory.eINSTANCE.createGleis_Schaltgruppe()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				GleisFactory.eINSTANCE.createGleisart_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				GleisFactory.eINSTANCE.createKonstruktion_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				GleisFactory.eINSTANCE.createLichtraumprofil_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BedienungFactory.eINSTANCE.createA_Wert_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BedienungFactory.eINSTANCE.createAnbindung_IB2_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BedienungFactory.eINSTANCE.createAnbindung_IB3_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BedienungFactory.eINSTANCE.createB_Wert_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BedienungFactory.eINSTANCE.createBedien_Anrueckabschnitt()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BedienungFactory.eINSTANCE
						.createBedien_Anrueckabschnitt_Bezeichnung_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BedienungFactory.eINSTANCE.createBedien_Anzeige_Element()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BedienungFactory.eINSTANCE
						.createBedien_Anzeige_Element_Allg_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BedienungFactory.eINSTANCE
						.createBedien_Anzeige_Element_Bezeichnung_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BedienungFactory.eINSTANCE.createBedien_Bezirk()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BedienungFactory.eINSTANCE
						.createBedien_Bezirk_Adressformel_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BedienungFactory.eINSTANCE
						.createBedien_Bezirk_Allg_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BedienungFactory.eINSTANCE
						.createBedien_Bezirk_Anhaenge_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BedienungFactory.eINSTANCE
						.createBedien_Einricht_Bauart_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BedienungFactory.eINSTANCE
						.createBedien_Einricht_Oertl_Bez_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BedienungFactory.eINSTANCE
						.createBedien_Einricht_Oertlich_Allg_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BedienungFactory.eINSTANCE
						.createBedien_Einrichtung_Oertlich()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BedienungFactory.eINSTANCE
						.createBedien_Einrichtung_Oertlich_Bezeichnung_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BedienungFactory.eINSTANCE.createBedien_GBT()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BedienungFactory.eINSTANCE
						.createBedien_GBT_Allg_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BedienungFactory.eINSTANCE.createBedien_Oberflaeche()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BedienungFactory.eINSTANCE
						.createBedien_Oberflaeche_Anhaenge_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BedienungFactory.eINSTANCE.createBedien_Oberflaeche_Bild()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BedienungFactory.eINSTANCE
						.createBedien_Oberflaeche_Bild_Allg_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BedienungFactory.eINSTANCE.createBedien_Oertlichkeit()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BedienungFactory.eINSTANCE
						.createBedien_Oertlichkeit_Kennzahlen_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BedienungFactory.eINSTANCE.createBedien_Platz()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BedienungFactory.eINSTANCE
						.createBedien_Platz_Allg_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BedienungFactory.eINSTANCE.createBedien_Platz_Art_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BedienungFactory.eINSTANCE.createBedien_Standort()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BedienungFactory.eINSTANCE
						.createBedien_Standort_Bezeichnung_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BedienungFactory.eINSTANCE.createBedien_Zentrale()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BedienungFactory.eINSTANCE
						.createBedien_Zentrale_Bezeichnung_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BedienungFactory.eINSTANCE
						.createBedienplatzbezeichnung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BedienungFactory.eINSTANCE
						.createBedienplatznummer_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BedienungFactory.eINSTANCE.createBedienraumnummer_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BedienungFactory.eINSTANCE
						.createBetriebsstellenbezeichner_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BedienungFactory.eINSTANCE
						.createBez_Bed_Anrueckabschnitt_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BedienungFactory.eINSTANCE
						.createBez_Bed_Anzeige_Element_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BedienungFactory.eINSTANCE.createBez_Bed_Zentrale_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BedienungFactory.eINSTANCE.createBezeichnung_BSO_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BedienungFactory.eINSTANCE
						.createBSO_IP_AB_Teilsystem_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BedienungFactory.eINSTANCE
						.createBSO_IP_Adressblock_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BedienungFactory.eINSTANCE
						.createBSO_Teilsystem_Art_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BedienungFactory.eINSTANCE.createC_Wert_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BedienungFactory.eINSTANCE.createDD_Wert_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BedienungFactory.eINSTANCE.createHersteller_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BedienungFactory.eINSTANCE
						.createHupe_Anschaltzeit_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BedienungFactory.eINSTANCE
						.createIP_Adressblock_Blau_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BedienungFactory.eINSTANCE
						.createIP_Adressblock_Blau_V4_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BedienungFactory.eINSTANCE
						.createIP_Adressblock_Blau_V6_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BedienungFactory.eINSTANCE
						.createIP_Adressblock_Grau_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BedienungFactory.eINSTANCE
						.createIP_Adressblock_Grau_V4_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BedienungFactory.eINSTANCE
						.createIP_Adressblock_Grau_V6_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BedienungFactory.eINSTANCE.createKennzahl_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BedienungFactory.eINSTANCE.createMelder_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BedienungFactory.eINSTANCE
						.createOberflaeche_Bildart_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BedienungFactory.eINSTANCE
						.createOberflaeche_Zustaendigkeit_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BedienungFactory.eINSTANCE.createRegionalbereich_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BedienungFactory.eINSTANCE.createRueckschauzeit_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BedienungFactory.eINSTANCE.createSchalter_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BedienungFactory.eINSTANCE.createSchrankreihe_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BedienungFactory.eINSTANCE
						.createSteuerbezirksname_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BedienungFactory.eINSTANCE
						.createSteuerbezirksnummer_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BedienungFactory.eINSTANCE.createTaste_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BedienungFactory.eINSTANCE.createVorschauzeit_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BedienungFactory.eINSTANCE.createX_Wert_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BedienungFactory.eINSTANCE.createY_Wert_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BedienungFactory.eINSTANCE.createYY_Wert_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Ansteuerung_ElementFactory.eINSTANCE
						.createAEA_Allg_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Ansteuerung_ElementFactory.eINSTANCE
						.createAEA_Energieversorgung_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Ansteuerung_ElementFactory.eINSTANCE
						.createAEA_GFK_IP_Adressblock_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Ansteuerung_ElementFactory.eINSTANCE
						.createAussenelementansteuerung()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Ansteuerung_ElementFactory.eINSTANCE
						.createAussenelementansteuerung_Art_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Ansteuerung_ElementFactory.eINSTANCE
						.createAussenelementansteuerung_Bezeichnung_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Ansteuerung_ElementFactory.eINSTANCE
						.createBandbreite_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Ansteuerung_ElementFactory.eINSTANCE.createBauart_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Ansteuerung_ElementFactory.eINSTANCE
						.createBezeichnung_AEA_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Ansteuerung_ElementFactory.eINSTANCE
						.createBezeichnung_ESTW_ZE_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Ansteuerung_ElementFactory.eINSTANCE
						.createBezeichnung_Stellwerk_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Ansteuerung_ElementFactory.eINSTANCE
						.createBezeichnung_TSO_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Ansteuerung_ElementFactory.eINSTANCE
						.createEnergieversorgung_Art_Ersatz_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Ansteuerung_ElementFactory.eINSTANCE
						.createEnergieversorgung_Art_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Ansteuerung_ElementFactory.eINSTANCE
						.createESTW_ZE_Energieversorgung_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Ansteuerung_ElementFactory.eINSTANCE
						.createESTW_Zentraleinheit()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Ansteuerung_ElementFactory.eINSTANCE
						.createESTW_Zentraleinheit_Allg_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Ansteuerung_ElementFactory.eINSTANCE
						.createESTW_Zentraleinheit_Bezeichnung_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Ansteuerung_ElementFactory.eINSTANCE
						.createGFK_Kategorie_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Ansteuerung_ElementFactory.eINSTANCE
						.createHersteller_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Ansteuerung_ElementFactory.eINSTANCE
						.createIP_Adressblock_Blau_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Ansteuerung_ElementFactory.eINSTANCE
						.createIP_Adressblock_Blau_V4_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Ansteuerung_ElementFactory.eINSTANCE
						.createIP_Adressblock_Blau_V6_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Ansteuerung_ElementFactory.eINSTANCE
						.createIP_Adressblock_Grau_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Ansteuerung_ElementFactory.eINSTANCE
						.createIP_Adressblock_Grau_V4_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Ansteuerung_ElementFactory.eINSTANCE
						.createIP_Adressblock_Grau_V6_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Ansteuerung_ElementFactory.eINSTANCE
						.createMedium_Art_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Ansteuerung_ElementFactory.eINSTANCE
						.createNetz_Art_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Ansteuerung_ElementFactory.eINSTANCE
						.createRegionalbereich_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Ansteuerung_ElementFactory.eINSTANCE
						.createStandort_Beschreibung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Ansteuerung_ElementFactory.eINSTANCE.createStell_Bereich()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Ansteuerung_ElementFactory.eINSTANCE.createStellelement()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Ansteuerung_ElementFactory.eINSTANCE
						.createTechnik_Art_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Ansteuerung_ElementFactory.eINSTANCE
						.createTechnik_Beschreibung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Ansteuerung_ElementFactory.eINSTANCE.createTechnik_Standort()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Ansteuerung_ElementFactory.eINSTANCE
						.createTechnik_Standort_Bezeichnung_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Ansteuerung_ElementFactory.eINSTANCE
						.createTSO_IP_AB_Teilsystem_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Ansteuerung_ElementFactory.eINSTANCE
						.createTSO_IP_Adressblock_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Ansteuerung_ElementFactory.eINSTANCE
						.createTSO_Teilsystem_Art_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Ansteuerung_ElementFactory.eINSTANCE
						.createTueranschlag_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Ansteuerung_ElementFactory.eINSTANCE.createUebertragungsweg()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Ansteuerung_ElementFactory.eINSTANCE
						.createUebertragungsweg_Art_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Ansteuerung_ElementFactory.eINSTANCE
						.createUebertragungsweg_Technik_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Ansteuerung_ElementFactory.eINSTANCE.createUnterbringung()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Ansteuerung_ElementFactory.eINSTANCE
						.createUnterbringung_Allg_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Ansteuerung_ElementFactory.eINSTANCE
						.createUnterbringung_Art_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Ansteuerung_ElementFactory.eINSTANCE
						.createUnterbringung_Befestigung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Ansteuerung_ElementFactory.eINSTANCE
						.createUnterbringung_Polygonzug_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Ansteuerung_ElementFactory.eINSTANCE
						.createZusatzinformation_Stellwerk_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Medien_und_TrassenFactory.eINSTANCE
						.createAder_Durchmesser_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Medien_und_TrassenFactory.eINSTANCE
						.createAder_Querschnitt_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Medien_und_TrassenFactory.eINSTANCE
						.createAder_Reserve_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Medien_und_TrassenFactory.eINSTANCE
						.createAnzahl_Verseilelemente_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Medien_und_TrassenFactory.eINSTANCE
						.createBezeichnung_Kabel_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Medien_und_TrassenFactory.eINSTANCE
						.createBezeichnung_Kabel_Verteilpunkt_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Medien_und_TrassenFactory.eINSTANCE
						.createInduktionsschutz_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Medien_und_TrassenFactory.eINSTANCE.createKabel()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Medien_und_TrassenFactory.eINSTANCE
						.createKabel_Allg_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Medien_und_TrassenFactory.eINSTANCE
						.createKabel_Art_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Medien_und_TrassenFactory.eINSTANCE
						.createKabel_Bezeichnung_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Medien_und_TrassenFactory.eINSTANCE
						.createKabel_Element_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Medien_und_TrassenFactory.eINSTANCE
						.createKabel_Laenge_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Medien_und_TrassenFactory.eINSTANCE
						.createKabel_Typ_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Medien_und_TrassenFactory.eINSTANCE
						.createKabel_Verteilpunkt()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Medien_und_TrassenFactory.eINSTANCE
						.createKabel_Verteilpunkt_Art_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Medien_und_TrassenFactory.eINSTANCE
						.createKabel_Verteilpunkt_Bezeichnung_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Medien_und_TrassenFactory.eINSTANCE
						.createNagetierschutz_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Medien_und_TrassenFactory.eINSTANCE.createTrasse_Kante()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Medien_und_TrassenFactory.eINSTANCE
						.createTrasse_Kante_Art_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Medien_und_TrassenFactory.eINSTANCE.createTrasse_Knoten()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Medien_und_TrassenFactory.eINSTANCE
						.createTrasse_Knoten_Art_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Medien_und_TrassenFactory.eINSTANCE
						.createTrasse_Nutzer_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Medien_und_TrassenFactory.eINSTANCE
						.createVerseilart_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE
						.createID_Anforderer_Element_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE.createID_Anforderung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE
						.createID_Anhang_ohne_Proxy_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE.createID_Anhang_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE
						.createID_AnhangBearbeitungsvermerk_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE
						.createID_Anschluss_Element_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE
						.createID_ATO_TS_Instanz_ohne_Proxy_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE.createID_ATO_TS_Instanz_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE
						.createID_Ausgabe_Fachdaten_ohne_Proxy_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE
						.createID_Aussenelementansteuerung_ohne_Proxy_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE
						.createID_Aussenelementansteuerung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE
						.createID_Bahnsteig_Anlage_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE
						.createID_Bahnsteig_Kante_ohne_Proxy_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE
						.createID_Bahnsteig_Kante_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE
						.createID_Balise_ohne_Proxy_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE.createID_Balise_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE.createID_Basis_Objekt_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE
						.createID_Bedien_Anzeige_Element_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE.createID_Bedien_Bezirk_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE
						.createID_Bedien_Einrichtung_Oertlich_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE
						.createID_Bedien_Oberflaeche_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE
						.createID_Bedien_Standort_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE
						.createID_Bedien_Zentrale_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE
						.createID_Befestigung_Bauwerk_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE.createID_Beginn_Bereich_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE
						.createID_Bezugspunkt_Positionierung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE.createID_Bezugspunkt_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE
						.createID_Binaerdaten_ohne_Proxy_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE.createID_Block_Anlage_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE.createID_Block_Element_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE.createID_Block_Strecke_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE
						.createID_BUE_Anlage_ohne_Proxy_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE.createID_BUE_Anlage_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE
						.createID_BUE_Bedien_Anzeige_Element_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE
						.createID_BUE_Einschaltung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE
						.createID_BUE_Gleisbezogener_Gefahrraum_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE
						.createID_BUE_Schnittstelle_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE
						.createID_BUE_WS_Fstr_Zuordnung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE
						.createID_Datenpunkt_ohne_Proxy_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE.createID_Datenpunkt_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE
						.createID_DP_Bezug_Funktional_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE.createID_Einschaltpunkt_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE.createID_Element_Grenze_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE.createID_Element_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE
						.createID_Element_Unterbringung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE
						.createID_Energie_Eingang_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE.createID_Energie_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE
						.createID_ESTW_Zentraleinheit_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE.createID_ETCS_Kante_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE
						.createID_ETCS_Knoten_ohne_Proxy_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE.createID_ETCS_Knoten_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE.createID_EV_Modul_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE
						.createID_Fachtelegramm_ohne_Proxy_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE.createID_Fachtelegramm_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE.createID_Fla_Schutz_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE
						.createID_FMA_Anlage_Rangier_Frei_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE.createID_FMA_Anlage_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE.createID_FMA_Element_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE.createID_FMA_Komponente_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE
						.createID_Fortschaltung_Start_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE
						.createID_Fstr_Aneinander_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE
						.createID_Fstr_Ausschluss_Besonders_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE
						.createID_Fstr_DWeg_ohne_Proxy_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE.createID_Fstr_DWeg_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE
						.createID_Fstr_Fahrweg_ohne_Proxy_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE.createID_Fstr_Fahrweg_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE
						.createID_Fstr_Zug_Rangier_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE
						.createID_FT_Anschaltbedingung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE
						.createID_FT_Fahrweg_Teil_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE.createID_GEO_Art_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE.createID_GEO_Kante_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE.createID_GEO_Knoten_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE
						.createID_GEO_Punkt_ohne_Proxy_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE.createID_GEO_Punkt_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE
						.createID_GFR_Anlage_ohne_Proxy_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE
						.createID_Gleis_Abschnitt_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE
						.createID_Gleis_Bezeichnung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE.createID_Grenzzeichen_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE
						.createID_Handschalt_Wirkfunktion_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE.createID_Hoehenpunkt_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE
						.createID_Information_Eingang_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE
						.createID_Information_Primaer_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE
						.createID_Komponente_Programmiert_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE
						.createID_Lageplan_Blattschnitt_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE.createID_Lageplan_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE
						.createID_Lageplan_Zustand_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE
						.createID_LEU_Anlage_ohne_Proxy_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE.createID_LEU_Anlage_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE
						.createID_LEU_Bezug_Funktional_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE
						.createID_LEU_Schaltkasten_ohne_Proxy_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE.createID_LO_Einbau_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE
						.createID_Markante_Stelle_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE
						.createID_Markanter_Punkt_Gleis_Abschluss_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE
						.createID_Markanter_Punkt_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE.createID_NB_Element_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE.createID_NB_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE.createID_NB_Zone_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE
						.createID_Oertlichkeit_Ausgabe_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE
						.createID_Oertlichkeit_Proxy_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE.createID_Oertlichkeit_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE
						.createID_PlanPro_Schnittstelle_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE.createID_Planung_Einzel_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE
						.createID_Planungsgrundlage_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE
						.createID_PZB_Element_Bezugspunkt_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE
						.createID_PZB_Element_Mitnutzung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE.createID_PZB_Element_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE
						.createID_PZB_Element_Zuordnung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE.createID_Quellelement_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE.createID_RBC_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE
						.createID_Regelzeichnung_ohne_Proxy_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE.createID_Regelzeichnung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE.createID_Schalter_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE
						.createID_Schaltmittel_Zuordnung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE
						.createID_Schlosskombination_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE.createID_Schluessel_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE
						.createID_Schluesselsperre_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE
						.createID_Signal_Befestigung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE.createID_Signal_Fank_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE
						.createID_Signal_Gleisbezechnung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE
						.createID_Signal_ohne_Proxy_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE.createID_Signal_Rahmen_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE
						.createID_Signal_Signalbegriff_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE.createID_Signal_Start_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE.createID_Signal_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE.createID_Sonderanlage_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE.createID_Stellelement_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE.createID_Stellwerk_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE
						.createID_Strecke_Bremsweg_ohne_Proxy_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE.createID_Strecke_Punkt_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE.createID_Strecke_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE
						.createID_Technischer_Punkt_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE
						.createID_TOP_Kante_ohne_Proxy_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE.createID_TOP_Kante_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE.createID_TOP_Knoten_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE.createID_Trasse_Kante_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE.createID_Trasse_Knoten_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE.createID_Ueberhoehung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE
						.createID_Uebertragungsweg_Nach_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE
						.createID_Uebertragungsweg_Von_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE.createID_Umfahrpunkt_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE
						.createID_Unterbringung_ohne_Proxy_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE
						.createID_Unterbringung_Technik_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE.createID_Unterbringung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE.createID_Ur_Objekt_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE
						.createID_Verknuepftes_Element_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE
						.createID_W_Kr_Anlage_ohne_Proxy_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE.createID_W_Kr_Anlage_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE
						.createID_W_Kr_Gsp_Element_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE
						.createID_W_Kr_Gsp_Komponente_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE
						.createID_Weichenlaufkette_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE.createID_Ziel_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE.createID_ZL_DLP_Fstr_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE.createID_ZL_Fstr_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE
						.createID_ZL_Signalgruppe_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE.createID_ZL_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE
						.createID_ZLV_Bus_ohne_Proxy_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE.createID_ZLV_Bus_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE
						.createID_ZN_Anzeigefeld_Anstoss_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE.createID_ZN_Anzeigefeld_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE
						.createID_ZN_Fortschalt_Kriterium_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE.createID_ZN_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE
						.createID_ZN_Unterstation_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE.createID_ZN_ZBS_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE.createID_Zugeinwirkung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				VerweiseFactory.eINSTANCE
						.createID_Zweites_Haltfallkriterium_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ATOFactory.eINSTANCE
						.createAbstand_ATO_Halt_Vor_EoA_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ATOFactory.eINSTANCE.createATO_Segment_Profile()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ATOFactory.eINSTANCE
						.createATO_Segment_Profile_Bezeichnung_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ATOFactory.eINSTANCE.createATO_Timing_Point()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ATOFactory.eINSTANCE
						.createATO_Timing_Point_Allg_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ATOFactory.eINSTANCE
						.createATO_Timing_Point_Bezeichnung_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ATOFactory.eINSTANCE.createATO_TS_Instanz()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ATOFactory.eINSTANCE
						.createATO_TS_Instanz_Adresse_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ATOFactory.eINSTANCE.createBezeichnung_ATO_TP_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ATOFactory.eINSTANCE.createErreichungstoleranz_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ATOFactory.eINSTANCE.createHaltetoleranz_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ATOFactory.eINSTANCE.createNID_ATOTS_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ATOFactory.eINSTANCE.createNID_C_ATOTS_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ATOFactory.eINSTANCE.createNID_SP_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ATOFactory.eINSTANCE.createNID_TP_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				PlanProFactory.eINSTANCE.createAdresse_PLZ_Ort_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				PlanProFactory.eINSTANCE.createAdresse_Strasse_Nr_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				PlanProFactory.eINSTANCE.createAkteur()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				PlanProFactory.eINSTANCE.createAkteur_Allg_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				PlanProFactory.eINSTANCE.createAkteur_Zuordnung()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				PlanProFactory.eINSTANCE.createAusgabe_Fachdaten()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				PlanProFactory.eINSTANCE.createBauabschnitt_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				PlanProFactory.eINSTANCE.createBauphase_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				PlanProFactory.eINSTANCE
						.createBauzustand_Kurzbezeichnung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				PlanProFactory.eINSTANCE
						.createBauzustand_Langbezeichnung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				PlanProFactory.eINSTANCE.createBemerkung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				PlanProFactory.eINSTANCE.createBezeichnung_Anlage_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				PlanProFactory.eINSTANCE
						.createBezeichnung_Planung_Gruppe_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				PlanProFactory.eINSTANCE
						.createBezeichnung_Planung_Projekt_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				PlanProFactory.eINSTANCE
						.createBezeichnung_Unteranlage_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				PlanProFactory.eINSTANCE.createContainer_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				PlanProFactory.eINSTANCE
						.createDatum_Abschluss_Einzel_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				PlanProFactory.eINSTANCE
						.createDatum_Abschluss_Gruppe_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				PlanProFactory.eINSTANCE
						.createDatum_Abschluss_Projekt_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				PlanProFactory.eINSTANCE
						.createDatum_Regelwerksstand_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				PlanProFactory.eINSTANCE.createDatum_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				PlanProFactory.eINSTANCE.createDatum_Uebernahme_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				PlanProFactory.eINSTANCE.createE_Mail_Adresse_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				PlanProFactory.eINSTANCE
						.createErzeugung_Zeitstempel_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				PlanProFactory.eINSTANCE.createFachdaten_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				PlanProFactory.eINSTANCE
						.createFuehrende_Oertlichkeit_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				PlanProFactory.eINSTANCE.createIdent_Rolle_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				PlanProFactory.eINSTANCE.createIndex_Ausgabe_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				PlanProFactory.eINSTANCE.createInformativ_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				PlanProFactory.eINSTANCE
						.createKoordinatensystem_BB_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				PlanProFactory.eINSTANCE
						.createKoordinatensystem_PB_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				PlanProFactory.eINSTANCE
						.createLaufende_Nummer_Ausgabe_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				PlanProFactory.eINSTANCE
						.createLST_Objekte_Planungsbereich_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				PlanProFactory.eINSTANCE.createLST_Planung_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				PlanProFactory.eINSTANCE.createLST_Zustand()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				PlanProFactory.eINSTANCE.createName_Akteur_10_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				PlanProFactory.eINSTANCE.createName_Akteur_5_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				PlanProFactory.eINSTANCE.createName_Akteur_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				PlanProFactory.eINSTANCE.createName_Organisation_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				PlanProFactory.eINSTANCE
						.createObjektmanagement_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				PlanProFactory.eINSTANCE.createOrganisation()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				PlanProFactory.eINSTANCE
						.createOrganisationseinheit_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				PlanProFactory.eINSTANCE.createPlanPro_Schnittstelle()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				PlanProFactory.eINSTANCE
						.createPlanPro_Schnittstelle_Allg_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				PlanProFactory.eINSTANCE
						.createPlanPro_XSD_Version_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				PlanProFactory.eINSTANCE
						.createPlanung_E_Allg_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				PlanProFactory.eINSTANCE.createPlanung_E_Art_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				PlanProFactory.eINSTANCE
						.createPlanung_E_Ausgabe_Besonders_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				PlanProFactory.eINSTANCE
						.createPlanung_E_Handlung_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				PlanProFactory.eINSTANCE.createPlanung_Einzel()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				PlanProFactory.eINSTANCE
						.createPlanung_G_Allg_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				PlanProFactory.eINSTANCE
						.createPlanung_G_Art_Besonders_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				PlanProFactory.eINSTANCE
						.createPlanung_G_Fuehrende_Strecke_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				PlanProFactory.eINSTANCE
						.createPlanung_G_Schriftfeld_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				PlanProFactory.eINSTANCE.createPlanung_Gruppe()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				PlanProFactory.eINSTANCE
						.createPlanung_P_Allg_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				PlanProFactory.eINSTANCE.createPlanung_Phase_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				PlanProFactory.eINSTANCE.createPlanung_Projekt()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				PlanProFactory.eINSTANCE
						.createPolygone_Betrachtungsbereich_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				PlanProFactory.eINSTANCE
						.createPolygone_Planungsbereich_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				PlanProFactory.eINSTANCE
						.createPolygonzug_Betrachtungsbereich_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				PlanProFactory.eINSTANCE
						.createPolygonzug_Planungsbereich_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				PlanProFactory.eINSTANCE.createProjekt_Nummer_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				PlanProFactory.eINSTANCE
						.createReferenz_Planung_Basis_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				PlanProFactory.eINSTANCE
						.createReferenz_Vergleich_Besonders_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				PlanProFactory.eINSTANCE.createStrecke_Abschnitt_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				PlanProFactory.eINSTANCE.createStrecke_Km_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				PlanProFactory.eINSTANCE.createStrecke_Nummer_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				PlanProFactory.eINSTANCE.createTelefonnummer_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				PlanProFactory.eINSTANCE.createUntergewerk_Art_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				PlanProFactory.eINSTANCE
						.createVerantwortliche_Stelle_DB_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				PlanProFactory.eINSTANCE
						.createVergleich_Ausgabestand_Basis_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				PlanProFactory.eINSTANCE
						.createVergleichstyp_Besonders_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				PlanProFactory.eINSTANCE.createWerkzeug_Name_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				PlanProFactory.eINSTANCE.createWerkzeug_Version_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnsteigFactory.eINSTANCE.createBahnsteig_Anlage()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnsteigFactory.eINSTANCE
						.createBahnsteig_Anlage_Bezeichnung_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnsteigFactory.eINSTANCE.createBahnsteig_Dach()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnsteigFactory.eINSTANCE.createBahnsteig_Kante()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnsteigFactory.eINSTANCE
						.createBahnsteig_Kante_Allg_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnsteigFactory.eINSTANCE
						.createBahnsteig_Kante_Bezeichnung_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnsteigFactory.eINSTANCE.createBahnsteig_Zugang()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnsteigFactory.eINSTANCE
						.createBahnsteig_Zugang_Allg_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnsteigFactory.eINSTANCE
						.createBahnsteig_Zugang_Art_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnsteigFactory.eINSTANCE
						.createBezeichnung_Bahnsteig_Anlage_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnsteigFactory.eINSTANCE
						.createBezeichnung_Bahnsteig_Kante_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnsteigFactory.eINSTANCE.createHauptzugang_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnsteigFactory.eINSTANCE.createLage_Zum_Gleis_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnsteigFactory.eINSTANCE.createSystemhoehe_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createAbstand_Bes_Langer_Einfahrweg_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createAbstand_Datenpunkt_EH_EM_Folgesignal_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createAbstand_Datenpunkt_EP_TPI_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createAbstand_Datenpunkt_TPI_Folgesignal_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createAbstand_Einmesspunkt_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createAbstand_Grenze_Bereich_C_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createAbstand_Reduziert_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createAnlagenteil_Sonstige_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createAnordnung_Im_DP_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createAnwendung_ESG_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createAnwendung_GNT_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createAnwendung_Sonst_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createAnwendungssystem_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createAnzahl_Voll_LEU_Kalkuliert_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createAnzeigetext_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createArt_Bedingung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createAusgang_Nr_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createAusrichtung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createAusstieg_ETCS_Sperre_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE.createBalise()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createBalise_Allg_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createBalise_Geraetestand_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createBalisenhalter_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createBaseline_System_Version_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createBedingung_Besondere_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createBedingung_PZB_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createBedingung_Signal_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createBedingung_Sonstige_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createBedingung_Weiche_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createBedingung_Weichenlage_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createBez_Strecke_BTS_1_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createBez_Strecke_BTS_2_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createBez_Strecke_BTS_3_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createBez_ZUB_Bereichsgrenze_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createBezeichnung_ETCS_Kante_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createBezeichnung_LEU_Anlage_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createBezeichnung_RBC_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createBezeichnung_ZUB_SE_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createBezeichnung_ZUB_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createBgrenze_Nach_ESG_Bed_Ausstieg_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createBgrenze_Nach_L2_Bed_Einstieg_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createBgrenze_Nach_LZB_Bed_Einstieg_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createBgrenze_Nach_Ohne_Bed_Einstieg_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createBgrenze_Nach_PZB_Bed_Einstieg_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createBgrenze_Nach_ZBS_Bed_Einstieg_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createBgrenze_RBC_Wechsel_BTS_Kette_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE.createBinaerdaten()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createBinaerdaten_Datei_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createD_LEVELTR_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createDateiname_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createDateityp_Binaerdatei_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE.createDaten_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE.createDatenpunkt()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createDatenpunkt_Allg_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createDatenpunkt_Beschreibung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createDatenpunkt_Einmesspunkt_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createDatenpunkt_Laenge_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE.createDatenpunkt_Link()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createDelta_VGES_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createDelta_VLES_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createDelta_VZES_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE.createDP_ATO_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createDP_Bezug_Funktional_Art_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createDP_Bezug_Funktional_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createDP_ETCS_Adresse_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createDP_Link_Art_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createDP_Telegramm_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createDP_Telegramm_ESG_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createDP_Typ_Art_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createDP_Typ_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createDP_Typ_ESG_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createDP_Typ_ETCS_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createDP_Typ_GESG_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createDP_Typ_GETCS_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createDP_Typ_GGNT_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createDP_Typ_GNT_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createDP_Typ_GSonst_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createDP_Typ_GTrans_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createDP_Typ_GZBS_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createDP_Typ_Sonst_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createDP_Typ_Trans_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createDP_Typ_V_La_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createDP_Typ_ZBS_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createDP_Verlinkt_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createDunkelschaltanstoss_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createDWeg_Intervall_200_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createDWeg_Intervall_50_200_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createDWeg_Intervall_50_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createEingang_Gepuffert_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createEinstieg_Erlaubt_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createEinstieg_Ohne_Rueckw_Sig_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createEinzeldatei_Art_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createEnergie_Eingang_Art_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createESG_Ind_Erlaeuterung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createESG_Ind_Parameter_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createESG_Ind_Parameterwert_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createESG_Individuelle_Merkmale_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createESG_Spezifische_Merkmale_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createETCS_Adresse_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createETCS_Gefahrpunktabstand_Abweichend_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE.createETCS_Kante()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createETCS_Kante_Bezeichnung_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE.createETCS_Knoten()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createETCS_Knoten_Art_Sonstige_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createETCS_Paketnummer_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createETCS_Par_Erlaeuterung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createETCS_Parametername_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createETCS_Parameterwert_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createETCS_Richtungsanzeige()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE.createETCS_Signal()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createETCS_Signal_Allg_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createETCS_Signal_DWeg_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createETCS_Signal_TBV_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createETCS_System_Version_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE.createETCS_W_Kr()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createETCS_W_Kr_MUKA_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE.createEV_Modul()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createEV_Modul_Art_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createEV_Modul_Ausgang_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createEV_Modul_Eingang_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createEV_Modul_Physisch_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createEV_Modul_Typ_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createEV_Modul_Virtuell_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createFabrikat_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE.createFachtelegramm()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createFT_Anschaltbedingung()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createFT_ESG_Merkmale_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createFT_ESG_Subtyp_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createFT_ESG_Typ_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createFT_ETCS_L2_Merkmale_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createFT_ETCS_L2_Typ_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createFT_ETCS_Trans_Merkmale_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createFT_ETCS_Trans_Paket_41_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createFT_ETCS_Trans_Paket_N_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE.createFT_Fahrweg_Teil()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createFT_Fahrweg_Teil_Allg_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createFT_Fahrweg_Teile_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createFT_GNT_Merkmale_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createFT_GNT_Punktart_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createFT_Hinweis_Funktion_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createFT_ZBS_Merkmale_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createFT_ZBS_Merkmale_La_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createFT_ZBS_Typ_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createFW_Teil_Nummer_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createGruppen_ID_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createHarter_Ausstieg_Aus_L2_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createHersteller_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createHinweis_Balisenbefestigung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createIndividualisierung_Weitere_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createIst_Befahren_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createKm_BTS_1_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createKm_BTS_2_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createKm_BTS_3_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createKnoten_Auf_TOP_Kante_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createKonfigurationskennung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createL_ACKLEVELTR_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createLaenge_1_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createLaenge_Ausfuehrungsbereich_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createLaenge_Gestufte_V_Signalisierung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createLaenge_Soll_Mind_150_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createLeistungsbedarf_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE.createLEU_Anlage()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createLEU_Anlage_Allg_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createLEU_Anlage_Art_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createLEU_Anlage_Bezeichnung_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createLEU_Ausgang_Nr_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE.createLEU_Modul()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createLEU_Modul_Allg_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createLEU_Modul_Art_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createLEU_Modul_Ausgang_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createLEU_Modul_Bezeichnung_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createLEU_Modul_Geraetestand_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createLEU_Modul_Typ_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE.createLEU_Schaltkasten()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createLEU_Schaltkasten_Bezeichnung_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createLEU_Schaltkasten_Energie_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createLEU_Schaltkasten_Position_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createLEU_Schaltkasten_Typ_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createLEU_Steuernde_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createLfd_Nr_Am_Bezugspunkt_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createLink_Distanz_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE.createLLA_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE.createLM_G_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createLT_Binaerdatei_Hilfe_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createLT_Binaerdaten_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE.createLuft_Telegramm()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createM_LEVELTR_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createMassgebende_Neig_1_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createMassgebende_Neig_Schutzstrecke_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createMassgebende_Neigung_Mind_150_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createMassgebende_Neigung_Mind_Sig_150_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createMassgebende_Neigung_Mind_Sig_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createMastschild_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createMax_Leistung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createMax_Unterbrechungszeit_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createMetallteil_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createMetallteil_Kategorie_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createMetallteil_Laenge_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createModulnummer_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createMontageabweichung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createNeigung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createNennleistung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE.createNID_BG_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE.createNID_C_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createNID_RBC_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createNID_STM_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createNID_TSR_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createNummer_Schaltkasten_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createOberstrombegrenzung_Gueterzug_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createOberstrombegrenzung_Reisezug_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createPort_Nr_Ausg_Physisch_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createPosition_Sonstige_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createPosition_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createPrimaerquelle_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createPrioritaet_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createProg_Datei_Einzel_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createProg_Datei_Gruppe()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createProjektierungsfall_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE.createRBC()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createRBC_Allg_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createRBC_Bezeichnung_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createRBC_ETCS_System_Version_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createRBC_SRS_Version_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createRekursion_2_Nr_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createRekursion_Nr_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createRufnummer_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE.createSBE_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createSchutzstrecke_Erforderlich_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createSchutzstrecke_Vorhanden_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createSolllaenge_Mind_Sig_150_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createSolllaenge_Mind_Sig_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createSonstige_Standortangabe_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createSpannung_Art_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createSpannung_Toleranz_Obere_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createSpannung_Toleranz_Untere_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createSRS_Version_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createStandortangabe_Balisenschild_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createStart_W_Element_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE.createSTZ_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createSystem_Vor_Grenze_Besonders_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createSystem_Vor_Grenze_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createTBV_Meldepunkt_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createTBV_Tunnelbereich_Laenge_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createTBV_Tunnelsignal_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createTelegramm_Index_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createTelegrammnummer_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createText_Bedingung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createTextmeldung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createUeberbrueckung_EV_Unterbrechung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createUeberwachung_Laenge_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createUmfahrstrasse_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createUntergruppen_ID_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createV_Befehl_R_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createV_Befehl_Z_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE.createV_Frei_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createV_Start_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE.createV_Ziel_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createV_Zul_Strecke_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createVBC_Kennung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createVBC_NID_C_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createVBC_Setzen_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createVBC_Timer_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createVerbot_Anhalten_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createVerbot_Regenerative_Bremse_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createVerbot_WB_Art_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createVerkuerzter_Abstand_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createVerwendung_Als_Rueckfall_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createVerwendung_Hilfe_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createVerwendung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE.createVGR_1_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE.createVGR_2_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE.createVGR_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE.createVLA_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createVorsignalabstand_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE.createVZ_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createW_Anschluss_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE.createW_Lage_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createWirkrichtung_In_Datenpunkt_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createWirksam_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createZBS_La_Bereich_Distanz_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createZBS_La_Bereich_Geschwindigkeit_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createZBS_La_Bereich_Laenge_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createZBS_La_Bereich_Neigung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createZBS_Merkmale_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createZBS_Reaktion_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createZBS_Schutzstrecke()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createZBS_Schutzstrecke_Allg_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE.createZBS_Signal()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createZBS_Signal_Signalabstand_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createZiel_DP_Ausrichtung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createZiel_Ist_Fahrwegende_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createZiel_W_Element_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE.createZLA_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createZUB_Bereichsgrenze()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createZUB_Bereichsgrenze_Allg_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createZUB_Bereichsgrenze_Bezeichnung_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createZUB_Bereichsgrenze_Nach_ESG_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createZUB_Bereichsgrenze_Nach_GNT_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createZUB_Bereichsgrenze_Nach_L2_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createZUB_Bereichsgrenze_Nach_L2_Von_ESG_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createZUB_Bereichsgrenze_Nach_LZB_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createZUB_Bereichsgrenze_Nach_Ohne_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createZUB_Bereichsgrenze_Nach_PZB_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createZUB_Bereichsgrenze_Nach_Sonstige_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createZUB_Bereichsgrenze_Nach_ZBS_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createZUB_Bgrenze_RBC_Wechsel_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createZUB_SE_Ausruestung_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createZUB_Streckeneigenschaft()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createZUB_Streckeneigenschaft_Bezeichnung_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BlockFactory.eINSTANCE.createAuto_Erlaubnisholen_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BlockFactory.eINSTANCE
						.createAuto_Erlaubnisruecklauf_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BlockFactory.eINSTANCE.createBetriebsfuehrung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BlockFactory.eINSTANCE.createBlock_Anlage()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BlockFactory.eINSTANCE
						.createBlock_Anlage_Allg_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BlockFactory.eINSTANCE.createBlock_Bauform_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BlockFactory.eINSTANCE.createBlock_Element()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BlockFactory.eINSTANCE
						.createBlock_Element_Allg_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BlockFactory.eINSTANCE
						.createBlock_Element_Erlaubnis_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BlockFactory.eINSTANCE.createBlock_Strecke()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BlockFactory.eINSTANCE
						.createBlock_Strecke_Allg_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BlockFactory.eINSTANCE.createBremsweg_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BlockFactory.eINSTANCE
						.createErlaubnis_Staendig_Vorhanden_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BlockFactory.eINSTANCE
						.createErlaubnisabgabespeicherung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BlockFactory.eINSTANCE.createErlaubnisholen_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BlockFactory.eINSTANCE.createRueckblockwecker_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BlockFactory.eINSTANCE.createSchaltung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BlockFactory.eINSTANCE.createSchutzuebertrager_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BlockFactory.eINSTANCE.createStrecke_Art_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BlockFactory.eINSTANCE
						.createStreckengeschwindigkeit_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BlockFactory.eINSTANCE
						.createTraktion_Art_Elektrisch_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BlockFactory.eINSTANCE.createVorblockwecker_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BlockFactory.eINSTANCE.createZugbeeinflussung_Art_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BlockFactory.eINSTANCE.createZusatzinformation_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE
						.createAbstand_Gehweg_Fahrbahn_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE
						.createAkustik_Fussgaenger_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE.createAusrichtung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE
						.createAusrichtung_Winkel_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE.createAuto_Het_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE.createBaulast_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE.createBaumprofil_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE
						.createBeeinflussung_Strassenverkehr_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE
						.createBez_Schrankenantrieb_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE
						.createBezeichnung_BUE_GFR_Eckpunkt_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE
						.createBezeichnung_GFR_Element_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE
						.createBezeichnung_GFR_Tripelspiegel_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE
						.createBezeichnung_Verkehrszeichen_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE.createBlitzpfeil_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE
						.createBUE_Abhaengigkeit_Fue_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE.createBUE_Anlage()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE
						.createBUE_Anlage_Allg_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE
						.createBUE_Anlage_Fuss_Rad_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE.createBUE_Anlage_Strasse()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE
						.createBUE_Anlage_Strasse_Allg_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE.createBUE_Anlage_V()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE
						.createBUE_Anlage_V_Allg_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE.createBUE_Ausschaltung()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE.createBUE_Bauart_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE
						.createBUE_Bedien_Anz_Element_Allg_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE
						.createBUE_Bedien_Anzeige_Element()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE.createBUE_Buestra_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE
						.createBUE_Deckendes_Signal_Zuordnung()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE.createBUE_Einschaltung()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE
						.createBUE_Einschaltung_Hp_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE
						.createBUE_Einschaltung_Zuordnung()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE
						.createBUE_Funktionsueberwachung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE
						.createBUE_Gefahrraum_Eckpunkt()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE
						.createBUE_Gefahrraum_Eckpunkt_Bezeichnung_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE
						.createBUE_Gleisbezogener_Gefahrraum()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE
						.createBUE_Handschalteinrichtung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE.createBUE_Kante()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE.createBUE_Kreuzungsplan()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE
						.createBUE_Kreuzungsplan_Koordinaten_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE.createBUE_Mit_GFR_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE
						.createBUE_Nachlaufzeit_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE.createBUE_Neigung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE.createBUE_Schnittstelle()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE
						.createBUE_Schnittstelle_Allg_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE
						.createBUE_Sicherungsart_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE
						.createBUE_Sicherungszeit_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE
						.createBUE_Spezifisches_Signal()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE.createBUE_Strasse_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE.createBUE_Technik_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE
						.createBUE_Vorlaufzeit_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE.createBUE_WS_Fstr_Zuordnung()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE
						.createEinschaltverz_Errechnet_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE
						.createEinschaltverz_Gewaehlt_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE
						.createErsatzstecker_Gleisbezogen_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE
						.createFahrbahn_Befestigung_Gleis_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE
						.createFahrbahn_Befestigung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE
						.createFahrbahn_Breite_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE
						.createFue_Schaltfall_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE
						.createFuss_Radweg_Art_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE
						.createFuss_Radweg_Seite_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE.createGFR_Anlage()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE
						.createGFR_Anlage_Allg_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE.createGFR_Art_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE.createGFR_Element()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE
						.createGFR_Element_Bezeichnung_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE.createGFR_Neigung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE.createGFR_Tripelspiegel()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE
						.createGFR_Tripelspiegel_Allg_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE
						.createGFR_Tripelspiegel_Bezeichnung_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE.createGFR_Typ_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE.createGitterbehang_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE.createGleis_Am_Bue_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE.createHaltezeit_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE.createHersteller_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE
						.createHp_Ersatzstecker_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE
						.createKlassifizierung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE
						.createKontrastblende_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE
						.createKreuzungswinkel_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE
						.createKurzzugschaltung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE.createLagerung_Art_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE.createLFUE_Impuls_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE.createLieferlaenge_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE
						.createMontage_Ausgleichsgewichte_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE
						.createMontage_Besonders_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE.createMontagehoehe_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE
						.createOptik_Durchmesser_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE
						.createOptik_Symbolmaske_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE.createPegel_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE
						.createPixel_Koordinate_X_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE
						.createPixel_Koordinate_Y_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE
						.createRaeumstrecke_DAB_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE
						.createRaeumstrecke_DBK_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE
						.createRaeumstrecke_DCK_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE
						.createRaeumstrecke_DSK_Strich_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE.createRaeumstrecke_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE
						.createRichtungspfeil_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE
						.createSA_Schrankenbaum_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE.createSchaltgruppe_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE
						.createSchaltmittel_Fstr_Zuordnung()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE.createSchrankenantrieb()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE
						.createSchrankenantrieb_Allg_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE
						.createSchrankenantrieb_Bezeichnung_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE.createSchutzbuegel_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE
						.createSicherheitsabstand_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE
						.createSignalverz_Errechnet_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE
						.createSignalverz_Gewaehlt_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE.createSperrlaenge_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE
						.createSperrstrecke_Fussgaenger_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE.createSperrstrecke_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE
						.createStoerhalt_Haltfall_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE
						.createStoerhalt_Merkhinweis_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE
						.createTeilsperrstrecke_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE
						.createTeilvorgabezeit_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE
						.createTragkopf_Verstellbar_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE
						.createV_Max_Schiene_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE
						.createV_Max_Strasse_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE
						.createV_Min_Fussweg_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE
						.createV_Min_Schiene_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE
						.createV_Min_Strasse_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE.createVerkehrszeichen()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE
						.createVerkehrszeichen_Allg_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE
						.createVerkehrszeichen_Andreaskreuz_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE
						.createVerkehrszeichen_Bezeichnung_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE
						.createVerkehrszeichen_Lz_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE
						.createVorgeschaltet_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE
						.createVz_Sperrstrecke_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE
						.createVz_Sperrstrecke_Schranke_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE
						.createVz_Sperrstrecke_Vorgeschaltet_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE.createWinkel_Alpha_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE
						.createZeitueberschreitungsmeldung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				BahnuebergangFactory.eINSTANCE.createZusatzschild_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				OrtungFactory.eINSTANCE.createBettungswiderstand_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				OrtungFactory.eINSTANCE
						.createBezeichnung_Kennbuchstabe_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				OrtungFactory.eINSTANCE.createFMA_Anlage()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				OrtungFactory.eINSTANCE
						.createFMA_Anlage_Allg_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				OrtungFactory.eINSTANCE
						.createFMA_Anlage_Bezeichnung_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				OrtungFactory.eINSTANCE
						.createFMA_Anlage_Elektr_Merkmale_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				OrtungFactory.eINSTANCE
						.createFMA_Anlage_Kaskade_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				OrtungFactory.eINSTANCE
						.createFMA_Anlage_Uebertragung_FMinfo_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				OrtungFactory.eINSTANCE
						.createFMA_Anschluss_Bezeichnung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				OrtungFactory.eINSTANCE
						.createFMA_Anschluss_Speiserichtung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				OrtungFactory.eINSTANCE.createFMA_Art_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				OrtungFactory.eINSTANCE.createFMA_Element()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				OrtungFactory.eINSTANCE
						.createFMA_Element_Allg_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				OrtungFactory.eINSTANCE
						.createFMA_Element_Anschluss_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				OrtungFactory.eINSTANCE.createFMA_Element_Art_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				OrtungFactory.eINSTANCE
						.createFMA_Element_Seilanzahl_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				OrtungFactory.eINSTANCE.createFMA_Element_Seiltyp_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				OrtungFactory.eINSTANCE.createFMA_Hilffreimeldung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				OrtungFactory.eINSTANCE.createFMA_Isolierung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				OrtungFactory.eINSTANCE
						.createFMA_Kaskade_Bezeichnung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				OrtungFactory.eINSTANCE
						.createFMA_Kaskade_Einzelauswertung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				OrtungFactory.eINSTANCE.createFMA_Komponente()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				OrtungFactory.eINSTANCE
						.createFMA_Komponente_Achszaehlpunkt_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				OrtungFactory.eINSTANCE.createFMA_Komponente_Art_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				OrtungFactory.eINSTANCE
						.createFMA_Komponente_Schienenprofil_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				OrtungFactory.eINSTANCE
						.createFMA_Komponente_Stromversorgung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				OrtungFactory.eINSTANCE.createFMA_Komponente_Typ_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				OrtungFactory.eINSTANCE
						.createFMA_Laenge_Beeinflusst_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				OrtungFactory.eINSTANCE.createFMA_Laenge_E1_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				OrtungFactory.eINSTANCE.createFMA_Laenge_E2_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				OrtungFactory.eINSTANCE.createFMA_Laenge_E3_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				OrtungFactory.eINSTANCE.createFMA_Laenge_S_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				OrtungFactory.eINSTANCE.createFMA_Laenge_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				OrtungFactory.eINSTANCE.createFMA_Typ_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				OrtungFactory.eINSTANCE
						.createSchaltmittel_Funktion_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				OrtungFactory.eINSTANCE.createSchaltmittel_Zuordnung()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				OrtungFactory.eINSTANCE
						.createUebertragung_FMinfo_Richtung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				OrtungFactory.eINSTANCE
						.createUebertragung_FMinfo_Typ_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				OrtungFactory.eINSTANCE.createZugeinwirkung()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				OrtungFactory.eINSTANCE
						.createZugeinwirkung_Allg_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				OrtungFactory.eINSTANCE.createZugeinwirkung_Art_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				OrtungFactory.eINSTANCE.createZugeinwirkung_Typ_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				FlankenschutzFactory.eINSTANCE
						.createEKW_Kr_Anteil_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				FlankenschutzFactory.eINSTANCE.createFahrt_Ueber_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				FlankenschutzFactory.eINSTANCE
						.createFla_Freimelde_Zuordnung()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				FlankenschutzFactory.eINSTANCE
						.createFla_Raum_Freimeldung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				FlankenschutzFactory.eINSTANCE.createFla_Schutz()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				FlankenschutzFactory.eINSTANCE
						.createFla_Schutz_Anforderer_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				FlankenschutzFactory.eINSTANCE
						.createFla_Schutz_Signal_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				FlankenschutzFactory.eINSTANCE
						.createFla_Schutz_W_Gsp_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				FlankenschutzFactory.eINSTANCE
						.createFla_Schutz_Weitergabe_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				FlankenschutzFactory.eINSTANCE
						.createFla_Signal_Zielsperrung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				FlankenschutzFactory.eINSTANCE.createFla_Verzicht_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				FlankenschutzFactory.eINSTANCE.createFla_W_Lage_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				FlankenschutzFactory.eINSTANCE.createFla_Zwieschutz()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				FlankenschutzFactory.eINSTANCE
						.createFla_Zwieschutz_Element_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				FlankenschutzFactory.eINSTANCE.createMassnahme_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				FlankenschutzFactory.eINSTANCE
						.createNachlaufverhinderung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				FlankenschutzFactory.eINSTANCE
						.createZwieschutz_Art_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				FahrstrasseFactory.eINSTANCE
						.createAufloesung_Ssp_Zielgleis_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				FahrstrasseFactory.eINSTANCE
						.createAufloesung_Verzoegerung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				FahrstrasseFactory.eINSTANCE
						.createAutomatische_Einstellung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				FahrstrasseFactory.eINSTANCE
						.createBezeichnung_Fstr_DWeg_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				FahrstrasseFactory.eINSTANCE
						.createBezeichnung_Markanter_Punkt_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				FahrstrasseFactory.eINSTANCE
						.createDWeg_Reihenfolge_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				FahrstrasseFactory.eINSTANCE
						.createDWeg_V_Aufwertung_Verzicht_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				FahrstrasseFactory.eINSTANCE.createDWeg_V_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				FahrstrasseFactory.eINSTANCE.createDWeg_Vorzug_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				FahrstrasseFactory.eINSTANCE
						.createElement_Verschluss_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				FahrstrasseFactory.eINSTANCE.createF_Bedienung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				FahrstrasseFactory.eINSTANCE.createFstr_Abhaengigkeit()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				FahrstrasseFactory.eINSTANCE
						.createFstr_Abhaengigkeit_Ssp_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				FahrstrasseFactory.eINSTANCE.createFstr_Aneinander()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				FahrstrasseFactory.eINSTANCE
						.createFstr_Aneinander_Bedienstring_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				FahrstrasseFactory.eINSTANCE
						.createFstr_Aneinander_Zuordnung()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				FahrstrasseFactory.eINSTANCE
						.createFstr_Bedienstring_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				FahrstrasseFactory.eINSTANCE.createFstr_DWeg()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				FahrstrasseFactory.eINSTANCE
						.createFstr_DWeg_Allg_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				FahrstrasseFactory.eINSTANCE
						.createFstr_DWeg_Bezeichnung_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				FahrstrasseFactory.eINSTANCE
						.createFstr_DWeg_Spezifisch_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				FahrstrasseFactory.eINSTANCE.createFstr_DWeg_W_Kr()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				FahrstrasseFactory.eINSTANCE.createFstr_Fahrweg()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				FahrstrasseFactory.eINSTANCE
						.createFstr_Mittel_Art_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				FahrstrasseFactory.eINSTANCE
						.createFstr_Mittel_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				FahrstrasseFactory.eINSTANCE
						.createFstr_Mittel_V_Aufwertung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				FahrstrasseFactory.eINSTANCE.createFstr_Nichthaltfall()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				FahrstrasseFactory.eINSTANCE
						.createFstr_Rangier_Art_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				FahrstrasseFactory.eINSTANCE
						.createFstr_Rangier_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				FahrstrasseFactory.eINSTANCE
						.createFstr_Rangier_Fla_Zuordnung()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				FahrstrasseFactory.eINSTANCE
						.createFstr_Reihenfolge_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				FahrstrasseFactory.eINSTANCE.createFstr_Signalisierung()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				FahrstrasseFactory.eINSTANCE.createFstr_Umfahrpunkt()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				FahrstrasseFactory.eINSTANCE.createFstr_V_Hg_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				FahrstrasseFactory.eINSTANCE.createFstr_V_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				FahrstrasseFactory.eINSTANCE
						.createFstr_Vsigabstand_Verkuerzt_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				FahrstrasseFactory.eINSTANCE.createFstr_Zug_Art_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				FahrstrasseFactory.eINSTANCE.createFstr_Zug_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				FahrstrasseFactory.eINSTANCE
						.createFstr_Zug_DWeg_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				FahrstrasseFactory.eINSTANCE.createFstr_Zug_Rangier()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				FahrstrasseFactory.eINSTANCE
						.createFstr_Zug_Rangier_Allg_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				FahrstrasseFactory.eINSTANCE.createLaenge_Soll_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				FahrstrasseFactory.eINSTANCE.createMarkanter_Punkt()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				FahrstrasseFactory.eINSTANCE
						.createMarkanter_Punkt_Bezeichnung_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				FahrstrasseFactory.eINSTANCE
						.createMassgebende_Neigung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				FahrstrasseFactory.eINSTANCE
						.createRangier_Gegenfahrtausschluss_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				FahrstrasseFactory.eINSTANCE.createSonstiger_Punkt()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				FahrstrasseFactory.eINSTANCE
						.createStart_Signal_Charakter_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				SignaleFactory.eINSTANCE.createAnschaltdauer_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				SignaleFactory.eINSTANCE.createAuto_Einstellung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				SignaleFactory.eINSTANCE.createBefestigung_Art_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				SignaleFactory.eINSTANCE.createBeleuchtet_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				SignaleFactory.eINSTANCE.createBesetzte_Ausfahrt_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				SignaleFactory.eINSTANCE.createDA_Manuell_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				SignaleFactory.eINSTANCE.createDunkelschaltung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				SignaleFactory.eINSTANCE.createDurchfahrt_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				SignaleFactory.eINSTANCE
						.createFiktives_Signal_Funktion_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				SignaleFactory.eINSTANCE.createFundament_Art_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				SignaleFactory.eINSTANCE
						.createFunktion_Ohne_Signal_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				SignaleFactory.eINSTANCE.createGegengleis_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				SignaleFactory.eINSTANCE.createGeltungsbereich_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				SignaleFactory.eINSTANCE.createGeschaltet_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				SignaleFactory.eINSTANCE
						.createHoehe_Fundamentoberkante_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				SignaleFactory.eINSTANCE
						.createObere_Lichtpunkthoehe_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				SignaleFactory.eINSTANCE
						.createPZB_Schutzstrecke_Soll_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				SignaleFactory.eINSTANCE.createRahmen_Art_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				SignaleFactory.eINSTANCE.createRahmen_Hoehe_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				SignaleFactory.eINSTANCE
						.createRangierstrasse_Restaufloesung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				SignaleFactory.eINSTANCE.createRichtpunkt_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				SignaleFactory.eINSTANCE
						.createRichtpunktentfernung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				SignaleFactory.eINSTANCE.createSignal()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				SignaleFactory.eINSTANCE.createSignal_Art_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				SignaleFactory.eINSTANCE.createSignal_Befestigung()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				SignaleFactory.eINSTANCE
						.createSignal_Befestigung_Allg_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				SignaleFactory.eINSTANCE
						.createSignal_Befestigungsart_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				SignaleFactory.eINSTANCE.createSignal_Fank_Zuordnung()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				SignaleFactory.eINSTANCE.createSignal_Fiktiv_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				SignaleFactory.eINSTANCE.createSignal_Fstr_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				SignaleFactory.eINSTANCE
						.createSignal_Fstr_Aus_Inselgleis_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				SignaleFactory.eINSTANCE.createSignal_Fstr_S_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				SignaleFactory.eINSTANCE.createSignal_Funktion_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				SignaleFactory.eINSTANCE.createSignal_Rahmen()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				SignaleFactory.eINSTANCE
						.createSignal_Real_Aktiv_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				SignaleFactory.eINSTANCE
						.createSignal_Real_Aktiv_Schirm_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				SignaleFactory.eINSTANCE.createSignal_Real_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				SignaleFactory.eINSTANCE.createSignal_Signalbegriff()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				SignaleFactory.eINSTANCE
						.createSignal_Signalbegriff_Allg_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				SignaleFactory.eINSTANCE
						.createSignalsicht_Erreichbar_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				SignaleFactory.eINSTANCE
						.createSignalsicht_Mindest_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				SignaleFactory.eINSTANCE.createSignalsicht_Soll_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				SignaleFactory.eINSTANCE.createSignalsystem_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				SignaleFactory.eINSTANCE
						.createSonstige_Zulaessige_Anordnung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				SignaleFactory.eINSTANCE.createStreuscheibe_Art_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				SignaleFactory.eINSTANCE
						.createStreuscheibe_Betriebsstellung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				SignaleFactory.eINSTANCE.createTunnelsignal_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				SignaleFactory.eINSTANCE.createZs2_Ueberwacht_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				LayoutinformationenFactory.eINSTANCE
						.createBez_Lageplan_Blattschnitt_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				LayoutinformationenFactory.eINSTANCE
						.createBezeichnung_Lageplan_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				LayoutinformationenFactory.eINSTANCE
						.createDarstellung_Polygonzug_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				LayoutinformationenFactory.eINSTANCE
						.createDarstellung_Richtungswinkel_Bezeichnung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				LayoutinformationenFactory.eINSTANCE
						.createDarstellung_Richtungswinkel_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				LayoutinformationenFactory.eINSTANCE.createElement_Position()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				LayoutinformationenFactory.eINSTANCE
						.createElement_Position_Allg_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				LayoutinformationenFactory.eINSTANCE.createElement_Stil()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				LayoutinformationenFactory.eINSTANCE
						.createElement_Stil_Allg_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				LayoutinformationenFactory.eINSTANCE
						.createFuellung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				LayoutinformationenFactory.eINSTANCE.createLageplan()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				LayoutinformationenFactory.eINSTANCE
						.createLageplan_Art_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				LayoutinformationenFactory.eINSTANCE
						.createLageplan_Bezeichnung_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				LayoutinformationenFactory.eINSTANCE
						.createLageplan_Blattschnitt()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				LayoutinformationenFactory.eINSTANCE
						.createLageplan_Blattschnitt_Bezeichnung_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				LayoutinformationenFactory.eINSTANCE.createLageplan_Zustand()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				LayoutinformationenFactory.eINSTANCE
						.createLinie_Art_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				LayoutinformationenFactory.eINSTANCE
						.createLinie_Farbwert_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				LayoutinformationenFactory.eINSTANCE
						.createLinie_Subart_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				LayoutinformationenFactory.eINSTANCE
						.createPlanPro_Layoutinfo()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				LayoutinformationenFactory.eINSTANCE
						.createPolygonzug_Ausrichtung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				LayoutinformationenFactory.eINSTANCE
						.createPolygonzug_Blattschnitt_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				LayoutinformationenFactory.eINSTANCE
						.createReferenz_LST_Zustand_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				LayoutinformationenFactory.eINSTANCE
						.createReferenz_Objekt_Darstellung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				NahbedienungFactory.eINSTANCE.createAWU_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				NahbedienungFactory.eINSTANCE
						.createBezeichnung_NB_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				NahbedienungFactory.eINSTANCE
						.createBezeichnung_NB_Zone_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				NahbedienungFactory.eINSTANCE.createF_ST_Z_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				NahbedienungFactory.eINSTANCE.createFA_FAE_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				NahbedienungFactory.eINSTANCE
						.createFreie_Stellbarkeit_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				NahbedienungFactory.eINSTANCE.createKennzahl_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				NahbedienungFactory.eINSTANCE.createNB()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				NahbedienungFactory.eINSTANCE.createNB_Art_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				NahbedienungFactory.eINSTANCE
						.createNB_Bedien_Anzeige_Element()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				NahbedienungFactory.eINSTANCE
						.createNB_Bedien_Anzeige_Funktionen_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				NahbedienungFactory.eINSTANCE
						.createNB_Bezeichnung_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				NahbedienungFactory.eINSTANCE
						.createNB_Funktionalitaet_NB_R_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				NahbedienungFactory.eINSTANCE.createNB_Grenze_Art_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				NahbedienungFactory.eINSTANCE
						.createNB_Rueckgabevoraussetzung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				NahbedienungFactory.eINSTANCE
						.createNB_Verhaeltnis_Besonders_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				NahbedienungFactory.eINSTANCE.createNB_Zone()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				NahbedienungFactory.eINSTANCE
						.createNB_Zone_Allg_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				NahbedienungFactory.eINSTANCE.createNB_Zone_Allg_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				NahbedienungFactory.eINSTANCE
						.createNB_Zone_Bezeichnung_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				NahbedienungFactory.eINSTANCE.createNB_Zone_Element()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				NahbedienungFactory.eINSTANCE
						.createNB_Zone_Element_Allg_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				NahbedienungFactory.eINSTANCE.createNB_Zone_Grenze()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				NahbedienungFactory.eINSTANCE
						.createNB_Zone_Reihenfolgezwang_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				NahbedienungFactory.eINSTANCE.createRang_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				NahbedienungFactory.eINSTANCE.createSBUE_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				NahbedienungFactory.eINSTANCE.createSLE_SLS_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				NahbedienungFactory.eINSTANCE.createTaste_ANF_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				NahbedienungFactory.eINSTANCE.createTaste_FGT_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				NahbedienungFactory.eINSTANCE.createTaste_WGT_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				NahbedienungFactory.eINSTANCE.createW_Gsp_Lage_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				NahbedienungFactory.eINSTANCE.createWHU_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				NahbedienungFactory.eINSTANCE.createWUS_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				PZBFactory.eINSTANCE.createGUE_Abstand_Abweichend_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				PZBFactory.eINSTANCE.createGUE_Anordnung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				PZBFactory.eINSTANCE.createGUE_Bauart_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				PZBFactory.eINSTANCE.createGUE_Energieversorgung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				PZBFactory.eINSTANCE.createGUE_Messstrecke_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				PZBFactory.eINSTANCE.createINA_Gefahrstelle_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				PZBFactory.eINSTANCE.createMessfehler_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				PZBFactory.eINSTANCE
						.createPrioritaet_Gefahrstelle_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				PZBFactory.eINSTANCE.createPruefgeschwindigkeit_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				PZBFactory.eINSTANCE.createPruefzeit_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				PZBFactory.eINSTANCE.createPZB_Abstand_GM_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				PZBFactory.eINSTANCE.createPZB_Art_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				PZBFactory.eINSTANCE.createPZB_Element()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				PZBFactory.eINSTANCE.createPZB_Element_GM_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				PZBFactory.eINSTANCE.createPZB_Element_GUE_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				PZBFactory.eINSTANCE.createPZB_Element_Zuordnung()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				PZBFactory.eINSTANCE
						.createPZB_Element_Zuordnung_BP_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				PZBFactory.eINSTANCE
						.createPZB_Element_Zuordnung_Fstr_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				PZBFactory.eINSTANCE
						.createPZB_Element_Zuordnung_INA_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				PZBFactory.eINSTANCE.createPZB_INA_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				PZBFactory.eINSTANCE.createPZB_Zuordnung_Signal()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				PZBFactory.eINSTANCE.createWirksamkeit_Fstr_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				PZBFactory.eINSTANCE.createWirksamkeit_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				RegelzeichnungFactory.eINSTANCE.createBild_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				RegelzeichnungFactory.eINSTANCE.createRegelzeichnung()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				RegelzeichnungFactory.eINSTANCE
						.createRegelzeichnung_Allg_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				RegelzeichnungFactory.eINSTANCE
						.createRegelzeichnung_Parameter()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				RegelzeichnungFactory.eINSTANCE
						.createRegelzeichnung_Parameter_Allg_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				RegelzeichnungFactory.eINSTANCE.createRZ_Nummer_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				RegelzeichnungFactory.eINSTANCE
						.createRZ_Parameter_Name_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				RegelzeichnungFactory.eINSTANCE
						.createRZ_Parameter_Wert_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				RegelzeichnungFactory.eINSTANCE.createTitel_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				RegelzeichnungFactory.eINSTANCE.createUntertitel_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				SchluesselabhaengigkeitenFactory.eINSTANCE
						.createBedienung_Art_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				SchluesselabhaengigkeitenFactory.eINSTANCE
						.createBeschreibung_Sonderanlage_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				SchluesselabhaengigkeitenFactory.eINSTANCE
						.createBezeichnung_Schloss_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				SchluesselabhaengigkeitenFactory.eINSTANCE
						.createBezeichnung_Schluessel_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				SchluesselabhaengigkeitenFactory.eINSTANCE
						.createBezeichnung_Sk_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				SchluesselabhaengigkeitenFactory.eINSTANCE
						.createBUE_Lage_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				SchluesselabhaengigkeitenFactory.eINSTANCE
						.createGsp_Lage_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				SchluesselabhaengigkeitenFactory.eINSTANCE
						.createHauptschloss_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				SchluesselabhaengigkeitenFactory.eINSTANCE.createSchloss()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				SchluesselabhaengigkeitenFactory.eINSTANCE
						.createSchloss_Art_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				SchluesselabhaengigkeitenFactory.eINSTANCE
						.createSchloss_Bezeichnung_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				SchluesselabhaengigkeitenFactory.eINSTANCE
						.createSchloss_BUE_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				SchluesselabhaengigkeitenFactory.eINSTANCE
						.createSchloss_Gsp_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				SchluesselabhaengigkeitenFactory.eINSTANCE
						.createSchloss_Sk_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				SchluesselabhaengigkeitenFactory.eINSTANCE
						.createSchloss_Sonderanlage_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				SchluesselabhaengigkeitenFactory.eINSTANCE
						.createSchloss_Ssp_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				SchluesselabhaengigkeitenFactory.eINSTANCE
						.createSchloss_W_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				SchluesselabhaengigkeitenFactory.eINSTANCE
						.createSchlosskombination()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				SchluesselabhaengigkeitenFactory.eINSTANCE
						.createSchlosskombination_Bezeichnung_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				SchluesselabhaengigkeitenFactory.eINSTANCE.createSchluessel()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				SchluesselabhaengigkeitenFactory.eINSTANCE
						.createSchluessel_Allg_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				SchluesselabhaengigkeitenFactory.eINSTANCE
						.createSchluessel_Bartform_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				SchluesselabhaengigkeitenFactory.eINSTANCE
						.createSchluessel_Bezeichnung_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				SchluesselabhaengigkeitenFactory.eINSTANCE
						.createSchluessel_Gruppe_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				SchluesselabhaengigkeitenFactory.eINSTANCE
						.createSchluessel_In_Grdst_Eingeschl_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				SchluesselabhaengigkeitenFactory.eINSTANCE
						.createSchluesselsperre()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				SchluesselabhaengigkeitenFactory.eINSTANCE
						.createSonderanlage_Lage_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				SchluesselabhaengigkeitenFactory.eINSTANCE
						.createTechnisch_Berechtigter_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				SchluesselabhaengigkeitenFactory.eINSTANCE
						.createVerschluss_Ort_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				SchluesselabhaengigkeitenFactory.eINSTANCE
						.createW_Anbaulage_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				SchluesselabhaengigkeitenFactory.eINSTANCE
						.createW_Lage_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Weichen_und_GleissperrenFactory.eINSTANCE
						.createAuffahrortung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Weichen_und_GleissperrenFactory.eINSTANCE
						.createAustausch_Antriebe_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Weichen_und_GleissperrenFactory.eINSTANCE
						.createAuswurfrichtung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Weichen_und_GleissperrenFactory.eINSTANCE
						.createBesonderes_Fahrwegelement_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Weichen_und_GleissperrenFactory.eINSTANCE
						.createElektrischer_Antrieb_Anzahl_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Weichen_und_GleissperrenFactory.eINSTANCE
						.createElektrischer_Antrieb_Lage_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Weichen_und_GleissperrenFactory.eINSTANCE
						.createElement_Lage_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Weichen_und_GleissperrenFactory.eINSTANCE
						.createEntgleisungsschuh_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Weichen_und_GleissperrenFactory.eINSTANCE
						.createGeschwindigkeit_L_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Weichen_und_GleissperrenFactory.eINSTANCE
						.createGeschwindigkeit_R_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Weichen_und_GleissperrenFactory.eINSTANCE
						.createGleis_Abschluss()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Weichen_und_GleissperrenFactory.eINSTANCE
						.createGleis_Abschluss_Art_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Weichen_und_GleissperrenFactory.eINSTANCE
						.createGleissperre_Betriebsart_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Weichen_und_GleissperrenFactory.eINSTANCE
						.createGleissperre_Element_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Weichen_und_GleissperrenFactory.eINSTANCE
						.createGleissperre_Vorzugslage_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Weichen_und_GleissperrenFactory.eINSTANCE
						.createGleissperrensignal_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Weichen_und_GleissperrenFactory.eINSTANCE
						.createGZ_Freimeldung_L_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Weichen_und_GleissperrenFactory.eINSTANCE
						.createGZ_Freimeldung_R_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Weichen_und_GleissperrenFactory.eINSTANCE
						.createHerzstueck_Antriebe_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Weichen_und_GleissperrenFactory.eINSTANCE
						.createIsolierfall_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Weichen_und_GleissperrenFactory.eINSTANCE
						.createKr_KrW_Seitenzuordnung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Weichen_und_GleissperrenFactory.eINSTANCE
						.createKreuzung_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Weichen_und_GleissperrenFactory.eINSTANCE
						.createKreuzungsgleis_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Weichen_und_GleissperrenFactory.eINSTANCE
						.createSchutzschiene_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Weichen_und_GleissperrenFactory.eINSTANCE
						.createStammgleis_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Weichen_und_GleissperrenFactory.eINSTANCE
						.createVorzugslage_Automatik_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Weichen_und_GleissperrenFactory.eINSTANCE.createW_Kr_Anlage()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Weichen_und_GleissperrenFactory.eINSTANCE
						.createW_Kr_Anlage_Allg_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Weichen_und_GleissperrenFactory.eINSTANCE
						.createW_Kr_Art_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Weichen_und_GleissperrenFactory.eINSTANCE
						.createW_Kr_Grundform_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Weichen_und_GleissperrenFactory.eINSTANCE
						.createW_Kr_Gsp_Element()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Weichen_und_GleissperrenFactory.eINSTANCE
						.createW_Kr_Gsp_Element_Allg_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Weichen_und_GleissperrenFactory.eINSTANCE
						.createW_Kr_Gsp_Komponente()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Weichen_und_GleissperrenFactory.eINSTANCE
						.createW_Kr_Gsp_Stellart_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Weichen_und_GleissperrenFactory.eINSTANCE
						.createWeiche_Betriebsart_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Weichen_und_GleissperrenFactory.eINSTANCE
						.createWeiche_Element_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Weichen_und_GleissperrenFactory.eINSTANCE
						.createWeiche_Vorzugslage_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Weichen_und_GleissperrenFactory.eINSTANCE
						.createWeichenlaufkette()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Weichen_und_GleissperrenFactory.eINSTANCE
						.createWeichenlaufkette_Zuordnung()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Weichen_und_GleissperrenFactory.eINSTANCE
						.createWeichensignal_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Weichen_und_GleissperrenFactory.eINSTANCE
						.createZungenpaar_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				Weichen_und_GleissperrenFactory.eINSTANCE
						.createZungenpruefkontakt_Anzahl_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZuglenkungFactory.eINSTANCE
						.createAnnaeherungsgeschwindigkeit_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZuglenkungFactory.eINSTANCE
						.createAnzahl_Wiederhol_ZL_Anstoesse_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZuglenkungFactory.eINSTANCE
						.createDeadlockpruefung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZuglenkungFactory.eINSTANCE.createDWeg_Prio_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZuglenkungFactory.eINSTANCE
						.createEinstellkontrollzeit_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZuglenkungFactory.eINSTANCE.createFstr_Bildezeit_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZuglenkungFactory.eINSTANCE.createFUEM_Auswertung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZuglenkungFactory.eINSTANCE.createGK_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZuglenkungFactory.eINSTANCE.createGKZSS_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZuglenkungFactory.eINSTANCE.createLenkabbruchzeit_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZuglenkungFactory.eINSTANCE
						.createLenkziffernstellen_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZuglenkungFactory.eINSTANCE
						.createPersonal_Reaktionszeit_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZuglenkungFactory.eINSTANCE
						.createSichtzeit_Vorsignal_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZuglenkungFactory.eINSTANCE
						.createSignalgruppe_Bezeichner_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZuglenkungFactory.eINSTANCE.createTv_GK_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZuglenkungFactory.eINSTANCE
						.createVmax_Annaeherung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZuglenkungFactory.eINSTANCE.createZL()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZuglenkungFactory.eINSTANCE.createZL_Allg_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZuglenkungFactory.eINSTANCE.createZL_DLP_Abschnitt()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZuglenkungFactory.eINSTANCE.createZL_DLP_Fstr()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZuglenkungFactory.eINSTANCE.createZL_Fstr()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZuglenkungFactory.eINSTANCE
						.createZL_Fstr_Allg_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZuglenkungFactory.eINSTANCE.createZL_Fstr_Anstoss()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZuglenkungFactory.eINSTANCE
						.createZL_Fstr_Anstoss_Allg_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZuglenkungFactory.eINSTANCE
						.createZL_Fstr_Anstoss_GK_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZuglenkungFactory.eINSTANCE
						.createZL_Fstr_Zuschlag_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZuglenkungFactory.eINSTANCE.createZL_Signalgruppe()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZuglenkungFactory.eINSTANCE
						.createZL_Signalgruppe_Allg_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZuglenkungFactory.eINSTANCE.createZL_Signalgruppe_Zuordnung()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZuglenkungFactory.eINSTANCE.createZL_ZN_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZuglenkungFactory.eINSTANCE.createZN_Stellen_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZugnummernmeldeanlageFactory.eINSTANCE
						.createAkustikdauer_Anb_Ann_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZugnummernmeldeanlageFactory.eINSTANCE
						.createAkustikdauer_Sonst_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZugnummernmeldeanlageFactory.eINSTANCE
						.createAkustikdauer_Voranz_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZugnummernmeldeanlageFactory.eINSTANCE
						.createAnschlussnummer_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZugnummernmeldeanlageFactory.eINSTANCE
						.createAusfahrdruck_Gegengleis_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZugnummernmeldeanlageFactory.eINSTANCE
						.createAusfahrdruck_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZugnummernmeldeanlageFactory.eINSTANCE
						.createBedienbarkeit_Anzeigefeld_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZugnummernmeldeanlageFactory.eINSTANCE
						.createBesonderes_Schaltkriterium_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZugnummernmeldeanlageFactory.eINSTANCE
						.createBezeichnung_Besondere_Anlage_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZugnummernmeldeanlageFactory.eINSTANCE
						.createBezeichnung_Stellwerk_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZugnummernmeldeanlageFactory.eINSTANCE
						.createBf_Kennung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZugnummernmeldeanlageFactory.eINSTANCE
						.createBf_Nr_ANB_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZugnummernmeldeanlageFactory.eINSTANCE
						.createBf_Nr_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZugnummernmeldeanlageFactory.eINSTANCE
						.createBf_Nr_ZN_A_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZugnummernmeldeanlageFactory.eINSTANCE
						.createDurchfahrdruck_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZugnummernmeldeanlageFactory.eINSTANCE
						.createEinfahrdruck_Gegengleis_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZugnummernmeldeanlageFactory.eINSTANCE
						.createEinfahrdruck_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZugnummernmeldeanlageFactory.eINSTANCE
						.createEinwahlstelle_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZugnummernmeldeanlageFactory.eINSTANCE
						.createFunktionalitaet_Anzeigefeld_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZugnummernmeldeanlageFactory.eINSTANCE.createHOA_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZugnummernmeldeanlageFactory.eINSTANCE
						.createIP_Adresse_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZugnummernmeldeanlageFactory.eINSTANCE
						.createKoppelunterstation_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZugnummernmeldeanlageFactory.eINSTANCE
						.createKUs_Zeittelegramm_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZugnummernmeldeanlageFactory.eINSTANCE
						.createMeldedruck_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZugnummernmeldeanlageFactory.eINSTANCE
						.createPrioritaet_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZugnummernmeldeanlageFactory.eINSTANCE
						.createReaktivierungsfunktion_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZugnummernmeldeanlageFactory.eINSTANCE
						.createSichtbarkeit_Anzeigefeld_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZugnummernmeldeanlageFactory.eINSTANCE
						.createTelegramm_02_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZugnummernmeldeanlageFactory.eINSTANCE
						.createTelegramm_03_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZugnummernmeldeanlageFactory.eINSTANCE
						.createTelegramm_04_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZugnummernmeldeanlageFactory.eINSTANCE
						.createTelegramm_10_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZugnummernmeldeanlageFactory.eINSTANCE
						.createTelegramm_21_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZugnummernmeldeanlageFactory.eINSTANCE
						.createTelegramm_30_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZugnummernmeldeanlageFactory.eINSTANCE
						.createTelegramm_84_Alle_Fstr_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZugnummernmeldeanlageFactory.eINSTANCE
						.createTelegramm_84_Einzelne_Fstr_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZugnummernmeldeanlageFactory.eINSTANCE
						.createTelegramm_84_Fuer_Alle_Fstr_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZugnummernmeldeanlageFactory.eINSTANCE
						.createTelegramm_84_Verzicht_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZugnummernmeldeanlageFactory.eINSTANCE
						.createTelegramm_85_Alle_Fstr_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZugnummernmeldeanlageFactory.eINSTANCE
						.createTelegramm_85_Einzelne_Fstr_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZugnummernmeldeanlageFactory.eINSTANCE
						.createTelegramm_85_Fuer_Alle_Fstr_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZugnummernmeldeanlageFactory.eINSTANCE
						.createTelegrammwiederholung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZugnummernmeldeanlageFactory.eINSTANCE
						.createUnterstation_Max_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZugnummernmeldeanlageFactory.eINSTANCE
						.createUnterstation_Nr_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZugnummernmeldeanlageFactory.eINSTANCE
						.createVerzoegerung_Manuell_Loeschung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZugnummernmeldeanlageFactory.eINSTANCE
						.createVormeldestart_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZugnummernmeldeanlageFactory.eINSTANCE
						.createZBS_Adresse_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZugnummernmeldeanlageFactory.eINSTANCE
						.createZBS_Anbindung_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZugnummernmeldeanlageFactory.eINSTANCE
						.createZBS_Schnittstelle_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZugnummernmeldeanlageFactory.eINSTANCE
						.createZeitsynchronisation_Funkuhr_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZugnummernmeldeanlageFactory.eINSTANCE.createZLV_Bus()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZugnummernmeldeanlageFactory.eINSTANCE
						.createZLV_Bus_Allg_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZugnummernmeldeanlageFactory.eINSTANCE
						.createZLV_Bus_Besondere_Anlage()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZugnummernmeldeanlageFactory.eINSTANCE
						.createZLV_Bus_Besondere_Anlage_Bezeichnung_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZugnummernmeldeanlageFactory.eINSTANCE
						.createZLV_Bus_Bezeichnung_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZugnummernmeldeanlageFactory.eINSTANCE
						.createZLV_Bus_Nr_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZugnummernmeldeanlageFactory.eINSTANCE
						.createZLV_Bus_US_Zuordnung()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZugnummernmeldeanlageFactory.eINSTANCE
						.createZLV_Bus_US_Zuordnung_Telegramm_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZugnummernmeldeanlageFactory.eINSTANCE
						.createZLV_Bus_Zuordnung_Allg_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZugnummernmeldeanlageFactory.eINSTANCE.createZN()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZugnummernmeldeanlageFactory.eINSTANCE
						.createZN_A_Bedienbezeichner_Frei_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZugnummernmeldeanlageFactory.eINSTANCE
						.createZN_A_Bezeichner_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZugnummernmeldeanlageFactory.eINSTANCE.createZN_Akustik()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZugnummernmeldeanlageFactory.eINSTANCE
						.createZN_Akustik_Anzeigefeld_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZugnummernmeldeanlageFactory.eINSTANCE
						.createZN_Allg_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZugnummernmeldeanlageFactory.eINSTANCE
						.createZN_Anlagentyp_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZugnummernmeldeanlageFactory.eINSTANCE.createZN_Anzeigefeld()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZugnummernmeldeanlageFactory.eINSTANCE
						.createZN_Anzeigefeld_Allg_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZugnummernmeldeanlageFactory.eINSTANCE
						.createZN_Anzeigefeld_Bezeichnung_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZugnummernmeldeanlageFactory.eINSTANCE
						.createZN_Anzeigefeld_Loeschkriterium_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZugnummernmeldeanlageFactory.eINSTANCE
						.createZN_Feld_Ohne_Anzeige_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZugnummernmeldeanlageFactory.eINSTANCE
						.createZN_Fortschalt_Krit_Druck_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZugnummernmeldeanlageFactory.eINSTANCE
						.createZN_Fortschalt_Krit_Schalt_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZugnummernmeldeanlageFactory.eINSTANCE
						.createZN_Fortschalt_Kriterium()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZugnummernmeldeanlageFactory.eINSTANCE
						.createZN_Modem_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZugnummernmeldeanlageFactory.eINSTANCE
						.createZN_Schaltkriterium_TypeClass()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZugnummernmeldeanlageFactory.eINSTANCE
						.createZN_Telegramm_84_Zuordnung()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZugnummernmeldeanlageFactory.eINSTANCE
						.createZN_Telegramm_85_Zuordnung()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZugnummernmeldeanlageFactory.eINSTANCE
						.createZN_Unterstation()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZugnummernmeldeanlageFactory.eINSTANCE
						.createZN_Unterstation_Allg_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZugnummernmeldeanlageFactory.eINSTANCE
						.createZN_Unterstation_Bf_Nr_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZugnummernmeldeanlageFactory.eINSTANCE.createZN_ZBS()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT,
				ZugnummernmeldeanlageFactory.eINSTANCE
						.createZugvorbereitungsmeldung_TypeClass()));
	}

	/**
	 * This returns the label text for
	 * {@link org.eclipse.emf.edit.command.CreateChildCommand}. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getCreateChildText(Object owner, Object feature, Object child,
			Collection<?> selection) {
		Object childFeature = feature;
		Object childObject = child;

		boolean qualify = childFeature == TablemodelPackage.Literals.TABLE_ROW__CELLS
				|| childFeature == TablemodelPackage.Literals.TABLE_ROW__ROW_OBJECT
				|| childFeature == TablemodelPackage.Literals.TABLE_ROW__FOOTNOTES;

		if (qualify) {
			return getString("_UI_CreateChild_text2",
					new Object[] { getTypeText(childObject),
							getFeatureText(childFeature), getTypeText(owner) });
		}
		return super.getCreateChildText(owner, feature, child, selection);
	}

	/**
	 * Return the resource locator for this item provider's resources. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public ResourceLocator getResourceLocator() {
		return TablemodelEditPlugin.INSTANCE;
	}

}
