/**
 * Copyright (c) 2023 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

import LageplanFeature from '@/feature/LageplanFeature'

import { SiteplanState } from '@/model/SiteplanModel'
import { ISvgElement } from '@/model/SvgElement'
import { Feature } from 'ol'
import Geometry from 'ol/geom/Geometry'
import OlPoint from 'ol/geom/Point'
import { createFeature, FeatureType } from './FeatureInfo'
import UnknownObject from '@/model/UnknownObject'

/**
 * Container for all Cant features
 */
export default class UnknownObjectFeature extends LageplanFeature<UnknownObject> {
  protected getObjectsModel (model: SiteplanState): UnknownObject[] {
    return model.unknownObjects
  }

  protected getObjectSvg (model: UnknownObject): ISvgElement {
    return this.svgService.getFeatureSvg(model, FeatureType.Unknown)
  }

  getFeatures (model: SiteplanState): Feature<Geometry>[] {
    return this.getObjectsModel(model).flatMap(element => this.createUnknownFeature(element))
  }

  private createUnknownFeature (object: UnknownObject): Feature<Geometry> {
    const feature = createFeature(
      FeatureType.Unknown,
      object,
      new OlPoint([object.position.x, object.position.y]),
      object.objectType
    )

    feature.setStyle((_, resolution) => {
      const baseResolution = this.map.getView().getResolutionForZoom(this.svgService.getBaseZoomLevel())
      const scale = baseResolution / resolution

      // Determine style for the FMAComponent
      const style = this.svgService.getFeatureStyle(object, FeatureType.Unknown)

      // Rescale the feature according to the current zoom level
      // to keep a constant size
      style
        .getImage()
        ?.setScale(scale)

      // Rotate the feature
      style.getImage()?.setRotation((object.position.rotation * Math.PI) / 180)

      return style
    })
    return feature
  }

  compareChangedState (initial: SiteplanState, final: SiteplanState): Feature<Geometry>[] {
    return super.compareChangedState(initial, final, [], unknowObject => this.getObjectSvg(unknowObject))
  }
}
