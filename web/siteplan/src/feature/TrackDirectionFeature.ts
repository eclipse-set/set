
import LageplanFeature from '@/feature/LageplanFeature'
import { SignalPart } from '@/model/Signal'
import { SiteplanState } from '@/model/SiteplanModel'
import { ISvgElement } from '@/model/SvgElement'
import Track from '@/model/Track'
import { Feature } from 'ol'
import { getCenter } from 'ol/extent'
import Geometry from 'ol/geom/Geometry'
import OlPoint from 'ol/geom/Point'
import { createFeature, FeatureType } from './FeatureInfo'

/**
 *
 * @author Voigt
 */
export default class TrackDirectionFeature extends LageplanFeature<Track> {
  getFeatures (model: SiteplanState): Feature<Geometry>[] {
    return this.getObjectsModel(model).map(element => this.createTrackDirectionArrowFeature(element))
  }

  protected getObjectsModel (model: SiteplanState): Track[] {
    return model.tracks
  }

  protected getObjectSvg () {
    return this.svgService.getFeatureSvg({}, FeatureType.TrackDirectionArrow) // no data!
  }

  private createTrackDirectionArrowFeature (track: Track): Feature<Geometry> {
    const feature = createFeature(
      FeatureType.TrackDirectionArrow,
      {},
      new OlPoint([track.sections[0].segments[0].positions[0].x, track.sections[0].segments[0].positions[0].y]),
      undefined // no label
    )

    const style = new Style({
      geometry: geometry ?? undefined,
      fill: this.getFill() ? this.getFill().clone() : undefined,
      image: this.getImage() ? this.getImage().clone() : undefined,
      renderer: this.getRenderer() ?? undefined,
      stroke: this.getStroke() ? this.getStroke().clone() : undefined,
      text: this.getText() ? this.getText().clone() : undefined,
      zIndex: this.getZIndex(),
    }

    const svg = this.getObjectSvg()

    this.createArrowBBox(feature, track, svg)
    feature.setStyle((_, resolution) => {
      const baseResolution = this.map.getView().getResolutionForZoom(this.svgService.getBaseZoomLevel())
      const scale = baseResolution / resolution

      // Rotate the signal labels according to the map rotation
      // signalMount.attachedSignals.forEach(mount =>
      //  updateLabelOrientation(mount.label, signalMount.position.rotation, this.map))

      // Determine style for the signal mount

      // Rescale the feature according to the current zoom level
      // to keep a constant size
      style.getImage()?.setScale(scale)
      // Rotate the feature
      style.getImage()?.setRotation(((0.0) * Math.PI) / 180) // TODO
      return style
    })
    return feature
  }

  private createArrowBBox (feature: Feature<Geometry>, track: Track, svg: ISvgElement) {
    if (!svg.nullpunkt) {
      svg.boundingBox.forEach(bbox => (
        LageplanFeature.createBBox( // TODO
          feature,
          {
            x: track.sections[0].segments[0].positions[0].x,
            y: track.sections[0].segments[0].positions[0].y,
            rotation: 0.0
          },
          bbox,
          [0, 0],
          [1, 1]
        )
      ))
    } else {
      for (const bbox of svg.boundingBox) {
        const translate = [
          getCenter(bbox)[0] - svg.nullpunkt.x,
          svg.nullpunkt.y - getCenter(bbox)[1]
        ]
        LageplanFeature.createBBox(feature, {
          x: track.sections[0].segments[0].positions[0].x, // TODO
          y: track.sections[0].segments[0].positions[0].y,
          rotation: 0.0
        }, bbox, translate, [1, 1])
      }
    }
  }

  setFeatureColor (feature: Feature<Geometry>, color?: number[], partID?: string): Feature<Geometry> {
    if (!color && !partID) {
      this.resetFeatureColor(feature)
    } else if (partID) {
      super.setFeatureColor(feature, color, partID)
    } else {
      Object.values(SignalPart).forEach(part => {
        this.setFeatureRegionColor(feature, part, color)
      })
    }

    return feature
  }

  /* protected setFeatureRegionColor (
    feature: Feature<Geometry>,
    objectPart?: string | undefined,
    color?: number[]
  ): Feature<Geometry> {
    const signalModel = getFeatureData(feature) as Track
    // IMPROVE: Because at the moment Signal Additive doesn't have GUID,
    // you can't set region color for this.

    this.setObjectColor(signalModel, objectPart, color ?? this.getRegionColor(feature))

    return feature
  } */

  compareChangedState (initial: SiteplanState, final: SiteplanState): Feature<Geometry>[] {
    return super.compareChangedState(initial, final, [], mount => this.getObjectSvg(), Object.values(SignalPart))
  }
}
