/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.core.dialogservice;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.BiPredicate;

import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IDialogLabelKeys;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.basis.ProjectInitializationData;
import org.eclipse.set.basis.Wrapper;
import org.eclipse.set.basis.constants.ToolboxConstants;
import org.eclipse.set.basis.constants.ToolboxOS;
import org.eclipse.set.basis.exceptions.ErrorStatus;
import org.eclipse.set.basis.extensions.PathExtensions;
import org.eclipse.set.basis.files.ToolboxFileFilter;
import org.eclipse.set.basis.files.ToolboxFileFilterBuilder;
import org.eclipse.set.basis.rename.RenameConfirmation;
import org.eclipse.set.core.Messages;
import org.eclipse.set.core.services.dialog.DialogService;
import org.eclipse.set.core.services.files.ToolboxFileService;
import org.eclipse.set.custom.extensions.FileDialogExtensions;
import org.eclipse.set.utils.ToolboxConfiguration;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;

/**
 * Implementation of {@link DialogService}.
 * 
 * @author Schaefer
 */
public class DialogServiceImpl implements DialogService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(DialogServiceImpl.class);

	private List<ToolboxFileFilter> csvFileFilters;

	private List<ToolboxFileFilter> dokumentFileFilters;

	private List<ToolboxFileFilter> fileFiltersForAllFiles;

	@Inject
	private ToolboxFileService fileService;

	private List<ToolboxFileFilter> imageFileFilters;

	private List<ToolboxFileFilter> modelFileFilters;

	private List<ToolboxFileFilter> planProFileFilters;

	private List<ToolboxFileFilter> xlsxFileFilters;

	@Inject
	@Translation
	Messages messages;

	@Inject
	UISynchronize sync;

	@Inject
	@Translation
	org.eclipse.set.utils.Messages utilMessages;

	@Override
	public boolean confirmClose(final Shell shell) {
		final ConfirmDialog confirmDialog = new ConfirmDialog(shell,
				messages.WindowCloseAddon_ConfirmTitle,
				messages.WindowCloseAddon_CloseMessage,
				messages.DialogService_CloseLabel);
		return confirmDialog.confirmed();
	}

	@Override
	public boolean confirmCloseUnsaved(final Shell shell) {
		final ConfirmDialog confirmDialog = new ConfirmDialog(shell,
				messages.WindowCloseAddon_ConfirmTitle,
				messages.WindowCloseAddon_CloseUnsavedMessage,
				messages.DialogService_CloseUnsavedLabel);
		return confirmDialog.confirmed();
	}

	@Override
	public boolean confirmCreateDirectory(final Shell shell, final Path path) {
		final ConfirmDialog confirmDialog = new ConfirmDialog(shell,
				messages.DialogService_ConfirmCreateDirectory_Title,
				String.format(
						messages.DialogService_ConfirmCreateDirectory_MessagePattern,
						path.toString()),
				messages.DialogService_ConfirmCreateDirectory_ConfirmLabel);
		return confirmDialog.confirmed();
	}

	@Override
	public boolean confirmDeletion(final Shell shell, final Path path) {
		final ConfirmDialog confirmDialog = new ConfirmDialog(shell,
				utilMessages.Dialogs_confirmDeletionTitle,
				String.format(
						utilMessages.Dialogs_confirmDeletionMessagePattern,
						path.getFileName().toString()),
				messages.DialogService_ConfirmDeleteLabel);
		final Wrapper<Boolean> result = new Wrapper<>();
		Display.getDefault().syncExec(() -> {
			result.setValue(Boolean.valueOf(confirmDialog.confirmed()));
		});
		return result.getValue().booleanValue();
	}

	@Override
	public boolean confirmOverwrite(final Shell shell, final Path path) {
		final ConfirmDialog confirmDialog = new ConfirmDialog(shell,
				utilMessages.Dialogs_confirmOverwriteTitle,
				String.format(
						utilMessages.Dialogs_confirmOverwriteMessagePattern,
						path.getFileName().toString()),
				utilMessages.Dialogs_confirmOverwriteLabel);
		final Wrapper<Boolean> result = new Wrapper<>();
		Display.getDefault().syncExec(() -> {
			result.setValue(Boolean.valueOf(confirmDialog.confirmed()));
		});
		return result.getValue().booleanValue();
	}

	@Override
	public boolean confirmOverwriteOperationalData(final Shell shell) {
		final ConfirmDialog confirmDialog = new ConfirmDialog(shell,
				messages.DialogService_ConfirmOverwriteOperationalData_Title,
				messages.DialogService_ConfirmOverwriteOperationalData_Message,
				messages.DialogService_ConfirmOverwriteOperationalData_ConfirmLabel);
		return confirmDialog.confirmed();
	}

	@Override
	public boolean confirmOverwriteUnsaved(final Shell shell,
			final String title, final String mess) {
		final ConfirmDialog confirmDialog = new ConfirmDialog(shell, title,
				mess,
				messages.DialogService_ConfirmOverwriteOperationalData_ConfirmLabel);
		return confirmDialog.confirmed();
	}

	// Helper class to widen the rename message dialog
	private static class RenameMessageDialog extends MessageDialog {

		public RenameMessageDialog(final Shell parentShell,
				final String dialogTitle, final Image dialogTitleImage,
				final String dialogMessage, final int dialogImageType,
				final String[] dialogButtonLabels, final int defaultIndex) {
			super(parentShell, dialogTitle, dialogTitleImage, dialogMessage,
					dialogImageType, dialogButtonLabels, defaultIndex);
		}

		@Override
		protected Point getInitialSize() {
			final Point size = getShell().computeSize(SWT.DEFAULT, SWT.DEFAULT,
					true);
			size.x = (int) (size.x * 1.2);
			return size;
		}

	}

	@Override
	public RenameConfirmation confirmRename(final Shell shell,
			final String oldFilename, final String newFilename) {
		final MessageDialog messageDialog = new RenameMessageDialog(shell,
				messages.DialogService_ConfirmRename_Title, null,
				String.format(
						messages.DialogService_ConfirmRename_MessagePattern,
						oldFilename, newFilename),
				MessageDialog.CONFIRM,
				new String[] { messages.DialogService_ConfirmRename_ChangeLabel,
						messages.DialogService_ConfirmRename_NewLabel,
						JFaceResources
								.getString(IDialogLabelKeys.CANCEL_LABEL_KEY) },
				2);
		return RenameConfirmation.fromDialogResult(messageDialog.open());
	}

	@Override
	public boolean confirmSetDefaultsManagement(final Shell shell) {
		return MessageDialog.openQuestion(shell,
				messages.DialogService_ConfirmFillDefaults_Title,
				messages.DialogService_ConfirmFillDefaults_Label);
	}

	@Override
	public String selectValueDialog(final Shell shell, final String title,
			final String message, final String comboLabel,
			final List<String> selectItems) {
		final SelectValueDialog dialog = new SelectValueDialog(shell, title,
				message, selectItems);
		dialog.open();
		final Optional<String> firstResult = dialog.getFirstResult();

		if (firstResult.isEmpty()) {
			return null;
		}
		return firstResult.get();
	}

	@Override
	public boolean confirmExportNotCompleteTable(final Shell shell) {
		return MessageDialog.openQuestion(shell,
				messages.DialogService_ConfirmExportNotCompleteTable_Title,
				messages.DialogService_ConfirExportNotCompleteTable_Label);
	}

	@Override
	public void error(final Shell shell, final Exception e) {
		LOGGER.error(e.getMessage(), e);
		final ErrorStatus status = new ExceptionTransformation(messages)
				.transform(e, null);

		sync.syncExec(() -> ErrorDialog.openError(shell, status.getErrorTitle(),
				status.getUserMessage(), status.getStatus()));
	}

	@Override
	public void error(final Shell shell, final String message) {
		LOGGER.error(message);
		final ErrorStatus status = new ExceptionTransformation(messages)
				.transform(null, null);
		sync.syncExec(() -> ErrorDialog.openError(shell, status.getErrorTitle(),
				message, status.getStatus()));
	}

	@Override
	public void error(final Shell shell, final String title,
			final String message, final Exception e) {
		LOGGER.error(e.getMessage(), e);
		sync.syncExec(() -> ErrorDialog.openError(shell, title, message,
				new ExceptionTransformation(messages).transform(e, null)
						.getStatus()));
	}

	@Override
	public void error(final Shell shell, final String title,
			final String message, final String technicalMsg,
			final Exception e) {
		LOGGER.error(e.getMessage(), e);
		sync.syncExec(() -> ErrorDialog.openError(shell, title, message,
				new ExceptionTransformation(messages).transform(e, technicalMsg)
						.getStatus()));
	}

	@Override
	public List<ToolboxFileFilter> getCsvFileFilters() {
		return csvFileFilters;
	}

	@Override
	public List<ToolboxFileFilter> getDokumentFileFilters() {
		return dokumentFileFilters;
	}

	@Override
	public List<ToolboxFileFilter> getFileFiltersForAllFiles() {
		return fileFiltersForAllFiles;
	}

	@Override
	public List<ToolboxFileFilter> getImageFileFilters() {
		return imageFileFilters;
	}

	@Override
	public List<ToolboxFileFilter> getModelFileFilters() {
		return modelFileFilters;
	}

	@Override
	public List<ToolboxFileFilter> getPlanProFileFilters() {
		return planProFileFilters;
	}

	@Override
	public List<ToolboxFileFilter> getXlsxFileFilters() {
		return xlsxFileFilters;
	}

	@Override
	public boolean loadIncompleteModel(final Shell shell,
			final String filename) {
		final LoadIncompleteFileDialog validationDialog = new LoadIncompleteFileDialog(
				shell, messages, filename);
		final int result = validationDialog.open();
		validationDialog.close();
		return result == IDialogConstants.OK_ID;
	}

	@Override
	public boolean loadIncompleteModel(final Shell shell,
			final String dialogTitle, final String dialogMsg,
			final String... buttonsLabel) {
		final LoadIncompleteFileDialog dialog = new LoadIncompleteFileDialog(
				shell, dialogTitle, dialogMsg, buttonsLabel);
		final int open = dialog.open();
		dialog.close();
		return open == IDialogConstants.OK_ID;
	}

	@Override
	public boolean loadInvalidModel(final Shell shell, final String filename) {
		final LoadInvalidFileDialog validationDialog = new LoadInvalidFileDialog(
				shell, messages, filename);
		final int result = validationDialog.open();
		validationDialog.close();
		return result == IDialogConstants.OK_ID;
	}

	@Override
	public boolean loadInvalidModel(final Shell shell, final String dialogTitle,
			final String dialogMsg, final String... buttonsLabel) {
		final LoadInvalidFileDialog dialog = new LoadInvalidFileDialog(shell,
				dialogTitle, dialogMsg, buttonsLabel);
		final int open = dialog.open();
		dialog.close();
		return open == IDialogConstants.OK_ID;
	}

	@Override
	public void openInformation(final Shell shell, final String title,
			final String message) {
		MessageDialog.openInformation(shell, title, message);
	}

	@Override
	public int projectInitialization(final Shell shell,
			final BiPredicate<Shell, ProjectInitializationData> initAction) {
		final ProjectFilenameInitialization dialog = new ProjectFilenameInitialization(
				shell, messages, this);
		dialog.setInitAction(initAction);
		dialog.setCreateDirectoryPermission(this::confirmCreateDirectory);
		return dialog.open();
	}

	@Override
	public void reportExported(final Shell shell) {
		MessageDialog.openInformation(shell,
				messages.DialogService_ReportExported_Title,
				messages.DialogService_ReportExported_Message);
	}

	@Override
	public void reportImported(final Shell shell) {
		MessageDialog.openInformation(shell,
				messages.DialogService_ReportImported_Title,
				messages.DialogService_ReportImported_Message);
	}

	@Override
	public void reportSavedFile(final Shell shell, final Path path) {
		MessageDialog.openInformation(shell,
				utilMessages.AttachmentTable_fileSaveSuccess,
				String.format(
						utilMessages.AttachmentTable_fileSuccessfullySavedPattern,
						path.toString()));
	}

	@Override
	public void reportSavedSession(final Shell shell,
			final IModelSession session) {
		final ReportSavedSessionDialog dialog = new ReportSavedSessionDialog(
				shell, session, messages);
		dialog.open();
	}

	@Override
	public void showDistance(final Shell shell, final double distance) {
		MessageDialog.openInformation(shell,
				messages.DialogService_ShowDistance_Title,
				String.format(
						messages.DialogService_ShowDistance_DistancePattern,
						Double.valueOf(distance)));
	}

	@Override
	public void showProgress(final Shell shell,
			final IRunnableWithProgress runnable)
			throws InvocationTargetException, InterruptedException {
		new ProgressMonitorDialog(shell).run(true, false, runnable);
	}

	@Override
	public void showProgressUISync(final Shell shell, final String message,
			final Runnable runnable) {
		final MessageDialog dialog = new MessageDialog(shell,
				messages.DialogService_ProgressUISyncTitle, null, message,
				MessageDialog.INFORMATION, new String[] {}, 0);
		dialog.setBlockOnOpen(false);
		dialog.open();
		sync.syncExec(runnable);
		dialog.close();
	}

	@Override
	public <T> T showProgressUISync(final Shell shell, final String message,
			final Callable<T> callAble)
			throws InterruptedException, ExecutionException {
		final MessageDialog dialog = new MessageDialog(shell,
				messages.DialogService_ProgressUISyncTitle, null, message,
				MessageDialog.INFORMATION, new String[] {}, 0);
		dialog.setBlockOnOpen(false);
		dialog.open();
		try (ExecutorService executor = Executors.newSingleThreadExecutor()) {
			final Future<T> future = executor.submit(callAble);
			final T t = future.get(120, TimeUnit.SECONDS);
			dialog.close();
			return t;
		} catch (final TimeoutException e) {
			throw new ExecutionException(e);
		}
	}

	@PostConstruct
	private void postConstruct() {
		modelFileFilters = Lists.newLinkedList();
		modelFileFilters.add(ToolboxFileFilterBuilder
				.forName(messages.DialogService_ModelFilterName)
				.add(fileService.extensionsForCategory(
						ToolboxConstants.EXTENSION_CATEGORY_PPFILE))
				.filterNameWithFilterList(true)
				.create());

		planProFileFilters = Lists.newLinkedList();
		planProFileFilters.add(ToolboxFileFilterBuilder
				.forName(messages.DialogService_PlanProFilterName)
				.add(fileService.extensionsForCategory(
						ToolboxConstants.EXTENSION_CATEGORY_PPALL))
				.filterNameWithFilterList(true)
				.create());
		planProFileFilters.add(ToolboxFileFilterBuilder
				.forName(messages.DialogService_ModelFilterName)
				.add(fileService.extensionsForCategory(
						ToolboxConstants.EXTENSION_CATEGORY_PPFILE))
				.filterNameWithFilterList(true)
				.create());
		planProFileFilters.add(ToolboxFileFilterBuilder
				.forName(messages.DialogService_MergeFilterName)
				.add(fileService.extensionsForCategory(
						ToolboxConstants.EXTENSION_CATEGORY_PPMERGE))
				.filterNameWithFilterList(true)
				.create());

		csvFileFilters = Lists.newLinkedList();
		csvFileFilters.add(ToolboxFileFilterBuilder
				.forName(messages.DialogService_CsvFilterName)
				.add(PathExtensions.CSV_FILE_EXTENSIONS)
				.filterNameWithFilterList(true)
				.create());

		imageFileFilters = Lists.newLinkedList();
		imageFileFilters.add(ToolboxFileFilterBuilder
				.forName(messages.DialogService_ImagesFilterName)
				.add(PathExtensions.IMAGE_FILE_EXTENSIONS)
				.filterNameWithFilterList(true)
				.create());

		dokumentFileFilters = Lists.newLinkedList();
		dokumentFileFilters.add(ToolboxFileFilterBuilder
				.forName(messages.DialogService_DokumentFilterName)
				.add(PathExtensions.DOKUMENT_FILE_EXTENSIONS)
				.filterNameWithFilterList(true)
				.create());

		xlsxFileFilters = Lists.newLinkedList();
		xlsxFileFilters.add(ToolboxFileFilterBuilder
				.forName(messages.DialogService_XlsxFilterName)
				.add(PathExtensions.EXCEL_FILE_EXTENSION)
				.filterNameWithFilterList(true)
				.create());

		fileFiltersForAllFiles = Lists.newLinkedList();
		fileFiltersForAllFiles.add(ToolboxFileFilterBuilder
				.forName(messages.DialogService_FilterForAllFiles)
				.add(PathExtensions.ALL_FILE_EXTENSION)
				.filterNameWithFilterList(true)
				.create());
	}

	protected String getPlanningDescription(final boolean isPrimaryValid,
			final boolean isSecondaryValid) {
		if (!isPrimaryValid && !isSecondaryValid) {
			return messages.DialogService_PlanningDescriptionPrimaryAndSecondary;
		}
		if (!isPrimaryValid) {
			return messages.DialogService_PlanningDescriptionPrimary;
		}
		if (!isSecondaryValid) {
			return messages.DialogService_PlanningDescriptionSecondary;
		}
		throw new IllegalArgumentException("isPrimaryValid=" + isPrimaryValid //$NON-NLS-1$
				+ " isSecondaryValid=" + isSecondaryValid); //$NON-NLS-1$
	}

	private static Optional<Path> selectFile(final int style, final Shell shell,
			final List<ToolboxFileFilter> filters,
			final Optional<Path> defaultPath, final Optional<String> title) {
		final FileDialog fileDialog = new FileDialog(shell, style);
		title.ifPresent(fileDialog::setText);
		final Optional<Path> parent = defaultPath.map(Path::getParent);
		final Optional<Path> fileName = defaultPath.map(Path::getFileName);
		parent.ifPresent(p -> fileDialog.setFilterPath(p.toString()));
		fileName.ifPresent(f -> fileDialog.setFileName(f.toString()));
		fileDialog.setOverwrite(true);
		FileDialogExtensions.setExtensionFilters(fileDialog, filters);
		return Optional.ofNullable(fileDialog.open()).map(Paths::get);
	}

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
	public void openDirectoryAfterExport(final Shell shell, final Path path) {
		final int result = MessageDialog.open(MessageDialog.INFORMATION, shell,
				messages.DialogService_OpenDirectoryAfterExport_Title,
				messages.DialogService_OpenDirectoryAfterExport_Message,
				SWT.NONE, IDialogConstants.OK_LABEL,
				messages.DialogService_OpenDirectoryAfterExport_ShowDir);
		if (result == 1) {
			try {
				final String openExplorerCmd = ToolboxConfiguration
						.getCurrentOS() == ToolboxOS.LINUX ? "nautilus" //$NON-NLS-1$
								: "explorer"; //$NON-NLS-1$
				Runtime.getRuntime()
						.exec(new String[] { openExplorerCmd,
								path.toString() });
			} catch (final IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	@Override
	public Optional<Path> openFileDialog(final Shell shell,
			final List<ToolboxFileFilter> filters,
			final Optional<Path> filterPath) {
		return selectFile(SWT.OPEN, shell, filters, filterPath,
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

	@Override
	public boolean sitePlanError(final Shell shell, final String filename) {
		final LoadInvalidSiteplanDialog dialog = new LoadInvalidSiteplanDialog(
				shell, filename, messages);
		final int result = dialog.open();
		dialog.close();
		return result == IDialogConstants.OK_ID;
	}

}
