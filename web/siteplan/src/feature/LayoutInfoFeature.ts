/**
 * Copyright (c) 2023 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

import { LayoutInfo, SheetCut } from '@/model/LayoutInfo'
import { SiteplanState } from '@/model/SiteplanModel'
import SvgDraw from '@/util/SVG/Draw/SvgDraw'
import { Feature } from 'ol'
import { Geometry, Polygon } from 'ol/geom'
import LineString from 'ol/geom/LineString'
import Point from 'ol/geom/Point'
import { Icon, Stroke, Style } from 'ol/style'
import { FeatureType, createFeature } from './FeatureInfo'
import LageplanFeature from './LageplanFeature'

export interface SheetCutFeatureData {
  label: string
  guid: string
  sheetIndex: string
  polygon: Polygon
  directionLine : LineString
}
/**
 * Contains all feature of layout sheet cut
 */
export default class LayoutInfoFeature extends LageplanFeature<LayoutInfo> {
  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  protected getObjectsModel (_model: SiteplanState): LayoutInfo[] {
    throw new Error('Method not implemented.')
  }

  public getLayoutFeatures (layoutInfos: LayoutInfo[]): Feature<Geometry>[] {
    const result: Feature<Geometry>[] = layoutInfos.flatMap(layout =>
      layout.sheetsCut.flatMap(sheetCut =>
        this.createSheetCutFeatures(layout.label, sheetCut)))
    return result
  }

  private createSheetCutFeatures (label: string, sheetCut: SheetCut) : Feature<Geometry>{
    const directionLine = new LineString(
      sheetCut.polygonDirection
        .map(coor => [coor.x ,coor.y])
    )
    const sheetCutPolygon = new Polygon([
      sheetCut.polygon
        .map(coor => [coor.x, coor.y])
    ])
    const featureData: SheetCutFeatureData = {
      label,
      guid: sheetCut.guid,
      sheetIndex: sheetCut.label,
      directionLine,
      polygon: sheetCutPolygon

    }
    const feature = createFeature(
      FeatureType.SheetCut,
      featureData,
      sheetCutPolygon,
      label
    )

    feature.setStyle((_, resolution) => {
      const baseResolution = this.map.getView().getResolutionForZoom(this.svgService.getBaseZoomLevel())
      const scale = baseResolution / resolution
      const featureStyle =  new Style ({
        stroke: new Stroke({
          width: 1.5 * scale,
          color: 'black'
        })
      })
      return [
        this.drawDirectionLine(directionLine,scale),
        this.drawArrow(directionLine, scale),
        featureStyle
      ]
    })
    return feature
  }

  private drawDirectionLine (directionLine: LineString, scale: number):Style {
    return new Style({
      geometry: directionLine,
      stroke: new Stroke({
        width: 1.5 * scale,
        color: 'black'
      })
    })
  }

  private drawArrow (directionLine: LineString, scale: number) :Style {
    const pointStart = directionLine.getCoordinates()[0]
    const pointEnd = directionLine.getCoordinates().at(directionLine.getCoordinates().length - 1)
    if (!pointStart || !pointEnd) {
      return new Style()
    }

    const arrowSVG = SvgDraw.drawArrow(8, 15, 'black').content
    arrowSVG.setAttribute('stroke-width', '2')
    const arrowRotation = Math.atan2(
      pointEnd[0] - pointStart[0],
      pointEnd[1] - pointStart[1]
    )
    return new Style({
      geometry: new Point(pointEnd),
      image: new Icon({
        src: 'data:image/svg+xml;utf8,' + arrowSVG.outerHTML,
        scale,
        rotateWithView: true,
        anchor: [0.5, 0],
        rotation: arrowRotation
      })
    })
  }
}
