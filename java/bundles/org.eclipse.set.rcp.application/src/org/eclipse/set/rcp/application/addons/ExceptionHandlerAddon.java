/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.rcp.application.addons;

import java.util.function.Consumer;

import jakarta.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import org.eclipse.set.core.services.dialog.DialogService;

/**
 * Custom exception handler.
 * 
 * @author Schaefer
 */
public class ExceptionHandlerAddon {

	@Inject
	DialogService dialogService;

	@Inject
	@Optional
	private void startUpComplete(
			@UIEventTopic(UIEvents.UILifeCycle.APP_STARTUP_COMPLETE) final MApplication application) {
		final MWindow child = application.getChildren().get(0);
		final Shell shell = (Shell) child.getWidget();

		Display.getCurrent()
			.setRuntimeExceptionHandler(new Consumer<RuntimeException>() {
				@Override
				public void accept(final RuntimeException e) {
					dialogService.error(shell, e);
				}
			});
	}
}
