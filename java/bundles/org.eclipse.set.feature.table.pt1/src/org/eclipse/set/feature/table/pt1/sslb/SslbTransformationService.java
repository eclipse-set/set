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

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.set.core.services.enumtranslation.EnumTranslationService;
import org.eclipse.set.core.services.graph.TopologicalGraphService;
import org.eclipse.set.feature.table.PlanPro2TableTransformationService;
import org.eclipse.set.feature.table.pt1.AbstractPlanPro2TableModelTransformator;
import org.eclipse.set.feature.table.pt1.AbstractPlanPro2TableTransformationService;
import org.eclipse.set.feature.table.pt1.messages.Messages;
import org.eclipse.set.model.planpro.Block.Block_Anlage;
import org.eclipse.set.model.planpro.Block.Block_Element;
import org.eclipse.set.model.tablemodel.CellContent;
import org.eclipse.set.model.tablemodel.ColumnDescriptor;
import org.eclipse.set.model.tablemodel.CompareStateCellContent;
import org.eclipse.set.model.tablemodel.CompareTableCellContent;
import org.eclipse.set.model.tablemodel.RowGroup;
import org.eclipse.set.model.tablemodel.StringCellContent;
import org.eclipse.set.model.tablemodel.Table;
import org.eclipse.set.model.tablemodel.TableCell;
import org.eclipse.set.model.tablemodel.TableRow;
import org.eclipse.set.model.tablemodel.TablemodelFactory;
import org.eclipse.set.model.tablemodel.extensions.TableExtensions;
import org.eclipse.set.model.tablemodel.extensions.TableRowExtensions;
import org.eclipse.set.ppmodel.extensions.BasisAttributExtensions;
import org.eclipse.set.ppmodel.extensions.EObjectExtensions;
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup;
import org.eclipse.set.ppmodel.extensions.utils.TableNameInfo;
import org.eclipse.set.utils.table.TableInfo.Pt1TableCategory;
import org.eclipse.set.utils.table.sorting.TableRowGroupComparator;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.event.EventAdmin;

import com.google.common.collect.Streams;

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
	@Reference
	private EventAdmin eventAdmin;

	/**
	 * constructor.
	 */
	public SslbTransformationService() {
		super();
	}

	@Override
	public AbstractPlanPro2TableModelTransformator createTransformator() {
		return new SslbTransformator(cols, enumTranslationService,
				topGraphService, eventAdmin);
	}

	@Override
	public Comparator<RowGroup> getRowGroupComparator() {
		return TableRowGroupComparator.builder()
				.sort("F", LEXICOGRAPHICAL, ASC) //$NON-NLS-1$
				.sort("I", LEXICOGRAPHICAL, ASC) //$NON-NLS-1$
				.build();
	}

	@Override
	public TableNameInfo getTableNameInfo() {
		return new TableNameInfo(messages.ToolboxTableNameSslbLong,
				messages.ToolboxTableNameSslbPlanningNumber,
				messages.ToolboxTableNameSslbShort,
				messages.ToolboxTableNameSslbRil);
	}

	@Override
	protected String getTableHeading() {
		return messages.SslbTableView_Heading;
	}

	@Override
	protected String getShortcut() {
		return messages.ToolboxTableNameSslbShort.toLowerCase();
	}

	@Override
	protected List<String> getTopologicalColumnPosition() {
		return List.of(SslbColumns.Streckenfreimeldung,
				SslbColumns.Anrueckabschnitt_Bezeichnung,
				SslbColumns.Anrueckabschnitt_Anordnung);
	}

	@Override
	protected Map<Class<?>, String> getFootnotesColumnReferences() {
		return Collections.emptyMap();
	}

	@Override
	protected Pt1TableCategory getTableCategory() {
		return Pt1TableCategory.ESTW;
	}

	@Override
	// Special case for block element: find the another BlockElement, which
	// belong to the Block_Anlage of the filtered Block_Element and added to the
	// filtered table, despite the another Block_Element not belong to the
	// current selected area
	// See: ppmtab - Relevant area
	public void addAdditionRow(final Table originalTable,
			final Table filteredTable) {
		final List<RowGroup> originalTableRowGroups = TableExtensions
				.getTableRowGroups(originalTable);
		final List<RowGroup> filteredTableRowGroups = TableExtensions
				.getTableRowGroups(filteredTable);
		// When the tables are same
		if (originalTableRowGroups.size() == filteredTableRowGroups.size()
				&& originalTableRowGroups.stream()
						.allMatch(group -> filteredTableRowGroups.stream()
								.anyMatch(g -> group.getLeadingObject()
										.equals(g.getLeadingObject())))) {
			return;
		}

		try {
			final List<Block_Element> blockElements = filteredTableRowGroups
					.stream()
					.map(RowGroup::getLeadingObject)
					.map(Block_Element.class::cast)
					.toList();
			blockElements.forEach(block -> {
				final Block_Element anotherBlockElement = findAnotherBlockElement(
						block);
				if (anotherBlockElement == null
						|| blockElements.contains(anotherBlockElement)) {
					return;
				}
				final Optional<RowGroup> anotherBlockRowGroup = originalTableRowGroups
						.stream()
						.filter(ele -> ele.getLeadingObject()
								.equals(anotherBlockElement))
						.findFirst();
				if (anotherBlockRowGroup.isEmpty()) {
					return;
				}
				final RowGroup clone = EcoreUtil
						.copy(anotherBlockRowGroup.get());
				final TableRow tableRow = clone.getRows().getFirst();
				final ColumnDescriptor columnDescriptor = TableRowExtensions
						.getColumnDescriptors(tableRow)
						.stream()
						.filter(description -> description.getColumnPosition()
								.equals(SslbColumns.Streckenziel_Start))
						.findFirst()
						.orElse(null);
				if (columnDescriptor == null) {
					throw new NullPointerException("Table missing column"); //$NON-NLS-1$
				}
				final TableCell routeEndCell = TableRowExtensions
						.getCell(tableRow, columnDescriptor);
				routeEndCell.setContent(
						setNewRouteEndValue(routeEndCell.getContent()));
				TableExtensions.addRowGroup(filteredTable, clone);
			});
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static Block_Element findAnotherBlockElement(
			final Block_Element current) {
		final MultiContainer_AttributeGroup container = BasisAttributExtensions
				.getContainer(current);
		final Optional<Block_Anlage> blockAnlage = Streams
				.stream(container.getBlockAnlage())
				.filter(anlage -> EObjectExtensions
						.getNullableObject(anlage,
								ele -> ele.getIDBlockElementA().getValue())
						.orElse(null)
						.equals(current)
						|| EObjectExtensions
								.getNullableObject(anlage,
										ele -> ele.getIDBlockElementB()
												.getValue())
								.orElse(null)
								.equals(current))
				.findFirst();

		if (blockAnlage.isEmpty()) {
			return null;
		}

		return blockAnlage.get().getIDBlockElementA().getValue() == current
				? blockAnlage.get().getIDBlockElementB().getValue()
				: blockAnlage.get().getIDBlockElementA().getValue();
	}

	private static CellContent setNewRouteEndValue(
			final CellContent cellContent) {
		if (cellContent == null) {
			return null;
		}
		return switch (cellContent) {
			case final StringCellContent stringCellContent -> {
				final List<String> newValues = stringCellContent.getValue()
						.stream()
						.map(value -> String.format("(%s)", value)) //$NON-NLS-1$
						.toList();
				final StringCellContent newCellContent = TablemodelFactory.eINSTANCE
						.createStringCellContent();
				newCellContent.getValue().addAll(newValues);
				if (stringCellContent.getSeparator() != null) {
					newCellContent
							.setSeparator(stringCellContent.getSeparator());
				}
				yield newCellContent;
			}
			case final CompareStateCellContent compareCellContent -> {
				final CompareStateCellContent newCellContent = TablemodelFactory.eINSTANCE
						.createCompareStateCellContent();
				if (compareCellContent.getSeparator() != null) {
					newCellContent
							.setSeparator(compareCellContent.getSeparator());
				}

				newCellContent.setOldValue(
						setNewRouteEndValue(compareCellContent.getOldValue()));
				newCellContent.setNewValue(
						setNewRouteEndValue(compareCellContent.getNewValue()));
				yield newCellContent;
			}
			case final CompareTableCellContent compareTableCellContent -> setNewRouteEndValue(
					compareTableCellContent.getMainPlanCellContent());
			default -> null;
		};
	}
}
