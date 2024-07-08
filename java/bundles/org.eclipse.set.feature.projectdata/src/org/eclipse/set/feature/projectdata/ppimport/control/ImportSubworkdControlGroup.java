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
import org.eclipse.set.basis.exceptions.UserAbortion;
import org.eclipse.set.basis.files.ToolboxFileRole;
import org.eclipse.set.feature.projectdata.ppimport.control.ImportModelControl.ImportTarget;
import org.eclipse.set.feature.projectdata.utils.AbstractImportGroup;
import org.eclipse.set.feature.projectdata.utils.ServiceProvider;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;

/**
 * 
 */
public class ImportSubworkdControlGroup extends AbstractImportGroup {

	private ImportModelControl importControl;

	/**
	 * @param serviceProvider
	 *            the {@link ServiceProvider}
	 * @param modelSession
	 *            the {@link IModelSession}
	 * @param broker
	 *            the {@link IEventBroker}
	 * 
	 */
	public ImportSubworkdControlGroup(final ServiceProvider serviceProvider,
			final IModelSession modelSession, final IEventBroker broker) {
		super(serviceProvider, modelSession, broker);
	}

	@Override
	public void doImport(final Shell shell) {
		importControl.doImport(shell);
		if (importControl.isImported()) {
			try {
				updateModelAfterImport(shell);
			} catch (IllegalArgumentException | NullPointerException e) {
				throw new RuntimeException(e);
			} catch (final UserAbortion e) {
				// We ignore an user abortion
			} finally {
				if (modelSession.isDirty()) {
					modelSession.revert();
				}
			}
		}
	}

	@Override
	public void createControl(final Composite parent, final Shell shell,
			final ToolboxFileRole role) {
		final Group group = createGroup(parent, shell,
				serviceProvider.messages.PlanProImportPart_importGroup);
		importControl = new ImportModelControl(serviceProvider, modelSession,
				ImportTarget.ALL,
				() -> importButton.setEnabled(importControl.isValid()));
		importControl.createControl(group, shell, role);
		importControl.getComboField().getContainerCombo().dispose();
		importControl.getComboField().setEnabled(true);
		createImportButton(group,
				serviceProvider.messages.PlanpRoImportPart_importModelButton);
	}

	@Override
	protected void updateImportButton() {
		importButton.setEnabled(importControl.isEnabled());
	}

	@Override
	protected void resetGroup() {
		importControl.resetControl();
	}

}
