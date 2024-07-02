/**
 * Copyright (c) 2023 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.swtbot.utils;

import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.function.BiPredicate;
import java.util.function.Function;

import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.basis.ProjectInitializationData;
import org.eclipse.set.basis.extensions.PathExtensions;
import org.eclipse.set.basis.files.ToolboxFileFilter;
import org.eclipse.set.basis.files.ToolboxFileFilterBuilder;
import org.eclipse.set.basis.rename.RenameConfirmation;
import org.eclipse.set.core.services.dialog.DialogService;
import org.eclipse.swt.widgets.Shell;

import jakarta.inject.Inject;

/**
 * Mocked dialog service to handle native dialogs
 *
 */
public class MockDialogService implements DialogService {
	public Function<List<ToolboxFileFilter>, Optional<Path>> exportFileDialogHandler;
	public Function<List<ToolboxFileFilter>, Optional<Path>> openFileDialogHandler;

	@Inject
	UISynchronize sync;

	@Override
	public boolean confirmClose(final Shell shell) {
		return true;
	}

	@Override
	public boolean confirmCloseUnsaved(final Shell shell) {
		return true;
	}

	@Override
	public boolean confirmCreateDirectory(final Shell shell, final Path path) {
		return true;
	}

	@Override
	public boolean confirmDeletion(final Shell shell, final Path path) {
		return true;
	}

	@Override
	public boolean confirmExportNotCompleteTable(final Shell shell) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean confirmOverwrite(final Shell shell, final Path path) {
		return true;
	}

	@Override
	public boolean confirmOverwriteOperationalData(final Shell shell) {
		return true;
	}

	@Override
	public boolean confirmOverwriteUnsaved(final Shell shell,
			final String title, final String mess) {
		return true;
	}

	@Override
	public RenameConfirmation confirmRename(final Shell shell,
			final String oldFilename, final String newFilename) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean confirmSetDefaultsManagement(final Shell shell) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean createCompositePlanningWithInvalidInput(final Shell shell,
			final boolean isPrimaryValid, final boolean isSecondaryValid) {
		return true;
	}

	/**
	 * @param shell
	 *            the shell
	 * @param date
	 *            the date
	 * 
	 * @return whether automatic filling is confirmed
	 */
	@Override
	public boolean doApplyAutofill(final Shell shell, final String date) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void error(final Shell shell, final Exception e) {
		throw new RuntimeException(e);
	}

	@Override
	public void error(final Shell shell, final String message) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void error(final Shell shell, final String title,
			final String message, final Exception e) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void error(final Shell shell, final String title,
			final String message, final String technicalMsg,
			final Exception e) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<ToolboxFileFilter> getCsvFileFilters() {
		final List<ToolboxFileFilter> csvFileFilters = new LinkedList<>();
		csvFileFilters.add(ToolboxFileFilterBuilder.forName("CSV")
				.add(PathExtensions.CSV_FILE_EXTENSIONS)
				.filterNameWithFilterList(true).create());
		return csvFileFilters;
	}

	@Override
	public List<ToolboxFileFilter> getDokumentFileFilters() {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<ToolboxFileFilter> getFileFiltersForAllFiles() {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<ToolboxFileFilter> getImageFileFilters() {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<ToolboxFileFilter> getModelFileFilters() {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<ToolboxFileFilter> getPlanProFileFilters() {
		return List.of();
	}

	@Override
	public List<ToolboxFileFilter> getXlsxFileFilters() {
		return List.of();
	}

	@Override
	public boolean loadIncompleteModel(final Shell shell,
			final String filename) {
		return true;
	}

	@Override
	public boolean loadInvalidModel(final Shell shell, final String filename) {
		return true;
	}

	@Override
	public void openDirectoryAfterExport(final Shell shell, final Path path) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Optional<Path> openFileDialog(final Shell shell,
			final List<ToolboxFileFilter> filters,
			final Optional<Path> lastSelectedFile) {
		return openFileDialogHandler.apply(filters);
	}

	@Override
	public void openInformation(final Shell shell, final String title,
			final String message) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int projectInitialization(final Shell shell,
			final BiPredicate<Shell, ProjectInitializationData> initAction) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void reportExported(final Shell shell) {
		// do nothing
	}

	@Override
	public void reportImported(final Shell shell) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void reportSavedFile(final Shell shell, final Path path) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void reportSavedSession(final Shell shell,
			final IModelSession session) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Optional<Path> saveFileDialog(final Shell shell,
			final List<ToolboxFileFilter> filters, final Path defaultPath) {
		return exportFileDialogHandler.apply(filters);
	}

	@Override
	public Optional<Path> saveFileDialog(final Shell shell,
			final List<ToolboxFileFilter> filters, final Path defaultPath,
			final String title) {
		return exportFileDialogHandler.apply(filters);
	}

	@Override
	public Optional<String> selectDirectory(final Shell shell,
			final String pathFilter) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String selectValueDialog(final Shell shell, final String title,
			final String message, final String comboLabel,
			final List<String> selectItems) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void showDistance(final Shell shell, final double distance) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void showProgress(final Shell shell,
			final IRunnableWithProgress runnable)
			throws InvocationTargetException, InterruptedException {
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> T showProgressUISync(final Shell shell, final String message,
			final Callable<T> callAble)
			throws InterruptedException, ExecutionException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void showProgressUISync(final Shell shell, final String message,
			final Runnable runnable) {
		sync.syncExec(runnable);
	}

	@Override
	public boolean sitePlanError(final Shell shell, final String filename) {
		throw new UnsupportedOperationException();
	}

}
