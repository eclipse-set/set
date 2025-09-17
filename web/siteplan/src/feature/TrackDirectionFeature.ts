import LageplanFeature from '@/feature/LageplanFeature'
import { Coordinate } from '@/model/Position'
import { SiteplanState } from '@/model/SiteplanModel'
import Track from '@/model/Track'
import TrackSection, { orderedSegmentsOfTrackSectionWithTolerance } from '@/model/TrackSection'
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
      // startCoordinate might be undefined. In that case, don't draw the segment
      // (as we don't know where it starts!)
      if (!section.startCoordinate)
        continue

      // only run assertions in dev mode
      if (Configuration.developmentMode()) {
        // TODO move these tolerances somewhere else!
        TrackDirectionFeature.assertStartPosOccursInPositions(section.startCoordinate, section,true, 0.001)
      }

      const orderedSegments = orderedSegmentsOfTrackSectionWithTolerance(section,0.001)
      if (!orderedSegments)
        continue

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

      for (let i = 1; i < positionList.length; i++) {
        const segmentpartsLength = []
        const cumulativeLength = [0.0]

        const p1 = positionList[i - 1]
        const p2 = positionList[i]

        const lengthThisPart = distance([p1.x,p1.y],[p2.x,p2.y])
        segmentpartsLength.push(lengthThisPart)
        cumulativeLength.push(cumulativeLength.at(-1)! + lengthThisPart)

        if (cumulativeLength.at(-1) == undefined ||
            cumulativeLength.at(-1)! < TrackDirectionFeature.MIN_TRACK_LENGTH_TO_DISPLAY_FEATURE) {
          // part too short to display!
          continue
        }

        const halfLength = cumulativeLength.at(-1)! / 2
        // len zero => last index = -1. In that case, 0 should be returned
        const lastPosBeforeMidle: number = cumulativeLength
          .findLastIndex(value => value < halfLength)!
        // is defined, cumlen[0] = 0, cumlen[-1] > length > 0

        const dir = normalizedDirection(
          positionList[lastPosBeforeMidle],
          positionList[lastPosBeforeMidle + 1]
        )

        if (dir == undefined) {
          // dir_vec = undefined <=> len of that part is zero.
          // In that case: no top-dir can sensibly be shown!
          continue
        }

        const remainingLength = halfLength - cumulativeLength[lastPosBeforeMidle]
        const lastBeforeMiddle = positionList[lastPosBeforeMidle]
        const middle : Coordinate = {
          x: lastBeforeMiddle.x + dir.x * remainingLength,
          y: lastBeforeMiddle.y + dir.y * remainingLength
        }

        const geometry = new OlPoint([middle.x,middle.y])

        const angleRad = - Math.atan2(dir.y, dir.x)  // y, x

        const data : TrackDirectionArrowData = {
          guid: track.guid,
          rotation: angleRad
        }

        const arrowStyle = this.drawArrow(geometry,angleRad)

        const feature = createFeature(
          FeatureType.TrackDirectionArrow,
          data,
          geometry,
          undefined // no label
        )
        feature.setStyle((_, resolution) => {
          const baseResolution = this.map.getView().getResolutionForZoom(this.svgService.getBaseZoomLevel())
          const scale = baseResolution / resolution

          arrowStyle.getImage()?.setScale(scale)
          return arrowStyle
        })
        markers.push(feature)
      }
    }
    return markers
  }

  private drawArrow (position: OlPoint , rotation: number) :Style { // add rotation
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
