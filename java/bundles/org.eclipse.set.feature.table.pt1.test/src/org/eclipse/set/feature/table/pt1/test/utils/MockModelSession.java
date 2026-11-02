/**
 * Copyright (c) 2026 DB InfraGO AG and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 */
package org.eclipse.set.feature.table.pt1.test.utils;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.basis.Pair;
import org.eclipse.set.basis.ToolboxPaths;
import org.eclipse.set.basis.constants.ContainerType;
import org.eclipse.set.basis.constants.PlanProFileNature;
import org.eclipse.set.basis.constants.TableType;
import org.eclipse.set.basis.constants.ValidationResult;
import org.eclipse.set.basis.constants.ValidationResult.FileValidateState;
import org.eclipse.set.basis.constants.ValidationResult.Outcome;
import org.eclipse.set.basis.exceptions.UserAbortion;
import org.eclipse.set.basis.files.ToolboxFile;
import org.eclipse.set.model.planpro.Layoutinformationen.PlanPro_Layoutinfo;
import org.eclipse.set.model.planpro.PlanPro.PlanPro_Schnittstelle;
import org.eclipse.set.model.temporaryintegration.TemporaryIntegration;
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup;
import org.eclipse.swt.widgets.Shell;

/**
 * 
 */
public class MockModelSession implements IModelSession {
	PlanPro_Schnittstelle schnittstelle;
	ToolboxFile toolboxfile;

	public MockModelSession(PlanPro_Schnittstelle schnittstelle,
			ToolboxFile toolboxfile) {
		this.schnittstelle = schnittstelle;
		this.toolboxfile = toolboxfile;
	}

	@Override
	public void addSymbolRotation(double rotation) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void cancelMergeMode(Predicate<Path> confirmDeletion)
			throws IOException, UserAbortion {
		throw new UnsupportedOperationException();
	}

	@Override
	public void cleanUp() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void close() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void discardChanges() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void exitMergeMode(Shell shell) throws IOException, UserAbortion {
		throw new UnsupportedOperationException();
	}

	@Override
	public MultiContainer_AttributeGroup getContainer(ContainerType container) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getGuid() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Shell getMainWindow() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<EObject> getModels() {
		throw new UnsupportedOperationException();
	}

	@Override
	public PlanProFileNature getNature() {
		throw new UnsupportedOperationException();
	}

	@Override
	public PlanPro_Schnittstelle getPlanProSchnittstelle() {
		return schnittstelle;
	}

	@Override
	public PlanPro_Layoutinfo getLayoutInformation() {
		throw new UnsupportedOperationException();
	}

	@Override
	public double getSymbolRotation() {
		throw new UnsupportedOperationException();
	}

	@Override
	public TableType getTableType() {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<Pair<String, String>> getSelectedControlAreas() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isPlanningAreaIgnored() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Path getTempDir() {
		throw new UnsupportedOperationException();
	}

	@Override
	public ToolboxFile getToolboxFile() {
		return toolboxfile;
	}

	@Override
	public ToolboxPaths getToolboxPaths() {
		throw new UnsupportedOperationException();
	}

	@Override
	public ValidationResult getValidationResult(
			Class<? extends EObject> sourceClass) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Outcome getValidationsOutcome(
			Function<ValidationResult, Outcome> outcome) {
		throw new UnsupportedOperationException();
	}

	@Override
	public FileValidateState getFileValidateState() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void init() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isDirty() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isLoaded() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isMergeMode() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isNewProject() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isReportSavedDialogSuppressed(int kind) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void refreshValidation() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void revert() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean save(Shell shell) throws UserAbortion {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean save(Shell shell, boolean askUser) throws UserAbortion {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean saveNew(Shell shell) throws UserAbortion {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setReportSavedDialogSuppressed(int kind, boolean value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isSingleState() {
		throw new UnsupportedOperationException();
	}

	@Override
	public SaveFixResult getSaveFixResult() {
		throw new UnsupportedOperationException();
	}

	@Override
	public EditingDomain getEditingDomain() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Optional<TemporaryIntegration> getTemporaryIntegration() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void switchToMergeMode(TemporaryIntegration newTemporaryIntegration,
			String mergeDir, Shell shell, ToolboxFile temporaryToolboxFile)
			throws IOException, UserAbortion {
		throw new UnsupportedOperationException();
	}

}
