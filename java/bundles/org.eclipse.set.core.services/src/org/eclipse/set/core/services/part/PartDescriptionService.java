/**
 * Copyright (c) 2020 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.core.services.part;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.set.basis.part.PartDescription;
import org.eclipse.set.basis.viewgroups.ToolboxViewGroup;

/**
 * Provide a part description.
 * 
 * @author Schaefer
 */
public interface PartDescriptionService {

	/**
	 * @param context
	 *            the eclipse context for injection helpers
	 * 
	 * @return the part description
	 */
	PartDescription getDescription(IEclipseContext context);

	/**
	 * @return the view type
	 */
	ToolboxViewGroup getToolboxViewGroup();
}
