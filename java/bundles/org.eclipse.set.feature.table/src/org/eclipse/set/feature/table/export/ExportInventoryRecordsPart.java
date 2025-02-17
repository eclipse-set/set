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
import org.eclipse.set.basis.constants.TableType;
import org.eclipse.set.model.titlebox.Titlebox;
import org.eclipse.set.model.titlebox.extensions.TitleboxExtensions;

/**
 * Part for exporting inventory records (Bestandsunterlagen erstellen).
 * 
 * @author Schaefer
 */
public class ExportInventoryRecordsPart extends PlanProExportPart {

	@Override
	protected String getDescription() {
		return messages.TableExportPart_ExportInventoryDescription;
	}

	@Override
	protected String getExportButtonText() {
		return messages.TableExportPart_ExportInventoryRecords;
	}

	@Override
	protected ExportType getExportType() {
		return ExportType.INVENTORY_RECORDS;
	}

	@Override
	protected void updateTitlebox(final Titlebox titlebox) {
		TitleboxExtensions.set(titlebox, TitleboxExtensions.DOC_TYPE_ADDRESS,
				TitleboxExtensions.INVENTORY_RECORDS_DOC_TYPE_SHOTCUT);
	}

	@Override
	public TableType getTableType() {
		return TableType.FINAL;
	}
}
