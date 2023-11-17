/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.core.services.dialog;

import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;

import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.basis.ProjectInitializationData;
import org.eclipse.set.basis.files.ToolboxFileFilter;
import org.eclipse.set.basis.rename.RenameConfirmation;
import org.eclipse.swt.widgets.Shell;

/**
 * Manage UI dialogs.
 * 
 * @author Schaefer
 */
public interface DialogService {

	/**
	 * @param shell
	 *            the shell for the dialog
	 * 
	 * @return whether the user has confirmed to close a saved application
	 */
	boolean confirmClose(Shell shell);

	/**
	 * @param shell
	 *            the shell for the dialog
	 * 
	 * @return whether the user has confirmed to close an unsaved application
	 */
	boolean confirmCloseUnsaved(Shell shell);

	/**
	 * @param shell
	 *            the shell for the dialog
	 * @param path
	 *            the path
	 * 
	 * @return whether the user has confirmed to create the given directory
	 */
	boolean confirmCreateDirectory(Shell shell, Path path);

	/**
	 * @param shell
	 *            the shell for the dialog
	 * @param path
	 *            the path
	 * 
	 * @return whether the user has confirmed to delete the given path
	 */
	boolean confirmDeletion(Shell shell, Path path);

	/**
	 * @param shell
	 *            the shell for the dialog
	 * @param path
	 *            the path
	 * 
	 * @return whether the user has confirmed to overwrite the given path
	 */
	boolean confirmOverwrite(Shell shell, Path path);

	/**
	 * @param shell
	 *            the shell for the dialog
	 * 
	 * @return whether the user has confirmed to overwrite the operational data
	 */
	boolean confirmOverwriteOperationalData(Shell shell);

	/**
	 * @param shell
	 *            the shell for the dialog
	 * @param title
	 *            the dialog title
	 * @param mess
	 *            the dialog messages
	 * @return whether the user has confirmed to overwrite the data
	 */
	boolean confirmOverwriteUnsaved(Shell shell, String title, String mess);

	/**
	 * @param shell
	 *            the shell for the dialog
	 * @param oldFilename
	 *            the old filename
	 * @param newFilename
	 *            the new filename
	 * 
	 * @return the rename confirmation
	 */
	RenameConfirmation confirmRename(Shell shell, String oldFilename,
			String newFilename);

	/**
	 * @param shell
	 *            the shell
	 * @param isPrimaryValid
	 *            whether the primary planning was valid
	 * @param isSecondaryValid
	 *            whether the secondary planning was valid
	 * 
	 * @return whether the composite planning should be created
	 */
	boolean createCompositePlanningWithInvalidInput(Shell shell,
			boolean isPrimaryValid, boolean isSecondaryValid);

	/**
	 * @param shell
	 *            the shell
	 * @param date
	 *            the date
	 * 
	 * @return whether automatic filling is confirmed
	 */
	boolean doApplyAutofill(Shell shell, String date);

	/**
	 * Generate an error dialog with a default title and message.
	 * 
	 * @param shell
	 *            the shell, used to center the dialog
	 * @param e
	 *            the exception to report
	 */
	void error(Shell shell, Exception e);

	/**
	 * Generate an error dialog with a default title and message.
	 * 
	 * @param shell
	 *            the shell, used to center the dialog
	 * @param message
	 *            the message to report
	 */
	void error(Shell shell, String message);

	/**
	 * Generate an error dialog with the given title and message.
	 * 
	 * @param shell
	 *            the shell, used to center the dialog
	 * @param title
	 *            the title of the dialog
	 * @param message
	 *            the error message presented to the user
	 * @param e
	 *            the exception to report
	 */
	void error(Shell shell, String title, String message, Exception e);

	/**
	 * Generate an error dialog with the given title and message.
	 * 
	 * @param shell
	 *            the shell, used to center the dialog
	 * @param title
	 *            the title of the dialog
	 * @param message
	 *            the error message presented to the user
	 * @param technicalMsg
	 *            the error technical Message
	 * @param e
	 *            the exception to report
	 */
	void error(Shell shell, String title, String message, String technicalMsg,
			Exception e);

	/**
	 * @return the toolbox file filters for csv files
	 */
	List<ToolboxFileFilter> getCsvFileFilters();

	/**
	 * @return the toolbox file filters for all dokument file
	 */
	List<ToolboxFileFilter> getDokumentFileFilters();

	/**
	 * @return the file filters for all files
	 */
	List<ToolboxFileFilter> getFileFiltersForAllFiles();

	/**
	 * @return the toolbox file filters for image files
	 */
	List<ToolboxFileFilter> getImageFileFilters();

	/**
	 * @return the toolbox file filters for PlanPro model files
	 */
	List<ToolboxFileFilter> getModelFileFilters();

	/**
	 * @return the toolbox file filters for all PlanPro files
	 */
	List<ToolboxFileFilter> getPlanProFileFilters();

	/**
	 * @return the toolbox file filters for xlsx-Excel files
	 */
	List<ToolboxFileFilter> getXlsxFileFilters();

	/**
	 * @param shell
	 *            the shell
	 * @param filename
	 *            the filename
	 * 
	 * @return whether the model should be loaded (<code>true</code>) or whether
	 *         the loading should be aborted (<code>false</code>)
	 */
	boolean loadIncompleteModel(Shell shell, String filename);

	/**
	 * @param shell
	 *            the shell
	 * @param filename
	 *            the filename
	 * 
	 * @return whether the model should be loaded (<code>true</code>) or whether
	 *         the loading should be aborted (<code>false</code>)
	 */
	boolean loadInvalidModel(Shell shell, String filename);

	/**
	 * @param shell
	 *            the shell, used to center the dialog
	 * @param path
	 *            the path for the directory
	 */
	void openDirectoryAfterExport(Shell shell, Path path);

	/**
	 * Opens a dialog for selecting a file to read.
	 * 
	 * @param shell
	 *            the shell, used to center the dialog
	 * @param filters
	 *            the toolbox file filters
	 * @param filterPath
	 *            an optional path to open the selection dialog at
	 * 
	 * @return an (optional) path with the selected file
	 */
	Optional<Path> openFileDialog(Shell shell, List<ToolboxFileFilter> filters,
			Optional<Path> filterPath);

	/**
	 * @param shell
	 *            the shell, used to center the dialog
	 * @param title
	 *            the title of the info dialog
	 * @param message
	 *            the dialog message
	 */
	void openInformation(Shell shell, String title, String message);

	/**
	 * The initialization action shall return true, if the action is successful
	 * and false otherwise.
	 * 
	 * @param shell
	 *            the shell
	 * @param initAction
	 *            the initialization action
	 * 
	 * @return the return code
	 */
	int projectInitialization(Shell shell,
			BiPredicate<Shell, ProjectInitializationData> initAction);

	/**
	 * @param shell
	 *            the shell for the dialog
	 */
	void reportExported(Shell shell);

	/**
	 * @param shell
	 *            the shell for the dialog
	 */
	void reportImported(Shell shell);

	/**
	 * @param shell
	 *            the shell for the dialog
	 * @param path
	 *            the path for the saved file
	 */
	void reportSavedFile(Shell shell, Path path);

	/**
	 * @param shell
	 *            the shell for the dialog
	 * @param session
	 *            the model session
	 */
	void reportSavedSession(Shell shell, IModelSession session);

	/**
	 * Opens a dialog for selecting a file to write.
	 * 
	 * @param shell
	 *            the shell, used to center the dialog
	 * @param filters
	 *            the toolbox file filters
	 * @param defaultPath
	 *            the default path
	 * 
	 * @return an (optional) path with the selected file
	 */
	Optional<Path> saveFileDialog(Shell shell, List<ToolboxFileFilter> filters,
			Path defaultPath);

	/**
	 * Opens a dialog for selecting a file to write.
	 * 
	 * @param shell
	 *            the shell, used to center the dialog
	 * @param filters
	 *            the toolbox file filters
	 * @param defaultPath
	 *            the default path
	 * @param title
	 *            the title for the dialog
	 * 
	 * @return an (optional) path with the selected file
	 */
	Optional<Path> saveFileDialog(Shell shell, List<ToolboxFileFilter> filters,
			Path defaultPath, String title);

	/**
	 * @param shell
	 *            the shell
	 * @param pathFilter
	 *            the path filter
	 * 
	 * @return the optional selected directory (empty if the user aborted the
	 *         selection)
	 */
	Optional<String> selectDirectory(final Shell shell,
			final String pathFilter);

	/**
	 * @param shell
	 *            the shell, used to center the dialog
	 * @param distance
	 *            the distance to report
	 */
	void showDistance(Shell shell, double distance);

	/**
	 * Show Progress.
	 * 
	 * @param shell
	 *            the shell
	 * @param runnable
	 *            the runnable to show the progress for
	 * 
	 * @throws InvocationTargetException
	 *             wraps any exception or error which occurs while running the
	 *             runnable
	 * @throws InterruptedException
	 *             propagated by the context if the runnable acknowledges
	 *             cancelation by throwing this exception. This should not be
	 *             thrown if cancelable is false.
	 */
	void showProgress(Shell shell, IRunnableWithProgress runnable)
			throws InvocationTargetException, InterruptedException;

	/**
	 * Show Progress UI synchronized.
	 * 
	 * @param shell
	 *            the shell
	 * @param message
	 *            the progress message
	 * @param runnable
	 *            the runnable
	 */
	void showProgressUISync(Shell shell, String message, Runnable runnable);

	/**
	 * @param shell
	 *            the shell for the dialog
	 * @return whether the user has confirmed to apply default values globally
	 */
	boolean confirmSetDefaultsGlobally(Shell shell);

	/**
	 * Create a siteplan Error dialog.
	 * 
	 * @param shell
	 *            the shell
	 * @param filename
	 *            the filename
	 * @return whether the model should be loaded (<code>true</code>) or whether
	 *         the loading should be aborted (<code>false</code>)
	 */
	boolean sitePlanError(Shell shell, String filename);

}
