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
import { Cant } from '@/model/Cant'
import PositionedObject from '@/model/PositionedObject'
import { LineString } from 'ol/geom'
import { Style, Stroke } from 'ol/style'
import { store } from '@/store'
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

  getFeatures (model: SiteplanState): Feature<Geometry>[] {
    return this.getObjectsModel(model).flatMap((element, index) => this.createCantFeature(element, index))
  }

  private createCantFeature (cant: Cant, index: number): Feature<Geometry>[] {
    cant.number = index
    return [
      this.createCantLineFeature(cant),
      this.createCantPointFeature(cant, cant.pointA),
      this.createCantPointFeature(cant, cant.pointB)
    ]
  }

  private createCantLineFeature (cant: Cant): Feature<Geometry> {
    const coords = [
      [
        cant.pointA.position.x,
        cant.pointA.position.y
      ],[
        cant.pointB.position.x,
        cant.pointB.position.y
      ]
    ]

    const feature =  createFeature(
      FeatureType.Cant,
      cant,
      new LineString(coords),
      cant.number.toString()
    )

    const color = `hsl(${cant.number * 137.508},50%,75%)`

    feature.setStyle((_, resolution) => {
      if (store.state.visibleCants[cant.number]) {
        const baseResolution = this.map.getView().getResolutionForZoom(this.svgService.getBaseZoomLevel())
        const scale = baseResolution / resolution
        return new Style({
          stroke: new Stroke({
            width: 5 * scale,
            color: color
          })
        })
      }

      return []
    })

    return feature
  }

  private createCantPointFeature (cant: Cant, cantPoint: PositionedObject): Feature<Geometry> {
    const feature = createFeature(
      FeatureType.Cant,
      cant,
      new OlPoint([cantPoint.position.x, cantPoint.position.y]),
      cant.number.toString()
    )

    feature.setStyle((_, resolution) => {
      const baseResolution = this.map.getView().getResolutionForZoom(this.svgService.getBaseZoomLevel())
      const scale = baseResolution / resolution

      // Determine style for the FMAComponent
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
