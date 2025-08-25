/**
 * Copyright (c) 2025 DB InfraGO AG and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 */
package org.eclipse.set.feature.siteplan.browserfunctions;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Base64;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.imageio.ImageIO;

import org.eclipse.set.basis.FreeFieldInfo;
import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.basis.OverwriteHandling;
import org.eclipse.set.core.services.dialog.DialogService;
import org.eclipse.set.core.services.session.SessionService;
import org.eclipse.set.feature.siteplan.Messages;
import org.eclipse.set.feature.siteplan.SiteplanBrowser;
import org.eclipse.set.model.titlebox.Titlebox;
import org.eclipse.set.ppmodel.extensions.utils.PlanProToFreeFieldTransformation;
import org.eclipse.set.ppmodel.extensions.utils.PlanProToTitleboxTransformation;
import org.eclipse.set.services.export.ExportService;
import org.eclipse.set.utils.ToolboxConfiguration;
import org.eclipse.set.utils.exception.ExceptionHandler;
import org.eclipse.swt.widgets.Shell;

/**
 * Browser function for take images data from Browser and pack that to PDF
 * 
 * @author truong
 */
public class ExportSiteplanBrowserFunction
		extends SelectFolderDialogBrowserFunction {
	private final List<BufferedImage> exportImages = new LinkedList<>();
	private final IModelSession modelSession;
	private final ExportService exportService;
	private final Messages message;
	private final SessionService sessionService;

	/**
	 * Constructor.
	 * 
	 * @param browser
	 *            browser to add this function to
	 * @param name
	 *            name of this function for Javascript
	 * @param modelSession
	 *            the model session
	 * @param sessionService
	 *            the {@link SessionService}
	 * @param exportService
	 *            the export service
	 * @param shell
	 *            a shell to open dialogs with
	 * @param dialogService
	 *            the dialog service
	 * @param message
	 *            the {@link Messages}
	 */
	public ExportSiteplanBrowserFunction(final SiteplanBrowser browser,
			final String name, final IModelSession modelSession,
			final SessionService sessionService,
			final ExportService exportService, final Shell shell,
			final DialogService dialogService, final Messages message) {
		super(browser, name, shell, dialogService);
		this.modelSession = modelSession;
		this.sessionService = sessionService;
		this.exportService = exportService;
		this.message = message;
	}

	@Override
	public Object function(final Object[] arguments) {
		exportImages.clear();
		final Optional<String> optionalOutputDir = dialogService
				.selectDirectory(shell,
						ToolboxConfiguration.getDefaultPath().toString());
		final String key = (String) arguments[0];
		final int sheetcutCount = Integer.parseInt((String) arguments[1]);
		// The pixel pro physical meter in Openlayer
		final double ppm = Double.parseDouble((String) arguments[2]);
		optionalOutputDir.ifPresentOrElse(outputDir -> {
			// User selected a folder
			webBrowser.executeJavascript(String.format("%s('%s', '%s');", //$NON-NLS-1$
					DIALOG_CALLBACK_FUNCTION, key,
					outputDir.replace("\\", "\\\\"))); //$NON-NLS-1$ //$NON-NLS-2$
			webBrowser.setBeforeDownloadFunc(url -> sheetcutImageHandle(url,
					sheetcutCount, Path.of(outputDir), ppm));
		}, () -> // User did not select a folder
		webBrowser.executeJavascript(String.format("%s('%s', null);", //$NON-NLS-1$
				DIALOG_CALLBACK_FUNCTION, key)));
		return null;
	}

	private void sheetcutImageHandle(final String url, final int sheetcutcount,
			final Path outDir, final double ppm) {
		final String base64Data = url.split(",")[1]; //$NON-NLS-1$
		final byte[] decode = Base64.getDecoder().decode(base64Data);
		try (final ByteArrayInputStream inputstream = new ByteArrayInputStream(
				decode)) {
			final BufferedImage bufferedImage = ImageIO.read(inputstream);
			exportImages.add(bufferedImage);
			if (exportImages.size() >= sheetcutcount) {
				final PlanProToFreeFieldTransformation planProToFreeField = PlanProToFreeFieldTransformation
						.create();
				final FreeFieldInfo freeFieldInfo = planProToFreeField
						.transform(modelSession);
				dialogService.showProgressUISync(shell,
						message.WebSiteplanPart_Export, () -> {
							exportService.exportSiteplanPdf(exportImages,
									createTitleBox(), freeFieldInfo, ppm,
									outDir.toAbsolutePath().toString(),
									modelSession.getToolboxPaths(),
									modelSession.getTableType(),
									OverwriteHandling.forCheckbox(true),
									new ExceptionHandler(shell, dialogService));
							exportImages.clear();
						});

				dialogService.openDirectoryAfterExport(shell, outDir);
			}

		} catch (final IOException e) {
			dialogService.error(shell, e);
		}
	}

	private Titlebox createTitleBox() {
		final PlanProToTitleboxTransformation titleBoxTransform = new PlanProToTitleboxTransformation(
				sessionService);
		return titleBoxTransform.transform(null, null);
	}
}
