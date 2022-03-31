/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.custom.extensions

import org.eclipse.set.basis.files.ToolboxFileFilter
import java.util.List
import org.eclipse.swt.widgets.FileDialog

/**
 * Extensions for {@link FileDialog}.
 * 
 * @author Schaefer
 */
class FileDialogExtensions {

	/**
	 * @param dialog this {@link FileDialog}
	 * @param extensions the extensions to set
	 */
	static def void setExtensionFilters(
		FileDialog dialog,
		List<ToolboxFileFilter> extensions
	) {
		if (!extensions.empty) {
			dialog.setFilterNames = extensions.map[filterName]
			dialog.setFilterExtensions = extensions.map[filterExtensions]
		}
	}
}
