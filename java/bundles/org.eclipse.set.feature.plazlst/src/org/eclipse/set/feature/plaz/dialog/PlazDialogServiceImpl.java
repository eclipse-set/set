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

import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.set.feature.plaz.Messages;
import org.eclipse.swt.widgets.Shell;

import jakarta.inject.Inject;

/**
 * 
 */
public class PlazDialogServiceImpl implements PlazDialogService {
	@Inject
	@Translation
	Messages messages;

	@Override
	public void plazError(final Shell shell) {
		MessageDialog.openError(shell,
				messages.PptDialogServiceImpl_PlazError_Title,
				messages.PptDialogServiceImpl_PlazError_Message);
	}
}
