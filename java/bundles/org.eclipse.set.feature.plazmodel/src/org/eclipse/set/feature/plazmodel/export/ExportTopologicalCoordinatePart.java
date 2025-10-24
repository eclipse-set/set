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

import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.set.basis.ToolboxPaths.ExportPathExtension;
import org.eclipse.set.basis.constants.ContainerType;
import org.eclipse.set.core.services.configurationservice.UserConfigurationService;
import org.eclipse.set.feature.plazmodel.Messages;
import org.eclipse.set.feature.plazmodel.check.GeoCoordinateValid;
import org.eclipse.set.model.planpro.Basisobjekte.Punkt_Objekt;
import org.eclipse.set.model.planpro.Ortung.FMA_Komponente;
import org.eclipse.set.model.planpro.PZB.PZB_Element;
import org.eclipse.set.model.planpro.PlanPro.PlanPro_Schnittstelle;
import org.eclipse.set.ppmodel.extensions.BasisAttributExtensions;
import org.eclipse.set.ppmodel.extensions.EObjectExtensions;
import org.eclipse.set.ppmodel.extensions.MultiContainer_AttributeGroupExtensions;
import org.eclipse.set.ppmodel.extensions.PlanProSchnittstelleExtensions;
import org.eclipse.set.ppmodel.extensions.PunktObjektExtensions;
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup;
import org.eclipse.set.utils.BasePart;
import org.eclipse.set.utils.table.export.ExportToCSV;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.locationtech.jts.geom.Coordinate;

import com.google.common.collect.Streams;

import jakarta.inject.Inject;

/**
 * Export topological coordinate of {@link Punkt_Objekt}, which calculate
 * through {@link GeoCoordinateValid}
 * 
 * @author truong
 */
@SuppressWarnings("nls")
public class ExportTopologicalCoordinatePart extends BasePart {
	@Inject
	IEclipseContext context;

	@Inject
	UserConfigurationService userConfigService;

	@Inject
	@Translation
	Messages messages;

	private static String HEADER_PATTERN = """
			PlaZ Modell-Prüfung Topologische Coordinate
			Datei: %s
			Prüfungszeit: %s


			"Lfd. Nr.";"Zustand";"Objektart";"Guid";"Koordinatensystem";"x";"y"
			""";

	ExportToCSV<String> csvExport;

	/**
	 * Default Constructor
	 */
	public ExportTopologicalCoordinatePart() {
		super();
		csvExport = new ExportToCSV<>(HEADER_PATTERN);
	}

	@Override
	protected void createView(final Composite parent) {
		final Button exportButton = new Button(parent, SWT.PUSH);
		exportButton.setText(
				"Exportieren Topologische Coordinate von Punkt_Objekt (csv)");
		exportButton.addListener(SWT.Selection, event -> exportHandle());
	}

	private void exportHandle() {
		final Optional<String> optionalOutputDir = getDialogService()
				.selectDirectory(getToolboxShell(),
						userConfigService.getLastExportPath().toString());

		try {
			getDialogService().showProgress(getToolboxShell(),
					monitor -> optionalOutputDir.ifPresent(outputDir -> {
						monitor.beginTask(
								messages.PlazExport_ExportProcess_Message,
								IProgressMonitor.UNKNOWN);
						final Path exportPath = getModelSession()
								.getToolboxPaths()
								.getTableExportPath("tologische_coordinate",
										Path.of(outputDir), null,
										ExportPathExtension.TABLE_CSV_EXPORT_EXTENSION);
						exportCoordinateToCSV(exportPath);
					}));
			optionalOutputDir.ifPresent(dir -> {
				getDialogService().openDirectoryAfterExport(getToolboxShell(),
						Path.of(dir));
				userConfigService.setLastExportPath(Path.of(dir));
			});
		} catch (InvocationTargetException | InterruptedException e) {
			Thread.currentThread().interrupt();
			getDialogService().error(getToolboxShell(), e);
		}
	}

	private void exportCoordinateToCSV(final Path path) {
		if (Files.exists(path, LinkOption.NOFOLLOW_LINKS) && !getDialogService()
				.confirmOverwrite(getToolboxShell(), path)) {
			return;
		}
		final List<TopologicalCoordinate> topologicalCoordinates = getTopologicalCoordiante();
		if (topologicalCoordinates == null) {
			return;
		}
		topologicalCoordinates.sort((first, second) -> {
			final String firstClassName = first.po()
					.eClass()
					.getInstanceClassName();
			final String secondClassName = second.po()
					.eClass()
					.getInstanceClassName();
			return firstClassName.compareTo(secondClassName);
		});
		final List<String> csvEntry = new LinkedList<>();
		for (int i = 0; i < topologicalCoordinates.size(); i++) {
			csvEntry.add(transformToCsv(i + 1, topologicalCoordinates.get(i)));
		}
		csvExport.exportToCSV(Optional.of(path), csvEntry);
	}

	private List<TopologicalCoordinate> getTopologicalCoordiante() {
		final GeoCoordinateValid geoCoordinateValid = context
				.get(GeoCoordinateValid.class);
		if (geoCoordinateValid == null) {
			getDialogService().openInformation(getToolboxShell(),
					messages.PlazExport_Topological_Coordinate_Part,
					messages.PlazExport_ExportProcess_ErrorDialog_Message);
			return null;
		}

		final List<TopologicalCoordinate> topologischeCoordinaten = geoCoordinateValid
				.getTopologischeCoordinaten();
		if (topologischeCoordinaten != null) {
			return topologischeCoordinaten;
		}

		final PlanPro_Schnittstelle planProSchnittstelle = getModelSession()
				.getPlanProSchnittstelle();
		List.of(ContainerType.FINAL, ContainerType.INITIAL,
				ContainerType.SINGLE)
				.stream()
				.map(type -> PlanProSchnittstelleExtensions
						.getContainer(planProSchnittstelle, type))
				.filter(Objects::nonNull)
				.map(MultiContainer_AttributeGroup::getPunktObjekts)
				.flatMap(Streams::stream)
				.filter(po -> PunktObjektExtensions.existLateralDistance(po)
						|| po instanceof FMA_Komponente
						|| po instanceof PZB_Element)
				.forEach(po -> po.getPunktObjektTOPKante().forEach(potk -> {
					final MultiContainer_AttributeGroup container = BasisAttributExtensions
							.getContainer(po);
					final ContainerType containerType = MultiContainer_AttributeGroupExtensions
							.getContainerType(container);
					geoCoordinateValid.calculateCoordinate(containerType, po,
							potk);
				}));
		return geoCoordinateValid.getTopologischeCoordinaten();
	}

	private static String transformToCsv(final int index,
			final TopologicalCoordinate topCoor) {
		final String state = switch (topCoor.state()) {
			case FINAL -> "Ziel";
			case INITIAL -> "Start";
			default -> "Alleinstehend";
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
								: String.valueOf(coord.x).replace(".", ","),
						coord == null ? "Fehler bei der Berechnung"
								: String.valueOf(coord.y).replace(".", ","))
				.stream()
				.collect(Collectors.joining(";")) + System.lineSeparator();
	}

}
