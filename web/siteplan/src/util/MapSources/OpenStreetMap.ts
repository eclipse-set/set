/*
 * Copyright (c) 2021 DB Netz AG and others.
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
 * Map source for OpenStreeMap-based topological maps
 *
 * @author Stuecker
 */
export default class OpenStreetMap extends MapSource {
  source: OSM | null = null

  constructor () {
    super('OSM', 'OpenStreetMap', MapSourceType.Topological)
  }

  setAsSource (tileLayer: TileLayer<TileSource>): void {
    if (this.source !== null) {
      tileLayer.setSource(this.source)
    } else {
      this.source = new OSM({
        crossOrigin: 'anonymous'
      })
      tileLayer.setSource(this.source)
    }
  }
}
