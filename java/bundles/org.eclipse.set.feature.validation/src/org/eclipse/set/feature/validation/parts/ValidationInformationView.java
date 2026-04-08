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

import org.eclipse.set.model.validationreport.ValidationReport;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;

/**
 * 
 */
public class ValidationInformationView {

	private final Composite parent;
	private final ValidationReport validationReport;
	private static final String MODEL_INFOMATIONEN = "Modellinformationen";

	public ValidationInformationView(final Composite parent,
			final ValidationReport validationReport) {
		this.parent = parent;
		this.validationReport = validationReport;
		createView();
	}

	private void createView() {
		final ExpandBar expandBar = new ExpandBar(parent, SWT.None);
		expandBar.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
	}

	private void createModelInformation(final ExpandBar expandBar) {
		final ExpandItem expandItem = new ExpandItem(expandBar, SWT.None);
		expandItem.setText(MODEL_INFOMATIONEN);

	}

}
