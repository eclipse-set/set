/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.validation.session;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.set.basis.Pair;
import org.eclipse.set.basis.ToolboxPaths;
import org.eclipse.set.basis.constants.ExportType;
import org.eclipse.set.basis.extensions.PathExtensions;
import org.eclipse.set.ppmodel.extensions.PlanProSchnittstelleExtensions;

import com.google.common.base.Strings;

/**
 * Implementation of {@link ToolboxPaths} using the model session.
 * 
 * @author Schaefer
 */
public class ToolboxPathsImpl implements ToolboxPaths {

	private final ModelSession modelSession;

	/**
	 * @param modelSession
	 *            the model session
	 */
	public ToolboxPathsImpl(final ModelSession modelSession) {
		this.modelSession = modelSession;
	}

	@Override
	public Path getTableExportPath(final String shortcut, final Path base,
			final ExportType exportType, final ExportPathExtension extension) {
		final List<String> pathPart = new LinkedList<>();
		pathPart.add(getModelBaseName(exportType));
		pathPart.add(StringUtils.capitalize(shortcut));

		final String areaName = getControlAreaName();
		if (!Strings.isNullOrEmpty(areaName)) {
			pathPart.add(areaName);
		}

		return Paths.get(base.toString(),
				String.join("_", pathPart) + extension.value); //$NON-NLS-1$
	}

	private String getModelBaseName(final ExportType exportType) {
		final Path derivedPath = PlanProSchnittstelleExtensions.getDerivedPath(
				modelSession.getPlanProSchnittstelle(), "xxx", "xxx", //$NON-NLS-1$ //$NON-NLS-2$
				exportType);
		return PathExtensions.getBaseFileName(derivedPath);
	}

	private String getControlAreaName() {
		final List<Pair<String, String>> controlAreaIds = modelSession
				.getSelectedControlAreas();
		if (controlAreaIds.size() != 1) {
			return null;
		}
		return controlAreaIds.getFirst().getFirst();
	}
}
