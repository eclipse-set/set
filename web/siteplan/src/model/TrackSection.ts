/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
import { distance } from '@/util/Math'
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

export default interface TrackSection
{
    guid: string
    shape: TrackShape
    segments: TrackSegment[]
    color: string
    startCoordinate: Coordinate
}

export type FlippedFlag = boolean

/**
 * The distance, at which two coordinates of track segments are considered the same point.
 * This value is to be used as the tolerance in orderedSegmentsOfTrackSectionWithTolerance
 */
export const TRACK_SEGMENT_ORDERING_COORDINATE_MATCHING_DISTANCE = 0.001

/**
   * orderedSegments returns a list of segments,
   * where every end of one segment is equal to the start of the next.
   *
   * also, the very first start is equal to the startCoordinate of this TrackSection.
   *
   * Notice: if flipped is true, the segment should be handled in reverse. Examples:
   * If a and b are not flipped,   then:    a.positions[last] = b.positions[0]
   * If a is not flipped and b is, then:    a.positions[last] = b.positions[last]
   * If a is flipped and b is not, then:    a.positions[0] = b.positions[0]
   * If a and b are flipped,       then:    a.positions[0] = b.positions[last]
   *
   * @param [tolerance=0.0] is the max distance between any two coordinates, at wich
   *                        they will be considered to be referencing the same point.
   *                        In that case, the segments will be ordered and flipped,
   *                        s.t. they will follow each other in the result
   *
   *                        Ideally, this parameter is not required in the future.
   *                        Unfortunately, the model-transformator code produces
   *                        a JSON with slightly varying positions.
   * @returns undefined, when section.startCoordinate is not defined
   */
export function orderedSegmentsOfTrackSectionWithTolerance (section: TrackSection, tolerance = 0.0):
  [TrackSegment, FlippedFlag][] | undefined {
  // if undefined (like in invalid .planpro-files),
  // it's not possible to determine the correct ordering!
  if (!section.startCoordinate)
    return undefined

  const lastPos = { x:section.startCoordinate.x,y:section.startCoordinate.y }
  const result: [TrackSegment,boolean][] = []

  // find at first pos of all segments:
  const segmentsWithPosNotFlipped = section.segments.filter(
    seg => distance([seg.positions[0].x,seg.positions[0].y],[lastPos.x,lastPos.y]) <= tolerance
  )
  const segmentsWithPosFlipped = section.segments.filter(
    seg => distance(
      [seg.positions[seg.positions.length - 1].x,seg.positions[seg.positions.length - 1].y],
      [lastPos.x,lastPos.y]
    ) <= tolerance

  )
  // TODO throw exception?
  const amountSegmentsWithLastPos = segmentsWithPosNotFlipped.length + segmentsWithPosFlipped.length
  console.assert(
    amountSegmentsWithLastPos === 1,
    `there must be exactly one segment where start or end is equal to lastPos! (${amountSegmentsWithLastPos} found)`
  )

  if (segmentsWithPosNotFlipped.length === 1) {
    result.push([segmentsWithPosNotFlipped[0],false])
  } else if (segmentsWithPosFlipped.length === 1) {
    result.push([segmentsWithPosFlipped[0],true])
  } else {
    // the assertion above will then fail...
    return undefined
  }

  return result
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
