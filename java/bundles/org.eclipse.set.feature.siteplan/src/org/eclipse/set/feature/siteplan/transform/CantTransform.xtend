/**
 * Copyright (c) 2023 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.siteplan.transform

import org.eclipse.set.feature.siteplan.positionservice.PositionService
import org.eclipse.set.feature.siteplan.trackservice.TrackService
import org.eclipse.set.model.siteplan.PositionedObject
import org.eclipse.set.model.siteplan.SiteplanFactory
import org.eclipse.set.model.siteplan.SiteplanPackage
import org.eclipse.set.toolboxmodel.Geodaten.Ueberhoehung
import org.eclipse.set.toolboxmodel.Geodaten.Ueberhoehungslinie
import org.osgi.service.component.annotations.Component
import org.osgi.service.component.annotations.Reference

/**
 * Transforms PlanPro Ueberhoehungslinie to siteplan Cants 
 */
@Component(service=Transformator)
class CantTransform extends BaseTransformator<Ueberhoehungslinie> {
	@Reference
	TrackService trackService

	@Reference
	PositionService positionService

	override void transform(Ueberhoehungslinie ue) {
		// Do not process incomplete cants
		if (ue.IDUeberhoehungA === null || ue.IDUeberhoehungA === null) {
			return
		}

		val it = SiteplanFactory.eINSTANCE.createCant

		guid = ue.identitaet?.wert
		form = ue.ueberhoehungslinieAllg?.ueberhoehungslinieForm?.wert?.
			toString ?: ""
		val ueLength = ue.ueberhoehungslinieAllg?.ueberhoehungslinieLaenge?.wert
		if (ueLength !== null)
			length = ueLength.doubleValue

		pointA = transformUeberhoehung(ue.IDUeberhoehungA)
		pointB = transformUeberhoehung(ue.IDUeberhoehungB)

		addSiteplanElement(it, SiteplanPackage.eINSTANCE.siteplanState_Cants)
	}

	private def PositionedObject transformUeberhoehung(Ueberhoehung ue) {
		val result = SiteplanFactory.eINSTANCE.createPositionedObject
		result.guid = ue.identitaet?.wert
		result.position = positionService.transformPosition(
			trackService.getCoordinate(ue)
		)
		return result
	}

}
