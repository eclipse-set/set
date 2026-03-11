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
package org.eclipse.set.feature.projectdata.utils;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.basis.constants.Events;
import org.eclipse.set.basis.exceptions.UserAbortion;
import org.eclipse.set.basis.files.ToolboxFileRole;
import org.eclipse.set.core.fileservice.ToolboxIDResolver;
import org.eclipse.set.utils.events.EditingCompleted;
import org.eclipse.set.utils.events.ToolboxEvents;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;

/**
 * The import group, which contain {@link AbstractImportControl} and import
 * button
 * 
 * @author truong
 */
public abstract class AbstractImportGroup {
	protected ServiceProvider serviceProvider;
	protected IModelSession modelSession;
	protected Button importButton;

	protected IEventBroker broker;
	protected boolean isImportSuccess;

	/**
	 * @param serviceProvider
	 *            the {@link ServiceProvider}
	 * @param modelSession
	 *            the {@link IModelSession}
	 * @param broker
	 *            the {@link IEventBroker}
	 * 
	 */
	public AbstractImportGroup(final ServiceProvider serviceProvider,
			final IModelSession modelSession, final IEventBroker broker) {
		this.serviceProvider = serviceProvider;
		this.modelSession = modelSession;
		this.broker = broker;
	}

	/**
	 * @param parent
	 *            the parent
	 * @param shell
	 *            the shell
	 * @param groupName
	 *            the name of this group
	 * @return the group
	 */
	public static Group createGroup(final Composite parent, final Shell shell,
			final String groupName) {
		final Group group = new Group(parent, SWT.SHADOW_ETCHED_IN);
		group.setText(groupName);
		group.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		group.setLayout(new GridLayout());
		return group;
	}

	protected void createImportButton(final Composite parent,
			final String buttonName) {
		importButton = new Button(parent, SWT.NONE);
		importButton.setText(buttonName);
		importButton.setEnabled(false);
		importButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(final SelectionEvent e) {
				doImport(parent.getShell());
			}

			@Override
			public void widgetSelected(final SelectionEvent e) {
				widgetDefaultSelected(e);
			}
		});
	}

	protected abstract void createControl(final Composite parent,
			final Shell shell, final ToolboxFileRole role);

	protected abstract void doImport(Shell shell);

	protected abstract void updateImportButton();

	protected abstract void resetGroup();

	protected void setImportSuccess(final boolean success) {
		isImportSuccess = success;
	}

	protected void updateModelAfterImport(final Shell shell)
			throws UserAbortion {
		ToolboxIDResolver
				.resolveIDReferences(modelSession.getPlanProSchnittstelle());
		modelSession.save(shell);
		serviceProvider.dialogService.reportImported(shell);
		resetGroup();
		ToolboxEvents.send(broker, new EditingCompleted());
		broker.send(Events.MODEL_CHANGED, modelSession);
	}
}
