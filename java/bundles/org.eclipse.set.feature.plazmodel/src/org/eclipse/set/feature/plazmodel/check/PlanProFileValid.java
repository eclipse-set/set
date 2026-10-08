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

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import org.eclipse.core.runtime.Assert;
import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.basis.PlanProXMLNode;
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
	List<String> EXPECT_FILES_NAME = List.of("content.xml", "layout.xml",
			"manifest.xml", "media");

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
		final PlazError validManifestContent = validManifestContent(
				unzipDirectory);
		if (validManifestContent != null) {
			result.add(validManifestContent);
		}

		return result;
	}

	private PlazError validManifestContent(final Path unzipDirectory) {
		final List<File> listFiles = Arrays
				.asList(unzipDirectory.toFile().listFiles());
		final Optional<File> manifestFileOpt = listFiles.stream()
				.filter(f -> f.getName().equalsIgnoreCase("manifest.xml"))
				.findFirst();
		if (manifestFileOpt.isEmpty()) {
			return null;
		}
		try {
			boolean error = false;
			final PlanProXMLNode manifesXMLNode = PlanProXMLNode
					.read(manifestFileOpt.get().toPath());
			final Set<PlanProXMLNode> evaluateXPath = PlanProXMLNode
					.evaluateXPath(manifesXMLNode, "//manifest");
			if (evaluateXPath.size() != 1) {
				return createError("manifest.xml fehlende Inhalt");
			}
			final PlanProXMLNode manifestNode = evaluateXPath.stream()
					.toList()
					.getFirst();
			for (final PlanProXMLNode child : manifestNode.getChildren()) {
				if (child.getNodeName().equals("contentList")) {
					error = inValidXMLNode(child, "content", "name",
							"content.xml");
				}

				if (child.getNodeName().equals("layoutList")
						&& !child.getChildren().isEmpty()) {
					error = listFiles.stream()
							.noneMatch(f -> f.getName().equals("layout.xml"))
							|| inValidXMLNode(child, "layout", "name",
									"layout.xml");
				}

				if (child.getNodeName().equals("mediaList")
						&& !child.getChildren().isEmpty()) {
					final Optional<File> mediaDirectory = listFiles.stream()
							.filter(f -> f.isDirectory()
									&& f.getName().equals("media"))
							.findFirst();
					error = !mediaDirectory.isEmpty()
							&& !validMedias(child, mediaDirectory.get());

				}
			}
			if (error) {
				return createError("manifest.xml fehlende Inhalt");
			}

		} catch (final Exception e) {
			return createError(e.getMessage());
		}
		return null;
	}

	private boolean validMedias(final PlanProXMLNode mediaListNode,
			final File mediaDirectory) {
		final File[] medias = mediaDirectory.listFiles();
		if (mediaListNode.getChildren().size() != medias.length) {
			return false;
		}
		for (final File media : medias) {
			final String mediaGUID = media.getName();
			if (inValidXMLNode(mediaListNode, "media", "guid", mediaGUID)) {
				return false;
			}
		}
		return true;
	}

	private boolean inValidXMLNode(final PlanProXMLNode node,
			final String nodeName, final String attributeName,
			final String attributeValue) {
		if (node.getNodeName().equals(nodeName)) {
			return node.getAttributes()
					.stream()
					.noneMatch(attribute -> attribute.attributeName()
							.equalsIgnoreCase(attributeName)
							&& attribute.value()
									.equalsIgnoreCase(attributeValue));
		}
		return node.getChildren().isEmpty() || node.getChildren()
				.stream()
				.anyMatch(child -> inValidXMLNode(child, nodeName,
						attributeName, attributeValue));

	}

	private List<PlazError> validPlanProStructure(final Path unzipDirectory) {
		final File[] files = unzipDirectory.toFile().listFiles();
		if (files.length != EXPECT_FILES_NAME.size()) {
			return List.of(createError(getGeneralErrMsg()));
		}

		final List<File> missingFiles = Stream.of(files)
				.filter(f -> !EXPECT_FILES_NAME.contains(f.getName()))
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
