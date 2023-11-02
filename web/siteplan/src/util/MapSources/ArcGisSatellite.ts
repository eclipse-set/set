/*
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
import TileLayer from 'ol/layer/Tile'
import XYZ from 'ol/source/XYZ'
import { MapSource, MapSourceType } from '../MapSource'
import TileSource from 'ol/source/Tile'

/**
 * Map source for ArcGis satellite maps
 *
 * @author Stuecker
 */
export default class ArcGisSatellite extends MapSource {
  constructor () {
    super('ARCGIS', 'Esri Satellit', MapSourceType.Satellite)
  }

  setAsSource (tileLayer: TileLayer<TileSource>): void {
    tileLayer.setSource(new XYZ({
      attributions: [
        'Powered by Esri',
        'Source: Esri, DigitalGlobe, GeoEye, Earthstar Geographics, CNES/Airbus DS, USDA, USGS, AeroGRID, IGN, and the GIS User Community'
      ],
      attributionsCollapsible: false,
      url: 'https://services.arcgisonline.com/ArcGIS/rest/services/World_Imagery/MapServer/tile/{z}/{y}/{x}',
      maxZoom: 23,
      crossOrigin: 'anonymous'
    }))
  }
}
