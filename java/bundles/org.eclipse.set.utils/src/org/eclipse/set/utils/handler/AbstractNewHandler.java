/**
 * Copyright (c) 2019 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.handler;

import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.util.Date;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.window.Window;
import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.basis.InitializationData;
import org.eclipse.set.basis.constants.Events;
import org.eclipse.set.basis.exceptions.UserAbortion;
import org.eclipse.set.core.services.cache.CacheService;
import org.eclipse.set.core.services.dialog.DialogService;
import org.eclipse.set.core.services.part.ToolboxPartService;
import org.eclipse.set.core.services.session.SessionService;
import org.eclipse.set.core.services.update.ModelUpdateService;
import org.eclipse.set.utils.Messages;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.inject.Inject;

/**
 * Common functions for new PlanPro files.
 * 
 * @param <T>
 *            type of the initialization data
 * 
 * @author Rumpf/Schaefer
 */
public abstract class AbstractNewHandler<T extends InitializationData>
		extends AbstractHandler {

	private static class SaveFailed extends Exception {
		public SaveFailed() {
			//
		}
	}

	static final Logger logger = LoggerFactory
			.getLogger(AbstractNewHandler.class);

	@Inject
	protected SessionService sessionService;

	@Inject
	CacheService cacheService;

	@Inject
	DialogService dialogService;

	@Inject
	IEventBroker eventBroker;

	@Inject
	@Translation
	Messages messages;

	@Inject
	@Optional
	IModelSession modelSession;

	@Inject
	ModelUpdateService modelUpdateService;

	IModelSession newSession;

	/**
	 * @param initializationData
	 *            the initialization data
	 * 
	 * @return the new session
	 */
	public abstract IModelSession createNewSession(final T initializationData);

	/**
	 * @return the error cause message
	 */
	public abstract String getErrorCauseMessage();

	/**
	 * @return the error message
	 */
	public abstract String getErrorMessage();

	/**
	 * @return the error title message
	 */
	public abstract String getErrorTitleMessage();

	/**
	 * @param file
	 *            the location of the new file
	 * 
	 * @return the message for successfully creating the given file
	 */
	public abstract String getSuccessMessage(Path file);

	/**
	 * @return the title message for the success dialog
	 */
	public abstract String getSuccessTitleMessage();

	/**
	 * @return the task message
	 */
	public abstract String getTaskMessage();

	/**
	 * Open initialization dialog.
	 * 
	 * @param shell
	 *            the shell
	 * @param application
	 *            the application
	 * 
	 * @return the result
	 */
	public abstract int openDialog(Shell shell, MApplication application);

	/**
	 * @param initializationData
	 *            the initialization data
	 * @param shell
	 *            the shell
	 * @param application
	 *            the application
	 * 
	 * @return the result
	 */
	public boolean save(final T initializationData, final Shell shell,
			final MApplication application) {
		initializationData.setCreationDate(new Date());
		// we create a Runnable with progress for loading the planpro file
		final IRunnableWithProgress loadFileThread = new IRunnableWithProgress() {
			@Override
			public void run(final IProgressMonitor monitor)
					throws InvocationTargetException, InterruptedException {
				// start a single task with unknown timeframe
				monitor.beginTask(getTaskMessage(), IProgressMonitor.UNKNOWN);

				// this is the actual loading of the file
				logger.trace("Loading session..."); //$NON-NLS-1$
				try {
					newSession = createNewSession(initializationData);

					final boolean saved = newSession.saveNew(shell);
					if (!saved) {
						throw new SaveFailed();
					}
					newSession.refreshValidation();
					Display.getDefault().asyncExec(new Runnable() {
						@Override
						public void run() {
							dialogService.openInformation(shell,
									getSuccessTitleMessage(),
									getSuccessMessage(
											newSession.getToolboxFile()
													.getPath()));
						}
					});
					newSession.getModels()
							.forEach(m -> modelUpdateService.add(m));
				} catch (final UserAbortion | SaveFailed e) {
					throw new InvocationTargetException(e);
				}

				// stop progress
				monitor.done();
				logger.trace("Loading session done."); //$NON-NLS-1$
			}
		};

		// we start our new defined thread with a progress dialog
		try {
			new ProgressMonitorDialog(shell).run(true, false, loadFileThread);

			if (newSession != null) {
				// set the new, global model session
				application.getContext().set(IModelSession.class, newSession);
				eventBroker.send(Events.MODEL_CHANGED, newSession);
			}
		} catch (final InvocationTargetException | InterruptedException ex) {
			if (ex instanceof InvocationTargetException) {
				final InvocationTargetException ite = (InvocationTargetException) ex;
				final Throwable cause = ite.getCause();
				if (cause instanceof SaveFailed) {
					// bypass error reporting, because save function has its own
					return false;
				}
			}
			reportError(shell, ex, dialogService, getErrorTitleMessage(),
					getErrorMessage(), getErrorCauseMessage());
			return false;
		}
		return true;
	}

	/**
	 * Show the initialization part.
	 * 
	 * @param toolboxViewService
	 *            the toolbox view service
	 */
	public abstract void showInitPart(ToolboxPartService toolboxViewService);

	@Execute
	private void execute(final Shell shell, final MApplication application,
			final ToolboxPartService toolboxViewService) {
		// unsaved data
		if (modelSession != null && modelSession.isDirty()) {
			if (!dialogService.confirmCloseUnsaved(shell)) {
				return;
			}
		}

		// close the current session
		if (modelSession != null) {
			modelSession.close();
		}

		// clean up the view area
		toolboxViewService.clean();

		final int result = openDialog(shell, application);

		if (result != Window.CANCEL) {
			showInitPart(toolboxViewService);
		}

		logger.trace("Execute done."); //$NON-NLS-1$
	}

	protected DialogService getDialogService() {
		return dialogService;
	}
}
