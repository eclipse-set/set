/**
 * Copyright (c) 2020 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions.utils

import org.eclipse.set.model.planpro.PlanPro.Name_Akteur_10_TypeClass
import org.eclipse.set.model.planpro.PlanPro.Name_Akteur_5_TypeClass
import org.eclipse.set.model.planpro.PlanPro.Name_Akteur_TypeClass
import org.eclipse.set.model.planpro.PlanPro.PlanProFactory

/** 
 * Transform Name Akteur elements.
 * 
 * @author Schaefer
 */
class NameAkteurTransformation {

	/**
	 * @param nameAkteur the Name Akteur to transform
	 * @param type the type to transform to
	 * 
	 * @return the transformed Name Akteur
	 */
	def <T> T transform(Name_Akteur_TypeClass nameAkteur, Class<T> type) {
		switch (type) {
			case Name_Akteur_5_TypeClass:
				return nameAkteur.transform5 as T
			case Name_Akteur_10_TypeClass:
				return nameAkteur.transform10 as T
			default:
				throw new IllegalArgumentException(type.simpleName)
		}
	}

	private def Name_Akteur_5_TypeClass create PlanProFactory.eINSTANCE.createName_Akteur_5_TypeClass
		transform5(Name_Akteur_TypeClass nameAkteur) {
		wert = nameAkteur.wert.shortenTo(5)

		return
	}

	private def Name_Akteur_10_TypeClass create PlanProFactory.eINSTANCE.createName_Akteur_10_TypeClass
		transform10(Name_Akteur_TypeClass nameAkteur) {
		wert = nameAkteur.wert.shortenTo(10)

		return
	}

	private static def String shortenTo(String text, int length) {
		if (text === null) {
			return text
		}
		val endIndex = Math.min(text.length, length)
		return text.substring(0, endIndex)
	}
}
