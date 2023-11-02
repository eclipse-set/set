/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
import { checkInstance } from '@/util/ObjectExtension'
import { Label } from './Label'
import { defaultPositionObj, Position } from './Position'
import { defaultRouteLocationObj, RouteObject } from './RouteObject'
import SiteplanObject, { defaultObjectColorObj } from './SiteplanObject'

export enum PZBType {
    GM = 'GM',
    GUE_GSA = 'GUE_GSA',
    GUE_GSE = 'GUE_GSE'
}

export enum PZBElement {
    None = 'None',
    F500Hz = '500Hz',
    F1000Hz = '1000Hz',
    F2000Hz = '2000Hz',
    F1000Hz2000Hz = '1000Hz2000Hz',
}

export enum PZBEffectivity {
    None = 'None',
    Signal = 'Signal',
    Route = 'Route',
    Always = 'Always'
}

export interface PZB extends RouteObject, SiteplanObject
{
    guid: string
    position: Position
    type: PZBType
    element: PZBElement
    rightSide: boolean
    effectivity: PZBEffectivity
}

export function defaultPZBObj (): PZB {
  return {
    guid: '0',
    position: defaultPositionObj(),
    type: PZBType.GM,
    element: PZBElement.F1000Hz,
    rightSide: true,
    effectivity: PZBEffectivity.Always,
    objectColors: [defaultObjectColorObj()],
    routeLocations: [defaultRouteLocationObj()]
  }
}

// eslint-disable-next-line @typescript-eslint/no-explicit-any
export function instanceofPZB (object: any): boolean {
  return checkInstance(object, defaultPZBObj())
}

/**
     * Returns a label for a PZB
     * @param pzb the PZB
     * @returns a label for the PZB
     */
export function getPZBLabel (pzb: PZB): Label {
  let text = ''
  switch (pzb.type) {
    case PZBType.GUE_GSA:
      text = 'GSA'
      break
    case PZBType.GUE_GSE:
      text = 'GSE'
      break
    case PZBType.GM:
      switch (pzb.element) {
        case PZBElement.F1000Hz:
          text = '1'
          break
        case PZBElement.F2000Hz:
          text = '2'
          break
        case PZBElement.F500Hz:
          text = '0,5'
          break
        case PZBElement.F1000Hz2000Hz:
          text = '1/2'
          break
        default:
      }

      break
    default:
  }

  return {
    text,
    orientationInverted: true
  } as Label
}
