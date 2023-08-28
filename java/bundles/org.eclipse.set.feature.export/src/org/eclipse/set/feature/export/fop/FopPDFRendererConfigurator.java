/** 
 * Copyright (c) 2022 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.export.fop;

import java.util.ArrayList;
import java.util.List;

import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.io.InternalResourceResolver;
import org.apache.fop.fonts.EmbedFontInfo;
import org.apache.fop.fonts.EmbeddingMode;
import org.apache.fop.fonts.EncodingMode;
import org.apache.fop.fonts.Font;
import org.apache.fop.fonts.FontAdder;
import org.apache.fop.fonts.FontCollection;
import org.apache.fop.fonts.FontEventAdapter;
import org.apache.fop.fonts.FontInfo;
import org.apache.fop.fonts.FontManager;
import org.apache.fop.fonts.FontManagerConfigurator;
import org.apache.fop.fonts.FontTriplet;
import org.apache.fop.fonts.FontUris;
import org.apache.fop.render.RendererConfig.RendererConfigParser;
import org.apache.fop.render.pdf.PDFRendererConfigurator;
import org.eclipse.set.core.services.font.FontService;

/**
 * Custom configuration for the Fop PDF Renderer using the {@link FontService}
 * 
 * @author Stuecker
 */
public class FopPDFRendererConfigurator extends PDFRendererConfigurator {
	private final FontService fontService;

	/**
	 * @param userAgent
	 *            the user agent
	 * @param rendererConfigParser
	 *            the config parser
	 * @param fontService
	 *            the font service providing fonts
	 */
	public FopPDFRendererConfigurator(final FOUserAgent userAgent,
			final RendererConfigParser rendererConfigParser,
			final FontService fontService) {
		super(userAgent, rendererConfigParser);
		this.fontService = fontService;
	}

	@SuppressWarnings("nls")
	private static int toFontWeight(final String weight) {
		switch (weight) {
		case "light":
			return Font.WEIGHT_NORMAL;
		case "normal":
			return Font.WEIGHT_NORMAL;
		case "bold":
			return Font.WEIGHT_BOLD;
		case "extra bold":
			return Font.WEIGHT_EXTRA_BOLD;
		default:
			throw new IllegalArgumentException("Invalid font weight");
		}
	}

	@Override
	protected FontCollection getCustomFontCollection(
			final InternalResourceResolver resolver, final String mimeType)
			throws FOPException {
		final List<EmbedFontInfo> fontList = new ArrayList<>();
		fontService.getFopFonts().forEach(font -> {
			final FontUris fontUri = new FontUris(font.path().toUri(), null);
			final List<FontTriplet> fontTriplets = List.of(new FontTriplet(
					font.name(), font.style(), toFontWeight(font.weight())));
			fontList.add(new EmbedFontInfo(fontUri, true, false, fontTriplets,
					null, EncodingMode.AUTO, EmbeddingMode.SUBSET, false, false,
					true));
		});
		return createCollectionFromFontList(resolver, fontList);
	}

	@Override
	protected List<FontCollection> getDefaultFontCollection() {
		final List<FontCollection> fontsCollections = super.getDefaultFontCollection();
		// Detec system font
		final List<EmbedFontInfo> systemFonts = new ArrayList<>();
		final FontManager fontManager = userAgent.getFontManager();
		final FontEventAdapter eventAdapter = new FontEventAdapter(
				userAgent.getEventBroadcaster());
		final FontAdder fontAdder = new FontAdder(fontManager,
				fontManager.getResourceResolver(), eventAdapter);
		try {
			fontManager.autoDetectFonts(true, fontAdder, false, eventAdapter,
					systemFonts);
			fontsCollections.add(createCollectionFromFontList(
					userAgent.getResourceResolver(), systemFonts));
		} catch (final FOPException e) {
			throw new RuntimeException(e);
		}
		return fontsCollections;
	}

	@Override
	public void setupFontInfo(final String mimeType, final FontInfo fontInfo)
			throws FOPException {
		final FontManager fontManager = userAgent.getFontManager();
		fontManager.setReferencedFontsMatcher(FontManagerConfigurator
				.createFontsMatcher(List.of("Arial.*"), false)); //$NON-NLS-1$
		super.setupFontInfo(mimeType, fontInfo);
	}

}
