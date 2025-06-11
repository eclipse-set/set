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
import Track from '@/model/Track'
import SvgDraw from '@/util/SVG/Draw/SvgDraw'
import AbstractDrawSVG from './AbstractDrawSVG'

/**
 * Draw SVG for {@link SignalMount}
 *
 * @author Truong
 */
export default class SvgDrawTrackDirectionArrow extends AbstractDrawSVG {
  SVG_DRAWAREA = 200
  SVG_DRAWAREA_CENTER = this.SVG_DRAWAREA / 2

  public drawSVG<T extends object> (data: T, label?: Label | undefined): ISvgElement {
    const track = data as Track
    const svg = this.getSvgFromCatalog({})
    if (svg === null) {
      console.warn('Failed to draw TrackDirection Marker' + track.toString())
      return SvgDraw.getErrorSVG()
    }

    return this.draw(svg, track)
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
  draw (arrow_svg: ISvgElement, track: Track): ISvgElement {
    const svg = SvgDraw.createSvgWithHead(this.SVG_DRAWAREA, this.SVG_DRAWAREA)
    const pzbsvg = arrow_svg.content.cloneNode(true) as Element
    pzbsvg.setTranslate(this.SVG_DRAWAREA_CENTER, this.SVG_DRAWAREA_CENTER)

    svg.appendChild(pzbsvg)
    return new SvgElement('TrackDirectionArrow', svg, [], null, arrow_svg.boundingBox)
  }

  public getFeatureType (): FeatureType {
    return FeatureType.TrackDirectionArrow
  }
}
