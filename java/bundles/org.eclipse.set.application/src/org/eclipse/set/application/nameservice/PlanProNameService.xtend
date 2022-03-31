/**
 * Copyright (c) 2019 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.application.nameservice

import de.scheidtbachmann.planpro.model.model1902.Bahnuebergang.BUE_Anlage
import de.scheidtbachmann.planpro.model.model1902.BasisTypen.BasisAttribut_AttributeGroup
import de.scheidtbachmann.planpro.model.model1902.Basisobjekte.Anhang
import de.scheidtbachmann.planpro.model.model1902.Basisobjekte.Basis_Objekt
import de.scheidtbachmann.planpro.model.model1902.Basisobjekte.Ur_Objekt
import de.scheidtbachmann.planpro.model.model1902.Fahrstrasse.ENUMFstrArt
import de.scheidtbachmann.planpro.model.model1902.Fahrstrasse.Fstr_DWeg
import de.scheidtbachmann.planpro.model.model1902.Fahrstrasse.Fstr_Zug_Rangier
import de.scheidtbachmann.planpro.model.model1902.Fahrstrasse.Markanter_Punkt
import de.scheidtbachmann.planpro.model.model1902.Geodaten.Ueberhoehung
import de.scheidtbachmann.planpro.model.model1902.Gleis.Gleis_Abschnitt
import de.scheidtbachmann.planpro.model.model1902.Signale.Signal
import de.scheidtbachmann.planpro.model.model1902.Weichen_und_Gleissperren.W_Kr_Gsp_Element
import java.util.Collections
import javax.inject.Inject
import org.eclipse.emf.ecore.EObject
import org.eclipse.set.basis.constants.ToolboxConstants
import org.eclipse.set.core.services.enumtranslation.EnumTranslationService
import org.eclipse.set.core.services.name.NameService
import org.eclipse.set.ppmodel.extensions.UrObjectExtensions
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import static extension org.eclipse.set.ppmodel.extensions.DwegExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.FahrwegExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.FstrUmfahrpunktExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.FstrZugRangierExtensions.*

/**
 * Implementation of {@link NameService} for the PlanPro model.
 * 
 * @author Schaefer
 */
class PlanProNameService implements NameService {

	@Inject
	EnumTranslationService enumTranslationService

	static final Logger LOGGER = LoggerFactory.getLogger(
		typeof(PlanProNameService));

	override getName(EObject object) {
		return object.nameDispatch
	}

	override getTypeName(EObject object) {
		return object.typeNameDispatch
	}

	override getValue(EObject object) {
		return object.valueDispatch
	}

	private def dispatch String typeNameDispatch(EObject object) {
		throw new IllegalArgumentException(object.toString)
	}

	private def dispatch String typeNameDispatch(Ur_Objekt object) {
		return UrObjectExtensions.getTypeName(object)
	}

	private def dispatch String nameDispatch(EObject object) {
		throw new IllegalArgumentException(object.toString)
	}

	private def dispatch String nameDispatch(
		BasisAttribut_AttributeGroup attribute) {
		return attribute.eContainingFeature.name
	}

	private def dispatch String nameDispatch(Void object) {
		return ""
	}

	private def dispatch String nameDispatch(Ur_Objekt object) {
		LOGGER.error('''«object.class.simpleName» not supported.''')
		return '''«object.identitaet.wert»'''
	}

	private def dispatch String nameDispatch(Anhang anhang) {
		return anhang?.anhangAllg?.dateiname?.wert ?: "(ohne Dateiname)"
	}

	private def dispatch String nameDispatch(Ueberhoehung ueberhoehung) {
		return ueberhoehung?.ueberhoehungAllg?.GEOPAD?.wert ?: "(ohne GEO PAD)"
	}

	private def dispatch String nameDispatch(W_Kr_Gsp_Element element) {
		return element?.bezeichnung?.bezeichnungTabelle?.wert ?:
			"(ohne Tabellenbezeichnung)"
	}

	private def dispatch String nameDispatch(BUE_Anlage anlage) {
		return anlage?.bezeichnung?.bezeichnungTabelle?.wert ?:
			"(ohne Tabellenbezeichnung)"
	}

	private def dispatch String nameDispatch(Fstr_Zug_Rangier fstr) {
		val fstrArt = fstr?.fstrZugRangierAllg?.fstrArt?.wert?.translate ?: "?"
		val start = fstr?.fstrFahrweg?.start?.bezeichnung?.bezeichnungTabelle?.
			wert ?: "?"
		val zielObjekt = fstr?.fstrFahrweg?.zielObjekt
		val ziel = switch (zielObjekt) {
			Signal: {
				zielObjekt?.bezeichnung?.bezeichnungTabelle?.wert ?: "?"
			}
			Markanter_Punkt: {
				zielObjekt?.bezeichnung?.bezeichnungMarkanterPunkt?.wert ?: "?"
			}
			default: {
				"?"
			}
		}
		val umfahrpunktList = fstr?.fstrFahrweg?.umfahrpunkte?.map [
			umfahrpunkt.umfahrpunktName
		].sortWith(ToolboxConstants.LST_OBJECT_NAME_COMPARATOR) ?:
			Collections.emptyList
		val umfahrpunkt = '''«FOR u : umfahrpunktList SEPARATOR ", "»«u»«ENDFOR»'''
		val dweg = fstr?.fstrDWeg?.bezeichnung?.bezeichnungFstrDWeg?.wert
		return '''«fstrArt» "«start»/«ziel»«IF !umfahrpunktList.empty» [«umfahrpunkt»]«ENDIF»«IF dweg !==null» («dweg»)«ENDIF»"'''
	}

	private def dispatch String nameDispatch(Fstr_DWeg dweg) {
		return dweg.fullName
	}

	private def dispatch String nameDispatch(Signal signal) {
		return signal?.bezeichnung?.bezeichnungTabelle?.wert ?:
			"(ohne Tabellenbezeichnung)"
	}

	private def String translate(ENUMFstrArt fstrArt) {
		return enumTranslationService.translate(fstrArt).presentation
	}

	private def dispatch String valueDispatch(EObject object) {
		throw new IllegalArgumentException(object.toString)
	}

	private def dispatch String valueDispatch(Void object) {
		return ""
	}

	private def dispatch String valueDispatch(
		BasisAttribut_AttributeGroup object) {
		val valueFunctions = object.class.methods.filter[it.name == "getWert"]
		if (valueFunctions.size != 1) {
			throw new IllegalArgumentException('''No unique "getWert" function found for «object.class»''')
		}
		val valueFunction = valueFunctions.get(0)
		try {
			return valueFunction.invoke(object).toString
		} catch (Exception e) {
			throw new RuntimeException(e)
		}
	}

	private def dispatch String umfahrpunktName(Basis_Objekt objekt) {
		throw new IllegalArgumentException(objekt.toString)
	}

	private def dispatch String umfahrpunktName(Void objekt) {
		return null
	}

	private def dispatch String umfahrpunktName(Gleis_Abschnitt abschnitt) {
		return abschnitt?.bezeichnung?.bezeichnungTabelle?.wert ?: "?"
	}

	private def dispatch String umfahrpunktName(W_Kr_Gsp_Element element) {
		return element?.bezeichnung?.bezeichnungTabelle?.wert ?: "?"
	}
}
