/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.core.version;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.osgi.service.component.annotations.Component;

import org.eclipse.set.core.services.version.PlanProVersionService;
import org.eclipse.set.model.validationreport.ValidationreportFactory;
import org.eclipse.set.model.validationreport.VersionInfo;
import org.eclipse.set.ppmodel.extensions.PlanProPackageExtensions;
import org.eclipse.set.ppmodel.extensions.SignalbegriffeRil301PackageExtensions;

/**
 * Implementation for {@link PlanProVersionService}.
 * 
 * @author Schaefer
 */
@Component
public class PlanProVersionServiceImpl implements PlanProVersionService {

	private static final String DELIMITER = ""; //$NON-NLS-1$
	private static final String INTEGRATION_KEY = "tag:scheidt-bachmann-st.de,2018-06-29:planpro/temporaryintegration/"; //$NON-NLS-1$
	private static final String KEY_VALUE_PATTERN = ".*xmlns:.*=\\s*[\'\"](%s[^\'\" ]+)[\'\"].*"; //$NON-NLS-1$
	private static final String PLAN_PRO_KEY = "http://www.plan-pro.org/modell/PlanPro/"; //$NON-NLS-1$
	private static final String SIGNALS_KEY = "http://www.plan-pro.org/modell/Signalbegriffe_Ril_301/"; //$NON-NLS-1$
	private static final int VALUE_GROUP = 1;

	private static String getText(final Path location) {
		List<String> lines;
		try {
			lines = Files.readAllLines(location);
		} catch (final IOException e) {
			// we ignore I/O exceptions and return an empty string
			return ""; //$NON-NLS-1$
		}

		// we only inspect the first 10 lines
		final List<String> subList = lines.subList(0,
				Math.min(10, lines.size()));

		return subList.stream().collect(Collectors.joining(DELIMITER));
	}

	private static String parseVersion(final String text, final String key) {
		final Pattern p = Pattern
				.compile(String.format(KEY_VALUE_PATTERN, key));
		final Matcher m = p.matcher(text);
		if (m.matches()) {
			final String fullVersion = m.group(VALUE_GROUP);
			return fullVersion.substring(fullVersion.lastIndexOf('/') + 1);
		}
		return null;
	}

	@Override
	public VersionInfo createSupportedVersion() {
		final VersionInfo versionInfo = ValidationreportFactory.eINSTANCE
				.createVersionInfo();
		versionInfo.setPlanPro(PlanProPackageExtensions.getModelVersion());
		versionInfo.setSignals(
				SignalbegriffeRil301PackageExtensions.getModelVersion());
		return versionInfo;
	}

	@Override
	public VersionInfo createUsedVersion(final Path location) {
		final VersionInfo versionInfo = ValidationreportFactory.eINSTANCE
				.createVersionInfo();

		final String text = getText(location);

		final String planProVersion = parseVersion(text, PLAN_PRO_KEY);
		final String integrationVersion = parseVersion(text, INTEGRATION_KEY);

		versionInfo.setPlanPro(
				planProVersion != null ? planProVersion : integrationVersion);
		versionInfo.setSignals(parseVersion(text, SIGNALS_KEY));

		return versionInfo;
	}
}
