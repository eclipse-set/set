/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
import LageplanFeature from '@/feature/LageplanFeature'
import { SiteplanState } from '@/model/SiteplanModel'
import { getColor } from '@/model/SiteplanObject'
import TrackSwitchEndMarker from '@/model/TrackSwitchEndMarker'
import { Feature } from 'ol'
import Geometry from 'ol/geom/Geometry'
import Polygon from 'ol/geom/Polygon'
import { Stroke, Style } from 'ol/style'
import { createFeature, FeatureType } from './FeatureInfo'

/**
 * Container for all track switch features
 *
 * @author Stuecker
 */
export default class TrackSwitchEndMarkerFeature extends LageplanFeature<TrackSwitchEndMarker> {
  protected getObjectsModel (model: SiteplanState): TrackSwitchEndMarker[] {
    return model.trackSwitchEndMarkers
  }

  getFeatures (model: SiteplanState): Feature<Geometry>[] {
    return this.getObjectsModel(model).map(marker => this.createTrackSwitchEndMarkerFeature(marker))
  }

  private createTrackSwitchEndMarkerFeature (marker: TrackSwitchEndMarker): Feature<Geometry> {
    const coordinates: number[][] = [
      [marker.legACoordinate.x, marker.legACoordinate.y],
      [marker.legBCoordinate.x, marker.legBCoordinate.y]
    ]

    const feature = createFeature(
      FeatureType.TrackSwitchEndMarker,
      marker,
      new Polygon([coordinates])
    )

    feature.setStyle((_, resolution) => {
      const baseResolution = this.map.getView().getResolutionForZoom(this.svgService.getBaseZoomLevel())
      const scale = baseResolution / resolution
      return new Style({
        stroke: new Stroke({
          color: getColor(marker) ?? 'black',
          width: 2 * scale
        })
      })
    })

    return feature
  }
}
