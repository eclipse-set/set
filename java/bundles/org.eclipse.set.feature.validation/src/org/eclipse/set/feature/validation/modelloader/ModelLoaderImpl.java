/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.validation.modelloader;

import java.nio.file.Path;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.set.basis.constants.ValidationResult;
import org.eclipse.set.basis.files.ToolboxFile;
import org.eclipse.set.core.services.dialog.DialogService;
import org.eclipse.set.core.services.modelloader.ModelLoader;
import org.eclipse.set.core.services.validation.ValidationService;
import org.eclipse.set.feature.validation.Messages;
import org.eclipse.set.model.planpro.Layoutinformationen.PlanPro_Layoutinfo;
import org.eclipse.set.model.planpro.PlanPro.PlanPro_Schnittstelle;
import org.eclipse.set.ppmodel.extensions.PlanProSchnittstelleExtensions;
import org.eclipse.swt.widgets.Shell;

import jakarta.inject.Inject;

/**
 * Implementation of {@link ModelLoader}.
 * 
 * @author Schaefer
 */
public class ModelLoaderImpl implements ModelLoader {
	@Inject
	private DialogService dialogService;

	@Inject
	private ValidationService validationService;

	@Inject
	@Translation
	Messages messages;

	@Override
	public PlanPro_Schnittstelle loadModel(final ToolboxFile toolboxFile,
			final Consumer<ValidationResult> storeValidationResult) {
		final PlanPro_Schnittstelle planProModel = readPlanProModel(toolboxFile,
				storeValidationResult);
		readLayoutInformationen(toolboxFile, storeValidationResult);
		return planProModel;
	}

	@Override
	public PlanPro_Schnittstelle loadModelSync(final ToolboxFile toolboxFile,
			final Consumer<ValidationResult> storeValidationResult,
			final Shell shell) {
		try {
			return dialogService.showProgressUISync(shell,
					messages.ModelLoaderImpl_loadMsg,
					() -> loadModel(toolboxFile, storeValidationResult));
		} catch (final ExecutionException e) {
			dialogService.error(shell, e);
		} catch (final InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		return null;
	}

	private PlanPro_Schnittstelle readPlanProModel(
			final ToolboxFile toolboxFile,
			final Consumer<ValidationResult> storeValidationResult) {
		ValidationResult validationResult = new ValidationResult(
				PlanPro_Schnittstelle.class);
		final PlanPro_Schnittstelle schnitStelle = validationService
				.checkLoad(toolboxFile, path -> {
					toolboxFile.openModel();
					return toolboxFile.getPlanProResource();
				}, PlanProSchnittstelleExtensions::readFrom, validationResult);
		validationResult = validationService.validateSource(validationResult,
				toolboxFile);
		storeValidationResult.accept(validationResult);
		return schnitStelle;
	}

	private void readLayoutInformationen(final ToolboxFile toolboxFile,
			final Consumer<ValidationResult> storeValidationResult) {
		final Path layoutPath = toolboxFile.getLayoutPath();
		if (layoutPath == null || !layoutPath.toFile().exists()) {
			return;
		}
		ValidationResult validationResult = new ValidationResult(
				PlanPro_Layoutinfo.class);
		validationService.checkLoad(toolboxFile, path -> {
			toolboxFile.openLayout();
			return toolboxFile.getLayoutResource();
		}, PlanProSchnittstelleExtensions::readFrom, validationResult);
		validationResult = validationService.validateSource(
				new ValidationResult(PlanPro_Layoutinfo.class), toolboxFile);
		storeValidationResult.accept(validationResult);

	}
}
