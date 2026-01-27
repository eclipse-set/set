/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
import { FeatureType } from '@/feature/FeatureInfo'
import { ExternalElementControl } from '@/model/ExternalElementControl'
import { FMAComponent } from '@/model/FMAComponent'
import LockKey from '@/model/LockKey'
import { PZB } from '@/model/PZB'
import { ISvgElement, ISvgPoint, SvgElement, SvgPoint, ZusatzSignal } from '@/model/SvgElement'
import TrackClose from '@/model/TrackClose'
import TrackLock from '@/model/TrackLock'
import UnknownObject from '@/model/UnknownObject'
import { fromHTMLElement } from '@/util/ExtentExtension'
import CantSVGCatalog from '@/util/SVG/SVGCatalog/CantCatalog'
import DirectionBoardSVGCatalog from '@/util/SVG/SVGCatalog/DirectionBoardSVGCatalog'
import ExternalElementControlSVGCatalog from '@/util/SVG/SVGCatalog/ExternalElementControlSVGCatalog'
import FMAComponentCatalog from '@/util/SVG/SVGCatalog/FMAComponentCatalog'
import LockKeySVGCatalog from '@/util/SVG/SVGCatalog/LockKeySVGCatalog'
import OthersSVGCatalog from '@/util/SVG/SVGCatalog/OtherCatalog'
import PZBCatalog from '@/util/SVG/SVGCatalog/PZBCatalog'
import SignalMountSVGCatalog from '@/util/SVG/SVGCatalog/SignalMountSVGCatalog'
import AdditiveSignalScreen from '@/util/SVG/SVGCatalog/Signals/AdditiveSignalScreen'
import BesideSignalScreen from '@/util/SVG/SVGCatalog/Signals/BesideSignalScreen'
import CrossingSignalScreen from '@/util/SVG/SVGCatalog/Signals/CrossingSignalScreen'
import GuardSignalScreen from '@/util/SVG/SVGCatalog/Signals/GuardSignalScreen'
import HlSvSystemScreen from '@/util/SVG/SVGCatalog/Signals/HlSvSystemScreen'
import HvSystemScreen from '@/util/SVG/SVGCatalog/Signals/HvSystemScreen'
import KsSystemScreen from '@/util/SVG/SVGCatalog/Signals/KsSystemScreen'
import OrientationSignalScreen from '@/util/SVG/SVGCatalog/Signals/OrientationSignalScreen'
import ShuntServiceSignalScreen from '@/util/SVG/SVGCatalog/Signals/ShuntServiceSignalScreen'
import SlowDriveSignalScreen from '@/util/SVG/SVGCatalog/Signals/SlowDriveSignalScreen'
import SwitchSignalScreen from '@/util/SVG/SVGCatalog/Signals/SwitchSignaleScreen'
import SignalSVGCatalog from '@/util/SVG/SVGCatalog/SignalSVGCatalog'
import TrackCloseSVGCatalog from '@/util/SVG/SVGCatalog/TrackCloseSVGCatalog'
import TrackLockSVGCatalog from '@/util/SVG/SVGCatalog/TrackLockSVGCatalog'
import { AndereSignalGroup, HauptVorSignalGroup, OtherSVGCatalog, SVGMast } from '@/util/SVG/SvgEnum'
import axios from 'axios'

export default class SvgCatalogService {
  private static INSTANCE = new SvgCatalogService()

  private catalogLoaded: Promise<void>
  private catalog: Map<string, ISvgElement[]> = new Map<string, ISvgElement[]>()
  private signalSVGCatalog: SignalSVGCatalog[] = []
  private fmaComponentCatalog: FMAComponentCatalog
  private pzbCatalog: PZBCatalog
  private trackLockCatalog: TrackLockSVGCatalog
  private trackCloseCatalog: TrackCloseSVGCatalog
  private directionBoardCatalog: DirectionBoardSVGCatalog
  private signalMountCatalog: SignalMountSVGCatalog
  private eecCatalog: ExternalElementControlSVGCatalog
  private lockKeyCatalog: LockKeySVGCatalog
  private othersCatalog: OthersSVGCatalog
  private cantCatalog: CantSVGCatalog
  private constructor () {
    this.catalogLoaded = this.loadSvgCatalog()
    this.registerSignalSVGCatalog()

    this.fmaComponentCatalog = new FMAComponentCatalog(this.catalog)
    this.pzbCatalog = new PZBCatalog(this.catalog)
    this.trackLockCatalog = new TrackLockSVGCatalog(this.catalog)
    this.trackCloseCatalog = new TrackCloseSVGCatalog(this.catalog)
    this.directionBoardCatalog = new DirectionBoardSVGCatalog(this.catalog)
    this.signalMountCatalog = new SignalMountSVGCatalog(this.catalog)
    this.eecCatalog = new ExternalElementControlSVGCatalog(this.catalog)
    this.lockKeyCatalog = new LockKeySVGCatalog(this.catalog)
    this.cantCatalog = new CantSVGCatalog(this.catalog)
    this.othersCatalog = new OthersSVGCatalog(this.catalog)
  }

  public static getInstance () {
    return SvgCatalogService.INSTANCE
  }

  public async isReady (): Promise<void> {
    await this.catalogLoaded
  }

  public getSignalSVGCatalog (): SignalSVGCatalog[] {
    return this.signalSVGCatalog
  }

  public getTrackLockSVGCatalog (): TrackLockSVGCatalog {
    return this.trackLockCatalog
  }

  public getDirectionBoardSVGCatalog () : DirectionBoardSVGCatalog {
    return this.directionBoardCatalog
  }

  public getSignalMountSVGCatalog () : SignalMountSVGCatalog {
    return this.signalMountCatalog
  }

  public getCatalog (): Map<string, ISvgElement[]> {
    return this.catalog
  }

  public getObjSvg<T extends object> (object: T, featureType: FeatureType) {
    switch (featureType) {
      case FeatureType.FMA:
        return this.fmaComponentCatalog.getSVG(object as FMAComponent)
      case FeatureType.PZB:
        return this.pzbCatalog.getSVG(object as PZB)
      case FeatureType.TrackLock:
        return this.trackLockCatalog.getTrackLockSVG(object as TrackLock)
      case FeatureType.TrackClose:
        return this.trackCloseCatalog.getTrackCloseSvg(object as TrackClose)
      case FeatureType.ExternalElementControl:
        return this.eecCatalog.getExternalElementControlSvg(object as ExternalElementControl)
      case FeatureType.LockKey:
        return this.lockKeyCatalog.getLockKeySvg(object as LockKey)
      case FeatureType.Cant:
      case FeatureType.CantLine:
        return this.cantCatalog.getCantSvg()
      case FeatureType.Unknown:
        return this.othersCatalog.getUnknownSvg(object as UnknownObject)
      default:
        return null
    }
  }

  private async loadSvgCatalog (): Promise<void> {
    console.info('Start loading svg catalog...')
    const listSignalSvgFile = Object.assign({}, HauptVorSignalGroup, AndereSignalGroup, SVGMast)
    const promises: Promise<void>[] = []
    for (const [, file] of Object.entries(listSignalSvgFile)) {
      promises.push(this.loadSvgFile(file))
    }
    const listOtherSvgFile = Object.assign({}, OtherSVGCatalog)
    for (const [, file] of Object.entries(listOtherSvgFile)) {
      promises.push(this.loadSvgFile(file))
    }
    await Promise.all(promises)
    console.info('Loading svg catalog finished.')
  }

  private loadSvgFile (file: string): Promise<void> {
    const parser = new DOMParser()
    return axios.get(`/SvgKatalog/${file}.svg`).then(response => {
      const domXML = parser.parseFromString(response.data, 'image/svg+xml')
      const svgs = this.createSVGElements(domXML)
      this.catalog.set(file, svgs)
    })
  }

  /**
    * Register all Signal Type
    */
  private registerSignalSVGCatalog () {
    this.signalSVGCatalog.push(new KsSystemScreen(this.catalog))
    this.signalSVGCatalog.push(new HvSystemScreen(this.catalog))
    this.signalSVGCatalog.push(new HlSvSystemScreen(this.catalog))
    this.signalSVGCatalog.push(new AdditiveSignalScreen(this.catalog))
    this.signalSVGCatalog.push(new GuardSignalScreen(this.catalog))
    this.signalSVGCatalog.push(new SlowDriveSignalScreen(this.catalog))
    this.signalSVGCatalog.push(new ShuntServiceSignalScreen(this.catalog))
    this.signalSVGCatalog.push(new OrientationSignalScreen(this.catalog))
    this.signalSVGCatalog.push(new SwitchSignalScreen(this.catalog))
    this.signalSVGCatalog.push(new BesideSignalScreen(this.catalog))
    this.signalSVGCatalog.push(new BesideSignalScreen(this.catalog))
    this.signalSVGCatalog.push(new CrossingSignalScreen(this.catalog))
  }

  /**
   * Create liste SvgElement from DOM
   * @param htmlDocument
   * @returns SvgElement liste
   */
  private createSVGElements (htmlDocument: Document): ISvgElement[] {
    const defs = htmlDocument.getElementsByTagName('defs')
    if (defs.length === 0) {
      return []
    }

    const listElement = defs.item(0)?.children
    if (!listElement) {
      return []
    }

    const result: ISvgElement[] = new Array<ISvgElement>()
    for (const item of listElement) {
      const svgElement = this.createSVGElement(item)
      if (svgElement) {
        result.push(svgElement)
      }
    }
    return result
  }

  /**
   * Create SvgElement from DOM
   * @param ele
   * @returns SvgElement
   */
  private createSVGElement (ele: Element): ISvgElement | null {
    const content = ele.cloneNode(true) as Element
    if (content === null) {
      return null
    }

    const id = content.getAttribute('id')
    const anchors = new Array<ISvgPoint>()
    let nullPunk = null
    const boundBox = fromHTMLElement(content, true)
    // IMPROVE: Chrome does not find anchors if the 'toolbox'-Namespace is used
    // as a workaround '*' is used to search all namespaces. See PLANPRO-4658
    const toolboxAnchors = content.getElementsByTagNameNS('*', 'anchor')
    for (let index = toolboxAnchors.length - 1; index >= 0; index--) {
      const item = toolboxAnchors[index]
      const pointID = item.getAttribute('id')
      const pointX = item.getAttribute('x')
      const pointY = item.getAttribute('y')
      if (pointID && pointX && pointY) {
        anchors.push(new SvgPoint(pointID, pointX, pointY))
        if (item.parentNode) {
          item.parentNode.removeChild(item)
        }
      }
    }
    const nullpunkelement = content.children.namedItem('nullpunkt')
    if (nullpunkelement) {
      const id = nullpunkelement.id
      const x = nullpunkelement.getAttribute('x')
      const y = nullpunkelement.getAttribute('y')
      if (x && y) {
        nullPunk = new SvgPoint(id, x, y)
        if (nullpunkelement.parentNode) {
          nullpunkelement.parentNode.removeChild(nullpunkelement)
        }
      }
    }

    if (!id) {
      return null
    }

    const zusatzSignalPosition = content.getElementsByTagNameNS('*', 'zusatzsignale')
    if (zusatzSignalPosition.length > 0) {
      const item = zusatzSignalPosition[0]
      const parent = item.parentNode
      const position = item.getAttribute('position')
      if (parent) {
        parent.removeChild(item)
      }

      return new ZusatzSignal(id, content, anchors, nullPunk, position || '', boundBox)
    }

    return new SvgElement(id, content, anchors, nullPunk, boundBox)
  }
}
