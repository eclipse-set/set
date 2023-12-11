/**
 * Copyright (c) 2023 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

import LageplanFeature from '@/feature/LageplanFeature'

import { SiteplanState } from '@/model/SiteplanModel'
import { ISvgElement } from '@/model/SvgElement'
import Geometry from 'ol/geom/Geometry'
import OlPoint from 'ol/geom/Point'
import { createFeature, FeatureType } from './FeatureInfo'
import { Cant, CantPoint } from '@/model/Cant'
import { Feature } from 'ol'

/**
 * Container for all Cant features
 */
export default class CantFeature extends LageplanFeature<Cant> {
  protected getObjectsModel (model: SiteplanState): Cant[] {
    return model.cants
  }

  protected getObjectSvg (cant: Cant): ISvgElement {
    return this.svgService.getFeatureSvg(
      cant,
      FeatureType.Cant
    )
  }

  private ensureCantsSetExists (cant: CantPoint) {
    if (!('cants' in cant)) {
      (cant as CantPoint).cants = new Set()
    }
  }

  getFeatures (model: SiteplanState): Feature<Geometry>[] {
    const cantLines = this.getObjectsModel(model)
    const cantPoints = new Set<CantPoint>()

    cantLines.forEach(line => {
      this.ensureCantsSetExists(line.pointB)

      let existingPointA = [...cantPoints].find(cp => cp.guid === line.pointA.guid)
      if (!existingPointA) {
        this.ensureCantsSetExists(line.pointA)
        cantPoints.add(line.pointA)
        existingPointA = line.pointA
      }

      existingPointA.cants.add(line)

      let existingPointB = [...cantPoints].find(cp => cp.guid === line.pointB.guid)
      if (!existingPointB) {
        this.ensureCantsSetExists(line.pointB)
        cantPoints.add(line.pointB)
        existingPointB = line.pointB
      }

      existingPointB.cants.add(line)
    })

    return [...cantPoints].map(element => this.createCantFeature(element))
  }

  private createCantFeature (cantPoint: CantPoint): Feature<Geometry> {
    const feature = createFeature(
      FeatureType.Cant,
      cantPoint,
      new OlPoint([cantPoint.position.x, cantPoint.position.y]),
      cantPoint.guid
    )

    feature.setStyle((_, resolution) => {
      const baseResolution = this.map.getView().getResolutionForZoom(this.svgService.getBaseZoomLevel())
      const scale = baseResolution / resolution

      // Determine style for the Cant
      const style = this.svgService.getFeatureStyle(cantPoint, FeatureType.Cant)

      // Rescale the feature according to the current zoom level
      // to keep a constant size
      style
        .getImage()
        .setScale(scale)

      // Rotate the feature
      style.getImage().setRotation((cantPoint.position.rotation * Math.PI) / 180)

      return style
    })
    return feature
  }

  compareChangedState (initial: SiteplanState, final: SiteplanState): Feature<Geometry>[] {
    return super.compareChangedState(initial, final, [], cant => this.getObjectSvg(cant))
  }
}
