/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.pt1.ssko;

import org.eclipse.set.core.services.enumtranslation.EnumTranslationService;
import org.eclipse.set.feature.table.pt1.AbstractPlanPro2TableModelTransformator;
import org.eclipse.set.feature.table.pt1.AbstractPlanPro2TableTransformationService;
import org.eclipse.set.feature.table.pt1.messages.Messages;
import org.eclipse.set.ppmodel.extensions.utils.TableNameInfo;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Service for creating the ssko table model. org.eclipse.set.feature.table
 * 
 * @author rumpf
 * 
 * @usage production
 */
@Component(service = {
		AbstractPlanPro2TableTransformationService.class }, immediate = true, property = {
				"table.shortcut=ssko" })
public final class SskoTransformationService
		extends AbstractPlanPro2TableTransformationService<SskoColumns> {

	@Reference
	private Messages messages;
	@Reference
	private EnumTranslationService enumTranslationService;

	/**
	 * constructor.
	 */
	public SskoTransformationService() {
		super();
	}

	@Override
	public void buildColumns() {
		this.columns = new SskoColumns(this.messages);
	}

	@Override
	public AbstractPlanPro2TableModelTransformator createTransformator() {
		return new SskoTransformator(columns, enumTranslationService);
	}

	@Override
	public TableNameInfo getTableNameInfo() {
		return new TableNameInfo(messages.ToolboxTableNameSskoLong,
				messages.ToolboxTableNameSskoPlanningNumber,
				messages.ToolboxTableNameSskoShort);
	}
}
