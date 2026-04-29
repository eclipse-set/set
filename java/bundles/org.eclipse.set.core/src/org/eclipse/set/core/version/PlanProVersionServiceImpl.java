/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.core.version;

import static org.eclipse.set.core.services.version.PlanProVersionService.PlanProVersionFormat.parseVersionFormat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.eclipse.set.basis.PlanProSchemaDir;
import org.eclipse.set.core.services.Services;
import org.eclipse.set.core.services.version.PlanProVersionService;
import org.eclipse.set.model.planpro.PlanPro.PlanProPackage;
import org.eclipse.set.model.planpro.Signalbegriffe_Ril_301.Signalbegriffe_Ril_301Package;
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

	@Override
	public VersionInfo getSupportedVersions() {
		final VersionInfo versionInfo = ValidationreportFactory.eINSTANCE
				.createVersionInfo();
		final List<String> modelVersionsSupported = getSupportedVersions(
				PlanProPackage.eNAME);

		versionInfo.getPlanProVersions().addAll(modelVersionsSupported);
		final List<String> signalbegriffSupportedVersion = getSupportedVersions(
				Signalbegriffe_Ril_301Package.eNAME);
		versionInfo.getSignalbegriffeVersions()
				.addAll(signalbegriffSupportedVersion);
		return versionInfo;
	}

	private static List<String> getSupportedVersions(final String packageName) {
		return PlanProSchemaDir.getSchemaPaths()
				.stream()
				.filter(p -> p.getFileName()
						.toString()
						.equals(packageName + ".xsd")) //$NON-NLS-1$
				.map(Path::getParent)
				.map(Path::getFileName)
				.map(Path::toString)
				.collect(Collectors.toSet())
				.stream()
				.sorted(PlanProVersionFormat.compareVersion())
				.toList();
	}

	@Override
	public VersionInfo createUsedVersion(final Path location) {
		final VersionInfo versionInfo = ValidationreportFactory.eINSTANCE
				.createVersionInfo();

		final String text = getText(location);

		final String planProVersion = parseVersion(text, PLAN_PRO_KEY);

		versionInfo.getPlanProVersions()
				.add(planProVersion != null ? planProVersion : ""); //$NON-NLS-1$
		versionInfo.getSignalbegriffeVersions()
				.add(parseVersion(text, SIGNALS_KEY));

		return versionInfo;
	}

	@Override
	public PlanProVersionFormat getSupportedVersionFormat() {
		return parseVersionFormat(PlanProPackageExtensions.getModelVersion());
	}

	@Override
	public VersionInfo getCurrentVersion() {
		final VersionInfo versionInfo = ValidationreportFactory.eINSTANCE
				.createVersionInfo();
		final String planProVersion = PlanProPackageExtensions
				.getModelVersion();
		versionInfo.getPlanProVersions().add(planProVersion);

		final String signalBegriffeVersion = SignalbegriffeRil301PackageExtensions
				.getModelVersion();
		versionInfo.getSignalbegriffeVersions().add(signalBegriffeVersion);
		return versionInfo;
	}

	@Override
	public boolean isSupportedVersion(final String uri) {
		final VersionInfo supportedVersions = getSupportedVersions();
		final String loadedVersion = uri.substring(uri.lastIndexOf("/") + 1); //$NON-NLS-1$
		if (uri.startsWith(PLAN_PRO_KEY)) {
			return supportedVersions.getPlanProVersions()
					.contains(loadedVersion);
		}

		if (uri.startsWith(SIGNALS_KEY)) {
			return supportedVersions.getSignalbegriffeVersions()
					.contains(loadedVersion);
		}
		return false;
	}
}
