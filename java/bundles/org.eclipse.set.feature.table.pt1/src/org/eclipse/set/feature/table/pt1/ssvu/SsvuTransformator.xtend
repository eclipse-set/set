/**
 * Copyright (c) 2016 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.pt1.ssvu

import java.util.Set
import org.eclipse.emf.common.util.BasicEList
import org.eclipse.emf.common.util.EList
import org.eclipse.set.core.services.enumtranslation.EnumTranslationService
import org.eclipse.set.feature.table.pt1.AbstractPlanPro2TableModelTransformator
import org.eclipse.set.model.planpro.Ansteuerung_Element.Aussenelementansteuerung
import org.eclipse.set.model.planpro.Ansteuerung_Element.ESTW_Zentraleinheit
import org.eclipse.set.model.planpro.Ansteuerung_Element.Stell_Bereich
import org.eclipse.set.model.planpro.Ansteuerung_Element.Uebertragungsweg
import org.eclipse.set.model.planpro.Bahnuebergang.BUE_Schnittstelle
import org.eclipse.set.model.planpro.Basisobjekte.Basis_Objekt
import org.eclipse.set.model.planpro.Bedienung.Bedien_Bezirk
import org.eclipse.set.model.planpro.Bedienung.Bedien_Zentrale
import org.eclipse.set.model.planpro.PZB.PZB_Element
import org.eclipse.set.model.planpro.Schluesselabhaengigkeiten.Schluesselsperre
import org.eclipse.set.model.planpro.Signale.Signal
import org.eclipse.set.model.planpro.Weichen_und_Gleissperren.W_Kr_Gsp_Element
import org.eclipse.set.model.tablemodel.ColumnDescriptor
import org.eclipse.set.model.tablemodel.Table
import org.eclipse.set.model.tablemodel.TableRow
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup
import org.eclipse.set.utils.table.TMFactory
import org.osgi.service.event.EventAdmin

import static org.eclipse.set.feature.table.pt1.ssvu.SsvuColumns.*

import static extension org.eclipse.set.ppmodel.extensions.UrObjectExtensions.*

/**
 * Table transformation for a Übertragungswegtabelle (Ssvu).
 * 
 * @author Schneider
 */
class SsvuTransformator extends AbstractPlanPro2TableModelTransformator {

	var TMFactory factory = null
	var MultiContainer_AttributeGroup container = null

	new(Set<ColumnDescriptor> cols,
		EnumTranslationService enumTranslationService, EventAdmin eventAdmin) {
		super(cols, enumTranslationService, eventAdmin)
	}

	override transformTableContent(MultiContainer_AttributeGroup container,
		TMFactory factory, Stell_Bereich controlArea) {
		this.factory = factory
		this.container = container
		return container.transform(controlArea)
	}

	private def Table create factory.table transform(
		MultiContainer_AttributeGroup container, Stell_Bereich controlArea) {
		container.uebertragungsweg.filter[isPlanningObject].
			filterObjectsInControlArea(controlArea).forEach [ it |
				if (Thread.currentThread.interrupted) {
					return
				}
				it.transform
			]
		return
	}

	private def TableRow create factory.newTableRow(uebertragungsweg) transform(
		Uebertragungsweg uebertragungsweg) {

		val row = it

		// A: Ssvu.Grundsatzangaben.von	
		fill(cols.getColumn(von), uebertragungsweg, [transformToVon])

		// B: Ssvu.Grundsatzangaben.nach
		fill(cols.getColumn(nach), uebertragungsweg, [transformToNach])

		// C: Ssvu.Grundsatzangaben.Verwendung
		fill(cols.getColumn(Verwendung), uebertragungsweg, [
			transformToVerwendung
		])

		// D: Ssvu.Technik.Netzart
		fill(cols.getColumn(Netzart), uebertragungsweg, [transformToNetzart])

		// E: Ssvu.Technik.Technikart
		fill(cols.getColumn(Technikart), uebertragungsweg, [
			transformToTechnikart
		])

		// F: Ssvu.Technik.Schnittstelle
		fill(cols.getColumn(Schnittstelle), uebertragungsweg, [
			transformToSchnitstelle
		])

		// G: Ssvu.Technik.BranBreite
		fill(cols.getColumn(BranBreite), uebertragungsweg, [
			transformToBandbreite
		])

		// H: Ssvu.Bemerkung
		fill(cols.getColumn(Bemerkung), uebertragungsweg, [
			transformToBemerkung(row)
		])

		return
	}

	private def String create createUebertragungswegByID(uebertragungsweg?.IDUebertragungswegVon?.value?.identitaet?.wert) transformToVon(
		Uebertragungsweg uebertragungsweg
	) {
		return
	}

	private def String create createUebertragungswegByID(uebertragungsweg?.IDUebertragungswegNach?.value?.identitaet?.wert) transformToNach(
		Uebertragungsweg uebertragungsweg
	) {
		return
	}

	private def String createUebertragungswegByID(String uebertragungswegID) {

		var EList<Basis_Objekt> elementList = new BasicEList<Basis_Objekt>();

		elementList.addAll(container.signal);
		elementList.addAll(container.WKrGspElement);
		elementList.addAll(container.schluesselsperre);
		elementList.addAll(container.PZBElement);
		elementList.addAll(container.bedienBezirk);
		elementList.addAll(container.bedienZentrale);
		elementList.addAll(container.ESTWZentraleinheit);
		elementList.addAll(container.aussenelementansteuerung);
		elementList.addAll(container.BUESchnittstelle);

		return elementList.findFirst[idStellelement == uebertragungswegID].
			elementBezeichnung
	}

	private def String create createVerwendung(uebertragungsweg) transformToVerwendung(
		Uebertragungsweg uebertragungsweg
	) {
		return
	}

	private def String createVerwendung(Uebertragungsweg uebertragungsweg) {
		return uebertragungsweg?.uebertragungswegArt?.translate
	}

	private def String create createNetzart(uebertragungsweg) transformToNetzart(
		Uebertragungsweg uebertragungsweg
	) {
		return
	}

	private def String createNetzart(Uebertragungsweg uebertragungsweg) {
		return uebertragungsweg?.uebertragungswegTechnik?.netzArt?.translate
	}

	private def String create createTechnikart(uebertragungsweg) transformToTechnikart(
		Uebertragungsweg uebertragungsweg
	) {
		return
	}

	private def String createTechnikart(Uebertragungsweg uebertragungsweg) {
		return uebertragungsweg?.uebertragungswegTechnik?.technikArt?.
			translate
	}

	private def String create createSchnitstelle(uebertragungsweg) transformToSchnitstelle(
		Uebertragungsweg uebertragungsweg
	) {
		return
	}

	private def String createSchnitstelle(Uebertragungsweg uebertragungsweg) {
		return uebertragungsweg?.uebertragungswegTechnik?.mediumArt?.
			translate
	}

	private def String create createBandbreite(uebertragungsweg) transformToBandbreite(
		Uebertragungsweg uebertragungsweg
	) {
		return
	}

	private def String createBandbreite(Uebertragungsweg uebertragungsweg) {
		return uebertragungsweg?.uebertragungswegTechnik?.bandbreite?.
			translate
	}

	private def String create createBemerkung(uebertragungsweg, row) transformToBemerkung(
		Uebertragungsweg uebertragungsweg,
		TableRow row
	) {
		return
	}

	private def String createBemerkung(Uebertragungsweg uebertragungsweg,
		TableRow row) {
		val technikBeschreibung = uebertragungsweg?.uebertragungswegTechnik?.
			technikBeschreibung?.wert

		fillFootnotes(row, uebertragungsweg)
		val comments = newLinkedList(technikBeschreibung).filterNull.filter [
			!empty
		]
		return '''«FOR comment : comments SEPARATOR ", "»«comment»«ENDFOR»'''
	}

	private def dispatch String getIdStellelement(Basis_Objekt element) {
		throw new IllegalArgumentException(element.class.simpleName)
	}

	private def dispatch String getIdStellelement(Signal element) {
		return element?.signalReal?.signalRealAktiv?.IDStellelement?.value?.
			identitaet?.wert;
	}

	private def dispatch String getIdStellelement(W_Kr_Gsp_Element element) {
		return element?.IDStellelement?.value?.identitaet?.wert
	}

	private def dispatch String getIdStellelement(Schluesselsperre element) {
		return element?.IDStellelement?.value?.identitaet?.wert
	}

	private def dispatch String getIdStellelement(PZB_Element element) {
		return element?.IDStellelement?.value?.identitaet?.wert
	}

	private def dispatch String getIdStellelement(BUE_Schnittstelle element) {
		return element?.IDStellelement?.value?.identitaet?.wert
	}

	private def dispatch String getIdStellelement(Bedien_Bezirk element) {
		return element?.identitaet?.wert
	}

	private def dispatch String getIdStellelement(Bedien_Zentrale element) {
		return element?.identitaet?.wert
	}

	private def dispatch String getIdStellelement(ESTW_Zentraleinheit element) {
		return element?.identitaet?.wert
	}

	private def dispatch String getIdStellelement(
		Aussenelementansteuerung element) {
		return element?.identitaet?.wert
	}

	private def dispatch String getElementBezeichnung(Basis_Objekt element) {
		throw new IllegalArgumentException(element.class.simpleName)
	}

	private def dispatch String getElementBezeichnung(Signal element) {
		return element?.bezeichnung?.bezeichnungTabelle?.wert;
	}

	private def dispatch String getElementBezeichnung(
		W_Kr_Gsp_Element element) {
		return element?.bezeichnung?.bezeichnungTabelle?.wert
	}

	private def dispatch String getElementBezeichnung(
		Schluesselsperre element) {
		return element?.bezeichnung?.bezeichnungTabelle?.wert
	}

	private def dispatch String getElementBezeichnung(PZB_Element element) {
		val pzbZuordnungSignal = container?.PZBZuordnungSignal.findFirst [
			identitaet?.wert ==
				element?.IDPZBElementZuordnung?.value?.identitaet?.wert
		]
		return container?.signal.findFirst [
			identitaet?.wert ==
				pzbZuordnungSignal?.IDSignal?.value?.identitaet?.wert
		]?.bezeichnung?.bezeichnungTabelle?.wert
	}

	private def dispatch String getElementBezeichnung(
		BUE_Schnittstelle element) {
		// IMPROVE use cache
		val bueAnlage = container?.BUEAnlage.findFirst [
			IDBUESchnittstelle?.value?.identitaet?.wert ==
				element?.identitaet?.wert
		]
		return bueAnlage?.bezeichnung?.bezeichnungTabelle?.wert
	}

	private def dispatch String getElementBezeichnung(Bedien_Bezirk element) {
		return element?.bedienBezirkAllg?.steuerbezirksname?.wert
	}

	private def dispatch String getElementBezeichnung(Bedien_Zentrale element) {
		return element?.bezeichnung?.bezBedZentrale?.wert
	}

	private def dispatch String getElementBezeichnung(
		ESTW_Zentraleinheit element) {
		return element?.bezeichnung?.bezeichnungESTWZE?.wert
	}

	private def dispatch String getElementBezeichnung(
		Aussenelementansteuerung element) {
		return element?.bezeichnung?.bezeichnungAEA?.wert
	}
}
