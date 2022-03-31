/**
 * Copyright (c) 2019 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.core.rcp.dialogservice;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.set.basis.files.ToolboxFileFilter;
import org.eclipse.set.core.Messages;
import org.eclipse.set.core.dialogservice.DialogServiceCommonImpl;
import org.eclipse.set.custom.extensions.FileDialogExtensions;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

/**
 * Implementation of {@link DialogServiceCommonImpl}
 * 
 * @author roeber
 */
public class DialogServiceRcpImpl extends DialogServiceCommonImpl {

	private static Optional<Path> selectFile(final int style, final Shell shell,
			final List<ToolboxFileFilter> filters,
			final Optional<Path> defaultPath, final Optional<String> title) {
		final FileDialog fileDialog = new FileDialog(shell, style);
		title.ifPresent(t -> fileDialog.setText(t));
		final Optional<Path> parent = defaultPath.map(p -> p.getParent());
		final Optional<Path> fileName = defaultPath.map(p -> p.getFileName());
		parent.ifPresent(p -> fileDialog.setFilterPath(p.toString()));
		fileName.ifPresent(f -> fileDialog.setFileName(f.toString()));
		fileDialog.setOverwrite(true);
		FileDialogExtensions.setExtensionFilters(fileDialog, filters);
		return Optional.ofNullable(fileDialog.open()).map(s -> Paths.get(s));
	}

	@Inject
	@Translation
	Messages messages;

	@Inject
	UISynchronize sync;

	@Inject
	@Translation
	org.eclipse.set.utils.Messages utilMessages;

	@Override
	public boolean createCompositePlanningWithInvalidInput(final Shell shell,
			final boolean isPrimaryValid, final boolean isSecondaryValid) {
		final int result = MessageDialog.open(MessageDialog.WARNING, shell,
				messages.createCompositePlanningWithInvalidInputTitle,
				String.format(
						messages.createCompositePlanningWithInvalidInputPattern,
						getPlanningDescription(isPrimaryValid,
								isSecondaryValid)),
				SWT.NONE, messages.DialogService_CreateCompositePlanningButton,
				messages.DialogService_ReturnButton);
		return result == 0;
	}

	@Override
	public boolean doApplyAutofill(final Shell shell, final String date) {
		final int buttonIndex = MessageDialog.open(MessageDialog.QUESTION,
				shell, messages.DialogService_AutoFillConfirmation_Title,
				String.format(
						messages.DialogService_AutoFillConfirmation_Pattern,
						date),
				SWT.NONE,
				messages.DialogService_AutoFillConfirmation_Button_Apply,
				messages.DialogService_AutoFillConfirmation_Button_NotApply);
		return buttonIndex == 0;
	}

	@Override
	public void fileDownload(final Shell shell, final Path path) {
		// not used with RCP
		throw new UnsupportedOperationException();
	}

	@Override
	public void openDirectoryAfterExport(final Shell shell, final Path path) {
		final int result = MessageDialog.open(MessageDialog.INFORMATION, shell,
				messages.DialogService_OpenDirectoryAfterExport_Title,
				messages.DialogService_OpenDirectoryAfterExport_Message,
				SWT.NONE, IDialogConstants.OK_LABEL,
				messages.DialogService_OpenDirectoryAfterExport_ShowDir);
		if (result == 1) {
			try {
				Runtime.getRuntime().exec("explorer " + path.toString()); //$NON-NLS-1$
			} catch (final IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	@Override
	public Optional<Path> openFileDialog(final Shell shell,
			final List<ToolboxFileFilter> filters) {
		return selectFile(SWT.OPEN, shell, filters, Optional.empty(),
				Optional.empty());
	}

	@Override
	public Optional<Path> saveFileDialog(final Shell shell,
			final List<ToolboxFileFilter> filters) {
		return selectFile(SWT.SAVE, shell, filters, Optional.empty(),
				Optional.empty());
	}

	@Override
	public Optional<Path> saveFileDialog(final Shell shell,
			final List<ToolboxFileFilter> filters, final Path defaultPath) {
		return selectFile(SWT.SAVE, shell, filters,
				Optional.ofNullable(defaultPath), Optional.empty());
	}

	@Override
	public Optional<Path> saveFileDialog(final Shell shell,
			final List<ToolboxFileFilter> filters, final Path defaultPath,
			final String title) {
		return selectFile(SWT.SAVE, shell, filters,
				Optional.ofNullable(defaultPath), Optional.ofNullable(title));
	}

	@Override
	public Optional<String> selectDirectory(final Shell shell,
			final String pathFilter) {
		final DirectoryDialog directoryDialog = new DirectoryDialog(shell);
		directoryDialog.setFilterPath(pathFilter);
		directoryDialog.setMessage(messages.DialogService_SelectDirectory);
		final String dir = directoryDialog.open();
		if (dir != null) {
			final File dirFile = new File(dir);
			if (!dirFile.exists()) {
				dirFile.mkdir();
			}
		}
		return Optional.ofNullable(dir);
	}
}
