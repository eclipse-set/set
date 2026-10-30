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
package org.eclipse.set.feature.plazmodel.check;

import static org.eclipse.set.basis.constants.PlanProZipFileContent.isPlanProZipFileContent;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import org.eclipse.core.runtime.Assert;
import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.basis.constants.PlanProZipFileContent;
import org.eclipse.set.basis.files.ToolboxFile;
import org.eclipse.set.model.plazmodel.PlazError;
import org.eclipse.set.model.plazmodel.PlazFactory;
import org.eclipse.set.model.validationreport.ValidationSeverity;
import org.osgi.service.component.annotations.Component;

/**
 * Valid the structure of loaded File
 * 
 * @author Truong
 */
@Component
@SuppressWarnings("nls")
public class PlanProFileValid implements PlazCheck {
	// IMPROVE: the content of manifest file should also valid. Currently
	// given't xls schema for this file
	@Override
	public List<PlazError> run(final IModelSession modelSession) {
		final ToolboxFile toolboxFile = modelSession.getToolboxFile();
		if (!toolboxFile.getFormat().isZippedPlanPro()) {
			return Collections.emptyList();
		}
		final List<PlazError> result = new ArrayList<>();
		final Path unzipDirectory = toolboxFile.getModelPath().getParent();
		Assert.isTrue(Files.isDirectory(unzipDirectory));
		result.addAll(validPlanProStructure(unzipDirectory));

		return result;
	}

	private List<PlazError> validPlanProStructure(final Path unzipDirectory) {
		final File[] files = unzipDirectory.toFile().listFiles();
		if (files.length != PlanProZipFileContent.values().length) {
			return List.of(createError(getGeneralErrMsg()));
		}

		final List<File> missingFiles = Stream.of(files)
				.filter(f -> !isPlanProZipFileContent(f.getName()))
				.toList();
		if (!missingFiles.isEmpty()) {
			return List.of(createError(getGeneralErrMsg()));
		}
		return Collections.emptyList();
	}

	private PlazError createError(final String messages) {
		final PlazError plazError = PlazFactory.eINSTANCE.createPlazError();
		plazError.setSeverity(ValidationSeverity.WARNING);
		plazError.setMessage(messages);
		plazError.setType(checkType());
		return plazError;
	}

	@Override
	public String checkType() {
		return "Dateiinhalte";
	}

	@Override
	public String getDescription() {
		return "Die geladene Datei entspricht der Struktur einer PlanPro-Datei.";
	}

	@Override
	public String getGeneralErrMsg() {
		return "Die geladene Datei ist nicht konform zur erwarteten PlanPro-Struktur.";
	}

}
