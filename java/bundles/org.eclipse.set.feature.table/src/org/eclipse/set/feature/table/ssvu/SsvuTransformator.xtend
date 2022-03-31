/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.ssvu

import de.scheidtbachmann.planpro.model.model1902.Ansteuerung_Element.Aussenelementansteuerung
import de.scheidtbachmann.planpro.model.model1902.Ansteuerung_Element.ESTW_Zentraleinheit
import de.scheidtbachmann.planpro.model.model1902.Ansteuerung_Element.Uebertragungsweg
import de.scheidtbachmann.planpro.model.model1902.Bahnuebergang.BUE_Schnittstelle
import de.scheidtbachmann.planpro.model.model1902.Basisobjekte.Basis_Objekt
import de.scheidtbachmann.planpro.model.model1902.Bedienung.Bedien_Bezirk
import de.scheidtbachmann.planpro.model.model1902.Bedienung.Bedien_Zentrale
import de.scheidtbachmann.planpro.model.model1902.PZB.PZB_Element
import de.scheidtbachmann.planpro.model.model1902.Schluesselabhaengigkeiten.Schluesselsperre
import de.scheidtbachmann.planpro.model.model1902.Signale.Signal
import de.scheidtbachmann.planpro.model.model1902.Weichen_und_Gleissperren.W_Kr_Gsp_Element
import org.eclipse.emf.common.util.BasicEList
import org.eclipse.emf.common.util.EList
import org.eclipse.set.feature.table.AbstractPlanPro2TableModelTransformator
import org.eclipse.set.utils.table.TMFactory
import org.eclipse.set.feature.table.messages.MessagesWrapper
import org.eclipse.set.model.tablemodel.Table
import org.eclipse.set.model.tablemodel.TableRow
import org.eclipse.set.model.tablemodel.format.TextAlignment
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup

import static extension org.eclipse.set.model.tablemodel.extensions.TableExtensions.*

/**
 * Table transformation for a Übertragungswegtabelle (Ssvu).
 * 
 * @author Schneider
 */
class SsvuTransformator extends AbstractPlanPro2TableModelTransformator {

	val SsvuColumns cols

	var TMFactory factory = null
	var MultiContainer_AttributeGroup container = null

	new(SsvuColumns columns, MessagesWrapper messagesWrapper) {
		super(messagesWrapper)
		this.cols = columns;
	}

	override transformTableContent(MultiContainer_AttributeGroup container,
		TMFactory factory) {
		this.factory = factory
		this.container = container
		return container.transform
	}

	private def Table create factory.table transform(
		MultiContainer_AttributeGroup container) {
		container.uebertragungsweg.forEach[it|
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

		fill(cols.von, uebertragungsweg, [
			transformToVon
		])

		fill(cols.nach, uebertragungsweg, [
			transformToNach
		])

		fill(cols.verwendung, uebertragungsweg, [
			transformToNach
		])

		fill(cols.verwendung, uebertragungsweg, [
			transformToVerwendung
		])

		fill(cols.netzart, uebertragungsweg, [
			transformToNetzart
		])

		fill(cols.technikart, uebertragungsweg, [
			transformToTechnikart
		])

		fill(cols.schnittstelle, uebertragungsweg, [
			transformToSchnitstelle
		])

		fill(cols.bandbreite, uebertragungsweg, [
			transformToBandbreite
		])

		fill(cols.bemerkung, uebertragungsweg, [
			transformToBemerkung(row)
		])

		return
	}

	private def String create createUebertragungswegByID(uebertragungsweg?.IDUebertragungswegVon?.wert) transformToVon(
		Uebertragungsweg uebertragungsweg
	) {
		return
	}

	private def String create createUebertragungswegByID(uebertragungsweg?.IDUebertragungswegNach?.wert) transformToNach(
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
		return uebertragungsweg?.uebertragungswegArt?.wert?.translate
	}

	private def String create createNetzart(uebertragungsweg) transformToNetzart(
		Uebertragungsweg uebertragungsweg
	) {
		return
	}

	private def String createNetzart(Uebertragungsweg uebertragungsweg) {
		return uebertragungsweg?.uebertragungswegTechnik?.netzArt?.wert?.
			translate
	}

	private def String create createTechnikart(uebertragungsweg) transformToTechnikart(
		Uebertragungsweg uebertragungsweg
	) {
		return
	}

	private def String createTechnikart(Uebertragungsweg uebertragungsweg) {
		return uebertragungsweg?.uebertragungswegTechnik?.technikArt?.wert?.
			translate
	}

	private def String create createSchnitstelle(uebertragungsweg) transformToSchnitstelle(
		Uebertragungsweg uebertragungsweg
	) {
		return
	}

	private def String createSchnitstelle(Uebertragungsweg uebertragungsweg) {
		return uebertragungsweg?.uebertragungswegTechnik?.mediumArt?.wert?.
			translate
	}

	private def String create createBandbreite(uebertragungsweg) transformToBandbreite(
		Uebertragungsweg uebertragungsweg
	) {
		return
	}

	private def String createBandbreite(Uebertragungsweg uebertragungsweg) {
		return uebertragungsweg?.uebertragungswegTechnik?.bandbreite?.wert?.
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
		val other = footnoteTransformation.transform(uebertragungsweg, row)
		val comments = newLinkedList(technikBeschreibung, other).filterNull.
			filter[!empty]
		return '''«FOR comment : comments SEPARATOR ", "»«comment»«ENDFOR»'''
	}

	private def dispatch String getIdStellelement(Basis_Objekt element) {
		throw new IllegalArgumentException(element.class.simpleName)
	}

	private def dispatch String getIdStellelement(Signal element) {
		return element?.signalReal?.signalRealAktiv?.IDStellelement?.wert;
	}

	private def dispatch String getIdStellelement(W_Kr_Gsp_Element element) {
		return element?.IDStellelement?.wert
	}

	private def dispatch String getIdStellelement(Schluesselsperre element) {
		return element?.IDStellelement?.wert
	}

	private def dispatch String getIdStellelement(PZB_Element element) {
		return element?.IDStellelement?.wert
	}

	private def dispatch String getIdStellelement(BUE_Schnittstelle element) {
		return element?.IDStellelement?.wert
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
			identitaet?.wert == element?.IDPZBElementZuordnung?.wert
		]
		return container?.signal.findFirst [
			identitaet?.wert == pzbZuordnungSignal?.IDSignal?.wert
		]?.bezeichnung?.bezeichnungTabelle?.wert
	}

	private def dispatch String getElementBezeichnung(BUE_Schnittstelle element) {
		// IMPROVE use cache
		val bueAnlage = container?.BUEAnlage.findFirst [
			IDBUESchnittstelle?.wert == element?.identitaet?.wert
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

	override void formatTableContent(Table table) {
		// H: Ssvu.Bemerkung
		table.setTextAlignment(7, TextAlignment.LEFT);
	}
}
