/**
 * Copyright (c) 2024 DB InfraGO AG and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 */
package org.eclipse.set.feature.plaz.dialog;

import org.eclipse.swt.widgets.Shell;

/**
 * Manage PlaZ specific UI dialogs.
 */
public interface PlazDialogService {
	/**
	 * Create a PlaZ error dialog.
	 * 
	 * @param shell
	 *            the shell
	 */
	void plazError(Shell shell);

}
