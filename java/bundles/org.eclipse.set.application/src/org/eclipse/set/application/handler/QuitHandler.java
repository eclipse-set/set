/**
 * Copyright (c) 2015 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.application.handler;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.workbench.modeling.IWindowCloseHandler;

/**
 * Diese Steuerung kann die Anwendung beenden.
 * 
 * @author Schaefer
 */
public class QuitHandler {

	@Execute
	private static void execute(final MWindow window) {
		final IWindowCloseHandler closeHandler = window.getContext()
				.get(IWindowCloseHandler.class);
		closeHandler.close(window);
	}
}
