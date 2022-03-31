/**
 * Copyright (c) 2016 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import de.scheidtbachmann.planpro.model.model1902.Bahnuebergang.BUE_Anlage
import de.scheidtbachmann.planpro.model.model1902.Schluesselabhaengigkeiten.Schloss
import de.scheidtbachmann.planpro.model.model1902.Schluesselabhaengigkeiten.Schlosskombination
import de.scheidtbachmann.planpro.model.model1902.Schluesselabhaengigkeiten.Schluessel
import de.scheidtbachmann.planpro.model.model1902.Schluesselabhaengigkeiten.Schluesselsperre
import de.scheidtbachmann.planpro.model.model1902.Weichen_und_Gleissperren.W_Kr_Gsp_Element
import static extension org.eclipse.set.ppmodel.extensions.ZeigerExtensions.*

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
		return schloss.schlossSk.IDSchlosskombination.resolve(
			Schlosskombination)
	}

	/**
	 * @param schloss this schloss
	 * 
	 * @returns the Schluesselsperre
	 */
	def static Schluesselsperre getSchluesselsperre(Schloss schloss) {
		return schloss?.schlossSsp?.IDSchluesselsperre.resolve(Schluesselsperre)
	}

	/**
	 * @param schloss this schloss
	 * 
	 * @returns the Schluessel
	 */
	def static Schluessel getSchluesel(Schloss schloss) {
		return schloss.IDSchluessel.resolve(Schluessel)
	}

	/**
	 * @param schloss this schloss
	 * 
	 * @returns the BUE_Anlage
	 */
	def static BUE_Anlage getBueAnlage(Schloss schloss) {
		return schloss.schlossBUE.IDBUEAnlage.resolve(BUE_Anlage)
	}

	/**
	 * @param schloss this schloss
	 * 
	 * @returns the W_Kr_Gsp_Element (W)
	 */
	def static W_Kr_Gsp_Element getGspElement(Schloss schloss) {
		return schloss.schlossGsp.IDGspElement.resolve(W_Kr_Gsp_Element)
	}

	/**
	 * @param schloss this schloss
	 * 
	 * @returns the W_Kr_Gsp_Element (W-Kr)
	 */
	def static W_Kr_Gsp_Element getWKrElement(Schloss schloss) {
		return schloss?.schlossW?.IDWKrElement.resolve(W_Kr_Gsp_Element)
	}

	/**
	 * @param schloss this schloss
	 * 
	 * @returns the Sonderanlage
	 */
	def static W_Kr_Gsp_Element getSonderanlage(Schloss schloss) {
		return schloss?.schlossSonderanlage?.IDSonderanlage.resolve(
			W_Kr_Gsp_Element)
	}
}
