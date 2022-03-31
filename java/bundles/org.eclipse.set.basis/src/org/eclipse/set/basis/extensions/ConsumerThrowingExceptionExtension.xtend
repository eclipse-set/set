/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis.extensions

import org.eclipse.set.basis.ConsumerThrowingException
import java.util.function.Consumer

/**
 * Extensions for {@link ConsumerThrowingException}.
 * 
 * @author Schaefer
 */
class ConsumerThrowingExceptionExtension {
	static def <T, R> Consumer<T> rethrowException(
		ConsumerThrowingException<T> t
	) {
		return [x|try {
			t.accept(x)
		} catch (Exception e) {
			throw new RuntimeException(e)
		}]
	}
}
