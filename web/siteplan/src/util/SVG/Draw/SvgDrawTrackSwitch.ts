/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

import { FeatureType } from '@/feature/FeatureInfo'
import { TrackSwitchFeatureData } from '@/feature/TrackSwitchFeature'
import { Label } from '@/model/Label'
import { Coordinate } from '@/model/Position'
import { LeftRight } from '@/model/SiteplanModel'
import { ISvgElement, SvgElement } from '@/model/SvgElement'
import TrackSwitch, { SwitchType, TrackSwitchPart } from '@/model/TrackSwitch'
import TrackSwitchComponent, { TurnoutOperatingMode } from '@/model/TrackSwitchComponent'
import '@/util/ElementExtensions'
import AbstractDrawSVG from '@/util/SVG/Draw/AbstractDrawSVG'
import { GeometryCollection } from 'ol/geom'
import Point from 'ol/geom/Point'
import { Icon, Style } from 'ol/style'
import SvgDraw from './SvgDraw'

/**
 * Draw SVGs for TrackSwitchs
 * @author Stuecker
 */
export default class SvgDrawTrackSwitch extends AbstractDrawSVG {
  // Area to drawing the track switch
  SVG_DRAWAREA_SWITCH = 1000

  // Area to use for drawing the start track switch marker
  SVG_DRAWAREA_START = 40
  SVG_DRAWAREA_START_CENTER = this.SVG_DRAWAREA_START / 2

  // Area to use for drawing the track switch label
  SVG_DRAWAREA_LABEL = 200
  SVG_DRAWAREA_LABEL_CENTER = this.SVG_DRAWAREA_LABEL / 2

  // Point contact label text
  SVG_POINT_CONTACT_LABEL_TEXT = 'Zprk'

  // Label font size
  LABEL_FONT_SIZE = 25

  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  public drawSVG<T extends object> (data: T, label?: Label | undefined): ISvgElement {
    const drawData = data as {featureData: TrackSwitchFeatureData, drawPart: TrackSwitchPart,}
    switch (drawData.drawPart) {
      case TrackSwitchPart.Main:
        return this.drawTrackSwitch(
          drawData.featureData.trackSwitch,
          drawData.featureData.component,
          drawData.featureData.outlineCoor
        )
      case TrackSwitchPart.StartMarker:
        return this.drawStart(drawData.featureData.component ?? undefined)
      default:
        return this.drawLabel(drawData.featureData.component)
    }
  }

  /**
   * Prepares a TrackSwitchComponent for rendering
   * @param switchComponent  the TrackSwitch Component to draw
   * @param outlineCoors outline coordinates of the track switch
   * @returns a {@link ISvgElement} containing the final SVG for rendering
   */
  drawTrackSwitch (
    tswitch: TrackSwitch,
    switchComponent: TrackSwitchComponent | null,
    outlineCoors: number[][]
  ): ISvgElement {
    const svg = SvgDraw.createSvgWithHead(this.SVG_DRAWAREA_SWITCH, this.SVG_DRAWAREA_SWITCH)
    if (!switchComponent) {
      return new SvgElement('Empty', svg, [], null ,[])
    }

    const g = document.createElement('g')
    g.setAttribute('class', TrackSwitchPart.Main)
    g.setAttribute('id', `${TrackSwitchPart.Main}_${switchComponent.guid}`)
    g.setAttribute('stroke', 'black')
    g.setAttribute('stroke-width', '2.5')
    g.setTranslate(this.SVG_DRAWAREA_SWITCH / 2, this.SVG_DRAWAREA_SWITCH / 2)

    let pathLine = `M${0},${0} `

    // Transform coordinates to svg coordinatesystem
    outlineCoors.forEach(coor => {
      const transform = this.transformCoor(coor, outlineCoors[0])
      pathLine += `L${transform[0]},${transform[1]} `
    })

    const outline = document.createElement('path')
    outline.setAttribute('d', pathLine)
    g.appendChild(outline)

    this.drawTrackSwitchStyle(g, tswitch, switchComponent).forEach(ele => (
      svg.appendChild(ele)
    ))

    return new SvgElement('TrackSwitch', svg, [], null, [])
  }

  private transformCoor (coor: number[], originPoint: number[]) : number[] {
    return [
      (coor[0] - originPoint[0]) * SvgDraw.SVG_OFFSET_SCALE_METER_TO_PIXEL_FACTOR,
      (originPoint[1] - coor[1]) * SvgDraw.SVG_OFFSET_SCALE_METER_TO_PIXEL_FACTOR
    ]
  }

  /**
   * Draw inside style for Track Switch
   * @param outlineSVGGroup outline svg
   * @param component track switch component
   * @returns list of HTML Element
   */
  private drawTrackSwitchStyle (
    outlineSVGGroup: HTMLElement,
    tswitch: TrackSwitch,
    component: TrackSwitchComponent
  ): Element[] {
    if (tswitch.switchType === SwitchType.SimpleCross) {
      outlineSVGGroup.setAttribute('fill', `url(%23${SvgDraw.DIAGONAL_LINE_ID})`)
      return [SvgDraw.diagonalPattern(component.start.rotation), outlineSVGGroup]
    }

    const rotate = component.mainLeg.connection === LeftRight.RIGHT
      ? component.start.rotation - 45
      : component.start.rotation + 45

    switch (component.operatingMode) {
      case TurnoutOperatingMode.ElectricRemote:
      case TurnoutOperatingMode.MechanicalRemote:
        return [outlineSVGGroup]
      case TurnoutOperatingMode.ElectricLocal:
        outlineSVGGroup.setAttribute('fill', `url(%23${SvgDraw.CROSS_LINE_ID})`)
        return [
          SvgDraw.crossPattern(
            `rotate(${rotate})`,
            10,
            10,
            1.5
          ), outlineSVGGroup
        ]
      case TurnoutOperatingMode.MechanicalLocal:
      case TurnoutOperatingMode.Trailable:
        outlineSVGGroup.setAttribute('fill', `url(%23${SvgDraw.DIAGONAL_LINE_ID})`)
        return [SvgDraw.diagonalPattern(rotate), outlineSVGGroup]
      case TurnoutOperatingMode.DeadLeft:
      case TurnoutOperatingMode.DeadRight:
      case TurnoutOperatingMode.NonOperational:
      case TurnoutOperatingMode.Other:
      case TurnoutOperatingMode.Undefined:
        outlineSVGGroup.setAttribute('fill', 'transparent')
        return [outlineSVGGroup]
      default:
        console.warn('Cannot display track switch component ' +
            component.label?.text + ' (' + component.guid + '): Invalid operating mode.')
        outlineSVGGroup.setAttribute('fill', 'rgba(0, 0, 255, 0.5')
        return [outlineSVGGroup]
    }
  }

  /**
   * Prepares a TrackSwitch Start for rendering
   *
   * @returns a {@link ISvgElement} containing the final SVG for rendering
   */
  drawStart (switchComponent?: TrackSwitchComponent): ISvgElement {
    if (!switchComponent) {
      return new SvgElement('Empty', document.createElement('svg'), [], null, [])
    }

    const svg = SvgDraw.createSvgWithHead(this.SVG_DRAWAREA_START, this.SVG_DRAWAREA_START)
    const line = document.createElement('path')
    line.setAttribute('class', TrackSwitchPart.StartMarker)
    line.setAttribute('id', `${TrackSwitchPart.StartMarker}_${switchComponent.guid}`)
    line.setAttribute('stroke-width', '4')
    line.setAttribute('d', 'M10,0 L30,0')
    svg.appendChild(line)
    return new SvgElement('TrackSwitch', svg, [], null, [])
  }

  /**
     * Prepares a TrackSwitch Label for rendering
     *
     * @param tswitch the TrackSwitchComponent to draw
     * @returns a {@link ISvgElement} containing the final SVG for rendering
     */
  drawLabel (tswitch: TrackSwitchComponent | null): ISvgElement {
    if (!tswitch || !tswitch.label) {
      return new SvgElement('Empty', document.createElement('svg'), [], null, [])
    }

    let label = tswitch.label
    if (tswitch.pointDetectorCount > 0) {
      label = {
        color: label.color,
        text: label.text + ' ' + this.SVG_POINT_CONTACT_LABEL_TEXT,
        orientationInverted: label.orientationInverted
      }
    }

    const svg = SvgDraw.createSvgWithHead(this.SVG_DRAWAREA_LABEL, this.SVG_DRAWAREA_LABEL)
    svg.appendChild(SvgDraw.drawLabelAt(
      label,
      this.SVG_DRAWAREA_LABEL_CENTER,
      this.SVG_DRAWAREA_LABEL_CENTER,
      false,
      true,
      this.LABEL_FONT_SIZE
    ))

    return new SvgElement('TrackSwitchLabel', svg, [], null, [])
  }

  public static getTrailableArrow (
    arrowCoor: Coordinate[] | undefined,
    color: string | number[],
    scale: number
  ) {
    if (!arrowCoor) {
      return []
    }

    const rotation = Math.atan2(arrowCoor[1].x - arrowCoor[0].x, arrowCoor[1].y - arrowCoor[0].y)
    const arrowSVG = SvgDraw.drawArrow(8, 15, color).content
    arrowSVG.setAttribute('stroke-width', '0.25')
    const arrowStyle = new Style({
      geometry: new Point([(arrowCoor[1].x + arrowCoor[0].x) / 2, (arrowCoor[1].y + arrowCoor[0].y) / 2]),
      image: new Icon({
        src: 'data:image/svg+xml;utf8,' + arrowSVG.outerHTML,
        rotation,
        rotateWithView: true,
        scale
      })

    })
    return [arrowStyle]
  }

  public static getTrackSwitchLockCross (crossPos: Coordinate[], scale: number) {
    const geometries = new GeometryCollection(crossPos
      .map(coor => new Point([coor.x, coor.y])))
    const crossStyle = new Style({
      geometry: geometries,
      image: new Icon({
        src: 'data:image/svg+xml;utf8,' + SvgDraw.drawCross(7.5, 7.5).content.outerHTML,
        rotation: Math.PI / 2,
        rotateWithView: true,
        scale
      })
    })
    return [crossStyle]
  }

  public getFeatureType (): FeatureType {
    return FeatureType.TrackSwitch
  }
}

