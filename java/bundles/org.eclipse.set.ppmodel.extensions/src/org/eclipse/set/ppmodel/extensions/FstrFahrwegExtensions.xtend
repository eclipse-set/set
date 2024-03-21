/**
 * Copyright (c) 2023 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import org.eclipse.set.model.planpro.Fahrstrasse.Fstr_DWeg
import org.eclipse.set.model.planpro.Fahrstrasse.Fstr_Fahrweg

import static extension org.eclipse.set.ppmodel.extensions.BasisAttributExtensions.*

class FstrFahrwegExtensions {
	
	static def Iterable<Fstr_DWeg> getFstrDweg(Fstr_Fahrweg farhweg) {
		return farhweg.container.fstrDWeg.filter[IDFstrFahrweg === farhweg]
	}
}