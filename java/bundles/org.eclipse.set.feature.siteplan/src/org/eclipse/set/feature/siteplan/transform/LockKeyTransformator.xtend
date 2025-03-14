/**
 * Copyright (c) 2022 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.siteplan.transform

import org.eclipse.set.core.services.geometry.PointObjectPositionService
import org.eclipse.set.feature.siteplan.positionservice.PositionService
import org.eclipse.set.model.planpro.Schluesselabhaengigkeiten.Schloss
import org.eclipse.set.model.planpro.Schluesselabhaengigkeiten.Schluesselsperre
import org.eclipse.set.model.siteplan.LockKeyType
import org.eclipse.set.model.siteplan.SiteplanFactory
import org.eclipse.set.model.siteplan.SiteplanPackage
import org.osgi.service.component.annotations.Component
import org.osgi.service.component.annotations.Reference

import static extension org.eclipse.set.feature.siteplan.transform.TransformUtils.*

/**
 * Transform PlanPro lockkeys to Siteplan lockkeys
 */
@Component(service=Transformator)
class LockKeyTransformator extends BaseTransformator<Schluesselsperre> {
	@Reference
	PointObjectPositionService pointObjectPositionService

	@Reference
	PositionService positionService

	static val ERROR_NO_UNTERBRINGUNG = "Keine Unterbringung für Schlüsselsperre '%s' gefunden"

	override transform(Schluesselsperre lockkey) {
		val result = SiteplanFactory.eINSTANCE.createLockKey
		result.guid = lockkey.identitaet?.wert
		result.label = lockkey.bezeichnung?.label
		result.locked = transformLockStatus(lockkey)
		result.type = transformLockKeyType(lockkey)
		val coor = pointObjectPositionService.getCoordinate(
			lockkey.IDUnterbringung?.value?.punktObjektTOPKante)
		if (coor !== null) {
			result.position = positionService.transformPosition(coor)
		} else {
			recordError(result.guid,
				String.format(ERROR_NO_UNTERBRINGUNG, result.guid))
			return
		}

		lockkey.IDUnterbringung?.value?.punktObjektStrecke?.
			transformPunktObjektStrecke(result)
		addSiteplanElement(result,
			SiteplanPackage.eINSTANCE.siteplanState_Lockkeys)
	}

	def LockKeyType transformLockKeyType(Schluesselsperre schluesselsperre) {
		switch (schluesselsperre.bedienungArt?.wert) {
			case ENUM_BEDIENUNG_ART_AUSSEN,
			case ENUM_BEDIENUNG_ART_AUSSEN_AWANST:
				return LockKeyType.OUTSIDE
			case ENUM_BEDIENUNG_ART_INNEN:
				return LockKeyType.INSIDE
			default:
				return null
		}
	}

	def Boolean transformLockStatus(Schluesselsperre schluesselsperre) {
		return container.allContents.filter(Schloss).
			filter[schlossSsp !== null].findFirst [
				schlossSsp.IDSchluesselsperre?.value == schluesselsperre
			]?.schluesselInGrdstEingeschl?.wert ?: false

	}

}
