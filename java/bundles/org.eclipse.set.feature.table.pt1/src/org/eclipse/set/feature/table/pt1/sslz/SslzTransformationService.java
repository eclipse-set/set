/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.pt1.sslz;

import org.eclipse.set.core.services.enumtranslation.EnumTranslationService;
import org.eclipse.set.feature.table.PlanPro2TableTransformationService;
import org.eclipse.set.feature.table.pt1.AbstractPlanPro2TableModelTransformator;
import org.eclipse.set.feature.table.pt1.AbstractPlanPro2TableTransformationService;
import org.eclipse.set.feature.table.pt1.messages.Messages;
import org.eclipse.set.ppmodel.extensions.utils.TableNameInfo;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Service for creating the ssks table model. org.eclipse.set.feature.table
 * 
 * @author rumpf
 * 
 * @usage production
 */
@Component(service = {
		PlanPro2TableTransformationService.class }, immediate = true, property = {
				"table.category=estw", "table.shortcut=sslz" })
public final class SslzTransformationService
		extends AbstractPlanPro2TableTransformationService {

	@Reference
	private Messages messages;
	@Reference
	private EnumTranslationService enumTranslationService;

	/**
	 * constructor.
	 */
	public SslzTransformationService() {
		super();
	}

	@Override
	public AbstractPlanPro2TableModelTransformator createTransformator() {
		return new SslzTransformator(cols, enumTranslationService);
	}

	@Override
	public TableNameInfo getTableNameInfo() {
		return new TableNameInfo(messages.ToolboxTableNameSslzLong,
				messages.ToolboxTableNameSslzPlanningNumber,
				messages.ToolboxTableNameSslzShort);
	}

	@Override
	protected String getTableHeading() {
		return messages.SslzTableView_Heading;
	}
}
