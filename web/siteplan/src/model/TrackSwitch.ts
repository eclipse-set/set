/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
import { checkInstance } from '@/util/ObjectExtension'
import ContinuousTrackSegment from './ContinuousTrackSegment'
import { defaultCoordinateObj } from './Position'
import SiteplanObject, { defaultObjectColorObj } from './SiteplanObject'
import TrackSwitchComponent, { defaultTrackSwitchComponentObj } from './TrackSwitchComponent'

export default interface TrackSwitch extends SiteplanObject
{
    components: TrackSwitchComponent[]
    design: string
    continuousSegments: ContinuousTrackSegment[]
}

export function defaultTrackSwitchObj (): TrackSwitch {
  return {
    components: [defaultTrackSwitchComponentObj()],
    design: 'abc',
    continuousSegments: [
      {
        start: defaultCoordinateObj(),
        end: defaultCoordinateObj()
      }
    ],
    guid: 'abc',
    objectColors: [defaultObjectColorObj()]
  }
}

// eslint-disable-next-line @typescript-eslint/no-explicit-any
export function isInstanceOfTrackSwitch (object: any): boolean {
  return checkInstance(object, defaultTrackSwitchObj())
}

export enum TrackSwitchPart {
    StartMarker = 'start',
    Main = 'main'

}
