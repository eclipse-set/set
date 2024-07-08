/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.projectdata.ppimport;

import java.util.List;

import org.eclipse.set.basis.files.ToolboxFileRole;
import org.eclipse.set.feature.projectdata.ppimport.control.ImportContainerControlGroup;
import org.eclipse.set.feature.projectdata.ppimport.control.ImportLayoutControlGroup;
import org.eclipse.set.feature.projectdata.ppimport.control.ImportSubworkdControlGroup;
import org.eclipse.set.feature.projectdata.utils.ServiceProvider;
import org.eclipse.set.model.planpro.PlanPro.Container_AttributeGroup;
import org.eclipse.set.ppmodel.extensions.PlanProSchnittstelleExtensions;
import org.eclipse.set.utils.BasePart;
import org.eclipse.set.utils.RefreshAction;
import org.eclipse.set.utils.SelectableAction;
import org.eclipse.set.utils.events.ContainerDataChanged;
import org.eclipse.swt.widgets.Composite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.inject.Inject;

/**
 * Import PlanPro models.
 * 
 * @author Schaefer
 */
public class PlanProImportPart extends BasePart {

	static final Logger logger = LoggerFactory
			.getLogger(PlanProImportPart.class);

	protected ModelInformationGroup modelInformationGroup;

	@Inject
	private ServiceProvider serviceProvider;

	private ImportSubworkdControlGroup importModelGroup;
	private ImportContainerControlGroup importContainerGroup;

	private ImportLayoutControlGroup importLayout;

	/**
	 * 
	 */
	@Inject
	public PlanProImportPart() {
		super();
	}

	@Override
	protected void createView(final Composite parent) {

		if (PlanProSchnittstelleExtensions
				.isPlanning(getModelSession().getPlanProSchnittstelle())) {
			modelInformationGroup = new ModelInformationGroup(getModelSession(),
					serviceProvider.messages);
			modelInformationGroup.createInfoGroup(parent);

			importModelGroup = new ImportSubworkdControlGroup(serviceProvider,
					getModelSession(), getBroker());
			importModelGroup.createControl(parent, getToolboxShell(),
					ToolboxFileRole.SECONDARY_PLANNING);

			importContainerGroup = new ImportContainerControlGroup(
					serviceProvider, getModelSession(), getBroker());
			importContainerGroup.createControl(parent, getToolboxShell(), null);

			importLayout = new ImportLayoutControlGroup(serviceProvider,
					getModelSession(), getBroker());
			importLayout.createControl(parent, getToolboxShell(),
					ToolboxFileRole.THIRD_PLANNING);
		} else {
			modelInformationGroup.createNotSupportedInfo(parent);
		}
	}

	@Override
	protected SelectableAction getOutdatedAction() {
		return new RefreshAction(this, e -> {
			modelInformationGroup.updateInfoGroup();
			setOutdated(false);
		});
	}

	@Override
	protected void handleContainerDataChanged(final ContainerDataChanged e) {
		setOutdated(true);
	}

	@Override
	protected void updateViewContainerDataChanged(
			final List<Container_AttributeGroup> container) {
		if (isOutdated()) {
			modelInformationGroup.updateInfoGroup();
			setOutdated(false);
		}
	}
}
