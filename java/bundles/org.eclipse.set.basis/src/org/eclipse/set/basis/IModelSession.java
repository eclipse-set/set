/**
 * Copyright (c) 2015 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.set.basis.constants.ContainerType;
import org.eclipse.set.basis.constants.PlanProFileNature;
import org.eclipse.set.basis.constants.TableType;
import org.eclipse.set.basis.constants.ValidationResult;
import org.eclipse.set.basis.constants.ValidationResult.Outcome;
import org.eclipse.set.basis.exceptions.UserAbortion;
import org.eclipse.set.basis.files.ToolboxFile;
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup;
import org.eclipse.set.toolboxmodel.PlanPro.PlanPro_Schnittstelle;
import org.eclipse.swt.widgets.Shell;

/**
 * A model session provides the context for working with a PlanPro data model.
 * 
 * @author bleidiessel
 */
public interface IModelSession {

	/**
	 * Additional rotation to all symbols.
	 * 
	 * @param rotation
	 *            the additional rotation (counterclockwise in degrees)
	 */
	void addSymbolRotation(double rotation);

	/**
	 * Cancel merging.
	 * 
	 * @param confirmDeletion
	 *            confirm deletion predicate
	 * @throws IOException
	 *             an I/O exception of some sort has occurred
	 * @throws UserAbortion
	 *             if the user aborts deletion/canceling
	 */
	void cancelMergeMode(final Predicate<Path> confirmDeletion)
			throws IOException, UserAbortion;

	/**
	 * clean up temporary files.
	 */
	void cleanUp();

	/**
	 * Close the session.
	 */
	void close();

	/**
	 * Discard any unsaved changes to the PlanPro model.
	 * 
	 * @param source
	 *            the source view
	 */
	void discardChanges();

	/**
	 * Finish merging.
	 * 
	 * @param confirmOverwrite
	 *            confirm overwrite predicate
	 * @param shell
	 *            the shell
	 * @throws IOException
	 *             an I/O exception of some sort has occurred
	 * @throws UserAbortion
	 *             if the user aborts saving
	 */
	void exitMergeMode(Shell shell) throws IOException, UserAbortion;

	/**
	 * @param container
	 *            the container type
	 * 
	 * @return the container
	 */
	MultiContainer_AttributeGroup getContainer(ContainerType container);

	/**
	 * @return the editing domain for the loaded resource
	 */
	EditingDomain getEditingDomain();

	/**
	 * @return the guid of the model session
	 */
	String getGuid();

	/**
	 * @return the main window
	 */
	Shell getMainWindow();

	/**
	 * @return the models of this session
	 */
	Set<EObject> getModels();

	/**
	 * @return the {@link PlanProFileNature nature} of the model session
	 */
	PlanProFileNature getNature();

	/**
	 * @return root of the PlanPro model
	 */
	PlanPro_Schnittstelle getPlanProSchnittstelle();

	/**
	 * @return the total symbol rotation (counterclockwise in degrees)
	 */
	double getSymbolRotation();

	/**
	 * @return the (global) table type
	 */
	TableType getTableType();

	/**
	 * @return the temporary directory of this session
	 */
	Path getTempDir();

	/**
	 * Provide the optional temporary integration. A temporary integration
	 * exists, if the model session is in merge mode.
	 * 
	 * @return the optional temporary integration
	 */
	/*
	 * TODO(1.10.0.1): Readd once temporary integrations are reenabled
	 * Optional<ToolboxTemporaryIntegration> getTemporaryIntegration();
	 */
	/**
	 * @return the toolbox file
	 */
	ToolboxFile getToolboxFile();

	/**
	 * @return the toolbox paths
	 */
	ToolboxPaths getToolboxPaths();

	/**
	 * @param sourceClass
	 *            the class of source to validate
	 * @return the result of the last validation
	 * 
	 */
	ValidationResult getValidationResult(Class<? extends EObject> sourceClass);

	/**
	 * Get outcome result of validation
	 * 
	 * @param outcome
	 *            outcome type
	 * @return {@link Outcome}
	 */
	Outcome getValidationsOutcome(Function<ValidationResult, Outcome> outcome);

	/**
	 * Validates and loads the model resource and the PlanPro Schnittstelle.
	 */
	void init();

	/**
	 * @return whether there are unsaved changes in the session model
	 */
	boolean isDirty();

	/**
	 * @return whether the PlanPro model was loaded
	 */
	boolean isLoaded();

	/**
	 * @return whether the session is in merge mode
	 */
	boolean isMergeMode();

	/**
	 * @return <code>true</code>, if the session was created via new project,
	 *         <code>false</code> otherwise.
	 */
	boolean isNewProject();

	/**
	 * @param kind
	 *            the dialog kind
	 * 
	 * @return whether a dialog of the given kind should be suppressed
	 */
	boolean isReportSavedDialogSuppressed(int kind);

	/**
	 * Refresh validation information of the session.
	 */
	void refreshValidation();

	/**
	 * Revert any unsaved changes of the session.
	 */
	void revert();

	/**
	 * @param shell
	 *            the shell for displaying user dialogs
	 * 
	 * @return whether saving was completed without error
	 * 
	 * @throws UserAbortion
	 *             if the user aborts saving
	 */
	boolean save(Shell shell) throws UserAbortion;

	/**
	 * Save the loaded PlanPro file.
	 * 
	 * @param shell
	 *            the shell for displaying user dialogs
	 * @param askUser
	 *            whether to ask the user when renaming a file
	 * 
	 * @return whether saving was completed without error
	 * 
	 * @throws UserAbortion
	 *             if the user aborts saving
	 */
	boolean save(Shell shell, boolean askUser) throws UserAbortion;

	/**
	 * @param shell
	 *            the shell for displaying user dialogs
	 * 
	 * @return whether saving was completed without error
	 * 
	 * @throws UserAbortion
	 *             if the user aborts saving
	 */
	boolean saveNew(Shell shell) throws UserAbortion;

	/**
	 * @param kind
	 *            the dialog kind
	 * @param value
	 *            whether a dialog of the given kind should be suppressed
	 */
	void setReportSavedDialogSuppressed(int kind, boolean value);

	/**
	 * @return whether the loaded data is a single state. Returns false if no
	 *         data is loaded
	 */
	boolean isSingleState();

	/**
	 * Switch the session to merge mode.
	 * 
	 * @param temporaryIntegration
	 *            the temporary integration
	 * @param mergeDir
	 *            the merge dir
	 * @param shell
	 *            the shell for displaying user dialogs
	 * @param temporaryToolboxFile
	 *            the temporary ToolboxFile
	 * @throws IOException
	 *             if an I/O exception of some sort has occurred
	 * @throws UserAbortion
	 *             if the user aborts saving
	 */
	/*
	 * TODO(1.10.0.1): Readd once temporary integrations are reenabled void
	 * switchToMergeMode(ToolboxTemporaryIntegration temporaryIntegration,
	 * String mergeDir, Shell shell, ToolboxFile temporaryToolboxFile) throws
	 * IOException, UserAbortion;
	 */
	/**
	 * The applied level of save fixes
	 */
	enum SaveFixResult {
		NONE, OBJEKTMANAGEMENT, GLOBAL
	}

	/**
	 * @return the applied level of save fixes from the last save
	 */
	SaveFixResult getSaveFixResult();
}
