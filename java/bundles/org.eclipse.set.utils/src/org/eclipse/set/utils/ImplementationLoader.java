/**
 * Copyright (c) 2019 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils;

import java.text.MessageFormat;

/**
 * Loader for framework specific implementation
 * 
 * @author roeber
 *
 */
public class ImplementationLoader {

	/**
	 * @param type
	 *            class type an instance will be created of
	 * @return instance instance of passed class type
	 */
	public static Object newInstance(final Class<?> type) {
		final String name = type.getName();
		Object result = null;
		try {
			result = type.getClassLoader().loadClass(name + "Impl") //$NON-NLS-1$
					.getDeclaredConstructor().newInstance();
		} catch (final Throwable throwable) {
			final String txt = "Could not load implementat ion for {0} "; //$NON-NLS-1$
			final String msg = MessageFormat.format(txt, new Object[] { name });
			throw new RuntimeException(msg, throwable);
		}
		return result;
	}
}
