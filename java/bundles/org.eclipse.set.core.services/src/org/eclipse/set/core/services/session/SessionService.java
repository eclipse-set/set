/**
 * Copyright (c) 2020 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.core.services.session;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.basis.InitializationData;
import org.eclipse.set.basis.files.ToolboxFile.Format;
import org.eclipse.set.basis.files.ToolboxFileExtension;
import org.eclipse.set.basis.viewgroups.ToolboxViewGroup;

/**
 * Provide session specific information.
 * 
 * @author Schaefer
 */
public interface SessionService {

	/**
	 * Close the given model session.
	 * 
	 * @param modelSession
	 *            the model session
	 * 
	 * @return whether the session was closed
	 */
	boolean close(IModelSession modelSession);

	/**
	 * Create a new editing domain for the given file format.
	 * 
	 * @return the new editing domain
	 */
	EditingDomain createEditingDomain();

	/**
	 * @return the list of view groups
	 */
	List<ToolboxViewGroup> getViewGroups();

	/**
	 * @param format
	 *            the format
	 * 
	 * @return the default extension
	 */
	String getDefaultExtension(Format format);

	/**
	 * Provide (an optional) default part ID for a new session.
	 * 
	 * @param modelSession
	 *            the model session
	 * 
	 * @return the default part ID
	 */
	Optional<String> getDefaultPartID(IModelSession modelSession);

	/**
	 * @param path
	 *            the path of the toolbox file
	 * 
	 * @return the toolbox file format
	 */
	Format getFormat(Path path);

	/**
	 * @return the format for new initializations
	 */
	Format getInitializationFormat();

	/**
	 * @return the format for a merged file (not the integration file!)
	 */
	Format getMergedFileFormat();

	/**
	 * Provide the package URI for the given file format.
	 * 
	 * @param format
	 *            the file format
	 * 
	 * @return the package URI
	 */
	URI getPackageUri(Format format);

	/**
	 * @return the format for plain PlanPro files
	 */
	Format getPlainPlanProFormat();

	/**
	 * @return a map from categories to a set of supported plain file extensions
	 */
	Map<String, Set<ToolboxFileExtension>> getPlainSupportMap();

	/**
	 * @return the format for zipped PlanPro files
	 */
	Format getZippedPlanProFormat();

	/**
	 * @return a map from categories to a set of supported zipped file
	 *         extensions
	 */
	Map<String, Set<ToolboxFileExtension>> getZippedSupportMap();

	/**
	 * Create a new model session from the given initialization data.
	 * 
	 * @param initializationData
	 *            the initialization data
	 * 
	 * @return the new model session
	 */
	IModelSession initModelSession(InitializationData initializationData);

	/**
	 * Create a new model session from the given path to the toolbox file.
	 * 
	 * @param path
	 *            the path to the toolbox file
	 * 
	 * @return the new model session
	 */
	IModelSession loadModelSession(Path path);

	/**
	 * @param application
	 *            the application
	 */
	void setApplication(MApplication application);
}
