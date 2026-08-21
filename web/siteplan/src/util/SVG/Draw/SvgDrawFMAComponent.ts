/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

import { FeatureType } from '@/feature/FeatureInfo'
import { FMAComponent } from '@/model/FMAComponent'
import { Label } from '@/model/Label'
import { ISvgElement, ISvgPoint, SvgElement } from '@/model/SvgElement'
import '@/util/ElementExtensions'
import AbstractDrawSVG from '@/util/SVG/Draw/AbstractDrawSVG'
import SvgDraw from './SvgDraw'

/**
 * Draw SVGs for FMA Components
 * @author Stuecker
 */
export default class SvgDrawFMAComponent extends AbstractDrawSVG {
  // Area to use for drawing fma objects
  SVG_FMA_DRAWAREA = 200
  SVG_FMA_DRAWAREA_CENTER = this.SVG_FMA_DRAWAREA / 2

  // Offsets for placing the label along objects
  SVG_FMA_LABEL_OFFSET_X = 0
  SVG_FMA_LABEL_OFFSET_Y = 25

  // Parameter for extra height for long labels.
  // This value multiplied by the length of the label is the minimum draw area
  DRAWAREA_LABEL_PER_CHARACTER_HEIGHT = 23

  // Label font size
  LABEL_FONT_SIZE = 14

  // Positioning offset for the FMA Component
  static SVG_FMA_X_OFFSET = 10

  // Offset for the inverted (or flipped) label to correct for text alignment
  SVG_INVERTED_LABEL_OFFSET_Y = 18

  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  public drawSVG<T extends object> (data: T, label?: Label): ISvgElement | null {
    const fma = data as FMAComponent
    const svg = this.getSvgFromCatalog(data)
    if (svg === null) {
      console.warn('Unsupported FMA ' + fma.type.toString())
      return SvgDraw.getErrorSVG()
    }

    return this.draw(svg, fma.rightSide, fma.label ?? null)
  }

  /**
   * Prepares a FMA Component for rendering
   *
   * @param fma The FMA Component SVG
   * @param rightSide whether to render the to
   *                the right side of the track or the left
   * @param label the label for the FMA Component (or null if none)
   * @returns a {@link ISvgElement} containing the final SVG for rendering
   */
  draw (fma: ISvgElement, rightSide: boolean, label: Label | null): ISvgElement {
    // Add extra area if required to fully display the label
    let drawArea = this.SVG_FMA_DRAWAREA
    if (label != null) {
      drawArea = Math.max(drawArea, label.text.length * this.DRAWAREA_LABEL_PER_CHARACTER_HEIGHT)
    }

    const drawAreaCenter = drawArea / 2
    const svg = SvgDraw.createSvgWithHead(drawArea, drawArea)

    const fmasvg = fma.content.cloneNode(true) as Element
    const offset = rightSide ? SvgDrawFMAComponent.SVG_FMA_X_OFFSET : -SvgDrawFMAComponent.SVG_FMA_X_OFFSET
    fmasvg.setTranslate(drawAreaCenter + offset, drawAreaCenter)

    if (label !== null) {
      fmasvg.appendChild(SvgDraw.drawLabelAt(
        label,
        this.SVG_FMA_LABEL_OFFSET_X,
        this.SVG_FMA_LABEL_OFFSET_Y,
        false,
        false,
        this.LABEL_FONT_SIZE
      ))
    }

    svg.appendChild(fmasvg)

    return new SvgElement('FMAComponent', svg, new Array<ISvgPoint>(), null, fma.boundingBox)
  }

  public getFeatureType (): FeatureType {
    return FeatureType.FMA
  }
}
