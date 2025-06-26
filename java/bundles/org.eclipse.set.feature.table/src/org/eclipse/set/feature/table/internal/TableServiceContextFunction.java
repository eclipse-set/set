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
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.set.feature.table.PlanPro2TableTransformationService;
import org.eclipse.set.services.table.TableService;
import org.eclipse.set.utils.table.TableInfo;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * Create and publish the {@link TableService}.
 * 
 * @author Schaefer
 * 
 * @usage production
 */
@Component(service = IContextFunction.class, property = "service.context.key:String=org.eclipse.set.services.table.TableService")
public class TableServiceContextFunction extends ContextFunction {

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
		if (idObject != null && categoryObject != null) {
			return new TableInfo(categoryObject.toString(),
					idObject.toString());
		}
		throw new IllegalAccessException(
				"table.shortcut or table.category missing in properties"); //$NON-NLS-1$
	}

	private final Map<TableInfo, PlanPro2TableTransformationService> modelServiceMap = new ConcurrentHashMap<>();

	private TableServiceImpl tableService;

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
		if (tableService != null) {
			tableService.addModelService(service, properties);
		} else {
			modelServiceMap.put(tableInfo, service);
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
		final MApplication application = context.get(MApplication.class);
		final IEclipseContext applicationContext = application.getContext();
		applicationContext.set(TableService.class, tableService);
		return tableService;
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
		if (tableService != null) {
			tableService.removeModelService(properties);
		} else {
			modelServiceMap.remove(tableInfo);
		}
	}
}
