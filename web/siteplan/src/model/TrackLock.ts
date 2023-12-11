/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

import { defaultLabelObj, Label } from './Label'
import { defaultPositionObj, Position } from './Position'
import SiteplanObject, { defaultObjectColorObj } from './SiteplanObject'
import { LeftRight } from './SiteplanModel'
import { checkInstance } from '@/util/ObjectExtension'
import { TurnoutOperatingMode } from './TrackSwitchComponent'

export enum TrackLockSignal {
  bothside = 'beidseitig',
  oneside = 'einseitig',
}

export enum TrackLockLocation {
  onTrack = 'OnTrack',
  besideTrack = 'BesideTrack'
}

export interface TrackLockComponent {
  guid: string
  position: Position
  trackLockSignal: TrackLockSignal
  ejectionDirection: LeftRight
}

export function defautTrackLockComponentObj (): TrackLockComponent {
  return {
    guid: '123',
    position: defaultPositionObj(),
    trackLockSignal: TrackLockSignal.oneside,
    ejectionDirection: LeftRight.LEFT
  }
}

// eslint-disable-next-line @typescript-eslint/no-explicit-any
export function isInstanceOfTrackLockComponent (obj: any): boolean {
  return checkInstance(obj, defautTrackLockComponentObj())
}

export default interface TrackLock extends SiteplanObject {
  label?: Label
  preferredLocation: TrackLockLocation
  operatingMode: TurnoutOperatingMode
  components: TrackLockComponent[]
}

export function defaultTrackLockObj (): TrackLock {
  return {
    guid: '123',
    label: defaultLabelObj(),
    preferredLocation: TrackLockLocation.onTrack,
    operatingMode: TurnoutOperatingMode.MechanicalLocal,
    components: [defautTrackLockComponentObj()],
    objectColors: [defaultObjectColorObj()]
  }
}
// eslint-disable-next-line @typescript-eslint/no-explicit-any
export function isInstanceOfTrackLock (obj: any): boolean {
  return checkInstance(obj, defaultTrackLockObj())
}

