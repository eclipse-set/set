/**
 * Copyright (c) 2019 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.application.renameservice;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Consumer;

import jakarta.inject.Inject;

import org.eclipse.set.basis.constants.ExportType;
import org.eclipse.set.basis.exceptions.UserAbortion;
import org.eclipse.set.basis.extensions.PathExtensions;
import org.eclipse.set.basis.files.ToolboxFile;
import org.eclipse.set.basis.rename.RenameConfirmation;
import org.eclipse.set.core.services.dialog.DialogService;
import org.eclipse.set.core.services.files.ToolboxFileService;
import org.eclipse.set.core.services.rename.RenameService;
import org.eclipse.set.ppmodel.extensions.PlanProSchnittstelleExtensions;
import org.eclipse.set.model.planpro.PlanPro.PlanPro_Schnittstelle;
import org.eclipse.swt.widgets.Shell;

/**
 * Implementation for {@link RenameService}.
 * 
 * @author Schaefer
 */
public class RenameServiceImpl implements RenameService {

	@Inject
	private DialogService dialogService;

	@Inject
	ToolboxFileService fileService;

	@Override
	public ToolboxFile save(final Shell shell, final ToolboxFile toolboxFile,
			final PlanPro_Schnittstelle planProObject, final boolean askUser,
			final Consumer<ToolboxFile> saveAction)
			throws UserAbortion, IOException {
		// old and new Path
		final Path oldPath = toolboxFile.getPath();
		final Path directory = oldPath.getParent();
		final String extension = PathExtensions.getExtension(oldPath);
		final Path newPath = PlanProSchnittstelleExtensions.getDerivedPath(
				planProObject, directory.toString(), extension,
				ExportType.PLANNING_RECORDS);
		final boolean rename = !oldPath.equals(newPath);

		ToolboxFile newToolboxFile = null;

		boolean deleteOld = false;
		if (rename) {
			if (askUser) {
				// confirm renaming
				final String oldFilename = oldPath.getFileName().toString();
				final String newFilename = newPath.getFileName().toString();
				final RenameConfirmation renameConfirmation = dialogService
						.confirmRename(shell, oldFilename, newFilename);
				switch (renameConfirmation) {
				case ABORT:
					throw new UserAbortion();
				case CHANGE:
					deleteOld = true;
					break;
				case NEW:
					deleteOld = false;
					break;
				default:
					throw new IllegalArgumentException(
							renameConfirmation.toString());
				}
			}

			// confirm overwriting
			if (Files.exists(newPath)
					&& !dialogService.confirmOverwrite(shell, newPath)) {
				throw new UserAbortion();
			}

			// create new toolbox file
			newToolboxFile = fileService.create(toolboxFile);
			newToolboxFile.setPath(newPath);
		} else {
			newToolboxFile = toolboxFile;
		}

		// remove old file
		if (deleteOld && rename) {
			toolboxFile.delete(false);
		}

		// save
		saveAction.accept(newToolboxFile);

		return newToolboxFile;
	}
}
