/*
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
import TileLayer from 'ol/layer/Tile'
import { MapSource, MapSourceType } from '../MapSource'
import XYZ from 'ol/source/XYZ'
import TileSource from 'ol/source/Tile'
import Configuration from '../Configuration'

/**
 * Map source for Mapbox satellite maps
 * Requries a Mapbox api token
 *
 * @author Stuecker
 */
export default class MapboxSat extends MapSource {
  source: XYZ | null = null

  constructor () {
    super('MAPBOX_SAT', 'Mapbox', MapSourceType.Satellite, Configuration.getToolboxConfiguration().mapboxApiKey)
  }

  setAsSource (tileLayer: TileLayer<TileSource>): void {
    if (this.source === null) {
      this.source = new XYZ({
        attributions:
              '© <a target="_blank" href="https://www.mapbox.com/map-feedback/">Mapbox</a> ' +
              '© <a target="_blank" href="https://www.openstreetmap.org/copyright">' +
              'OpenStreetMap contributors</a>',
        url:
              'https://api.mapbox.com/v4/mapbox.satellite/{z}/{x}/{y}.jpg90?access_token=' +
              this.getAPIKey(),
        crossOrigin: 'anonymous'
      })
    }

    tileLayer.setSource(this.source)
  }
}
