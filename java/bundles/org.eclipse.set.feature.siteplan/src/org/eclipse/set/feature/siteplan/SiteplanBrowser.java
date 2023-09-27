/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.siteplan;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.emfcloud.jackson.module.EMFModule;
import org.eclipse.emfcloud.jackson.module.EMFModule.Feature;
import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.basis.cache.Cache;
import org.eclipse.set.browser.DownloadListener;
import org.eclipse.set.core.services.cache.CacheService;
import org.eclipse.set.core.services.dialog.DialogService;
import org.eclipse.set.feature.siteplan.json.SiteplanEObjectSerializer;
import org.eclipse.set.feature.siteplan.transform.LayoutTransformator;
import org.eclipse.set.feature.siteplan.transform.SiteplanTransformator;
import org.eclipse.set.model.siteplan.Siteplan;
import org.eclipse.set.utils.FileWebBrowser;
import org.eclipse.set.utils.ToolboxConfiguration;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Web server wrapper to provide the siteplan web application
 */
public class SiteplanBrowser extends FileWebBrowser
		implements DownloadListener {
	private static final Path PATH_OSIFONT = Paths
			.get("data/fonts/osifont/osifont-lgpl3fe.ttf"); //$NON-NLS-1$
	private final SiteplanTransformator transformator;
	private final CacheService cacheService;
	private final IModelSession modelSession;
	private final DialogService dialogService;
	private final UISynchronize ui;
	private final Shell shell;
	private Path selectedOutputDir;

	/**
	 * Creates a new web siteplan server on a random free port
	 * 
	 * @param parent
	 *            Parent composite
	 * @param context
	 *            Eclipse context for retrieving services
	 * @param shell
	 *            Shell to create dialogs for
	 * 
	 * @throws IOException
	 *             If the application is missing
	 */
	public SiteplanBrowser(final Composite parent,
			final IEclipseContext context, final Shell shell)
			throws IOException {
		super(parent);

		transformator = context.get(SiteplanTransformator.class);
		modelSession = context.get(IModelSession.class);
		dialogService = context.get(DialogService.class);
		ui = context.get(UISynchronize.class);
		cacheService = context.get(CacheService.class);
		this.shell = shell;

		serveRootDirectory(Paths.get(SiteplanConstants.SITEPLAN_DIRECTORY));

		// Server index.html as ?, as the web application cannot handle being
		// served from a "index.html" path
		serveFile("?", "text/html", //$NON-NLS-1$ //$NON-NLS-2$
				Paths.get(SiteplanConstants.SITEPLAN_DIRECTORY, "index.html")); //$NON-NLS-1$
		serveUri("font", this::serveFont); //$NON-NLS-1$
		serveUri("siteplan.json", this::serveSiteplan); //$NON-NLS-1$
		serveUri("configuration.json", this::serveConfiguration); //$NON-NLS-1$

		getBrowser().setDownloadListener(this);
	}

	@SuppressWarnings("resource") // Stream is closed by the browser
	private void serveFont(final Response response) throws IOException {
		response.setMimeType("application/x-font-ttf"); //$NON-NLS-1$
		response.setResponseData(Files.newInputStream(PATH_OSIFONT));
	}

	/**
	 * Helper record for creating the JSON configuration
	 *
	 * This class must match the ToolboxConfiguration class in the frontend
	 * application
	 */
	private record SiteplanConfiguration(boolean developmentMode,
			String mapSources, String hereApiKey, String mapboxApiKey,
			String dop20ApiKey, String dop20InternUrl, int lodScale,
			int exportDPI, String trackWidth, String trackWidthInterval,
			int baseZoomLevel, boolean defaultCollisionsEnabled,
			String defaultSheetCutCRS) {
		SiteplanConfiguration() {
			this(ToolboxConfiguration.isDevelopmentMode(),
					ToolboxConfiguration.getMapSources(),
					ToolboxConfiguration.getHereApiKey(),
					ToolboxConfiguration.getMapboxApiKey(),
					ToolboxConfiguration.getDop20ApiKey(),
					ToolboxConfiguration.getDop20InternUrl(),
					ToolboxConfiguration.getLodScale(),
					ToolboxConfiguration.getExportDPI(),
					ToolboxConfiguration.getTrackWidth(),
					ToolboxConfiguration.getTrackWidthIntervall(),
					ToolboxConfiguration.getBaseZoomLevel(),
					ToolboxConfiguration.getDefaultCollisionsEnabled(),
					LayoutTransformator.selectedCRS == null
							? ToolboxConfiguration.getDefaultSheetCutCRS()
							: LayoutTransformator.selectedCRS.getLiteral());
		}
	}

	private void serveConfiguration(final Response response)
			throws JsonProcessingException {
		response.setMimeType("application/json;charset=UTF-8"); //$NON-NLS-1$
		final String json = new ObjectMapper().writerWithDefaultPrettyPrinter()
				.writeValueAsString(new SiteplanConfiguration());
		response.setResponseData(json);
	}

	private void serveSiteplan(final Response response)
			throws JsonProcessingException {

		// Transform the planpro data to the siteplan data
		final Cache cache = cacheService
				.getCache(SiteplanConstants.SITEPLAN_CACHE_ID);
		final Siteplan siteplan = cache.get(
				SiteplanConstants.SITEPLAN_TRANSFORMATION_CACHE_ID,
				() -> transformator.transform(modelSession));
		// Configure the EMF JSON mapper
		final ObjectMapper mapper = new ObjectMapper();
		final EMFModule module = new EMFModule();
		// Do not serialize type information in JSON
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
	public Optional<Path> beforeDownload(final String suggestedName,
			final String url) {
		return Optional.of(Path.of(
				selectedOutputDir.toAbsolutePath().toString(), suggestedName));
	}

	@Override
	public void downloadFinished(final boolean success, final Path path) {
		// Notify user and offer to open the directory
		ui.asyncExec(() -> dialogService.openDirectoryAfterExport(shell,
				selectedOutputDir));
	}

	/**
	 * @param outputDir
	 *            the output directory for file downloads
	 */
	public void setSelectedFolder(final Path outputDir) {
		selectedOutputDir = outputDir;
	}
}
