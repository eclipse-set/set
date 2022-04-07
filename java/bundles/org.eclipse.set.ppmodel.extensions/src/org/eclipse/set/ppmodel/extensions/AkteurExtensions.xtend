/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import org.eclipse.set.toolboxmodel.PlanPro.Akteur_Allg_AttributeGroup
import org.eclipse.set.toolboxmodel.PlanPro.Akteur
import java.util.Optional

/**
 * This class extends {@link Akteur} and related attribute groups.
 */
class AkteurExtensions {

	/**
	 * @param akteuerAllgemeineMerkmale this Akteur Allgemeine Merkmale
	 * 
	 * @return the optional name of this Akteur Allgemeine Merkmale
	 */
	static def Optional<String> getName(
		Akteur_Allg_AttributeGroup akteurAllgemeineMerkmale) {
		return Optional.ofNullable(akteurAllgemeineMerkmale?.nameAkteur?.wert)
	}

	/**
	 * @param akteuerAllgemeineMerkmale this Akteur Allgemeine Merkmale
	 * 
	 * @return the optional name5 of this Akteur Allgemeine Merkmale
	 */
	static def Optional<String> getName5(
		Akteur_Allg_AttributeGroup akteurAllgemeineMerkmale) {
		return Optional.ofNullable(akteurAllgemeineMerkmale?.nameAkteur5?.wert)
	}

	/**
	 * @param akteuerAllgemeineMerkmale this Akteur Allgemeine Merkmale
	 * 
	 * @return the optional name10 of this Akteur Allgemeine Merkmale
	 */
	static def Optional<String> getName10(
		Akteur_Allg_AttributeGroup akteurAllgemeineMerkmale) {
		return Optional.ofNullable(akteurAllgemeineMerkmale?.nameAkteur10?.wert)
	}
}
