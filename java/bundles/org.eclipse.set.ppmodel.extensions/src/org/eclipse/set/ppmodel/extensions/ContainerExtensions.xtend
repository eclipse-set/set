/**
 * Copyright (c) 2017 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import de.scheidtbachmann.planpro.model.model1902.Basisobjekte.Basis_Objekt
import de.scheidtbachmann.planpro.model.model1902.Basisobjekte.Punkt_Objekt
import de.scheidtbachmann.planpro.model.model1902.PlanPro.Ausgabe_Fachdaten
import de.scheidtbachmann.planpro.model.model1902.PlanPro.Container_AttributeGroup
import de.scheidtbachmann.planpro.model.model1902.PlanPro.LST_Zustand
import de.scheidtbachmann.planpro.model.model1902.PlanPro.PlanPro_Schnittstelle
import de.scheidtbachmann.planpro.model.model1902.PlanPro.Planung_Einzel
import java.util.LinkedList
import java.util.List
import org.eclipse.emf.ecore.EObject
import org.eclipse.set.basis.constants.ContainerType
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup
import org.eclipse.set.ppmodel.extensions.utils.LstObjectAttribute

import static extension org.eclipse.set.ppmodel.extensions.BasisObjektExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.PlanProSchnittstelleExtensions.*

/**
 * Extensions for {@link MultiContainer_AttributeGroup}.
 * 
 * @author Schaefer
 */
class ContainerExtensions {

	/**
	 * @param container this container
	 * 
	 * @return the number of LST-Objekte in this container
	 */
	static def int getSize(Container_AttributeGroup container) {
		return container.eContents.size
	}

	def static Iterable<Punkt_Objekt> getPunktObjekte(
		MultiContainer_AttributeGroup container) {
		val List<Punkt_Objekt> punktObjekte = new LinkedList<Punkt_Objekt>

		// PLANPRO-2324 filter for Punkt_Objekt in container.eContents - measure impact on performance!
		punktObjekte.addAll(container.bahnsteigZugang)
		punktObjekte.addAll(container.BUEAnlage)
		punktObjekte.addAll(container.FMAElement)
		punktObjekte.addAll(container.FMAKomponente)
		punktObjekte.addAll(container.gleisAbschluss)
		punktObjekte.addAll(container.hoehenpunkt);
		punktObjekte.addAll(container.PZBElement);
		punktObjekte.addAll(container.signal)
		punktObjekte.addAll(container.signalBefestigung)
		punktObjekte.addAll(container.sonstigerPunkt)
		punktObjekte.addAll(container.technischerPunkt)
		punktObjekte.addAll(container.ueberhoehung)
		punktObjekte.addAll(container.WKrGspKomponente)
		punktObjekte.addAll(container.zugeinwirkung)

		return punktObjekte
	}

	def static Iterable<Basis_Objekt> getNBElemente(
		MultiContainer_AttributeGroup container) {
		return container.signal + container.WKrGspKomponente + container.schluesselsperre
	}

	def static Iterable<Basis_Objekt> getSchalter(
		MultiContainer_AttributeGroup container) {
		return container.FMAAnlage + container.FMAKomponente + container.zugeinwirkung
	}

	/**
	 * @param container this container
	 * 
	 * @return list of Basis Objekte which may qualify as angrenzende Elemente
	 * for a GZ Freimeldung L/R
	 */
	def static Iterable<Basis_Objekt> getGZFreimeldungAngrenzendeElemente(
		MultiContainer_AttributeGroup container) {
		return container.WKrGspElement + container.gleisAbschnitt
	}

	/** 
	 * Returns a unique id to be used for caching data for this container.
	 * The id will be consistent across executions
	 * 
	 * @param container this container
	 * 
	 * @return a unique caching id.
	 */
	def static String getCacheId(MultiContainer_AttributeGroup container) {
		return container.cacheString
	}

	/**
	 * @param container this container
	 * 
	 * @return the associated Einzelplanung of this container
	 */
	def static Planung_Einzel getPlanungEinzel(
		Container_AttributeGroup container
	) {
		val planning = container.planProSchnittstelle.LSTPlanung.
			objektmanagement.eAllContents.filter(Planung_Einzel).filter [
				IDAusgabeFachdaten.wert ==
					container.ausgabeFachdaten.identitaet.wert
			].toSet

		if (planning.size !== 1) {
			throw new IllegalArgumentException(
				'''No Planung_Einzel for Container of Zustand «container.zustandGuid» found.'''
			)
		}

		return planning.get(0)
	}

	/**
	 * @param container this container
	 * 
	 * @return the guid of the Zustand containing this container
	 */
	def static String getZustandGuid(Container_AttributeGroup container) {
		return container.eContainer.guid
	}

	/**
	 * @param container this container
	 * 
	 * @return list of all LST object/attributes
	 */
	def static Ausgabe_Fachdaten getAusgabeFachdaten(
		Container_AttributeGroup container
	) {
		return container.eContainer.eContainer as Ausgabe_Fachdaten
	}

	/**
	 * @param container this container
	 * 
	 * @return the PlanPro Schnittstelle of this container
	 */
	def static PlanPro_Schnittstelle getPlanProSchnittstelle(
		Container_AttributeGroup container) {
		return getPlanProSchnittstelleDispatch(container)
	}

	/**
	 * @param container this container
	 * 
	 * @return a representation, optimized for log output
	 */
	def static String logString(MultiContainer_AttributeGroup container) {
		val schnittstelleType = container.planProSchnittstelle.
			eContainingFeature
		if (schnittstelleType !== null) {
			return '''«schnittstelleType.name»/«container.containerType»'''
		}
		return '''«container.containerType»'''
	}

	/**
	 * @param container this container
	 * 
	 * @return the container type
	 */
	def static ContainerType containerType(
		MultiContainer_AttributeGroup container) {
		val schnittstelle = container.planProSchnittstelle

		if (schnittstelle.getContainer(ContainerType.SINGLE) === container) {
			return ContainerType.SINGLE
		}

		if (schnittstelle.getContainer(ContainerType.INITIAL) === container) {
			return ContainerType.INITIAL
		}

		if (schnittstelle.getContainer(ContainerType.FINAL) === container) {
			return ContainerType.FINAL
		}

		return null
	}

	/**
	 * @param container this container
	 * 
	 * @return the PlanPro Schnittstelle of this container
	 */
	def static PlanPro_Schnittstelle getPlanProSchnittstelle(
		MultiContainer_AttributeGroup container) {
		return getPlanProSchnittstelleDispatch(container.firstLSTZustand)
	}

	/**
	 * @param container this container
	 * 
	 * @return list of all LST object/attributes
	 */
	def static List<LstObjectAttribute> getObjectAttributes(
		MultiContainer_AttributeGroup container
	) {
		return container.basisObjekt.map [
			objectAttributes
		].flatten.toList
	}

	private def static dispatch PlanPro_Schnittstelle getPlanProSchnittstelleDispatch(
		EObject object
	) {
		return object.eContainer.planProSchnittstelleDispatch
	}

	private def static dispatch PlanPro_Schnittstelle getPlanProSchnittstelleDispatch(
		PlanPro_Schnittstelle schnittstelle
	) {
		return schnittstelle
	}

	def static dispatch String getGuid(EObject object) {
		throw new IllegalArgumentException(object.eClass.name)
	}

	def static dispatch String getGuid(LST_Zustand zustand) {
		return zustand.identitaet.wert
	}
}
