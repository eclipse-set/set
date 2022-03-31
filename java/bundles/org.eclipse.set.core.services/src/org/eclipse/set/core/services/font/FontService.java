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

/**
 * Provides fonts.
 * 
 * @author Stuecker
 */
public interface FontService {
	/**
	 * @return the path to the font to use for the siteplan
	 */
	Path getSiteplanFont();

	// IMPROVE: Java-17: Use a record here
	/**
	 * Font definition
	 */
	@SuppressWarnings("javadoc")
	public class FopFont {
		public FopFont(final Path path, final String name, final String weight,
				final String style) {
			this.path = path;
			this.name = name;
			this.weight = weight;
			this.style = style;

		}

		public final Path path;
		public final String name;
		public final String weight;
		public final String style;
	}

	/**
	 * @return a list of fonts to register for fop
	 */
	Iterable<FopFont> getFopFonts();
}
