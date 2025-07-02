/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

import { Label } from '@/model/Label'
import { KMMarker } from '@/model/Route'
import { SignalPart } from '@/model/Signal'
import { ISvgElement, SvgElement } from '@/model/SvgElement'
import '@/util/ElementExtensions'
import { fromCenterPointAndMasure } from '@/util/ExtentExtension'

/**
  * Draw SVG components
  * @author Truong
  */
export default class SvgDraw {
  // Scaling factor for transforming a distance in meters into svg sizes.
  // Signals are drawn with 10mm units in a map scaled to 1:1000
  // As a result, 1m in the plan equals 10 svg units for drawing
  // For an unknown additional reason we additionally need to
  // scale to 2/3rds. See PLANPRO-4712 for details
  static SVG_OFFSET_SCALE_METER_TO_PIXEL_FACTOR = 10 * (2.01 / 3)

  static SVG_OFFSET_SCALE_PIXEL_TO_METER_FACTOR = 1 / this.SVG_OFFSET_SCALE_METER_TO_PIXEL_FACTOR

  static POINT_TO_PIXEL_FACTOR = 1.3

  // Offset for the inverted (or flipped) label to correct for text alignment
  static SVG_INVERTED_LABEL_OFFSET_Y = 9

  // Area to use for drawing km markers objects
  static SVG_KMMARKER_DRAWAREA_X = 300
  static SVG_KMMARKER_DRAWAREA_Y = 100
  static SVG_KMMARKER_DRAWAREA_CENTER_X = SvgDraw.SVG_KMMARKER_DRAWAREA_X / 2
  static SVG_KMMARKER_DRAWAREA_CENTER_Y = SvgDraw.SVG_KMMARKER_DRAWAREA_Y / 2

  // Area to use for drawing track section markers objects
  static SVG_TRACK_SECTION_MARKER_DRAWAREA_X = 50
  static SVG_TRACK_SECTION_MARKER_DRAWAREA_Y = 50
  static SVG_TRACK_SECTION_MARKER_DRAWAREA_CENTER_X = SvgDraw.SVG_TRACK_SECTION_MARKER_DRAWAREA_X / 2
  static SVG_TRACK_SECTION_MARKER_DRAWAREA_CENTER_Y = SvgDraw.SVG_TRACK_SECTION_MARKER_DRAWAREA_Y / 2

  // Offsets for placing the label along objects
  static SVG_KMMARKER_LABEL_OFFSET_X = SvgDraw.SVG_KMMARKER_DRAWAREA_CENTER_X - 100
  static SVG_KMMARKER_LABEL_OFFSET_Y = SvgDraw.SVG_KMMARKER_DRAWAREA_CENTER_Y

  static KMMARKER_LABEL_FONT_SIZE = 30

  static  DIAGONAL_LINE_ID = 'diagonal'
  static CROSS_LINE_ID = 'cross'

  public static createSvgWithHead (width: number, height: number): HTMLElement {
    const svg = document.createElement('svg')
    svg.setAttribute('width', width.toString())
    svg.setAttribute('height', height.toString())
    svg.setAttribute('xmlns', 'http://www.w3.org/2000/svg')
    svg.setAttribute('stroke-width', '2.5')
    svg.setAttribute('stroke', 'black')
    return svg
  }

  public static createRecolorFilter (): Element {
    // Add a SVG filter which allows recoloring
    const ns = 'http://www.w3.org/2000/svg'
    const filter = document.createElementNS(ns, 'filter')
    filter.setAttribute('id', 'recolor')
    filter.setAttribute('color-interpolation-filters', 'sRGB')
    const colorMatrix = document.createElementNS(ns, 'feColorMatrix')
    colorMatrix.setAttribute('in', 'SourceGraphic')
    colorMatrix.setAttribute('type', 'matrix')
    filter.appendChild(colorMatrix)
    return filter
  }

  public static drawLabelAt (
    label: Label | null,
    labelX: number,
    labelY: number,
    isTopMounted: boolean,
    centerText: boolean,
    fontSize: number,
    rotate?: number,
    centerVertical?: boolean
  ): Element {
    const labelsvg = document.createElement('text')
    if (!label) {
      return labelsvg
    }

    labelsvg.setAttribute('id', 'label')
    labelsvg.setAttribute('font-size', fontSize.toString())
    labelsvg.setAttribute('font-family', 'siteplanfont')
    labelsvg.setAttribute('stroke-width', '0.2')
    labelsvg.setAttribute('style', 'writing-mode: vertical-rl;')
    const textAnchor = this.getTextAnchor(centerText, label)
    labelsvg.setAttribute('text-anchor', textAnchor)
    let labelRotation = rotate ?? 0
    // Rotate the text if required and set up the anchor point
    if (label.orientationInverted) {
      labelRotation += 180
      if (!centerVertical) {
        if (isTopMounted) {
          labelY -= SvgDraw.SVG_INVERTED_LABEL_OFFSET_Y
        } else {
          labelY += SvgDraw.SVG_INVERTED_LABEL_OFFSET_Y
        }
      }
    }

    if (centerVertical) {
      labelsvg.setAttribute('dominant-baseline', 'middle')
    }

    labelsvg.setAttribute('transform', `rotate(${labelRotation}, ${labelX}, ${labelY})`)
    labelsvg.setPosition(labelX, labelY)
    labelsvg.append(label.text)
    return labelsvg
  }

  private static getTextAnchor (centerText: boolean, label: Label): string {
    if (centerText) {
      return 'middle'
    }

    return label.orientationInverted ? 'end' : 'start'
  }

  /**
    * Returns an SVG that can be used to signify an unsupported or
    * incorrect signal specification
    */
  public static getErrorSVG (): ISvgElement {
    const svg = document.createElement('svg')
    svg.setAttribute('width', '100')
    svg.setAttribute('height', '100')
    svg.setAttribute('xmlns', 'http://www.w3.org/2000/svg')
    const circle = document.createElement('circle')
    circle.setAttribute('r', '20')
    circle.setAttribute('cx', '50')
    circle.setAttribute('cy', '50')
    circle.setAttribute('style', 'fill:red')
    const circle2 = document.createElement('circle')
    circle2.setAttribute('r', '15')
    circle2.setAttribute('cx', '50')
    circle2.setAttribute('cy', '50')
    circle2.setAttribute('style', 'fill:blue')
    const circle3 = document.createElement('circle')
    circle3.setAttribute('r', '10')
    circle3.setAttribute('cx', '50')
    circle3.setAttribute('cy', '50')
    circle3.setAttribute('style', 'fill:red')
    svg.appendChild(circle)
    svg.appendChild(circle2)
    svg.appendChild(circle3)

    const boundBox = fromCenterPointAndMasure([50, 50], 40, 40)
    return new SvgElement('Error', svg, [], null, [boundBox])
  }

  /**
    * Returns a {@link ISvgElement} for a KMMarker
    * @param marker the KMMarker
    * @param routeLabel the label for the KMMarker
    */
  public static getKMMarker (marker: KMMarker, routeLabel: Label): ISvgElement {
    const svg = SvgDraw.createSvgWithHead(this.SVG_KMMARKER_DRAWAREA_X, this.SVG_KMMARKER_DRAWAREA_Y)
    const circle = document.createElement('circle')
    circle.setAttribute('r', '10')
    circle.setAttribute('cx', this.SVG_KMMARKER_DRAWAREA_CENTER_X.toString())
    circle.setAttribute('cy', this.SVG_KMMARKER_DRAWAREA_CENTER_Y.toString())
    circle.setAttribute('style', 'stroke:black; stroke-width: 3; fill: none')
    svg.appendChild(circle)
    // Add a second circle if this is a full kilometer
    if (marker.value % 1000 === 0) {
      const circle = document.createElement('circle')
      circle.setAttribute('r', '15')
      circle.setAttribute('cx', this.SVG_KMMARKER_DRAWAREA_CENTER_X.toString())
      circle.setAttribute('cy', this.SVG_KMMARKER_DRAWAREA_CENTER_Y.toString())
      circle.setAttribute('style', 'stroke:black; stroke-width: 3; fill: none')
      svg.appendChild(circle)
    }

    // Add the label
    svg.appendChild(SvgDraw.drawLabelAt(
      routeLabel,
      this.SVG_KMMARKER_LABEL_OFFSET_X,
      this.SVG_KMMARKER_LABEL_OFFSET_Y,
      false,
      true,
      this.KMMARKER_LABEL_FONT_SIZE
    ))
    return new SvgElement('KMMarker', svg, [], null, [])
  }

  static getTrackSectionMarker (): ISvgElement {
    const svg = SvgDraw.createSvgWithHead(
      this.SVG_TRACK_SECTION_MARKER_DRAWAREA_X,
      this.SVG_TRACK_SECTION_MARKER_DRAWAREA_Y
    )
    const circle = document.createElement('circle')
    circle.setAttribute('r', '5')
    circle.setAttribute('cx', this.SVG_TRACK_SECTION_MARKER_DRAWAREA_CENTER_X.toString())
    circle.setAttribute('cy', this.SVG_TRACK_SECTION_MARKER_DRAWAREA_CENTER_Y.toString())
    circle.setAttribute('style', 'stroke:yellow; stroke-width: 1; fill: yellow')
    svg.appendChild(circle)
    return new SvgElement('TrackSectionMarker', svg, [], null, [])
  }

  static drawCross (width: number, height: number): ISvgElement {
    const svg = SvgDraw.createSvgWithHead(width, height)
    svg.setAttribute('stroke-width', '1')
    const lineA = this.drawLine([0, 0], [width, height])
    const lineB = this.drawLine([width, 0], [0, height])
    svg.appendChild(lineA)
    svg.appendChild(lineB)
    return new SvgElement('Cross', svg, [], null, [])
  }

  static drawLine (startPoint: number[], destPoint: number[]): HTMLElement {
    const line = document.createElement('line')
    line.setAttribute('x1', `${startPoint[0]}`)
    line.setAttribute('y1', `${startPoint[1]}`)
    line.setAttribute('x2', `${destPoint[0]}`)
    line.setAttribute('y2', `${destPoint[1]}`)
    return line
  }

  static drawArrow (width: number, height: number, color: string | number[]): ISvgElement {
    const svg = SvgDraw.createSvgWithHead(width, height)
    const strokeWidth = 0.1
    const arrow = document.createElement('path')
    arrow.setAttribute('stroke-width', `${strokeWidth}`)
    arrow.setAttribute('d', `M${width / 2},0 ${width - strokeWidth},${height} ${strokeWidth},${height} z`)
    if (typeof (color) !== 'string') {
      color = `rgb(${color[0]}, ${color[1]}, ${color[2]})`
    }

    arrow.setAttribute('fill', `${color}`)
    svg.appendChild(arrow)
    return new SvgElement('Arrow', svg, [], null, [])
  }

  static getRouteMarker (label: Label): ISvgElement {
    const svg = SvgDraw.createSvgWithHead(this.SVG_KMMARKER_DRAWAREA_X, this.SVG_KMMARKER_DRAWAREA_Y)
    // Add the label
    const labelSvg = SvgDraw.drawLabelAt(
      label,
      this.SVG_KMMARKER_DRAWAREA_CENTER_X - 60,
      this.SVG_KMMARKER_DRAWAREA_CENTER_Y + 8,
      false,
      true,
      13,
      90,
      true
    )
    labelSvg.setAttribute('id', SignalPart.RouteMarker)
    svg.appendChild(labelSvg)
    return new SvgElement('RouteMarker', svg, [], null, [])
  }

  static getTrackMarker (label: Label): ISvgElement {
    const fontSize = 30
    const svg = SvgDraw.createSvgWithHead(
      fontSize * this.POINT_TO_PIXEL_FACTOR,
      label.text.length * fontSize * this.POINT_TO_PIXEL_FACTOR
    )
    // Add a SVG filter which adds a white background
    const ns = 'http://www.w3.org/2000/svg'
    const defs = document.createElementNS(ns, 'defs')
    const filter = document.createElementNS(ns, 'filter')
    filter.setAttribute('x', '0')
    filter.setAttribute('y', '-0.15')
    filter.setAttribute('width', '1')
    filter.setAttribute('height', '1.3')
    filter.setAttribute('id', 'background')
    filter.setAttribute('color-interpolation-filters', 'sRGB')

    // White flood
    const flood = document.createElementNS(ns, 'feFlood')
    flood.setAttribute('flood-color', 'white')
    flood.setAttribute('flood-opacity', '0')
    flood.setAttribute('result', 'bg')

    // Merge source and background
    const composite = document.createElementNS(ns, 'feComposite')
    composite.setAttribute('in', 'SourceGraphic')

    filter.appendChild(flood)
    filter.appendChild(composite)
    defs.appendChild(filter)
    svg.appendChild(defs)

    // Add label
    const labelSVG = SvgDraw.drawLabelAt(
      label,
      fontSize * this.POINT_TO_PIXEL_FACTOR / 2,
      label.text.length * fontSize * this.POINT_TO_PIXEL_FACTOR / 2,
      false,
      true,
      fontSize,
      0,
      true
    )
    labelSVG.setAttribute('filter', 'url(%23background)')

    svg.appendChild(labelSVG)
    return new SvgElement('RouteMarker', svg, [], null, [])
  }

  static crossPattern (rotate: string, width: number, height: number, strokeWidth: number): Element {
    const ns = 'http://www.w3.org/2000/svg'
    const pattern = document.createElementNS(ns, 'pattern')
    pattern.setAttribute('id', this.CROSS_LINE_ID)
    pattern.setAttribute('patternUnits', 'userSpaceOnUse')
    pattern.setAttribute('patternTransform', rotate)
    pattern.setAttribute('width', `${width}`)
    pattern.setAttribute('height', `${height}`)

    const rect = document.createElement('rect')
    rect.setAttribute('width', `${width}`)
    rect.setAttribute('height', `${height}`)
    rect.setAttribute('fill', 'white')
    rect.setAttribute('stroke', 'black')
    rect.setAttribute('stroke-width', `${strokeWidth}`)
    pattern.appendChild(rect)

    return pattern
  }

  static diagonalPattern (rotate: number): Element {
    const ns = 'http://www.w3.org/2000/svg'
    const pattern = document.createElementNS(ns, 'pattern')
    pattern.setAttribute('id', this.DIAGONAL_LINE_ID)
    pattern.setAttribute('patternUnits', 'userSpaceOnUse')
    pattern.setAttribute('patternTransform', `rotate(${rotate})`)
    pattern.setAttribute('width', '0.5')
    pattern.setAttribute('height', '7')

    const line = document.createElement('path')
    line.setAttribute('d', 'M-1,0 L6,0')
    line.setAttribute('stroke-linecap', 'round')
    line.setAttribute('stroke', 'black')
    line.setAttribute('stroke-width', '1.3')
    pattern.appendChild(line)

    return pattern
  }
}
