/**
 * Copyright (c) 2023 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.feature.table.pt1.ssbb;

import org.eclipse.set.core.services.enumtranslation.EnumTranslationService;
import org.eclipse.set.feature.table.PlanPro2TableTransformationService;
import org.eclipse.set.feature.table.pt1.AbstractPlanPro2TableModelTransformator;
import org.eclipse.set.feature.table.pt1.AbstractPlanPro2TableTransformationService;
import org.eclipse.set.feature.table.pt1.messages.Messages;
import org.eclipse.set.ppmodel.extensions.utils.TableNameInfo;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Service for creating the ssbb table model
 * 
 * @author truong
 */
@Component(service = {
		PlanPro2TableTransformationService.class }, immediate = true, property = {
				"table.shortcut=ssbb" })
public class SsbbTransformationService
		extends AbstractPlanPro2TableTransformationService {

	@Reference
	private Messages messages;
	@Reference
	private EnumTranslationService enumTranslationService;

	/**
	 * constructor
	 */
	public SsbbTransformationService() {
		super();
	}

	@Override
	protected String getTableHeading() {
		return messages.SsbbTableView_Heading;
	}

	@Override
	public TableNameInfo getTableNameInfo() {
		return new TableNameInfo(messages.ToolboxTableNameSsbbLong,
				messages.ToolboxTableNameSsbbPlanningNumber,
				messages.ToolboxTableNameSsbbShort);
	}

	@Override
	public AbstractPlanPro2TableModelTransformator createTransformator() {
		return new SsbbTransformator(cols, enumTranslationService);
	}

}
