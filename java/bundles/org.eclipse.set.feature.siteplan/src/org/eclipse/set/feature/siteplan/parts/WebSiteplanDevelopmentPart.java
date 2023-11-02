/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.siteplan.parts;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.emfcloud.jackson.module.EMFModule;
import org.eclipse.emfcloud.jackson.module.EMFModule.Feature;
import org.eclipse.set.basis.files.ToolboxFileFilterBuilder;
import org.eclipse.set.feature.siteplan.Messages;
import org.eclipse.set.feature.siteplan.json.SiteplanEObjectSerializer;
import org.eclipse.set.feature.siteplan.transform.SiteplanTransformator;
import org.eclipse.set.model.siteplan.Siteplan;
import org.eclipse.set.utils.BasePart;
import org.eclipse.set.utils.events.SelectedRowEvent;
import org.eclipse.set.utils.events.ToolboxEventHandler;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Provides a part to retrieve the siteplan.json model file
 */
public class WebSiteplanDevelopmentPart extends BasePart {
	@Inject
	@Translation
	protected Messages messages;

	@Inject
	IEclipseContext context;

	ToolboxEventHandler<SelectedRowEvent> selectRowEvent;

	/**
	 * Create the part.
	 */
	public WebSiteplanDevelopmentPart() {
		super();
	}

	@SuppressWarnings("nls") // No translation for development options
	@Override
	protected void createView(final Composite parent) {
		final Button button = new Button(parent, SWT.PUSH);
		button.setText("Lageplanmodell exportieren (JSON)"); //$NON-NLS-1$
		button.addListener(SWT.Selection,
				(final Event event) -> getDialogService()
						.saveFileDialog(getToolboxShell(),
								List.of(ToolboxFileFilterBuilder
										.forName("Lageplan").add("json")
										.create()),
								Path.of("siteplan.json"))
						.ifPresent(path -> {
							writeSiteplanJSON(path);
							getDialogService().openDirectoryAfterExport(
									getToolboxShell(), path.getParent());
						}));
	}

	private void writeSiteplanJSON(final Path path) {
		// Transform the planpro data to the siteplan data
		final SiteplanTransformator transformator = context
				.get(SiteplanTransformator.class);
		final Siteplan siteplan = transformator.transform(getModelSession());
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
