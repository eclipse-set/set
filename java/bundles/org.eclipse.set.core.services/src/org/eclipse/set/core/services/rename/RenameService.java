/**
 * Copyright (c) 2019 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.core.services.rename;

import java.io.IOException;
import java.util.function.Consumer;

import org.eclipse.set.basis.exceptions.UserAbortion;
import org.eclipse.set.basis.files.ToolboxFile;
import org.eclipse.set.toolboxmodel.PlanPro.PlanPro_Schnittstelle;
import org.eclipse.swt.widgets.Shell;

/**
 * Rename a PlanPro file.
 * 
 * @author Schaefer
 */
public interface RenameService {

	/**
	 * @param shell
	 *            the parent for dialogs
	 * @param toolboxFile
	 *            the toolbox file
	 * @param planProObject
	 *            the PlanPro object
	 * @param askUser
	 *            whether to ask the user when renaming a file
	 * @param saveAction
	 *            save toolbox file action
	 * @return the (new) toolbox file for the PlanPro object
	 * 
	 * @throws IOException
	 *             if an I/O Exception occurs
	 * @throws UserAbortion
	 *             if the user aborts saving
	 */
	ToolboxFile save(Shell shell, ToolboxFile toolboxFile,
			PlanPro_Schnittstelle planProObject, boolean askUser,
			Consumer<ToolboxFile> saveAction) throws IOException, UserAbortion;
}
