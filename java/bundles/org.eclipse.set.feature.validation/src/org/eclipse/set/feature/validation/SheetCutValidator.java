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
package org.eclipse.set.feature.validation;

import java.util.List;

import org.eclipse.set.basis.PlanProXMLNode;
import org.eclipse.set.basis.constants.ValidationResult;
import org.eclipse.set.basis.files.ToolboxFile;
import org.eclipse.set.core.services.validation.CustomValidator;
import org.eclipse.set.model.planpro.Layoutinformationen.DocumentRoot;
import org.eclipse.set.model.planpro.Layoutinformationen.Lageplan_Blattschnitt;
import org.eclipse.set.model.planpro.Layoutinformationen.PlanPro_Layoutinfo;
import org.eclipse.set.model.planpro.Layoutinformationen.Polygonzug_Ausrichtung_TypeClass;
import org.eclipse.set.model.planpro.Layoutinformationen.Polygonzug_Blattschnitt_TypeClass;
import org.eclipse.set.model.validationreport.ValidationSeverity;
import org.eclipse.set.utils.xml.EObjectXMLFinder;
import org.osgi.service.component.annotations.Component;

/**
 * 
 * @author brombacher
 */
@Component(immediate = true, service = CustomValidator.class)
public class SheetCutValidator extends AbstractCustomValidator {
	public static final String SHEET_CUT_VALIDATION_TYPE = "Blattschnitte"; //$NON-NLS-1$

	@Override
	public void validate(final ToolboxFile toolboxFile,
			final ValidationResult result, final FileType type) {
		try {

			if (type != FileType.Layout) {
				return;
			}

			final DocumentRoot layoutRoot = toolboxFile.getLayoutDocumentRoot();

			if (layoutRoot == null) {
				return;
			}

			final PlanPro_Layoutinfo layoutInfo = layoutRoot
					.getPlanProLayoutinfo();

			if (layoutInfo == null) {
				return;
			}

			final List<Lageplan_Blattschnitt> sheetCuts = layoutInfo
					.getLageplanBlattschnitt();

			if (sheetCuts.isEmpty()) {
				result.addCustomProblem(new CustomValidationProblemImpl(
						"Keine Blattschnitte vorhanden", //$NON-NLS-1$
						ValidationSeverity.WARNING, this.validationType(), null,
						null, null));
			} else {
				final EObjectXMLFinder nodeFinder = new EObjectXMLFinder(
						toolboxFile, toolboxFile.getLayoutPath());
				boolean anyProblems = false;
				for (final Lageplan_Blattschnitt sheetCut : sheetCuts) {
					final PlanProXMLNode node = nodeFinder.find(sheetCut);
					final int line = node == null ? 0
							: nodeFinder.getLineNumber(node);
					final String objektArt = nodeFinder.getObjectType(node);

					final Polygonzug_Ausrichtung_TypeClass ausrichtung = sheetCut
							.getPolygonzugAusrichtung();
					final PlanProXMLNode ausrichtungsNode = nodeFinder
							.find(ausrichtung);
					final int ausrichtungsLine = ausrichtungsNode == null ? line
							: nodeFinder.getLineNumber(ausrichtungsNode);

					final String wert = ausrichtung == null ? null
							: ausrichtung.getWert();
					if (wert == null || wert.isBlank()) {
						anyProblems = true;
						final CustomValidationProblemImpl problem = new CustomValidationProblemImpl(
								"Blattschnitt ohne Ausrichtung", //$NON-NLS-1$
								ValidationSeverity.WARNING,
								this.validationType(), objektArt, null, null);
						problem.setLineNumber(ausrichtungsLine);
						result.addCustomProblem(problem);
					} else {
						final String[] tokens = wert.trim().split("\\s+"); //$NON-NLS-1$
						if (tokens.length != 4) {
							anyProblems = true;
							final CustomValidationProblemImpl problem = new CustomValidationProblemImpl(
									String.format(
											"Ausrichtung hat %d Koordinaten (erwartet 4)", //$NON-NLS-1$
											Integer.valueOf(tokens.length)),
									ValidationSeverity.WARNING,
									this.validationType(), objektArt, null,
									null);
							problem.setLineNumber(ausrichtungsLine);
							result.addCustomProblem(problem);
						}
					}

					final Polygonzug_Blattschnitt_TypeClass polygon = sheetCut
							.getPolygonzugBlattschnitt();
					final PlanProXMLNode polygonNode = nodeFinder.find(polygon);
					final int polygonLine = polygonNode == null ? line
							: nodeFinder.getLineNumber(polygonNode);

					final String polygonWert = polygon == null ? null
							: polygon.getWert();
					if (polygonWert == null || polygonWert.isBlank()) {
						anyProblems = true;
						final CustomValidationProblemImpl problem = new CustomValidationProblemImpl(
								"Blattschnitt ohne Polygon", //$NON-NLS-1$
								ValidationSeverity.WARNING,
								this.validationType(), objektArt, null, null);
						problem.setLineNumber(polygonLine);
						result.addCustomProblem(problem);
					} else {
						final String[] polygonTokens = polygonWert.trim()
								.split("\\s+"); //$NON-NLS-1$
						if (polygonTokens.length % 2 != 0) {
							anyProblems = true;
							final CustomValidationProblemImpl problem = new CustomValidationProblemImpl(
									String.format(
											"Polygon hat %d Koordinaten (ungerade Anzahl)", //$NON-NLS-1$
											Integer.valueOf(
													polygonTokens.length)),
									ValidationSeverity.WARNING,
									this.validationType(), objektArt, null,
									null);
							problem.setLineNumber(polygonLine);
							result.addCustomProblem(problem);
						} else if (polygonTokens.length / 2 < 3) {
							anyProblems = true;
							final CustomValidationProblemImpl problem = new CustomValidationProblemImpl(
									String.format(
											"Polygon hat %d Punkte, mindestens 3 erforderlich", //$NON-NLS-1$
											Integer.valueOf(
													polygonTokens.length / 2)),
									ValidationSeverity.WARNING,
									this.validationType(), objektArt, null,
									null);
							problem.setLineNumber(polygonLine);
							result.addCustomProblem(problem);
						}
					}
				}

				if (!anyProblems) {
					result.addCustomProblem(getSuccessValidationReport(
							String.format("%d Blattschnitte sind vorhanden", //$NON-NLS-1$
									Integer.valueOf(sheetCuts.size()))));
				}
			}
		} catch (final Exception ex) {
			result.addCustomProblem(new CustomValidationProblemImpl(
					"Blattschnitte konnten nicht geprüft werden", //$NON-NLS-1$
					ValidationSeverity.WARNING, this.validationType(), null,
					null, null));
		}
	}

	@Override
	public String validationType() {
		return SHEET_CUT_VALIDATION_TYPE;
	}

}
