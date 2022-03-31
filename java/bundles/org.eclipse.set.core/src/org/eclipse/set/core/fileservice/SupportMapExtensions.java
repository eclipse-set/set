/**
 * Copyright (c) 2020 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.core.fileservice;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.set.basis.files.ToolboxFileExtension;

/**
 * Extensions for support maps.
 * 
 * @author Schaefer
 */
public class SupportMapExtensions {

	/**
	 * @param supportMap
	 *            the support map
	 * 
	 * @return the set of supported extensions
	 */
	public static Set<String> getSupportedExtensions(
			final Map<String, Set<ToolboxFileExtension>> supportMap) {
		return supportMap.values().stream().flatMap(Set::stream)
				.map(ToolboxFileExtension::getExtension)
				.collect(Collectors.toSet());
	}
}
