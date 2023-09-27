/**
 * Copyright (c) 2022 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.siteplan.transform;

import org.eclipse.set.model.siteplan.SiteplanState;
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup;

/**
 * Siteplan transformator
 */
public interface Transformator {
	/**
	 * @param state
	 *            the state to transform into
	 * @param container
	 *            the container to tranform
	 */
	void transformContainer(SiteplanState state,
			MultiContainer_AttributeGroup container);
}
