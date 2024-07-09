/**
 * Copyright (c) 2022 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.siteplan.transform

import org.eclipse.set.core.services.geometry.GeoKanteGeometryService
import org.eclipse.set.feature.siteplan.positionservice.PositionService
import org.eclipse.set.model.planpro.Weichen_und_Gleissperren.ENUMGleissperreVorzugslage
import org.eclipse.set.model.planpro.Weichen_und_Gleissperren.ENUMWKrGspStellart
import org.eclipse.set.model.planpro.Weichen_und_Gleissperren.W_Kr_Gsp_Element
import org.eclipse.set.model.planpro.Weichen_und_Gleissperren.W_Kr_Gsp_Komponente
import org.eclipse.set.model.siteplan.LeftRight
import org.eclipse.set.model.siteplan.SiteplanFactory
import org.eclipse.set.model.siteplan.SiteplanPackage
import org.eclipse.set.model.siteplan.TrackLockLocation
import org.eclipse.set.model.siteplan.TurnoutOperatingMode
import org.osgi.service.component.annotations.Component
import org.osgi.service.component.annotations.Reference

import static org.eclipse.set.feature.siteplan.transform.TransformUtils.*

import static extension org.eclipse.set.ppmodel.extensions.WKrGspElementExtensions.*

/**
 * Transforms a track lock from the PlanPro model to a siteplan TrackLock
 * 
 * @author Truong
 */
@Component(service=Transformator)
class TrackLockTransformator extends BaseTransformator<W_Kr_Gsp_Element> {
	@Reference
	GeoKanteGeometryService geometryService

	@Reference
	PositionService positionService

	static String lockBothSide = "beidseitig"
	static String lockOneSide = "einseitig"

	override void transform(W_Kr_Gsp_Element gspElement) {
		// Check if is a track lock
		if (gspElement.gleissperreElement?.gleissperreVorzugslage === null) {
			return
		}
		val result = SiteplanFactory.eINSTANCE.createTrackLock
		result.guid = gspElement.identitaet.wert
		result.label = getLabel(gspElement.bezeichnung)

		result.preferredLocation = gspElement.transformLocation
		result.operatingMode = gspElement.transformOperatingMode

		gspElement.WKrGspKomponenten.forEach [
			val component = SiteplanFactory.eINSTANCE.createTrackLockComponent
			component.guid = identitaet.wert
			component.position = positionService.transformPosition(
				geometryService.getCoordinate(it)
			)
			component.trackLockSignal = transformTrackLockSignal
			component.ejectionDirection = transformEjectionDirection
			result.components.add(component)
		]

		result.addSiteplanElement(
			SiteplanPackage.eINSTANCE.siteplanState_TrackLock)
	}

	private def LeftRight transformEjectionDirection(
		W_Kr_Gsp_Komponente komponente) {
		val direction = komponente.entgleisungsschuh?.auswurfrichtung?.wert
		switch (direction) {
			case ENUM_LINKS_RECHTS_LINKS:
				return LeftRight.LEFT
			case ENUM_LINKS_RECHTS_RECHTS:
				return LeftRight.RIGHT
			default:
				return null
		}
	}

	private def String transformTrackLockSignal(
		W_Kr_Gsp_Komponente komponente) {
		val lockSignal = komponente?.entgleisungsschuh?.gleissperrensignal?.wert
		if (lockSignal === null) {
			return null
		}

		switch (lockSignal) {
			case ENUM_GLEISSPERRENSIGNAL_INNENBELEUCHTET_BEIDSEITIG,
			case ENUM_GLEISSPERRENSIGNAL_REFLEKTIEREND_BEIDSEITIG:
				return lockBothSide
			case ENUM_GLEISSPERRENSIGNAL_INNENBELEUCHTET_EINSEITIG,
			case ENUM_GLEISSPERRENSIGNAL_REFLEKTIEREND_EINSEITIG:
				return lockOneSide
			default:
				return ""
		}
	}

	private def TrackLockLocation transformLocation(
		W_Kr_Gsp_Element gspElement) {
		val location = gspElement.gleissperreElement?.gleissperreVorzugslage?.
			wert
		if (location === null) {
			return null
		}
		switch (location) {
			case ENUMGleissperreVorzugslage.
				ENUM_GLEISSPERRE_VORZUGSLAGE_ABGELEGT:
				return TrackLockLocation.BESIDE_TRACK
			case ENUMGleissperreVorzugslage.
				ENUM_GLEISSPERRE_VORZUGSLAGE_AUFGELEGT:
				return TrackLockLocation.ON_TRACK
			default:
				return null
		}
	}

	private def TurnoutOperatingMode transformOperatingMode(
		W_Kr_Gsp_Element gspElement) {
		val operatingMode = gspElement.WKrGspElementAllg?.WKrGspStellart?.wert
		if (operatingMode === null) {
			return null
		}
		switch (operatingMode) {
			case ENUMWKrGspStellart.
				ENUMW_KR_GSP_STELLART_ELEKTRISCH_FERNGESTELLT,
			case ENUMWKrGspStellart.
				ENUMW_KR_GSP_STELLART_MECHANISCH_FERNGESTELLT:
				return TurnoutOperatingMode.MECHANICAL_REMOTE
			case ENUMWKrGspStellart.
				ENUMW_KR_GSP_STELLART_ELEKTRISCH_ORTSGESTELLT,
			case ENUMWKrGspStellart.
				ENUMW_KR_GSP_STELLART_MECHANISCH_ORTSGESTELLT:
				return TurnoutOperatingMode.MECHANICAL_LOCAL
			default:
				return null
		}
	}
}
