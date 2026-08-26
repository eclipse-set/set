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

import static org.eclipse.set.ppmodel.extensions.PlanProSchnittstelleExtensions.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.ToLongFunction;
import java.util.stream.Collectors;

import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.jface.databinding.swt.typed.WidgetProperties;
import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.basis.constants.ToolboxConstants;
import org.eclipse.set.basis.observable.SupplierObservableValue;
import org.eclipse.set.core.services.part.ToolboxPartService;
import org.eclipse.set.feature.validation.Messages;
import org.eclipse.set.model.planpro.PlanPro.PlanPro_Schnittstelle;
import org.eclipse.set.model.validationreport.ObjectScope;
import org.eclipse.set.model.validationreport.ValidationProblem;
import org.eclipse.set.model.validationreport.ValidationReport;
import org.eclipse.set.model.validationreport.ValidationSeverity;
import org.eclipse.set.model.validationreport.VersionInfo;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.xtext.xbase.lib.Pair;

/**
 * The section control for loaded file information
 * 
 * @author truong
 */
public class ValidationViewModelInfo extends Composite {

	protected class GroupSectionControl {
		Composite viewSection;
		Group group;

		public GroupSectionControl(final Composite viewSection,
				final String groupName) {
			this.viewSection = viewSection;
			createGroup(groupName);
		}

		private void createGroup(final String groupName) {
			group = new Group(viewSection, SWT.NONE);
			group.setText(groupName);
			group.setLayout(new GridLayout(2, false));
			group.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		}

		public GroupSectionControl addTextControl(final String labelText,
				final String text) {
			final TextFieldWithLabelControl textFieldWithLabelControl = new TextFieldWithLabelControl(
					group, labelText, text);
			textFieldWithLabelControl.createControl();
			return this;
		}

		public GroupSectionControl addObservableTextControl(
				final String labelText, final Supplier<String> getTextFunc) {
			final Label label = new Label(group, SWT.NONE);
			label.setText(labelText);

			final Text textField = new Text(group, SWT.BORDER);
			textField.setLayoutData(
					new GridData(SWT.FILL, SWT.CENTER, true, false));
			textField.setEnabled(false);
			final SupplierObservableValue<String> text = new SupplierObservableValue<>(
					getTextFunc, String.class);
			observableValues.add(text);
			final DataBindingContext ctx = new DataBindingContext();
			ctx.bindValue(WidgetProperties.text().observe(textField), text);
			return this;
		}
	}

	protected record TextFieldWithLabelControl(Composite parent,
			String labelText, String text) {
		public void createControl() {
			final Label label = new Label(parent, SWT.NONE);
			label.setText(labelText);

			final Text textField = new Text(parent, SWT.BORDER);
			textField.setLayoutData(
					new GridData(SWT.FILL, SWT.CENTER, true, false));
			textField.setEnabled(false);
			textField.setText(text);
		}
	}

	protected Messages messages;
	protected ValidationReport validationReport;
	FormToolkit formToolkit;
	private final List<SupplierObservableValue<String>> observableValues;

	/**
	 * @param parent
	 *            the parent composite
	 * @param messages
	 *            the {@link Messages}
	 * @param validationReport
	 *            the {@link ValidationReport}
	 */
	public ValidationViewModelInfo(final Composite parent,
			final Messages messages, final ValidationReport validationReport) {
		super(parent, SWT.NONE);
		setLayout(new GridLayout());
		setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		formToolkit = new FormToolkit(getDisplay());
		this.messages = messages;
		this.validationReport = validationReport;
		this.observableValues = new ArrayList<>();
	}

	/**
	 * @param modelSession
	 *            the {@link IModelSession}
	 * @param toolboxPartService
	 *            the {@link ToolboxPartService}
	 */
	public void createView(final IModelSession modelSession,
			final ToolboxPartService toolboxPartService) {
		createModelInformationGroup();
		createFunctionalInformationenGroup();
		createMetadataInformationenGroup(modelSession);
		createValidationReportOverviewGroup(toolboxPartService);
	}

	private void createModelInformationGroup() {
		final Composite section = createExpandedSecion(
				messages.ValidationReport_ModelInfo);

		final GroupSectionControl loadedFileGroup = new GroupSectionControl(
				section, messages.ValidationReport_GeladeneDatei);
		loadedFileGroup //
				.addTextControl(
						messages.ValidationReport_GeladeneDatei_DateiName,
						validationReport.getFileInfo().getFileName())
				.addTextControl(
						messages.ValidationReport_GeladeneDatei_TimeStamp,
						validationReport.getFileInfo().getTimeStamp())
				.addTextControl(messages.ValidationReport_GeladeneDatei_MD5,
						validationReport.getFileInfo().getChecksum())
				.addTextControl(messages.ValidationReport_GeladeneDatei_GUID,
						validationReport.getFileInfo().getGuid());

		final GroupSectionControl validityGroup = new GroupSectionControl(
				section, messages.ValidationReport_Validity);
		validityGroup //
				.addTextControl(messages.ValidationReport_Validity_XSD_VALID,
						validationReport.getXsdValid())
				.addTextControl(messages.ValidationReport_Validity_EMF_VALID,
						validationReport.getEmfValid())
				.addTextControl(messages.ValidationReport_Validity_ModelLoaded,
						validationReport.getModelLoaded());

		final Pair<String, String> usedVersion = transformVersionInfo(
				validationReport.getFileInfo().getUsedVersion());
		final GroupSectionControl loadedVersionGroup = new GroupSectionControl(
				section, messages.ValidationReport_Used_Version);
		loadedVersionGroup //
				.addTextControl(messages.ValidationReport_Version_PlanPro,
						usedVersion.getKey())
				.addTextControl(
						messages.ValidationReport_Version_Signalbegriffe,
						usedVersion.getValue());

		final Pair<String, String> supportedVersions = transformVersionInfo(
				validationReport.getSupportedVersion());
		final GroupSectionControl supportVersionGroup = new GroupSectionControl(
				section, messages.ValidationReport_Supported_Version);
		supportVersionGroup //
				.addTextControl(messages.ValidationReport_Version_PlanPro,
						supportedVersions.getKey())
				.addTextControl(
						messages.ValidationReport_Version_Signalbegriffe,
						supportedVersions.getValue());
	}

	private void createFunctionalInformationenGroup() {
		final Composite expandedSecion = createExpandedSecion(
				messages.ValidationReport_FunctionalModelInfo);
		final GroupSectionControl subworkGroup = new GroupSectionControl(
				expandedSecion, messages.ValidationReport_Subworks);
		subworkGroup.addTextControl(
				messages.ValidationReport_Subwork_Model_Content,
				validationReport.getSubworkTypes());

		final GroupSectionControl containerContentsGroup = new GroupSectionControl(
				expandedSecion, messages.ValidationReport_ContainerContents);
		containerContentsGroup.addTextControl(
				messages.ValidationReport_Subwork_Model_Content,
				validationReport.getFileInfo().getContainerContents());
	}

	@SuppressWarnings("nls")
	private void createMetadataInformationenGroup(
			final IModelSession modelSession) {
		final Composite expandedSecion = createExpandedSecion(
				messages.ValidationReport_Metadata);
		final PlanPro_Schnittstelle planProSchnittstelle = modelSession
				.getPlanProSchnittstelle();
		final GroupSectionControl groupSectionControl = new GroupSectionControl(
				expandedSecion, "");

		groupSectionControl
				.addObservableTextControl(
						messages.ValidationReport_Metadata_Location,
						() -> getFuehrendeOertlichkeit(planProSchnittstelle)
								.orElse(""))
				.addObservableTextControl(
						messages.ValidationReport_Metadata_Route,
						() -> getStreckeAbschnitt(planProSchnittstelle)
								.orElse(""))
				.addObservableTextControl(
						messages.ValidationReport_Metadata_BuildDesignation,
						() -> getBauzustandKurzbezeichnung(planProSchnittstelle)
								.orElse(""));

		final GroupSectionControl secondGroup = new GroupSectionControl(
				expandedSecion, "");
		secondGroup
				.addObservableTextControl(
						messages.ValidationReport_Metadata_Index,
						() -> getIndexAusgabe(planProSchnittstelle).orElse(""))
				.addObservableTextControl(
						messages.ValidationReport_Metadata_LfdNr,
						() -> getLaufendeNummerAusgabe(planProSchnittstelle)
								.orElse(""))
				.addObservableTextControl(
						messages.ValidationReport_GeladeneDatei_TimeStamp,
						() -> {
							final Optional<XMLGregorianCalendar> datumAbschlussGruppe = getDatumAbschlussGruppe(
									planProSchnittstelle);
							return datumAbschlussGruppe.isPresent() //
									? datumAbschlussGruppe.get().toString() //
									: "";
						});
	}

	private void createValidationReportOverviewGroup(
			final ToolboxPartService toolboxPartService) {
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
		showTableButton.addListener(SWT.Selection, event -> toolboxPartService
				.showPart(ToolboxConstants.VALIDATION_TABLE_PART_ID));
		showTableButton
				.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, true, true));

	}

	private void createReportInfoGroup(final Composite expandedSection,
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

	private Composite createExpandedSecion(final String sectionTitle) {
		final Composite container = new Composite(this, SWT.NONE);
		container.setLayout(new FillLayout());
		container.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		final ExpandableComposite ec = formToolkit
				.createExpandableComposite(container,
						ExpandableComposite.TWISTIE
								| ExpandableComposite.TITLE_BAR
								| ExpandableComposite.EXPANDED);
		ec.setBackground(getBackground());
		ec.setText(sectionTitle);
		final Composite section = new Composite(ec, SWT.NONE);
		section.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		section.setLayout(new GridLayout(2, true));
		ec.setClient(section);
		return section;
	}

	private static Pair<String, String> transformVersionInfo(
			final VersionInfo versionInfo) {
		final Function<Collection<String>, String> toString = versions -> versions
				.stream()
				.collect(Collectors.joining(", ")); //$NON-NLS-1$
		return new Pair<>(toString.apply(versionInfo.getPlanProVersions()),
				toString.apply(versionInfo.getSignalbegriffeVersions()));
	}

	@Override
	public void update() {
		super.update();
		observableValues.forEach(SupplierObservableValue::calculate);
	}
}
