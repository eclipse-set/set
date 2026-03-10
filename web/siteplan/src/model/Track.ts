/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
import { checkInstance } from '@/util/ObjectExtension'
import { Coordinate, defaultCoordinateObj, defaultPositionObj, Position } from './Position'
import SiteplanObject, { defaultObjectColorObj } from './SiteplanObject'
import TrackSection, { defaultTrackSectionObj } from './TrackSection'

export interface TrackDesignation {
    name: string
    position: Position
}

export function defaultTrackDesignationObj (): TrackDesignation {
  return {
    name: 'abc',
    position: defaultPositionObj()
  }
}

// eslint-disable-next-line @typescript-eslint/no-explicit-any
export function isInstanceOfTrackDesignation (object: any): boolean {
  return checkInstance(object, defaultTrackDesignationObj())
}

export default interface Track extends SiteplanObject
{
    guid: string
    sections: TrackSection[]
    designations: TrackDesignation[]
    startCoordinate: Coordinate
}

export function defaultTrackObj (): Track {
  return {
    guid: 'abc',
    designations: [defaultTrackDesignationObj()],
    sections: [defaultTrackSectionObj()],
    objectColors: [defaultObjectColorObj()],
    startCoordinate: defaultCoordinateObj()
  }
}

// eslint-disable-next-line @typescript-eslint/no-explicit-any
export function isInstanceOfPlatform (object: any): boolean {
  return checkInstance(object, defaultTrackObj())
}
