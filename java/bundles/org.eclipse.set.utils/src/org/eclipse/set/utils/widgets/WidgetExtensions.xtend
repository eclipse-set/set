/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.widgets

import org.eclipse.swt.widgets.Control
import org.eclipse.swt.widgets.Shell
import org.eclipse.swt.widgets.Widget

/**
 * Extensions for {@link Widget}.
 * 
 * @author Schaefer
 */
class WidgetExtensions {

	/**
	 * @param widget this widget
	 * 
	 * @return the shell of this widget
	 */
	static def Shell getShell(Widget widget) {
		return widget.getShellDispatch
	}

	private static dispatch def Shell getShellDispatch(Widget widget) {
		return null
	}

	private static dispatch def Shell getShellDispatch(Control control) {
		return control.shell
	}
}
