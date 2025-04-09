/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.application.contribution;

import java.nio.file.Path;

import org.eclipse.set.application.handler.OpenPlanProHandler;
import org.eclipse.set.basis.ExampleFile;
import org.eclipse.set.core.services.example.ExampleService;
import org.eclipse.swt.widgets.Shell;

/**
 * This contribution to an menu item can load an example file.
 * 
 * @author Schaefer
 */
public class LoadExampleMenuItem extends OpenPlanProHandler {

	@Override
	protected Path chooseFile(final Shell shell) {
		final ExampleFile example = (ExampleFile) getMenuItem()
				.getTransientData()
				.get(ExampleService.EXAMPLE_FILE_KEY);
		return example.getPath();
	}
}
