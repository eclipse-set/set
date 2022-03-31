/** 
 * Copyright (c) 2022 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.export.fop;

import org.apache.fop.render.intermediate.IFContext;
import org.apache.fop.render.intermediate.IFDocumentHandlerConfigurator;
import org.apache.fop.render.pdf.PDFDocumentHandler;
import org.apache.fop.render.pdf.PDFRendererConfig.PDFRendererConfigParser;
import org.eclipse.set.core.services.font.FontService;

/**
 * PDF Document handler providing a modified configurator using a font service
 * 
 * @author Stuecker
 */
public class FopPDFDocumentHandler extends PDFDocumentHandler {
	private final FontService fontService;

	/**
	 * @param context
	 *            the context
	 * @param fontService
	 *            the font service
	 */
	public FopPDFDocumentHandler(final IFContext context,
			final FontService fontService) {
		super(context);
		this.fontService = fontService;
	}

	@Override
	public IFDocumentHandlerConfigurator getConfigurator() {
		return new FopPDFRendererConfigurator(getUserAgent(),
				new PDFRendererConfigParser(), fontService);
	}

}