/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.core.services.part;

import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.basis.part.PartDescription;
import org.eclipse.set.basis.viewgroups.ToolboxViewGroup;

/**
 * The central toolbox service for coordinating the parts and the session.
 * 
 * @author bleidiessel
 */
public interface ToolboxPartService {
	/** ID des Extensionpoints */
	public static final String ITOOLBOXDOCUMENTVIEW = "org.eclipse.set.core.services.view.extensionpoint"; //$NON-NLS-1$

	/**
	 * Der aktuell angezeigte Part im Kontext.
	 */
	public static final String VISIBLE_DOCUMENT_PART = ToolboxPartService.class
			.getName() + ".visibleDocumentPartName"; //$NON-NLS-1$

	/**
	 * Clean up the view area.
	 */
	public void clean();

	/**
	 * @return the active part
	 */
	public MPart getActivePart();

	/**
	 * @return the list of all open parts
	 */
	public List<MPart> getOpenParts();

	/**
	 * Liefert zu einer ID die bekannte Sicht oder null
	 * 
	 * @param viewId
	 *            Die ID der gesuchten Sicht
	 * @return Die Sicht oder null wenn keine Sicht für die ID vorhanden ist
	 */
	public PartDescription getRegisteredDescription(String viewId);

	/**
	 * @return map of view groups to registered part descriptions
	 */
	public Map<ToolboxViewGroup, List<PartDescription>> getRegisteredDescriptions();

	/**
	 * Provide the part descriptions for the views in a view group.
	 * 
	 * @param group
	 *            the view group
	 * @return the part descriptions
	 */
	public List<PartDescription> getRegisteredDescriptions(
			ToolboxViewGroup group);

	/**
	 * @return whether the action area is visible
	 */
	public boolean isActionAreaVisible();

	/**
	 * @param description
	 *            the part description
	 * 
	 * @return whether a part with the given description is open
	 */
	public boolean isOpen(PartDescription description);

	/**
	 * Show the action area.
	 */
	public void showActionArea();

	/**
	 * Shows the default part.
	 * 
	 * @param session
	 *            the model session
	 * 
	 * @return whether the wanted part was shown
	 */
	public boolean showDefaultPart(IModelSession session);

	/**
	 * @param part
	 *            the part to be shown
	 * 
	 * @return whether the wanted part was shown
	 */
	public boolean showPart(PartDescription part);

	/**
	 * @param id
	 *            the id of the part to be shown
	 * 
	 * @return whether the wanted part was shown
	 */
	public boolean showPart(String id);

	/**
	 * Show a part with the given pdf file.
	 * 
	 * @param path
	 *            the path to the pdf file
	 */
	public void showPdfPart(Path path);

	/**
	 * Returns the registerred toolbox view groups
	 */
	public Collection<ToolboxViewGroup> getViewGroups();
}
