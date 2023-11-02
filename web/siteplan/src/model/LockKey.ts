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
import { defaultRouteLocationObj, RouteObject } from './RouteObject'
import SiteplanObject, { defaultObjectColorObj } from './SiteplanObject'

export enum LockKeyType {
  Inside = 'inside',
  Outside = 'outside'
}

export default interface LockKey extends SiteplanObject, RouteObject {
  guid: string
  label: Label
  position: Position
  locked: boolean
  type: LockKeyType
}

export function defaultLockKeyObj (): LockKey {
  return {
    guid: '123',
    label: defaultLabelObj(),
    position: defaultPositionObj(),
    locked: true,
    type: LockKeyType.Inside,
    objectColors: [defaultObjectColorObj()],
    routeLocations: [defaultRouteLocationObj()]
  }
}

// eslint-disable-next-line @typescript-eslint/no-explicit-any
export function isInstaceOfLockKey (obj: any):boolean {
  return checkInstance(obj, defaultLockKeyObj())
}
