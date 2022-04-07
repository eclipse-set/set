/**
 * Copyright (c) 2015 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.export.parts;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
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
import org.eclipse.set.basis.part.PartDescription;
import org.eclipse.set.core.services.export.AdditionalExportService;
import org.eclipse.set.core.services.export.CheckboxModelElement;
import org.eclipse.set.core.services.part.ToolboxPartService;
import org.eclipse.set.model.tablemodel.Table;
import org.eclipse.set.model.titlebox.Titlebox;
import org.eclipse.set.ppmodel.extensions.utils.PlanProToFreeFieldTransformation;
import org.eclipse.set.ppmodel.extensions.utils.PlanProToTitleboxTransformation;
import org.eclipse.set.utils.SaveAndRefreshAction;
import org.eclipse.set.utils.SelectableAction;
import org.eclipse.set.utils.events.ContainerDataChanged;
import org.eclipse.set.utils.events.ProjectDataChanged;
import org.eclipse.set.utils.exception.ExceptionHandler;
import org.eclipse.set.utils.viewgroups.SetViewGroups;

import com.google.common.collect.Lists;

import org.eclipse.set.toolboxmodel.PlanPro.Container_AttributeGroup;

/**
 * Viewpart for the export of documents.
 * 
 * @author rumpf
 */
public abstract class PlanProExportPart
		extends DocumentExportPart<IModelSession> {

	@Inject
	@Optional
	private AdditionalExportService additionalExportService;

	final Map<String, PartDescription> configurations = new HashMap<>();

	@Inject
	ToolboxPartService toolboxPartService;

	/**
	 * creates the part.
	 */
	@Inject
	public PlanProExportPart() {
		super(IModelSession.class);
	}

	@Override
	protected CheckboxModelElement[] createCheckboxModelElements() {
		final List<PartDescription> tableDescriptions = toolboxPartService
				.getRegisteredDescriptions(SetViewGroups.getTable());

		// sort lexicographically
		Collections.sort(tableDescriptions, new Comparator<PartDescription>() {
			@Override
			public int compare(final PartDescription o1,
					final PartDescription o2) {
				final String toolboxViewName1 = o1.getToolboxViewName();
				final String toolboxViewName2 = o2.getToolboxViewName();
				return toolboxViewName1.compareTo(toolboxViewName2);
			}
		});

		final List<CheckboxModelElement> result = Lists.newArrayList();

		for (final PartDescription description : tableDescriptions) {
			final String name = description.getToolboxViewName();
			final String id = description.getId();
			result.add(new CheckboxModelElement(id, name));
			configurations.put(id, description);
		}
		if (additionalExportService != null) {
			additionalExportService
					.createAdditionalCheckboxModelElements(result);
		}

		return result.toArray(new CheckboxModelElement[0]);
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
			final PartDescription description = configurations.get(id);
			final String shortcut = tableService.extractShortcut(description);
			final Map<TableType, Table> tables = compileService
					.compile(shortcut, modelSession);
			final PlanProToTitleboxTransformation planProToTitlebox = PlanProToTitleboxTransformation
					.create();
			final Titlebox titlebox = planProToTitlebox.transform(
					modelSession.getPlanProSchnittstelle(),
					tableService.getTableNameInfo(shortcut));
			updateTitlebox(titlebox);
			final PlanProToFreeFieldTransformation planProToFreeField = PlanProToFreeFieldTransformation
					.create();
			final FreeFieldInfo freeFieldInfo = planProToFreeField
					.transform(modelSession);
			exportService.export(tables, getExportType(), titlebox,
					freeFieldInfo, shortcut, getSelectedDirectory().toString(),
					modelSession.getToolboxPaths(), overwriteHandling,
					new ExceptionHandler(getToolboxShell(),
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
