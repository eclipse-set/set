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
package org.eclipse.set.feature.validation.parts;

import java.util.List;
import java.util.function.ToLongFunction;

import org.eclipse.set.basis.constants.ToolboxConstants;
import org.eclipse.set.core.services.part.ToolboxPartService;
import org.eclipse.set.feature.validation.Messages;
import org.eclipse.set.model.validationreport.ObjectScope;
import org.eclipse.set.model.validationreport.ValidationProblem;
import org.eclipse.set.model.validationreport.ValidationReport;
import org.eclipse.set.model.validationreport.ValidationSeverity;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

/**
 * 
 */
public class ValidationReportOverview extends ModelInfoSection {
	private final ValidationReport validationReport;
	private final ToolboxPartService toolboxPartservice;

	public ValidationReportOverview(final Composite parent,
			final ValidationReport validationReport,
			final ToolboxPartService toolboxPartservice,
			final Messages messages) {
		super(parent, messages);
		this.validationReport = validationReport;
		this.toolboxPartservice = toolboxPartservice;
	}

	protected void createValidationReportOverviewGroup(
			final ValidationReport validationReport) {
		final Composite expandedSecion = createExpandedSecion(
				messages.ValidationReport_Report_Title);
		createReportInfoGroup(expandedSecion,
				messages.ValidationReport_Report_PlaningRegion,
				ObjectScope.PLAN);

		createReportInfoGroup(expandedSecion,
				messages.ValidationReport_Report_ViewRegion,
				ObjectScope.BETRACHTUNG);

		final Button showTableButton = new Button(expandedSecion, SWT.PUSH);
		showTableButton.setText(messages.ShowValidationTableMsg);
		showTableButton.addListener(SWT.Selection, event -> toolboxPartservice
				.showPart(ToolboxConstants.VALIDATION_TABLE_PART_ID));
		showTableButton
				.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, true, true));

	}

	protected void createReportInfoGroup(final Composite expandedSection,
			final String groupTitle, final ObjectScope scope) {
		final List<ValidationProblem> reportInRegion = validationReport
				.getProblems()
				.stream()
				.filter(report -> report.getObjectScope().equals(scope))
				.toList();
		final ToLongFunction<ValidationSeverity> getSeverityCount = severity -> reportInRegion
				.stream()
				.filter(report -> report.getSeverity().equals(severity))
				.count();

		final GroupSectionControl regionGroup = new GroupSectionControl(
				expandedSection, groupTitle);

		final long errorCount = getSeverityCount
				.applyAsLong(ValidationSeverity.ERROR);
		regionGroup.addTextControl(messages.ErrorMsg,
				String.valueOf(errorCount));

		final long warningCount = getSeverityCount
				.applyAsLong(ValidationSeverity.WARNING);
		regionGroup.addTextControl(messages.WarningMsg,
				String.valueOf(warningCount));

		final long successCount = getSeverityCount
				.applyAsLong(ValidationSeverity.SUCCESS);
		regionGroup.addTextControl(messages.SuccessMsg,
				String.valueOf(successCount));
	}
}
