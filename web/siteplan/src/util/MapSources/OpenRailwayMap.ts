/*
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
import TileLayer from 'ol/layer/Tile'
import OSM from 'ol/source/OSM'
import { MapSource, MapSourceType } from '../MapSource'
import TileSource from 'ol/source/Tile'

/**
 * Map source for OpenRailwayMap topological map
 *
 * @author Peters
 */
export default class OpenRailwayMap extends MapSource {
  source: OSM | null = null

  constructor () {
    super('ORM', 'OpenRailwayMap', MapSourceType.Topological)
  }

  setAsSource (tileLayer: TileLayer<TileSource>): void {
    if (this.source !== null) {
      tileLayer.setSource(this.source)
    } else {
      this.source = new OSM({
        crossOrigin: 'anonymous',
        url: 'https://{a-c}.tiles.openrailwaymap.org/standard/{z}/{x}/{y}.png',
        attributions:
          'Data <a target="_blank" href="https://www.openstreetmap.org/copyright">&copy; OpenStreetMap contributors</a>, ' +
          'Service by <a target="_blank" href="https://www.openrailwaymap.org/">OpenRailwayMap</a>'
      })
      tileLayer.setSource(this.source)
    }
  }
}
