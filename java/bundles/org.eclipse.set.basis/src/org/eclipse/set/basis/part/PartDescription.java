/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis.part;

import org.eclipse.set.basis.constants.PlanProFileNature;
import org.eclipse.set.basis.viewgroups.ToolboxViewGroup;

/**
 * Description for an registered view, which will be displayed within the action
 * area.
 * 
 * @author bleidiessel
 */
public class PartDescription {

	private final String contributionUri;
	private final PlanProFileNature defaultForNature;
	private final String id;
	private final boolean toolboxViewExclusiveEditor;
	private final String toolboxViewName;
	private final boolean toolboxViewNeedsCleanSession;
	private final boolean toolboxViewNeedsEmfValidation;
	private final boolean toolboxViewNeedsLoadedModel;
	private final boolean toolboxViewNeedsXsdValidation;
	private final boolean toolboxViewProcessIntegration;
	private final boolean toolboxViewProcessInvalid;
	private final boolean toolboxViewProcessPlanning;
	private final boolean toolboxViewProcessState;
	private final String toolboxViewToolTip;
	private final ToolboxViewGroup toolboxViewType;
	private final int partOrderPriority;

	/**
	 * Create the part description.
	 * 
	 * @param id
	 *            view id
	 * @param contributionUri
	 *            contribution URI of the class implementing the part
	 * @param toolboxViewName
	 *            the view name
	 * @param toolboxViewToolTip
	 *            the tool tip text
	 * @param toolboxViewType
	 *            the type of the view
	 * @param defaultForNature
	 *            the file nature using this view as the default (may be
	 *            <code>null</code>, if this part is not the default for any
	 *            file nature)
	 * @param toolboxViewProcessState
	 *            whether the view can process a state model
	 * @param toolboxViewProcessPlanning
	 *            whether the view can process a planning model
	 * @param toolboxViewProcessIntegration
	 *            whether the view can process an integration model
	 * @param toolboxViewProcessInvalid
	 *            whether the view can process an invalid model
	 * @param toolboxViewNeedsLoadedModel
	 *            whether the view need a loaded model
	 * @param toolboxViewNeedsXsdValidation
	 *            whether the view need an XSD valid model
	 * @param toolboxViewNeedsEmfValidation
	 *            whether the view need an EMF valid model
	 * @param toolboxViewNeedsCleanSession
	 *            whether the view needs a clean session
	 * @param toolboxViewExclusiveEditor
	 *            whether the view is an exclusive editor
	 */
	public PartDescription(final String id, final String contributionUri,
			final String toolboxViewName, final String toolboxViewToolTip,
			final ToolboxViewGroup toolboxViewType,
			final PlanProFileNature defaultForNature,
			final boolean toolboxViewNeedsLoadedModel,
			final boolean toolboxViewNeedsXsdValidation,
			final boolean toolboxViewNeedsEmfValidation,
			final boolean toolboxViewProcessState,
			final boolean toolboxViewProcessPlanning,
			final boolean toolboxViewProcessIntegration,
			final boolean toolboxViewProcessInvalid,
			final boolean toolboxViewNeedsCleanSession,
			final boolean toolboxViewExclusiveEditor) {
		this(id, contributionUri, toolboxViewName, toolboxViewToolTip,
				toolboxViewType, defaultForNature, toolboxViewNeedsLoadedModel,
				toolboxViewNeedsXsdValidation, toolboxViewNeedsEmfValidation,
				toolboxViewProcessState, toolboxViewProcessPlanning,
				toolboxViewProcessIntegration, toolboxViewProcessInvalid,
				toolboxViewNeedsCleanSession, toolboxViewExclusiveEditor, 0);
	}

	/**
	 * Create the part description.
	 * 
	 * @param id
	 *            view id
	 * @param contributionUri
	 *            contribution URI of the class implementing the part
	 * @param toolboxViewName
	 *            the view name
	 * @param toolboxViewToolTip
	 *            the tool tip text
	 * @param toolboxViewType
	 *            the type of the view
	 * @param defaultForNature
	 *            the file nature using this view as the default (may be
	 *            <code>null</code>, if this part is not the default for any
	 *            file nature)
	 * @param toolboxViewProcessState
	 *            whether the view can process a state model
	 * @param toolboxViewProcessPlanning
	 *            whether the view can process a planning model
	 * @param toolboxViewProcessIntegration
	 *            whether the view can process an integration model
	 * @param toolboxViewProcessInvalid
	 *            whether the view can process an invalid model
	 * @param toolboxViewNeedsLoadedModel
	 *            whether the view need a loaded model
	 * @param toolboxViewNeedsXsdValidation
	 *            whether the view need an XSD valid model
	 * @param toolboxViewNeedsEmfValidation
	 *            whether the view need an EMF valid model
	 * @param toolboxViewNeedsCleanSession
	 *            whether the view needs a clean session
	 * @param toolboxViewExclusiveEditor
	 *            whether the view is an exclusive editor
	 * @param partOrderPriority
	 *            the priority of the view for ordering. the highest priority
	 *            will be listed first
	 */
	public PartDescription(final String id, final String contributionUri,
			final String toolboxViewName, final String toolboxViewToolTip,
			final ToolboxViewGroup toolboxViewType,
			final PlanProFileNature defaultForNature,
			final boolean toolboxViewNeedsLoadedModel,
			final boolean toolboxViewNeedsXsdValidation,
			final boolean toolboxViewNeedsEmfValidation,
			final boolean toolboxViewProcessState,
			final boolean toolboxViewProcessPlanning,
			final boolean toolboxViewProcessIntegration,
			final boolean toolboxViewProcessInvalid,
			final boolean toolboxViewNeedsCleanSession,
			final boolean toolboxViewExclusiveEditor,
			final int partOrderPriority) {
		this.id = id;
		this.contributionUri = contributionUri;
		this.toolboxViewName = toolboxViewName;
		this.toolboxViewToolTip = toolboxViewToolTip;
		this.toolboxViewType = toolboxViewType;
		this.defaultForNature = defaultForNature;
		this.toolboxViewNeedsLoadedModel = toolboxViewNeedsLoadedModel;
		this.toolboxViewNeedsXsdValidation = toolboxViewNeedsXsdValidation;
		this.toolboxViewNeedsEmfValidation = toolboxViewNeedsEmfValidation;
		this.toolboxViewProcessState = toolboxViewProcessState;
		this.toolboxViewProcessPlanning = toolboxViewProcessPlanning;
		this.toolboxViewProcessIntegration = toolboxViewProcessIntegration;
		this.toolboxViewProcessInvalid = toolboxViewProcessInvalid;
		this.toolboxViewNeedsCleanSession = toolboxViewNeedsCleanSession;
		this.toolboxViewExclusiveEditor = toolboxViewExclusiveEditor;
		this.partOrderPriority = partOrderPriority;
	}

	/**
	 * @param nature
	 *            the PlanPro file nature
	 * 
	 * @return whether the view can process a PlanPro file of the given nature
	 */
	public boolean canProcess(final PlanProFileNature nature) {
		switch (nature) {
		case INVALID:
			return toolboxViewProcessInvalid;
		case INFORMATION_STATE:
			return toolboxViewProcessState;
		case PLANNING:
			return toolboxViewProcessPlanning;
		case INTEGRATION:
			return toolboxViewProcessIntegration;
		default:
			throw new IllegalArgumentException(nature.toString());
		}
	}

	/**
	 * Contribution URI of the class implementing the part.
	 * 
	 * @return the URI as a string
	 */
	public String getContributionUri() {
		return contributionUri;
	}

	/**
	 * @return the file nature using this view as the default (may be
	 *         <code>null</code>, if this part is not the default for any file
	 *         nature)
	 */
	public PlanProFileNature getDefaultForNature() {
		return defaultForNature;
	}

	/**
	 * @return the view id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the type (drop-down list) of the view
	 */
	public ToolboxViewGroup getToolboxViewGroup() {
		return toolboxViewType;
	}

	/**
	 * @return the view name
	 */
	public String getToolboxViewName() {
		return toolboxViewName;
	}

	/**
	 * @return the tool tip text for the view
	 */
	public String getToolboxViewToolTip() {
		return toolboxViewToolTip;
	}

	/**
	 * @return whether the view is an exclusive editor
	 */
	public boolean isExclusiveEditor() {
		return toolboxViewExclusiveEditor;
	}

	/**
	 * @return whether the view needs a clean session
	 */
	public boolean needsCleanSession() {
		return toolboxViewNeedsCleanSession;
	}

	/**
	 * @return whether the view needs an EMF valid model
	 */
	public boolean needsEmfValidation() {
		return toolboxViewNeedsEmfValidation;
	}

	/**
	 * @return whether the view needs a loaded model
	 */
	public boolean needsLoadedModel() {
		return toolboxViewNeedsLoadedModel;
	}

	/**
	 * @return whether the view needs an XSD valid model
	 */
	public boolean needsXsdValidation() {
		return toolboxViewNeedsXsdValidation;
	}

	/**
	 * @return the priority of the view for ordering
	 */
	public int getOrderPriority() {
		return partOrderPriority;
	}
}