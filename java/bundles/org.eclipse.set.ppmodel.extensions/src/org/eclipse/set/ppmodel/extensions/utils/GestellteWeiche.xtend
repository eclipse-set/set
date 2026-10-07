/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions.utils

import org.eclipse.set.model.planpro.Weichen_und_Gleissperren.W_Kr_Gsp_Element
import java.util.Objects

/**
 * A {@link W_Kr_Gsp_Element} with a Lage.
 * 
 * @author Schaefer
 */
class GestellteWeiche {
	
	/**
	 * The Lage of a gestellte Weiche.
	 */
	static enum Lage {
		L, R
	}
	
	public val W_Kr_Gsp_Element element
	
	public val Lage lage

	/**
	 * @param element the element
	 * @param lage the Lage
	 */	
	new(W_Kr_Gsp_Element element, Lage lage) {
		this.element = element
		this.lage = lage
	}
	
	/**
	 * @return the Bezeichnung of the gestellte Weiche
	 */
	def String getBezeichnung() {
		return '''«element.bezeichnung.bezeichnungTabelle.wert» «lage»'''
	}
	
	override int hashCode() {
		return Objects.hash(element, lage)
	}
	
	override boolean equals(Object obj) {
		return hashCode == Objects.hashCode(obj)
	}
	
}
