/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

import LageplanFeature from '@/feature/LageplanFeature'
import { SignalPart } from '@/model/Signal'
import { SignalMount } from '@/model/SignalMount'
import { SiteplanState } from '@/model/SiteplanModel'
import { ISvgElement } from '@/model/SvgElement'
import { updateLabelOrientation } from '@/util/ModelExtensions'
import { Feature } from 'ol'
import { getCenter } from 'ol/extent'
import Geometry from 'ol/geom/Geometry'
import OlPoint from 'ol/geom/Point'
import { createFeature, FeatureType, getFeatureData } from './FeatureInfo'

/**
 * Container for all signal features
 *
 * @author Stuecker
 */
export default class SignalFeature extends LageplanFeature<SignalMount> {
  getFeatures (model: SiteplanState): Feature<Geometry>[] {
    return this.getObjectsModel(model).map(element => this.createSignalFeature(element))
  }

  protected getObjectsModel (model: SiteplanState): SignalMount[] {
    return model.signals
  }

  protected getObjectSvg (signalMount: SignalMount) {
    return this.svgService.getFeatureSvg(signalMount, FeatureType.Signal)
  }

  private createSignalFeature (signalMount: SignalMount): Feature<Geometry> {
    const feature = createFeature(
      FeatureType.Signal,
      signalMount,
      new OlPoint([signalMount.position.x, signalMount.position.y]),
      signalMount.attachedSignals[0].label?.text
    )

    const svg = this.getObjectSvg(signalMount)
    this.createSignalBBox(feature, signalMount, svg)
    feature.setStyle((_, resolution) => {
      const baseResolution = this.map.getView().getResolutionForZoom(this.svgService.getBaseZoomLevel())
      const scale = baseResolution / resolution

      // Rotate the signal labels according to the map rotation
      signalMount.attachedSignals.forEach(mount =>
        updateLabelOrientation(mount.label, signalMount.position.rotation, this.map))

      // Determine style for the signal mount
      const style = this.svgService.getFeatureStyle(signalMount, FeatureType.Signal)
      // Rescale the feature according to the current zoom level
      // to keep a constant size
      style.getImage()?.setScale(scale)
      // Rotate the feature
      style.getImage()?.setRotation(((signalMount.position.rotation) * Math.PI) / 180)
      return style
    })
    return feature
  }

  private createSignalBBox (feature: Feature<Geometry>, signalMount: SignalMount, signalSvg: ISvgElement) {
    if (!signalSvg.nullpunkt) {
      signalSvg.boundingBox.forEach(bbox => (
        LageplanFeature.createBBox(
          feature,
          signalMount.position,
          bbox,
          [0, 0],
          [1, 1]
        )
      ))
    } else {
      for (const bbox of signalSvg.boundingBox) {
        const translate = [
          getCenter(bbox)[0] - signalSvg.nullpunkt.x,
          signalSvg.nullpunkt.y - getCenter(bbox)[1]
        ]
        LageplanFeature.createBBox(feature, signalMount.position, bbox, translate, [1, 1])
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

  protected setFeatureRegionColor (
    feature: Feature<Geometry>,
    objectPart?: string | undefined,
    color?: number[]
  ): Feature<Geometry> {
    const signalModel = getFeatureData(feature) as SignalMount
    // IMPROVE: Because at the moment Signal Additive doesn't have GUID,
    // you can't set region color for this.
    switch (objectPart) {
      case SignalPart.Label:
      case SignalPart.RouteMarker:
      case SignalPart.Mast: {
        this.setObjectColor(signalModel, objectPart, color ?? this.getRegionColor(feature))
        break
      }
      case SignalPart.Schirm: {
        if (signalModel.attachedSignals.length === 1) {
          this.setObjectColor(
            signalModel,
            objectPart,
            color ?? this.getRegionColor(feature, signalModel.attachedSignals[0].guid)
          )
        } else {
          signalModel.attachedSignals.forEach(signal => {
            const regionColor = color ?? this.getRegionColor(feature, signal.guid)
            this.setObjectColor(signalModel, `${objectPart}_${signal.guid}`, regionColor)
          })
        }

        break
      }
      default:
        break
    }

    return feature
  }

  compareChangedState (initial: SiteplanState, final: SiteplanState): Feature<Geometry>[] {
    return super.compareChangedState(initial, final, [], mount => this.getObjectSvg(mount), Object.values(SignalPart))
  }
}
