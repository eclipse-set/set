/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
import { checkInstance } from '@/util/ObjectExtension'
import { Coordinate, defaultCoordinateObj } from './Position'
import TrackSegment, { defaultTrackSegmentObj } from './TrackSegment'

export enum TrackShape {
    Straight = 'Straight',
    Curve = 'Curve',
    Clothoid = 'Clothoid',
    Blosscurve = 'Blosscurve',
    BlossCurvedSimple = 'BlossCurvedSimple',
    Other = 'Other',
    DirectionalStraightKinkEnd = 'DirectionalStraightKinkEnd',
    KmJump = 'KmJump',
    TransitionCurveSForm = 'TransitionCurveSForm',
    SFormSimpleCurved = 'SFormSimpleCurved',
    Unknown = 'Unknown'
}

/**
 * segments are not ordered!
 */
export default interface TrackSection
{
    guid: string
    shape: TrackShape
    segments: TrackSegment[]
    color: string
    startCoordinate: Coordinate
}

export function defaultTrackSectionObj (): TrackSection {
  return {
    guid: '123',
    shape: TrackShape.BlossCurvedSimple,
    segments: [defaultTrackSegmentObj()],
    color: 'black',
    startCoordinate: defaultCoordinateObj()
  }
}

// eslint-disable-next-line @typescript-eslint/no-explicit-any
export function isInstanceOfTrackSection (x: any): boolean {
  return checkInstance(x, defaultTrackSectionObj)
}
