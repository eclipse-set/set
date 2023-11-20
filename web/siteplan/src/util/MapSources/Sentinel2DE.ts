/*
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
import TileLayer from 'ol/layer/Tile'
import TileWMS from 'ol/source/TileWMS'
import { MapSource, MapSourceType } from '../MapSource'
import TileSource from 'ol/source/Tile'

/**
 * Map source for Sentinel2-based satellite maps offered by
 * the Bundesamt für Kartographie und Geodäsie (BKG)
 *
 * @author Stuecker
 */
export default class Sentinel2DE extends MapSource {
  source: TileWMS | null = null

  constructor () {
    super('BKG_SENT', 'Sentinel 2 (DE)', MapSourceType.Satellite)
  }

  setAsSource (tileLayer: TileLayer<TileSource>): void {
    if (this.source === null) {
      this.source = new TileWMS({
        url: 'https://sgx.geodatenzentrum.de/wms_sentinel2_de',
        params: { LAYERS: 'topp:rgb', TILED: true },
        serverType: 'geoserver',
        crossOrigin: 'anonymous',
        attributions: '© Europäische Union, enthält Copernicus Sentinel-2 Daten ' +
          new Date().getFullYear() +
          ', verarbeitet durch das Bundesamt für Kartographie und Geodäsie (BKG)',
        attributionsCollapsible: false
      })
    }

    tileLayer.setSource(this.source)
  }
}
