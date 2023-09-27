/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
import { checkInstance } from '@/util/ObjectExtension'
import { Coordinate, defaultPositionObj } from './Position'

export enum TrackType {
    None = 'None',
    Other = 'Other',
    SideTrack = 'SideTrack',
    ConnectingTrack = 'ConnectingTrack',
    MainTrack = 'MainTrack',
    PassingMainTrack = 'PassingMainTrack',
    RouteTrack = 'RouteTrack'
}

export default interface TrackSegment
{
    type: TrackType[]
    positions: Coordinate[]
}

export function defaultTrackSegmentObj () : TrackSegment {
  return {
    type: [TrackType.ConnectingTrack],
    positions: [defaultPositionObj()]
  }
}

// eslint-disable-next-line @typescript-eslint/no-explicit-any
export function isInstanceOfTrackSegment (x: any): boolean {
  return checkInstance(x, defaultTrackSegmentObj())
}
