/**
 * Copyright (c) 2015 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.handler;

import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;

import jakarta.inject.Inject;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.menu.MDirectMenuItem;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.basis.Wrapper;
import org.eclipse.set.basis.constants.Events;
import org.eclipse.set.core.services.dialog.DialogService;
import org.eclipse.set.core.services.part.ToolboxPartService;
import org.eclipse.set.core.services.session.SessionService;
import org.eclipse.set.core.services.update.ModelUpdateService;
import org.eclipse.set.utils.Messages;
import org.eclipse.swt.widgets.Shell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Common functions for loading PlanPro files.
 * 
 * @author Schaefer
 */
public abstract class AbstractOpenHandler extends AbstractHandler {

	static final Logger logger = LoggerFactory
			.getLogger(AbstractOpenHandler.class);

	@Inject
	private DialogService dialogService;

	private MDirectMenuItem menuItem;

	@Inject
	private IEventBroker eventBroker;

	@Inject
	@Translation
	private Messages messages;

	@Inject
	@Optional
	private IModelSession oldModelSession;

	@Inject
	private ToolboxPartService toolboxViewService;

	@Inject
	protected SessionService sessionService;

	@Inject
	ModelUpdateService modelUpdateService;

	/**
	 * @return the dialog service
	 */
	public DialogService getDialogService() {
		return dialogService;
	}

	/**
	 * @return the menu item
	 */
	public MDirectMenuItem getMenuItem() {
		return menuItem;
	}

	private void loadSession(final Shell shell,
			final MApplication application) {

		// unsaved data
		if (oldModelSession != null && oldModelSession.isDirty()) {
			if (!dialogService.confirmCloseUnsaved(shell)) {
				return;
			}
		}

		// close the current session
		if (oldModelSession != null) {
			oldModelSession.close();
		}

		// choose file if not present
		final Path path = chooseFile(shell);
		if (path == null) {
			return;
		}

		final Wrapper<IModelSession> newSessionWrapper = new Wrapper<>();
		newSessionWrapper.setValue(null);

		// we create a Runnable with progress for loading the planpro file
		final IRunnableWithProgress loadFileThread = new IRunnableWithProgress() {
			@Override
			public void run(final IProgressMonitor monitor)
					throws InvocationTargetException, InterruptedException {
				// start a single task with unknown timeframe
				monitor.beginTask(getTaskMessage(), IProgressMonitor.UNKNOWN);

				// create the session
				logger.trace("Loading session..."); //$NON-NLS-1$
				newSessionWrapper.setValue(createSession(path));
				newSessionWrapper.getValue()
						.getModels()
						.forEach(m -> modelUpdateService.add(m));
				// stop progress
				monitor.done();
				logger.trace("Loading session done."); //$NON-NLS-1$
			}
		};

		// we start our new defined thread with a progress dialog
		try {
			new ProgressMonitorDialog(shell).run(true, false, loadFileThread);
		} catch (final InvocationTargetException ex) {
			final Throwable cause = ex.getCause();
			logger.error("Validation error", cause); //$NON-NLS-1$
			reportError(shell, ex, dialogService, getErrorTitleMessage(),
					getErrorMessage(), getErrorCauseMessage());
			newSessionWrapper.setValue(null);
		} catch (final InterruptedException e) {
			// we ignore the cancellation of the loading thread
		}

		// test validation result
		newSessionWrapper.setValue(
				validation(newSessionWrapper.getValue(), shell, path));

		// success
		if (newSessionWrapper.getValue() != null) {
			success(newSessionWrapper.getValue(), application);
		}

		logger.trace("Execute done."); //$NON-NLS-1$
	}

	protected abstract Path chooseFile(Shell shell);

	protected abstract IModelSession createSession(Path path);

	@Execute
	protected void execute(final Shell shell, final MApplication application,
			@Optional final MDirectMenuItem item) {
		this.menuItem = item;
		loadSession(shell, application);
	}

	protected abstract String getErrorCauseMessage();

	protected abstract String getErrorMessage();

	protected abstract String getErrorTitleMessage();

	protected abstract String getTaskMessage();

	protected void success(final IModelSession modelSession,
			final MApplication application) {
		// set the new, global model session
		application.getContext().set(IModelSession.class, modelSession);

		eventBroker.send(Events.MODEL_CHANGED,
				modelSession.getPlanProSchnittstelle());

		// reset the default part
		toolboxViewService.showDefaultPart(null);

		// show the default part to the user
		toolboxViewService.showDefaultPart(modelSession);
	}

	protected abstract IModelSession validation(IModelSession modelSession,
			Shell shell, Path path);
}
