/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis;

/**
 * A translateable can provide a key for look-up a translation.
 * 
 * @author Schaefer
 */
public interface Translateable {

	/**
	 * @return the key for look-up a translation
	 */
	String getKey();
}
