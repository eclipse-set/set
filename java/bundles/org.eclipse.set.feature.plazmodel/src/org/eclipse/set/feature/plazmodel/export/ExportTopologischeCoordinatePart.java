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
package org.eclipse.set.feature.plazmodel.export;

import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.set.basis.files.ToolboxFileFilter;
import org.eclipse.set.basis.files.ToolboxFileFilterBuilder;
import org.eclipse.set.core.services.configurationservice.UserConfigurationService;
import org.eclipse.set.feature.plazmodel.check.GeoCoordinateValid;
import org.eclipse.set.ppmodel.extensions.EObjectExtensions;
import org.eclipse.set.utils.BasePart;
import org.eclipse.set.utils.table.export.ExportToCSV;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.locationtech.jts.geom.Coordinate;

import jakarta.inject.Inject;

/**
 * 
 */
public class ExportTopologischeCoordinatePart extends BasePart {
	@Inject
	IEclipseContext context;

	@Inject
	UserConfigurationService userConfigService;

	private static String HEADER_PATTERN = """
			PlaZ Modell-Prüfung Topologische Coordinate
			Datei: %s
			Prüfungszeit: %s
			Werkzeugkofferversion: %s


			"Lfd. Nr.";"Zustand";"Objektart";"Guid";"Koordinatensystem";"x";"y"
			"""; //$NON-NLS-1$

	Path selectedDir;
	ExportToCSV<String> csvExport;

	/**
	 * Default Constructor
	 */
	public ExportTopologischeCoordinatePart() {
		super();
		csvExport = new ExportToCSV<>(HEADER_PATTERN);
	}

	@Override
	protected void createView(final Composite parent) {
		selectedDir = userConfigService.getLastExportPath();
		final Button exportButton = new Button(parent, SWT.PUSH);
		exportButton.setText(
				"Exportieren Topologische Coordinate von Punkt_Objekt (csv)"); //$NON-NLS-1$
		final ToolboxFileFilter toolboxFileFilter = ToolboxFileFilterBuilder
				.forName("top_coordinate") //$NON-NLS-1$
				.add("csv") //$NON-NLS-1$
				.filterNameWithFilterList(true)
				.create();
		exportButton.addListener(SWT.Selection,
				event -> getDialogService()
						.saveFileDialog(getToolboxShell(),
								List.of(toolboxFileFilter), selectedDir)
						.ifPresent(path -> {
							selectedDir = path;
							userConfigService.setLastExportPath(selectedDir);
							exportCoordinateToCSV(path);
						}));
	}

	private void exportCoordinateToCSV(final Path path) {
		final GeoCoordinateValid geoCoordinateValid = context
				.get(GeoCoordinateValid.class);
		geoCoordinateValid.run(getModelSession());
		final List<TopologischeCoordinate> topologischeCoordinaten = geoCoordinateValid
				.getTopologischeCoordinaten();
		if (topologischeCoordinaten == null) {
			return;
		}
		topologischeCoordinaten.sort((first, second) -> {
			final String firstClassName = first.po()
					.eClass()
					.getInstanceClassName();
			final String secondClassName = second.po()
					.eClass()
					.getInstanceClassName();
			return firstClassName.compareTo(secondClassName);
		});
		final List<String> csvEntry = new LinkedList<>();
		for (int i = 0; i < topologischeCoordinaten.size(); i++) {
			csvEntry.add(transformToCsv(i + 1, topologischeCoordinaten.get(i)));
		}
		csvExport.exportToCSV(Optional.of(path), csvEntry);
	}

	private static String transformToCsv(final int index,
			final TopologischeCoordinate topCoor) {
		final String state = switch (topCoor.state()) {
			case FINAL -> "Ziel"; //$NON-NLS-1$
			case INITIAL -> "Start"; //$NON-NLS-1$
			default -> "Alleinzustehender"; //$NON-NLS-1$
		};
		final String instanceClassName = topCoor.po()
				.eClass()
				.getInstanceClassName();
		final String crs = EObjectExtensions
				.getNullableObject(topCoor,
						top -> top.coordinate().getCRS().getLiteral())
				.orElse("Fehler bei der Berechnung");
		final Coordinate coord = EObjectExtensions
				.getNullableObject(topCoor,
						top -> top.coordinate().getCoordinate())
				.orElse(null);
		return List
				.of(String.valueOf(index), state,
						instanceClassName.substring(
								instanceClassName.lastIndexOf(".") + 1),
						topCoor.po().getIdentitaet().getWert(), crs,
						coord == null ? "Fehler bei der Berechnung"
								: String.valueOf(coord.x),
						coord == null ? "Fehler bei der Berechnung"
								: String.valueOf(coord.y))
				.stream()
				.collect(Collectors.joining(";")) + System.lineSeparator(); //$NON-NLS-1$
	}

}
