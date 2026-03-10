/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.pt1.ssko;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipse.set.core.services.enumtranslation.EnumTranslationService;
import org.eclipse.set.feature.table.PlanPro2TableTransformationService;
import org.eclipse.set.feature.table.pt1.AbstractPlanPro2TableModelTransformator;
import org.eclipse.set.feature.table.pt1.AbstractPlanPro2TableTransformationService;
import org.eclipse.set.feature.table.pt1.messages.Messages;
import org.eclipse.set.model.planpro.Basisobjekte.Strecke_Km_TypeClass;
import org.eclipse.set.model.planpro.Verweise.ID_Strecke_TypeClass;
import org.eclipse.set.ppmodel.extensions.utils.TableNameInfo;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.event.EventAdmin;

/**
 * Service for creating the ssko table model. org.eclipse.set.feature.table
 * 
 * @author rumpf
 * 
 * @usage production
 */
@Component(service = {
		PlanPro2TableTransformationService.class }, immediate = true, property = {
				"table.category=estw", "table.shortcut=ssko" })
public final class SskoTransformationService
		extends AbstractPlanPro2TableTransformationService {

	@Reference
	private Messages messages;
	@Reference
	private EnumTranslationService enumTranslationService;
	@Reference
	private EventAdmin eventAdmin;

	/**
	 * constructor.
	 */
	public SskoTransformationService() {
		super();
	}

	@Override
	public AbstractPlanPro2TableModelTransformator createTransformator() {
		return new SskoTransformator(cols, enumTranslationService, eventAdmin);
	}

	@Override
	public TableNameInfo getTableNameInfo() {
		return new TableNameInfo(messages.ToolboxTableNameSskoLong,
				messages.ToolboxTableNameSskoPlanningNumber,
				messages.ToolboxTableNameSskoShort);
	}

	@Override
	protected String getTableHeading() {
		return messages.SskoTableView_Heading;
	}

	@Override
	protected String getShortcut() {
		return messages.ToolboxTableNameSskoShort.toLowerCase();
	}

	@Override
	protected List<String> getTopologicalColumnPosition() {
		return Collections.emptyList();
	}

	@Override
	protected Map<Class<?>, String> getFootnotesColumnReferences() {
		return Map.of(ID_Strecke_TypeClass.class,
				SskoColumns.Sk_Ssp_Unterbringung_Strecke,
				Strecke_Km_TypeClass.class,
				SskoColumns.Sk_Ssp_Unterbringung_km);
	}
}
