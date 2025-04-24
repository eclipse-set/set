/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.export;

import org.eclipse.set.basis.constants.ExportType;
import org.eclipse.set.basis.constants.PlanProFileNature;
import org.eclipse.set.basis.constants.TableType;
import org.eclipse.set.model.titlebox.Titlebox;

/**
 * Part for exporting planning records (Dokumentensatz erstellen).
 * 
 * @author Schaefer
 */
public class ExportPlanningRecordsPart extends PlanProExportPart {

	@Override
	protected String getDescription() {
		if (getModelSession().getNature() == PlanProFileNature.PLANNING) {
			return messages.TableExportPart_ExportPlanningDescription;
		}
		return messages.TableExportPart_ExportStateDescription;
	}

	@Override
	protected String getExportButtonText() {
		return messages.TableExportPart_ExportButton;
	}

	@Override
	protected ExportType getExportType() {
		return ExportType.PLANNING_RECORDS;
	}

	@Override
	protected void updateTitlebox(final Titlebox titlebox) {
		// No update required, as the title box is not marked
		// for planning records
	}

	@Override
	public TableType getTableType() {
		return getModelSession().getTableType();
	}
}
