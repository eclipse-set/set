/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

import LageplanFeature from '@/feature/LageplanFeature'
import { Position } from '@/model/Position'
import { Route, RouteSection } from '@/model/Route'
import { SiteplanState } from '@/model/SiteplanModel'
import { store } from '@/store'
import { Coordinate } from 'ol/coordinate'
import Feature from 'ol/Feature'
import Geometry from 'ol/geom/Geometry'
import OlLineString from 'ol/geom/LineString'
import { Stroke, Style } from 'ol/style'
import { createFeature, FeatureType } from './FeatureInfo'

export default class RouteFeature extends LageplanFeature<Route> {
  getFeatures (model: SiteplanState): Feature<Geometry> [] {
    return this.getObjectsModel(model).flatMap(route =>
      route?.sections?.flatMap(section =>
        this.createRouteSectionFeature(section)))
  }

  protected getObjectsModel (model: SiteplanState): Route[] {
    return model.routes
  }

  private createRouteSectionFeature (routeSection: RouteSection): Feature<Geometry> {
    const coordinates: Coordinate[] = []
    routeSection.positions.forEach((position: Position) => coordinates.push([position.x, position.y]))
    if (coordinates.length < 1) {
      throw Error('Error creating track section with GUID ' + routeSection.guid)
    }

    const feature = createFeature(
      FeatureType.Route,
      routeSection,
      new OlLineString(coordinates)
    )
    feature.setStyle((_, resolution) => {
      if (!store.state.routeVisible) {
        return new Style()
      }

      const baseResolution = this.map.getView().getResolutionForZoom(this.svgService.getBaseZoomLevel())
      const scale = baseResolution / resolution

      const style = new Style({
        stroke: new Stroke({
          color: '#a640ff',
          width: scale * 2
        })
      })
      return style
    })

    return feature
  }

  setFeatureColor (feature: Feature<Geometry>): Feature<Geometry> {
    return feature
  }
}
