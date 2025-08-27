import LageplanFeature from '@/feature/LageplanFeature'
import { Coord } from '@/model/Position'
import { SiteplanState } from '@/model/SiteplanModel'
import { ISvgElement } from '@/model/SvgElement'
import Track from '@/model/Track'
import TrackSection, { orderedSegmentsOfTrackSection } from '@/model/TrackSection'
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
    const res = this.getObjectsModel(model).map(element => this.createTrackDirectionArrowFeatures(element))
      .flat()
    return res
  }

  protected getObjectsModel (model: SiteplanState): Track[] {
    return model.tracks
  }

  protected getObjectSvg (track: Track): ISvgElement {
    return this.svgService.getFeatureSvg(track, FeatureType.TrackDirectionArrow) // no data needed!
  }

  private assertStartPosOccursInPositions (startPos: Coord, section: TrackSection, withDist = true, tolerance = 0.0) {
    const segmentsWithStartCoord = section.segments
      .filter(seg => seg.positions.find(pos => startPos.exact_eq(pos)))

    if (!withDist && tolerance === 0.0) {
      console.assert(segmentsWithStartCoord.length > 0, section.guid)
    }

    // this is only used in the error text of the assertion below!
    const segmentPosDistances = []
    for (const segment of section.segments) {
      for (const pos of segment.positions) {
        segmentPosDistances.push(startPos.distanceTo(pos))
      }
    }
    // TODO why does this not do the same thing?
    // const segmentDistances =
    // const segmentPosDistances = section.segments.map(
    //   seg => seg.positions.map(
    //     pos => Math.sqrt((pos.x - startCoord.x) ** 2 + (pos.y, startCoord.y) ** 2)
    //   )
    // ).flat()

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
      // This seems to be incorrect:
      // const section = sectionI as TrackSectionC
      // instead do it this way:

      // startCoordinate might be undefined. In that case, don't draw the segment
      // (as we don't know where it starts!)
      if (!section.startCoordinate)
        continue

      const startCoord = Coord.fromCoordinate(section.startCoordinate)

      // TODO move these tolerances somewhere else!
      this.assertStartPosOccursInPositions(startCoord, section,true, 0.001)

      const orderedSegments = orderedSegmentsOfTrackSection(section)
      if (!orderedSegments)
        continue

      const positionList : Coord[] = []
      let lastPos: Coord | null = null

      for (const [segment,isFlipped] of orderedSegments!) {
        console.assert(segment != null, 'all segments must be not null') // TODO check this in unittest for section.orderedSegments()
        const segmentFirst = Coord.fromCoordinate(segment.positions[0])
        const segmentLast = Coord.fromCoordinate(segment.positions[segment.positions.length - 1])
        if (!isFlipped) {
          console.assert(lastPos === null || segmentFirst.exact_eq(lastPos))
          // push positions in correct order.
          // First element is only pushed in very first segment!
          if (!lastPos) {
            positionList.push(segmentFirst)
          }

          for (let i = 1; i < segment.positions.length; i++) {
            positionList.push(Coord.fromCoordinate(segment.positions[i]))
          }

          lastPos = segmentLast
        } else {
          console.assert(lastPos === null || segmentLast.exact_eq(lastPos))
          // push positions in inverse order.
          // First element is only pushed in very first segment!
          if (!lastPos) {
            positionList.push(segmentLast)
          }

          for (let i = segment.positions.length - 2; i >= 0 ; i--) {
            positionList.push(Coord.fromCoordinate(segment.positions[i]))
          }
          lastPos = segmentFirst
        }
      }

      for (let i = 1; i < positionList.length; i++) {
        const segmentparts_length = []
        const cumulative_length = [0.0]

        const length_this_part = Coord.distance(positionList[i - 1],positionList[i])
        segmentparts_length.push(length_this_part)
        cumulative_length.push(cumulative_length.at(-1)! + length_this_part)

        if (cumulative_length.at(-1) == undefined ||
            cumulative_length.at(-1)! < TrackDirectionFeature.MIN_TRACK_LENGTH_TO_DISPLAY_FEATURE) {
          // part too short to display!
          continue
        }

        const half_length = cumulative_length.at(-1)! / 2
        // len zero => last index = -1. In that case, 0 should be returned
        const last_pos_before_midle: number = cumulative_length
          .findLastIndex(value => value < half_length)!
        // is defined, cumlen[0] = 0, cumlen[-1] > length > 0

        const dir = Coord.normalizedDirection(
          positionList[last_pos_before_midle],
          positionList[last_pos_before_midle + 1]
        )

        if (dir == undefined) {
          // dir_vec = undefined <=> len of that part is zero.
          // In that case: no top-dir can sensibly be shown!
          continue
        }

        const remaining_length = half_length - cumulative_length[last_pos_before_midle]
        const middle : Coord =
          positionList[last_pos_before_midle]
            .plus(
              dir.scaledBy(remaining_length)
            )

        const geometry = middle.toOlPoint()

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
