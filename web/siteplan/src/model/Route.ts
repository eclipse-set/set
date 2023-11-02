/**
  * Copyright (c) 2021 DB Netz AG and others.
  *
  * All rights reserved. This program and the accompanying materials
  * are made available under the terms of the Eclipse Public License v2.0
  * which accompanies this distribution, and is available at
  * http://www.eclipse.org/legal/epl-v20.html
  */
import { checkInstance } from '@/util/ObjectExtension'
import { defaultPositionObj, Position } from './Position'
import SiteplanObject, { defaultObjectColorObj } from './SiteplanObject'
import { TrackShape } from './TrackSection'

export interface RouteSection
{
    guid: string
    shape: TrackShape
    positions: Position[]
}

export function defaultRouteSectionObj (): RouteSection {
  return {
    guid: '0',
    shape: TrackShape.BlossCurvedSimple,
    positions: [defaultPositionObj()]
  }
}

export interface KMMarker
{
    position: Position
    value: number
}

export function defaultKMMarkerObj (): KMMarker {
  return {
    position: defaultPositionObj(),
    value: 0
  }
}

export interface Route extends SiteplanObject
{
    sections: RouteSection[]
    markers: KMMarker[]
}

export function defaultRouteObj (): Route {
  return {
    guid: '0',
    markers: [defaultKMMarkerObj()],
    objectColors: [defaultObjectColorObj()],
    sections: [defaultRouteSectionObj()]
  }
}

// eslint-disable-next-line @typescript-eslint/no-explicit-any
export function isInstanceOfRoute (object: any): boolean {
  return checkInstance<Route>(object, defaultRouteObj())
}
