/**
 * Copyright (c) 2019 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.core.fileservice;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.set.basis.constants.ToolboxConstants;
import org.eclipse.set.basis.extensions.PathExtensions;
import org.eclipse.set.basis.files.SetFormat;
import org.eclipse.set.basis.files.ToolboxFile;
import org.eclipse.set.basis.files.ToolboxFile.Format;
import org.eclipse.set.basis.files.ToolboxFileAC;
import org.eclipse.set.basis.files.ToolboxFileRole;
import org.eclipse.set.core.services.files.ToolboxFileFormatService;
import org.eclipse.set.core.services.files.ToolboxFileService;
import org.eclipse.set.ppmodel.extensions.DocumentRootExtensions;
import org.eclipse.set.ppmodel.extensions.PlanProSchnittstelleExtensions;
import org.eclipse.set.toolboxmodel.PlanPro.DocumentRoot;
import org.eclipse.set.toolboxmodel.PlanPro.PlanProFactory;
import org.eclipse.set.toolboxmodel.PlanPro.PlanPro_Schnittstelle;
import org.eclipse.set.toolboxmodel.transform.IDReferenceUtils;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

import com.google.common.collect.Lists;

/**
 * Implementation of the {@link ToolboxFileService}
 * 
 * @author Schaefer
 */
@Component(immediate = true)
public class ToolboxFileServiceImpl implements ToolboxFileService {

	private final List<ToolboxFileFormatService> formats = Lists
			.newLinkedList();

	/**
	 * @param format
	 *            the toolbox file format service
	 */
	@Reference(cardinality = ReferenceCardinality.MULTIPLE, policy = ReferencePolicy.DYNAMIC)
	public void addFormat(final ToolboxFileFormatService format) {
		formats.add(format);
	}

	@Override
	public ToolboxFile convertFormat(final ToolboxFile toolboxFile,
			final ToolboxFileRole role, final Path tempDir,
			final Format format) {

		if (toolboxFile.getFormat() == format) {
			return toolboxFile;
		}
		final PlanPro_Schnittstelle oldschnittstelle = PlanProSchnittstelleExtensions
				.readFrom(toolboxFile.getPlanProResource());
		final PlanPro_Schnittstelle newschnittstelle = EcoreUtil
				.copy(oldschnittstelle);

		ToolboxFile newToolboxFile = toolboxFile;
		Path newPath = toolboxFile.getPath();
		if (format.isZippedPlanPro()) {
			newToolboxFile = convertToZipped(role);
			newPath = PathExtensions.replaceExtension(newPath,
					ToolboxConstants.EXTENSION_PLANPRO);
		} else if (format.isPlain()) {
			newToolboxFile = convertToPlain(role);
			newPath = PathExtensions.replaceExtension(newPath,
					ToolboxConstants.EXTENSION_PPXML);
		}

		final DocumentRoot documentRoot = PlanProFactory.eINSTANCE
				.createDocumentRoot();
		DocumentRootExtensions.fix(documentRoot);
		documentRoot.setPlanProSchnittstelle(newschnittstelle);
		newToolboxFile.getPlanProResource().getContents().add(documentRoot);
		newToolboxFile.setPath(newPath);
		IDReferenceUtils.retargetIDReferences(oldschnittstelle,
				newschnittstelle);
		return newToolboxFile;
	}

	@Override
	public ToolboxFile create(final Format format, final ToolboxFileRole role) {
		final Optional<ToolboxFileFormatService> formatService = formats
				.stream().filter(f -> f.isSupported(format)).findFirst();
		final ToolboxFile result = formatService
				.map(f -> f.create(format, role)).orElse(null);
		if (result == null) {
			throw new IllegalArgumentException(
					"path \"" + format.toString() + "\" not supported."); //$NON-NLS-1$ //$NON-NLS-2$
		}
		return result;
	}

	@Override
	public ToolboxFile create(final ToolboxFile toolboxFile) {
		final Optional<ToolboxFileFormatService> formatService = formats
				.stream().filter(f -> f.isSupported(toolboxFile)).findFirst();
		final ToolboxFile result = formatService.map(f -> f.create(toolboxFile))
				.orElse(null);
		if (result == null) {
			throw new IllegalArgumentException("toolbox file \"" //$NON-NLS-1$
					+ toolboxFile.getClass().getName() + "\" not supported."); //$NON-NLS-1$
		}
		return result;
	}

	@Override
	public List<String> extensionsForCategory(final String category) {
		return formats.stream()
				.flatMap(f -> f.extensionsForCategory(category).stream())
				.sorted((a, b) -> Integer.compare(b.getPriority(),
						a.getPriority()))
				.map(e -> e.getExtension()).collect(Collectors.toList());
	}

	@Override
	public ToolboxFile load(final Path path, final ToolboxFileRole role) {
		final Optional<ToolboxFileFormatService> formatService = formats
				.stream().filter(f -> f.isSupported(path)).findFirst();
		final ToolboxFile result = formatService.map(f -> f.load(path, role))
				.orElse(null);
		if (result == null) {
			throw new IllegalArgumentException(
					"path \"" + path.toString() + "\" not supported."); //$NON-NLS-1$ //$NON-NLS-2$
		}
		return result;
	}

	@Override
	public ToolboxFileAC loadAC(final Path path, final ToolboxFileRole role) {
		return new ToolboxFileAC(load(path, role));
	}

	/**
	 * @param format
	 *            the toolbox file format service
	 */
	public void removeFormat(final ToolboxFileFormatService format) {
		formats.remove(format);
	}

	private ToolboxFile convertToPlain(final ToolboxFileRole role) {
		return create(SetFormat.createPlainPlanPro(), role);
	}

	private ToolboxFile convertToZipped(final ToolboxFileRole role) {
		return create(SetFormat.createZippedPlanPro(), role);
	}
}
