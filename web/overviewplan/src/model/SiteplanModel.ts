/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
import { defaultSignalMountObj, SignalMount } from './SignalMount'
import TrackSwitch, { defaultTrackSwitchObj } from './TrackSwitch'
import Track, { defaultTrackObj } from './Track'
import { defaultFMAComponentObj, FMAComponent } from './FMAComponent'
import { defaultPZBObj, PZB } from './PZB'
import { defaultRouteObj, Route } from './Route'
import { Position } from './Position'
import TrackSwitchEndMarker, {
  defaultTrackSwitchEndMarkerObj
} from './TrackSwitchEndMarker'
import ModelError, { defaultModelErrorObj } from './Error'
import { defaultPZBGUObj, PZBGU } from './PZBGU'
import { defaultStationObj, Station } from './Station'
import TrackLock, { defaultTrackLockObj } from './TrackLock'
import { store } from '@/store'
import TrackClose, { defaultTrackCloseObj } from './TrackClose'
import { checkInstance } from '@/util/ObjectExtension'
import {
  defaultExternalElementControlObj,
  ExternalElementControl
} from './ExternalElementControl'
import LockKey, { defaultLockKeyObj } from './LockKey'
import { LayoutInfo } from './LayoutInfo'
import PositionedObject, { defaultPositionedObj } from './PositionedObject'
import { Cant, defaultCantObj } from './Cant'
import UnknownObject, { defaultUnknownObj } from './UnknownObject'

export const SiteplanColorValue = {
  COLOR_UNCHANGED_VIEW: [140, 150, 157],
  COLOR_UNCHANGED_PLANNING: [0, 0, 0],
  COLOR_ADDED: [179, 40, 33], // RAL3016
  COLOR_REMOVED: [241, 221, 56] // RAL1016
}

export function isPlanningObject (guid: string): boolean {
  return store.state.planningObjectGuids[guid] === true
}

export interface ObjectManagement {
  planningObjectIDs: string[]
  planningGroupID: string
}

export interface SiteplanState {
  signals: SignalMount[]
  trackSwitches: TrackSwitch[]
  trackSwitchEndMarkers: TrackSwitchEndMarker[]
  trackLock: TrackLock[]
  tracks: Track[]
  fmaComponents: FMAComponent[]
  pzb: PZB[]
  pzbGU: PZBGU[]
  routes: Route[]
  stations: Station[]
  errors: ModelError[]
  trackClosures: TrackClose[]
  externalElementControls: ExternalElementControl[]
  lockkeys: LockKey[]
  cants: Cant[]
  unknownObjects: UnknownObject[]
}

export function defaultSiteplanStateObj (): SiteplanState {
  return {
    signals: [defaultSignalMountObj()],
    trackSwitches: [defaultTrackSwitchObj()],
    errors: [defaultModelErrorObj()],
    fmaComponents: [defaultFMAComponentObj()],
    pzb: [defaultPZBObj()],
    pzbGU: [defaultPZBGUObj()],
    routes: [defaultRouteObj()],
    stations: [defaultStationObj()],
    trackClosures: [defaultTrackCloseObj()],
    trackLock: [defaultTrackLockObj()],
    tracks: [defaultTrackObj()],
    trackSwitchEndMarkers: [defaultTrackSwitchEndMarkerObj()],
    externalElementControls: [defaultExternalElementControlObj()],
    lockkeys: [defaultLockKeyObj()],
    cants: [defaultCantObj()],
    unknownObjects: [defaultUnknownObj()]
  }
}

// eslint-disable-next-line @typescript-eslint/no-explicit-any
export function isInstanceOfSiteplanState (object: any): boolean {
  return checkInstance(object, defaultSiteplanStateObj())
}

export default interface SiteplanModel {
  centerPosition: Position
  changedInitialState: SiteplanState
  initialState: SiteplanState
  commonState: SiteplanState
  finalState: SiteplanState
  changedFinalState: SiteplanState
  objectManagement: ObjectManagement[]
  layoutInfo: LayoutInfo[]
}

export enum LeftRight {
  LEFT = 'LEFT',
  RIGHT = 'RIGHT',
}

export enum Direction {
  FORWARD = 'FORWARD',
  OPPOSITE = 'OPPOSITE',
}
