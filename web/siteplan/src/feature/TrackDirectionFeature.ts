import LageplanFeature from '@/feature/LageplanFeature'
import { Coordinate, Position } from '@/model/Position'
import { SiteplanState } from '@/model/SiteplanModel'
import Track from '@/model/Track'
import TrackSection, { orderedSegmentsOfTrackSectionWithTolerance, TRACK_SEGMENT_ORDERING_COORDINATE_MATCHING_DISTANCE } from '@/model/TrackSection'
import Configuration from '@/util/Configuration'
import { distance } from '@/util/Math'
import SvgDraw from '@/util/SVG/Draw/SvgDraw'
import { Feature } from 'ol'
import Geometry from 'ol/geom/Geometry'
import OlPoint from 'ol/geom/Point'
import { default as Icon } from 'ol/style/Icon'
import { default as Style } from 'ol/style/Style'
import { createFeature, FeatureType } from './FeatureInfo'

/** data stored per track direction feature */
interface TrackDirectionArrowData {
  guid: string
  rotation: number
}

/**
 * A small arrow head, at every segment of every section,
 * pointing in the direction of that segment.
 * It is placed at 2/3 of the length of every segment (in the direction of that segment).
 * It does remain the same size, no matter the zoom (unlike most other features).
 * @author Voigt
 */
export default class TrackDirectionFeature extends LageplanFeature<Track> {
  /** if a track is shorter then this, no arrow head is created for that track segment. */
  static readonly MIN_TRACK_LENGTH_TO_DISPLAY_FEATURE = 0.0001

  getFeatures (model: SiteplanState): Feature<Geometry>[] {
    return this.getObjectsModel(model).map(element => this.createTrackDirectionArrowFeatures(element))
      .flat()
  }

  protected getObjectsModel (model: SiteplanState): Track[] {
    return model.tracks
  }

  private static assertStartPosOccursInPositions (
    startPos: Coordinate,
    section: TrackSection,
    withDist = true,
    tolerance = 0.0
  ) {
    const segmentsWithStartCoord = section.segments
      .filter(seg => seg.positions.find(pos => coordinatesEqual(startPos,pos)))

    if (!withDist && tolerance === 0.0) {
      console.assert(segmentsWithStartCoord.length > 0, section.guid)
    }

    // this is only used in the error text of the assertion below!
    const segmentPosDistances = section.segments.flatMap(segment =>
      segment.positions.map((pos => distance([startPos.x,startPos.y],[pos.x,pos.y]))))

    const anyBelowDistTolerance = segmentPosDistances.filter(dist => dist <= tolerance).length > 0

    console.assert(anyBelowDistTolerance, section.guid, 'distance:',Math.min(...segmentPosDistances))
  }

  /**
   * creates all markers for that track. A track may consist of multiple segments.
   * A Segment is a list of points.
   * One marker is placed for every segment with a length >= 0.000001.
   * So a track may yield less markers than it has segments
   * @param track subject
   * @returns direction markers for the given track
   */
  private createTrackDirectionArrowFeatures (track: Track): Feature<Geometry>[] {
    const markers: Feature<Geometry>[] = []

    for (const section of track.sections) {
      const sectionPolyLine = TrackDirectionFeature.sectionPolyLine(section)
      if (!sectionPolyLine) continue // can't draw that section -> skip

      // don't draw section's arrow head, when it is too short
      console.assert(TrackDirectionFeature.MIN_TRACK_LENGTH_TO_DISPLAY_FEATURE > 0.0)
      if (TrackDirectionFeature.polylineLength(sectionPolyLine) <
      TrackDirectionFeature.MIN_TRACK_LENGTH_TO_DISPLAY_FEATURE)
        continue

      // determine position and rotation of arrow head
      const middleOnEdge = TrackDirectionFeature.middlePositionOfPolyline(sectionPolyLine)
      if (!middleOnEdge) continue // invalid section: either len 0 or too short!

      markers.push(this.createFeature(middleOnEdge, track.guid))
    }

    return markers
  }

  private createFeature (position: Position, trackGuid: string): Feature<Geometry> {
    // define feature data
    const data : TrackDirectionArrowData = {
      guid: trackGuid,
      rotation: position.rotation
    }

    // define feature
    const geometry = new OlPoint([position.x,position.y])
    const feature = createFeature(
      FeatureType.TrackDirectionArrow,
      data,
      geometry,
      undefined // no label
    )

    // define style
    feature.setStyle((_, resolution) => {
      const baseResolution = this.map.getView().getResolutionForZoom(this.svgService.getBaseZoomLevel())
      const scale = baseResolution / resolution

      const arrowStyle = TrackDirectionFeature.drawArrow(geometry,position.rotation)
      arrowStyle.getImage()?.setScale(scale)
      return arrowStyle
    })
    return feature
  }

  private static polylineLength (points: Coordinate[]): number {
    if (points.length < 2) return 0.0

    let length = 0.0

    for (let i = 1; i < points.length; i++) {
      const p1 = points[i - 1]
      const p2 = points[i]
      length += distance([p1.x,p1.y],[p2.x,p2.y])
    }

    return length
  }

  /**
   * @returns middle coordinate and orientation(at that position) of @param polyLine.
   *  returns undefined, when: direction can't be determined (length of polyline is zero)
   *
   */
  private static middlePositionOfPolyline (polyLine: Coordinate[]): Position | undefined {
    if (polyLine.length < 2) return undefined

    const segmentpartsLength = []
    const cumulativeLength = [0.0]
    for (let i = 1; i < polyLine.length; i++) {
      const p1 = polyLine[i - 1]
      const p2 = polyLine[i]

      const lengthThisPart = distance([p1.x,p1.y],[p2.x,p2.y])
      segmentpartsLength.push(lengthThisPart)
      cumulativeLength.push(cumulativeLength.at(-1)! + lengthThisPart)
    }

    // length is zero => return undefined
    if (cumulativeLength.at(-1) == undefined ||
            cumulativeLength.at(-1)! === 0.0) {
      return undefined
    }

    const halfLength = cumulativeLength.at(-1)! / 2
    // len zero => last index = -1. In that case, 0 should be returned
    const lastPosBeforeMidle: number = cumulativeLength
      .findLastIndex(value => value < halfLength)!
    // is defined, cumlen[0] = 0, cumlen[-1] > length > 0

    const firstPosAfterMidle: number = cumulativeLength
      .findIndex(value => value >= halfLength)!

    console.assert(lastPosBeforeMidle < firstPosAfterMidle)

    const dir = normalizedDirection(
      polyLine[lastPosBeforeMidle],
      polyLine[firstPosAfterMidle]
    )

    // should be covered by (length != 0.0) - check
    console.assert(dir !== undefined)

    const remainingLength = halfLength - cumulativeLength[lastPosBeforeMidle]
    const lastBeforeMiddle = polyLine[lastPosBeforeMidle]
    const middle : Coordinate = {
      x: lastBeforeMiddle.x + dir!.x * remainingLength,
      y: lastBeforeMiddle.y + dir!.y * remainingLength
    }

    const angleRad = - Math.atan2(dir!.y, dir!.x)  // notice: y, x

    return {
      x: middle.x,
      y: middle.y,
      rotation: angleRad
    }
  }

  /**
   * from a given @param section TrackSection, produce a list of coordinates from section.
   * startCoordinate to end. For this, segments are flipped and ordered in a way where
   * adjacent segments have the same coordinates (with tolerance =
   * TRACK_SEGMENT_ORDERING_COORDINATE_MATCHING_DISTANCE) at the "toching side".
   *
   * @returns undefined when:
   *    - section.startCoordinate is null/undefined or
   *    - orderedSegmentsOfTrackSectionWithTolerance() returns undefined
   *    (see docs for explanation when that happens)
   */
  private static sectionPolyLine (section: TrackSection): Coordinate[] | undefined {
    const matchingDistance = TRACK_SEGMENT_ORDERING_COORDINATE_MATCHING_DISTANCE

    // startCoordinate might be undefined. In that case, don't draw the segment
    // (as we don't know where it starts!)
    if (!section.startCoordinate)
      return undefined

    // only run assertions in dev mode TODO remove
    if (Configuration.developmentMode()) {
      // TODO move these tolerances somewhere else!
      TrackDirectionFeature.assertStartPosOccursInPositions(section.startCoordinate, section,true, matchingDistance)
    }

    const orderedSegments = orderedSegmentsOfTrackSectionWithTolerance(section,matchingDistance)
    if (!orderedSegments)
      return undefined

    const positionList : Coordinate[] = []
    let lastPos: Coordinate | null = null

    for (const [segment,isFlipped] of orderedSegments!) {
      console.assert(segment != null, 'all segments must be not null') // TODO check this in unittest for section.orderedSegments()
      if (segment === null) continue

      const segmentPositions = [...segment.positions]
      if (isFlipped) segmentPositions.reverse()

      const segmentFirst = segmentPositions[0]
      const segmentLast = segmentPositions[segmentPositions.length - 1]

      console.assert(lastPos === null || coordinatesEqual(segmentFirst,lastPos))

      // push positions in inverse order.
      // First element is only pushed in very first segment!
      if (!lastPos) {
        positionList.push(segmentFirst)
      }

      positionList.push(...segmentPositions.slice(1))

      lastPos = segmentLast
    }

    return positionList
  }

  private static drawArrow (position: OlPoint , rotation: number) :Style { // add rotation
    const arrowSVG = SvgDraw.drawArrow(16, 32, 'black').content
    arrowSVG.setAttribute('stroke-width', '2')

    return new Style({
      geometry: position,
      image: new Icon({
        src: 'data:image/svg+xml;utf8,' + arrowSVG.outerHTML,
        rotateWithView: true,
        anchor: [0.5, 0],
        rotation: rotation + Math.PI / 2.0
      })
    })
  }
}

const EPSILON_DISTANCE_ZERO = 0.000001

function normalizedDirection (from:Coordinate, to:Coordinate): Coordinate | undefined {
  const len = distance([from.x,from.y],[to.x,to.y])
  if (len < EPSILON_DISTANCE_ZERO ) {
    return undefined
  }

  return { x:(to.x - from.x) / len,y:(to.y - from.y) / len }
}

function coordinatesEqual (a:Coordinate, b:Coordinate): boolean {
  return a.x === b.x && a.y === b.y
}
