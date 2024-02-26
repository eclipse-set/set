/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
import { checkInstance } from '@/util/ObjectExtension'
import { defaultLabelObj, Label } from './Label'
import { defaultPositionObj, Position } from './Position'
import SiteplanObject, { defaultObjectColorObj } from './SiteplanObject'

export interface Platform
{
    guid: string
    points: Position[]
    label?: Label
}

export function defaultPlatformObj (): Platform {
  return {
    guid: 'abc',
    points: [defaultPositionObj()],
    label: defaultLabelObj()
  }
}
// eslint-disable-next-line @typescript-eslint/no-explicit-any
export function isInstanceOfPlatform (object: any): boolean {
  return checkInstance(object, defaultPlatformObj())
}
export interface Station extends SiteplanObject {
    platforms: Platform[]
    label?: Label
}

export function defaultStationObj (): Station {
  return {
    platforms: [defaultPlatformObj()],
    label: defaultLabelObj(),
    guid: 'abc',
    objectColors: [defaultObjectColorObj()]
  }
}

// eslint-disable-next-line @typescript-eslint/no-explicit-any
export function isInstanceOfStation (object: any): boolean {
  return checkInstance(object, defaultStationObj())
}

export enum StationPart {
    FirstLine = 'first',
    SecondLine = 'second',
    ThridLine = 'thrid',
    FourLine = 'four',
    Label = 'label'
}
