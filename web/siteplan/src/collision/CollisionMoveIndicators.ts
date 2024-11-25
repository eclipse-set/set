/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

import { getFeatureData } from '@/feature/FeatureInfo'
import { ModelType } from '@/feature/FeatureMetadata'
import { Feature } from 'ol'
import Geometry from 'ol/geom/Geometry'
import LineString from 'ol/geom/LineString'
import Point from 'ol/geom/Point'
import { Fill, Stroke, Style, Text } from 'ol/style'
import OlStyle from 'ol/style/Style'

export default class CollisionMoveIndicators {
  public createStyle (
    feature: Feature<Geometry>,
    linePoints: (number[] | null)[],
    originalStyle: OlStyle | OlStyle[] | void,
    scale: number
  ): void | OlStyle[] {
    if (originalStyle instanceof OlStyle) {
      originalStyle = [originalStyle]
    }

    if (originalStyle instanceof Array) {
      originalStyle = originalStyle.concat(
        this.createMovelineStyle(
          linePoints[0],
          linePoints[1],
          scale,
          getFeatureData(feature as Feature<Geometry>)
        )
      )
    }

    return originalStyle
  }

  public createMovelineStyle (
    oldCenter: number[] | null,
    newCenter: number[] | null,
    scale: number,
    feature: ModelType
  ): OlStyle[] {
    if (!oldCenter || !newCenter) {
      return []
    }

    // Move line
    const moveLinesStyle = new Style({
      geometry: new LineString([oldCenter, newCenter]),
      stroke: new Stroke({
        width: 0.25 * scale,
        color: feature.color ?? 'black'
      })
    })

    // Circle at original location
    const oldCenterStyle = new Style({
      geometry: new Point(oldCenter),
      text: new Text({
        text: '⬤',
        textAlign: 'center',
        stroke: new Stroke({ color: feature.color ?? 'black', width: 1 }),
        fill: new Fill({ color: feature.color ?? 'black' }),
        font: 'normal 4px/1 siteplanfont',
        scale
      })
    })
    return [moveLinesStyle, oldCenterStyle]
  }
}
