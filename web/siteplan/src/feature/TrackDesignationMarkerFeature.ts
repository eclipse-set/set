/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

import LageplanFeature from '@/feature/LageplanFeature'
import { Label } from '@/model/Label'
import { SiteplanState } from '@/model/SiteplanModel'
import { getLabelColor } from '@/model/SiteplanObject'
import Track, { TrackDesignation } from '@/model/Track'
import { updateLabelColor, updateLabelOrientation } from '@/util/ModelExtensions'
import { Feature } from 'ol'
import Geometry from 'ol/geom/Geometry'
import OlPoint from 'ol/geom/Point'
import { createFeature, FeatureType } from './FeatureInfo'

/**
 * Container for all track features
 *
 * @author Stuecker
 */
export default class TrackDesignationMarkerFeature extends LageplanFeature<Track> {
  protected getObjectsModel (model: SiteplanState): Track[] {
    return model.tracks
  }

  getFeatures (model: SiteplanState): Feature<Geometry>[] {
    return this.getObjectsModel(model).flatMap(track =>
      track?.designations?.map(designation =>
        this.createTrackDesignationFeature(track, designation)))
  }

  private createTrackDesignationFeature (track: Track, designation: TrackDesignation): Feature<Geometry> {
    const feature = createFeature(
      FeatureType.TrackDesignationMarker,
      track,
      new OlPoint([designation.position.x, designation.position.y])
    )
    const label: Label = { text: designation.name, orientationInverted: false, color: [0, 0, 0] }
    const rotation = designation.position.rotation

    feature.setStyle((_, resolution) => {
      const baseResolution = this.map.getView().getResolutionForZoom(this.svgService.getBaseZoomLevel())
      const scale = baseResolution / resolution
      updateLabelOrientation(label, rotation, this.map)
      updateLabelColor(label, getLabelColor(track))
      const style = this.svgService.getFeatureStyle({ label }, FeatureType.TrackDesignationMarker, label)
      // Rescale the feature according to the current zoom level
      // to keep a constant size
      style
        .getImage()
        .setScale(scale)

      style
        .getImage()
        .setRotation((rotation * Math.PI) / 180)
      return style
    })
    return feature
  }

  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  setFeatureColor (feature: Feature<Geometry>): Feature<Geometry> {
    return feature
  }
}
