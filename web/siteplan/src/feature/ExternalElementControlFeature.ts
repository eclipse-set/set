/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

import { ExternalElementControl } from '@/model/ExternalElementControl'
import { Label } from '@/model/Label'
import { SiteplanState } from '@/model/SiteplanModel'
import { getLabelColor } from '@/model/SiteplanObject'
import { ISvgElement } from '@/model/SvgElement'
import { updateLabelColor, updateLabelOrientation } from '@/util/ModelExtensions'
import { Feature } from 'ol'
import { Geometry } from 'ol/geom'
import OlPoint from 'ol/geom/Point'
import { createFeature, FeatureType } from './FeatureInfo'
import LageplanFeature from './LageplanFeature'

/**
 * Contain all external element control features
 */
export default class ExternalElementControlFeature extends LageplanFeature<ExternalElementControl> {
  protected getObjectsModel (model: SiteplanState): ExternalElementControl[] {
    return model.externalElementControls
  }

  protected getObjectSvg (eec: ExternalElementControl, label?: Label | null): ISvgElement {
    return this.svgService.getFeatureSvg(
      eec,
      FeatureType.ExternalElementControl,
      label ?? undefined
    )
  }

  getFeatures (model: SiteplanState): Feature<Geometry>[] {
    return this.getObjectsModel(model)
      .map(ele => this.createExternalElementControlFeature(ele))
  }

  private createExternalElementControlFeature (eec: ExternalElementControl): Feature<Geometry> {
    const feature = createFeature(
      FeatureType.ExternalElementControl,
      eec,
      new OlPoint([eec.position.x, eec.position.y]),
      eec.label?.text
    )
    const svg = this.getObjectSvg(eec, eec.label)
    this.createBBox(feature, eec, svg)
    feature.setStyle((_, resolution) => {
      const baseResolution = this.map.getView().getResolutionForZoom(this.svgService.getBaseZoomLevel())
      const scale = baseResolution / resolution

      updateLabelOrientation(eec.label, eec.position.rotation, this.map)
      updateLabelColor(eec.label, getLabelColor(eec))
      const style = this.svgService.getFeatureStyle(eec, FeatureType.ExternalElementControl, eec.label)
      style.getImage().setScale(scale)
      style.getImage().setRotation((eec.position.rotation * Math.PI) / 180)
      return style
    })
    return feature
  }

  private createBBox (
    feature: Feature<Geometry>,
    eec: ExternalElementControl,
    svg: ISvgElement
  ) {
    svg.boundingBox.forEach(bbox => {
      LageplanFeature.createBBox(feature, eec.position, bbox, [0, 0], [1, 1])
    })
  }

  compareChangedState (initial: SiteplanState, final: SiteplanState): Feature<Geometry>[] {
    return super.compareChangedState(initial, final, [], ecc => this.getObjectSvg(ecc))
  }
}
