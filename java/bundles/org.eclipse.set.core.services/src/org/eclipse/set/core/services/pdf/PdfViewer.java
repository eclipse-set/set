/**
 * Copyright (c) 2019 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.core.services.pdf;

import java.nio.file.Path;
import java.util.Optional;

/**
 * A pdf viewer can render a pdf document and set different view modes.
 * 
 * @author Schaefer
 */
public interface PdfViewer {
	/**
	 * Interface for user requested saves
	 */
	public interface SaveListener {
		/**
		 * Called when the user requests to save the file
		 * 
		 * @param filename
		 *            the raw filename
		 * @return Optional.empty if the file should not be saved or A path to
		 *         the storage location to save or
		 */
		Optional<Path> saveFile(String filename);

		/**
		 * Called when the file was saved
		 * 
		 * @param path
		 *            the file location
		 */
		void saveCompleted(Path path);
	}

	/**
	 * Refresh the view.
	 */
	void refresh();

	/**
	 * Show the pdf document at the given path
	 * 
	 * @param path
	 *            the path
	 */
	void show(Path path);

	/**
	 * @param saveListener
	 *            the save listener
	 */
	void setSaveListener(SaveListener saveListener);
}
