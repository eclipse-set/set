import { Coordinate } from '@/model/Position'
import TrackSection, { TrackShape } from '@/model/TrackSection'
import TrackSegment from '@/model/TrackSegment'
import { distance } from '@/util/Math'
import { FlippedFlag } from '@/util/TrivialTypes'

/**
 * This implementation of TrackSection provides useful functionality on TrackSections.
 * It makes sense to add it as methods to the TrackSection,
 * instead of implementing it as a utility function, because these fn are run
 * precisely only on TrackSections.
 * It also makes sense to leave the model/TrackSection unaltered,
 * so that the folder "model" is only for specification of the LageplanModel.
 */
export class TrackSectionC implements TrackSection {
  guid: string
  shape: TrackShape
  segments: TrackSegment[]
  color: string
  startCoordinate: Coordinate

  constructor (
    guid: string,
    shape: TrackShape,
    segments: TrackSegment[],
    color: string,
    startCoordinate: Coordinate
  ) {
    this.guid = guid
    this.segments = segments
    this.shape = shape
    this.color = color
    this.startCoordinate = startCoordinate
  }

  static fromTrackSection (trackSection: TrackSection): TrackSectionC | undefined {
    return new TrackSectionC(
      trackSection.guid,
      trackSection.shape,
      trackSection.segments,
      trackSection.color,
      trackSection.startCoordinate
    )
  }

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
   * Important:
   *    this implementation has a tolerance, for now.
   *    This is undesired and should be changed. Unfortunately, the java project
   *    Transformer code produces a JSON which requires a tolerance.
   */

  // TODO unittest this!

  public orderedSegments (): [TrackSegment, FlippedFlag][] | undefined {
    return this.orderedSegmentsWithTolerance(0.001) // TODO remove tolerance and move body of ... withtolerance to here!
    // the code that needs to be swapped is this one:
    /*
    const segmentsWithPosNotFlipped = this.segments.filter(
      seg => seg.positions[0].x === lastPos.x && seg.positions[0].y === lastPos.y
    )
    const segmentsWithPosFlipped = this.segments.filter(
      seg => seg.positions[seg.positions.length - 1].x === lastPos.x
          && seg.positions[seg.positions.length - 1].y === lastPos.y
    )
    */
  }

  // look orderedSegments() for documentation!
  // TODO unittest this!
  // TODO remove this (with tolerance). And fix the underlying problem:
  // TODO         startCoordinate is not exactly any position of segments.positions!
  private orderedSegmentsWithTolerance (tolerance = 0.0): [TrackSegment, FlippedFlag][] | undefined {
    // if undefined (like in invalid .planpro-files),
    // it's not possible to determine the correct ordering!
    if (!this.startCoordinate)
      return undefined

    const lastPos = { x:this.startCoordinate.x,y:this.startCoordinate.y }
    const result: [TrackSegment,boolean][] = []

    // find at first pos of all segments:
    const segmentsWithPosNotFlipped = this.segments.filter(
      seg => distance([seg.positions[0].x,seg.positions[0].y],[lastPos.x,lastPos.y]) <= tolerance
    )
    const segmentsWithPosFlipped = this.segments.filter(
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
}
