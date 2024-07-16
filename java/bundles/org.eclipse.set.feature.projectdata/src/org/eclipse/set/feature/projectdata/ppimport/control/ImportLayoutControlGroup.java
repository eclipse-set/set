/**
 * Copyright (c) 2024 DB InfraGO AG and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 */
package org.eclipse.set.feature.projectdata.ppimport.control;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.basis.files.ToolboxFileRole;
import org.eclipse.set.feature.projectdata.utils.AbstractImportGroup;
import org.eclipse.set.feature.projectdata.utils.ServiceProvider;
import org.eclipse.set.feature.validation.session.ModelSession;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;

/**
 * Import control group for layout information from another project
 * 
 * @author Truong
 */
public class ImportLayoutControlGroup extends AbstractImportGroup {
	private ImportLayoutControl importControl;

	/**
	 * @param serviceProvider
	 *            the {@link ServiceProvider}
	 * @param modelSession
	 *            the {@link ModelSession}
	 * @param broker
	 *            the {@link IEventBroker}}
	 */
	public ImportLayoutControlGroup(final ServiceProvider serviceProvider,
			final IModelSession modelSession, final IEventBroker broker) {
		super(serviceProvider, modelSession, broker);
	}

	@Override
	public void doImport(final Shell shell) {
		importControl.doImport(shell);
	}

	@Override
	public void createControl(final Composite parent, final Shell shell,
			final ToolboxFileRole role) {
		final Group group = createGroup(parent, shell,
				serviceProvider.messages.PlanProImportPart_importLayoutGroup);
		importControl = new ImportLayoutControl(serviceProvider, modelSession,
				broker, this::updateImportButton);
		importControl.createControl(group, shell, role);
		createImportButton(group,
				serviceProvider.messages.PLanpRoImportPart_importLayoutButton);
	}

	@Override
	protected void updateImportButton() {
		importButton.setEnabled(importControl.isValid());
	}

	@Override
	protected void resetGroup() {
		importControl.resetControl();
		importButton.setEnabled(false);
	}
}
