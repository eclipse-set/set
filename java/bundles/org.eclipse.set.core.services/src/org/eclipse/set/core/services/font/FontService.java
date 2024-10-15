/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.core.services.font;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Provides fonts.
 * 
 * @author Stuecker
 */
public interface FontService {
	@SuppressWarnings("javadoc")

	public record FopFont(Path path, String name, String weight, String style) {
		//
	}

	/**
	 * @return a list of fonts to register for fop
	 */
	Iterable<FopFont> getFopFonts();

	/**
	 * @return the default path of Toolbox Font
	 */
	@SuppressWarnings("nls")
	default Path getDefaultFont() {
		return Paths
				.get("data/fonts/Open_Sans_Condensed/OpenSans-CondLight.ttf");
	}
}
