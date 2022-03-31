/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.basis.extensions;

import java.io.InputStream;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.eclipse.core.runtime.Assert;

/**
 * Extensions for {@link Source}.
 * 
 * @author Schaefer
 */
public class SourceExtensions {

	/**
	 * @param classContext
	 *            the class context
	 * @param name
	 *            name of the desired resource
	 * 
	 * @return a source for the resource
	 */
	@SuppressWarnings("resource")
	public static StreamSource getResourceAsSource(final Class<?> classContext,
			final String name) {
		// IMPROVE: It's not clear, if the returned input stream is closed.
		// But it's also not clear, if it makes sense to close a stream that
		// later maybe used by StreamSource. Does StreamSource close the stream?
		final InputStream inputStream = classContext.getResourceAsStream(name);
		Assert.isNotNull(inputStream);
		return new StreamSource(inputStream);
	}

	private SourceExtensions() {
	}
}
