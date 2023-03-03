/**
 * Copyright (c) 2017 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions.utils

import org.eclipse.set.toolboxmodel.PlanPro.PlanPro_Schnittstelle
import org.eclipse.set.basis.FreeFieldInfo
import org.eclipse.set.basis.IModelSession

/**
 * Transformation from {@link IModelSession} to {@link FreeFieldInfo}.
 * 
 * @author Schaefer
 */
class PlanProToFreeFieldTransformation {

	private new() {
	}

	/**
	 * Creates a new transformation.
	 */
	def static PlanProToFreeFieldTransformation create() {
		return new PlanProToFreeFieldTransformation
	}

	/**
	 * Transforms a PlanPro Schnittstelle to a FreeFieldInfo.
	 */
	def FreeFieldInfo create new FreeFieldInfo transform(
		IModelSession session) {
		val filename = session.toolboxFile.path.fileName.toString
		val timestamp = session.planProSchnittstelle.timestamp
		val checksum = session.toolboxFile.checksum
		significantInformation = '''«filename» «timestamp» MD5: «checksum»'''
		return
	}

	private def String getTimestamp(PlanPro_Schnittstelle schnittstelle) {
		return String.format("%1$td.%1$tm.%1$tY %1$tT",
			schnittstelle.planProSchnittstelleAllg.erzeugungZeitstempel.wert.
				toGregorianCalendar)
	}
}
