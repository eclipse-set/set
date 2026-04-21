/**
 * Copyright (c) 2024 DB InfraGO AG and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 */
package org.eclipse.set.feature.table.pt1.sskx;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.nebula.widgets.nattable.sort.SortDirectionEnum;
import org.eclipse.set.basis.constants.Events;
import org.eclipse.set.core.services.enumtranslation.EnumTranslationService;
import org.eclipse.set.core.services.graph.BankService;
import org.eclipse.set.feature.table.PlanPro2TableTransformationService;
import org.eclipse.set.feature.table.pt1.AbstractPlanPro2TableTransformationService;
import org.eclipse.set.feature.table.pt1.messages.Messages;
import org.eclipse.set.model.planpro.Signale.Signal;
import org.eclipse.set.model.tablemodel.RowGroup;
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup;
import org.eclipse.set.ppmodel.extensions.utils.TableNameInfo;
import org.eclipse.set.utils.table.TableModelTransformator;
import org.eclipse.set.utils.table.sorting.ComparatorBuilder.CellComparatorType;
import org.eclipse.set.utils.table.sorting.TableRowGroupComparator;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;

/**
 * Service for creating the Sskz table model. org.eclipse.set.feature.table
 * 
 * @author Truong
 */
@Component(service = {
		PlanPro2TableTransformationService.class }, immediate = true, property = {
				"table.category=supplement-estw", "table.shortcut=sskx",
				EventConstants.EVENT_TOPIC + "=" + Events.CLOSE_PART,
				EventConstants.EVENT_TOPIC + "=" + Events.CLOSE_SESSION })
public class SskxTransformationService extends
		AbstractPlanPro2TableTransformationService implements EventHandler {

	@Reference
	private Messages messages;

	@Reference
	private EnumTranslationService enumTranslationService;

	@Reference
	private BankService bankService;
	@Reference
	private EventAdmin eventAdmin;

	/**
	 * constructor.
	 */
	public SskxTransformationService() {
		super();
	}

	@Override
	protected String getTableHeading() {
		return messages.SskxTableView_Heading;
	}

	@Override
	public TableNameInfo getTableNameInfo() {
		return new TableNameInfo(messages.ToolboxTableNameSskxLong,
				messages.ToolboxTableNameSskxPlanningNumber,
				messages.ToolboxTableNameSskxShort,
				messages.ToolboxTableNameSskxRil);
	}

	@Override
	public TableModelTransformator<MultiContainer_AttributeGroup> createTransformator() {
		return new SskxTransformator(cols, enumTranslationService, bankService,
				eventAdmin, messages.ToolboxTableNameSskxShort);
	}

	@Override
	public void handleEvent(final Event event) {
		final String property = (String) event.getProperty(IEventBroker.DATA);
		if (messages.ToolboxTableNameSskxShort.toLowerCase().equals(property)
				|| event.getTopic().equals(Events.CLOSE_SESSION)) {
			Thread.getAllStackTraces().keySet().forEach(thread -> {
				if (thread.getName()
						.toLowerCase()
						.startsWith(messages.ToolboxTableNameSskxShort
								.toLowerCase())) {
					thread.interrupt();
				}
			});
		}
	}

	@Override
	protected String getShortcut() {
		return messages.ToolboxTableNameSskxShort.toLowerCase();
	}

	@Override
	protected List<String> getTopologicalColumnPosition() {
		return List.of(SskxColumns.Gleis, SskxColumns.Lichtraumprofil);
	}

	@Override
	protected Map<Class<?>, String> getFootnotesColumnReferences() {
		return Collections.emptyMap();
	}

	@Override
	public Comparator<RowGroup> getRowGroupComparator() {
		return TableRowGroupComparator.builder().sortByRouteAndKm(obj -> {
			if (obj instanceof final Signal signal) {
				return signal;
			}
			return null;
		})
				.sort(SskxColumns.Bezeichnung_Signal,
						CellComparatorType.LEXICOGRAPHICAL,
						SortDirectionEnum.ASC)
				.build();
	}
}
