/**
 * Copyright (c) 2020 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.feature.plaz.version;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;

import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.set.basis.ui.VersionInfo;
import org.eclipse.set.core.services.version.AdditionalVersionService;
import org.eclipse.set.feature.plaz.Messages;
import org.eclipse.set.feature.plaz.service.PlazService;

import jakarta.inject.Inject;

/**
 * Add PlaZ Version information.
 * 
 * @author Schaefer
 */
public class PlazAdditionalVersionService implements AdditionalVersionService {

	@Inject
	@Translation
	private Messages messages;

	@Inject
	private PlazService plazService;

	@Override
	public void addInfo(final List<VersionInfo> versionInfo) {
		final VersionInfo plazVersionInfo = new VersionInfo();
		plazVersionInfo.label = messages.PlazAdditionalVersionService_PlazVersionLabel;
		plazVersionInfo.version = LocalDate.parse(plazService.getVersion())
				.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM));
		versionInfo.add(plazVersionInfo);
	}
}
