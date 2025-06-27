
import LageplanFeature from '@/feature/LageplanFeature'
import { SiteplanState } from '@/model/SiteplanModel'
import { ISvgElement } from '@/model/SvgElement'
import Track from '@/model/Track'
import SvgDrawTrackDirectionArrow from '@/util/SVG/Draw/SvgDrawTrackDirectionArrow'
import { Feature } from 'ol'
import { getCenter } from 'ol/extent'
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
  static readonly TDF_FEATURE = (feature,resolution) => {
    // const baseResolution = this.map.getView().getResolutionForZoom(this.svgService.getBaseZoomLevel())
    const baseResolution = 0.1 // TODO????
    const scale = baseResolution / resolution

    const style = new OlStyle({
      image: new OlIcon({
        opacity: 1,
        // src: 'https://openlayers.org/en/latest/examples/data/icon.png',
        src: 'SvgKatalog/GleisTopologischeAusrichtung.svg',
        rotateWithView: true,
        scale
        /* anchor: [0.5,0.5],
        anchorXUnits: 'fraction',
        anchorYUnits: 'fraction' */

      })
      // geometry: geometry

    })
    // console.log('tdf icon size: ',style.getImage.getSize())
    style.getImage()?.setRotation(((0.0) * Math.PI) / 180) // TODO
    return style
  }

  getFeatures (model: SiteplanState): Feature<Geometry>[] {
    const res = this.getObjectsModel(model).map(element => this.createTrackDirectionArrowFeature(element))
    return res
  }

  protected getObjectsModel (model: SiteplanState): Track[] {
    return model.tracks
  }

  protected getObjectSvg (track: Track) {
    return this.svgService.getFeatureSvg(track, FeatureType.TrackDirectionArrow) // no data!
  }

  private createTrackDirectionArrowFeature (track: Track): Feature<Geometry> {
    const geometry = new OlPoint([
      track.sections[0].segments[0].positions[0].x,
      track.sections[0].segments[0].positions[0].y
    ])

    const drawer = new SvgDrawTrackDirectionArrow(this.svgService.getCatalogService())
    // const svg = drawer.drawSVG(track, undefined)

    const svg = `<svg xmlns="http://www.w3.org/2000/svg" version="1.1" xmlns:xlink="http://www.w3.org/1999/xlink"
    xmlns:toolbox="tag:scheidt-bachmann-st.com,2021:hs" viewBox="0 0 100 150" stroke-width="2.5" stroke="black">
    <defs>
        <g class="BUE" id="Bue0">
            <rect width="30" height="55" y="12.5" fill="white" />
            <circle r="9.5" cx="15" cy="29" />
            <circle r="9.5" cx="15" cy="52" fill="white" />
            <toolbox:anchor id="mastAnkerTop" x="15" y="12.5" />
            <toolbox:anchor id="mastAnkerBottom" x="15" y="65" />
            <toolbox:rectbound x="0" y="12.5" width="30" height="55" />
        </g>
        <g class="BUE" id="Bue2">
            <rect width="20" height="88" y="12.5" rx="1" />
            <rect fill="white" x="18" y="4" width="12" height="12" stroke="white" transform="rotate(45)" />
            <rect fill="white" x="33" y="19" width="12" height="12" stroke="white" transform="rotate(45)" />
            <rect fill="white" x="48" y="34" width="12" height="12" stroke="white" transform="rotate(45)" />
            <rect fill="white" x="63" y="49" width="12" height="12" stroke="white" transform="rotate(45)" />
            <toolbox:anchor id="mastAnkerTop" x="10" y="12.5" />
            <toolbox:anchor id="mastAnkerBottom" x="10" y="100" />
            <toolbox:rectbound x="0" y="12.5" width="20" height="88" />
        </g>
        <g class="BUE" id="Bue21R">
            <rect width="20" height="88" y="12.5" rx="1" />
            <rect fill="white" x="40" y="26" width="12" height="12" stroke="white" transform="rotate(45)" />
            <toolbox:anchor id="mastAnkerTop" x="10" y="12.5" />
            <toolbox:anchor id="mastAnkerBottom" x="10" y="100" />
            <toolbox:rectbound x="0" y="12.5" width="20" height="88" />
        </g>
        <g class="BUE" id="Bue22R">
            <rect width="20" height="88" y="12.5" rx="1" />
            <rect fill="white" x="31" y="17" width="12" height="12" stroke="white" transform="rotate(45)" />
            <rect fill="white" x="50" y="36" width="12" height="12" stroke="white" transform="rotate(45)" />
            <toolbox:anchor id="mastAnkerTop" x="10" y="12.5" />
            <toolbox:anchor id="mastAnkerBottom" x="10" y="100" />
            <toolbox:rectbound x="0" y="12.5" width="20" height="88" />
        </g>
        <g class="BUE" id="Bue23R">
            <rect width="20" height="88" y="12.5" rx="1" />
            <rect fill="white" x="25" y="11" width="12" height="12" stroke="white" transform="rotate(45)" />
            <rect fill="white" x="42" y="28" width="12" height="12" stroke="white" transform="rotate(45)" />
            <rect fill="white" x="59" y="45" width="12" height="12" stroke="white" transform="rotate(45)" />
            <toolbox:anchor id="mastAnkerTop" x="10" y="12.5" />
            <toolbox:anchor id="mastAnkerBottom" x="10" y="100" />
            <toolbox:rectbound x="0" y="12.5" width="20" height="88" />
        </g>
        <g class="BUE" id="So15">
            <rect fill="white" width="14" height="98" y="0" />
            <rect fill="black" x="0" y="14" width="14" height="14" />
            <rect fill="black" x="0" y="42" width="14" height="14" />
            <rect fill="black" x="0" y="70" width="14" height="14" />
            <toolbox:anchor id="mastAnkerTop" x="7" y="0" />
            <toolbox:anchor id="mastAnkerBottom" x="7" y="98" />
            <toolbox:rectbound x="0" y="0" width="14" height="98" />
        </g>
        <g class="BUE" id="So14">
            <rect fill="white" width="14" height="98" y="0" />
            <rect fill="black" x="0" y="14" width="14" height="14" />
            <rect fill="black" x="0" y="42" width="14" height="14" />
            <rect fill="black" x="0" y="70" width="14" height="14" />
            <rect fill="black" x="-5" y="97" width="24" height="1" />
            <toolbox:anchor id="mastAnkerBottom" x="7" y="98" />
            <toolbox:rectbound x="0" y="0" width="14" height="98" />
            <toolbox:rectbound x="-5" y="97" width="24" height="1" />
        </g>
        <g class="BUE" id="BueKT">
            <rect width="25" height="35" fill="white" />
            <text font-size="30" stroke-width="0" x="12.5" y="28" font-family="siteplanfont" text-anchor="middle">
                K
            </text>
            <toolbox:anchor id="mastAnkerTop" x="12.5" y="0" />
            <toolbox:anchor id="mastAnkerBottom" x="12.5" y="35" />
            <toolbox:rectbound x="0" y="0" width="25" height="35" />
        </g>
        <g class="BUE" id="BueAT">
            <rect width="25" height="35" fill="white" />
            <text font-size="30" stroke-width="0" x="12.5" y="28" font-family="siteplanfont" text-anchor="middle">
                A
            </text>
            <text id="km" font-size="15" stroke-width="0" x="28" y="40" font-family="siteplanfont" />
            <toolbox:anchor id="mastAnkerTop" x="12.5" y="0" />
            <toolbox:anchor id="mastAnkerBottom" x="12.5" y="35" />
            <toolbox:rectbound x="0" y="0" width="25" height="35" />
        </g>
        <g class="BUE" id="BueATWdh">
            <rect width="25" height="35" fill="white" />
            <text font-size="30" stroke-width="0" x="12.5" y="28" font-family="siteplanfont" text-anchor="middle">
                A
            </text>
            <text id="km" font-size="15" stroke-width="0" x="28" y="40" font-family="siteplanfont" />
            <toolbox:anchor id="mastAnkerTop" x="12.5" y="0" />
            <toolbox:anchor id="mastAnkerBottom" x="12.5" y="35" />
            <toolbox:rectbound x="0" y="0" width="25" height="35" />
        </g>
        <g class="BUE" id="BueATZusatz">
            <rect width="25" height="35" fill="white" />
            <text font-size="30" stroke-width="0" x="12.5" y="28" font-family="siteplanfont" text-anchor="middle">
                B
            </text>
            <text font-size="13" stroke-width="0" x="30" y="-20" font-family="siteplanfont">
                Bü
            </text>
            <text font-size="13" stroke-width="0" x="30" y="-30" font-family="siteplanfont">
                Bü
            </text>
            <line x1="25" x2="34" y1="0" y2="-18" stroke-width="1.5" />
            <toolbox:anchor id="mastAnkerTop" x="12.5" y="0" />
            <toolbox:anchor id="mastAnkerBottom" x="12.5" y="35" />
            <toolbox:rectbound x="-1.25" y="-1.25" width="27.5" height="37.5" />
            <toolbox:rectbound x="27" y="-38" width="15.5" height="37.5" />
            <toolbox:rectbound x="0" y="0" width="25" height="35" />
            <toolbox:rectbound x="27.5" y="-38" width="17" height="35.5" />
        </g>
    </defs>
    <g width="100" height="100" transform="translate(30,50)">
        <use xlink:href="#BueATZusatz" />
    </g>
</svg>`

    // icon.setDisplacement([track.sections[0].segments[0].positions[0].x, track.sections[0].segments[0].positions[0].y])

    const feature = createFeature(
      FeatureType.TrackDirectionArrow,
      {
        guid: 'TestGuid',
        data: track
      },
      geometry,
      undefined // no label
    )

    // this.createArrowBBox(feature, track, svg)

    feature.setStyle(TrackDirectionFeature.TDF_FEATURE)

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

  /* setFeatureColor (feature: Feature<Geometry>, color?: number[], partID?: string): Feature<Geometry> {
    // TODO allow new colors!
    return feature
  } */

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
    return super.compareChangedState(initial, final, [], mount => this.getObjectSvg(mount))
  }
}
