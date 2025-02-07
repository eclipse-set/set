/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.application.contribution;

import java.util.List;

import jakarta.inject.Inject;

import org.eclipse.e4.ui.di.AboutToShow;
import org.eclipse.e4.ui.model.application.ui.menu.MDirectMenuItem;
import org.eclipse.e4.ui.model.application.ui.menu.MMenuElement;
import org.eclipse.e4.ui.workbench.modeling.EModelService;

import org.eclipse.set.basis.ExampleFile;
import org.eclipse.set.core.services.example.ExampleService;

/**
 * Provides menu entries for example files.
 * 
 * @author Schaefer
 */
public class LoadExampleContribution {

	private static final String CONTRIBUTION_CLASS = "bundleclass://org.eclipse.set.application/org.eclipse.set.application.contribution.LoadExampleMenuItem"; //$NON-NLS-1$
	private static final String CONTRIBUTOR_PLUGIN = "platform:/plugin/org.eclipse.set.application"; //$NON-NLS-1$

	private static void addExample(final ExampleFile exampleFile,
			final EModelService modelService, final List<MMenuElement> items) {
		final MDirectMenuItem directMenuItem = modelService
				.createModelElement(MDirectMenuItem.class);
		directMenuItem.setLabel(exampleFile.getLabel());
		directMenuItem.getTransientData()
				.put(ExampleService.EXAMPLE_FILE_KEY, exampleFile);
		directMenuItem.setContributorURI(CONTRIBUTOR_PLUGIN);
		directMenuItem.setContributionURI(CONTRIBUTION_CLASS);
		items.add(directMenuItem);
	}

	@Inject
	private ExampleService exampleService;

	@AboutToShow
	private void aboutToShow2(final List<MMenuElement> items,
			final EModelService modelService) {
		final List<ExampleFile> exampleFiles = exampleService.getExampleFiles();
		for (final ExampleFile exampleFile : exampleFiles) {
			addExample(exampleFile, modelService, items);
		}
	}
}
