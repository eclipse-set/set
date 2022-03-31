/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.application.parts;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.set.application.Messages;
import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.basis.constants.ToolboxConstants;
import org.eclipse.set.core.services.pdf.PdfRendererService;
import org.eclipse.set.core.services.pdf.PdfViewer;
import org.eclipse.set.core.services.pdf.PdfViewerPart;
import org.eclipse.set.utils.BasePart;
import org.eclipse.set.utils.SelectableAction;
import org.eclipse.set.utils.ToolboxConfiguration;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;

/**
 * Display a pdf file.
 * 
 * @author Schaefer
 */
public class ViewPdfPart extends BasePart<IModelSession>
		implements PdfViewerPart {

	@Optional
	@Inject
	private PdfRendererService rendererService;

	private PdfViewer viewer;

	@Inject
	@Translation
	Messages messages;

	/**
	 * Create the part.
	 */
	@Inject
	public ViewPdfPart() {
		super(IModelSession.class);
	}

	@Override
	public PdfViewer getViewer() {
		return viewer;
	}

	@Override
	protected void createView(final Composite parent) {
		final String filename = (String) getToolboxPart().getTransientData()
				.get(ToolboxConstants.FILE_PARAMETER);
		final Path path = Paths.get(filename);

		getBanderole().setExportAction(new SelectableAction() {
			@Override
			public String getText() {
				return messages.ViewPdfPart_ExportButton;
			}

			@Override
			public void selected(final SelectionEvent e) {
				export(path);
			}
		});

		if (rendererService != null) {
			viewer = rendererService.createViewer(parent);
			viewer.show(path);
		}
	}

	/**
	 * Exports a pdf file
	 * 
	 * @param source
	 *            the pdf source file
	 */
	protected void export(final Path source) {
		final java.util.Optional<String> optionalOutputDir = getDialogService()
				.selectDirectory(getToolboxShell(),
						ToolboxConfiguration.getDefaultPath().toString());
		optionalOutputDir.ifPresent(outputDir -> {
			final Path dest = Paths.get(outputDir,
					source.getFileName().toString());
			if (dest.toFile().exists()) {
				final boolean overwrite = getDialogService()
						.confirmOverwrite(getToolboxShell(), dest);
				if (!overwrite) {
					return;
				}
			}
			try {
				Files.copy(source, dest, StandardCopyOption.REPLACE_EXISTING);
				getDialogService().openDirectoryAfterExport(getToolboxShell(),
						Paths.get(outputDir));
			} catch (final IOException e) {
				getDialogService().error(getToolboxShell(),
						messages.ViewPdfPart_ExportErrorTitle,
						String.format(messages.ViewPdfPart_ExportErrorText,
								dest.toString()),
						e);
			}
		});
	}
}
