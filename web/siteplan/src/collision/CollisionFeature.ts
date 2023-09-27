/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

import { Rectangle } from '@/collision/CollisionExtension'
import NamedFeatureLayer from '@/util/NamedFeatureLayer'
import { Feature } from 'ol'
import { Coordinate } from 'ol/coordinate'
import Geometry from 'ol/geom/Geometry'
import GeometryCollection from 'ol/geom/GeometryCollection'
import Point from 'ol/geom/Point'
import Polygon from 'ol/geom/Polygon'
import VectorSource from 'ol/source/Vector'
import { Fill, Stroke, Style } from 'ol/style'
import { StyleFunction } from 'ol/style/Style'
import { createFeature, FeatureLayerType, FeatureType, getFeatureBoundArea, getFeatureBounds, getFeatureGUID } from '../feature/FeatureInfo'

/**
 * Container for all collision features
 *
 * @author Truong
 */
export interface CollisionFeatureData {
  guid: string
  refFeature: Feature<Geometry>
  originalPos: Coordinate
  originalStyleFunction: StyleFunction | undefined
}

export default class CollisionFeature {
  public static addCollisionFeatures (layers: NamedFeatureLayer[]) {
    const features = layers.map(layer => layer.getSource())
      .filter((c): c is VectorSource<Geometry> => c != null)
      .flatMap(c => c.getFeatures())

    const layer = layers.find(c => c.getLayerType() === FeatureLayerType.Collision)
    features.forEach(feature => {
      const bounds = getFeatureBounds(feature)
      if (bounds.length > 0) {
        const bbox = this.createBBoxFeature(feature, bounds)
        bounds.forEach(polygon => {
          const coors = polygon.getCoordinates()[0].map(c => [c[0], c[1]])
          getFeatureBoundArea(bbox).push(coors as Rectangle)
        })
        layer?.getSource()?.addFeature(bbox)
      }
    })
  }

  private static createBBoxFeature (refFeature: Feature<Geometry>, polygon: Polygon[]): Feature<Geometry> {
    const refGeo = refFeature.getGeometry()
    if (!refGeo) {
      throw new Error('Missing geometry')
    }

    let originalPos = [0, 0]
    if ('getCoordinates' in refGeo) {
      originalPos = (refGeo as Point).getCoordinates()
    }

    const data: CollisionFeatureData = {
      guid: getFeatureGUID(refFeature),
      refFeature,
      originalPos,
      originalStyleFunction: refFeature.getStyleFunction()
    }
    const feature = createFeature(
      FeatureType.Collision,
      data,
      new GeometryCollection(polygon)
    )
    feature.setStyle(this.createBBoxStyle())
    return feature
  }

  private static createBBoxStyle (): Style {
    return new Style({
      fill: new Fill({
        color: 'rgba(0,255,255, 0.1)'
      }),
      stroke: new Stroke({
        width: 1,
        color: 'rgba(0,255,255, 1)'
      })
    })
  }
}
