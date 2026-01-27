/**
 * Copyright (c) 2023 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.pt1.sskp;

import static org.eclipse.set.feature.table.pt1.sskp.SskpColumns.*;
import static org.eclipse.set.ppmodel.extensions.utils.IterableExtensions.getFirstOrNull;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.eclipse.nebula.widgets.nattable.sort.SortDirectionEnum;
import org.eclipse.set.core.services.enumtranslation.EnumTranslationService;
import org.eclipse.set.core.services.graph.TopologicalGraphService;
import org.eclipse.set.feature.table.PlanPro2TableTransformationService;
import org.eclipse.set.feature.table.pt1.AbstractPlanPro2TableModelTransformator;
import org.eclipse.set.feature.table.pt1.AbstractPlanPro2TableTransformationService;
import org.eclipse.set.feature.table.pt1.messages.Messages;
import org.eclipse.set.model.planpro.Basisobjekte.Basis_Objekt;
import org.eclipse.set.model.planpro.Basisobjekte.Punkt_Objekt;
import org.eclipse.set.model.planpro.PZB.PZB_Element;
import org.eclipse.set.model.tablemodel.ColumnDescriptor;
import org.eclipse.set.model.tablemodel.CompareCellContent;
import org.eclipse.set.model.tablemodel.MultiColorCellContent;
import org.eclipse.set.model.tablemodel.MultiColorContent;
import org.eclipse.set.model.tablemodel.RowGroup;
import org.eclipse.set.model.tablemodel.RowMergeMode;
import org.eclipse.set.model.tablemodel.StringCellContent;
import org.eclipse.set.model.tablemodel.TableCell;
import org.eclipse.set.ppmodel.extensions.PZBElementExtensions;
import org.eclipse.set.ppmodel.extensions.utils.TableNameInfo;
import org.eclipse.set.utils.table.ColumnDescriptorModelBuilder;
import org.eclipse.set.utils.table.sorting.ComparatorBuilder.CellComparatorType;
import org.eclipse.set.utils.table.sorting.TableRowGroupComparator;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.event.EventAdmin;

/**
 * Service for creating the sskp table model. org.eclipse.set.feature.table
 * 
 * @author Truong
 */
@Component(service = {
		PlanPro2TableTransformationService.class }, immediate = true, property = {
				"table.category=estw", "table.shortcut=sskp" })
public class SskpTransformationService
		extends AbstractPlanPro2TableTransformationService {

	@Reference
	private Messages messages;
	@Reference
	private EnumTranslationService enumTranslationService;
	@Reference
	private TopologicalGraphService topGraphService;
	@Reference
	private EventAdmin eventAdmin;

	/**
	 * constructor.
	 */
	public SskpTransformationService() {
		super();
	}

	@Override
	public AbstractPlanPro2TableModelTransformator createTransformator() {
		return new SskpTransformator(cols, enumTranslationService,
				topGraphService, eventAdmin);
	}

	@Override
	public TableNameInfo getTableNameInfo() {
		return new TableNameInfo(messages.ToolboxTableNameSskpLong,
				messages.ToolboxTableNameSskpPlanningNumber,
				messages.ToolboxTableNameSskpShort);
	}

	@Override
	protected String getTableHeading() {
		return messages.SskpTableView_Heading;
	}

	private static String getCellContent(final TableCell cell) {
		String cellValue = ""; //$NON-NLS-1$
		if (cell.getContent() instanceof final StringCellContent cellContent) {
			cellValue = getFirstOrNull(cellContent.getValue());
		}
		if (cell.getContent() instanceof final CompareCellContent cellContent) {
			if (cellContent.getNewValue().isEmpty()
					|| cellContent.getOldValue().isEmpty()) {
				cellValue = Optional
						.ofNullable(getFirstOrNull(cellContent.getNewValue()))
						.orElse(getFirstOrNull(cellContent.getOldValue()));
			}
			cellValue = cellContent.getNewValue().get(0)
					+ cellContent.getSeparator()
					+ cellContent.getOldValue().get(0);
		}
		if (cell.getContent() instanceof final MultiColorCellContent cellContent) {
			final MultiColorContent firstOrNull = getFirstOrNull(
					cellContent.getValue());

			cellValue = firstOrNull != null ? firstOrNull.getMultiColorValue()
					: null;
		}
		return cellValue == null || cellValue.isEmpty() ? null
				: cellValue.split(" ")[0]; //$NON-NLS-1$
	}

	@Override
	public Comparator<RowGroup> getRowGroupComparator() {
		final List<String> gmOrder = List.of("2000", "1000/2000", "1000", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				"500"); //$NON-NLS-1$
		return TableRowGroupComparator.builder().sortByRouteAndKm(obj -> {
			if (obj instanceof final PZB_Element pzb) {
				final List<Basis_Objekt> bezugPunkts = PZBElementExtensions
						.getPZBElementBezugspunkt(pzb);

				if (!bezugPunkts.isEmpty() && bezugPunkts
						.getFirst() instanceof final Punkt_Objekt po) {
					return po;
				}
			}
			return null;
		})
				.sort(Bezugselement, CellComparatorType.LEXICOGRAPHICAL,
						SortDirectionEnum.ASC)
				.sort(Wirkfrequenz, Comparator.comparing(
						SskpTransformationService::getCellContent,
						Comparator.nullsLast(Comparator.comparing(
								gmOrder::indexOf, Integer::compareUnsigned))))
				.build();

	}

	@Override
	public ColumnDescriptor fillHeaderDescriptions(
			final ColumnDescriptorModelBuilder builder) {
		final ColumnDescriptor cd = super.fillHeaderDescriptions(builder);
		// Merge all columns except C to F
		cd.setMergeCommonValues(RowMergeMode.ENABLED);
		List.of(SskpColumns.PZB_Schutzpunkt, //
				SskpColumns.GeschwindigkeitsKlasse, //
				SskpColumns.PZB_Schutzstrecke_Soll, //
				SskpColumns.PZB_Schutzstrecke_Ist)
				.forEach(it -> cols.forEach(col -> {
					if (it.equals(col.getColumnPosition())) {
						col.setMergeCommonValues(RowMergeMode.DISABLED);
					}
				}));

		return cd;
	}

	@Override
	protected String getShortcut() {
		return messages.ToolboxTableNameSskpShort.toLowerCase();
	}

	@Override
	protected List<String> getTopologicalColumnPosition() {
		return List.of(PZB_Schutzstrecke_Ist, Abstand_Signal_Weiche,
				Abstand_GM_2000, Gef_Stelle_Abstand,
				Abstand_GM_2000_Bahnsteig_Anfang,
				Abstand_GM_2000_Bahnsteig_Ende, H_Tafel_Abstand,
				Abstand_vorsignalWdh_GM_2000);
	}
}
