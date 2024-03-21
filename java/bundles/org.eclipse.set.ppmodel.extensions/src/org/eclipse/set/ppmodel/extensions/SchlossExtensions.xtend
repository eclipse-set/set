/**
 * Copyright (c) 2016 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import org.eclipse.set.model.planpro.Bahnuebergang.BUE_Anlage
import org.eclipse.set.model.planpro.Schluesselabhaengigkeiten.Schloss
import org.eclipse.set.model.planpro.Schluesselabhaengigkeiten.Schlosskombination
import org.eclipse.set.model.planpro.Schluesselabhaengigkeiten.Schluessel
import org.eclipse.set.model.planpro.Schluesselabhaengigkeiten.Schluesselsperre
import org.eclipse.set.model.planpro.Weichen_und_Gleissperren.W_Kr_Gsp_Element

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
		return schloss.schlossSk.IDSchlosskombination?.value
	}

	/**
	 * @param schloss this schloss
	 * 
	 * @returns the Schluesselsperre
	 */
	def static Schluesselsperre getSchluesselsperre(Schloss schloss) {
		return schloss?.schlossSsp?.IDSchluesselsperre?.value
	}

	/**
	 * @param schloss this schloss
	 * 
	 * @returns the Schluessel
	 */
	def static Schluessel getSchluesel(Schloss schloss) {
		return schloss.IDSchluessel?.value
	}

	/**
	 * @param schloss this schloss
	 * 
	 * @returns the BUE_Anlage
	 */
	def static BUE_Anlage getBueAnlage(Schloss schloss) {
		return schloss.schlossBUE.IDBUEAnlage?.value
	}

	/**
	 * @param schloss this schloss
	 * 
	 * @returns the W_Kr_Gsp_Element (W)
	 */
	def static W_Kr_Gsp_Element getGspElement(Schloss schloss) {
		return schloss.schlossGsp.IDGspElement?.value
	}

	/**
	 * @param schloss this schloss
	 * 
	 * @returns the W_Kr_Gsp_Element (W-Kr)
	 */
	def static W_Kr_Gsp_Element getWKrElement(Schloss schloss) {
		return schloss?.schlossW?.IDWKrElement?.value
	}

	/**
	 * @param schloss this schloss
	 * 
	 * @returns the Sonderanlage
	 */
	def static W_Kr_Gsp_Element getSonderanlage(Schloss schloss) {
		return schloss?.schlossSonderanlage?.IDSonderanlage?.value
	}
}
