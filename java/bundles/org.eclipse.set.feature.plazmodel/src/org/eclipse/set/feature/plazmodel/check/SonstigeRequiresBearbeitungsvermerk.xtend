/**
 * Copyright (c) 2022 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.plazmodel.check

import java.util.Map
import org.eclipse.set.model.plazmodel.PlazFactory
import org.osgi.service.component.annotations.Component

import org.eclipse.set.basis.IModelSession
import org.eclipse.set.model.planpro.Bahnsteig.Bahnsteig_Zugang
import org.eclipse.set.model.planpro.Bahnsteig.ENUMBahnsteigZugangArt
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.xbase.lib.Functions.Function1
import java.util.Iterator
import org.eclipse.set.model.planpro.Ansteuerung_Element.Aussenelementansteuerung
import org.eclipse.set.model.planpro.Ansteuerung_Element.ENUMAussenelementansteuerungArt
import org.eclipse.set.model.planpro.Ansteuerung_Element.ENUMBandbreite
import org.eclipse.set.model.planpro.Ansteuerung_Element.Uebertragungsweg
import org.eclipse.set.model.planpro.Basisobjekte.Basis_Objekt

/**
 * Validates that any enum value "Sonstige" has an according bearbeitungsvermerk anywhere
 * 
 * @author mariusheine
 */
@Component
class SonstigeRequiresBearbeitungsvermerk implements PlazCheck {	
	final Check<Basis_Objekt>[] RELEVANT_ENTITIES = #[
		new Check(Aussenelementansteuerung, [ aussenElementAnsteuerungArt ], "aussenelementansteuerungArt"),
		new Check(Uebertragungsweg, [ bandbreite ], "aussenelementansteuerungArt"),
		new Check(Bahnsteig_Zugang, [ bahnsteigZugangArt ], "bahnsteigZugangArt")
	];
	
	override run(IModelSession modelSession) {
		return RELEVANT_ENTITIES.flatMap([
			return it.singleRun(modelSession.planProSchnittstelle.eAllContents)
		]).toList;
	}

	private def singleRun(Check<Basis_Objekt> check, Iterator<EObject> contents) {
		return contents.filter(check.entityClass).
			filter(check.hasSonstige).
			filter([ hasBearbeitungsVermerk ]).
			map [
				val err = PlazFactory.eINSTANCE.createPlazError
				err.message = transformErrorMsg(Map.of(
					"GUID", it.identitaet?.wert ?: "(ohne Identität)",
					"Typ", check.entityClass.canonicalName,
					"Feld", check.enumField
				))
				err.type = checkType
				err.object = it
				return err
			].toList
	}
	
	private def boolean hasBearbeitungsVermerk(Basis_Objekt objekt) {
		return objekt.IDBearbeitungsvermerk.size > 0;
	}
	
	private def boolean aussenElementAnsteuerungArt(Aussenelementansteuerung ansteuerungElement) {
		return ansteuerungElement.AEAAllg.aussenelementansteuerungArt.wert != ENUMAussenelementansteuerungArt.ENUM_AUSSENELEMENTANSTEUERUNG_ART_SONSTIGE_VALUE
	}
	
	private def boolean bandbreite(Uebertragungsweg uebertragungsweg) {
		return uebertragungsweg.uebertragungswegTechnik.bandbreite.wert != ENUMBandbreite.ENUM_BANDBREITE_SONSTIGE_VALUE;
	}
	
	private def boolean bahnsteigZugangArt(Bahnsteig_Zugang bahnsteigZugang) {
		return bahnsteigZugang.bahnsteigZugangAllg.bahnsteigZugangArt.wert != ENUMBahnsteigZugangArt.ENUM_BAHNSTEIG_ZUGANG_ART_SONSTIGE_VALUE
	}
	
	override checkType() {
		return "Bearbeitungsvermerk für Sonstige"
	}
	
	override getDescription() {
		return "Alles mit Referenz \"Sonstige\" hat einen Bearbeitungsvermerk."
	}
	
	override getGeneralErrMsg() {
		return "Fehlender Bearbeitungsvermerk in \"{Typ}\" weil \"{Feld}\" den Wert \"Sonstige\" hat."
	}
	
	private static class Check<T extends Basis_Objekt> {
		public var Class<T> entityClass;
		public var Function1<T, Boolean> hasSonstige;
		public var String enumField;
		
		new(Class<T> entityClass, Function1<T, Boolean> hasSonstige, String enumField) {
			this.entityClass = entityClass;
			this.hasSonstige = hasSonstige;
			this.enumField = enumField;
		}
	}
}
