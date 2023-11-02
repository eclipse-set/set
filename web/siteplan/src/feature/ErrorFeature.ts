/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

import LageplanFeature from '@/feature/LageplanFeature'
import ModelError from '@/model/Error'
import { SiteplanState } from '@/model/SiteplanModel'
import { Feature } from 'ol'
import Geometry from 'ol/geom/Geometry'
import OlPoint from 'ol/geom/Point'
import { Style, Text } from 'ol/style'
import { createFeature, FeatureType } from './FeatureInfo'

/**
 * Container for all Error features
 *
 * @author Stuecker
 */
export default class ErrorFeature extends LageplanFeature<ModelError> {
  protected getObjectsModel (model: SiteplanState): ModelError[] {
    return model.errors
  }

  getFeatures (model: SiteplanState): Feature<Geometry>[] {
    return this.getObjectsModel(model)
      .filter(x => x.position)
      .map(element => this.createError(element))
  }

  private createError (error: ModelError): Feature<Geometry> {
    if (!error.position) {
      throw new Error('Can only create errors with a position')
    }

    const feature = createFeature(
      FeatureType.Error,
      error,
      new OlPoint([error.position.x, error.position.y])
    )

    feature.setStyle((_, resolution) => {
      const baseResolution = this.map.getView().getResolutionForZoom(this.svgService.getBaseZoomLevel())
      const scale = baseResolution / resolution
      return new Style({
        text: new Text({
          text: '⚠',
          scale: scale * 2,
          offsetY: -12 * scale
        })
      })
    })
    return feature
  }
}
