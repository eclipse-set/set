/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.nattable.utils;

import org.eclipse.nebula.widgets.nattable.export.NatExporter;
import org.eclipse.nebula.widgets.nattable.export.command.ExportCommand;
import org.eclipse.nebula.widgets.nattable.export.command.ExportCommandHandler;
import org.eclipse.nebula.widgets.nattable.layer.ILayer;
import org.eclipse.swt.widgets.Shell;

/**
 * This export handler can be configured whether to open the exported artifact
 * or not.
 * 
 * @author Schaefer
 */
public class CustomizableExportCommandHandler extends ExportCommandHandler {

	private final ILayer exportLayer;
	private boolean openResult = false;

	/**
	 * @param layer
	 *            the layer to be exported
	 */
	public CustomizableExportCommandHandler(final ILayer layer) {
		super(layer);
		exportLayer = layer;
	}

	@Override
	public boolean doCommand(final ExportCommand command) {
		createExporter(command.getShell()).exportSingleLayer(exportLayer,
				command.getConfigRegistry());
		return true;
	}

	/**
	 * @param value
	 *            whether to open the exported artifact or not
	 * 
	 * @return this command handler
	 */
	public CustomizableExportCommandHandler setOpen(final boolean value) {
		openResult = value;
		return this;
	}

	private NatExporter createExporter(final Shell shell) {
		final NatExporter natExporter = new NatExporter(shell);
		natExporter.setOpenResult(openResult);
		return natExporter;
	}
}
