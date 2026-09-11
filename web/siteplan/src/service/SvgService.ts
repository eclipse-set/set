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
import { defaultObjectColorObj, isInstanceOfObjectColor, ObjectColor } from '@/model/SiteplanObject'
import { ISvgElement } from '@/model/SvgElement'
import { store } from '@/store'
import Configuration from '@/util/Configuration'
import { getFirstValue } from '@/util/ObjectExtension'
import SvgDraw from '@/util/SVG/Draw/SvgDraw'
import SvgDrawBridge from '@/util/SVG/Draw/SvgDrawBridge'
import SvgDrawCant from '@/util/SVG/Draw/SvgDrawCant'
import SvgDrawExternalElementControl from '@/util/SVG/Draw/SvgDrawExternalElementControl'
import SvgDrawFMAComponent from '@/util/SVG/Draw/SvgDrawFMAComponent'
import SvgDrawLockKey from '@/util/SVG/Draw/SvgDrawLockKey'
import SvgDrawOthers from '@/util/SVG/Draw/SvgDrawOthers'
import SvgDrawPZB from '@/util/SVG/Draw/SvgDrawPZB'
import SvgDrawTrackClose from '@/util/SVG/Draw/SvgDrawTrackClose'
import SvgDrawTracklock from '@/util/SVG/Draw/SvgDrawTracklock'
import SvgDrawTrackSwitch from '@/util/SVG/Draw/SvgDrawTrackSwitch'
import OlIcon from 'ol/style/Icon'
import OlStyle from 'ol/style/Style'
import AbstractDrawSVG from '../util/SVG/Draw/AbstractDrawSVG'
import SvgDrawSignal from '../util/SVG/Draw/SvgDrawSignal'
import SvgCatalogService from './SvgCatalogService'
import SvgColorService from './SvgColorService'

/** all data required to produce any svg. Different featureTypes store different data T */
export interface DrawSVGData<T> {
  data: T
  featureType: FeatureType
  label?: Label
}

/** Given some DrawSVGData, it produeces svg or styling on request.
 *  DrawSVGData is cached, so for repeated requests of the same data a SVG
 *  is constructed only once.
*/
export default class SvgService {
  private featureStyleCache: Map<string, OlStyle> = new Map<string, OlStyle>()
  private featureSvgElementCache: Map<string, ISvgElement> = new Map<string, ISvgElement>()
  private drawFeatureClass: AbstractDrawSVG[] = []
  private catalogService: SvgCatalogService
  constructor () {
    this.catalogService = SvgCatalogService.getInstance()
    this.registerDrawFeatureSvg()
  }

  public registerDrawFeatureSvg () {
    this.drawFeatureClass.push(new SvgDrawBridge(this.catalogService))
    this.drawFeatureClass.push(new SvgDrawSignal(this.catalogService))
    this.drawFeatureClass.push(new SvgDrawFMAComponent(this.catalogService))
    this.drawFeatureClass.push(new SvgDrawPZB(this.catalogService))
    this.drawFeatureClass.push(new SvgDrawTrackClose(this.catalogService))
    this.drawFeatureClass.push(new SvgDrawTracklock(this.catalogService))
    this.drawFeatureClass.push(new SvgDrawTrackSwitch(this.catalogService))
    this.drawFeatureClass.push(new SvgDrawLockKey(this.catalogService))
    this.drawFeatureClass.push(new SvgDrawExternalElementControl(this.catalogService))
    this.drawFeatureClass.push(new SvgDrawOthers(this.catalogService))
    this.drawFeatureClass.push(new SvgDrawCant(this.catalogService))
  }

  /**
   * Returns the base zoom level.
   * This is used to calculate the final size of the image.
   * Adjusting it will require rescaling of all SVG images
   */
  public getBaseZoomLevel (): number {
    return Configuration.getToolboxConfiguration().baseZoomLevel
  }

  public getFeatureStyle<T extends object> (data: T, featureType: FeatureType, label?: Label) {
    const drawData = {
      data,
      featureType,
      label
    }
    return this.getSVGStyle(drawData, drawData => {
      return this.drawFeatureSVG(drawData.data, drawData.featureType, drawData.label)
    })
  }

  private getSVGStyle<T extends object> (
    drawData: DrawSVGData<T>,
    svgFunction: ((object: DrawSVGData<T>) => ISvgElement)
  ): OlStyle {
    const cacheKey = JSON.stringify(drawData)
    let cachedStyle = this.featureStyleCache.get(cacheKey)
    if (cachedStyle === undefined) {
      const cachedSvg = this.featureSvgElementCache.get(cacheKey)
      const svg = cachedSvg ?? svgFunction(drawData)
      this.appendSiteplanfontStyle(svg.content)
      const objColor = getFirstValue<ObjectColor>(drawData, 'objectColors', defaultObjectColorObj())

      if (objColor !== undefined && isInstanceOfObjectColor(objColor) &&
        !SvgColorService.isAlreadColorable(svg.content)) {
        SvgColorService.applyColor(svg.content, Array.isArray(objColor) ? objColor : [objColor])
      }

      cachedStyle = new OlStyle({
        image: new OlIcon({
          opacity: 1,
          src: 'data:image/svg+xml;utf8,' + svg.content.outerHTML,
          rotateWithView: true
        })
      })
      if (!cachedSvg) {
        this.featureSvgElementCache.set(cacheKey, svg)
      }

      this.featureStyleCache.set(cacheKey, cachedStyle)
    }

    return cachedStyle
  }

  private appendSiteplanfontStyle (element: Element) {
    if (store.state.siteplanfont === null) {
      return
    }

    const fontStyle = document.createElement('style')
    fontStyle.innerHTML = store.state.siteplanfont
    element.appendChild(fontStyle)
  }

  public getFeatureSvg<T extends object> (data: T, featureType: FeatureType, label?: Label): ISvgElement {
    const drawData: DrawSVGData<T> = {
      data,
      featureType,
      label
    }
    const cacheKey = JSON.stringify(drawData)
    let cachedSvg = this.featureSvgElementCache.get(cacheKey)
    if (cachedSvg === undefined) {
      cachedSvg = this.drawFeatureSVG(data, featureType, label)
      this.featureSvgElementCache.set(cacheKey, cachedSvg)
    }

    return cachedSvg
  }

  public getSvgElementInGroup (groupName: string): ISvgElement[] | null | undefined {
    return this.catalogService.getCatalog().get(groupName)
  }

  public drawFeatureSVG<T extends object> (data: T, featureType: FeatureType, label?: Label): ISvgElement {
    const svg = this.drawFeatureClass.map(drawClass => {
      if (drawClass.getFeatureType() === featureType) {
        return drawClass.drawSVG(data, label)
      }

      return drawClass.drawOtherSVG(data, featureType, label)
    }).find(ele => ele !== null)

    if (svg && svg !== null) {
      return svg
    }

    return SvgDraw.getErrorSVG()
  }
}
