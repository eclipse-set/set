/**
 * Copyright (c) 2023 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.feature.export.parts;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.emfcloud.jackson.module.EMFModule;
import org.eclipse.emfcloud.jackson.module.EMFModule.Feature;
import org.eclipse.set.basis.files.ToolboxFileFilterBuilder;
import org.eclipse.set.feature.overviewplan.transformator.OverviewplanTransformator;
import org.eclipse.set.feature.siteplan.json.SiteplanEObjectSerializer;
import org.eclipse.set.feature.siteplan.transform.SiteplanTransformator;
import org.eclipse.set.model.siteplan.Siteplan;
import org.eclipse.set.utils.BasePart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Listener;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author truong
 *
 */
public class SiteplanModelExportPart extends BasePart {
	@Inject
	IEclipseContext context;

	/**
	 * Defautl constructor
	 */
	public SiteplanModelExportPart() {
		super();
	}

	@Override
	protected void createView(final Composite parent) {
		final Button siteplanExport = new Button(parent, SWT.PUSH);
		siteplanExport.setText("Lageplanmodell exportieren (JSON)"); //$NON-NLS-1$
		siteplanExport.addListener(SWT.Selection,
				selectionListerner(ToolboxFileFilterBuilder.forName("Lageplan"), //$NON-NLS-1$
						Path.of("siteplan.json"), path -> writeJson(path, //$NON-NLS-1$
								t -> t.get(SiteplanTransformator.class)
										.transform(getModelSession()))));

		final Button overviewplanExport = new Button(parent, SWT.PUSH);
		overviewplanExport.setText("Overviewmodell exportieren (JSON)"); //$NON-NLS-1$
		overviewplanExport.addListener(SWT.Selection,
				selectionListerner(
						ToolboxFileFilterBuilder.forName("Uebersichtplan"), //$NON-NLS-1$
						Path.of("overviewplan.json"), path -> writeJson(path, //$NON-NLS-1$
								t -> {
									final OverviewplanTransformator overviewplanTransformator = t
											.get(OverviewplanTransformator.class);
									return overviewplanTransformator
											.transform(getModelSession());
								})));
	}

	private Listener selectionListerner(
			final ToolboxFileFilterBuilder fileFilter, final Path outPath,
			final Consumer<Path> writeJsonConsumer) {
		return event -> getDialogService().saveFileDialog(getToolboxShell(),
				List.of(fileFilter.add("json") //$NON-NLS-1$
						.create()),
				outPath).ifPresent(path -> {
					writeJsonConsumer.accept(path);
					getDialogService().openDirectoryAfterExport(
							getToolboxShell(), path.getParent());
				});
	}

	private void writeJson(final Path path,
			final Function<IEclipseContext, Siteplan> transformator) {
		final Siteplan siteplan = transformator.apply(context);
		final ObjectMapper mapper = new ObjectMapper();
		final EMFModule module = new EMFModule();
		// Do not serialize type information in JSON
		module.configure(Feature.OPTION_SERIALIZE_TYPE, false);
		// Serialize default attributes
		module.configure(Feature.OPTION_SERIALIZE_DEFAULT_VALUE, true);
		mapper.registerModule(module);
		mapper.registerModule(SiteplanEObjectSerializer.getModule(module));
		// Write response
		String json;
		try {
			json = mapper.writerWithDefaultPrettyPrinter()
					.writeValueAsString(siteplan);
			Files.writeString(path, json);
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}
}
