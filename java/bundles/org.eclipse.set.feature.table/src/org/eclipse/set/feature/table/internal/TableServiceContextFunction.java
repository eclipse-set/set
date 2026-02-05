/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.internal;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.e4.core.contexts.ContextFunction;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IContextFunction;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.basis.constants.Events;
import org.eclipse.set.basis.constants.ToolboxConstants;
import org.eclipse.set.basis.files.ToolboxFileRole;
import org.eclipse.set.basis.graph.AbstractDirectedEdgePath;
import org.eclipse.set.basis.graph.Digraphs;
import org.eclipse.set.core.services.Services;
import org.eclipse.set.core.services.session.SessionService;
import org.eclipse.set.feature.table.PlanPro2TableTransformationService;
import org.eclipse.set.services.table.TableDiffService;
import org.eclipse.set.services.table.TableDiffService.TableCompareType;
import org.eclipse.set.services.table.TableService;
import org.eclipse.set.utils.ToolboxConfiguration;
import org.eclipse.set.utils.table.TableInfo;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;

/**
 * Create and publish the {@link TableService}.
 * 
 * @author Schaefer
 * 
 * @usage production
 */
@Component(service = { IContextFunction.class,
		EventHandler.class }, property = {
				"service.context.key:String=org.eclipse.set.services.table.TableService",
				EventConstants.EVENT_TOPIC + "=" + Events.MODEL_CHANGED,
				EventConstants.EVENT_TOPIC + "=" + Events.COMPARE_MODEL_LOADED,
				EventConstants.EVENT_TOPIC + "=" + Events.CLOSE_SESSION })
public class TableServiceContextFunction extends ContextFunction
		implements EventHandler {

	@Reference
	SessionService sessionService;

	/**
	 * @param properties
	 *            the properties
	 * 
	 * @return the element id
	 * 
	 * @throws IllegalAccessException
	 *             if the properties to not contain the table shortcut
	 */
	public static TableInfo getTableInfo(final Map<String, Object> properties)
			throws IllegalAccessException {
		final Object idObject = properties.get("table.shortcut"); //$NON-NLS-1$
		final Object categoryObject = properties.get("table.category"); //$NON-NLS-1$
		final Object isInDevMode = properties.get("table.devMode"); //$NON-NLS-1$

		if (idObject != null && categoryObject != null) {
			return new TableInfo(categoryObject.toString(), idObject.toString(),
					Boolean.parseBoolean(isInDevMode == null ? "false" //$NON-NLS-1$
							: isInDevMode.toString()));
		}
		throw new IllegalAccessException(
				"table.shortcut or table.category missing in properties"); //$NON-NLS-1$
	}

	private TableServiceImpl tableService;
	private final Map<TableInfo, PlanPro2TableTransformationService> modelServiceMap = new ConcurrentHashMap<>();

	private final Map<TableCompareType, TableDiffService> diffServiceMap = new ConcurrentHashMap<>();

	/**
	 * Adds a model service. For a model service to be properly added it has to
	 * set the <code>table.shortcut</code property.
	 * 
	 * @param service
	 *            the service
	 * @param properties
	 *            the service properties
	 * @throws IllegalAccessException
	 *             if the table.shortcut property is not set
	 */
	@Reference(cardinality = ReferenceCardinality.MULTIPLE, policy = ReferencePolicy.DYNAMIC)
	public void addModelService(
			final PlanPro2TableTransformationService service,
			final Map<String, Object> properties)
			throws IllegalAccessException {
		final TableInfo tableInfo = getTableInfo(properties);
		if (tableInfo.isDevMode()
				&& !ToolboxConfiguration.isDevelopmentMode()) {
			return;
		}
		if (tableService != null) {
			tableService.addModelService(service, properties);
		} else {
			modelServiceMap.put(tableInfo, service);
		}
	}

	/**
	 * removes a model service.
	 * 
	 * @param service
	 *            the service
	 * @param properties
	 *            the service properties
	 * @throws IllegalAccessException
	 *             if the table.shortcut property is not set
	 */
	public void removeModelService(
			final PlanPro2TableTransformationService service,
			final Map<String, Object> properties)
			throws IllegalAccessException {
		final TableInfo tableInfo = getTableInfo(properties);
		if (tableInfo.isDevMode()
				&& !ToolboxConfiguration.isDevelopmentMode()) {
			return;
		}
		if (tableService != null) {
			tableService.removeModelService(properties);
		} else {
			modelServiceMap.remove(tableInfo);
		}
	}

	/**
	 * @param diffService
	 *            the {@link TableDiffService}
	 * @param properties
	 *            the osgi properties
	 */
	@Reference(cardinality = ReferenceCardinality.MULTIPLE, policy = ReferencePolicy.DYNAMIC)
	public void addTableDiffService(final TableDiffService diffService,
			final Map<String, String> properties) {
		if (tableService != null) {
			tableService.addDiffService(diffService);
		} else {
			diffServiceMap.put(diffService.getCompareType(), diffService);
		}
	}

	/**
	 * @param diffService
	 *            the {@link TableDiffService}
	 * @param properties
	 *            the osgi properites
	 */
	public void removeTableDiffService(final TableDiffService diffService,
			final Map<String, String> properties) {
		if (tableService != null) {
			tableService.removeDiffService(diffService.getCompareType());
		} else {
			diffServiceMap.remove(diffService.getCompareType());
		}
	}

	@Override
	public Object compute(final IEclipseContext context,
			final String contextKey) {
		tableService = ContextInjectionFactory.make(TableServiceImpl.class,
				context);
		modelServiceMap.keySet()
				.forEach(tableInfo -> tableService.addModelServiceByInfo(
						tableInfo, modelServiceMap.get(tableInfo)));
		diffServiceMap.forEach((compareType, service) -> tableService
				.addDiffSerivce(compareType, service));
		final MApplication application = context.get(MApplication.class);
		final IEclipseContext applicationContext = application.getContext();
		applicationContext.set(TableService.class, tableService);
		return tableService;
	}

	@Override
	public void handleEvent(final Event event) {
		if (event.getTopic().equals(Events.MODEL_CHANGED)
				|| event.getTopic().equals(Events.COMPARE_MODEL_LOADED)) {
			AbstractDirectedEdgePath.setEdgeToPointsCacheSupplier(
					schnittstelle -> EdgeToPointsCacheProxy.getCacheInstance(
							schnittstelle, Services.getCacheService()));
			Digraphs.setEdgeToSubPathCacheSupplier(schnitstelle -> Services
					.getCacheService()
					.getCache(schnitstelle,
							ToolboxConstants.CacheId.DIRECTED_EDGE_TO_SUBPATH));
			if (event.getTopic().equals(Events.MODEL_CHANGED)) {
				tableService.cleanWorkNotesProTable();
			}
		}

		if (event.getTopic().equals(Events.CLOSE_SESSION)) {
			final ToolboxFileRole closeSession = (ToolboxFileRole) event
					.getProperty(IEventBroker.DATA);
			final IModelSession loadedSession = sessionService
					.getLoadedSession(closeSession);
			EdgeToPointsCacheProxy.clearCacheInstance(
					loadedSession.getPlanProSchnittstelle());
			tableService.clearInstance();
		}
	}
}
