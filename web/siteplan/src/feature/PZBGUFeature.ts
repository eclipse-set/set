/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

import { PZBType } from '@/model/PZB'
import { PZBGU } from '@/model/PZBGU'
import { getColor } from '@/model/SiteplanObject'
import { angle, midpoint, pointInDistance, toDeg } from '@/util/Math'
import { isLabelFlipRequired } from '@/util/ModelExtensions'
import { PolygonBuilder } from '@/util/PolygonBuilder'
import { Feature } from 'ol'
import { Coordinate } from 'ol/coordinate'
import Geometry from 'ol/geom/Geometry'
import LineString from 'ol/geom/LineString'
import Point from 'ol/geom/Point'
import Polygon from 'ol/geom/Polygon'
import { Fill, Stroke, Style } from 'ol/style'
import Text from 'ol/style/Text'
import { createFeature, FeatureType } from './FeatureInfo'
import PZBFeature from './PZBFeature'

/**
 * Container for all track switch features
 *
 * @author Stuecker
 */
export default class PZBGUFeature extends PZBFeature {
  createPZBGUFeature (pzbGU: PZBGU): Feature<Geometry>[] {
    const pzbFeatures = pzbGU.pzbs.map(pzb => {
      return this.createPZBComponentFeature(pzb)
    })

    const gm = pzbGU.pzbs.find(pzb => pzb.type === PZBType.GM)
    const gse = pzbGU.pzbs.find(pzb => pzb.type === PZBType.GUE_GSE)
    if (!gm || !gse) {
      return []
    }

    // Draw GU 'frame'
    const PZB_GU_DISTANCE = 2.375
    const PZB_GU_HEIGHT = 25
    const PZB_GU_ARROW_HEIGHT = 20
    const PZB_GU_TEXT_OFFSET_Y = 16
    const PZB_GU_START_HEIGHT = 5
    const PZB_GU_LINE_SCALE = 1.5

    // Base positions for the PZB magnets and their vertical direction
    const gmPosition = [gm.position.x, gm.position.y]
    const gmRotation = gm.position.rotation - 90
    const gsePosition = [gse.position.x, gse.position.y]
    const gseRotation = gse.position.rotation - 90

    const lineFeature = (coordinates: Coordinate[]) =>
      createFeature(
        FeatureType.PZBGU,
        pzbGU,
        new LineString(coordinates)
      )

    const lineFeatures = [
      lineFeature([
        pointInDistance(gmPosition, gmRotation, PZB_GU_START_HEIGHT),
        pointInDistance(gmPosition, gmRotation, PZB_GU_HEIGHT)
      ]),
      lineFeature([
        pointInDistance(gsePosition, gseRotation, PZB_GU_START_HEIGHT),
        pointInDistance(gsePosition, gseRotation, PZB_GU_HEIGHT)
      ])
    ]

    // Draw the center line including the arrows
    lineFeatures.forEach(feature => feature.setStyle((_, resolution) => {
      const baseResolution = this.map.getView().getResolutionForZoom(this.svgService.getBaseZoomLevel())
      const scale = baseResolution / resolution
      return new Style({
        stroke: new Stroke({
          width: PZB_GU_LINE_SCALE * scale,
          color: getColor(pzbGU) ?? 'black'
        })
      })
    }))

    // Calculate the orientation angle between the two points,
    // which depends on the orientation on the track
    const baseRotation = toDeg(angle(gmPosition, gsePosition)) + (gmRotation + gseRotation) / 2 + 180
    // Arrow feature
    const arrowAnchorGM = pointInDistance(
      pointInDistance(gmPosition, gmRotation, PZB_GU_ARROW_HEIGHT),
      gmRotation - 90 + baseRotation,
      PZB_GU_DISTANCE
    )
    const arrowAnchorGSE = pointInDistance(
      pointInDistance(gsePosition, gseRotation, PZB_GU_ARROW_HEIGHT),
      gseRotation + 90 + baseRotation,
      PZB_GU_DISTANCE
    )
    const avgRotation = (gmRotation + gseRotation) / 2
    const textAnchor = pointInDistance(midpoint(
      pointInDistance(gmPosition, gmRotation, PZB_GU_START_HEIGHT),
      pointInDistance(gsePosition, gseRotation, PZB_GU_START_HEIGHT)
    ), avgRotation, PZB_GU_TEXT_OFFSET_Y)

    const arrowFeature = this.pzbGUArrowFeature(
      pzbGU,
      arrowAnchorGM,
      gmRotation,
      arrowAnchorGSE,
      gseRotation,
      textAnchor,
      baseRotation
    )
    return pzbFeatures.concat(lineFeatures, [arrowFeature])
  }

  private pzbGUArrowFeature (
    pzbGU: PZBGU,
    arrowAnchorGM: number[],
    gmRotation: number,
    arrowAnchorGSE: number[],
    gseRotation: number,
    textAnchor: number[],
    baseRotation: number
  ) {
    const PZB_GU_TEXT_SCALE = 1.25
    // Width of the arrow triangle at its base
    const ARROW_BASE_WIDTH = 0.5
    // Width of the arrow line
    const ARROW_WIDTH = 0.5
    // Angle of the arrow sides
    const ARROW_ANGLE = 80
    // Distance from the arrow base to the arrow point
    const ARROW_POINT_LENGTH = 2.3
    const coordinates =
      new PolygonBuilder(arrowAnchorGM[0], arrowAnchorGM[1], gmRotation + baseRotation)
        // Left arrow
        .moveDistance(ARROW_WIDTH / 2, 0)
        .moveDistance(ARROW_BASE_WIDTH / 2, 0)
        .moveDistance(-ARROW_POINT_LENGTH, -ARROW_ANGLE)
        .moveDistance(-ARROW_POINT_LENGTH, ARROW_ANGLE)
        .moveDistance(ARROW_BASE_WIDTH / 2, 0)
        // Right arrow
        .moveToVirtual(arrowAnchorGSE[0], arrowAnchorGSE[1])
        .moveDistance(ARROW_WIDTH / 2, 180)
        .moveDistance(ARROW_BASE_WIDTH / 2, 180)
        .moveDistance(ARROW_POINT_LENGTH, -ARROW_ANGLE)
        .moveDistance(ARROW_POINT_LENGTH, ARROW_ANGLE)
        .moveDistance(ARROW_BASE_WIDTH / 2, 180)
        .getCoordinates()

    const feature = createFeature(
      FeatureType.PZBGU,
      pzbGU,
      new Polygon([coordinates])
    )

    // Fill the polygon black
    feature.setStyle((_, resolution) => {
      const baseResolution = this.map.getView().getResolutionForZoom(this.svgService.getBaseZoomLevel())
      const scale = baseResolution / resolution
      let textRotation = (gseRotation + gmRotation) / 2
      if (isLabelFlipRequired(textRotation - 90, this.map)) {
        textRotation += 180
      }

      return [
        new Style({
          stroke: new Stroke({
            width: 0
          }),
          fill: new Fill({
            color: getColor(pzbGU) ?? 'black'
          })
        }),
        // Text style
        new Style({
          geometry: new Point(textAnchor),
          text: new Text({
            text: 'GPE ' + pzbGU.length.toString() + ' m',
            scale: scale * PZB_GU_TEXT_SCALE,
            rotateWithView: true,
            rotation: textRotation * Math.PI / 180,
            textAlign: 'center',
            fill: new Fill({
              color: getColor(pzbGU) ?? 'black'
            })
          })
        })
      ]
    })
    return feature
  }
}
