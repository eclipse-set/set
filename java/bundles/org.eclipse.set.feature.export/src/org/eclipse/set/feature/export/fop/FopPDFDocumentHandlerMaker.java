/** 
 * Copyright (c) 2022 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.export.fop;

import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.render.intermediate.IFContext;
import org.apache.fop.render.intermediate.IFDocumentHandler;
import org.apache.fop.render.pdf.PDFDocumentHandlerMaker;
import org.eclipse.set.core.services.font.FontService;

/**
 * Document handler maker to supply our custom pdf document handler for PDFs
 * 
 * See {@link PDFDocumentHandlerMaker}
 * 
 * @author Stuecker
 *
 */
public class FopPDFDocumentHandlerMaker extends PDFDocumentHandlerMaker {
	final FontService fontService;

	/**
	 * @param fontService
	 *            the font service
	 */
	public FopPDFDocumentHandlerMaker(final FontService fontService) {
		super();
		this.fontService = fontService;
	}

	@Override
	public IFDocumentHandler makeIFDocumentHandler(final IFContext ifContext) {
		final FopPDFDocumentHandler handler = new FopPDFDocumentHandler(
				ifContext, fontService);
		final FOUserAgent ua = ifContext.getUserAgent();
		if (ua.isAccessibilityEnabled()) {
			ua.setStructureTreeEventHandler(
					handler.getStructureTreeEventHandler());
		}
		return handler;
	}

}