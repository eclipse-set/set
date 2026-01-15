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
import LockKey from '@/model/LockKey'
import { ISvgElement, SvgElement } from '@/model/SvgElement'
import { getHeight, getWidth } from 'ol/extent'
import AbstractDrawSVG from './AbstractDrawSVG'
import SvgDraw from './SvgDraw'

export default class SvgDrawLockKey extends AbstractDrawSVG{
  SVG_DRAWAREA = 500
  LABEL_FONT_SIZE = 14

  public drawSVG<T extends object> (data: T, label?: Label | undefined): ISvgElement | null {
    const lockkey = data as LockKey
    const svg = this.getSvgFromCatalog(data)
    if (svg === null) {
      console.warn('Unsupported Schluesselsperre: ' + lockkey.guid)
      return SvgDraw.getErrorSVG()
    }

    return this.drawLockKey(svg, lockkey, label)
  }

  private drawLockKey (lockKeySvg: ISvgElement, lockKey: LockKey, label?: Label): ISvgElement {
    const svg = SvgDraw.createSvgWithHead(this.SVG_DRAWAREA, this.SVG_DRAWAREA)
    const clone = lockKeySvg.content.cloneNode(true) as Element

    const lockkeyPath = clone.querySelector('#schloss')
    if (!lockKey.locked) {
      lockkeyPath?.setAttribute('fill', 'grey')
      lockkeyPath?.setAttribute('stroke', 'grey')
    }

    const translate = [
      (this.SVG_DRAWAREA - getWidth(lockKeySvg.boundingBox[0])) / 2,
      (this.SVG_DRAWAREA - getHeight(lockKeySvg.boundingBox[0])) / 2
    ]
    clone.setAttribute('transform', `translate(${translate[0]},${translate[1]})`)
    svg.appendChild(clone)

    if (label) {
      svg.appendChild(SvgDraw.drawLabelAt(
        label,
        this.SVG_DRAWAREA / 2 - getHeight(lockKeySvg.boundingBox[0]) - 2,
        this.SVG_DRAWAREA / 2,
        false,
        true,
        this.LABEL_FONT_SIZE
      ))
    }

    return new SvgElement('LockKey', svg, [], null, lockKeySvg.boundingBox)
  }

  public getFeatureType (): FeatureType {
    return FeatureType.LockKey
  }
}
