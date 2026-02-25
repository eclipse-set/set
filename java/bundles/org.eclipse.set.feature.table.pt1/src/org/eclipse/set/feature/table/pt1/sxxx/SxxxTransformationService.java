/**
 * Copyright (c) 2026 DB InfraGO AG and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 */
package org.eclipse.set.feature.table.pt1.sxxx;

import static org.eclipse.nebula.widgets.nattable.sort.SortDirectionEnum.ASC;
import static org.eclipse.set.utils.table.sorting.ComparatorBuilder.CellComparatorType.LEXICOGRAPHICAL;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.set.core.services.enumtranslation.EnumTranslationService;
import org.eclipse.set.feature.table.PlanPro2TableTransformationService;
import org.eclipse.set.feature.table.pt1.AbstractPlanPro2TableTransformationService;
import org.eclipse.set.feature.table.pt1.messages.Messages;
import org.eclipse.set.model.tablemodel.ColumnDescriptor;
import org.eclipse.set.model.tablemodel.RowGroup;
import org.eclipse.set.model.tablemodel.RowMergeMode;
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup;
import org.eclipse.set.ppmodel.extensions.utils.TableNameInfo;
import org.eclipse.set.utils.table.ColumnDescriptorModelBuilder;
import org.eclipse.set.utils.table.TableModelTransformator;
import org.eclipse.set.utils.table.sorting.TableRowGroupComparator;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.event.EventAdmin;

/**
 * 
 */
@Component(service = {
		PlanPro2TableTransformationService.class }, immediate = true, property = {
				"table.category=supplement", "table.shortcut=sxxx" })
public class SxxxTransformationService
		extends AbstractPlanPro2TableTransformationService {
	@Reference
	Messages messages;

	@Reference
	EventAdmin eventAdmin;

	@Reference
	EnumTranslationService enumTranslationService;

	@Override
	protected String getTableHeading() {
		return messages.SxxxTableView_Heading;
	}

	@Override
	protected String getShortcut() {
		return messages.ToolboxTableNameSxxxShort;
	}

	@Override
	protected List<String> getTopologicalColumnPosition() {
		return Collections.emptyList();
	}

	@Override
	public TableNameInfo getTableNameInfo() {
		return new TableNameInfo(messages.ToolboxTableNameSxxxLong,
				messages.ToolboxTableNameSxxxPlanningNumber,
				messages.ToolboxTableNameSxxxShort);
	}

	@Override
	public TableModelTransformator<MultiContainer_AttributeGroup> createTransformator() {
		return new SxxxTransformator(cols, enumTranslationService, eventAdmin);
	}

	@Override
	public Comparator<RowGroup> getRowGroupComparator() {
		return TableRowGroupComparator.builder()
				.sort(SxxxColumns.Text_Content, LEXICOGRAPHICAL, ASC)
				.sort(SxxxColumns.Reference_Object, LEXICOGRAPHICAL, ASC)
				.build();
	}

	@Override
	public ColumnDescriptor fillHeaderDescriptions(
			final ColumnDescriptorModelBuilder builder) {
		final ColumnDescriptor cd = super.fillHeaderDescriptions(builder);
		// only merge on column A
		cd.setMergeCommonValues(RowMergeMode.ENABLED);
		List.of(SxxxColumns.Reference_Object, SxxxColumns.Visualation_In_Table)
				.forEach(it -> cols.forEach(col -> {
					if (it.equals(col.getColumnPosition())) {
						col.setMergeCommonValues(RowMergeMode.DISABLED);
					}
				}));

		return cd;
	}

}
