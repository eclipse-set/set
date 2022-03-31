/**
 * Copyright (c) 2020 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis.attachments

import org.eclipse.set.basis.files.ToolboxFile
import org.eclipse.set.basis.guid.Guid
import java.util.function.Function
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.set.basis.MediaInfo

/**
 * Attachment with data and toolbox file.
 * 
 * @param <T>
 *            the type of the attachment
 *  
 * @author Schaefer
 */
class AttachmentInfo<T> implements MediaInfo<T> {

	@Accessors
	public T element

	@Accessors
	public byte[] data

	@Accessors
	public ToolboxFile toolboxFile

	@Accessors
	public Function<T, Guid> guidProvider

	override getGuid() {
		return guidProvider.apply(element)
	}
}
