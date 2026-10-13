/**
 * Copyright (c) 2026 DB InfraGO AG and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 */
package org.eclipse.set.basis.constants;

import java.util.Arrays;

/**
 * The contents of a planpro zip file
 * 
 * @author Truong
 */
@SuppressWarnings("nls")
public enum PlanProZipFileContent {
	/**
	 * The content.xml
	 */

	CONTENT_XML("content.xml"),
	/**
	 * The layout.xml
	 */
	LAYOUT_XML("layout.xml"),
	/**
	 * the manifest.xml
	 */
	MANIFEST_XML("manifest.xml"),
	/**
	 * The media directory
	 */
	MEDIA_DIRECTORY("media");

	String fileName;

	private PlanProZipFileContent(final String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the string value
	 */
	@Override
	public String toString() {
		return fileName;
	}

	/**
	 * @param value
	 *            the file name to compare
	 * @return whether file name equals
	 */
	public boolean equalsIgnoreCase(final String value) {
		return fileName.equals(value);
	}

	/**
	 * @param fileName
	 *            the file name
	 * @return true, if the {@link PlanProZipFileContent} contains this file
	 */
	public static boolean isPlanProZipFileContent(final String fileName) {
		return Arrays.stream(PlanProZipFileContent.values())
				.map(PlanProZipFileContent::toString)
				.anyMatch(f -> f.equals(fileName));
	}

}
