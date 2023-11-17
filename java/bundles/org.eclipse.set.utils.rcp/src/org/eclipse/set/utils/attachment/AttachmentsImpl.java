/**
 * Copyright (c) 2019 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.attachment;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.eclipse.set.basis.attachments.Attachment;
import org.eclipse.set.basis.attachments.FileKind;
import org.eclipse.set.basis.files.ToolboxFileFilter;
import org.eclipse.set.basis.files.ToolboxFileFilter.InvalidFilterFilename;
import org.eclipse.set.core.services.dialog.DialogService;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Shell;

/**
 * framework specific implementation of {@link Attachments}.
 * 
 * @author roeber
 */
public class AttachmentsImpl extends Attachments {

	@Override
	void exportInternal(final Shell shell, final Attachment attachment,
			final DialogService dialogService, final String exportDir) {
		final DirectoryDialog dialog = new DirectoryDialog(shell);
		if (exportDir != null) {
			dialog.setFilterPath(exportDir);
		}
		final String dirname = dialog.open();
		if (dirname != null) {
			final Path path = Paths.get(dirname, attachment.getFullFilename());
			if (path.toFile().exists()) {
				final boolean overwrite = dialogService.confirmOverwrite(shell,
						path);
				if (!overwrite) {
					return;
				}
			}
			final byte[] data = attachment.getData();
			try (final FileOutputStream stream = new FileOutputStream(
					path.toString());) {
				stream.write(data);
				dialogService.openDirectoryAfterExport(shell, path.getParent());
			} catch (final IOException e1) {
				dialogService.error(shell, e1);
			}
		}
	}

	@Override
	Attachment loadInternal(final Shell shell, final FileKind fileKind,
			final DialogService dialogService,
			final List<ToolboxFileFilter> extensions)
			throws InvalidFilterFilename {
		final Optional<Path> optionalPath = dialogService.openFileDialog(shell,
				extensions, Optional.empty());
		if (optionalPath.isPresent()) {
			final Path path = optionalPath.get();
			ToolboxFileFilter.check(extensions, path);
			byte[] content = null;
			final Path pathFilename = path.getFileName();
			content = getContent(path);
			return new AttachmentImpl(pathFilename, fileKind, content);
		}
		return null;
	}
}
