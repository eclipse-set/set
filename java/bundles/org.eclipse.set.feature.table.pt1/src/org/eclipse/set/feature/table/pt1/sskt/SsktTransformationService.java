/**
 * Copyright (c) 2019 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.pt1.sskt;

import static org.eclipse.nebula.widgets.nattable.sort.SortDirectionEnum.ASC;
import static org.eclipse.set.utils.table.sorting.ComparatorBuilder.CellComparatorType.MIXED_STRING;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.set.core.services.enumtranslation.EnumTranslationService;
import org.eclipse.set.feature.table.PlanPro2TableTransformationService;
import org.eclipse.set.feature.table.pt1.AbstractPlanPro2TableModelTransformator;
import org.eclipse.set.feature.table.pt1.AbstractPlanPro2TableTransformationService;
import org.eclipse.set.feature.table.pt1.messages.Messages;
import org.eclipse.set.model.planpro.Ansteuerung_Element.Technik_Standort;
import org.eclipse.set.model.planpro.Bedienung.Bedien_Standort;
import org.eclipse.set.model.tablemodel.ColumnDescriptor;
import org.eclipse.set.model.tablemodel.RowGroup;
import org.eclipse.set.model.tablemodel.RowMergeMode;
import org.eclipse.set.ppmodel.extensions.utils.TableNameInfo;
import org.eclipse.set.utils.table.ColumnDescriptorModelBuilder;
import org.eclipse.set.utils.table.sorting.TableRowGroupComparator;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.event.EventAdmin;

/**
 * Service for creating the sskt table model.
 * 
 * @author Schaefer
 * 
 * @usage production
 */
@Component(service = {
		PlanPro2TableTransformationService.class }, immediate = true, property = {
				"table.category=estw", "table.shortcut=sskt" })
public class SsktTransformationService
		extends AbstractPlanPro2TableTransformationService {

	@Reference
	private Messages messages;
	@Reference
	private EnumTranslationService enumTranslationService;
	@Reference
	private EventAdmin eventAdmin;

	@Override
	public AbstractPlanPro2TableModelTransformator createTransformator() {
		return new SsktTransformator(cols, enumTranslationService, eventAdmin);
	}

	@Override
	public Comparator<RowGroup> getRowGroupComparator() {
		return TableRowGroupComparator.builder()
				.sort(Technik_Standort.class, Bedien_Standort.class)
				.sort("A", MIXED_STRING, ASC) //$NON-NLS-1$
				.build();
	}

	@Override
	public TableNameInfo getTableNameInfo() {
		return new TableNameInfo(messages.ToolboxTableNameSsktLong,
				messages.ToolboxTableNameSsktPlanningNumber,
				messages.ToolboxTableNameSsktShort);
	}

	@Override
	protected String getTableHeading() {
		return messages.SsktTableView_Heading;
	}

	@Override
	public ColumnDescriptor fillHeaderDescriptions(
			final ColumnDescriptorModelBuilder builder) {
		final ColumnDescriptor cd = super.fillHeaderDescriptions(builder);
		// Merge all columns except M to O
		cd.setMergeCommonValues(RowMergeMode.ENABLED);
		List.of(SsktColumns.IP_Teilsystem_Art, //
				SsktColumns.Teilsystem_TS_Blau, //
				SsktColumns.Teilsystem_TS_Grau)
				.forEach(it -> cols.forEach(col -> {
					if (it.equals(col.getColumnPosition())) {
						col.setMergeCommonValues(RowMergeMode.DISABLED);
					}
				}));

		return cd;
	}

	@Override
	protected String getShortcut() {
		return messages.ToolboxTableNameSsktShort.toLowerCase();
	}

	@Override
	protected List<String> getTopologicalColumnPosition() {
		return Collections.emptyList();
	}
}
