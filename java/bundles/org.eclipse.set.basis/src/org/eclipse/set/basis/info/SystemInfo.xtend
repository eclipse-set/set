/**
 * Copyright (c) 2019 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis.info

import org.eclipse.swt.widgets.Display

/** 
 * Find out some system info.
 * 
 * @author Schaefer
 */
class SystemInfo {

	/** 
	 * @return the java info
	 */
	def static String getJavaInfo() {
		return '''«javaVendor» «javaVersion»'''
	}

	/** 
	 * @return the java vendor
	 */
	def static String getJavaVendor() {
		return System.getProperty("java.vendor")
	}

	/** 
	 * @return the java version
	 */
	def static String getJavaVersion() {
		return System.getProperty("java.version")
	}

	/** 
	 * @return the resolution
	 */
	def static String getResolution() {
		return '''«FOR m : Display.^default.monitors SEPARATOR ", "»«m.bounds.width»x«m.bounds.height»«ENDFOR»'''
	}
}
