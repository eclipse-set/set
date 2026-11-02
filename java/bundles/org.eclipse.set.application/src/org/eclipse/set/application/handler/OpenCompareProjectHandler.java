/**
 * Copyright (c) 2025 DB InfraGO AG and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 */
package org.eclipse.set.application.handler;

import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.basis.Wrapper;
import org.eclipse.set.basis.constants.Events;
import org.eclipse.set.basis.constants.ToolboxConstants;
import org.eclipse.set.basis.files.ToolboxFileRole;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * This handler can present a file dialog, assigns the selected file to a second
 * model session and visualization the different of PT1 table between two
 * project.
 * 
 * @author Truong
 */
public class OpenCompareProjectHandler extends OpenPlanProHandler {

	private boolean isProgressDialogDone = false;

	/**
	 * @return can execute only when a project was loaded
	 */
	@CanExecute
	public boolean canExecute() {
		return oldModelSession != null;
	}

	@Override
	protected void closeOldSession(final Shell shell) {
		final IModelSession compareSession = sessionService
				.getLoadedSession(getRole());
		if (compareSession != null) {
			compareSession.close();
		}
	}

	@Override
	protected ToolboxFileRole getRole() {
		return ToolboxFileRole.COMPARE_PLANNING;
	}

	@Override
	protected void success(final IModelSession modelSession,
			final MApplication application) {
		eventBroker.send(Events.COMPARE_PLAN_COMPLETE_LOADED, null);
	}

	@Override
	protected IRunnableWithProgress createLoadDataProgress(
			final Wrapper<IModelSession> newSessionWrapper, final Path path) {
		final Shell activeShell = Display.getCurrent().getActiveShell();
		return new IRunnableWithProgress() {

			@Override
			public void run(final IProgressMonitor monitor)
					throws InvocationTargetException, InterruptedException {
				// start a single task with unknown timeframe
				monitor.beginTask(getTaskMessage(), IProgressMonitor.UNKNOWN);
				isProgressDialogDone = false;
				// create the session
				logger.trace("Loading session..."); //$NON-NLS-1$
				newSessionWrapper.setValue(createSession(path));
				Display.getDefault()
						.syncExec(() -> newSessionWrapper.setValue(
								validation(newSessionWrapper.getValue(),
										activeShell, path)));

				if (newSessionWrapper.getValue() == null) {
					monitor.done();
					return;
				}
				// Trigger background progress
				eventBroker.send(Events.COMPARE_MODEL_LOADED,
						newSessionWrapper.getValue());

				Thread.sleep(500);
				while (!getBackgroundThread().stream()
						.filter(Thread::isAlive)
						.toList()
						.isEmpty()) {
					Thread.sleep(2000);
				}
				isProgressDialogDone = true;
				// stop progress
				monitor.done();
				logger.trace("Loading session done."); //$NON-NLS-1$

			}
		};
	}

	private List<Thread> getBackgroundThread() {
		return Thread.getAllStackTraces()
				.keySet()
				.stream() //
				.filter(t -> t.getName()
						.startsWith(
								ToolboxConstants.BACKGROUND_PROGESS_THREAD_NAME_PREFIX))
				.filter(t -> t.getName().contains(getRole().toString()))
				.toList();
	}

	@Override
	protected IModelSession validation(final IModelSession modelSession,
			final Shell shell, final Path path) {
		// Only show the validation dialog after modelsession loaded
		if (!isProgressDialogDone) {
			return super.validation(modelSession, shell, path);
		}
		return modelSession;
	}
}