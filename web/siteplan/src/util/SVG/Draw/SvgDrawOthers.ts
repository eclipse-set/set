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
import { getHeight } from 'ol/extent'
import AbstractDrawSVG from './AbstractDrawSVG'
import SvgDraw from './SvgDraw'

export default class SvgDrawOthers extends AbstractDrawSVG{
  SVG_DRAWAREA = 500
  LABEL_FONT_SIZE = 14
  public getFeatureType (): FeatureType {
    return FeatureType.Unknown
  }

  public drawSVG<T extends object> (data: T, label?: Label | undefined): ISvgElement | null {
    const svgImage = this.getSvgFromCatalog(data)
    if (svgImage === null) {
      console.warn('Unsupported object')
      return SvgDraw.getErrorSVG()
    }

    const svg = SvgDraw.createSvgWithHead(this.SVG_DRAWAREA, this.SVG_DRAWAREA)
    const clone = svgImage.content.cloneNode(true) as Element

    const translate = [
      (this.SVG_DRAWAREA - 20) / 2,
      (this.SVG_DRAWAREA - 50) / 2
    ]
    clone.setAttribute('transform', `translate(${translate[0]},${translate[1]})`)
    svg.appendChild(clone)

    if (label) {
      svg.appendChild(SvgDraw.drawLabelAt(
        label,
        this.SVG_DRAWAREA / 2 - getHeight(svgImage.boundingBox[0]) - 2,
        this.SVG_DRAWAREA / 2,
        false,
        true,
        this.LABEL_FONT_SIZE
      ))
    }

    return new SvgElement('Other', svg, [], null, svgImage.boundingBox)
  }
}
