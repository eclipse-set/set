/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.pt1.ssks;

import org.eclipse.set.core.services.enumtranslation.EnumTranslationService;
import org.eclipse.set.core.services.graph.BankService;
import org.eclipse.set.core.services.graph.TopologicalGraphService;
import org.eclipse.set.feature.table.PlanPro2TableTransformationService;
import org.eclipse.set.feature.table.pt1.AbstractPlanPro2TableModelTransformator;
import org.eclipse.set.feature.table.pt1.AbstractPlanPro2TableTransformationService;
import org.eclipse.set.feature.table.pt1.messages.Messages;
import org.eclipse.set.ppmodel.extensions.utils.TableNameInfo;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;
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
				"table.shortcut=ssks" })
public final class SsksTransformationService extends
		AbstractPlanPro2TableTransformationService implements EventHandler {

	@Reference
	private Messages messages;
	@Reference
	private EnumTranslationService enumTranslationService;
	@Reference
	private TopologicalGraphService topGraphService;
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
				topGraphService, bankingService, eventAdmin,
				messages.ToolboxTableNameSsksShort);
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
		// do nothing

	}
}
