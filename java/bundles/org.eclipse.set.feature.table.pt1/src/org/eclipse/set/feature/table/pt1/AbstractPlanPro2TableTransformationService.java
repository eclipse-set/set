/** 
 * Copyright (c) 2022 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.pt1;

import static org.eclipse.set.utils.table.TableBuilderFromExcel.headerBuilder;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.eclipse.set.feature.table.PlanPro2TableTransformationService;
import org.eclipse.set.model.tablemodel.ColumnDescriptor;
import org.eclipse.set.model.tablemodel.Table;
import org.eclipse.set.model.tablemodel.extensions.ColumnDescriptorExtensions;
import org.eclipse.set.utils.table.ColumnDescriptorModelBuilder;
import org.eclipse.set.utils.table.GroupBuilder;

/**
 * Common base for tables in this bundle
 * 
 */
public abstract class AbstractPlanPro2TableTransformationService
		extends PlanPro2TableTransformationService {
	private static final String TEMPLATE_DIR = "./data/export/excel/"; //$NON-NLS-1$
	protected Set<ColumnDescriptor> cols;

	@Override
	protected ColumnDescriptor buildHeading(final Table table) {
		// Add missing heading units
		final ColumnDescriptor root = super.buildHeading(table);
		ColumnDescriptorExtensions.addMissingHeadingUnits(root);
		return root;
	}

	@Override
	public ColumnDescriptor fillHeaderDescriptions(
			final ColumnDescriptorModelBuilder builder) {
		final GroupBuilder root = builder.createRootColumn(getTableHeading());
		final Path templatePath = Paths.get(TEMPLATE_DIR,
				getTableNameInfo().getShortName().toLowerCase()
						+ "_vorlage.xlt"); //$NON-NLS-1$
		try (final FileInputStream inputStream = new FileInputStream(
				templatePath.toFile());
				final Workbook workbook = new HSSFWorkbook(inputStream)) {
			final HSSFSheet sheetAt = (HSSFSheet) workbook.getSheetAt(0);
			headerBuilder(sheetAt, root, 1);

		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
		cols = getColumnsListe(root.getGroupRoot());
		return root.getGroupRoot();
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

	/**
	 * @return
	 */
	protected abstract String getTableHeading();
}
