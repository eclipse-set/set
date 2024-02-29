/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.application.handler;

import java.nio.file.Path;
import java.util.function.BiPredicate;

import jakarta.inject.Inject;

import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.set.application.Messages;
import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.basis.ProjectInitializationData;
import org.eclipse.set.basis.constants.ToolboxConstants;
import org.eclipse.set.core.services.part.ToolboxPartService;
import org.eclipse.set.utils.handler.AbstractNewHandler;
import org.eclipse.swt.widgets.Shell;

/**
 * creates a new project
 * 
 * @author Rumpf/Schaefer
 */
public class NewProjectHandler
		extends AbstractNewHandler<ProjectInitializationData> {

	@Inject
	@Translation
	Messages messages;

	@Override
	public IModelSession createNewSession(
			final ProjectInitializationData initializationData) {
		final IModelSession newSession = sessionService
				.initModelSession(initializationData);
		newSession.init();
		return newSession;
	}

	@Override
	public String getErrorCauseMessage() {
		return messages.Common_InfoErrorCause;
	}

	@Override
	public String getErrorMessage() {
		return messages.NewProjectHandler_SavingPlanProModelFileErrorMessage;
	}

	@Override
	public String getErrorTitleMessage() {
		return messages.NewProjectHandler_SavingPlanProModelFileErrorTitle;
	}

	@Override
	public String getSuccessMessage(final Path file) {
		return String.format(messages.NewProjectHandler_SuccessMessage, file);
	}

	@Override
	public String getSuccessTitleMessage() {
		return messages.NewProjectHandler_SuccessMessage_Title;
	}

	@Override
	public String getTaskMessage() {
		return messages.NewProjectHandler_CreatingPlanProModelFile;
	}

	@Override
	public int openDialog(final Shell shell, final MApplication application) {
		return getDialogService().projectInitialization(shell,
				new BiPredicate<Shell, ProjectInitializationData>() {
					@Override
					public boolean test(final Shell parent,
							final ProjectInitializationData initializationData) {
						return save(initializationData, parent, application);
					}
				});
	}

	@Override
	public void showInitPart(final ToolboxPartService toolboxViewService) {
		toolboxViewService.showPart(ToolboxConstants.PROJECT_PART_ID);
	}
}
