/**
 * Copyright (c) 2019 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.pdf.utils.rendererservice;

import org.eclipse.set.core.services.pdf.PdfRendererService;
import org.eclipse.set.core.services.pdf.PdfViewer;
import org.eclipse.set.pdf.utils.BrowserPdfViewer;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

/**
 * Implementation of {@link PdfRendererService}.
 * 
 * @author Schaefer
 */
public class BrowserPdfRendererService implements PdfRendererService {
	@Override
	public PdfViewer createViewer(final Composite parent) {
		parent.setLayout(new FillLayout());
		return new BrowserPdfViewer(parent);
	}
}
