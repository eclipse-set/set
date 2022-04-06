/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.validation.table;

import java.util.Collection;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.data.IDataProvider;
import org.eclipse.nebula.widgets.nattable.freeze.CompositeFreezeLayer;
import org.eclipse.nebula.widgets.nattable.freeze.FreezeLayer;
import org.eclipse.nebula.widgets.nattable.freeze.command.FreezeColumnCommand;
import org.eclipse.nebula.widgets.nattable.grid.data.DefaultColumnHeaderDataProvider;
import org.eclipse.nebula.widgets.nattable.grid.data.DefaultCornerDataProvider;
import org.eclipse.nebula.widgets.nattable.grid.data.DefaultRowHeaderDataProvider;
import org.eclipse.nebula.widgets.nattable.grid.layer.ColumnHeaderLayer;
import org.eclipse.nebula.widgets.nattable.grid.layer.CornerLayer;
import org.eclipse.nebula.widgets.nattable.grid.layer.GridLayer;
import org.eclipse.nebula.widgets.nattable.grid.layer.RowHeaderLayer;
import org.eclipse.nebula.widgets.nattable.layer.AbstractLayerTransform;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell;
import org.eclipse.nebula.widgets.nattable.selection.SelectionLayer;
import org.eclipse.nebula.widgets.nattable.selection.config.DefaultMoveSelectionConfiguration;
import org.eclipse.nebula.widgets.nattable.selection.config.DefaultRowSelectionLayerConfiguration;
import org.eclipse.nebula.widgets.nattable.ui.matcher.MouseEventMatcher;
import org.eclipse.nebula.widgets.nattable.viewport.ViewportLayer;
import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.core.services.part.ToolboxPartService;
import org.eclipse.set.feature.validation.Messages;
import org.eclipse.set.model.tablemodel.ColumnDescriptor;
import org.eclipse.set.model.tablemodel.Table;
import org.eclipse.set.model.tablemodel.extensions.ColumnDescriptorExtensions;
import org.eclipse.set.model.validationreport.ValidationReport;
import org.eclipse.set.nattable.utils.PlanProTableThemeConfiguration;
import org.eclipse.set.utils.BasePart;
import org.eclipse.set.utils.events.JumpToSourceLineEvent;
import org.eclipse.set.utils.events.ToolboxEvents;
import org.eclipse.set.utils.table.TableDataProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * View for the validation table
 * 
 * @author Stuecker
 *
 */
public class ValidationTableView {
	private static final String SOURCE_TEXT_VIEWER_PART_ID = "org.eclipse.set.application.descriptions.SourceTextViewDescriptionService"; //$NON-NLS-1$

	private final ToolboxPartService toolboxPartService;
	private final Messages messages;
	private final IEventBroker broker;
	private final BasePart<? extends IModelSession> part;

	/**
	 * @param toolboxPartService
	 *            the part service
	 * @param part
	 *            The source part used for events
	 * @param messages
	 *            The messages
	 * @param broker
	 *            The event broker
	 */
	public ValidationTableView(final ToolboxPartService toolboxPartService,
			final BasePart<? extends IModelSession> part,
			final Messages messages, final IEventBroker broker) {
		this.toolboxPartService = toolboxPartService;
		this.part = part;
		this.messages = messages;
		this.broker = broker;
	}

	class BodyLayerStack extends AbstractLayerTransform {

		private final IDataProvider bodyDataProvider;

		private final SelectionLayer selectionLayer;
		private final ViewportLayer viewportLayer;

		public BodyLayerStack(final DataLayer bodyDataLayer) {
			this.bodyDataProvider = bodyDataLayer.getDataProvider();
			this.selectionLayer = new SelectionLayer(bodyDataLayer);
			this.viewportLayer = new ViewportLayer(this.selectionLayer);

			this.selectionLayer.addConfiguration(
					new DefaultRowSelectionLayerConfiguration());
			this.selectionLayer
					.addConfiguration(new DefaultMoveSelectionConfiguration());

			final FreezeLayer freezeLayer = new FreezeLayer(
					this.selectionLayer);
			final CompositeFreezeLayer compositeFreezeLayer = new CompositeFreezeLayer(
					freezeLayer, viewportLayer, this.selectionLayer);
			setUnderlyingLayer(compositeFreezeLayer);

		}

		public IDataProvider getBodyDataProvider() {
			return this.bodyDataProvider;
		}

		public SelectionLayer getSelectionLayer() {
			return this.selectionLayer;
		}

		public ViewportLayer getViewportLayer() {
			return viewportLayer;
		}
	}

	/**
	 * Creates the table view
	 * 
	 * @param parent
	 *            the parent composite
	 * @param validationReport
	 *            the validation report to show
	 * @return the nattable control
	 */
	public Control create(final Composite parent,
			final ValidationReport validationReport) {
		final ValidationTableTransformationService service = new ValidationTableTransformationService(
				messages);

		final Table table = service.transform(validationReport);
		final ColumnDescriptor rootColumnDescriptor = table
				.getColumndescriptors().get(0);

		final IDataProvider bodyDataProvider = new TableDataProvider(table);
		final DataLayer bodyDataLayer = new DataLayer(bodyDataProvider);

		final BodyLayerStack bodyLayerStack = new BodyLayerStack(bodyDataLayer);

		// column header stack
		final IDataProvider columnHeaderDataProvider = new DefaultColumnHeaderDataProvider(
				ColumnDescriptorExtensions
						.getColumnLabels(rootColumnDescriptor));
		final DataLayer columnHeaderDataLayer = new DataLayer(
				columnHeaderDataProvider);
		final ColumnHeaderLayer columnHeaderLayer = new ColumnHeaderLayer(
				columnHeaderDataLayer, bodyLayerStack,
				bodyLayerStack.getSelectionLayer());

		// row header stack
		final IDataProvider rowHeaderDataProvider = new DefaultRowHeaderDataProvider(
				bodyDataProvider);
		final DataLayer rowHeaderDataLayer = new DataLayer(
				rowHeaderDataProvider, 40, 20);
		final RowHeaderLayer rowHeaderLayer = new RowHeaderLayer(
				rowHeaderDataLayer, bodyLayerStack,
				bodyLayerStack.getSelectionLayer());

		// Corner Layer stack
		final DefaultCornerDataProvider cornerDataProvider = new DefaultCornerDataProvider(
				columnHeaderDataProvider, rowHeaderDataProvider);
		final DataLayer cornerDataLayer = new DataLayer(cornerDataProvider);
		final CornerLayer cornerLayer = new CornerLayer(cornerDataLayer,
				rowHeaderLayer, columnHeaderLayer);

		// gridlayer
		final GridLayer gridLayer = new GridLayer(bodyLayerStack,
				columnHeaderLayer, rowHeaderLayer, cornerLayer);
		final NatTable natTable = new NatTable(parent, SWT.NO_BACKGROUND
				| SWT.DOUBLE_BUFFERED | SWT.V_SCROLL | SWT.H_SCROLL, gridLayer);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(natTable);
		// set style
		natTable.setTheme(new PlanProTableThemeConfiguration(natTable,
				columnHeaderDataLayer, bodyDataLayer, gridLayer,
				rootColumnDescriptor, bodyLayerStack, bodyDataProvider));

		natTable.getUiBindingRegistry().registerFirstDoubleClickBinding(
				MouseEventMatcher.bodyLeftClick(0),
				(final NatTable natTable2, final MouseEvent event) -> {
					final Collection<ILayerCell> selectedCells = bodyLayerStack
							.getSelectionLayer().getSelectedCells();
					if (selectedCells.isEmpty()) {
						return;
					}

					final int row = selectedCells.iterator().next()
							.getRowPosition();
					toolboxPartService.showPart(SOURCE_TEXT_VIEWER_PART_ID);
					ToolboxEvents.send(broker,
							new JumpToSourceLineEvent(validationReport
									.getProblems().get(row).getLineNumber(),
									part));
				}

		);

		natTable.doCommand(new FreezeColumnCommand(bodyLayerStack, 0));
		bodyLayerStack.getSelectionLayer().clear();

		return natTable;
	}
}
