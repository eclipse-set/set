/**
 * Copyright (c) 2022 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import java.util.concurrent.ExecutionException
import org.eclipse.set.basis.constants.ContainerType
import org.eclipse.set.basis.constants.ToolboxConstants
import org.eclipse.set.core.services.Services
import org.eclipse.set.model.planpro.BasisTypen.Zeiger_TypeClass
import org.eclipse.set.model.planpro.Basisobjekte.Ur_Objekt
import org.eclipse.set.model.planpro.PlanPro.PlanPro_Schnittstelle
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup
import org.eclipse.set.ppmodel.extensions.utils.UrObjectLoader

import static extension org.eclipse.set.ppmodel.extensions.PlanProSchnittstelleExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.PlanungEinzelExtensions.*

class MultiContainer_AttributeGroupExtensions {
	def static <T extends Ur_Objekt> T getObject(
		MultiContainer_AttributeGroup container,
		Class<T> clazz,
		Zeiger_TypeClass id
	) {
		return container.getObject(clazz, id?.wert)
	}

	def static <T extends Ur_Objekt> T getObject(
		MultiContainer_AttributeGroup container,
		Class<T> clazz,
		String guid
	) {
		if (guid === null) {
			return null
		}

		val cacheService = Services.getCacheService();
		val cache = cacheService.getCache(container.planProSchnittstelle,
			ToolboxConstants.CacheId.GUID_TO_OBJECT, container.cacheString)
		try {
			return cache.get(guid,
				new UrObjectLoader<T>(container.contents.filter [
					clazz.isInstance(it)
				].map[it as T], guid)) as T
		} catch (ExecutionException exc) {
			throw new RuntimeException(exc)
		}
	}

	def static ContainerType getContainerType(
		MultiContainer_AttributeGroup container) {
		val lstZustand = container.firstLSTZustand
		val schnittStelle = container.planProSchnittstelle
		if (schnittStelle?.LSTZustand !== null) {
			return ContainerType.SINGLE
		}

		val projects = schnittStelle.LSTPlanungGruppe
		if (projects.isEmpty) {
			return null
		}

		val initialZustands = projects.get.map [
			LSTPlanungEinzel?.LSTZustandStart?.identitaet?.wert
		].filterNull
		if (initialZustands.exists[it.equals(lstZustand.identitaet.wert)]) {
			return ContainerType.INITIAL
		}

		val finalZustands = projects.get.map [
			LSTPlanungEinzel?.LSTZustandZiel?.identitaet?.wert
		].filterNull
		if (finalZustands.exists[it.equals(lstZustand.identitaet.wert)]) {
			return ContainerType.FINAL
		}

		throw new IllegalArgumentException('''PlanProSchinttStelle not contains LST_Zustand: «lstZustand.identitaet.wert»''')
	}
	
	def static PlanPro_Schnittstelle getPlanProSchnittstelle(
		MultiContainer_AttributeGroup container) {
		val lstZustand = container.firstLSTZustand
		var parent = lstZustand.eContainer
		while (parent !== null && !(parent instanceof PlanPro_Schnittstelle)) {
			parent = parent.eContainer
		}
		return parent as PlanPro_Schnittstelle
	}
}
