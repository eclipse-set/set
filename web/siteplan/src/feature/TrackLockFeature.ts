/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

import LageplanFeature from '@/feature/LageplanFeature'
import { Position } from '@/model/Position'
import { SiteplanState } from '@/model/SiteplanModel'
import { getLabelColor } from '@/model/SiteplanObject'
import { ISvgElement } from '@/model/SvgElement'
import TrackLock, { TrackLockComponent, TrackLockLocation } from '@/model/TrackLock'
import { angle, midpoint } from '@/util/Math'
import { updateLabelColor, updateLabelOrientation } from '@/util/ModelExtensions'
import SvgDrawTracklock from '@/util/SVG/Draw/SvgDrawTracklock'
import { Feature } from 'ol'
import { Extent, getHeight, getWidth } from 'ol/extent'
import Geometry from 'ol/geom/Geometry'
import Point from 'ol/geom/Point'
import { FeatureType, createFeature } from './FeatureInfo'

export default class TrackLockFeature extends LageplanFeature<TrackLock> {
  protected getObjectsModel (model: SiteplanState): TrackLock[] {
    return model.trackLock
  }

  protected getObjectSvg (tracklock: TrackLock): ISvgElement {
    return this.svgService.getFeatureSvg(tracklock, FeatureType.TrackLock, tracklock.label)
  }

  getFeatures (model: SiteplanState): Feature<Geometry>[] {
    return model.trackLock.map(ele => this.createTrackLockFeature(ele))
  }

  private createTrackLockFeature (trackLock: TrackLock): Feature<Geometry> {
    const position = this.getTrackLockPosition(trackLock.components)
    const feature = createFeature(
      FeatureType.TrackLock,
      trackLock,
      new Point([position.x, position.y]),
      trackLock.label?.text
    )

    let rotation = position.rotation
    if (trackLock.preferredLocation === TrackLockLocation.onTrack) {
      rotation += 90
    }

    const svg = this.getObjectSvg(trackLock)
    this.createTrackLockBBox(feature, trackLock, position, svg, rotation)
    feature.setStyle((_, resolution) => {
      const baseResolution = this.map.getView().getResolutionForZoom(this.svgService.getBaseZoomLevel())
      const scale = baseResolution / resolution

      updateLabelOrientation(trackLock.label, position.rotation, this.map)
      updateLabelColor(trackLock.label, getLabelColor(trackLock))

      const style = this.svgService.getFeatureStyle(trackLock, FeatureType.TrackLock, trackLock.label)
      style.getImage()?.setScale(scale)
      style.getImage()?.setRotation((rotation * Math.PI) / 180)
      return style
    })
    return feature
  }

  private getTrackLockPosition (components: TrackLockComponent[]): Position {
    if (components.length === 1) {
      return components[0].position
    }

    return components.map(x => x.position).reduce((current, next) => {
      const midPoint = midpoint([current.x, current.y], [next.x, next.y])
      const rotation = angle([current.x, current.y], [next.x, next.y])

      return {
        x: midPoint[0],
        y: midPoint[1],
        rotation
      }
    })
  }

  private createTrackLockBBox (
    feature: Feature<Geometry>,
    trackLock: TrackLock,
    pos: Position,
    trackLockSvg: ISvgElement,
    rotation: number
  ): void {
    const offsetFactor = trackLock.preferredLocation === TrackLockLocation.besideTrack ? -1 : 1
    // Line width
    const offSet = 1.25
    let trackLockBBoxs = new Array<Extent>().concat(trackLockSvg.boundingBox)
    const isBesideTrack = trackLock.preferredLocation === TrackLockLocation.besideTrack
    // create bbox for tracklock signal
    if (trackLockSvg.nullpunkt) {
      const trackLockSignal = trackLockSvg.boundingBox.filter(bbox => bbox[0] !== 0 && bbox[1] !== 0)
      trackLockBBoxs = trackLockBBoxs.filter(bbox => !trackLockSignal.includes(bbox))
      trackLockSignal.forEach((bbox, index) => {
        let translate = [0, 0]

        // Translate bounding box of track lock signal circle up or down
        if (bbox[0] !== 0 && bbox[1] !== 0 && trackLockSvg.nullpunkt) {
          // Define transform direction of this bbox
          const factor = offsetFactor * Math.pow(-1, index + 1)
          translate = [
            factor * (getWidth(bbox) / 2 + offSet),
            factor * (trackLockSvg.nullpunkt.y + getHeight(bbox) / 2 + offSet)
          ]
        }

        if (isBesideTrack) {
          translate[0] -= SvgDrawTracklock.getOffsetX(trackLock, trackLockSvg.boundingBox) - 5
        }

        const position = { x: pos.x, y: pos.y, rotation }
        LageplanFeature.createBBox(feature, position, bbox, translate, [1, 1])
      })
    }

    const arrowBBox = trackLockBBoxs.reduce((prev, curr) =>
      getWidth(prev) < getWidth(curr) ? prev : curr)
    if (arrowBBox) {
      const arrowRotation = rotation - 45
      const position = {
        x: pos.x,
        y: pos.y,
        rotation: isBesideTrack ? arrowRotation - 90 : arrowRotation
      }
      LageplanFeature.createBBox(feature, position, arrowBBox, [0, 0], [1, 1])
    }

    trackLockBBoxs.filter(ele => ele !== arrowBBox).forEach(bbox => {
      const position = { x: pos.x,y: pos.y,rotation }
      LageplanFeature.createBBox(feature, position, bbox, [0, 0], [1, 1])
    })
  }

  compareChangedState (initial: SiteplanState, final: SiteplanState): Feature<Geometry>[] {
    return super.compareChangedState(initial, final, [], trackLock => this.getObjectSvg(trackLock))
  }
}
