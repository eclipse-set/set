/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.pt1.sslb;

import static org.eclipse.nebula.widgets.nattable.sort.SortDirectionEnum.ASC;
import static org.eclipse.set.utils.table.sorting.ComparatorBuilder.CellComparatorType.LEXICOGRAPHICAL;

import java.util.Comparator;

import org.eclipse.set.core.services.enumtranslation.EnumTranslationService;
import org.eclipse.set.core.services.graph.TopologicalGraphService;
import org.eclipse.set.feature.table.PlanPro2TableTransformationService;
import org.eclipse.set.feature.table.pt1.AbstractPlanPro2TableModelTransformator;
import org.eclipse.set.feature.table.pt1.AbstractPlanPro2TableTransformationService;
import org.eclipse.set.feature.table.pt1.messages.Messages;
import org.eclipse.set.model.tablemodel.RowGroup;
import org.eclipse.set.ppmodel.extensions.utils.TableNameInfo;
import org.eclipse.set.utils.table.sorting.TableRowGroupComparator;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Service for creating the sslb table model. org.eclipse.set.feature.table
 * 
 * @author rumpf
 * 
 * @usage production
 */
@Component(service = {
		PlanPro2TableTransformationService.class }, immediate = true, property = {
				"table.category=estw", "table.shortcut=sslb" })
public final class SslbTransformationService
		extends AbstractPlanPro2TableTransformationService {

	@Reference
	private Messages messages;
	@Reference
	private EnumTranslationService enumTranslationService;
	@Reference
	private TopologicalGraphService topGraphService;

	/**
	 * constructor.
	 */
	public SslbTransformationService() {
		super();
	}

	@Override
	public AbstractPlanPro2TableModelTransformator createTransformator() {
		return new SslbTransformator(cols, enumTranslationService,
				topGraphService);
	}

	@Override
	public Comparator<RowGroup> getRowGroupComparator() {
		return TableRowGroupComparator.builder().sort("F", LEXICOGRAPHICAL, ASC) //$NON-NLS-1$
				.sort("I", LEXICOGRAPHICAL, ASC).build(); //$NON-NLS-1$
	}

	@Override
	public TableNameInfo getTableNameInfo() {
		return new TableNameInfo(messages.ToolboxTableNameSslbLong,
				messages.ToolboxTableNameSslbPlanningNumber,
				messages.ToolboxTableNameSslbShort);
	}

	@Override
	protected String getTableHeading() {
		return messages.SslbTableView_Heading;
	}

}