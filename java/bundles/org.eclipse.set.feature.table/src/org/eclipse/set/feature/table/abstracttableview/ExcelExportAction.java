/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.abstracttableview;

import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.export.ExportConfigAttributes;
import org.eclipse.nebula.widgets.nattable.export.command.ExportCommand;
import org.eclipse.nebula.widgets.nattable.export.excel.ExcelExporter;
import org.eclipse.nebula.widgets.nattable.ui.action.IKeyAction;
import org.eclipse.swt.events.KeyEvent;

/**
 * Excel export key action implementation 
 *
 * @author Schneider
 */
public class ExcelExportAction implements IKeyAction {

    @Override
    public void run(NatTable natTable, KeyEvent event) {
    	natTable.getConfigRegistry().registerConfigAttribute(ExportConfigAttributes.EXPORTER,
                new ExcelExporter());
        natTable.doCommand(new ExportCommand(natTable.getConfigRegistry(),
                natTable.getShell()));
    }

}