/**
 * Copyright (c) 2020 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis.extensions

import com.google.common.util.concurrent.ExecutionError
import java.lang.reflect.InvocationTargetException
import javax.xml.transform.TransformerConfigurationException
import org.eclipse.set.basis.exceptions.FileExportException

/**
 * Extensions for exceptions.
 * 
 * @author Schaefer
 */
class Exceptions {

	/**
	 * @param e the throwable
	 * 
	 * @return whether the given throwable is caused by a thread death
	 */
	static def boolean isCausedByThreadDeath(Throwable e) {
		return e.causedByThreadDeathDispatch
	}

	private static def dispatch boolean isCausedByThreadDeathDispatch(Throwable e) {
		return false
	}

	private static def dispatch boolean isCausedByThreadDeathDispatch(ThreadDeath e) {
		return true
	}

	private static def dispatch boolean isCausedByThreadDeathDispatch(
		ExecutionError e) {
		return e.cause.causedByThreadDeathDispatch
	}

	private static def dispatch boolean isCausedByThreadDeathDispatch(
		TransformerConfigurationException e) {
		return e.cause.causedByThreadDeathDispatch
	}

	private static def dispatch boolean isCausedByThreadDeathDispatch(
		InvocationTargetException e) {
		return e.cause.causedByThreadDeathDispatch
	}

	private static def dispatch boolean isCausedByThreadDeathDispatch(
		FileExportException e) {
		return e.exception.causedByThreadDeathDispatch
	}
}
