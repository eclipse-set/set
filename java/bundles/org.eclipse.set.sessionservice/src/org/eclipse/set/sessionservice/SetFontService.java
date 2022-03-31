/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.sessionservice;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.eclipse.set.core.services.font.FontService;
import org.osgi.service.component.annotations.Component;

/**
 * PPT implementation of {@link FontService}.
 * 
 * @author Stuecker
 */
@Component
@SuppressWarnings("nls")
public class SetFontService implements FontService {
	@Override
	public Path getSiteplanFont() {
		return Paths
				.get("data/fonts/Open_Sans_Condensed/OpenSans-CondLight.ttf"); //$NON-NLS-1$
	}

	@Override
	public Iterable<FopFont> getFopFonts() {
		return List.of( //
				new FopFont(Paths.get(
						"data/fonts/Open_Sans_Condensed/OpenSans-CondLight.ttf"),
						"Open Sans Condensed", "normal", "normal"),
				new FopFont(Paths.get(
						"data/fonts/Open_Sans_Condensed/OpenSans-CondBold.ttf"),
						"Open Sans Condensed", "bold", "normal"));
	}
}
