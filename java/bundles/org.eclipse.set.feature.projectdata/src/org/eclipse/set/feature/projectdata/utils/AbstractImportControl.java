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

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.basis.constants.ValidationResult;
import org.eclipse.set.basis.constants.ValidationResult.FileValidateState;
import org.eclipse.set.basis.files.ToolboxFile;
import org.eclipse.set.basis.files.ToolboxFileAC;
import org.eclipse.set.basis.files.ToolboxFileRole;
import org.eclipse.set.core.services.modelloader.ModelLoader.ModelContents;
import org.eclipse.set.feature.validation.utils.ValidationOutcome;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

/**
 * 
 */
public abstract class AbstractImportControl {
	protected IModelSession modelSession;
	protected ServiceProvider serviceProvider;
	private boolean imported;

	protected ModelContents contentsToImport;

	/**
	 * 
	 */
	protected AbstractImportControl(final ServiceProvider serviceProvider,
			final IModelSession modelSession) {
		this.modelSession = modelSession;
		this.serviceProvider = serviceProvider;
		this.imported = false;
	}

	/**
	 * @return true, if the control valid
	 */
	public abstract boolean isValid();

	/**
	 * Rest control to default
	 */
	public abstract void resetControl();

	/**
	 * @return true, if this control is enable
	 */
	public abstract boolean isEnabled();

	/**
	 * Do import data
	 * 
	 * @param shell
	 *            the {@link Shell}
	 */
	public abstract void doImport(final Shell shell);

	/**
	 * @param parent
	 *            the parat
	 * @param shell
	 *            the shell
	 * @param role
	 *            the {@link ToolboxFileRole}
	 */
	public abstract void createControl(final Composite parent,
			final Shell shell, final ToolboxFileRole role);

	protected Boolean validatePath(final Path path,
			final Consumer<ModelContents> storeModel, final Shell shell,
			final ToolboxFileRole role) {
		try (ToolboxFileAC toolboxFile = serviceProvider.fileService
				.loadAC(path, role)) {
			toolboxFile.get().setTemporaryDirectory(modelSession.getTempDir());
			return validatePath(toolboxFile.get(), storeModel, shell);
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean validatePath(final ToolboxFile toolboxFile,
			final Consumer<ModelContents> storeModel, final Shell shell) {
		final List<ValidationResult> validationResults = new ArrayList<>();

		final ModelContents model = serviceProvider.modelLoader
				.loadModelSync(toolboxFile, validationResults::add, shell);

		final FileValidateState fileValidateState = ValidationOutcome
				.getFileValidateState(validationResults);
		boolean isFileValid = false;
		switch (fileValidateState) {
		case VALID: {
			isFileValid = true;
			break;
		}
		case INCOMPLETE: {
			serviceProvider.dialogService.openInformation(shell,
					serviceProvider.messages.PlanProImportPart_incompletePlanProFile,
					String.format(
							serviceProvider.messages.PlanProImportPart_incompletePlanProFileMessage,
							toolboxFile.getPath()));
			isFileValid = true;
			break;
		}
		case INVALID: {
			// XSD-invalid file, unable to load
			isFileValid = serviceProvider.dialogService.loadInvalidModel(shell,
					toolboxFile.getPath().toString());
			break;
		}
		default:
			isFileValid = false;
			break;
		}
		if (isFileValid) {
			storeModel.accept(model);
		}
		return Boolean.valueOf(isFileValid);
	}

	protected abstract ModifyListener selectedFileHandle();

	/**
	 * @param imported
	 *            set imported status
	 */
	public void setImported(final boolean imported) {
		this.imported = imported;
	}

	/**
	 * @return true, if data is imported
	 */
	public boolean isImported() {
		return imported;
	}

}
