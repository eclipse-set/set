/**
 * Copyright (c) 2020 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis.commands;

import java.io.IOException;

import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.set.basis.MediaInfo;

/**
 * This command can add attachment data and can undo the adding.
 * 
 * @param <T>
 *            the type of the attachment
 * 
 * @author Schaefer
 */
public class AddAttachmentData<T> extends AbstractCommand {

	/**
	 * @param <T>
	 *            the type of the attachment
	 * 
	 * @param mediaInfo
	 *            the element info
	 * 
	 * @return the new add attachment data command
	 */
	public static <T> AddAttachmentData<T> create(
			final MediaInfo<T> mediaInfo) {
		return new AddAttachmentData<>(mediaInfo);
	}

	private final MediaInfo<T> mediaInfo;

	private AddAttachmentData(final MediaInfo<T> mediaInfo) {
		this.mediaInfo = mediaInfo;
	}

	@Override
	public void execute() {
		try {
			mediaInfo.getToolboxFile()
					.createMedia(mediaInfo.getGuid(), mediaInfo.getData());
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void redo() {
		// we do not support redoing currently
		throw new UnsupportedOperationException();
	}

	@Override
	public void undo() {
		try {
			mediaInfo.getToolboxFile().deleteMedia(mediaInfo.getGuid());
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected boolean prepare() {
		return mediaInfo.getToolboxFile().hasDetachedAttachments();
	}
}
