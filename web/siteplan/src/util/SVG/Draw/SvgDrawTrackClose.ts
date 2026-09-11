/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

import { FeatureType } from '@/feature/FeatureInfo'
import { Label } from '@/model/Label'
import { ISvgElement, SvgElement } from '@/model/SvgElement'
import TrackClose, { TrackCloseType } from '@/model/TrackClose'
import AbstractDrawSVG from '@/util/SVG/Draw/AbstractDrawSVG'
import SvgDraw from './SvgDraw'

/**
 * Draw Svg for track close
 * @author Truong
 */
export default class SvgDrawTrackClose extends AbstractDrawSVG {
  SVG_DRAWAREA = 500
  SVG_DRAWAREA_CENTER = this.SVG_DRAWAREA / 2

  SVG_LABEL_OFFSET_X = 20
  LABEL_FONT_SIZE = 14

  public drawSVG<T extends object> (data: T, label?: Label | undefined): ISvgElement | null {
    const trackclose = data as TrackClose
    const svg = this.getSvgFromCatalog(data)
    if (svg === null) {
      console.warn('Unsupported TrackClose: ' + trackclose.trackCloseType.toString())
      return SvgDraw.getErrorSVG()
    }

    return this.drawTrackClose(svg, label)
  }

  drawTrackClose (trackCloseSvgElement: ISvgElement, label?: Label) {
    const svg = SvgDraw.createSvgWithHead(this.SVG_DRAWAREA, this.SVG_DRAWAREA)
    const trackcloseSvg = trackCloseSvgElement.content.cloneNode(true) as Element
    trackcloseSvg.setTranslate(this.SVG_DRAWAREA_CENTER, this.SVG_DRAWAREA_CENTER)
    if (label) {
      const offsetX = label.text === TrackCloseType.InfrastructureBorder
        ? this.SVG_LABEL_OFFSET_X + 35
        : this.SVG_LABEL_OFFSET_X
      this.drawLabel(svg, label, offsetX)
    }

    svg.appendChild(trackcloseSvg)
    return new SvgElement('TrackClose', svg, [], null, trackCloseSvgElement.boundingBox)
  }

  drawLabel (svg: Element, label: Label, offsetX : number) {
    const labelsvg = SvgDraw.drawLabelAt(
      label,
      this.SVG_DRAWAREA_CENTER + offsetX,
      this.SVG_DRAWAREA_CENTER,
      false,
      true,
      this.LABEL_FONT_SIZE
    )

    if (offsetX > this.SVG_LABEL_OFFSET_X) {
      labelsvg.setAttribute('text-decoration', 'underline')
    }

    svg.appendChild(labelsvg)
  }

  public getFeatureType (): FeatureType {
    return FeatureType.TrackClose
  }
}
