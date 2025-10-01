/**
 * Copyright (c) 2021 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.siteplan.transform

import java.text.ParseException
import java.util.List
import org.eclipse.set.basis.IModelSession
import org.eclipse.set.core.services.geometry.GeoKanteGeometryService
import org.eclipse.set.core.services.siteplan.SiteplanService
import org.eclipse.set.feature.siteplan.positionservice.PositionService
import org.eclipse.set.model.planpro.Layoutinformationen.PlanPro_Layoutinfo
import org.eclipse.set.model.planpro.PlanPro.PlanPro_Schnittstelle
import org.eclipse.set.model.siteplan.Siteplan
import org.eclipse.set.model.siteplan.SiteplanObject
import org.eclipse.set.model.siteplan.SiteplanPackage
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup
import org.osgi.service.component.annotations.Component
import org.osgi.service.component.annotations.Reference
import org.osgi.service.component.annotations.ReferenceCardinality
import org.osgi.service.component.annotations.ReferencePolicy
import org.osgi.service.component.annotations.ReferencePolicyOption

import static extension org.eclipse.set.feature.siteplan.transform.TransformUtils.*
import static extension org.eclipse.set.ppmodel.extensions.GeoKnotenExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.PlanProSchnittstelleExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.PlanungProjektExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.StreckeExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.StreckePunktExtensions.*
import java.math.BigDecimal
import org.eclipse.set.model.siteplan.TrackSwitch

/**
 * Transforms the PlanPro data model into the siteplan data model 
 * 
 * @author Stuecker
 */
@Component(service=SiteplanTransformator)
class SiteplanTransformatorImpl extends AbstractSiteplanTransformator {
	@Reference(cardinality=ReferenceCardinality.
		MULTIPLE, policy=ReferencePolicy.
		DYNAMIC, policyOption=ReferencePolicyOption.
		GREEDY, target="(component.name=org.eclipse.set.feature.siteplan.transform.*)")
	public final List<Transformator> transformators = newArrayList

	@Reference
	protected GeoKanteGeometryService geometryService

	@Reference
	protected PositionService positionService

	@Reference
	protected SiteplanService siteplanService

	override Siteplan transform(IModelSession modelSession) {
		val siteplan = super.transform(modelSession)
		if (siteplan === null) {
			return null;
		}
		siteplan.transformLayout(modelSession.layoutInformation)
		siteplan.eAllContents.filter(SiteplanObject).filter[guid !== null].
			forEach [
				if (it instanceof TrackSwitch) {
					components.forEach [ componente |
						siteplanService.addSiteplanElement(componente)
					]
				} else {
					siteplanService.addSiteplanElement(it)	
				}
			]
		return siteplan
	}

	def void transformLayout(Siteplan siteplan, PlanPro_Layoutinfo layoutinfo) {
		val layoutTransform = new LayoutTransformator(layoutinfo,
			positionService)
		layoutTransform.transformLayout(siteplan)
	}

	override create SiteplanPackage.eINSTANCE.siteplanFactory.createSiteplanState transformState(
		MultiContainer_AttributeGroup container) {
		transformators.createTransformatorThread(
			this.class.name,
			transformators.size,
			[ transformator |
				transformator.transformContainer(it, container)
			]
		)
	}

	override getLeadingPosition(PlanPro_Schnittstelle planproSchnittstelle,
		MultiContainer_AttributeGroup container) {
		// Add main track information
		val mainRouteInfo = planproSchnittstelle?.LSTPlanungProjekt?.
			leadingPlanungGruppe?.planungGFuehrendeStrecke

		if (mainRouteInfo === null) {
			return null
		}

		// Main route lookup from the list of routes
		val trackNumber = mainRouteInfo.streckeNummer.wert
		val mainRoutes = container.strecke.filter [ s |
			s.bezeichnung.bezeichnungStrecke.wert == trackNumber
		]

		if (mainRoutes.empty) {
			return null
		}

		var BigDecimal routeKm
		try {
			routeKm = new BigDecimal(
				mainRouteInfo.streckeKm.wert.replace(",", ""))
		} catch (ParseException | NumberFormatException | NullPointerException exc) {
			return null
		}
		val mainRoute = mainRoutes.head
		// Determine the coordinate at the point
		try {
			val mainCoordinate = mainRoute.getKilometerCoordinate(routeKm)
			val startEnd = mainRoute.startEnd
			if (startEnd === null)
				return null
			val crs = startEnd.head.geoKnoten.CRS
			return positionService.transformPosition(mainCoordinate, crs)
		} catch (RuntimeException exc) {
			return null
		}
	}
}
