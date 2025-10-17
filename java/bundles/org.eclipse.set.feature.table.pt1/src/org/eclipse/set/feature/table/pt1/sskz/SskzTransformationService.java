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
package org.eclipse.set.feature.table.pt1.sskz;

import java.util.List;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.set.basis.constants.Events;
import org.eclipse.set.core.services.enumtranslation.EnumTranslationService;
import org.eclipse.set.core.services.graph.BankService;
import org.eclipse.set.feature.table.PlanPro2TableTransformationService;
import org.eclipse.set.feature.table.pt1.AbstractPlanPro2TableTransformationService;
import org.eclipse.set.feature.table.pt1.messages.Messages;
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup;
import org.eclipse.set.ppmodel.extensions.utils.TableNameInfo;
import org.eclipse.set.utils.table.TableModelTransformator;
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
				"table.category=supplement-estw", "table.shortcut=sskz",
				EventConstants.EVENT_TOPIC + "=" + Events.CLOSE_PART,
				EventConstants.EVENT_TOPIC + "=" + Events.CLOSE_SESSION })
public class SskzTransformationService extends
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
	public SskzTransformationService() {
		super();
	}

	@Override
	protected String getTableHeading() {
		return messages.SskzTableView_Heading;
	}

	@Override
	public TableNameInfo getTableNameInfo() {
		return new TableNameInfo(messages.ToolboxTableNameSskzLong,
				messages.ToolboxTableNameSskzPlanningNumber,
				messages.ToolboxTableNameSskzShort);
	}

	@Override
	public TableModelTransformator<MultiContainer_AttributeGroup> createTransformator() {
		return new SskzTransformator(cols, bankService, enumTranslationService,
				eventAdmin, messages.ToolboxTableNameSskzShort);
	}

	@Override
	public void handleEvent(final Event event) {
		final String property = (String) event.getProperty(IEventBroker.DATA);
		if (messages.ToolboxTableNameSskzShort.toLowerCase().equals(property)
				|| event.getTopic().equals(Events.CLOSE_SESSION)) {
			Thread.getAllStackTraces().keySet().forEach(thread -> {
				if (thread.getName()
						.toLowerCase()
						.startsWith(messages.ToolboxTableNameSskzShort
								.toLowerCase())) {
					thread.interrupt();
				}
			});
		}
	}

	@Override
	protected String getShortcut() {
		return messages.ToolboxTableNameSskzShort.toLowerCase();
	}

	@Override
	protected List<String> getTopologicalColumnPosition() {
		return List.of(SskzColumns.Ueberhoehung);
	}
}
