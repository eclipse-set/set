/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.application.addons;

import java.io.IOException;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.workbench.IWorkbench;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.IWindowCloseHandler;
import org.eclipse.set.application.Messages;
import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.core.services.configurationservice.JSONConfigurationService;
import org.eclipse.set.core.services.dialog.DialogService;
import org.eclipse.swt.widgets.Shell;

/**
 * Custom window close handler.
 * 
 * @author Schaefer
 */
public class WindowCloseAddon implements IWindowCloseHandler {

	@Inject
	private DialogService dialogService;

	@Inject
	@Translation
	Messages messages;

	@Inject
	@Optional
	IModelSession modelSession;

	@Inject
	@Optional
	IWorkbench workbench;

	@Inject
	@Optional
	JSONConfigurationService jsonService;

	@Override
	public boolean close(final MWindow window) {
		final Shell shell = window.getContext().get(Shell.class);

		// unsaved data
		if (modelSession != null && modelSession.isDirty()) {
			if (dialogService.confirmCloseUnsaved(shell)) {
				close();
			}
			return false;
		}

		// saved data
		if (dialogService.confirmClose(shell)) {
			close();
		}
		return false;
	}

	private void close() {
		if (modelSession != null) {
			modelSession.cleanUp();
		}
		try {
			jsonService.storeJSON();
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
		workbench.close();
	}

	@Inject
	@Optional
	private void startUpComplete(
			@UIEventTopic(UIEvents.UILifeCycle.APP_STARTUP_COMPLETE) final MApplication application,
			final EModelService modelService) {
		final MWindow window = (MWindow) modelService.find("planpro.mainwindow", //$NON-NLS-1$
				application);
		window.getContext().set(IWindowCloseHandler.class, this);
	}
}
