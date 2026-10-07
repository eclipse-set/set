/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.application.exampleservice;

import java.io.File;
import java.io.FilenameFilter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.set.basis.RecentOpenFile;
import org.eclipse.set.basis.constants.ToolboxConstants;
import org.eclipse.set.basis.extensions.PathExtensions;
import org.eclipse.set.core.services.example.ExampleService;
import org.eclipse.set.core.services.files.ToolboxFileService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Implementation for the {@link ExampleService}.
 * 
 * @author Schaefer
 * 
 * @usage production
 */
@Component(immediate = true)
public class ExampleServiceImpl implements ExampleService {
	private static final String EXAMPLE_PROJECT_1_10_0_1_LABEL = "Beispielprojekt P-Hausen 1.10.0.1 laden"; //$NON-NLS-1$
	private static final String EXAMPLE_PROJECT_1_10_0_3_LABEL = "Beispielprojekt P-Hausen 1.10.0.3 laden"; //$NON-NLS-1$
	private static final String EXAMPLE_DIR = "examples"; //$NON-NLS-1$

	private List<RecentOpenFile> exampleFiles;

	ToolboxFileService fileService;

	@Override
	public List<RecentOpenFile> getExampleFiles() {
		if (exampleFiles == null) {
			exampleFiles = findFiles();
		}
		return exampleFiles;
	}

	/**
	 * @param fileService
	 *            the file service
	 */
	@Reference
	public void setFileService(final ToolboxFileService fileService) {
		this.fileService = fileService;
	}

	private List<RecentOpenFile> findFiles() {
		final Path pExamplesDir = Paths.get(EXAMPLE_DIR).toAbsolutePath();
		final File fExamplesDir = pExamplesDir.toFile();
		final String[] examples = fExamplesDir.list(new FilenameFilter() {

			@Override
			public boolean accept(final File dir, final String name) {
				final List<String> extensions = fileService
						.extensionsForCategory(
								ToolboxConstants.EXTENSION_CATEGORY_PPFILE);
				return extensions
						.contains(PathExtensions.getExtension(Paths.get(name)));
			}
		});

		final LinkedList<RecentOpenFile> result = new LinkedList<>();
		if (examples != null) {
			for (final String filename : examples) {
				if (filename.equalsIgnoreCase(
						ToolboxConstants.EXAMPLE_PROJECT_1_10_0_1)) {
					result.add(
							new RecentOpenFile(EXAMPLE_PROJECT_1_10_0_1_LABEL,
									Paths.get(EXAMPLE_DIR, filename)));
				} else if (filename.equalsIgnoreCase(
						ToolboxConstants.EXAMPLE_PROJECT_1_10_0_3)) {
					result.add(
							new RecentOpenFile(EXAMPLE_PROJECT_1_10_0_3_LABEL,
									Paths.get(EXAMPLE_DIR, filename)));
				} else {
					result.add(new RecentOpenFile(filename,
							Paths.get(EXAMPLE_DIR, filename)));
				}

			}
		}
		return result;
	}
}
