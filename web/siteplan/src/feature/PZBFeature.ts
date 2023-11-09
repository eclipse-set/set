/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

import LageplanFeature from '@/feature/LageplanFeature'
import { getPZBLabel, PZB } from '@/model/PZB'
import { SiteplanState } from '@/model/SiteplanModel'
import { getColor } from '@/model/SiteplanObject'
import { ISvgElement } from '@/model/SvgElement'
import { updateLabelColor, updateLabelOrientation } from '@/util/ModelExtensions'
import { Feature } from 'ol'
import { getWidth } from 'ol/extent'
import Geometry from 'ol/geom/Geometry'
import Point from 'ol/geom/Point'
import { createFeature, FeatureType } from './FeatureInfo'

/**
 * Container for all track switch features
 *
 * @author Stuecker
 */
export default class PZBFeature extends LageplanFeature<PZB> {
  getFeatures (model: SiteplanState): Feature<Geometry>[] {
    return model.pzb.map(element => this.createPZBComponentFeature(element))
  }

  protected getObjectSvg (model: PZB): ISvgElement {
    return this.svgService.getFeatureSvg(model, FeatureType.PZB, getPZBLabel(model))
  }

  protected getObjectsModel (model: SiteplanState): PZB[] {
    return model.pzb
  }

  createPZBComponentFeature (pzb: PZB): Feature<Geometry> {
    const feature = createFeature(
      FeatureType.PZB,
      pzb,
      new Point([pzb.position.x, pzb.position.y])
    )

    // If the pzb is positioned on the left side, flip it to ensure a correct orientation
    // along the track
    let rotation = pzb.position.rotation
    if (!pzb.rightSide) {
      rotation += 180
    }

    const label = getPZBLabel(pzb)
    const svg = this.getObjectSvg(pzb)
    this.createPZBBBox(feature, pzb, svg)
    feature.setStyle((_, resolution) => {
      const baseResolution = this.map.getView().getResolutionForZoom(this.svgService.getBaseZoomLevel())
      const scale = baseResolution / resolution
      // Determine style for the PZB
      updateLabelOrientation(label, rotation, this.map)
      updateLabelColor(label, getColor(pzb))
      const style = this.svgService.getFeatureStyle(pzb, FeatureType.PZB, label)

      // Rescale the feature according to the current zoom level to keep a constant size
      style
        .getImage()
        .setScale(scale)

      // Rotate the feature
      style.getImage().setRotation((rotation * Math.PI) / 180)

      return style
    })
    return feature
  }

  private createPZBBBox (feature: Feature<Geometry>, pzb: PZB, pzbSVG: ISvgElement): void {
    const offset = 1.25
    for (const bbox of pzbSVG.boundingBox) {
      const translate = [getWidth(bbox) / 2 - offset, 0]
      let rotation = pzb.position.rotation
      if (!pzb.rightSide) {
        rotation += 180
      }

      const position = {
        x: pzb.position.x,
        y: pzb.position.y,
        rotation
      }
      LageplanFeature.createBBox(feature, position, bbox, translate, [1, 1])
    }
  }

  compareChangedState (initial: SiteplanState, final: SiteplanState): Feature<Geometry>[] {
    return super.compareChangedState(initial, final, [], pzb => this.getObjectSvg(pzb))
  }
}
