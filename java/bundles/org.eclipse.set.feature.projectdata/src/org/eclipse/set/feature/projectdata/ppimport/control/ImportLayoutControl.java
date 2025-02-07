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
import org.eclipse.set.model.planpro.Layoutinformationen.DocumentRoot;
import org.eclipse.set.model.planpro.Layoutinformationen.LayoutinformationenFactory;
import org.eclipse.set.model.planpro.Layoutinformationen.LayoutinformationenPackage;
import org.eclipse.set.model.planpro.Layoutinformationen.PlanPro_Layoutinfo;
import org.eclipse.set.utils.events.EditingCompleted;
import org.eclipse.set.utils.events.ToolboxEvents;
import org.eclipse.set.utils.widgets.FileField;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * 
 */
public class ImportLayoutControl extends AbstractImportControl {
	private FileField fileField;
	private PlanPro_Layoutinfo layoutToImport;
	private final Runnable handleControlChange;
	private final IEventBroker broker;

	/**
	 * @param serviceProvider
	 *            {@link ServiceProvider}
	 * @param modelSession
	 *            the session
	 * @param broker
	 *            the {@link IEventBroker}
	 * @param handleControlChange
	 *            call back when control change
	 */
	public ImportLayoutControl(final ServiceProvider serviceProvider,
			final IModelSession modelSession, final IEventBroker broker,
			final Runnable handleControlChange) {
		super(serviceProvider, modelSession);
		this.handleControlChange = handleControlChange;
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
			modelSession.getEditingDomain()
					.getCommandStack()
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

	@Override
	public void createControl(final Composite parent, final Shell shell,
			final ToolboxFileRole role) {
		fileField = new FileField(parent,
				serviceProvider.dialogService.getPlanProFileFilters(),
				serviceProvider.dialogService);
		fileField.getComposite()
				.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		fileField.getButton()
				.setText(
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

	@Override
	protected ModifyListener selectedFileHandle() {
		return e -> {
			if (e.getSource() instanceof final Text text
					&& !text.getText().isBlank()) {
				handleControlChange.run();
			}
		};
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
		modelSession.getToolboxFile()
				.setResourcePath(layoutResource,
						modelSession.getToolboxFile().getLayoutPath());
	}

}
