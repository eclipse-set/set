/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions;

import de.scheidtbachmann.planpro.model.model1902.Basisobjekte.Anhang
import org.eclipse.set.basis.files.ToolboxFile
import org.eclipse.set.basis.guid.Guid
import de.scheidtbachmann.planpro.model.model1902.Basisobjekte.BasisobjekteFactory
import de.scheidtbachmann.planpro.model.model1902.Basisobjekte.Daten_TypeClass
import java.io.IOException
import de.scheidtbachmann.planpro.model.model1902.PlanPro.PlanPro_Schnittstelle

/**
 * Extensions for {@link Anhang}.
 * 
 * @author Schaefer
 */
class AnhangExtensions {

	/**
	 * @param this Anhang
	 * 
	 * @return the filename of this Anhang
	 */
	static def String getFilename(Anhang anhang) {
		return '''«anhang?.anhangAllg?.dateiname?.wert».«anhang?.anhangAllg?.dateityp?.wert?.literal»'''
	}
	
	/**
	 * get Content of Anhang from Zipped Planpro format
	 * @param anhang
	 * 		 this Anhang
	 * @param toolboxfile
	 * 		toolboxfile of {@link PlanPro_Schnittstelle}
	 * @retun the content of this anhang in Zipped file
	 */
	static def Daten_TypeClass getAnhangContentfromMedia(Anhang anhang, ToolboxFile toolboxfile) {
			try {
				val anhangbyteArr = toolboxfile.getMedia(Guid.create(anhang.identitaet.wert))
				var daten = BasisobjekteFactory.eINSTANCE.createDaten_TypeClass
				daten.wert = anhangbyteArr
				return daten
			} catch (IOException exc) {
				return null
			} 
	}
}
