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

import org.eclipse.e4.ui.di.AboutToShow;
import org.eclipse.e4.ui.model.application.ui.menu.MDirectMenuItem;
import org.eclipse.e4.ui.model.application.ui.menu.MMenuElement;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.set.basis.RecentOpenFile;
import org.eclipse.set.core.services.example.ExampleService;

import jakarta.inject.Inject;

/**
 * Provides menu entries for example files.
 * 
 * @author Schaefer
 */
public class LoadExampleContribution {

	private static final String CONTRIBUTION_CLASS = "bundleclass://org.eclipse.set.application/org.eclipse.set.application.handler.OpenPlanProHandler"; //$NON-NLS-1$
	private static final String CONTRIBUTOR_PLUGIN = "platform:/plugin/org.eclipse.set.application"; //$NON-NLS-1$

	protected void addExample(final RecentOpenFile exampleFile,
			final EModelService modelService, final List<MMenuElement> items) {
		items.add(createDirectMenuItem(modelService, exampleFile));
	}

	protected MDirectMenuItem createDirectMenuItem(
			final EModelService modelService,
			final RecentOpenFile exampleFile) {
		final MDirectMenuItem directMenuItem = modelService
				.createModelElement(MDirectMenuItem.class);
		directMenuItem.setLabel(exampleFile.getLabel());
		directMenuItem.getTransientData()
				.put(ExampleService.EXAMPLE_FILE_KEY, exampleFile);
		directMenuItem.setContributorURI(CONTRIBUTOR_PLUGIN);
		directMenuItem.setContributionURI(getItemContributionClass());
		return directMenuItem;
	}

	@Inject
	private ExampleService exampleService;

	@AboutToShow
	protected void aboutToShow2(final List<MMenuElement> items,
			final EModelService modelService) {
		final List<RecentOpenFile> exampleFiles = exampleService
				.getExampleFiles();
		for (final RecentOpenFile exampleFile : exampleFiles) {
			addExample(exampleFile, modelService, items);
		}
	}

	@SuppressWarnings("static-method")
	protected String getItemContributionClass() {
		return CONTRIBUTION_CLASS;
	}
}
