/**
 * Copyright (c) 2019 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.core.services.pdf;

import org.eclipse.swt.widgets.Composite;

/**
 * A pdf renderer service can create pdf viewers.
 * 
 * @author Schaefer
 */
public interface PdfRendererService {

	/**
	 * @param parent
	 *            the composite used by the viewer to display pdf documents
	 * 
	 * @return the new pdf viewer
	 */
	PdfViewer createViewer(Composite parent);
}
