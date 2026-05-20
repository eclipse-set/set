/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

import { FeatureType } from '@/feature/FeatureInfo'
import { Label } from '@/model/Label'
import { PZB, PZBEffectivity } from '@/model/PZB'
import { ISvgElement, SvgElement } from '@/model/SvgElement'
import '@/util/ElementExtensions'
import AbstractDrawSVG from '@/util/SVG/Draw/AbstractDrawSVG'
import SvgDraw from './SvgDraw'

/**
 * Draw SVGs for PZBs
 * @author Stuecker
 */
export default class SvgDrawPZB extends AbstractDrawSVG {
  // Area to use for drawing pzb objects
  SVG_DRAWAREA = 200
  SVG_DRAWAREA_CENTER = this.SVG_DRAWAREA / 2

  // Offsets for placing the label along PZB elements
  SVG_LABEL_OFFSET_X = 24
  SVG_LABEL_OFFSET_Y = 4

  // Offsets for placing the "F" label along PZB route elements
  SVG_ROUTE_LABEL_OFFSET_X = 12
  SVG_ROUTE_LABEL_OFFSET_Y = -21
  SVG_ROUTE_LABEL_TEXT = 'F'

  // Label font size
  LABEL_FONT_SIZE = 14

  public drawSVG<T extends object> (data: T, label?: Label | undefined): ISvgElement | null {
    const pzb = data as PZB
    const svg = this.getSvgFromCatalog(data)
    if (svg === null) {
      console.warn('Unsupported PZB ' + pzb.type.toString())
      return SvgDraw.getErrorSVG()
    }

    return this.draw(svg, pzb.rightSide, label, pzb.effectivity)
  }

  /**
     * Prepares a PZB for rendering
     *
     * @param pzb The PZB SVG
     * @param rightSide whether to render the to the right
     *                  side of the track or the left
     * @param label the label for the PZB (or null if none)
     * @param effectivity the pzb effectivity
     * @returns a {@link ISvgElement} containing the final SVG for rendering
     */
  draw (pzb: ISvgElement, rightSide: boolean, label: Label | undefined, effectivity: PZBEffectivity): ISvgElement {
    const svg = SvgDraw.createSvgWithHead(this.SVG_DRAWAREA, this.SVG_DRAWAREA)
    const pzbsvg = pzb.content.cloneNode(true) as Element
    pzbsvg.setTranslate(this.SVG_DRAWAREA_CENTER, this.SVG_DRAWAREA_CENTER)
    if (label) {
      svg.appendChild(
        SvgDraw.drawLabelAt(
          label,
          this.SVG_DRAWAREA_CENTER + this.SVG_LABEL_OFFSET_X,
          this.SVG_DRAWAREA_CENTER + this.SVG_LABEL_OFFSET_Y,
          false,
          true,
          this.LABEL_FONT_SIZE
        )
      )

      if (effectivity === PZBEffectivity.Route) {
        const routeLabel = {
          text: this.SVG_ROUTE_LABEL_TEXT,
          orientationInverted: label.orientationInverted,
          color: [0, 0, 0]
        }
        svg.appendChild(SvgDraw.drawLabelAt(
          routeLabel,
          this.SVG_DRAWAREA_CENTER + this.SVG_ROUTE_LABEL_OFFSET_X,
          this.SVG_DRAWAREA_CENTER + this.SVG_ROUTE_LABEL_OFFSET_Y,
          false,
          false,
          this.LABEL_FONT_SIZE
        ))
      }
    }

    svg.appendChild(pzbsvg)
    return new SvgElement('PZB', svg, [], null, pzb.boundingBox)
  }

  public getFeatureType (): FeatureType {
    return FeatureType.PZB
  }
}
