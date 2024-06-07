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
package org.eclipse.set.feature.projectdata.ppimport;

import java.nio.file.Path;
import java.util.function.Consumer;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.basis.constants.Events;
import org.eclipse.set.basis.exceptions.UserAbortion;
import org.eclipse.set.basis.files.PlanProFileResource;
import org.eclipse.set.basis.files.ToolboxFileRole;
import org.eclipse.set.core.services.modelloader.ModelLoader.ModelContents;
import org.eclipse.set.feature.projectdata.utils.AbstractImportControl;
import org.eclipse.set.feature.projectdata.utils.ServiceProvider;
import org.eclipse.set.feature.validation.session.ModelSession;
import org.eclipse.set.model.planpro.Layoutinformationen.DocumentRoot;
import org.eclipse.set.model.planpro.Layoutinformationen.LayoutinformationenFactory;
import org.eclipse.set.model.planpro.Layoutinformationen.LayoutinformationenPackage;
import org.eclipse.set.model.planpro.Layoutinformationen.PlanPro_Layoutinfo;
import org.eclipse.set.utils.events.EditingCompleted;
import org.eclipse.set.utils.events.ToolboxEvents;
import org.eclipse.set.utils.widgets.FileField;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * Import control group for layout information from another project
 * 
 * @author Truong
 */
public class ImportLayoutControlGroup extends AbstractImportControl {

	private FileField fileField;
	private PlanPro_Layoutinfo layoutToImport;
	private Button importButton;
	private final IEventBroker broker;

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
		super(serviceProvider, modelSession);
		this.broker = broker;
	}

	@Override
	public boolean isValid() {
		return layoutToImport != null;
	}

	@Override
	public void resetControl() {
		layoutToImport = null;
		fileField.getText().setText(""); //$NON-NLS-1$
		importButton.setEnabled(false);
		setImported(false);
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public void doImport(final Shell shell) {
		final PlanPro_Layoutinfo sourceLayout = modelSession
				.getLayoutInformation();
		if (layoutToImport == null) {
			return;
		}
		if (sourceLayout != null && !serviceProvider.dialogService
				.confirmOverwriteOperationalData(shell)) {
			return;
		}
		// Create layoutinformation document root, when current project doesn't
		// contains layout information
		if (modelSession.getToolboxFile().getLayoutDocumentRoot() == null) {
			createDocumentRoot();
		}
		try {
			final Command importCommand = SetCommand.create(
					modelSession.getEditingDomain(),
					modelSession.getToolboxFile().getLayoutDocumentRoot(),
					LayoutinformationenPackage.eINSTANCE
							.getDocumentRoot_PlanProLayoutinfo(),
					layoutToImport);
			modelSession.getEditingDomain().getCommandStack()
					.execute(importCommand);
			modelSession.save(shell);
			serviceProvider.dialogService.reportImported(shell);
			ToolboxEvents.send(broker, new EditingCompleted());
			broker.send(Events.LAYOUT_CHANGED,
					modelSession.getLayoutInformation());
			resetControl();
			setImported(true);
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

	private void createDocumentRoot() {
		final EditingDomain editingDomain = modelSession.getEditingDomain();
		final DocumentRoot documentRoot = LayoutinformationenFactory.eINSTANCE
				.createDocumentRoot();
		final PlanProFileResource layoutResource = new PlanProFileResource(
				URI.createURI(LayoutinformationenPackage.eNS_URI));
		editingDomain.getResourceSet().getResources().add(layoutResource);
		layoutResource.eAdapters()
				.add(new AdapterFactoryEditingDomain.EditingDomainProvider(
						editingDomain));
		layoutResource.getContents().add(documentRoot);
		modelSession.getToolboxFile().setResourcePath(layoutResource,
				modelSession.getToolboxFile().getLayoutPath());
	}

	@Override
	public void createControl(final Composite parent, final Shell shell,
			final ToolboxFileRole role) {
		final Group group = new Group(parent, SWT.SHADOW_ETCHED_IN);
		group.setText(
				serviceProvider.messages.PlanProImportPart_importLayoutGroup);
		group.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		group.setLayout(new GridLayout());
		createFileFieldControl(group, shell, role);
		createImportButton(group);
	}

	private void createFileFieldControl(final Composite parent,
			final Shell shell, final ToolboxFileRole role) {
		fileField = new FileField(parent,
				serviceProvider.dialogService.getPlanProFileFilters(),
				serviceProvider.dialogService);
		fileField.getComposite()
				.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		fileField.getButton().setText(
				serviceProvider.messages.PlanProImportPart_fileFieldButtonText);
		fileField.setPathValidation(path -> validatePath(path, t -> {
			if (t.layoutInfo() == null) {
				serviceProvider.dialogService.openInformation(shell,
						serviceProvider.messages.PlanProImportPart_Notcontains_Layout_Title,
						serviceProvider.messages.PlanProImportPart_Notcontains_Layout_Msg);
				return;
			}
			layoutToImport = t.layoutInfo();
		}, shell, role));
		fileField.getText().addModifyListener(selectedFileHandle());
	}

	@Override
	protected Boolean validatePath(final Path path,
			final Consumer<ModelContents> storeModel, final Shell shell,
			final ToolboxFileRole role) {
		final boolean validatePath = super.validatePath(path, storeModel, shell,
				role).booleanValue();
		return Boolean.valueOf(layoutToImport != null && validatePath);
	}

	private void createImportButton(final Composite parent) {
		importButton = new Button(parent, SWT.NONE);
		importButton.setText(
				serviceProvider.messages.PLanpRoImportPart_importLayoutButton);
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

	@Override
	protected ModifyListener selectedFileHandle() {
		return e -> importButton.setEnabled(layoutToImport != null
				&& e.getSource() instanceof final Text text
				&& !text.getText().isBlank());
	}
}
