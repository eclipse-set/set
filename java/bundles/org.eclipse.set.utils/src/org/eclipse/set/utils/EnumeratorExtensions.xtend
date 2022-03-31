/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils

import org.eclipse.emf.common.util.Enumerator
import java.util.regex.Pattern

/**
 * Extensions for {@link Enumerator}.
 * 
 * @author Schaefer
 */
class EnumeratorExtensions {
	
	/**
	 * @param enumerator this enumerator
	 * @param regex the regular expression
	 * 
	 * @return whether the enumerator literal matches the given expression
	 */
	static def boolean matches(Enumerator enumerator, String regex) {
		return Pattern.matches(regex, enumerator.literal)
	}
}
