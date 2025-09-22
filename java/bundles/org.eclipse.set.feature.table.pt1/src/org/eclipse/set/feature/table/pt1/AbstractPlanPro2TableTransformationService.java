/** 
 * Copyright (c) 2022 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.pt1;

import static org.eclipse.set.model.tablemodel.extensions.TableExtensions.setTextAlignment;
import static org.eclipse.set.utils.excel.ExcelWorkbookExtension.getFirstDataRow;
import static org.eclipse.set.utils.excel.ExcelWorkbookExtension.getRepeatingColumns;
import static org.eclipse.set.utils.table.TableBuilderFromExcel.headerBuilder;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.eclipse.set.feature.table.PlanPro2TableTransformationService;
import org.eclipse.set.model.planpro.Ansteuerung_Element.Stell_Bereich;
import org.eclipse.set.model.tablemodel.ColumnDescriptor;
import org.eclipse.set.model.tablemodel.Table;
import org.eclipse.set.model.tablemodel.extensions.ColumnDescriptorExtensions;
import org.eclipse.set.model.tablemodel.extensions.TableExtensions;
import org.eclipse.set.model.tablemodel.extensions.TableRowExtensions;
import org.eclipse.set.model.tablemodel.format.TextAlignment;
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup;
import org.eclipse.set.utils.table.ColumnDescriptorModelBuilder;
import org.eclipse.set.utils.table.GroupBuilder;

/**
 * Common base for tables in this bundle
 * 
 */
public abstract class AbstractPlanPro2TableTransformationService
		extends PlanPro2TableTransformationService {
	private static final String TEMPLATE_DIR = "data/export/excel/"; //$NON-NLS-1$
	protected Set<ColumnDescriptor> cols;

	protected XSSFSheet excelTemplate = null;

	@Override
	public ColumnDescriptor buildHeading(final Table table) {
		// Add missing heading units
		final ColumnDescriptor root = super.buildHeading(table);
		ColumnDescriptorExtensions.addMissingHeadingUnits(root);
		return root;
	}

	/**
	 * creates the columns.
	 */
	protected Set<ColumnDescriptor> getColumnsListe(
			final ColumnDescriptor root) {
		final Set<ColumnDescriptor> colunnsListe = new HashSet<>();
		root.getChildren().forEach(child -> {
			colunnsListe.add(child);
			final Set<ColumnDescriptor> nestedColumn = getColumnsListe(child);
			if (!nestedColumn.isEmpty()) {
				colunnsListe.addAll(nestedColumn);
			}
		});
		return colunnsListe;
	}

	@Override
	public ColumnDescriptor fillHeaderDescriptions(
			final ColumnDescriptorModelBuilder builder) {
		final GroupBuilder root = builder.createRootColumn(getTableHeading());
		final Path templatePath = Paths
				.get(TEMPLATE_DIR,
						getTableNameInfo().getShortName().toLowerCase()
								+ "_vorlage.xlsx") //$NON-NLS-1$
				.toAbsolutePath();
		try (final FileInputStream inputStream = new FileInputStream(
				templatePath.toFile());
				final Workbook workbook = new XSSFWorkbook(inputStream)) {
			excelTemplate = (XSSFSheet) workbook.getSheetAt(0);
			headerBuilder(excelTemplate, root, 1);
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
		cols = getColumnsListe(root.getGroupRoot());
		return root.getGroupRoot();
	}

	@Override
	public Set<Integer> getFixedColumnsPos() {
		final Set<Integer> repeatingColumns = getRepeatingColumns(
				excelTemplate);
		if (repeatingColumns.isEmpty()) {
			// By default first column will be fixed
			return Collections.singleton(Integer.valueOf(0));
		}
		// In Nattable row count column is not caculate
		return repeatingColumns.stream()
				.map(ele -> Integer.valueOf(ele.intValue() - 1))
				.filter(ele -> ele.intValue() >= 0)
				.collect(Collectors.toCollection(HashSet<Integer>::new));

	}

	/**
	 * @return
	 */
	protected abstract String getTableHeading();

	@Override
	protected void setColumnTextAlignment(final Table table) {
		if (excelTemplate == null) {
			return;
		}
		getFirstDataRow(excelTemplate).forEach(cell -> {
			if (cell != null) {
				final HorizontalAlignment alignment = cell.getCellStyle()
						.getAlignment();
				// Nattable start with column "A"
				// and Excel table start with row index column
				final int columnIdx = cell.getColumnIndex() - 1;
				switch (alignment) {
					case LEFT: {
						setTextAlignment(table, columnIdx, TextAlignment.LEFT);
						break;
					}
					case RIGHT: {
						setTextAlignment(table, columnIdx, TextAlignment.RIGHT);
						break;
					}
					// Another align will ignore
					default:
						break;
				}
			}

		});
	}

	@Override
	public Table transform(final MultiContainer_AttributeGroup model,
			final Stell_Bereich controlArea) {
		final Table table = super.transform(model, controlArea);
		if (transformator instanceof final AbstractPlanPro2TableModelTransformator pt1TableTransformator) {
			pt1TableTransformator.updateWaitingFillCell(getShortcut());
			pt1TableTransformator.getTopologicalCell()
					.forEach(TableRowExtensions::setTopologicalCell);
			setTopologicalColumnHightlight(table);
		}
		return table;
	}

	protected abstract String getShortcut();

	protected abstract List<String> getTopologicalColumnPosition();

	protected void setTopologicalColumnHightlight(final Table table) {
		final Set<ColumnDescriptor> topologicalCols = getTopologicalColumnPosition()
				.stream()
				.flatMap(position -> cols.stream()
						.filter(col -> col.getColumnPosition() != null
								&& col.getColumnPosition().equals(position)
								// Only take the column at last level
								&& col.getChildren().isEmpty()))
				.collect(Collectors.toSet());
		TableExtensions.getTableRows(table)
				.forEach(row -> TableRowExtensions.setTopologicalCell(row,
						topologicalCols));
	}
}
