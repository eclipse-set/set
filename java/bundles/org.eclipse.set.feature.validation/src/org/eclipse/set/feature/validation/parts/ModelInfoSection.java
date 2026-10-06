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

import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.set.feature.validation.Messages;
import org.eclipse.set.model.validationreport.ValidationReport;
import org.eclipse.set.model.validationreport.VersionInfo;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
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
public class ModelInfoSection {

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

	Messages messages;
	Composite parent;

	/**
	 * @param parent
	 *            the parent composite
	 * @param messages
	 *            the {@link Messages}
	 */
	public ModelInfoSection(final Composite parent, final Messages messages) {
		this.messages = messages;
		this.parent = parent;
	}

	protected void createModelInformationGroup(
			final ValidationReport validationReport) {
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

	protected void createFunctionalInformationenGroup(
			final ValidationReport validationReport) {
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

	private Composite createExpandedSecion(final String sectionTitle) {
		final Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new FillLayout());
		container.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		final FormToolkit formToolkit = new FormToolkit(parent.getDisplay());
		final ExpandableComposite ec = formToolkit
				.createExpandableComposite(container,
						ExpandableComposite.TWISTIE
								| ExpandableComposite.TITLE_BAR
								| ExpandableComposite.EXPANDED);
		ec.setBackground(parent.getBackground());
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
}
