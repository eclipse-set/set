/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

import LageplanFeature from '@/feature/LageplanFeature'
import { SiteplanState } from '@/model/SiteplanModel'
import { Platform, Station, StationPart } from '@/model/Station'
import { isLabelFlipRequired } from '@/util/ModelExtensions'
import { Feature } from 'ol'
import Geometry from 'ol/geom/Geometry'

import { getColor, getLabelColor } from '@/model/SiteplanObject'
import { midpoint, pointInDistance } from '@/util/Math'
import OlLineString from 'ol/geom/LineString'
import Point from 'ol/geom/Point'
import { Fill, Stroke, Style, Text } from 'ol/style'
import { createFeature, FeatureType } from './FeatureInfo'

/**
 * Container for all Station features
 *
 * @author Stuecker
 */
export default class PlatformFaature extends LageplanFeature<Station> {
  getFeatures (model: SiteplanState): Feature<Geometry>[] {
    return this.getObjectsModel(model).flatMap(station =>
      station.platforms.flatMap(platform =>
        this.createPlatformFeature(station, platform)))
  }

  protected getObjectsModel (model: SiteplanState): Station[] {
    return model.stations
  }

  private createPlatformFeature (station: Station, platform: Platform): Feature<Geometry>[] {
    // First line, parallel to the tracks
    const points = platform.points.map(point => [point.x, point.y])
    // Second line parallel to the tracks, offset by 0.5
    const points2 = points.map(point => pointInDistance(point, platform.points[0].rotation + 90, 0.5))
    // sides: Vertical to the tracks
    const points3: number[][] = [points[0], pointInDistance(points[0], platform.points[0].rotation + 90, 2)]
    const points4: number[][] = [points[1], pointInDistance(points[1], platform.points[1].rotation + 90, 2)]
    const platformPoints = [
      { line: points, color: getColor(station, `${StationPart.FirstLine}_${platform.guid}`) },
      { line: points2, color: getColor(station, `${StationPart.SecondLine}_${platform.guid}`) },
      { line: points3, color: getColor(station, `${StationPart.ThridLine}_${platform.guid}`) },
      { line: points4, color: getColor(station, `${StationPart.FourLine}_${platform.guid}`) }
    ]

    const features: Feature<Geometry>[] = platformPoints.map(points => {
      const feature = createFeature(
        FeatureType.Platform,
        platform,
        new OlLineString(points.line)
      )
      feature.setStyle((_: unknown, resolution: number) => {
        const baseResolution = this.map.getView().getResolutionForZoom(this.svgService.getBaseZoomLevel())
        const scale = baseResolution / resolution
        const style = new Style({
          stroke: new Stroke({
            color: points.color ?? getColor(station) ?? 'black',
            width: scale * 2.5
          })
        })
        return style
      })
      return feature
    })

    return features.concat([this.createPlatformLabel(station, platform)])
  }

  private createPlatformLabel (station: Station, platform: Platform) {
    const avgRotation = (platform.points[0].rotation + platform.points[1].rotation) / 2 + 90
    const feature = createFeature(
      FeatureType.Platform,
      platform,
      new Point(this.getPlatformLabelPosition(platform))
    )

    feature.setStyle((_, resolution) => {
      const baseResolution = this.map.getView().getResolutionForZoom(this.svgService.getBaseZoomLevel())
      const scale = baseResolution / resolution
      let textRotation = avgRotation
      if (isLabelFlipRequired(textRotation - 90, this.map)) {
        textRotation += 180
      }

      return new Style({
        text: new Text({
          text: 'Gleis ' + platform.label.text,
          scale: scale * 3,
          rotateWithView: true,
          rotation: textRotation * Math.PI / 180,
          textAlign: 'center',
          fill: new Fill({
            color: getLabelColor(station) ?? 'black'
          })
        })
      })
    })
    return feature
  }

  private getPlatformLabelPosition (platform: Platform): number[] {
    const avgRotation = (platform.points[0].rotation + platform.points[1].rotation) / 2 + 90
    return pointInDistance(midpoint(
      pointInDistance([platform.points[0].x, platform.points[0].y], platform.points[0].rotation, 3),
      pointInDistance([platform.points[1].x, platform.points[1].y], platform.points[1].rotation, 3)
    ), avgRotation, 5)
  }

  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  setFeatureColor (feature: Feature<Geometry>): Feature<Geometry> {
    return feature
  }
}
