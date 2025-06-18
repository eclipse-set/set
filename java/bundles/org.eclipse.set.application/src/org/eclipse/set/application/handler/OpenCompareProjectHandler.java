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

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Evaluate;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.basis.constants.Events;
import org.eclipse.set.basis.files.ToolboxFileRole;
import org.eclipse.set.utils.ToolboxConfiguration;
import org.eclipse.swt.widgets.Shell;

/**
 * This handler can present a file dialog, assigns the selected file to a second
 * model session and visualization the different of PT1 table between two
 * project.
 * 
 * @author Truong
 */
public class OpenCompareProjectHandler extends OpenPlanProHandler {

	/**
	 * @return can execute only when a project was loaded
	 */
	@CanExecute
	public boolean canExecute() {
		return oldModelSession != null;
	}

	/**
	 * @return currently available only by development mode
	 */
	@Evaluate
	public static boolean isOpenCompareProjectVisible() {
		return ToolboxConfiguration.isDevelopmentMode();
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
		eventBroker.send(Events.COMPARE_MODEL_LOADED, modelSession);
	}
}