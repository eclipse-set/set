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

import org.eclipse.set.core.services.Services;
import org.eclipse.set.core.services.version.PlanProVersionService;
import org.eclipse.set.model.validationreport.ValidationreportFactory;
import org.eclipse.set.model.validationreport.VersionInfo;
import org.eclipse.set.ppmodel.extensions.PlanProPackageExtensions;
import org.eclipse.set.ppmodel.extensions.SignalbegriffeRil301PackageExtensions;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * Implementation for {@link PlanProVersionService}.
 * 
 * @author Schaefer
 */
@Component
public class PlanProVersionServiceImpl implements PlanProVersionService {

	private static final String DELIMITER = ""; //$NON-NLS-1$
	private static final String KEY_VALUE_PATTERN = ".*xmlns:.*=\\s*[\'\"](%s[^\'\" ]+)[\'\"].*"; //$NON-NLS-1$
	private static final String PLAN_PRO_KEY = "http://www.plan-pro.org/modell/PlanPro/"; //$NON-NLS-1$
	private static final String SIGNALS_KEY = "http://www.plan-pro.org/modell/Signalbegriffe_Ril_301/"; //$NON-NLS-1$
	private static final int VALUE_GROUP = 1;

	private static final String VERSION_FORMAT = "(?<major>[1-9]+\\.[0-9]+)\\.(?<patch>[0-9]+)(\\.(?<minor>[0-9]))*"; //$NON-NLS-1$

	@Activate
	private void active() {
		Services.setPlanProVersionService(this);
	}

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

	private static PlanProVersionFormat parseVersionFormat(
			final String version) {
		final Pattern compile = Pattern.compile(VERSION_FORMAT);
		final Matcher matcher = compile.matcher(version);
		if (!matcher.matches()) {
			throw new IllegalArgumentException("Illegal Version Foramt"); //$NON-NLS-1$
		}

		return new PlanProVersionFormat(matcher.group("major"), //$NON-NLS-1$
				matcher.group("patch"), matcher.group("minor")); //$NON-NLS-1$ //$NON-NLS-2$

	}

	@Override
	public VersionInfo createSupportedVersion() {
		final VersionInfo versionInfo = ValidationreportFactory.eINSTANCE
				.createVersionInfo();
		final PlanProVersionFormat modelVersionFormat = parseVersionFormat(
				PlanProPackageExtensions.getModelVersion());
		versionInfo
				.setPlanPro(modelVersionFormat.getMajorPatchVersion() + ".*"); //$NON-NLS-1$
		final PlanProVersionFormat signalVersionFormat = parseVersionFormat(
				SignalbegriffeRil301PackageExtensions.getModelVersion());
		versionInfo
				.setSignals(signalVersionFormat.getMajorPatchVersion() + ".*"); //$NON-NLS-1$
		return versionInfo;
	}

	@Override
	public VersionInfo createUsedVersion(final Path location) {
		final VersionInfo versionInfo = ValidationreportFactory.eINSTANCE
				.createVersionInfo();

		final String text = getText(location);

		final String planProVersion = parseVersion(text, PLAN_PRO_KEY);

		versionInfo.setPlanPro(planProVersion != null ? planProVersion : ""); //$NON-NLS-1$
		versionInfo.setSignals(parseVersion(text, SIGNALS_KEY));

		return versionInfo;
	}

	@Override
	public PlanProVersionFormat getSupportedVersionFormat() {
		return parseVersionFormat(PlanProPackageExtensions.getModelVersion());
	}

	@Override
	public String getCurrentVersion() {
		return PlanProPackageExtensions.getModelVersion();
	}
}
