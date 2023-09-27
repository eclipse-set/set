/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

import LageplanFeature from '@/feature/LageplanFeature'
import { KMMarker, Route } from '@/model/Route'
import { SiteplanState } from '@/model/SiteplanModel'
import { getLabelColor } from '@/model/SiteplanObject'
import { updateLabelColor, updateLabelOrientation } from '@/util/ModelExtensions'
import Feature from 'ol/Feature'
import Geometry from 'ol/geom/Geometry'
import OlPoint from 'ol/geom/Point'
import { createFeature, FeatureType, getFeatureData } from './FeatureInfo'

export default class RouteMarkerFeature extends LageplanFeature<Route> {
  protected getObjectsModel (model: SiteplanState): Route[] {
    return model.routes
  }

  setFeatureColor (feature: Feature<Geometry>, color?: number[] | undefined): Feature<Geometry> {
    this.setObjectColor(
      getFeatureData(feature),
      'marker',
      color ?? [0, 0, 0]
    )
    return feature
  }

  getFeatures (model: SiteplanState): Feature<Geometry> [] {
    return this.getObjectsModel(model).flatMap(route =>
      route?.markers?.flatMap(marker =>
        this.createRouteMarkerFeature(marker, route)))
  }

  private createRouteMarkerFeature (marker: KMMarker, route: Route): Feature<Geometry> {
    const feature = createFeature(
      FeatureType.RouteMarker,
      marker,
      new OlPoint([marker.position.x, marker.position.y])
    )

    feature.setStyle((_, resolution) => {
      const baseResolution = this.map.getView().getResolutionForZoom(this.svgService.getBaseZoomLevel())
      const scale = baseResolution / resolution

      // Determine style for the marker
      const label = {
        text: (marker.value / 1000).toFixed(1).replace('.', ','),
        orientationInverted: true,
        color: [0, 0, 0]
      }
      updateLabelOrientation(label, marker.position.rotation, this.map)
      updateLabelColor(label, getLabelColor(route))
      const style = this.svgService.getFeatureStyle(marker, FeatureType.RouteMarker, label)

      // Rescale the feature according to the current zoom level
      // to keep a constant size
      style
        .getImage()
        .setScale(scale)

      // Rotate the feature
      style.getImage().setRotation(((marker.position.rotation) * Math.PI) / 180)

      return style
    })

    return feature
  }
}
