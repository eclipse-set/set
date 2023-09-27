/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

import LageplanFeature from '@/feature/LageplanFeature'
import { Label } from '@/model/Label'
import { Position } from '@/model/Position'
import { SignalPart } from '@/model/Signal'
import { SignalMount } from '@/model/SignalMount'
import { SiteplanState } from '@/model/SiteplanModel'
import { getColor } from '@/model/SiteplanObject'
import { pointInDistance } from '@/util/Math'
import { updateLabelColor, updateLabelOrientation } from '@/util/ModelExtensions'
import NamedFeatureLayer from '@/util/NamedFeatureLayer'
import { Feature } from 'ol'
import { LineString, Point } from 'ol/geom'
import Geometry from 'ol/geom/Geometry'
import { Stroke, Style } from 'ol/style'
import { createFeature, FeatureLayerType, FeatureType, getFeatureData, getFeatureType } from './FeatureInfo'

/**
 * Container for all track features
 *
 * @author Stuecker
 */

export default class SignalRouteMarkerFeature extends LageplanFeature<SignalMount> {
  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  protected getObjectsModel (model: SiteplanState): SignalMount[] {
    return []
  }

  getDelayedFeatures (model: SiteplanState, layers: NamedFeatureLayer[]): Feature<Geometry>[] {
    const signalLayer = layers.find(layer => layer.getLayerType() === FeatureLayerType.Signal)
    const source = signalLayer?.getSource()
    if (!source) {
      return []
    }

    const signalFeatures = source.getFeatures().filter(feature => getFeatureType(feature) === FeatureType.Signal)
    return signalFeatures.flatMap(feature => this.createSignalLocationLine(feature))
  }

  private createSignalLocationLine (signalFeature: Feature<Geometry>): Feature<Geometry>[] {
    const signalMount = getFeatureData(signalFeature) as SignalMount
    const signalPos = signalMount.position
    // Find direction of the signal
    const lineCoordinates = this.getLocationLineCoors(signalPos)
    const lineFeature = createFeature(
      FeatureType.SignalRouteMarker,
      signalMount.attachedSignals[0],
      new LineString([lineCoordinates[0], lineCoordinates[1]])
    )
    lineFeature.setStyle((_, resolution) => {
      const baseResolution = this.map.getView().getResolutionForZoom(this.svgService.getBaseZoomLevel())
      const scale = baseResolution / resolution
      const style = new Style({
        stroke: new Stroke({
          color: getColor(signalMount, SignalPart.RouteMarker) ?? 'black'
        })
      })
      style.getStroke().setWidth(scale * 2)
      return style
    })

    // Add km text
    const textFeature = createFeature(
      FeatureType.SignalRouteMarker,
      signalMount.attachedSignals[0],
      new Point([signalPos.x, signalPos.y])
    )

    const label: Label = {
      text: signalMount.attachedSignals[0].routeLocations[0].km,
      orientationInverted: false,
      color: getColor(signalMount, SignalPart.RouteMarker) ?? [0, 0, 0]
    }

    const labelRotation = signalPos.rotation + 180
    textFeature.setStyle((_, resolution) => {
      const baseResolution = this.map.getView().getResolutionForZoom(this.svgService.getBaseZoomLevel())
      const scale = baseResolution / resolution
      updateLabelOrientation(label, labelRotation + 90, this.map)
      updateLabelColor(label, getColor(signalMount, SignalPart.RouteMarker))
      const style = this.svgService.getFeatureStyle(
        signalMount.attachedSignals[0],
        FeatureType.SignalRouteMarker,
        label
      )
      // Rescale the feature according to the current zoom level
      // to keep a constant size
      style
        .getImage()
        .setScale(scale)
      style.getImage().setRotation((labelRotation * Math.PI) / 180)
      return style
    })

    this.followFeatureMovement(lineFeature, signalFeature)
    this.followFeatureMovement(textFeature, signalFeature)

    return [textFeature, lineFeature]
  }

  private getLocationLineCoors (singalPos: Position): number[][] {
    const offset = 2
    const startPoint = pointInDistance([singalPos.x, singalPos.y], singalPos.rotation + 90, offset)
    const endPoint = pointInDistance([singalPos.x, singalPos.y], singalPos.rotation + 90, offset * 6)
    return [startPoint, endPoint]
  }

  private followFeatureMovement (feature: Feature<Geometry>, featureToFollow: Feature<Geometry>) {
    const originalPoint = (featureToFollow.getGeometry() as Point).clone()
    const originalGeometry = feature.getGeometry()
    featureToFollow.getGeometry()?.on('change', () => {
      const newPoint = featureToFollow.getGeometry() as Point
      if (originalPoint && newPoint) {
        const newCoord = newPoint.getCoordinates()
        const originalCoord = originalPoint.getCoordinates()
        const translate = [newCoord[0] - originalCoord[0], newCoord[1] - originalCoord[1]]
        feature.setGeometry(originalGeometry?.clone())
        feature.getGeometry()?.translate(translate[0], translate[1])
      }
    })
  }

  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  setFeatureColor (feature: Feature<Geometry>): Feature<Geometry> {
    return feature
  }
}
