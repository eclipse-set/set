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

export enum FMAComponentType {
    None = 'None',
    Axle = 'Axle',
    NFTrackCircuit = 'NFTrackCircuit',
    TFTrackCircuit = 'TFTrackCircuit'
}

export interface FMAComponent extends RouteObject, SiteplanObject
{
    guid: string
    position: Position
    label?: Label
    type: FMAComponentType
    rightSide: boolean
}

export function defaultFMAComponentObj (): FMAComponent {
  return {
    guid: '0',
    position: defaultPositionObj(),
    type: FMAComponentType.Axle,
    rightSide: true,
    routeLocations: [defaultRouteLocationObj()],
    objectColors: [defaultObjectColorObj()]
  }
}

// eslint-disable-next-line @typescript-eslint/no-explicit-any
export function isInstanceOfFMA (obj: any): boolean {
  return checkInstance(obj, defaultFMAComponentObj())
}
