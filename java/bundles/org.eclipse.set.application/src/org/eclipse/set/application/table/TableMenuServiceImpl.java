/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.application.table;

import java.util.LinkedList;
import java.util.List;
import java.util.function.IntPredicate;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.selection.SelectionLayer;
import org.eclipse.set.application.Messages;
import org.eclipse.set.basis.constants.Events;
import org.eclipse.set.basis.constants.ToolboxConstants;
import org.eclipse.set.core.services.Services;
import org.eclipse.set.core.services.part.ToolboxPartService;
import org.eclipse.set.utils.events.JumpToSiteplanEvent;
import org.eclipse.set.utils.events.JumpToSourceLineEvent;
import org.eclipse.set.utils.events.JumpToTableEvent;
import org.eclipse.set.utils.events.ToolboxEvents;
import org.eclipse.set.utils.table.menu.TableBodyMenuConfiguration;
import org.eclipse.set.utils.table.menu.TableBodyMenuConfiguration.TableBodyMenuItem;
import org.eclipse.set.utils.table.menu.TableMenuService;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

import jakarta.inject.Inject;

/**
 * Implementation of {@link TableMenuService}
 * 
 * @author Truong
 *
 */
public class TableMenuServiceImpl implements TableMenuService {
	private static final String SOURCE_TEXT_VIEWER_PART_ID = "org.eclipse.set.application.descriptions.SourceWebTextViewDescriptionService"; //$NON-NLS-1$
	private static final String SITE_PLAN_PART_ID = "org.eclipse.set.feature.siteplan.descriptions.WebSiteplanDescriptionService"; //$NON-NLS-1$

	@Inject
	ToolboxPartService toolboxPartService;

	@Inject
	@Translation
	Messages messages;

	@Inject
	protected IEventBroker broker;
	protected List<TableBodyMenuItem> menuItems = new LinkedList<>();

	@Override
	public TableBodyMenuConfiguration createMenuConfiguration(
			final NatTable natTable, final SelectionLayer selectionLayer) {
		return new TableBodyMenuConfiguration(natTable, selectionLayer,
				menuItems);
	}

	@Override
	public void addMenuItem(final TableBodyMenuItem menuItem) {
		this.menuItems.add(menuItem);
	}

	@Override
	public List<TableBodyMenuItem> getMenuItems() {
		return this.menuItems;
	}

	@Override
	public TableBodyMenuItem createShowInTextViewItem(
			final JumpToSourceLineEvent toolboxEvent,
			final SelectionLayer selectionlayer,
			final IntPredicate enablePredicate) {

		return new TableBodyMenuItem(messages.TableMenuService_TextView,
				selectionlayer, new SelectionAdapter() {
					@Override
					public void widgetSelected(final SelectionEvent e) {

						if (selectionlayer.getSelectedCells().isEmpty()) {
							return;
						}
						toolboxPartService.showPart(SOURCE_TEXT_VIEWER_PART_ID);
						ToolboxEvents.send(broker, toolboxEvent);
					}
				}, enablePredicate);
	}

	@Override
	public TableBodyMenuItem createShowInSitePlanItem(
			final JumpToSiteplanEvent jumpEvent,
			final SelectionLayer selectionlayer,
			final IntPredicate enablePredicate) {
		return new TableBodyMenuItem(messages.TableMenuService_Siteplan,
				selectionlayer, new SelectionAdapter() {
					@Override
					public void widgetSelected(final SelectionEvent e) {

						if (selectionlayer.getSelectedCells().isEmpty()) {
							return;
						}
						toolboxPartService.showPart(SITE_PLAN_PART_ID);
						// When opening the site plan for the first time,
						// it takes a moment to transform PlanPro objects into
						// SitePlan objects.
						// Therefore, wait until the site plan has finished
						// loading, then send jump event.
						if (Services.getSiteplanService()
								.isNotFirstTimeOpenSiteplan()) {
							ToolboxEvents.send(broker, jumpEvent);
						} else {
							broker.subscribe(Events.SITEPLAN_OPENING,
									handleSiteplanLoadingEvent(jumpEvent));
						}
					}
				}, enablePredicate);
	}

	@Override
	public TableBodyMenuItem createShowInTableItem(
			final JumpToTableEvent jumpEvent,
			final SelectionLayer selectionLayer,
			final IntPredicate enablePredicate) {
		return new TableBodyMenuItem(messages.TableMenuService_Table,
				selectionLayer, new SelectionAdapter() {
					@Override
					public void widgetSelected(final SelectionEvent e) {
						final String tableShortcut = jumpEvent
								.getTableShortcut();
						if (selectionLayer.getSelectedCells().isEmpty()
								|| tableShortcut == null
								|| tableShortcut.isEmpty()) {
							return;
						}
						toolboxPartService
								.showPart(ToolboxConstants.TABLE_PART_ID_PREFIX
										+ tableShortcut.toLowerCase());
						ToolboxEvents.send(broker, jumpEvent);
					}
				}, enablePredicate);
	}

	private EventHandler handleSiteplanLoadingEvent(
			final JumpToSiteplanEvent jumpEvent) {
		return new EventHandler() {

			@Override
			public void handleEvent(final Event event) {
				if (event.getTopic().equals(Events.SITEPLAN_OPENING)
						&& event.getProperty(
								IEventBroker.DATA) instanceof final Boolean isLoading
						&& !isLoading.booleanValue()) {
					ToolboxEvents.send(broker, jumpEvent);
					broker.unsubscribe(this);
				}
			}
		};

	}
}
