/**
 * Copyright (c) 2023 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

import UnknownObject from '@/model/UnknownObject'
import { OtherSVGCatalog } from '../SvgEnum'
import AbstractSVGCatalog from './AbstractSVGCatalog'
import TrackDirectionFeature from '@/feature/TrackDirectionFeature'
import Feature, { FeatureLike } from 'ol/Feature'
import OlIcon from 'ol/style/Icon'
import OlStyle from 'ol/style/Style'
import { createFeature, FeatureType, getFeatureData } from '@/feature/FeatureInfo'
import Geometry from 'ol/geom/Geometry'

export default class OthersSVGCatalog extends AbstractSVGCatalog{
  getUnknownSvg (obj: UnknownObject) {
    console.log(obj.objectType)
    switch (obj.objectType) {
      case 'Hoehenpunkt':
      case 'Sonstiger_Punkt':
        return this.getSVGFromCatalog(obj.objectType)
      case 'Gleisausrichtungsmarkierung':
        return this.trackdirectionmarker()
      default:
        return null
    }
  }

  public getCantSvg () {
    return this.getSVGFromCatalog('ueberhoehungspunkt')
  }

  public catalogName (): string {
    return OtherSVGCatalog.Others
  }

  /**
     * styling for all TrackDirectionFeatures. It is dependent on the feature,
     * specifically the model.data value "rotation"
     */
  private trackdirectionmarker (): OlStyle {
    const ICON_PATH = 'SvgKatalog/GleisModellAusrichtung.svg'
    const TDF_FEATURE_STYLE = (feature: FeatureLike) => {
      return new OlStyle({
        image: new OlIcon({
          opacity: 1,
          src: ICON_PATH,
          rotateWithView: true,
          scale: 0.4,
          rotation: getFeatureData(feature as Feature<Geometry>).rotation, // is this cast always possible?
          anchor: [0.5,0.5],
          anchorXUnits: 'fraction',
          anchorYUnits: 'fraction'

        })
      })
    }
    return TDF_FEATURE_STYLE
  }
}
