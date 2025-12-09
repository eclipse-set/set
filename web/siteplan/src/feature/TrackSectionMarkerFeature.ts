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
import Track from '@/model/Track'
import TrackSection from '@/model/TrackSection'
import { store } from '@/store'
import { Feature } from 'ol'
import { Coordinate as OlCoordinate } from 'ol/coordinate'
import Geometry from 'ol/geom/Geometry'
import OlPoint from 'ol/geom/Point'
import { Style } from 'ol/style'
import { createFeature, FeatureType } from './FeatureInfo'

/**
 * Container for all track section marker features
 *
 * @author Stuecker
 */

export default class TrackSectionMarkerFeature extends LageplanFeature<Track> {
  // In LOD-mode, use a fixed scale equivalent to 1:1000
  static LOD_VIEW_SCALE = 0.337

  protected getObjectsModel (model: SiteplanState): Track[] {
    return model.tracks
  }

  getFeatures (model: SiteplanState): Feature<Geometry>[] {
    return this.getObjectsModel(model).flatMap(track =>
      track?.sections.flatMap(section =>
        this.createTrackSectionMarkerFeature([section.startCoordinate.x, section.startCoordinate.y], section)))
  }

  private createTrackSectionMarkerFeature (position: OlCoordinate, trackSection: TrackSection): Feature<Geometry> {
    const feature = createFeature(
      FeatureType.TrackSectionMarker,
      trackSection,
      new OlPoint(position)
    )

    feature.setStyle((_, resolution) => {
      if (!store.state.trackSectionColorVisible) {
        return new Style()
      }

      const baseResolution = this.map.getView().getResolutionForZoom(this.svgService.getBaseZoomLevel())
      const scale = baseResolution / resolution

      // Determine style for the marker
      const style = this.svgService.getFeatureStyle(
        { type: FeatureType.TrackSectionMarker },
        FeatureType.TrackSectionMarker
      )

      // Rescale the feature according to the current zoom level
      // to keep a constant size
      style.getImage()?.setScale(scale)

      return style
    })

    return feature
  }

  setFeatureColor (feature: Feature<Geometry>): Feature<Geometry> {
    return feature
  }
}
