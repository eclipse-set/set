/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

import { getFeatureBoundArea, getFeatureData } from '@/feature/FeatureInfo'
import { Feature } from 'ol'
import { getCenter } from 'ol/extent'
import { Polygon } from 'ol/geom'
import Geometry from 'ol/geom/Geometry'
import GeometryCollection from 'ol/geom/GeometryCollection'
import { Rectangle } from './CollisionExtension'

export default class BoundingBoxesHandler {
  public rescaleBoundingBoxes (
    bboxFeatures: Feature<Geometry>[],
    oldScale: number,
    newScale: number
  ): Feature<Geometry>[] {
    const clone = Array<Feature<Geometry>>().concat(bboxFeatures)
    const scaleFactor = newScale / oldScale
    clone.forEach(feature => {
      const featureData = getFeatureData(feature)
      if (!featureData) {
        return
      }

      const geo = feature.getGeometry()
      if (!geo) {
        return
      }

      const boundsArea = getFeatureBoundArea(feature)
      switch (geo.getType()) {
        case 'GeometryCollection':
          const geometries = (geo as GeometryCollection).getGeometries().map((x, i) => {
            const center = getCenter(x.getExtent())
            x.scale(scaleFactor, scaleFactor, center)
            if (x.getType() === 'Polygon') {
              boundsArea.splice(i, 1, this.transformToRectAngle(x as Polygon))
            }

            return x
          })
          feature.setGeometry(new GeometryCollection(geometries))
          break
        default:
          const center = getCenter(geo.getExtent())
          geo.scale(scaleFactor, scaleFactor, center)

          boundsArea.splice(0, 1, this.transformToRectAngle(geo as Polygon))
          feature.setGeometry(geo)
          break
      }
    })
    return clone
  }

  private transformToRectAngle (poly: Polygon): Rectangle {
    return poly.getCoordinates()[0].map(c => [c[0],c[1]]) as Rectangle
  }
}
