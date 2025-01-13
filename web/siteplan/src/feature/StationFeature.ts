/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

import LageplanFeature from '@/feature/LageplanFeature'
import { SiteplanState } from '@/model/SiteplanModel'
import { getLabelColor } from '@/model/SiteplanObject'
import { defaultStationObj, Platform, Station, StationPart } from '@/model/Station'
import { centroid, midpoint, pointInDistance } from '@/util/Math'
import { isLabelFlipRequired } from '@/util/ModelExtensions'
import { getpropertypeName } from '@/util/ObjectExtension'
import { Feature } from 'ol'
import Geometry from 'ol/geom/Geometry'
import Point from 'ol/geom/Point'
import { Fill, Style, Text } from 'ol/style'
import { createFeature, FeatureType, getFeatureData } from './FeatureInfo'

/**
 * Container for all Station features
 *
 * @author Stuecker
 */
export default class StationFeature extends LageplanFeature<Station> {
  protected getObjectsModel (model: SiteplanState): Station[] {
    return model.stations
  }

  getFeatures (model: SiteplanState): Feature<Geometry>[] {
    return this.getObjectsModel(model).map(element => this.createStationFeature(element))
  }

  private createStationFeature (station: Station): Feature<Geometry> {
    const labelPositions = station.platforms.map(this.getStationLabelPosition)
    return this.createStationLabel(station, centroid(labelPositions))
  }

  private createStationLabel (station: Station, position: number[]): Feature<Geometry> {
    const avgRotation = (
      station.platforms
        .map(platform => (platform.points[0].rotation + platform.points[1].rotation) / 2 + 90)
        .reduce((p, c) => p + c, 0)
    ) / station.platforms.length
    const feature = createFeature(
      FeatureType.Station,
      station,
      new Point(position),
      station.label?.text
    )
    if (station.label?.text) {
      feature.setStyle((_, resolution) => {
        const baseResolution = this.map.getView().getResolutionForZoom(this.svgService.getBaseZoomLevel())
        const scale = baseResolution / resolution
        let textRotation = avgRotation
        if (isLabelFlipRequired(textRotation - 90, this.map)) {
          textRotation += 180
        }
        
        return new Style({
          text: new Text({
            text: 'Bstg ' + station.label.text,
            scale: scale * 3,
            rotateWithView: true,
            rotation: textRotation * Math.PI / 180,
            textAlign: 'center',
            fill: new Fill({
              color: getLabelColor(station) ?? 'black'
            })
          })
        })
      })
    }
    
    return feature
  }

  private getStationLabelPosition (platform: Platform): number[] {
    const avgRotation = (platform.points[0].rotation + platform.points[1].rotation) / 2 + 90
    return pointInDistance(midpoint(
      pointInDistance([platform.points[0].x, platform.points[0].y], platform.points[0].rotation, 3),
      pointInDistance([platform.points[1].x, platform.points[1].y], platform.points[1].rotation, 3)
    ), avgRotation, 5)
  }

  setFeatureColor (feature: Feature<Geometry>, color?: number[] | undefined): Feature<Geometry> {
    return color
      ? super.setFeatureColor(feature, color)
      : this.setFeatureRegionColor(feature)
  }

  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  protected setFeatureRegionColor (feature: Feature<Geometry>, featurePart?: string | undefined): Feature<Geometry> {
    const station = getFeatureData(feature) as Station
    station.platforms.forEach(platform => {
      const regionColor = this.getRegionColor(feature, platform.guid)
      Object.values(StationPart).forEach(part => {
        this.setObjectColor(
          station,
          `${part}_${platform.guid}`,
          regionColor
        )
      })
    })

    return feature
  }

  compareChangedState (initial: SiteplanState, final: SiteplanState): Feature<Geometry>[] {
    const compareProps = [
      {
        prop: getpropertypeName(defaultStationObj(), x => x.label),
        partID: 'label'
      },
      {
        prop: getpropertypeName(defaultStationObj(), x => x.platforms)
      }
    ]
    return super.compareChangedState(initial, final, compareProps)
  }
}
