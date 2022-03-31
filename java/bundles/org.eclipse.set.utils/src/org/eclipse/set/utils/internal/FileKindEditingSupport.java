/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.internal;

import java.util.Arrays;
import java.util.List;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;

import org.eclipse.set.basis.attachments.Attachment;
import org.eclipse.set.basis.attachments.FileKind;

/**
 * Support for editing the file kind.
 * 
 * @author Schaefer
 */
public class FileKindEditingSupport extends EditingSupport {

	private final String[] fileKindItems;
	private final List<FileKind> fileKinds;
	private final TableViewer viewer;

	/**
	 * @param viewer
	 *            the table viewer
	 * @param fileKinds
	 *            the file kinds
	 */
	public FileKindEditingSupport(final TableViewer viewer,
			final List<FileKind> fileKinds) {
		super(viewer);
		this.viewer = viewer;
		this.fileKindItems = new String[fileKinds.size()];
		this.fileKinds = fileKinds;
		for (int i = 0; i < fileKindItems.length; i++) {
			fileKindItems[i] = fileKinds.get(i).getTranslation();
		}
	}

	@Override
	protected boolean canEdit(final Object element) {
		return true;
	}

	@Override
	protected CellEditor getCellEditor(final Object element) {
		return new ComboBoxCellEditor(viewer.getTable(), fileKindItems,
				SWT.READ_ONLY);
	}

	@Override
	protected Object getValue(final Object element) {
		final Attachment attachment = (Attachment) element;
		final FileKind fileKind = attachment.getFileKind();
		for (int i = 0; i < fileKindItems.length; i++) {
			if (fileKindItems[i].equals(fileKind.getTranslation())) {
				return Integer.valueOf(i);
			}
		}
		throw new IllegalArgumentException(
				fileKind + " not in " + Arrays.asList(fileKindItems)); //$NON-NLS-1$
	}

	@Override
	protected void setValue(final Object element, final Object value) {
		final Attachment attachment = (Attachment) element;
		attachment.setFileKind(fileKinds.get(((Integer) value).intValue()));
	}
}
