/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions.exception

import org.eclipse.set.toolboxmodel.Geodaten.TOP_Kante
import java.util.List
import org.eclipse.set.toolboxmodel.Fahrstrasse.Fstr_DWeg

class AreaNotContinuous extends RuntimeException {
	
	new() {
	}
	
	/**
	 * @param start the start TOP Kante
	 * @param end the end TOP Kante
	 * @param topKanten the list of TOP Kanten
	 */
	new(
		TOP_Kante start,
		TOP_Kante end,
		List<TOP_Kante> kanten
	) {
		super(
			'''start=«start.msgString» kanten=«FOR kante: kanten SEPARATOR ", "»«
				kante.msgString»«ENDFOR» end=«end.msgString»'''
		)
	}
	
	new(
		AreaNotContinuous e,
		Fstr_DWeg dweg
	) {
		super(
			'''dweg=«dweg.identitaet.wert» «e.message»'''
		)
	}
	
	private static def String getMsgString(TOP_Kante kante) {
		return '''[«kante.IDTOPKnotenA.identitaet.wert» «kante.IDTOPKnotenB.identitaet.wert»]'''
	}
}
