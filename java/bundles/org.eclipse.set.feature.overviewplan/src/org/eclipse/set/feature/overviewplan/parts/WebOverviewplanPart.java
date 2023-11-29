/**
 * Copyright (c) 2023 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.overviewplan.parts;

import java.io.IOException;
import java.nio.file.Paths;

import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.emfcloud.jackson.module.EMFModule;
import org.eclipse.emfcloud.jackson.module.EMFModule.Feature;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.set.browser.RequestHandler.Response;
import org.eclipse.set.feature.overviewplan.transformator.OverviewplanTransformator;
import org.eclipse.set.feature.siteplan.json.SiteplanEObjectSerializer;
import org.eclipse.set.model.siteplan.Siteplan;
import org.eclipse.set.utils.BasePart;
import org.eclipse.set.utils.FileWebBrowser;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 */
public class WebOverviewplanPart extends BasePart {

	private static final String WEB_PATH = "./web/siteplan"; //$NON-NLS-1$
	@Inject
	IEclipseContext context;

	/**
	 * Create the part.
	 */
	public WebOverviewplanPart() {
		super();
	}

	private void serveOverviewplan(final Response response)
			throws JsonProcessingException {
		final OverviewplanTransformator overviewplanTransformator = context
				.get(OverviewplanTransformator.class);
		final Siteplan siteplan = overviewplanTransformator
				.transform(getModelSession());
		// Configure the EMF JSON mapper
		final ObjectMapper mapper = new ObjectMapper();
		final EMFModule module = new EMFModule();
		module.configure(Feature.OPTION_SERIALIZE_TYPE, false);
		// Serialize default attributes
		module.configure(Feature.OPTION_SERIALIZE_DEFAULT_VALUE, true);
		mapper.registerModule(module);
		mapper.registerModule(SiteplanEObjectSerializer.getModule(module));
		// Write response
		final String json = mapper.writerWithDefaultPrettyPrinter()
				.writeValueAsString(siteplan);
		response.setMimeType("application/json;charset=UTF-8"); //$NON-NLS-1$
		response.setResponseData(json);
	}

	@Override
	protected void createView(final Composite parent) {

		try {
			final FileWebBrowser browser = new FileWebBrowser(parent);
			GridDataFactory.swtDefaults().align(SWT.FILL, SWT.FILL)
					.grab(true, true).span(2, 1).applyTo(browser.getControl());
			browser.serveRootDirectory(Paths.get(WEB_PATH));
			browser.serveFile("?", "text/html", //$NON-NLS-1$ //$NON-NLS-2$
					Paths.get(WEB_PATH, "index.html")); //$NON-NLS-1$
			browser.serveUri("overviewplan.json", this::serveOverviewplan); //$NON-NLS-1$
			browser.setUrl("https://toolbox/?"); //$NON-NLS-1$
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}
}
