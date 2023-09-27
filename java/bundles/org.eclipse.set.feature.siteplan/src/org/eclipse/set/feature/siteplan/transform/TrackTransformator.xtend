/**
 * Copyright (c) 2021 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.siteplan.transform

import java.math.BigDecimal
import java.util.Set
import org.eclipse.set.feature.siteplan.positionservice.PositionService
import org.eclipse.set.feature.siteplan.trackservice.GEOKanteMetadata
import org.eclipse.set.feature.siteplan.trackservice.GEOKanteSegment
import org.eclipse.set.feature.siteplan.trackservice.TrackService
import org.eclipse.set.model.siteplan.SiteplanFactory
import org.eclipse.set.model.siteplan.SiteplanPackage
import org.eclipse.set.model.siteplan.TrackDesignation
import org.eclipse.set.model.siteplan.TrackSection
import org.eclipse.set.model.siteplan.TrackSegment
import org.eclipse.set.model.siteplan.TrackShape
import org.eclipse.set.model.siteplan.TrackType
import org.eclipse.set.toolboxmodel.BasisTypen.ENUMLinksRechts
import org.eclipse.set.toolboxmodel.BasisTypen.ENUMWirkrichtung
import org.eclipse.set.toolboxmodel.Basisobjekte.Bereich_Objekt_Teilbereich_AttributeGroup
import org.eclipse.set.toolboxmodel.Geodaten.ENUMGEOForm
import org.eclipse.set.toolboxmodel.Geodaten.GEO_Form_TypeClass
import org.eclipse.set.toolboxmodel.Geodaten.TOP_Kante
import org.eclipse.set.toolboxmodel.Gleis.Gleis_Art
import org.eclipse.set.toolboxmodel.Gleis.Gleis_Bezeichnung
import org.eclipse.set.toolboxmodel.Weichen_und_Gleissperren.W_Kr_Gsp_Komponente
import org.osgi.service.component.annotations.Component
import org.osgi.service.component.annotations.Reference

import static org.eclipse.set.toolboxmodel.Geodaten.ENUMTOPAnschluss.*

import static extension org.eclipse.set.feature.siteplan.transform.TransformUtils.*
import static extension org.eclipse.set.ppmodel.extensions.GeoKnotenExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.BasisAttributExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.BereichObjektExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.GeoKanteExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.PunktObjektExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.TopKanteExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.TopKnotenExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.WKrGspElementExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.WKrGspKomponenteExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.utils.IterableExtensions.*

/**
 * Transforms a track from the PlanPro model to a siteplan track
 * @author Peters
 */
@Component(service=Transformator)
class TrackTransformator extends BaseTransformator<TOP_Kante> {
	@Reference
	TrackService trackService

	@Reference
	PositionService positionService

	static val ERROR_NO_GLEIS_ART = "Keine Gleisart für Segment der GEO_Kante '%s' gefunden."
	static val ERROR_MULTIPLE_GLEIS_ART = "Mehrere Gleisarten für Segment der GEO_Kante '%s' gefunden."

	/**
	 * Transforms a PlanPro TOP_Kante to a Siteplan track
	 * 
	 * @param topKante the PlanPro TOP_Kante
	 * @returns a Siteplan track or null on failure
	 */
	override void transform(TOP_Kante topKante) {
		// Skip transforming continuous track segments within track switches (DKW/EKW)
		if (topKante.isContinuousTrackInSwitch)
			return;

		val track = SiteplanFactory.eINSTANCE.createTrack()
		track.guid = topKante.identitaet.wert
		val geoKantes = trackService.getGeoKanten(topKante)

		geoKantes.createTransformatorThread(this.class.name + "_" + track.guid,
			Runtime.runtime.availableProcessors, [
				val section = transformTrackSection(it)
				val md = it
				synchronized (track) {
					track.sections.add(section)
					track.designations +=
						it.segments.flatMap[bereichObjekte].filter(
							Gleis_Bezeichnung).toSet.map[transform(it, md)].
							filterNull
				}
			])

		track.addSiteplanElement(SiteplanPackage.eINSTANCE.siteplanState_Tracks)
	}

	def TrackSection transformTrackSection(GEOKanteMetadata md) {
		val section = SiteplanFactory.eINSTANCE.createTrackSection
		section.guid = md.geoKante.identitaet.wert
		section.shape = transformGeoForm(md.geoKante.GEOKanteAllg.GEOForm)
		transform(md).filter[segment|!segment.positions.empty].forEach [
			section.segments.add(it)
		]

		return section
	}

	/**
	 * @return true if the TOP_Kante represents a continouse leg of a DKW/EKW track switch 
	 */
	def boolean isContinuousTrackInSwitch(TOP_Kante topKante) {
		val trackSwitchComponents = topKante.container.WKrGspKomponente.filter [
			continuousCrossingLeg == topKante
		]
		return !trackSwitchComponents.filter [
			it.getWKrGspElement.WKrGspKomponenten.size == 2
		].empty
	}

	def TOP_Kante getContinuousCrossingLeg(W_Kr_Gsp_Komponente component) {
		val topKnoten = component.topKnoten
		val crossingSide = component.zungenpaar?.kreuzungsgleis?.wert
		if (crossingSide == ENUMLinksRechts.ENUM_LINKS_RECHTS_RECHTS) {
			return topKnoten?.topKanten?.filter [
				getTOPAnschluss(topKnoten) == ENUMTOP_ANSCHLUSS_LINKS
			]?.head
		} else if (crossingSide == ENUMLinksRechts.ENUM_LINKS_RECHTS_LINKS) {
			return topKnoten?.topKanten?.filter [
				getTOPAnschluss(topKnoten) == ENUMTOP_ANSCHLUSS_RECHTS
			]?.head
		}
	}

	static def TrackShape transformGeoForm(GEO_Form_TypeClass form) {
		switch (form.wert) {
			case ENUMGEOForm.ENUMGEO_FORM_GERADE:
				return TrackShape.STRAIGHT
			case ENUMGEOForm.ENUMGEO_FORM_BOGEN:
				return TrackShape.CURVE
			case ENUMGEOForm.ENUMGEO_FORM_KLOTHOIDE:
				return TrackShape.CLOTHOID
			case ENUMGEOForm.ENUMGEO_FORM_BLOSSKURVE:
				return TrackShape.BLOSSCURVE
			case ENUMGEOForm.ENUMGEO_FORM_BLOSS_EINFACH_GESCHWUNGEN:
				return TrackShape.BLOSS_CURVED_SIMPLE
			case ENUMGEOForm.ENUMGEO_FORM_SONSTIGE:
				return TrackShape.OTHER
			case ENUMGEOForm.ENUMGEO_FORM_RICHTGERADE_KNICK_AM_ENDE_200_GON:
				return TrackShape.DIRECTIONAL_STRAIGHT_KINK_END
			case ENUMGEOForm.ENUMGEO_FORM_KM_SPRUNG:
				return TrackShape.KM_JUMP
			case ENUMGEOForm.ENUMGEO_FORM_UEBERGANGSBOGEN_SFORM:
				return TrackShape.TRANSITION_CURVE_SFORM
			case ENUMGEOForm.ENUMGEO_FORM_SFORM_EINFACH_GESCHWUNGEN:
				return TrackShape.SFORM_SIMPLE_CURVED
			default:
				return TrackShape.UNKNOWN
		}
	}

	def Iterable<TrackSegment> transform(GEOKanteMetadata geoKante) {
		return geoKante.segments.map[transform(it, geoKante)]
	}

	/**
	 * Transforms a PlanPro GEO_Kante to a Siteplan track section
	 * 
	 * @param geoKante the PlanPro GEO_Kante
	 * @param startDistance the distance from the start of the TOP_Kante to the GEO_Kante
	 * @param coordinates a list of coordinates for this section
	 * @returns a Siteplan track or null on failure
	 */
	private def TrackSegment transform(GEOKanteSegment segment,
		GEOKanteMetadata md) {
		val result = SiteplanFactory.eINSTANCE.createTrackSegment
		val geoKante = md.geoKante

		result.positions.addAll(segment.getCoordinates(md).map [
			positionService.transformPosition(it)
		])
		result.type.addAll(segment.getTrackType(md))
		val geoKnotenA = geoKante.geoKnotenA
		// Record an error if there is not exactly one track type defined for the segment 
		val center = geoKante.getCoordinate(geoKnotenA,
			geoKante.GEOKanteAllg.GEOLaenge.wert.doubleValue / 2, 0,
			ENUMWirkrichtung.ENUM_WIRKRICHTUNG_IN)
		val guid = geoKante.identitaet.wert
		if (result.type.length > 1) {
			recordError(guid, String.format(ERROR_MULTIPLE_GLEIS_ART, guid),
				positionService.transformCoordinate(center.geometricInformation,
					geoKnotenA.CRS))
		} else if (result.type.length == 0) {
			recordError(guid, String.format(ERROR_NO_GLEIS_ART, guid),
				positionService.transformCoordinate(center.geometricInformation,
					geoKnotenA.CRS))
		}

		return result
	}

	private def Set<TrackType> getTrackType(GEOKanteSegment segment,
		GEOKanteMetadata md) {
		val trackTypeAreas = segment.bereichObjekte.filter(Gleis_Art).distinctBy [
			it.gleisart.wert
		].toSet
		val siteplanTrackType = trackTypeAreas.map[transformTrackType].toSet
		if (trackTypeAreas.length > 1) {
			for (var i = 0; i < trackTypeAreas.length; i++) {
				val currentArea = trackTypeAreas.get(i)
				for (var j = i + 1; j < trackTypeAreas.length; j++) {
					val length = currentArea.getOverlappingLength(
						trackTypeAreas.get(j))
					if (length.compareTo(BigDecimal.ZERO) === 0) {
						return Set.of(siteplanTrackType.minBy[value])
					}
				}
			}
		}
		return siteplanTrackType
	}

	private def transformTrackType(Gleis_Art gleisart) {
		switch (gleisart.gleisart.wert) {
			case ENUM_GLEISART_ANSCHLUSSGLEIS:
				return TrackType.CONNECTING_TRACK
			case ENUM_GLEISART_DURCHGEHENDES_HAUPTGLEIS:
				return TrackType.PASSING_MAIN_TRACK
			case ENUM_GLEISART_HAUPTGLEIS:
				return TrackType.MAIN_TRACK
			case ENUM_GLEISART_NEBENGLEIS:
				return TrackType.SIDE_TRACK
			case ENUM_GLEISART_SONSTIGE:
				return TrackType.OTHER
			case ENUM_GLEISART_STRECKENGLEIS:
				return TrackType.ROUTE_TRACK
		}
	}

	/**
	 * Transform a PlanPro Gleis_Bezeichnung to a Siteplan track designation
	 * 
	 * @param gleisBezeichnung {@link Gleis_Bezeichnung}
	 * @param md {@link GEOKanteMetadata}
	 * @return {@link TrackDesignation}
	 */
	private def TrackDesignation transform(
		Gleis_Bezeichnung gleisBezeichnung,
		GEOKanteMetadata md
	) {
		val result = SiteplanFactory.eINSTANCE.createTrackDesignation
		result.name = gleisBezeichnung.bezeichnung.bezGleisBezeichnung.wert
		// Find the longest stretch
		val maxTB = gleisBezeichnung.bereichObjektTeilbereich.max [ a, b |
			a.length.compareTo(b.length)
		]

		// Is the stretch within this GEO_Kante?	
		if (maxTB.IDTOPKante?.identitaet?.wert !=
			md.geoKante.topKante.identitaet?.wert)
			return null

		val centerDistance = maxTB.length / 2
		if (centerDistance < md.start || centerDistance >= md.end)
			return null;

		result.position = positionService.transformPosition(
			md.getCoordinate(centerDistance, 0,
				ENUMWirkrichtung.ENUM_WIRKRICHTUNG_IN))
		return result
	}

	def double getLength(Bereich_Objekt_Teilbereich_AttributeGroup tb) {
		return tb.begrenzungB.wert.doubleValue - tb.begrenzungA.wert.doubleValue

	}

}
