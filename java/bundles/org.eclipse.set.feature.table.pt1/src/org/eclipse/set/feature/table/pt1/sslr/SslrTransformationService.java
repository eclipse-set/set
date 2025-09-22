/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.pt1.sslr;

import static org.eclipse.nebula.widgets.nattable.sort.SortDirectionEnum.ASC;
import static org.eclipse.set.utils.table.sorting.ComparatorBuilder.CellComparatorType.MIXED_STRING;

import java.util.Comparator;
import java.util.List;

import org.eclipse.set.core.services.enumtranslation.EnumTranslationService;
import org.eclipse.set.feature.table.PlanPro2TableTransformationService;
import org.eclipse.set.feature.table.pt1.AbstractPlanPro2TableModelTransformator;
import org.eclipse.set.feature.table.pt1.AbstractPlanPro2TableTransformationService;
import org.eclipse.set.feature.table.pt1.messages.Messages;
import org.eclipse.set.model.tablemodel.RowGroup;
import org.eclipse.set.ppmodel.extensions.utils.TableNameInfo;
import org.eclipse.set.utils.table.sorting.TableRowGroupComparator;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.event.EventAdmin;

/**
 * Service for creating the sslr table model.
 * 
 * @author Schneider/Schaefer
 * 
 * @usage production
 */
@Component(service = {
		PlanPro2TableTransformationService.class }, immediate = true, property = {
				"table.category=estw", "table.shortcut=sslr" })
public class SslrTransformationService
		extends AbstractPlanPro2TableTransformationService {
	@Reference
	private Messages messages;
	@Reference
	private EnumTranslationService enumTranslationService;
	@Reference
	private EventAdmin eventAdmin;

	@Override
	public AbstractPlanPro2TableModelTransformator createTransformator() {
		return new SslrTransformator(cols, enumTranslationService, eventAdmin);
	}

	@Override
	public Comparator<RowGroup> getRowGroupComparator() {
		return TableRowGroupComparator.builder()
				.sort("B", MIXED_STRING, ASC) //$NON-NLS-1$
				.sort("C", MIXED_STRING, ASC) //$NON-NLS-1$
				.sort("D", MIXED_STRING, ASC) //$NON-NLS-1$
				.build();
	}

	@Override
	public TableNameInfo getTableNameInfo() {
		return new TableNameInfo(messages.ToolboxTableNameSslrLong,
				messages.ToolboxTableNameSslrPlanningNumber,
				messages.ToolboxTableNameSslrShort);
	}

	@Override
	protected String getTableHeading() {
		return messages.Sslr_Heading;
	}

	@Override
	protected String getShortcut() {
		return messages.ToolboxTableNameSslrShort.toLowerCase();
	}

	@Override
	protected List<String> getTopologicalColumnPosition() {
		return List.of(SslrColumns.Fahrweg_Entscheidungsweiche);
	}
}
