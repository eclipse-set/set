/**
 * Copyright (c) 2018 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.application.addons

import de.scheidtbachmann.planpro.model.model1902.Basisobjekte.Ur_Objekt
import de.scheidtbachmann.planpro.model.model1902.PlanPro.PlanPro_Schnittstelle
import java.util.concurrent.ExecutionException
import javax.inject.Inject
import org.eclipse.e4.core.di.annotations.Optional
import org.eclipse.e4.core.services.events.IEventBroker
import org.eclipse.e4.ui.di.UIEventTopic
import org.eclipse.e4.ui.model.application.MApplication
import org.eclipse.e4.ui.workbench.UIEvents
import org.eclipse.set.basis.constants.ContainerType
import org.eclipse.set.basis.constants.Events
import org.eclipse.set.basis.constants.ToolboxConstants
import org.eclipse.set.core.services.Services
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup
import org.osgi.service.event.Event

import static extension org.eclipse.set.ppmodel.extensions.PlanProSchnittstelleExtensions.*

/**
 * GuidToObjectCache implementation to prepare the cache when a model is loaded
 * 
 * @author Stuecker
 */
class GuidToObjectCacheAddon {
	
	@Inject
	@Optional
	def void startUpComplete(
			@SuppressWarnings("unused") @UIEventTopic(UIEvents.UILifeCycle.APP_STARTUP_COMPLETE)  MApplication application) {
		val applicationContext = application.context;
		val eventBroker = applicationContext.get(IEventBroker);
		eventBroker.subscribe(Events.MODEL_CHANGED, [modelChangedHandler]);
	}

	private def modelChangedHandler(Event event) {
		val planProSchnittstelle = event.getProperty(
			IEventBroker.DATA) as PlanPro_Schnittstelle;
		fillCache(planProSchnittstelle)
	}

	private def void fillCache(PlanPro_Schnittstelle schnittstelle) {
		fillCache(schnittstelle.getContainer(ContainerType.INITIAL))
		fillCache(schnittstelle.getContainer(ContainerType.FINAL))
		fillCache(schnittstelle.getContainer(ContainerType.SINGLE))
	}

	def void fillCache(MultiContainer_AttributeGroup container) {
		if (container === null)
			return;

		val cacheString = container.getCacheString()
		val cache = Services.cacheService.getCache(
			ToolboxConstants.CacheId.GUID_TO_OBJECT, cacheString)
		for (Ur_Objekt object : container.urObjekt) {
			try {
				cache.get(object.identitaet.wert, [object])
			} catch (ExecutionException exc) {
				// Ignore exception
			};
		}
	}
}
