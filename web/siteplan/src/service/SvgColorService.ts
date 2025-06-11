/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

import { defaultObjectColorObj, isInstanceOfObjectColor, ObjectColor } from '@/model/SiteplanObject'
import { getFirstValue } from '@/util/ObjectExtension'

/**
 * Apply color for Svg Element by element ID
 * @author Truong
 */
export default class SvgColorService {
  public static createRecolorFilter (id: string, color: number[]): Element {
    // Add a SVG filter which allows recoloring
    const ns = 'http://www.w3.org/2000/svg'
    const filter = document.createElementNS(ns, 'filter')
    filter.setAttribute('id', id)
    filter.setAttribute('color-interpolation-filters', 'sRGB')
    const colorMatrix = document.createElementNS(ns, 'feColorMatrix')
    colorMatrix.setAttribute('in', 'SourceGraphic')
    colorMatrix.setAttribute('type', 'matrix')
    filter.appendChild(colorMatrix)
    colorMatrix.setAttribute('values', `
      1 0 0 0 ${color[0] / 255}
      0 1 0 0 ${color[1] / 255}
      0 0 1 0 ${color[2] / 255}
      0 0 0 1 0`)
    return filter
  }

  public static isColorable (obj: object): boolean {
    const objColor = getFirstValue(obj, 'objectColors', defaultObjectColorObj())
    return objColor !== undefined && isInstanceOfObjectColor(objColor)
  }

  public static isAlreadColorable (svg: Element): boolean {
    return svg.getElementsByTagName('filter').length > 0
  }

  public static applyColor (svg: Element, objectColor: ObjectColor[]) {
    if (objectColor.some(obj => this.shouldApplyColor(svg,obj))) {
      objectColor.forEach(obj => this.applyColorByIds(svg, obj.id, obj.color))
      return
    }

    this.applyColorSvg(svg, objectColor.find(obj => obj.id === 'feature')?.color ?? [0, 0, 0])
  }

  private static shouldApplyColor (svg: Element, obj: ObjectColor) {
    return svg.querySelector('#' + obj.id) !== null
  }

  public static applyColorByIds (svg: Element, elementID: string | null, color: number[]) {
    if (!elementID) {
      return
    }

    const element = svg.querySelector('#' + elementID)
    if (!element) {
      return
    }

    element.setAttribute('filter', `url(%23${elementID}_filter)`)
    svg.insertBefore(
      SvgColorService.createRecolorFilter(elementID + '_filter', color),
      svg.children[0]
    )
  }

  protected static applyColorSvg (svg: Element, color: number[]) {
    svg.appendChild(SvgColorService.createRecolorFilter('recolor', color))
    svg.setAttribute('filter', 'url(%23recolor)')
  }
}
