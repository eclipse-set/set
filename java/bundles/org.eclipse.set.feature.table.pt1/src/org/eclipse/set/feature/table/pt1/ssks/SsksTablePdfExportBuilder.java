/**
 * Copyright (c) 2025 DB InfraGO AG and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 */
package org.eclipse.set.feature.table.pt1.ssks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.set.feature.export.pdf.FopPdfExportBuilder;
import org.eclipse.set.feature.table.pt1.messages.Messages;
import org.eclipse.set.model.tablemodel.ColumnDescriptor;
import org.eclipse.set.model.tablemodel.Table;
import org.eclipse.set.model.tablemodel.TableRow;
import org.eclipse.set.model.tablemodel.extensions.TableExtensions;
import org.eclipse.set.model.tablemodel.extensions.TableRowExtensions;
import org.eclipse.set.services.export.TableExport;
import org.eclipse.set.services.fop.FopService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * Specifier PDF export for table Ssks via FOP
 * 
 * @author Truong
 */
@Component(immediate = true, service = TableExport.class)
public class SsksTablePdfExportBuilder extends FopPdfExportBuilder {

	@Reference
	private Messages messages;

	/**
	 * @param fopService
	 *            the FOP Service
	 */
	@Override
	@Reference(cardinality = ReferenceCardinality.MANDATORY, policy = ReferencePolicy.STATIC, unbind = "-")
	public void setFopService(final FopService fopService) {
		this.fopService = fopService;
	}

	@Override
	public List<String> getPageBreakRowsIndex(final Table table) {
		final List<String> result = new ArrayList<>();
		List.of(SsksColumns.Fiktives_Signal, SsksColumns.Reales_Signal)
				.forEach(columnPosition -> {
					final ColumnDescriptor col = TableExtensions
							.getColumns(table)
							.stream()
							.filter(column -> column.getColumnPosition()
									.equals(columnPosition))
							.findFirst()
							.orElse(null);
					final Optional<Integer> lastRowIndex = getLastRowIndex(
							table, col);
					if (lastRowIndex.isPresent()) {
						result.add(lastRowIndex.get().toString());
					}
				});
		return result;
	}

	private static Optional<Integer> getLastRowIndex(final Table table,
			final ColumnDescriptor column) {
		final List<Entry<Integer, TableRow>> signalEntries = getSignalEntries(
				table, column);
		final Optional<Entry<Integer, TableRow>> collect = signalEntries
				.stream()
				.collect(Collectors
						.maxBy(Comparator.comparingInt(Map.Entry::getKey)));
		if (collect.isPresent()) {
			return Optional.of(collect.get().getKey());
		}
		return Optional.empty();
	}

	private static List<Entry<Integer, TableRow>> getSignalEntries(
			final Table table, final ColumnDescriptor column) {
		if (column == null) {
			return Collections.emptyList();
		}
		final List<TableRow> tableRows = TableExtensions.getTableRows(table);
		return tableRows.stream()
				.collect(Collectors.toMap(
						e -> Integer.valueOf(tableRows.indexOf(e) + 1),
						Function.identity()))
				.entrySet()
				.parallelStream()
				.filter(entry -> {
					final TableRow row = entry.getValue();
					final String cellValue = TableRowExtensions
							.getPlainStringValue(row, column);
					return !cellValue.isEmpty() && !cellValue.isBlank();
				})
				.toList();

	}

	@Override
	public String getTableShortcut() {
		return messages.ToolboxTableNameSsksShort.toLowerCase();
	}

	@Override
	public ExportFormat getExportFormat() {
		return ExportFormat.PDF;
	}

}
