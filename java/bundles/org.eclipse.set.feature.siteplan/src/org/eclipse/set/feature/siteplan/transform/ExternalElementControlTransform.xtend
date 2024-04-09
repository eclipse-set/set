/**
 * Copyright (c) 2021 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.siteplan.transform

import org.eclipse.set.model.siteplan.ControlStationType
import org.eclipse.set.model.siteplan.ExternalElementControlArt
import org.eclipse.set.model.siteplan.SiteplanFactory
import org.eclipse.set.model.siteplan.SiteplanPackage
import org.eclipse.set.model.planpro.Ansteuerung_Element.Aussenelementansteuerung
import org.eclipse.set.model.planpro.Ansteuerung_Element.ENUMAussenelementansteuerungArt
import org.eclipse.set.model.planpro.Bedienung.Bedien_Platz
import org.osgi.service.component.annotations.Component

import static extension org.eclipse.set.feature.siteplan.transform.TransformUtils.transformPunktObjektStrecke
import static extension org.eclipse.set.ppmodel.extensions.AussenelementansteuerungExtensions.*
import org.osgi.service.component.annotations.Reference
import org.eclipse.set.feature.siteplan.trackservice.TrackService
import org.eclipse.set.feature.siteplan.positionservice.PositionService

@Component(service=Transformator)
class ExternalElementControlTransform extends BaseTransformator<Aussenelementansteuerung> {
	@Reference
	TrackService trackService;
	
	@Reference
	PositionService positionService;
	static val ERROR_NO_UNTERBRINGUNG = "Keine Unterbringung für Aussenelementansteuerung '%s' gefunden"
	override transform(Aussenelementansteuerung aea) {
		val result = SiteplanFactory.eINSTANCE.createExternalElementControl
 		result.guid = aea.identitaet.wert
		result.label = SiteplanFactory.eINSTANCE.createLabel
		result.label.text = aea.bezeichnung?.bezeichnungAEA?.wert
		result.elementType = transformControlArt(aea)
		val coordinate = trackService.getCoordinateAt(aea.unterbringung?.punktObjektTOPKante, 0.0)
		if (coordinate !== null) {
			result.position = positionService.transformPosition(coordinate)
		} else {
			recordError(result.guid, String.format(ERROR_NO_UNTERBRINGUNG, result.guid))
			return
		}
		
		result.controlStation = transformControlStation(aea)
		aea.unterbringung?.punktObjektStrecke?.transformPunktObjektStrecke(result)
		result.addSiteplanElement(SiteplanPackage.eINSTANCE.siteplanState_ExternalElementControls)
	}
	
	def ExternalElementControlArt transformControlArt(
		Aussenelementansteuerung aussenelementansteuerung
	) {
		switch(aussenelementansteuerung.AEAAllg.aussenelementansteuerungArt.wert) {
			case ENUMAussenelementansteuerungArt.ENUM_AUSSENELEMENTANSTEUERUNG_ART_ESTW_A:
				return ExternalElementControlArt.ESTW_A
			case ENUMAussenelementansteuerungArt.ENUM_AUSSENELEMENTANSTEUERUNG_ART_FE_AK:
				return ExternalElementControlArt.FE_AK
			case ENUMAussenelementansteuerungArt.ENUM_AUSSENELEMENTANSTEUERUNG_ART_GFK:
				return ExternalElementControlArt.GFK
			case ENUMAussenelementansteuerungArt.ENUM_AUSSENELEMENTANSTEUERUNG_ART_GLEISFREIMELDE_INNENANLAGE:
				return ExternalElementControlArt.GLEISFREIMELDE_INNENANLAGE
			case ENUMAussenelementansteuerungArt.ENUM_AUSSENELEMENTANSTEUERUNG_ART_OBJEKTCONTROLLER:
				return ExternalElementControlArt.OBJEKTCONTROLLER
			case ENUMAussenelementansteuerungArt.ENUM_AUSSENELEMENTANSTEUERUNG_ART_SONSTIGE:
				return ExternalElementControlArt.SONSTIGE
			case ENUMAussenelementansteuerungArt.ENUM_AUSSENELEMENTANSTEUERUNG_ART_VIRTUELLE_AUSSENELEMENTANSTEUERUNG:
				return ExternalElementControlArt.VIRTUELLE_AUSSENELEMENTANSTEUERUNG
			default:
				return null
		}
	}
	
	def ControlStationType transformControlStation(Aussenelementansteuerung aea) {
		val controlStation = container.allContents.filter(Bedien_Platz)
			.findFirst[it.IDUnterbringung.value === aea.unterbringung]
		if (controlStation === null) {
			return ControlStationType.WITHOUT_CONTROL	
		}
		
		switch (controlStation.bedienPlatzAllg.bedienPlatzArt.wert) {
			case ENUM_BEDIEN_PLATZ_ART_NOT_BPS:
				return ControlStationType.EMERGENCY_CONTROL
			case ENUM_BEDIEN_PLATZ_ART_NOT_BPS_ABGESETZT:
				return ControlStationType.EMERGENCY_CONTROL_DISPOSE
			case ENUM_BEDIEN_PLATZ_ART_STANDARD_BPS:
				return ControlStationType.DEFAULT_CONTROL
			case ENUM_BEDIEN_PLATZ_ART_STANDARD_BPS_ABGESETZT:
				return ControlStationType.DEFAULT_CONTROL_DISPOSE
			case ENUM_BEDIEN_PLATZ_ART_SONSTIGE:
				return ControlStationType.OTHER
		}
	}
	
}