/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.overview;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.common.util.ECollections;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell;
import org.eclipse.set.basis.constants.ToolboxConstants;
import org.eclipse.set.basis.files.ToolboxFile;
import org.eclipse.set.core.services.Services;
import org.eclipse.set.core.services.enumtranslation.EnumTranslationService;
import org.eclipse.set.feature.table.messages.Messages;
import org.eclipse.set.model.tablemodel.RowGroup;
import org.eclipse.set.model.tablemodel.Table;
import org.eclipse.set.model.tablemodel.extensions.TableRowExtensions;
import org.eclipse.set.utils.BasePart;
import org.eclipse.set.utils.events.JumpToSiteplanEvent;
import org.eclipse.set.utils.events.JumpToSourceLineEvent;
import org.eclipse.set.utils.events.JumpToTableEvent;
import org.eclipse.set.utils.table.TableError;
import org.eclipse.set.utils.table.menu.TableMenuService;
import org.eclipse.set.utils.table.sorting.AbstractSortByColumnTables;
import org.eclipse.set.utils.xml.XMLNodeFinder;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * View for table errors
 * 
 * @author Peters
 *
 */
public class TableErrorTableView extends AbstractSortByColumnTables {

	private final Messages messages;
	private NatTable natTable;

	private Collection<TableError> tableErrors = new ArrayList<>();
	private final EnumTranslationService enumTranslationService;
	private final TableMenuService tableMenuService;
	private final BasePart part;
	private final XMLNodeFinder xmlNodeFinder;

	/**
	 * @param messages
	 *            The messages
	 * @param part
	 *            the part
	 * @param enumTranslationService
	 *            the enum translation service
	 * @param tableMenuService
	 *            teh table menu service
	 */
	public TableErrorTableView(final Messages messages, final BasePart part,
			final EnumTranslationService enumTranslationService,
			final TableMenuService tableMenuService) {
		this.messages = messages;
		this.enumTranslationService = enumTranslationService;
		this.tableMenuService = tableMenuService;
		this.part = part;
		final ToolboxFile toolboxFile = part.getModelSession().getToolboxFile();
		this.xmlNodeFinder = new XMLNodeFinder();
		xmlNodeFinder.read(toolboxFile, toolboxFile.getModelPath());
	}

	/**
	 * Creates the table view
	 * 
	 * @param parent
	 *            the parent composite
	 * @return the nattable control
	 */
	public Control create(final Composite parent) {
		final Table table = getTable();
		this.createTableBodyData(table, rowIndex -> rowIndex);
		addMenuItems();
		natTable = createTable(parent, table);
		return natTable;
	}

	/**
	 * Updates the table view
	 * 
	 * @param errors
	 *            the new table errors
	 */
	public void updateView(final Collection<TableError> errors) {
		tableErrors = errors;
		if (natTable != null) {
			bodyDataProvider.refresh(getTable());
			natTable.refresh();
		}
	}

	private Table getTable() {
		final TableErrorTransformationService service = new TableErrorTransformationService(
				messages, enumTranslationService);
		final Table table = service.transform(tableErrors);

		ECollections.sort(table.getTablecontent().getRowgroups(),
				service.getRowGroupComparator());
		return table;
	}

	private void addMenuItems() {
		tableMenuService.addMenuItem(tableMenuService.createShowInTableItem(
				createJumpToTableEvent(), getSelectionLayer(), position -> {
					final String shortCut = getRowReferenceTable(position);
					return shortCut != null && !shortCut.isEmpty();
				}));
		tableMenuService.addMenuItem(tableMenuService.createShowInTextViewItem(
				createJumpToTextViewEvent(part), getSelectionLayer(),
				rowPosition -> getRowReferenceObjectGuid(rowPosition) != null));
		tableMenuService.addMenuItem(tableMenuService.createShowInSitePlanItem(
				creataJumpToSiteplanEvent(), getSelectionLayer(),
				rowPosition -> {
					final String guid = getRowReferenceObjectGuid(rowPosition);
					return guid != null && Services.getSiteplanService()
							.isSiteplanElement(guid);
				}));
	}

	private JumpToTableEvent createJumpToTableEvent() {
		return new JumpToTableEvent() {
			@Override
			public String getSearchKey() {
				return getSelectedObjectGuid();
			}

			@Override
			public String getTableShortcut() {
				final int selectionRowPosition = getSelectionRowPosition();
				if (selectionRowPosition == -1) {
					return null;
				}
				return getRowReferenceTable(selectionRowPosition);
			}

			@Override
			public String getTableCategory() {
				if (part.getToolboxPart().getElementId().startsWith(
						ToolboxConstants.ESTW_TABLE_PART_ID_PREFIX)) {
					return ToolboxConstants.ESTW_CATEGORY;
				}
				return ToolboxConstants.ETCS_CATEGORY;
			}
		};
	}

	@Override
	protected JumpToSourceLineEvent createJumpToTextViewEvent(
			final BasePart basePart) {
		return new JumpToSourceLineEvent(basePart) {
			@Override
			public String getObjectGuid() {
				return getSelectedObjectGuid();
			}
		};
	}

	@Override
	protected JumpToSiteplanEvent creataJumpToSiteplanEvent() {
		return new JumpToSiteplanEvent() {
			@Override
			public String getGuid() {
				return getSelectedObjectGuid();
			}
		};
	}

	@Override
	protected TableMenuService getTableMenuService() {
		return tableMenuService;
	}

	@Override
	protected XMLNodeFinder getXMLNodeFinder() {
		return xmlNodeFinder;
	}

	private String getSelectedObjectGuid() {
		final int selectionRowPosition = getSelectionRowPosition();
		if (selectionRowPosition == -1) {
			return null;
		}
		return getRowReferenceObjectGuid(selectionRowPosition);
	}

	private int getSelectionRowPosition() {
		final Collection<ILayerCell> selectedCells = bodyLayerStack
				.getSelectionLayer().getSelectedCells();
		if (selectedCells.isEmpty()) {
			return -1;
		}

		return selectedCells.iterator().next().getRowPosition();
	}

	private String getRowReferenceTable(final int rowPosition) {
		try {
			return getSelectedError(rowPosition).getSource();
		} catch (final NullPointerException e) {
			return null;
		}
	}

	private String getRowReferenceObjectGuid(final int rowPosition) {
		try {
			return getSelectedError(rowPosition).getGuid();
		} catch (final NullPointerException e) {
			return null;
		}
	}

	private TableError getSelectedError(final int rowPosition) {
		final RowGroup group = TableRowExtensions
				.getGroup(bodyDataProvider.getRow(rowPosition).getRow());
		if (group.getLeadingObject() instanceof final TableError error) {
			return error;
		}
		return null;
	}

	@Override
	protected String getCSVHeaderPattern() {
		return null;
	}

	@Override
	public void exportCsv() {
		// do nothing
	}
}
