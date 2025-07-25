/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.pt1.ssks;

import static org.eclipse.nebula.widgets.nattable.sort.SortDirectionEnum.ASC;
import static org.eclipse.set.utils.table.sorting.ComparatorBuilder.CellComparatorType.EMPTY_LAST;
import static org.eclipse.set.utils.table.sorting.ComparatorBuilder.CellComparatorType.LEXICOGRAPHICAL;

import java.util.Comparator;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.set.basis.constants.Events;
import org.eclipse.set.core.services.enumtranslation.EnumTranslationService;
import org.eclipse.set.core.services.graph.BankService;
import org.eclipse.set.feature.table.PlanPro2TableTransformationService;
import org.eclipse.set.feature.table.pt1.AbstractPlanPro2TableModelTransformator;
import org.eclipse.set.feature.table.pt1.AbstractPlanPro2TableTransformationService;
import org.eclipse.set.feature.table.pt1.messages.Messages;
import org.eclipse.set.model.tablemodel.RowGroup;
import org.eclipse.set.ppmodel.extensions.utils.TableNameInfo;
import org.eclipse.set.utils.table.sorting.TableRowGroupComparator;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;

/**
 * Service for creating the ssks table model. org.eclipse.set.feature.table
 * 
 * @author rumpf
 * 
 * @usage production
 */
@Component(service = { PlanPro2TableTransformationService.class,
		EventHandler.class }, immediate = true, property = {
				"table.category=estw", "table.shortcut=ssks",
				EventConstants.EVENT_TOPIC + "=" + Events.CLOSE_PART,
				EventConstants.EVENT_TOPIC + "=" + Events.CLOSE_SESSION, })
public final class SsksTransformationService extends
		AbstractPlanPro2TableTransformationService implements EventHandler {

	@Reference
	private Messages messages;
	@Reference
	private EnumTranslationService enumTranslationService;
	@Reference
	private BankService bankingService;

	@Reference
	private EventAdmin eventAdmin;

	/**
	 * constructor.
	 */
	public SsksTransformationService() {
		super();
	}

	@Override
	public AbstractPlanPro2TableModelTransformator createTransformator() {
		return new SsksTransformator(cols, enumTranslationService,
				bankingService, eventAdmin, messages.ToolboxTableNameSsksShort);
	}

	@Override
	public TableNameInfo getTableNameInfo() {
		return new TableNameInfo(messages.ToolboxTableNameSsksLong,
				messages.ToolboxTableNameSsksPlanningNumber,
				messages.ToolboxTableNameSsksShort);
	}

	@Override
	protected String getTableHeading() {
		return messages.SsksTableView_Heading;
	}

	@Override
	public void handleEvent(final Event event) {
		final String property = event.getTopic().equals(Events.CLOSE_PART)
				? (String) event.getProperty(IEventBroker.DATA)
				: ""; //$NON-NLS-1$
		if (messages.ToolboxTableNameSsksShort.toLowerCase().equals(property)
				|| event.getTopic().equals(Events.CLOSE_SESSION)) {
			Thread.getAllStackTraces().keySet().forEach(thread -> {
				if (thread.getName()
						.toLowerCase()
						.startsWith(messages.ToolboxTableNameSsksShort
								.toLowerCase())) {
					thread.interrupt();
				}
			});
		}
	}

	@SuppressWarnings("nls")
	@Override
	public Comparator<RowGroup> getRowGroupComparator() {
		return TableRowGroupComparator.builder()
				.sort("B", EMPTY_LAST, ASC)
				.sort("D", EMPTY_LAST, ASC)
				.sort("A", LEXICOGRAPHICAL, ASC)
				.build();
	}
}
