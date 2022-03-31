/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions.utils

import javax.xml.datatype.XMLGregorianCalendar
import java.text.SimpleDateFormat

/**
 * Extensions for {@link XMLGregorianCalendar}.
 */
class XMLGregorianCalendarExtensions {
	
	static def String toString(XMLGregorianCalendar xmlGregorianCalendar, String format) {
		val calendar = xmlGregorianCalendar.toGregorianCalendar
		val SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(calendar.time);
	}
}
