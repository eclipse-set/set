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
 *
 * @author Voigt
 */
export default class TrackDirectionFeature extends LageplanFeature<Track> {
  static readonly TDF_FEATURE = feature => {
    const rotation = feature.values_.data.model.data

    const style = new OlStyle({
      image: new OlIcon({
        opacity: 1,
        // src: 'https://openlayers.org/en/latest/examples/data/icon.png',
        src: 'SvgKatalog/GleisModellAusrichtung.svg',
        rotateWithView: true,
        scale: 0.4,
        rotation,
        anchor: [0.5,0.5],
        anchorXUnits: 'fraction',
        anchorYUnits: 'fraction'

      })
      // geometry: geometry

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
    return this.svgService.getFeatureSvg(track, FeatureType.TrackDirectionArrow) // no data!
  }

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
          // console.log(length_this_part)
          segmentparts_length.push(length_this_part)
          cumulative_length.push(cumulative_length.at(-1)! + length_this_part)
        }

        if (cumulative_length.at(-1) == undefined || cumulative_length.at(-1)! < 0.00001) {
          // part too short to display!
          continue
        }

        const half_length = cumulative_length.at(-1)! * 0.66 // place at 2/3rds
        // len zero => last index = -1. In that case, 0 should be returned
        // const last_pos_before_midle: number =
        // Math.max(0,cumulative_length.findLastIndex(value => value < half_length)!)
        const last_pos_before_midle: number = cumulative_length
          .findLastIndex(value => value < half_length)!

        const direction_vector = normalized_direction(
          segment.positions[last_pos_before_midle],
          segment.positions[last_pos_before_midle + 1]
        )

        if (direction_vector != undefined) {
          const remaining_length = half_length - cumulative_length[last_pos_before_midle]
          const middle = {
            x: segment.positions[last_pos_before_midle].x + direction_vector.x * remaining_length,
            y: segment.positions[last_pos_before_midle].y + direction_vector.y * remaining_length
          }

          // markers.push([middle, direction_vector])

          const geometry = new OlPoint([
            middle.x,
            middle.y
          ])

          const angleRad = - Math.atan2(direction_vector.y, direction_vector.x)  // y, x
          const angleDeg = angleRad * (180 / Math.PI)

          const feature = createFeature(
            FeatureType.TrackDirectionArrow,
            {
              guid: 'TestGuid',
              data: angleRad
            },
            geometry,
            undefined // no label
          )
          feature.setStyle(TrackDirectionFeature.TDF_FEATURE)

          markers.push(feature)
        } else {
          // dir_vec = undefined <=> len of that part is zero.
          // In that case: no top-dir can sensibly be shown!
        }
      }
    }

    // this.createArrowBBox(feature, track, svg)

    return markers
  }

  compareChangedState (initial: SiteplanState, final: SiteplanState): Feature<Geometry>[] {
    return super.compareChangedState(initial, final, [], mount => this.getObjectSvg(mount))
  }
}
