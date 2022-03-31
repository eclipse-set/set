/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.validation.modelloader;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.util.function.Consumer;

import javax.inject.Inject;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.set.basis.PlanProSchemaDir;
import org.eclipse.set.basis.ResourceLoader;
import org.eclipse.set.basis.constants.ValidationResult;
import org.eclipse.set.basis.files.ToolboxFile;
import org.eclipse.set.core.services.dialog.DialogService;
import org.eclipse.set.core.services.modelloader.ModelLoader;
import org.eclipse.set.core.services.validation.ValidationService;
import org.eclipse.set.feature.validation.Messages;
import org.eclipse.set.ppmodel.extensions.PlanProSchnittstelleExtensions;
import org.eclipse.swt.widgets.Shell;

import de.scheidtbachmann.planpro.model.model1902.PlanPro.PlanPro_Schnittstelle;

/**
 * Implementation of {@link ModelLoader}.
 * 
 * @author Schaefer
 */
public class ModelLoaderImpl implements ModelLoader {

	private static final PlanProSchemaDir<PlanPro_Schnittstelle> SCHEMA_DIR = new PlanProSchemaDir<>(
			PlanPro_Schnittstelle.class);

	@Inject
	private DialogService dialogService;

	@Inject
	private ValidationService validationService;

	@Inject
	@Translation
	Messages messages;

	@Override
	public ValidationResult loadModel(final ToolboxFile toolboxFile,
			final Consumer<PlanPro_Schnittstelle> storeModel, final Shell shell,
			final boolean ensureValid) {
		final ValidationResult result = new ValidationResult();
		final IRunnableWithProgress loadFileThread = monitor -> {
			monitor.beginTask(messages.ModelLoaderImpl_loadMsg,
					IProgressMonitor.UNKNOWN);
			final PlanPro_Schnittstelle schnittstelle = validationService
					.checkLoad(toolboxFile, new ResourceLoader() {

						@Override
						public Resource load(final Path location)
								throws IOException {
							toolboxFile.open();
							return toolboxFile.getResource();
						}
					}, resource -> PlanProSchnittstelleExtensions
							.readFrom(resource), result);
			validationService.xsdValidation(toolboxFile, SCHEMA_DIR, result);
			validationService.emfValidation(schnittstelle, result);
			validationService.customValidation(toolboxFile, result);
			storeModel.accept(schnittstelle);
		};
		try {
			new ProgressMonitorDialog(shell).run(true, false, loadFileThread);
		} catch (final InvocationTargetException e) {
			dialogService.error(shell, e);
			return result;
		} catch (final InterruptedException e) {
			return result;
		}
		return result;
	}
}
