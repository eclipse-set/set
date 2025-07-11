import LageplanFeature from '@/feature/LageplanFeature'
import { Coordinate } from '@/model/Position'
import { SiteplanState } from '@/model/SiteplanModel'
import Track from '@/model/Track'
import { Feature } from 'ol'
import Geometry from 'ol/geom/Geometry'
import OlPoint from 'ol/geom/Point'
import OlIcon from 'ol/style/Icon'
import OlStyle from 'ol/style/Style'
import { createFeature, FeatureType } from './FeatureInfo'

/**
 * A small arrow head, at every segment of every section,
 * pointing in the direction of that segment.
 * It is placed at 2/3 of the length of every segment (in the direction of that segment).
 * It does remain the same size, no matter the zoom (unlike most other features).
 * @author Voigt
 */
export default class TrackDirectionFeature extends LageplanFeature<Track> {
  /**
   * styling for all TrackDirectionFeatures. It is dependent on the feature,
   * specifically the model.data value "rotation"
   * @param feature sty
   * @returns
   */
  static readonly TDF_FEATURE_STYLE = feature => {
    const rotation = feature.values_.data.model.data

    const style = new OlStyle({
      image: new OlIcon({
        opacity: 1,
        src: 'SvgKatalog/GleisModellAusrichtung.svg',
        rotateWithView: true,
        scale: 0.4,
        rotation,
        anchor: [0.5,0.5],
        anchorXUnits: 'fraction',
        anchorYUnits: 'fraction'

      })
    })
    return style
  }

  getFeatures (model: SiteplanState): Feature<Geometry>[] {
    const res = this.getObjectsModel(model).map(element => this.createTrackDirectionArrowFeatures(element))
      .flat()
    return res
  }

  protected getObjectsModel (model: SiteplanState): Track[] {
    return model.tracks
  }

  protected getObjectSvg (track: Track) {
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

    function distance_coord (a: Coordinate, b: Coordinate): number {
      return Math.sqrt((a.x - b.x) ** 2 + (a.y - b.y) ** 2)
    }

    function normalized_direction (a: Coordinate, b: Coordinate): Coordinate | undefined {
      const len = distance_coord(a,b)
      if (len <= 0.000001) {
        return undefined
      }

      return {
        x: (b.x - a.x) / len,
        y: (b.y - a.y) / len
      }
    }

    for (const section of track.sections) {
      for (const segment of section.segments) {
        const segmentparts_length = []
        const cumulative_length = [0.0]

        for (let i = 1; i < segment.positions.length; i++) {
          const length_this_part = distance_coord(segment.positions[i - 1],segment.positions[i])
          segmentparts_length.push(length_this_part)
          cumulative_length.push(cumulative_length.at(-1)! + length_this_part)
        }

        if (cumulative_length.at(-1) == undefined || cumulative_length.at(-1)! < 0.000001) {
          // part too short to display!
          continue
        }

        const half_length = cumulative_length.at(-1)! * 0.66 // place at 2/3rds
        // len zero => last index = -1. In that case, 0 should be returned
        const last_pos_before_midle: number = cumulative_length
          .findLastIndex(value => value < half_length)!
        // is defined, cumlen[0] = 0, cumlen[-1] > length > 0

        const dir = normalized_direction(
          segment.positions[last_pos_before_midle],
          segment.positions[last_pos_before_midle + 1]
        )

        if (dir != undefined) {
          const remaining_length = half_length - cumulative_length[last_pos_before_midle]
          const middle = {
            x: segment.positions[last_pos_before_midle].x + dir.x * remaining_length,
            y: segment.positions[last_pos_before_midle].y + dir.y * remaining_length
          }

          // markers.push([middle, direction_vector])

          const geometry = new OlPoint([
            middle.x,
            middle.y
          ])

          const angleRad = - Math.atan2(dir.y, dir.x)  // y, x

          const feature = createFeature(
            FeatureType.TrackDirectionArrow,
            {
              guid: track.guid,
              data: angleRad
            },
            geometry,
            undefined // no label
          )
          feature.setStyle(TrackDirectionFeature.TDF_FEATURE_STYLE)
          markers.push(feature)
        } else {
          // dir_vec = undefined <=> len of that part is zero.
          // In that case: no top-dir can sensibly be shown!
        }
      }
    }
    return markers
  }

  compareChangedState (initial: SiteplanState, final: SiteplanState): Feature<Geometry>[] {
    return super.compareChangedState(initial, final, [], mount => this.getObjectSvg(mount))
  }
}
