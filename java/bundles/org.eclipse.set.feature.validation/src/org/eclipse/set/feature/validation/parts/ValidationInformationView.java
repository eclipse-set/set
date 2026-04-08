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

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.set.feature.validation.Messages;
import org.eclipse.set.model.validationreport.FileInfo;
import org.eclipse.set.model.validationreport.ValidationReport;
import org.eclipse.set.model.validationreport.ValidationreportPackage;
import org.eclipse.set.model.validationreport.provider.FileInfoItemProvider;
import org.eclipse.set.model.validationreport.provider.ValidationreportItemProviderAdapterFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * 
 */
public class ValidationInformationView {

	private final Composite parent;
	private final ValidationReport validationReport;
	private final Messages messages;
	private final ValidationreportItemProviderAdapterFactory providerAdapterFactory;
	private static final String MODEL_INFOMATIONEN = "Modellinformationen";

	public ValidationInformationView(final Composite parent,
			final ValidationReport validationReport, final Messages messages) {
		this.parent = parent;
		this.validationReport = validationReport;
		this.messages = messages;
		this.providerAdapterFactory = new ValidationreportItemProviderAdapterFactory();
		createView();
	}

	private void createView() {
		final ExpandBar expandBar = new ExpandBar(parent, SWT.NONE);
		expandBar.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		createModelInformation(expandBar);
	}

	private void createModelInformation(final ExpandBar expandBar) {

		final Composite composite = new Composite(expandBar, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		final GridLayout gridLayout = new GridLayout(2, true);

		composite.setLayout(gridLayout);
		final ExpandItem expandItem = new ExpandItem(expandBar, SWT.NONE);
		createLoadedFileInfoGroup(composite);
		expandItem.setText(MODEL_INFOMATIONEN);
	}

	private Group createLoadedFileInfoGroup(final Composite composite) {
		final Group group = new Group(composite, SWT.NONE);
		group.setText(messages.ValidationReport_GeladeneDatei);
		group.setLayout(new GridLayout(2, true));
		final Adapter adapter = providerAdapterFactory.createFileInfoAdapter();
		if (adapter instanceof final FileInfoItemProvider fileInfoProvider) {
			final FileInfo fileInfo = validationReport.getFileInfo();
			final IItemPropertyDescriptor fileNameDescriptor = fileInfoProvider
					.getPropertyDescriptor(fileInfo,
							ValidationreportPackage.eINSTANCE
									.getFileInfo_FileName());
			final Label label = new Label(group, SWT.BEGINNING);
			label.setText(fileNameDescriptor.getDisplayName(fileInfo));

			final Text text = new Text(group, SWT.END);
			text.setEditable(false);
			text.setText(fileInfo.getFileName());
		}
		return group;
	}

}
