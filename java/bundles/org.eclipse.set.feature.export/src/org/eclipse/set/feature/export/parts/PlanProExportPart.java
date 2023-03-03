/**
 * Copyright (c) 2015 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.export.parts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.set.basis.FreeFieldInfo;
import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.basis.OverwriteHandling;
import org.eclipse.set.basis.constants.ExportType;
import org.eclipse.set.basis.constants.TableType;
import org.eclipse.set.core.services.export.AdditionalExportService;
import org.eclipse.set.core.services.export.CheckboxModelElement;
import org.eclipse.set.core.services.part.ToolboxPartService;
import org.eclipse.set.model.tablemodel.Table;
import org.eclipse.set.model.titlebox.Titlebox;
import org.eclipse.set.ppmodel.extensions.utils.PlanProToFreeFieldTransformation;
import org.eclipse.set.ppmodel.extensions.utils.PlanProToTitleboxTransformation;
import org.eclipse.set.toolboxmodel.PlanPro.Container_AttributeGroup;
import org.eclipse.set.utils.SaveAndRefreshAction;
import org.eclipse.set.utils.SelectableAction;
import org.eclipse.set.utils.events.ContainerDataChanged;
import org.eclipse.set.utils.events.ProjectDataChanged;
import org.eclipse.set.utils.exception.ExceptionHandler;

/**
 * Viewpart for the export of documents.
 * 
 * @author rumpf
 */
public abstract class PlanProExportPart extends DocumentExportPart {

	@Inject
	@Optional
	private AdditionalExportService additionalExportService;

	@Inject
	ToolboxPartService toolboxPartService;

	/**
	 * creates the part.
	 */
	@Inject
	public PlanProExportPart() {
		super();
	}

	@Override
	protected CheckboxModelElement[] createCheckboxModelElements() {
		final List<String> shortCuts = new ArrayList<>(
				tableService.getAvailableTables());
		Collections.sort(shortCuts);

		final List<CheckboxModelElement> elements = new ArrayList<>(
				shortCuts.stream().map(tableService::getTableNameInfo)
						.map(info -> new CheckboxModelElement(
								info.getShortName().toLowerCase(),
								info.getFullDisplayName()))
						.toList());

		if (additionalExportService != null) {
			additionalExportService
					.createAdditionalCheckboxModelElements(elements);
		}

		return elements.toArray(new CheckboxModelElement[0]);
	}

	@Override
	protected void export(final CheckboxModelElement element,
			final IModelSession modelSession,
			final OverwriteHandling overwriteHandling,
			final IProgressMonitor monitor) {
		final String id = element.getId();

		if (additionalExportService != null
				&& additionalExportService.isAdditionalExportId(id)) {
			additionalExportService.createAdditionalExport(id, modelSession,
					monitor, getSelectedDirectory(), getExportType(),
					overwriteHandling);
		} else {
			final Map<TableType, Table> tables = compileService.compile(id,
					modelSession);
			final PlanProToTitleboxTransformation planProToTitlebox = PlanProToTitleboxTransformation
					.create();
			final Titlebox titlebox = planProToTitlebox.transform(
					modelSession.getPlanProSchnittstelle(),
					tableService.getTableNameInfo(id));
			updateTitlebox(titlebox);
			final PlanProToFreeFieldTransformation planProToFreeField = PlanProToFreeFieldTransformation
					.create();
			final FreeFieldInfo freeFieldInfo = planProToFreeField
					.transform(modelSession);
			exportService.export(tables, getExportType(), titlebox,
					freeFieldInfo, id, getSelectedDirectory().toString(),
					modelSession.getToolboxPaths(), modelSession.getTableType(),
					overwriteHandling, new ExceptionHandler(getToolboxShell(),
							getDialogService()));
		}
	}

	protected abstract ExportType getExportType();

	@Override
	protected SelectableAction getOutdatedAction() {
		return new SaveAndRefreshAction(this);
	}

	@Override
	protected String getTaskMessage() {
		return messages.exportTable_message;
	}

	@Override
	protected void handleContainerDataChanged(final ContainerDataChanged e) {
		setOutdated(true);
	}

	@Override
	protected void handleProjectDataChanged(final ProjectDataChanged e) {
		setOutdated(true);
	}

	protected abstract void updateTitlebox(Titlebox titlebox);

	@Override
	protected void updateViewContainerDataChanged(
			final List<Container_AttributeGroup> container) {
		setOutdated(false);
	}

	@Override
	protected void updateViewProjectDataChanged(
			final List<Notification> notifications) {
		setOutdated(false);
	}
}
