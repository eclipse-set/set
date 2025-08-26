import LageplanFeature from '@/feature/LageplanFeature'
import { SiteplanState } from '@/model/SiteplanModel'
import { ISvgElement } from '@/model/SvgElement'
import Track from '@/model/Track'
import { distanceCoords, normalizedDirection } from '@/util/Math'
import SvgDraw from '@/util/SVG/Draw/SvgDraw'
import { Feature } from 'ol'
import { FeatureLike } from 'ol/Feature'
import Geometry from 'ol/geom/Geometry'
import OlPoint from 'ol/geom/Point'
import { default as Icon, default as OlIcon } from 'ol/style/Icon'
import { default as OlStyle, default as Style } from 'ol/style/Style'
import { createFeature, FeatureType, getFeatureData } from './FeatureInfo'

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

  static readonly ICON_PATH = 'SvgKatalog/GleisModellAusrichtung.svg'
  /**
   * styling for all TrackDirectionFeatures. It is dependent on the feature,
   * specifically the model.data value "rotation"
   * @param feature sty
   * @returns
   */
  static readonly TDF_FEATURE_STYLE = (feature: FeatureLike) => {
    // 'data:image/svg+xml;utf8,' + svg.content.outerHTML,

    return new OlStyle({
      image: new OlIcon({
        opacity: 1,
        src: TrackDirectionFeature.ICON_PATH,
        rotateWithView: true,
        scale: 0.4,
        rotation: getFeatureData(feature as Feature<Geometry>).rotation, // is this cast always possible?
        anchor: [0.5,0.5],
        anchorXUnits: 'fraction',
        anchorYUnits: 'fraction'

      })
    })
  }

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
      // find start and end
      const inReverse = !(section.segments[0].positions[0].x === section.startCoordinate.x && section.segments[0].positions[0].y === section.startCoordinate.y)

      // assume segments are all oriented in the same direction.
      // if not inReverse:
      //      A---x---y---z---B
      // segments: A-x, x-y, y-z, z-B
      // if in inReverse:
      //      A---x---y---z---B
      // segments: B-z, z-y, y-x, x-A
      // assert this assumption here:
      // assert.strictEqual('1=2',1,2)

      if (!inReverse) {
        const lastPos = { 'x':section.startCoordinate.x,'y':section.startCoordinate.y }

        for (const segment of section.segments) {
          const nextPos = segment.positions[0]
          console.assert(nextPos.x === lastPos.x,'segment wrong or flipped',nextPos.x,nextPos.y,lastPos.x,lastPos.y)
          console.assert(nextPos.y === lastPos.y,'segment wrong or flipped',nextPos.x,nextPos.y,lastPos.x,lastPos.y)
          lastPos.x = segment.positions[segment.positions.length - 1].x
          lastPos.y = segment.positions[segment.positions.length - 1].y
        }
      } else {
        const lastPos = { 'x':section.startCoordinate.x,'y':section.startCoordinate.y }
        // iterate backwards through section.segments
        for (let i = section.segments.length - 1; i >= 0; --i) {
          const segment = section.segments[i]

          const nextPos = segment.positions[segment.positions.length - 1]
          console.assert(nextPos.x === lastPos.x,'segment wrong or flipped',nextPos.x,nextPos.y,lastPos.x,lastPos.y)
          console.assert(nextPos.y === lastPos.y,'segment wrong or flipped',nextPos.x,nextPos.y,lastPos.x,lastPos.y)
          lastPos.x = segment.positions[0].x
          lastPos.y = segment.positions[0].y
        }
      }

      // var start = inReverse ? section.segments[section.segments.length-1] :
      for (const segment of section.segments) {
        const segmentparts_length = []
        const cumulative_length = [0.0]

        for (let i = 1; i < segment.positions.length; i++) {
          const length_this_part = distanceCoords(segment.positions[i - 1],segment.positions[i])
          segmentparts_length.push(length_this_part)
          cumulative_length.push(cumulative_length.at(-1)! + length_this_part)
        }

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

        const dir = normalizedDirection(
          segment.positions[last_pos_before_midle],
          segment.positions[last_pos_before_midle + 1]
        )

        if (dir == undefined) {
          // dir_vec = undefined <=> len of that part is zero.
          // In that case: no top-dir can sensibly be shown!
          continue
        }

        const remaining_length = half_length - cumulative_length[last_pos_before_midle]
        const middle = {
          x: segment.positions[last_pos_before_midle].x + dir.x * remaining_length,
          y: segment.positions[last_pos_before_midle].y + dir.y * remaining_length
        }

        const geometry = new OlPoint([
          middle.x,
          middle.y
        ])

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
