/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

import LageplanFeature from '@/feature/LageplanFeature'
import ContinuousTrackSegment from '@/model/ContinuousTrackSegment'
import { Coordinate, Position } from '@/model/Position'
import { SiteplanColorValue, SiteplanState } from '@/model/SiteplanModel'
import { getColor, getLabelColor } from '@/model/SiteplanObject'
import TrackSwitch, { SwitchType, TrackSwitchPart } from '@/model/TrackSwitch'
import TrackSwitchComponent, { TurnoutOperatingMode } from '@/model/TrackSwitchComponent'
import { angle, clampAngle, distance, pointInDistance, toDeg } from '@/util/Math'
import { updateLabelColor, updateLabelOrientation } from '@/util/ModelExtensions'
import { compare } from '@/util/ObjectExtension'
import { PolygonBuilder } from '@/util/PolygonBuilder'
import SvgDrawTrackSwitch from '@/util/SVG/Draw/SvgDrawTrackSwitch'
import { Feature } from 'ol'
import Geometry from 'ol/geom/Geometry'
import LineString from 'ol/geom/LineString'
import Point from 'ol/geom/Point'
import Polygon from 'ol/geom/Polygon'
import { Stroke, Style } from 'ol/style'
import { createFeature, FeatureType, getFeatureData } from './FeatureInfo'

/**
 * Container for all track switch features
 *
 * @author Stuecker
 */
export interface TrackSwitchFeatureData {
  guid: string
  trackSwitch: TrackSwitch
  component: TrackSwitchComponent | null
  outlineCoor: number[][]
}
export default class TrackSwitchFeature extends LageplanFeature<TrackSwitch> {
  private static LABEL_DISTANCE = 2.5

  protected getObjectsModel (model: SiteplanState): TrackSwitch[] {
    return model.trackSwitches
  }

  getFeatures (model: SiteplanState): Feature<Geometry>[] {
    return model.trackSwitches.flatMap(tswitch => this.createTrackSwitchFeature(tswitch))
  }

  private createTrackSwitchFeature (tswitch: TrackSwitch): Feature<Geometry>[] {
    const features = tswitch.components.map(x => this.createTrackSwitchComponentFeature(tswitch, x))
    if (!tswitch.continuousSegments) {
      tswitch.continuousSegments = []
    }

    const continuousSegmentFeatures = tswitch.continuousSegments.map(x => (
      this.createContinuousTrackSegmentFeature(x, tswitch)
    ))
    return features.concat(continuousSegmentFeatures)
  }

  private createTrackSwitchComponentFeature (tswitch: TrackSwitch, component: TrackSwitchComponent): Feature<Geometry> {
    const coordinates: number[][] = []

    // As we define a polygon from a line of the outer shell coordinates, the
    // coordinates must be in the correct order.
    // As both sets of leg coordinates start at the TOP_Knoten,
    // we reverse the sideLeg
    // so that a triangle-shaped polygon
    // TOP_Knoten -> main leg end -> side leg end -> TOP_Knoten...
    // is created
    component.mainLeg.coordinates.forEach(coor => coordinates.push([coor.x, coor.y]))
    const sideLegCoorClone: Coordinate[] = component.sideLeg.coordinates.map(coor => {
      return {
        x: coor.x,
        y: coor.y
      }
    })

    // By EKW the side leg start from cross point,
    // also end of the main leg
    if (!tswitch.design?.startsWith('EKW')) {
      sideLegCoorClone.reverse()
    }

    sideLegCoorClone.forEach(coor => coordinates.push([coor.x, coor.y]))

    // DKW and KR haven't the legs same TOP_Kante
    if (tswitch.design?.startsWith('DKW') || tswitch.design?.startsWith('KR') || tswitch.design?.startsWith('EKW')) {
      coordinates.push([component.mainLeg.coordinates[0].x, component.mainLeg.coordinates[0].y])
    }

    // Geometries for the three components
    const outlineGeometry = new Polygon([coordinates])
    const startGeometry = new Point([component.start.x, component.start.y])
    const labelGeometry = new Point(this.getLabelPosition(component.labelPosition, outlineGeometry))

    const data: TrackSwitchFeatureData = {
      guid: tswitch.guid,
      trackSwitch: tswitch,
      component,
      outlineCoor: coordinates
    }
    const feature = createFeature(
      FeatureType.TrackSwitch,
      data,
      outlineGeometry,
      component.label?.text
    )
    LageplanFeature.createBBox(
      feature,
      { ...component.sideLeg.coordinates[0], rotation: 0 },
      outlineGeometry.getExtent(),
      [0, 0],
      [1, 1],
      outlineGeometry
    )
    const trackSwitchMainColor = getColor(tswitch, TrackSwitchPart.Main) ?? 'black'
    feature.setStyle((_, resolution) => {
      const baseResolution = this.map.getView().getResolutionForZoom(this.svgService.getBaseZoomLevel())
      const scale = baseResolution / resolution
      const result: Style[] = []

      // Determine style for the track switch
      const mainStyle = this.svgService.getFeatureStyle(
        {
          featureData: data,
          drawPart: TrackSwitchPart.Main
        },
        FeatureType.TrackSwitch
      )
      mainStyle.getImage()?.setScale(scale)
      mainStyle.setGeometry(startGeometry)

      result.push(mainStyle)

      const switchStyle = this.getComponentStyle(component, trackSwitchMainColor , scale)
      result.push(...switchStyle)
      updateLabelOrientation(component.label, component.labelPosition.rotation, this.map)
      updateLabelColor(component.label, getLabelColor(tswitch))
      if (tswitch.switchType !== SwitchType.SimpleCross &&
        tswitch.switchType !== SwitchType.FlatCross) {
        const startStyle = this.svgService.getFeatureStyle(
          {
            featureData: data,
            drawPart: TrackSwitchPart.StartMarker
          },
          FeatureType.TrackSwitch
        )
        // Rescale the feature according to the current zoom level
        // to keep a constant size
        startStyle.getImage()?.setScale(scale)
        startStyle.setGeometry(startGeometry)
        // Rotate the feature
        startStyle.getImage()?.setRotation((component.start.rotation * Math.PI) / 180)
        result.push(startStyle)
      }

      // Add the label
      if (component.label) {
        const labelStyle = this.svgService.getFeatureStyle(
          { featureData: data },
          FeatureType.TrackSwitch,
          component.label
        )
        labelStyle.getImage()?.setScale(scale)
        labelStyle.setGeometry(labelGeometry)
        labelStyle.getImage()?.setRotation((component.labelPosition.rotation * Math.PI) / 180)
        result.push(labelStyle)
      }

      return result
    })

    return feature
  }

  private getComponentStyle (
    component: TrackSwitchComponent,
    color: string | number[],
    scale: number
  ): Style[] {
    switch (component.operatingMode) {
      case TurnoutOperatingMode.Trailable:
        const labelSpilt = component.label?.text.split(' ')
        if (!labelSpilt?.some(ele => ele === 'Rf') && component.label) {
          component.label.text += ' Rf'
        }

        const mainLegLastPos = component.mainLeg.coordinates[component.mainLeg.coordinates.length - 1]
        const sideLegLastPos = component.sideLeg.coordinates[component.sideLeg.coordinates.length - 1]
        const preferredLocation = component.preferredLocation
        let arrowCoors: Coordinate[]
        if (preferredLocation !== null &&
          preferredLocation === component.sideLeg.connection) {
          arrowCoors = [mainLegLastPos, sideLegLastPos]
        } else {
          arrowCoors = [sideLegLastPos, mainLegLastPos]
        }

        return SvgDrawTrackSwitch.getTrailableArrow(arrowCoors,color, scale)
      case TurnoutOperatingMode.DeadLeft:
        return this.getTrackSwitchLockCross(component, 'links', scale)
      case TurnoutOperatingMode.DeadRight:
        return this.getTrackSwitchLockCross(component, 'rechts', scale)
      default:
        return []
    }
  }

  private getTrackSwitchLockCross (component: TrackSwitchComponent, lockSide: string, scale: number) {
    if (component.mainLeg.connection.toLowerCase() === lockSide) {
      const sideLegCoorsLength = component.sideLeg.coordinates.length
      const lastPos = component.sideLeg.coordinates[sideLegCoorsLength - 1]
      const preLastPos = component.sideLeg.coordinates[sideLegCoorsLength - 2]
      return SvgDrawTrackSwitch.getTrackSwitchLockCross([lastPos, preLastPos], scale)
    } else {
      const mainLegCoorsLength = component.mainLeg.coordinates.length
      const lasPos = component.mainLeg.coordinates[mainLegCoorsLength - 1]
      const preLastPos = component.mainLeg.coordinates[mainLegCoorsLength - 2]
      return SvgDrawTrackSwitch.getTrackSwitchLockCross([lasPos, preLastPos], scale)
    }
  }

  private createContinuousTrackSegmentFeature (segment: ContinuousTrackSegment, trackSwitch: TrackSwitch) {
    // Ratio of the length to draw the segment with
    // (1 = 100%, 0.5 = 50%, 0 = 0%)
    const CONTINUOUS_SEGMENT_LENGTH_RATIO = 0.5
    // Scaled width of the segment
    const CONTINUOUS_SEGMENT_WIDTH = 2

    // Draw a centered line between start and
    // end coordinates of length distance * CONTINUOUS_SEGMENT_LENGTH_RATIO
    const directionAngle = toDeg(angle([segment.start.x, segment.start.y], [segment.end.x, segment.end.y]))
    const length = distance([segment.start.x, segment.start.y], [segment.end.x, segment.end.y])
    const startCoordinates = new PolygonBuilder(segment.start.x, segment.start.y, - directionAngle)
      .moveDistance(length * ((1 - CONTINUOUS_SEGMENT_LENGTH_RATIO) / 2), 90)
      .getCoordinates()
    const endCoordinates = new PolygonBuilder(segment.end.x, segment.end.y,180 - directionAngle)
      .moveDistance(length * ((1 - CONTINUOUS_SEGMENT_LENGTH_RATIO) / 2), 90)
      .getCoordinates()
    const coordinates = startCoordinates.concat(endCoordinates)
    const data: TrackSwitchFeatureData = {
      guid: trackSwitch.guid,
      trackSwitch,
      component: null,
      outlineCoor: coordinates
    }
    const feature = createFeature(FeatureType.TrackSwitch, data, new LineString(coordinates))
    feature.setStyle((_, resolution) => {
      const baseResolution = this.map.getView().getResolutionForZoom(this.svgService.getBaseZoomLevel())
      const scale = baseResolution / resolution
      return new Style({
        stroke: new Stroke({
          width: CONTINUOUS_SEGMENT_WIDTH * scale,
          color: getColor(trackSwitch, TrackSwitchPart.Main) ?? 'black'
        })
      })
    })
    return feature
  }

  // Calculate best label position that is furthest away
  // from the track switch polygon
  private getLabelPosition (position: Position, outlineGeometry: Polygon) : number[] {
    const positivePoint = pointInDistance(
      [position.x, position.y],
      clampAngle(position.rotation + 90),
      TrackSwitchFeature.LABEL_DISTANCE
    )
    const negativePoint = pointInDistance(
      [position.x, position.y],
      clampAngle(position.rotation - 90),
      TrackSwitchFeature.LABEL_DISTANCE
    )

    if (outlineGeometry.containsXY(positivePoint[0], positivePoint[1])) {
      return negativePoint
    } else if (outlineGeometry.containsXY(negativePoint[0], negativePoint[1])) {
      return positivePoint
    }

    const positiveDistance = distance(outlineGeometry.getClosestPoint(positivePoint), positivePoint)
    const negativeDistance = distance(outlineGeometry.getClosestPoint(negativePoint), negativePoint)

    return positiveDistance > negativeDistance ? positivePoint : negativePoint
  }

  setFeatureColor (feature: Feature<Geometry>, color?: number[], id?: string): Feature<Geometry> {
    if (color) {
      super.setFeatureColor(feature, color, id)
    } else {
      Object.values(TrackSwitchPart).forEach(part => {
        this.setFeatureRegionColor(feature, part)
      })
    }

    return feature
  }

  protected setFeatureRegionColor (feature: Feature<Geometry>, featurePart?: string | undefined): Feature<Geometry> {
    const featureData = getFeatureData(feature) as TrackSwitchFeatureData
    featureData.trackSwitch.components.forEach(component => {
      const componentRegionColor = this.getRegionColor(feature, component.guid)
      const partID = `${featurePart}_${component.guid}`
      this.setObjectColor(featureData.trackSwitch, partID, componentRegionColor)
    })
    return feature
  }

  compareChangedState (initial: SiteplanState, final: SiteplanState): Feature<Geometry>[] {
    let isDiff = false
    this.getObjectsModel(initial).forEach(initialTrackSwitch => {
      const finalTrackSwitch = this.getObjectsModel(final).find(ele => ele.guid === initialTrackSwitch.guid)
      if (!finalTrackSwitch) {
        return
      }

      initialTrackSwitch.components.forEach(initialComponent => {
        finalTrackSwitch.components.forEach(finalComponent => {
          if (initialComponent.guid !== finalComponent.guid) {
            return
          }

          if (finalComponent.label !== initialComponent.label) {
            this.setLabelColor(finalTrackSwitch, SiteplanColorValue.COLOR_ADDED)
            this.setLabelColor(initialTrackSwitch, SiteplanColorValue.COLOR_REMOVED)
            isDiff = true
          }

          const isDiffMainLeg = compare(initialComponent.mainLeg, finalComponent.mainLeg)
          const isDiffSideLeg = compare(initialComponent.sideLeg, finalComponent.sideLeg)
          if (isDiffMainLeg || isDiffSideLeg ||
            initialComponent.preferredLocation !== finalComponent.preferredLocation) {
            this.setObjectColor(finalTrackSwitch, 'feature', SiteplanColorValue.COLOR_ADDED)
            this.setObjectColor(initialTrackSwitch, 'feature',SiteplanColorValue.COLOR_REMOVED)
            isDiff = true
          }
        })
      })
    })
    return isDiff ? this.createCompareFeatures(initial, final) : this.getFeatures(final)
  }
}
