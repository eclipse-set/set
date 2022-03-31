/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.attachment;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.set.basis.attachments.Attachment;
import org.eclipse.set.basis.attachments.FileKind;
import org.eclipse.set.basis.extensions.PathExtensions;
import org.eclipse.set.basis.files.ToolboxFileFilter;
import org.eclipse.set.basis.files.ToolboxFileFilter.InvalidFilterFilename;
import org.eclipse.set.basis.guid.Guid;
import org.eclipse.set.core.services.dialog.DialogService;
import org.eclipse.set.utils.ImplementationLoader;
import org.eclipse.swt.widgets.Shell;

/**
 * Utilities for {@link Attachment}s.
 * 
 * @author Schaefer
 */
public abstract class Attachments {

	static class AttachmentImpl implements Attachment {

		private static final String PDF_EXTENSION = "pdf"; //$NON-NLS-1$
		private final byte[] content;
		private FileKind fileKind;
		private final Path filename;
		private final String id;

		public AttachmentImpl(final Path filename, final FileKind fileKind,
				final byte[] content) {
			this.filename = filename;
			this.content = content;
			this.fileKind = fileKind;

			id = Guid.create().toString();
		}

		@Override
		public String getBaseFilename() {
			return PathExtensions.getBaseFileName(filename);
		}

		@Override
		public byte[] getData() {
			return content;
		}

		@Override
		public String getFileExtension() {
			return PathExtensions.getExtension(filename);
		}

		@Override
		public FileKind getFileKind() {
			return fileKind;
		}

		@Override
		public String getFullFilename() {
			return filename.getFileName().toString();
		}

		@Override
		public String getId() {
			return id;
		}

		@Override
		public Object getOriginal() {
			return null;
		}

		@Override
		public boolean isPdf() {
			return getFileExtension().toLowerCase().equals(PDF_EXTENSION);
		}

		@Override
		public void setFileKind(final FileKind fileKind) {
			this.fileKind = fileKind;
		}
	}

	private final static Attachments IMPL;

	static {
		IMPL = (Attachments) ImplementationLoader
				.newInstance(Attachments.class);
	}

	/**
	 * Opens a file dialog and saves an attachment.
	 * 
	 * @param shell
	 *            the parent shell
	 * @param attachment
	 *            the attachment
	 * @param dialogService
	 *            the dialog service
	 * @param exportDir
	 *            the export directory
	 */
	public static void export(final Shell shell, final Attachment attachment,
			final DialogService dialogService, final String exportDir) {
		IMPL.exportInternal(shell, attachment, dialogService, exportDir);
	}

	/**
	 * @param path
	 *            path to file
	 * @return content of file stored @path
	 */
	public static byte[] getContent(final Path path) {
		try (final FileInputStream input = new FileInputStream(path.toFile())) {
			final List<byte[]> arrays = new LinkedList<>();
			for (int available = input
					.available(); available > 0; available = input
							.available()) {
				final byte[] b = new byte[available];
				input.read(b);
				arrays.add(b);
			}
			int size = 0;
			for (final byte[] a : arrays) {
				size = size + a.length;
			}
			final byte[] content = new byte[size];
			int position = 0;
			for (final byte[] a : arrays) {
				copyArray(a, content, position);
				position = position + a.length;
			}
			return content;
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Opens a file dialog and loads an attachment.
	 * 
	 * @param shell
	 *            the parent shell
	 * @param fileKind
	 *            the file kind
	 * @param dialogService
	 *            the dialog service
	 * @param extensions
	 *            filter extensions
	 * 
	 * @return the attachment or <code>null</code>
	 * 
	 * @throws InvalidFilterFilename
	 *             if the chosen filename does not match a given filter
	 *             extension
	 */
	public static Attachment load(final Shell shell, final FileKind fileKind,
			final DialogService dialogService,
			final List<ToolboxFileFilter> extensions)
			throws InvalidFilterFilename {
		return IMPL.loadInternal(shell, fileKind, dialogService, extensions);
	}

	private static void copyArray(final byte[] from, final byte[] to,
			final int position) {
		for (int i = 0; i < from.length; i++) {
			to[position + i] = from[i];
		}
	}

	abstract void exportInternal(final Shell shell, final Attachment attachment,
			final DialogService dialogService, final String tempDir);

	abstract Attachment loadInternal(final Shell shell, final FileKind fileKind,
			final DialogService dialogService,
			final List<ToolboxFileFilter> extensions)
			throws InvalidFilterFilename;

}
