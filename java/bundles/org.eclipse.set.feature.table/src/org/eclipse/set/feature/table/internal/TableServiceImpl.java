/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.internal;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.basis.MissingSupplier;
import org.eclipse.set.basis.cache.Cache;
import org.eclipse.set.basis.constants.ContainerType;
import org.eclipse.set.basis.constants.Events;
import org.eclipse.set.basis.constants.TableType;
import org.eclipse.set.basis.constants.ToolboxConstants;
import org.eclipse.set.basis.constants.ToolboxViewState;
import org.eclipse.set.basis.extensions.MApplicationElementExtensions;
import org.eclipse.set.basis.graph.AbstractDirectedEdgePath;
import org.eclipse.set.basis.graph.Digraphs;
import org.eclipse.set.basis.part.PartDescription;
import org.eclipse.set.basis.threads.Threads;
import org.eclipse.set.core.services.Services;
import org.eclipse.set.core.services.cache.CacheService;
import org.eclipse.set.feature.table.PlanPro2TableTransformationService;
import org.eclipse.set.feature.table.TableService;
import org.eclipse.set.feature.table.messages.Messages;
import org.eclipse.set.model.planpro.Ansteuerung_Element.Stell_Bereich;
import org.eclipse.set.model.tablemodel.ColumnDescriptor;
import org.eclipse.set.model.tablemodel.Table;
import org.eclipse.set.model.tablemodel.TableRow;
import org.eclipse.set.model.tablemodel.TablemodelFactory;
import org.eclipse.set.model.tablemodel.extensions.TableCellExtensions;
import org.eclipse.set.model.tablemodel.extensions.TableExtensions;
import org.eclipse.set.model.tablemodel.extensions.TableRowExtensions;
import org.eclipse.set.ppmodel.extensions.ContainerExtensions;
import org.eclipse.set.ppmodel.extensions.utils.TableNameInfo;
import org.eclipse.set.services.table.TableDiffService;
import org.eclipse.set.utils.BasePart;
import org.eclipse.set.utils.ToolboxConfiguration;
import org.eclipse.set.utils.table.TableError;
import org.eclipse.set.utils.table.TableTransformationService;
import org.eclipse.swt.widgets.Display;
import org.eclipse.xtext.xbase.lib.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;

import jakarta.inject.Inject;

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

	static final Logger logger = LoggerFactory.getLogger(TableService.class);

	@Inject
	private TableDiffService diffService;

	@Inject
	private IEventBroker broker;

	@Inject
	CacheService cacheService;

	@Inject
	@Translation
	Messages messages;

	private final Map<String, PlanPro2TableTransformationService> modelServiceMap = new ConcurrentHashMap<>();
	private static final Queue<Pair<BasePart, Runnable>> transformTableThreads = new LinkedList<>();

	private static final String EMPTY = "empty"; //$NON-NLS-1$

	private CacheService getCache() {
		return ToolboxConfiguration.isDebugMode() ? Services.getNoCacheService()
				: cacheService;
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
	void addModelService(final PlanPro2TableTransformationService service,
			final Map<String, Object> properties)
			throws IllegalAccessException {
		final String elementId = TableServiceContextFunction
				.getElementId(properties);
		modelServiceMap.put(elementId, service);
	}

	void addModelServiceById(final String elementId,
			final PlanPro2TableTransformationService service) {
		modelServiceMap.put(elementId, service);
	}

	private Table createDiffTable(final String elementId,
			final IModelSession modelSession, final Stell_Bereich controlArea) {

		final Table startTable = transformToTable(elementId, TableType.INITIAL,
				modelSession, controlArea == null ? Collections.emptyMap()
						: Map.of(controlArea, ContainerType.INITIAL));
		final Table zielTable = transformToTable(elementId, TableType.FINAL,
				modelSession, controlArea == null ? Collections.emptyMap()
						: Map.of(controlArea, ContainerType.FINAL));
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

	private PlanPro2TableTransformationService getModelService(
			final String elementId) {
		final PlanPro2TableTransformationService modelService = modelServiceMap
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
		return new ArrayList<>(modelServiceMap.keySet());
	}

	@Override
	public Set<Integer> getFixedColumns(final String elementID) {
		return getModelService(extractShortcut(elementID)).getFixedColumnsPos();
	}

	@Override
	@SuppressWarnings("unchecked")
	public Map<String, Collection<TableError>> getTableErrors() {
		final HashMap<String, Collection<TableError>> map = new HashMap<>();
		final Cache cache = getCache()
				.getCache(ToolboxConstants.CacheId.TABLE_ERRORS);
		final Collection<String> tableShortcuts = cache.getKeys();
		tableShortcuts.forEach(shortCut -> map.put(shortCut,
				(Collection<TableError>) cache.getIfPresent(shortCut)));
		return map;
	}

	@SuppressWarnings("unchecked")
	private boolean combineTableErrors(final String shortCut) {
		final Collection<TableError> initialErrors = (Collection<TableError>) getCache()
				.getCache(ToolboxConstants.CacheId.TABLE_ERRORS_INITIAL)
				.getIfPresent(shortCut);
		final Collection<TableError> finalErrors = (Collection<TableError>) getCache()
				.getCache(ToolboxConstants.CacheId.TABLE_ERRORS_FINAL)
				.getIfPresent(shortCut);
		if (initialErrors == null || finalErrors == null) {
			return false;
		}
		final Collection<TableError> combined = new ArrayList<>();
		combined.addAll(initialErrors);
		combined.addAll(finalErrors);
		getCache().getCache(ToolboxConstants.CacheId.TABLE_ERRORS).set(shortCut,
				combined);
		broker.post(Events.TABLEERROR_CHANGED, null);
		return true;
	}

	private void saveTableError(final String shortCut,
			final TableType tableType, final Collection<TableError> errors) {
		final String shortName = getTableNameInfo(shortCut).getShortName();
		errors.forEach(error -> {
			error.setSource(shortName);
			error.setTableType(tableType);
		});
		switch (tableType) {
		case INITIAL:
			getCache().getCache(ToolboxConstants.CacheId.TABLE_ERRORS_INITIAL)
					.set(shortCut, errors);
			break;
		case FINAL:
			getCache().getCache(ToolboxConstants.CacheId.TABLE_ERRORS_FINAL)
					.set(shortCut, errors);
			break;
		default:
			return;
		}
		combineTableErrors(shortCut);
	}

	private Object loadTransform(final String elementId,
			final TableType tableType, final IModelSession modelSession,
			final Stell_Bereich controlArea) {
		final String shortCut = extractShortcut(elementId);
		final PlanPro2TableTransformationService modelService = getModelService(
				shortCut);
		final Table transformedTable;
		if (tableType == TableType.DIFF) {
			transformedTable = createDiffTable(elementId, modelSession,
					controlArea);
			modelService.format(transformedTable);
		} else {
			transformedTable = modelService.transform(
					modelSession.getContainer(tableType.getContainerForTable()),
					controlArea);
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
	 * @param properties
	 *            the service properties
	 * @throws IllegalAccessException
	 *             if the table.shortcut property is not set
	 */
	public void removeModelService(final Map<String, Object> properties)
			throws IllegalAccessException {
		final String elementId = TableServiceContextFunction
				.getElementId(properties);
		modelServiceMap.remove(elementId);
	}

	@Override
	public String transformToCsv(final String elementId,
			final TableType tableType, final IModelSession modelSession,
			final Map<Stell_Bereich, ContainerType> controlAreas) {
		final Table table = transformToTable(elementId, tableType, modelSession,
				controlAreas);
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
			final TableType tableType, final IModelSession modelSession,
			final Map<Stell_Bereich, ContainerType> controlAreas) {
		final String shortCut = extractShortcut(elementId);
		final String containerId = getContainerCacheId(modelSession, tableType);
		final Cache cache = getCache().getCache(
				ToolboxConstants.SHORTCUT_TO_TABLE_CACHE_ID, containerId);
		if (controlAreas.isEmpty()) {
			return (Table) cache.get(
					cacheService.cacheKeyBuilder(shortCut, EMPTY),
					() -> loadTransform(shortCut, tableType, modelSession,
							null));
		}
		final Table resultTable = TablemodelFactory.eINSTANCE.createTable();
		controlAreas.forEach((area, type) -> {
			if (!type.getDefaultTableType().equals(tableType)
					&& tableType != TableType.DIFF) {
				return;
			}
			final String areaCacheKey = cacheService.cacheKeyBuilder(shortCut,
					area.getIdentitaet().getWert());
			final Table table = (Table) cache.get(areaCacheKey,
					() -> loadTransform(shortCut, tableType, modelSession,
							area));
			if (resultTable.getColumndescriptors().isEmpty()) {
				resultTable.getColumndescriptors().addAll(
						EcoreUtil.copyAll(table.getColumndescriptors()));
				resultTable.setTablecontent(
						EcoreUtil.copy(table.getTablecontent()));
			} else {
				table.getTablecontent().getRowgroups()
						.forEach(rowGroup -> TableExtensions
								.addRowGroup(resultTable, rowGroup));
			}
		});

		// sorting
		if (resultTable.getTablecontent() != null) {
			ECollections.sort(resultTable.getTablecontent().getRowgroups(),
					getModelService(shortCut).getRowGroupComparator());
		}

		return resultTable;
	}

	@Override
	public Table transformToTable(final String elementId,
			final TableType tableType, final IModelSession modelSession) {
		final String shortCut = extractShortcut(elementId);
		final String containerId = getContainerCacheId(modelSession, tableType);
		final Cache cache = getCache().getCache(
				ToolboxConstants.SHORTCUT_TO_TABLE_CACHE_ID, containerId);
		return (Table) cache.get(shortCut,
				() -> loadTransform(shortCut, tableType, modelSession, null));
	}

	@Override
	public void updateTable(final BasePart tablePart,
			final Runnable updateTableHandler, final Runnable clearInstance) {
		final MElementContainer<MUIElement> parent = tablePart.getToolboxPart()
				.getParent();

		// Get already open table parts
		final List<MPart> openTableParts = parent.getChildren().stream()
				.filter(child -> child instanceof final MPart part
						&& part.getElementId().startsWith(
								ToolboxConstants.TABLE_PART_ID_PREFIX)
						&& part.isVisible())
				.map(MPart.class::cast).toList();

		transformTableThreads.add(new Pair<>(tablePart, updateTableHandler));
		final List<MPart> parts = transformTableThreads.stream()
				.map(pair -> pair.getKey().getToolboxPart()).toList();

		// Create a loading monitor only when the current table part isn't open
		// or already collect all transform handler of the open table parts
		final IRunnableWithProgress updateTableProgress = !tablePart
				.getToolboxPart().isVisible()
				|| parts.containsAll(openTableParts) ? createProgressMonitor()
						: null;

		if (updateTableProgress != null) {
			final ProgressMonitorDialog monitor = new ProgressMonitorDialog(
					tablePart.getToolboxShell());
			try {
				logger.info("Start ProgressMonitorDialog..."); //$NON-NLS-1$
				monitor.run(true, true, updateTableProgress);
			} catch (final InvocationTargetException e) {
				logger.error(e.toString(), e);
				throw new RuntimeException(e);
			} catch (final InterruptedException e) {
				clearInstance.run();
				transformTableThreads
						.forEach(pair -> MApplicationElementExtensions
								.setViewState(pair.getKey().getToolboxPart(),
										ToolboxViewState.CANCELED));

				Thread.currentThread().interrupt();
			}
		}
	}

	private IRunnableWithProgress createProgressMonitor() {
		// runnable for the transformation
		return monitor -> {
			// start a single task with unknown timeframe
			monitor.beginTask(
					messages.Abstracttableview_transformation_progress,
					transformTableThreads.size() > 1
							? transformTableThreads.size()
							: IProgressMonitor.UNKNOWN);
			// listen to cancel
			Threads.stopCurrentOnCancel(monitor);

			// Wait for table transform
			for (Pair<BasePart, Runnable> transformThread; (transformThread = transformTableThreads
					.poll()) != null;) {
				final String shortcut = extractShortcut(transformThread.getKey()
						.getToolboxPart().getElementId());
				final TableNameInfo tableNameInfo = getTableNameInfo(shortcut);
				monitor.subTask(tableNameInfo.getFullDisplayName());
				Display.getDefault().syncExec(transformThread.getValue());
				monitor.worked(1);
			}
			if (monitor.isCanceled()) {
				throw new InterruptedException();
			}
			// stop progress
			monitor.done();
			logger.info("ProgressMonitorDialog done."); //$NON-NLS-1$

		};
	}

	/**
	 * Activation after injection.
	 */
	public void activate() {
		wireCacheSupplier();
	}

	private void wireCacheSupplier() {
		AbstractDirectedEdgePath.setEdgeToPointsCacheSupplier(
				() -> new EdgeToPointsCacheProxy(getCache()));
		Digraphs.setEdgeToSubPathCacheSupplier(() -> getCache()
				.getCache(ToolboxConstants.CacheId.DIRECTED_EDGE_TO_SUBPATH));
	}
}
