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

import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;

import org.eclipse.set.model.planpro.ATO.ATOFactory;
import org.eclipse.set.model.planpro.Ansteuerung_Element.Ansteuerung_ElementFactory;
import org.eclipse.set.model.planpro.Bahnsteig.BahnsteigFactory;
import org.eclipse.set.model.planpro.Bahnuebergang.BahnuebergangFactory;
import org.eclipse.set.model.planpro.Balisentechnik_ETCS.Balisentechnik_ETCSFactory;
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
import org.eclipse.set.model.planpro.Weichen_und_Gleissperren.Weichen_und_GleissperrenFactory;
import org.eclipse.set.model.planpro.Zuglenkung.ZuglenkungFactory;
import org.eclipse.set.model.planpro.Zugnummernmeldeanlage.ZugnummernmeldeanlageFactory;
import org.eclipse.set.model.tablemodel.SimpleFootnoteContainer;
import org.eclipse.set.model.tablemodel.TablemodelPackage;

/**
 * This is the item provider adapter for a
 * {@link org.eclipse.set.model.tablemodel.SimpleFootnoteContainer} object. <!--
 * begin-user-doc --> <!-- end-user-doc -->
 * 
 * @generated
 */
public class SimpleFootnoteContainerItemProvider
		extends FootnoteContainerItemProvider {
	/**
	 * This constructs an instance from a factory and a notifier. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public SimpleFootnoteContainerItemProvider(AdapterFactory adapterFactory) {
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

		}
		return itemPropertyDescriptors;
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
			childrenFeatures.add(
					TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__FOOTNOTES);
			childrenFeatures.add(
					TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT);
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
	 * This returns SimpleFootnoteContainer.gif. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator()
				.getImage("full/obj16/SimpleFootnoteContainer"));
	}

	/**
	 * This returns the label text for the adapted class. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		return getString("_UI_SimpleFootnoteContainer_type");
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

		switch (notification.getFeatureID(SimpleFootnoteContainer.class)) {
			case TablemodelPackage.SIMPLE_FOOTNOTE_CONTAINER__FOOTNOTES:
			case TablemodelPackage.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT:
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
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__FOOTNOTES,
				BasisobjekteFactory.eINSTANCE.createBearbeitungsvermerk()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				BasisobjekteFactory.eINSTANCE.createAnhang()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				BasisobjekteFactory.eINSTANCE.createBearbeitungsvermerk()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				BasisobjekteFactory.eINSTANCE.createLieferobjekt()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				BasisobjekteFactory.eINSTANCE.createProxy_Objekt()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				GeodatenFactory.eINSTANCE.createGEO_Kante()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				GeodatenFactory.eINSTANCE.createGEO_Knoten()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				GeodatenFactory.eINSTANCE.createGEO_Punkt()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				GeodatenFactory.eINSTANCE.createGeschwindigkeitsprofil()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				GeodatenFactory.eINSTANCE.createHoehenlinie()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				GeodatenFactory.eINSTANCE.createHoehenpunkt()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				GeodatenFactory.eINSTANCE.createOertlichkeit()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				GeodatenFactory.eINSTANCE.createStrecke()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				GeodatenFactory.eINSTANCE.createStrecke_Bremsweg()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				GeodatenFactory.eINSTANCE.createStrecke_Punkt()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				GeodatenFactory.eINSTANCE.createTechnischer_Bereich()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				GeodatenFactory.eINSTANCE.createTechnischer_Punkt()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				GeodatenFactory.eINSTANCE.createTOP_Kante()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				GeodatenFactory.eINSTANCE.createTOP_Knoten()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				GeodatenFactory.eINSTANCE
						.createTrasse_Kante_child_AttributeGroup()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				GeodatenFactory.eINSTANCE.createUeberhoehung()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				GeodatenFactory.eINSTANCE.createUeberhoehungslinie()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				GleisFactory.eINSTANCE.createGleis_Abschnitt()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				GleisFactory.eINSTANCE.createGleis_Art()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				GleisFactory.eINSTANCE.createGleis_Baubereich()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				GleisFactory.eINSTANCE.createGleis_Bezeichnung()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				GleisFactory.eINSTANCE.createGleis_Fahrbahn()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				GleisFactory.eINSTANCE.createGleis_Lichtraum()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				GleisFactory.eINSTANCE.createGleis_Schaltgruppe()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				BedienungFactory.eINSTANCE.createBedien_Anrueckabschnitt()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				BedienungFactory.eINSTANCE.createBedien_Anzeige_Element()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				BedienungFactory.eINSTANCE.createBedien_Bezirk()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				BedienungFactory.eINSTANCE
						.createBedien_Einrichtung_Oertlich()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				BedienungFactory.eINSTANCE.createBedien_GBT()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				BedienungFactory.eINSTANCE.createBedien_Oberflaeche()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				BedienungFactory.eINSTANCE.createBedien_Oberflaeche_Bild()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				BedienungFactory.eINSTANCE.createBedien_Oertlichkeit()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				BedienungFactory.eINSTANCE.createBedien_Platz()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				BedienungFactory.eINSTANCE.createBedien_Standort()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				BedienungFactory.eINSTANCE.createBedien_Zentrale()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				Ansteuerung_ElementFactory.eINSTANCE
						.createAussenelementansteuerung()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				Ansteuerung_ElementFactory.eINSTANCE
						.createESTW_Zentraleinheit()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				Ansteuerung_ElementFactory.eINSTANCE.createStell_Bereich()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				Ansteuerung_ElementFactory.eINSTANCE.createStellelement()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				Ansteuerung_ElementFactory.eINSTANCE.createTechnik_Standort()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				Ansteuerung_ElementFactory.eINSTANCE.createUebertragungsweg()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				Ansteuerung_ElementFactory.eINSTANCE.createUnterbringung()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				Medien_und_TrassenFactory.eINSTANCE.createKabel()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				Medien_und_TrassenFactory.eINSTANCE
						.createKabel_Verteilpunkt()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				Medien_und_TrassenFactory.eINSTANCE.createTrasse_Kante()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				Medien_und_TrassenFactory.eINSTANCE.createTrasse_Knoten()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				ATOFactory.eINSTANCE.createATO_Segment_Profile()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				ATOFactory.eINSTANCE.createATO_Timing_Point()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				ATOFactory.eINSTANCE.createATO_TS_Instanz()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				PlanProFactory.eINSTANCE.createAkteur()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				PlanProFactory.eINSTANCE.createAkteur_Zuordnung()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				PlanProFactory.eINSTANCE.createAusgabe_Fachdaten()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				PlanProFactory.eINSTANCE.createLST_Zustand()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				PlanProFactory.eINSTANCE.createOrganisation()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				PlanProFactory.eINSTANCE.createPlanPro_Schnittstelle()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				PlanProFactory.eINSTANCE.createPlanung_Einzel()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				PlanProFactory.eINSTANCE.createPlanung_Gruppe()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				PlanProFactory.eINSTANCE.createPlanung_Projekt()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				BahnsteigFactory.eINSTANCE.createBahnsteig_Anlage()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				BahnsteigFactory.eINSTANCE.createBahnsteig_Dach()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				BahnsteigFactory.eINSTANCE.createBahnsteig_Kante()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				BahnsteigFactory.eINSTANCE.createBahnsteig_Zugang()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE.createBalise()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE.createBinaerdaten()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE.createDatenpunkt()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE.createDatenpunkt_Link()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE.createETCS_Kante()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE.createETCS_Knoten()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createETCS_Richtungsanzeige()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE.createETCS_Signal()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE.createETCS_W_Kr()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE.createEV_Modul()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE.createFachtelegramm()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createFT_Anschaltbedingung()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE.createFT_Fahrweg_Teil()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE.createLEU_Anlage()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE.createLEU_Modul()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE.createLEU_Schaltkasten()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE.createLuft_Telegramm()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createProg_Datei_Gruppe()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE.createRBC()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createZBS_Schutzstrecke()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE.createZBS_Signal()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createZUB_Bereichsgrenze()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				Balisentechnik_ETCSFactory.eINSTANCE
						.createZUB_Streckeneigenschaft()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				BlockFactory.eINSTANCE.createBlock_Anlage()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				BlockFactory.eINSTANCE.createBlock_Element()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				BlockFactory.eINSTANCE.createBlock_Strecke()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				BahnuebergangFactory.eINSTANCE.createBUE_Anlage()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				BahnuebergangFactory.eINSTANCE.createBUE_Anlage_Strasse()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				BahnuebergangFactory.eINSTANCE.createBUE_Anlage_V()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				BahnuebergangFactory.eINSTANCE.createBUE_Ausschaltung()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				BahnuebergangFactory.eINSTANCE
						.createBUE_Bedien_Anzeige_Element()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				BahnuebergangFactory.eINSTANCE
						.createBUE_Deckendes_Signal_Zuordnung()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				BahnuebergangFactory.eINSTANCE.createBUE_Einschaltung()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				BahnuebergangFactory.eINSTANCE
						.createBUE_Einschaltung_Zuordnung()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				BahnuebergangFactory.eINSTANCE
						.createBUE_Gefahrraum_Eckpunkt()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				BahnuebergangFactory.eINSTANCE
						.createBUE_Gleisbezogener_Gefahrraum()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				BahnuebergangFactory.eINSTANCE.createBUE_Kante()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				BahnuebergangFactory.eINSTANCE.createBUE_Kreuzungsplan()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				BahnuebergangFactory.eINSTANCE.createBUE_Schnittstelle()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				BahnuebergangFactory.eINSTANCE
						.createBUE_Spezifisches_Signal()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				BahnuebergangFactory.eINSTANCE.createBUE_WS_Fstr_Zuordnung()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				BahnuebergangFactory.eINSTANCE.createGFR_Anlage()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				BahnuebergangFactory.eINSTANCE.createGFR_Element()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				BahnuebergangFactory.eINSTANCE.createGFR_Tripelspiegel()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				BahnuebergangFactory.eINSTANCE
						.createSchaltmittel_Fstr_Zuordnung()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				BahnuebergangFactory.eINSTANCE.createSchrankenantrieb()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				BahnuebergangFactory.eINSTANCE.createVerkehrszeichen()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				OrtungFactory.eINSTANCE.createFMA_Anlage()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				OrtungFactory.eINSTANCE.createFMA_Element()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				OrtungFactory.eINSTANCE.createFMA_Komponente()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				OrtungFactory.eINSTANCE.createSchaltmittel_Zuordnung()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				OrtungFactory.eINSTANCE.createZugeinwirkung()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				FlankenschutzFactory.eINSTANCE
						.createFla_Freimelde_Zuordnung()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				FlankenschutzFactory.eINSTANCE.createFla_Schutz()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				FlankenschutzFactory.eINSTANCE.createFla_Zwieschutz()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				FahrstrasseFactory.eINSTANCE.createFstr_Abhaengigkeit()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				FahrstrasseFactory.eINSTANCE.createFstr_Aneinander()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				FahrstrasseFactory.eINSTANCE
						.createFstr_Aneinander_Zuordnung()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				FahrstrasseFactory.eINSTANCE.createFstr_DWeg()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				FahrstrasseFactory.eINSTANCE.createFstr_DWeg_W_Kr()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				FahrstrasseFactory.eINSTANCE.createFstr_Fahrweg()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				FahrstrasseFactory.eINSTANCE.createFstr_Nichthaltfall()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				FahrstrasseFactory.eINSTANCE
						.createFstr_Rangier_Fla_Zuordnung()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				FahrstrasseFactory.eINSTANCE.createFstr_Signalisierung()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				FahrstrasseFactory.eINSTANCE.createFstr_Umfahrpunkt()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				FahrstrasseFactory.eINSTANCE.createFstr_Zug_Rangier()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				FahrstrasseFactory.eINSTANCE.createMarkanter_Punkt()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				FahrstrasseFactory.eINSTANCE.createSonstiger_Punkt()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				SignaleFactory.eINSTANCE.createSignal()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				SignaleFactory.eINSTANCE.createSignal_Befestigung()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				SignaleFactory.eINSTANCE.createSignal_Fank_Zuordnung()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				SignaleFactory.eINSTANCE.createSignal_Rahmen()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				SignaleFactory.eINSTANCE.createSignal_Signalbegriff()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				LayoutinformationenFactory.eINSTANCE.createElement_Position()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				LayoutinformationenFactory.eINSTANCE.createElement_Stil()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				LayoutinformationenFactory.eINSTANCE.createLageplan()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				LayoutinformationenFactory.eINSTANCE
						.createLageplan_Blattschnitt()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				LayoutinformationenFactory.eINSTANCE.createLageplan_Zustand()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				LayoutinformationenFactory.eINSTANCE
						.createPlanPro_Layoutinfo()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				NahbedienungFactory.eINSTANCE.createNB()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				NahbedienungFactory.eINSTANCE
						.createNB_Bedien_Anzeige_Element()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				NahbedienungFactory.eINSTANCE.createNB_Zone()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				NahbedienungFactory.eINSTANCE.createNB_Zone_Element()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				NahbedienungFactory.eINSTANCE.createNB_Zone_Grenze()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				PZBFactory.eINSTANCE.createPZB_Element()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				PZBFactory.eINSTANCE.createPZB_Element_Zuordnung()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				PZBFactory.eINSTANCE.createPZB_Zuordnung_Signal()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				RegelzeichnungFactory.eINSTANCE.createRegelzeichnung()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				RegelzeichnungFactory.eINSTANCE
						.createRegelzeichnung_Parameter()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				SchluesselabhaengigkeitenFactory.eINSTANCE.createSchloss()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				SchluesselabhaengigkeitenFactory.eINSTANCE
						.createSchlosskombination()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				SchluesselabhaengigkeitenFactory.eINSTANCE.createSchluessel()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				SchluesselabhaengigkeitenFactory.eINSTANCE
						.createSchluesselsperre()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				Weichen_und_GleissperrenFactory.eINSTANCE
						.createGleis_Abschluss()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				Weichen_und_GleissperrenFactory.eINSTANCE.createW_Kr_Anlage()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				Weichen_und_GleissperrenFactory.eINSTANCE
						.createW_Kr_Gsp_Element()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				Weichen_und_GleissperrenFactory.eINSTANCE
						.createW_Kr_Gsp_Komponente()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				Weichen_und_GleissperrenFactory.eINSTANCE
						.createWeichenlaufkette()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				Weichen_und_GleissperrenFactory.eINSTANCE
						.createWeichenlaufkette_Zuordnung()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				ZuglenkungFactory.eINSTANCE.createZL()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				ZuglenkungFactory.eINSTANCE.createZL_DLP_Abschnitt()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				ZuglenkungFactory.eINSTANCE.createZL_DLP_Fstr()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				ZuglenkungFactory.eINSTANCE.createZL_Fstr()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				ZuglenkungFactory.eINSTANCE.createZL_Fstr_Anstoss()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				ZuglenkungFactory.eINSTANCE.createZL_Signalgruppe()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				ZuglenkungFactory.eINSTANCE.createZL_Signalgruppe_Zuordnung()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				ZugnummernmeldeanlageFactory.eINSTANCE.createZLV_Bus()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				ZugnummernmeldeanlageFactory.eINSTANCE
						.createZLV_Bus_Besondere_Anlage()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				ZugnummernmeldeanlageFactory.eINSTANCE
						.createZLV_Bus_US_Zuordnung()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				ZugnummernmeldeanlageFactory.eINSTANCE.createZN()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				ZugnummernmeldeanlageFactory.eINSTANCE.createZN_Akustik()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				ZugnummernmeldeanlageFactory.eINSTANCE.createZN_Anzeigefeld()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				ZugnummernmeldeanlageFactory.eINSTANCE
						.createZN_Fortschalt_Kriterium()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				ZugnummernmeldeanlageFactory.eINSTANCE
						.createZN_Telegramm_84_Zuordnung()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				ZugnummernmeldeanlageFactory.eINSTANCE
						.createZN_Telegramm_85_Zuordnung()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				ZugnummernmeldeanlageFactory.eINSTANCE
						.createZN_Unterstation()));

		newChildDescriptors.add(createChildParameter(
				TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT,
				ZugnummernmeldeanlageFactory.eINSTANCE.createZN_ZBS()));
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

		boolean qualify = childFeature == TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__FOOTNOTES
				|| childFeature == TablemodelPackage.Literals.SIMPLE_FOOTNOTE_CONTAINER__OWNER_OBJECT;

		if (qualify) {
			return getString("_UI_CreateChild_text2",
					new Object[] { getTypeText(childObject),
							getFeatureText(childFeature), getTypeText(owner) });
		}
		return super.getCreateChildText(owner, feature, child, selection);
	}

}
