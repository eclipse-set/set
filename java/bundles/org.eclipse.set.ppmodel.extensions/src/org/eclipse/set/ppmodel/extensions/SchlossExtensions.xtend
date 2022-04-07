/**
 * Copyright (c) 2016 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import org.eclipse.set.toolboxmodel.Bahnuebergang.BUE_Anlage
import org.eclipse.set.toolboxmodel.Schluesselabhaengigkeiten.Schloss
import org.eclipse.set.toolboxmodel.Schluesselabhaengigkeiten.Schlosskombination
import org.eclipse.set.toolboxmodel.Schluesselabhaengigkeiten.Schluessel
import org.eclipse.set.toolboxmodel.Schluesselabhaengigkeiten.Schluesselsperre
import org.eclipse.set.toolboxmodel.Weichen_und_Gleissperren.W_Kr_Gsp_Element

/**
 * Extensions for {@link Schloss}.
 */
class SchlossExtensions extends BasisObjektExtensions {

	/**
	 * @param schloss this schloss
	 * 
	 * @returns the Schlosskombination
	 */
	def static Schlosskombination getSchlossKombination(Schloss schloss) {
		return schloss.schlossSk.IDSchlosskombination
	}

	/**
	 * @param schloss this schloss
	 * 
	 * @returns the Schluesselsperre
	 */
	def static Schluesselsperre getSchluesselsperre(Schloss schloss) {
		return schloss?.schlossSsp?.IDSchluesselsperre
	}

	/**
	 * @param schloss this schloss
	 * 
	 * @returns the Schluessel
	 */
	def static Schluessel getSchluesel(Schloss schloss) {
		return schloss.IDSchluessel
	}

	/**
	 * @param schloss this schloss
	 * 
	 * @returns the BUE_Anlage
	 */
	def static BUE_Anlage getBueAnlage(Schloss schloss) {
		return schloss.schlossBUE.IDBUEAnlage
	}

	/**
	 * @param schloss this schloss
	 * 
	 * @returns the W_Kr_Gsp_Element (W)
	 */
	def static W_Kr_Gsp_Element getGspElement(Schloss schloss) {
		return schloss.schlossGsp.IDGspElement
	}

	/**
	 * @param schloss this schloss
	 * 
	 * @returns the W_Kr_Gsp_Element (W-Kr)
	 */
	def static W_Kr_Gsp_Element getWKrElement(Schloss schloss) {
		return schloss?.schlossW?.IDWKrElement
	}

	/**
	 * @param schloss this schloss
	 * 
	 * @returns the Sonderanlage
	 */
	def static W_Kr_Gsp_Element getSonderanlage(Schloss schloss) {
		return schloss?.schlossSonderanlage?.IDSonderanlage
	}
}
