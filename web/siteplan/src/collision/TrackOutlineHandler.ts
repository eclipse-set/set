/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

import { createFeature, FeatureLayerType, FeatureType, getFeatureData } from '@/feature/FeatureInfo'
import NamedFeatureLayer from '@/util/NamedFeatureLayer'
import { Feature } from 'ol'
import Geometry from 'ol/geom/Geometry'

export default class TrackOutlineHandler{
  public getTrackOutlineLookUp (layer: NamedFeatureLayer[]): Feature<Geometry>[] | undefined {
    if (Array.isArray(layer)) {
      const collisionLayer = (layer as NamedFeatureLayer[]).find(c => c.getLayerType() === FeatureLayerType.Collision)
      if (!collisionLayer) {
        return
      }

      const trackOutlineFeatures = collisionLayer.getFeaturesByType(FeatureType.TrackOutline)
      if (!trackOutlineFeatures) {
        return
      }

      const newOutlineFeature = trackOutlineFeatures.map(feautre => {
        const featureData = getFeatureData(feautre)
        const geometry = featureData.outlineGeometry as Geometry
        return createFeature(FeatureType.TrackOutline, featureData, geometry)
      })
      // return new PolygonLookup(getGeoPolygons(newOutlineFeature))
      return newOutlineFeature
    }

    return undefined
  }
}
