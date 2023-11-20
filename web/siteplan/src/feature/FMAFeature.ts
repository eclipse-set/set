/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

import LageplanFeature from '@/feature/LageplanFeature'
import { FMAComponent } from '@/model/FMAComponent'

import { SiteplanState } from '@/model/SiteplanModel'
import { getColor } from '@/model/SiteplanObject'
import { ISvgElement } from '@/model/SvgElement'
import { updateLabelColor, updateLabelOrientation } from '@/util/ModelExtensions'
import SvgDrawFMAComponent from '@/util/SVG/Draw/SvgDrawFMAComponent'
import { Feature } from 'ol'
import Geometry from 'ol/geom/Geometry'
import OlPoint from 'ol/geom/Point'
import { createFeature, FeatureType } from './FeatureInfo'

/**
 * Container for all FMA Component features
 *
 * @author Stuecker
 */
export default class FMAFeature extends LageplanFeature<FMAComponent> {
  protected getObjectsModel (model: SiteplanState): FMAComponent[] {
    return model.fmaComponents
  }

  protected getObjectSvg (model: FMAComponent): ISvgElement {
    return this.svgService.getFeatureSvg(model, FeatureType.FMA, model.label)
  }

  getFeatures (model: SiteplanState): Feature<Geometry>[] {
    return this.getObjectsModel(model).map(element => this.createFMAComponentFeature(element))
  }

  private createFMAComponentFeature (fma: FMAComponent): Feature<Geometry> {
    const feature = createFeature(
      FeatureType.FMA,
      fma,
      new OlPoint([fma.position.x, fma.position.y]),
      fma.label?.text
    )

    const svg = this.getObjectSvg(fma)
    this.createFMABBox(feature, fma, svg)
    feature.setStyle((_, resolution) => {
      const baseResolution = this.map.getView().getResolutionForZoom(this.svgService.getBaseZoomLevel())
      const scale = baseResolution / resolution

      // Rotate the fma label according to the map rotation
      updateLabelOrientation(fma.label, fma.position.rotation, this.map)
      updateLabelColor(fma.label, getColor(fma))

      // Determine style for the FMAComponent
      const style = this.svgService.getFeatureStyle(fma, FeatureType.FMA, fma.label)

      // Rescale the feature according to the current zoom level
      // to keep a constant size
      style
        .getImage()
        .setScale(scale)

      // Rotate the feature
      style.getImage().setRotation((fma.position.rotation * Math.PI) / 180)

      return style
    })
    return feature
  }

  private createFMABBox (feature: Feature<Geometry>, fma: FMAComponent, fmaSVG: ISvgElement) {
    const translate = fma.rightSide
      ? [SvgDrawFMAComponent.SVG_FMA_X_OFFSET, 0]
      : [-SvgDrawFMAComponent.SVG_FMA_X_OFFSET, 0]
    fmaSVG.boundingBox.forEach(bbox => {
      LageplanFeature.createBBox(feature, fma.position, bbox, translate, [1, 1])
    })
  }

  compareChangedState (initial: SiteplanState, final: SiteplanState): Feature<Geometry>[] {
    return super.compareChangedState(initial, final, [], fma => this.getObjectSvg(fma))
  }
}
