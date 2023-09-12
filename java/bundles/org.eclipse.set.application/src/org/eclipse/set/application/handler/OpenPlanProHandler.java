/**
 * Copyright (c) 2015 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.application.handler;

import java.nio.file.Path;

import javax.inject.Inject;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.set.application.Messages;
import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.basis.constants.ValidationResult;
import org.eclipse.set.basis.constants.ValidationResult.Outcome;
import org.eclipse.set.utils.ToolboxConfiguration;
import org.eclipse.set.utils.handler.AbstractOpenHandler;
import org.eclipse.swt.widgets.Shell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This handler can present a file dialog, assigns the selected file to a new
 * model session and distributes the session via the application context.
 * 
 * @author Schaefer
 */
public class OpenPlanProHandler extends AbstractOpenHandler {

	static final Logger logger = LoggerFactory
			.getLogger(OpenPlanProHandler.class);

	@Inject
	@Translation
	private org.eclipse.set.utils.Messages utilMessages;

	@Inject
	IEventBroker eventBroker;

	@Inject
	@Translation
	Messages messages;

	@Inject
	EPartService partService;

	@Override
	protected Path chooseFile(final Shell shell) {
		return getDialogService()
				.openFileDialog(shell,
						getDialogService().getPlanProFileFilters())
				.orElse(null);
	}

	@Override
	protected IModelSession createSession(final Path path) {
		final IModelSession session = sessionService.loadModelSession(path);
		session.init();
		return session;
	}

	@Override
	protected String getErrorCauseMessage() {
		return messages.Common_InfoErrorCause;
	}

	@Override
	protected String getErrorMessage() {
		return messages.OpenPlanProHandler_LoadingPlanProModelFileErrorMessage;
	}

	@Override
	protected String getErrorTitleMessage() {
		return messages.OpenPlanProHandler_LoadingPlanProModelFileErrorTitle;
	}

	@Override
	protected String getTaskMessage() {
		return messages.OpenPlanProHandler_LoadingPlanProModelFile;
	}

	@Override
	protected IModelSession validation(final IModelSession modelSession,
			final Shell shell, final Path path) {
		if (modelSession == null) {
			return null;
		}

		final Outcome validationOutcome = modelSession
				.getValidationsOutcome(ValidationResult::getOutcome);
		if (validationOutcome == Outcome.VALID
				|| validationOutcome == Outcome.NOT_SUPPORTED) {
			return modelSession;
		}

		final Outcome xsdOutcome = modelSession
				.getValidationsOutcome(ValidationResult::getXsdOutcome);
		final Outcome emfOutcome = modelSession
				.getValidationsOutcome(ValidationResult::getEmfOutcome);
		if ((xsdOutcome == Outcome.VALID || xsdOutcome == Outcome.NOT_SUPPORTED)
				&& (emfOutcome == Outcome.VALID
						|| emfOutcome == Outcome.NOT_SUPPORTED)) {
			// Invalid, but only due to custom validations
			// as a result, consider this an incomplete file

			// Skip user questioning in development mode
			if (ToolboxConfiguration.isDevelopmentMode()) {
				logger.info("user dialog for loading of invalid file skipped" //$NON-NLS-1$
						+ " due to development mode being enabled"); //$NON-NLS-1$
				return modelSession;
			}

			if (!getDialogService().loadIncompleteModel(shell,
					path.toString())) {
				modelSession.close();
				return null;
			}
			return modelSession;
		}

		// Invalid file
		if (!getDialogService().loadInvalidModel(shell, path.toString())) {
			modelSession.close();
			return null;
		}
		return modelSession;
	}
}
