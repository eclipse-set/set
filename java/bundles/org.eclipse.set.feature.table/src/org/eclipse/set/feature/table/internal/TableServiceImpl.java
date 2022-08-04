/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.inject.Inject;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.basis.MissingSupplier;
import org.eclipse.set.basis.cache.Cache;
import org.eclipse.set.basis.constants.Events;
import org.eclipse.set.basis.constants.TableType;
import org.eclipse.set.basis.constants.ToolboxConstants;
import org.eclipse.set.basis.graph.AbstractDirectedEdgePath;
import org.eclipse.set.basis.graph.Digraphs;
import org.eclipse.set.basis.part.PartDescription;
import org.eclipse.set.core.services.cache.CacheService;
import org.eclipse.set.feature.table.AbstractPlanPro2TableTransformationService;
import org.eclipse.set.feature.table.TableService;
import org.eclipse.set.model.tablemodel.ColumnDescriptor;
import org.eclipse.set.model.tablemodel.Table;
import org.eclipse.set.model.tablemodel.TableRow;
import org.eclipse.set.model.tablemodel.extensions.TableCellExtensions;
import org.eclipse.set.model.tablemodel.extensions.TableExtensions;
import org.eclipse.set.model.tablemodel.extensions.TableRowExtensions;
import org.eclipse.set.ppmodel.extensions.ContainerExtensions;
import org.eclipse.set.ppmodel.extensions.utils.TableNameInfo;
import org.eclipse.set.services.table.TableDiffService;
import org.eclipse.set.utils.table.TableError;
import org.eclipse.set.utils.table.TableTransformationService;

import com.google.common.base.Strings;

/**
 * TableService implementation with the task of transforming the planpro model
 * into table model instances for selected tables.
 * <p>
 * The table service collects {@link TableTransformationService} instances from
 * the osgi service registry for each toolbox table. These instances need to
 * fill in the osgi service property named <code>table.shortcut</code> with the
 * shortcut table name (e.g. "ssld").
 * <p>
 * The table service caches transformed tables and listens to to basic events in
 * the toolbox via the osgi event admin service on the topic
 * <code>modelsession/container/*</code> which will lead to invalidate all cache
 * entries in any case an event is received.
 * 
 * @author rumpf
 *
 */
public final class TableServiceImpl implements TableService {

	@Inject
	private CacheService cacheService;

	@Inject
	private TableDiffService diffService;

	@Inject
	private IEventBroker broker;

	private final Map<String, AbstractPlanPro2TableTransformationService> modelServiceMap = new ConcurrentHashMap<>();

	/**
	 * constructor.
	 */
	public TableServiceImpl() {
		super();
	}

	/**
	 * adds a model service. For a model service to be properly added it has to
	 * set the <code>table.shortcut</code property.
	 * 
	 * @param service
	 *            the service
	 * @param properties
	 *            the service properties
	 * @throws IllegalAccessException
	 *             if the table.shortcut property is not set
	 */
	void addModelService(
			final AbstractPlanPro2TableTransformationService service,
			final Map<String, Object> properties)
			throws IllegalAccessException {
		final String elementId = TableServiceContextFunction
				.getElementId(properties);
		modelServiceMap.put(elementId, service);
	}

	void addModelServiceById(final String elementId,
			final AbstractPlanPro2TableTransformationService service) {
		modelServiceMap.put(elementId, service);
	}

	private Table createDiffTable(final String elementId,
			final IModelSession modelSession) {
		final Table startTable = transformToTable(elementId, TableType.INITIAL,
				modelSession);
		final Table zielTable = transformToTable(elementId, TableType.FINAL,
				modelSession);
		if (zielTable == null || startTable == null) {
			return null;
		}
		return diffService.createDiffTable(startTable, zielTable);
	}

	@Override
	public String extractShortcut(final PartDescription element) {
		return element.getToolboxViewName().substring(0, 4);
	}

	@Override
	public String extractShortcut(final String elementId) {
		final String[] parts = elementId.split("\\."); //$NON-NLS-1$
		return parts[parts.length - 1];
	}

	private String getContainerCacheId(final IModelSession modelSession,
			final TableType tableType) {

		// For table diffs, combine initial and final cache ids
		if (tableType == TableType.DIFF) {
			return getContainerCacheId(modelSession, TableType.INITIAL) + "#" //$NON-NLS-1$
					+ getContainerCacheId(modelSession, TableType.FINAL);
		}

		// For other tables, use the container's cache id
		return tableType.toString() + "$" //$NON-NLS-1$
				+ ContainerExtensions.getCacheId(modelSession
						.getContainer(tableType.getContainerForTable()));
	}

	private AbstractPlanPro2TableTransformationService getModelService(
			final String elementId) {
		final AbstractPlanPro2TableTransformationService modelService = modelServiceMap
				.get(elementId.toLowerCase());
		if (modelService == null) {
			throw new IllegalArgumentException(
					"no model service for " + elementId + " found!"); //$NON-NLS-1$ //$NON-NLS-2$
		}
		return modelService;
	}

	@Override
	public TableNameInfo getTableNameInfo(final String shortcut) {
		return getModelService(shortcut).getTableNameInfo();
	}

	@Override
	public Collection<String> getAvailableTables() {
		final ArrayList<String> available = new ArrayList<>(
				modelServiceMap.keySet());
		// sskp is currently not available
		available.remove("sskp"); //$NON-NLS-1$
		return available;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Map<String, Collection<TableError>> getTableErrors() {
		final HashMap<String, Collection<TableError>> map = new HashMap<>();
		final Cache cache = cacheService
				.getCache(ToolboxConstants.CacheId.TABLE_ERRORS);
		final Collection<String> tableShortcuts = cache.getKeys();
		tableShortcuts.forEach(shortCut -> map.put(shortCut,
				(Collection<TableError>) cache.getIfPresent(shortCut)));
		return map;
	}

	@SuppressWarnings("unchecked")
	private boolean combineTableErrors(final String shortCut) {
		final Collection<TableError> initialErrors = (Collection<TableError>) cacheService
				.getCache(ToolboxConstants.CacheId.TABLE_ERRORS_INITIAL)
				.getIfPresent(shortCut);
		final Collection<TableError> finalErrors = (Collection<TableError>) cacheService
				.getCache(ToolboxConstants.CacheId.TABLE_ERRORS_FINAL)
				.getIfPresent(shortCut);
		if (initialErrors == null || finalErrors == null) {
			return false;
		}
		final Collection<TableError> combined = new ArrayList<>();
		combined.addAll(initialErrors);
		combined.addAll(finalErrors);
		cacheService.getCache(ToolboxConstants.CacheId.TABLE_ERRORS)
				.set(shortCut, combined);
		broker.post(Events.TABLEERROR_CHANGED, null);
		return true;
	}

	private void saveTableError(final String shortCut,
			final TableType tableType, final Collection<TableError> errors) {
		final String shortName = getTableNameInfo(shortCut).getShortName();
		errors.forEach(error -> error.setSource(shortName));
		switch (tableType) {
		case INITIAL:
			cacheService.getCache(ToolboxConstants.CacheId.TABLE_ERRORS_INITIAL)
					.set(shortCut, errors);
			break;
		case FINAL:
			cacheService.getCache(ToolboxConstants.CacheId.TABLE_ERRORS_FINAL)
					.set(shortCut, errors);
			break;
		default:
			return;
		}
		combineTableErrors(shortCut);
	}

	private Object loadTransform(final String elementId,
			final TableType tableType, final IModelSession modelSession,
			final String shortCut) {
		final AbstractPlanPro2TableTransformationService modelService = getModelService(
				shortCut);
		final Table transformedTable;
		if (tableType == TableType.DIFF) {
			transformedTable = createDiffTable(elementId, modelSession);
			modelService.format(transformedTable);
		} else {
			transformedTable = modelService.transform(modelSession
					.getContainer(tableType.getContainerForTable()));
			saveTableError(shortCut, tableType, modelService.getTableErrors());
		}
		if (Thread.currentThread().isInterrupted()
				|| transformedTable == null) {
			return MissingSupplier.MISSING_VALUE;
		}
		// sorting
		ECollections.sort(transformedTable.getTablecontent().getRowgroups(),
				modelService.getRowGroupComparator());

		return transformedTable;
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
			final AbstractPlanPro2TableTransformationService service,
			final Map<String, Object> properties)
			throws IllegalAccessException {
		final String elementId = TableServiceContextFunction
				.getElementId(properties);
		modelServiceMap.remove(elementId);
	}

	@Override
	public String transformToCsv(final String elementId,
			final TableType tableType, final IModelSession modelSession) {
		final Table table = transformToTable(elementId, tableType,
				modelSession);
		return transformToCsv(table);
	}

	/**
	 * 
	 * @param table
	 * @return table as csv string
	 */

	@SuppressWarnings("static-method")
	private String transformToCsv(final Table table) {
		final List<TableRow> rows = TableExtensions.getTableRows(table);
		final List<ColumnDescriptor> colNames = table.getColumndescriptors()
				.stream() //
				.filter(descriptor -> !Strings
						.isNullOrEmpty(descriptor.getLabel())) //
				.toList();
		final String delimeter = ";"; //$NON-NLS-1$
		String result = ""; //$NON-NLS-1$
		for (final ColumnDescriptor colName : colNames) {
			result += colName.getLabel() + delimeter;
		}
		result += "\n"; //$NON-NLS-1$

		for (final TableRow row : rows) {
			for (final ColumnDescriptor colName : colNames) {
				try {
					result += TableCellExtensions.getPlainStringValue(
							TableRowExtensions.getCell(row, colName))
							+ delimeter;
				} catch (final Exception e) {
					result += e.toString();
				}
			}
			result += "\n"; //$NON-NLS-1$
		}
		return result;
	}

	@Override
	public Table transformToTable(final String elementId,
			final TableType tableType, final IModelSession modelSession) {
		final String shortCut = extractShortcut(elementId);
		final String containerId = getContainerCacheId(modelSession, tableType);
		final Cache cache = cacheService.getCache(
				ToolboxConstants.SHORTCUT_TO_TABLE_CACHE_ID, containerId);
		return (Table) cache.get(shortCut, () -> loadTransform(shortCut,
				tableType, modelSession, shortCut));
	}

	/**
	 * Activation after injection.
	 */
	public void activate() {
		wireCacheSupplier();
	}

	private void wireCacheSupplier() {
		AbstractDirectedEdgePath.setEdgeToPointsCacheSupplier(
				() -> new EdgeToPointsCacheProxy(cacheService));
		Digraphs.setEdgeToSubPathCacheSupplier(() -> cacheService
				.getCache(ToolboxConstants.CacheId.DIRECTED_EDGE_TO_SUBPATH));
	}
}
