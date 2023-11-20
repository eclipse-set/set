/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

import { Label } from '@/model/Label'
import { SiteplanState } from '@/model/SiteplanModel'
import { getLabelColor } from '@/model/SiteplanObject'
import { ISvgElement } from '@/model/SvgElement'
import TrackClose, { TrackCloseType } from '@/model/TrackClose'
import { updateLabelColor, updateLabelOrientation } from '@/util/ModelExtensions'
import { Feature } from 'ol'
import { getCenter } from 'ol/extent'
import { Geometry } from 'ol/geom'
import OlPoint from 'ol/geom/Point'
import { createFeature, FeatureType } from './FeatureInfo'
import LageplanFeature from './LageplanFeature'

/**
 * Contains all feature of track close
 * @author Truong
 */
export default class TrackCloseFeature extends LageplanFeature<TrackClose> {
  protected getObjectsModel (model: SiteplanState): TrackClose[] {
    return model.trackClosures
  }

  protected getObjectSvg (trackclose: TrackClose, label?: Label | null): ISvgElement {
    return this.svgService.getFeatureSvg(
      trackclose,
      FeatureType.TrackClose,
      label ?? undefined
    )
  }

  getFeatures (model: SiteplanState): Feature<Geometry>[] {
    return this.getObjectsModel(model).map(trackClose => this.createTrackCloseFeature(trackClose))
  }

  createTrackCloseFeature (trackClose: TrackClose): Feature<Geometry> {
    const feature = createFeature(
      FeatureType.TrackClose,
      trackClose,
      new OlPoint([trackClose.position.x, trackClose.position.y])
    )
    const trackCloseLabel = this.getTrackCloseLabel(trackClose)

    const svg = this.getObjectSvg(trackClose, trackCloseLabel)
    this.createTrackCloseBBox(feature, trackClose, svg)
    feature.setStyle((_, resolution) => {
      const baseResolution = this.map.getView().getResolutionForZoom(this.svgService.getBaseZoomLevel())
      const scale = baseResolution / resolution

      updateLabelOrientation(trackCloseLabel, trackClose.position.rotation, this.map)
      updateLabelColor(trackCloseLabel, getLabelColor(trackClose))
      const style = this.svgService.getFeatureStyle(trackClose, FeatureType.TrackClose, trackCloseLabel ?? undefined)
      style.getImage().setScale(scale)

      style.getImage().setRotation((trackClose.position.rotation * Math.PI) / 180)
      return style
    })

    return feature
  }

  private getTrackCloseLabel (trackClose: TrackClose): Label | null {
    switch (trackClose.trackCloseType) {
      case TrackCloseType.FixedBufferStop:
      case TrackCloseType.FrictionBufferStop:
      case TrackCloseType.HeadRamp:
      case TrackCloseType.ThresholCross:
        return null
      default:
        return {
          text: trackClose.trackCloseType,
          orientationInverted: true,
          color: [0, 0, 0]
        } as Label
    }
  }

  createTrackCloseBBox (feature: Feature<Geometry>, trackClose: TrackClose, svg: ISvgElement) {
    svg.boundingBox.forEach(bbox => {
      const tranlsate = getCenter(bbox)
      LageplanFeature.createBBox(feature, trackClose.position, bbox, [tranlsate[0], -tranlsate[1]], [1, 1])
    })
  }

  compareChangedState (initial: SiteplanState, final: SiteplanState): Feature<Geometry>[] {
    return super.compareChangedState(initial, final, [], trackClose => this.getObjectSvg(trackClose))
  }
}
